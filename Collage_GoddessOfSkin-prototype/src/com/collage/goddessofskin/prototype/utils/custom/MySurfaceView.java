package com.collage.goddessofskin.prototype.utils.custom;

import android.R;
import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {

	Paint Pnt = new Paint();

	mothed mothed = new mothed();;

	SurfaceHolder holder;

	Message msg;

	Handler handler;

	MyViewThread mThread;

	public MySurfaceView(Context context) {
		super(context);

		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		getHolder().addCallback(this);

		mThread = new MyViewThread(getContext(), getHolder());
	}

	public MySurfaceView(Context context, AttributeSet as) {
		// TODO Auto-generated constructor stub
		super(context, as);
		getHolder().addCallback(this);

	}

	public MySurfaceView(Context context, AttributeSet as, int style) {
		// TODO Auto-generated constructor stub
		super(context, as, style);
		getHolder().addCallback(this);
	}

	// 뷰가 처음 만들어 질때 호출되는 메소드
	public void surfaceCreated(SurfaceHolder holder) {

		mothed.result();
		handler2.sendEmptyMessage(0);

	};

	Handler handler2 = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				try {

					mThread = new MyViewThread(getContext(), getHolder()); // GameThread
																			// 생성
					mThread.setDaemon(true);
					mThread.start();

				} catch (Exception e) {
					// TODO: handle exception
					restartThread();
				}

				break;

			}

		};

	};

	// 뷰화면의 크기가 변화가 생겼을때 호출되는 메소드 (화면의 폭과 높이 정보가 인자로 들어온다.)
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void restartThread() {// 스레드를 제시작하는 메소드
		// TODO Auto-generated method stub
		// 스레드 정지

		mThread.stopForever();// 스레드 정지

		mThread = null;// 스레드를 비우고

		mThread = new MyViewThread(getContext(), getHolder());

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean done = true;
		while (done) {
			try {
				mThread.stopForever();
				mThread.join();
				done = false;
			} catch (InterruptedException e) {
			}
		}

	}

}

class MyViewThread extends Thread {

	Context context;

	private SurfaceHolder surfaceholder;

	private boolean isRunning = true;
	private boolean isWait = false;

	RectF rf = new RectF();

	Paint Pnt = new Paint();

	int Start = mothed.vo.getStart_clock();// 시작
  
	int End = mothed.vo.getEnd_clock();

	int imgX = 0;

	MyViewThread(Context context, SurfaceHolder _holder) {
		surfaceholder = _holder;
		this.context = context;
	}

	// 스래드를 완전 정지하는 메소드
	public void stopForever() {
		// TODO Auto-generated method stub
		synchronized (this) {

			this.isRunning = isRunning;

			notify();

		}
	}

	public void run() {
		Canvas c;

		while (imgX < (End - Start)) {
			c = null;
			try {
				c = surfaceholder.lockCanvas(null);// 화면에 정보를 다 담을때까지 화면을 잠궈놓는다.

				synchronized (surfaceholder) {
					// drawBackground(c);
					// drawMyBitmap(c);
					drawCricle(c);
					// drawCricle_1(c);
					// drawCricle_2(c);

					imgX++;

				}
			} finally {

				if (c != null) {
					surfaceholder.unlockCanvasAndPost(c);
				}
			}
		}
	}

	private void drawCricle_2(Canvas canvas) {
		// TODO Auto-generated method stub

		RectF rf3 = new RectF();
		Pnt.setColor(Color.RED);
		rf.set(150, 50, 600, 500);// (left,top,right,bottom)
		canvas.drawArc(rf3, 240, imgX, true, Pnt);
		canvas.drawText("Arc", 200, 470, Pnt);

		RectF rf4 = new RectF();
		Pnt.setColor(Color.WHITE);
		rf.set(250, 150, 500, 400);
		canvas.drawArc(rf4, 240, imgX, true, Pnt);

	}

	private void drawCricle_1(Canvas canvas) {
		// TODO Auto-generated method stub

		Pnt.setColor(Color.WHITE);
		canvas.drawPaint(Pnt);

		RectF rf3 = new RectF();
		Pnt.setColor(Color.BLUE);
		rf3.set(50, 50, 500, 500);
		canvas.drawArc(rf3, Start - 90, imgX, true, Pnt);
		canvas.drawText("Arc", 200, 470, Pnt);
		Log.v("dd", "시작각도=" + Start);
		Log.v("dd", "끝각도=" + End);

		RectF rf4 = new RectF();
		Pnt.setColor(Color.WHITE);
		rf4.set(150, 150, 400, 400);
		canvas.drawArc(rf4, Start - 90, imgX, true, Pnt);

	}

	private void drawCricle(Canvas canvas) {
		// TODO Auto-generated method stub

		Pnt.setColor(Color.WHITE);
		canvas.drawPaint(Pnt);

		Pnt.setColor(Color.YELLOW);
		RectF rf = new RectF(190, 350, 400, 400);
		rf.set(150, 50, 600, 500);
		canvas.drawArc(rf, Start - 90, imgX, true, Pnt);
		Pnt.setColor(Color.RED);
		canvas.drawText("Arc", 190, 400, Pnt);

		Pnt.setColor(Color.WHITE);
		RectF rf2 = new RectF(100, 260, 200, 200);
		rf2.set(250, 150, 500, 400);
		canvas.drawArc(rf2, Start - 90, imgX, true, Pnt);
		Pnt.setColor(Color.RED);
		canvas.drawText("Arc", 500, 470, Pnt);

		// RectF rf3 = new RectF();
		// Pnt.setColor(Color.BLUE);
		// rf3.set(50, 50, 500, 500);
		// canvas.drawArc(rf, 100, imgX, true, Pnt);
		// canvas.drawText("Arc", 200, 470, Pnt);
		//
		// RectF rf4 = new RectF();
		// Pnt.setColor(Color.WHITE);
		// rf4.set(150, 150, 400, 400);
		// canvas.drawArc(rf2, 0, imgX, true, Pnt);
		// canvas.drawText("자외선", 200, 470, Pnt);

		// Pnt.setColor(Color.BLUE);
		// float[] pts={10,210,50,250,50,250,110,220};
		// canvas.drawLines(pts,Pnt);
		// Pnt.setColor(Color.BLACK);
		// float[] pts2={20,210,50,240,100,220};
		// canvas.drawPoints(pts2, Pnt);

	}

	public void drawBackground(Canvas _canvas) {
		_canvas.drawARGB(255, 255, 255, 255);
	}

	// private void drawMyBitmap(Canvas _canvas) {
	// _canvas.drawBitmap(testImage01, imgX, imgY, null);
	// imgX++;
	// imgY++;
	// }
}
