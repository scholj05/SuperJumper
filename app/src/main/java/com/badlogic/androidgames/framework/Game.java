package com.badlogic.androidgames.framework;

import android.content.Context;

import com.badlogic.androidgames.Logging.Data;

public interface Game {
    public Input getInput();
    
    public FileIO getFileIO();

    public Data getData();
    
    public Graphics getGraphics();
    
    public Audio getAudio();
    
    public void setScreen(Screen screen);
    
    public Screen getCurrentScreen();
    
    public Screen getStartScreen();

    public Context getContext();
}
