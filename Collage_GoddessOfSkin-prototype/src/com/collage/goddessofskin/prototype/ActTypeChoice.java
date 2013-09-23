package com.collage.goddessofskin.prototype;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.collage.goddessofskin.prototype.defined.Const.SkinType;
import com.collage.goddessofskin.prototype.manager.SharedPreferenceManager;

public class ActTypeChoice extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_type_choice);
	}
	
	public void doChoice(View v)
	{
		SkinType type = SkinType.Normal;
		
		switch (v.getId())
		{
			case R.id.act_type_choice_btn_oily 			: type = SkinType.Oily;			break;
			case R.id.act_type_choice_btn_dry			: type = SkinType.Dry; 			break;
			case R.id.act_type_choice_btn_combination	: type = SkinType.Combimation;	break; 
		}
		
		// save preference
		SharedPreferenceManager.getInstance(getApplicationContext()).setType(type);
		
		setResult(RESULT_OK);
		finish();
	}
}
