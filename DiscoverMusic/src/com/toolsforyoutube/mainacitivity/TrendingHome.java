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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.youtubeexpose.R;
import com.toolsforyoutube.adapter.CategoryAdapter;
import com.toolsforyoutube.adapter.LazyAdapterPrevious;
import com.toolsforyoutube.items.SearchVideoItem;
import com.toolsforyoutube.utils.CustomFunctions;
import com.toolsforyoutube.utils.JsonUtils;
import com.toolsforyoutube.youtbeplayer.PlayerViewDemoActivity;

public class TrendingHome extends SherlockFragment{
	ArrayList<HashMap<String, String>> inboxList;
	List<SearchVideoItem> video_list;
	LazyAdapterPrevious adapter;
	CategoryAdapter trendingAdapter;
	ListView list;


	private  final String URL = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2Cstatistics%2CcontentDetails&chart=mostPopular&key=AIzaSyBpu8hgnXbkqFVWrAvwRUEz7T13ii3I7WM";
	private  final String LoadMoreFirst = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2Cstatistics%2CcontentDetails&pageToken=";
	private  final String LoadMoreSecond = "&chart=mostPopular&key=AIzaSyBpu8hgnXbkqFVWrAvwRUEz7T13ii3I7WM";
	private String LoadMoreFinal;
	private String NextPageToken;
	private String ToatalVideoItems = null;
	private long VideoItemsTotal;

	private  final String TITLE = "title";
	private  final String VIEWS = "viewCount";
	private  final String DURATION = "duration";
	private  final String PUBLISHED = "publishedAt";
	private  final String THUMBNAIL = "thumbnails";
	String category_id;
	String videoId;
	Boolean isLoading;
	int i = 0;
	int j = 0;


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
				R.layout.trendinghome, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		list=(ListView)getActivity().findViewById(R.id.listview);
		video_list = new ArrayList<SearchVideoItem>();
		isLoading = true;
		View view = getActivity().getLayoutInflater().inflate(R.layout.progressbar, null);
		TextView tv = (TextView)view.findViewById(R.id.textView1);
		tv.setText("Please wait..");
		ProgressBar pb = (ProgressBar)view.findViewById(R.id.progressBar1);
		pb.setIndeterminate(true);
		new MyTask().execute();	
		pb.setIndeterminate(false);

		list.setOnScrollListener(new EndlessScrollListner() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				if(NextPageToken != null&&isLoading ==false){
					LoadMoreFinal = LoadMoreFirst+NextPageToken+LoadMoreSecond;
//					new LoadMore().execute(LoadMoreFinal);
					long ListSize = (long )video_list.size();
					Long.parseLong(ToatalVideoItems);
					if(ListSize==VideoItemsTotal){
						if(getActivity()!=null)
							Toast.makeText(getActivity(), "NO MORE VIDEOS", Toast.LENGTH_SHORT).show();
					}
					isLoading = false;

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
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("LOADING TRENDING VIDEOS");
			pDialog.setCancelable(false);
			pDialog.getWindow().setGravity(Gravity.BOTTOM);
			//			pDialog.show();
			if(getActivity()!=null){
				((HomeActivity)getActivity()).showProgressBar();
			}}

		@Override
		protected String doInBackground(String... params) {
			String response = JsonUtils.getJSONString(URL);
			//			System.out.println(response);
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
						VideoItemsTotal =  Long.parseLong(ToatalVideoItems);
					}

					for (int i = 0; i < items.length(); i++) {

						JSONObject videoObject = items.getJSONObject(i); 
						SearchVideoItem newVideo = new SearchVideoItem();
						videoId = videoObject.getString("id");

						//					" SNIPPET PART " INSIDE ITEMS ARRAY FROM JSON
						JSONObject snippet = videoObject.getJSONObject("snippet");
						String title = snippet.getString(TITLE);
						String published = snippet.getString(PUBLISHED);

						//					" THUMBNAILS INSIDE SNIPPET PART " INSIDE ITEMS ARRAY FROM JSON
						JSONObject thumbnail = snippet.getJSONObject(THUMBNAIL);
						JSONObject high = thumbnail.getJSONObject("default");
						String thumbnail_image = high.getString("url");


						//					" CONTENTDETAILS PART " INSIDE ITEMS ARRAY FROM JSON
						JSONObject contentDetails = videoObject.getJSONObject("contentDetails");
						String duration = contentDetails.getString(DURATION);

						//					" STATISTICS PART " INSIDE ITEMS ARRAY FROM JSON
						JSONObject statistics = videoObject.getJSONObject("statistics");

						String views = statistics.getString(VIEWS);

						//					ADDING VIDEO ITEMS
						newVideo.setVideo_id(videoId);
						newVideo.setvideo_title(title);
						newVideo.setvideo_image(thumbnail_image);
						newVideo.setVideo_views(views);
						if(duration.isEmpty()==false){
							String finaltime = CustomFunctions.convertTime(duration);
							newVideo.setVideo_duration(finaltime);
						}
						String finalDate = CustomFunctions.converDate(published);
						newVideo.setVideo_published(finalDate);
						video_list.add(newVideo);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (NullPointerException e){
				if(getActivity()!=null)
					Toast.makeText(getActivity(), "ERROR OCCURED ", Toast.LENGTH_SHORT).show();
			}

			if(video_list.isEmpty()==false){
				trendingAdapter = new CategoryAdapter(getActivity(), video_list);
				list.setAdapter(trendingAdapter);
				trendingAdapter.notifyDataSetChanged();
				j++;
				if(getActivity()!=null){
					Toast.makeText(getActivity(), "RUN j  "+j,Toast.LENGTH_SHORT ).show();
				}
				if(getActivity()!=null){
					((HomeActivity)getActivity()).hideProgressBar();}
			}else{
				if(getActivity()!=null)
				{Toast.makeText(getActivity(), "NO VIDEOS AVAILABLE FOR NOW", Toast.LENGTH_SHORT).show();
				}
			}
			isLoading = false;
		}
	}

