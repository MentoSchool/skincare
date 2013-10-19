package com.collage.goddessofskin.prototype.fragment.weather;

import java.nio.channels.GatheringByteChannel;
import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.zip.Inflater;

import com.collage.goddessofskin.DB.Ult_Db;
import com.collage.goddessofskin.api.ApiMain;
import com.collage.goddessofskin.api.Yh_AsyncWeather;
import com.collage.goddessofskin.prototype.ActSplash;
import com.collage.goddessofskin.prototype.R;
import com.collage.goddessofskin.prototype.utils.custom_layout.CustemGallery;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragWeatherToday extends Fragment  {

	Ult_Db db;

	SimpleCursorAdapter adapter;
	// ���� url
	String params = "http://weather.yahooapis.com/forecastrss?p=KSXX0037&u=c";

	String[] weather_detail;

	private Fragment fragment;

	private Yh_AsyncWeather yh_AsyncWeather;

	private FragWeatherToday_CustemCalleryAdapter calleryAdapter;
	
	private ListView Ult;

	private CustemGallery mCustemGallery;

	private final String TAG = "FragWeatherToday";

	private Button Schedule_App;

	private TextView tv_cur_Weather, tv_select_Weather, tv_local,
			tv_temperature_cur, tv_temperature_hi, tv_temperature_low,
			Gallery_text;

	

	private Dialog mDialog = null;

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

		CreateDialog();//스플레쉬 적용

		db = new Ult_Db(getActivity());

		Resources res = getResources();

		weather_detail = res.getStringArray(R.array.weather);
	
		

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

		mCustemGallery = (CustemGallery) getActivity().findViewById(
				R.id.gallery);

		Ult = (ListView) getActivity().findViewById(R.id.Ult_Detail);

		// String ult = ApiMain.model.getToday();
		
		

		weather.execute(params);

		// Ult.setText("�ڿܼ�:" + ult);

	}
	
	
	
	
	
	private void CreateDialog() {
		// TODO Auto-generated method stub
		
		final View innerView =getActivity().getLayoutInflater().inflate(R.layout.splash_surb, null);
		
		mDialog = new Dialog(getActivity());
		//타이틀 바를 지운다.
	    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       
        mDialog.setContentView(innerView);
        // Back키 눌렀을 경우 Dialog Cancle 여부 설정
        mDialog.setCancelable(false);
        //밖을 터치 했을 때 사라지게 한다.
   //     mDialog.setCanceledOnTouchOutside(true);
        //전체화면 다이얼 설정
        mDialog.getWindow().setLayout(android.view.WindowManager.LayoutParams.MATCH_PARENT, android.view.WindowManager.LayoutParams.MATCH_PARENT);
        //안쪽 뷰를 터치못하게 한다.
        mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, 
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        //전체 투명으로 한다.
      mDialog.getWindow().setBackgroundDrawable
      (new ColorDrawable(android.graphics.Color.TRANSPARENT));
		
      mDialog.show();
		
	}
	

	public Handler handler = new Handler() {

		

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			
			case 0:

				mDialog.dismiss();
				
				Weather_Detail();

				String ult = ApiMain.model.getToday();

				Log.v("dd", "자외선::" + ult);
				try {
					if (ult == null) {

						Prosess("5");

					} else if (ult != null) {

						if (Integer.parseInt(ult) <= 2) {
							Prosess("5");
						} else if (Integer.parseInt(ult) <= 5) {
							Prosess("4");
						} else if (Integer.parseInt(ult) <= 7) {
							Prosess("3");
						} else if (Integer.parseInt(ult) <= 10) {
							Prosess("2");
						} else if (Integer.parseInt(ult) >= 11) {
							Prosess("1");
						}

					}
				} catch (Exception e) {
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

			
			
			
//			OnItemClickListener clickListener = new OnItemClickListener() {
//				@Override
//				public void onItemClick(AdapterView<?> arg0, View view,
//						int position, long arg3) {
//					// TODO Auto-generated method stub
//		
//					
//					
//				}
//				
//				
//			};
			
			calleryAdapter = new FragWeatherToday_CustemCalleryAdapter(getActivity());	
			
			mCustemGallery.setAdapter(calleryAdapter);
			

			tv_cur_Weather.setText("현재 날씨:  "
					+ weather_detail[Integer.parseInt(Yh_AsyncWeather.vo
							.getCurConditionCode())]);
			tv_select_Weather.setText("오늘 날씨:  "
					+ weather_detail[Integer.parseInt(Yh_AsyncWeather.vo
							.getTodayConditionCode())]);
			tv_local.setText("지역:" + Yh_AsyncWeather.vo.getCurLocation());
			tv_temperature_cur.setText("현재 온도:"
					+ Yh_AsyncWeather.vo.getCurTemp() + "℃");
			tv_temperature_hi.setText("최고 온도:"
					+ Yh_AsyncWeather.vo.getTodayHigh() + "℃");
			tv_temperature_low.setText("최하 온도:"
					+ Yh_AsyncWeather.vo.getTodayLow() + "℃");
			
			
			OnItemClickListener itemClickListener = new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					
					int resourcId = (Integer)calleryAdapter.getItem(position);
					Drawable drawable = getResources().getDrawable(resourcId);
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(),resourcId);
					
					Toast.makeText(getActivity(), "Selected Image: " + getResources().getText(resourcId) 
							+ "\nHeight: " + bitmap.getHeight() + "\nWidth: " 
							+ bitmap.getWidth(), Toast.LENGTH_SHORT).show();
				}
				
				};
			mCustemGallery.setOnItemClickListener(itemClickListener);
           
	
			
			
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
		// ���̾�α�
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
