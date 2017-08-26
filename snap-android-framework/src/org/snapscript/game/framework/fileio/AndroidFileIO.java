package org.snapscript.game.framework.fileio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.snapscript.game.framework.FileIO;
import org.snapscript.game.framework.Game;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;

public class AndroidFileIO implements FileIO {
	Game game;
	AssetManager assets;
	String externalStoragePath;

	/*
	 * in this constructor we store the Context instance, which is the gateway to almost everything in
	 * Android, store an AssetManager, which we pull from the Context, store the
	 * external storage�s path,
	 */
	public AndroidFileIO(Game game) {
		this.game = game;
		this.assets = game.getContext().getAssets();
		this.externalStoragePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator;
	}

	public InputStream readAsset(String fileName) throws IOException {
		return assets.open(fileName);
	}

	public InputStream readFile(String fileName) throws IOException {
        return new FileInputStream(externalStoragePath + fileName);
	}

	public OutputStream writeFile(String fileName) throws IOException {
		return new FileOutputStream(externalStoragePath + fileName);
	}

	public SharedPreferences getPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(game.getContext());
	}
}