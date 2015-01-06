package com.toolsforyoutube.mainacitivity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youtubeexpose.R;
import com.indiantvserials.database.TvDataSource;
import com.toolsforyoutube.adapter.PlaylistCategoryAdapter;
import com.toolsforyoutube.items.PlayListFolder;
import com.toolsforyoutube.items.PlaylistVideo;

public class PlaylistCategory extends Activity {

	public static List<PlayListFolder> FolderList;
	public static ListView list;
	TextView add;
	public static TvDataSource datasource;
	PlaylistCategoryAdapter adapter;

	//	For new fav video object from intent
	String Vtitle = null;
	String Vid = null;
	String Vimage =null;
	String UniqueId = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist_category);
		datasource = new TvDataSource(this);
		datasource.open();
		Bundle b = getIntent().getExtras();
		if (b  != null){
			Vtitle= b.getString("title");
			Vid = b.getString("id");
			Vimage = b.getString("image");
			UniqueId = b.getString("UniqueId");

		}

		list = (ListView) findViewById(android.R.id.list);
		list.setOnItemClickListener(myOnItemClickListener);

		add = (TextView) findViewById(R.id.textView1);
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PlaylistCategory.this, CreatePlaylistEditBox.class);
				i.putExtra("ADDPLAYLIST", true);
				startActivityForResult(i, 1);
			}
		});
		DisplayPlaylist();

	}
	

	OnItemClickListener myOnItemClickListener
	= new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			//			if (JsonUtils.isNetworkAvailable(PlaylistCategory.this)) {
			if(UniqueId!=null){
				PlayListFolder folder = FolderList.get(position);
				Long FolderId = folder.getPid();
				PlaylistVideo Pvideo = new PlaylistVideo();
				Pvideo.setVideo_id(Vid);
				Pvideo.setVideo_name(Vtitle);
				Pvideo.setvideo_image_url(Vimage);
				Pvideo.setPlaylist_id(FolderId);
				Toast.makeText(PlaylistCategory.this, FolderId+"\n"+Vtitle+"\n"+Vimage+"\n"+Vid+"\n", Toast.LENGTH_SHORT).show();
				datasource.createVideo(Pvideo);
				UniqueId=null;
				finish();
			}else{
				UniqueId=null;
				PlayListFolder folder = FolderList.get(position);
				Long FolderId = folder.getPid();
				Intent intent = new Intent(PlaylistCategory.this, PlaylistVideos.class);
				intent.putExtra("FolderId", FolderId);
				startActivity(intent);
			}
		}};
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {

			if (requestCode == 1) {
				if(resultCode == RESULT_OK){
					String result=data.getStringExtra("result");
					Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

					DisplayPlaylist();
				}
				if (resultCode == RESULT_CANCELED) {
				}
			}
		}//onActivityResult

		public void DisplayPlaylist(){
			FolderList = datasource.GetPlaylistFolder();
			adapter = new PlaylistCategoryAdapter(PlaylistCategory.this, FolderList);
			list.setAdapter(adapter);
		}
		
		
}

