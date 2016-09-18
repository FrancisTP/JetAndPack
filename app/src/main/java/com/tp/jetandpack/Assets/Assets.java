package com.tp.jetandpack.Assets;

//import com.tp.framework.Music;
//import com.tp.framework.Sound;
//import android.util.Log;

import com.tp.framework.gl.Animation;
//import com.tp.framework.gl.Font;
import com.tp.framework.gl.Texture;
import com.tp.framework.gl.TextureRegion;
import com.tp.framework.impl.GLGame;

public class Assets {
	
	protected static GLGame glGame;
	
	public static String onScreen = "";
	
	// Ready state
	public static boolean readyState;

	// AlwaysLoaded
	public static Texture SplashScreenScreen;
	public static TextureRegion SplashScreen;

	// Line Texture
	public static Texture collisionLinesTexture;
	public static TextureRegion line;
	public static TextureRegion circle;
	
	// MainMenu
	public static Texture mainMenuBackgroundTexture;
	public static TextureRegion mainMenuBackground;

	public static Texture mainMenuObjectsSprites;
	public static TextureRegion mainMenuButtonDown;
	public static TextureRegion mainMenuButtonUp;
	public static TextureRegion mainMenuJetAndPack;
	public static TextureRegion mainMenuPlay;
	public static TextureRegion mainMenuRoundStar;
	public static TextureRegion mainMenuSettings;
	public static TextureRegion mainMenuShootingStar;
	public static TextureRegion mainMenuStar;
	public static TextureRegion mainMenuStore;
	public static TextureRegion mainMenuTitleContour;
	public static TextureRegion mainMenuUpgrade;

	// World Select
	public static Texture worldSelectSprites;
	public static TextureRegion worldOne;
	public static TextureRegion underConstruction;
	public static TextureRegion worldContourLight;
	public static TextureRegion worldContourDark;
	public static TextureRegion backWorldSelectLight;
	public static TextureRegion backWorldSelectDark;

	// Level Select
	public static Texture worldOneSelectBackgroundTexture;
	public static TextureRegion worldOneSelectBackground;

	public static Texture worldOneSelectSprites;
	public static TextureRegion backLevelSelectLight;
	public static TextureRegion backLevelSelectDark;
	public static TextureRegion levelSelectOne;
	public static TextureRegion levelSelectTwo;
	public static TextureRegion levelSelectThree;
	public static TextureRegion levelSelectFour;
	public static TextureRegion levelSelectFive;
	public static TextureRegion levelSelectSix;
	public static TextureRegion levelSelectSeven;
	public static TextureRegion levelSelectEight;
	public static TextureRegion levelSelectLevelIconLight;
	public static TextureRegion levelSelectLevelIconDark;
	public static TextureRegion levelSelectLock;
	public static TextureRegion levelSelectOneStar;
	public static TextureRegion levelSelectTwoStars;
	public static TextureRegion levelSelectThreeStars;

	public static Texture worldOneBackgroundSprite;
	public static TextureRegion worldOneBackground;

	public static Texture worldOnePlanetSpriteSheet;
	public static TextureRegion worldOnePlanet;
	public static TextureRegion worldOneWall;

	//// Pause Menu
	public static Texture pauseMenuSpriteSheet;
	public static TextureRegion pauseMenuBackground;
	public static TextureRegion pauseMenuPauseButtonLight;
	public static TextureRegion pauseMenuPauseButtonDark;
	public static TextureRegion pauseMenuQuitButtonLight;
	public static TextureRegion pauseMenuQuitButtonDark;
	public static TextureRegion pauseMenuResumeButtonLight;
	public static TextureRegion pauseMenuResumeButtonDark;
	public static TextureRegion pauseMenuSoundsTitle;
	public static TextureRegion pauseMenuMusicTitle;
	public static TextureRegion pauseMenuSoundBarOutline;
	public static TextureRegion pauseMenuSoundBarEnd;
	public static TextureRegion pauseMenuSoundBarFilling;
	public static TextureRegion pauseMenuSoundBarNubLight;
	public static TextureRegion pauseMenuSoundBarNubDark;

