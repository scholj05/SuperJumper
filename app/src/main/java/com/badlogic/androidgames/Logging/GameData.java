package com.badlogic.androidgames.Logging;

import android.os.SystemClock;

import com.badlogic.androidgames.jumper.Settings;

import java.util.Date;
import java.util.Set;

/**
 * Created by Jesse on 22/11/2017.
 */

public class GameData {

    private String gameStart;           // time of game being played
    private Date gameStartDate;         // temp value for internal use before stop
    public String gameTime;             // time of game lost
    public String gameScore;            // score achieved this game
    public String levelsCompleted;      // levels completed this game
    public String colourMode;           // b/w vs coloured
    public String brightnessMode;       // light vs dark
    public String gender;               // chosen character gender
    public String soundOn;              // sound enabled
    public String progressionDirection; // direction of game

    public GameData()
    {
        colourMode = (com.badlogic.androidgames.jumper.Settings.isColour ? "coloured" : "black and white");
        brightnessMode = (com.badlogic.androidgames.jumper.Settings.isLight ? "light" : "dark");
        soundOn = Boolean.toString(com.badlogic.androidgames.jumper.Settings.soundEnabled);
        progressionDirection = Settings.gameDirectionUp ? "up" : "down";

        int genderInt = com.badlogic.androidgames.jumper.Settings.currentGender;
        if (genderInt == 0) gender = "male";
        else if (genderInt == 1) gender = "female";
        else gender = "neutral";
        gameStart = getTimeAsString();
        gameStartDate = getTime();
    }

    public void stop()
    {
        gameTime = Long.toString((getTime().getTime() - gameStartDate.getTime()) / 1000);
        gameScore = Integer.toString(Settings.lastScore);
        levelsCompleted = Integer.toString(Settings.lastLevelsThisLife);
    }

    public String gameKey()
    {
        return gameStart;
    }

    private String getTimeAsString()
    {
        Date date = new Date();
        return date.toString();
    }

    private Date getTime()
    {
        Date date = new Date();
        return date;
    }
}
