package com.pail.myzkbycy.control;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

/**
 * 
 * @ClassName MyApplication.java
 * @Description 自定义Application
 * @author Derek zhang dzhang@xtremeprog.com
 * @date 2013-1-9
 *
 */
public class MyApplication extends Application {
	private List<Activity> activityList = new LinkedList<Activity>();
	
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	/**
	 *把新打开的Activity标记下,用于退出处理 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	/**
	 * 退出处理,把所有打开的activity 结束掉
	 */
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}
}