	//public static Texture worldOneLevelsSprites;
	//

	////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////


	public static void load(GLGame game){
		glGame = game;
		loadAlwaysLoaded(glGame);
	}
	public static void load(){
		//loadAlwaysLoaded(glGame);
		if(onScreen.equals("MainMenuScreen"))
			loadMainMenu(glGame);
		else if(onScreen.equals("WorldSelectScreen"))
			loadWorldSelect(glGame);
		else if(onScreen.equals("WorldOneSelectScreen"))
			loadWorldOneSelect(glGame);
		else if(onScreen.equals("WorldOneLevelOneScreen") || onScreen.equals("WorldOneLevelTwoScreen") || onScreen.equals("WorldOneLevelThreeScreen") || onScreen.equals("WorldOneLevelFourScreen") || onScreen.equals("WorldOneLevelFiveScreen") || onScreen.equals("WorldOneLevelSixScreen") || onScreen.equals("WorldOneLevelSevenScreen") || onScreen.equals("WorldOneLevelEightScreen"))
			loadWorldOneLevels(glGame);
	}
	
	
	public static void reload(){
		reloadAlwaysLoaded();
		if(onScreen.equals("MainMenuScreen"))
			reloadMainMenu();
		else if(onScreen.equals("WorldSelectorScreen"))
			reloadWorldSelect();
		else if(onScreen.equals("WorldOneSelectScreen"))
			reloadWorldOneSelect();
		else if(onScreen.equals("WorldOneLevelOneScreen") || onScreen.equals("WorldOneLevelTwoScreen") || onScreen.equals("WorldOneLevelThreeScreen") || onScreen.equals("WorldOneLevelFourScreen") || onScreen.equals("WorldOneLevelFiveScreen") || onScreen.equals("WorldOneLevelSixScreen") || onScreen.equals("WorldOneLevelSevenScreen") || onScreen.equals("WorldOneLevelEightScreen"))
			reloadWorldOneLevels();
	}
	
	public static void dispose(){
		unloadAlwaysLoaded();
		if(onScreen.equals("MainMenuScreen"))
			unloadMainMenu();
		else if(onScreen.equals("WorldSelectorScreen"))
			unloadWorldSelect();
		else if(onScreen.equals("WorldOneSelectScreen"))
			unloadWorldOneSelect();
		else if(onScreen.equals("WorldOneLevelOneScreen") || onScreen.equals("WorldOneLevelTwoScreen") || onScreen.equals("WorldOneLevelThreeScreen") || onScreen.equals("WorldOneLevelFourScreen") || onScreen.equals("WorldOneLevelFiveScreen") || onScreen.equals("WorldOneLevelSixScreen") || onScreen.equals("WorldOneLevelSevenScreen") || onScreen.equals("WorldOneLevelEightScreen"))
			unloadWorldOneLevels();
	}
	
	public static void clear(){
		//clearAlwaysLoaded();
		if(onScreen.equals("MainMenuScreen"))
			clearMainMenu();
		else if(onScreen.equals("WorldSelectorScreen"))
			clearWorldSelect();
		else if(onScreen.equals("WorldOneSelectScreen"))
			clearWorldOneSelect();
		else if(onScreen.equals("WorldOneLevelOneScreen") || onScreen.equals("WorldOneLevelTwoScreen") || onScreen.equals("WorldOneLevelThreeScreen") || onScreen.equals("WorldOneLevelFourScreen") || onScreen.equals("WorldOneLevelFiveScreen") || onScreen.equals("WorldOneLevelSixScreen") || onScreen.equals("WorldOneLevelSevenScreen") || onScreen.equals("WorldOneLevelEightScreen"))
			clearWorldOneLevels();
	}


	////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////


