package com.collage.goddessofskin.prototype.utils.custom_layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation.AnimationListener;

public class MyView extends View implements Runnable {

	Thread thread;
	
	
	Canvas canvas;
	
	Thread animater = null;


	private int th;


	private Paint Pnt;

	// xml에서 MyView 클래스(custom class)를 사용하고 싶으면
	// 인자 2개, 3개짜리 생성자가 존재해야한다.
	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyView(Context context) {
		super(context);

	}

	public void start() {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	for (th = 0; th < 10; th++) {

		canvas.drawCircle(canvas.getWidth() / 2, // x좌표
		(float) (canvas.getHeight() / 2), // y좌표
		canvas.getWidth() / th, Pnt);// 반지름.
		
		try {
			thread.sleep(900, 0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
	}
		
		
		
	}
	
	
	@Override
	protected void onDraw(final Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
       this.canvas = canvas;
		final Paint Pnt = new Paint(Paint.ANTI_ALIAS_FLAG);
         this.Pnt = Pnt;
		// 파란색 원그리기
		Pnt.setColor(Color.GREEN);

	
		
		
		
//			public void run() {
//				int cur_angle = 0;
//				for (cur_angle = 0; cur_angle < 6; cur_angle++) {
//
//					canvas.drawCircle(canvas.getWidth() / 2, // x좌표
//							(float) (canvas.getHeight() / 2), // y좌표
//							canvas.getWidth() / cur_angle, Pnt);// 반지름.
//				
//					try {
//						thread.sleep( 900,0);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//				}
//				}

			

		Pnt.setColor(Color.BLUE);
		
		canvas.drawCircle(canvas.getWidth() / th, canvas.getHeight() / 2,
				canvas.getWidth() / (3 * 2), Pnt);
	}
}

// import com.collage.goddessofskin.prototype.R;
//
// import android.content.Context;
// import android.graphics.Bitmap;
// import android.graphics.BitmapFactory;
// import android.graphics.Canvas;
// import android.graphics.Color;
// import android.graphics.Matrix;
// import android.graphics.Paint;
// import android.graphics.Path;
// import android.graphics.Rect;
// import android.graphics.RectF;
// import android.util.AttributeSet;
// import android.util.Log;
// import android.view.View;
// // View 상속 - View 는 처음에 크기가 정해지지 않음...
// public class MyView extends View {
//
// private int mode;
//
// public void setMode(int mode) {
// this.mode = mode;
// }
// // xml에서 MyView 클래스(custom class)를 사용하고 싶으면
// // 인자 2개, 3개짜리 생성자가 존재해야한다.
// public MyView(Context context, AttributeSet attrs, int defStyle) {
// super(context, attrs, defStyle);
// // TODO Auto-generated constructor stub
// }
// public MyView(Context context, AttributeSet attrs) {
// super(context, attrs);
// // TODO Auto-generated constructor stub
// }
// public MyView(Context context) {
// super(context);
//
// }
//
// // 이 뷰의 크기가 처음 결정되거나, 이후 변경될 때 실행
// // 뷰의 크기를 판단하여 어떤 작업을 수행해야 하는 경우,
// // 이 메서드에서 혹은 이 메서드 실행 후에 판단해야 한다.
// @Override // 현 너비, 현 높이, 변경전 너비, 변경전 높이
// protected void onSizeChanged(int w, int h, int oldw, int oldh) {
// Log.d("ddit", "MyView 크기 : onCreate : " + w + ", " + h);
// super.onSizeChanged(w, h, oldw, oldh);
// }
// // 이 뷰가 화면에 그려져야할 필요가 있을 때 호출되는 메서드.
// // 인자로 전달된 Canvas 객체의 drawXXXX() 메서드들로 그림을 그리면,
// // 그 그림이 이 뷰(이 표면)에 출력된다.
// @Override
// protected void onDraw(Canvas canvas) {
// Paint pnt = new Paint(); // 붓과 물감 역할을 하는 객체
//
// switch (mode) {
// case 1:
// canvas.drawColor(Color.RED); // 영역 전체를 지정된 색으로 색칠
// // 상수에 없는 색은 canvas.drawColor(Color.argb(alpha, red, green, blue)())메서드로 조합
//
// pnt.setColor(Color.WHITE);
// canvas.drawPoint(10, 10, pnt); // 10, 10 에 점 찍기
// canvas.drawLine(20, 10, 200, 50, pnt); // 20, 10~200, 50 직선 그리기
// canvas.drawCircle(100, 90, 50, pnt); // 중심이 100,90이고 반지름이 50인 원 그리기
//
// pnt.setColor(Color.YELLOW);
// pnt.setAlpha(100); // 투명도
// canvas.drawRect(10, 120, 200, 220, pnt);
// // 10, 120 과 200, 220을 대각선 꼭짓점으로 하는 직사각형 그리기
//
// pnt.setColor(Color.BLACK);
// pnt.setAlpha(255);
// pnt.setTextSize(30); // 글자 크기
// // Paint 객체의 set메서드로 글자크기, 글자체, 밑줄, 장평, 기울임 등 설정 가능
// canvas.drawText("Hello world~*", 10, 250, pnt); // 글씨쓰기
// break;
// case 2:
// pnt.setColor(Color.WHITE);
// canvas.drawPaint(pnt); // 지정된 페인트로 정체영열을 색칠
// pnt.setColor(Color.CYAN);
// RectF r = new RectF(10,10,100,100); // 10, 10~100, 100 직사각형 정
// canvas.drawRoundRect(r, 15, 15, pnt); // 모서리가 둥근 사각
// r.set(100, 10, 150, 100);
// canvas.drawOval(r, pnt); // 지정된 사각형에 접하는 타원 그리기
// r.set(10, 110, 100, 200);
// canvas.drawArc(r, 10, 150, true, pnt); // 원호 (부채꼴)
// r.set(110, 110, 200, 200);
// canvas.drawArc(r, 10, 150, false, pnt); // 원호 (반달)
// break;
//
// case 3:
// canvas.drawColor(Color.LTGRAY);
// pnt.setColor(Color.BLACK);
// pnt.setStrokeWidth(15); // 선두께
// pnt.setStrokeCap(Paint.Cap.BUTT); // 선 끝모양
// canvas.drawLine(10, 10, 200, 10, pnt);
// pnt.setStrokeCap(Paint.Cap.ROUND); // 선 끝모양
// canvas.drawLine(10, 30, 200, 30, pnt);
// pnt.setStrokeCap(Paint.Cap.SQUARE); // 선 끝모양
// canvas.drawLine(10, 50, 200, 50, pnt);
//
// pnt.setStrokeWidth(5); // 선두께
// pnt.setStyle(Paint.Style.FILL); //도형 내부 색칠 여부
// canvas.drawCircle(30, 180, 20, pnt);
// pnt.setStyle(Paint.Style.FILL_AND_STROKE); //도형 내부 색칠 여부
// canvas.drawCircle(80, 180, 20, pnt);
// pnt.setStyle(Paint.Style.STROKE); //도형 내부 색칠 여부
// canvas.drawCircle(130, 180, 20, pnt);
// break;
//
// case 4:
// canvas.drawColor(Color.BLACK);
// pnt.setStrokeWidth(5);
// pnt.setColor(Color.YELLOW);
// pnt.setStyle(Paint.Style.STROKE);
//
// Path path = new Path(); // 경로 정보를 저장하는 객체
// path.addRect(100, 0, 150, 90, Path.Direction.CW); // 그리는 방향 : CW - 시계방향
// path.addCircle(50, 50, 40, Path.Direction.CCW); // CCW - 반시계 방향
// canvas.drawPath(path, pnt);
//
// path.reset(); // 초기화(저장된 내용 모두 삭제)
// path.moveTo(10, 110); // 기준점을 이동
// // 기본적으로 path에서 기준점은 마지막 경로의 마지막 지점이다.
// path.lineTo(50, 150); // 10, 110~50, 150 직선 경로
// path.rLineTo(50, -30); // 50, 150~100, 120 직선경로
// // r이 붙은 메서드는 기준점(시작점)으로부터의 상대좌표(상대경로) 사용
// path.quadTo(120, 170, 200, 110); // 2차베지어곡선 경로
// // 100, 120에서 출발하여 120, 170에 근접하고, 200, 110에서 끝나는 2차 베지어 곡선
// canvas.drawPath(path, pnt);
//
// path.reset();
// path.moveTo(10, 220);
// path.cubicTo(80, 150, 150, 220, 220, 180);
// // 10,220 에서 시작하고 80, 150과 150, 220에 근접하고 220, 180에서 끝나는 3차 베지어 곡선
// //canvas.drawPath(path, pnt);
//
// pnt.setTextSize(20);
// pnt.setStyle(Paint.Style.FILL);
// canvas.drawTextOnPath("경로 위에 글씨를 쓰자~", path, 0, 0, pnt);
// // path위 글자 시작위치, path와 글자와의 사이
// //canvas.drawTextOnPath(text, path, hOffset, vOffset, paint)
// break;
//
// case 5:
// // Bitmap 생성
// // 1. 이미지 정보가 없는 빈비트맵 객체 또는 다른 비트맵의 사본으로 생성.
// // Bitmap.createBitmap() 메서드 사용
// // 2. 다양한 이미지소스 (파일, 스트림, 배열, 리소스..) 를 비트맵 객체로 변환
// // BitmapFactory.decodeXXXX() 메서드 사용
// Bitmap bm =
// BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
// // bm의 이미지를 이 뷰의 10,10에 베껴 그리기
// canvas.drawBitmap(bm, 10, 10, null);
// // bm 이미지의 전체 영역을 이 뷰의 80, 10~110, 60 사각형 영역에 베껴 그리기
// canvas.drawBitmap(bm, null, new Rect(80, 10, 110, 60), null);
// // bm 이미지에서 10,20~40,40 사각형 영역에 해당하는 일부분만 이 뷰의 120,10~200,100 사각형 영역에 베껴 그리기.
// canvas.drawBitmap(bm, new Rect(10, 20, 40, 40), new Rect(120, 10, 200, 100),
// null);
//
// // Matrix : 이미지의 확대, 축소, 미러링, 이동, 회전 효과를 설정하는 객체.
// Matrix mtx = new Matrix();
// mtx.postScale(1.5f, -0.7f); // x축 방향으로 1.5배, y축 방향으로 0.7배로 크기변경
// // 음수는 미러링을 의미
// Bitmap bm1 =
// Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mtx, false);
// // bm 의 전체 영역을 복사하여 새로운 Bitmap 객체를 생성
//
// mtx.postRotate(45); // 45도 회전효과
// Bitmap bm2 =
// Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mtx, false);
//
// canvas.drawBitmap(bm1, 10, 250, pnt);
// canvas.drawBitmap(bm2, 100, 250, pnt);
//
//
// // Bitmap : 도화지 역할(실제 픽셀별 색상정보가 저장되는 공간)
// // Canvas : 그림 그리는 도구의 집합체
// // Paint : 붓과 물감 역할
//
// Bitmap backBmp = Bitmap.createBitmap(200, 100, Bitmap.Config.ARGB_8888);
// // 점 1개의 색상을 32 비트에 저장하면서 200 X 100 이미지를 저장할 수 있는 비트맵 객체 생성
// Canvas backCvs = new Canvas(backBmp);
// // backCsv 의 drawXXX 메서드로 그림을 그리면,
// // 캔버스와 연결된 비트맵 객체 (backBmp)에 해당 이미지가 저장.(setPixel)
// backCvs.drawColor(Color.DKGRAY);
// pnt.setColor(Color.WHITE);
// for(int x=0; x<200; x+=5){
// backCvs.drawLine(x, 0, 200-x, 100, pnt);
// }
// canvas.drawBitmap(backBmp, 10, 120, null);
// break;
// }
//
// super.onDraw(canvas);
// }
// }
