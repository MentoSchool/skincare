package com.collage.skincare.utils.graphics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.provider.MediaStore.Images;

import com.collage.skincare.utils.io.FileUtil;

/**
 * 사진 관련
 * 
 * @author M.O2 Jungho.Song (threeword)
 * @since [2011. 5. 18. 오후 5:40:46]
 */
public class ImageUtil
{
	private static final String TAG = ImageUtil.class.getSimpleName();

	private static final Uri STORAGE_URI = Images.Media.EXTERNAL_CONTENT_URI;
	private static final Uri THUMB_URI = Images.Thumbnails.EXTERNAL_CONTENT_URI;
	private static final Uri VIDEO_STORAGE_URI = Uri.parse("content://media/external/video/media");

	/**
	 * 이미지 추가
	 * 
	 * @param cr
	 *            컨텐츠
	 * @param title
	 *            이름
	 * @param dateTaken
	 *            시간
	 * @param location
	 *            위치
	 * @param directory
	 *            폴더명
	 * @param filename
	 *            파일명
	 * @param source
	 *            비트맵소스
	 * @param jpegData
	 *            바이트소스
	 * @param degree
	 *            각
	 * @return Uri 이미지 Uri
	 */
	public static Uri insert(ContentResolver cr, String title, long dateTaken, Location location, String directory, String filename, Bitmap source, byte[] jpegData, int[] degree)
	{

		OutputStream outputStream = null;
		String filePath = directory + "/" + filename;

		try
		{
			File dir = new File(directory);

			if (!dir.exists()) dir.mkdirs();
			File file = new File(directory, filename);
			outputStream = new FileOutputStream(file);

			if (source != null)
			{
				// 파일 저장 오류 시
				if (!source.compress(FileUtil.getFormat(), FileUtil.getQuality(), outputStream))
				{
					return null;
				}
				degree[0] = 0;
			}
			else
			{
				outputStream.write(jpegData);
				degree[0] = getExifOrientation(filePath);
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			FileUtil.closeSilently(outputStream);
		}

		ContentValues values = new ContentValues(7);
		values.put(Images.Media.TITLE, title);

		values.put(Images.Media.DISPLAY_NAME, filename);
		values.put(Images.Media.DATE_TAKEN, dateTaken);
		values.put(Images.Media.MIME_TYPE, "image/jpeg");
		values.put(Images.Media.ORIENTATION, degree[0]);
		values.put(Images.Media.DATA, filePath);
		if (location != null)
		{
			values.put(Images.Media.LATITUDE, location.getLatitude());
			values.put(Images.Media.LONGITUDE, location.getLongitude());
		}

		return cr.insert(STORAGE_URI, values);
	}

	/**
	 * 저장한 사진 데이터를 제거</p> - 편집중인 사진에 대한 데이터는 남기지 않음
	 * <p>
	 * 
	 * @param contentResolver
	 *            컨텐츠
	 * @param imageUri
	 *            데이터 Uri
	 */
	public static int delete(ContentResolver contentResolver, Uri imageUri)
	{
		return contentResolver.delete(imageUri, null, null);
	}

	public synchronized static int delete(ContentResolver contentResolver, String id)
	{
		String where = MediaStore.Images.Media._ID + "='" + id + "'";
		String[] selectionArgs = null;

		return contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, where, selectionArgs);
	}

	/**
	 * 미디어 업데이트
	 * 
	 * @param context
	 */
	public static void update(Context context)
	{
		Uri uri = Uri.parse("file://" + Environment.getExternalStorageDirectory());
		Intent refreshI = new Intent(Intent.ACTION_MEDIA_MOUNTED, uri);
		context.sendBroadcast(refreshI);
	}

	/**
	 * 이미지 각 정보
	 * 
	 * @param filepath
	 *            파일경로
	 * @return 각
	 */
	public static int getExifOrientation(String filepath)
	{
		int degree = 0;
		ExifInterface exif = null;
		try
		{
			exif = new ExifInterface(filepath);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		if (exif != null)
		{
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1)
			{
				// We only recognize a subset of orientation tag values.
				switch (orientation)
				{
					case ExifInterface.ORIENTATION_ROTATE_90:
						degree = 90;
					break;
					case ExifInterface.ORIENTATION_ROTATE_180:
						degree = 180;
					break;
					case ExifInterface.ORIENTATION_ROTATE_270:
						degree = 270;
					break;
				}
			}
		}
		return degree;
	}

