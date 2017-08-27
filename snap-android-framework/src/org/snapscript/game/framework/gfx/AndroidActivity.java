package org.snapscript.game.framework.gfx;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snapscript.dx.stock.MethodAdapter;
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
      
      try {
         final List l = new ArrayList();
         l.add(0);
         l.add(1);
         l.add(2);
         l.add(3);
         l.add(4);
         l.add(5);
          final Map m = new HashMap();
          m.put("a", "A");
          m.put("b", "B");
          m.put("c", "C");
          m.put("d", "D");
          m.put("e", "E");
          m.put("f", "F");
         final Method method =List.class.getDeclaredMethod("get", int.class);
         final Method method2 =Math.class.getDeclaredMethod("max", int.class, int.class);
         final Method method3 =Map.class.getDeclaredMethod("get", Object.class);
         final Method method4 =Class.class.getDeclaredMethod("forName", String.class);
         final ProxyBuilder builder = ProxyBuilder.forClass(MethodAdapter.class).parentClassLoader(AndroidActivity.class.getClassLoader());
         final Class accessorClass = builder.buildMethodAccessor(method);
         final Class accessorClass2 = builder.buildMethodAccessor(method2);
         final Class accessorClass3 = builder.buildMethodAccessor(method3);
         final Class accessorClass4 = builder.buildMethodAccessor(method4);
         final MethodAdapter accessor = (MethodAdapter)accessorClass.newInstance();
         final MethodAdapter accessor2 = (MethodAdapter)accessorClass2.newInstance();
         final MethodAdapter accessor3 = (MethodAdapter)accessorClass3.newInstance();
         final MethodAdapter accessor4 = (MethodAdapter)accessorClass4.newInstance();
          timeIt("normal (1000) List.get(int)", new Runnable() {
            public void run(){
                for(int i = 0; i < 1000; i++) {
                    Object val = new Object[]{2};
                    l.get(2);
                }
            }
         });
         
         timeIt("reflect (1000) List.get(int)", new Runnable() {
            public void run(){
               try {
                   for(int i = 0; i < 1000; i++) {
                       method.invoke(l, 2);
                   }
               }catch(Exception e){
                  e.printStackTrace();
               }
            }
         });
          timeIt("accessor (1000) List.get(int)", new Runnable() {
              public void run(){
                  try {
                      for(int i = 0; i < 1000; i++) {
                          accessor.invoke(l, 2);
                      }
                  }catch(Exception e){
                      e.printStackTrace();
                  }
              }
          });

          System.err.println("------------------------------------------------------------");


         timeIt("normal (1000000) List.get(int)", new Runnable() {
             public void run() {
                 for(int i = 0; i < 1000000; i++) {
                     Object val = new Object[]{2};
                     l.get(2);
                 }
             }
         });
         
         timeIt("reflect (1000000) List.get(int)", new Runnable() {
            public void run(){
               try {
                   for(int i = 0; i < 1000000; i++) {
                       method.invoke(l, 2);
                   }
               }catch(Exception e){
                  e.printStackTrace();
               }
            }
         });
          timeIt("accessor (1000000) List.get(int)", new Runnable() {
              public void run() {
                  try {
                      for(int i = 0; i < 1000000; i++) {
                          accessor.invoke(l, 2);
                      }
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
          });

          System.err.println("------------------------------------------------------------");

          timeIt("normal (1000000) Math.max(int, int)", new Runnable() {
              public void run() {
                  for(int i = 0; i < 1000000; i++) {
                      Object val = new Object[]{i,i+1};
                      Math.max(i, i+1);
                  }
              }
          });

          timeIt("reflect (1000000) Math.max(int, int)", new Runnable() {
              public void run(){
                  try {
                      for(int i = 0; i < 1000000; i++) {
                          method2.invoke(null, i, i+1);
                      }
                  }catch(Exception e){
                      e.printStackTrace();
                  }
              }
          });
          timeIt("accessor (1000000) Math.max(int, int)", new Runnable() {
              public void run() {
                  try {
                      for(int i = 0; i < 1000000; i++) {
                          accessor2.invoke(null, i, i+1);
                      }
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
          });

          System.err.println("------------------------------------------------------------");

          timeIt("normal (1000000) Map.get(Object)", new Runnable() {
              public void run() {
                  for(int i = 0; i < 1000000; i++) {
                      Object val = new Object[]{"a"};
                      m.get("a");
                  }
              }
          });

          timeIt("reflect (1000000) Map.get(Object)", new Runnable() {
              public void run(){
                  try {
                      for(int i = 0; i < 1000000; i++) {
                          method3.invoke(m, "a");
                      }
                  }catch(Exception e){
                      e.printStackTrace();
                  }
              }
          });
          timeIt("accessor (1000000) Map.get(Object)", new Runnable() {
              public void run() {
                  try {
                      for(int i = 0; i < 1000000; i++) {
                          accessor3.invoke(m, "a");
                      }
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
          });

          System.err.println("------------------------------------------------------------");

          timeIt("normal (100000) Class.forName(String)", new Runnable() {
              public void run() {
                  for(int i = 0; i < 100000; i++) {
                      try {
                          Object val = new Object[]{"java.lang.String"};
                          Class.forName("java.lang.String");
                      }catch(Exception e){
                          e.printStackTrace();
                      }
                  }
              }
          });

          timeIt("reflect (100000) Class.forName(String)", new Runnable() {
              public void run(){
                  try {
                      for(int i = 0; i < 100000; i++) {
                          method4.invoke(null, "java.lang.String");
                      }
                  }catch(Exception e){
                      e.printStackTrace();
                  }
              }
          });
          timeIt("accessor (100000) Class.forName(String)", new Runnable() {
              public void run() {
                  try {
                      for(int i = 0; i < 100000; i++) {
                          accessor4.invoke(null, "java.lang.String");
                      }
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
          });

          System.err.println("------------------------------------------------------------");

      } catch(Exception e){
         e.printStackTrace();
      }
      try {
         Thread.sleep(10000);
      }catch(Exception e){}
		AndroidGameBuilder factory = new AndroidGameBuilder(this, w, h, isLandscape);
		factory.createGame();
      state = ActivityState.CREATED;
	}
	
	public static void timeIt(String msg, Runnable r) {
	   long s = System.currentTimeMillis();
	   r.run();
	   long f = System.currentTimeMillis();
	   System.err.println("################################## "+msg +": "+ (f-s));
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