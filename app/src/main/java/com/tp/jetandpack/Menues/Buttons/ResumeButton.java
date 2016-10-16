package com.tp.jetandpack.Menues.Buttons;

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
import com.tp.jetandpack.Menues.LoadingScreen;

import java.util.List;

/**
 * Created by franc on 2016-01-27.
 */
public class ResumeButton {


    private Rectangle bounds;
    private int state;

    TextureRegion lightSprite, darkSprite;

    final int BOUNDS_NOT_TOUCHED = 0;
    final int BOUNDS_TOUCHED = 1;

    public ResumeButton() {
        lightSprite = Assets.pauseMenuResumeButtonLight;
        darkSprite = Assets.pauseMenuResumeButtonDark;

        bounds = new Rectangle(248, 286, lightSprite.width, lightSprite.height);
    }

    public ResumeButton(float x, float y, float widthHeight, String number) {
        bounds = new Rectangle(x, y, widthHeight, widthHeight);
    }

    public ResumeButton(float x, float y, float width, float height, String number) {
        bounds = new Rectangle(x, y, width, height);
    }

    public ResumeButton(Rectangle bounds, String number){
        this.bounds = bounds;
    }

    public void listenToTouches(List<Input.TouchEvent> touchEvents, Camera2D guiCam, Game game, GLGame glGame, Vector2 touchPoint) {

        int len = touchEvents.size();

        for (int i = 0; i < len && touchEvents.size() != 0; i++) {
            Input.TouchEvent event = touchEvents.get(i);

            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);

            if (event.type == Input.TouchEvent.TOUCH_UP) {

                state = BOUNDS_NOT_TOUCHED;

                if(OverlapTester.pointInRectangle(bounds, touchPoint)){
                    Levels.state = Levels.RUNNING_STATE;
                    return;
                }
            }

            if(event.type == Input.TouchEvent.TOUCH_DOWN) {
                if (OverlapTester.pointInRectangle(bounds, touchPoint)) {
                    state = BOUNDS_TOUCHED;
                    return;
                }
            }

            if(event.type == Input.TouchEvent.TOUCH_DRAGGED) {
                if (!OverlapTester.pointInRectangle(bounds, touchPoint)) {
                    state = BOUNDS_NOT_TOUCHED;
                    return;
                }
            }
        }
    }

    public void display(SpriteBatcher batcher){

        if(state == BOUNDS_NOT_TOUCHED) {
            batcher.drawSprite(bounds.x, bounds.y, bounds.width, bounds.height, lightSprite);
        } else {
            batcher.drawSprite(bounds.x, bounds.y, bounds.width, bounds.height, darkSprite);
        }
    }
}
