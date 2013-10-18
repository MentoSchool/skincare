package com.collage.goddessofskin.prototype;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ActTypeTest6 extends Activity {
	public static Activity ActTypeTestActivity6;
	ActTypeTest5 acttypetest5=(ActTypeTest5) ActTypeTest5.ActTypeTestActivity5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_type_test);
		
		ActTypeTestActivity6 = ActTypeTest6.this;
		acttypetest5.finish();
		
		TextView txttest1=(TextView) findViewById(R.id.txttest1);
		String text="6.���ֺ��� �?��尡 �ټ� �ִ�.";
		txttest1.setText(text);
		Intent intent=getIntent();
		final ArrayList<Integer> mYesNocount=intent.getIntegerArrayListExtra("count");
		/*if(mYesNocount.get(1)!=null){
			mYesNocount.remove(1);
		}*/
		//back��ư ������ ��,
		/*Button testBackbutton=(Button) findViewById(R.id.testbackbtn);
		testBackbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			
			this.setResult(RESULT_OK);
			finish();
				
			}
		});
		*/
		
		final RadioGroup radiogroup=(RadioGroup) findViewById(R.id.RadioGroup1);
		
		radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					 Intent intent=new Intent(ActTypeTest6.this,ActTypeTest7.class);
				  final RadioButton rb=(RadioButton) findViewById(checkedId);
					    if(rb!=null){
					    	if(checkedId==R.id.yes_radiobtn){   
					    		mYesNocount.add(5,1);
					    		Toast.makeText(ActTypeTest6.this,"Yes select"+mYesNocount.size(), Toast.LENGTH_SHORT).show();
					    	}else if(checkedId==R.id.no_radiobtn){   
					    		mYesNocount.add(5,0);
					    		Toast.makeText(ActTypeTest6.this,"no select", Toast.LENGTH_SHORT).show();
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
