package org.snapscript.game.framework.script;

import java.net.URI;

import org.snapscript.game.framework.KeyGenerator;
import org.snapscript.game.framework.gfx.AndroidConfiguration;

import android.content.Context;
import android.os.Build;

public class AndroidScriptConfiguration {

   private static final String PROCESS_TEMPLATE = "android-%s";
   private static final String ADDRESS_TEMPLATE = "http://%s:%s/resource";
   private static final String SYSTEM_TEMPLATE = "Android %s";

   private final KeyGenerator generator;
   private final Context context;

   public AndroidScriptConfiguration(Context context) {
      this.generator = new KeyGenerator();
      this.context = context;
   }

   public String getSystemName() {
      return String.format(SYSTEM_TEMPLATE, Build.VERSION.RELEASE);
   }

   public String getProcessName() {
      return String.format(PROCESS_TEMPLATE, generator.getDeviceKey());
   }

   public URI getRemoteAddress() {
      return URI.create(String.format(ADDRESS_TEMPLATE, getRemoteHost(), getRemotePort()));
   }

   public String getRemoteHost() {
      return AndroidConfiguration.getString(context, "remote_host");
   }

   public int getRemotePort() {
      return AndroidConfiguration.getInteger(context, "remote_port");
   }

   public String getLogLevel() {
      return AndroidConfiguration.getString(context, "log_level");
   }

   public String getContextName() {
      return AndroidConfiguration.getString(context, "variable_context");
   }

   public String getLauncherName() {
      return AndroidConfiguration.getString(context, "variable_launcher");
   }
   
   public String getWidthName() {
      return AndroidConfiguration.getString(context, "variable_width");
   }
   
   public String getHeightName() {
      return AndroidConfiguration.getString(context, "variable_height");
   }
   
   public String getLandscapeName() {
      return AndroidConfiguration.getString(context, "variable_landscape");
   }

   public int getThreadCount() {
      return AndroidConfiguration.getInteger(context, "thread_count");
   }

   public int getStackSize() {
      return AndroidConfiguration.getInteger(context, "stack_size");
   }
}

