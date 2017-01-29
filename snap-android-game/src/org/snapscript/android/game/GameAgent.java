package org.snapscript.android.game;

import android.os.Handler;
import android.util.Log;

public class GameAgent implements Game {
   
   private static final String TAG = GameAgent.class.getSimpleName();

   private final GameController controller;
   private final GameActivity activity;
   private final FrameAdapter listener;
   private final FrameThread thread;
   private final Handler handler;
   private final Frame frame;
   
   public GameAgent(GameActivity activity){
      this.handler = new Handler(activity.getMainLooper());
      this.frame = new Frame(activity);
      this.thread = new FrameThread(frame.getHolder(), frame);
      this.controller = new GameController(frame);
      this.listener = new FrameAdapter(thread, frame);
      this.activity = activity;
   }

   @Override
   public void start(final Panel panel) {
      frame.onCreate(panel);
      handler.post(new Runnable() {
         @Override
         public void run() {
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
