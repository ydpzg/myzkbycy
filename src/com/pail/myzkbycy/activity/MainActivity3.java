package com.pail.myzkbycy.activity;

import java.util.ArrayList;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.adapter.MainGridViewAdapter;

import android.app.AliasActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity3 extends BaseActivity {
	private ImageView iv_maintop;
	private ViewPager viewPager;
	private ArrayList<View> viewList;
	private ViewGroup main;
	private ViewGroup group;
	private ImageView[] imageViews;
	private ImageView imageView;
	private GridView[] mainView;
	private LayoutInflater inflater;
	private MainGridViewAdapter[] adapters;
	private View layout;
	private int width;
//	int[] image_page1 = new int[] { R.drawable.visio_home_icon_me,
//			R.drawable.visio_home_icon_information, R.drawable.visio_home_icon_order,
//			R.drawable.visio_home_icon_collection, R.drawable.visio_home_icon_payment,
//			R.drawable.visio_home_icon_balance, R.drawable.visio_home_icon_reimbursement,
//			R.drawable.visio_home_icon_rechargeable, R.drawable.visio_home_icon_transfer };
//	int[] name_page1 = new int[] { R.string.main_notification,
//			R.string.main_messagecenter, R.string.main_ordermanger,
//			R.string.main_mycrsh, R.string.main_mypay,
//			R.string.main_mybalanceinquiry, R.string.main_creditcardpayments,
//			R.string.main_phonerecharge, R.string.main_transferaccounts
//	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
//		hideTop();
//		DisplayMetrics dm = new DisplayMetrics();   
//        getWindowManager().getDefaultDisplay().getMetrics(dm);  
//        width=dm.widthPixels;
//		inflater = getLayoutInflater();
//		main = (ViewGroup) inflater.inflate(R.layout.main_layout, null);
//		group = (ViewGroup) main.findViewById(R.id.main_viewGroup);
//		viewPager = (ViewPager) main.findViewById(R.id.main_viewPager);
//		viewList = new ArrayList<View>();
//		adapters = new MainGridViewAdapter[2];
//		mainView = new GridView[2];
//		layout = inflater.inflate(R.layout.main_gridview, null);
//		mainView[0] = (GridView) layout.findViewById(R.id.main_gv);
//		adapters[0] = new MainGridViewAdapter(this, image_page1, name_page1,width);
//		mainView[0].setAdapter(adapters[0]);
////		mainView[0].setVerticalSpacing(150);
//		viewList.add(layout);
//		setCenterView(main);
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		mainView[0].setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				PageOneListener(position);
			}
		});
	}


	class MyAdapter extends PagerAdapter {
		public int getCount() {
			return viewList.size();
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(viewList.get(arg1));
		}

		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(viewList.get(arg1));
			return viewList.get(arg1);
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		public Parcelable saveState() {
			return null;
		}

		public void startUpdate(View arg0) {

		}

		public void finishUpdate(View arg0) {
		}
	}


	public void PageOneListener(int position) {
		Intent intent = new Intent();
		switch (position) {
		// 我的三维度
		case 0:
//			intent.setClass(this, PersonCenterActivity.class);
//			startActivity(intent);
			break;
		// 消息中心
		case 1:
//			intent.setClass(this, MessageCenterActivity.class);
//			startActivity(intent);
//			break;
		}
		

	}
}
