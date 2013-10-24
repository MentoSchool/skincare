package com.collage.goddessofskin.prototype.fragment.weather;

import com.collage.goddessofskin.prototype.R;
import com.collage.goddessofskin.prototype.utils.custom.CustemGallery;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class FragwWeatherFunSub extends Fragment {

	private CustemGallery FunSub;

	private FragWeatherFun_CustemCalleryAdapter calleryAdapter;

	private int surIn = 0;
	
	private String location ="";

	private int value[] = { R.drawable.bar_1, R.drawable.bar_2,
			R.drawable.bar_3, R.drawable.bar_4, R.drawable.bar_5, };

	ImageView imageView;
	
	TextView Fun_Location;

	public FragwWeatherFunSub(int i,String b) {
		// TODO Auto-generated constructor stub

		this.surIn = i;
		this.location=b;

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

		imageView = (ImageView) view.findViewById(R.id.value);

		Fun_Location = (TextView)view.findViewById(R.id.Fun_Location);
		imageView.setImageResource(value[surIn]);

		FunSub = (CustemGallery) view.findViewById(R.id.fun_gallery);
		calleryAdapter = new FragWeatherFun_CustemCalleryAdapter(getActivity());
        
		FunSub.setAdapter(calleryAdapter);
        Fun_Location.setText(location);
	}

}
