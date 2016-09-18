package com.tp.jetandpack.Menues.Buttons;

import android.annotation.SuppressLint;

import com.tp.framework.Game;
import com.tp.framework.Input;
import com.tp.framework.gl.Camera2D;
import com.tp.framework.gl.FPSCounter;
import com.tp.framework.gl.SpriteBatcher;
import com.tp.framework.impl.GLScreen;
import com.tp.framework.math.Vector2;
import com.tp.jetandpack.Assets.Assets;
import com.tp.jetandpack.Settings.SoundController;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by franc on 2016-01-30.
 */
public class Levels extends GLScreen{
    // Variables
    protected Camera2D guiCam;
    protected SpriteBatcher batcher;
    protected Vector2 touchPoint;
    protected FPSCounter fpsCounter = new FPSCounter();

    final protected static int LOADING_STATE = -1;
    final protected static int RUNNING_STATE = 0;
    final protected static int PAUSE_STATE = 2;

    protected float stateTime;
    protected float deltaT;
    protected static int state;

    public Levels(Game game) {
        super(game);

        guiCam = new Camera2D(glGraphics, 1280, 800); // Screen resolution 1280x800
        batcher = new SpriteBatcher(glGraphics, 100); // A maximum of 100 sprite per batch
        touchPoint = new Vector2();
        deltaT = 0;

        stateTime = 3;

        state = RUNNING_STATE;
    }

    @Override
    public void update(float deltaTime){
        deltaT = deltaTime;

        SoundController.update();

        if(Assets.readyState){
            List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
            game.getInput().getKeyEvents();


            listenToTouches(touchEvents);
        }

        //time += deltaTime;
        //Log.d("deltaTime", "" + time);
    }

    public void listenToTouches(List<Input.TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);



            if(state == RUNNING_STATE){}


            if (event.type == Input.TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);

            }
            if (event.type == Input.TouchEvent.TOUCH_DOWN) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);


            }
            if (event.type == Input.TouchEvent.TOUCH_DRAGGED) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);


            }
        }
    }

    @SuppressLint("FloatMath")
    @Override
    public void present(float deltaTime){
        stateTime += deltaTime; // updates stateTime if we want frame independent movement

        // Initiates everything needed to render sprites
        GL10 gl = glGraphics.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();

        gl.glEnable(GL10.GL_TEXTURE_2D);

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        // Draw

        //batcher.endBatch();
        // Display fps on LogCat
        //fpsCounter.logFrame();
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
}

