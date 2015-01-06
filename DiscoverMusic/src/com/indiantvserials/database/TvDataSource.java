package com.indiantvserials.database;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.toolsforyoutube.items.MusicGenreItem;
import com.toolsforyoutube.items.PlaylistVideo;
import com.toolsforyoutube.items.PlayListFolder;

public class TvDataSource {

	public static final String LOGTAG="EXPLORECA";

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;


	private static final String[] playlistColumns = {
		TvDbOpenHelper.COLUMN_TITLE,
		TvDbOpenHelper.COLUMN_IMAGE,
		TvDbOpenHelper.COLUMN_ID
	};


	//	VIDEO COLUMN
	private static final String[] videoColumns = {
		TvDbOpenHelper.COLUMN_VIDEO_VID,
		TvDbOpenHelper.COLUMN_VIDEO_TITLE,
		TvDbOpenHelper.COLUMN_VIDEO_IMAGE,
		TvDbOpenHelper.COLUMN_VIDEO_ID,
		TvDbOpenHelper.COLUMN_PLAYLIST_ID
	};
	
//	GENRE COLUMN
	private static final String[] genreColumns = {
		TvDbOpenHelper.COLUMN_GENRE_TABLE_ID,
		TvDbOpenHelper.COLUMN_GENRE_TOPIC_ID,
		TvDbOpenHelper.COLUMN_GENRE_TITLE,
		TvDbOpenHelper.COLUMN_GENRE_IMAGE
		
	};

	public TvDataSource(Context context) {
		dbhelper = new TvDbOpenHelper(context);
	}

	public void open() {
		Log.e(LOGTAG, "Database opened");
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		Log.e(LOGTAG, "Database closed");		
		dbhelper.close();
	}

	public PlayListFolder create(PlayListFolder playlist) {
		ContentValues values = new ContentValues();
		values.put(TvDbOpenHelper.COLUMN_TITLE, playlist.getPtitle());
		values.put(TvDbOpenHelper.COLUMN_IMAGE, playlist.getPimage());
		long insertid = database.insert(TvDbOpenHelper.TABLE_PLAYLIST, null, values);
		Log.e("Databaselog", "Database createeed"+insertid);	
		playlist.setPid(insertid);
		return playlist;
	}

	public List<PlayListFolder> GetPlaylistFolder(){
		Cursor cursor = database.query(TvDbOpenHelper.TABLE_PLAYLIST, playlistColumns, 
				null, null, null, null, null);
		List<PlayListFolder> playlists = cursorToList(cursor);
		return playlists;
	}

