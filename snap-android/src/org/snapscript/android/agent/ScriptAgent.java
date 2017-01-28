package org.snapscript.android.agent;

import java.net.URI;

import org.snapscript.agent.ProcessAgent;
import org.snapscript.core.EmptyModel;
import org.snapscript.core.Model;

import android.app.Activity;
import android.os.Bundle;

public class ScriptAgent extends Activity {

   public static final String REMOTE_HOST = "192.168.1.66";
   public static final String LOG_LEVEL = "DEBUG";
   public static final int HTTP_PORT = 4457;
   public static final int EVENT_PORT = 4456;
   public static final int THREAD_COUNT = 4;
   public static final int STACK_SIZE = 2000000;
   
   @Override
   public void onCreate(Bundle bundle) {
      super.onCreate(bundle);
      try {
         Model model = new EmptyModel();
         ProcessAgent agent = new ProcessAgent(
               URI.create("http://"+REMOTE_HOST+":"+HTTP_PORT+"/resource"), 
               "android-" + System.currentTimeMillis(), 
               LOG_LEVEL,
               EVENT_PORT, 
               THREAD_COUNT,
               STACK_SIZE);
         agent.start(model);
      } catch (Exception e) {
         e.printStackTrace();
      }

   }
}