	public static void loadAlwaysLoaded(GLGame game){

		SplashScreenScreen = new Texture(game, "SplashScreen.png");
		SplashScreen = new TextureRegion(SplashScreenScreen, 0, 0, 1280, 800);
	}
	public static void reloadAlwaysLoaded(){
		SplashScreenScreen.reload();
	}
	public static void unloadAlwaysLoaded(){
		SplashScreenScreen.dispose();
	}
	public static void clearAlwaysLoaded(){

		unloadAlwaysLoaded();

		SplashScreenScreen = null;
		SplashScreen = null;
	}


	////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////
	
	
	public static void loadMainMenu(GLGame game){

		// Background
		mainMenuBackgroundTexture = new Texture(game, "MainMenuBackground.png");
		mainMenuBackground = new TextureRegion(mainMenuBackgroundTexture, 0, 0, 1280, 800);

		// Main menu objects
		mainMenuObjectsSprites = new Texture(game, "MainMenuObjects.png");
		mainMenuButtonDown = new TextureRegion(mainMenuObjectsSprites, 984, 2, 250, 80);
		mainMenuButtonUp = new TextureRegion(mainMenuObjectsSprites, 984, 84, 250, 80);
		mainMenuJetAndPack = new TextureRegion(mainMenuObjectsSprites, 546, 2, 350, 168);
		mainMenuPlay = new TextureRegion(mainMenuObjectsSprites, 984, 166, 114, 56);
		mainMenuRoundStar = new TextureRegion(mainMenuObjectsSprites, 818, 230, 8, 8);
		mainMenuSettings = new TextureRegion(mainMenuObjectsSprites, 770, 172, 212, 56);
		mainMenuShootingStar = new TextureRegion(mainMenuObjectsSprites, 546, 230, 270, 13);
		mainMenuStar = new TextureRegion(mainMenuObjectsSprites, 984, 224, 22, 22);
		mainMenuStore = new TextureRegion(mainMenuObjectsSprites, 1100, 166, 138, 45);
		mainMenuTitleContour = new TextureRegion(mainMenuObjectsSprites, 2, 2, 542, 244);
		mainMenuUpgrade = new TextureRegion(mainMenuObjectsSprites, 546, 172, 222, 56);

		// Collision lines
		collisionLinesTexture = new Texture(game, "CollisionLines.png");
		line = new TextureRegion(collisionLinesTexture, 519, 2, 2, 2);
		circle = new TextureRegion(collisionLinesTexture, 2, 2, 515, 515);
	}
	public static void reloadMainMenu(){
		mainMenuBackgroundTexture.reload();
		mainMenuObjectsSprites.reload();
		collisionLinesTexture.reload();
	}
	public static void unloadMainMenu(){
		mainMenuBackgroundTexture.dispose();
		mainMenuObjectsSprites.dispose();
		collisionLinesTexture.dispose();
	}
	public static void clearMainMenu(){
		
		unloadMainMenu();

		// Background
		mainMenuBackgroundTexture = null;
		mainMenuBackground = null;

		// Main menu objects
		mainMenuObjectsSprites = null;
		mainMenuButtonDown = null;
		mainMenuButtonUp = null;
		mainMenuJetAndPack = null;
		mainMenuPlay = null;
		mainMenuRoundStar = null;
		mainMenuSettings = null;
		mainMenuShootingStar = null;
		mainMenuStar = null;
		mainMenuStore = null;
		mainMenuTitleContour = null;
		mainMenuUpgrade = null;

		// Collision lines
		collisionLinesTexture = null;
		line = null;
		circle = null;
	}


	////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////


