package com.tp.jetandpack.Menues.Buttons;

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
import com.tp.jetandpack.Menues.GameMenus.PauseMenu;

import java.util.List;

/**
 * Created by franc on 2016-01-27.
 */
public class PauseButton {


    private Rectangle bounds;
    private int state;

    final int LOADING_STATE = -1;
    final int RUNNING_STATE = 0;
    final int PAUSED_STATE = 2;

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

    public void update(float deltaTime, List<Input.TouchEvent> touchEvents, Camera2D guiCam, Game game, GLGame glGame, Vector2 touchPoint) {
        pauseMenu.update(deltaTime, touchEvents, guiCam, game, glGame, touchPoint);

        listenToTouches(touchEvents, guiCam, game, glGame, touchPoint);
    }

    public void listenToTouches(List<Input.TouchEvent> touchEvents, Camera2D guiCam, Game game, GLGame glGame, Vector2 touchPoint) {

        int len = touchEvents.size();

        for (int i = 0; i < len && touchEvents.size() != 0; i++) {
            Input.TouchEvent event = touchEvents.get(i);

            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);

            // Three possible states of the game, three possible finger events for each
            if (Levels.state == Levels.RUNNING_STATE) {
                state = BOUNDS_NOT_TOUCHED;
                if (event.type == Input.TouchEvent.TOUCH_UP) {
                    if (!OverlapTester.pointInRectangle(bounds, touchPoint)) {
                        state = BOUNDS_NOT_TOUCHED;
                        buttonPressed = false;
                        return;
                    } else {
                        Levels.state = Levels.PAUSED_STATE;
                        state = BOUNDS_TOUCHED;
                        buttonPressed = true;
                        return;
                    }
                } else if(event.type == Input.TouchEvent.TOUCH_DOWN) {
                    if (OverlapTester.pointInRectangle(bounds, touchPoint)) {
                        state = BOUNDS_TOUCHED;
                        buttonPressed = true;
                        return;
                    }
                } else if(event.type == Input.TouchEvent.TOUCH_DRAGGED) {
                    if (!OverlapTester.pointInRectangle(bounds, touchPoint)) {
                        state = BOUNDS_NOT_TOUCHED;
                        buttonPressed = false;
                        return;
                    }
                }

            } else if (Levels.state == Levels.PAUSED_STATE) {
                // Nothing yet

            } else if (Levels.state == Levels.LOADING_STATE) {
                // Nothing yet

            }
        }
    }

    public void display(SpriteBatcher batcher){


        if(Levels.state == Levels.RUNNING_STATE) {
            if (state == BOUNDS_NOT_TOUCHED)
                batcher.drawSprite(bounds.x, bounds.y, lightSprite.width, lightSprite.height, lightSprite);
            else {
                batcher.drawSprite(bounds.x, bounds.y, darkSprite.width, darkSprite.height, darkSprite);
            }
        } else if (Levels.state == Levels.PAUSED_STATE) {
            pauseMenu.display(batcher);
        }
    }
}
