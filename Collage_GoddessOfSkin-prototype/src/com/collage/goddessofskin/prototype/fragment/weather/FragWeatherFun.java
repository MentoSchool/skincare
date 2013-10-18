package com.collage.goddessofskin.prototype.fragment.weather;

import com.collage.goddessofskin.prototype.R;
import com.collage.goddessofskin.prototype.defined.Const.DrawerMenu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class FragWeatherFun extends Fragment {

	FragwWeatherFunSub funSub;
	Fragment fragment;
	ImageButton button_1,button_2,button_3,button_4,button_5;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.frag_weather_fun, container,
				false);

		return view;
	};

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		button_1 = (ImageButton) getActivity().findViewById(R.id.btn_01);
		button_2 = (ImageButton) getActivity().findViewById(R.id.btn_02);
		button_3 = (ImageButton) getActivity().findViewById(R.id.btn_03);
		button_4 = (ImageButton) getActivity().findViewById(R.id.btn_04);
		button_5 = (ImageButton) getActivity().findViewById(R.id.btn_05);

		button_1.setOnClickListener(listener);
		button_2.setOnClickListener(listener);
		button_3.setOnClickListener(listener);
		button_4.setOnClickListener(listener);
		button_5.setOnClickListener(listener);

	};

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// Fragment에서 Fragment로 이동하는 방법.
			switch (v.getId()) {
			case R.id.btn_01:
				fragment = new FragwWeatherFunSub();
				break;

			case R.id.btn_02:
				fragment = new FragwWeatherFunSub();
				break;
			case R.id.btn_03:
				fragment = new FragwWeatherFunSub();
				break;
			case R.id.btn_04:
				fragment = new FragwWeatherFunSub();
				break;
			case R.id.btn_05:
				fragment = new FragwWeatherFunSub();
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
