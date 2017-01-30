package org.snapscript.android.game;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class GameActivity extends Activity {

    private static final String TAG = GameActivity.class.getSimpleName();

    private Frame frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Agent agent = new Agent(this);
        LinearLayout layout = new LinearLayout(this);
        ProgressBar pb = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        pb.setLayoutParams(params);
        layout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        layout.setBackgroundColor(Color.BLACK);
        layout.addView(pb);
        setContentView(layout);
        agent.start();

        Log.d(TAG, "View added");
    }
    
    public void onStart(Frame frame) {
       Log.d(TAG, "Started...");
       this.frame = frame;
    }

    @Override
    public void onRestart() {
        Log.d(TAG, "Restarting...");

        if (frame != null) {
           frame.onStart(frame);
        }
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "Pausing...");
        if (frame != null) {
           frame.onPause(frame);
        }
        super.onPause();
    }
}