	/**
	 * 이미지 정보 가져오기</p> - 여기에서는 디버깅용으로 사용
	 * 
	 * @param activity
	 *            호출한 액티비티
	 * @param contentUri
	 *            이미지 데이터 Uri
	 * @return 이미지 정보 번들
	 */
	public Bundle getImageInfo(Activity activity, Uri contentUri)
	{
		Bundle bundle = new Bundle();

		// select
		String[] projection = new String[]
		{
				MediaStore.Images.Media._ID, // 원본 이미지 id column
				MediaStore.Images.Media.DATA, // 원본 이미지 파일경로 column
				MediaStore.Images.Media.SIZE, // 원본 이미지 파일 사이즈 column
				MediaStore.Images.Media.LATITUDE, // 원본 이미지 위도 column
				MediaStore.Images.Media.LONGITUDE, // 원본 이미지 경도 column
				MediaStore.Images.Media.MINI_THUMB_MAGIC
		// 썸네일 이미지 id column
		};

		// db 쿼리
		Cursor imgCursor = activity.managedQuery(contentUri, projection, null, null, null);

		if (imgCursor != null && imgCursor.moveToFirst())
		{
			int imgIDCol = imgCursor.getColumnIndex(MediaStore.Images.Media._ID);
			int imgDataCol = imgCursor.getColumnIndex(MediaStore.Images.Media.DATA);
			int imgSizeCol = imgCursor.getColumnIndex(MediaStore.Images.Media.SIZE);
			int imgLatitudeCol = imgCursor.getColumnIndex(MediaStore.Images.Media.LATITUDE);
			int imgLongitudeCol = imgCursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE);
			int imgThumbIDCol = imgCursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE);

