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
    //���� ��Ƽ��Ƽ
    
    
    
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        // Sample data set.  children[i] contains the children (String[]) for groups[i].
        private String[] groups = { "�Ǻ�Ÿ���� �ٲٰ� �ͽ��ϴ�.",
        								    "�������� ����ִ� ������ ���� �;��.",
        								    "�˶��Ҹ��� �����ϰ� �;��.",
        								    "�˾������� �ϰ�ͽ��ϴ�.",
        								    "���� �� �Ǻ� ������ ���� �ñ��մϴ�.",
        								    "��ǥ�Ǻλ����� �ٲٰ� �;��.",
        								    "�˶� ������ ��� �ϳ���?"
        								    };
        private String[][] children = {
                { " �� ���� ��� ������ Ŭ�� > ������ ���� Ŭ�� > �����Ʒ� �Ǻ�Ÿ�� �缳��  " },
                { " �� ���� ��� ������ Ŭ�� \"����ִ� ���� ����\"> \"�� ��ġ ã��\" Ŭ��"},
                { " �� ���� ��� ������ Ŭ�� > \"�Ҹ�����\" Ŭ��  " },
                { " �� ���� ��� ������ Ŭ�� > \"�˾�����\" Ŭ��  " },
                {  "�� ���� ��� ������ Ŭ�� > ������ ���� Ŭ�� > �ƹ�Ÿ �Ʒ� 5���� �׸�" },
                { " �� ���� ��� ������ Ŭ�� > ������ ���� Ŭ�� > ������ ��� 3��Ŭ��   " },
                { " �� ���� ��� ������ Ŭ�� \"������ �Ѵ��� ����\" > �׷��� �Ʒ��� \"��\" ��ư Ŭ��   " }
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
