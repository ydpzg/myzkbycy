package com.pail.myzkbycy.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.bean.NotificationData;
import com.pail.myzkbycy.constants.Constant;
import com.pail.myzkbycy.control.AutoLoginPreferences;
import com.pail.myzkbycy.control.LoginUserPreferences;
import com.pail.myzkbycy.lib.UserFunctions;
import com.pail.myzkbycy.util.DialogUtil;
import com.pail.myzkbycy.util.NetworkUtil;
import com.pail.myzkbycy.util.PxUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.JsonReader;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	private GridView gridView;
	int[] image_page = new int[] { 
			R.drawable.visio_home_icon_information,
			R.drawable.visio_home_icon_me,
			R.drawable.visio_home_icon_balance,
			R.drawable.visio_home_icon_order,
			R.drawable.visio_home_icon_inquires,
			R.drawable.visio_home_icon_more,
			R.drawable.visio_home_icon_more,
			R.drawable.visio_home_icon_reimbursement,
			R.drawable.visio_home_icon_rechargeable };
	int[] name_page = new int[] { 
			R.string.main_notification,
			R.string.main_user_inf, 
			R.string.main_payment_query, 
			R.string.main_today_offer,
			R.string.main_week_offer, 
			R.string.main_all_plant,
			R.string.main_package_inf,
			R.string.main_unsubscribe,
			R.string.main_setup_admin };
	private long exitTime = 0;
	private NotificationData notificationData;
	private RotateAnimation anim_rotate = null;
	private Button loading;
	private TextView bottom_title, bottom_date;
	private PopupWindow popup;
	private RelativeLayout parentLayout;
	private LinearLayout bottom_LL;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//异步获取通知信息的最新的一条
		if(NetworkUtil.getInstance().isNetworkGood(MainActivity.this)){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					JSONObject json = UserFunctions.getInstance().getLastNotification();
					if(json == null) {
						Log.i("test", "failconnection");
					}
					try {
						notificationData = new NotificationData();
						notificationData.setNews_title(json
								.getString("news_title").toString());
						notificationData.setNews_content(json
								.getString("news_content").toString());
						notificationData.setNews_data(json.getString(
								"news_data").toString());
						notificationData.setNews_id(json.getString(
								"news_id").toString());
						Log.i("json", notificationData.getNews_title() + "  " + notificationData.getNews_data());
						MainActivity.this.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								bottom_title.setText(notificationData.getNews_title());
								bottom_date.setText("发布人：梦园        发布时间：" + notificationData.getNews_data());
							} 
						});
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		} else {
			DialogUtil.getInstance().showTipDialog(MainActivity.this,
					 "网络连接不通");
		}
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setTopText(getString(R.string.zkbycy));
		setBottomVisable(View.GONE);
		setRightButton(View.GONE);
		setCenterView(R.layout.main);
		setLeftButton(R.drawable.button4, View.VISIBLE, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		}, R.string.exit);

		Log.i("test", "auto="
				+ AutoLoginPreferences.getInstance(this).getNameValue("user"));
		gridView = (GridView) findViewById(R.id.gridview);
		bottom_title = (TextView) findViewById(R.id.bottom_title);
		bottom_date = (TextView) findViewById(R.id.bottom_date);
		parentLayout = (RelativeLayout) findViewById(R.id.parentLayout);
		bottom_LL = (LinearLayout) findViewById(R.id.bottom_LL);
		
		bottom_title.setOnClickListener(this);
		bottom_date.setOnClickListener(this);
		//加载动画
