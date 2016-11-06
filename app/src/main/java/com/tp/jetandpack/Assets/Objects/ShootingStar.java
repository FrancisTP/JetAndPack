package com.tp.jetandpack.Assets.Objects;

//import android.util.Log;

import com.tp.framework.gl.SpriteBatcher;
import com.tp.jetandpack.Assets.Assets;

import java.util.Random;

/**
 * Created by Francis on 2015-06-24.
 */
public class ShootingStar {
    float x, y;
    float width, height;
    float shiningPeriod;

    float startingX, startingY;
    float endingX, endingY;

    float defaultX, defaultY;
    float defaultWidth, defaultHeight;

    float xMoveInterval, yMoveInterval;

    float sinX = 0;
    float angle;
    Random random = new Random();
    //random.nextInt(max - min + 1) + min

    boolean hasPassed;
    boolean passing;
    boolean done;

    int randomInterval;
    int startSide;
    int endSide;
    int probability;

    public ShootingStar(){
        x = -270;
        y = -270;

        defaultX = x;
        defaultY = y;

        width = 270;
        height = 13;

        initiateSame();
    }

    public ShootingStar(float width, float height){
        if(width >= height){
            x = -width;
            y = -width;
        } else {
            x = -height;
            y = -height;
        }

        defaultX = x;
        defaultY = y;

        this.width = width;
        this.height = height;

        initiateSame();
    }

    public void initiateSame(){
        startingX = 0;
        startingY = 0;
        endingX = 0;
        endingY = 0;

        defaultWidth = width;
        defaultHeight = height;

        probability = 350;

        xMoveInterval = 0;
        yMoveInterval = 0;

        angle = 0;

        hasPassed = false;
        passing = false;
        done = false;

        float tempMili = random.nextInt(100)/100;
        float tempSec = random.nextInt(12 - 5 + 1) + 5;
        shiningPeriod = tempSec + tempMili;

        randomInterval = 0;
        startSide = 0;
        endSide = 0;
    }

    public void update(float deltaTime){
        sinX += shiningPeriod/60;
        if(sinX >= 360)
            sinX -= 360;

        width = defaultWidth + ((float)Math.sin(sinX) * 2);
        height = defaultHeight + ((float)Math.sin(sinX) * 2);

        if(!passing) {
            randomInterval = random.nextInt(probability) + 1;

            if (randomInterval == 50) {
                startSide = random.nextInt(4) + 1;
                endSide = random.nextInt(4) + 1;

                if (startSide != endSide) {
                    passing = true;

                    if (startSide == 1) {
                        startingX = defaultX;
                        startingY = random.nextInt(800) + 1;
                    } else if (startSide == 2) {
                        startingX = random.nextInt(1280) + 1;
                        startingY = -defaultY + 800;
                    } else if (startSide == 3) {
                        startingX = -defaultX + 1280;
                        startingY = random.nextInt(800) + 1;
                    } else if (startSide == 4) {
                        startingX = random.nextInt(1280) + 1;
                        startingY = defaultY;
                    }

                    if (endSide == 1) {
                        endingX = defaultX;
                        endingY = random.nextInt(800) + 1;
                    } else if (endSide == 2) {
                        endingX = random.nextInt(1280) + 1;
                        endingY = -defaultY + 800;
                    } else if (endSide == 3) {
                        endingX = -defaultX + 1280;
                        endingY = random.nextInt(800) + 1;
                    } else if (endSide == 4) {
                        endingX = random.nextInt(1280) + 1;
                        endingY = defaultY;
                    }

                    x = startingX;
                    y = startingY;

                    xMoveInterval = (endingX - startingX) / 30;
                    yMoveInterval = (endingY - startingY) / 30;

                    if(xMoveInterval > 0)
                        angle = (float)((Math.atan((endingY - startingY)/(endingX - startingX))) * 180 / Math.PI);
                    else
                        angle = (float)((Math.atan((endingY - startingY)/(endingX - startingX))) * 180 / Math.PI) - 180;

                } else {
                    startSide = 0;
                    endSide = 0;
                }
            }
        } else {
            // Passing
            if (xMoveInterval > 0 && yMoveInterval > 0) {
                //Left to Right Upwards
                if(x > endingX && y > endingY)
                    done = true;
            } else if(xMoveInterval > 0 && yMoveInterval < 0){
                //Left to Right Downwards
                if(x > endingX && y < endingY)
                    done = true;
            } else if(xMoveInterval < 0 && yMoveInterval > 0){
                //Right to Left Upwards
                if(x < endingX && y > endingY)
                    done = true;
            } else if(xMoveInterval < 0 && yMoveInterval < 0){
                //Right to Left Downwards
                if(x < endingX && y < endingY)
                    done = true;
            }

            if(!done && passing){
                x += xMoveInterval;
                y += yMoveInterval;

            } else {
                startingX = 0;
                startingY = 0;
                endingX = 0;
                endingY = 0;

                x = defaultX;
                y = defaultY;

                xMoveInterval = 0;
                yMoveInterval = 0;

                angle = 0;

                hasPassed = false;
                passing = false;
                done = false;

                randomInterval = 0;
                startSide = 0;
                endSide = 0;
            }
        }
    }

    public void draw(SpriteBatcher batcher){
        batcher.drawSprite(x, y, width, height, angle, Assets.mainMenuShootingStar);
    }
}
