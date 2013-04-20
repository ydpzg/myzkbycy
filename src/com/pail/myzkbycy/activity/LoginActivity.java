package com.pail.myzkbycy.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.bean.PlantInfo;
import com.pail.myzkbycy.constants.Constant;
import com.pail.myzkbycy.control.AutoLoginPreferences;
import com.pail.myzkbycy.control.HistroyUserPreferences;
import com.pail.myzkbycy.control.LoginUserPreferences;
import com.pail.myzkbycy.dao.DaoCenter;
import com.pail.myzkbycy.dao.LSqlContants;
import com.pail.myzkbycy.lib.UserFunctions;
import com.pail.myzkbycy.lib.UserModel;
import com.pail.myzkbycy.util.DialogUtil;
import com.pail.myzkbycy.util.NetworkUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginActivity extends BaseActivity {
	private EditText et_userName, et_password;
	private Button btn_historyUser, btn_login, btn_regist;
	private CheckBox cb_rememberPassword, cb_autoLogin;
	private LinearLayout llt_userName;
	private DialogUtil dialogUtil;
	private int width;
	private List<String> historyUsers;
	private Map<String, String> histroyList;
	private ListView userList;
	private MyAdapter adapter;
	private PopupWindow popupWindow;
	boolean flag = false;
	boolean isRemeberPwd = false;
	private HistroyUserPreferences histroyUserPreferences;
	private String passwordText;
	private ProgressDialog pDialog;
	private JSONObject json;
	private AutoLoginPreferences autoLoginPreferences;
	private DaoCenter dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		checkDatabase(); 
		initDao();
		if(autoLoginPreferences.getAutoLogin("auto_login")) {
			String loginName = AutoLoginPreferences.getInstance(this).getNameValue("autoLoginName");
			String pw = HistroyUserPreferences.getInstance(this).getHistoryPW(loginName);
			et_userName.setText(loginName);
			et_password.setText(pw);
			cb_autoLogin.setChecked(true);
			btn_login.performClick();
		}
	}
	public void checkDatabase() {
		// 创建数据库
		try {
			File dbf = new File("/data/data/" + getPackageName() + "/databases");
			Log.i("test", "/data/data/" + getPackageName());
			if (!dbf.exists()) {
				dbf.mkdirs();
				// 复制asseets中的db文件到DB_PATH下
				copyDataBase();
//				SQLiteDatabase.openOrCreateDatabase(dbf, null);
			}
		} catch (IOException e) {
			throw new Error("数据库创建失败");
		} finally {
			Log.i("test", "success");
		}
	}
	public void initDao() {
		dao = DaoCenter.getInstance();
		dao.initDaoCenter(this);
		dao.open();
//		ArrayList<Object> ddArrayList = DaoCenter.getInstance().getDao()
//				.queryAllData("plantinfo", PlantInfo.class);
//		if (ddArrayList != null) {
//			// 数据库已存在，do nothing.
//			Log.i("test", "has one");
//			for(int i = 0; i < ddArrayList.size();i++) {
//				Log.i("test", ((PlantInfo)ddArrayList.get(i)).toString());
//				PlantInfo plantInfo = (PlantInfo)ddArrayList.get(i);
//			}
//			PlantInfo plantInfo = (PlantInfo)ddArrayList.get(5);
//		} else {
//			// 创建数据库
//			try {
//				File dir = new File(getDatabasePath(LSqlContants.DB_NAME).getAbsolutePath());
//				if (!dir.exists()) {
//					dir.mkdirs();
//				}
//				File dbf = new File(getDatabasePath(LSqlContants.DB_NAME).getAbsolutePath());
//				if (dbf.exists()) {
//					dbf.delete();
//				}
//				SQLiteDatabase.openOrCreateDatabase(dbf, null);
//				// 复制asseets中的db文件到DB_PATH下
//				copyDataBase();
//			} catch (IOException e) {
//				throw new Error("数据库创建失败");
//			} finally {
//				Log.i("test", "success");
//			}
//		}
	}
	private void copyDataBase() throws IOException {
		// Open your local db as the input stream
		InputStream myInput = this.getAssets().open(LSqlContants.DB_NAME);
		// Path to the just created empty db
		String outFileName = getDatabasePath(LSqlContants.DB_NAME).getAbsolutePath();
		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if (!flag) {
			initwight();
			flag = true;
		}
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		dialogUtil = DialogUtil.getInstance();
		histroyUserPreferences = HistroyUserPreferences.getInstance(this);
		autoLoginPreferences = AutoLoginPreferences.getInstance(this);
		
		
		
		setCenterView(R.layout.login_layout);
		et_password = (EditText) this.findViewById(R.id.login_password_et);
		et_userName = (EditText) this.findViewById(R.id.login_inputusername_et);
		btn_historyUser = (Button) this.findViewById(R.id.login_showhitory_btn);
		btn_login = (Button) this.findViewById(R.id.login_userlogin_btn);
		cb_autoLogin = (CheckBox) this.findViewById(R.id.login_autologin_cb);
		cb_rememberPassword = (CheckBox) this
				.findViewById(R.id.login_rememberpassword_cb);
		setTopText(getString(R.string.user_login));
		setBottomVisable(View.GONE);
		setRightButton(R.drawable.logo, View.GONE, null);
		setLeftButton(R.drawable.logo, View.GONE, null);
		historyUsers = new ArrayList<String>();
		historyUsers.add(getString(R.string.login_cleanHistory));
		histroyList = histroyUserPreferences.quallyAllHistoryUser();
		Map<String, ?> allData = histroyList;
		Set<String> keySet = allData.keySet();
		for (String tempUserName : keySet) {
			if (!"".equals(tempUserName)) {
				historyUsers.add(tempUserName);
			}
		}
	}

	// 历史用户登录弹窗
	public void initwight() {
		llt_userName = (LinearLayout) this
				.findViewById(R.id.login_username_llt);
		width = llt_userName.getWidth();
		initPopup();
		btn_historyUser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showPopup();

			}
		});
	}

	// 历史用户登录弹窗
	public void initPopup() {
		View loginView = (View) this.getLayoutInflater().inflate(
				R.layout.listview, null);
		userList = (ListView) loginView.findViewById(R.id.listView1);
		adapter = new MyAdapter(getApplicationContext(), historyUsers);
		userList.setAdapter(adapter);
		popupWindow = new PopupWindow(loginView, width,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		userList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				if (position == 0) {
					histroyUserPreferences.clearAllHistoryUser();
					historyUsers.clear();
					historyUsers.add(getString(R.string.login_cleanHistory));
					adapter.notifyDataSetChanged();
				} else {
					et_userName.setText(historyUsers.get(position));
				}
				popupWindow.dismiss();
			}
		});
	}

	public void showPopup() {
		popupWindow.showAsDropDown(llt_userName, 0, -3);
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		btn_historyUser.setOnClickListener(this);
		btn_login.setOnClickListener(this);
		// btn_regist.setOnClickListener(this);
		cb_autoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					cb_rememberPassword.setChecked(true);
				}
			}
		});
		cb_rememberPassword
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						isRemeberPwd = isChecked;
						if (!isChecked) {
							cb_autoLogin.setChecked(false);
						}
					}
				});
		et_userName.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				// 如果该用户记住密码，则输入完用户名后可以输入密码
				passwordText = (String) histroyList.get(arg0 + "");
				if (!"".equals(passwordText)) {
					et_password.setText(passwordText);
					cb_rememberPassword.setChecked(true);
				} else {
					et_password.setText("");
					cb_rememberPassword.setChecked(false);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
		super.initListener();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if (v == btn_historyUser) {
			showHistoryUser();
		}
		if (v == btn_login) {
			if (!Constant.Debug_Model) {
				userLogin();
			} else {
//				UserModel userModel = new UserModel();
//				userModel.setUser("11122233344");
				turnToMainActivity();
			}
		}
	}

	/**
	 * 忘记密码按钮监听事件
	 */
	// public void forgetPassword() {
	// Memeber memeber = new Memeber();
	// startToNextActivity(GetAuthCodeActivity.class, memeber);
	// }

	/**
	 * 显示历史用户按钮监听事件
	 */
	public void showHistoryUser() {
		toastPlay("历史用户", getApplicationContext());
	}

	/**
	 * 用户登录按钮监听事件
	 */
	public void userLogin() {
		// 检测网络是否连接
		if (!NetworkUtil.getInstance().isNetworkGood(this)) {
			dialogUtil.getInstance().showTipDialog(this, "网络连接不正常，请检查");
			return;
		}
		if (et_userName.getText().length() != 11) {
			dialogUtil.showTipDialog(this, "手机号码不足11位", null, "确定");
		} else {
			new addOrDelFriendFromURL().execute("1");
		}
		 
		// if (et_userName.getText().length() > 16
		// | et_userName.getText().length() < 6) {
		// dialogUtil.showTipDialog(this,
		// getString(R.string.login_name_unlaw), null,
		// getString(R.string.more_sure));
		// } else if (et_password.getText().length() > 10
		// | et_password.getText().length() < 6) {
		// dialogUtil.showTipDialog(this,
		// getString(R.string.password_update_nulaw), null,
		// getString(R.string.more_sure));
		// } else {
		// if (isRemeberPwd) {
		// histroyUserPreferences.recordHistoryUser(et_userName.getText()
		// .toString(), et_password.getText().toString());
		// } else {
		// histroyUserPreferences.recordHistoryUser(et_userName.getText()
		// .toString(), "");
		// }
		// }
	}

	/**
	 * 用户注册按钮监听事件
	 */
	// public void userRegist() {
	// startToNextActivity(RegistActivity.class);
	// }

	/**
	 * 用户不存在时的对话框
	 */
	public void showNoUserDialog() {
		// dialogUtil.showTipDialog(this, getString(R.string.login_nouser),
		// null,
		// getString(R.string.more_sure));
	}

	class MyAdapter extends BaseAdapter {
		List<String> histroyUser;
		Context context;
		LayoutInflater inflater;

		public MyAdapter(Context context, List<String> histroy) {
			inflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			this.histroyUser = histroy;
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return histroyUser.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = null;
			ViewHolder holder = null;
			if (convertView == null || convertView.getTag() == null) {
				view = inflater.inflate(R.layout.listitem, null);
				holder = new ViewHolder(view);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) convertView.getTag();
			}
			view.setMinimumHeight(60);
			holder.textView.setText(histroyUser.get(position));
			return view;
		}

		class ViewHolder {
			TextView textView;

			public ViewHolder(View view) {
				textView = (TextView) view.findViewById(R.id.userHistroyName);
			}
		}

	}

	public void turnToMainActivity() {
		Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//		intent.putExtra("userModel", userModel);
		LoginUserPreferences.getInstance(this).setLoginUser(et_userName.getText().toString());
		startActivity(intent);
	}

	class addOrDelFriendFromURL extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = UserFunctions.createProgressDialog(LoginActivity.this,
					"正在登陆中，请稍后...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			Bundle data = new Bundle();
			try {
				json = UserFunctions.getInstance().loginUser(et_userName.getText().toString(),
						et_password.getText().toString());

			} catch (Exception e) {
				// TODO: handle exception
			}
			if (json == null) {
				return "failConnection";
			}
			// notificationDatas = new NotificationData[json.length()];
			// for (int i = 0; i < json.length(); i++) {
			// try {
			// notificationDatas[i] = new NotificationData();
			// JSONObject tempJsonObject = json.getJSONObject(json.names()
			// .get(i).toString());
			// notificationDatas[i].setNews_title(tempJsonObject
			// .getString("news_title").toString());
			// notificationDatas[i].setNews_content(tempJsonObject
			// .getString("news_content").toString());
			// notificationDatas[i].setNews_data(tempJsonObject.getString(
			// "news_data").toString());
			// notificationDatas[i].setNews_id(tempJsonObject.getString(
			// "news_id").toString());
			// Log.i("json", notificationDatas[i].getNews_content());
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }

			return "successConnection";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			if (result.equals("failConnection")) {
				DialogUtil.getInstance().showTipDialog(LoginActivity.this,
						"无法连接上服务器");
			} else {
				try {
					String successString = json.getString("success");

					if (successString.equals("1")) {
						// Toast.makeText(LoginActivity.this, "登陆成功",
						// Toast.LENGTH_SHORT).show();
						
						if (isRemeberPwd) {
							histroyUserPreferences.recordHistoryUser(et_userName.getText()
									.toString(), et_password.getText().toString());
						} else {
							histroyUserPreferences.recordHistoryUser(et_userName.getText()
									.toString(), "");
						}
						if (cb_autoLogin.isChecked()) {
							autoLoginPreferences.setAutoLogin("auto_login");
							autoLoginPreferences.setNameValue("autoLoginName", et_userName.getText().toString());
						} else {
							autoLoginPreferences.removeAutoLogin("auto_login");
						}
						autoLoginPreferences.setNameValue("user", et_userName.getText().toString());
						autoLoginPreferences.setNameValue("password", et_password.getText().toString());
//						
//						UserModel userModel = new UserModel();
//						userModel.setUser(et_userName.getText().toString());
						turnToMainActivity();
						LoginActivity.this.finish();
					} else {
						DialogUtil.getInstance().showTipDialog(
								LoginActivity.this, "登陆失败");
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// for(int i = 0;i < notificationDatas.length;i++) {
			// addSonViewToLayout(notificationDatas[i]);
			// Log.i("test", notificationDatas[i].getNews_data());
			// }
		}
	}
}
