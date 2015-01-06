package com.toolsforyoutube.items;


public class MusicGenreItem extends Object {

	private long id;
	private String genre_id;
	private String genre_title;
	private String genre_image;
	private String genre_topic_id;
	private String genre_update;
	public String getGenre_update() {
		return genre_update;
	}
	public void setGenre_update(String genre_update) {
		this.genre_update = genre_update;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGenre_id() {
		return genre_id;
	}
	public void setGenre_id(String genre_id) {
		this.genre_id = genre_id;
	}
	public String getGenre_title() {
		return genre_title;
	}
	public void setGenre_title(String genre_title) {
		this.genre_title = genre_title;
	}
	public String getGenre_image() {
		return genre_image;
	}
	public void setGenre_image(String genre_image) {
		this.genre_image = genre_image;
	}
	public String getGenre_topic_id() {
		return genre_topic_id;
	}
	public void setGenre_topic_id(String genre_topic_id) {
		this.genre_topic_id = genre_topic_id;
	}


}


