package com.collage.skincare.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.collage.skincare.R;
import com.collage.skincare.api.Yh_AsyncWeather;

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

	
	private int[] mImageID = { R.drawable.pack_1, R.drawable.pack_2,
			R.drawable.pack_3, R.drawable.pack_4 };
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

		String[] weather_code = { "녹차가루 한 스푼을 넣어주세요.", "물 세 스푼을 넣어주세요.", "물과 가루가 섞이게 골고루 섞어주세요.", "섞여진 팩을 얼굴에 골고루 발라주세요." };

		if (mview == null) {
			mview = mInflater.inflate(R.layout.frag_main_pak_gallery, null);
		}

		image = (ImageView) mview.findViewById(R.id.image);
		view = (TextView) mview.findViewById(R.id.GalleryText);
		image.setBackgroundResource(mImageID[position]);
		view.setText(weather_code[position]);

		return mview;
		// return iv[position];
	}

}
