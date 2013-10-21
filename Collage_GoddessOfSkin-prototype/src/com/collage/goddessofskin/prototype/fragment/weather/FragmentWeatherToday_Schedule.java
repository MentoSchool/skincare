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

	String ult = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater
				.inflate(R.layout.frag_weather_today_schedule, null);

		init(view);

		return view;

	}

	private void init(View view) {
		// TODO Auto-generated method stub

		TextView view_01 = (TextView) view.findViewById(R.id.ult_text);

		try {

			ult = ApiMain.model.getToday();

			if (ult == "") {

				view_01.setText("자외선:해가 없습니다");

			} else {

				view_01.setText("자외선:" + ult);

			}

		} catch (Exception e) {
			view_01.setText("해가 없습니다");
		}

	}

}
