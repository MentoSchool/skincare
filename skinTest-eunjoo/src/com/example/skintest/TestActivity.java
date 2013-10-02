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
		Button testresultbtn=(Button) findViewById(R.id.testresultbtn);   //�ӽ÷� �����ϴ� �κ���!!!!!!!!!!!!!!
	
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
				//����fragment�� ����
				//������ ������ yes_count�� no_count����!
				
			}
		});
		
		radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
				  final RadioButton rb=(RadioButton) findViewById(checkedId);
					    if(rb!=null){
					    	if(checkedId==0x7f090007){    //yes�� checkedId��  0x7f090007
					    		yes_count=yes_count+1;
					    		Toast.makeText(TestActivity.this,"Yes select"+yes_count, Toast.LENGTH_SHORT).show();     //yes count������Ű��
					    		//���� �������� �Ѿ�� +checkedID �ʱ�ȭ
					    	}else if(checkedId==0x7f090008){    //no�� checkedId��  0x7f090008
					    		no_count=no_count+1;
					    		Toast.makeText(TestActivity.this,"No select"+no_count, Toast.LENGTH_SHORT).show(); 		//no count������Ű�� 
					    		//���� �������� �Ѿ�� 
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