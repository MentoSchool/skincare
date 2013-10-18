package com.collage.goddessofskin.prototype.fragment.weather;

import java.util.ArrayList;

import com.collage.goddessofskin.DB.Ult_Db;
import com.collage.goddessofskin.api.ApiMain;
import com.collage.goddessofskin.api.Yh_AsyncWeather;
import com.collage.goddessofskin.prototype.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FragWeatherToday extends Fragment {

	Ult_Db db;

	SimpleCursorAdapter adapter;
	// 야후 url
	String params = "http://weather.yahooapis.com/forecastrss?p=KSXX0037&u=c";

	String[] weather_detail;

	private Fragment fragment;

	private Yh_AsyncWeather yh_AsyncWeather;

	private ListView Ult;

	private FragWeatherToday_CustemGallery mCustemGallery;

	private final String TAG = "FragWeatherToday";

	private Button Schedule_App;

	private ListView Item_main;

	private TextView tv_cur_Weather, tv_select_Weather, tv_local,
			tv_temperature_cur, tv_temperature_hi, tv_temperature_low;

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

		Resources res = getResources();

		weather_detail = res.getStringArray(R.array.weather);

		db = new Ult_Db(getActivity());

		Schedule_App = (Button) getActivity()
				.findViewById(R.id.Schedule_butten);
		tv_cur_Weather = (TextView) getActivity().findViewById(
				R.id.tv_cur_Weather);
		tv_select_Weather = (TextView) getActivity().findViewById(
				R.id.tv_select_Weather);
		tv_local = (TextView) getActivity().findViewById(R.id.tv_local);
		tv_temperature_cur = (TextView) getActivity().findViewById(
				R.id.tv_temperature_cur);
		tv_temperature_hi = (TextView) getActivity().findViewById(
				R.id.tv_temperature_hi);
		tv_temperature_low = (TextView) getActivity().findViewById(
				R.id.tv_temperature_low);

		Schedule_App.setOnClickListener(listener);

		mCustemGallery = (FragWeatherToday_CustemGallery) getActivity()
				.findViewById(R.id.gallery);

		mCustemGallery.setAdapter(new FragWeatherToday_CustemCalleryAdapter(
				getActivity()));

		Ult = (ListView) getActivity().findViewById(R.id.Ult_Detail);

		// String ult = ApiMain.model.getToday();

		weather.execute(params);

		// Ult.setText("자외선:" + ult);

	}

	public Handler handler = new Handler() {// 핸들러 처리부분

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 0:

				Weather_Detail();

				String ult = ApiMain.model.getToday();

				Log.v("dd","자외선 실험::" + ult);
try{				
				if(ult == null){
					
					Prosess("5");
					
				}else if(ult != null) {
					
					if (Integer.parseInt(ult) <=2 ) {
						Prosess("5");	
					}else if (Integer.parseInt(ult)<=5) {
						Prosess("4");
					}else if (Integer.parseInt(ult)<=7) {
						Prosess("3");
					}else if (Integer.parseInt(ult)<=10) {
						Prosess("2");
					}else if (Integer.parseInt(ult)>=11) {
						Prosess("1");
					}
					
					
					
				}
           }catch(Exception e){
	          e.printStackTrace();
	          Prosess("6");
           }
				break;

			default:
				break;
			}

		}

		private void Weather_Detail() {
			// TODO Auto-generated method stub

			tv_cur_Weather.setText("현재날씨:"
					+ weather_detail[Integer.parseInt(Yh_AsyncWeather.vo
							.getCurConditionCode())]);
			tv_select_Weather.setText("오늘날씨:"
					+ weather_detail[Integer.parseInt(Yh_AsyncWeather.vo
							.getTodayConditionCode())]);
			tv_local.setText("지역:" + Yh_AsyncWeather.vo.getCurLocation());
			tv_temperature_cur.setText("현재:" + Yh_AsyncWeather.vo.getCurTemp()
					+ "℃");
			tv_temperature_hi.setText("최고:" + Yh_AsyncWeather.vo.getTodayHigh()
					+ "℃");
			tv_temperature_low.setText("최하:" + Yh_AsyncWeather.vo.getTodayLow()
					+ "℃");

		};

	};

	Yh_AsyncWeather weather = new Yh_AsyncWeather(handler);

	private void Prosess(String id) {
		// TODO Auto-generated method stub

		Cursor cursor = db.selectNotes(id);

		adapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_1, cursor, new String[] {
						"title", "_id" }, new int[] { android.R.id.text1 });

		Ult.setAdapter(adapter);

	}

	OnClickListener listener = new OnClickListener() {
		// 다이얼로그
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {
			case R.id.Schedule_butten:

				fragment = new FragmentWeatherToday_Schedule();

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
