package com.collage.goddessofskin.prototype.fragment.settings;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.collage.goddessofskin.prototype.R;

public class NoteEdit extends Activity {
    private EditText mTitleText;
    private EditText mBodyText;
    private Long mRowId;
    private NotesDbAdapter mDbHelper;
    //Bundle savedInstanceState
    //파일에 내부적으로 정보(일반적으로 번들 형태)에 저장하도록 하는 인자 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_note_edit);
        setTitle("편집");

        mDbHelper = new NotesDbAdapter(this);
        mDbHelper.open();
        
        mTitleText = (EditText) findViewById(R.id.title);
        mBodyText = (EditText) findViewById(R.id.body);
        Button confirmButton = (Button) findViewById(R.id.confirm);
        mRowId = (savedInstanceState == null) ? null :
            (Long) savedInstanceState.getSerializable(NotesDbAdapter._ID);
        if (mRowId == null) {
            Bundle extras = getIntent().getExtras();
            mRowId = extras != null ? extras.getLong(NotesDbAdapter._ID)
                                    : null;
        }
        
        populateFields();
        
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }
    //디비에서 데이터 한건을 읽은 후  화면에 출력한다.
    private void populateFields() {
        if (mRowId != null) {
            Cursor note = mDbHelper.check(mRowId);
            startManagingCursor(note);
            note.moveToFirst();
            
            int idx=note.getColumnIndex(NotesDbAdapter.TITLE);
            mTitleText.setText(note.getString(idx));
            idx=note.getColumnIndex(NotesDbAdapter.BODY);
            mBodyText.setText(note.getString(idx));
        }
    }
    //액티비티가 화면에 가려지면 자동 호출된다. 
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //디비에 저장하는 사용자 정의 메소드
        saveState();
        //파일에 저장한다. 
        outState.putSerializable(NotesDbAdapter._ID, mRowId);
    }
    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }
    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }
    //사용자 정의 메소드로 디비에 저장한다. 
    private void saveState() {
        String title = mTitleText.getText().toString();
        String body = mBodyText.getText().toString();
        //아이디가 없으면 추가
        if (mRowId == null) {
            long id = mDbHelper.insertNote(title, body, "8");
            Log.e("tag",title);
            Log.e("tag",String.valueOf(id));
            if (id > 0) {
                mRowId = id;
            }
        } else { //있으면 수정
            mDbHelper.updateNote(title, body, mRowId);
        }
    }
}
