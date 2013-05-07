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

public class ReserveOffer2Adapter extends BaseAdapter {

	Context context;
	List<Map<String, Object>> listItem;
	private LayoutInflater layoutInflater;
	private Animation slideDownAnimation, slideUpAnimation;
	private View defaultView;

	public ReserveOffer2Adapter(Context context) {
		layoutInflater = LayoutInflater.from(context);
		this.context = context;
		slideDownAnimation = AnimationUtils.loadAnimation(context,
				R.anim.slide_down);
		slideUpAnimation = AnimationUtils.loadAnimation(context,
				R.anim.slide_up);
		defaultView = layoutInflater.inflate(R.layout.reserve_offer_2_layout,
				null);
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
			convertView = layoutInflater.inflate(R.layout.reserve_offer_2_list,
					null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.item_date.setText(listItem.get(position).get("item_date")
				.toString());
		viewHolder.textView1.setText(listItem.get(position).get("plant1").toString());
		viewHolder.textView2.setText(listItem.get(position).get("plant2").toString());
		viewHolder.textView3.setText(listItem.get(position).get("plant3").toString());
		return convertView;
	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent;
			switch (v.getId()) {
			// case R.id.nutrition_btn:
			// intent = new Intent(context, PlantNutritionActivity.class);
			// intent.putExtra("plantId", v.getTag().toString());
			// context.startActivity(intent);
			// break;
			// case R.id.cook_btn:
			// intent = new Intent(context, CookMethodActivity.class);
			// intent.putExtra("web_link",
			// listItem.get(Integer.valueOf(v.getTag().toString()))
			// .get("web_link").toString());
			// context.startActivity(intent);
			// // Uri uriUrl =
			// // Uri.parse("http://github.com/jfeinstein10/slidingmenu");
			// // Intent launchBrowser = new Intent(Intent.ACTION_VIEW,
			// // uriUrl);
			// // context.startActivity(launchBrowser);
			// break;
			default:
				break;
			}
		}
	};

	static class ViewHolder {
		TextView item_date;
		TextView textView1;
		TextView textView2;
		TextView textView3;

		public ViewHolder(View view) {
			// TODO Auto-generated constructor stub

			item_date = (TextView) view.findViewById(R.id.item_date);
			textView1 = (TextView) view.findViewById(R.id.textView1);
			textView2 = (TextView) view.findViewById(R.id.textView2);
			textView3 = (TextView) view.findViewById(R.id.textView3);
		}
	}

	public List<Map<String, Object>> getListItem() {
		return listItem;
	}

	public void setListItem(List<Map<String, Object>> listItem) {
		this.listItem = listItem;
	}
}
