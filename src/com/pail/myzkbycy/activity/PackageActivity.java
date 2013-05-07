package com.pail.myzkbycy.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.activity.UnsubscribeActivity.cancelServerFromURL;
import com.pail.myzkbycy.activity.UnsubscribeActivity.getUserActiveFromURL;
import com.pail.myzkbycy.adapter.AllPlantAdapter;
import com.pail.myzkbycy.bean.NotificationData;
import com.pail.myzkbycy.bean.Plant_Detail;
import com.pail.myzkbycy.bean.SetMealModel;
import com.pail.myzkbycy.bean.UserInfData;
import com.pail.myzkbycy.constants.Constant;
import com.pail.myzkbycy.control.AutoLoginPreferences;
import com.pail.myzkbycy.control.HistroyUserPreferences;
import com.pail.myzkbycy.control.LoginUserPreferences;
import com.pail.myzkbycy.control.SetPreferences;
import com.pail.myzkbycy.lib.UserFunctions;
import com.pail.myzkbycy.lib.UserModel;
import com.pail.myzkbycy.util.DialogUtil;
import com.pail.myzkbycy.util.NetworkUtil;
import com.pail.myzkbycy.util.PxUtil;

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

public class PackageActivity extends BaseActivity {
	private LinearLayout setmeal_LL;
	private int packageChooseInx = 1;
	private Button submit_btn;
	private TextView serverState_TV;
	private int serverPackageInx;
	private ProgressDialog pDialog;
	private String loginUser;
	private String temp_setmealname;
	private String stateStr, setMealId;
	private ArrayList<SetMealModel> setMealModels; 
	private ArrayList<CheckBox> checkBoxs; 
	
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				String successString = (String) msg.obj;
				if(successString.equals("1")) {
					Toast.makeText(PackageActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
					serverPackageInx = packageChooseInx;
				} else if(successString.equals("0")) {
					Toast.makeText(PackageActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
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
		if(NetworkUtil.getInstance().isNetworkGood(PackageActivity.this)){
			new getPackageInfFromURL().execute("", "n");
		} else {
			DialogUtil.getInstance().showTipDialog(PackageActivity.this,
					 "网络连接不正常，请检查");
		}
		
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.package_layout);
		setTopText("套餐信息");
		setBottomVisable(View.GONE);

		loginUser = LoginUserPreferences.getInstance(this).getLoginUser();
		 
		submit_btn = (Button) findViewById(R.id.submit_btn);
		setmeal_LL = (LinearLayout) findViewById(R.id.setmeal_LL);
		serverState_TV = (TextView) findViewById(R.id.serverState_TV);
		
		submit_btn.setOnClickListener(this);
		
		
	}
	private void checkSet(int inx) {
		for(int i = 0;i < checkBoxs.size();i++) {
			if(i == inx - 1) {
				checkBoxs.get(i).setChecked(true);
			} else {
				checkBoxs.get(i).setChecked(false);
			}
		}
	}

	private int checkSetmeal(String setmealName) {
		int inx = 1;
		inx = setmealName.charAt(0) - 'A' + 1;
		Log.i("inx", inx + "");
		return inx;
	}

	private int transSetMealID(String str) {
		int inx = 1;
		int i;
		for(i = 0;i < setMealModels.size();i++) {
			if(setMealModels.get(i).getTemp_setid().equals(str)) {
				break;
			}
		}
		if(i < setMealModels.size()) {
			inx = i + 1;
		}
		return inx;
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
		if(v == submit_btn) {
			String tempStr = "";
			if(packageChooseInx == serverPackageInx) {
				tempStr = "要更改的套餐跟之前的一样，无需更改";
			} else {
				tempStr = "确定要更改套餐吗？此操作不能撤销！";
			}
			
			OnClickListener onClickListener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(packageChooseInx != serverPackageInx) {
						new getPackageInfFromURL().execute(setMealModels.get(packageChooseInx - 1).getTemp_setid(), "y");
					}
					DialogUtil.dismissDialog();
				}
			};
			DialogUtil.showTip2Dialog(PackageActivity.this, tempStr, onClickListener);
		}
	}
	class getPackageInfFromURL extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = UserFunctions.createProgressDialog(PackageActivity.this,
					"数据处理中，请稍候...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			Bundle data = new Bundle();
			JSONObject json = UserFunctions.getInstance().getSetmeal(loginUser, params[0], params[1]);
			if (json == null) {
				return "failConnection";
			}
			try {
				int i = 0;
				setMealModels = new ArrayList<SetMealModel>();
				while(!json.isNull("n" + i)) {
					SetMealModel setMealModel = new SetMealModel();
					JSONObject jsonObject = json.getJSONObject("n" + i);
					setMealModel.setTemp_class(jsonObject.getString("temp_class").toString());
					setMealModel.setTemp_price(jsonObject.getString("temp_price").toString());
					setMealModel.setTemp_content(jsonObject.getString("temp_content").toString());
					setMealModel.setTemp_setid(jsonObject.getString("temp_setid").toString());
					setMealModels.add(setMealModel);
					i++;
				}
				
				temp_setmealname = json.getString("temp_setmealname").toString();
				stateStr = json.getString("stateStr").toString();
				setMealId = json.getString("temp_user_setmealID").toString();
				serverPackageInx = transSetMealID(setMealId);
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
				DialogUtil.getInstance().showTipDialog(PackageActivity.this,
						"无法连接上服务器");
			} else {
				if(setmeal_LL.getChildCount() == 0) {
					addSetMealViews();
				}
				checkSet(serverPackageInx);
				serverState_TV.setText(stateStr);
				packageChooseInx = serverPackageInx;
				submit_btn.setVisibility(View.VISIBLE);
				if(stateStr.startsWith("已") || stateStr.startsWith("套餐更改已通过审核")) {
					submit_btn.setVisibility(View.GONE);
				} else {
					submit_btn.setVisibility(View.VISIBLE);
				}
			}
		}
	}
	public void removeSelMealViews() {
		setmeal_LL.removeAllViews();
	}
	public void addSetMealViews() {
		checkBoxs = new ArrayList<CheckBox>();
		for(int i = 0;i < setMealModels.size();i++) {
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.FILL_PARENT, android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(0, PxUtil.dip2px(this, 14), 0, 0);
			RelativeLayout relativeLayout = new RelativeLayout(this);
			if(setMealModels.size() == 1) {
				relativeLayout.setBackgroundResource(R.drawable.preference_single_item);
			} else {
				if(i == 0) {
					relativeLayout.setBackgroundResource(R.drawable.preference_first_item);
				} else if(i == setMealModels.size() - 1) {
					relativeLayout.setBackgroundResource(R.drawable.preference_last_item);
				} else {
					relativeLayout.setBackgroundResource(R.drawable.preference_item);
				}
			}
			relativeLayout.setClickable(true);
			relativeLayout.setGravity(Gravity.CENTER_VERTICAL);
			
			LinearLayout linearLayout = new LinearLayout(this);
			linearLayout.setOrientation(LinearLayout.VERTICAL);
			linearLayout.setPadding(0, 0, PxUtil.dip2px(this, 50), 0);
			relativeLayout.addView(linearLayout);
			
			
			TextView setMealTextView = new TextView(this);
			setMealTextView.setSingleLine(true);
			setMealTextView.setTextColor(getResources().getColor(R.color.black));
			setMealTextView.setTextSize(PxUtil.dip2px(this, 8));
			setMealTextView.setPadding(PxUtil.dip2px(this, 2), PxUtil.dip2px(this, 2), PxUtil.dip2px(this, 2), PxUtil.dip2px(this, 2));
			setMealTextView.setText("套餐" + setMealModels.get(i).getTemp_class() + "   " + setMealModels.get(i).getTemp_price() + " 元/月");
			linearLayout.addView(setMealTextView);
			
			TextView contentTextView = new TextView(this);
			contentTextView.setTextColor(getResources().getColor(R.color.black));
			contentTextView.setTextSize(PxUtil.dip2px(this, 7));
			contentTextView.setPadding(PxUtil.dip2px(this, 2), PxUtil.dip2px(this, 2), PxUtil.dip2px(this, 2), PxUtil.dip2px(this, 2));
			contentTextView.setText(setMealModels.get(i).getTemp_content());
			linearLayout.addView(contentTextView);
			
			CheckBox checkBox = new CheckBox(this);
			RelativeLayout.LayoutParams checkBoxLayoutParams = new RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT, android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
			checkBoxLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
			checkBoxLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			checkBox.setLayoutParams(checkBoxLayoutParams);
			checkBox.setClickable(false);
			checkBox.setButtonDrawable(R.drawable.checkbox);
			checkBoxs.add(i, checkBox);
			relativeLayout.addView(checkBoxs.get(i));
			relativeLayout.setTag(i);
			relativeLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(packageChooseInx != Integer.valueOf(v.getTag().toString()) + 1) {
						for(int j = 0;j < checkBoxs.size();j++) {
							checkBoxs.get(j).setChecked(false);
						}
						checkBoxs.get(Integer.valueOf(v.getTag().toString())).setChecked(true);
						packageChooseInx = Integer.valueOf(v.getTag().toString()) + 1;
						Log.i("click" , packageChooseInx + "");
					}
				}
			});
			
			relativeLayout.setLayoutParams(layoutParams);
			setmeal_LL.addView(relativeLayout);
		}
	}
}
