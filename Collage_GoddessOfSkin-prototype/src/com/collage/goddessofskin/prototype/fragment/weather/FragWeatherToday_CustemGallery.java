package com.collage.goddessofskin.prototype.fragment.weather;

import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;


public class FragWeatherToday_CustemGallery extends Gallery {

	private static Camera mCamera;
	
	public FragWeatherToday_CustemGallery(Context context) {
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public FragWeatherToday_CustemGallery(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		this(context,attrs,0);
		
		
	}

	public FragWeatherToday_CustemGallery(Context context, AttributeSet attrs,
			int defStyle) {
		// TODO Auto-generated constructor stub
		super(context, attrs, defStyle);
		
		mCamera = new Camera();
		setSpacing(-40);
		
	}
	
	 protected boolean getChildStaticTransformation(View child, Transformation t) {
         final int mCenter = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
         final int childCenter = child.getLeft() + child.getWidth() / 2;
         final int childWidth = child.getWidth();
 
         t.clear();
         t.setTransformationType(Transformation.TYPE_MATRIX);
         float rate = Math.abs((float) (mCenter - childCenter) / childWidth);
 
         mCamera.save();
         final Matrix matrix = t.getMatrix();
         float zoomAmount = (float) (rate * 200.0);
         mCamera.translate(0.0f, 0.0f, zoomAmount);
         mCamera.getMatrix(matrix);
         matrix.preTranslate(-(childWidth / 2), -(childWidth / 2));
         matrix.postTranslate((childWidth / 2), (childWidth / 2));
         mCamera.restore();
 
         return true;
     }

	
	
	
	
	
	
	
}
