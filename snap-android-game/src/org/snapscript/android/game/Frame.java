package org.snapscript.android.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class Frame extends SurfaceView implements Panel {

   private Panel panel;

   public Frame(Context context) {
      super(context);
   }

   public void onCreate(Panel panel) {
      this.panel = panel;
   }

   @Override
   public void onPause(Frame frame) {
      if (panel != null) {
         panel.onPause(frame);

      }
   }

   @Override
   public void onStart(Frame frame) {
      if (panel != null) {
         panel.onStart(frame);

      }
   }

   @Override
   public void onUpdate(Frame frame) {
      if (panel != null) {
         panel.onUpdate(frame);
      }
   }

   @Override
   public void onRender(Frame frame, Canvas canvas) {
      if (panel != null) {
         panel.onRender(frame, canvas);
      }
   }

   @Override
   public void onChanged(Frame frame, int format, int width, int height) {
      if (panel != null) {
         panel.onChanged(frame, format, width, height);

      }
   }

   @Override
   public void onDestroyed(Frame frame) {
      if (panel != null) {
         panel.onDestroyed(frame);

      }
   }

   @Override
   public void onClick(Frame frame, int x, int y) {
      if (panel != null) {
         panel.onClick(frame, x, y);
      }
   }

   @Override
   public void onRightToLeftSwipe(Frame frame) {
      if (panel != null) {
         panel.onRightToLeftSwipe(frame);
      }
   }

   @Override
   public void onLeftToRightSwipe(Frame frame) {
      if (panel != null) {
         panel.onLeftToRightSwipe(frame);
      }
   }

   @Override
   public void onTopToBottomSwipe(Frame frame) {
      if (panel != null) {
         panel.onTopToBottomSwipe(frame);
      }
   }

   @Override
   public void onBottomToTopSwipe(Frame frame) {
      if (panel != null) {
         panel.onBottomToTopSwipe(frame);
      }
   }
}
