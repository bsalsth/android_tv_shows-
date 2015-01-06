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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.youtubeexpose.R;
import com.toolsforyoutube.adapter.MusicPlaylistAdapter;
import com.toolsforyoutube.adapter.SearchResultAdapter;
import com.toolsforyoutube.adapter.MusicPlaylistAdapter;
import com.toolsforyoutube.adapter.LazyAdapterPrevious;
import com.toolsforyoutube.items.SearchVideoItem;
import com.toolsforyoutube.utils.JsonUtils;
import com.toolsforyoutube.youtbeplayer.PlayerViewDemoActivity;

public class VideosByPlaylist extends SherlockFragment{
	ArrayList<HashMap<String, String>> inboxList;
	List<SearchVideoItem> video_list;
	LazyAdapterPrevious adapter;
	MusicPlaylistAdapter adp;
	ListView list;

	private  final String TITLE = "title";
	private  final String THUMBNAIL = "thumbnails";
	String category_id;
	String videoId;
	
	private  final String URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet%2CcontentDetails&playlistId=PLFgquLnL59al-0D9dcABWn7VYFO_PkKEn&key=AIzaSyBpu8hgnXbkqFVWrAvwRUEz7T13ii3I7WM";
	private  final String LoadMoreFirst = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet%2CcontentDetails&maxResults=5&playlistId=PLFgquLnL59al-0D9dcABWn7VYFO_PkKEn&pageToken=";
	private  final String LoadMoreSecond = "&key=AIzaSyBpu8hgnXbkqFVWrAvwRUEz7T13ii3I7WM";
	private String LoadMoreFinal;
	private String NextPageToken;
	private String ToatalVideoItems = null;
	private long VideoItemsTotal;
	Boolean isLoading;
	private Button btnLoadMore;


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
				R.layout.videosbyplaylist, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		Toast.makeText(getActivity(), getFragmentManager().getClass()+"", Toast.LENGTH_SHORT).show();
		list=(ListView)getActivity().findViewById(R.id.listview);
		video_list = new ArrayList<SearchVideoItem>();
		new MyTask().execute(URL);	

		list.setOnScrollListener(new EndlessScrollListner() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				if(NextPageToken != null&&isLoading !=true){
					LoadMoreFinal = LoadMoreFirst+NextPageToken+LoadMoreSecond;
					new MyTask().execute(LoadMoreFinal);
					long ListSize = (long )video_list.size();
					Long.parseLong(ToatalVideoItems);
					if(ListSize==VideoItemsTotal){
						Toast.makeText(getActivity(), "NO MORE VIDEOS", Toast.LENGTH_SHORT).show();
					}
//					btnLoadMore.setVisibility(Button.GONE);
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
				Intent i = new Intent(getActivity(),PlayerViewDemoActivity.class);
				i.putExtra("video_id",video_id);
				i.putExtra("titleLyrics",titleLyrics);
				//				Toast.makeText(appContext, YouTubeApplication.getId_map().get(position)+"ID FROM VIDEOLIST",Toast.LENGTH_SHORT).show();
				startActivity(i);
			}
		});

	}

	private	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;		
		private int lastViewedPosition;
		private int topOffset;

		@Override
		protected void onPreExecute() {
			isLoading = true;
			super.onPreExecute();
//			pDialog.show();
			Activity currentActivity = getActivity();
			if(currentActivity!=null){
				((HomeActivity)getActivity()).showProgressBar();
			}
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
						//					" SNIPPET PART " INSIDE ITEMS ARRAY FROM JSON
						JSONObject snippet = videoObject.getJSONObject("snippet");
						String title = snippet.getString(TITLE);
						
						JSONObject contentDetails = videoObject.getJSONObject("contentDetails");
						videoId = contentDetails.getString("videoId");

						//					" THUMBNAILS INSIDE SNIPPET PART " INSIDE ITEMS ARRAY FROM JSON
						JSONObject thumbnail = snippet.getJSONObject(THUMBNAIL);
						JSONObject high = thumbnail.getJSONObject("high");
						String thumbnail_image = high.getString("url");
						
						newVideo.setVideo_id(videoId);
						newVideo.setvideo_title(title);
						newVideo.setvideo_image(thumbnail_image);
						video_list.add(newVideo);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (NullPointerException e){
				Toast.makeText(getActivity(), "ERROR OCCURED ", Toast.LENGTH_SHORT).show();
			}

			if(video_list.isEmpty()==false){
				adp = new MusicPlaylistAdapter(getActivity(), video_list);
				lastViewedPosition = list.getFirstVisiblePosition();
				//get offset of first visible view
				View v = list.getChildAt(0);
				topOffset = (v == null) ? 0 : v.getTop();
				Activity currentActivity = getActivity();
				if(currentActivity!=null){
					((HomeActivity)getActivity()).hideProgressBar();
				}
				list.setFastScrollEnabled(true);
				list.setSelectionFromTop(lastViewedPosition, topOffset);
				list.setAdapter(adp);
				isLoading = false;
				
			}else{
				Toast.makeText(getActivity(), "NO VIDEOS AVAILABLE FOR NOW", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
