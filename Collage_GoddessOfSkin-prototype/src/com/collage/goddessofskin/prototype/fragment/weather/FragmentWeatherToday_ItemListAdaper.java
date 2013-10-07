package com.collage.goddessofskin.prototype.fragment.weather;

import java.util.ArrayList;

import com.collage.goddessofskin.prototype.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentWeatherToday_ItemListAdaper extends BaseAdapter {

	final String TAG = "FragmentWeatherToday_ItemListAdaper";

	public Context mContext;

	private LayoutInflater mLayoutInflater;

	int resource;

	private ArrayList<FragWeatherToday_items_VO> items_VOs;

	public FragmentWeatherToday_ItemListAdaper(Context context,
			ArrayList<FragWeatherToday_items_VO> items_VOs, int resource) {
		// TODO Auto-generated constructor stub
		mContext = context;
		this.items_VOs = items_VOs;
		this.mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.resource = resource;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items_VOs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Holder holder = new Holder();
		
		if(convertView == null){
			
			convertView = mLayoutInflater.inflate(resource, null);
			
			holder.tv_weather_Type_Name = (TextView)convertView.findViewById(R.id.tv_weather_Type_Name);
			holder.tv_name_Of_Space = (TextView)convertView.findViewById(R.id.tv_name_Of_Space);
			holder.tv_temperature = (TextView)convertView.findViewById(R.id.tv_temperature);
			holder.tv_wind = (TextView)convertView.findViewById(R.id.tv_wind);
			holder.tv_humidity = (TextView)convertView.findViewById(R.id.tv_humidity);
			
			convertView.setTag(holder);
			
		}else {
			holder=(Holder)convertView.getTag();
		}
		//set content 내용 설정
		String tv_weather_Type_Name = items_VOs.get(position).getWeather_Type_Name().toString();
		String tv_name_Of_Space = items_VOs.get(position).getName_Of_Space().toString();
		String tv_temperature = items_VOs.get(position).getTemperature().toString();
		String tv_wind = items_VOs.get(position).getWind().toString();
		String tv_humidity = items_VOs.get(position).getHumidity().toString();
		
		holder.tv_weather_Type_Name.setText(tv_weather_Type_Name);
		holder.tv_name_Of_Space.setText(tv_name_Of_Space);
		holder.tv_temperature.setText(tv_temperature);
		holder.tv_wind.setText(tv_wind);
		holder.tv_humidity.setText(tv_humidity);
		
		holder.tv_weather_Type_Name.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, items_VOs.get(position).getWeather_Type_Name().toString(), Toast.LENGTH_SHORT).show();		
			}
		});

		holder.tv_name_Of_Space.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	Toast.makeText(mContext, items_VOs.get(position).getName_Of_Space().toString(), Toast.LENGTH_SHORT).show();
			}
		});
		holder.tv_temperature.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, items_VOs.get(position).getTemperature().toString(), Toast.LENGTH_SHORT).show();
				
				
			}
		});
		holder.tv_wind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, items_VOs.get(position).getWind().toString(), Toast.LENGTH_SHORT).show();	
			}
		});
	   holder.tv_humidity.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, items_VOs.get(position).getHumidity().toString(), Toast.LENGTH_SHORT).show();
		}
	} );
	   
	   return convertView;
	   
	}
	private class Holder{
		
		TextView tv_weather_Type_Name,tv_name_Of_Space,tv_wind,tv_humidity,tv_temperature;
		
		
		
		}
	
	
}
