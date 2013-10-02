package com.collage.goddessofskin.prototype;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.collage.goddessofskin.prototype.defined.Const.DrawerMenu;
import com.collage.goddessofskin.prototype.fragment.FragMain;
import com.collage.goddessofskin.prototype.fragment.schedule.FragScheduleBoard;
import com.collage.goddessofskin.prototype.fragment.schedule.FragScheduleSettings;
import com.collage.goddessofskin.prototype.fragment.settings.FragSettingsHelp;
import com.collage.goddessofskin.prototype.fragment.settings.FragSettingsProfile;
import com.collage.goddessofskin.prototype.fragment.weather.FragWeatherFun;
import com.collage.goddessofskin.prototype.fragment.weather.FragWeatherToday;
//응아아온이ㅏ럼ㄴ이ㅏㅣ러ㅣㅏ;ㄴㅇ머리남ㅋㅋㅋ.ㅍㅠㅠㅠ
//폰트를 바꿈
/**
 * Main activity
 * 
 * @author jungho.song@kodeglam.com (threeword)
 * @since 2013. 9. 23. �ㅽ썑 1:58:47
 */
public class ActMain extends Activity 
{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mDrawerMenus;
	private View mDrawer;
	
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

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        mTitle = mDrawerTitle = getTitle();
        mDrawerMenus = getResources().getStringArray(R.array.drawer_menus);
        
        mDrawerLayout = (DrawerLayout) findViewById(R.id.act_main_drawer_layout);
        mDrawer = findViewById(R.id.act_main_left_drawer);
        mDrawerList = (ListView) findViewById(R.id.drawer_menus);
        
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.view_drawer_list_item, mDrawerMenus));
        mDrawerList.setOnItemClickListener(mDrawerListItemClickListener);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                )
        {
			public void onDrawerClosed(View view)
			{
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView)
			{
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            doMenuSelected(0);
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		// If the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawer);
		menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}
		// Handle action buttons
		switch (item.getItemId())
		{
			case R.id.action_websearch:
				/*
				// create intent to perform web search for this planet
				Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
				intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
				// catch event that there's no activity to handle intent
				if (intent.resolveActivity(getPackageManager()) != null)
				{
					startActivity(intent);
				}
				else
				{
					Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
				}
				*/
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
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
	
	/**
	 * Drawer menu selected
	 * @param position
	 */
	private void doMenuSelected(int position)
	{
		// update the main content by replacing fragments
		Fragment fragment = null;
		
		DrawerMenu dm = DrawerMenu.values()[position];
		switch (dm)
		{
			case Home : fragment = new FragMain(); break;
			
			case ScheduleSettings : fragment = new FragScheduleSettings(); break;
			case ScheduleBoard : fragment = new FragScheduleBoard(); break;
			
			case WeatherToday : fragment = new FragWeatherToday(); break;
			case WeatherFun : fragment = new FragWeatherFun(); break;
			
			case Share : 
			{
			}
			break;
			
			case SettingsProfile : fragment = new FragSettingsProfile(); break;
			case SettingsAlarm : 
			{
			}
			break;
			case SettingsPopup : 
			{
			}
			break;
			case SettingsSkinType : 
			{
			}
			break;
			case SettingsHelp : fragment = new FragSettingsHelp(); break;
		}
		if(fragment != null)
		{
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.act_main_content_frame, fragment).commit();
		}

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mDrawerMenus[position]);
		mDrawerLayout.closeDrawer(mDrawer);
	}
}