package com.pail.myzkbycy.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.bean.NotificationData;
import com.pail.myzkbycy.constants.Constant;
import com.pail.myzkbycy.control.HistroyUserPreferences;
import com.pail.myzkbycy.lib.UserFunctions;
import com.pail.myzkbycy.lib.UserModel;
import com.pail.myzkbycy.util.DialogUtil;
import com.pail.myzkbycy.util.NetworkUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class NotificationActivity extends BaseActivity {
	
	private ProgressDialog pDialog;
	private JSONObject json;
	private NotificationData[] notificationDatas;
	private LinearLayout parentTextLinearLayout;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new addOrDelFriendFromURL().execute("");
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.notification_layout);
		setTopText("梦园通知");
		setBottomVisable(View.GONE);
		parentTextLinearLayout = (LinearLayout) findViewById(R.id.parentTextLinearLayout);
	}


	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
	}


	class addOrDelFriendFromURL extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = UserFunctions.createProgressDialog(NotificationActivity.this,
					"后台忙碌中，请稍后...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			UserFunctions userFunction = new UserFunctions();
			Message msg = new Message();
			Bundle data = new Bundle();
			JSONObject json = userFunction.getNotification("we are friends");
			if(json == null) {
				return "failConnection";
			}
			notificationDatas = new NotificationData[json.length()];
			for (int i = 0; i < json.length(); i++) {
				try {
					notificationDatas[i] = new NotificationData();
					JSONObject tempJsonObject = json.getJSONObject(json.names()
							.get(i).toString());
					notificationDatas[i].setNews_title(tempJsonObject
							.getString("news_title").toString());
					notificationDatas[i].setNews_content(tempJsonObject
							.getString("news_content").toString());
					notificationDatas[i].setNews_data(tempJsonObject.getString(
							"news_data").toString());
					notificationDatas[i].setNews_id(tempJsonObject.getString(
							"news_id").toString());
					Log.i("json", notificationDatas[i].getNews_content());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return "successConnection";
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			if(result.equals("failConnection")) {
				DialogUtil.getInstance().showTipDialog(NotificationActivity.this, "无法连接上服务器");
			} else {
				for(int i = 0;i < notificationDatas.length;i++) {
					addSonViewToLayout(notificationDatas[i]);
					Log.i("test", notificationDatas[i].getNews_data());
				}
			}
		}
	}
	public void addSonViewToLayout(NotificationData notificationData) {
		TextView textView1 = new TextView(this);
		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		textView1.setGravity(Gravity.CENTER_HORIZONTAL);
		textView1.setLayoutParams(params1);
		textView1.setText(Html.fromHtml(notificationData.getNews_title()));
		textView1.setTextColor(getResources().getColor(R.color.black));
		parentTextLinearLayout.addView(textView1);
		
		TextView textView3 = new TextView(this);
		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		textView3.setGravity(Gravity.RIGHT);
		textView3.setLayoutParams(params3);
		textView3.setText(Html.fromHtml(notificationData.getNews_data()));
		textView3.setTextColor(getResources().getColor(R.color.black));
		parentTextLinearLayout.addView(textView3);
		
		TextView textView2 = new TextView(this);
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		textView2.setLayoutParams(params2);
		textView2.setText(Html.fromHtml(notificationData.getNews_content()));
		textView2.setTextColor(getResources().getColor(R.color.black));
		parentTextLinearLayout.addView(textView2);
		
	}
}
