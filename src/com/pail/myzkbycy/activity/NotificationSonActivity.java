package com.pail.myzkbycy.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.bean.NotificationData;
import com.pail.myzkbycy.lib.UserFunctions;
import com.pail.myzkbycy.util.DialogUtil;
import com.pail.myzkbycy.util.NetworkUtil;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class NotificationSonActivity extends BaseActivity {
	
	private ProgressDialog pDialog;
	private TextView news_title_TV, news_data_TV, news_content_TV;
	private String news_id;
	private NotificationData notificationData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(NetworkUtil.getInstance().isNetworkGood(NotificationSonActivity.this)){
			new addOrDelFriendFromURL().execute("");
		} else {
			DialogUtil.getInstance().showTipDialog(NotificationSonActivity.this,
					 "网络连接不正常，请检查");
		}
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.notification_son_layout);
		setTopText("梦园通知");
		setBottomVisable(View.GONE);
		
		news_title_TV = (TextView) findViewById(R.id.news_title_TV);
		news_data_TV = (TextView) findViewById(R.id.news_data_TV);
		news_content_TV = (TextView) findViewById(R.id.news_content_TV);
		
		news_id = getIntent().getStringExtra("news_id");
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
			pDialog = UserFunctions.createProgressDialog(NotificationSonActivity.this,
					"数据处理中，请稍候...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			Bundle data = new Bundle();
			JSONObject json = UserFunctions.getInstance().getContentNotification(news_id);
			if(json == null) {
				return "failConnection";
			}
            try {
            	notificationData = new NotificationData();
				notificationData.setNews_id(json.getString("news_id"));
				notificationData.setNews_content(json.getString("news_content"));
				notificationData.setNews_title(json.getString("news_title"));
				notificationData.setNews_data(json.getString("news_data"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "successConnection";
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			if(result.equals("failConnection")) {
				DialogUtil.getInstance().showTipDialog(NotificationSonActivity.this, "无法连接上服务器");
			} else {
				news_title_TV.setText(notificationData.getNews_title());
				news_data_TV.setText(notificationData.getNews_data());
				news_content_TV.setText(Html.fromHtml(notificationData.getNews_content()));
			}
		}
	}
//	public void add
}
