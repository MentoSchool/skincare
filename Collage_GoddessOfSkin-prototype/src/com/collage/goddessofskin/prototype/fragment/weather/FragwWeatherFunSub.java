package com.collage.goddessofskin.prototype.fragment.weather;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.collage.goddessofskin.api.ApiMain;
import com.collage.goddessofskin.api.Yh_AsyncWeather;
import com.collage.goddessofskin.prototype.R;
import com.collage.goddessofskin.prototype.utils.custom.CustemGallery;
import com.google.android.gms.internal.aw;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract.RawContacts.Data;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class FragwWeatherFunSub extends Fragment {

	private CustemGallery FunSub;

	private FragWeatherFun_CustemCalleryAdapter calleryAdapter;

	private int surIn = 0;

	private String location = "";

	private int value[] = { R.drawable.bar_1, R.drawable.bar_2,
			R.drawable.bar_3, R.drawable.bar_4, R.drawable.bar_5, };

	ImageView Fun_value_imageView;

	TextView Fun_Location, Fun_Date, Fun_Detail, Fun_value, Fun_main_Detail ;

	Long now = System.currentTimeMillis();

	Date data = new Date(now);

	SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy년MM월dd일 HH시");

	String strNow = sdfNow.format(data);
	
	int Temp = Integer.parseInt(Yh_AsyncWeather.vo.getTodayHigh())-Integer.parseInt(Yh_AsyncWeather.vo.getTodayLow());
	
	
	String[]Discomfort_Index=null;
	String[]Heat_Index=null;      
	String[]Dewpoint_Index=null;  
	String[]cold_Index=null;      
	String[]sunglasses_Index=null;
	String[]ggogslu_Index=null;   
	String[]fun_detail=null;   


	
	
	
	public FragwWeatherFunSub(int i, String b) {
		// TODO Auto-generated constructor stub

		this.surIn = i;
		this.location = b;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.frag_weather_fun_sub, container,
				false);

		init(view);

		return view;

	}

	private void init(View view) {
		// TODO Auto-generated method stub

		Resources resources = getResources();

		
		Discomfort_Index =resources.getStringArray(R.array.Discomfort_Index);// 불쾌지수
		Heat_Index       =resources.getStringArray(R.array.Heat_Index);// 열지수
		Dewpoint_Index   =resources.getStringArray(R.array.Dewpoint_Index);// 체감온도
		cold_Index       =resources.getStringArray(R.array.cold_Index);// 감기지수
		sunglasses_Index =resources.getStringArray(R.array.sunglasses_Index);// 선글라스지수
		ggogslu_Index    =resources.getStringArray(R.array.ggoglu_Index);// 선글라스지수
        fun_detail       =resources.getStringArray(R.array.Fun_Detail);
		

	
		Fun_value_imageView = (ImageView) view.findViewById(R.id.value);

		Fun_Date = (TextView) view.findViewById(R.id.Fun_Date);
		Fun_Location = (TextView) view.findViewById(R.id.Fun_Location);
       
		Fun_main_Detail = (TextView) view.findViewById(R.id.fun_value);
		Fun_value = (TextView) view.findViewById(R.id.fun_main_detail);
		Fun_Detail = (TextView) view.findViewById(R.id.fun_Detail);

		Fun_value_imageView.setImageResource(value[0]);

		FunSub = (CustemGallery) view.findViewById(R.id.fun_gallery);
		calleryAdapter = new FragWeatherFun_CustemCalleryAdapter(getActivity());

		Fun_Date.setText("기준:" + strNow);
		FunSub.setAdapter(calleryAdapter);
		Fun_Location.setText("위치:" + location);

		OnItemClickListener itemClickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				int resoucId = (Integer) calleryAdapter.getItem(position);

				switch (resoucId) {
				case 0:

					//꽃가루
					Fun_value.setText("안녕하세요 재미있는 꽃가루 지수(설명 tip)");
					Fun_Detail.setText(fun_detail[0]);
					Fun_main_Detail.setText(ggogslu_Index[4]);
					Toast.makeText(getActivity(), "0번 클릭", Toast.LENGTH_SHORT)
							.show();
                    Fun_value_imageView.setImageResource(value[4]);
					break;
				case 1:
					//열지수
					Fun_value.setText("안녕하세요 재미있는 불퀘 지수(설명 tip)");
					Fun_Detail.setText(fun_detail[1]);
					Fun_main_Detail.setText(Heat_Index[4]);
					
					Toast.makeText(getActivity(), "1번 클릭", Toast.LENGTH_SHORT)
							.show();
					Fun_value_imageView.setImageResource(value[4]);
					break;
				case 2:
					//썬글라스
					Fun_value.setText("안녕하세요 재미있는 썬글라스 지수(설명 tip)");
					Fun_Detail.setText(fun_detail[2]);
					Fun_main_Detail.setText(sunglasses_Index[2]);
					
					Toast.makeText(getActivity(), "2번 클릭", Toast.LENGTH_SHORT)
							.show();
					Fun_value_imageView.setImageResource(value[2]);
					break;
				case 3:
					//감기지수
					
					Fun_value.setText("안녕하세요 재미있는 감기 지수(설명 tip)");
					Fun_Detail.setText(fun_detail[3]+"("+" 일교차:"+String.valueOf(Temp)+"℃"+")");
					Fun_main_Detail.setText(cold_Index[0]);
					
					Toast.makeText(getActivity(), "3번 클릭", Toast.LENGTH_SHORT)
							.show();
				    Fun_value_imageView.setImageResource(value[3]);
					break;

				default:
					break;
				}

			}

		};

		FunSub.setOnItemClickListener(itemClickListener);

	}

}
