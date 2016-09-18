package com.tp.jetandpack;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.tp.framework.Screen;
import com.tp.framework.impl.GLGame;
import com.tp.jetandpack.Assets.Assets;
import com.tp.jetandpack.Menues.LoadingScreen;
import com.tp.jetandpack.Settings.Saves;
import com.tp.jetandpack.Settings.SoundController;


public class JetAndPack extends GLGame {
    public boolean firstTimeCreate = true;

    public Screen getStartScreen(){
        return new LoadingScreen(this, "MainMenuScreen");
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config){
        super.onSurfaceCreated(gl, config);
        if(firstTimeCreate){
            Saves.load(getFileIO());
            Assets.load(this);
            SoundController.load(this);
            firstTimeCreate = false;
            Assets.readyState = true;
        } else {
            Assets.reload();
        }
    }

    @Override
    public void onPause(){
        SoundController.pauseMusic();
        super.onPause();
    }

    @Override
    public void onResume() {
        SoundController.resumeMusic();
        super.onResume();
    }

    @Override
    public void onBackPressed(){
        // Je suis un baller
    }
}