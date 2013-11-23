package com.pail.myzkbycy.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.adapter.PaymentQueryAdapter;
import com.pail.myzkbycy.bean.PaymentDetail;
import com.pail.myzkbycy.control.LoginUserPreferences;
import com.pail.myzkbycy.lib.UserFunctions;
import com.pail.myzkbycy.util.DialogUtil;
import com.pail.myzkbycy.util.NetworkUtil;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PaymentQueryActivity extends BaseActivity {

	private ProgressDialog pDialog;
	private PaymentDetail[] paymentDetails;
	private ListView listView;
	private List<Map<String, Object>> listData;
	private PaymentQueryAdapter paymentQueryAdapter;
	private int displayWidth;
	private int displayHeight;
	private int list_child_item_height;
	private int isClickInx = 0;
	private String loginUser;

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
		if(NetworkUtil.getInstance().isNetworkGood(PaymentQueryActivity.this)){
			new addOrDelFriendFromURL().execute("");
		} else {
			DialogUtil.getInstance().showTipDialog(PaymentQueryActivity.this,
					 "网络连接不正常，请检查");
		}
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.payment_query);
		setTopText("缴费查询");
		setBottomVisable(View.GONE);

		loginUser = LoginUserPreferences.getInstance(this).getLoginUser();
		
		Display display = getWindowManager().getDefaultDisplay();

		displayWidth = display.getWidth();
		displayHeight = display.getHeight();
		
		listView = (ListView) findViewById(R.id.list);
		paymentQueryAdapter = new PaymentQueryAdapter(this);
		listView.setAdapter(paymentQueryAdapter);
		
        list_child_item_height = 0;
        
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				PaymentShowDialog selectDialog = new PaymentShowDialog(PaymentQueryActivity.this, R.style.dialog);//创建Dialog并设置样式主题
//				Window win = selectDialog.getWindow();
//				android.view.WindowManager.LayoutParams params = new android.view.WindowManager.LayoutParams();
//				params.x = 0;//设置x坐标
//				Log.i("aaa", ListViewUtil.getListViewHeightBasedOnChildren(listView) + "");
////				View listItem = paymentQueryAdapter.getView(0, null, listView);
////		        listItem.measure(0, 0); // 计算子项View 的宽高
//		        list_child_item_height = arg1.getMeasuredHeight() + 1;
//				params.y = (int) (-displayHeight / 2 + list_child_item_height * 2 + actionY);//设置y坐标
//				win.setAttributes(params);
//				selectDialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
//				selectDialog.show();
//				TextView effective_time_TV = (TextView) selectDialog.findViewById(R.id.effective_time_TV);
//				effective_time_TV.setText(payment_Details[arg2].getEffective_time());
//				TextView payment_way_TV = (TextView) selectDialog.findViewById(R.id.payment_way_TV);
//				payment_way_TV.setText(payment_Details[arg2].getPayment_way());
//
//			}
//		});
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.i("bbbb", arg2 + "");
				if (listData != null) {
					if (isClickInx == arg2) {
						listData.get(arg2).put("isClick", false);
						isClickInx = -1;
					} else {
						if(isClickInx != -1) {
							listData.get(isClickInx).put("isClick", false);
						} else {
						}
						listData.get(arg2).put("isClick", true);
						isClickInx = arg2;
					}	
					paymentQueryAdapter.notifyDataSetChanged();
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
//					paymentQueryAdapter.notifyDataSetChanged();
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
					PaymentQueryActivity.this, "数据处理中，请稍候...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			Bundle data = new Bundle();
			if(!NetworkUtil.getInstance().isNetworkGood(PaymentQueryActivity.this)){
				 return "failConnection";
			}
			Log.i("connect", NetworkUtil.getInstance().isNetworkGood(PaymentQueryActivity.this) + "");
			JSONObject json = UserFunctions.getInstance().getPaymentInf(loginUser);
			Log.i("vvv", "ddf");
			if (json == null) {
			    return "failConnection";
			}
			JSONObject tempJO;
			try {
				tempJO = json.getJSONObject("n0");
				if(tempJO.getString("success").toString().equals("0")) {
					return "null";
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			paymentDetails = new PaymentDetail[json.length()];
			for (int i = 0; i < json.length(); i++) {
				try {
					paymentDetails[i] = new PaymentDetail();
					// JSONObject tempJsonObject =
					// json.getJSONObject(json.names()
					// .get(i).toString());
					JSONObject tempJsonObject = json.getJSONObject("n"
							+ (json.length() - 1 - i));
					paymentDetails[i].setTime(tempJsonObject.getString(
							"payment_time").toString());
					paymentDetails[i].setId(tempJsonObject.getString(
							"payment_id").toString());
					paymentDetails[i].setBegin(tempJsonObject.getString(
							"payment_begin").toString());
					paymentDetails[i].setEnd(tempJsonObject.getString(
							"payment_end").toString());
					paymentDetails[i].setNumber(tempJsonObject
							.getString("payment_number").toString());
					Log.i("bbb", paymentDetails[i].toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
//			payment_Details = new Payment_Detail[5];
//			for (int i = 0; i < 5; i++) {
//				payment_Details[i] = new Payment_Detail(); 
//				payment_Details[i].setPayment_time("2013-02-25");
//				payment_Details[i].setPayment_account("200");
//				payment_Details[i].setEffective_time("2013-02-25 ~ 2013-03-24");
//				payment_Details[i].setPayment_way("cash");
//			}
			listData = getData(paymentDetails);
			paymentQueryAdapter.setListItem(listData);
			handler.sendEmptyMessage(0);
			return "successConnection";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			 if (result.equals("failConnection")) {
				 DialogUtil.getInstance().showTipDialog(PaymentQueryActivity.this,
				 "无法连接上服务器");
			 } else {
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

	public List<Map<String, Object>> getData(PaymentDetail[] payment_Details) {
		List<Map<String, Object>> lData = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (int i = 0; i < payment_Details.length; i++) {
			map = new HashMap<String, Object>();
			map.put("payment_time", payment_Details[i].getTime());
			map.put("payment_account", payment_Details[i].getNumber());
			map.put("effective_time", payment_Details[i].getBegin() + " ~ " + payment_Details[i].getEnd());
			map.put("payment_way", "");
			if(i == 0) {
				map.put("isClick", true);
			} else {
				map.put("isClick", false);
			}
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
