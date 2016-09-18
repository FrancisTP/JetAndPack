package com.tp.jetandpack.Menues.LevelMenus;

import android.annotation.SuppressLint;

import com.tp.framework.Game;
import com.tp.framework.Input.TouchEvent;
//import com.tp.framework.gl.Animation;
import com.tp.framework.gl.Camera2D;
import com.tp.framework.gl.FPSCounter;
import com.tp.framework.gl.SpriteBatcher;
//import com.tp.framework.gl.TextureRegion;
import com.tp.framework.impl.GLScreen;
//import com.tp.framework.math.Circle;
import com.tp.framework.math.Rectangle;
import com.tp.framework.math.Vector2;
import com.tp.framework.math.OverlapTester;
import com.tp.jetandpack.Assets.Assets;
import com.tp.jetandpack.Menues.LoadingScreen;
import com.tp.jetandpack.Settings.SoundController;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class WorldSelectScreen extends GLScreen{
    // Variables
    Camera2D guiCam;
    SpriteBatcher batcher;
    Vector2 touchPoint;
    FPSCounter fpsCounter = new FPSCounter();

    int state;
    final int LOADING_STATE = -1;
    final int RUNNING_STATE = 0;
    final int CHANGE_SCREEN_STATE = 1;

    float stateTime = 3;

    Rectangle worldOneBounds;
    int worldOneState;
    Rectangle worldTwoBounds;
    int worldTwoState;
    Rectangle worldThreeBounds;
    int worldThreeState;
    Rectangle worldFourBounds;
    int worldFourState;
    Rectangle worldFiveBoundsLeft;
    Rectangle worldFiveBoundsRight;
    int worldFiveState;
    Rectangle worldSixBounds;
    int worldSixState;
    Rectangle worldSevenBounds;
    int worldSevenState;
    Rectangle worldEightBounds;
    int worldEightState;

    Rectangle backWorldSelectorBounds;
    int backWorldSelectorState;

    final int BOUNDS_NOT_TOUCHED = 0;
    final int BOUNDS_TOUCHED = 1;
    final int BOUNDS_CHOSEN_NOT_TOUCHED = 2;
    final int BOUNDS_CHOSEN_TOUCHED = 3;

    public WorldSelectScreen(Game game){
        super(game);

        guiCam = new Camera2D(glGraphics, 1280, 800); // Screen resolution 1280x800
        batcher = new SpriteBatcher(glGraphics, 100); // A maximum of 100 sprite per batch
        touchPoint = new Vector2();


        worldOneBounds = new Rectangle(160, 600, 320, 400);
        worldOneState = BOUNDS_NOT_TOUCHED;

        worldTwoBounds = new Rectangle(480, 600, 320, 400);
        worldTwoState = BOUNDS_NOT_TOUCHED;

        worldThreeBounds = new Rectangle(800, 600, 320, 400);
        worldThreeState = BOUNDS_NOT_TOUCHED;

        worldFourBounds = new Rectangle(1120, 600, 320, 400);
        worldFourState = BOUNDS_NOT_TOUCHED;

        worldFiveBoundsLeft = new Rectangle(70, 269.5f, 141, 259);
        worldFiveBoundsRight = new Rectangle(229.5f, 200, 179, 400);
        worldFiveState = BOUNDS_NOT_TOUCHED;

        worldSixBounds = new Rectangle(480, 200, 320, 400);
        worldSixState = BOUNDS_NOT_TOUCHED;

        worldSevenBounds = new Rectangle(800, 200, 320, 400);
        worldSevenState = BOUNDS_NOT_TOUCHED;

        worldEightBounds = new Rectangle(1120, 200, 320, 400);
        worldEightState = BOUNDS_NOT_TOUCHED;

        backWorldSelectorBounds = new Rectangle(70, 70, 141, 141);
        backWorldSelectorState = BOUNDS_NOT_TOUCHED;

        state = RUNNING_STATE;
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

        //time += deltaTime;
        //Log.d("deltaTime", "" + time);
    }

    public void listenToTouches(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len && touchEvents.size() != 0; i++) {
            TouchEvent event = touchEvents.get(i);

            if (event.type == TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);

                /*
                worldOneState = BOUNDS_NOT_TOUCHED;
                worldTwoState = BOUNDS_NOT_TOUCHED;
                worldThreeState = BOUNDS_NOT_TOUCHED;
                worldFourState = BOUNDS_NOT_TOUCHED;
                worldFiveState = BOUNDS_NOT_TOUCHED;
                worldSixState = BOUNDS_NOT_TOUCHED;
                worldSevenState = BOUNDS_NOT_TOUCHED;
                worldEightState = BOUNDS_NOT_TOUCHED;
                */
                backWorldSelectorState = BOUNDS_NOT_TOUCHED;

                if (OverlapTester.pointInRectangle(worldOneBounds, touchPoint)) {
                    if(worldOneState == BOUNDS_CHOSEN_TOUCHED)
                        game.setScreen(new LoadingScreen(glGame, "WorldOneSelectScreen"));

                    worldOneState = BOUNDS_CHOSEN_NOT_TOUCHED;
                    return;
                }
                if (OverlapTester.pointInRectangle(worldTwoBounds, touchPoint)) {
                    if(worldTwoState == BOUNDS_CHOSEN_TOUCHED)
                        worldTwoState = BOUNDS_CHOSEN_NOT_TOUCHED;     // change screen

                    worldTwoState = BOUNDS_CHOSEN_NOT_TOUCHED;
                    return;
                }
                if (OverlapTester.pointInRectangle(worldThreeBounds, touchPoint)) {
                    if(worldThreeState == BOUNDS_CHOSEN_TOUCHED)
                        worldThreeState = BOUNDS_CHOSEN_NOT_TOUCHED;     // change screen

                    worldThreeState = BOUNDS_CHOSEN_NOT_TOUCHED;
                    return;
                }
                if (OverlapTester.pointInRectangle(worldFourBounds, touchPoint)) {
                    if(worldFourState == BOUNDS_CHOSEN_TOUCHED)
                        worldFourState = BOUNDS_CHOSEN_NOT_TOUCHED;     // change screen

                    worldFourState = BOUNDS_CHOSEN_NOT_TOUCHED;
                    return;
                }
                if (OverlapTester.pointInRectangle(worldFiveBoundsLeft, touchPoint) || OverlapTester.pointInRectangle(worldFiveBoundsRight, touchPoint)) {
                    if(worldFiveState == BOUNDS_CHOSEN_TOUCHED)
                        worldFiveState = BOUNDS_CHOSEN_NOT_TOUCHED;     // change screen

                    worldFiveState = BOUNDS_CHOSEN_NOT_TOUCHED;
                    return;
                }
                if(OverlapTester.pointInRectangle(worldSixBounds, touchPoint)) {
                    if(worldSixState == BOUNDS_CHOSEN_TOUCHED)
                        worldSixState = BOUNDS_CHOSEN_NOT_TOUCHED;     // change screen

                    worldSixState = BOUNDS_CHOSEN_NOT_TOUCHED;
                    return;
                }
                if(OverlapTester.pointInRectangle(worldSevenBounds, touchPoint)) {
                    if(worldSevenState == BOUNDS_CHOSEN_TOUCHED)
                        worldSevenState = BOUNDS_CHOSEN_NOT_TOUCHED;     // change screen

                    worldSevenState = BOUNDS_CHOSEN_NOT_TOUCHED;
                    return;
                }
                if(OverlapTester.pointInRectangle(worldEightBounds, touchPoint)) {
                    if(worldEightState == BOUNDS_CHOSEN_TOUCHED)
                        worldEightState = BOUNDS_CHOSEN_NOT_TOUCHED;     // change screen

                    worldEightState = BOUNDS_CHOSEN_NOT_TOUCHED;
                    return;
                }
                if(OverlapTester.pointInRectangle(backWorldSelectorBounds, touchPoint)){
                    //state = CHANGE_SCREEN_STATE;
                    game.setScreen(new LoadingScreen(glGame, "MainMenuScreen"));
                    return;
                }
            }
            if (event.type == TouchEvent.TOUCH_DOWN) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);

                if(OverlapTester.pointInRectangle(worldOneBounds, touchPoint))
                    if(worldOneState == BOUNDS_CHOSEN_NOT_TOUCHED) {
                        worldOneState = BOUNDS_CHOSEN_TOUCHED;

                    }else
                        worldOneState = BOUNDS_TOUCHED;

                if(OverlapTester.pointInRectangle(worldTwoBounds, touchPoint))
                    if(worldTwoState == BOUNDS_CHOSEN_NOT_TOUCHED)
                        worldTwoState = BOUNDS_CHOSEN_TOUCHED;
                    else
                        worldTwoState = BOUNDS_TOUCHED;

                if(OverlapTester.pointInRectangle(worldThreeBounds, touchPoint))
                    if(worldThreeState == BOUNDS_CHOSEN_NOT_TOUCHED)
                        worldThreeState = BOUNDS_CHOSEN_TOUCHED;
                    else
                        worldThreeState = BOUNDS_TOUCHED;

                if(OverlapTester.pointInRectangle(worldFourBounds, touchPoint))
                    if(worldFourState == BOUNDS_CHOSEN_NOT_TOUCHED)
                        worldFourState = BOUNDS_CHOSEN_TOUCHED;
                    else
                        worldFourState = BOUNDS_TOUCHED;

                if(OverlapTester.pointInRectangle(worldFiveBoundsLeft, touchPoint) || OverlapTester.pointInRectangle(worldFiveBoundsRight, touchPoint))
                    if(worldFiveState == BOUNDS_CHOSEN_NOT_TOUCHED)
                        worldFiveState = BOUNDS_CHOSEN_TOUCHED;
                    else
                        worldFiveState = BOUNDS_TOUCHED;

                if(OverlapTester.pointInRectangle(worldSixBounds, touchPoint))
                    if(worldSixState == BOUNDS_CHOSEN_NOT_TOUCHED)
                        worldSixState = BOUNDS_CHOSEN_TOUCHED;
                    else
                        worldSixState = BOUNDS_TOUCHED;

                if(OverlapTester.pointInRectangle(worldSevenBounds, touchPoint))
                    if(worldSevenState == BOUNDS_CHOSEN_NOT_TOUCHED)
                        worldSevenState = BOUNDS_CHOSEN_TOUCHED;
                    else
                        worldSevenState = BOUNDS_TOUCHED;

                if(OverlapTester.pointInRectangle(worldEightBounds, touchPoint))
                    if(worldEightState == BOUNDS_CHOSEN_NOT_TOUCHED)
                        worldEightState = BOUNDS_CHOSEN_TOUCHED;
                    else
                        worldEightState = BOUNDS_TOUCHED;

                if(OverlapTester.pointInRectangle(backWorldSelectorBounds, touchPoint))
                    backWorldSelectorState = BOUNDS_TOUCHED;



                if(!OverlapTester.pointInRectangle(worldOneBounds, touchPoint))
                    worldOneState = BOUNDS_NOT_TOUCHED;
                if(!OverlapTester.pointInRectangle(worldTwoBounds, touchPoint))
                    worldTwoState = BOUNDS_NOT_TOUCHED;
                if(!OverlapTester.pointInRectangle(worldThreeBounds, touchPoint))
                    worldThreeState = BOUNDS_NOT_TOUCHED;
                if(!OverlapTester.pointInRectangle(worldFourBounds, touchPoint))
                    worldFourState = BOUNDS_NOT_TOUCHED;
                if(!OverlapTester.pointInRectangle(worldFiveBoundsLeft, touchPoint) && !OverlapTester.pointInRectangle(worldFiveBoundsRight, touchPoint))
                    worldFiveState = BOUNDS_NOT_TOUCHED;
                if(!OverlapTester.pointInRectangle(worldSixBounds, touchPoint))
                    worldSixState = BOUNDS_NOT_TOUCHED;
                if(!OverlapTester.pointInRectangle(worldSevenBounds, touchPoint))
                    worldSevenState = BOUNDS_NOT_TOUCHED;
                if(!OverlapTester.pointInRectangle(worldEightBounds, touchPoint))
                    worldEightState = BOUNDS_NOT_TOUCHED;
            }
            if (event.type == TouchEvent.TOUCH_DRAGGED) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);

                if(OverlapTester.pointInRectangle(worldOneBounds, touchPoint))
                    if(worldOneState != BOUNDS_CHOSEN_NOT_TOUCHED && worldOneState != BOUNDS_CHOSEN_TOUCHED)
                        worldOneState = BOUNDS_TOUCHED;
                if(OverlapTester.pointInRectangle(worldTwoBounds, touchPoint))
                    if(worldTwoState != BOUNDS_CHOSEN_NOT_TOUCHED && worldTwoState != BOUNDS_CHOSEN_TOUCHED)
                        worldTwoState = BOUNDS_TOUCHED;
                if(OverlapTester.pointInRectangle(worldThreeBounds, touchPoint))
                    if(worldThreeState != BOUNDS_CHOSEN_NOT_TOUCHED && worldThreeState != BOUNDS_CHOSEN_TOUCHED)
                        worldThreeState = BOUNDS_TOUCHED;
                if(OverlapTester.pointInRectangle(worldFourBounds, touchPoint))
                    if(worldFourState != BOUNDS_CHOSEN_NOT_TOUCHED && worldFourState != BOUNDS_CHOSEN_TOUCHED)
                        worldFourState = BOUNDS_TOUCHED;
                if(OverlapTester.pointInRectangle(worldFiveBoundsLeft, touchPoint) || OverlapTester.pointInRectangle(worldFiveBoundsRight, touchPoint))
                    if(worldFiveState != BOUNDS_CHOSEN_NOT_TOUCHED && worldFiveState != BOUNDS_CHOSEN_TOUCHED)
                        worldFiveState = BOUNDS_TOUCHED;
                if(OverlapTester.pointInRectangle(worldSixBounds, touchPoint))
                    if(worldSixState != BOUNDS_CHOSEN_NOT_TOUCHED && worldSixState != BOUNDS_CHOSEN_TOUCHED)
                        worldSixState = BOUNDS_TOUCHED;
                if(OverlapTester.pointInRectangle(worldSevenBounds, touchPoint))
                    if(worldSevenState != BOUNDS_CHOSEN_NOT_TOUCHED && worldSevenState != BOUNDS_CHOSEN_TOUCHED)
                        worldSevenState = BOUNDS_TOUCHED;
                if(OverlapTester.pointInRectangle(worldEightBounds, touchPoint))
                    if(worldEightState != BOUNDS_CHOSEN_NOT_TOUCHED && worldEightState != BOUNDS_CHOSEN_TOUCHED)
                        worldEightState = BOUNDS_TOUCHED;

                if(!OverlapTester.pointInRectangle(worldOneBounds, touchPoint))
                    worldOneState = BOUNDS_NOT_TOUCHED;
                if(!OverlapTester.pointInRectangle(worldTwoBounds, touchPoint))
                    worldTwoState = BOUNDS_NOT_TOUCHED;
                if(!OverlapTester.pointInRectangle(worldThreeBounds, touchPoint))
                    worldThreeState = BOUNDS_NOT_TOUCHED;
                if(!OverlapTester.pointInRectangle(worldFourBounds, touchPoint))
                    worldFourState = BOUNDS_NOT_TOUCHED;
                if(!OverlapTester.pointInRectangle(worldFiveBoundsLeft, touchPoint) && !OverlapTester.pointInRectangle(worldFiveBoundsRight, touchPoint))
                    worldFiveState = BOUNDS_NOT_TOUCHED;
                if(!OverlapTester.pointInRectangle(worldSixBounds, touchPoint))
                    worldSixState = BOUNDS_NOT_TOUCHED;
                if(!OverlapTester.pointInRectangle(worldSevenBounds, touchPoint))
                    worldSevenState = BOUNDS_NOT_TOUCHED;
                if(!OverlapTester.pointInRectangle(worldEightBounds, touchPoint))
                    worldEightState = BOUNDS_NOT_TOUCHED;
                if(!OverlapTester.pointInRectangle(backWorldSelectorBounds, touchPoint))
                    backWorldSelectorState = BOUNDS_NOT_TOUCHED;
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
        batcher.beginBatch(Assets.worldSelectSprites);
        batcher.drawSprite(160, 600, 320, 400, Assets.worldOne);
        batcher.drawSprite(480, 600, 320, 400, Assets.underConstruction);
        batcher.drawSprite(800, 600, 320, 400, Assets.underConstruction);
        batcher.drawSprite(1120, 600, 320, 400, Assets.underConstruction);
        batcher.drawSprite(160, 200, 320, 400, Assets.underConstruction);
        batcher.drawSprite(480, 200, 320, 400, Assets.underConstruction);
        batcher.drawSprite(800, 200, 320, 400, Assets.underConstruction);
        batcher.drawSprite(1120, 200, 320, 400, Assets.underConstruction);

        if(worldOneState == BOUNDS_TOUCHED || worldOneState == BOUNDS_CHOSEN_NOT_TOUCHED)
            batcher.drawSprite(160, 600, 325, 405, Assets.worldContourLight);
        else if(worldOneState == BOUNDS_CHOSEN_TOUCHED)
            batcher.drawSprite(160, 600, 325, 405, Assets.worldContourDark);

        if(worldTwoState == BOUNDS_TOUCHED || worldTwoState == BOUNDS_CHOSEN_NOT_TOUCHED)
            batcher.drawSprite(480, 600, 325, 405, Assets.worldContourLight);
        else if(worldTwoState == BOUNDS_CHOSEN_TOUCHED)
            batcher.drawSprite(480, 600, 325, 405, Assets.worldContourDark);

        if(worldThreeState == BOUNDS_TOUCHED || worldThreeState == BOUNDS_CHOSEN_NOT_TOUCHED)
            batcher.drawSprite(800, 600, 325, 405, Assets.worldContourLight);
        else if(worldThreeState == BOUNDS_CHOSEN_TOUCHED)
            batcher.drawSprite(800, 600, 325, 405, Assets.worldContourDark);

        if(worldFourState == BOUNDS_TOUCHED || worldFourState == BOUNDS_CHOSEN_NOT_TOUCHED)
            batcher.drawSprite(1120, 600, 325, 405, Assets.worldContourLight);
        else if(worldFourState == BOUNDS_CHOSEN_TOUCHED)
            batcher.drawSprite(1120, 600, 325, 405, Assets.worldContourDark);

        if(worldFiveState == BOUNDS_TOUCHED || worldFiveState == BOUNDS_CHOSEN_NOT_TOUCHED)
            batcher.drawSprite(160, 200, 325, 405, Assets.worldContourLight);
        else if(worldFiveState == BOUNDS_CHOSEN_TOUCHED)
            batcher.drawSprite(160, 200, 325, 405, Assets.worldContourDark);

        if(worldSixState == BOUNDS_TOUCHED || worldSixState == BOUNDS_CHOSEN_NOT_TOUCHED)
            batcher.drawSprite(480, 200, 325, 405, Assets.worldContourLight);
        else if(worldSixState == BOUNDS_CHOSEN_TOUCHED)
            batcher.drawSprite(480, 200, 325, 405, Assets.worldContourDark);

        if(worldSevenState == BOUNDS_TOUCHED || worldSevenState == BOUNDS_CHOSEN_NOT_TOUCHED)
            batcher.drawSprite(800, 200, 325, 405, Assets.worldContourLight);
        else if(worldSevenState == BOUNDS_CHOSEN_TOUCHED)
            batcher.drawSprite(800, 200, 325, 405, Assets.worldContourDark);

        if(worldEightState == BOUNDS_TOUCHED || worldEightState == BOUNDS_CHOSEN_NOT_TOUCHED)
            batcher.drawSprite(1120, 200, 325, 405, Assets.worldContourLight);
        else if(worldEightState == BOUNDS_CHOSEN_TOUCHED)
            batcher.drawSprite(1120, 200, 325, 405, Assets.worldContourDark);


        if(backWorldSelectorState == BOUNDS_NOT_TOUCHED)
            batcher.drawSprite(70, 70, 141, 141, Assets.backWorldSelectLight);
        else
            batcher.drawSprite(70, 70, 141, 141, Assets.backWorldSelectDark);

        batcher.endBatch();

        // Stop rendering
        gl.glDisable(GL10.GL_BLEND);

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
