package org.snapscript.game.framework.gfx;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.snapscript.android.game.R;

import android.os.StrictMode;
import android.util.Log;

public class AndroidGameBuilder {
   
   private final AndroidGameLauncher launcher;
   private final AndroidActivity activity;
   private final Executor executor;
   private final float width;
   private final float height;
   private final boolean isLandscape;
   
   public AndroidGameBuilder(AndroidActivity activity, float width, float height, boolean isLandscape){
      this.executor = new ScheduledThreadPoolExecutor(1);
      this.launcher = new AndroidGameLauncher(activity);
      this.activity = activity;
      this.width = width;
      this.height = height;
      this.isLandscape = isLandscape;
   }

   public void createGame() {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      
      StrictMode.setThreadPolicy(policy);
      executor.execute(new Runnable() {
         @Override
         public void run() {
            String factoryClass = activity.getResources().getString(R.string.factory_class);
            
            try {
               Class factoryType = Class.forName(factoryClass);
               AndroidGameFactory gameFactory = (AndroidGameFactory)factoryType.newInstance();
               
               gameFactory.createGame(activity, launcher, width, height, isLandscape);
            } catch(Exception e) {
               Log.e("build", "Could not create game from ", e);
            }
         }
      });
   }
}
