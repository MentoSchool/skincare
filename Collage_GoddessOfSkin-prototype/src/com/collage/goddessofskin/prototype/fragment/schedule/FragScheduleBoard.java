package com.collage.goddessofskin.prototype.fragment.schedule;

import com.collage.goddessofskin.prototype.R;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragScheduleBoard extends Fragment {

	Button btn_graph;

	Fragment fragment;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	View rootView  = inflater.inflate(R.layout.frag_schedule_board,
				container, false);

		init(rootView);
		
		
		return rootView;
	}

	private void init(View rootView2) {
		// TODO Auto-generated method stub
		btn_graph = (Button)rootView2.findViewById(R.id.btn_graph);

		btn_graph.setOnClickListener(listener);
	}

//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onActivityCreated(savedInstanceState);
//
//		btn_graph = (Button) getActivity().findViewById(R.id.btn_graph);
//
//		btn_graph.setOnClickListener(listener);
//
//	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {

			case R.id.btn_graph:

				fragment = new FragScheduleGraph();

				break;

			}

			if (fragment != null) {
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.act_main_content_frame, fragment)
						.commit();
			}

		}
	};

}
