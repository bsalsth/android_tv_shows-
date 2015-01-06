/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.toolsforyoutube.youtbeplayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.androidhive.xmlparsing.XMLParser;
import com.example.youtubeexpose.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.toolsforyoutube.adapter.LyricsAdapter;
import com.toolsforyoutube.items.SearchVideoItem;
import com.toolsforyoutube.utils.AlertDialogManager;

/**
 * A simple YouTube Android API demo application which shows how to create a simple application that
 * displays a YouTube Video in a {@link YouTubePlayerView}.
 * <p>
 * Note, to use a {@link YouTubePlayerView}, your activity must extend {@link YouTubeBaseActivity}.
 */
public class PlayerViewDemoActivity extends YouTubeFailureRecoveryActivity {
	String id;
	String titleLyrics;
	String DEMOURL= null;
//	static final String URL = "http://api.chartlyrics.com/apiv1.asmx/SearchLyricDirect?artist=michael%20jackson&song=bad";
	LyricsAdapter adapter;
	List<SearchVideoItem> video_list;
	ListView list;
	private final String USER_AGENT = "Mozilla/5.0";

	// All static variables
		static final String URL = "http://api.chartlyrics.com/apiv1.asmx";
	// XML node keys
	static final String KEY_ITEM = "item"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_COST = "cost";
	static final String KEY_DESC = "description";
	public ArrayList<HashMap<String, String>> menuItems;
	public XMLParser parser ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playerview_demo);
		list = (ListView) findViewById(R.id.lyricslist);
		video_list = new ArrayList<SearchVideoItem>();

		menuItems = new ArrayList<HashMap<String, String>>();

		parser = new XMLParser();
		new parseX().execute();
		String a = null;
				
		try {
			a = sendGet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		AlertDialogManager.showAlertDialog(PlayerViewDemoActivity.this, "test", a+ "asdfghj", true);
	}


	@Override
	public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
			boolean wasRestored) {
		if (!wasRestored) {
			//	      player.cueVideo(id);
		}

	}

	@Override
	protected YouTubePlayer.Provider getYouTubePlayerProvider() {
		return (YouTubePlayerView) findViewById(R.id.youtube_view);
	}

	public class parseX extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params) {
			String xml = parser.getXmlFromUrl(URL); // getting XML
//			mno.
			return xml;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			AlertDialogManager.showAlertDialog(PlayerViewDemoActivity.this, "test",  "sd"+result,true);

//			Document doc = parser.getDomElement(result); // getting DOM element
			//
			//			NodeList n = (NodeList) doc.getChildNodes();
			//			for(int i=0;i<n.getLength();i++){
			//				Node e = (Node) n.item(i);
			//				if(n.item(i).getNodeName()=="Lyric"){
			//					AlertDialogManager.showAlertDialog(PlayerViewDemoActivity.this, "test", "eureka111", true);
			//				}
			//			}

			//		   if(n.getNodeName().equals("Lyric")){
			//			   AlertDialogManager.showAlertDialog(PlayerViewDemoActivity.this, "test", "eureka111", true);
			//		   }

			//			NodeList nl = doc.getElementsByTagName("item");
			//		NodeList nd = (NodeList) doc.getChildNodes();
			//			String abc = nd.getNodeValue();
			// looping through all item nodes <item>
			//			for (int i = 0; i < nl.getLength(); i++) {
			//				// creating new HashMap
			//				HashMap<String, String> map = new HashMap<String, String>();
			//				Element e = (Element) nl.item(i);
			//				// adding each child node to HashMap key => value
			//				map.put(KEY_ID, parser.getValue(e, KEY_ID));
			//				map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
			//				map.put(KEY_COST, "Rs." + parser.getValue(e, KEY_COST));
			//				map.put(KEY_DESC, parser.getValue(e, KEY_DESC));
			//				if(i==0){
			//					AlertDialogManager.showAlertDialog(PlayerViewDemoActivity.this, "test",  parser.getValue(e, KEY_NAME)+"", true);
			//				}
			//				// adding HashList to ArrayList
			//				menuItems.add(map);
			//			}

			//			

		}
	}
	public  String sendGet() throws Exception {
		 
		String url = "http://www.google.com/search?q=developer";
 
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
 
		// add request header
		request.addHeader("User-Agent", USER_AGENT);
 
		HttpResponse response = client.execute(request);
 
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + 
                       response.getStatusLine().getStatusCode());
 
		BufferedReader rd = new BufferedReader(
                       new InputStreamReader(response.getEntity().getContent()));
 
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
 
		System.out.println(result.toString());
		return result.toString();
	}
 

}
