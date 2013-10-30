package com.collage.skincare.utils.custom;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.collage.skincare.R;

public class CustemImageView_Clock extends View
{

	int cx, cy, cw; // 뷰의 중앙.시계의 폭
	int pw, ph;// 시계 바늘의 폭과 높이

	private Bitmap clock;
	private Bitmap pins[] = new Bitmap[3];

	int hour, min, sec;// 시,분,초
	int rHour, rMin, rSec;// 시,분,초침의 회전각

	private int mode;

	public void setMode(int mode)
	{
		this.mode = mode;
	}

	// xml에서 View 클레스(custom class)를 사용하고 싶으면
	// 인자 2개 3개짜리 생성자가 존재 해야한다.
	public CustemImageView_Clock(Context context, AttributeSet attrs, int defStyle)
	{
		// TODO Auto-generated constructor stub

		super(context, attrs, defStyle);

	}

	public CustemImageView_Clock(Context context, AttributeSet attrs)
	{
		// TODO Auto-generated constructor stub

		super(context, attrs);

	}

	public CustemImageView_Clock(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

		cx = display.getWidth() / 2;

		cy = (display.getHeight() - 100) / 2;// 높이의 중심을 조금 위로 잡는다.

		Resources res = context.getResources();
		clock = BitmapFactory.decodeResource(res, R.drawable.weather_icon_10);
		pins[0] = BitmapFactory.decodeResource(res, R.drawable.clock);
		pins[1] = BitmapFactory.decodeResource(res, R.drawable.clock_1);
		pins[2] = BitmapFactory.decodeResource(res, R.drawable.clock_2);

		cw = clock.getWidth() / 2;
		pw = pins[0].getWidth() / 2;
		ph = pins[0].getHeight() - 10;

		mHandler.sendEmptyMessageDelayed(0, 15);

	}

	Handler mHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			// TODO Auto-generated method stub

			mHandler.sendEmptyMessageDelayed(0, 500);
		}

	};

	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{

	};

	protected void onDraw(Canvas canvas)
	{

		CalcTime();

		canvas.drawColor(Color.WHITE);

		canvas.drawBitmap(clock, cx - pw, cy - ph, null);// 시계 배경을 캔버스에 그린다.

		canvas.rotate(rHour, cx, cy);// 시침을 회전 시킨다.

		canvas.drawBitmap(pins[2], cx - pw, cy - ph, null);

		canvas.rotate(rMin - rHour, cx, cy);// 분침을 회전 시킨다.

		canvas.drawBitmap(pins[1], cx - pw, cy - ph, null);

		canvas.rotate(rSec - rHour, cx, cy);// 분침을 호전시킨다.

		canvas.drawBitmap(pins[0], cx - pw, cy - ph, null);

		canvas.rotate(-rSec, cx, cy);// 캔버스를 원래 대로 회전 시킴

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(24);
		canvas.drawText(String.format("%2d:%2d:%2d", hour, min, sec), cx - 40, cy + cw + 50, paint);

	}

	private void CalcTime()
	{
		// TODO Auto-generated method stub
		GregorianCalendar calendar = new GregorianCalendar();
		hour = calendar.get(Calendar.HOUR);
		min = calendar.get(Calendar.MINUTE);
		sec = calendar.get(Calendar.SECOND);

		rSec = sec * 6;
		rMin = min * 6 + rSec / 60;
		rHour = hour * 6 + rMin / 60;

	};

}
