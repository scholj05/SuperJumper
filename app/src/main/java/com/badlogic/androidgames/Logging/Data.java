package com.badlogic.androidgames.Logging;

import android.content.Context;
import android.os.SystemClock;
import android.provider.Settings;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jesse on 22/11/2017.
 */

public class Data {

    private String userID;          // mac address
    private String appStart;          // time of app opening
    private String appStop;           // time of app closing
    private List<GameData> gameList = new ArrayList<GameData>();
    private GameData currentGame;

    FirebaseDatabase database;
    DatabaseReference reference;


    public Data(Context context)
    {
        userID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        start();
    }

    public void start()
    {
        appStart = getTime();
    }

    public void stop()
    {
        appStop = getTime();
        upload();
    }


    public void newGame()
    {
        currentGame = new GameData();
    }

    public void endGame()
    {
        if (currentGame == null) return;

        currentGame.stop();
        gameList.add(currentGame);
        currentGame = null;
    }

    private void upload()
    {
        //reference.child(Long.toString(appStart)).child("app_close").setValue(Long.toString(appStop));
        for (GameData i : gameList)
        {
            reference.child(userID).child(appStart).child(i.gameKey()).setValue(i);
        }
        gameList.clear();
    }

    private String getTime()
    {
        Date date = new Date();
        return date.toString();
    }
}