	private List<PlayListFolder> cursorToList(Cursor cursor) {
		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rowsssss");
		List<PlayListFolder> playlists = new ArrayList<PlayListFolder>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				PlayListFolder playlist = new PlayListFolder();
				playlist.setPid(cursor.getLong(cursor.getColumnIndex(TvDbOpenHelper.COLUMN_ID)));
				Log.i(LOGTAG, "Returned " + playlist.getPid() + " latest ids");
				playlist.setPtitle(cursor.getString(cursor.getColumnIndex(TvDbOpenHelper.COLUMN_TITLE)));
				playlist.setPimage(cursor.getString(cursor.getColumnIndex(TvDbOpenHelper.COLUMN_IMAGE)));
				playlists.add(playlist);
			}
		}
		return playlists;
	}
	public void UpdatePlaylistFolder(long playlisdId, String NewTitle){

		ContentValues values=new ContentValues();
		values.put(TvDbOpenHelper.COLUMN_TITLE, NewTitle);
		database.update(TvDbOpenHelper.TABLE_PLAYLIST,values,"table_id="+playlisdId,null);

	}
	
	public void DeletePlaylistFolder(long playlisdId){

		database.delete(TvDbOpenHelper.TABLE_PLAYLIST, "table_id="+playlisdId, null);

	}
	public void removeAll(Context context)
	{
		database.delete(TvDbOpenHelper.TABLE_PLAYLIST, null, null);
		dbhelper = new TvDbOpenHelper(context);
	}

	//	FAVOURITE VIDEO SECTION
	//	VIDEO DATASOURCH HELPER FUNCTION STARTS
	public PlaylistVideo createVideo(PlaylistVideo video) {
		ContentValues values = new ContentValues();
		values.put(TvDbOpenHelper.COLUMN_VIDEO_VID, video.getVideo_id()) ;
		values.put(TvDbOpenHelper.COLUMN_VIDEO_TITLE, video.getVideo_name());
		values.put(TvDbOpenHelper.COLUMN_PLAYLIST_ID, video.getPlaylist_id());
		values.put(TvDbOpenHelper.COLUMN_VIDEO_IMAGE, video.getvideo_image_url());
		long insertid = database.insert(TvDbOpenHelper.TABLE_VIDEO, null, values);
		Log.e("favdatabase", "Database createeed"+insertid);	
		video.setId(insertid);
		return video;
	}

	public List<PlaylistVideo> FindVideos(){
		Cursor cursor = database.query(TvDbOpenHelper.TABLE_VIDEO, videoColumns, 
				null, null, null, null, null);
		List<PlaylistVideo> playlists = cursorToListFav(cursor);
		return playlists;
	}
	public List<PlaylistVideo> findFiltered(long playlisdId){
		Cursor cursor = database.query(TvDbOpenHelper.TABLE_VIDEO, videoColumns, 
				"playlist_id = "+playlisdId, null, null, null, null);
		List<PlaylistVideo> videos = cursorToListFav(cursor);
		return videos;
	}

	private List<PlaylistVideo> cursorToListFav(Cursor cursor) {
		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rowsssss");
		List<PlaylistVideo> playlists = new ArrayList<PlaylistVideo>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				PlaylistVideo video = new PlaylistVideo();
				video.setId(cursor.getLong(cursor.getColumnIndex(TvDbOpenHelper.COLUMN_VIDEO_ID)));
				Log.i(LOGTAG, "Returned " + video.getId() + " latest ids");
				video.setVideo_name(cursor.getString(cursor.getColumnIndex(TvDbOpenHelper.COLUMN_VIDEO_TITLE)));
				video.setVideo_id(cursor.getString(cursor.getColumnIndex(TvDbOpenHelper.COLUMN_VIDEO_VID)));
				video.setvideo_image_url(cursor.getString(cursor.getColumnIndex(TvDbOpenHelper.COLUMN_VIDEO_IMAGE)));
				video.setPlaylist_id(cursor.getLong(cursor.getColumnIndex(TvDbOpenHelper.COLUMN_PLAYLIST_ID)));
				playlists.add(video);
			}
		}
		return playlists;
	}
	
	
//	GENRE CODE STARTS HERE

	public MusicGenreItem createGenre(MusicGenreItem genre) {
		ContentValues values = new ContentValues();
		values.put(TvDbOpenHelper.COLUMN_GENRE_TITLE, genre.getGenre_title());
		values.put(TvDbOpenHelper.COLUMN_GENRE_IMAGE, genre.getGenre_image());
		values.put(TvDbOpenHelper.COLUMN_GENRE_TOPIC_ID, genre.getGenre_topic_id());
		values.put(TvDbOpenHelper.COLUMN_GENRE_UPDATE, genre.getGenre_update());
		long insertid = database.insert(TvDbOpenHelper.TABLE_GENRE, null, values);
		Log.e("Databaselog", "Database createeed"+insertid);	
		genre.setId(insertid);
		return genre;
	}

	public List<MusicGenreItem> getAllGenre(){
		Cursor cursor = database.query(TvDbOpenHelper.TABLE_GENRE, genreColumns, 
				null, null, null, null, null);
		List<MusicGenreItem> genreList = cursorToListGenre(cursor);
		return genreList;
	}

	private List<MusicGenreItem> cursorToListGenre(Cursor cursor) {
		List<MusicGenreItem> genreList = new ArrayList<MusicGenreItem>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				MusicGenreItem genre = new MusicGenreItem();
				genre.setId(cursor.getLong(cursor.getColumnIndex(TvDbOpenHelper.COLUMN_GENRE_TABLE_ID)));
				genre.setGenre_topic_id(cursor.getString(cursor.getColumnIndex(TvDbOpenHelper.COLUMN_GENRE_TOPIC_ID)));
				genre.setGenre_title(cursor.getString(cursor.getColumnIndex(TvDbOpenHelper.COLUMN_GENRE_TITLE)));
				genre.setGenre_image(cursor.getString(cursor.getColumnIndex(TvDbOpenHelper.COLUMN_GENRE_IMAGE)));
				genre.setGenre_update(cursor.getString(cursor.getColumnIndex(TvDbOpenHelper.COLUMN_GENRE_UPDATE)));
				genreList.add(genre);
			}
		}
		return genreList;
	}





}