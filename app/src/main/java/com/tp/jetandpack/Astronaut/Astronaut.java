package com.tp.jetandpack.Astronaut;

import android.util.Log;

import com.tp.framework.DynamicGameObject;
import com.tp.framework.Game;
import com.tp.framework.GameObjectCircle;
import com.tp.framework.GameObjectRectangle;
import com.tp.framework.Input;
import com.tp.framework.gl.Animation;
import com.tp.framework.gl.Camera2D;
import com.tp.framework.gl.SpriteBatcher;
import com.tp.framework.gl.TextureRegion;
import com.tp.framework.impl.GLGame;
import com.tp.framework.math.Vector2;
import com.tp.jetandpack.Assets.Assets;
import com.tp.jetandpack.Menues.Buttons.Levels;

import java.util.List;

public class Astronaut extends DynamicGameObject{
	Vector2 leftArm;
	Vector2 rightArm;
	
	public final int FLY_STATE = 0;
	public final int FALL_STATE = 1;
	public final int HIT_STATE = 2;
	public final static float ASTRONAUT_WIDTH = 0.5f;
	public final static float ASTRONAUT_HEIGHT = 1;
	int state;
	
	public float lastY;
	public boolean goingUp;
	public float fakeX;
	public float speed;
	public float initialY;
	public int range;
	public float armRotation;
	public float armRotationIncrement;
	
	public GameObjectCircle head;
	public GameObjectRectangle body;
	public GameObjectRectangle jetPack;
	public GameObjectRectangle leftLeg;
	public GameObjectRectangle rightLeg;
	
	float stateTime;
	
	public Astronaut(float x, float y){
		super(x, y, ASTRONAUT_WIDTH, ASTRONAUT_HEIGHT);
		state = FLY_STATE;
		stateTime = 0;
		fakeX = 0;
		speed = 1;
		initialY = y;
		armRotation = 60;
		armRotationIncrement = 0.5f;
		range = 180;
		leftArm = new Vector2(x, y - 8);
		rightArm = new Vector2(x + 15, y - 5);
		goingUp = false;
		lastY = y;
		
		head = new GameObjectCircle(x + 10, y + 35, 24);
		body = new GameObjectRectangle(x - 1, y - 4, 28, 50);
		jetPack = new GameObjectRectangle(x - 20, y + 11, 26, 44);
		leftLeg = new GameObjectRectangle(x + 20, y - 40, 14, 30);
		rightLeg = new GameObjectRectangle(x - 12, y - 36, 26, 18);
	}
	
	public void update(float deltaTime, List<Input.TouchEvent> touchEvents, Camera2D guiCam, Game game, GLGame glGame, Vector2 touchPoint){

		updateRunning(deltaTime);
	}

	public void updateRunning(float deltaTime) {
		this.stateTime += deltaTime;

		fakeX -= speed * deltaTime;

		//Calculates sin wave for player
		if(fakeX < 2*Math.PI)
			fakeX += 2*Math.PI;
		float xMovement = (float)(Math.sin(fakeX));
		if(xMovement < 0)
			state = FALL_STATE;
		if(xMovement >= 0)
			state = FLY_STATE;

		// Determines arm rotation speed
		if(!goingUp && xMovement > -0.60)
			armRotationIncrement = 0.65f;
		else if(!goingUp && xMovement <= -0.60 && xMovement > -0.75)
			armRotationIncrement = 0.40f;
		else if(!goingUp && xMovement <= -0.75)
			armRotationIncrement = 0.20f;
		if(goingUp && xMovement < 0.75)
			armRotationIncrement = -0.45f;
		else if(goingUp && xMovement > 0.75)
			armRotationIncrement = -0.25f;

		armRotation += armRotationIncrement;

		if(armRotation>100)
			armRotation = 100;
		if(armRotation < 20)
			armRotation = 20;
		position.y = range*xMovement + initialY;

		if(position.y < lastY)
			goingUp = false;
		if(position.y >= lastY)
			goingUp = true;

		lastY = position.y;

		// Updates the collision boxes position
		head.setPosition(position.x + 10, position.y + 35);
		body.setPosition(position.x - 1, position.y - 4);
		jetPack.setPosition(position.x - 20, position.y + 11);
		leftLeg.setPosition(position.x + 20, position.y - 40);
		rightLeg.setPosition(position.x - 12, position.y - 36);

		// Arm rotation calculations
		leftArm = leftArm.rotateAroundPoint(position.x - 5, position.y + 9, -7.6f, 20, armRotation);
		rightArm = rightArm.rotateAroundPoint(position.x, position.y - 3, -12.5f, 10, armRotation);
	}

	public float getYPosition() {
		return position.y;
	}

	public void setYPosition(float y) {
		position.y = y;
		leftArm = leftArm.rotateAroundPoint(position.x - 5, position.y + 9, -7.6f, 20, armRotation);
		rightArm = rightArm.rotateAroundPoint(position.x, position.y - 3, -12.5f, 10, armRotation);
	}
	
	public void hit(){
		state = HIT_STATE;
	}

	public void drawSprite(SpriteBatcher batcher) {

		if (!Levels.isAstronautFinished) {
			if(state == FALL_STATE){
				TextureRegion keyFrame = Assets.astronautFire.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
				batcher.drawSprite(position.x - 20, position.y - 60, 41, 96, keyFrame);
			}

			batcher.drawSprite(rightArm.x, rightArm.y, Assets.astronautRightArm.width, Assets.astronautRightArm.height, armRotation, Assets.astronautRightArm);
			batcher.drawSprite(position.x + 10, position.y + 40, Assets.astronautNeutralFace.width, Assets.astronautNeutralFace.height, Assets.astronautNeutralFace);
			batcher.drawSprite(position.x, position.y, Assets.astronautBody.width, Assets.astronautBody.height, Assets.astronautBody);
			batcher.drawSprite(leftArm.x, leftArm.y, Assets.astronautLeftArm.width, Assets.astronautLeftArm.height, armRotation, Assets.astronautLeftArm);
		} else {
			batcher.drawSprite(position.x, position.y, Assets.winningPose.width, Assets.winningPose.height, Assets.winningPose);
		}

		//drawShape(batcher);
	}
	
	public void drawShape(SpriteBatcher batcher){
		body.drawShape(batcher);
		jetPack.drawShape(batcher);
		leftLeg.drawShape(batcher);
		rightLeg.drawShape(batcher);
		head.drawShape(batcher);
	}

	public int getState() {
		return state;
	}
}
