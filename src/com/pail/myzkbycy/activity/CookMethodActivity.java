package com.pail.myzkbycy.activity;


import org.json.JSONObject;

import com.pail.myzkbycy.BaseActivity;
import com.pail.myzkbycy.R;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class CookMethodActivity extends BaseActivity {

	private WebView mWebView;
	private Handler mHandler = new Handler();   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
//        mWebView.loadUrl("http://www.qq.com");  
		Log.i("test", getIntent().getStringExtra("web_link").toString());
        mWebView.loadUrl(getIntent().getStringExtra("web_link").toString());       
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		setCenterView(R.layout.cook_method_layout);
		setTopText("烹饪方法");
		setBottomVisable(View.GONE);

		mWebView = (WebView) findViewById(R.id.webview);       
        WebSettings webSettings = mWebView.getSettings();       
        webSettings.setJavaScriptEnabled(true);       
//        mWebView.addJavascriptInterface(new Object() {       
//            public void clickOnAndroid() {       
//                mHandler.post(new Runnable() {       
//                    public void run() {       
//                        mWebView.loadUrl("javascript:wave()");       
//                    }       
//                });       
//            }       
//        }, "demo");       
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
