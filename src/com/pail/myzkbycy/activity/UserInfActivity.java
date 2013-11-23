package com.pail.myzkbycy.activity;


import org.json.JSONException;
import org.json.JSONObject;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.bean.UserInfData;
import com.pail.myzkbycy.control.LoginUserPreferences;
import com.pail.myzkbycy.lib.UserFunctions;
import com.pail.myzkbycy.util.DialogUtil;
import com.pail.myzkbycy.util.NetworkUtil;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.LinearLayout;

public class UserInfActivity extends BaseActivity {

	private ProgressDialog pDialog;
	private JSONObject json;
	private UserInfData userInfData;
	private LinearLayout parentTextLinearLayout;
	private TextView true_name_TV, index_phone_TV, user_active_TV,
			user_status_TV, set_price_TV, middle_number_TV, users_qq_TV,
			users_feixin_TV, pickup_place_TV, pickup_time_TV;
	private String loginUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(NetworkUtil.getInstance().isNetworkGood(UserInfActivity.this)){
			new addOrDelFriendFromURL().execute("");
		} else {
			DialogUtil.getInstance().showTipDialog(UserInfActivity.this,
					 "网络连接不正常，请检查");
		}
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.user_inf_layout);
		setTopText("用户信息");
		setBottomVisable(View.GONE);
		// parentTextLinearLayout = (LinearLayout)
		// findViewById(R.id.parentTextLinearLayout);

		loginUser = LoginUserPreferences.getInstance(this).getLoginUser();
		
		true_name_TV = (TextView) findViewById(R.id.true_name_TV);
		index_phone_TV = (TextView) findViewById(R.id.index_phone_TV);
		user_active_TV = (TextView) findViewById(R.id.user_active_TV);
		user_status_TV = (TextView) findViewById(R.id.user_status_TV);
		set_price_TV = (TextView) findViewById(R.id.set_price_TV);
		middle_number_TV = (TextView) findViewById(R.id.middle_number_TV);
		users_qq_TV = (TextView) findViewById(R.id.users_qq_TV);
		users_feixin_TV = (TextView) findViewById(R.id.users_feixin_TV);
		pickup_place_TV = (TextView) findViewById(R.id.pickup_place_TV);
		pickup_time_TV = (TextView) findViewById(R.id.pickup_time_TV);
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
			pDialog = UserFunctions.createProgressDialog(UserInfActivity.this,
					"数据处理中，请稍候...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			Bundle data = new Bundle();
			JSONObject json = UserFunctions.getInstance().getUserInf(loginUser);
			if (json == null) {
				return "failConnection";
			}
			try {
				userInfData = new UserInfData();
				userInfData
						.setTrue_name(json.getString("true_name").toString());
				userInfData.setTemp_area_id(json.getString("temp_area_id")
						.toString());
				userInfData.setTemp_qq(json.getString("temp_qq").toString());
				userInfData.setTemp_feixin(json.getString("temp_feixin")
						.toString());
				userInfData.setIndex_place(json.getString("index_place")
						.toString());
				userInfData.setIndex_name(json.getString("index_name")
						.toString());
				userInfData.setIndex_address(json.getString("index_address")
						.toString());
				userInfData.setIndex_middle_number(json.getString(
						"index_middle_number").toString());
				userInfData.setIndex_phone(json.getString("index_phone")
						.toString());
				userInfData
						.setIndex_set(json.getString("index_set").toString());
				userInfData.setIndex_price(json.getString("index_price")
						.toString());
				userInfData.setTemp_user_active(json.getString(
						"temp_user_active").toString());
				userInfData.setTemp_user_status(json.getString(
						"temp_user_status").toString());
				userInfData.setTemp_qu_time(json.getString("temp_qu_time")
						.toString());
				Log.i("json", userInfData.toString());
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
			if (result.equals("failConnection")) {
				DialogUtil.getInstance().showTipDialog(UserInfActivity.this,
						"无法连接上服务器");
			} else {
				true_name_TV.setText(userInfData.getTrue_name());
				index_phone_TV.setText(userInfData.getIndex_phone());
				if (userInfData.getTemp_user_active().equals("Y")) {
					user_active_TV.setText("已激活");
				} else if (userInfData.getTemp_user_active().equals("N")) {
					user_active_TV.setText("未激活");
				}
				if (userInfData.getTemp_user_status().equals("Y")) {
					user_status_TV.setText("管理员");
				} else if (userInfData.getTemp_user_status().equals("N")) {
					user_status_TV.setText("注册会员");
				}
				// user_status_TV.setText(userInfData.getIndex_phone());
				set_price_TV.setText(userInfData.getIndex_set() + "型 "
						+ userInfData.getIndex_price() + "元/月");
				middle_number_TV.setText(userInfData.getIndex_middle_number());
				users_qq_TV.setText(userInfData.getTemp_qq());
				users_feixin_TV.setText(userInfData.getTemp_feixin());
				pickup_place_TV.setText("32栋前（英东楼后，艺术设计馆旁）");
				pickup_time_TV.setText("周一至周五（11:20-13:30）");

			}
		}
	}
	// public void addSonViewToLayout(NotificationData notificationData) {
	// TextView textView1 = new TextView(this);
	// LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
	// LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	// textView1.setGravity(Gravity.CENTER_HORIZONTAL);
	// textView1.setLayoutParams(params1);
	// textView1.setText(Html.fromHtml(notificationData.getNews_title()));
	// textView1.setTextColor(getResources().getColor(R.color.black));
	// parentTextLinearLayout.addView(textView1);
	//
	// TextView textView3 = new TextView(this);
	// LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
	// LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	// textView3.setGravity(Gravity.RIGHT);
	// textView3.setLayoutParams(params3);
	// textView3.setText(Html.fromHtml(notificationData.getNews_data()));
	// textView3.setTextColor(getResources().getColor(R.color.black));
	// parentTextLinearLayout.addView(textView3);
	//
	// TextView textView2 = new TextView(this);
	// LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
	// LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	// textView2.setLayoutParams(params2);
	// textView2.setText(Html.fromHtml(notificationData.getNews_content()));
	// textView2.setTextColor(getResources().getColor(R.color.black));
	// parentTextLinearLayout.addView(textView2);
	//
	// }
}
