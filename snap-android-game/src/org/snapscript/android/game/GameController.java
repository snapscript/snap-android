package org.snapscript.android.game;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameController implements View.OnTouchListener {
    
   private static final String TAG = Game.class.getSimpleName();
    
    private static final int MIN_SWIPE_DISTANCE = 100;
    private static final int MAX_CLICK_TOLERANCE = 50;
    
    private Frame frame;
    private float downX;
    private float downY;

    public GameController(Frame frame) {
        this.frame = frame;
    }

    public void onRightToLeftSwipe(View v) {
        Log.v(TAG, "RightToLeftSwipe!");
        frame.onRightToLeftSwipe(frame);
    }

    public void onLeftToRightSwipe(View v) {
        Log.v(TAG, "LeftToRightSwipe!");
        frame.onLeftToRightSwipe(frame);
    }

    public void onTopToBottomSwipe(View v) {
        Log.v(TAG, "onTopToBottomSwipe!");
        frame.onTopToBottomSwipe(frame);
    }

    public void onBottomToTopSwipe(View v) {
        Log.v(TAG, "onBottomToTopSwipe!");
        frame.onBottomToTopSwipe(frame);
    }

    private void onClick(View v, int x, int y) {
        Log.v(TAG, "onClick!");
        frame.onClick(frame, x, y);
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                return true;
            case MotionEvent.ACTION_UP: {
                float upX = event.getX();
                float upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // click?
                if (deltaX <= MAX_CLICK_TOLERANCE && deltaY <= MAX_CLICK_TOLERANCE) {
                    this.onClick(v, (int) upX, (int) upY);
                    return true;
                }

                // swipe horizontal?
                if (Math.abs(deltaX) > MIN_SWIPE_DISTANCE) {
                    // left or right
                    if (deltaX < 0) {
                        this.onLeftToRightSwipe(v);
                        return true;
                    }
                    if (deltaX > 0) {
                        this.onRightToLeftSwipe(v);
                        return true;
                    }
                } else {
                    Log.i(TAG, "Horizontal swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_SWIPE_DISTANCE);
                }

                // swipe vertical?
                if (Math.abs(deltaY) > MIN_SWIPE_DISTANCE) {
                    // top or down
                    if (deltaY < 0) {
                        this.onTopToBottomSwipe(v);
                        return true;
                    }
                    if (deltaY > 0) {
                        this.onBottomToTopSwipe(v);
                        return true;
                    }
                } else {
                    Log.i(TAG, "Vertical swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_SWIPE_DISTANCE);
                }
            }
        }

        return false;
    }
}
