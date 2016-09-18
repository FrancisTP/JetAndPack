package com.tp.jetandpack.World_01.Planet;

import com.tp.framework.Game;
import com.tp.framework.Input;
import com.tp.framework.gl.Camera2D;
import com.tp.framework.gl.SpriteBatcher;
import com.tp.framework.gl.TextureRegion;
import com.tp.framework.impl.GLGame;
import com.tp.framework.GameObjectCircle;
import com.tp.framework.math.Vector2;
import com.tp.framework.math.Rectangle;
import com.tp.jetandpack.Assets.Assets;

import java.util.List;

/**
 * Created by franc on 2016-01-28.
 */
public class Planet {

    private String type;
    private float width, height;
    private float x, y;
    private float angle;
    public float prevAngle;
    public float previousTouchX;
    TextureRegion sprite;

    Rectangle bounds;
    private final Vector2 position;

    boolean firstTimeTouch;
    boolean fingerDown;

    final int BOUNDS_NOT_TOUCHED = 0;
    final int BOUNDS_TOUCHED = 1;

    Vector2 topPart;
    Vector2 topRightPart;
    Vector2 rightPart;
    Vector2 bottomRightPart;
    Vector2 bottomPart;
    Vector2 bottomLeftPart;
    Vector2 leftPart;
    Vector2 topLeftPart;

    float prevAngleLowerLimitOne;
    float prevAngleUpperLimitOne;

    float prevAngleLowerLimitTwo;
    float prevAngleUpperLimitTwo;

    GameObjectCircle tree;


    public Planet(String type){
        this.type = type;

        prevAngleLowerLimitOne = 347; // 707
        prevAngleUpperLimitOne = 353; // 714

        prevAngleLowerLimitTwo = 334; //694
        prevAngleUpperLimitTwo = 341; //701


        x = 640;
        y = -96.5f; //-818
        angle = 0;
        prevAngle = 360;

        firstTimeTouch = false;
        fingerDown = false;

        bounds = new Rectangle(x, y, 2086, 2086);

        position = new Vector2(x, y);

        topPart = new Vector2(x, y);
        topRightPart = new Vector2(x, y);
        rightPart = new Vector2(x, y);
        bottomRightPart = new Vector2(x, y);
        bottomPart = new Vector2(x, y);
        bottomLeftPart = new Vector2(x, y);
        leftPart = new Vector2(x, y);
        topLeftPart = new Vector2(x, y);



        if(type.equals("WorldOne"))
            sprite = Assets.worldOnePlanet;
        else
            sprite = Assets.worldOnePlanet;

        tree = new GameObjectCircle(0, 0, 1);
    }

    public void listenToTouches(List<Input.TouchEvent> touchEvents, Camera2D guiC, Game g, GLGame glG, float deltaTime) {
        Vector2 touchPoint = new Vector2();
        Camera2D guiCam = guiC;
        Game game = g;
        GLGame glGame = glG;

        int len = touchEvents.size();

        for (int i = 0; i < len && touchEvents.size() != 0; i++) {
            Input.TouchEvent event = touchEvents.get(i);

            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);

            if (event.type == Input.TouchEvent.TOUCH_UP) {
                firstTimeTouch = false;
                fingerDown = false;
            }

            if(event.type == Input.TouchEvent.TOUCH_DOWN) {

            }

            if(event.type == Input.TouchEvent.TOUCH_DRAGGED) {
                if(!fingerDown){
                    firstTimeTouch = true;
                    fingerDown = true;
                    update(touchPoint.x, true);
                }
                if(fingerDown){
                    update(touchPoint.x, false);
                }
            }
        }
    }

    public void display(SpriteBatcher batcher){

        // Calculation
        topPart = topPart.rotateAroundPoint(640, -818, 0, -521.5f, angle);
        topRightPart = topRightPart.rotateAroundPoint(640, -818, 0, -521.5f, angle - 45);
        rightPart = rightPart.rotateAroundPoint(640, -818, 0, -521.5f, angle - 90);
        bottomRightPart = bottomRightPart.rotateAroundPoint(640, -818, 0, -521.5f, angle - 135);
        bottomPart = bottomPart.rotateAroundPoint(640, -818, 0, -521.5f, angle - 180);
        bottomLeftPart = bottomLeftPart.rotateAroundPoint(640, -818, 0, -521.5f, angle - 225);
        leftPart = leftPart.rotateAroundPoint(640, -818, 0, -521.5f, angle - 270);
        topLeftPart = topLeftPart.rotateAroundPoint(640, -818, 0, -521.5f, angle - 315);

        tree.rotateAroundPoint(640, -818, 1340, 360 - angle - 15);

        batcher.drawSprite(tree.x, tree.y, 606, 622, tree.angle, Assets.worldOneWall);
        // Eight parts of the planet
        batcher.drawSprite(topPart.x, topPart.y, sprite.width, sprite.height, angle, sprite);
        batcher.drawSprite(topRightPart.x, topRightPart.y, sprite.width, sprite.height, angle - 45, sprite);
        batcher.drawSprite(rightPart.x, rightPart.y, sprite.width, sprite.height, angle - 90, sprite);
        batcher.drawSprite(bottomRightPart.x, bottomRightPart.y, sprite.width, sprite.height, angle - 135, sprite);
        batcher.drawSprite(bottomPart.x, bottomPart.y, sprite.width, sprite.height, angle - 180, sprite);
        batcher.drawSprite(bottomLeftPart.x, bottomLeftPart.y, sprite.width, sprite.height, angle - 225, sprite);
        batcher.drawSprite(leftPart.x, leftPart.y, sprite.width, sprite.height, angle - 270, sprite);
        batcher.drawSprite(topLeftPart.x, topLeftPart.y, sprite.width, sprite.height, angle -315, sprite);

    }

    private void checkAngle(){
        if(angle >= 360){
            angle -= 360;
        }
        if(angle < 0){
            angle += 360;
        }
    }

    public void addAngle(float a){
        angle += a;
        checkAngle();
    }

    public void update(float touchX, boolean firstTimeTouch) {
            this.firstTimeTouch = firstTimeTouch;

            bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);

            if (firstTimeTouch) {
                previousTouchX = touchX;
            } else {
                float difference = (previousTouchX - touchX) / 20;
                if (difference > 5)
                    difference = 5;
                if (difference < -5)
                    difference = -5;
                addAngle(difference);

                if (prevAngle >= prevAngleLowerLimitOne && prevAngle < prevAngleUpperLimitOne && angle < prevAngleLowerLimitOne)
                    angle = prevAngleLowerLimitOne;
                if (prevAngle <= prevAngleUpperLimitTwo && prevAngle > prevAngleLowerLimitTwo && angle > prevAngleUpperLimitTwo)
                    angle = prevAngleUpperLimitTwo;


                prevAngle = angle;

                previousTouchX = touchX;
            }

    }
}
