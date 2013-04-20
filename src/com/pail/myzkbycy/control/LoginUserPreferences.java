package com.pail.myzkbycy.control;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.pail.myzkbycy.constants.Constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LoginUserPreferences {
	private static LoginUserPreferences instance;
	private SharedPreferences sharedSet;

	private LoginUserPreferences() {
	}

	private LoginUserPreferences(Context context) {
		sharedSet = context.getSharedPreferences(Constant.CUR_USER,
				Context.MODE_PRIVATE);
		
	}

	public static LoginUserPreferences getInstance(Context context) {
		if (instance == null) {
			instance = new LoginUserPreferences(context);
		}
		
		return instance;
	}

	public void setLoginUser(String user) {
		Editor edit = sharedSet.edit();
		edit.putString("login_user", user);
		edit.commit();
	}
	
	public String getLoginUser() {
		return sharedSet.getString("login_user", "");
	}
	
	public void cleanLoginUser() {
		setLoginUser("");
	}
}
