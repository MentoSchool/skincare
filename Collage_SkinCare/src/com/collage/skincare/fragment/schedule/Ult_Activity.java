package com.collage.skincare.fragment.schedule;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.collage.skincare.R;
import com.collage.skincare.db.FragScheduleBoard_Alram_Db;

public class Ult_Activity extends Activity implements OnTimeChangedListener, OnDateChangedListener
{

	private static final int INSERT_ID = Menu.FIRST;

	private static final int DELETE_ID = Menu.FIRST + 1;

	private static final int ACTIVITY_CREATE = 0;

	private static final int ACTIVITY_EDIT = 1;

	FragScheduleBoard_Alram_Db dbAdapter;

	SimpleCursorAdapter adapter;

	// 알람 관련 맴버 변수

	// 알람 메니저
	private AlarmManager mManager;

	// 설정 일시

	public static GregorianCalendar mCalendar;

	public static Calendar calendar;

	// 일자 설정 클래스

	private DatePicker mDate;

	// 시작 설정 클래스

	private TimePicker mTime;

	// 통지 관련 맴버 변수

	CursorAdapter cursorAdapter;

	private NotificationManager mNotification;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// 셋버튼,리셋버튼의 리스너를 등록
		setContentView(R.layout.activity_main);
		// registerForContextMenu(getListView());

		// 통지 매니저를 취득
		mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// 알람 매니저를 취득
		mManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		// 현제 시각을 취득
		mCalendar = new GregorianCalendar();

		Log.v("AlarmActivity", mCalendar.getTime().toString());

		Button b = (Button) findViewById(R.id.set);

		b.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

