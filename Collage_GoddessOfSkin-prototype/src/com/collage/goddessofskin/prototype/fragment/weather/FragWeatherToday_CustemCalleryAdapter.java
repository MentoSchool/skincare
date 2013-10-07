package com.collage.goddessofskin.prototype.fragment.weather;

import com.collage.goddessofskin.prototype.R;

import android.R.anim;
import android.content.*;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.*;
import android.view.*;
import android.widget.*;



public class FragWeatherToday_CustemCalleryAdapter extends BaseAdapter{

	
	 private Context mContext;
     private ImageView image;
     private ListView list;
     
     private LayoutInflater mInflater;
     private int count;
 
     private int[] mImageID = { R.drawable.weather_1, R.drawable.weather_2, R.drawable.weather_3, R.drawable.weather_1, R.drawable.weather_2, R.drawable.weather_3,
             };
 
     
     

	public FragWeatherToday_CustemCalleryAdapter(Context c) {
		// TODO Auto-generated constructor stub
		mContext = c;
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		count = mImageID.length;
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
         
         if (mview == null) {
             mview = mInflater.inflate(R.layout.frag_weather_today_gallery, null);
         }
        
         image = (ImageView) mview.findViewById(R.id.image);
         
         image.setBackgroundResource(mImageID[position]);
         
			         return mview;
     }

	
}
