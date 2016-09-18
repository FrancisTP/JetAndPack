package com.tp.jetandpack.Menues;

import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.annotation.SuppressLint;

import com.tp.framework.CollisionTester;
import com.tp.framework.Game;
import com.tp.framework.GameObject;
import com.tp.framework.Input.TouchEvent;
//import com.tp.framework.gl.Animation;
import com.tp.framework.gl.Camera2D;
import com.tp.framework.gl.FPSCounter;
import com.tp.framework.gl.SpriteBatcher;
//import com.tp.framework.gl.TextureRegion;
import com.tp.framework.impl.GLScreen;
//import com.tp.framework.math.Circle;
import com.tp.framework.math.Circle;
import com.tp.framework.math.Rectangle;
import com.tp.framework.math.Vector2;
import com.tp.framework.math.OverlapTester;
import com.tp.jetandpack.Assets.Assets;
import com.tp.jetandpack.Assets.Objects.DisapearingStar;
import com.tp.jetandpack.Assets.Objects.ShootingStar;
import com.tp.jetandpack.Assets.Objects.Star;
import com.tp.jetandpack.Settings.SoundController;

public class MainMenuScreen extends GLScreen {
	// Variables 
	Camera2D guiCam;
	SpriteBatcher batcher;
	Vector2 touchPoint;
	FPSCounter fpsCounter = new FPSCounter();
	 
	int state;
	final int LOADING_STATE = -1;
	final int RUNNING_STATE = 0;
	final int CHANGE_SCREEN_STATE = 1;

	GameObject title;
	float titleSinX;
	float titleDefaultY;

	Rectangle playBounds;
	int playState;
	Rectangle settingsBounds;
	int settingsState;
	Rectangle upgradeBounds;
	int upgradeState;
	Rectangle storeBounds;
	int storeState;
	
	final int BOUNDS_NOT_TOUCHED = 0;
	final int BOUNDS_TOUCHED = 1;

	Star starOne;
	Star starTwo;
	Star starThree;
	Star starFour;
	Star starFive;
	Star starSix;
	Star starSeven;

	ShootingStar shootingStarOne;
	ShootingStar shootingStarTwo;

	final int starNumber = 15;
	DisapearingStar[] disapearingStars;

	Circle noStarZone;

	float stateTime = 3;


	public MainMenuScreen(Game game){
		super(game);
		
		guiCam = new Camera2D(glGraphics, 1280, 800); // Screen resolution 1280x800
		batcher = new SpriteBatcher(glGraphics, 100); // A maximum of 100 sprite per batch
		touchPoint = new Vector2();
		
		state = RUNNING_STATE;

		titleSinX = 0;
		titleDefaultY = 650;
		title = new GameObject(640, titleDefaultY, 350, 168);

		// Rectangles used to detect touch inputs
		playBounds = new Rectangle(195, 470, 250, 80);
		playState = BOUNDS_NOT_TOUCHED;

		settingsBounds = new Rectangle(195, 350, 250, 80);
		settingsState = BOUNDS_NOT_TOUCHED;

		upgradeBounds = new Rectangle(195, 230, 250, 80);
		upgradeState = BOUNDS_NOT_TOUCHED;

		storeBounds = new Rectangle(195, 110, 250, 80);
		storeState = BOUNDS_NOT_TOUCHED;

		// Initiating stars
		starOne = new Star(88, 750, 22, 22);
		starTwo = new Star(135, 725, 10, 10);
		starThree = new Star(40, 700, 15, 15);

		starFour = new Star(17, 270, 10, 10);
		starFive = new Star(26, 255, 15, 15);

		starSix = new Star(177, 30, 17, 17);
		starSeven = new Star(192, 50, 12, 12);

		shootingStarOne = new ShootingStar();
		shootingStarTwo = new ShootingStar();

		noStarZone = new Circle(880, 200, 476);

		disapearingStars = new DisapearingStar[starNumber];

		for(int i=0; i < disapearingStars.length; i++){
			disapearingStars[i] = new DisapearingStar();
		}


		// Request a song
		//SoundController.requestSong("MainMenuSong.mp3");
	}
	
