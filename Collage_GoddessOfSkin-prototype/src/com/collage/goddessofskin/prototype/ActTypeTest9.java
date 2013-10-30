package com.collage.goddessofskin.prototype;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ActTypeTest9 extends Activity {
	public static Activity ActTypeTestActivity9;
	ActTypeTest8 acttypetest8=(ActTypeTest8) ActTypeTest8.ActTypeTestActivity8;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_type_test_back);
		
		ActTypeTestActivity9 = ActTypeTest9.this;
		acttypetest8.finish();
		
		TextView txttest1=(TextView) findViewById(R.id.txttest1);
		String text="9.세안 후 기초관리를 하지 않더라도 금방 기름기가 돈다.";
		txttest1.setText(text);
		Intent intent=getIntent();
		final ArrayList<Integer> mYesNocount=intent.getIntegerArrayListExtra("count");


		Button testBackbutton=(Button) findViewById(R.id.testbackbtn);
		testBackbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ActTypeTest9.this,ActTypeTest8.class);
				startActivity(intent);
			}
		});
		
		final RadioGroup radiogroup=(RadioGroup) findViewById(R.id.RadioGroup1);
		
		radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					 Intent intent=new Intent(ActTypeTest9.this,ActTypeTest10.class);
				  final RadioButton rb=(RadioButton) findViewById(checkedId);
					    if(rb!=null){
					    	if(checkedId==R.id.yes_radiobtn){   
					    		mYesNocount.remove(8);
					    		mYesNocount.add(8,1);
					    		Toast.makeText(ActTypeTest9.this,"Yes select", Toast.LENGTH_SHORT).show();
					    	}else if(checkedId==R.id.no_radiobtn){  
					    		mYesNocount.remove(8);
					    		mYesNocount.add(8,0);
					    		Toast.makeText(ActTypeTest9.this,"no select", Toast.LENGTH_SHORT).show();
					    	}
					}else{
				
					}
					    intent.putIntegerArrayListExtra("count", mYesNocount);
						startActivity(intent); 
				}
			
			}
			
		});
		
		
	}
}
