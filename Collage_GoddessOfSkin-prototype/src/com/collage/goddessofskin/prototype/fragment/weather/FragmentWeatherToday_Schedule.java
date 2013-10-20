package com.collage.goddessofskin.prototype.fragment.weather;

import com.collage.goddessofskin.api.ApiMain;
import com.collage.goddessofskin.prototype.R;

import android.app.Fragment;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class FragmentWeatherToday_Schedule extends Fragment {

	TextView view;

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.frag_weather_today_schedule,
				container, false);

		return view;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		

		view = (TextView) getActivity().findViewById(R.id.ult_text);

	
		view.setText("자외선 수치:" + ApiMain.model.getToday());

	}

}
