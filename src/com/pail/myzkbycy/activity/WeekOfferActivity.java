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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class WeekOfferActivity extends BaseActivity {

	private ProgressDialog pDialog;
	private JSONObject json;
	private Plant_Detail[] plant_Details;
	private ListView listView;
	private List<Map<String, Object>> listData;
	private AllPlantAdapter allPlantAdapter;
	private int isClickInx = -1;
	
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			allPlantAdapter.notifyDataSetChanged();
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(NetworkUtil.getInstance().isNetworkGood(WeekOfferActivity.this)){
			new addOrDelFriendFromURL().execute("");
		} else {
			DialogUtil.getInstance().showTipDialog(WeekOfferActivity.this,
					 "网络连接不正常，请检查");
		}
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.all_plant_layout);
		setTopText("本周供菜");
		setBottomVisable(View.GONE);

		listView = (ListView) findViewById(R.id.list);
		allPlantAdapter = new AllPlantAdapter(this);
		listView.setAdapter(allPlantAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (listData != null) {
					if (isClickInx == arg2) {
						listData.get(arg2).put("isClick", false);
						isClickInx = -1;
					} else {
						if(isClickInx != -1) {
							listData.get(isClickInx).put("isClick", false);
						}
						listData.get(arg2).put("isClick", true);
						isClickInx = arg2;
					}	

					allPlantAdapter.notifyDataSetChanged();
				}
			}
		});
//		listView.setOnScrollListener(new OnScrollListener() {
//			
//			@Override
//			public void onScrollStateChanged(AbsListView view, int scrollState) {
//				// TODO Auto-generated method stub
//				if(isClickInx != -1) {
//					listData.get(isClickInx).put("isClick", false);
//					allPlantAdapter.notifyDataSetChanged();
//					isClickInx = -1;
//				}
//			}
//			
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem,
//					int visibleItemCount, int totalItemCount) {
//				// TODO Auto-generated method stub
//				
//			}
//		});

		// true_name_TV = (TextView) findViewById(R.id.true_name_TV);
		// index_phone_TV = (TextView) findViewById(R.id.index_phone_TV);
		// user_active_TV = (TextView) findViewById(R.id.user_active_TV);
		// user_status_TV = (TextView) findViewById(R.id.user_status_TV);
		// set_price_TV = (TextView) findViewById(R.id.set_price_TV);
		// middle_number_TV = (TextView) findViewById(R.id.middle_number_TV);
		// users_qq_TV = (TextView) findViewById(R.id.users_qq_TV);
		// users_feixin_TV = (TextView) findViewById(R.id.users_feixin_TV);
		// pickup_place_TV = (TextView) findViewById(R.id.pickup_place_TV);
		// pickup_time_TV = (TextView) findViewById(R.id.pickup_time_TV);
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
			pDialog = UserFunctions.createProgressDialog(WeekOfferActivity.this,
					"数据处理中，请稍候...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			Bundle data = new Bundle();
			JSONObject json = UserFunctions.getInstance().getWeekOffer();
			if (json == null) {
				return "failConnection";
			}
			plant_Details = new Plant_Detail[json.length()];
			for (int i = 0; i < json.length(); i++) {
				try {
					plant_Details[i] = new Plant_Detail();
					// JSONObject tempJsonObject =
					// json.getJSONObject(json.names()
					// .get(i).toString());
					JSONObject tempJsonObject = json.getJSONObject("n"
							+ (json.length() - 1 - i));
					plant_Details[i].setPlant_id(tempJsonObject.getString(
							"plant_id").toString());
					plant_Details[i].setPlant_time(tempJsonObject.getString(
							"plant_time").toString());
					plant_Details[i].setPlant_name(tempJsonObject.getString(
							"plant_name").toString());
					plant_Details[i].setDruguse_data(tempJsonObject.getString(
							"druguse").toString());
					plant_Details[i].setFertilizer_data(tempJsonObject
							.getString("fertilizer").toString());
					plant_Details[i].setCook_web(tempJsonObject
							.getString("web_link").toString());
					plant_Details[i].setClick(false);
					Log.i("json", plant_Details[i].toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			listData = getData(plant_Details);
			allPlantAdapter.setListItem(listData);
			handler.sendEmptyMessage(0);
			return "successConnection";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			// if (result.equals("failConnection")) {
			// DialogUtil.getInstance().showTipDialog(AllPlantActivity.this,
			// "无法连接上服务器");
			// } else {
			// true_name_TV.setText(userInfData.getTrue_name());
			// index_phone_TV.setText(userInfData.getIndex_phone());
			// if (userInfData.getTemp_user_active().equals("Y")) {
			// user_active_TV.setText("已激活");
			// } else if (userInfData.getTemp_user_active().equals("N")) {
			// user_active_TV.setText("未激活");
			// }
			// if (userInfData.getTemp_user_status().equals("Y")) {
			// user_status_TV.setText("管理员");
			// } else if (userInfData.getTemp_user_status().equals("N")) {
			// user_status_TV.setText("注册会员");
			// }
			// // user_status_TV.setText(userInfData.getIndex_phone());
			// set_price_TV.setText(userInfData.getIndex_set() + "型 "
			// + userInfData.getIndex_price() + "元/月");
			// middle_number_TV.setText(userInfData.getIndex_middle_number());
			// users_qq_TV.setText(userInfData.getTemp_qq());
			// users_feixin_TV.setText(userInfData.getTemp_feixin());
			// pickup_place_TV.setText("32栋前（英东楼后，艺术设计馆旁）");
			// pickup_time_TV.setText("周一至周五（11:20-13:30）");
			//
			// }
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

	public List<Map<String, Object>> getData(Plant_Detail[] plant_Details) {
		List<Map<String, Object>> lData = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (int i = 0; i < plant_Details.length; i++) {
			map = new HashMap<String, Object>();
			map.put("item_id", plant_Details[i].getPlant_id());
			map.put("item_name", plant_Details[i].getPlant_name());
			map.put("item_date", plant_Details[i].getPlant_time());
			map.put("isClick", plant_Details[i].isClick());
			map.put("web_link", plant_Details[i].getCook_web());
			
			lData.add(map);
		}

		return lData;
	}

	public boolean isAllDefault() {
		boolean res = true;
		for (int i = 0; i < listData.size(); i++) {
			if((Boolean) listData.get(i).get("isClick")) {
				res = false;
				break;
			}
		}
		return res;
	}
}
