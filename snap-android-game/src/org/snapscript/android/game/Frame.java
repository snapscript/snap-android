package org.snapscript.android.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class Frame extends SurfaceView {

   private final Panel panel;
   private final int rate;

   public Frame(Context context, Panel panel, int rate) {
      super(context);
      this.panel = panel;
      this.rate = rate;
   }

   public void onPause(Frame frame) {
      panel.onPause(frame);
   }

   public void onStart(Frame frame) {
      panel.onStart(frame, rate);
   }

   public void onUpdate(Frame frame, int rate) {
      panel.onUpdate(frame, rate);
   }

   public void onRender(Frame frame, Canvas canvas) {
      panel.onRender(frame, canvas);
   }

   public void onChanged(Frame frame, int format, int width, int height) {
      panel.onChanged(frame, format, width, height);
   }

   public void onDestroyed(Frame frame) {
      panel.onDestroyed(frame);
   }

   public void onClick(Frame frame, int x, int y) {
      panel.onClick(frame, x, y);
   }

   public void onRightToLeftSwipe(Frame frame) {
      panel.onRightToLeftSwipe(frame);
   }

   public void onLeftToRightSwipe(Frame frame) {
      panel.onLeftToRightSwipe(frame);
   }

   public void onTopToBottomSwipe(Frame frame) {
      panel.onTopToBottomSwipe(frame);
   }

   public void onBottomToTopSwipe(Frame frame) {
      panel.onBottomToTopSwipe(frame);
   }
}
