package org.snapscript.android.game;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class Game extends Activity {
    private static final String TAG = Game.class.getSimpleName();

    private Agent agent;
    private Frame frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
//        final AgentListener listener = new AgentListener(this);
//        new Thread(new Runnable() {
//           public void run(){
//              try {
//                 Log.i(TAG, "Waiting for 5000");
//                 Thread.sleep(5000); // 5 second start
//                 Panel panel = new GamePanel(Game.this);
//                 listener.onStart(panel);
//              } catch(Exception e){
//                 Log.e(TAG, "Could not createm panel", e);
//              }
//           }
//        }).start();
        agent = new Agent(this);
        agent.start();

        Log.d(TAG, "View added");
    }
    
    public void onStart(Frame frame) {
       Log.d(TAG, "Started with " + frame);
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
