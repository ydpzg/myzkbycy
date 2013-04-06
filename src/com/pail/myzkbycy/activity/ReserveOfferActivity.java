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
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ReserveOfferActivity extends BaseActivity {

//	private ProgressDialog pDialog;
//	private JSONObject json;
	private Button nextBtn;
//	private Plant_Detail[] plant_Details;
//	private ListView listView;
//	private List<Map<String, Object>> listData;
//	private AllPlantAdapter allPlantAdapter;
//	private int isClickInx = -1;
	
//	Handler handler = new Handler(new Handler.Callback() {
//
//		@Override
//		public boolean handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			switch (msg.what) {
//			case 0:
//				String successString = (String) msg.obj;
//				if(successString.equals("1")) {
//					Toast.makeText(ReserveOfferActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
//					unsubscribeBtn.setClickable(false);
//				} else if(successString.equals("0")) {
//					Toast.makeText(ReserveOfferActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
//				}
//				break;
//
//			default:
//				break;
//			}
//			return false;
//		}
//	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.reserve_offer_layout);
		setTopText(getString(R.string.main_reserve_offer));
		setBottomVisable(View.GONE);
		
		nextBtn = (Button) findViewById(R.id.nextBtn); 
		nextBtn.setOnClickListener(this);
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
		if(v == nextBtn) {
			Intent intent = new Intent(ReserveOfferActivity.this, ReserveOffer2Activity.class);
			startActivity(intent);
		}
	}

	
}
