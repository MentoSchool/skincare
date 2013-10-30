package com.collage.skincare;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.collage.skincare.defined.Const.SkinType;
import com.collage.skincare.manager.SharedPreferenceManager;

public class ActTypeTest extends Activity
{
	private ListView mCheckedList;
	private SkinType mSkinType;

	private DialogInterface.OnClickListener onDialogClickListener = new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			switch (which)
			{
				case Dialog.BUTTON_POSITIVE : doStart(); break;
				case Dialog.BUTTON_NEGATIVE : doReset(); break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_type_test);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		mSkinType = SkinType.Normal;

		mCheckedList = (ListView) findViewById(R.id.act_type_test_list);
		mCheckedList.setItemsCanFocus(false);
		mCheckedList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mCheckedList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, getResources().getStringArray(R.array.type_test)));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.act_type_test, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
			{
				finish();
			}
				return true;

			case R.id.action_type_test_accept:
			{
				int count = mCheckedList.getCheckedItemCount();

				if (count <= 3) mSkinType = SkinType.Dry;
				else if (count <= 6) mSkinType = SkinType.Combination;
				else mSkinType = SkinType.Oily;

				int resId = getResources().getIdentifier("type_choice_" + mSkinType.name().toLowerCase(Locale.getDefault()), "string", getPackageName());

				new AlertDialog.Builder(this).setIcon(R.drawable.ic_dialog).setTitle(R.string.type_test_dialog_title).setMessage(getString(R.string.type_test_dialog_message, getString(resId))).setPositiveButton(R.string.type_test_dialog_ok, onDialogClickListener).setNegativeButton(R.string.type_test_dialog_cancel, onDialogClickListener).create().show();
			}
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * 다시하기
	 */
	private void doReset()
	{
		int i, len = mCheckedList.getCount();
		for (i = 0; i < len; i++)
			mCheckedList.setItemChecked(i, false);

		mSkinType = SkinType.Normal;
	}

	/**
	 * 지정하기
	 */
	private void doStart()
	{
		// save preference
		SharedPreferenceManager.getInstance(getApplicationContext()).setType(mSkinType);

		setResult(RESULT_OK);
		finish();
	}
}