	private	class LoadMore extends AsyncTask<String, Void, String> {

		private int lastViewedPosition;
		private int topOffset;
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			isLoading = true;
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("LOADING more VIDEOS");
			pDialog.setCancelable(false);
			pDialog.getWindow().setGravity(Gravity.BOTTOM);
			//			pDialog.show();
			if(getActivity()!=null){
				((HomeActivity)getActivity()).showProgressBar();	
			}}

		@Override
		protected String doInBackground(String... params) {
			String response = JsonUtils.getJSONString(params[0]);
			System.out.println(response);
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			i++;
			if(getActivity()!=null){
				Toast.makeText(getActivity(), "RUN "+i,Toast.LENGTH_SHORT ).show();
			}JSONObject json;
			try {
				json = new JSONObject(result);
				//				ITEMS == ARRAY FROM JSON

				if(json.has("kind")){
					JSONArray items = json.getJSONArray("items"); // this is the "items: [ ] part
					if(json.has("nextPageToken")){
						NextPageToken = json.getString("nextPageToken");
					}

					for (int i = 0; i < items.length(); i++) {

						JSONObject videoObject = items.getJSONObject(i); 
						SearchVideoItem newVideo = new SearchVideoItem();
						videoId = videoObject.getString("id");
						//					" SNIPPET PART " INSIDE ITEMS ARRAY FROM JSON
						JSONObject snippet = videoObject.getJSONObject("snippet");
						String title = snippet.getString(TITLE);
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
						//						String dislikes = statistics.getString(DISLIKES);

						//					ADDING VIDEO ITEMS
						newVideo.setVideo_id(videoId);
						newVideo.setvideo_title(title);
						newVideo.setvideo_image(thumbnail_image);
						newVideo.setVideo_views(views);
						if(duration.isEmpty()==false){
							String finaltime = CustomFunctions.convertTime(duration);
							newVideo.setVideo_duration(finaltime);
						}
						String finalDate = CustomFunctions.converDate(published);

						newVideo.setVideo_published(finalDate);
						video_list.add(newVideo);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			catch (NullPointerException e){
				if(getActivity()!=null){
					Toast.makeText(getActivity(), "ERROR OCCURED ", Toast.LENGTH_SHORT).show();
				}
			}
			if(video_list.isEmpty()==false){
				lastViewedPosition = list.getFirstVisiblePosition();
				//get offset of first visible view
				View v = list.getChildAt(0);
				topOffset = (v == null) ? 0 : v.getTop();
				Activity currentActivity = getActivity();
				if(currentActivity!=null){
					trendingAdapter = new CategoryAdapter(currentActivity, video_list);
				}
				trendingAdapter.notifyDataSetChanged();
				list.setAdapter(trendingAdapter);
				RefreshView();
				list.setFastScrollEnabled(true);
				list.setSelectionFromTop(lastViewedPosition, topOffset);


			}else{
				if(getActivity()!=null){
					Toast.makeText(getActivity(),"NO VIDEOS AVAILABLE FOR NOW", Toast.LENGTH_SHORT).show();
				}}
			if(pDialog.isShowing()){
				pDialog.dismiss();
			}
			if(getActivity()!=null){
				((HomeActivity)getActivity()).hideProgressBar();}
		}

	}

	public void RefreshView(){
		trendingAdapter.notifyDataSetChanged();
	}
}
