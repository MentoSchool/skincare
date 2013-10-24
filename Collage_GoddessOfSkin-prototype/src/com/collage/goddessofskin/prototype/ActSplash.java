package com.collage.goddessofskin.prototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.collage.goddessofskin.prototype.defined.Const.SkinType;
import com.collage.goddessofskin.prototype.manager.SharedPreferenceManager;

public class ActSplash extends Activity
{
	private static final int REQ_CODE_CHOICE = 0;
	private static final int REQ_CODE_TEST = 1;

	private static final int DURATION_PROGRESS = 2000;

	private Runnable mRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			SkinType type = SharedPreferenceManager.getInstance(getApplicationContext()).getType();
			if (type == SkinType.Normal)
			{
				findViewById(R.id.act_splash_question).setVisibility(View.VISIBLE);
				findViewById(R.id.act_splash_image).setVisibility(View.GONE);
				//findViewById(R.id.act_splash_title).setVisibility(View.GONE);
				//findViewById(R.id.act_splash_progress).setVisibility(View.GONE);
			}
			else
			{
				gotoMain();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_splash);

		getWindow().getDecorView().postDelayed(mRunnable, DURATION_PROGRESS);
	}
	
	@Override
	public void onBackPressed()
	{
		getWindow().getDecorView().removeCallbacks(mRunnable);
		super.onBackPressed();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQ_CODE_CHOICE || requestCode == REQ_CODE_TEST)
		{
			if (resultCode == RESULT_OK)
			{
				gotoMain();
			}
		}
	}

	public void gotoTypeChoice(View v)
	{
		startActivityForResult(new Intent(this, ActTypeChoice.class), REQ_CODE_CHOICE);
	}

	public void gotoTypeTest(View v)
	{
		startActivityForResult(new Intent(this, ActTypeTest.class), REQ_CODE_TEST);
	}

	private void gotoMain()
	{
		// debug
		SkinType type = SharedPreferenceManager.getInstance(getApplicationContext()).getType();
		Toast.makeText(getApplicationContext(), String.format("Your skin type is %s", type.name()), Toast.LENGTH_SHORT).show();
		
		startActivity(new Intent(this, ActMain.class));
		finish();
	}
}
