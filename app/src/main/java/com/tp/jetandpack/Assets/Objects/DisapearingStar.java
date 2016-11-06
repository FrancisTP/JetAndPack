package com.tp.jetandpack.Assets.Objects;

import com.tp.framework.gl.SpriteBatcher;
import com.tp.jetandpack.Assets.Assets;

import java.util.Random;

/**
 * Created by Francis on 2015-06-25.
 */
public class DisapearingStar {
    float x, y;
    float width, height;
    float shiningPeriod;

    float size;

    float sinX;
    Random random = new Random();
    //random.nextInt(max - min + 1) + min

    boolean ready;
    boolean flashed;

    public DisapearingStar(){
        this.x = random.nextInt(1281);
        this.y = random.nextInt(801);

        width = 0;
        height = 0;

        float temp = (random.nextInt(6 - 2 + 1) + 2) / 2;
        shiningPeriod = temp;

        sinX = 0;

        size = random.nextInt(8) + 1;

        ready = false;
        flashed = false;
    }

    public void update(float deltaTime){
        if(ready) {
            if (!flashed)
                sinX += shiningPeriod / 60;
            if (sinX >= Math.PI) {
                sinX = 0;
                flashed = true;
                width = 0;
                height = 0;
                ready = false;
            }

            width = (float) Math.sin(sinX) * size;
            height = (float)Math.sin(sinX) * size;
        }
    }

    public void reset(){
        this.x = random.nextInt(1281);
        this.y = random.nextInt(801);

        width = 0;
        height = 0;

        float temp = (random.nextInt(6 - 2 + 1) + 2) / 2;
        shiningPeriod = temp;

        sinX = 0;

        size = random.nextInt(8) + 1;

        ready = false;
        flashed = false;
    }

    public void draw(SpriteBatcher batcher){
        if(ready)
            batcher.drawSprite(x, y, width, height, Assets.mainMenuRoundStar);
    }
}
