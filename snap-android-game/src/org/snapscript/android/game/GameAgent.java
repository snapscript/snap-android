package org.snapscript.android.game;

import android.os.Handler;
import android.util.Log;

public class GameAgent implements Game {
   
   private static final String TAG = GameAgent.class.getSimpleName();

   private final GameActivity activity;
   private final Handler handler;
   
   public GameAgent(GameActivity activity){
      this.handler = new Handler(activity.getMainLooper());
      this.activity = activity;
   }

   @Override
   public void start(final Panel panel) {
      start(panel, 20);
   }
   
   @Override
   public void start(final Panel panel, final int rate) {
      handler.post(new Runnable() {
         @Override
         public void run() {
            Frame frame = new Frame(activity, panel, rate);
            FrameThread thread = new FrameThread(frame.getHolder(), frame, rate);
            GameController controller = new GameController(frame);
            FrameManager listener = new FrameManager(thread, frame);
            
            Log.i(TAG, "Posting creation");
            activity.setContentView(frame);
            activity.onStart(frame);
            frame.getHolder().addCallback(listener);
            frame.setOnTouchListener(controller);
            frame.setFocusable(true);
            Log.i(TAG, "Finished posting");

         }
      });
   }
}
