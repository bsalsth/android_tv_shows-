package com.toolsforyoutube.mainacitivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.youtubeexpose.R;
import com.toolsforyoutube.adapter.CategoryAdapter;
import com.toolsforyoutube.adapter.LazyAdapterPrevious;
import com.toolsforyoutube.items.SearchVideoItem;
import com.toolsforyoutube.utils.CustomFunctions;
import com.toolsforyoutube.utils.JsonUtils;
import com.toolsforyoutube.youtbeplayer.PlayerViewDemoActivity;

public class CategoryVideos extends SherlockFragment {
	ArrayList<HashMap<String, String>> inboxList;
	List<SearchVideoItem> video_list;
	LazyAdapterPrevious adapter;
	CategoryAdapter adp;
	ListView list;
	private  final String URL1 = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&chart=mostPopular&maxResults=5&videoCategoryId=";
	private  final String LoadMoreFirst = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2Cstatistics%2CcontentDetails&pageToken=";
	private  final String LoadMoreSecond = "&chart=mostPopular&key=AIzaSyBpu8hgnXbkqFVWrAvwRUEz7T13ii3I7WM";
	private String LoadMoreFinal;

	private String NextPageToken = null;
	private String ToatalVideoItems = null;
	private boolean isLoading;

	private String videoCategory;
	private  final String URL2 ="&key=AIzaSyBpu8hgnXbkqFVWrAvwRUEz7T13ii3I7WM";
	private   String URL = "";

	private  final String TITLE = "title";
	private  final String DESCRIPTION = "description"; 
	private  final String VIEWS = "viewCount";
	private  final String DURATION = "duration";
	private  final String PUBLISHED = "publishedAt";
	private  final String LIKES = "likeCount";
	private  final String DISLIKES = "dislikeCount";
	private  final String THUMBNAIL = "thumbnails";
	private  final String CHANNEL_TITLE = "channelTitle";
	String category_id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	} 

	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(
				R.layout.category_video_list, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		Bundle args = getArguments();
		if (args  != null && args.containsKey("category_id")){
			category_id = args.getString("category_id");
			videoCategory = category_id;}
		URL = URL1+videoCategory+URL2;
		video_list = new ArrayList<SearchVideoItem>();
		isLoading = true;
		new MyTask().execute(URL);

		list=(ListView)getView().findViewById(R.id.listview);
		list.setOnScrollListener(new EndlessScrollListner() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				if(NextPageToken != null&&isLoading !=true){
					LoadMoreFinal = LoadMoreFirst+NextPageToken+LoadMoreSecond;
					new MyTask().execute(LoadMoreFinal);
				}else{
					Toast.makeText(getActivity(), "NO MORE VIDEOS", Toast.LENGTH_SHORT).show();

				}
			}
		});
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SearchVideoItem item = video_list.get(position);
				String video_id = item.getVideo_id();
				Intent i = new Intent(getActivity(),PlayerViewDemoActivity.class);
				i.putExtra("video_id",video_id);
				//				Toast.makeText(appContext, YouTubeApplication.getId_map().get(position)+"ID FROM VIDEOLIST",Toast.LENGTH_SHORT).show();
				startActivity(i);
			}
		});
	}

	private	class MyTask extends AsyncTask<String, Void, String> {
		private int lastViewedPosition;
		private int topOffset;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			isLoading =true;
			if(getActivity()!=null){
				((HomeActivity)getActivity()).showProgressBar();	
			}}

		@Override
		protected String doInBackground(String... params) {
			String response = JsonUtils.getJSONString(params[0]);
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject json;
			try {
				json = new JSONObject(result);
				//				ITEMS == ARRAY FROM JSON

				if(json.has("kind")){
					JSONArray items = json.getJSONArray("items"); // this is the "items: [ ] part
					if(json.has("nextPageToken")){
						NextPageToken = json.getString("nextPageToken");
						JSONObject pageInfo = json.getJSONObject("pageInfo");
						ToatalVideoItems= pageInfo.getString("totalResults");
					}

					for (int i = 0; i < items.length(); i++) {

						JSONObject videoObject = items.getJSONObject(i); 
						SearchVideoItem newVideo = new SearchVideoItem();
						String videoId = videoObject.getString("id");
						//					JSONObject imageItems = videoObject.getJ SONObject("thumbnail");
						//					String image = imageItems.getString("hqDefault");
						//					newVideo.setvideo_image(image);

						//					" SNIPPET PART " INSIDE ITEMS ARRAY FROM JSON
						JSONObject snippet = videoObject.getJSONObject("snippet");
						String title = snippet.getString(TITLE);
						String description = snippet.getString(DESCRIPTION);
						String published = snippet.getString(PUBLISHED);

						//					" THUMBNAILS INSIDE SNIPPET PART " INSIDE ITEMS ARRAY FROM JSON
						JSONObject thumbnail = snippet.getJSONObject(THUMBNAIL);
						JSONObject high = thumbnail.getJSONObject("high");
						String thumbnail_image = high.getString("url");


						//					" CONTENTDETAILS PART " INSIDE ITEMS ARRAY FROM JSON
						JSONObject contentDetails = videoObject.getJSONObject("contentDetails");
						String duration = contentDetails.getString(DURATION);

						//					" STATISTICS PART " INSIDE ITEMS ARRAY FROM JSON
						JSONObject statistics = videoObject.getJSONObject("statistics");

						String views = statistics.getString(VIEWS);
						String likes = statistics.getString(LIKES);
//						String dislikes = statistics.getString(DISLIKES);

						//					ADDING VIDEO ITEMS
						newVideo.setVideo_id(videoId);
						newVideo.setvideo_title(title);
						newVideo.setvideo_image(thumbnail_image);
						newVideo.setVideo_views(views);
						if(duration.isEmpty()==false){
							String finaltime =  CustomFunctions.convertTime(duration);
							newVideo.setVideo_duration(finaltime);
						}
						String finalDate =  CustomFunctions.converDate(published);
						newVideo.setVideo_published(finalDate);
						video_list.add(newVideo);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			catch (NullPointerException e){
				Toast.makeText(getActivity(), "ERROR OCCURED ", Toast.LENGTH_SHORT).show();
			}

			//			adapter = new LazyAdapter(getApplicationContext(), video_list);
			if(video_list.isEmpty()==false){
				adp = new CategoryAdapter(getActivity(), video_list);
				list.setFastScrollEnabled(true);
				list.setSelectionFromTop(lastViewedPosition, topOffset);
				adp.notifyDataSetChanged();
				list.setAdapter(adp);
			}else{
				Toast.makeText(getActivity(), "NO VIDEOS AVAILABLE FOR NOW", Toast.LENGTH_SHORT).show();
			}
			if(getActivity()!=null){
				((HomeActivity)getActivity()).hideProgressBar();	
			}
			isLoading =false;
		}

	}

}
