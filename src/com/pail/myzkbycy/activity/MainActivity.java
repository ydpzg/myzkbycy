package com.pail.myzkbycy.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.constants.Constant;
import com.pail.myzkbycy.control.AutoLoginPreferences;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	private GridView gridView;
	int[] image_page = new int[] { R.drawable.visio_home_icon_information,
			R.drawable.visio_home_icon_me,
			R.drawable.visio_home_icon_more,
			R.drawable.visio_home_icon_inquires,
			R.drawable.visio_home_icon_order,
			R.drawable.visio_home_icon_balance,
			R.drawable.visio_home_icon_reimbursement,
			R.drawable.visio_home_icon_rechargeable };
	int[] name_page = new int[] { R.string.main_notification,
			R.string.main_user_inf, R.string.main_all_plant,
			R.string.main_week_offer, R.string.main_reserve_offer,
			R.string.main_payment_query, R.string.main_unsubscribe,
			R.string.main_setup_admin };
	private long exitTime = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setTopText(getString(R.string.zkbycy));
		setBottomVisable(View.GONE);
		setRightButton(View.INVISIBLE);

		Log.i("test", "auto="
				+ AutoLoginPreferences.getInstance(this).getNameValue("user"));
		setCenterView(R.layout.main);
		gridView = (GridView) findViewById(R.id.gridview);

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
				.equals(getString(R.string.main_reserve_offer))) {
			Intent intent = new Intent(MainActivity.this,
					ReserveOfferActivity.class);
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

}
