package com.pail.myzkbycy.activity;

import java.util.ArrayList;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import com.pail.myzkbycy.adapter.GalleryImageAdapter;
import com.pail.myzkbycy.bean.PlantInfo;
import com.pail.myzkbycy.dao.DaoCenter;
import com.pail.myzkbycy.util.PxUtil;
import com.pail.myzkbycy.view.GalleryFlow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class Gallery3DActivity extends BaseActivity {
	private TextView mTitle, mConText;
	private DaoCenter dao;
	private GalleryImageAdapter galleryImageAdapter;
	private String[] imagesNames;
	private PlantInfo[] plantInfos;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
	}
	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.all_plant2);
		setTopText("现有种菜");
		setBottomVisable(View.GONE);
		
		setRightButton(R.drawable.button5_home2, View.VISIBLE, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Gallery3DActivity.this, AllPlantActivity.class);
				startActivity(intent);
				finish();
			}
		}, R.string.showText);
		
		mTitle = (TextView) findViewById(R.id.mTitle);
		mConText = (TextView) findViewById(R.id.mContext);

		ArrayList<Object> ddArrayList = DaoCenter.getInstance().getDao()
				.queryAllData("plantinfo", PlantInfo.class);
		if (ddArrayList != null) {
			plantInfos = new PlantInfo[ddArrayList.size()];
			imagesNames = new String[ddArrayList.size()];
			Log.i("test", "size = " + ddArrayList.size());
			for(int i = 0; i < ddArrayList.size();i++) {
				PlantInfo plantInfo = (PlantInfo)ddArrayList.get(i);
				plantInfos[i] = plantInfo; 
				imagesNames[i] = plantInfo.getPicName();
			}
		} else {
			
			Log.i("test", "ddArrayList = " + "null");
		}
		galleryImageAdapter = new GalleryImageAdapter(this, imagesNames);
		galleryImageAdapter.createReflectedImages();// 创建倒影效果
		GalleryFlow galleryFlow = (GalleryFlow) this
				.findViewById(R.id.myGallery);
		galleryFlow.setFadingEdgeLength(0);
		galleryFlow.setSpacing(0); // 图片之间的间距
		galleryFlow.setAdapter(galleryImageAdapter);

		galleryFlow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				mTitle.setText(plantInfos[arg2].getPlantName());
				mConText.setText(Html.fromHtml(plantInfos[arg2].getPlantContext()));

			}
		});
		galleryFlow.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				mTitle.setText(plantInfos[arg2].getPlantName());
				mConText.setText(Html.fromHtml(plantInfos[arg2].getPlantContext()));

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		galleryFlow.setSelection(ddArrayList.size() / 2);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
//		galleryImageAdapter.BitmapRecycle();
//		System.gc();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		galleryImageAdapter.BitmapRecycle();
		System.gc();
	}
}
