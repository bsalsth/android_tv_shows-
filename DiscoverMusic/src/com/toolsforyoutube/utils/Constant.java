package com.toolsforyoutube.utils;

import java.io.Serializable;

public class Constant implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		//this is the path of uploaded image of server where image store
		public static final String SERVER_IMAGE_UPFOLDER="http://whatsnepal.com/indianserials/category_image/";
		public static final String SERVER_IMAGE_UPFOLDER_P="http://whatsnepal.com/indianserials/images/";
		
		//this url gives list of plalist
		public static final String CATEGORY_URL = "http://whatsnepal.com/indianserials/api.php";
		public static final String MAIN_CATEGORY_URL = "http://whatsnepal.com/indianserials/categories.php";
		public static final String PLAYLIST_BY_CATEGORY = "http://whatsnepal.com/indianserials/category_playlist?category_id=";
		
		
		
		public static final String CATEGORY_ARRAY_NAME="Category List";
		public static final String CATEGORY_ID="id";
		public static final String CATEGORY_NAME="name";
		public static final String CATEGORY_IMAGE="image";
		
		public static final String PLAYLIST_ARRAY_NAME="Youtube Playlists";
		public static final String PLAYLIST_NAME="playlist_name";
		public static final String PLAYLIST_CID="pid";
		public static final String PLAYLIST_IMAGE="playlist_image";
		public static final String PLAYLIST_USER_ID="playlist_unique_id";
		//for title display in CategoryItemF
		public static String PLAYLIST_USERTITLE;
		public static String PLAYLIST_USERNAME;
		
		public static final String PLAYLIST_URL_FIRST="http://gdata.youtube.com/feeds/api/playlists/";
		public static final String PLAYLIST_URL_BACK="/?v=2&max-results=10&alt=jsonc";
		
		public static final String PLAYLISTLODEMORE_URL_FIRST="http://gdata.youtube.com/feeds/api/playlists/";
		public static final String PLAYLISTLODEMORE_URL_BACK="/?v=2&max-results=10&alt=jsonc&start-index=";
		
			
}
