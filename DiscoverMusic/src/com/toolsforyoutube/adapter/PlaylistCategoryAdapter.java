package com.toolsforyoutube.adapter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubeexpose.R;
import com.indiantvserials.database.TvDataSource;
import com.toolsforyoutube.items.PlayListFolder;
import com.toolsforyoutube.mainacitivity.CreatePlaylistEditBox;
import com.toolsforyoutube.mainacitivity.PlaylistCategory;

public class PlaylistCategoryAdapter extends BaseAdapter {

	private Activity activity;
	private List<PlayListFolder> data;
	private static LayoutInflater inflater=null;
	List<PlayListFolder> channels;
	CharSequence Settings[] = new CharSequence[] {"EDIT PLAYLIST", "DELETE PLAYLIST"};

	public PlaylistCategoryAdapter(Activity a, List<PlayListFolder> d) {
		activity = a;
		data=d;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			vi = inflater.inflate(R.layout.playlistfolder_adapter, null);

		TextView title = (TextView)vi.findViewById(R.id.title); // title
		ImageView settings = (ImageView) vi.findViewById(R.id.settings); //settings
		int colorPos = position % 2;
		GradientDrawable gd;
		if(colorPos == 1){
			gd = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM,
					new int[] {Color.parseColor("#adff2f"),Color.parseColor("#6eb43f")});
			gd.setCornerRadius(0f);
		}else{
			gd = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM,
					new int[] {Color.parseColor("#17FF0F"),Color.parseColor("#008080")});
			gd.setCornerRadius(0f);
		}
		vi.setBackgroundDrawable(gd);
		final PlayListFolder channel = data.get(position);
		// Setting all values in listview
		title.setText(channel.getPtitle());
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
						if(Settings[which]=="EDIT PLAYLIST"){
							Intent mIntent = new Intent(activity, CreatePlaylistEditBox.class);
							Bundle mBundle = new Bundle();
							mBundle.putBoolean("EDITPLAYLIST", true);
							mBundle.putLong("Playlistid", channel.getPid());
							mIntent.putExtras(mBundle);
							activity.startActivity(mIntent);
							RefreshNow();
						}else if (Settings[which]=="DELETE PLAYLIST") {
						DeleteVideo(channel.getPid());
						}
					}
				});
				builder.show();
			}

		});
		return vi;

	}
	public void DeleteVideo(long id){
		TvDataSource datasource = new TvDataSource(activity);
		datasource.open();
		datasource.DeletePlaylistFolder(id);
		datasource.close();
		RefreshNow();
	}
	public void RefreshNow(){

		this.notifyDataSetChanged();
		
		
	}
	
}