	public static void loadWorldSelect(GLGame game){
		worldSelectSprites = new Texture(game, "WorldSelectorSprites.png");
		worldOne = new TextureRegion(worldSelectSprites, 978, 2, 320, 400);
		underConstruction = new TextureRegion(worldSelectSprites, 656, 2, 320, 400);
		worldContourLight = new TextureRegion(worldSelectSprites, 329, 2, 325, 405);
		worldContourDark = new TextureRegion(worldSelectSprites, 2, 2, 325, 405);
		backWorldSelectLight = new TextureRegion(worldSelectSprites, 1300, 145, 141, 141);
		backWorldSelectDark = new TextureRegion(worldSelectSprites, 1300, 2, 141, 141);
	}
	public static void reloadWorldSelect(){
		worldSelectSprites.reload();
	}
	public static void unloadWorldSelect(){
		worldSelectSprites.dispose();
	}
	public static void clearWorldSelect(){
		worldSelectSprites = null;
		worldOne = null;
		underConstruction = null;
		worldContourLight = null;
		worldContourDark = null;
		backWorldSelectLight = null;
		backWorldSelectDark = null;
	}


	////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////


	public static void loadWorldOneSelect(GLGame game){
		worldOneSelectBackgroundTexture = new Texture(game, "WorldOneSelectorBackground.png");
		worldOneSelectBackground = new TextureRegion(worldOneSelectBackgroundTexture, 0, 0, 1280, 800);

		worldOneSelectSprites = new Texture(game, "WorldOneSelectorSprites.png");
		backLevelSelectLight = new TextureRegion(worldOneSelectSprites, 663, 2, 141, 141);
		backLevelSelectDark = new TextureRegion(worldOneSelectSprites, 520, 2, 141, 141);
		levelSelectOne = new TextureRegion(worldOneSelectSprites, 955, 2, 34, 80);
		levelSelectTwo = new TextureRegion(worldOneSelectSprites, 903, 84, 46, 80);
		levelSelectThree = new TextureRegion(worldOneSelectSprites, 855, 84, 46, 82);
		levelSelectFour = new TextureRegion(worldOneSelectSprites, 855, 2, 49, 80);
		levelSelectFive = new TextureRegion(worldOneSelectSprites, 906, 2, 47, 80);
		levelSelectSix = new TextureRegion(worldOneSelectSprites, 806, 86, 47, 82);
		levelSelectSeven = new TextureRegion(worldOneSelectSprites, 951, 84, 38, 80);
		levelSelectEight = new TextureRegion(worldOneSelectSprites, 806, 2, 47, 82);
		levelSelectLevelIconLight = new TextureRegion(worldOneSelectSprites, 205, 2, 201, 201);
		levelSelectLevelIconDark = new TextureRegion(worldOneSelectSprites, 2, 2, 201, 201);
		levelSelectLock = new TextureRegion(worldOneSelectSprites, 408, 2, 110, 142);
		levelSelectOneStar = new TextureRegion(worldOneSelectSprites, 408, 146, 102, 52);
		levelSelectTwoStars = new TextureRegion(worldOneSelectSprites, 616, 145, 102, 52);
		levelSelectThreeStars = new TextureRegion(worldOneSelectSprites, 512, 146, 102, 52);
	}
	public static void reloadWorldOneSelect(){
		worldOneSelectBackgroundTexture.reload();
		worldOneSelectSprites.reload();
	}
	public static void unloadWorldOneSelect(){
		worldOneSelectBackgroundTexture.dispose();
		worldOneSelectSprites.dispose();
	}
	public static void clearWorldOneSelect(){
		worldOneSelectBackgroundTexture = null;
		worldOneSelectBackground = null;

		worldOneSelectSprites = null;
		backLevelSelectLight = null;
		backLevelSelectDark = null;
		levelSelectOne = null;
		levelSelectTwo = null;
		levelSelectThree = null;
		levelSelectFour = null;
		levelSelectFive = null;
		levelSelectSix = null;
		levelSelectSeven = null;
		levelSelectEight = null;
		levelSelectLevelIconLight = null;
		levelSelectLevelIconDark = null;
		levelSelectLock = null;
		levelSelectOneStar = null;
		levelSelectTwoStars = null;
		levelSelectThreeStars = null;
	}


	////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////


