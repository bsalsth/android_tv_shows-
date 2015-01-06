package com.toolsforyoutube.mainacitivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.youtubeexpose.R;

public class Lyrics extends Activity implements View.OnTouchListener {

TextView _view;
ViewGroup _root;
private int _xDelta;
private int _yDelta;
String artist ;
String lyrics;
String title;
TextView artistview,lyricsview,titleview;

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.movable);
    Intent intent = getIntent();
	artist = intent.getStringExtra("lyricsArtist");
	lyrics = intent.getStringExtra("lyrics");
	title = intent.getStringExtra("lyticsTitle");
	titleview = (TextView) findViewById(R.id.title);
	lyricsview = (TextView) findViewById(R.id.lyrics);
	artistview = (TextView) findViewById(R.id.artist);
	titleview.setText(title);
	lyricsview.setText(lyrics);
	artistview.setText(artist);

    _root = (ViewGroup)findViewById(R.id.parent_view);

    _view = new TextView(this);
    _view.setText("TextView!!!!!!!!");

    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 50);
    layoutParams.leftMargin = 50;
    layoutParams.topMargin = 50;
    layoutParams.bottomMargin = -250;
    layoutParams.rightMargin = -250;
    _view.setLayoutParams(layoutParams);

    _root.setOnTouchListener(this);
    _root.addView(_view);
}

public boolean onTouch(View view, MotionEvent event) {
    final int X = (int) event.getRawX();
    final int Y = (int) event.getRawY();
    switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
            RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
            _xDelta = X - lParams.leftMargin;
            _yDelta = Y - lParams.topMargin;
            break;
        case MotionEvent.ACTION_UP:
            break;
        case MotionEvent.ACTION_POINTER_DOWN:
            break;
        case MotionEvent.ACTION_POINTER_UP:
            break;
        case MotionEvent.ACTION_MOVE:
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
            layoutParams.leftMargin = X - _xDelta;
            layoutParams.topMargin = Y - _yDelta;
            layoutParams.rightMargin = -250;
            layoutParams.bottomMargin = -250;
            view.setLayoutParams(layoutParams);
            break;
    }
    _root.invalidate();
    return true;
}}