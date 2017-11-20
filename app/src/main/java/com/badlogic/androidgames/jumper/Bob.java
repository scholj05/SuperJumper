package com.badlogic.androidgames.jumper;

import android.util.Log;

import com.badlogic.androidgames.framework.DynamicGameObject;

public class Bob extends DynamicGameObject {
	public static final int BOB_STATE_JUMP = 0;
	public static final int BOB_STATE_FALL = 1;
	public static final int BOB_STATE_HIT = 2;
	public static float BOB_JUMP_VELOCITY = 11;
	public static float BOB_MOVE_VELOCITY = 20;
	public static final float BOB_WIDTH = 0.8f;
	public static final float BOB_HEIGHT = 0.8f;
	
	int state;
	float stateTime;

	boolean isUp;

	public Bob(float x, float y, boolean isUp) {
		super(x, y, BOB_WIDTH, BOB_HEIGHT);
		state = BOB_STATE_FALL;
		stateTime = 0;
		this.isUp = isUp;

		if (!isUp)
		{
			BOB_JUMP_VELOCITY = -BOB_JUMP_VELOCITY;
			BOB_MOVE_VELOCITY = -BOB_MOVE_VELOCITY;
		}
	}
	
	public void update(float deltaTime) {
		velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
		position.add(-velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);

		if(velocity.y > 0 && state != BOB_STATE_HIT) {
			if(state != BOB_STATE_JUMP) {
				state = BOB_STATE_JUMP;
				stateTime = 0;
			}
		}

		if(velocity.y < 0 && state != BOB_STATE_HIT) {
			if(state != BOB_STATE_FALL) {
				state = BOB_STATE_FALL;
				stateTime = 0;
			}
		}
	
		if(position.x < 0)
			position.x = World.WORLD_WIDTH;
		if(position.x > World.WORLD_WIDTH)
			position.x = 0;
		stateTime += deltaTime;
		Log.d("Velocity", Float.toString(velocity.x) + ", " + Float.toString(velocity.y));
	}
	
	public void hitSquirrel() {
		velocity.set(0,0);
		state = BOB_STATE_HIT;
		stateTime = 0;
	}

	public void hitPlatform() {
		velocity.y = BOB_JUMP_VELOCITY;
		state = BOB_STATE_JUMP;
		stateTime = 0;
	}

	public void hitSpring() {
		velocity.y = 1.5f * BOB_JUMP_VELOCITY;
		state = BOB_STATE_JUMP;
		stateTime = 0;
	}	
}
