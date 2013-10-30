package com.collage.skincare.fragment.schedule;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.collage.skincare.R;
import com.collage.skincare.db.FragScheduleBoard_Alram_Db;
import com.collage.skincare.fragment.settings.FragSettingsProfile;

public class Alram_CustomAdapter extends CursorAdapter
{

	
	private int[] Value = {R.drawable.sleep,R.drawable.water,R.drawable.ult};
	private String[] type = {"수면 시간","수분 섭취","UV 차단제"};
	
	
	LayoutInflater inflater;

	public Alram_CustomAdapter(Context context, Cursor c)
	{
		super(context, c);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{
		
	String[]Weather	=context.getResources().getStringArray(R.array.weather);
		
		
		// profile_list_image, textView_01, textView_02, textView_03
		ImageView iv = (ImageView) view.findViewById(R.id.frag_schedule_alram_image);
		TextView tv1 = (TextView) view.findViewById(R.id.textView01);
		TextView tv2 = (TextView) view.findViewById(R.id.textView02);
		TextView tv3 = (TextView) view.findViewById(R.id.textView03);
		TextView tv4 = (TextView) view.findViewById(R.id.textView04);
		
		int index = cursor.getColumnIndex(BaseColumns._ID);
		tv1.setText(String.valueOf(cursor.getLong(index)));

		index = cursor.getColumnIndex(FragScheduleBoard_Alram_Db.Cur_Time);
		tv2.setText(cursor.getString(index));

		index = cursor.getColumnIndex(FragScheduleBoard_Alram_Db.Selection_Time);
		tv3.setText(cursor.getString(index));
		
		index = cursor.getColumnIndex(FragScheduleBoard_Alram_Db.Alram_Type);
//		tv4.setText(Weather[Integer.parseInt(cursor.getString(index))]);
		iv.setImageResource(Value[Integer.parseInt(cursor.getString(index))]);
		tv4.setText(type[Integer.parseInt(cursor.getString(index))]);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{
		View view = inflater.inflate(R.layout.frag_schedule_board_list_item, null);
		return view;
	}

}
