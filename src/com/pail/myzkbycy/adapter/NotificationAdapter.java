package com.pail.myzkbycy.adapter;

import java.util.List;
import java.util.Map;

import org.apache.http.cookie.Cookie;

import com.pail.myzkbycy.R;
import com.pail.myzkbycy.activity.AllPlant_backup_Activity;
import com.pail.myzkbycy.activity.CookMethodActivity;
import com.pail.myzkbycy.activity.PlantNutrition_backup_Activity;

import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.style.LeadingMarginSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotificationAdapter extends BaseAdapter {

	Context context;
	List<Map<String, Object>> listItem;
	private LayoutInflater layoutInflater;
	private View defaultView;

	public NotificationAdapter(Context context) {
		layoutInflater = LayoutInflater.from(context);
		this.context = context;
		defaultView = layoutInflater.inflate(R.layout.notification_list, null);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int res;
		if (listItem == null) {
			res = 0;
		} else {
			res = listItem.size();
		}
		return res;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.notification_list, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.inx.setText(String.valueOf(position + 1));
		viewHolder.news_title.setText(listItem.get(position).get("news_title")
				.toString());
		viewHolder.news_data.setText("发布人：梦园       发布时间：" + listItem.get(position).get("news_data")
				.toString());
		return convertView;
	}


	static class ViewHolder {
		TextView inx;
		TextView news_title;
		TextView news_data;

		public ViewHolder(View view) {
			// TODO Auto-generated constructor stub

			inx = (TextView) view.findViewById(R.id.inx);
			news_title = (TextView) view.findViewById(R.id.news_title);
			news_data = (TextView) view.findViewById(R.id.news_data);
		}
	}

	public List<Map<String, Object>> getListItem() {
		return listItem;
	}

	public void setListItem(List<Map<String, Object>> listItem) {
		this.listItem = listItem;
	}
}
