package com.pail.myzkbycy.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.adapter.AllPlantAdapter;
import com.pail.myzkbycy.bean.PlantInfo;
import com.pail.myzkbycy.bean.Plant_Detail;
import com.pail.myzkbycy.lib.UserFunctions;
import com.pail.myzkbycy.util.DialogUtil;
import com.pail.myzkbycy.util.NetworkUtil;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AllPlant_backup_Activity extends BaseActivity {

	private ProgressDialog pDialog;
	private Plant_Detail[] plant_Details;
	private ListView listView;
	private List<Map<String, Object>> listData;
	private AllPlantAdapter allPlantAdapter;
	private int isClickInx = -1;
	
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			allPlantAdapter.notifyDataSetChanged();
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(NetworkUtil.getInstance().isNetworkGood(AllPlant_backup_Activity.this)){
			new addOrDelFriendFromURL().execute("");
		} else {
			DialogUtil.getInstance().showTipDialog(AllPlant_backup_Activity.this,
					 "网络连接不正常，请检查");
		}
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.all_plant_layout);
		setTopText("现有种菜");
		setBottomVisable(View.GONE);

		listView = (ListView) findViewById(R.id.list);
		allPlantAdapter = new AllPlantAdapter(this);
		listView.setAdapter(allPlantAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (listData != null) {
					if (isClickInx == arg2) {
						listData.get(arg2).put("isClick", false);
						isClickInx = -1;
					} else {
						if(isClickInx != -1) {
							listData.get(isClickInx).put("isClick", false);
						}
						listData.get(arg2).put("isClick", true);
						isClickInx = arg2;
					}	

					allPlantAdapter.notifyDataSetChanged();
				}
			}
		});
//		listView.setOnScrollListener(new OnScrollListener() {
//			
//			@Override
//			public void onScrollStateChanged(AbsListView view, int scrollState) {
//				// TODO Auto-generated method stub
//				if(isClickInx != -1) {
//					listData.get(isClickInx).put("isClick", false);
//					allPlantAdapter.notifyDataSetChanged();
//					isClickInx = -1;
//				}
//			}
//			
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem,
//					int visibleItemCount, int totalItemCount) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
		
//		ArrayList<Object> ddArrayList = DaoCenter.getInstance().getDao()
//				.queryAllData("plantinfo", PlantInfo.class);
//		if (ddArrayList != null) {
//			plantInfos = new PlantInfo[ddArrayList.size()];
//			imagesNames = new String[ddArrayList.size()];
//			Log.i("test", "size = " + ddArrayList.size());
//			plant_Details = new Plant_Detail[ddArrayList.size()];
//			for(int i = 0; i < ddArrayList.size();i++) {
//				PlantInfo plantInfo = (PlantInfo)ddArrayList.get(i);
//				plantInfos[i] = plantInfo; 
//				imagesNames[i] = plantInfo.getPicName();
//				
//				plant_Details[i] = new Plant_Detail();
//				// JSONObject tempJsonObject =
//				// json.getJSONObject(json.names()
//				// .get(i).toString());
//				plant_Details[i].setPlant_id(plantInfos[i].getPlantId() + "");
//				plant_Details[i].setPlant_name(plantInfos[i].getPlantName());
//				plant_Details[i].setDruguse_data("123");
//				plant_Details[i].setFertilizer_data("456");
//				plant_Details[i].setClick(false);
//				plant_Details[i].setPlant_time(plantInfos[i].getExpectedTime());
//				plant_Details[i].setCook_web("");
//				Log.i("json", plant_Details[i].toString());
//			}
//			
//			
//			listData = getData(plant_Details);
//			allPlantAdapter.setListItem(listData);
//			handler.sendEmptyMessage(0);
//		} else {
//			Log.i("test", "ddArrayList = " + "null");
//		}

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
			pDialog = UserFunctions.createProgressDialog(AllPlant_backup_Activity.this,
					"数据处理中，请稍候...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			Bundle data = new Bundle();
			JSONObject json = UserFunctions.getInstance().getAllPlant();
			if (json == null) {
				return "failConnection";
			}
			plant_Details = new Plant_Detail[json.length()];

			for (int i = 0; i < json.length(); i++) {
				try {
					plant_Details[i] = new Plant_Detail();
					// JSONObject tempJsonObject =
					// json.getJSONObject(json.names()
					// .get(i).toString());
					JSONObject tempJsonObject = json.getJSONObject("n"
							+ (json.length() - 1 - i));
					plant_Details[i].setPlant_id(tempJsonObject.getString(
							"plant_id").toString());
					plant_Details[i].setPlant_time(tempJsonObject.getString(
							"plant_time").toString());
					plant_Details[i].setPlant_name(tempJsonObject.getString(
							"plant_name").toString());
					plant_Details[i].setDruguse_data(tempJsonObject.getString(
							"druguse").toString());
					plant_Details[i].setFertilizer_data(tempJsonObject
							.getString("fertilizer").toString());
					plant_Details[i].setCook_web(tempJsonObject
							.getString("web_link").toString());
					plant_Details[i].setClick(false);
					Log.i("json", plant_Details[i].toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			listData = getData(plant_Details);
			allPlantAdapter.setListItem(listData);
			handler.sendEmptyMessage(0);
			return "successConnection";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
		}
	}


	public List<Map<String, Object>> getData(Plant_Detail[] plant_Details) {
		List<Map<String, Object>> lData = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (int i = 0; i < plant_Details.length; i++) {
			map = new HashMap<String, Object>();
			map.put("item_id", plant_Details[i].getPlant_id());
			map.put("item_name", plant_Details[i].getPlant_name());
			map.put("item_date", plant_Details[i].getPlant_time());
			map.put("isClick", plant_Details[i].isClick());
			map.put("web_link", plant_Details[i].getCook_web());
			
			lData.add(map);
		}

		return lData;
	}

	public boolean isAllDefault() {
		boolean res = true;
		for (int i = 0; i < listData.size(); i++) {
			if((Boolean) listData.get(i).get("isClick")) {
				res = false;
				break;
			}
		}
		return res;
	}
}
