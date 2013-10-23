package com.collage.goddessofskin.prototype.fragment;

import com.collage.goddessofskin.api.Yh_AsyncWeather;
import com.collage.goddessofskin.prototype.R;

import android.R.anim;
import android.content.*;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.net.nsd.NsdManager.RegistrationListener;
import android.util.*;
import android.view.*;
import android.widget.*;

public class FragMain_Pack_CustemCalleryAdapter extends BaseAdapter {

	private Context mContext;
	private ImageView image;
	private ListView list;
	private TextView view;

	private LayoutInflater mInflater;
	private int count;

	private String[] weather_detail;

	Yh_AsyncWeather weather;

	private Resources resources;

	final static int[] Image_Weather = { R.drawable.w_12, R.drawable.w_12,
			R.drawable.w_15, R.drawable.w_08, R.drawable.w_01, R.drawable.w_06,
			R.drawable.w_11, R.drawable.w_11, R.drawable.w_06, R.drawable.w_06,
			R.drawable.w_00, R.drawable.w_06, R.drawable.w_06, R.drawable.w_03,
			R.drawable.w_03, R.drawable.w_03, R.drawable.w_03, R.drawable.w_00,
			R.drawable.w_00, R.drawable.w_16, R.drawable.w_16, R.drawable.w_16,
			R.drawable.w_16, R.drawable.w_12, R.drawable.w_12, R.drawable.w_02,
			R.drawable.w_09, R.drawable.w_05, R.drawable.w_04, R.drawable.w_05,
			R.drawable.w_04, R.drawable.w_07, R.drawable.w_13, R.drawable.w_14,
			R.drawable.w_13, R.drawable.w_01, R.drawable.w_13, R.drawable.w_08,
			R.drawable.w_08, R.drawable.w_08, R.drawable.w_06, R.drawable.w_03,
			R.drawable.w_02, R.drawable.w_03, R.drawable.w_04, R.drawable.w_08,
			R.drawable.w_02, R.drawable.w_08

	};

	private int[] mImageID = { R.drawable.w_02, R.drawable.w_03,
			R.drawable.w_04, R.drawable.w_08 };

	// private int weather_0 = Integer
	// .parseInt(weather.vo.getTodayConditionCode());
	// private int weather_1 = Integer.parseInt(weather.vo
	// .getTomorrowConditionCode());
	// private int weather_2 = Integer.parseInt(weather.vo
	// .getTomorrowConditionCode_next());
	// private int weather_3 = Integer.parseInt(weather.vo
	// .getTomorrowConditionCode_next2());
	// private int weather_4 = Integer.parseInt(weather.vo
	// .getTomorrowConditionCode_next3());

	public FragMain_Pack_CustemCalleryAdapter(Context c) {
		// TODO Auto-generated constructor stub
		mContext = c;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		count = mImageID.length;

		// 받아 온 Context에서 getResources()가저와서 R을 인식한다.

		Resources resources_1 = c.getResources();

		this.resources = resources_1;

		//
		// iv= new ImageView[count];
		// for(int i=0; i<count; i++){
		// iv[i] = new ImageView(mContext);
		// iv[i].setImageResource(mImageID[i]);
		// iv[i].setScaleType(ImageView.ScaleType.FIT_XY);
		// iv[i].setLayoutParams(new Gallery.LayoutParams(400, 400));

		// }
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View mview = convertView;

		// int[] mImageID = { R.drawable.weather_icon_01,
		// R.drawable.weather_icon_02, R.drawable.weather_icon_03,
		// R.drawable.weather_icon_04, R.drawable.weather_icon_05 };

		String[] weather_code = { "딸기팩", "살구팩", "요쿠르트", "우유" };

		if (mview == null) {
			mview = mInflater
					.inflate(R.layout.frag_main_pak_gallery, null);
		}

		image = (ImageView) mview.findViewById(R.id.image);
		view = (TextView) mview.findViewById(R.id.GalleryText);
		image.setBackgroundResource(mImageID[position]);
		view.setText(weather_code[position]);

		return mview;
		// return iv[position];
	}

}
