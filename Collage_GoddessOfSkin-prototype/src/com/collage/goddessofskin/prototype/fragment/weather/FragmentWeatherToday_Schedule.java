package com.collage.goddessofskin.prototype.fragment.weather;

import com.collage.goddessofskin.api.ActMain;
import com.collage.goddessofskin.prototype.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentWeatherToday_Schedule extends Fragment{

	TextView view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.frag_weather_today_schedule, container, false);
		
		return view;
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		view = (TextView)getActivity().findViewById(R.id.ult_text);
		
		ActMain.getInstance().onClick1();
		//우선 적용은 해놨는데  지수가 늦게 뜸
		view.setText("자외선 지수:" + ActMain.vo.getToday_UltraViolet());
		
		
	}
	
	
	
}
