package com.badlogic.androidgames.jumper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.badlogic.androidgames.framework.FileIO;

public class Settings {
	public static boolean soundEnabled = true;
	public static boolean gameDirectionUp = true;
	public static int levelsThisLife = 0;
	public static int lastLevelsThisLife = 0;
	public static int lastScore = 0;
	public final static int[] highscores = new int[] { 0, 0, 0, 0, 0 };
	public final static String file = ".superjumper";
	public static boolean isColour = true;
	public static boolean isLight = false;
	public static int currentGender = 0;

	public static void load(FileIO files) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(files.readFile(file)));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			for(int i = 0; i < 5; i++) {
				highscores[i] = Integer.parseInt(in.readLine());
			}
		} catch (IOException e) {
		} catch (NumberFormatException e) {
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}

	public static void save(FileIO files) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(files.writeFile(file)));
			out.write(Boolean.toString(soundEnabled));
			out.write("\n");
			for(int i = 0; i < 5; i++) {
				out.write(Integer.toString(highscores[i]));
				out.write("\n");
			}

		} catch (IOException e) {
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}

	public static void addScore(int score) {
		lastScore = score;
		for(int i=0; i < 5; i++) {
			if(highscores[i] < score) {
				for(int j= 4; j > i; j--)
					highscores[j] = highscores[j-1];
				highscores[i] = score;
				break;
			}
		}
	}

	public static void toggleColour()
	{
		isColour = !isColour;
		Assets.loadTextures();
	}

	public static void toggleLight()
	{
		isLight = !isLight;
		Assets.loadTextures();
	}

	public static void toggleGender()
	{
		currentGender = (++currentGender % 3);
		Assets.loadTextures();
	}

}
