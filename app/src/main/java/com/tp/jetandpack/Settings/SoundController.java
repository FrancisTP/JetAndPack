package com.tp.jetandpack.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.tp.framework.Music;
import com.tp.framework.Sound;
import com.tp.framework.impl.GLGame;

import java.util.LinkedList;

/**
 * Created by Francis on 2015-06-24.
 */
/*
    public void play();

	public void stop();

	public void pause();

	public void setLooping(boolean looping);

	public void setVolume(float volume);

	public boolean isPlaying();

	public boolean isStopped();

	public boolean isLooping();

	public void dispose();
 */

public class SoundController {

    protected static GLGame glGame;

    public static Music music;

    public static float musicVolume;

    public static int state;

    private static final int NOTHING = 0;
    private static final int FADE_OUT = 1;
    public static final int CHANGING = 2;

    public static String musicPlaying;

    public static String fileName;
    public static String musicVol;

    public static SharedPreferences settings;

    public static void load(GLGame game){
        glGame = game;

        state = NOTHING;

        musicPlaying = "";

       //////////////////////////////////////////////////////////////////////////////////////////

        fileName = "jetandpacksoundsettings";
        musicVol = "musicVolume";

        settings = glGame.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        // Checks if the music volume key already exists, if not creates one
        if(!settings.contains(musicVol)){
            // Key does not exist
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(musicVol, "0.5");
            editor.apply();
        }
        // Load saved music volume
        musicVolume = Float.parseFloat(settings.getString(musicVol, null));

        music = null;
    }

    public static void update(){
        if(state == FADE_OUT){
            musicVolume -= 0.004;
            music.setVolume(musicVolume);
            if(musicVolume <= 0){
                music.stop();
                musicVolume = Float.parseFloat(settings.getString(musicVol, null));
                state = CHANGING;
            }
        } else if (state == CHANGING){
            state = NOTHING;
            startSong(musicPlaying);
        }
    }

    public static void requestSong(String song){
        // Check if a song is already playing
        if(musicPlaying != song) {
            musicPlaying = song;
            if (music != null) {
                if (music.isPlaying() && state == NOTHING) {
                    state = FADE_OUT;
                } else if (state == NOTHING) {
                    startSong(musicPlaying);
                }
            } else {
                startSong(musicPlaying);
            }
        }
    }

    public static void startSong(String song){
        if(music != null)
            music.dispose();
        music = glGame.getAudio().newMusic(song);
        music.setLooping(true);
        music.setVolume(musicVolume);
        music.play();
    }

    public static void pauseMusic(){
        if(music != null)
            music.pause();
    }

    public static void resumeMusic(){
        if(music != null)
            music.play();
    }

    public static void stopMusic(){
        if(music != null) {
            music.stop();
            music.dispose();
            music = null;
            musicPlaying = "";
        }
    }
}
