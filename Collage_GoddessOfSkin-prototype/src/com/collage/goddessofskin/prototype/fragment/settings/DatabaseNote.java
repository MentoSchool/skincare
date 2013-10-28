package com.collage.goddessofskin.prototype.fragment.settings;

import com.collage.goddessofskin.prototype.R;

import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DatabaseNote extends ListActivity{
    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;

    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    
    NotesDbAdapter dbAdapter;
    SimpleCursorAdapter adapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_settings_profile);
        
        registerForContextMenu(getListView());
        
    }

    @Override
    protected void onResume() {
        super.onResume();

        dbAdapter = new NotesDbAdapter(this);

        dbAdapter.open();
        
        fillData();
    }
        
    private void fillData() {
        
        // 모든 데이터의 커서를 얻어옴
        Cursor c = dbAdapter.fetchAllNotes();

        // 리스트뷰에 데이터베이스의 저장된 데이터를 연결
        String[] from = new String[] { BaseColumns._ID, NotesDbAdapter.TITLE};
        // 한 행을 보여줄 XML 파일의 텍스트 뷰 id
        int to[] = {android.R.id.text1, android.R.id.text2};

        adapter = new SimpleCursorAdapter(this, 
              android.R.layout.simple_list_item_2, c, from, to);

        setListAdapter(adapter);
        // Activity의 라이프사이클에 따라 알아서 커서를 관리해 줌, 커서를 쓰는 
        // 액티비티가 종료(destroy)할 때 cursor를 따로 close를 해주지 않아도 
        //알아서 close해준다.
        startManagingCursor(c);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, INSERT_ID, Menu.NONE, "추가").setIcon(
                android.R.drawable.ic_menu_add).setAlphabeticShortcut('a');
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case INSERT_ID:
             //NoteEdit로 아이디 없이 넘어가면 추가
            Intent intent = new Intent(this, NoteEdit.class);
            startActivityForResult(intent , ACTIVITY_CREATE);
            return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
   public void onCreateContextMenu(ContextMenu menu, View v,
      ContextMenuInfo menuInfo) {
      super.onCreateContextMenu(menu, v, menuInfo);
     //삭제 메뉴 추가
     menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "삭제");
 }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterContextMenuInfo info = 
                    (AdapterContextMenuInfo) item.getMenuInfo();
                dbAdapter.deleteNote(info.id);            
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }    
     
    //NoteEdit로 아이디 값만 넘겨줌 아이디 있으면 수정
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
         long rowId = -1;
        super.onListItemClick(l, v, position, id);
        
        Intent intent = new Intent(this, NoteEdit.class);
        TextView tv = (TextView) v.findViewById(android.R.id.text1);
        rowId = Long.parseLong(tv.getText().toString());
        if(rowId !=-1){
            intent.putExtra(NotesDbAdapter._ID, rowId);
            startActivityForResult(intent, ACTIVITY_EDIT);
        }
    }
    
    //조회해서 화면에 ㄱㄱ함
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
}