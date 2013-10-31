package com.collage.skincare.fragment.schedule;

import android.R.style;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.collage.skincare.R;
import com.collage.skincare.db.FragScheduleBoard_Alram_Db;
import com.collage.skincare.utils.custom.Click_Vo;
import com.collage.skincare.utils.custom.MyView;

public class FragScheduleBoard extends Fragment {

	Alram_CustomAdapter adapter;

	FragScheduleBoard_Alram_Db dbAdapter;
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		dbAdapter = new FragScheduleBoard_Alram_Db(getActivity());

		dbAdapter.open();

		fillData();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);

		View rootView = inflater.inflate(R.layout.frag_schedule_board,
				container, false);

		init(rootView);

		return rootView;
	}

	

	private void init(View rootView) {
		// TODO Auto-generated method stub

//		ImageButton btn_graph = (ImageButton) rootView
//				.findViewById(R.id.btn_graph);
//		btn_graph.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// FragScheduleGraph로 화면전환
//				Fragment fragment = new FragScheduleGraph();
//				FragmentManager fm = getFragmentManager();
//				fm.beginTransaction()
//						.replace(R.id.act_main_content_frame, fragment)
//						.commit();
//			}
//		});

		
		
		Button schedule_setting_all = (Button) rootView
				.findViewById(R.id.schedule_setting_all);
		schedule_setting_all.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				fillData();
				
				
				
			}
		});

		Button schedule_setting_sleep = (Button) rootView
				.findViewById(R.id.schedule_setting_sleep);

		schedule_setting_sleep.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				String id = "0";
                
				Alram_Type_Data(id);				
			}

			
		});

		Button schedule_setting_water = (Button) rootView
				.findViewById(R.id.schedule_setting_water);
		schedule_setting_water.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				String id = "1";

				Alram_Type_Data(id);

			}
		});
		Button schedule_setting_ult = (Button) rootView
				.findViewById(R.id.schedule_setting_ult);
		schedule_setting_ult.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				String id = "2";

				Alram_Type_Data(id);

			}
		});

		ListView lv = (ListView) rootView.findViewById(R.id.alarms_list);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// Alaram List항목 눌렀을 때,

				Intent intent = new Intent(getActivity(), Sleep_Activity.class);

				startActivity(intent);
			}
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.frag_schedule, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_new_sleep: {
			Intent intent = new Intent(getActivity(), Sleep_Activity.class);

			startActivity(intent);
		}
			return true;

		case R.id.action_new_water: {
			Intent intent = new Intent(getActivity(), Water_Activity.class);

			startActivity(intent);
		}
			return true;

		case R.id.action_new_uv: {
			Intent intent = new Intent(getActivity(), Ult_Activity.class);

			startActivity(intent);
		}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	private void Alram_Type_Data(String id) {
		// TODO Auto-generated method stub
		ListView listView = (ListView) getActivity().findViewById(
				R.id.alarms_list);
		Cursor c = dbAdapter.check_alram_Type(id);

		adapter = new Alram_CustomAdapter(listView.getContext(), c);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(clickListener);
		// Activity의 라이프 사이클에 따라 알아서 커서를 관리해 줌 , 커서를 쓰는
		// 액티비티가 종료(destroy)할때 cursor를 따로 close를 재주지 않아도
		// 알아서 close해준다.
		getActivity().startManagingCursor(c);

	}

	private void fillData() {

		ListView listView = (ListView) getActivity().findViewById(
				R.id.alarms_list);
		// TODO Auto-generated method stub
		// 모든 데이터의 커서를 얻어옴
		Cursor c = dbAdapter.fetchAllNote();
		// c.moveToFirst();
		// 리스트뷰에 데이터베이스의 저장된 데이터를 연결
		// String[] from = new String[]
		// {
		// BaseColumns._ID, FragScheduleBoard_Alram_Db.Cur_Time,
		// FragScheduleBoard_Alram_Db.Selection_Time,
		// FragScheduleBoard_Alram_Db.Alram_Type
		// };
		// String[] from = new String[] { "_ID", "TITLE" };
		// 한행을 보여줄 XML 파일의 텍스트 뷰 id
		// int[] to = new int[]
		// {
		// R.id.textView01, R.id.textView02, R.id.textView03, R.id.textView04
		// };

		// cursorAdapter = new CursorAdapter(this, c);

		// adapter = new SimpleCursorAdapter(listView.getContext(),
		// R.layout.frag_schedule_board_list_item, c, from, to);

		adapter = new Alram_CustomAdapter(listView.getContext(), c);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(clickListener);
		// Activity의 라이프 사이클에 따라 알아서 커서를 관리해 줌 , 커서를 쓰는
		// 액티비티가 종료(destroy)할때 cursor를 따로 close를 재주지 않아도
		// 알아서 close해준다.
		getActivity().startManagingCursor(c);

	}

	OnItemClickListener clickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub

			final TextView view = (TextView) arg1.findViewById(R.id.textView01);
			final TextView view2 = (TextView) arg1
					.findViewById(R.id.textView02);
			final TextView view3 = (TextView) arg1
					.findViewById(R.id.textView03);
			final TextView view4 = (TextView) arg1
					.findViewById(R.id.textView04);

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

			new AlertDialog.Builder(getActivity()).setTitle("--수면---")

			.setMessage(Html.fromHtml(message))

			.setPositiveButton("삭제", deleteListener)

			.show();

		}

	};

}
