package org.snapscript.android.game;

import java.util.concurrent.atomic.AtomicBoolean;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * The Main thread which contains the game loop. The thread must have access to 
 * the surface view and holder to trigger events every game tick.
 */
public class FrameThread implements Runnable {

   private static final String TAG = FrameThread.class.getSimpleName();

   // maximum number of frames to be skipped
   private final static int MAX_FRAME_SKIPS = 10;

   private final SurfaceHolder surfaceHolder;
   private final SampleAverager averager;
   private final AtomicBoolean active;
   private final Frame frame;
   private final Thread thread;
   private final int framePeriod;
   private final int frameRate;

   // Flag to hold game state
   private static boolean running;

   public static void setRunning(boolean running) {
      FrameThread.running = running;
   }

   public FrameThread(SurfaceHolder surfaceHolder, Frame frame, int frameRate) {
      this.averager = new SampleAverager();
      this.active = new AtomicBoolean(false);
      this.thread = new Thread(this);
      this.surfaceHolder = surfaceHolder;
      this.framePeriod = 1000 / frameRate;
      this.frameRate = frameRate;
      this.frame = frame;
      running = true;
   }

   public void start() {
      try {
         if (active.compareAndSet(false, true)) {
            thread.start();
         }
      } catch (Exception e) {
         Log.e(TAG, "Error starting", e);
      }
   }

   public void stop() {
      try {
         if (active.compareAndSet(true, false)) {
            Thread.interrupted();
            thread.join();
         }
      } catch (Exception e) {
         Log.e(TAG, "Error stopping", e);
      }
   }

   @Override
   public void run() {
      Canvas canvas = null;

      averager.sample(1000 / frameRate); // set an initial average

      while (running) {
         long beginTime = System.currentTimeMillis();
         float averageTime = averager.average();
         int averageRate = Math.round(1000 / averageTime);

         // try locking the canvas for exclusive pixel editing in the surface
         try {
            averageRate = averageRate == 0 ? frameRate : averageRate; // make sure its not zero
            canvas = surfaceHolder.lockCanvas();

            synchronized (surfaceHolder) {
               int framesSkipped = 0; // reset the frames skipped

               // update game state
               frame.onUpdate(frame, averageRate); 
               frame.onRender(frame, canvas); // render state to the screen: draws the canvas on the panel

               long refreshTime = System.currentTimeMillis() - beginTime; // calculate how long did the cycle take
               long sleepTime = framePeriod - refreshTime; // calculate sleep time

               if (sleepTime > 0) {
                  // if sleepTime > 0 we're OK
                  try {
                     // send the thread to sleep for a short period: very useful
                     // for battery saving
                     Thread.sleep(sleepTime);
                  } catch (InterruptedException e) {
                     Log.e(TAG, "Frame thread interrupted", e);
                  }
               }
               while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) { // we need to catch up
                  frame.onUpdate(frame, averageRate); // update without rendering
                  sleepTime += framePeriod; // add frame period to check if in next frame
                  framesSkipped++;
               }
               long totalTime = System.currentTimeMillis() - beginTime;
               
               if (framesSkipped > 0) {
                  Log.v(TAG, "Skipped " + framesSkipped + " frames");
               }
               averager.sample(totalTime);

            }
         } finally {
            // in case of an exception the surface is not left in an
            // inconsistent state
            if (canvas != null) {
               surfaceHolder.unlockCanvasAndPost(canvas);
            }
         }
      }
   }
}
