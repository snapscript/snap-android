package org.snapscript.android.game;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

public class GameAgent implements Game {
   
   private static final String TAG = GameAgent.class.getSimpleName();

   private final BlockingQueue<Panel> queue;
   private final GameController controller;
   private final FrameAdapter listener;
   private final FrameThread thread;
   private final Activity activity;
   private final Handler handler;
   private final Frame frame;
   
   public GameAgent(Activity activity){
      this.handler = new Handler(activity.getMainLooper());
      this.queue = new SynchronousQueue<Panel>();
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
            frame.getHolder().addCallback(listener);
            frame.setOnTouchListener(controller);
            frame.setFocusable(true);
            Log.i(TAG, "Finished posting");

         }
      });
   }
}
