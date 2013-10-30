package com.collage.skincare.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.collage.skincare.defined.Const.SkinType;

public class SharedPreferenceManager
{
	private static final String NAME = "name";
	private static final String AGE = "age";
	private static final String GENDER = "gender";
	private static final String TYPE = "type";

	private static SharedPreferences mSharedPreferences = null;

	public static class SingletonHolder
	{
		public static final SharedPreferenceManager holder = new SharedPreferenceManager();
	}

	private SharedPreferenceManager()
	{}

	public static SharedPreferenceManager getInstance(Context context)
	{
		if (mSharedPreferences == null) mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return SingletonHolder.holder;
	}

	public void setType(SkinType type)
	{
		if (mSharedPreferences != null)
		{
			mSharedPreferences.edit().putInt(TYPE, type.ordinal()).apply();
		}
	}

	public SkinType getType()
	{
		if (mSharedPreferences != null)
		{
			int type = mSharedPreferences.getInt(TYPE, SkinType.Normal.ordinal());
			return SkinType.values()[type];
		}
		else
			return SkinType.Normal;
	}

}
