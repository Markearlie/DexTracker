package com.dextracker.adapter;


import java.util.ArrayList;
import java.util.List;

import com.dextracker.Leader;
import com.dextracker.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListViewAdapter extends BaseAdapter {

	private static LayoutInflater inflater = null;
	Context context;
	List<Leader> leaders = new ArrayList<Leader>();

	public CustomListViewAdapter(Context context, List<Leader> leaders) {
		this.leaders = leaders;
		this.context = context;

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (vi == null)
			vi = inflater.inflate(R.layout.listview_layout, null);

		
		TextView leaderPos = (TextView) vi.findViewById(R.id.textView1);
		leaderPos.setText(Integer.toString(position + 1));
		
		TextView lineOne = (TextView) vi.findViewById(R.id.line_one);
		lineOne.setText(leaders.get(position).getAlias());
		
		TextView lineTwo = (TextView) vi.findViewById(R.id.line_two);
		lineTwo.setText(Integer.toString(leaders.get(position).getSpeedScore()));
		
		TextView lineThree = (TextView) vi.findViewById(R.id.line_three);
		lineThree.setText(Integer.toString(leaders.get(position).getAccuracyScore()));
		
		
		return vi;
	}

	@Override
	public int getCount() {
		return leaders.size();
	}

	@Override
	public Object getItem(int position) {
		return leaders.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
