package com.collage.goddessofskin.prototype.fragment.settings;

import java.io.File;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.collage.goddessofskin.prototype.R;
import com.collage.goddessofskin.prototype.utils.io.FileUtil;

public class FragSettingsProfile extends Fragment implements OnClickListener {
	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;

	private Uri mImageCaptureUri;
	private ImageView mPhotoImageView;

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

		if (resultCode != getActivity().RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case CROP_FROM_CAMERA: {
			// 크롭이 된 이후의 이미지를 넘겨 받습니다. 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
			// 임시 파일을 삭제합니다.
			final Bundle extras = data.getExtras();

			
			
			
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				mPhotoImageView.setImageBitmap(photo);
			
				FileUtil.save(photo,getActivity().getExternalCacheDir().getAbsolutePath(),"frofile.jpg");
				
				
			}

			
			
			// 임시 파일 삭제
			File f = new File(mImageCaptureUri.getPath());
			if (f.exists()) {
				f.delete();
			}

			break;
		}

		case PICK_FROM_ALBUM: {
			// 이후의 처리가 카메라와 같으므로 일단 break없이 진행합니다.
			// 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.

			mImageCaptureUri = data.getData();
		}

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

			break;
		}
		}
	}

	public void onClick(View v) {
		new AlertDialog.Builder(getActivity())
		 .setIcon(R.drawable.ic_launcher)
		.setTitle("업로드할 이미지 선택")
		.setPositiveButton("사진촬영", cameraListener)
		.setNeutralButton("앨범선택", albumListener)
		.setNegativeButton("취소", cancelListener).show();
		
	}

}
