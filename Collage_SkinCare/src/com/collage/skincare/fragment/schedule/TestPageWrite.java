package com.collage.skincare.fragment.schedule;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.collage.skincare.R;

public class TestPageWrite extends Activity {

	TextView textView_1, textView_2, textView_3, textView_4;

	private AlarmManager mManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frag_schedule_alarm);

		String[] Resources = getResources().getStringArray(R.array.oil);

		Button a = (Button) findViewById(R.id.alram_stop);

		Button b = (Button) findViewById(R.id.alram_rset);

		textView_1 = (TextView) findViewById(R.id.type_detail_01);
		
		textView_2 = (TextView) findViewById(R.id.type_detail_02);
		
		textView_3 = (TextView) findViewById(R.id.type_detail_03);
		
		textView_4 = (TextView) findViewById(R.id.type_detail_04);

		textView_1.setText(Resources[0]);
		
		textView_2.setText(Resources[1]);
		
		textView_3.setText(Resources[2]);
		
		textView_4.setText(Resources[3]);

		a.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mManager.cancel(pendingIntent(1234));
			}
		});
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(TestPageWrite.this,
						Sleep_Activity.class);

				startActivity(intent);
			}
		});

	}

	private PendingIntent pendingIntent(int j) {
		// TODO Auto-generated method stub
		Intent i = new Intent(getApplicationContext(), TestPageWrite.class);

		PendingIntent pi = PendingIntent.getActivity(this, j, i, 0);

		return pi;
	}
}
