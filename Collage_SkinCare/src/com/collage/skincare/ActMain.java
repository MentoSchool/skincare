package com.collage.skincare;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.collage.skincare.api.ApiMain;
import com.collage.skincare.defined.Const;
import com.collage.skincare.defined.Const.DrawerMenu;
import com.collage.skincare.defined.Const.SkinType;
import com.collage.skincare.fragment.FragMain_Pack;
import com.collage.skincare.fragment.schedule.FragScheduleBoard;
import com.collage.skincare.fragment.settings.FragSettingsHelp;
import com.collage.skincare.fragment.settings.FragSettingsProfile;
import com.collage.skincare.fragment.weather.FragWeatherFun;
import com.collage.skincare.fragment.weather.FragWeatherToday;
import com.collage.skincare.manager.SharedPreferenceManager;
import com.collage.skincare.utils.graphics.BitmapUtil.BitmapWorkerTask;

/**
 * Main activity
 */
public class ActMain extends FragmentActivity implements FragSettingsProfile.Callbacks
{
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private TextView mSkinType;
	private TextView mName;
	private ImageView mSkinPhoto;
	private ImageView mProfilePhoto;
	
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mDrawerMenus;
	private View mDrawer;
	private MediaPlayer mp;
	private Dialog mDialog = null;


	/**
	 * Drawer menu list item click event listener
	 */
	private AdapterView.OnItemClickListener mDrawerListItemClickListener = new AdapterView.OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			doMenuSelected(position);
		}
	};

	// 시험

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		ApiMain.getInstance().UltraApi();// 자외선 Api
		
		mTitle = mDrawerTitle = getTitle();
		mDrawerMenus = getResources().getStringArray(R.array.drawer_menus);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.act_main_drawer_layout);
		mDrawer = findViewById(R.id.act_main_left_drawer);
		mDrawerList = (ListView) findViewById(R.id.drawer_menus);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new DrawerMenuAdapter(this, R.layout.view_drawer_list_item, mDrawerMenus));
		mDrawerList.setOnItemClickListener(mDrawerListItemClickListener);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close)
		{
			public void onDrawerClosed(View view)
			{
				getActionBar().setTitle(mTitle);
			}

			public void onDrawerOpened(View drawerView)
			{
				getActionBar().setTitle(mDrawerTitle);
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null)
		{
			doMenuSelected(0);
		}
		
		
		// 초기화
		mSkinType = (TextView) findViewById(R.id.drawer_profile_type);
		mName = (TextView) findViewById(R.id.drawer_profile_name);
		mSkinPhoto = (ImageView) findViewById(R.id.drawer_profile_skin);
		mProfilePhoto = (ImageView) findViewById(R.id.drawer_profile_avatar);
		
		updateSkinPhoto();
		updateProfilePhoto();
		updateName();
		updateType();
	}
	
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (mDrawerToggle.onOptionsItemSelected(item))
			return true;
		else
			return super.onOptionsItemSelected(item);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void setTitle(CharSequence title)
	{
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}
	
	@Override
	public void onBackPressed()
	{

		Builder d = new AlertDialog.Builder(this);
		d.setMessage("정말 종료하시겠습니까?");
		d.setPositiveButton("예", new DialogInterface.OnClickListener()
		{

			public void onClick(DialogInterface dialog, int which)
			{
				// process전체 종료
				finish();
			}
		});
		d.setNegativeButton("아니요", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
			}
		});
		d.show();
	}
	
	@Override
	public void updateSkinPhoto()
	{
		int w, h;
		
		w = getResources().getDimensionPixelSize(R.dimen.profile_skin_width);
		h = getResources().getDimensionPixelSize(R.dimen.profile_skin_width);
		
		BitmapWorkerTask skinTask = new BitmapWorkerTask(mSkinPhoto, w, h);
		skinTask.execute(getExternalCacheDir().getAbsolutePath().concat("/" + Const.PHOTO_SKIN));
	}

	@Override
	public void updateProfilePhoto()
	{
		int w, h;
	    
		w = getResources().getDimensionPixelSize(R.dimen.profile_avatar_width);
		h = getResources().getDimensionPixelSize(R.dimen.profile_avatar_height);
		
	    BitmapWorkerTask profileTask = new BitmapWorkerTask(mProfilePhoto,w, h);
	    profileTask.execute(getExternalCacheDir().getAbsolutePath().concat("/" + Const.PHOTO_PROFILE));
	}

	@Override
	public void updateName()
	{
	    String name = SharedPreferenceManager.getInstance(getApplicationContext()).getName();
	    if(!TextUtils.isEmpty(name)) mName.setText(name);
	}

	@Override
	public void updateType()
	{
	    SkinType type = SharedPreferenceManager.getInstance(getApplicationContext()).getType();
	    int resId = getResources().getIdentifier("type_choice_" + type.name().toLowerCase(Locale.getDefault()), "string", getPackageName());
	    mSkinType.setText(resId);
	}

	/**
	 * Drawer menu selected
	 * 
	 * @param position
	 */
	private void doMenuSelected(int position)
	{
		// update the main content by replacing fragments
		Fragment fragment = null;

		Activity activity = null;

		Fragment fragmentActivity = null;

		DrawerMenu dm = DrawerMenu.values()[position];
		switch (dm)
		{
			case Home:
				fragment = new FragMain_Pack();
			break;

			//		case ScheduleSettings:
			//			fragment = new FragScheduleSettings();
			//			break;

			case Schedule:
				fragment = new FragScheduleBoard();
			break;

			case WeatherToday:

				fragment = new FragWeatherToday();
			break;
			case WeatherFun:
				ApiMain.getInstance().UltraApi();
				fragment = new FragWeatherFun();
			break;

			//		case Share:
			//
			//			fragment = new FragMain();
			//			break;

			case Settings:
				fragment = new FragSettingsProfile();

			break;

			/*
			 * case SettingsAlarm: { Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER); intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "알림음 설정"); Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 1l); intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false); intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, uri); startActivityForResult(intent, 123);
			 * 
			 * } break; case SettingsPopup: { Builder d = new AlertDialog.Builder(this); d.setTitle("팝업설정"); d.setSingleChoiceItems(R.array.select_popup, 0, new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int whichButton) {
			 * 
			 * }
			 * 
			 * }); d.setPositiveButton("확인", new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int whichButton) { // 이구간은 확인버튼을 선택했을때 설정되어야하는 구간이니까 멘토님한테 물어본다. } }); d.setNegativeButton("취소", new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which) { dialog.cancel(); } }); d.show();
			 * 
			 * } break; case SettingsSkinType: { } break;
			 */

			case Help:
			{
				fragment = new FragSettingsHelp();
			}
			break;

		}
		if (fragment != null)
		{
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.act_main_content_frame, fragment).commit();
		}

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mDrawerMenus[position]);
		mDrawerLayout.closeDrawer(mDrawer);
	}

	private class DrawerMenuAdapter extends ArrayAdapter<String>
	{
		private int drawablePadding = 0;

		public DrawerMenuAdapter(Context context, int resource, String[] objects)
		{
			super(context, resource, objects);

			drawablePadding = getResources().getDimensionPixelOffset(R.dimen.menu_item_drawable_padding);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			TextView v = (TextView) super.getView(position, convertView, parent);

			DrawerMenu dm = DrawerMenu.values()[position];
			int resId = getResources().getIdentifier("selector_ic_menu_" + dm.name().toLowerCase(Locale.getDefault()), "drawable", getPackageName());

			v.setCompoundDrawablePadding(drawablePadding);
			v.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);

			return v;
		}

	}
}
