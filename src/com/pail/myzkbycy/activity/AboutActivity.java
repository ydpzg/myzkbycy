package com.pail.myzkbycy.activity;



import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends BaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.about_layout);
		setTopText("关于");
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


}