	public static void loadWorldOneLevels(GLGame game){
		loadPauseMenu(game);

		worldOneBackgroundSprite = new Texture(game, "WorldOneSelectorBackground.png");
		worldOneBackground = new TextureRegion(worldOneBackgroundSprite, 0, 0, 1280, 800);

		worldOnePlanetSpriteSheet = new Texture(game, "WorldOnePlanetSP.png");
		worldOnePlanet = new TextureRegion(worldOnePlanetSpriteSheet, 2,2,796,1043);
		worldOneWall = new TextureRegion(worldOnePlanetSpriteSheet, 2,1047,606,622);
	}
	public static void reloadWorldOneLevels(){
		reloadPauseMenu();
		worldOneBackgroundSprite.reload();
		worldOnePlanetSpriteSheet.reload();
	}
	public static void unloadWorldOneLevels(){
		unloadPauseMenu();
		worldOneBackgroundSprite.dispose();
		worldOnePlanetSpriteSheet.dispose();
	}
	public static void clearWorldOneLevels(){
		clearPauseMenu();
		worldOneBackgroundSprite = null;
		worldOneBackground = null;

		worldOnePlanetSpriteSheet = null;
		worldOnePlanet = null;
		worldOneWall = null;

	}


	////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////


	public static void loadPauseMenu(GLGame game){

		pauseMenuSpriteSheet = new Texture(game, "PauseMenuSpriteSheet.png");
		pauseMenuBackground = new TextureRegion(pauseMenuSpriteSheet, 2,2,1100,620);
		pauseMenuPauseButtonLight = new TextureRegion(pauseMenuSpriteSheet, 145,624,141,141);
		pauseMenuPauseButtonDark = new TextureRegion(pauseMenuSpriteSheet, 2,624,141,141);
		pauseMenuQuitButtonLight = new TextureRegion(pauseMenuSpriteSheet, 288,702,230,76);
		pauseMenuQuitButtonDark = new TextureRegion(pauseMenuSpriteSheet, 288,624,230,76);
		pauseMenuResumeButtonLight = new TextureRegion(pauseMenuSpriteSheet, 520,702,230,76);
		pauseMenuResumeButtonDark = new TextureRegion(pauseMenuSpriteSheet, 520,624,230,76);
		pauseMenuSoundsTitle = new TextureRegion(pauseMenuSpriteSheet, 752,674,158,64);
		pauseMenuMusicTitle = new TextureRegion(pauseMenuSpriteSheet, 912,660,128,64);
		pauseMenuSoundBarOutline = new TextureRegion(pauseMenuSpriteSheet, 752,624,327,16);
		pauseMenuSoundBarEnd = new TextureRegion(pauseMenuSpriteSheet, 1081,624,13,16);
		pauseMenuSoundBarFilling = new TextureRegion(pauseMenuSpriteSheet, 752,642,302,16);
		pauseMenuSoundBarNubLight = new TextureRegion(pauseMenuSpriteSheet, 752,740,30,30);
		pauseMenuSoundBarNubDark = new TextureRegion(pauseMenuSpriteSheet, 1056,642,30,30);
	}
	public static void reloadPauseMenu(){
		pauseMenuSpriteSheet.reload();
	}
	public static void unloadPauseMenu(){
		pauseMenuSpriteSheet.dispose();
	}
	public static void clearPauseMenu(){
		pauseMenuSpriteSheet = null;
		pauseMenuBackground = null;
		pauseMenuPauseButtonLight = null;
		pauseMenuPauseButtonDark = null;
		pauseMenuQuitButtonLight = null;
		pauseMenuQuitButtonDark = null;
		pauseMenuResumeButtonLight = null;
		pauseMenuResumeButtonDark = null;
		pauseMenuSoundsTitle = null;
		pauseMenuMusicTitle = null;
		pauseMenuSoundBarOutline = null;
		pauseMenuSoundBarEnd = null;
		pauseMenuSoundBarFilling = null;
		pauseMenuSoundBarNubLight = null;
		pauseMenuSoundBarNubDark = null;

	}


	////////////////////////////////////////////////////////////////////////////////////
}
