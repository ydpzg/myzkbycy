package com.pail.myzkbycy.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.bean.PlantInfo;
import com.pail.myzkbycy.constants.Constant;
import com.pail.myzkbycy.control.SetPreferences;
import com.pail.myzkbycy.dao.DaoCenter;
import com.pail.myzkbycy.lib.UserFunctions;
import com.pail.myzkbycy.util.DialogUtil;
import com.pail.myzkbycy.util.FileUtil;
import com.pail.myzkbycy.util.NetworkUtil;
import com.pail.myzkbycy.util.PicUtil;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
			case 2:
				tip_TV.setText("当前网络不是Wifi网络，不显示图片！");
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
			if(NetworkUtil.getInstance().isNetworkGood(PlantNutritionActivity.this)){
				new addOrDelFriendFromURL().execute("");
			} else {
				DialogUtil.getInstance().showTipDialog(PlantNutritionActivity.this,
						 "网络连接不正常，请检查");
			}
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
					PlantNutritionActivity.this, "数据处理中，请稍候...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			Bundle data = new Bundle();
			JSONObject json = UserFunctions.getInstance().getPlantDetail(plantId,
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
//						Drawable tempBitmap = PicUtil
//								.getcontentPic("http://192.168.1.124/my_zkbycy/upload_pic/" + picId);
						int picSaveID = SetPreferences.getInstance(PlantNutritionActivity.this).getPicDownload();
						if(picSaveID == 2 || NetworkUtil.getInstance().isWifiWork(PlantNutritionActivity.this)) {
							Drawable tempBitmap = PicUtil
									.getcontentPic("http://www.zkbycy.com/upload_pic/" + picId);
							Message msg = new Message();
							msg.obj = tempBitmap;
							msg.what = 1;
							handler.sendMessage(msg);
							Log.i("download", "222");
						} else {
							handler.sendEmptyMessage(2);
						}
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
