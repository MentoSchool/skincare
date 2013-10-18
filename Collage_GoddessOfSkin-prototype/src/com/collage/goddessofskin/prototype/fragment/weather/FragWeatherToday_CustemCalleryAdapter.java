package com.collage.goddessofskin.prototype.fragment.weather;

import com.collage.goddessofskin.prototype.R;

import android.R.anim;
import android.content.*;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.net.nsd.NsdManager.RegistrationListener;
import android.util.*;
import android.view.*;
import android.widget.*;



public class FragWeatherToday_CustemCalleryAdapter extends BaseAdapter{

	
	 private Context mContext;
     private ImageView image;
     private ListView list;
     
     private LayoutInflater mInflater;
     private int count;
 
     private int[] mImageID = { R.drawable.weather_icon_01, R.drawable.weather_icon_02, R.drawable.weather_icon_03, R.drawable.weather_icon_04, R.drawable.weather_icon_05, R.drawable.weather_icon_06,
    		 R.drawable.weather_icon_07,R.drawable.weather_icon_08,R.drawable.weather_icon_09,R.drawable.weather_icon_10
             };
     private ImageView[]iv;
     
     

	public FragWeatherToday_CustemCalleryAdapter(Context c) {
		// TODO Auto-generated constructor stub
		mContext = c;
//		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		count = mImageID.length;
		
		iv= new ImageView[count];
		for(int i=0; i<count; i++){
		iv[i] = new ImageView(mContext);
		iv[i].setImageResource(mImageID[i]);
		iv[i].setScaleType(ImageView.ScaleType.FIT_XY);
		iv[i].setLayoutParams(new Gallery.LayoutParams(400, 400));
		
		}
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
//         View mview = convertView;
         
//         if (mview == null) {
//             mview = mInflater.inflate(R.layout.frag_weather_today_gallery, null);
//         }
//        
//         
//         
//         
//         
//         image = (ImageView) mview.findViewById(R.id.image);
//         
//         image.setBackgroundResource(mImageID[position]);
//         
			         return iv[position];
     }

	
}
