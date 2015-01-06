package com.hindiserials.slidingmenu;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.youtubeexpose.R;
import com.toolsforyoutube.adapter.CaltegoryListAdapter;
import com.toolsforyoutube.items.CategoryItem;
import com.toolsforyoutube.mainacitivity.CategoryVideos;
import com.toolsforyoutube.mainacitivity.CategoryFragmentActivity;
import com.toolsforyoutube.utils.JsonUtils;

public class SlidingLeftCategoryMenu extends Fragment {
	private  final String URL = "https://www.googleapis.com/youtube/v3/videoCategories?part=snippet&regionCode=US&key=AIzaSyBpu8hgnXbkqFVWrAvwRUEz7T13ii3I7WM";

	List<CategoryItem> category;
	CaltegoryListAdapter adapter;;
	ListView list;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.slidingleftcat, null);
	}


	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		list = (ListView) getView().findViewById(R.id.listView0);
		category = new ArrayList<CategoryItem>();
		new MyTask().execute();

		list.setOnItemClickListener(myOnItemClickListener);

	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	//	getting video categories from youtube api

	private	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("LOADING YOUTUBE");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String response = JsonUtils.getJSONString(URL);
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
				items = json.getJSONArray("items");

				for (int i = 0; i < items.length(); i++) {

					JSONObject videoObject = items.getJSONObject(i); 
					CategoryItem categoryItem = new CategoryItem();
					String id = videoObject.getString("id");
					JSONObject snippet = videoObject.getJSONObject("snippet");
					String title = snippet.getString("title");
					//					String channelId = snippet.getString("channelId");
					categoryItem.setCategory_id(id);
					categoryItem.setCategory_title(title);
					//					categoryItem.setCategory_channelId(channelId);
					category.add(categoryItem);

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // this is the "items: [ ] part

			ListView list = (ListView) getView().findViewById(R.id.listView0);
			adapter = new  CaltegoryListAdapter(getActivity(),category);
			list.setAdapter(adapter);
		}
	}
	OnItemClickListener myOnItemClickListener
	= new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			CategoryItem item = category.get(position);
			String category_id = item.getCategory_id();
			Fragment newContent = new CategoryVideos();
			final Bundle bundle = new Bundle();
			bundle.putString("category_id", category_id);
			newContent.setArguments(bundle);
			if (newContent != null)
				switchFragment(newContent);

			((BaseActivity)getActivity()).getSlidingMenu().toggle();
//			Toast.makeText(getActivity(),category_id +":: id clicked", Toast.LENGTH_SHORT).show();
		}};

		// the meat of switching the above fragment
		private void switchFragment(Fragment fragment) {
			if (getActivity() == null)
				return;

			if (getActivity() instanceof CategoryFragmentActivity) {
				CategoryFragmentActivity fca = (CategoryFragmentActivity) getActivity();
				fca.switchContent(fragment);
			} 

		}
}