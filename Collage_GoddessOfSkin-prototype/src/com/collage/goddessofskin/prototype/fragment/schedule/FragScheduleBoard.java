package com.collage.goddessofskin.prototype.fragment.schedule;

import com.collage.goddessofskin.prototype.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class FragScheduleBoard extends Fragment
{	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
		View rootView = inflater.inflate(R.layout.frag_schedule_board, container, false);
		
		
		return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		ImageButton btn_graph=(ImageButton) getActivity().findViewById(R.id.btn_graph);
		btn_graph.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//FragScheduleGraph로 화면전환
				Fragment fragment=new FragScheduleGraph();
				FragmentManager fm=getFragmentManager();
				fm.beginTransaction()
				.replace(R.id.act_main_content_frame, fragment)
				.commit();
			}
		});
		
		Button schedule_setting_sleep =(Button) getActivity().findViewById(R.id.schedule_setting_sleep);
		schedule_setting_sleep.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				//FragScheduleSettings로 화면전환
				Fragment fragment=new FragScheduleSettings();
				FragmentManager fm=getFragmentManager();
				fm.beginTransaction()
				.replace(R.id.act_main_content_frame, fragment)
				.commit();
			
			}
		});
		
		ListView lv=(ListView) getActivity().findViewById(R.id.alarms_list);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				// Alaram List항목 눌렀을 때, 
				 
				 
			}
		});
		
		
		
		
	}
		
		
		
}

