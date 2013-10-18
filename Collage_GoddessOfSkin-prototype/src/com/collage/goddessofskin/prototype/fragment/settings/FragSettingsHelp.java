package com.collage.goddessofskin.prototype.fragment.settings;



import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import com.collage.goddessofskin.prototype.R;


public class FragSettingsHelp extends ExpandableListActivity  {

    ExpandableListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up our adapter
        mAdapter = new MyExpandableListAdapter();
        setListAdapter(mAdapter);
        registerForContextMenu(getExpandableListView());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Sample menu");
       
    }
    //도움말 액티비티
    
    
    
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        // Sample data set.  children[i] contains the children (String[]) for groups[i].
        private String[] groups = { "피부타입을 바꾸고 싶습니다.",
        								    "내지역의 재미있는 날씨를 보고 싶어요.",
        								    "알람소리를 설정하고 싶어요.",
        								    "팝업설정을 하고싶습니다.",
        								    "현재 내 피부 레벨에 대해 궁금합니다.",
        								    "목표피부사진을 바꾸고 싶어요.",
        								    "알람 설정은 어떻게 하나요?",
        								    "일주일동안의 내 피부상태 변화를 보고싶습니다.",
        								    "프로필사진을 바꾸고 싶습니다.",
        								    "내가 관리한 피부스케줄을 추가하고 싶습니다."
        								    };
        private String[][] children = {
                { " 앱 우측 상단 아이콘 클릭 > 프로필 사진 클릭 > 사진아래 피부타입 재설정  " },
                { " 앱 우측 상단 아이콘 클릭 \"재미있는 날씨 정보\"> \"내 위치 찾기\" 클릭"},
                { " 앱 우측 상단 아이콘 클릭 > \"소리설정\" 클릭  " },
                { " 앱 우측 상단 아이콘 클릭 > \"팝업설정\" 클릭  " },
                {  "앱 우측 상단 아이콘 클릭 > 프로필 사진 클릭 > 아바타 아래 5개의 네모" },
                { " 앱 우측 상단 아이콘 클릭 > 프로필 사진 클릭 > 사진위 배경 3초클릭   " },
                { " 앱 우측 상단 아이콘 클릭 \"스케줄 한눈에 보기\" > 그래프 아래쪽 \"잠\" 버튼 클릭   " },
                {"  앱 우측 상단 아이콘 클릭 > \"스케줄 한눈에 보기\"> 원형그래프 클릭"},
                {"  앱 우측 상단 아이콘 클릭 > 프로필 사진 클릭 > 프로필사진 창 클릭"},
                {"  앱 우측 상단 아이콘 클릭 > 프로필 사진 클릭 > 상단에 +기호 클릭 > 원하는 글 쓰기"}
        };
        
        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }

        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, 64);

            TextView textView = new TextView(FragSettingsHelp.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            textView.setPadding(36, 0, 0, 0);
            return textView;
        }
        
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getChild(groupPosition, childPosition).toString());
            return textView;
        }

        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        public int getGroupCount() {
            return groups.length;
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getGroup(groupPosition).toString());
            return textView;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }

    }
}
