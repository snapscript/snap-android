package org.snapscript.game.framework.gfx;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import org.snapscript.dx.stock.ProxyAdapter;
import org.snapscript.dx.stock.ProxyBuilder;
import org.snapscript.game.framework.Game;
import org.snapscript.game.framework.GameListener;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * 
 * <li>Perform window management. In our context, this means setting up an
 * activity and an AndroidFastRenderView, and handling the activity life cycle
 * in a clean way.</li> <li>Use and manage a WakeLock so that the screen does
 * not dim. Instantiate and hand out references to Graphics, Audio, FileIO, and
 * Input to interested parties.</li> <li>Manage Screens and integrate them with
 * the activity life cycle.</li>
 * 
 * @author mahesh
 * 
 */
public class AndroidActivity extends Activity implements GameListener {
   
   private enum ActivityState {
      NONE,
      CREATED,
      RESUMED,
      PAUSED
   }
   
   private ActivityState state = ActivityState.NONE;
	private Game game;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//check the current orientation of the device and set the Width and Height of our Game. 
		boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		float h=metrics.heightPixels;
		float w=metrics.widthPixels;
		Log.v("game"," window width & height (in pixels): " + w + ", " + h);
		
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
      Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
		  @Override
		  public void uncaughtException(Thread t, Throwable e) {
			  e.printStackTrace();
		  }
	  });
		AndroidGameBuilder factory = new AndroidGameBuilder(this, w, h, isLandscape);
		factory.createGame();
      state = ActivityState.CREATED;
	}

	@Override
	public void onCreate(Game game){
	   this.game = game;
	   if(state == ActivityState.RESUMED) {
	      game.getController().onResume();
	   }
	}

	@Override
	public void onResume() {
		super.onResume();
		state = ActivityState.RESUMED;
		if(game != null) {
		   game.getController().onResume();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	    state = ActivityState.PAUSED;
      if(game != null) {
         game.getController().onPause();
      }
	}
}