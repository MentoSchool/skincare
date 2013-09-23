package com.collage.goddessofskin.prototype.fragment.schedule;

import com.collage.goddessofskin.prototype.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragScheduleBoard extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.frag_schedule_board, container, false);
		
		return rootView;
	}
}
