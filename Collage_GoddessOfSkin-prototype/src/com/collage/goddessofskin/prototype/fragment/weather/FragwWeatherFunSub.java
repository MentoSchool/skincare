package com.collage.goddessofskin.prototype.fragment.weather;

import com.collage.goddessofskin.prototype.R;
import com.collage.goddessofskin.prototype.utils.custom.CustemGallery;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragwWeatherFunSub extends Fragment{

	private CustemGallery FunSub;
	
	private FragWeatherToday_CustemCalleryAdapter calleryAdapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.frag_weather_fun_sub, container, false);
		
		init(view);
		
		
		return view;
		
		
	}

	private void init(View view) {
		// TODO Auto-generated method stub
		
		FunSub =(CustemGallery)view.findViewById(R.id.fun_gallery);
		
		calleryAdapter = new FragWeatherToday_CustemCalleryAdapter(getActivity());
		
		
		FunSub.setAdapter(calleryAdapter);

		
	}
	
	
}
