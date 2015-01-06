package com.toolsforyoutube.items;

import android.app.Activity;

public class PlaylistVideo extends Activity {

	private long id;
	private String video_id;
	private String video_name;
	private String video_image_url;
	private long playlist_id;

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getVideo_id() {
		return video_id;
	}
	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}
	public String getVideo_name() {
		return video_name;
	}
	public void setVideo_name(String video_name) {
		this.video_name = video_name;
	}
	public String getvideo_image_url() {
		return video_image_url;
	}
	public void setvideo_image_url(String video_image_url) {
		this.video_image_url = video_image_url;
	}
	public long getPlaylist_id() {
		return playlist_id;
	}
	public void setPlaylist_id(long playlist_id) {
		this.playlist_id = playlist_id;
	}
	
	}


