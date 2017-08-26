package org.snapscript.game.framework;

import android.app.Dialog;

public interface GameController {
   public void onBackPressed();
   public void onResume();
   public void onPause();
   public Dialog onCreateDialog(int id);
}
