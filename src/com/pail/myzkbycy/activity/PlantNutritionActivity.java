package com.pail.myzkbycy.activity;

import java.net.URL;
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
import com.pail.myzkbycy.bean.PlantInfo;
import com.pail.myzkbycy.bean.Plant_Detail;
import com.pail.myzkbycy.bean.UserInfData;
import com.pail.myzkbycy.constants.Constant;
import com.pail.myzkbycy.control.AsynImageLoader;
import com.pail.myzkbycy.control.HistroyUserPreferences;
import com.pail.myzkbycy.dao.DaoCenter;
import com.pail.myzkbycy.lib.UserFunctions;
import com.pail.myzkbycy.lib.UserModel;
import com.pail.myzkbycy.util.DialogUtil;
import com.pail.myzkbycy.util.FileUtil;
import com.pail.myzkbycy.util.NetworkUtil;
import com.pail.myzkbycy.util.PicUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.webkit.URLUtil;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class PlantNutritionActivity extends BaseActivity {

	private ProgressDialog pDialog;
	private JSONObject json;
	private String plantId;
	private String content, title, picId, webId;
	private TextView title_TV, content_TV, tip_TV;
	private ImageView plant_pic;
	private Bitmap bitmap;

	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				tip_TV.setVisibility(View.VISIBLE);
				break;
			case 1:
				Drawable tempDrawable = (Drawable) msg.obj;
				plant_pic.setBackgroundDrawable(tempDrawable);
				tip_TV.setVisibility(View.GONE);
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
		
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.plant_nutrition);
		setTopText("营养价值");
		setBottomVisable(View.GONE);

		title_TV = (TextView) findViewById(R.id.title_TV);
		content_TV = (TextView) findViewById(R.id.content_TV);
		tip_TV = (TextView) findViewById(R.id.tip_TV);
		plant_pic = (ImageView) findViewById(R.id.plant_pic);
		
		plantId = getIntent().getStringExtra("plantId");
		String classnameString = getIntent().getStringExtra("classname");

		if(classnameString.equals(Constant.ALL_PLANT_ACTIVITY)) {
			ArrayList<Object> ddArrayList = DaoCenter.getInstance().getDao()
					.queryOneData("plantinfo", PlantInfo.class, "plantId=" + plantId);
			if (ddArrayList != null) {
				PlantInfo plantInfo = (PlantInfo)ddArrayList.get(0);
				title_TV.setText(plantInfo.getPlantName());
				content_TV.setText(Html.fromHtml(plantInfo.getPlantContext()));
				bitmap = FileUtil.getImageFromAssetsFile(this, "pic/" + plantInfo.getPicName() + ".jpg");
				plant_pic.setImageBitmap(bitmap);
			} else {
				Log.i("test", "ddArrayList = null");
			}
		} else if(classnameString.equals(Constant.WEEK_OFFER_ACTIVITY)) {
			new addOrDelFriendFromURL().execute("");
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
	}

	class addOrDelFriendFromURL extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = UserFunctions.createProgressDialog(
					PlantNutritionActivity.this, "后台忙碌中，请稍后...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			UserFunctions userFunction = new UserFunctions();
			Message msg = new Message();
			Bundle data = new Bundle();
			JSONObject json = userFunction.getPlantDetail(plantId,
					"nutrition_value");
			if (json == null) {
				return "failConnection";
			}
			try {
				title = json.getString("title").toString();
				content = json.getString("content").toString();
				picId = json.getString("pic_link").toString();
				webId = json.getString("web_link").toString();
				Log.i("test", "title=" + title);
				Log.i("test", "content=" + content);
				Log.i("test", "picId=" + picId);
				Log.i("test", "webId=" + webId);
				
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Log.i("download", "111");
						handler.sendEmptyMessage(0);
						Drawable tempBitmap = PicUtil
								.getcontentPic("http://192.168.1.124/my_zkbycy/upload_pic/" + picId);
						Message msg = new Message();
						msg.obj = tempBitmap;
						msg.what = 1;
						handler.sendMessage(msg);
						Log.i("download", "222");
					}
				}).start();
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
				DialogUtil.getInstance().showTipDialog(
						PlantNutritionActivity.this, "无法连接上服务器");
			} else {
				title_TV.setText(title);
				content_TV.setText(Html.fromHtml(content));
				// AsynImageLoader asynImageLoader = new AsynImageLoader();
				// asynImageLoader.showImageAsyn(plant_pic,
				// "http://192.168.1.124/my_zkbycy/upload_pic/" + picId,
				// R.drawable.ic_launcher);
				Log.i("html", content);
			}
		}
	}

}
