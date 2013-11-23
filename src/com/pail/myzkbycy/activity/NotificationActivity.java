package com.pail.myzkbycy.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.adapter.NotificationAdapter;
import com.pail.myzkbycy.bean.NotificationData;
import com.pail.myzkbycy.lib.UserFunctions;
import com.pail.myzkbycy.util.DialogUtil;
import com.pail.myzkbycy.util.NetworkUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class NotificationActivity extends BaseActivity {
	
	private ProgressDialog pDialog;
	private NotificationData[] notificationDatas;
	private ListView listView;
	private List<Map<String, Object>> listData;
	private NotificationAdapter notificationAdapter;
	
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			notificationAdapter.notifyDataSetChanged();
			return false;
		}
	});
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(NetworkUtil.getInstance().isNetworkGood(NotificationActivity.this)){
			new addOrDelFriendFromURL().execute("");
		} else {
			DialogUtil.getInstance().showTipDialog(NotificationActivity.this,
					 "网络连接不正常，请检查");
		}
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.notification_layout);
		setTopText("梦园通知");
		setBottomVisable(View.GONE);
		
		listView = (ListView) findViewById(R.id.list);
		notificationAdapter = new NotificationAdapter(this);
		listView.setAdapter(notificationAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(NotificationActivity.this, NotificationSonActivity.class);
				intent.putExtra("news_id", listData.get(arg2).get("news_id").toString());
				startActivity(intent);
			}
		});
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
					"数据处理中，请稍候...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			Bundle data = new Bundle();
			JSONObject json = UserFunctions.getInstance().getTitlesNotification();
			if(json == null) {
				return "failConnection";
			}
			notificationDatas = new NotificationData[json.length()];
			for (int i = 0; i < json.length(); i++) {
				try {
					notificationDatas[i] = new NotificationData();
					JSONObject tempJsonObject = json.getJSONObject("n" + i);
					notificationDatas[i].setNews_title(tempJsonObject
							.getString("news_title").toString());
					notificationDatas[i].setNews_data(tempJsonObject.getString(
							"news_data").toString());
					notificationDatas[i].setNews_id(tempJsonObject.getString(
							"news_id").toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			listData = getData(notificationDatas);
			notificationAdapter.setListItem(listData);
			handler.sendEmptyMessage(0);
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
//				for(int i = 0;i < notificationDatas.length;i++) {
//					addSonViewToLayout(notificationDatas[i]);
//					Log.i("test", notificationDatas[i].getNews_data());
//				}
			}
		}
	}
	public List<Map<String, Object>> getData(NotificationData[] notificationDatas) {
		List<Map<String, Object>> lData = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (int i = 0; i < notificationDatas.length; i++) {
			map = new HashMap<String, Object>();
			map.put("news_id", notificationDatas[i].getNews_id());
			map.put("news_title", notificationDatas[i].getNews_title());
			map.put("news_data", notificationDatas[i].getNews_data());
			map.put("news_content", notificationDatas[i].getNews_content());
			
			lData.add(map);
		}

		return lData;
	}
//	public void addSonViewToLayout(NotificationData notificationData) {
//		TextView textView1 = new TextView(this);
//		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
//				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//		textView1.setGravity(Gravity.CENTER_HORIZONTAL);
//		textView1.setLayoutParams(params1);
//		textView1.setText(Html.fromHtml(notificationData.getNews_title()));
//		textView1.setTextColor(getResources().getColor(R.color.black));
//		parentTextLinearLayout.addView(textView1);
//		
//		TextView textView3 = new TextView(this);
//		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
//				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//		textView3.setGravity(Gravity.RIGHT);
//		textView3.setLayoutParams(params3);
//		textView3.setText(Html.fromHtml(notificationData.getNews_data()));
//		textView3.setTextColor(getResources().getColor(R.color.black));
//		parentTextLinearLayout.addView(textView3);
//		
//		TextView textView2 = new TextView(this);
//		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
//				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//		textView2.setLayoutParams(params2);
//		textView2.setText(Html.fromHtml(notificationData.getNews_content()));
//		textView2.setTextColor(getResources().getColor(R.color.black));
//		parentTextLinearLayout.addView(textView2);
//		
//	}
}
