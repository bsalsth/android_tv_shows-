package com.hindiserials.slidingmenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youtubeexpose.R;
import com.toolsforyoutube.mainacitivity.CategoryVideos;
import com.toolsforyoutube.mainacitivity.CategoryFragmentActivity;

public class SlidingRightCategoryMenu extends ListFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}


	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SampleAdapter adapter = new SampleAdapter(getActivity());
		adapter.add(new SampleItem("TOP 25 VIDEOS ", R.drawable.success));
		adapter.add(new SampleItem("TOP 25 SONGS", R.drawable.ic_launcher));
		adapter.add(new SampleItem("TOP 25 MOVIES", R.drawable.ic_launcher));
		setListAdapter(adapter);
	}

	private class SampleItem {
		public String tag;
		public int iconRes;
		public SampleItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		switch (position) {
		case 0:
			newContent = new CategoryVideos();
			((BaseActivity)getActivity()).getSlidingMenu().toggle();
			Toast.makeText(getActivity(), "one", Toast.LENGTH_SHORT).show();

			break;
		case 1:
//			((MainFragmentActivity)getActivity()).getViewPagerObject().setCurrentItem(1);
//			((BaseActivity)getActivity()).getSlidingMenu().toggle();
			Toast.makeText(getActivity(), "two", Toast.LENGTH_SHORT).show();
			newContent = new CategoryVideos();
			((BaseActivity)getActivity()).getSlidingMenu().toggle();
			break;

		case 2:
//			SendMail mail = new SendMail();
				String[] TO = {"bsalbizi@gmail.com"};
				String[] CC = {"nyimashrestha@gmail.com"};
				String subject = "Your Subject Here";
				String message = "Your suggestion here";

				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, TO);
				email.putExtra(Intent.EXTRA_SUBJECT, subject);
				email.putExtra(Intent.EXTRA_TEXT, message);
				email.putExtra(Intent.EXTRA_CC, CC);

				// need this to prompts email client only
				email.setType("message/rfc822");
				try {
					startActivity(Intent.createChooser(email, "Choose an Email client(Best with Gmail)"));
					//			         finish();
//					Log.i("Finished sending email...", "");
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(getActivity().getApplicationContext(), 
							"There is no email client installed.", Toast.LENGTH_SHORT).show();
				}

				//				mail.sendEmail();
				((BaseActivity)getActivity()).getSlidingMenu().toggle();
			break;

		}
		if (newContent != null)
			switchFragment(newContent);
	}


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
