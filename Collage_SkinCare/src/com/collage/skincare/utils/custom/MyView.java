package com.collage.skincare.utils.custom;

import com.collage.skincare.fragment.schedule.FragScheduleBoard;
import com.collage.skincare.fragment.schedule.Sleep_Activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class MyView extends View {

   Sleep_Activity activity = new Sleep_Activity();	
	
	RectF rf = new RectF();

	Paint Pnt = new Paint();

	int imgX = 60;

	int Start = 60;
	
	int a = activity.vo3.getColor();
	

	public MyView(Context context) {
		super(context);
	}

	public MyView(Context context, AttributeSet set) {
		super(context, set);
	}

	public MyView(Context context, AttributeSet set, int defStyle) {
		super(context, set, defStyle);
	}

	public void onDraw(Canvas canvas) {

		
		
		
		
		
		

		final float ZERO = -90f; // drawAcr를 이용하면 오른쪽이 0도가 된다. 일반적으로 가장 위를 0으로
									// 보기 때문에 - 90도를 해준다.
		final float DOTONE = 72f; // 이 소스에서는 5점이 만점이기 때문에 360/5를 해서 1점당 72도를 준다.
		float score = 3.8f; // 점수. 하드코딩으로 넣었지만 나중에 변경 예정

		float degree = score * DOTONE;

		Paint p = new Paint(); // 페인트 객체 p 생성
		p.setAntiAlias(true); // 윤곽에 안티알리아싱을 처리해서 부드럽게 할건지 설정
		p.setStyle(Paint.Style.STROKE); // 원의 윤곽선만 그리는 페인트 스타일
		p.setStrokeWidth(20); // 윤곽선의 두께
		p.setAlpha(0x00); // 배경 원의 투명도. 이 부분을 00으로 투명하게 처리하지 않으면 배경 원과 점수의 원이 다
							// 보인다.

		RectF rectF = new RectF(230, 50, 530, 350); // 사각형 객체 rectF를 생성하며 점수 원의
														// 크기를 사각형으로 보고 (좌, 상,
														// 우, 하) 좌표 설정. 좌상이 기준이
														// 된다.

		if (a < 2) {
			p.setColor(Color.RED);
		} else if (a < 3) {
			p.setColor(Color.YELLOW);
		} else if (a < 4) {
			p.setColor(Color.BLUE);
		} else if (a <= 5) {
			p.setColor(Color.GREEN);
		} // 점수에 맞춰 점수 원의 색을 변경한다.

		canvas.drawArc(rectF, ZERO, degree, false, p); // 점수 원(호)를 그리는 메소드.
														// (정사각형 객체, 시작각도, 끝각도,
														// 시작각도와 끝 각도에서의 중앙으로 선을
														// 그을것이냐, 사용할 페인트 객체).
														// 각도는 시계방향으로 증가한다.

	}

	
}