package com.pail.myzkbycy.control;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.pail.myzkbycy.constants.Constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AutoLoginPreferences {
	private static AutoLoginPreferences instance;
	private SharedPreferences sharedAutoLogin;

	private AutoLoginPreferences() {
	}

	private AutoLoginPreferences(Context context) {
		sharedAutoLogin = context.getSharedPreferences(Constant.AUTO_LOGIN,
				Context.MODE_PRIVATE);
		
	}

	public static AutoLoginPreferences getInstance(Context context) {
		if (instance == null) {
			instance = new AutoLoginPreferences(context);
		}
		
		return instance;
	}

	public void setNameValue(String name, String value) {
		Editor edit = sharedAutoLogin.edit();
		edit.putString(name, value);
		edit.commit();
	}
	
	public boolean getAutoLogin(String name) {
		return sharedAutoLogin.getBoolean(name, false);
	}
	
	public void setAutoLogin(String name) {
		Editor edit = sharedAutoLogin.edit();
		edit.putBoolean(name, true);
		edit.commit();
	}
	public void removeAutoLogin(String name) { 
		Editor edit = sharedAutoLogin.edit();
		edit.putBoolean(name, false);
		edit.commit();
	}
	public String getNameValue(String name) {
		return sharedAutoLogin.getString(name, "");
	}
}
