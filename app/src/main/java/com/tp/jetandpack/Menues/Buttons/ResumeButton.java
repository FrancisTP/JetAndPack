package com.tp.jetandpack.Menues.Buttons;

import com.tp.framework.Game;
import com.tp.framework.Input;
import com.tp.framework.gl.Camera2D;
import com.tp.framework.gl.SpriteBatcher;
import com.tp.framework.impl.GLGame;
import com.tp.framework.math.OverlapTester;
import com.tp.framework.math.Rectangle;
import com.tp.framework.math.Vector2;
import com.tp.jetandpack.Menues.LoadingScreen;

import java.util.List;

/**
 * Created by franc on 2016-01-27.
 */
public class ResumeButton {


    private Rectangle bounds;
    private int state;

    final int BOUNDS_NOT_TOUCHED = 0;
    final int BOUNDS_TOUCHED = 1;

    private String number;

    public ResumeButton(float x, float y, float widthHeight, String number) {
        bounds = new Rectangle(x, y, widthHeight, widthHeight);
        this.number = number;
    }

    public ResumeButton(float x, float y, float width, float height, String number) {
        bounds = new Rectangle(x, y, width, height);
        this.number = number;
    }

    public ResumeButton(Rectangle bounds, String number){
        this.bounds = bounds;
        this.number = number;
    }

    public void listenToTouches(List<Input.TouchEvent> touchEvents, Camera2D guiCam, Game game, GLGame glGame, Vector2 touchPoint, String screen) {

        int len = touchEvents.size();

        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);

            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);

            if (event.type == Input.TouchEvent.TOUCH_UP) {

                state = BOUNDS_NOT_TOUCHED;

                if(OverlapTester.pointInRectangle(bounds, touchPoint)){
                    if(number.equals("1")) {
                        game.setScreen(new LoadingScreen(glGame, screen));
                        return;
                    }
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

        if(state == BOUNDS_NOT_TOUCHED){}
            // Light
        else{}
            // Dark


    }
}
