package com.tp.jetandpack;

import com.tp.framework.Game;
import com.tp.framework.Input;
import com.tp.framework.gl.Camera2D;
import com.tp.framework.gl.SpriteBatcher;
import com.tp.framework.gl.TextureRegion;
import com.tp.framework.impl.GLGame;
import com.tp.framework.math.OverlapTester;
import com.tp.framework.math.Rectangle;
import com.tp.framework.math.Vector2;

import java.util.List;

/**
 * Created by franc on 2016-01-27.
 */
public class PauseMenu {

    private String screen;
    private TextureRegion sprite;
    float x, y;

    private QuitButton quitButton;

    public PauseMenu(String screen) {
        this.screen = screen;
        sprite = Assets.pauseMenuBackground;
        x = 640;
        y = 400;

        quitButton = new QuitButton("WorldOneSelectScreen");
    }


    public void listenToTouches(List<Input.TouchEvent> touchEvents, Camera2D guiCam, Game game, GLGame glGame, Vector2 touchPoint) {

        quitButton.listenToTouches(touchEvents, guiCam, game, glGame, touchPoint);

    }

    public void display(SpriteBatcher batcher){

        batcher.drawSprite(x, y, sprite.width, sprite.height, sprite);
        quitButton.display(batcher);
    }
}
