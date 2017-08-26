package org.snapscript.game.framework;

import android.app.Activity;
import android.graphics.Bitmap;


public interface Game {
	public Input getInput();

	public FileIO getFileIO();

	public Graphics getGraphics();

	public Audio getAudio();
	
	public Activity getContext();
	
	public GameController getController();

	  /**
    * returns Buffer Bitmap of FastRendererView associated with game
    * @return
    */
   public Bitmap getBuffer();
   
	/**
	 * allows us to set the current screen of the game. These methods will be
	 * implemented once, along with all the internal thread creation, window
	 * management, and main loop logic that will constantly ask the current
	 * screen to present and update itself.
	 * 
	 * @param screen
	 */
	public void setScreen(Screen screen);

	/**
	 * allows us to set the current screen of the game. These methods will be
	 * implemented once, along with all the internal thread creation, window
	 * management, and main loop logic that will constantly ask the current
	 * screen to present and update itself.
	 * 
	 * @return
	 */
	public Screen getCurrentScreen();

	/**
	 * returning an instance to the first screen of our game.
	 * 
	 * @return
	 */
	public Screen getStartScreen();
	
	public int getScreenWidth();
	
	public int getScreenHeight();
}