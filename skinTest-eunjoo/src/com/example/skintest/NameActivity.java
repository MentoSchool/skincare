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
	boolean mInitSpinner; //�ʱ�ȭ �Ϸắ��(?)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_name);
		Button testStartBtn=(Button) findViewById(R.id.testStartBtn);
		Spinner locationspin=(Spinner) findViewById(R.id.locationspinner);
		locationspin.setPrompt("��ġ�� �����ϼ���");
		
		//testButton Ŭ�� �� �߻��ϴ� �̺�Ʈ
		testStartBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent(NameActivity.this,MainActivity.class);
				startActivity(intent);
				
			}
		
		} );
		
		//location select (Spinner)
		adspin=ArrayAdapter.createFromResource(this, R.array.location, android.R.layout.simple_spinner_item);
		adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //Ŭ���� �˾��� ���̾ƿ� ����!
		locationspin.setAdapter(adspin);
		
		locationspin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
				if(mInitSpinner==false){
					mInitSpinner=true;
					return;
				}
				Toast.makeText(NameActivity.this,adspin.getItem(position)+"�� �����ϼ̽��ϴ�.", Toast.LENGTH_SHORT).show();
			}
			public void onNothingSelected(AdapterView<?> parent){
			}
			
		});
	}
}



/*
 * public void mOnClick(View v){
 * 		new AlertDialog.Builder(this)
 * 		.setTitle("���� ����")
 * 		.setMessage("�̸��� �����ϼž� �մϴ�")
 * 		.setIcon(R.drawable.androboy)
 * 		.setCancelable(false)
 * 		.setNegativeButton("�ݱ�",null)
 * 		.show();
 * }
 * */
