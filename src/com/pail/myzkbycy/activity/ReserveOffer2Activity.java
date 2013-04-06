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
import com.pail.myzkbycy.adapter.ReserveOffer2Adapter;
import com.pail.myzkbycy.bean.NotificationData;
import com.pail.myzkbycy.bean.Plant_Detail;
import com.pail.myzkbycy.bean.UserInfData;
import com.pail.myzkbycy.constants.Constant;
import com.pail.myzkbycy.control.HistroyUserPreferences;
import com.pail.myzkbycy.lib.UserFunctions;
import com.pail.myzkbycy.lib.UserModel;
import com.pail.myzkbycy.util.DialogUtil;
import com.pail.myzkbycy.util.NetworkUtil;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ReserveOffer2Activity extends BaseActivity implements
		OnGestureListener {

	// private ProgressDialog pDialog;
	// private JSONObject json;
	private Spinner spinner1, spinner2, spinner3;
	private ViewFlipper flipper;
	private GestureDetector detector;
	private ListView listView;
	private Button submitBtn;
	private List<Map<String, Object>> listData;
	private final String[] weeks = { "第一周", "第二周", "第三周", "第四周", "第五周" };
	private ReserveOffer2Adapter reserveOffer2Adapter;
	private final String[] weekNameStrings = { "Mon", "Tue", "Wed", "Thr",
			"Fri" };

	// private int isClickInx = -1;

	// Handler handler = new Handler(new Handler.Callback() {
	//
	// @Override
	// public boolean handleMessage(Message msg) {
	// // TODO Auto-generated method stub
	// allPlantAdapter.notifyDataSetChanged();
	// return false;
	// }
	// });

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// new addOrDelFriendFromURL().execute("");

	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.reserve_offer_2_layout);
		setTopText("预定配菜");
		setBottomVisable(View.GONE);

		detector = new GestureDetector(this);
		flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper);

		submitBtn = (Button) findViewById(R.id.submit);
		submitBtn.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.list);
		reserveOffer2Adapter = new ReserveOffer2Adapter(this);
		listView.setAdapter(reserveOffer2Adapter);
		listView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return onTouchEvent(event);
			}
		});
		// listData = getData("暂无");
		// reserveOffer2Adapter.setListItem(listData);

		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner3 = (Spinner) findViewById(R.id.spinner3);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		for (int i = 0; i < weeks.length; i++) {
			adapter.add(weeks[i]);
		}
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i("test", "position=" + position);
				Toast.makeText(ReserveOffer2Activity.this,
						"已经切换到" + weeks[position], Toast.LENGTH_SHORT).show();
				listData = getData(position + "");
				reserveOffer2Adapter.setListItem(listData);
				reserveOffer2Adapter.notifyDataSetChanged();
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		spinner.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return onTouchEvent(event);
			}
		});
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		for (int i = 0; i < 4; i++) {
			if(i == 0) {
				adapter1.add("");
			} else {
				adapter1.add("11111" + i);
			}
		}
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter1);
		spinner1.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return onTouchEvent(event);
			}
		});
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		for (int i = 0; i < 4; i++) {
			if(i == 0) {
				adapter2.add("");
			} else {
				adapter2.add("22222" + i);
			}
		}
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter2);
		spinner2.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return onTouchEvent(event);
			}
		});
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		for (int i = 0; i < 4; i++) {
			if(i == 0) {
				adapter3.add("");
			} else {
				adapter3.add("33333" + i);
			} 
		}
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner3.setAdapter(adapter3);
		spinner3.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return onTouchEvent(event);
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
		if(v == submitBtn) {
			
		}
	}

	public List<Map<String, Object>> getData(String str) {
		List<Map<String, Object>> lData = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (int i = 0; i < weekNameStrings.length; i++) {
			map = new HashMap<String, Object>();
			map.put("item_date", weekNameStrings[i]);
			map.put("plant1", str + 0);
			map.put("plant2", str + 00);
			map.put("plant3", str + 000);
			lData.add(map);
		}

		return lData;
	}

	public boolean onDoubleTap(MotionEvent e) {
		if (flipper.isFlipping()) {
			flipper.stopFlipping();
		} else {
			flipper.startFlipping();
		}
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		// 用户按下屏幕，快速移动后松开（就是在屏幕上滑动）
		// e1:第一个ACTION_DOWN事件（手指按下的那一点）
		// e2:最后一个ACTION_MOVE事件 （手指松开的那一点）
		// velocityX:手指在x轴移动的速度 单位：像素/秒
		// velocityY:手指在y轴移动的速度 单位：像素/秒

		if (e1.getX() - e2.getX() > 60) {
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_left_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_left_out));
			this.flipper.showNext();
			Log.i("test", flipper.getCurrentView().getClass() + "");
			Log.i("test", flipper.getCurrentView().getContext() + "");
			return true;
		} else if (e1.getX() - e2.getX() < -60) {
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_right_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_right_out));
			this.flipper.showPrevious();
			Log.i("test", flipper.getCurrentView().getClass() + "");
			Log.i("test", flipper.getCurrentView().getContext() + "");
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.detector.onTouchEvent(event);
	}
}
