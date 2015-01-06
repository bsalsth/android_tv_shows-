package com.toolsforyoutube.mainacitivity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.Window;
import com.example.youtubeexpose.R;
import com.toolsforyoutube.adapter.SearchResultAdapter;
import com.toolsforyoutube.items.SearchVideoItem;
import com.toolsforyoutube.utils.CustomFunctions;
import com.toolsforyoutube.utils.JsonUtils;
import com.toolsforyoutube.youtbeplayer.PlayerViewDemoActivity;

public class SearchResult extends Activity {
	SearchResultAdapter adapter;
	List<SearchVideoItem> video_list;
	private  final String TITLE = "title";
	private  final String DESCRIPTION = "description"; 
	private  final String PUBLISHED = "publishedAt";
	private  final String THUMBNAIL = "thumbnails";
	private  final String CHANNEL_TITLE = "channelTitle";

	private  String SearchedTerm;
	private  String query;
	private  String VOrder;
	private  String VDuration;
	private  String VType;
	private  String urlByTopic = null;
	private static String API_KEY = "AIzaSyBpu8hgnXbkqFVWrAvwRUEz7T13ii3I7WM";

	private String NextPageToken;
	private String ToatalVideoItems = null;
	private boolean isLoading;
	int i;

	ListView list;

	String videoId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_video_list);

		list = (ListView) findViewById(R.id.listview);
		video_list = new ArrayList<SearchVideoItem>();
		Intent GetSearchedTerm = getIntent();
		query = GetSearchedTerm.getStringExtra("SearchQuery");
		VOrder = GetSearchedTerm.getStringExtra("VOrder");
		VDuration = GetSearchedTerm.getStringExtra("VDuration");
		VType = GetSearchedTerm.getStringExtra("VType");
		urlByTopic = GetSearchedTerm.getStringExtra("urlByTopic");
		if(urlByTopic!=null){
			SearchedTerm = urlByTopic;
			new MyTask().execute(SearchedTerm);
			Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();
		}else{
			SearchedTerm = CustomFunctions.getSearchApi(VOrder, VDuration,VType, query, API_KEY);
			new MyTask().execute(SearchedTerm);
			Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_LONG).show();
		}

		list.setOnScrollListener(new EndlessScrollListner() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				if(NextPageToken != null&&isLoading !=true){
					Toast.makeText(getApplicationContext(),VOrder+  VDuration + VType+"\nnext =="+NextPageToken,Toast.LENGTH_LONG).show();
					if(urlByTopic!=null){
						String urlByTopicLoadMore = urlByTopic+"&pageToken="+NextPageToken;
						new MyTask().execute(urlByTopicLoadMore);	
					}else{
						new MyTask().execute(CustomFunctions.getNextSearchPage(VOrder, VDuration,VType, query, API_KEY,NextPageToken));

					}
				}else{
				}
			}
		});

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SearchVideoItem item = video_list.get(position);
				String video_id = item.getVideo_id();
				String titleLyrics = item.getvideo_title().replace(" ", "%20");
				
				Intent i = new Intent(SearchResult.this,PlayerViewDemoActivity.class);
				i.putExtra("video_id",video_id);
				i.putExtra("titleLyrics",titleLyrics);
				//				Toast.makeText(appContext, YouTubeApplication.getId_map().get(position)+"ID FROM VIDEOLIST",Toast.LENGTH_SHORT).show();
				startActivity(i);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	private	class MyTask extends AsyncTask<String, Void, String> {
		private int lastViewedPosition;
		private int topOffset;


		ProgressDialog pDialog;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			i++;
			isLoading =true;
//			new HomeActivity().showProgressBar();		
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

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
			JSONObject json;
			JSONArray items;
			try {
				json = new JSONObject(result);
				if(json.has("nextPageToken")){
					NextPageToken = json.getString("nextPageToken");
					JSONObject pageInfo = json.getJSONObject("pageInfo");
					ToatalVideoItems= pageInfo.getString("totalResults");
				}
				items = json.getJSONArray("items");

				for (int i = 0; i < items.length(); i++) {

					JSONObject videoObject = items.getJSONObject(i); 
					SearchVideoItem newVideo = new SearchVideoItem();

					JSONObject type = videoObject.getJSONObject("id");
					if(type.has("videoId")){
						videoId = type.getString("videoId");
					}
					//	" SNIPPET PART " INSIDE ITEMS ARRAY FROM JSON
					JSONObject snippet = videoObject.getJSONObject("snippet");
					String title = snippet.getString(TITLE);
					String description = snippet.getString(DESCRIPTION);
					String published = snippet.getString(PUBLISHED);
					String channelTitle = snippet.getString("channelTitle");
					//					" THUMBNAILS INSIDE SNIPPET PART " INSIDE ITEMS ARRAY FROM JSON
					JSONObject thumbnail = snippet.getJSONObject(THUMBNAIL);
					JSONObject high = thumbnail.getJSONObject("high");
					String thumbnail_image = high.getString("url");
					newVideo.setvideo_title(title);
					newVideo.setvideo_image(thumbnail_image);
					newVideo.setVideo_channel(channelTitle);
					newVideo.setVideo_published(published);
					newVideo.setVideo_id(videoId);
					video_list.add(newVideo);


				}
			} catch (JSONException e) {
				e.printStackTrace();
			} 
			if(video_list.isEmpty()==false){
				lastViewedPosition = list.getFirstVisiblePosition();
				//get offset of first visible view
				View v = list.getChildAt(0);
				topOffset = (v == null) ? 0 : v.getTop();
				adapter = new  SearchResultAdapter(SearchResult.this, video_list);
				list.setAdapter(adapter);
				list.setFastScrollEnabled(true);
				list.setSelectionFromTop(lastViewedPosition, topOffset);


			}else{
				Toast.makeText(SearchResult.this,"NO VIDEOS AVAILABLE FOR NOW", Toast.LENGTH_SHORT).show();
			}
//			new HomeActivity().hideProgressBar();	
			isLoading = false;

		}
	}

}
