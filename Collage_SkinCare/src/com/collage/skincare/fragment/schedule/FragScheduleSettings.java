package com.collage.skincare.fragment.schedule;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collage.skincare.R;
import com.collage.skincare.utils.custom.MySurfaceView;

public class FragScheduleSettings extends Fragment
{

	MySurfaceView surfaceView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.frag_schedule_settings, container, false);

		init(rootView);

		return rootView;
	}

	private void init(View rootView)
	{
		// TODO Auto-generated method stub

		surfaceView = (MySurfaceView) rootView.findViewById(R.id.MyView);

	}

}
