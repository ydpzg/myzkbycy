package com.pail.myzkbycy.adapter;

import java.util.List;
import java.util.Map;

import org.apache.http.cookie.Cookie;

import com.pail.myzkbycy.R;
import com.pail.myzkbycy.activity.AllPlantActivity;
import com.pail.myzkbycy.activity.CookMethodActivity;
import com.pail.myzkbycy.activity.PlantNutritionActivity;

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

public class AllPlantAdapter extends BaseAdapter {

	Context context;
	List<Map<String, Object>> listItem;
	private LayoutInflater layoutInflater;
	private Animation slideDownAnimation, slideUpAnimation;
	private View defaultView;

	public AllPlantAdapter(Context context) {
		layoutInflater = LayoutInflater.from(context);
		this.context = context;
		slideDownAnimation = AnimationUtils.loadAnimation(context,
				R.anim.slide_down);
		slideUpAnimation = AnimationUtils.loadAnimation(context,
				R.anim.slide_up);
		defaultView = layoutInflater.inflate(R.layout.all_plant_list, null);
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
			convertView = layoutInflater.inflate(R.layout.all_plant_list, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (position != listItem.size() - 1) {
			viewHolder.item_inx.setText(String.valueOf(position + 1));
			viewHolder.item_name.setText(listItem.get(position).get("item_name")
					.toString());
			viewHolder.item_date.setText(listItem.get(position).get("item_date")
					.toString());
			boolean isClick = (Boolean) listItem.get(position).get("isClick");
			if (isClick) {
				Log.i("test", "isclick=" + position);
				viewHolder.grow_btn.setVisibility(View.VISIBLE);
				viewHolder.nutrition_btn.setVisibility(View.VISIBLE);
				viewHolder.cook_btn.setVisibility(View.VISIBLE);
				viewHolder.below_LL.startAnimation(slideDownAnimation);
				Log.i("test", "onclick=" + isClick);
			} else {
				viewHolder.grow_btn.setVisibility(View.GONE);
				viewHolder.nutrition_btn.setVisibility(View.GONE);
				viewHolder.cook_btn.setVisibility(View.GONE);
				Log.i("test", "onclick=" + isClick);
			}
			viewHolder.nutrition_btn.setOnClickListener(onClickListener);
			viewHolder.grow_btn.setOnClickListener(onClickListener);
			viewHolder.cook_btn.setOnClickListener(onClickListener);
			viewHolder.nutrition_btn.setTag(listItem.get(position).get("item_id"));
			viewHolder.cook_btn.setTag(position);
		} else {
			viewHolder.item_inx.setText("");
			viewHolder.item_name.setText("");
			viewHolder.item_date.setText("");
			viewHolder.grow_btn.setVisibility(View.GONE);
			viewHolder.nutrition_btn.setVisibility(View.GONE);
			viewHolder.cook_btn.setVisibility(View.GONE);
		}
		return convertView;
	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent;
			switch (v.getId()) {
			case R.id.nutrition_btn:
				intent = new Intent(context, PlantNutritionActivity.class);
				intent.putExtra("plantId", v.getTag().toString());
				context.startActivity(intent);
				break;
			case R.id.cook_btn:
				intent = new Intent(context, CookMethodActivity.class);
				intent.putExtra("web_link",
						listItem.get(Integer.valueOf(v.getTag().toString()))
								.get("web_link").toString());
				context.startActivity(intent);
//				Uri uriUrl = Uri.parse("http://github.com/jfeinstein10/slidingmenu");
//				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl); 
//				context.startActivity(launchBrowser);
				break;
			default:
				break;
			}
		}
	};

	static class ViewHolder {
		TextView item_inx;
		TextView item_name;
		TextView item_date;
		Button grow_btn;
		Button nutrition_btn;
		Button cook_btn;
		LinearLayout above_LL;
		LinearLayout below_LL;

		public ViewHolder(View view) {
			// TODO Auto-generated constructor stub

			item_inx = (TextView) view.findViewById(R.id.item_inx);
			item_name = (TextView) view.findViewById(R.id.item_name);
			item_date = (TextView) view.findViewById(R.id.item_date);
			below_LL = (LinearLayout) view.findViewById(R.id.below_LL);
			grow_btn = (Button) view.findViewById(R.id.grow_btn);
			nutrition_btn = (Button) view.findViewById(R.id.nutrition_btn);
			cook_btn = (Button) view.findViewById(R.id.cook_btn);
		}
	}

	public List<Map<String, Object>> getListItem() {
		return listItem;
	}

	public void setListItem(List<Map<String, Object>> listItem) {
		this.listItem = listItem;
	}
}
