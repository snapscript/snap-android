package org.snapscript.android.game;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.agent.ProcessAgent;
import org.snapscript.common.ThreadPool;
import org.snapscript.core.MapModel;
import org.snapscript.core.Model;

import android.app.Activity;
import android.os.StrictMode;
import android.util.Log;

public class Agent {

   private static final String TAG = Agent.class.getSimpleName();
   
   private static final String REMOTE_HOST = "192.168.1.66";
   private static final String LOG_LEVEL = "DEBUG";
   private static final String CONTEXT_NAME = "context";
   private static final String AGENT_NAME = "agent";
   private static final int HTTP_PORT = 4457;
   private static final int EVENT_PORT = 4456;
   private static final int THREAD_COUNT = 4;
   private static final int STACK_SIZE = 2000000;
   
   private final AgentListener listener;
   private final AtomicBoolean active;
   private final Activity activity;
   private final Executor executor;
   
   public Agent(Activity activity) {
      this.executor = new ThreadPool(1);
      this.listener = new AgentListener(activity);
      this.active = new AtomicBoolean();
      this.activity = activity;
   }
   
   public void start() {
      try {
         if(active.compareAndSet(false, true)) {
            final StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            final Map<String, Object> map = new HashMap<String, Object>();
            final Model model = new MapModel(map);
            final ProcessAgent agent = new ProcessAgent(
                  URI.create("http://"+REMOTE_HOST+":"+HTTP_PORT+"/resource"), 
                  "android-" + System.currentTimeMillis(), 
                  LOG_LEVEL,
                  EVENT_PORT, 
                  THREAD_COUNT,
                  STACK_SIZE);
            
            StrictMode.setThreadPolicy(policy); 
            map.put(CONTEXT_NAME, activity);
            map.put(AGENT_NAME, listener);
            executor.execute(new Runnable() {
               @Override
               public void run() {
                  try {
                     agent.start(model);
                     listener.onStart(activity);
                  }catch(Exception e) {
                     Log.e(TAG, "Error starting agent", e);
                  }
               }
            });
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

   }
}
