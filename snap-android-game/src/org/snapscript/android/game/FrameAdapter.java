package org.snapscript.android.game;

import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

class FrameAdapter implements Callback {
   
   private final FrameThread thread;
   private final Frame frame;
   
   public FrameAdapter(FrameThread thread, Frame frame) {
      this.thread = thread;
      this.frame = frame;
   }
   
   @Override
   public void surfaceCreated(SurfaceHolder holder) {
      frame.onStart(frame);
      thread.start();
   }

   @Override
   public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      frame.onChanged(frame, format, width, height);
   }

   @Override
   public void surfaceDestroyed(SurfaceHolder holder) {
      frame.onDestroyed(frame);
      thread.stop();
   }
}