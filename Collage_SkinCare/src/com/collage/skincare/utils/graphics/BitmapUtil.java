package com.collage.skincare.utils.graphics;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.View;

/**
 * 비트맵 관련
 * 
 * @author M.O2 Jungho.Song (threeword)
 * @since [2011. 5. 24. 오후 3:40:13]
 */
public class BitmapUtil
{

	/**
	 * 비율을 유지 하면서 비트맵 크기 변경 - 원본 비트맵의 높이가 넓이보다 큰 경우 : 지정한 높이를 기준으로 원본 비트맵 조정 - 원본 비트맵의 넓이가 높이보다 큰 경우 : 지정한 넓이를 기준으로 원본 비트맵 조정
	 * 
	 * @param srcBitmap
	 *            원본 비트맵 소스
	 * @param scaleWidth
	 *            조절할 넓이
	 * @param scaleHeight
	 *            조절할 높이
	 * @return
	 */
	public static Bitmap scaleInner(Bitmap src, int dstWitdh, int dstHeight)
	{
		int origWidth = src.getWidth();
		int origHeight = src.getHeight();
		int scaledWidth = origWidth;
		int scaledHeight = origHeight;

		if (origWidth < origHeight)
		{
			if (origHeight > dstHeight)
			{
				scaledHeight = dstHeight;
				scaledWidth = (int) ((float) origWidth * dstHeight / (float) origHeight);
			}
		}
		else
		{
			if (origWidth > dstWitdh)
			{
				scaledWidth = dstWitdh;
				scaledHeight = (int) ((float) origHeight * dstWitdh / (float) origWidth);
			}
		}

		return Bitmap.createScaledBitmap(src, scaledWidth, scaledHeight, true);
	}

	/**
	 * 비율을 유지 하면서 비트맵 크기 변경 - 원본 비트맵의 높이가 넓이보다 큰 경우 : 지정한 높이를 기준으로 원본 비트맵 조정 - 원본 비트맵의 넓이가 높이보다 큰 경우 : 지정한 넓이를 기준으로 원본 비트맵 조정
	 * 
	 * @param srcBitmap
	 *            원본 비트맵 소스
	 * @param scaleWidth
	 *            조절할 넓이
	 * @param scaleHeight
	 *            조절할 높이
	 * @return
	 */
	public static Bitmap scaleFit(Bitmap src, int dstWitdh, int dstHeight)
	{
		int origWidth = src.getWidth();
		int origHeight = src.getHeight();
		int scaledWidth = origWidth;
		int scaledHeight = origHeight;

		if (origWidth < origHeight)
		{
			if (origWidth > dstWitdh)
			{
				scaledWidth = dstWitdh;
				scaledHeight = (int) ((float) origHeight * dstWitdh / (float) origWidth);
			}
		}
		else
		{
			if (origHeight > dstHeight)
			{
				scaledHeight = dstHeight;
				scaledWidth = (int) ((float) origWidth * dstHeight / (float) origHeight);
			}
		}

		return Bitmap.createScaledBitmap(src, scaledWidth, scaledHeight, true);
	}

	/**
	 * 뷰를 비트맵으로 변환
	 * 
	 * @param view
	 *            변환 할 뷰
	 * @param root
	 *            화면 전체 캡쳐 여부
	 * @return
	 */
	public static Bitmap capture(View v, boolean root)
	{
		if (root)
		{
			v.getRootView().destroyDrawingCache();
			v.getRootView().setDrawingCacheEnabled(true);
			v.getRootView().buildDrawingCache();
			return v.getRootView().getDrawingCache();
		}
		else
		{
			v.destroyDrawingCache();
			v.setDrawingCacheEnabled(true);
			v.buildDrawingCache();
			return v.getDrawingCache();
		}
	}

	/**
	 * 비트맵 자르기
	 * 
	 * @param src
	 *            원본 비트맵
	 * @param rect
	 *            자르기 할 영역
	 * @param m
	 *            변환 매트릭스
	 * @return
	 */
	public static Bitmap crop(Bitmap src, Rect rect, Matrix m)
	{
		return Bitmap.createBitmap(src, rect.left, rect.top, rect.right, rect.bottom, (m == null) ? new Matrix() : m, true);
	}

}
