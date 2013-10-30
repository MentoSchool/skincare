package com.collage.skincare.fragment.settings;

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

public class CustomAdapter extends CursorAdapter
{

	LayoutInflater inflater;

	public CustomAdapter(Context context, Cursor c)
	{
		super(context, c);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{
		// profile_list_image, textView_01, textView_02, textView_03
		ImageView iv = (ImageView) view.findViewById(R.id.profile_list_image);
		TextView tv1 = (TextView) view.findViewById(R.id.textView_01);
		TextView tv2 = (TextView) view.findViewById(R.id.textView_02);
		TextView tv3 = (TextView) view.findViewById(R.id.textView_03);
		int index = cursor.getColumnIndex(BaseColumns._ID);
		tv1.setText(String.valueOf(cursor.getLong(index)));

		index = cursor.getColumnIndex(NotesDbAdapter.TITLE);
		tv2.setText(cursor.getString(index));

		index = cursor.getColumnIndex(NotesDbAdapter.CURWHEATERCODE);
		tv3.setText(cursor.getString(index));
		iv.setImageResource(FragSettingsProfile.Image_Weather[Integer.parseInt(cursor.getString(index))]);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{
		View view = inflater.inflate(R.layout.frag_schedule_setting_list_item, null);
		return view;
	}

}
