package com.pail.myzkbycy.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.pail.myzkbycy.control.AsynImageLoader;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class FileUtil {
	private static final String TAG = "FileUtil";

	public static File getCacheFile(String imageUri) {
		File cacheFile = null;
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File sdCardDir = Environment.getExternalStorageDirectory();
				String fileName = getFileName(imageUri);
				File dir = new File(sdCardDir.getCanonicalPath() + "/123");
				// + AsynImageLoader.CACHE_DIR);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				cacheFile = new File(dir, fileName);
				Log.i(TAG, "exists:" + cacheFile.exists() + ",dir:" + dir
						+ ",file:" + fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "getCacheFileError:" + e.getMessage());
		}

		return cacheFile;
	}

	public static String getFileName(String path) {
		int index = path.lastIndexOf("/");
		return path.substring(index + 1);
	}

	/**
	 * 从Assets中读取图片
	 */
	public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
		Bitmap image = null;
		AssetManager am = context.getResources().getAssets();
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;

	}
}