			bundle.putString(MediaStore.Images.Media._ID, imgCursor.getString(imgIDCol));
			bundle.putString(MediaStore.Images.Media.DATA, imgCursor.getString(imgDataCol));
			bundle.putInt(MediaStore.Images.Media.SIZE, imgCursor.getInt(imgSizeCol));
			bundle.putString(MediaStore.Images.Media.LATITUDE, imgCursor.getString(imgLatitudeCol));
			bundle.putString(MediaStore.Images.Media.LONGITUDE, imgCursor.getString(imgLongitudeCol));
			bundle.putString(MediaStore.Images.Media.MINI_THUMB_MAGIC, imgCursor.getString(imgThumbIDCol));
		}

		return bundle;
	}

	/**********************************************************************************************
	 * 인텐트 **************************************************************************************
	 **********************************************************************************************/
	/** 기본 SDK 버젼 **/
	private static final int BASE_SDK_VERSION = 11;

	/**
	 * 이미지를 설정
	 * 
	 * @param activity
	 *            호출하는 액티비티
	 * @param id
	 *            이미지 아이디
	 * @param title
	 *            다이얼로그 제목
	 * @param requestCode
	 *            onActivityResult에서 처리할 요청코드
	 */
	public static void startSetAs(Activity activity, long id, CharSequence title, int requestCode)
	{

		/*
		 * Uri mFileUri = Uri.parse(file.toString()); String mFilePath = mFileUri.getPath(); Cursor mCursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null, "_data = '" + mFilePath + "'", null,null); mCursor.moveToNext(); int cursorId = mCursor.getInt(0);
		 */

		// ContentProvider 에 접근 하기 위한, 'content' 스키마로 변경, 연락처의 사진을 등록할 경우 content로 uri를 넘겨야 적용이 된다. (android 3.0)
		Uri dataUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, (int) id);

		// 인텐트 생성
		Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
		intent.setDataAndType(dataUri, "image/*");
		intent.putExtra(Media.MIME_TYPE, "image/*");
		activity.startActivityForResult(Intent.createChooser(intent, title), requestCode);
	}

	/**
	 * 이미지 공유
	 * 
	 * @param activity
	 *            호출하는 액티비티
	 * @param file
	 *            이미지파일
	 * @param title
	 *            다이얼로그 제목
	 * @param requestCode
	 *            onActivityResult에서 처리할 요청코드
	 */
	public static void startShare(Activity activity, File file, CharSequence title, int requestCode)
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/jpg");
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		activity.startActivityForResult(Intent.createChooser(intent, title), requestCode);
	}

	/**
	 * 내장 기본 사진 선택 액티비티 호출
	 * 
	 * @param activity
	 *            호출하는 액티비티
	 * @param requestCode
	 *            onActivityResult에서 처리할 요청코드
	 */
	public static void startSelectPhoto(Activity activity, int requestCode)
	{
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 사진 자르기 액티비티 호출
	 * 
	 * @param activity
	 *            호출하는 액티비티
	 * @param reqeustCode
	 *            onActivityResult에서 처리할 요청코드
	 * @param info
	 *            사진 자르기 정보 ( UseDefaultActivity.CropInformation )
	 */
	public static void startCropPicture(Activity activity, int reqeustCode, ImageUtil.CropInformation info)
	{
		if (info.data == null) return;

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setData(info.data);
		intent.putExtra("outputX", info.outputX);
		intent.putExtra("outputY", info.outputY);
		intent.putExtra("aspectX", info.aspectX);
		intent.putExtra("aspectY", info.aspectY);
		intent.putExtra("scale", info.scale);
		intent.putExtra("scaleUpIfNeeded", info.scaleUpIfNeeded);
		intent.putExtra("noFaceDetection", info.noFaceDetection);
		intent.putExtra("circleCrop", info.circleCrop);
		intent.putExtra("return-data", info.returnData);
		intent.putExtra("outputFormat", info.outputFormat);
		activity.startActivityForResult(intent, reqeustCode);
	}

	/**
	 * 사진 자르기에서 얻은 데이터의 URI</p> - SDK 버젼(HoneyComn 이후)에 따라 자른 사진의 URI를 넘겨주는 방법이 다름!!</p> - 버젼별로 데이터를 참조하는 방법이 달라 이 메소드에서 제어
	 * 
	 * @param data
	 *            사진 자르기 후 onActivityResult에서 전달 받는 Intent
	 * @return 사진 데이터 Uri
	 */
	public static Uri getCropPicture(Intent data)
	{
		Uri returnUri;

		// SDK 버젼 확인
		int sdkVersion = Integer.parseInt(Build.VERSION.SDK);

		// HoneyComb 버젼 부터...
		if (sdkVersion >= BASE_SDK_VERSION)
		{
			returnUri = data.getData();
		}
		// 이전 버젼
		else
		{
			returnUri = Uri.parse(data.getAction());
		}

		return returnUri;
	}

	/**
	 * 사진 자르기 시 필요한 정보
	 * 
	 * @author M.O2 Jungho.Song (threeword)
	 */
	public static class CropInformation
	{
		/**
		 * 저장할 이미지 넚이</p> - 기본값 : 100
		 */
		public int outputX = 100;

		/**
		 * 저장할 이미지 높이</p> - 기본값 : 100
		 */
		public int outputY = 100;

		/**
		 * 사진 자르기 시 편집틀의 가로 비율</p> - 기본값 : 1
		 */
		public int aspectX = 1;

		/**
		 * 사진 자르기 시 편집틀의 세로 비율</p> - 기본값 : 1
		 */
		public int aspectY = 1;

		/**
		 * 저장시 크기 변경 여부</p> - 기본값 : true
		 */
		public boolean scale = true;

		/**
		 * 자동 크기 변경 여부</p> - 기본값 : true
		 */
		public boolean scaleUpIfNeeded = true;

		/**
		 * 얼굴 자동 탑지 안함 여부</p> - 기본값 : true
		 */
		public boolean noFaceDetection = true;

		/**
		 * 사진 자르기 시 편집틀을 원으로 설정 여부</p> - 기본값 : false
		 */
		public boolean circleCrop = false;

		/**
		 * 사진 자르기 시 반환 데이터 여부</p> - Activity 감에는 최대 100KByte만 전송된다. 그이상이라면 output을 이용하여 데이터로 저장할것!!</p> - 기본값 : false
		 */
		public boolean returnData = false;

		/**
		 * 저장시 파일 포맷</p> - 기본값 : jpeg
		 */
		public String outputFormat = Bitmap.CompressFormat.JPEG.toString();

		/**
		 * 사진 자르기 할 이미지 데이터 Uri</p> - 필수 설정 변수!!
		 */
		public Uri data = null;
	}
}
