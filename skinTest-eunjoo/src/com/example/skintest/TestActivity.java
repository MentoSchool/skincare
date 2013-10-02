package com.example.skintest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class TestActivity extends Activity {
	int yes_count=0;
	int no_count=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skin_test);
	 
		final RadioGroup radiogroup=(RadioGroup) findViewById(R.id.RadioGroup1);
		Button testbackbtn=(Button) findViewById(R.id.testbackbtn);
		Button testresultbtn=(Button) findViewById(R.id.testresultbtn);   //임시로 연결하는 부분임!!!!!!!!!!!!!!
	
		testresultbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TestActivity.this,TestReuslt_oilyActivitiy.class);
				startActivity(intent);
				
			}
		});
		
		testbackbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//이전fragment로 가기
				//이전에 눌렀던 yes_count나 no_count감소!
				
			}
		});
		
		radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
				  final RadioButton rb=(RadioButton) findViewById(checkedId);
					    if(rb!=null){
					    	if(checkedId==0x7f090007){    //yes의 checkedId가  0x7f090007
					    		yes_count=yes_count+1;
					    		Toast.makeText(TestActivity.this,"Yes select"+yes_count, Toast.LENGTH_SHORT).show();     //yes count증가시키기
					    		//다음 문항으로 넘어가기 +checkedID 초기화
					    	}else if(checkedId==0x7f090008){    //no의 checkedId가  0x7f090008
					    		no_count=no_count+1;
					    		Toast.makeText(TestActivity.this,"No select"+no_count, Toast.LENGTH_SHORT).show(); 		//no count증가시키기 
					    		//다음 문항으로 넘어가기 
					    	}
					}else{
						Toast.makeText(TestActivity.this,"Please, select one", Toast.LENGTH_SHORT).show();
					}
				}
				
			}
		});
		
}
}

/*if(yes_count<=3){
Intent intent=new Intent(TestActivity.this,TestReuslt_dryActivitiy.class);
startActivity(intent);
}else if(yes_count<=6){
Intent intent=new Intent(TestActivity.this,TestReuslt_complexityActivitiy.class);
startActivity(intent);
}else{
Intent intent=new Intent(TestActivity.this,TestReuslt_complexityActivitiy.class);
startActivity(intent);
}
*/