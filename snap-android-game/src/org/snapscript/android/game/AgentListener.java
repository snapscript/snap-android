package org.snapscript.android.game;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class AgentListener {
   
   private static final String TAG = AgentListener.class.getSimpleName();

   private final BlockingQueue<Panel> queue;
   private final GameController controller;
   private final FrameListener listener;
   private final FrameThread thread;
   private final Handler handler;
   private final Frame frame;
   
   public AgentListener(Context context){
      this.handler = new Handler(context.getMainLooper()); 
      this.queue = new SynchronousQueue<Panel>();
      this.frame = new Frame(context);
      this.thread = new FrameThread(frame.getHolder(), frame);
      this.controller = new GameController(frame);
      this.listener = new FrameListener(thread, frame);
   }
   
   public void onStart(final Panel panel) {
      try {
         queue.offer(panel);
      } catch(Exception e) {
         Log.e(TAG, "Failed to start", e);
      }
   }
   
   public void onStart(final Activity activity) {
      try {
         Panel panel = queue.take();
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
      } catch(Exception e) {
         Log.e(TAG, "Failed to start", e);
      }
   }
}
