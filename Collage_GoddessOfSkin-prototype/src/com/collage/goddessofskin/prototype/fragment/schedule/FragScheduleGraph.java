package com.collage.goddessofskin.prototype.fragment.schedule;

import java.util.zip.Inflater;

import com.collage.goddessofskin.prototype.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragScheduleGraph extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.frag_schedule_graph, container,
				false);

		return view;

	}
}
