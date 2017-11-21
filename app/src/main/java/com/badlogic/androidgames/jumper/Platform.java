package com.badlogic.androidgames.jumper;

import com.badlogic.androidgames.framework.DynamicGameObject;

public class Platform extends DynamicGameObject {
	public static final float PLATFORM_WIDTH = 2;
	public static final float PLATFORM_HEIGHT = 0.5f;
	public static final int PLATFORM_TYPE_STATIC = 0;
	public static final int PLATFORM_TYPE_MOVING = 1;
	public static final int PLATFORM_STATE_NORMAL = 0;
	public static final int PLATFORM_STATE_PULVERIZING = 1;
	public static final float PLATFORM_PULVERIZE_TIME = 0.2f * 4;
	public static final float PLATFORM_VELOCITY = 2;
	
	int type;
	int state;
	float stateTime;
	int bounceCount;
	
	public Platform(int type, float x, float y) {
		super(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
		this.type = type;
		this.state = PLATFORM_STATE_NORMAL;
		this.stateTime = 0;
		if(type == PLATFORM_TYPE_MOVING) {
			velocity.x = PLATFORM_VELOCITY;
		}
	}
	
	public void update(float deltaTime) {
		if(type == PLATFORM_TYPE_MOVING) {
			position.add(velocity.x * deltaTime, 0);
			bounds.lowerLeft.set(position).sub(PLATFORM_WIDTH / 2, PLATFORM_HEIGHT / 2);

			if(position.x < PLATFORM_WIDTH / 2) {
				velocity.x = -velocity.x;
				position.x = PLATFORM_WIDTH / 2;
			}
			if(position.x > World.WORLD_WIDTH - PLATFORM_WIDTH / 2) {
				velocity.x = -velocity.x;
				position.x = World.WORLD_WIDTH - PLATFORM_WIDTH / 2;
			}
		}
		stateTime += deltaTime;
	}	
	
	public void pulverize() {
		state = PLATFORM_STATE_PULVERIZING;
		stateTime = 0;
		velocity.x = 0;
	}

	public void bounce()
	{
		bounceCount++;
		if ((bounceCount == 1 && Settings.levelsThisLife > 1) || 	// if level 3, 1 bounce
			(bounceCount == 2 && Settings.levelsThisLife == 1) ||	// if level 2, 2 bounces
			(bounceCount == 3 && Settings.levelsThisLife == 0))		// if level 1, 3 bounces
			pulverize();
	}
}
