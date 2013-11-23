package com.pail.myzkbycy.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.bean.CaisNow;
import com.pail.myzkbycy.control.LoginUserPreferences;
import com.pail.myzkbycy.lib.UserFunctions;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

public class ReserveCaiActivity extends BaseActivity {
	private Dialog pDialog;
	private String loginUser;
	private String sess_user_id;

	private TextView tv_mon;
	private TextView tv_tue;
	private TextView tv_wed;
	private TextView tv_thi;
	private TextView tv_fri;
	private TextView tv_cai11;
	private TextView tv_cai12;
	private TextView tv_cai13;
	private TextView tv_cai21;
	private TextView tv_cai22;
	private TextView tv_cai23;
	private TextView tv_cai31;
	private TextView tv_cai32;
	private TextView tv_cai33;
	private TextView tv_cai41;
	private TextView tv_cai42;
	private TextView tv_cai43;
	private TextView tv_cai51;
	private TextView tv_cai52;
	private TextView tv_cai53;

	private Spinner spi_caiA;
	private Spinner spi_caiB;
	private Spinner spi_caiC;

	private LinearLayout ll_update;
	private TextView tx_update_time;
	private Button btn_confirm;

	int modified_inx = -1;
	String temp_time = "";
	String temp_week = "";
	String temp_date = "";
	String year;
	String month;
	String day;

	private String[] datas = new String[15];
	String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	private List<CaisNow> caiAList;
	private List<CaisNow> caiBList;
	private List<CaisNow> caiCList;
	
	private String nameA = "";
	private String nameB = "";
	private String nameC = "";
	private int selectAInx = 0;
	private int selectBInx = 0;
	private int selectCInx = 0;

	Handler handler_show_time = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			tx_update_time.setText("左边菜数量是在此时间 " + temp_date + "  " + temp_week + "  " + temp_time + "查询的");

