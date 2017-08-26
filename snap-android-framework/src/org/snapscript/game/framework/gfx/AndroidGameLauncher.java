package org.snapscript.game.framework.gfx;

import org.snapscript.game.framework.gfx.AndroidActivity;
import org.snapscript.game.framework.gfx.AndroidGame;

import android.os.Handler;
import android.util.Log;

public class AndroidGameLauncher {

   private final AndroidActivity activity;
   private final Handler handler;
   
   public AndroidGameLauncher(AndroidActivity activity){
      this.handler = new Handler(activity.getMainLooper());
      this.activity = activity;
   }

   public void start(final AndroidGame game) {
      handler.post(new Runnable() {
         @Override
         public void run() {
            try {
               Log.i("launch", "Posting creation");
               game.onStart();
               activity.onCreate(game);
               Log.i("launch", "Finished posting");
            }catch(Exception e) {
               Log.e("launch", "Error launching game", e);
            }
         }
      });
   }
}
