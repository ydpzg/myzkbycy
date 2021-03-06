package com.pail.myzkbycy.util;

import com.pail.myzkbycy.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @ClassName TipDialog.java
 * @Description 自定义Dialog，实现一个圆角白色背景一个Textview，一个button的布局。
 * @date 2013-1-15
 *
 */

public class TipDialog extends Dialog{
	private Context context;    
	private Button confirmBtn;//底部按钮
	private TextView showTV;//显示TextView
	private String showString;//显示的文字
	private String btnText;//按钮上的文字
	private View.OnClickListener onClickListener;//按钮的监听器
	public TipDialog(Context context) {
		super(context);
		this.context = context; 
	}
	
	public TipDialog(Context context, int theme) {        
		super(context, theme);        
		this.context = context;    
	} 
	
	public TipDialog(Context context, int theme, String showString) {
		super(context, theme);
		this.context = context;
		this.showString = showString;
	}
	
	public TipDialog(Context context, String showString, View.OnClickListener onClickListener) {
		super(context, R.style.TipDialog);
		this.context = context;
		this.showString = showString;
		this.onClickListener = onClickListener;
	}
	
	public TipDialog(Context context, String showString, View.OnClickListener onClickListener, String btnText) {
		super(context, R.style.TipDialog);
		this.context = context;
		this.showString = showString;
		this.onClickListener = onClickListener;
		this.btnText = btnText;
	}
	
	public TipDialog(Context context, String showString) {
		super(context, R.style.TipDialog);
		this.context = context;
		this.showString = showString;
	}
	
	public TipDialog(Context context, String showString, String btnText) {
		super(context, R.style.TipDialog);
		this.context = context;
		this.showString = showString;
		this.btnText = btnText;
	}
	

	@Override    
	protected void onCreate(Bundle savedInstanceState) {        
		// TODO Auto-generated method stub        
		super.onCreate(savedInstanceState);        
		setContentView(R.layout.tip_dialog);    
		confirmBtn = (Button) findViewById(R.id.confirm);
		showTV = (TextView) findViewById(R.id.showInformation);
		//默认，按钮的动作为关闭Dialog
		if(onClickListener == null) {
			confirmBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dismiss();
				}
			});
		} else {
			confirmBtn.setOnClickListener(onClickListener);
		}
		
		if(btnText != null) {
			confirmBtn.setText(btnText);
		}
		showTV.setText(showString);
	}
	//显示Dialog
	public void showDialog() {
		show();
	}
	
	
	
//	//关闭Dialog
//	public void dismissDialog() {
//		dismiss();
//	}
}
