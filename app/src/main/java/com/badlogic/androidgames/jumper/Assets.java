package com.badlogic.androidgames.jumper;

import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Sound;
import com.badlogic.androidgames.framework.gl.Animation;
import com.badlogic.androidgames.framework.gl.Font;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGame;

public class Assets {
	public static Texture background;
	public static TextureRegion backgroundRegion;
	public static Texture items;
	public static TextureRegion mainMenu;
	public static TextureRegion pauseMenu;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion highScoresRegion;
	public static TextureRegion logo;
	public static TextureRegion colourLight;
	public static TextureRegion colourDark;
	public static TextureRegion blackWhite;
	public static TextureRegion coloured;
	public static TextureRegion up;
	public static TextureRegion down;
	public static TextureRegion left;
	public static TextureRegion right;

	public static TextureRegion gender;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion arrow;
	public static TextureRegion pause;
	public static TextureRegion spring;
	public static TextureRegion castle;
	public static Animation coinAnim;
	public static Animation bobJump;
	public static Animation bobFall;
	public static TextureRegion bobHit;
	public static Animation squirrelFly;
	public static TextureRegion platform;
	public static Animation brakingPlatform;
	public static Font font;
	public static Music music;
	public static Sound jumpSound;
	public static Sound highJumpSound;
	public static Sound hitSound;
	public static Sound coinSound;
	public static Sound clickSound;

	public static Texture debug;
	public static TextureRegion debugBox;
	public static GLGame mGame;

	public static void load(GLGame game) {

		mGame = game;
		loadTextures();

		font = new Font(items, 224, 0, 16, 16, 20);	
		
		music = game.getAudio().newMusic("music.mp3");
		music.setLooping(true);
		music.setVolume(0.5f);
		if(Settings.soundEnabled)
			music.play();
		jumpSound = game.getAudio().newSound("jump.ogg");
		highJumpSound = game.getAudio().newSound("highjump.ogg");
		hitSound = game.getAudio().newSound("hit.ogg");
		coinSound = game.getAudio().newSound("coin.ogg");
		clickSound = game.getAudio().newSound("click.ogg");
	}

	public static void loadTextures() {
		// set the background texture based on selected colour settings
		if (Settings.isLight && Settings.isColour)
			background = new Texture(mGame, "backgroundLight.png");
		else if (!Settings.isLight && Settings.isColour)
			background = new Texture(mGame, "backgroundDark.png");
		else if (Settings.isLight && !Settings.isColour)
			background = new Texture(mGame, "backgroundLightBW.png");
		else
			background = new Texture(mGame, "backgroundDarkBW.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 320, 480);

		if (Settings.isColour)
		{
			if (Settings.currentGender == 0)//male
				items = new Texture(mGame, "items_m.png");
			else if (Settings.currentGender == 1)//female
				items = new Texture(mGame, "items_f.png");
			else//neutral
				items = new Texture(mGame, "items.png");

		}
		else
		{
			if (Settings.currentGender == 0)//male
				items = new Texture(mGame, "items_m_bw.png");
			else if (Settings.currentGender == 1)//female
				items = new Texture(mGame, "items_f_bw.png");
			else//neutral
				items = new Texture(mGame, "items_bw.png");
		}

		debug = new Texture(mGame, "debug.png");// texture for testing
		debugBox = new TextureRegion(debug, 0, 0, 400, 400);// texture region for testing
		mainMenu = new TextureRegion(items, 0, 224, 300, 110);
		pauseMenu = new TextureRegion(items, 224, 128, 192, 96);
		ready = new TextureRegion(items, 320, 224, 192, 32);
		gameOver = new TextureRegion(items, 352, 256, 160, 96);
		highScoresRegion = new TextureRegion(Assets.items, 0, 257, 300, 110 / 3);
		logo = new TextureRegion(items, 0, 352, 274, 142);
		colourLight = new TextureRegion(items, 390, 352, 84, 14);
		colourDark = new TextureRegion(items, 296, 352, 84, 14);
		blackWhite = new TextureRegion(items, 296, 375, 144, 14);
		coloured = new TextureRegion(items, 296, 478, 145, 14);
		up = new TextureRegion(items, 279, 396, 68, 14);
		down = new TextureRegion(items, 359, 396, 68, 14);
		gender = new TextureRegion(items, 296, 417, 120, 14);

		soundOff = new TextureRegion(items, 0, 0, 64, 64);
		soundOn = new TextureRegion(items, 64, 0, 64, 64);
		arrow = new TextureRegion(items, 8, 83, 43, 25);
		left = new TextureRegion(items, 9, 67, 9, 9);
		right = new TextureRegion(items, 24, 67, 9, 9);
		pause = new TextureRegion(items, 64, 64, 64, 64);

		spring = new TextureRegion(items, 128, 0, 32, 32);
		castle = new TextureRegion(items, 128, 64, 64, 64);
		coinAnim = new Animation(0.2f,
				new TextureRegion(items, 128, 32, 32, 32),
				new TextureRegion(items, 160, 32, 32, 32),
				new TextureRegion(items, 192, 32, 32, 32),
				new TextureRegion(items, 160, 32, 32, 32));
		bobJump = new Animation(0.2f,
				new TextureRegion(items, 0, 128, 32, 32),
				new TextureRegion(items, 32, 128, 32, 32));
		bobFall = new Animation(0.2f,
				new TextureRegion(items, 64, 128, 32, 32),
				new TextureRegion(items, 96, 128, 32, 32));
		bobHit = new TextureRegion(items, 128, 128, 32, 32);
		squirrelFly = new Animation(0.2f,
				new TextureRegion(items, 0, 160, 32, 32),
				new TextureRegion(items, 32, 160, 32, 32));
		platform = new TextureRegion(items, 64, 160, 64, 16);
		brakingPlatform = new Animation(0.2f,
				new TextureRegion(items, 64, 160, 64, 16),
				new TextureRegion(items, 64, 176, 64, 16),
				new TextureRegion(items, 64, 192, 64, 16),
				new TextureRegion(items, 64, 208, 64, 16));
	}



	public static void reload() {
		background.reload();
		items.reload();
		if(Settings.soundEnabled)
			music.play();
	}

	public static void playSound(Sound sound) {
		if(Settings.soundEnabled)
			sound.play(1);
	}
}