	@Override
	public void update(float deltaTime){
		SoundController.update();

		if(Assets.readyState){
			List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
			
			if(state == RUNNING_STATE)
				listenToTouches(touchEvents);
		}

		// Title sin wave
		titleSinX += 0.05;
		if(titleSinX >= 360)
			titleSinX -= 360;
		title.position.y = titleDefaultY + ((float)Math.sin(titleSinX) * 2.5f);

		// Update stars
		starOne.update(deltaTime);
		starTwo.update(deltaTime);
		starThree.update(deltaTime);

		starFour.update(deltaTime);
		starFive.update(deltaTime);

		starSix.update(deltaTime);
		starSeven.update(deltaTime);

		shootingStarOne.update(deltaTime);
		shootingStarTwo.update(deltaTime);

		for(int i=0; i < disapearingStars.length; i++){
			if(disapearingStars[i].flashed || CollisionTester.CollisionTest(disapearingStars[i].x, disapearingStars[i].y, noStarZone)) {
				disapearingStars[i].reset();
			} else if(!disapearingStars[i].ready) {
				disapearingStars[i].ready = true;
			}
			disapearingStars[i].update(deltaTime);
		}

		//time += deltaTime;
		//Log.d("deltaTime", "" + time);
	}
	
	public void listenToTouches(List<TouchEvent> touchEvents){
		int len = touchEvents.size();
		for(int i=0; i<len; i++){
			TouchEvent event = touchEvents.get(i);
			
			if(event.type == TouchEvent.TOUCH_UP){
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);
				
				playState = BOUNDS_NOT_TOUCHED;
				settingsState = BOUNDS_NOT_TOUCHED;
				upgradeState = BOUNDS_NOT_TOUCHED;
				storeState = BOUNDS_NOT_TOUCHED;
			
				if(OverlapTester.pointInRectangle(playBounds, touchPoint)){
					//state = CHANGE_SCREEN_STATE;
					game.setScreen(new LoadingScreen(glGame, "WorldSelectScreen"));
					return;
				}
				if(OverlapTester.pointInRectangle(settingsBounds, touchPoint)){
					SoundController.requestSong("StoryBoardSong.mp3");
					return;
				}
				if(OverlapTester.pointInRectangle(upgradeBounds, touchPoint)){
					Random random = new Random();
					int ran = random.nextInt(2) + 1;
					if(ran == 1)
						SoundController.requestSong("ColorChangingSong.mp3");
					else
						SoundController.requestSong("BossBattleSong.mp3");
					return;
				}
				if(OverlapTester.pointInRectangle(storeBounds, touchPoint)){
					SoundController.stopMusic();
					return;
				}
				
			}
			if(event.type == TouchEvent.TOUCH_DOWN){
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);
				
				if(OverlapTester.pointInRectangle(playBounds, touchPoint))
					playState = BOUNDS_TOUCHED;
				if(OverlapTester.pointInRectangle(settingsBounds, touchPoint))
					settingsState = BOUNDS_TOUCHED;
				if(OverlapTester.pointInRectangle(upgradeBounds, touchPoint))
					upgradeState = BOUNDS_TOUCHED;
				if(OverlapTester.pointInRectangle(storeBounds, touchPoint))
					storeState = BOUNDS_TOUCHED;
			}
			if(event.type == TouchEvent.TOUCH_DRAGGED){
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);
				
