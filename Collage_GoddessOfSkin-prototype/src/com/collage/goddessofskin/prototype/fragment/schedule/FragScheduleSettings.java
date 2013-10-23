package com.collage.goddessofskin.prototype.fragment.schedule;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.collage.goddessofskin.prototype.R;
import com.collage.goddessofskin.prototype.utils.custom.CustemImageView_Clock;
import com.collage.goddessofskin.prototype.utils.custom.MySurfaceView;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

public class FragScheduleSettings extends Fragment {

	MySurfaceView surfaceView;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_schedule_settings,
				container, false);

		init(rootView);
		
		return rootView;
	}


	private void init(View rootView) {
		// TODO Auto-generated method stub
		
		surfaceView = (MySurfaceView)rootView.findViewById(R.id.MyView);
		
	
	}

}
