package com.collage.skincare.utils.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;

import com.collage.skincare.utils.graphics.BitmapUtil;

/**
 * 파일 관련
 * 
 * @author M.O2 Jungho.Song (threeword)
 * @since [2011. 5. 18. 오후 5:41:51]
 */
public class FileUtil
{
	/** 압축형식 **/
	private static CompressFormat COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
	/** 압축품질 **/
	private static int QUALITY = 60;

	private static int ERROR = -1;

	/**********************************************************************************************
	 * GET / SET ************************************************************************************
	 **********************************************************************************************/
	/** GET 압축 형식 **/
	public static CompressFormat getFormat()
	{
		return COMPRESS_FORMAT;
	}

	/** SET 압축 형식 **/
	public static void setFormat(CompressFormat compress_format)
	{
		COMPRESS_FORMAT = compress_format;
	}

	/** GET 압축 품질 **/
	public static int getQuality()
	{
		return QUALITY;
	}

	/** SET 압축 품질 **/
	public static void setQuality(int quality)
	{
		QUALITY = quality;
	}

	/**********************************************************************************************
	 * PUBLIC **************************************************************************************
	 **********************************************************************************************/
	/**
	 * 뷰를 파일(이미지)로 저장
	 * 
	 * @param v
	 *            뷰
	 * @param dirName
	 *            폴더명
	 * @param fileName
	 *            파일명
	 * @return 성공 여부
	 */
	public static File save(View v, String dirName, String fileName)
	{
		if (v == null || fileName == null || fileName.length() == 0) return null;

		return saveImage(BitmapUtil.capture(v, false), dirName, fileName);
	}

	/**
	 * 비트맵을 파일(이미지)로 저장
	 * 
	 * @param bmp
	 *            비트맵
	 * @param dirName
	 *            폴더명
	 * @param fileName
	 *            파일명
	 * @return 성공여부
	 */
	public static File save(Bitmap bmp, String dirName, String fileName)
	{
		if (bmp == null || fileName == null || fileName.length() == 0) return null;

		return saveImage(bmp, dirName, fileName);
	}

	/**
	 * 파일 삭제
	 * 
	 * @param filePath
	 *            파일 경로
	 * @return 파일이 정상적으로 삭제되면 true, 반대면 false
	 */
	public static Boolean delete(String filePath)
	{
		if (filePath != null)
		{
			return new File(filePath).delete();
		}

		return false;
	}

	/**
	 * InputStream 또는 OutputStream 종료
	 * 
	 * @param c
	 *            InputStream 또는 OutputStream
	 */
	public static void closeSilently(Closeable c)
	{
		if (c == null) return;

		try
		{
			c.close();
		}
		catch (Throwable t)
		{
			// do nothing
		}
	}

	/**********************************************************************************************
	 * PRIVATE *************************************************************************************
	 **********************************************************************************************/
	/**
	 * 파일로 저장하는 함수
	 * 
	 * @param bmp
	 *            저장할 비트맵
	 * @param dirName
	 *            폴더명
	 * @param fileName
	 *            파일명
	 * @return 파일이 정상적으로 저장되면 true, 반대면 false
	 */
	private static File saveImage(Bitmap bmp, String dirName, final String fileName)
	{
		try
		{
			// 폴더가 없는경우 생성
			File dir = new File(dirName);
			if (!dir.exists()) dir.mkdirs();

			File file = new File(dirName + "/" + fileName);
			FileOutputStream fos = new FileOutputStream(file);

			if (!bmp.compress(getFormat(), getQuality(), fos))
			{
				return null;
			}

			fos.flush();
			closeSilently(fos);

			return file;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 외부메모리 존재 유무
	 * 
	 * @return 외부 메모리가 존재하는 경우 true, 반대면 false
	 */
	public static Boolean externalMemoryAvailable()
	{
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 내장 가용메모리
	 * 
	 * @return
	 */
	public static long getAvailableInternalMemorySize()
	{
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * 내장 전체메모리
	 * 
	 * @return
	 */
	public static long getTotalInternalMemorySize()
	{
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}

	/**
	 * 외장 가용메모리
	 * 
	 * @return
	 */
	public static long getAvailableExternalMemorySize()
	{
		if (externalMemoryAvailable())
		{
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		}
		else
		{
			return ERROR;
		}
	}

	/**
	 * 내장 가용메모리
	 * 
	 * @return
	 */
	public static long getTotalExternalMemorySize()
	{
		if (externalMemoryAvailable())
		{
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		}
		else
		{
			return ERROR;
		}
	}

	/**
	 * 메모리 포맷 (Long -> String) 변경
	 * 
	 * @param size
	 * @return
	 */
	public static String formatSize(long size)
	{
		String suffix = null;

		if (size >= 1024)
		{
			suffix = "KB";
			size /= 1024;
			if (size >= 1024)
			{
				suffix = "MB";
				size /= 1024;
			}
		}

		StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

		int commaOffset = resultBuffer.length() - 3;
		while (commaOffset > 0)
		{
			resultBuffer.insert(commaOffset, ',');
			commaOffset -= 3;
		}

		if (suffix != null) resultBuffer.append(suffix);
		return resultBuffer.toString();
	}

}
