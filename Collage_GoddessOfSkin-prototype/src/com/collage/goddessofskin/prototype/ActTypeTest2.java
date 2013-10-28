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

public class ActTypeTest2 extends Activity {
	public static Activity ActTypeTestActivity2;
	ActTypeTest acttypetest=(ActTypeTest) ActTypeTest.ActTypeTestActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_type_test);
		
		ActTypeTestActivity2 = ActTypeTest2.this;
		acttypetest.finish();
		
		TextView txttest1=(TextView) findViewById(R.id.txttest1);
		String text="2.모공이 육안으로 정확히 보인다 .";
		txttest1.setText(text);
		Intent intent=getIntent();
		final ArrayList<Integer> mYesNocount=intent.getIntegerArrayListExtra("count");
	

		Button testBackbutton=(Button) findViewById(R.id.testbackbtn);
		testBackbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ActTypeTest2.this,ActTypeTest.class);
				startActivity(intent);
			}
		});
		
		
		final RadioGroup radiogroup=(RadioGroup) findViewById(R.id.RadioGroup1);
		
		radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					 Intent intent=new Intent(ActTypeTest2.this,ActTypeTest3.class);
				  final RadioButton rb=(RadioButton) findViewById(checkedId);
					    if(rb!=null){
					    	if(checkedId==R.id.yes_radiobtn){   
					    		mYesNocount.remove(1);
					    		mYesNocount.add(1,1);
					    		Toast.makeText(ActTypeTest2.this,"Yes select", Toast.LENGTH_SHORT).show();
					    	}else if(checkedId==R.id.no_radiobtn){   
					    		mYesNocount.remove(1);
					    		mYesNocount.add(1,0);
					    		Toast.makeText(ActTypeTest2.this,"no select", Toast.LENGTH_SHORT).show();
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
