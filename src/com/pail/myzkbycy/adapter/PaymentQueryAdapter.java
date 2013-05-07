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

public class PaymentQueryAdapter extends BaseAdapter {

	Context context;
	List<Map<String, Object>> listItem;
	private LayoutInflater layoutInflater;
	private Animation slideDownAnimation, slideUpAnimation;
	private View defaultView;

	public PaymentQueryAdapter(Context context) {
		layoutInflater = LayoutInflater.from(context);
		this.context = context;
		slideDownAnimation = AnimationUtils.loadAnimation(context,
				R.anim.slide_down);
		slideUpAnimation = AnimationUtils.loadAnimation(context,
				R.anim.slide_up);
		defaultView = layoutInflater.inflate(R.layout.payment_query_list, null);
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
			convertView = layoutInflater.inflate(R.layout.payment_query_list,
					null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.item_inx.setText(String.valueOf(position + 1));
		viewHolder.payment_time_TV.setText(listItem.get(position)
				.get("payment_time").toString());
		viewHolder.payment_account_TV.setText(listItem.get(position)
				.get("payment_account").toString());
		viewHolder.effect_time_TV.setText(listItem.get(position)
				.get("effective_time").toString());
		viewHolder.payment_way_TV.setText(listItem.get(position)
				.get("payment_way").toString());
		boolean isClick = (Boolean) listItem.get(position).get("isClick");
		if (isClick) {
			Log.i("test", "isclick=" + position);
			viewHolder.below_son1_LL.setVisibility(View.VISIBLE);
			viewHolder.below_LL.startAnimation(slideDownAnimation);
			Log.i("aaaa", "onclick=" + isClick + "   " + position);
		} else {
			viewHolder.below_son1_LL.setVisibility(View.GONE);
			Log.i("aaaa", "onclick=" + isClick + "   " + position);
		}
		return convertView;
	}

	static class ViewHolder {
		TextView item_inx;
		TextView payment_time_TV;
		TextView payment_account_TV;

		TextView effect_time_TV;
		TextView payment_way_TV;

		LinearLayout below_LL;
		LinearLayout below_son1_LL;
		LinearLayout below_son2_LL;

		public ViewHolder(View view) {
			// TODO Auto-generated constructor stub

			item_inx = (TextView) view.findViewById(R.id.item_inx);
			payment_time_TV = (TextView) view.findViewById(R.id.payment_time);
			payment_account_TV = (TextView) view
					.findViewById(R.id.payment_account);

			effect_time_TV = (TextView) view.findViewById(R.id.effect_time);
			payment_way_TV = (TextView) view.findViewById(R.id.payment_way);

			below_LL = (LinearLayout) view.findViewById(R.id.below_LL);
			below_son1_LL = (LinearLayout) view
					.findViewById(R.id.below_son1_LL);
			below_son2_LL = (LinearLayout) view
					.findViewById(R.id.below_son2_LL);
		}
	}

	public List<Map<String, Object>> getListItem() {
		return listItem;
	}

	public void setListItem(List<Map<String, Object>> listItem) {
		this.listItem = listItem;
	}
}
