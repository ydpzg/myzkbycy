package com.pail.myzkbycy.adapter;


import com.pail.myzkbycy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainGridViewAdapter extends BaseAdapter {

	Context context;
	int[] image;
	int[] name;
	LayoutInflater inflater;
	int size;

	public MainGridViewAdapter(Context context, int[] image, int[] name,int width) {
		this.context = context;
		this.image = image;
		this.name = name;
		inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		this.size=width/9;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return image.length;
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
		View view = null;
		ViewHolder holder = null;
		if (convertView == null || convertView.getTag() == null) {
			view = inflater.inflate(R.layout.main_gv_item, null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) convertView.getTag();
		}
		holder.iv_main.setBackgroundResource(image[position]);
		holder.iv_main.setPadding(size, size, size, size);
		holder.tv_main.setText(name[position]);
//		if(R.string.main_messagecenter == name[position]){
//			/*
//			 * 获取未读的信息数量
//			 */
//			//your code
//			int unreadMsg = 5;
//			holder.tv_msgCount.setText(unreadMsg+"");
//			holder.tv_msgCount.setVisibility(View.VISIBLE);
//			
//		}
		return view;
	}
	class ViewHolder{
		ImageView iv_main;
		TextView tv_main;
		TextView tv_msgCount ;
		public ViewHolder(View view){
			iv_main=(ImageView)view.findViewById(R.id.main_iv);
			tv_main=(TextView)view.findViewById(R.id.main_tv);
			tv_msgCount = (TextView) view.findViewById(R.id.tv_msgCount);
		}
		
	}

}
