package com.collage.goddessofskin.prototype;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.collage.goddessofskin.api.ApiMain;
import com.collage.goddessofskin.prototype.defined.Const.DrawerMenu;
import com.collage.goddessofskin.prototype.defined.Const.SkinType;
import com.collage.goddessofskin.prototype.fragment.FragMain;
import com.collage.goddessofskin.prototype.fragment.FragMain_Pack;
import com.collage.goddessofskin.prototype.fragment.schedule.FragScheduleBoard;
import com.collage.goddessofskin.prototype.fragment.schedule.FragScheduleSettings;
import com.collage.goddessofskin.prototype.fragment.settings.FragSettingsHelp;
import com.collage.goddessofskin.prototype.fragment.settings.FragSettingsProfile;
import com.collage.goddessofskin.prototype.fragment.weather.FragWeatherFun;
import com.collage.goddessofskin.prototype.fragment.weather.FragWeatherToday;
//�묒븘�꾩삩�담뀖�쇈꽩�담뀖�ｋ윭�ｃ뀖;�담뀋癒몃━�ⓦ뀑�뗣뀑.�띲뀪�졼뀪
//�고듃瑜�諛붽퓞
import com.collage.goddessofskin.prototype.manager.SharedPreferenceManager;

/**
 * Main activity
 * 
 * @author jungho.song@kodeglam.com (threeword)
 * @since 2013. 9. 23. 占썬끋��1:58:47
 */
public class ActMain extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mDrawerMenus;
	private View mDrawer;
	private MediaPlayer mp;
	private Dialog mDialog = null;
	/**
	 * Drawer menu list item click event listener
	 */
	private AdapterView.OnItemClickListener mDrawerListItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			doMenuSelected(position);
		}
	};

	// 시험

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		// StrictMode.enableDefaults();

		ApiMain.getInstance().UltraApi();// 자외선 Api

		SkinType type = SharedPreferenceManager.getInstance(getApplicationContext()).getType();
		Toast.makeText(getApplicationContext(), String.format("Your skin type is %s", type.name()), Toast.LENGTH_SHORT).show();

		TextView drawer_profile_type = (TextView) findViewById(R.id.drawer_profile_type);
		drawer_profile_type.setText(type.name());

		mTitle = mDrawerTitle = getTitle();
		mDrawerMenus = getResources().getStringArray(R.array.drawer_menus);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.act_main_drawer_layout);
		mDrawer = findViewById(R.id.act_main_left_drawer);
		mDrawerList = (ListView) findViewById(R.id.drawer_menus);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.view_drawer_list_item, mDrawerMenus));
		mDrawerList.setOnItemClickListener(mDrawerListItemClickListener);

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			doMenuSelected(0);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		if (mDrawerToggle.onOptionsItemSelected(item)) return true;
		else return super.onOptionsItemSelected(item);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);

	}

	/**
	 * Drawer menu selected
	 * 
	 * @param position
	 */
	private void doMenuSelected(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;

		Activity activity = null;

		Fragment fragmentActivity = null;

		DrawerMenu dm = DrawerMenu.values()[position];
		switch (dm) {
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
		case SettingsAlarm: {
			Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
			intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "알림음 설정");
			Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 1l);
			intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
			intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, uri);
			startActivityForResult(intent, 123);

		}
			break;
		case SettingsPopup: {
			Builder d = new AlertDialog.Builder(this);
			d.setTitle("팝업설정");
			d.setSingleChoiceItems(R.array.select_popup, 0, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {

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
			break;
		case SettingsSkinType: {
		}
			break;
			*/
			
		case SettingsHelp: {
			fragment = new FragSettingsHelp();
		}
			break;

		}
		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.act_main_content_frame, fragment).commit();
		}

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mDrawerMenus[position]);
		mDrawerLayout.closeDrawer(mDrawer);
	}

	public void onBackPressed() {

		Builder d = new AlertDialog.Builder(this);
		d.setMessage("정말 종료하시겠습니까?");
		d.setPositiveButton("예", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// process전체 종료
				finish();
			}
		});
		d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		d.show();
	}
}