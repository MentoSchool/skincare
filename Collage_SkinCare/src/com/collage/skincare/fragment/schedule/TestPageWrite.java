package com.collage.skincare.fragment.schedule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.collage.skincare.R;

public class TestPageWrite extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frag_schedule_alarm);
		
		Button a = (Button) findViewById(R.id.rest);
		a.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				resetAlarm();
			}

			private void resetAlarm() {
				// TODO Auto-generated method stub
				
			}

		});
		
		
		
		
		
	}

}
