package com.toolsforyoutube.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.youtubeexpose.R;
import com.toolsforyoutube.items.SearchVideoItem;

public class LyricsAdapter extends BaseAdapter {

	private Context activity;
	private List<SearchVideoItem> data;
	private static LayoutInflater inflater=null;
	public ImageLoader imageLoader; 
	List<SearchVideoItem> channels;

	public LyricsAdapter(Context a, List<SearchVideoItem> d) {
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
			vi = inflater.inflate(R.layout.lyricslistadapter, null);

		TextView title = (TextView)vi.findViewById(R.id.title); // title
		TextView published = (TextView)vi.findViewById(R.id.artistname); // title
		SearchVideoItem channel = data.get(position);

		// Setting all values in listview
		title.setText(channel.getvideo_title());
		published.setText(channel.getVideo_published());

		return vi;
	}
}