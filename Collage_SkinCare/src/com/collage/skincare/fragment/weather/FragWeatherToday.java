package com.collage.skincare.fragment.weather;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.collage.skincare.R;
import com.collage.skincare.api.ApiMain;
import com.collage.skincare.api.Yh_AsyncWeather;
import com.collage.skincare.db.Ult_Db;
import com.collage.skincare.utils.custom.CustemGallery;

public class FragWeatherToday extends Fragment
{

	Ult_Db db;

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

	private TextView tv_cur_Weather, tv_select_Weather, tv_local, tv_temperature_cur, tv_temperature_hi, tv_temperature_low, Gallery_text, tv_today_date, Ult_detail, Ult_score;

	private Dialog mDialog = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.frag_weather_today, container, false);

		init(view);

		return view;

	}

	private void init(View view)
	{
		// TODO Auto-generated method stub

		CreateDialog();// 스플레쉬 적용

		db = new Ult_Db(getActivity());

		Resources res = getResources();

		weather_detail = res.getStringArray(R.array.weather);

		tv_today_date = (TextView) view.findViewById(R.id.today_date);

		Schedule_App = (Button) view.findViewById(R.id.Schedule_butten);
		tv_cur_Weather = (TextView) view.findViewById(R.id.tv_cur_Weather);
		tv_select_Weather = (TextView) view.findViewById(R.id.tv_select_Weather);
		tv_local = (TextView) view.findViewById(R.id.tv_local);
		tv_temperature_cur = (TextView) view.findViewById(R.id.tv_temperature_cur);
		tv_temperature_hi = (TextView) view.findViewById(R.id.tv_temperature_hi);
		tv_temperature_low = (TextView) view.findViewById(R.id.tv_temperature_low);

		Schedule_App.setOnClickListener(listener);

		mCustemGallery = (CustemGallery) view.findViewById(R.id.gallery);

		Ult_score = (TextView) view.findViewById(R.id.tv_ult_score);
		Ult_detail = (TextView) view.findViewById(R.id.tv_ult_detail);

		;

		weather.execute(params);

	}

	private void CreateDialog()
	{
		// TODO Auto-generated method stub

		final View innerView = getActivity().getLayoutInflater().inflate(R.layout.splash_surb, null);

		mDialog = new Dialog(getActivity());
		// 타이틀 바를 지운다.
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		mDialog.setContentView(innerView);
		// Back키 눌렀을 경우 Dialog Cancle 여부 설정
		mDialog.setCancelable(false);
		// 밖을 터치 했을 때 사라지게 한다.
		// mDialog.setCanceledOnTouchOutside(true);
		// 전체화면 다이얼 설정
		mDialog.getWindow().setLayout(android.view.WindowManager.LayoutParams.MATCH_PARENT, android.view.WindowManager.LayoutParams.MATCH_PARENT);
		// 안쪽 뷰를 터치못하게 한다.
		mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
		// 전체 투명으로 한다.
		mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		mDialog.show();

	}

	public Handler handler = new Handler()
	{

		public void handleMessage(android.os.Message msg)
		{

			Resources resources = getResources();

			String[] ult_score = resources.getStringArray(R.array.ult_values);

			String[] ult_detail = resources.getStringArray(R.array.ult_score);

			switch (msg.what)
			{

				case 0:

					mDialog.dismiss();

					Weather_Detail();

					String ult = ApiMain.model.getToday();

					Log.v("dd", "자외선::" + ult);
					try
					{
						if (ult == null)
						{

							Ult_score.setText("자외선:" + ult_score[4]);
							Ult_detail.setText("설명:" + ult_detail[4]);

						}
						else if (ult != null)
						{

							if (Integer.parseInt(ult) <= 2)
							{
								Ult_score.setText("자외선:" + ult_score[4]);
								Ult_detail.setText("설명:" + ult_detail[4]);

							}
							else if (Integer.parseInt(ult) <= 5)
							{
								Ult_score.setText("자외선:" + ult_score[3]);
								Ult_detail.setText("설명:" + ult_detail[3]);

							}
							else if (Integer.parseInt(ult) <= 7)
							{
								Ult_score.setText("자외선:" + ult_score[2]);
								Ult_detail.setText("설명:" + ult_detail[2]);

							}
							else if (Integer.parseInt(ult) <= 10)
							{

								Ult_score.setText("자외선:" + ult_score[1]);
								Ult_detail.setText("설명:" + ult_detail[1]);

							}
							else if (Integer.parseInt(ult) >= 11)
							{
								Ult_score.setText("자외선:" + ult_score[0]);
								Ult_detail.setText("설명:" + ult_detail[0]);

							}

						}
					}
					catch (Exception e)
					{
						e.printStackTrace();

						Ult_score.setText("자외선:" + ult_score[4]);
						Ult_detail.setText("설명:" + ult_detail[4]);

					}
				break;

				default:
				break;
			}

		}

		private void Weather_Detail()
		{
			// TODO Auto-generated method stub

			// OnItemClickListener clickListener = new OnItemClickListener() {
			// @Override
			// public void onItemClick(AdapterView<?> arg0, View view,
			// int position, long arg3) {
			// // TODO Auto-generated method stub
			//
			//
			//
			// }
			//
			//
			// };

			calleryAdapter = new FragWeatherToday_CustemCalleryAdapter(getActivity());

			mCustemGallery.setAdapter(calleryAdapter);

			tv_today_date.setText("Date: " + Yh_AsyncWeather.vo.getCurDate());

			tv_cur_Weather.setText("현재 날씨:  " + weather_detail[Integer.parseInt(Yh_AsyncWeather.vo.getCurConditionCode())]);
			tv_select_Weather.setText("오늘 날씨:  " + weather_detail[Integer.parseInt(Yh_AsyncWeather.vo.getTodayConditionCode())]);
			tv_local.setText("지역:" + Yh_AsyncWeather.vo.getCurLocation());
			tv_temperature_cur.setText("현재 온도:" + Yh_AsyncWeather.vo.getCurTemp() + "℃");
			tv_temperature_hi.setText("최고 온도:" + Yh_AsyncWeather.vo.getTodayHigh() + "℃");
			tv_temperature_low.setText("최하 온도:" + Yh_AsyncWeather.vo.getTodayLow() + "℃");

			OnItemClickListener itemClickListener = new OnItemClickListener()
			{

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
				{
					// TODO Auto-generated method stub

					int resourcId = (Integer) calleryAdapter.getItem(position);

					Log.v("dd", String.valueOf(resourcId));

					switch (resourcId)
					{
						case 0:
							tv_today_date.setText("Date: " + Yh_AsyncWeather.vo.getTodayDate());
							tv_cur_Weather.setText("현재 날씨:  " + weather_detail[Integer.parseInt(Yh_AsyncWeather.vo.getCurConditionCode())]);
							tv_select_Weather.setText("오늘 날씨:  " + weather_detail[Integer.parseInt(Yh_AsyncWeather.vo.getTodayConditionCode())]);
							tv_local.setText("지역:" + Yh_AsyncWeather.vo.getCurLocation());
							tv_temperature_cur.setText("현재 온도:" + Yh_AsyncWeather.vo.getCurTemp() + "℃");
							tv_temperature_hi.setText("최고 온도:" + Yh_AsyncWeather.vo.getTodayHigh() + "℃");
							tv_temperature_low.setText("최하 온도:" + Yh_AsyncWeather.vo.getTodayLow() + "℃");

							Toast.makeText(getActivity(), "오늘의 날씨", Toast.LENGTH_SHORT).show();
						break;
						case 1:
							tv_today_date.setText("Date: " + Yh_AsyncWeather.vo.getTomorrowDate());
							Toast.makeText(getActivity(), "다음의 날씨", Toast.LENGTH_SHORT).show();
							tv_cur_Weather.setText("현재 날씨:  " + weather_detail[Integer.parseInt(Yh_AsyncWeather.vo.getCurConditionCode())]);
							tv_select_Weather.setText("오늘 날씨:  " + weather_detail[Integer.parseInt(Yh_AsyncWeather.vo.getTomorrowConditionCode())]);
							tv_local.setText("지역:" + Yh_AsyncWeather.vo.getCurLocation());
							tv_temperature_cur.setText("현재 온도:" + Yh_AsyncWeather.vo.getCurTemp() + "℃");
							tv_temperature_hi.setText("최고 온도:" + Yh_AsyncWeather.vo.getTomorrowHigh() + "℃");
							tv_temperature_low.setText("최하 온도:" + Yh_AsyncWeather.vo.getTomorrowLow() + "℃");

						break;
						case 2:
							tv_today_date.setText("Date: " + Yh_AsyncWeather.vo.getTomorrowDate_next());
							Toast.makeText(getActivity(), "다음2의 날씨", Toast.LENGTH_SHORT).show();
							tv_cur_Weather.setText("현재 날씨:  " + weather_detail[Integer.parseInt(Yh_AsyncWeather.vo.getCurConditionCode())]);
							tv_select_Weather.setText("오늘 날씨:  " + weather_detail[Integer.parseInt(Yh_AsyncWeather.vo.getTomorrowConditionCode_next())]);
							tv_local.setText("지역:" + Yh_AsyncWeather.vo.getCurLocation());
							tv_temperature_cur.setText("현재 온도:" + Yh_AsyncWeather.vo.getCurTemp() + "℃");
							tv_temperature_hi.setText("최고 온도:" + Yh_AsyncWeather.vo.getTomorrowHigh_next() + "℃");
							tv_temperature_low.setText("최하 온도:" + Yh_AsyncWeather.vo.getTomorrowLow_next() + "℃");
						break;
						case 3:
							tv_today_date.setText("Date: " + Yh_AsyncWeather.vo.getTomorrowDate_next2());
							Toast.makeText(getActivity(), "다음3의 날씨", Toast.LENGTH_SHORT).show();
							tv_cur_Weather.setText("현재 날씨:  " + weather_detail[Integer.parseInt(Yh_AsyncWeather.vo.getCurConditionCode())]);
							tv_select_Weather.setText("오늘 날씨:  " + weather_detail[Integer.parseInt(Yh_AsyncWeather.vo.getTomorrowConditionCode_next2())]);
							tv_local.setText("지역:" + Yh_AsyncWeather.vo.getCurLocation());
							tv_temperature_cur.setText("현재 온도:" + Yh_AsyncWeather.vo.getCurTemp() + "℃");
							tv_temperature_hi.setText("최고 온도:" + Yh_AsyncWeather.vo.getTomorrowHigh_next2() + "℃");
							tv_temperature_low.setText("최하 온도:" + Yh_AsyncWeather.vo.getTomorrowLow_next2() + "℃");
						break;
						case 4:
							tv_today_date.setText("Date: " + Yh_AsyncWeather.vo.getTomorrowDate_next3());
							Toast.makeText(getActivity(), "다음4의 날씨", Toast.LENGTH_SHORT).show();
							tv_cur_Weather.setText("현재 날씨:  " + weather_detail[Integer.parseInt(Yh_AsyncWeather.vo.getCurConditionCode())]);
							tv_select_Weather.setText("오늘 날씨:  " + weather_detail[Integer.parseInt(Yh_AsyncWeather.vo.getTomorrowConditionCode_next3())]);
							tv_local.setText("지역:" + Yh_AsyncWeather.vo.getCurLocation());
							tv_temperature_cur.setText("현재 온도:" + Yh_AsyncWeather.vo.getCurTemp() + "℃");
							tv_temperature_hi.setText("최고 온도:" + Yh_AsyncWeather.vo.getTomorrowHigh_next3() + "℃");
							tv_temperature_low.setText("최하 온도:" + Yh_AsyncWeather.vo.getTomorrowLow_next3() + "℃");
						break;
						case 5:
							tv_today_date.setText("Date: " + Yh_AsyncWeather.vo.getTomorrowConditionCode_next());
							Toast.makeText(getActivity(), "다음5의 날씨", Toast.LENGTH_SHORT).show();
							tv_cur_Weather.setText("현재 날씨:  " + weather_detail[Integer.parseInt(Yh_AsyncWeather.vo.getCurConditionCode())]);
							tv_select_Weather.setText("오늘 날씨:  " + weather_detail[Integer.parseInt(Yh_AsyncWeather.vo.getTodayConditionCode())]);
							tv_local.setText("지역:" + Yh_AsyncWeather.vo.getCurLocation());
							tv_temperature_cur.setText("현재 온도:" + Yh_AsyncWeather.vo.getCurTemp() + "℃");
							tv_temperature_hi.setText("최고 온도:" + Yh_AsyncWeather.vo.getTodayHigh() + "℃");
							tv_temperature_low.setText("최하 온도:" + Yh_AsyncWeather.vo.getTodayLow() + "℃");
						break;

						default:
						break;
					}

				}

			};
			mCustemGallery.setOnItemClickListener(itemClickListener);

		};

	};

	Yh_AsyncWeather weather = new Yh_AsyncWeather(handler);

	OnClickListener listener = new OnClickListener()
	{
		// ���̾�α�
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub

			switch (v.getId())
			{
				case R.id.Schedule_butten:

					fragment = new FragmentWeatherToday_Schedule();

				break;

			}
			if (fragment != null)
			{
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.act_main_content_frame, fragment).commit();
			}

		}
	};

}
