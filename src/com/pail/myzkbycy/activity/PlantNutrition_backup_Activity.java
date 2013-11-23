package com.pail.myzkbycy.activity;


import org.json.JSONException;
import org.json.JSONObject;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.lib.UserFunctions;
import com.pail.myzkbycy.util.DialogUtil;
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

public class PlantNutrition_backup_Activity extends BaseActivity {

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

		if(NetworkUtil.getInstance().isNetworkGood(PlantNutrition_backup_Activity.this)){
			new addOrDelFriendFromURL().execute("");
		} else {
			DialogUtil.getInstance().showTipDialog(PlantNutrition_backup_Activity.this,
					 "网络连接不正常，请检查");
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
					PlantNutrition_backup_Activity.this, "数据处理中，请稍候...");
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
						PlantNutrition_backup_Activity.this, "无法连接上服务器");
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
