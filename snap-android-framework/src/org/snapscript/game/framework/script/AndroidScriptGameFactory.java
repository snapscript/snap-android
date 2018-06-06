package org.snapscript.game.framework.script;

import static org.snapscript.studio.agent.ProcessMode.SERVICE;

import java.util.HashMap;
import java.util.Map;

import org.snapscript.core.scope.MapModel;
import org.snapscript.core.scope.Model;
import org.snapscript.game.framework.gfx.AndroidActivity;
import org.snapscript.game.framework.gfx.AndroidGameFactory;
import org.snapscript.game.framework.gfx.AndroidGameLauncher;
import org.snapscript.studio.agent.ProcessAgent;

public class AndroidScriptGameFactory implements AndroidGameFactory {

   @Override
   public void createGame(AndroidActivity activity, AndroidGameLauncher launcher, float width, float height, boolean isLandscape) {
      try {
         final AndroidScriptConfiguration configuration = new AndroidScriptConfiguration(activity);
         final Map<String, Object> map = new HashMap<String, Object>();
         final Model model = new MapModel(map);
         final ProcessAgent agent = new ProcessAgent(
                 SERVICE,
                 configuration.getRemoteAddress(),
                 configuration.getSystemName(),
                 configuration.getProcessName(),
                 configuration.getLogLevel(),
                 configuration.getThreadCount(),
                 configuration.getStackSize());
         
         map.put(configuration.getContextName(), activity);
         map.put(configuration.getLauncherName(), launcher);
         map.put(configuration.getWidthName(), width);
         map.put(configuration.getHeightName(), height);
         map.put(configuration.getLandscapeName(), isLandscape);
         agent.start(model);
      }catch(Exception e){
         throw new IllegalStateException("Error creating game", e);
      }
   }
   

}
