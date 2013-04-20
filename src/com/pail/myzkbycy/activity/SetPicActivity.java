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
import com.pail.myzkbycy.control.AutoLoginPreferences;
import com.pail.myzkbycy.control.HistroyUserPreferences;
import com.pail.myzkbycy.control.SetPreferences;
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
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SetPicActivity extends BaseActivity {
	private RelativeLayout set1_RL, set2_RL;
	private CheckBox set1_cb, set2_cb;
	private int setPicChooseInx = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.set_pic_layout);
		setTopText("设置管理");
		setBottomVisable(View.GONE);

		set1_RL = (RelativeLayout) findViewById(R.id.set1_RL);
		set2_RL = (RelativeLayout) findViewById(R.id.set2_RL);
		set1_cb = (CheckBox) findViewById(R.id.set1_cb);
		set2_cb = (CheckBox) findViewById(R.id.set2_cb);
		
		set1_RL.setOnClickListener(this);
		set2_RL.setOnClickListener(this);
		
		setPicChooseInx = SetPreferences.getInstance(SetPicActivity.this).getPicDownload();
		if(setPicChooseInx == 2) {
			set1_cb.setChecked(false);
			set2_cb.setChecked(true);
		} else {
			set1_cb.setChecked(true);
			set2_cb.setChecked(false);
		}
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
		if(v == set1_RL) {
			if(setPicChooseInx != 1) {
				set1_cb.setChecked(true);
				set2_cb.setChecked(false);
				setPicChooseInx = 1;
				SetPreferences.getInstance(SetPicActivity.this).setPicDownload(1);
			}
		}
		if(v == set2_RL) {
			if(setPicChooseInx != 2) {
				set1_cb.setChecked(false);
				set2_cb.setChecked(true);
				setPicChooseInx = 2;
				SetPreferences.getInstance(SetPicActivity.this).setPicDownload(2);
			}
		}
//		if(v == about_RL) {
//			Intent intent = new Intent(SetPicActivity.this, AboutActivity.class);
//			startActivity(intent);
//		}
//		if(v == reLogin_RL) {
//			AutoLoginPreferences.getInstance(this).removeAutoLogin("auto_login");
//			Intent intent = new Intent(SetPicActivity.this, LoginActivity.class);
//			startActivity(intent);
//			setResult(Activity.RESULT_OK);
//			this.finish();
//		}
	}

}