//		loading = (Button) findViewById(R.id.loading);
		initAnim();
		// 生成动态数组，并且转入数据
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < image_page.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", image_page[i]);// 添加图像资源的ID
			map.put("ItemText", getResources().getString(name_page[i]));// 按序号做ItemText
			lstImageItem.add(map);
		}
		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		SimpleAdapter saImageItems = new SimpleAdapter(this, // 没什么解释
				lstImageItem,// 数据来源
				R.layout.night_item,// night_item的XML实现

				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemText" },

				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.ItemImage, R.id.ItemText });
		// 添加并且显示
		gridView.setAdapter(saImageItems);
		// 添加消息处理
		gridView.setOnItemClickListener(new ItemClickListener());

		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if(v == bottom_title) {
			if(!bottom_title.getText().equals("")) {
				showLastNotification();
				bottom_LL.setVisibility(View.INVISIBLE);
			}
		}
		if(v == bottom_date) {
			if(!bottom_title.getText().equals("")) {
				showLastNotification();
				bottom_LL.setVisibility(View.INVISIBLE);
			}
		}
	}

	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// 在本例中arg2=arg3
			itemAction(arg0, arg2);
		}

	}

	// 每个Item执行的动作
	public void itemAction(AdapterView<?> adapterView, int itemId) {
		HashMap<String, Object> item = (HashMap<String, Object>) adapterView
				.getItemAtPosition(itemId);
		// 显示所选Item的ItemText
		Toast.makeText(MainActivity.this, item.get("ItemText").toString(),
				Toast.LENGTH_SHORT).show();
		if (item.get("ItemText").toString()
				.equals(getString(R.string.main_notification))) {
			Intent intent = new Intent(MainActivity.this,
					NotificationActivity.class);
			startActivity(intent);
		} else if (item.get("ItemText").toString()
				.equals(getString(R.string.main_user_inf))) {
			Intent intent = new Intent(MainActivity.this, UserInfActivity.class);
			startActivity(intent);
		} else if (item.get("ItemText").toString()
				.equals(getString(R.string.main_all_plant))) {
			Intent intent = new Intent(MainActivity.this,
					AllPlantActivity.class);
			startActivity(intent);
		} else if (item.get("ItemText").toString()
				.equals(getString(R.string.main_week_offer))) {
			Intent intent = new Intent(MainActivity.this,
					WeekOfferActivity.class);
			startActivity(intent);
		} else if (item.get("ItemText").toString()
				.equals(getString(R.string.main_today_offer))) {
			Intent intent = new Intent(MainActivity.this,
					TodayCaiActivity.class);
			startActivity(intent);
		} else if (item.get("ItemText").toString()
				.equals(getString(R.string.main_payment_query))) {
			Intent intent = new Intent(MainActivity.this,
					PaymentQueryActivity.class);
			startActivity(intent);
		} else if (item.get("ItemText").toString()
				.equals(getString(R.string.main_unsubscribe))) {
			Intent intent = new Intent(MainActivity.this,
					UnsubscribeActivity.class);
			startActivity(intent);
		} else if (item.get("ItemText").toString()
				.equals(getString(R.string.main_setup_admin))) {
			Intent intent = new Intent(MainActivity.this,
					SetAdminActivity.class);
			startActivityForResult(intent, Constant.MAIN_EXIT);
		} else if (item.get("ItemText").toString()
				.equals(getString(R.string.main_package_inf))) {
			Intent intent = new Intent(MainActivity.this,
					PackageActivity.class);
			startActivityForResult(intent, Constant.MAIN_EXIT);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constant.MAIN_EXIT) {
			if (resultCode == Activity.RESULT_OK) {
				this.finish();
			}
		}
	}

	// 连续两次返回退出程序

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	} // End of 连续两次返回退出程序
	
	private void initAnim() {
		anim_rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		anim_rotate.setDuration(1200);//转动的快慢
		anim_rotate.setRepeatMode(Animation.RESTART);
		anim_rotate.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.anim.linear_interpolator));
		anim_rotate.setRepeatCount(Animation.INFINITE);
	}
	
	private void showLastNotification() {
		View view = LayoutInflater.from(this).inflate(
				R.layout.wheel_all_date_layout, null);
		TextView noti_title = (TextView) view.findViewById(R.id.noti_title);
		TextView noti_content = (TextView) view.findViewById(R.id.noti_content);
		TextView noti_date = (TextView) view.findViewById(R.id.noti_date);
		noti_title.setText(notificationData.getNews_title());
		noti_content.setText(Html.fromHtml(notificationData.getNews_content()));
		noti_date.setText(notificationData.getNews_data());
		showPopupWindow(view);
	}
	/**
	 * 弹出popupWindow
	 * 
	 * @param view
	 * @return
	 */
	public PopupWindow showPopupWindow(View view) {
		if (popup != null && popup.isShowing()) {
			popup.dismiss();
		}
		popup = new PopupWindow(view, LayoutParams.FILL_PARENT,
				PxUtil.dip2px(this, 160), true);
		popup.setFocusable(true);
		popup.setBackgroundDrawable(new BitmapDrawable());
		popup.setOutsideTouchable(true);
		popup.setAnimationStyle(R.style.AnimationPreview);
		
		popup.showAtLocation(getWindow()
				.findViewById(Window.ID_ANDROID_CONTENT), Gravity.BOTTOM, 0, 0);
		popup.setTouchInterceptor(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					if(event.getY() < 0)
						bottom_LL.setVisibility(View.VISIBLE);
				}
				return false;
			}
		});
		return popup;
	}

	/**
	 * 关闭popupWindow
	 */
	public void dismissPopupWindow() {
		if (popup != null && popup.isShowing()) {
			popup.dismiss();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		loading.startAnimation(anim_rotate);
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		bottom_LL.setVisibility(View.VISIBLE);
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
//		loading.clearAnimation();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LoginUserPreferences.getInstance(this).cleanLoginUser();
	}
}
