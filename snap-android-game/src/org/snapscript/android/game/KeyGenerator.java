package org.snapscript.android.game;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.os.Build;

public class KeyGenerator {
   
   private static final String DATE_FORMAT = "ddHHmmss";
   
   private final DateFormat format;
   
   public KeyGenerator(){
      this.format = new SimpleDateFormat(DATE_FORMAT);
   }

   public synchronized String getDeviceKey() {
      long time = System.currentTimeMillis();
      String version = Build.VERSION.RELEASE.replace(".", "");
      String date = format.format(time);
      
      return String.format("%s%s", version, date); // this key is rubbish
   }
}
