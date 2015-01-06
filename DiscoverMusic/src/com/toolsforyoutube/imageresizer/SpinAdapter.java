package com.toolsforyoutube.imageresizer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.youtubeexpose.R;

public class SpinAdapter extends ArrayAdapter<String>
{
	Context c;
	String[] list ;
	LayoutInflater inflater;
	public SpinAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
		// TODO Auto-generated constructor stub
	}
	
	public SpinAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		c = context;
		list = objects;
		inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}




	@Override
	public View getDropDownView(int position, View convertView,ViewGroup parent)
	{
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) 
	{

		GradientDrawable gd;
			gd = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM,
					new int[] {Color.parseColor("#17FF0F"),Color.parseColor("#008080")});
			gd.setCornerRadius(0f);
		View row=inflater.inflate(R.layout.custom_spinner, parent, false);
		TextView label=(TextView)row.findViewById(R.id.textView1);
		label.setText(list[position]);
		row.setBackgroundDrawable(gd);

		return row;
	}

}