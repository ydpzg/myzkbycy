package com.pail.myzkbycy.control;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.pail.myzkbycy.constants.Constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SetPreferences {
	private static SetPreferences instance;
	private SharedPreferences sharedSet;

	private SetPreferences() {
	}

	private SetPreferences(Context context) {
		sharedSet = context.getSharedPreferences(Constant.SET,
				Context.MODE_PRIVATE);
		
	}

	public static SetPreferences getInstance(Context context) {
		if (instance == null) {
			instance = new SetPreferences(context);
		}
		
		return instance;
	}

	public void setPicDownload(int value) {
		Editor edit = sharedSet.edit();
		edit.putInt("picDownload", value);
		edit.commit();
	}
	
	public int getPicDownload() {
		return sharedSet.getInt("picDownload", 1);
	}
	
}
