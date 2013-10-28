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

public class ActTypeTest3 extends Activity {
	public static Activity ActTypeTestActivity3;
	ActTypeTest2 acttypetest2=(ActTypeTest2) ActTypeTest2.ActTypeTestActivity2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_type_test);
		
		ActTypeTestActivity3 = ActTypeTest3.this;
		acttypetest2.finish();
		
		TextView txttest1=(TextView) findViewById(R.id.txttest1);
		String text="3.메이크업이 금방 지워진다.";
		txttest1.setText(text);
		Intent intent=getIntent();
		final ArrayList<Integer> mYesNocount=intent.getIntegerArrayListExtra("count");


		Button testBackbutton=(Button) findViewById(R.id.testbackbtn);
		testBackbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ActTypeTest3.this,ActTypeTest2.class);
				startActivity(intent);
			}
		});
		
		final RadioGroup radiogroup=(RadioGroup) findViewById(R.id.RadioGroup1);
		
		radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					 Intent intent=new Intent(ActTypeTest3.this,ActTypeTest4.class);
				  final RadioButton rb=(RadioButton) findViewById(checkedId);
					    if(rb!=null){
					    	if(checkedId==R.id.yes_radiobtn){ 
					    		mYesNocount.remove(2);
					    		mYesNocount.add(2,1);
					    		Toast.makeText(ActTypeTest3.this,"Yes select", Toast.LENGTH_SHORT).show();
					    	}else if(checkedId==R.id.no_radiobtn){ 
					    		mYesNocount.remove(2);
					    		mYesNocount.add(2,0);
					    		Toast.makeText(ActTypeTest3.this,"no select", Toast.LENGTH_SHORT).show();
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
