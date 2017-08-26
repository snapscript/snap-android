package org.snapscript.game.framework.gfx;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.game.framework.Audio;
import org.snapscript.game.framework.FileIO;
import org.snapscript.game.framework.Game;
import org.snapscript.game.framework.GameController;
import org.snapscript.game.framework.Graphics;
import org.snapscript.game.framework.Input;
import org.snapscript.game.framework.Screen;
import org.snapscript.game.framework.fileio.AndroidFileIO;
import org.snapscript.game.framework.input.AndroidInput;
import org.snapscript.game.framework.sfx.AndroidAudio;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

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
public abstract class AndroidGame implements Game, GameController {
   protected AndroidFastRenderView renderView;
   protected AtomicBoolean start;
   protected Graphics graphics;
   protected Audio audio;
   protected Input input;
   protected FileIO fileIO;
   protected Screen screen;
   protected WakeLock wakeLock;
   protected Bitmap frameBuffer;
   protected Activity activity;
   protected int WIDTH;
   protected int HEIGHT = 240;
   protected float scaleX;
   protected float scaleY;

   public AndroidGame(Activity activity, float w, float h, boolean isLandscape) {
      this.activity = activity;
      Log.v("mario", " window width & height (in pixels): " + w + ", " + h);
      float aspectRaio = w / h;
      // adjust width according to the aspect ratio, using this we can deal with
      // any resolution.
      WIDTH = (int) (HEIGHT * aspectRaio);

      int frameBufferWidth = isLandscape ? WIDTH : HEIGHT;
      int frameBufferHeight = isLandscape ? HEIGHT : WIDTH;
      this.frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);
      // proceed by creating floats to scale and adjust everything to the
      // device's aspect ratio.
      this.scaleX = (float) frameBufferWidth / w;
      this.scaleY = (float) frameBufferHeight / h;

      this.start = new AtomicBoolean();
      this.fileIO = new AndroidFileIO(this);
      this.audio = new AndroidAudio(this);
      this.screen = getStartScreen();
      // we also use the PowerManager to define the wakeLock variable and we
      // acquire and
      // release wakelock in the onResume and onPause methods, respectively.
      PowerManager powerManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
      this.wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
   }

   public GameController getController() {
      return this;
   }

   public Activity getContext() {
      return activity;
   }
   
   public void onStart() {
      if(start.compareAndSet(false, true)) {
         renderView = new AndroidFastRenderView(this, frameBuffer);
         graphics = new AndroidGraphics(activity.getAssets(), frameBuffer);
         input = new AndroidInput(this, renderView, scaleX, scaleY);
         activity.setContentView(renderView);
      }
   }

   @Override
   public void onResume() {
      wakeLock.acquire();
      screen.resume();
      renderView.resume();
   }

   @Override
   public void onPause() {
      wakeLock.release();
      renderView.pause();
      screen.pause();
      if (activity.isFinishing())
         screen.dispose();
   }

   public Input getInput() {
      return input;
   }

   public FileIO getFileIO() {
      return fileIO;
   }

   public Graphics getGraphics() {
      return graphics;
   }

   public Audio getAudio() {
      return audio;
   }

   public void setScreen(Screen screen) {
      if (screen == null)
         throw new IllegalArgumentException("Screen must not be null");
      this.screen.pause();
      this.screen.dispose();
      screen.resume();
      screen.update(0);
      this.screen = screen;
   }

   public Screen getCurrentScreen() {
      return screen;
   }

   /**
    * returns Buffer Bitmap of FastRendererView associated with game
    * 
    * @return
    */
   public Bitmap getBuffer() {
      return frameBuffer;
   }

   public int getScreenWidth() {
      return WIDTH;
   }

   public int getScreenHeight() {
      return HEIGHT;
   }
}