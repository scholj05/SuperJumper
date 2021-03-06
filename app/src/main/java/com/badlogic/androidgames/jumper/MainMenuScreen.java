package com.badlogic.androidgames.jumper;

import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Animation;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class MainMenuScreen extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle soundBounds;
	Rectangle playBounds;
	Rectangle highscoresBounds;
	Rectangle helpBounds;
	Rectangle lightDarkBounds;
	Rectangle blackWhiteBounds;
	Rectangle upDownBounds;
	Rectangle genderBounds;
	Rectangle userBounds;
	Vector2 touchPoint;

	float stateTime;
	
	public MainMenuScreen(Game game) {
		super(game);
		guiCam = new Camera2D(glGraphics, 320, 480);
		batcher = new SpriteBatcher(glGraphics, 100);
		soundBounds = new Rectangle(0, 0, 64, 64);
		playBounds = new Rectangle(160 - 150, 250 + 18, 300, 36);
		highscoresBounds = new Rectangle(160 - 150, 250 - 18, 300, 36);
		helpBounds = new Rectangle(160 - 150, 250 - 18 - 36, 300, 36);
		lightDarkBounds = new Rectangle(160 - 150, 170 - 10, 300, 20);
		blackWhiteBounds = new Rectangle(160 - 150, 145 - 10, 300, 20);
		upDownBounds = new Rectangle(160 - 150, 120 - 10, 300, 20);
		genderBounds = new Rectangle(160 - 50, 56 - 32, 100, 64);
		userBounds = new Rectangle(0, 480 - 20, 320, 20);

		touchPoint = new Vector2();
	}
	
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();	
		int len = touchEvents.size();
		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP) {
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);

				if(OverlapTester.pointInRectangle(userBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					game.setScreen(new UserSelection(game));
					return;
				}
				if(OverlapTester.pointInRectangle(playBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					if (Settings.currentUser.equals("no one"))
					{
						game.setScreen(new UserSelection(game));
						glGame.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(game.getContext(), "Please login before playing", Toast.LENGTH_LONG).show();
							}
						});
					}
					else
						game.setScreen(new GameScreen(game));
					return;
				}
				if(OverlapTester.pointInRectangle(highscoresBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					game.setScreen(new HighscoresScreen(game));
					return;
				}
				if(OverlapTester.pointInRectangle(helpBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					game.setScreen(new HelpScreen(game));
					return;
				}
				if(OverlapTester.pointInRectangle(lightDarkBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					Settings.toggleLight();
					return;
				}
				if(OverlapTester.pointInRectangle(blackWhiteBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					Settings.toggleColour();
					return;
				}
				if(OverlapTester.pointInRectangle(upDownBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					Settings.gameDirectionUp = !Settings.gameDirectionUp;
					Log.d("upDownToggle", Boolean.toString(Settings.gameDirectionUp));
					return;
				}
				if(OverlapTester.pointInRectangle(genderBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					Settings.toggleGender();
					return;
				}
				if(OverlapTester.pointInRectangle(soundBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					Settings.soundEnabled = !Settings.soundEnabled;
					if(Settings.soundEnabled)
						Assets.music.play();
					else
						Assets.music.pause();
				}
			}
		}
		stateTime += deltaTime;
		stateTime = (stateTime >= 0.8f ? 0 : stateTime);

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

		String userString = "Logged in as " + Settings.currentUser;

		batcher.beginBatch(Assets.items_extended);
		Assets.smallFont.drawText(batcher, userString, 320 - (Assets.smallFont.glyphWidth * (userString.length() <= 39 ? userString.length() : 39)), 480 - Assets.smallFont.glyphHeight);
		batcher.endBatch();

		batcher.beginBatch(Assets.items);
		batcher.drawSprite(160, 480 - 24 - 71, 274, 142, Assets.logo);
		batcher.drawSprite(160, 250, 300, 110, Assets.mainMenu);
		batcher.drawSprite(160, 170, 84, 14, Settings.isLight?Assets.colourLight:Assets.colourDark);
		batcher.drawSprite(160, 145, 145, 14, Settings.isColour?Assets.coloured:Assets.blackWhite);
		batcher.drawSprite(160, 121, 68, 14, Settings.gameDirectionUp?Assets.up:Assets.down);
		batcher.drawSprite(155, 56, 64, 64, Assets.bobFall.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING));
		batcher.drawSprite(155, 14, 120, 14, Assets.gender);
		batcher.drawSprite(32, 32, 64, 64, Settings.soundEnabled?Assets.soundOn:Assets.soundOff);
		// arrows for settings buttons
		batcher.drawSprite(110, 170, 9, 9, Assets.left);
		batcher.drawSprite(211, 170, 9, 9, Assets.right);
		batcher.drawSprite(76, 145, 9, 9, Assets.left);
		batcher.drawSprite(244, 145, 9, 9, Assets.right);
		batcher.drawSprite(117, 121, 9, 9, Assets.left);
		batcher.drawSprite(204, 121, 9, 9, Assets.right);
		batcher.drawSprite(124, 50, 9, 9, Assets.left);
		batcher.drawSprite(194, 50, 9, 9, Assets.right);

		batcher.endBatch();

		// used to check the bounding box is correctly located by drawing it with a transparent texture
//		batcher.beginBatch(Assets.debug);
//		batcher.drawSprite(160, 120, 300, 20, Assets.debugBox);
//		batcher.endBatch();

		gl.glDisable(GL10.GL_BLEND);
	}	
	
	@Override
	public void pause() {
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}


}
