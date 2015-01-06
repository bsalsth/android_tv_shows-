package com.toolsforyoutube.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubeexpose.R;
import com.squareup.picasso.Picasso;
import com.toolsforyoutube.items.SearchVideoItem;
import com.toolsforyoutube.mainacitivity.PlaylistCategory;

public class SearchResultAdapter extends BaseAdapter {

	private Context activity;
	private List<SearchVideoItem> data;
	private static LayoutInflater inflater=null;
	public ImageLoader imageLoader; 
	List<SearchVideoItem> channels;

	String VideoTitle;
	String VideoImage;
	String VideoId;
	CharSequence Settings[] = new CharSequence[] {"ADD TO PLAYLIST", "SHARE", "blue", "black"};
	public SearchResultAdapter(Context a, List<SearchVideoItem> d) {
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
			vi = inflater.inflate(R.layout.searchresultadapter, null);

		TextView title = (TextView)vi.findViewById(R.id.title); // title
		TextView published = (TextView)vi.findViewById(R.id.published); // title
		ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
		ImageView settings=(ImageView)vi.findViewById(R.id.settings); // settings image
		final SearchVideoItem channel = data.get(position);

		// Setting all values in listview
		title.setText(channel.getvideo_title());
		published.setText(channel.getVideo_published());
		Picasso.with(activity)
		.load(channel.getvideo_image())
		.into(thumb_image);

		//		SETTING VALUES FOR FAVORITE
		VideoId = channel.getVideo_id();
		VideoTitle = channel.getvideo_title();
		VideoImage = channel.getvideo_image();

		settings.setOnClickListener(new OnClickListener() 
		{ 
			@Override
			public void onClick(View v) 
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setTitle("Pick a color");
				builder.setItems(Settings, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// the user clicked on Settings[which]
						if(Settings[which]=="ADD TO PLAYLIST"){
							Intent mIntent = new Intent(activity, PlaylistCategory.class);
							Bundle mBundle = new Bundle();
							mBundle.putString("UniqueId", "FROMADAPTER");
							mBundle.putString("id", channel.getVideo_id());
							mBundle.putString("title", channel.getvideo_title());
							mBundle.putString("image", channel.getvideo_image());
							mIntent.putExtras(mBundle);
							activity.startActivity(mIntent);
						}else if (Settings[which]=="SHARE") {
							Intent share = new Intent(android.content.Intent.ACTION_SEND);
							share.setType("text/plain");
							share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
							// Add data to the intent, the receiving app will decide
							// what to do with it.
							share.putExtra(Intent.EXTRA_SUBJECT, "Share This Video With Your Friend");
							share.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v="+channel.getVideo_id());
							activity.startActivity(Intent.createChooser(share, "Share link!"));
						}
					}
				});
				builder.show();
			}

		});
		return vi;
	}
}