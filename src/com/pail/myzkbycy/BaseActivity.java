package com.pail.myzkbycy;

import java.util.ArrayList;

import com.pail.myzkbycy.activity.MainActivity;
import com.pail.myzkbycy.control.MyApplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.FlagToString;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @ClassName BaseActivity.java
 * @Description 自定义一个activity基类
 * 
 */
public class BaseActivity extends Activity implements View.OnClickListener {
	protected View layout_top;
	private View layout_bottom;
	public Button btn_left;
	public Button btn_right;
	private Button btn_bottom;
	private TextView tv_title;
	private LinearLayout centerLayout;
	private TextView tv_bottom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((MyApplication) getApplication()).addActivity(this);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// 设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.base_layout);
		initProcess();

	}

	/**
	 * 初始化界面
	 */
	protected void initUI() {
		layout_top = findViewById(R.id.title_layout);
		layout_bottom = findViewById(R.id.bottom_title_layout);
		centerLayout = (LinearLayout) findViewById(R.id.centerlayout);
		tv_title = (TextView) layout_top.findViewById(R.id.tv_center);
		btn_left = (Button) layout_top.findViewById(R.id.bt_left);
		btn_right = (Button) layout_top.findViewById(R.id.bt_right);
		btn_bottom = (Button) layout_bottom.findViewById(R.id.bottom_btn);
		tv_bottom = (TextView) layout_bottom.findViewById(R.id.bottom_tv);
		btn_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		btn_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaseActivity.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}

	/**
	 * 初始化数据
	 */
	protected void initData() {
//		Intent intent = getIntent();
//		if (intent != null) {
//			int dataPacketSize = intent.getIntExtra(DATA_PACKET_SIZE, -1);
//			if (dataPacketSize > 0) {
//				ArrayList<DataPacket> dataPackets = new ArrayList<DataPacket>();
//				for (int i = 0; i < dataPacketSize; i++) {
//					String tag = DATA_PACKET_NAME + i;
//					DataPacket tempData = (DataPacket) intent
//							.getSerializableExtra(tag);
//					if (tempData != null) {
//						dataPackets.add(tempData);
//					}
//				}
//				if (dataPackets.size() > 0) {
//					receiveDataFromPrevious(dataPackets);
//				}
//			}
//
//		}
	}
