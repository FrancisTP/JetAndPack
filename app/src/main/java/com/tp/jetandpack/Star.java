package com.tp.jetandpack;

import com.tp.framework.gl.SpriteBatcher;
import java.util.Random;

/**
 * Created by Francis on 2015-06-24.
 */
public class Star {
    float x, y;
    float width, height;
    float shiningPeriod;

    float defaultWidth, defaultHeight;

    float sinX = 0;
    float angle;
    Random random = new Random();
    //random.nextInt(max - min + 1) + min

    public Star(float x, float y, float width, float height){
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.defaultWidth = this.width;
        this.defaultHeight = this.height;

        float tempMili = random.nextInt(100)/100;
        float tempSec = random.nextInt(4 - 2 + 1) + 2;
        shiningPeriod = tempSec + tempMili;

        int angleDecide = random.nextInt(2);

        if(angleDecide == 1)
            angle = 0;
        else
            angle = 45;
    }

    public Star(float x, float y){
        this.x = x;
        this.y = y;

        width = random.nextInt(24 - 10 + 1) + 10;
        height = width;

        this.defaultWidth = this.width;
        this.defaultHeight = this.height;

        float tempMili = random.nextInt(100)/100;
        float tempSec = random.nextInt(12 - 5 + 1) + 5;
        shiningPeriod = tempSec + tempMili;

        int angleDecide = random.nextInt(2) + 1;

        if(angleDecide == 1)
            angle = 0;
        else
            angle = 45;
    }

    public void update(float deltaTime){
        sinX += shiningPeriod/60;
        if(sinX >= 360)
            sinX -= 360;

        width = defaultWidth + ((float)Math.sin(sinX) * 2);
        height = defaultHeight + ((float)Math.sin(sinX) * 2);
    }

    public void draw(SpriteBatcher batcher){
        batcher.drawSprite(x, y, width, height, angle, Assets.mainMenuStar);
    }
}
