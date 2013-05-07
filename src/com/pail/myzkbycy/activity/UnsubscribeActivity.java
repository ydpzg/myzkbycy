package com.pail.myzkbycy.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.activity.NotificationActivity.addOrDelFriendFromURL;
import com.pail.myzkbycy.adapter.AllPlantAdapter;
import com.pail.myzkbycy.bean.NotificationData;
import com.pail.myzkbycy.bean.Plant_Detail;
import com.pail.myzkbycy.bean.UserInfData;
import com.pail.myzkbycy.constants.Constant;
import com.pail.myzkbycy.control.HistroyUserPreferences;
import com.pail.myzkbycy.control.LoginUserPreferences;
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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class UnsubscribeActivity extends BaseActivity {

	private ProgressDialog pDialog;
	private JSONObject json;
	private String activeStatusValue = "0";
	private String userStatusValue = "0";
	private Button unsubscribeBtn;
	private String loginUser;
//	private Plant_Detail[] plant_Details;
//	private ListView listView;
//	private List<Map<String, Object>> listData;
//	private AllPlantAdapter allPlantAdapter;
//	private int isClickInx = -1;
	
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				String successString = (String) msg.obj;
				
				if(successString.equals("1")) {
					DialogUtil.showTipDialog(UnsubscribeActivity.this, "操作成功");
					if(activeStatusValue.equals("Y")) {
						unsubscribeBtn.setText("退订申请已提交，请等待审核!");
					} else {
						unsubscribeBtn.setText("申请已提交，请等待审核!");
					}
					unsubscribeBtn.setClickable(false);
				} else if(successString.equals("0")) {
					DialogUtil.showTipDialog(UnsubscribeActivity.this, "操作失败");
				}
				break;

			default:
				break;
			}
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(NetworkUtil.getInstance().isNetworkGood(UnsubscribeActivity.this)){
			new getUserActiveFromURL().execute("");
		} else {
			DialogUtil.getInstance().showTipDialog(UnsubscribeActivity.this,
					 "网络连接不正常，请检查");
		}
		
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.unsubscribe_layout);
		setTopText(getString(R.string.main_unsubscribe));
		setBottomVisable(View.GONE);
		
		loginUser = LoginUserPreferences.getInstance(this).getLoginUser();
		
		unsubscribeBtn = (Button) findViewById(R.id.unsubscribeBtn); 
		unsubscribeBtn.setOnClickListener(this);
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
		if(v == unsubscribeBtn) {
			String tempStr = "";
			if(activeStatusValue.equals("N")) {
				tempStr = "确定要重新订菜吗？此操作不能撤销！";
			} else {
				tempStr = "确定要会员退订吗？此操作不能撤销！";
			}
			OnClickListener onClickListener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new cancelServerFromURL().execute("");
					DialogUtil.dismissDialog();
				}
			};
			DialogUtil.showTip2Dialog(UnsubscribeActivity.this, tempStr, onClickListener);
		}
	}

	class getUserActiveFromURL extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = UserFunctions.createProgressDialog(UnsubscribeActivity.this,
					"数据处理中，请稍候...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			Bundle data = new Bundle();
			JSONObject json = UserFunctions.getInstance().getUnsubscribe(loginUser, "getUserActive", activeStatusValue);
			if (json == null) {
				return "failConnection";
			}
			try {
				activeStatusValue = json.getString("active_status").toString();
				userStatusValue = json.getString("user_status").toString();
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
				DialogUtil.getInstance().showTipDialog(UnsubscribeActivity.this,
						"无法连接上服务器");
			} else {
				if (activeStatusValue.equals("Y")) {
					if(userStatusValue.equals("S")) {
						unsubscribeBtn.setText("退订申请已提交，请等待审核!");
						unsubscribeBtn.setClickable(false);
					} else {
						unsubscribeBtn.setText("会员退订");
						unsubscribeBtn.setClickable(true);
					}
				} else if (activeStatusValue.equals("N")) {
					if(userStatusValue.equals("T")) {
						unsubscribeBtn.setText("重新订菜");
					} else {
						unsubscribeBtn.setText("未激活");
						unsubscribeBtn.setClickable(false);
					}
				}

			}
		}
	}
	
	class cancelServerFromURL extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = UserFunctions.createProgressDialog(UnsubscribeActivity.this,
					"后台忙碌中，请稍后...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			Bundle data = new Bundle();
			JSONObject json = UserFunctions.getInstance().getUnsubscribe(loginUser, "cancel", activeStatusValue);
			if (json == null) {
				return "failConnection";
			}
			try {
				String successString = json.getString("success").toString();
				Message message = new Message();
				message.obj = successString;
				message.what = 0;
				handler.sendMessage(message);
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
				DialogUtil.getInstance().showTipDialog(UnsubscribeActivity.this,
						"无法连接上服务器");
			} else {
			}
		}
	}
}