				setAlarm();

			}

		});

		Button a = (Button) findViewById(R.id.rest);
		a.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				resetAlarm();
			}

		});
		Button c = (Button) findViewById(R.id.cancel);
		c.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

				nm.cancel(1234);
				finish();

			}
		});

		mDate = (DatePicker) findViewById(R.id.date_picker);
		mDate.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), this);
		mTime = (TimePicker) findViewById(R.id.time_picker);
		mTime.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
		mTime.setOnTimeChangedListener(this);

	}

	public class MyAdapter extends ArrayAdapter<String>
	{

		Activity context;

		public MyAdapter(Activity context, int textViewResourceId, String[] object)
		{
			// TODO Auto-generated constructor stub
			super(context, textViewResourceId, object);

			this.context = context;

		}

	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();

		dbAdapter = new FragScheduleBoard_Alram_Db(this);

		dbAdapter.open();

		fillData();

	}

	private void fillData()
	{

		ListView listView = (ListView) findViewById(R.id.list);
		// TODO Auto-generated method stub
		// 모든 데이터의 커서를 얻어옴
		Cursor c = dbAdapter.fetchAllNote();
		// c.moveToFirst();
		// 리스트뷰에 데이터베이스의 저장된 데이터를 연결
		String[] from = new String[]
		{
				BaseColumns._ID, FragScheduleBoard_Alram_Db.Cur_Time, FragScheduleBoard_Alram_Db.Selection_Time, FragScheduleBoard_Alram_Db.Alram_Type
		};
		// String[] from = new String[] { "_ID", "TITLE" };
		// 한행을 보여줄 XML 파일의 텍스트 뷰 id
		int[] to = new int[]
		{
				R.id.textView01, R.id.textView02, R.id.textView03, R.id.textView04
		};

		// cursorAdapter = new CursorAdapter(this, c);

		adapter = new SimpleCursorAdapter(listView.getContext(), R.layout.frag_schedule_board_list_item, c, from, to);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(clickListener);
		// Activity의 라이프 사이클에 따라 알아서 커서를 관리해 줌 , 커서를 쓰는
		// 액티비티가 종료(destroy)할때 cursor를 따로 close를 재주지 않아도
		// 알아서 close해준다.
		startManagingCursor(c);

	}

	OnItemClickListener clickListener = new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
		{
			// TODO Auto-generated method stub

			final TextView view = (TextView) arg1.findViewById(R.id.textView01);
			final TextView view2 = (TextView) arg1.findViewById(R.id.textView02);
			final TextView view3 = (TextView) arg1.findViewById(R.id.textView03);
			final TextView view4 = (TextView) arg1.findViewById(R.id.textView04);

			// 삭제 다이얼로그에 보여줄 메시지를 만든다.

			String message = "해당 알람를 삭제하시겠습니까?<br />" +

			"position : " + position + "<br />" +

			"data : " + view2.getText().toString() + "<br />";

			DialogInterface.OnClickListener deleteListener = new DialogInterface.OnClickListener()

			{

				@Override
				public void onClick(DialogInterface arg0, int arg1)

				{

					// 선택된 아이템을 리스트에서 삭제한다.

					long id = Integer.parseInt(view.getText().toString());

					dbAdapter.deleteNote(id);

					// Adapter에 데이터가 바뀐걸 알리고 리스트뷰에 다시 그린다.

					fillData();

				}

			};
			DialogInterface.OnClickListener Cancel_Listener = new DialogInterface.OnClickListener()

			{

				@Override
				public void onClick(DialogInterface arg0, int arg1)

				{

					// 선택된 아이템을 리스트에서 삭제한다.

					// Adapter에 데이터가 바뀐걸 알리고 리스트뷰에 다시 그린다.

					fillData();

				}

			};

			// 삭제를 물어보는 다이얼로그를 생성한다.

			new AlertDialog.Builder(Ult_Activity.this).setTitle("http://croute.me - 예제")

			.setMessage(Html.fromHtml(message))

			.setPositiveButton("삭제", deleteListener)

			.show();

		}

	};

	// /////////////////////////////////////////알람시작
	protected void setAlarm()
	{
		// TODO Auto-generated method stub

		if (System.currentTimeMillis() >= mCalendar.getTimeInMillis())
		{
			Log.v("dd", "현재시간" + mCalendar.getTimeInMillis());
			long curtime = System.currentTimeMillis();
			long time = mCalendar.getTimeInMillis();
			Calendar c = Calendar.getInstance();
			Calendar g = Calendar.getInstance();
			c.setTimeInMillis(time);
			g.setTimeInMillis(curtime);
			Toast.makeText(this, "잘못된 알람 설정입니다.", Toast.LENGTH_SHORT).show();
			Log.v("dd", "현재알람 설정시간" + mCalendar.getTime().toString());
			// Log.v("dd", "현재시간"+calendar.getTime().toString());
			Log.v("현재 시간", String.valueOf(System.currentTimeMillis()));
			Log.v("dd", "설정시간" + String.valueOf(c.get(Calendar.HOUR) + "시" + c.get(Calendar.MINUTE) + "분" + c.get(Calendar.SECOND) + "초"));// 설정 시간이
																																			// 나온다.
			Log.v("dd", "ㅅㅣ작시간" + String.valueOf(g.get(Calendar.HOUR) + "시" + g.get(Calendar.MINUTE) + "분" + g.get(Calendar.SECOND) + "초"));// 설정 시간이
																																			// 나온다.
		}
		else
		{

			mManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pendingIntent(1234));

			Toast.makeText(this, "현재 알람 설정", Toast.LENGTH_SHORT).show();

			Log.v("dd", "현재시간" + mCalendar.getTimeInMillis());
			long curtime = System.currentTimeMillis();
			long time = mCalendar.getTimeInMillis();
			Calendar c = Calendar.getInstance();
			Calendar g = Calendar.getInstance();
			c.setTimeInMillis(time);
			g.setTimeInMillis(curtime);
			Intent intent = new Intent(getApplicationContext(), AlramReceiver.class);
			PendingIntent sender = PendingIntent.getBroadcast(Ult_Activity.this, 0, intent, 0);

			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			am.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), 0, sender);

			Log.v("dd", "현재알람 설정시간" + mCalendar.getTime().toString());
			// Log.v("dd", "현재시간"+calendar.getTime().toString());
			Log.v("현재 시간", String.valueOf(System.currentTimeMillis()));
			Log.v("dd", "설정시간" + String.valueOf(c.get(Calendar.HOUR) + "시" + c.get(Calendar.MINUTE) + "분" + c.get(Calendar.SECOND) + "초"));// 설정 시간이
																																			// 나온다.
			Log.v("dd", "ㅅㅣ작시간" + String.valueOf(g.get(Calendar.HOUR) + "시" + g.get(Calendar.MINUTE) + "분" + g.get(Calendar.SECOND) + "초"));// 설정 시간이
																																			// 나온다.

			String selection_time = String.valueOf(c.get(Calendar.HOUR) + "시" + c.get(Calendar.MINUTE) + "분" + c.get(Calendar.SECOND) + "초");

			String cur_time = String.valueOf(g.get(Calendar.HOUR) + "시" + g.get(Calendar.MINUTE) + "분" + g.get(Calendar.SECOND) + "초");

			String alram_type = "2";

			dbAdapter.insertNote(cur_time, selection_time, alram_type);

			fillData();

		}

	}

	// 알람의 설정 시각에 발생하는 인텐트 작성
	private PendingIntent pendingIntent(int j)
	{
		// TODO Auto-generated method stub
		Intent i = new Intent(getApplicationContext(), TestPageWrite.class);

		PendingIntent pi = PendingIntent.getActivity(this, j, i, 0);

		return pi;
	}

	protected void resetAlarm()
	{
		// TODO Auto-generated method stub

		mManager.cancel(pendingIntent(1234));
		Toast.makeText(this, "리셋", Toast.LENGTH_SHORT).show();
	}

	// 일자 설정 클래스의 상태 변화 리스터

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
	{
		// TODO Auto-generated method stub
		mCalendar.set(year, monthOfYear, dayOfMonth, mTime.getCurrentHour(), mTime.getCurrentMinute());

		Log.v("일자설정 상태변화", mCalendar.getTime().toString());
		Log.v("현재 시간", String.valueOf(System.currentTimeMillis()));

	}

	// 시각 설정 클래스의 상태변화 리스너
	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
	{
		// TODO Auto-generated method stub

		mCalendar.set(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(), hourOfDay, minute);

		Log.v("시각 설정 클래스의 상태 변화 리스너", mCalendar.getTime().toString());

	}

}
