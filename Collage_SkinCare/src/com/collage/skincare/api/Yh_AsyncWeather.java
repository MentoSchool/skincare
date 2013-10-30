package com.collage.skincare.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class Yh_AsyncWeather extends AsyncTask<String, Void, Void>
{

	private static Yh_AsyncWeather asyncWeather_instance = new Yh_AsyncWeather();

	private Yh_AsyncWeather()
	{
		// TODO Auto-generated constructor stub
	}

	public static Yh_AsyncWeather getInstance()
	{
		if (asyncWeather_instance == null) asyncWeather_instance = new Yh_AsyncWeather();

		return asyncWeather_instance;
	}

	public static Weather_VO vo = new Weather_VO();

	ArrayList<String> list = new ArrayList<String>();

	final protected int TIME = 5000;

	final protected int NO_ERROR = 1;
	final protected int ERROR = 2;

	Handler mHandler;

	public Yh_AsyncWeather(Handler mHandler2)
	{
		// TODO Auto-generated constructor stub

		this.mHandler = mHandler2;

	}

	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(String... params)
	{

		System.setProperty("http.keepAlive", "false");
		HttpURLConnection urlConnection = null;

		try
		{
			URL url = new URL(params[0]);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoInput(true);
			urlConnection.setReadTimeout(TIME);
			urlConnection.setConnectTimeout(TIME);
			urlConnection.setInstanceFollowRedirects(false);
			urlConnection.connect();

			int responseCode = urlConnection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK)
			{
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();

				InputStream in = urlConnection.getInputStream();
				xpp.setInput(in, "UTF-8");

				// mApplicationSample.clearWeatherData();

				int count = 0;

				String tag = null;

				String curHumidity = null;
				String curLocation = null;
				String curCondition = null;
				String curConditionCode = null;
				String curTemp = null;
				String curDate = null;

				String todayDay = null;
				String todayDate = null;
				String todayLow = null;
				String todayHigh = null;
				String todayCondition = null;
				String todayConditionCode = null;

				String tomorrowDay = null;
				String tomorrowDate = null;
				String tomorrowLow = null;
				String tomorrowHigh = null;
				String tomorrowCondition = null;
				String tomorrowConditionCode = null;

				String tomorrowDay_next = null;
				String tomorrowDate_next = null;
				String tomorrowLow_next = null;
				String tomorrowHigh_next = null;
				String tomorrowCondition_next = null;
				String tomorrowConditionCode_next = null;

				String tomorrowDay_next2 = null;
				String tomorrowDate_next2 = null;
				String tomorrowLow_next2 = null;
				String tomorrowHigh_next2 = null;
				String tomorrowCondition_next2 = null;
				String tomorrowConditionCode_next2 = null;

				String tomorrowDay_next3 = null;
				String tomorrowDate_next3 = null;
				String tomorrowLow_next3 = null;
				String tomorrowHigh_next3 = null;
				String tomorrowCondition_next3 = null;
				String tomorrowConditionCode_next3 = null;

				int eventType = xpp.getEventType();

				while (eventType != XmlPullParser.END_DOCUMENT)
				{
					switch (eventType)
					{
						case XmlPullParser.START_DOCUMENT:
						break;

						case XmlPullParser.END_DOCUMENT:
						break;

						case XmlPullParser.START_TAG:
							tag = xpp.getName();

							// setString(str) �޼ҵ� �̿��ϸ� html �±� ����
							if (tag.equals("location"))
							{

								curLocation = xpp.getAttributeValue(null, "city");

							}
							if (tag.equals("atmosphere"))
							{
								curHumidity = xpp.getAttributeValue(null, "humidity");
							}

							if (tag.equals("condition"))
							{
								curCondition = xpp.getAttributeValue(null, "text");

								Log.v("dd", "condition=" + curCondition.toString());

								curConditionCode = xpp.getAttributeValue(null, "code");

								Log.v("dd", "code:" + curConditionCode);

								curTemp = xpp.getAttributeValue(null, "temp");
								curDate = xpp.getAttributeValue(null, "date");
							}

							if (tag.equals("forecast"))
							{
								if (count == 0)
								{
									todayDay = xpp.getAttributeValue(null, "day");
									Log.v("dd", "todayDay=" + xpp.getText());

									vo.setTodayDay(todayDay);

									todayDate = xpp.getAttributeValue(null, "date");
									todayLow = xpp.getAttributeValue(null, "low");
									todayHigh = xpp.getAttributeValue(null, "high");
									todayCondition = xpp.getAttributeValue(null, "text");
									todayConditionCode = xpp.getAttributeValue(null, "code");
								}
								else if (count == 1)
								{
									tomorrowDay = xpp.getAttributeValue(null, "day");
									tomorrowDate = xpp.getAttributeValue(null, "date");
									tomorrowLow = xpp.getAttributeValue(null, "low");
									tomorrowHigh = xpp.getAttributeValue(null, "high");
									tomorrowCondition = xpp.getAttributeValue(null, "text");
									tomorrowConditionCode = xpp.getAttributeValue(null, "code");
								}
								else if (count == 2)
								{

									tomorrowDay_next = xpp.getAttributeValue(null, "day");
									tomorrowDate_next = xpp.getAttributeValue(null, "date");
									tomorrowLow_next = xpp.getAttributeValue(null, "low");
									tomorrowHigh_next = xpp.getAttributeValue(null, "high");
									tomorrowCondition_next = xpp.getAttributeValue(null, "text");
									tomorrowConditionCode_next = xpp.getAttributeValue(null, "code");

									Log.v("dd", "tomorrowConditionCode_next::" + tomorrowConditionCode_next);

								}
								else if (count == 3)
								{

									tomorrowDay_next2 = xpp.getAttributeValue(null, "day");
									tomorrowDate_next2 = xpp.getAttributeValue(null, "date");
									tomorrowLow_next2 = xpp.getAttributeValue(null, "low");
									tomorrowHigh_next2 = xpp.getAttributeValue(null, "high");
									tomorrowCondition_next2 = xpp.getAttributeValue(null, "text");
									tomorrowConditionCode_next2 = xpp.getAttributeValue(null, "code");

								}
								else if (count == 4)
								{

									tomorrowDay_next3 = xpp.getAttributeValue(null, "day");
									tomorrowDate_next3 = xpp.getAttributeValue(null, "date");
									tomorrowLow_next3 = xpp.getAttributeValue(null, "low");
									tomorrowHigh_next3 = xpp.getAttributeValue(null, "high");
									tomorrowCondition_next3 = xpp.getAttributeValue(null, "text");
									tomorrowConditionCode_next3 = xpp.getAttributeValue(null, "code");

								}
								count++;
							}
						break;

						case XmlPullParser.END_TAG:
						break;

						case XmlPullParser.TEXT:
						break;

						default:
						break;
					}
					eventType = xpp.next();
				}

				vo.setCurConditionCode(curConditionCode);
				vo.setCurTemp(curTemp);
				vo.setCurDate(curDate);
				vo.setCurLocation(curLocation);
				vo.setCurHumidity(curHumidity);

				vo.setTodayDay(todayDay);
				vo.setTodayDate(todayDate);
				vo.setTodayLow(todayLow);
				vo.setTodayHigh(todayHigh);
				vo.setTodayCondition(todayConditionCode);
				vo.setTodayConditionCode(todayConditionCode);

				vo.setTomorrowDay(tomorrowDay);
				vo.setTomorrowDate(tomorrowDate);
				vo.setTomorrowLow(tomorrowLow);
				vo.setTomorrowHigh(tomorrowHigh);
				vo.setTomorrowCondition(tomorrowCondition);
				vo.setTomorrowConditionCode(tomorrowConditionCode);

				vo.setTomorrowDay_next(tomorrowDay_next);
				vo.setTomorrowDate_next(tomorrowDate_next);
				vo.setTomorrowLow_next(tomorrowLow_next);
				vo.setTomorrowHigh_next(tomorrowHigh_next);
				vo.setTomorrowCondition_next(tomorrowCondition_next);
				vo.setTomorrowConditionCode_next(tomorrowConditionCode_next);

				vo.setTomorrowDay_next2(tomorrowDay_next2);
				vo.setTomorrowDate_next2(tomorrowDate_next2);
				vo.setTomorrowLow_next2(tomorrowLow_next2);
				vo.setTomorrowHigh_next2(tomorrowHigh_next2);
				vo.setTomorrowCondition_next2(tomorrowCondition_next2);
				vo.setTomorrowConditionCode_next2(tomorrowConditionCode_next2);

				vo.setTomorrowDay_next3(tomorrowDay_next3);
				vo.setTomorrowDate_next3(tomorrowDate_next3);
				vo.setTomorrowLow_next3(tomorrowLow_next3);
				vo.setTomorrowHigh_next3(tomorrowHigh_next3);
				vo.setTomorrowCondition_next3(tomorrowCondition_next3);
				vo.setTomorrowConditionCode_next3(tomorrowConditionCode_next3);

				// mApplicationSample.hashmapWeather.put("curCondition",
				// curCondition);
				// mApplicationSample.hashmapWeather.put("curConditionCode",
				// curConditionCode);
				// mApplicationSample.hashmapWeather.put("curTemp", curTemp);
				// mApplicationSample.hashmapWeather.put("curDate", curDate);
				//
				// mApplicationSample.hashmapWeather.put("todayDay", todayDay);
				// mApplicationSample.hashmapWeather.put("todayDate",
				// todayDate);
				// mApplicationSample.hashmapWeather.put("todayLow", todayLow);
				// mApplicationSample.hashmapWeather.put("todayHigh",
				// todayHigh);
				// mApplicationSample.hashmapWeather.put("todayCondition",
				// todayCondition);
				// mApplicationSample.hashmapWeather.put("todayConditionCode",
				// todayConditionCode);
				//
				// mApplicationSample.hashmapWeather.put("tomorrowDay",
				// tomorrowDay);
				// mApplicationSample.hashmapWeather.put("tomorrowDate",
				// tomorrowDate);
				// mApplicationSample.hashmapWeather.put("tomorrowLow",
				// tomorrowLow);
				// mApplicationSample.hashmapWeather.put("tomorrowHigh",
				// tomorrowHigh);
				// mApplicationSample.hashmapWeather.put("tomorrowCondition",
				// tomorrowCondition);
				// mApplicationSample.hashmapWeather.put("tomorrowConditionCode",
				// tomorrowConditionCode);

				curCondition = null;
				curConditionCode = null;
				curTemp = null;
				curDate = null;

				todayDay = null;
				todayDate = null;
				todayLow = null;
				todayHigh = null;
				todayCondition = null;
				todayConditionCode = null;

				tomorrowDay = null;
				tomorrowDate = null;
				tomorrowLow = null;
				tomorrowHigh = null;
				tomorrowCondition = null;
				tomorrowConditionCode = null;

				if (in != null)
				{
					in.close();
					in = null;
				}
			}
		}
		catch (MalformedURLException e)
		{

		}
		catch (ProtocolException e)
		{

		}
		catch (IOException e)
		{

		}
		catch (XmlPullParserException e)
		{}
		catch (Exception e)
		{}
		finally
		{
			if (urlConnection != null)
			{
				urlConnection.disconnect();
				urlConnection = null;
			}
		}
		return null;

	}

	@Override
	protected void onPostExecute(Void result)
	{
		// �߰��� �ؾ��� �κ� ���ʿ�

		super.onPostExecute(result);

		mHandler.sendEmptyMessage(0);

	}

}
