package com.toolsforyoutube.mainacitivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.youtubeexpose.R;
import com.indiantvserials.database.TvDataSource;
import com.toolsforyoutube.items.PlayListFolder;

public class CreatePlaylistEditBox extends Activity {
	EditText ed;
	Button btn;
	String playlist;
	long PlaylistId;
	Boolean FiredIntent;
	Bundle CheckIntent;
	public static TvDataSource datasource;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_playlist_popup);
		datasource = new TvDataSource(this);
		datasource.open();
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,  
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		ed = (EditText) findViewById(R.id.editText1);
		CheckIntent = getIntent().getExtras();
		if(CheckIntent!=null){
			if(CheckIntent.getBoolean("EDITPLAYLIST"))
			PlaylistId = CheckIntent.getLong("Playlistid");
		}
		btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				playlist = ed.getText().toString();
				if(playlist.matches("")||CheckIntent.getBoolean("ADDPLAYLIST")){
					PlayListFolder folder = new PlayListFolder();
					folder.setPtitle(playlist);
					Intent returnIntent = new Intent();
					returnIntent.putExtra("result",playlist);
					setResult(RESULT_OK,returnIntent);
					datasource.create(folder);
				}else if (playlist.matches("")||CheckIntent.getBoolean("EDITPLAYLIST")) {
					datasource.UpdatePlaylistFolder(PlaylistId, playlist);
					Intent back = new Intent(CreatePlaylistEditBox.this, PlaylistCategory.class);
					startActivity(back);
				}
				datasource.close();
				finish();

			}
		});

	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 2) {
			if(resultCode == RESULT_OK){
				String result=data.getStringExtra("result");
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

//				DisplayPlaylist();
			}
			if (resultCode == RESULT_CANCELED) {
			}
		}
	}//onActivityResult

	


}
