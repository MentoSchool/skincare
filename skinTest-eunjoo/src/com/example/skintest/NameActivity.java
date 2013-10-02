package com.example.skintest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class NameActivity extends Activity {
	ArrayAdapter<CharSequence> adspin;
	boolean mInitSpinner; //초기화 완료변수(?)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_name);
		Button testStartBtn=(Button) findViewById(R.id.testStartBtn);
		Spinner locationspin=(Spinner) findViewById(R.id.locationspinner);
		locationspin.setPrompt("위치를 선정하세요");
		
		//testButton 클릭 시 발생하는 이벤트
		testStartBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent(NameActivity.this,MainActivity.class);
				startActivity(intent);
				
			}
		
		} );
		
		//location select (Spinner)
		adspin=ArrayAdapter.createFromResource(this, R.array.location, android.R.layout.simple_spinner_item);
		adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //클릭시 팝업의 레이아웃 지정!
		locationspin.setAdapter(adspin);
		
		locationspin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
				if(mInitSpinner==false){
					mInitSpinner=true;
					return;
				}
				Toast.makeText(NameActivity.this,adspin.getItem(position)+"를 지정하셨습니다.", Toast.LENGTH_SHORT).show();
			}
			public void onNothingSelected(AdapterView<?> parent){
			}
			
		});
	}
}



/*
 * public void mOnClick(View v){
 * 		new AlertDialog.Builder(this)
 * 		.setTitle("공지 사항")
 * 		.setMessage("이름을 지정하셔야 합니다")
 * 		.setIcon(R.drawable.androboy)
 * 		.setCancelable(false)
 * 		.setNegativeButton("닫기",null)
 * 		.show();
 * }
 * */
