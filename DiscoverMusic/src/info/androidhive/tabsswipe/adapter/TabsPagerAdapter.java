package info.androidhive.tabsswipe.adapter;

import com.hindiserials.slidingmenu.SampleListFragment;
import com.toolsforyoutube.mainacitivity.CategoryVideos;
import com.toolsforyoutube.mainacitivity.MusicGenre;
import com.toolsforyoutube.mainacitivity.MusicPlaylist;
import com.toolsforyoutube.mainacitivity.TrendingHome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
String CONTENT[]={"MUSIC PLAYLIST","MUSIC GENRE","TRENDING NOW"};
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		Fragment fragment = new Fragment();	
		switch (index) {
		case 0:
			fragment=  new MusicPlaylist();
			//						return new MusicGenre();
			//						return new TrendingHome();
		case 1:
			//						return new MusicPlaylist();
			//						return new MusicGenre();
			fragment=  new SampleListFragment();

		case 2:
			//						return new MusicPlaylist();
			//						return new MusicGenre();
			fragment=  new CategoryVideos();
		default:
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}
	@Override
	public CharSequence getPageTitle(int position) {
		return CONTENT[position];
	}
	@Override
	public float getPageWidth(int position) {
		return 0.93f;
	}

}
