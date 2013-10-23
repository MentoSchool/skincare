package com.collage.goddessofskin.prototype.utils.custom;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;

public class mothed {

	private SurfaceHolder _holder;
	
	private Context context;

	MyViewThread thread = new MyViewThread(context, _holder);

	static Click_Vo vo = new Click_Vo();

	public mothed() {
		// TODO Auto-generated constructor stub

	}

	public void result() {
		// TODO Auto-generated method stub
		String format_h = new String("HH");

		String format_m = new String("mm");

		String format_s_h = new String("6");

		String format_s_m = new String("0");

		double dom, dom2 = 0;

		SimpleDateFormat sdf_h = new SimpleDateFormat(format_h, Locale.KOREA);
		SimpleDateFormat sdf_m = new SimpleDateFormat(format_m, Locale.KOREA);

		dom = (30 * Integer.parseInt(sdf_h.format(new Date())) + 0.25
				* Integer.parseInt(sdf_m.format(new Date())));
		dom2 = ((30 * Integer.parseInt(format_s_h)) + (0.25 * Integer
				.parseInt(format_s_m)));
		// 1시간에 30도 + 1분에 0.25도

		// thread.setImgX((int) dom);
		// thread.setImgY((int) dom2);

		vo.setStart_clock((int) dom);
		vo.setEnd_clock((int) dom2);

		Log.v("dd", "시간은" + String.valueOf((int) dom));

	}

}
