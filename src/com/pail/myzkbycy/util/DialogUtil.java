package com.pail.myzkbycy.util;
import java.util.ArrayList;
import java.util.List;

import com.pail.myzkbycy.R;
import com.pail.myzkbycy.util.ClockLoadingDialog.OnTimeUpListener;


import android.R.integer;
import android.R.string;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.GpsStatus.Listener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DialogUtil {
	public static DialogUtil showDialog;
	//用于list的索引
	public static  int LEFTBUTTON = 0;
	public static  int RIGHTBUTTON = 1;
	private static Dialog dialog;
	private static RotateAnimation anim_rotate = null;
	public static String inputStr="";
	//有两个button的dialog，会返回一个存放button的list，便于写监听事件。
	private static ArrayList<Button> list;
	private static EditText edt;
	public static DialogUtil getInstance(){//实例化
		if(showDialog == null){
			showDialog = new DialogUtil();
		}
		return showDialog;
	}
	
	/**
	 * 初始化动画
	 */
	private static void initAnim(Context context) {
		anim_rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		anim_rotate.setDuration(1200);//转动的快慢
		anim_rotate.setRepeatMode(Animation.RESTART);
		anim_rotate.setInterpolator(AnimationUtils.loadInterpolator(context,android.R.anim.linear_interpolator));
		anim_rotate.setRepeatCount(Animation.INFINITE);
	}
	/**
	 * loading 对话框，该对话框有图标和文字说明,文字在图标上面显示
	 * @param context
	 */
	public static void showLoadingDialogWithTextDown(Context context, String showStr){
		try {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.dialog_loading_down, null);
			Button loadingBtn = (Button) linearLayout.findViewById(R.id.loading_btn_down);
			TextView textView = (TextView) linearLayout.findViewById(R.id.textView1); 
			initAnim(context);
			loadingBtn.startAnimation(anim_rotate);
			textView.setText(showStr);
			dialog = new Dialog(context, R.style.testdialog);
			dialog.setContentView(linearLayout);
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * loading 对话框，该对话框有图标和文字说明,文字在图标下面显示
	 * @param context
	 */
	public static void showLoadingDialogWithTextUP(Context context){
		try {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.dialog_loading_up, null);
			Button loadingBtn = (Button) linearLayout.findViewById(R.id.loading_btn_up);
			initAnim(context);
			loadingBtn.startAnimation(anim_rotate);
			dialog = new Dialog(context, R.style.testdialog);
			dialog.setContentView(linearLayout);
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * loading 对话框，该对话框有图标和文字说明,文字在图标下面显示
	 * @param context
	 */
	public static void showLoadingDialogWithTextUP(Context context, String showStr, OnClickListener onClickListener){
		try {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.dialog_loading_up, null);
			Button loadingBtn = (Button) linearLayout.findViewById(R.id.loading_btn_up);
			TextView textView = (TextView) linearLayout.findViewById(R.id.textView1); 
			initAnim(context);
			loadingBtn.startAnimation(anim_rotate);
			textView.setText(showStr);
			linearLayout.setOnClickListener(onClickListener);
			dialog = new Dialog(context, R.style.testdialog);
			dialog.setContentView(linearLayout);
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param context
	 * @param showStr
	 * @param onClickListener
	 */
	 
	public static void showLoadingDialogWithTextDown(Context context, String showStr, OnClickListener onClickListener){
		try {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.dialog_loading_up, null);
			Button loadingBtn = (Button) linearLayout.findViewById(R.id.loading_btn_up);
			TextView textView = (TextView) linearLayout.findViewById(R.id.textView1); 
			initAnim(context);
			loadingBtn.startAnimation(anim_rotate);
			textView.setText(showStr);
			linearLayout.setOnClickListener(onClickListener);
			dialog = new Dialog(context, R.style.testdialog);
			dialog.setContentView(linearLayout);
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param context
	 * @param showStr
	 * @param onClickListener
	 */
	
	public static void showClockLoadingDialog(Context context, String showString, View.OnClickListener onClickListener, int totalTime, OnTimeUpListener onTimeUpListener){
		try {
			dialog = new ClockLoadingDialog(context, showString, onClickListener, totalTime, onTimeUpListener);
			initAnim(context);
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void showTipDialog(Context context, String showString, OnClickListener onClickListener, String btnText) {
		try {
			dialog = new TipDialog(context, showString, onClickListener, btnText);
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void showTipDialog(Context context, String showString) {
		try {
			dialog = new TipDialog(context, showString, null, context.getString(R.string.sure));
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void showTip2Dialog(Context context, String showString, OnClickListener onClickListener) {
		try {
			dialog = new Tip2Dialog(context, showString, onClickListener);
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void dismissDialog(){
		if(dialog != null) {
			dialog.dismiss();
		}
	}
	
	
}
