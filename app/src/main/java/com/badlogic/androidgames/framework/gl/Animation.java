package com.badlogic.androidgames.framework.gl;

import android.util.Log;

public class Animation {
	public static final int ANIMATION_LOOPING = 0;
	public static final int ANIMATION_NONLOOPING = 1;

	final TextureRegion[] keyFrames;
	final float frameDuration;
	
	public Animation(float frameDuration, TextureRegion ... keyFrames) {
		this.frameDuration = frameDuration;
		this.keyFrames = keyFrames;
	}

	public TextureRegion getKeyFrame(float stateTime, int mode) {
		int frameNumber = (int)(stateTime / frameDuration);
		//Log.d("stateTime & frame", Float.toString(stateTime) + ", " + Float.toString(frameDuration));
		//Log.d("Animation frame", Integer.toString(frameNumber));
		if(mode == ANIMATION_NONLOOPING) {
			frameNumber = Math.min(keyFrames.length-1, frameNumber);
		} else {
		frameNumber = frameNumber % keyFrames.length;
		}
		return keyFrames[frameNumber];
	}
}
