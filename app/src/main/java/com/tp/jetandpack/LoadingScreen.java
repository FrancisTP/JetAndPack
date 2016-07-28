package com.tp.jetandpack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.FloatMath;
import android.util.Log;

import com.tp.framework.CollisionTester;
import com.tp.framework.Game;
import com.tp.framework.GameObject;
import com.tp.framework.Input.TouchEvent;
//import com.tp.framework.gl.Animation;
import com.tp.framework.gl.Camera2D;
import com.tp.framework.gl.FPSCounter;
import com.tp.framework.gl.SpriteBatcher;
//import com.tp.framework.gl.TextureRegion;
import com.tp.framework.impl.GLScreen;
//import com.tp.framework.math.Circle;
import com.tp.framework.math.Circle;
import com.tp.framework.math.Rectangle;
import com.tp.framework.math.Vector2;
import com.tp.framework.math.OverlapTester;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class LoadingScreen extends GLScreen {
    // Variables
    Camera2D guiCam;
    SpriteBatcher batcher;
    Vector2 touchPoint;
    FPSCounter fpsCounter = new FPSCounter();

    int state;
    final int LOADING_STATE = -1;
    final int RUNNING_STATE = 0;
    final int CHANGE_SCREEN_STATE = 1;

    String screenToLoad;

    float stateTime = 3;

    boolean startLoading;


    public LoadingScreen(Game game, String screenToLoad){
        super(game);

        guiCam = new Camera2D(glGraphics, 1280, 800); // Screen resolution 1280x800
        batcher = new SpriteBatcher(glGraphics, 100); // A maximum of 100 sprite per batch
        touchPoint = new Vector2();

        this.screenToLoad = screenToLoad;

        state = RUNNING_STATE;

        startLoading = false;
    }

    @Override
    public void update(float deltaTime){
        SoundController.update();

        if(Assets.readyState){
            List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
            game.getInput().getKeyEvents();

            if(state == RUNNING_STATE)
                listenToTouches(touchEvents);
        }


        if(startLoading) {
            Assets.readyState = false;
            Assets.clear();
            Assets.onScreen = screenToLoad;
            Assets.load();
            Assets.readyState = true;

            if (screenToLoad.equals("MainMenuScreen"))
                game.setScreen(new MainMenuScreen(game));


            else if(screenToLoad.equals("WorldSelectScreen"))
                game.setScreen(new WorldSelectScreen(game));


            else if(screenToLoad.equals("WorldOneSelectScreen"))
                game.setScreen(new WorldOneLevelSelectScreen(game));


            else if(screenToLoad.equals("WorldOneLevelOneScreen"))
                game.setScreen(new WorldOneLevelOneScreen(game));
            /*
            else if(screenToLoad.equals("WorldOneLevelTwoScreen"))
                game.setScreen(new WorldOneLevelTwoScreen(game));
            else if(screenToLoad.equals("WorldOneLevelThreeScreen"))
                game.setScreen(new WorldOneLevelThreeScreen(game));
            else if(screenToLoad.equals("WorldOneLevelFourScreen"))
                game.setScreen(new WorldOneLevelFourScreen(game));
            else if(screenToLoad.equals("WorldOneLevelFiveScreen"))
                game.setScreen(new WorldOneLevelFiveScreen(game));
            else if(screenToLoad.equals("WorldOneLevelSixScreen"))
                game.setScreen(new WorldOneLevelSixScreen(game));
            else if(screenToLoad.equals("WorldOneLevelSevenScreen"))
                game.setScreen(new WorldOneLevelSevenScreen(game));
            else if(screenToLoad.equals("WorldOneLevelEightScreen"))
                game.setScreen(new WorldOneLevelEightScreen(game));
            */

        }

        startLoading = true;
        //time += deltaTime;
        //Log.d("deltaTime", "" + time);
    }

    public void listenToTouches(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);

            if (event.type == TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);
            }
            if (event.type == TouchEvent.TOUCH_DOWN) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);
            }
            if (event.type == TouchEvent.TOUCH_DRAGGED) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);
            }
        }
    }

    @SuppressLint("FloatMath")
    @Override
    public void present(float deltaTime){

        if(state == LOADING_STATE)
            presentLoading(deltaTime);
        else if(state == RUNNING_STATE)
            presentRunning(deltaTime);
        else if(state == CHANGE_SCREEN_STATE)
            presentChange(deltaTime);

    }

    @SuppressLint("FloatMath")
    public void presentLoading(float deltaTime){
        presentRunning(deltaTime);
    }

    @SuppressLint("FloatMath")
    public void presentRunning(float deltaTime){
        stateTime += deltaTime; // updates stateTime if we want frame independent movement

        // Initiates everything needed to render sprites
        GL10 gl = glGraphics.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();

        gl.glEnable(GL10.GL_TEXTURE_2D);

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Assets.SplashScreenScreen);
        batcher.drawSprite(640, 400, 1280, 800, Assets.SplashScreen);
        batcher.endBatch();

        // Stop rendering
        gl.glDisable(GL10.GL_BLEND);

        // Display fps on LogCat
        //fpsCounter.logFrame();
    }

    @SuppressLint("FloatMath")
    public void presentChange(float deltaTime){
        presentRunning(deltaTime);

    }

    @Override
    public void pause(){
    }

    @Override
    public void resume(){
    }

    @Override
    public void dispose(){
    }

    public void getAssets(){
        Assets.readyState = false;
        Assets.clear();
        Assets.onScreen = screenToLoad;
        Assets.load();
        Assets.readyState = true;

        Log.d("getAssets() worked", "");

    }
}