//
//	/**
//	 * 收到从上一个页面传送过来的数据
//	 * 
//	 * @param dataReceive
//	 */
//	protected void receiveDataFromPrevious(ArrayList<DataPacket> dataLists) {
//		// TODO Auto-generated method stub
//
//	}

	/**
	 * 初始化监听事件
	 */
	protected void initListener() {
	}

	/**
	 * 设置标题栏可见性
	 * 
	 * @param visable
	 */
	protected void setTopVisable(int visable) {
		if (layout_top != null) {
			layout_top.setVisibility(visable);
		}
	}

	/**
	 * 隐藏标题栏
	 */
	protected void hideTop() {
		setTopVisable(View.GONE);
	}

	/**
	 * 显示标题栏
	 */
	protected void showTop() {
		setTopVisable(View.VISIBLE);
	}

	/**
	 * 设置底部可见性
	 * 
	 * @param visable
	 */
	protected void setBottomVisable(int visable) {
		if (layout_bottom != null) {
			layout_bottom.setVisibility(visable);
		}
	}

	/**
	 * 隐藏底部
	 */
	protected void hideBottom() {
		setBottomVisable(View.GONE);
	}

	/**
	 * 显示底部
	 */
	protected void showBottom() {
		setBottomVisable(View.VISIBLE);
	}

	/**
	 * 设置标题栏背景
	 * 
	 * @param resourceId
	 *            背景资源id
	 */
	protected void setTopBg(int resourceId) {
		layout_top.setBackgroundResource(resourceId);
	}

	/**
	 * 设置左边按钮
	 * 
	 * @param bgresourceId
	 *            背景资源id
	 * @param visibility
	 *            可视性
	 * @param listener
	 *            监听器
	 */
	protected void setLeftButton(int bgresourceId, int visibility,
			OnClickListener listener) {
		btn_left.setBackgroundResource(bgresourceId);
		btn_left.setVisibility(visibility);
		btn_left.setOnClickListener(listener);
	}

	/**
	 * 设置左边按钮
	 * 
	 * @param bgresourceId
	 *            背景资源id
	 * @param visibility
	 *            可视性
	 * @param listener
	 *            监听器
	 * @param stringId
	 *            字符串id
	 */
	protected void setLeftButton(int bgresourceId, int visibility,
			OnClickListener listener, int stringId) {
		setLeftButton(bgresourceId, visibility, listener);
		btn_left.setText(stringId);
	}

	/**
	 * 右边按钮的可见性
	 * 
	 * @param visibility
	 */
	protected void setRightButton(int visibility) {
		btn_right.setVisibility(visibility);
	}
	
	/**
	 * 右边按钮的文字设置
	 * @param text
	 */
	protected void setRightButton(String text){
		btn_right.setText(text);
	}

	/**
	 * 设置左边按钮
	 * 
	 * @param bgresourceId
	 *            背景资源id
	 * @param visibility
	 *            可视性
	 * @param listener
	 *            监听器
	 */
	protected void setRightButton(int bgresourceId, int visibility,
			OnClickListener listener) {
		btn_right.setBackgroundResource(bgresourceId);
		btn_right.setVisibility(visibility);
		btn_right.setOnClickListener(listener);
	}

	/**
	 * 设置右边按钮
	 * 
	 * @param bgresourceId
	 *            背景资源id
	 * @param visibility
	 *            可视性
	 * @param listener
	 *            监听器
	 * @param stringId
	 *            字符串id
	 */

	protected void setRightButton(int bgresourceId, int visibility,
			OnClickListener listener, int stringId) {
		setRightButton(bgresourceId, visibility, listener);
		btn_right.setText(stringId);
	}

	/**
	 * 设置底部按钮
	 * 
	 * @param bgresourceId
	 *            背景资源id
	 * @param visibility
	 *            可视性
	 * @param listener
	 *            监听器
	 * @param stringId
	 *            字符串id
	 */
	protected void setBottomButton(int bgresourceId, int visibility,
			OnClickListener listener, int stringId) {
		btn_bottom.setBackgroundResource(bgresourceId);
		btn_bottom.setVisibility(visibility);
		btn_bottom.setOnClickListener(listener);
		btn_bottom.setText(stringId);
	}

	/**
	 * 设置标题栏文字显示
	 * 
	 * @param resourceId
	 *            字符串资源id
	 */
	protected void setTopText(int resourceId) {
		tv_title.setText(resourceId);
	}

	/**
	 * 设置标题栏文字显示
	 * 
	 * @param text
	 *            字符串
	 */
	protected void setTopText(CharSequence text) {
		tv_title.setText(text);
	}


	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void toastPlay(String str, Context context) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 设置中间的显示内容
	 * 
	 * @param centerLayout
	 *            layout资源文件
	 */
	public void setCenterView(int layout) {
		centerLayout.removeAllViews();
		LayoutInflater inflater = getLayoutInflater();
		View addView = inflater.inflate(layout, null);
		centerLayout.addView(addView, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}

	/**
	 * 设置中间的显示内容
	 * 
	 * @param centerView
	 */
	public void setCenterView(View centerView) {
		centerLayout.removeAllViews();
		centerLayout.addView(centerView);
	}

	/**
	 * 加载的流程
	 */
	protected void initProcess() {
		initData();
		initUI();
		initListener();
	}

	/**
	 * 跳转到下一个activity
	 * 
	 * @param nextActivity
	 */
	public void startToNextActivity(Class<?> nextActivity) {
		Intent intent = new Intent(this, nextActivity);
		startActivity(intent);
	}

	/**
	 * 监听器
	 */
	@Override
	public void onClick(View v) {

	}

}
