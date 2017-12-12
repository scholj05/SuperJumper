package com.badlogic.androidgames.Logging;

import android.content.Context;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Jesse on 22/11/2017.
 */

public class Data {

    private String userID;          // mac address
    private String appStart;          // time of app opening
    private String appStop;           // time of app closing
    private List<GameData> gameList = new ArrayList<GameData>();
    private List<String> userList = new ArrayList<String>();
    private GameData currentGame;

    FirebaseDatabase database;
    DatabaseReference reference;


    public Data(Context context)
    {
        userID = com.badlogic.androidgames.jumper.Settings.currentUser;//.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        reference.child("userList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                Log.d("Count " ,""+dataSnapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren())
                {
                    String name = (String) postSnapshot.getKey();
                    if (name != null)
                        userList.add(name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Retrieve failed: " ,databaseError.getMessage());
            }
        });


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
        userID = com.badlogic.androidgames.jumper.Settings.currentUser;
        for (GameData i : gameList)
        {
            reference.child("sessions").child(userID).child(appStart).child(i.gameKey()).setValue(i);
        }
        gameList.clear();
    }

    private String getTime()
    {
        Date date = new Date();
        return date.toString();
    }

    public boolean addNewUser(String user)
    {
        if (userList.contains(user)) return false;
        else
        {
            reference.child("userList").child(user).setValue(0);
            return true;
        }
    }

    public boolean login(String user)
    {
        if (!com.badlogic.androidgames.jumper.Settings.currentUser.equals("no one"))
            stop(); start();
        if (!userList.contains(user)) return false;
        else
        {
            com.badlogic.androidgames.jumper.Settings.currentUser = user;
            return true;
        }
    }
}

