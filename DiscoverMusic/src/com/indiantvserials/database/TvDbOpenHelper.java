package com.indiantvserials.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TvDbOpenHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "Playlist_DATABASE";

	private static final String DATABASE_NAME = "playlist.db";
	private static final int DATABASE_VERSION = 1;


	//	PlAYLIST TABLE
	public static final String TABLE_PLAYLIST = "playlist";
	public static final String COLUMN_ID = "table_id";
	public static final String COLUMN_TITLE = "name";
	public static final String COLUMN_IMAGE = "image";


	private static final String CREATE_PLAYLIST = 
			"CREATE TABLE " + TABLE_PLAYLIST + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_TITLE + " TEXT, " +
					COLUMN_IMAGE + " TEXT " +
					")";

	//	For  Video  Table
	public static final String TABLE_VIDEO = "video";
	public static final String COLUMN_VIDEO_ID = "table_id";
	public static final String COLUMN_VIDEO_VID = "video_id";
	public static final String COLUMN_PLAYLIST_ID= "playlist_id";
	public static final String COLUMN_VIDEO_TITLE = "video_name";
	public static final String COLUMN_VIDEO_IMAGE = "video_image";

	private static final String CREATE_VIDEO = 
			"CREATE TABLE " + TABLE_VIDEO + " (" +
					COLUMN_VIDEO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_VIDEO_TITLE + " TEXT, " +
					COLUMN_PLAYLIST_ID + " long, " +
					COLUMN_VIDEO_IMAGE + " TEXT, " +
					COLUMN_VIDEO_VID + " TEXT " +
					")";
//	For  Music Genre Table
	public static final String TABLE_GENRE = "genre";
	public static final String COLUMN_GENRE_TABLE_ID = "table_id";
	public static final String COLUMN_GENRE_TOPIC_ID = "genre_id";
	public static final String COLUMN_GENRE_TITLE = "name";
	public static final String COLUMN_GENRE_IMAGE = "image";
	public static final String COLUMN_GENRE_UPDATE = "update";
	
	private static final String CREATE_GENRE = 
			"CREATE TABLE " + TABLE_GENRE + " (" +
					COLUMN_GENRE_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_GENRE_TOPIC_ID + " TEXT, " +
					COLUMN_GENRE_TITLE + " TEXT, " +
					COLUMN_GENRE_UPDATE + " TEXT, " +
					COLUMN_GENRE_IMAGE + " TEXT " +
					")";

	public TvDbOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_PLAYLIST);
		db.execSQL(CREATE_VIDEO);
		db.execSQL(CREATE_GENRE);
		Log.e(LOGTAG, "Table has been created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENRE);
		
		onCreate(db);

		Log.i(LOGTAG, "Database has been upgraded from " + 
				oldVersion + " to " + newVersion);
	}




}
