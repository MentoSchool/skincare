package com.collage.skincare.fragment.settings;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListFragment;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import android.widget.TextView;

import com.collage.skincare.R;
import com.collage.skincare.defined.Const;
import com.collage.skincare.defined.Const.SkinType;
import com.collage.skincare.manager.SharedPreferenceManager;
import com.collage.skincare.utils.graphics.BitmapUtil.BitmapWorkerTask;
import com.collage.skincare.utils.io.FileUtil;

public class FragSettingsProfile extends ListFragment implements OnClickListener
{
	public static interface Callbacks
	{
		void updateSkinPhoto();
		void updateProfilePhoto();
		void updateName();
		void updateType();
	}
	
	private Callbacks sActivityDummyCallbacks = new Callbacks()
	{
		@Override
		public void updateSkinPhoto()
		{
		}

		@Override
		public void updateProfilePhoto()
		{
		}

		@Override
		public void updateName()
		{
		}

		@Override
		public void updateType()
		{
		}
		
	};
	
	protected Callbacks mActivityCallbacks = sActivityDummyCallbacks;
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		
		if (!(activity instanceof Callbacks)) throw new IllegalStateException("Activity must implement fragment's callbacks.");
		mActivityCallbacks = (Callbacks) activity;
	}
	
	@Override
	public void onDetach()
	{
		super.onDetach();
		
		mActivityCallbacks = sActivityDummyCallbacks;
	}
	
	final static int[] Image_Weather =
	{
			R.drawable.w_12, R.drawable.w_12, R.drawable.w_15, R.drawable.w_08, R.drawable.w_01, R.drawable.w_06, R.drawable.w_11, R.drawable.w_11, R.drawable.w_06, R.drawable.w_06, R.drawable.w_00, R.drawable.w_06, R.drawable.w_06, R.drawable.w_03, R.drawable.w_03, R.drawable.w_03, R.drawable.w_03, R.drawable.w_00, R.drawable.w_00, R.drawable.w_16, R.drawable.w_16, R.drawable.w_16, R.drawable.w_16, R.drawable.w_12, R.drawable.w_12, R.drawable.w_02, R.drawable.w_09, R.drawable.w_05, R.drawable.w_04, R.drawable.w_05, R.drawable.w_04, R.drawable.w_07, R.drawable.w_13, R.drawable.w_14, R.drawable.w_13, R.drawable.w_01, R.drawable.w_13, R.drawable.w_08, R.drawable.w_08, R.drawable.w_08, R.drawable.w_06, R.drawable.w_03, R.drawable.w_02, R.drawable.w_03, R.drawable.w_04, R.drawable.w_08, R.drawable.w_02, R.drawable.w_08

	};

	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;

	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;

	private static final int NOTE_CREATE = 3;
	private static final int NOTE_EDIT = NOTE_CREATE + 1;

	private ImageView mSkinImageView;
	private ImageView mPhotoImageView;
	private Button mbutton;
	private NotesDbAdapter dbAdapter;
	private CustomAdapter adapter;
	private ImageView profile_image12;
	private Button mPopupBtn;
	private Button mAlarmBtn;
	private Uri mImageCaptureUri;
	private ImageView mSelectImageView;

	private DialogInterface.OnClickListener onPhotoDialogClickListener = new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			switch (which)
			{
				case Dialog.BUTTON_POSITIVE:
					doTakePhotoAction();
				break;
				case Dialog.BUTTON_NEUTRAL:
					doTakeAlbumAction();
				break;
				case Dialog.BUTTON_NEGATIVE:
					dialog.dismiss();
				break;
			}
		}
	};

	DialogInterface.OnClickListener onPopupDialogClickListener = new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			switch (which)
			{
				case Dialog.BUTTON_POSITIVE:
				break;
				case Dialog.BUTTON_NEGATIVE:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.frag_settings_profile, container, false);
		setHasOptionsMenu(true);

		mSkinImageView = (ImageView) rootView.findViewById(R.id.frag_settings_skin);
		mSkinImageView.setOnClickListener(this);
		
		mPhotoImageView = (ImageView) rootView.findViewById(R.id.frag_settings_profile);
		mPhotoImageView.setOnClickListener(this);

		mPopupBtn = (Button) rootView.findViewById(R.id.frag_settings_btn_popup);
		mPopupBtn.setOnClickListener(this);

		mAlarmBtn = (Button) rootView.findViewById(R.id.frag_settings_btn_alarm);
		mAlarmBtn.setOnClickListener(this);

		View view = rootView.findViewById(android.R.id.list);
		this.registerForContextMenu(view);
		
		// 초기화 
		int w, h;
		
		// skin photo
		Point outSize = new Point();
		getActivity().getWindowManager().getDefaultDisplay().getSize(outSize);
		w = outSize.x;
		h = getResources().getDimensionPixelSize(R.dimen.profile_setting_skin_height);
		
		BitmapWorkerTask skinTask = new BitmapWorkerTask(mSkinImageView, w, h);
		skinTask.execute(getActivity().getExternalCacheDir().getAbsolutePath().concat("/" + Const.PHOTO_SKIN));
	    
		// profile photo
		w = getResources().getDimensionPixelSize(R.dimen.profile_setting_avatar_width);
		h = getResources().getDimensionPixelSize(R.dimen.profile_setting_avatar_height);
		
	    BitmapWorkerTask profileTask = new BitmapWorkerTask(mPhotoImageView,w, h);
	    profileTask.execute(getActivity().getExternalCacheDir().getAbsolutePath().concat("/" + Const.PHOTO_PROFILE));
	    
	    // name
	    String name = SharedPreferenceManager.getInstance(getActivity()).getName();
	    if(!TextUtils.isEmpty(name)) ((TextView) rootView.findViewById(R.id.frag_settings_name)).setText(name);
	    
	    // type
	    SkinType type = SharedPreferenceManager.getInstance(getActivity()).getType();
	    ((TextView) rootView.findViewById(R.id.frag_settings_type)).setText(type.name());
	    
		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode != Activity.RESULT_OK) return;

		switch (requestCode)
		{
			case PICK_FROM_ALBUM:
			{
				// 이후의 처리가 카메라와 같으므로 일단 break없이 진행합니다.
				// 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.
				mImageCaptureUri = data.getData();
			}
			case PICK_FROM_CAMERA:
			{
				// 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
				// 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
				
				int width = mSelectImageView.getWidth();
				int height = mSelectImageView.getHeight();

				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(mImageCaptureUri, "image/*");

				intent.putExtra("outputX", width);
				intent.putExtra("outputY", height);
				intent.putExtra("aspectX", (int) width/height);
				intent.putExtra("aspectY", 1);
				intent.putExtra("scale", true);
				intent.putExtra("return-data", true);
				startActivityForResult(intent, CROP_FROM_CAMERA);
			}
			break;
			case CROP_FROM_CAMERA:
			{
				// 크롭이 된 이후의 이미지를 넘겨 받습니다. 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
				// 임시 파일을 삭제합니다.
				final Bundle extras = data.getExtras();

				if (extras != null)
				{
					Bitmap photo = extras.getParcelable("data");
					mSelectImageView.setImageBitmap(photo);

					FileUtil.save(photo, getActivity().getExternalCacheDir().getAbsolutePath(), (mSelectImageView == mSkinImageView) ? Const.PHOTO_SKIN : Const.PHOTO_PROFILE);
					
					// update menu drawer
					if(mSelectImageView == mSkinImageView) mActivityCallbacks.updateSkinPhoto();
					else mActivityCallbacks.updateProfilePhoto();
				}

				// 임시 파일 삭제
				File f = new File(mImageCaptureUri.getPath());
				if (f.exists())
				{
					f.delete();
				}

			}
			break;

			case NOTE_CREATE:
			case NOTE_EDIT:
			{
				fillData();
			}
			break;
		}
	}

	/**
	 * 메모장
	 */
	public void onResume()
	{
		super.onResume();

		dbAdapter = new NotesDbAdapter(getActivity());
		dbAdapter.open();

		fillData();
	}

	public void onClick(View v)
	{

		switch (v.getId())
		{
			case R.id.frag_settings_skin :
			case R.id.frag_settings_profile :
			{
				mSelectImageView = (ImageView) v;
				
				new AlertDialog.Builder(getActivity())
					.setIcon(R.drawable.ic_dialog)
					.setTitle(R.string.settings_photo_dialog_select)
					.setPositiveButton(R.string.settings_photo_dialog_positive, onPhotoDialogClickListener)
					.setNeutralButton(R.string.settings_photo_dialog_neutral, onPhotoDialogClickListener)
					.setNegativeButton(android.R.string.cancel, onPhotoDialogClickListener)
				.show();
			}
			break;

			case R.id.frag_settings_btn_alarm:
			{
				Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getString(R.string.settings_alarm_dialog_select));
				Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 1l);
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, uri);
				startActivityForResult(intent, 123);
			}
			break;

			case R.id.frag_settings_btn_popup:
			{
				new AlertDialog.Builder(getActivity()).setTitle(R.string.settings_popup_dialog_select).setSingleChoiceItems(R.array.select_popup, 0, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{

					}
				}).setPositiveButton(android.R.string.ok, onPopupDialogClickListener).setNegativeButton(android.R.string.cancel, onPopupDialogClickListener).show();
			}
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.frag_settings, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_new_note:
			{
				Intent intent = new Intent(getActivity(), NoteEdit.class);
				startActivityForResult(intent, NOTE_CREATE);
			}
				return true;

			default:
				return (super.onOptionsItemSelected(item));
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		// 삭제 메뉴 추가
		menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "삭제");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case DELETE_ID:
				AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
				dbAdapter.deleteNote(info.id);
				fillData();
				return true;
		}
		return super.onContextItemSelected(item);
	}

	// NoteEdit로 아이디 값만 넘겨줌 아이디 있으면 수정

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		long rowId = -1;
		super.onListItemClick(l, v, position, id);

		Intent intent = new Intent(getActivity(), NoteEdit.class);
		TextView tv = (TextView) v.findViewById(android.R.id.text1);
		rowId = Long.parseLong(tv.getText().toString());
		if (rowId != -1)
		{
			intent.putExtra(NotesDbAdapter._ID, rowId);
			startActivityForResult(intent, NOTE_EDIT);
		}
	}

	/**
	 * 카메라에서 이미지 가져오기
	 */
	private void doTakePhotoAction()
	{

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// 임시로 사용할 파일의 경로를 생성
		String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
		mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
		Log.d("test", mImageCaptureUri.toString());
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PICK_FROM_CAMERA);
	}

	/**
	 * 앨범에서 이미지 가져오기
	 */
	private void doTakeAlbumAction()
	{
		// 앨범 호출
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(intent, PICK_FROM_ALBUM);
	}

	private void fillData()
	{

		// 모든 데이터의 커서를 얻어옴
		Cursor c = dbAdapter.fetchAllNotes();

		//		// 리스트뷰에 데이터베이스의 저장된 데이터를 연결
		//		String[] from = new String[] { BaseColumns._ID, NotesDbAdapter.TITLE };
		//		// 한 행을 보여줄 XML 파일의 텍스트 뷰 id
		//		int to[] = { android.R.id.text1, android.R.id.text2 };
		//
		//		adapter = new SimpleCursorAdapter(getActivity(),
		//				android.R.layout.simple_list_item_2, c, from, to);
		adapter = new CustomAdapter(FragSettingsProfile.this.getActivity(), c);
		setListAdapter(adapter);

		// Activity의 라이프사이클에 따라 알아서 커서를 관리해 줌, 커서를 쓰는
		// 액티비티가 종료(destroy)할 때 cursor를 따로 close를 해주지 않아도
		// 알아서 close해준다.
		getActivity().startManagingCursor(c);
	}
}