package com.collage.goddessofskin.prototype.utils.graphics;

import android.graphics.Paint;


/**
 * 텍스트 관련
 * 
 * @author @author M.O2 Songi.Zang (songi_zang)
 * @since 2011. 7. 7. 오후 5:12:59
 */
public class TextUtil {
	/**
	 * 전체 텍스트의 넓이 값을 가져오기
	 * 
	 * @param text : 입력된 스트링 값
	 * @param size : 폰트 사이즈 
	 * @return 
	 */
	public static int getTextWidth(String text, float size)
	{
		float value;
		
		Paint paint = new Paint();
		paint.setTextSize(size);				
		value = paint.measureText(text, 0, text.length()); //전체 글자의 넓이		
		return (int) value;
	}
}
