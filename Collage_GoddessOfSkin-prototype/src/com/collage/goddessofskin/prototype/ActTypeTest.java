package com.collage.goddessofskin.prototype;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ActTypeTest extends Activity{
	public static Activity ActTypeTestActivity;
	public static final int REQUEST_TEXT=1;

@Override

protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.act_type_test);
	
	ActTypeTestActivity = ActTypeTest.this;
	
	final RadioGroup radiogroup=(RadioGroup) findViewById(R.id.RadioGroup1);
	
	
	radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
		ArrayList<Integer> mYesNocount=new ArrayList<Integer>();
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if(checkedId!=-1){
				 Intent intent=new Intent(ActTypeTest.this,ActTypeTest2.class);
			  final RadioButton rb=(RadioButton) findViewById(checkedId);
				    if(rb!=null){
				    	if(checkedId==R.id.yes_radiobtn){
				    		mYesNocount.add(0,1);
				    		Toast.makeText(ActTypeTest.this,"Yes select", Toast.LENGTH_SHORT).show();
				    	}else if(checkedId==R.id.no_radiobtn){   
				    		mYesNocount.add(0,0);
				    		Toast.makeText(ActTypeTest.this,"no select", Toast.LENGTH_SHORT).show();
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


