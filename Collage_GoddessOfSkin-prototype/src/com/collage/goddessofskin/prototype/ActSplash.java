package com.collage.goddessofskin.prototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.collage.goddessofskin.prototype.defined.Const.SkinType;
import com.collage.goddessofskin.prototype.manager.SharedPreferenceManager;

public class ActSplash extends Activity
{
	private static final int REQ_CODE_CHOICE = 0;
	private static final int REQ_CODE_TEST = 1;

	private static final int DURATION_PROGRESS = 2000;
	private static final int DURATION_ANIMATION = 500;
	
	private boolean isProgressFinish;

	private Runnable mRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			isProgressFinish = true;
			
			SkinType type = SharedPreferenceManager.getInstance(getApplicationContext()).getType();
			if (type == SkinType.Normal)
			{
				findViewById(R.id.act_splash_image).animate()
					.alpha(0)
					.setDuration(DURATION_ANIMATION)
					.setInterpolator(new DecelerateInterpolator(1.3f))
				.start();
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

		isProgressFinish = false;
		getWindow().getDecorView().postDelayed(mRunnable, DURATION_PROGRESS);
	}
	
	@Override
	public void onBackPressed()
	{
		isProgressFinish = false;
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
		if(!isProgressFinish) return;
		startActivityForResult(new Intent(this, ActTypeChoice.class), REQ_CODE_CHOICE);
	}

	public void gotoTypeTest(View v)
	{
		if(!isProgressFinish) return;
		startActivityForResult(new Intent(this, ActTypeTest.class), REQ_CODE_TEST);
	}

	private void gotoMain()
	{
		startActivity(new Intent(this, ActMain.class));
		finish();
	}
}
