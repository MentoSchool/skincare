package com.collage.goddessofskin.prototype.fragment.weather;

import java.util.ArrayList;

import com.collage.goddessofskin.prototype.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class FragWeatherToday extends Fragment {

	ScheduleDialog ScheduleDialog;
	
	private FragWeatherToday_CustemGallery mCustemGallery;
	
	private final String TAG = "FragWeatherToday";

	private Button Schedule_App;
	
	private ListView Item_main;
	
	private ArrayList<FragWeatherToday_items_VO> items_VOs;
	
	private FragmentWeatherToday_ItemListAdaper itemListAdaper;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.frag_weather_today, container,
				false);

		
		
		return view;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		Schedule_App = (Button)getActivity().findViewById(R.id.Schedule_butten);
		Schedule_App.setOnClickListener(listener);
		mCustemGallery = (FragWeatherToday_CustemGallery) getActivity()
				.findViewById(R.id.gallery);
		
		
        mCustemGallery.setAdapter(new FragWeatherToday_CustemCalleryAdapter(
				getActivity()));

		viewInit();

		setContent();

	}

	private void viewInit() {
		// TODO Auto-generated method stub
		
		Item_main = (ListView)getView().findViewById(R.id.today_weather_item);
		items_VOs = new ArrayList<FragWeatherToday_items_VO>();
		itemListAdaper = new FragmentWeatherToday_ItemListAdaper(getActivity().getBaseContext(), items_VOs, R.layout.frag_weather_today_item_row);

		Item_main.setAdapter(itemListAdaper);
	}
	

	private void setContent() {
		// TODO Auto-generated method stub
	    String weather_Type_Name ="Èå¸²";
		
		String name_Of_Space = "¼­¿ï";
		
		String temperature = "23C";
		
		String wind="15";
		
		String humidity="30";
		
		FragWeatherToday_items_VO items_VO = new FragWeatherToday_items_VO();
		
	   items_VO.setWeather_Type_Name("³¯¾¾:"+ weather_Type_Name);
	   items_VO.setName_Of_Space("À§Ä¡:"+ name_Of_Space);
	   items_VO.setTemperature("¿Âµµ:"+ temperature);
	   items_VO.setHumidity("½Àµµ" + humidity);
	   items_VO.setWind("Ç³¼Ó:"+ wind);
		
		items_VOs.add(items_VO);
		
	}
	OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.Schedule_butten:
				ScheduleDialog = new ScheduleDialog();
				
				ScheduleDialog.show(getFragmentManager(), "MYTAG");
				
				break;

			}
		
			
			
		}
	};
	

	

}


