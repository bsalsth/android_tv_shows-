package com.toolsforyoutube.mainacitivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.youtubeexpose.R;
import com.indiantvserials.database.TvDataSource;
import com.toolsforyoutube.adapter.MusicGenreAdapter;
import com.toolsforyoutube.items.MusicGenreItem;
import com.toolsforyoutube.utils.JsonUtils;

public class MusicGenre extends SherlockFragment{
	ArrayList<HashMap<String, String>> inboxList;
	List<MusicGenreItem> musicplaylist;
	MusicGenreAdapter genreAdapter;
	GridView list;

	private  final String TITLE = "title";
	private  final String THUMBNAIL = "thumbnails";
	String category_id;
	String videoId;

	private  final String URL = "https://www.googleapis.com/youtube/v3/channelSections?part=contentDetails&channelId=UC-9-kyTW8ZkZNDHQJ6FgpwQ&key=AIzaSyBpu8hgnXbkqFVWrAvwRUEz7T13ii3I7WM";
	private  final String LoadMoreSecond = "&key=AIzaSyBpu8hgnXbkqFVWrAvwRUEz7T13ii3I7WM";
	Boolean isLoading;

	// INITIALIZING FOR  CHANNEL DETAILS
	private String CHANNELURLFIRST ="https://www.googleapis.com/youtube/v3/channels?part=snippet%2CtopicDetails&id=";
	private String CHANNELURLFINAL;
	private TvDataSource datasource;
	private String update;
	ProgressDialog pDialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	} 

	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(
				R.layout.music_genre, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		datasource = new TvDataSource(getActivity());
		datasource.open();
		if(getActivity()!=null){
			((HomeActivity)getActivity()).hideProgressBar();	
		}
}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		list=(GridView)getActivity().findViewById(R.id.gridview);
		musicplaylist = new ArrayList<MusicGenreItem>();
		new loadChannelIds().execute(URL);	
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MusicGenreItem item = musicplaylist.get(position);
				String topicSearchUrl1 = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&maxResults=5&topicId=";
				String urlByTopic = topicSearchUrl1+item.getGenre_topic_id()+LoadMoreSecond;
				Intent i = new Intent(getActivity(),SearchResult.class);
				i.putExtra("urlByTopic",urlByTopic);
				startActivity(i);
			}
		});
	}
	private	class loadChannelIds extends AsyncTask<String, Void, String> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("LOADING MUSIC GENRE");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String response = JsonUtils.getJSONString(params[0]);
			System.out.println(response);
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			JSONObject json;
			try {
				json = new JSONObject(result);
				if(json.has("kind")){
					JSONArray items = json.getJSONArray("items"); // this is the "items: [ ] part
					JSONObject videoObject = items.getJSONObject(44); 
					
					JSONObject contentDetails = videoObject.getJSONObject("contentDetails");
					JSONArray channelsarray = contentDetails.getJSONArray("channels");
					String CHANNELIDS = "";
					for (int i = 0;i<channelsarray.length();i++){

						if(i<channelsarray.length()-1){
							CHANNELIDS += channelsarray.getString(i)+",";
						}else{
							CHANNELIDS+=channelsarray.getString(i);
						}
					}
				final String CHANNELURLFINAL = CHANNELURLFIRST+CHANNELIDS+LoadMoreSecond;
					new loadChannelData().execute(CHANNELURLFINAL);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			catch (NullPointerException e){
				Toast.makeText(getActivity(), "ERROR OCCURED ", Toast.LENGTH_SHORT).show();
			}
		}
	}


	private	class loadChannelData extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			String response = JsonUtils.getJSONString(params[0]);
//			System.out.println(response);
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject json;

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
			try {
				json = new JSONObject(result);
					JSONArray items = json.getJSONArray("items"); // this is the "items: [ ] part
					for (int i = 0; i < items.length(); i++) {
						JSONObject videoObject = items.getJSONObject(i); 
						MusicGenreItem newVideo = new MusicGenreItem();
						videoId = videoObject.getString("id");
						//						" SNIPPET PART " INSIDE ITEMS ARRAY FROM JSON
						JSONObject snippet = videoObject.getJSONObject("snippet");
						String title = snippet.getString(TITLE);
						//	" THUMBNAILS INSIDE SNIPPET PART " INSIDE ITEMS ARRAY FROM JSON
						JSONObject thumbnail = snippet.getJSONObject(THUMBNAIL);
						JSONObject high = thumbnail.getJSONObject("default");
						String thumbnail_image = high.getString("url");
						JSONObject topicDetails = videoObject.getJSONObject("topicDetails");
						String topicId = topicDetails.getJSONArray("topicIds").getString(0);
						newVideo.setGenre_id(videoId);
						newVideo.setGenre_title(title.replace("- Topic", ""));
						newVideo.setGenre_image(thumbnail_image);
						newVideo.setGenre_topic_id(topicId);
//						datasource.createGenre(newVideo);
						musicplaylist.add(newVideo);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			catch (NullPointerException e){
				Toast.makeText(getActivity(), "ERROR OCCURED ", Toast.LENGTH_SHORT).show();
			}

			if(musicplaylist.isEmpty()==false){
				Activity currentActivity = getActivity();
				if(currentActivity!=null){
				genreAdapter = new MusicGenreAdapter(currentActivity, musicplaylist);
				}
				list.setAdapter(genreAdapter);
				RefreshView();
				isLoading = false;
			}else{
				Toast.makeText(getActivity(), "NO VIDEOS AVAILABLE FOR NOW", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void RefreshView(){
		genreAdapter.notifyDataSetChanged();
	}

}
