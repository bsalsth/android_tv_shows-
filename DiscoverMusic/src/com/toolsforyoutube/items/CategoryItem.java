package com.toolsforyoutube.items;


public class CategoryItem extends Object {
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCategory_id() {
		return Category_id;
	}
	public void setCategory_id(String category_id) {
		Category_id = category_id;
	}
	public String getCategory_title() {
		return Category_title;
	}
	public void setCategory_title(String category_title) {
		Category_title = category_title;
	}
	public String getCategory_channelId() {
		return Category_channelId;
	}
	public void setCategory_channelId(String category_channelId) {
		Category_channelId = category_channelId;
	}
	private long id;
	private String Category_id;
	private String Category_title;
	private String Category_channelId;
	


}


