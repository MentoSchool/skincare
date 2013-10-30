package com.collage.skincare.db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class DBUtils
{
	public static final String DB_NAME = "UltrvLife.sqlite";

	// DB가 있나 체크하기
	public static boolean isCheckDB(Context mContext)
	{
		String filePath = "/data/data/" + mContext.getPackageName() + "/databases/" + DB_NAME;
		File file = new File(filePath);

		if (file.exists())
		{
			return true;
		}

		return false;

	}

	// DB를 복사하기
	// assets의 /db/xxxx.db 파일을 설치된 프로그램의 내부 DB공간으로 복사하기
	public static void copyDB(Context mContext)
	{
		Log.d("MiniApp", "copyDB");
		AssetManager manager = mContext.getAssets();
		String folderPath = "/data/data/" + mContext.getPackageName() + "/databases/";
		String filePath = "/data/data/" + mContext.getPackageName() + "/databases/" + DB_NAME;
		File folder = new File(folderPath);
		File file = new File(filePath);

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try
		{
			InputStream is = manager.open("db/" + DB_NAME);
			BufferedInputStream bis = new BufferedInputStream(is);

			if (folder.exists())
			{}
			else
			{
				folder.mkdirs();
			}

			if (file.exists())
			{
				file.delete();
				file.createNewFile();
			}

			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			int read = -1;
			byte[] buffer = new byte[1024];
			while ((read = bis.read(buffer, 0, 1024)) != -1)
			{
				bos.write(buffer, 0, read);
			}

			bos.flush();

			bos.close();
			fos.close();
			bis.close();
			is.close();

		}
		catch (IOException e)
		{
			Log.e("ErrorMessage : ", e.getMessage());
		}
	}
}
