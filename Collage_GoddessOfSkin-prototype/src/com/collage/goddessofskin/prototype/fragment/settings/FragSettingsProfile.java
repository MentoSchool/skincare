package com.collage.goddessofskin.prototype.fragment.settings;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListFragment;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.collage.goddessofskin.prototype.R;
import com.collage.goddessofskin.prototype.utils.io.FileUtil;

public class FragSettingsProfile extends ListFragment implements
		OnClickListener {
	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;

	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;

	private static final int NOTE_CREATE = 3;
	private static final int NOTE_EDIT = NOTE_CREATE + 1;

	private Uri mImageCaptureUri;
	private ImageView mPhotoImageView;
	private Button mbutton;
	NotesDbAdapter dbAdapter;
	SimpleCursorAdapter adapter;
	private ImageView profile_image12;
	private Button popup_Button1;
	private Button Alarmsound_Button;
	DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which)

		{
			doTakePhotoAction();
		}
	};

	DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			doTakeAlbumAction();
		}
	};

	DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_settings_profile,
				container, false);

		mPhotoImageView = (ImageView) rootView
				.findViewById(R.id.profile_image12);
		mPhotoImageView.setOnClickListener(this);

		popup_Button1 = (Button) rootView.findViewById(R.id.popup_Button1);
		popup_Button1.setOnClickListener(this);

		Alarmsound_Button = (Button) rootView
				.findViewById(R.id.Alarmsound_Button);
		Alarmsound_Button.setOnClickListener(this);
		setHasOptionsMenu(true);

		View view = rootView.findViewById(android.R.id.list);
		this.registerForContextMenu(view);
		return rootView;
	}

	/**
	 * 카메라에서 이미지 가져오기
	 */
	private void doTakePhotoAction() {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// 임시로 사용할 파일의 경로를 생성
		String url = "tmp_" + String.valueOf(System.currentTimeMillis())
				+ ".jpg";
		mImageCaptureUri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), url));
		Log.d("test", mImageCaptureUri.toString());
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				mImageCaptureUri);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PICK_FROM_CAMERA);
	}

	/**
	 * 앨범에서 이미지 가져오기
	 */
	private void doTakeAlbumAction() {

		// 앨범 호출
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(intent, PICK_FROM_ALBUM);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK)
			return;

		switch (requestCode) {
		case CROP_FROM_CAMERA: {
			// 크롭이 된 이후의 이미지를 넘겨 받습니다. 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
			// 임시 파일을 삭제합니다.
			final Bundle extras = data.getExtras();

			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				mPhotoImageView.setImageBitmap(photo);

				FileUtil.save(photo, getActivity().getExternalCacheDir()
						.getAbsolutePath(), "frofile.jpg");

			}

			// 임시 파일 삭제
			File f = new File(mImageCaptureUri.getPath());
			if (f.exists()) {
				f.delete();
			}

		}
			break;

		case PICK_FROM_ALBUM: {
			// 이후의 처리가 카메라와 같으므로 일단 break없이 진행합니다.
			// 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.

			mImageCaptureUri = data.getData();
		}
			break;

		case PICK_FROM_CAMERA: {
			// 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
			// 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.

			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(mImageCaptureUri, "image/*");

			intent.putExtra("outputX", 100);
			intent.putExtra("outputY", 100);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, CROP_FROM_CAMERA);

		}
			break;

		case NOTE_CREATE:
		case NOTE_EDIT: {
			fillData();
		}
			break;
		}
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.profile_image12:
			new AlertDialog.Builder(getActivity())
					.setIcon(R.drawable.ic_launcher).setTitle("업로드할 이미지 선택")
					.setPositiveButton("사진촬영", cameraListener)
					.setNeutralButton("앨범선택", albumListener)
					.setNegativeButton("취소", cancelListener).show();
			break;

		case R.id.Alarmsound_Button:

			Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
			intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "알림음 설정");
			Uri uri = ContentUris.withAppendedId(
					MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 1l);
			intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
			intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, uri);
			startActivityForResult(intent, 123);

			break;

		case R.id.popup_Button1:

			Builder d = new AlertDialog.Builder(getActivity());
			d.setTitle("팝업설정");
			d.setSingleChoiceItems(R.array.select_popup, 0,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

						}

					});
			d.setPositiveButton("확인", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// 이구간은 확인버튼을 선택했을때 설정되어야하는 구간이니까 멘토님한테 물어본다.
				}
			});
			d.setNegativeButton("취소", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			d.show();

		}

	}

	/**
	 * 메모장
	 */
	public void onResume() {
		super.onResume();

		dbAdapter = new NotesDbAdapter(getActivity());

		dbAdapter.open();

		fillData();
	}

	private void fillData() {

		// 모든 데이터의 커서를 얻어옴
		Cursor c = dbAdapter.fetchAllNotes();

		// 리스트뷰에 데이터베이스의 저장된 데이터를 연결
		String[] from = new String[] { BaseColumns._ID, NotesDbAdapter.TITLE,
				NotesDbAdapter.CURWHEATERCODE };
		// 한 행을 보여줄 XML 파일의 텍스트 뷰 id
		int to[] = new int[] { R.id.textView_01, R.id.textView_02,
				R.id.textView_03 };

		adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.frag_schedule_setting_list_item, c, from, to);

		setListAdapter(adapter);
		// Activity의 라이프사이클에 따라 알아서 커서를 관리해 줌, 커서를 쓰는
		// 액티비티가 종료(destroy)할 때 cursor를 따로 close를 해주지 않아도
		// 알아서 close해준다.
		getActivity().startManagingCursor(c);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.frag_settings, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_new_note: {
			Intent intent = new Intent(getActivity(), NoteEdit.class);
			startActivityForResult(intent, NOTE_CREATE);
		}
			return true;

		default:
			return (super.onOptionsItemSelected(item));
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		// 삭제 메뉴 추가
		menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "삭제");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			dbAdapter.deleteNote(info.id);
			fillData();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	// NoteEdit로 아이디 값만 넘겨줌 아이디 있으면 수정

	public void onListItemClick(ListView l, View v, int position, long id) {
		long rowId = -1;
		super.onListItemClick(l, v, position, id);

		Intent intent = new Intent(getActivity(), NoteEdit.class);
		TextView tv = (TextView) v.findViewById(android.R.id.text1);
		rowId = Long.parseLong(tv.getText().toString());
		if (rowId != -1) {
			intent.putExtra(NotesDbAdapter._ID, rowId);
			startActivityForResult(intent, NOTE_EDIT);
		}
	}

}
