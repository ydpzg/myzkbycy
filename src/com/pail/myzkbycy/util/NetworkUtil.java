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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class NetworkUtil {
	private static NetworkUtil networkUtil = null; 
	public static NetworkUtil getInstance(){//实例化
		if(networkUtil == null){
			networkUtil = new NetworkUtil();
		}
		return networkUtil;
	}
	public boolean isNetworkGood(Context context)
    {  
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);    
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();    
        if (networkinfo == null || !networkinfo.isAvailable()) {    
            return false;  
        }  
        return true;  
    }
}