			return false;
		}
	});
	Handler handler_toast = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Toast.makeText(ReserveCaiActivity.this, msg.getData().getString("msg"), Toast.LENGTH_SHORT).show();
			return false;
		}
	
	});
	Handler handler_visit = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				ll_update.setVisibility(View.GONE);
				break;
			case 1:
				ll_update.setVisibility(View.VISIBLE);

				break;

			default:
				break;
			}
			return false;
		}
	});

	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub

			tv_cai11.setText(datas[0]);
			tv_cai12.setText(datas[1]);
			tv_cai13.setText(datas[2]);
			tv_cai21.setText(datas[3]);
			tv_cai22.setText(datas[4]);
			tv_cai23.setText(datas[5]);
			tv_cai31.setText(datas[6]);
			tv_cai32.setText(datas[7]);
			tv_cai33.setText(datas[8]);
			tv_cai41.setText(datas[9]);
			tv_cai42.setText(datas[10]);
			tv_cai43.setText(datas[11]);
			tv_cai51.setText(datas[12]);
			tv_cai52.setText(datas[12]);
			tv_cai53.setText(datas[14]);

			int dateInx = getWeekOfDate(new Date(Integer.valueOf(year) - 1900,
					Integer.valueOf(month) - 1, Integer.valueOf(day)));
			Long tempLong = 0L;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.valueOf(year), Integer.valueOf(month) - 1,
					Integer.valueOf(day));
			tempLong = cal.getTimeInMillis();
			tempLong += (-dateInx + 1) * 3600 * 24000L;
			cal.setTimeInMillis(tempLong);

			tv_mon.setText("星期一\n" + simpleDateFormat.format(cal.getTime()));
			tempLong += 3600 * 24000L;
			cal.setTimeInMillis(tempLong);
			tv_tue.setText("星期二\n" + simpleDateFormat.format(cal.getTime()));
			tempLong += 3600 * 24000L;
			cal.setTimeInMillis(tempLong);
			tv_wed.setText("星期三\n" + simpleDateFormat.format(cal.getTime()));
			tempLong += 3600 * 24000L;
			cal.setTimeInMillis(tempLong);
			tv_thi.setText("星期四\n" + simpleDateFormat.format(cal.getTime()));
			tempLong += 3600 * 24000L;
			cal.setTimeInMillis(tempLong);
			tv_fri.setText("星期五\n" + simpleDateFormat.format(cal.getTime()));
			switch (dateInx) {
			case 1:
				tv_mon.setTextColor(Color.RED);
				break;
			case 2:

				tv_tue.setTextColor(Color.RED);
				break;
			case 3:

				tv_wed.setTextColor(Color.RED);
				break;
			case 4:
				tv_thi.setTextColor(Color.RED);

				break;
			case 5:
				tv_fri.setTextColor(Color.RED);

				break;

			default:
				break;
			}
			ArrayAdapter<String> adapterA = new ArrayAdapter<String>(
					ReserveCaiActivity.this,
					android.R.layout.simple_spinner_item,
					caiListToStrings(caiAList));
			adapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spi_caiA.setAdapter(adapterA);
			spi_caiA.setVisibility(View.VISIBLE);
			ArrayAdapter<String> adapterB = new ArrayAdapter<String>(
					ReserveCaiActivity.this,
					android.R.layout.simple_spinner_item,
					caiListToStrings(caiBList));
			adapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spi_caiB.setAdapter(adapterB);
			spi_caiB.setVisibility(View.VISIBLE);
			ArrayAdapter<String> adapterC = new ArrayAdapter<String>(
					ReserveCaiActivity.this,
					android.R.layout.simple_spinner_item,
					caiListToStrings(caiCList));
			adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spi_caiC.setAdapter(adapterC);
			spi_caiC.setVisibility(View.VISIBLE);
			return false;
		}

	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ll_update = (LinearLayout) findViewById(R.id.ll_update);
		btn_confirm = (Button) findViewById(R.id.btn_confirm);

		tx_update_time = (TextView) findViewById(R.id.tx_update_time);
		tv_mon = (TextView) findViewById(R.id.tv_mon);
		tv_tue = (TextView) findViewById(R.id.tv_tue);
		tv_wed = (TextView) findViewById(R.id.tv_wed);
		tv_thi = (TextView) findViewById(R.id.tv_thi);
		tv_fri = (TextView) findViewById(R.id.tv_fri);
		tv_cai11 = (TextView) findViewById(R.id.tv_cai11);
		tv_cai12 = (TextView) findViewById(R.id.tv_cai12);
		tv_cai13 = (TextView) findViewById(R.id.tv_cai13);
		tv_cai21 = (TextView) findViewById(R.id.tv_cai21);
		tv_cai22 = (TextView) findViewById(R.id.tv_cai22);
		tv_cai23 = (TextView) findViewById(R.id.tv_cai23);
		tv_cai31 = (TextView) findViewById(R.id.tv_cai31);
		tv_cai32 = (TextView) findViewById(R.id.tv_cai32);
		tv_cai33 = (TextView) findViewById(R.id.tv_cai33);
		tv_cai41 = (TextView) findViewById(R.id.tv_cai41);
		tv_cai42 = (TextView) findViewById(R.id.tv_cai42);
		tv_cai43 = (TextView) findViewById(R.id.tv_cai43);
		tv_cai51 = (TextView) findViewById(R.id.tv_cai51);
		tv_cai52 = (TextView) findViewById(R.id.tv_cai52);
		tv_cai53 = (TextView) findViewById(R.id.tv_cai53);

		spi_caiA = (Spinner) findViewById(R.id.spi_caiA);
		spi_caiB = (Spinner) findViewById(R.id.spi_caiB);
		spi_caiC = (Spinner) findViewById(R.id.spi_caiC);

		spi_caiA.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// Log.i("test", caiAList.get(position).toString());
				selectAInx = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		spi_caiB.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selectBInx = position;

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		spi_caiC.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selectAInx = position;

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		caiAList = new ArrayList<CaisNow>();
		caiBList = new ArrayList<CaisNow>();
		caiCList = new ArrayList<CaisNow>();

		btn_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(caiAList.size() > 0 && spi_caiA.getSelectedItemPosition() == 0) {
					Toast.makeText(ReserveCaiActivity.this, "菜A没有选择", Toast.LENGTH_SHORT).show();
					return;
				}
				if(caiBList.size() > 0 && spi_caiB.getSelectedItemPosition() == 0) {
					Toast.makeText(ReserveCaiActivity.this, "菜B没有选择", Toast.LENGTH_SHORT).show();
					return;
				}
				if(caiCList.size() > 0 && spi_caiC.getSelectedItemPosition() == 0) {
					Toast.makeText(ReserveCaiActivity.this, "菜C没有选择", Toast.LENGTH_SHORT).show();
					return;
				}
				
				//确定修改吗？然后发信息到服务器修改
				String str = "";
				if(!spi_caiA.getSelectedItem().toString().equals("请选择...")) {
					str += spi_caiA.getSelectedItem().toString() + "\n";
				}
				if(!spi_caiB.getSelectedItem().toString().equals("请选择...")) {
					str += spi_caiB.getSelectedItem().toString() + "\n";
				}
				if(!spi_caiC.getSelectedItem().toString().equals("请选择...")) {
					str += spi_caiC.getSelectedItem().toString() + "\n";
				}
				showMessageConfirm(ReserveCaiActivity.this, "确定要更改吗？", "选择修改\n" + str, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						new ModifyCaiFromURL().execute("");
					}
				});
			}
		});
		
		loginUser = LoginUserPreferences.getInstance(this).getLoginUser();
		sess_user_id = LoginUserPreferences.getInstance(this).getValue("sess_user_id");
		new addOrDelFriendFromURL().execute("");
		

	}
	public static void showMessageConfirm(Context c, String Title, String Msg, DialogInterface.OnClickListener onPositiveClickListener) {
		new AlertDialog.Builder(c)
		.setTitle(Title)
		.setMessage(Msg)
		.setCancelable(false)
		.setPositiveButton("确定",onPositiveClickListener)             
	    .setNegativeButton("取消",new DialogInterface.OnClickListener()                 
	    	{               
	    		@Override public void onClick(DialogInterface dialog, int which)                   
	    		{// TODO Auto-generated method stub                     
	    			//finish();  
	    			dialog.dismiss();
	    		}              
	    	}
	    )
	    .show();
	}
	public String[] caiListToStrings(List<CaisNow> list) {
		String[] strings = new String[list.size() + 1];
		strings[0] = "请选择...";
		for (int i = 0; i < list.size(); i++) {
			strings[i + 1] = list.get(i).getTemp_caiName() + "(余:"
					+ list.get(i).getTemp_shenxia_num() + ")";
		}

		return strings;
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.reserve_cai_layout);
		setTopText("预定配菜");
		setBottomVisable(View.GONE);

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
					ReserveCaiActivity.this, "数据处理中，请稍候...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.i("test", "doInBackGround");
			Message msg = new Message();
			Bundle data = new Bundle();
			JSONObject json = UserFunctions.getInstance().getReserveCai(
					loginUser, sess_user_id);
			if (json == null) {
				return "failConnection";
			}
			
			try {
				modified_inx = (Integer) json.get("modified_inx");
				datas[0] = (String) json.get("n0_A");
				datas[1] = (String) json.get("n0_B");
				datas[2] = (String) json.get("n0_C");
				datas[3] = (String) json.get("n1_A");
				datas[4] = (String) json.get("n1_B");
				datas[5] = (String) json.get("n1_C");
				datas[6] = (String) json.get("n2_A");
				datas[7] = (String) json.get("n2_B");
				datas[8] = (String) json.get("n2_C");
				datas[9] = (String) json.get("n3_A");
				datas[10] = (String) json.get("n3_B");
				datas[11] = (String) json.get("n3_C");
				datas[12] = (String) json.get("n4_A");
				datas[13] = (String) json.get("n4_B");
				datas[14] = (String) json.get("n4_C");
				year = (String) json.get("year");
				month = (String) json.get("month");
				day = (String) json.get("day");
				if (modified_inx != -1) {
					JSONObject jsonObject = json.getJSONObject("day_all_cai");
					temp_time = (String) json.get("temp_time");
					
					temp_week = (String) json.get("temp_week");
					temp_date = (String) json.get("temp_date");
					handler_show_time.sendEmptyMessage(0);
					JSONArray jsonArray = jsonObject.names();
					JSONObject jObject;
					for(int i = 0;i < jsonArray.length();i++) {
						jObject = jsonObject.getJSONObject(jsonArray.getString(i));
						CaisNow caisNow = new CaisNow();
						caisNow.setTemp_caiId(jObject.getString("temp_caiId"));
						caisNow.setTemp_caiName(jObject.getString("temp_caiName"));
						caisNow.setTemp_shenxia_num(jObject.getString("temp_shenxia_num"));
						caisNow.setTemp_zhongcai_id(jObject.getString("temp_zhongcai_id"));
						if(Integer.valueOf(caisNow.getTemp_shenxia_num().trim()) <= 0) {
							continue;
						}
						if(jsonArray.getString(i).startsWith("A")) {
							caiAList.add(caisNow);
						} else if(jsonArray.getString(i).startsWith("B")) {
							caiBList.add(caisNow);
						} else if(jsonArray.getString(i).startsWith("C")) {
							caiCList.add(caisNow);
						} 
					}
					handler_visit.sendEmptyMessage(1);
					Log.i("test", jsonArray.toString() + "   " + jsonArray.length());
				} else {
					handler_visit.sendEmptyMessage(0);
				}
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
			handler.sendEmptyMessage(0);
			pDialog.dismiss();
		}
	}

	public int getWeekOfDate(Date dt) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}

		return w;
	}
	class ModifyCaiFromURL extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = UserFunctions.createProgressDialog(
					ReserveCaiActivity.this, "数据处理中，请稍候...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.i("test", "doInBackGround");
			Message msg = new Message();
			Bundle data = new Bundle();
			CaisNow caisNowA = null;
			CaisNow caisNowB = null;
			CaisNow caisNowC = null;
			if(selectAInx != 0) {
				caisNowA = caiAList.get(selectAInx - 1);
			}
			if(selectBInx != 0) {
				caisNowB = caiBList.get(selectBInx - 1);
			}
			if(selectCInx != 0) {
				caisNowC = caiCList.get(selectCInx - 1);
			}
			JSONObject json = UserFunctions.getInstance().modifyReserveCai(
					loginUser, sess_user_id, caisNowA, caisNowB, caisNowC);
			try {
				String temp_update_message = json.getString("temp_update_message");
				Message message = new Message();
				Bundle bundle = new Bundle();
				bundle.putString("msg", temp_update_message);
				message.setData(bundle);
				handler_toast.sendMessage(message);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (json == null) {
				return "failConnection";
			}
			
			return "successConnection";
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			handler.sendEmptyMessage(0);
			pDialog.dismiss();
		}
	}
	

}
