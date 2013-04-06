package com.pail.myzkbycy.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.pail.myzkbycy.R;
import com.pail.myzkbycy.R.layout;
import com.pail.myzkbycy.R.menu;
import com.pail.myzkbycy.bean.NotificationData;
import com.pail.myzkbycy.lib.UserFunctions;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.support.v4.app.NavUtils;

public class MainActivity2 extends Activity {
	private LinearLayout parentTextLinearLayout;
	private TextView showContextTextView;
	private ProgressDialog pDialog;
	private NotificationData[] notificationDatas;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		parentTextLinearLayout = (LinearLayout) findViewById(R.id.parentText);

		new addOrDelFriendFromURL().execute("1");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
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

	class addOrDelFriendFromURL extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = UserFunctions.createProgressDialog(MainActivity2.this,
					"后台忙碌中，请稍后...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			UserFunctions userFunction = new UserFunctions();
			Message msg = new Message();
			Bundle data = new Bundle();
			JSONObject json = userFunction.loginUser("13760625015", "1");
//			notificationDatas = new NotificationData[json.length()];
//			for (int i = 0; i < json.length(); i++) {
//				try {
//					notificationDatas[i] = new NotificationData();
//					JSONObject tempJsonObject = json.getJSONObject(json.names()
//							.get(i).toString());
//					notificationDatas[i].setNews_title(tempJsonObject
//							.getString("news_title").toString());
//					notificationDatas[i].setNews_content(tempJsonObject
//							.getString("news_content").toString());
//					notificationDatas[i].setNews_data(tempJsonObject.getString(
//							"news_data").toString());
//					notificationDatas[i].setNews_id(tempJsonObject.getString(
//							"news_id").toString());
//					Log.i("json", notificationDatas[i].getNews_content());
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
//			for(int i = 0;i < notificationDatas.length;i++) {
//				addSonViewToLayout(notificationDatas[i]);
//				Log.i("test", notificationDatas[i].getNews_data());
//			}
		}
//		class addOrDelFriendFromURL extends AsyncTask<String, String, String> {
//			
//			@Override
//			protected void onPreExecute() {
//				// TODO Auto-generated method stub
//				super.onPreExecute();
//				pDialog = UserFunctions.createProgressDialog(MainActivity.this,
//						"后台忙碌中，请稍后...");
//			}
//			
//			@Override
//			protected String doInBackground(String... params) {
//				// TODO Auto-generated method stub
//				UserFunctions userFunction = new UserFunctions();
//				Message msg = new Message();
//				Bundle data = new Bundle();
//				JSONObject json = userFunction.test("we are friends");
//				notificationDatas = new NotificationData[json.length()];
//				for (int i = 0; i < json.length(); i++) {
//					try {
//						notificationDatas[i] = new NotificationData();
//						JSONObject tempJsonObject = json.getJSONObject(json.names()
//								.get(i).toString());
//						notificationDatas[i].setNews_title(tempJsonObject
//								.getString("news_title").toString());
//						notificationDatas[i].setNews_content(tempJsonObject
//								.getString("news_content").toString());
//						notificationDatas[i].setNews_data(tempJsonObject.getString(
//								"news_data").toString());
//						notificationDatas[i].setNews_id(tempJsonObject.getString(
//								"news_id").toString());
//						Log.i("json", notificationDatas[i].getNews_content());
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				
//				return null;
//			}
//			
//			@Override
//			protected void onPostExecute(String result) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(result);
//				pDialog.dismiss();
//				for(int i = 0;i < notificationDatas.length;i++) {
//					addSonViewToLayout(notificationDatas[i]);
//					Log.i("test", notificationDatas[i].getNews_data());
//				}
//			}
	}

}
