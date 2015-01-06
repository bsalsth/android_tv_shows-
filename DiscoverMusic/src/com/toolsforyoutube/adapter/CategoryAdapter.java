package com.toolsforyoutube.adapter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubeexpose.R;
import com.squareup.picasso.Picasso;
import com.toolsforyoutube.items.SearchVideoItem;
import com.toolsforyoutube.mainacitivity.PlaylistCategory;

public class CategoryAdapter extends BaseAdapter {
	private final DataSetObservable mDataSetObservable = new DataSetObservable();
	private Activity activity;
	private List<SearchVideoItem> data;
	private static LayoutInflater inflater=null;
	public ImageLoader imageLoader; 
	List<SearchVideoItem> channels;
	String VideoTitle;
	String VideoImage;
	String VideoId;
	CharSequence Settings[] = new CharSequence[] {"ADD TO PLAYLIST", "SHARE"};
	public void refreshDisplay(){
		notifyDataSetChanged();
	}
	public CategoryAdapter(Activity a, List<SearchVideoItem> d) {
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
		Animation anim = null;
		if(convertView==null)
			vi = inflater.inflate(R.layout.category_video_row, null);

		TextView title = (TextView)vi.findViewById(R.id.video_item_title); // title
		TextView views = (TextView)vi.findViewById(R.id.tv_views_count_value); // views
		TextView duration = (TextView)vi.findViewById(R.id.tv_duration_value); // duration
		TextView published = (TextView)vi.findViewById(R.id.tv_published_value); // date
		ImageView thumb_image=(ImageView)vi.findViewById(R.id.img_thumm); // thumb image
		ImageView add = (ImageView) vi.findViewById(R.id.addvideo);
		final SearchVideoItem channel = data.get(position);

		// Setting all values in listview

		title.setText(channel.getvideo_title());
		views.setText(channel.getVideo_views());
		duration.setText(channel.getVideo_duration());
		published.setText(channel.getVideo_published());

		//		String url = channel.getvideo_image();
		Picasso.with(activity)
		.load(channel.getvideo_image())
		.into(thumb_image);


		//		SETTING VALUES FOR FAVORITE
		VideoId = channel.getVideo_id();
		VideoTitle = channel.getvideo_title();
		VideoImage = channel.getvideo_image();


		add.setOnClickListener(new OnClickListener() 
		{ 
			@Override
			public void onClick(View v) 
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setTitle("SELECT A OPTION");
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

		anim = AnimationUtils.loadAnimation(activity, R.anim.push_up_in);
		anim.setDuration(750);

		vi.startAnimation(anim);
		anim = null;
		return vi;
	}
	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		super.registerDataSetObserver(observer);
		mDataSetObservable.registerObserver(observer);
		notifyDataSetChanged();
	}
	@Override
	public void notifyDataSetChanged() {
		mDataSetObservable.notifyChanged();

	}

}