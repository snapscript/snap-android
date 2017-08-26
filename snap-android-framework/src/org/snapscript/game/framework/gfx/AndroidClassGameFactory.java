package org.snapscript.game.framework.gfx;

import java.lang.reflect.Constructor;

import android.app.Activity;

public class AndroidClassGameFactory implements AndroidGameFactory {

   @Override
   public void createGame(AndroidActivity activity, AndroidGameLauncher launcher, float width, float height, boolean isLandscape) {
      try {
         String gameClass = AndroidConfiguration.getString(activity, "game_class");
         Class gameType = Class.forName(gameClass);
         Constructor gameFactory = gameType.getDeclaredConstructor(Activity.class, float.class, float.class, boolean.class);
         AndroidGame game = (AndroidGame)gameFactory.newInstance(activity, width, height, isLandscape);
         
         launcher.start(game);
      } catch(Exception e) {
         throw new IllegalStateException("Error creating game", e);
      }
   }

}
