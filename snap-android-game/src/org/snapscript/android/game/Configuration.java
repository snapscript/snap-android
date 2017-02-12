package org.snapscript.android.game;

import java.net.URI;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

public class Configuration {

   private static final String PROCESS_TEMPLATE = "android-%s";
   private static final String ADDRESS_TEMPLATE = "http://%s:%s/resource";
   private static final String SYSTEM_TEMPLATE = "Android %s";

   private final KeyGenerator generator;
   private final Resources resources;

   public Configuration(Context context) {
      this.generator = new KeyGenerator();
      this.resources = context.getResources();
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
      return resources.getString(R.string.remote_host);
   }

   public int getRemotePort() {
      return Integer.parseInt(resources.getString(R.string.remote_port));
   }

   public String getLogLevel() {
      return resources.getString(R.string.log_level);
   }

   public String getContextName() {
      return resources.getString(R.string.context_name);
   }

   public String getGameName() {
      return resources.getString(R.string.game_name);
   }

   public int getThreadCount() {
      return Integer.parseInt(resources.getString(R.string.thread_count));
   }

   public int getStackSize() {
      return Integer.parseInt(resources.getString(R.string.stack_size));
   }
}
