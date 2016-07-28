package com.tp.jetandpack;

import android.annotation.SuppressLint;

import com.tp.framework.Game;
import com.tp.framework.Input;
import com.tp.framework.gl.Camera2D;
import com.tp.framework.gl.FPSCounter;
import com.tp.framework.gl.SpriteBatcher;
import com.tp.framework.impl.GLScreen;
import com.tp.framework.math.Vector2;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by franc on 2016-01-27.
 */
public class WorldOneLevelOneScreen extends Levels {

    Planet planet;
    PauseButton pauseButton;

    final int BOUNDS_NOT_TOUCHED = 0;
    final int BOUNDS_TOUCHED = 1;
    final int BOUNDS_CHOSEN_NOT_TOUCHED = 2;
    final int BOUNDS_CHOSEN_TOUCHED = 3;

    public WorldOneLevelOneScreen(Game game) {
        super(game);

        pauseButton = new PauseButton(70.5f, 729.5f, 141, 141, "WorldOneSelectScreen");

        planet = new Planet("WorldOne");

        deltaT = 0;

        state = RUNNING_STATE;
    }

    @Override
    public void update(float deltaTime){
        deltaT += deltaTime;

        SoundController.update();

        if(Assets.readyState){
            List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
            game.getInput().getKeyEvents();


            listenToTouches(touchEvents);
        }

        //time += deltaTime;
        //Log.d("deltaTime", "" + time);
    }

    @Override
    public void listenToTouches(List<Input.TouchEvent> touchEvents) {
        int len = touchEvents.size();

        pauseButton.listenToTouches(touchEvents, guiCam, game, glGame, touchPoint);
        if (state == RUNNING_STATE) {
            for (int i = 0; i < len; i++) {
                Input.TouchEvent event = touchEvents.get(i);

                planet.listenToTouches(touchEvents, guiCam, game, glGame, deltaT);

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
        batcher.beginBatch(Assets.worldOneBackgroundSprite);
        batcher.drawSprite(640, 400, 1280, 800, Assets.worldOneBackground);
        batcher.endBatch();

        batcher.beginBatch(Assets.worldOnePlanetSpriteSheet);
        planet.display(batcher);
        batcher.endBatch();

        batcher.beginBatch(Assets.pauseMenuSpriteSheet);
        pauseButton.display(batcher);
        batcher.endBatch();
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
