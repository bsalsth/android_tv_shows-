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
import android.widget.Toast;

import com.example.youtubeexpose.R;
import com.squareup.picasso.Picasso;
import com.toolsforyoutube.items.MusicGenreItem;
import com.toolsforyoutube.mainacitivity.PlaylistCategory;

public class MusicGenreAdapter extends BaseAdapter {
	private final DataSetObservable mDataSetObservable = new DataSetObservable();
	private Activity activity;
	private List<MusicGenreItem> data;
	private static LayoutInflater inflater=null;
	public ImageLoader imageLoader; 
	List<MusicGenreItem> channels;
	String VideoTitle;
	String VideoImage;
	String VideoId;
	CharSequence Settings[] = new CharSequence[] {"ADD TO PLAYLIST", "SHARE"};
	public void refreshDisplay(){
		notifyDataSetChanged();
	}
	public MusicGenreAdapter(Activity a, List<MusicGenreItem> d) {
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
			vi = inflater.inflate(R.layout.music_genre_adapter, null);
		TextView title = (TextView)vi.findViewById(R.id.title); // title
		ImageView thumb_image=(ImageView)vi.findViewById(R.id.genre_image); // thumb image
		MusicGenreItem channel = data.get(position);
		title.setText(channel.getGenre_title());
		Picasso.with(activity)
		.load(channel.getGenre_image())
		.into(thumb_image);
		
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