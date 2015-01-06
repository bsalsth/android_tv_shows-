package com.toolsforyoutube.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubeexpose.R;
import com.squareup.picasso.Picasso;
import com.toolsforyoutube.items.PlaylistVideo;

public class PlaylistVideoAdapter extends BaseAdapter {

	private Context activity;
	private List<PlaylistVideo> data;
	private static LayoutInflater inflater=null;
	public ImageLoader imageLoader; 
	List<PlaylistVideo> channels;
	public PlaylistVideoAdapter(Context a, List<PlaylistVideo> d) {
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
			vi = inflater.inflate(R.layout.search_result_adapter, null);

		TextView title = (TextView)vi.findViewById(R.id.title); // title
		TextView published = (TextView)vi.findViewById(R.id.published); // title
		ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
		ImageView setting=(ImageView)vi.findViewById(R.id.settings); // thumb image
		
		
		
		PlaylistVideo channel = data.get(position);
		
		// Setting all values in listview
		title.setText(channel.getVideo_name());
		Picasso.with(activity)
		  .load(channel.getvideo_image_url())
		  .into(thumb_image);
		return vi;
	}
}