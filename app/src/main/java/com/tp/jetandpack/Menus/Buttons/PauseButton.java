package com.tp.jetandpack.Menus.Buttons;

import android.util.Log;

import com.tp.framework.Game;
import com.tp.framework.Input;
import com.tp.framework.gl.Camera2D;
import com.tp.framework.gl.SpriteBatcher;
import com.tp.framework.gl.TextureRegion;
import com.tp.framework.impl.GLGame;
import com.tp.framework.math.OverlapTester;
import com.tp.framework.math.Rectangle;
import com.tp.framework.math.Vector2;
import com.tp.jetandpack.Assets.Assets;
import com.tp.jetandpack.Menus.GameMenus.PauseMenu;

import java.util.List;

/**
 * Created by franc on 2016-01-27.
 */
public class PauseButton {


    private Rectangle bounds;
    private int state;

    final int LOADING_STATE = -1;
    final int RUNNING_STATE = 0;
    final int PAUSE_STATE = 2;

    boolean buttonPressed;

    final int BOUNDS_NOT_TOUCHED = 0;
    final int BOUNDS_TOUCHED = 1;

    PauseMenu pauseMenu;

    private String screen;
    private TextureRegion lightSprite, darkSprite;

    public PauseButton(float x, float y, float widthHeight, String screen) {
        bounds = new Rectangle(x, y, widthHeight, widthHeight);
        this.screen = screen;
        lightSprite = Assets.pauseMenuPauseButtonLight;
        darkSprite = Assets.pauseMenuPauseButtonDark;

        buttonPressed = false;
        pauseMenu = new PauseMenu(screen);
    }

    public PauseButton(float x, float y, float width, float height, String screen) {
        bounds = new Rectangle(x, y, width, height);
        this.screen = screen;
        lightSprite = Assets.pauseMenuPauseButtonLight;
        darkSprite = Assets.pauseMenuPauseButtonDark;

        buttonPressed = false;
        pauseMenu = new PauseMenu(screen);
    }

    public PauseButton(Rectangle bounds, String screen){
        this.bounds = bounds;
        this.screen = screen;
        lightSprite = Assets.pauseMenuPauseButtonLight;
        darkSprite = Assets.pauseMenuPauseButtonDark;

        buttonPressed = false;
        pauseMenu = new PauseMenu(screen);
    }

    public void listenToTouches(List<Input.TouchEvent> touchEvents, Camera2D guiCam, Game game, GLGame glGame, Vector2 touchPoint) {

        int len = touchEvents.size();

        pauseMenu.listenToTouches(touchEvents, guiCam, game, glGame, touchPoint);

        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);

            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);

            if (event.type == Input.TouchEvent.TOUCH_UP) {

                state = BOUNDS_NOT_TOUCHED;
                if(Levels.state == RUNNING_STATE && !buttonPressed) {
                    if (!OverlapTester.pointInRectangle(bounds, touchPoint)) {
                        Levels.state = RUNNING_STATE;
                        buttonPressed = false;

                        return;
                    }
                } else if(Levels.state == PAUSE_STATE && buttonPressed){
                    if (!OverlapTester.pointInRectangle(bounds, touchPoint)) {
                        Levels.state = RUNNING_STATE;
                        buttonPressed = false;
                        return;
                    }
                }
                if (OverlapTester.pointInRectangle(bounds, touchPoint)) {
                    buttonPressed = false;
                    return;
                }
            }

            if(event.type == Input.TouchEvent.TOUCH_DOWN) {
                if(Levels.state == RUNNING_STATE) {
                    if (OverlapTester.pointInRectangle(bounds, touchPoint)) {
                        state = BOUNDS_TOUCHED;
                        Levels.state = PAUSE_STATE;
                        buttonPressed = true;
                        return;
                    }
                }
            }

            if(event.type == Input.TouchEvent.TOUCH_DRAGGED) {
                if(Levels.state == RUNNING_STATE || (Levels.state == PAUSE_STATE && buttonPressed)) {
                    if (!OverlapTester.pointInRectangle(bounds, touchPoint)) {
                        //buttonPressed = false;
                        return;
                    }
                }
            }
        }
    }

    public void display(SpriteBatcher batcher){


        if(Levels.state == RUNNING_STATE) {
            if (state == BOUNDS_NOT_TOUCHED)
                batcher.drawSprite(bounds.x, bounds.y, lightSprite.width, lightSprite.height, lightSprite);
            else {
                batcher.drawSprite(bounds.x, bounds.y, darkSprite.width, darkSprite.height, darkSprite);
                Log.d("THIS SHIT DOESNT", " WORK!");
            }
        } else {
            if (state == BOUNDS_NOT_TOUCHED)
                pauseMenu.display(batcher);
            else
                batcher.drawSprite(bounds.x, bounds.y, lightSprite.width, lightSprite.height, darkSprite);
        }
    }
}
