package org.snapscript.game.framework.script;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.snapscript.core.scope.MapModel;
import org.snapscript.core.scope.Model;
import org.snapscript.game.framework.gfx.AndroidActivity;
import org.snapscript.game.framework.gfx.AndroidGameFactory;
import org.snapscript.game.framework.gfx.AndroidGameLauncher;
import org.snapscript.studio.agent.ProcessAgent;
import org.snapscript.studio.agent.ProcessContext;
import org.snapscript.studio.agent.ProcessMode;
import org.snapscript.studio.agent.ProcessStore;
import org.snapscript.studio.agent.worker.store.WorkerStore;

import android.util.Log;

public class AndroidScriptGameFactory implements AndroidGameFactory {
   
   private static final String TAG = AndroidScriptGameFactory.class.getSimpleName();

   @Override
   public void createGame(AndroidActivity activity, AndroidGameLauncher launcher, float width, float height, boolean isLandscape) {
      try {
         final AndroidScriptConfiguration configuration = new AndroidScriptConfiguration(activity);
         final Map<String, Object> map = new HashMap<String, Object>();
         final Model model = new MapModel(map);
         final AndroidScriptLog log = new AndroidScriptLog(TAG);
         final ProcessStore store = new WorkerStore(configuration.getRemoteAddress());
         final ProcessContext context = new ProcessContext(
               ProcessMode.SERVICE,
               store,
               configuration.getProcessName(),
               configuration.getSystemName(),
               configuration.getThreadCount(),
               configuration.getStackSize());
         final ProcessAgent agent = new ProcessAgent(
                 context,
                 configuration.getLogLevel());
         final Runnable task = new Runnable() {
            @Override
            public void run(){
               Log.i(TAG, "Finished");
            }
         };
         URI root = URI.create("http://" + configuration.getRemoteHost() + ":" + configuration.getRemotePort());
         
         map.put(configuration.getContextName(), activity);
         map.put(configuration.getLauncherName(), launcher);
         map.put(configuration.getWidthName(), width);
         map.put(configuration.getHeightName(), height);
         map.put(configuration.getLandscapeName(), isLandscape);
         agent.start(root, task, model, log);
      }catch(Exception e){
         throw new IllegalStateException("Error creating game", e);
      }
   }
   

}
