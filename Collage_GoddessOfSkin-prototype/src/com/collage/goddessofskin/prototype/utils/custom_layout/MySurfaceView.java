package com.collage.goddessofskin.prototype.utils.custom_layout;





import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{

	Paint Pnt = new Paint();
	
	MyViewThread mThread;
	
	
	
	public MySurfaceView(Context context) {
		super(context);
		getHolder().addCallback(this);
	}
	
	public MySurfaceView(Context context,AttributeSet as) {
		// TODO Auto-generated constructor stub
		super(context,as);
		getHolder().addCallback(this);
	}
	
	public MySurfaceView(Context context,AttributeSet as,int style) {
		// TODO Auto-generated constructor stub
		super(context,as,style);
		getHolder().addCallback(this);
	}
	

	public void surfaceCreated(SurfaceHolder holder) {
		mThread = new MyViewThread(getHolder()); // GameThread 생성
		mThread.start();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean done = true;
		while (done) {
			try {
				mThread.join();
				done = false;
			} catch (InterruptedException e) {
			}
		}

	}

}

class MyViewThread extends Thread {
	private SurfaceHolder surfaceholder;
	private boolean running = true;

	
	
	int imgX = 0;
	int imgY = 0;

	MyViewThread(SurfaceHolder _holder) {
		surfaceholder = _holder;
	}

	public void run() {
		Canvas c;

		while (imgX<100) {
			c = null;
			try {
				c = surfaceholder.lockCanvas(null);
				synchronized (surfaceholder) {
					// drawBackground(c);
					// drawMyBitmap(c);
					drawCricle(c);
					
					imgX++;

				}
			} finally {

				if (c != null) {
					surfaceholder.unlockCanvasAndPost(c);
				}
			}
		}
	}

	private void drawCricle(Canvas canvas) {
		// TODO Auto-generated method stub

		Paint Pnt = new Paint();
		
		 Pnt.setColor(Color.WHITE);
		 canvas.drawPaint(Pnt);
		
		
		
		
		RectF r = new RectF(9, 100, 500, 100);
		RectF r2 = new RectF(190, 350, 290, 450);
		// RectF r2=new RectF(9,10,100,100);
		// Pnt.setColor(Color.RED);
		// canvas.drawRoundRect(r,10,10,Pnt);
		// r.set(110,10,150,100);
		// canvas.drawOval(r,Pnt);
//		Pnt.setColor(Color.MAGENTA);
//		r.set(10, 110, 100, 200);
//		canvas.drawArc(r, 0, 150, true, Pnt);
//		Pnt.setColor(Color.GREEN);
//		r2.set(110, 110, 200, 200);
//		canvas.drawArc(r, 10, 150, false, Pnt);
		Pnt.setColor(Color.YELLOW);
		RectF rf = new RectF(190, 350, 400, 400);
		rf.set(50,50,500,500);
		canvas.drawArc(rf, 0, imgX, true, Pnt);
		canvas.drawText("Arc", 200, 470, Pnt);
		
		Pnt.setColor(Color.WHITE);
		RectF rf2 = new RectF(100, 260, 200, 200);
		rf2.set(150,150,400,400);
		canvas.drawArc(rf2, 0, imgX, true, Pnt);
		canvas.drawText("Arc", 200, 470, Pnt);
		
		
		
		
		
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

//	private void drawMyBitmap(Canvas _canvas) {
//		_canvas.drawBitmap(testImage01, imgX, imgY, null);
//		imgX++;
//		imgY++;
//	}
}
	
	

