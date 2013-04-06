package com.pail.myzkbycy.control;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.pail.myzkbycy.constants.Constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @ClassName HistroyUserPreferences.java
 * @Description 保存历史用户,并记录有无记住密码
 * @author Derek zhang dzhang@xtremeprog.com
 * @date 2013-1-10
 * 
 */
public class HistroyUserPreferences {
	private static HistroyUserPreferences instance;
	private SharedPreferences sharedPre;

	private HistroyUserPreferences() {
	}

	private HistroyUserPreferences(Context context) {
		sharedPre = context.getSharedPreferences(Constant.SHARED_HISTORY_USER,
				Context.MODE_PRIVATE);
		
	}

	public static HistroyUserPreferences getInstance(Context context) {
		if (instance == null) {
			instance = new HistroyUserPreferences(context);
		}
		
		return instance;
	}

	/**
	 * 保存一个历史用户
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码 当
	 */
	public void recordHistoryUser(String userName, String password) {
		Editor edit = sharedPre.edit();
		edit.putString(userName, password);
		edit.commit();
	}

	/**
	 * 获取历史用户,并从密码是否为空来判断是否有记录密码
	 * 
	 * @return
	 */
	public Map<String, String> quallyAllHistoryUser() {
		Map<String, String> historyList = new HashMap<String, String>();
		Map<String, ?> allData = sharedPre.getAll();
		Set<String> keySet = allData.keySet();
		for (String tempUserName : keySet) {
			if (!"".equals(tempUserName)) {
				String password = sharedPre.getString(tempUserName, null);
				historyList.put(tempUserName, password);
			}
		}
		return historyList;
	}
	public boolean clearAllHistoryUser(){
		Editor edit = sharedPre.edit();
		edit.clear();
		edit.commit();
		return true;
	}
	
	/**
	 * 判断用户是否第一次登陆
	 * @param user
	 * @return
	 */
	public boolean checkUserFirstLogin(String user){
		boolean result = false ;
		if(user != null && "".equals(user)){
			Map<String, ?> allData = sharedPre.getAll();
			Set<String> keySet = allData.keySet();
			for (String string : keySet) {
				if(user.equals(string)){
					result = true ;
					break;
				}
			}
		}
		return result ;
	}
}
