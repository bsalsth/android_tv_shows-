package com.toolsforyoutube.mainacitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Window;
import com.example.youtubeexpose.R;
import com.hindiserials.slidingmenu.HomeBaseActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.toolsforyoutube.imageresizer.SpinAdapter;

public class HomeActivity extends HomeBaseActivity  {
	ActionBar actionBar;
	private Fragment mContent;

	String response;
	SearchView search;
	//	Spinner variables setup
	private Spinner order , duration , type;
	private String VOrder = "";
	private String VDuration = "";
	private String VType = "";

	String[] VideoOrder = {
			"relevance",
			"date",
			"rating",
			"title",
			"videoCount",
			"viewCount"
	};
	String[] VideoDuration = {
			"any",
			"long",
			"medium",
			"short"
	};
	String[] VideoType = {
			"any",
			"episode",
			"movie"
	};

	public HomeActivity() {
		super(R.string.search_hint);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		setContentView(R.layout.activity_main);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new TrendingHome();	
		actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		getSlidingMenu().setMode(SlidingMenu.LEFT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, new TrendingHome())
		.commit();

		search = (SearchView) findViewById(R.id.searchView1);
		search.setIconifiedByDefault(false);

		//		START OF SPINNERS CALLS
		order = (Spinner)findViewById(R.id.spinner1);
		SpinAdapter SpinAdapter = new SpinAdapter(getApplicationContext(), R.layout.custom_spinner, VideoOrder);
		order.setAdapter(SpinAdapter);		

		order.setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						int position = order.getSelectedItemPosition();
						VOrder = VideoOrder[+position];
												Toast.makeText(getApplicationContext(),"You have selected "+VideoOrder[+position]+ "\n VOrder "+ VOrder ,Toast.LENGTH_LONG).show();

					}
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						VOrder  = "relevance";
												Toast.makeText(getApplicationContext(),"You have selected VOrder::"+VOrder,Toast.LENGTH_LONG).show();

					}
				}
				);

		duration = (Spinner)findViewById(R.id.spinner2);
		duration.setAdapter(new SpinAdapter(getApplicationContext(), R.layout.custom_spinner, VideoDuration));
		duration.setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						int position = duration.getSelectedItemPosition();
						VDuration = VideoDuration[+position];
												Toast.makeText(getApplicationContext(),"You have selected "+VideoDuration[+position]+"\n VideoDuration::"+VDuration,Toast.LENGTH_LONG).show();
					}
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						VDuration = "any";
												Toast.makeText(getApplicationContext(),"You have selected VideoDuration::"+VideoDuration,Toast.LENGTH_LONG).show();

					}
				}
				);

		type = (Spinner)findViewById(R.id.spinner3);
		type.setAdapter(new SpinAdapter(getApplicationContext(), R.layout.custom_spinner, VideoType));
		type.setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						int position = order.getSelectedItemPosition();
						VType = VideoType[+position];
												Toast.makeText(getApplicationContext(),"You have selected "+VideoType[+position]+ "\n VType "+ VType ,Toast.LENGTH_LONG).show();

					}
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						VType  = "any";
												Toast.makeText(getApplicationContext(),"You have selected VideoType::"+VType,Toast.LENGTH_LONG).show();

					}
				}
				);



		//		SEARCH METHOD CALLS GOES HERE

		//*** setOnQueryTextFocusChangeListener ***
		search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				//				Toast.makeText(getApplicationContext(), String.valueOf(hasFocus+"FocusChange"),
				//						Toast.LENGTH_SHORT).show();
			}
		});

		//*** setOnQueryTextListener ***
		search.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				Intent startSearch = new Intent(getApplicationContext(), SearchResult.class);
				startSearch.putExtra("SearchQuery", query);
				startSearch.putExtra("VOrder", VOrder);
				startSearch.putExtra("VDuration", VDuration);
				startSearch.putExtra("VType", VType);
				startActivity(startSearch);
				return false;
			}
			@Override
			public boolean onQueryTextChange(String newText) {
				//				Toast.makeText(getApplicationContext(), newText+"---TextChange",Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		getSlidingMenu().showContent();
	}
	
    // Should be called manually when an async task has started
    public void showProgressBar() {
        setProgressBarIndeterminateVisibility(true); 
    }
    
    // Should be called when an async task has finished
    public void hideProgressBar() {
    	setProgressBarIndeterminateVisibility(false); 
    }
}
