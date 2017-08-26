package org.snapscript.game.framework.gfx;

import android.content.Context;

public class AndroidConfiguration {
   
   private static final String RESOURCE_TYPE = "string";

   public static String getString(Context context, String name) {
      String packageName = context.getPackageName();
      int resId = context.getResources().getIdentifier(name, RESOURCE_TYPE, packageName);
      return context.getResources().getString(resId);
   }
   
   public static int getInteger(Context context, String name) {
      String value = getString(context, name);
      return Integer.parseInt(value);
   }
}
