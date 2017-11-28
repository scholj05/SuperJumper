package com.badlogic.androidgames.jumper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Looper;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

import java.util.List;
import java.util.Set;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Jesse on 24/11/2017.
 */

public class UserSelection extends GLScreen {
    Game game;
    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle backBounds;
    Rectangle addUserBounds;
    Rectangle loginBounds;
    Vector2 touchPoint;
    String[] highScores;
    float xOffset = 0;
    String textInput = "";

    public UserSelection(Game game) {//TODO: alertDialog w/ text input for logins
        super(game);
        this.game = game;
        guiCam = new Camera2D(glGraphics, 320, 480);
        backBounds = new Rectangle(0, 0, 64, 64);
        addUserBounds = new Rectangle(160 - 62.5f, 300 - 20, 125, 40);
        loginBounds = new Rectangle(160 - 62.5f, 200 - 20, 125, 40);

        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 100);
        xOffset = 160 - xOffset / 2 + Assets.font.glyphWidth / 2;




    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);

            if(event.type == Input.TouchEvent.TOUCH_UP) {
                if(OverlapTester.pointInRectangle(backBounds, touchPoint)) {
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
                if(OverlapTester.pointInRectangle(addUserBounds, touchPoint)) {
                    addUser();
                    return;
                }
                if(OverlapTester.pointInRectangle(loginBounds , touchPoint)) {
                    login();
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();

        gl.glEnable(GL10.GL_TEXTURE_2D);

        batcher.beginBatch(Assets.background);
        batcher.drawSprite(160, 240, 320, 480, Assets.backgroundRegion);
        batcher.endBatch();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Assets.items_extended);
        batcher.drawSprite(160, 200, 125, 14, Assets.login);
        batcher.drawSprite(160, 300, 125, 14, Assets.newUser);
        batcher.endBatch();

        batcher.beginBatch(Assets.items);
        //batcher.drawSprite(160, 360, 300, 33, Assets.highScoresRegion);

//        float y = 240;
//        for(int i = 4; i >= 0; i--) {
//            Assets.font.drawText(batcher, highScores[i], xOffset, y);
//            y += Assets.font.glyphHeight;
//        }

        batcher.drawSprite(32, 32, 64, 64, Assets.arrow);
        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
    }

    private void addUser()
    {
        final Runnable addUser = new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(glGame.getContext());
                builder.setTitle("Enter new user name");

                final EditText input = new EditText(glGame.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String textInput = input.getText().toString();
                        if (!glGame.data.addNewUser(textInput))
                        {
                            Toast.makeText(glGame.getContext(), "User already exists", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(glGame.getContext(), "Added new user: " + textInput, Toast.LENGTH_SHORT).show();
                            Settings.currentUser = textInput;
                            game.setScreen(new MainMenuScreen(game));
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        };
        glGame.runOnUiThread(addUser);
    }

    private void login()
    {
        final Runnable login = new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(glGame.getContext());
                builder.setTitle("Enter your user name");

                final EditText input = new EditText(glGame.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String textInput = input.getText().toString();
                        if (!glGame.data.login(textInput))
                        {
                            Toast.makeText(glGame.getContext(), "User doesn't exist, please try again", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(glGame.getContext(), "Logged in as: " + textInput, Toast.LENGTH_SHORT).show();
                            Settings.currentUser = textInput;
                            game.setScreen(new MainMenuScreen(game));
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        };
        glGame.runOnUiThread(login);
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
    }
}
