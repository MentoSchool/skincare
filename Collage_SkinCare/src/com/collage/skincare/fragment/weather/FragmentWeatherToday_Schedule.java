package com.collage.skincare.fragment.weather;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collage.skincare.R;
import com.collage.skincare.api.ApiMain;

public class FragmentWeatherToday_Schedule extends Fragment
{

	String ult = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.frag_weather_today_schedule, null);

		init(view);

		return view;

	}

	private void init(View view)
	{
		// TODO Auto-generated method stub

		TextView view_01 = (TextView) view.findViewById(R.id.ult_text);

		try
		{

			ult = ApiMain.model.getToday();

			if (ult == "")
			{

				view_01.setText("자외선:해가 없습니다");

			}
			else
			{

				view_01.setText("자외선:" + ult);

			}

		}
		catch (Exception e)
		{
			view_01.setText("해가 없습니다");
		}

	}

}
