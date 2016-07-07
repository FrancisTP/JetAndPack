package com.tp.framework.impl;

import com.tp.framework.Game;
import com.tp.framework.Screen;

public abstract class GLScreen extends Screen {
	protected final GLGraphics glGraphics;
	protected final GLGame glGame;
	
	public GLScreen(Game game){
		super(game);
		glGame = (GLGame)game;
		glGraphics = glGame.getGLGraphics();
	}
}
