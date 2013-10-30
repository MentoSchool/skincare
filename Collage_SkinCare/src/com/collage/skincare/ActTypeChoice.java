package com.collage.skincare;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.collage.skincare.defined.Const.SkinType;
import com.collage.skincare.manager.SharedPreferenceManager;

public class ActTypeChoice extends TabActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_type_choice);
		TabHost mTabHost = getTabHost();

		TabSpec spec;
		spec = mTabHost.newTabSpec("tag");
		spec.setIndicator(getString(R.string.type_choice_dry));
		spec.setContent(R.id.dry);
		mTabHost.addTab(spec);

		spec = mTabHost.newTabSpec("tag");
		spec.setIndicator(getString(R.string.type_choice_oily));
		spec.setContent(R.id.oily);
		mTabHost.addTab(spec);

		spec = mTabHost.newTabSpec("tag");
		spec.setIndicator(getString(R.string.type_choice_combination));
		spec.setContent(R.id.complexity);
		mTabHost.addTab(spec);
	}

	public void doChoice(View v)
	{
		SkinType type = SkinType.Normal;

		switch (v.getId())
		{
			case R.id.act_type_choice_btn_oily:
				type = SkinType.Oily;
			break;
			case R.id.act_type_choice_btn_dry:
				type = SkinType.Dry;
			break;
			case R.id.act_type_choice_btn_combination:
				type = SkinType.Combination;
			break;
		}

		// save preference
		SharedPreferenceManager.getInstance(getApplicationContext()).setType(type);

		setResult(RESULT_OK);
		finish();
	}
}
