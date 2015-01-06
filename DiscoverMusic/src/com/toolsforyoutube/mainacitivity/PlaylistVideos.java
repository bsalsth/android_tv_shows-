package com.toolsforyoutube.mainacitivity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youtubeexpose.R;
import com.indiantvserials.database.TvDataSource;
import com.toolsforyoutube.adapter.PlaylistVideoAdapter;
import com.toolsforyoutube.items.PlaylistVideo;

public class PlaylistVideos extends Activity {

	List<PlaylistVideo> FolderList;
	ListView list;
	TextView add;
	public static TvDataSource datasource;
	PlaylistVideoAdapter adapter;
//	For new fav video object from intent
	String Vtitle;
	String Vid ;
	String Vimage ;
	long FolderId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist_category);
		datasource = new TvDataSource(this);
		datasource.open();
		Intent intent = getIntent();
		FolderId = intent.getLongExtra("FolderId", 1);

		list = (ListView) findViewById(android.R.id.list);
		DisplayPlaylist();

	}
		public void DisplayPlaylist(){
			FolderList = datasource.findFiltered(FolderId);
			adapter = new PlaylistVideoAdapter(PlaylistVideos.this, FolderList);
			list.setAdapter(adapter);
		}

}

