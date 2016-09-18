package com.tp.jetandpack.Menues.Buttons;

import com.tp.framework.Game;
import com.tp.framework.Input;
import com.tp.framework.gl.Camera2D;
import com.tp.framework.gl.SpriteBatcher;
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
public class LevelButton {


    private Rectangle bounds;
    private int state;

    final int BOUNDS_NOT_TOUCHED = 0;
    final int BOUNDS_TOUCHED = 1;

    private String number;

    public LevelButton(float x, float y, float widthHeight, String number) {
        bounds = new Rectangle(x, y, widthHeight, widthHeight);
        this.number = number;
    }

    public LevelButton(float x, float y, float width,float height, String number) {
        bounds = new Rectangle(x, y, width, height);
        this.number = number;
    }

    public LevelButton(Rectangle bounds, String number){
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

        if(state == BOUNDS_NOT_TOUCHED)
            batcher.drawSprite(bounds.x, bounds.y, Assets.levelSelectLevelIconLight.width, Assets.levelSelectLevelIconLight.height, Assets.levelSelectLevelIconLight);
        else
            batcher.drawSprite(bounds.x, bounds.y, Assets.levelSelectLevelIconDark.width, Assets.levelSelectLevelIconDark.height, Assets.levelSelectLevelIconDark);

        if(number.equals("1"))
            batcher.drawSprite(bounds.x, bounds.y, Assets.levelSelectOne.width, Assets.levelSelectOne.height, Assets.levelSelectOne);
        else if(number.equals("2"))
            batcher.drawSprite(bounds.x, bounds.y, Assets.levelSelectTwo.width, Assets.levelSelectTwo.height, Assets.levelSelectTwo);
        else if(number.equals("3"))
            batcher.drawSprite(bounds.x, bounds.y, Assets.levelSelectThree.width, Assets.levelSelectThree.height, Assets.levelSelectThree);
        else if(number.equals("4"))
            batcher.drawSprite(bounds.x, bounds.y, Assets.levelSelectFour.width, Assets.levelSelectFour.height, Assets.levelSelectFour);
        else if(number.equals("5"))
            batcher.drawSprite(bounds.x, bounds.y, Assets.levelSelectFive.width, Assets.levelSelectFive.height, Assets.levelSelectFive);
        else if(number.equals("6"))
            batcher.drawSprite(bounds.x, bounds.y, Assets.levelSelectSix.width, Assets.levelSelectSix.height, Assets.levelSelectSix);
        else if(number.equals("7"))
            batcher.drawSprite(bounds.x, bounds.y, Assets.levelSelectSeven.width, Assets.levelSelectSeven.height, Assets.levelSelectSeven);
        else if(number.equals("8"))
            batcher.drawSprite(bounds.x, bounds.y, Assets.levelSelectEight.width, Assets.levelSelectEight.height, Assets.levelSelectEight);
    }
}
