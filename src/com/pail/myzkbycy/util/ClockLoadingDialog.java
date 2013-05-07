package com.pail.myzkbycy.util;

import java.security.PublicKey;
import java.util.Timer;
import java.util.TimerTask;

import com.pail.myzkbycy.R;


import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ClockLoadingDialog extends Dialog{
	private Context context;    
	private LinearLayout zero_dialog;
	private Button loadingBtn;//按钮
	private TextView textView1,textViewClock;//显示TextView
	private String showString;//显示的文字
	private View.OnClickListener onClickListener;//按钮的监听器
	private Timer timer;
	private TimerTask timerTask;
	private RotateAnimation anim_rotate;
	private int totalTime = 10, i;
	
	private OnTimeUpListener onTimeUpListener;
	
	public ClockLoadingDialog(Context context) {
		super(context);
		this.context = context; 
	}
	
	public ClockLoadingDialog(Context context, int theme) {        
		super(context, theme);        
		this.context = context;    
	} 
	
	public ClockLoadingDialog(Context context, int theme, String showString) {
		super(context, theme);
		this.context = context;
		this.showString = showString;
	}
	
	public ClockLoadingDialog(Context context, String showString, View.OnClickListener onClickListener, int totalTime, OnTimeUpListener onTimeUpListener) {
		super(context, R.style.testdialog);
		this.context = context;
		this.showString = showString;
		this.onClickListener = onClickListener;
		this.totalTime = totalTime;
		this.onTimeUpListener = onTimeUpListener;
	}
	
	
	public ClockLoadingDialog(Context context, String showString) {
		super(context, R.style.testdialog);
		this.context = context;
		this.showString = showString;
	}
	

	@Override    
	protected void onCreate(Bundle savedInstanceState) {        
		// TODO Auto-generated method stub        
		super.onCreate(savedInstanceState);        
		setContentView(R.layout.dialog_loading_down);    
		zero_dialog = (LinearLayout) findViewById(R.id.zero_dialog);
		loadingBtn = (Button) findViewById(R.id.loading_btn_down);
		textView1 = (TextView) findViewById(R.id.textView1);
		textViewClock = (TextView) findViewById(R.id.textViewClock);
		//默认，按钮的动作为关闭Dialog
		if(onClickListener != null) {
			zero_dialog.setOnClickListener(onClickListener);
		}
		
		textView1.setText(showString);
		initAnim(context);
	}
	
	private void initAnim(Context context) {
		anim_rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		anim_rotate.setDuration(1200);//转动的快慢
		anim_rotate.setRepeatMode(Animation.RESTART);
		anim_rotate.setInterpolator(AnimationUtils.loadInterpolator(context,android.R.anim.linear_interpolator));
		anim_rotate.setRepeatCount(Animation.INFINITE);
	}
	
	public void startTimer() {
		if(timerTask == null) {
			i = totalTime;
			timerTask = new TimerTask(){
				public void run(){	
					if(i > 0) {
						handler.sendEmptyMessage(0);
						i--;
					} else {
						handler.sendEmptyMessage(1);
						stopTimer();
					}
				}
			};	
		}	
		if(timer == null) {
			timer = new Timer(true);
			timer.schedule(timerTask, 1000, 1000);
		}
	}
	public void stopTimer() {  
		if(timer != null) {
			timer.cancel();
			timer = null;
		}
		if(timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
	}
	
	private Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				textViewClock.setText("超时："+i+"秒");
				break;
			case 1:
				if(onTimeUpListener != null) {
					onTimeUpListener.onTimeUpDoing();
				}
				break;

			default:
				break;
			}
			
			super.handleMessage(msg);
		}
	};
	
	public interface OnTimeUpListener {
		public void onTimeUpDoing();
	}
	
	
	public OnTimeUpListener getOnTimeUpListener() {
		return onTimeUpListener;
	}

	public void setOnTimeUpListener(OnTimeUpListener onTimeUpListener) {
		this.onTimeUpListener = onTimeUpListener;
	}

	

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		loadingBtn.startAnimation(anim_rotate);;
		startTimer();
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
		stopTimer();
	}
	
}
