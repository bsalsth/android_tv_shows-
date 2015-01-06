package com.toolsforyoutube.items;


public class SearchVideoItem extends Object {

	private long id;
	private String video_id;
	private String video_title;
	private String video_thumbnail;
	private String video_views;
	private String video_published;
	private String video_duration;
	private String video_likes;
	private String video_dislikes;
	private String description;
	private String video_channel;
	private String topic_details;

	public String getTopic_details() {
		return topic_details;
	}
	public void setTopic_details(String topic_details) {
		this.topic_details = topic_details;
	}
	public String getVideo_dislikes() {
		return video_dislikes;
	}
	public void setVideo_dislikes(String video_dislikes) {
		this.video_dislikes = video_dislikes;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVideo_channel() {
		return video_channel;
	}
	public void setVideo_channel(String video_channel) {
		this.video_channel = video_channel;
	}

	public String getVideo_views() {
		return video_views;
	}
	public void setVideo_views(String video_views) {
		this.video_views = video_views;
	}
	public String getVideo_published() {
		return video_published;
	}
	public void setVideo_published(String video_published) {
		this.video_published = video_published;
	}
	public String getVideo_duration() {
		return video_duration;
	}
	public void setVideo_duration(String video_duration) {
		this.video_duration = video_duration;
	}
	public String getVideo_likes() {
		return video_likes;
	}
	public void setVideo_likes(String video_likes) {
		this.video_likes = video_likes;
	}


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
	public String getvideo_title() {
		return video_title;
	}
	public void setvideo_title(String video_title) {
		this.video_title = video_title;
	}
	public String getvideo_image() {
		return video_thumbnail;
	}
	public void setvideo_image(String video_image) {
		this.video_thumbnail = video_image;
	}
}


