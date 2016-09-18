package com.tp.jetandpack.World_01;

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
import com.tp.jetandpack.Menues.Buttons.LevelButton;
import com.tp.jetandpack.Settings.SoundController;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class WorldOneLevelSelectScreen extends GLScreen{
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

    Rectangle backWorldSelectorBounds;
    int backLevelSelectState;

    LevelButton levelButtonOne;
    int levelOneState;
    LevelButton levelButtonTwo;
    int levelTwoState;
    LevelButton levelButtonThree;
    int levelThreeState;
    LevelButton levelButtonFour;
    int levelFourState;
    LevelButton levelButtonFive;
    int levelFiveState;
    LevelButton levelButtonSix;
    int levelSixState;
    LevelButton levelButtonSeven;
    int levelSevenState;
    LevelButton levelButtonEight;
    int levelEightState;


    final int BOUNDS_NOT_TOUCHED = 0;
    final int BOUNDS_TOUCHED = 1;
    final int BOUNDS_CHOSEN_NOT_TOUCHED = 2;
    final int BOUNDS_CHOSEN_TOUCHED = 3;

    public WorldOneLevelSelectScreen(Game game) {
        super(game);

        guiCam = new Camera2D(glGraphics, 1280, 800); // Screen resolution 1280x800
        batcher = new SpriteBatcher(glGraphics, 100); // A maximum of 100 sprite per batch
        touchPoint = new Vector2();

        backWorldSelectorBounds = new Rectangle(70, 70, 141, 141);
        backLevelSelectState = BOUNDS_NOT_TOUCHED;


        levelButtonOne = new LevelButton(new Rectangle(160, 650, 201, 201), "1");
        levelOneState = BOUNDS_NOT_TOUCHED;

        levelButtonTwo = new LevelButton(new Rectangle(480, 650, 201, 201), "2");
        levelTwoState = BOUNDS_NOT_TOUCHED;

        levelButtonThree = new LevelButton(new Rectangle(800, 650, 201, 201), "3");
        levelThreeState = BOUNDS_NOT_TOUCHED;

        levelButtonFour = new LevelButton(new Rectangle(1120, 650, 201, 201), "4");
        levelFourState = BOUNDS_NOT_TOUCHED;


        levelButtonFive = new LevelButton(new Rectangle(160, 350, 201, 201), "5");
        levelFiveState = BOUNDS_NOT_TOUCHED;

        levelButtonSix = new LevelButton(new Rectangle(480, 350, 201, 201), "6");
        levelSixState = BOUNDS_NOT_TOUCHED;

        levelButtonSeven = new LevelButton(new Rectangle(800, 350, 201, 201), "7");
        levelSevenState = BOUNDS_NOT_TOUCHED;

        levelButtonEight = new LevelButton(new Rectangle(1120, 350, 201, 201), "8");
        levelEightState = BOUNDS_NOT_TOUCHED;


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
        if (Assets.readyState) {
            int len = touchEvents.size();


            for (int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);

                levelButtonOne.listenToTouches(touchEvents, guiCam, game, glGame, touchPoint, "WorldOneLevelOneScreen");
                levelButtonTwo.listenToTouches(touchEvents, guiCam, game, glGame, touchPoint, "WorldOneLevelTwoScreen");
                levelButtonThree.listenToTouches(touchEvents, guiCam, game, glGame, touchPoint, "WorldOneLevelThreeScreen");
                levelButtonFour.listenToTouches(touchEvents, guiCam, game, glGame, touchPoint, "WorldOneLevelFourScreen");
                levelButtonFive.listenToTouches(touchEvents, guiCam, game, glGame, touchPoint, "WorldOneLevelFiveScreen");
                levelButtonSix.listenToTouches(touchEvents, guiCam, game, glGame, touchPoint, "WorldOneLevelSixScreen");
                levelButtonSeven.listenToTouches(touchEvents, guiCam, game, glGame, touchPoint, "WorldOneLevelSevenScreen");
                levelButtonEight.listenToTouches(touchEvents, guiCam, game, glGame, touchPoint, "WorldOneLevelEightScreen");

                if (event.type == TouchEvent.TOUCH_UP) {
                    touchPoint.set(event.x, event.y);
                    guiCam.touchToWorld(touchPoint);

                    backLevelSelectState = BOUNDS_NOT_TOUCHED;

                    if (OverlapTester.pointInRectangle(backWorldSelectorBounds, touchPoint)) {
                        //state = CHANGE_SCREEN_STATE;
                        game.setScreen(new LoadingScreen(glGame, "WorldSelectScreen"));
                        return;
                    }


                }
                if (event.type == TouchEvent.TOUCH_DOWN) {
                    touchPoint.set(event.x, event.y);
                    guiCam.touchToWorld(touchPoint);

                    if (OverlapTester.pointInRectangle(backWorldSelectorBounds, touchPoint))
                        backLevelSelectState = BOUNDS_TOUCHED;


                }
                if (event.type == TouchEvent.TOUCH_DRAGGED) {
                    touchPoint.set(event.x, event.y);
                    guiCam.touchToWorld(touchPoint);

                    if (!OverlapTester.pointInRectangle(backWorldSelectorBounds, touchPoint))
                        backLevelSelectState = BOUNDS_NOT_TOUCHED;

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
        batcher.beginBatch(Assets.worldOneSelectBackgroundTexture);
        batcher.drawSprite(640, 400, 1280, 800, Assets.worldOneSelectBackground);
        batcher.endBatch();

        batcher.beginBatch(Assets.worldOneSelectSprites);


        if(backLevelSelectState == BOUNDS_NOT_TOUCHED)
            batcher.drawSprite(70, 70, 141, 141, Assets.backLevelSelectLight);
        else
            batcher.drawSprite(70, 70, 141, 141, Assets.backLevelSelectDark);

        levelButtonOne.display(batcher);
        levelButtonTwo.display(batcher);
        levelButtonThree.display(batcher);
        levelButtonFour.display(batcher);
        levelButtonFive.display(batcher);
        levelButtonSix.display(batcher);
        levelButtonSeven.display(batcher);
        levelButtonEight.display(batcher);
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
