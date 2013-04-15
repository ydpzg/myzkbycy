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
import com.pail.myzkbycy.adapter.AllPlantAdapter;
import com.pail.myzkbycy.adapter.PaymentQueryAdapter;
import com.pail.myzkbycy.bean.NotificationData;
import com.pail.myzkbycy.bean.Payment_Detail;
import com.pail.myzkbycy.bean.Plant_Detail;
import com.pail.myzkbycy.bean.UserInfData;
import com.pail.myzkbycy.constants.Constant;
import com.pail.myzkbycy.control.HistroyUserPreferences;
import com.pail.myzkbycy.lib.UserFunctions;
import com.pail.myzkbycy.lib.UserModel;
import com.pail.myzkbycy.util.DialogUtil;
import com.pail.myzkbycy.util.ListViewUtil;
import com.pail.myzkbycy.util.NetworkUtil;
import com.pail.myzkbycy.util.PxUtil;
import com.pail.myzkbycy.view.PaymentShowDialog;

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
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
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

public class PaymentQueryActivity extends BaseActivity {

	private ProgressDialog pDialog;
	private JSONObject json;
	private Payment_Detail[] payment_Details;
	private ListView listView;
	private List<Map<String, Object>> listData;
	private PaymentQueryAdapter paymentQueryAdapter;
	private float actionY;
	private int displayWidth;
	private int displayHeight;
	private int list_child_item_height;
	// private int isClickInx = -1;

	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			paymentQueryAdapter.notifyDataSetChanged();
			return false;
		}
	});

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
		setCenterView(R.layout.payment_query);
		setTopText("缴费查询");
		setBottomVisable(View.GONE);

		Display display = getWindowManager().getDefaultDisplay();

		displayWidth = display.getWidth();
		displayHeight = display.getHeight();
		
		listView = (ListView) findViewById(R.id.list);
		paymentQueryAdapter = new PaymentQueryAdapter(this);
		listView.setAdapter(paymentQueryAdapter);
		
        list_child_item_height = 0;

		listView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					actionY = event.getY();
					break;

				default:
					break;
				}
				return false;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.i("test", arg2 + "  " + actionY);
				PaymentShowDialog selectDialog = new PaymentShowDialog(PaymentQueryActivity.this, R.style.dialog);//创建Dialog并设置样式主题
				Window win = selectDialog.getWindow();
				android.view.WindowManager.LayoutParams params = new android.view.WindowManager.LayoutParams();
				params.x = 0;//设置x坐标
				Log.i("aaa", ListViewUtil.getListViewHeightBasedOnChildren(listView) + "");
//				View listItem = paymentQueryAdapter.getView(0, null, listView);
//		        listItem.measure(0, 0); // 计算子项View 的宽高
		        list_child_item_height = arg1.getMeasuredHeight() + 1;
				params.y = (int) (-displayHeight / 2 + list_child_item_height * 2 + actionY);//设置y坐标
				win.setAttributes(params);
				selectDialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
				selectDialog.show();
				TextView effective_time_TV = (TextView) selectDialog.findViewById(R.id.effective_time_TV);
				effective_time_TV.setText(payment_Details[arg2].getEffective_time());
				TextView payment_way_TV = (TextView) selectDialog.findViewById(R.id.payment_way_TV);
				payment_way_TV.setText(payment_Details[arg2].getPayment_way());

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
			pDialog = UserFunctions.createProgressDialog(
					PaymentQueryActivity.this, "后台忙碌中，请稍后...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			UserFunctions userFunction = new UserFunctions();
			Message msg = new Message();
			Bundle data = new Bundle();
			// JSONObject json = userFunction.getWeekOffer();
			// if (json == null) {
			// return "failConnection";
			// }
			// plant_Details = new Plant_Detail[json.length()];
			// for (int i = 0; i < json.length(); i++) {
			// try {
			// plant_Details[i] = new Plant_Detail();
			// // JSONObject tempJsonObject =
			// // json.getJSONObject(json.names()
			// // .get(i).toString());
			// JSONObject tempJsonObject = json.getJSONObject("n"
			// + (json.length() - 1 - i));
			// plant_Details[i].setPlant_id(tempJsonObject.getString(
			// "plant_id").toString());
			// plant_Details[i].setPlant_time(tempJsonObject.getString(
			// "plant_time").toString());
			// plant_Details[i].setPlant_name(tempJsonObject.getString(
			// "plant_name").toString());
			// plant_Details[i].setDruguse_data(tempJsonObject.getString(
			// "druguse").toString());
			// plant_Details[i].setFertilizer_data(tempJsonObject
			// .getString("fertilizer").toString());
			// plant_Details[i].setCook_web(tempJsonObject
			// .getString("web_link").toString());
			// plant_Details[i].setClick(false);
			// Log.i("json", plant_Details[i].toString());
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			payment_Details = new Payment_Detail[5];
			for (int i = 0; i < 5; i++) {
				payment_Details[i] = new Payment_Detail(); 
				payment_Details[i].setPayment_time("2013-02-25");
				payment_Details[i].setPayment_account("200");
				payment_Details[i].setEffective_time("2013-02-25 ~ 2013-03-24");
				payment_Details[i].setPayment_way("cash");
			}
			listData = getData(payment_Details);
			paymentQueryAdapter.setListItem(listData);
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

	public List<Map<String, Object>> getData(Payment_Detail[] payment_Details) {
		List<Map<String, Object>> lData = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (int i = 0; i < payment_Details.length; i++) {
			map = new HashMap<String, Object>();
			map.put("payment_time", payment_Details[i].getPayment_time());
			map.put("payment_account", payment_Details[i].getPayment_account());
			map.put("effective_time", payment_Details[i].getEffective_time());
			map.put("payment_way", payment_Details[i].getPayment_way());

			lData.add(map);
		}

		return lData;
	}

	public boolean isAllDefault() {
		boolean res = true;
		for (int i = 0; i < listData.size(); i++) {
			if ((Boolean) listData.get(i).get("isClick")) {
				res = false;
				break;
			}
		}
		return res;
	}
}
