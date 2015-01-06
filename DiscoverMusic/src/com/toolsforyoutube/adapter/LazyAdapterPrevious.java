package com.toolsforyoutube.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubeexpose.R;
import com.toolsforyoutube.items.SearchVideoItem;

public abstract class LazyAdapterPrevious extends BaseAdapter{

	private  Activity activity;
	private Context context;
	View vi;
	private static LayoutInflater inflater=null;
	public ImageLoader imageLoader; 
	private List<SearchVideoItem> data;
	SearchVideoItem video;
	public LazyAdapterPrevious(Activity a, List<SearchVideoItem> v) {
		activity = a;
		data=v;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader=new ImageLoader(activity.getApplicationContext());
	}

	@Override
	public int getCount() {

		return data.size();

	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//        View vi=convertView;
		//        if(convertView==null)
		vi = inflater.inflate(R.layout.list_adapter, null);
		context = parent.getContext();

		TextView title = (TextView)vi.findViewById(R.id.title); // title
		//        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
		//        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
		ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
		//        Button btn =  (Button)vi.findViewById(R.id.button1);
//		ImageView fav = (ImageView) vi.findViewById(R.id.fav);

		//        HashMap<String, String> song = new HashMap<String, String>();
		//        channels = data.get(position);
		//        Channel channel = channels.get(position);
		video = data.get(position);
		// Setting all values in listview
		title.setText(video.getvideo_title());
		imageLoader.DisplayImage(video.getvideo_image(), thumb_image);
		return vi;
	}

}