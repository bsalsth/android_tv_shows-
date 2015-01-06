package com.toolsforyoutube.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.youtubeexpose.R;
import com.toolsforyoutube.items.CategoryItem;

public class CaltegoryListAdapter extends BaseAdapter {

	private Activity activity;
	private List<CategoryItem> data;
	private static LayoutInflater inflater=null;
	public ImageLoader imageLoader; 
	List<CategoryItem> channels;

	public void refreshDisplay(){
		notifyDataSetChanged();
	}

	public CaltegoryListAdapter(Activity a, List<CategoryItem> d) {
		activity = a;
		data=d;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader=new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		if(convertView==null)
			vi = inflater.inflate(R.layout.row, null);

		TextView title = (TextView)vi.findViewById(R.id.row_title); // title
		CategoryItem channel = data.get(position);
		title.setText(channel.getCategory_title());
		
		// Setting all values in listview
		return vi;
	}
}