				if(!OverlapTester.pointInRectangle(playBounds, touchPoint))
					playState = BOUNDS_NOT_TOUCHED;
				if(!OverlapTester.pointInRectangle(settingsBounds, touchPoint))
					settingsState = BOUNDS_NOT_TOUCHED;
				if(!OverlapTester.pointInRectangle(upgradeBounds, touchPoint))
					upgradeState = BOUNDS_NOT_TOUCHED;
				if(!OverlapTester.pointInRectangle(storeBounds, touchPoint))
					storeState = BOUNDS_NOT_TOUCHED;
			}
		}
	}

	@Override
	public void present(float deltaTime){
		if(state == LOADING_STATE)
			presentLoading(deltaTime);
		else if(state == RUNNING_STATE)
			presentRunning(deltaTime);
		else if(state == CHANGE_SCREEN_STATE)
			presentChange(deltaTime);
	}

	@SuppressLint("FloatMath")
	public void presentLoading(float deltaTime){
		presentRunning(deltaTime);
	}

	@SuppressLint("FloatMath")
	public void presentRunning(float deltaTime){
		stateTime += deltaTime; // updates stateTime if we want frame independent movement
		
		// Initiates everything needed to render sprites
		GL10 gl = glGraphics.getGL();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		// Draw background
		batcher.beginBatch(Assets.mainMenuBackgroundTexture);
		batcher.drawSprite(640, 400, 1280, 800, Assets.mainMenuBackground);
		batcher.endBatch();
		
		// Draw all objects
		batcher.beginBatch(Assets.mainMenuObjectsSprites);

		// Draw stars
		starOne.draw(batcher);
		starTwo.draw(batcher);
		starThree.draw(batcher);

		starFour.draw(batcher);
		starFive.draw(batcher);

		starSix.draw(batcher);
		starSeven.draw(batcher);

		shootingStarOne.draw(batcher);
		shootingStarTwo.draw(batcher);

		for(int i=0; i < disapearingStars.length; i++){
			disapearingStars[i].draw(batcher);
		}

			// Title
		batcher.drawSprite(title.position.x, titleDefaultY, 542, 244, Assets.mainMenuTitleContour);
		batcher.drawSprite(title.position.x, title.position.y, title.bounds.width, title.bounds.height, Assets.mainMenuJetAndPack);

			// Play button
		if(playState == BOUNDS_NOT_TOUCHED)
			batcher.drawSprite(playBounds.x, playBounds.y, playBounds.width, playBounds.height, Assets.mainMenuButtonUp);
		else
			batcher.drawSprite(playBounds.x, playBounds.y, playBounds.width, playBounds.height, Assets.mainMenuButtonDown);
		batcher.drawSprite(playBounds.x, playBounds.y, 114, 56, Assets.mainMenuPlay);

			// Settings button
		if(settingsState == BOUNDS_NOT_TOUCHED)
			batcher.drawSprite(settingsBounds.x, settingsBounds.y, settingsBounds.width, settingsBounds.height, Assets.mainMenuButtonUp);
		else
			batcher.drawSprite(settingsBounds.x, settingsBounds.y, settingsBounds.width, settingsBounds.height, Assets.mainMenuButtonDown);
		batcher.drawSprite(settingsBounds.x, settingsBounds.y, 212, 56, Assets.mainMenuSettings);

			// Upgrade button
		if(upgradeState == BOUNDS_NOT_TOUCHED)
			batcher.drawSprite(upgradeBounds.x, upgradeBounds.y, upgradeBounds.width, upgradeBounds.height, Assets.mainMenuButtonUp);
		else
			batcher.drawSprite(upgradeBounds.x, upgradeBounds.y, upgradeBounds.width, upgradeBounds.height, Assets.mainMenuButtonDown);
		batcher.drawSprite(upgradeBounds.x, upgradeBounds.y, 222, 56, Assets.mainMenuUpgrade);

			// Store button
		if(storeState == BOUNDS_NOT_TOUCHED)
			batcher.drawSprite(storeBounds.x, storeBounds.y, storeBounds.width, storeBounds.height, Assets.mainMenuButtonUp);
		else
			batcher.drawSprite(storeBounds.x, storeBounds.y, storeBounds.width, storeBounds.height, Assets.mainMenuButtonDown);
		batcher.drawSprite(storeBounds.x, storeBounds.y, 138, 45, Assets.mainMenuStore);

		batcher.endBatch();

		/*
		// Draw collision lines
		batcher.beginBatch(Assets.collisionLinesTexture);
		noStarZone.drawShape(batcher);
		batcher.endBatch();
		*/

		// Stop rendering
		gl.glDisable(GL10.GL_BLEND);
		
		// Display fps on LogCat
		//fpsCounter.logFrame();
	}

	@SuppressLint("FloatMath")
	public void presentChange(float deltaTime){
		presentRunning(deltaTime);

	}
	
	@Override
	public void pause(){
	}
	
	@Override
	public void resume(){
	}
	
	@Override
	public void dispose(){
	}
}
