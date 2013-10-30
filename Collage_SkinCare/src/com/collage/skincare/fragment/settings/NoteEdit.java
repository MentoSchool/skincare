package com.collage.skincare.fragment.settings;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.collage.skincare.R;
import com.collage.skincare.api.Yh_AsyncWeather;

public class NoteEdit extends Activity
{
	private EditText mTitleText;
	private EditText mBodyText;
	private Long mRowId;
	private NotesDbAdapter mDbHelper;

	Yh_AsyncWeather weather;

	//Bundle savedInstanceState
	//파일에 내부적으로 정보(일반적으로 번들 형태)에 저장하도록 하는 인자 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frag_note_edit);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		/*
		 * 새로 추가와 편집 구분 setTitle("편집");
		 */

		mDbHelper = new NotesDbAdapter(this);
		mDbHelper.open();

		mTitleText = (EditText) findViewById(R.id.title);
		mBodyText = (EditText) findViewById(R.id.body);
		//		Button confirmButton = (Button) findViewById(R.id.confirm);
		mRowId = (savedInstanceState == null) ? null : (Long) savedInstanceState.getSerializable(NotesDbAdapter._ID);
		if (mRowId == null)
		{
			Bundle extras = getIntent().getExtras();
			//			mRowId = extras != null ? extras.getLong(NotesDbAdapter._ID) : null;

			if (extras != null)
			{
				setTitle(R.string.action_title_note_modify);
				mRowId = extras.getLong(NotesDbAdapter._ID);
			}
			else
			{
				setTitle(R.string.action_title_note_new);
			}
		}

		populateFields();

		/*
		 * 액션바로 변경 confirmButton.setOnClickListener(new View.OnClickListener() { public void onClick(View view) { setResult(RESULT_OK); finish(); } });
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.act_note, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
			{
				finish();
			}
				return true;

			case R.id.action_note_accept:
			{
				setResult(RESULT_OK);
				finish();
			}
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	//디비에서 데이터 한건을 읽은 후  화면에 출력한다.
	private void populateFields()
	{
		if (mRowId != null)
		{
			Cursor note = mDbHelper.check(mRowId);
			startManagingCursor(note);
			note.moveToFirst();

			int idx = note.getColumnIndex(NotesDbAdapter.TITLE);
			mTitleText.setText(note.getString(idx));
			idx = note.getColumnIndex(NotesDbAdapter.BODY);
			mBodyText.setText(note.getString(idx));
		}
	}

	//액티비티가 화면에 가려지면 자동 호출된다. 
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		//디비에 저장하는 사용자 정의 메소드
		saveState();
		//파일에 저장한다. 
		outState.putSerializable(NotesDbAdapter._ID, mRowId);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		saveState();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		populateFields();
	}

	//사용자 정의 메소드로 디비에 저장한다. 
	private void saveState()
	{
		String title = mTitleText.getText().toString();
		String body = mBodyText.getText().toString();
		//아이디가 없으면 추가
		if (mRowId == null)
		{
			long id = mDbHelper.insertNote(title, body, weather.vo.getCurConditionCode());
			Log.e("tag", title);
			Log.e("tag", String.valueOf(id));
			if (id > 0)
			{
				mRowId = id;
			}
		}
		else
		{ //있으면 수정
			mDbHelper.updateNote(title, body, mRowId);
		}
	}
}
