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
	ImageButton button_soul, button_kang, button_degen, button_degu,
			button_kangju, button_busan, button_jejudo;

	
	
	
	
	private int date[] = { 1, 2, 3, 4 };

	private String[] location = { "서울", "강릉", "대전","대구", "공주", "부산", "제주도" };

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.frag_weather_fun, container,
				false);

		return view;
	};

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		button_soul = (ImageButton) getActivity().findViewById(R.id.btn_soul);
		button_kang = (ImageButton) getActivity().findViewById(R.id.btn_kang);
		 button_degen = (ImageButton)
		 getActivity().findViewById(R.id.btn_degen);
		 button_kangju = (ImageButton)
		 getActivity().findViewById(R.id.btn_kangju);
		 button_busan = (ImageButton)
		 getActivity().findViewById(R.id.btn_busan);
		 button_jejudo = (ImageButton)
		 getActivity().findViewById(R.id.btn_jejudo);
		 button_degu = (ImageButton)
		 getActivity().findViewById(R.id.btn_degu);

		button_soul.setOnClickListener(listener);
		button_kang.setOnClickListener(listener);
		button_degen.setOnClickListener(listener);
		button_kangju.setOnClickListener(listener);
		button_busan.setOnClickListener(listener);
		button_jejudo.setOnClickListener(listener);
		button_degu.setOnClickListener(listener);

	};

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// Fragment���� Fragment�� �̵��ϴ� ���.
			switch (v.getId()) {
			case R.id.btn_soul:
				fragment = new FragwWeatherFunSub(date[1], location[0]);
				break;

			case R.id.btn_kang:
				fragment = new FragwWeatherFunSub(date[2], location[1]);
				break;
			case R.id.btn_degen:

				fragment = new FragwWeatherFunSub(date[3], location[2]);
				break;
			case R.id.btn_degu:
				fragment = new FragwWeatherFunSub(date[3], location[3]);
				break;
			case R.id.btn_kangju:
				fragment = new FragwWeatherFunSub(date[0], location[4]);
				break;

			case R.id.btn_busan:
				fragment = new FragwWeatherFunSub(date[2], location[5]);
				break;

			case R.id.btn_jejudo:

				fragment = new FragwWeatherFunSub(date[0], location[6]);
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
