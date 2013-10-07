package com.collage.goddessofskin.prototype.fragment.weather;

import com.collage.goddessofskin.prototype.R;
import com.collage.goddessofskin.prototype.defined.Const.DrawerMenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class FragWeatherFun extends Fragment {

	FragwWeatherFunSub funSub;

	ImageButton button;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.frag_weather_fun, container,
				false);

		return view;
	};

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		button = (ImageButton)getActivity().findViewById(R.id.btn_015);

		button.setOnClickListener(listener);

	};

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			

			}

		
	};

}
