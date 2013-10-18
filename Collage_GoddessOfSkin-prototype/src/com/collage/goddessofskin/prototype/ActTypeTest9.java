package com.collage.goddessofskin.prototype;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
		setContentView(R.layout.act_type_test);
		
		ActTypeTestActivity9 = ActTypeTest9.this;
		acttypetest8.finish();
		
		TextView txttest1=(TextView) findViewById(R.id.txttest1);
		String text="9.���� �� ���ʰ� ���� �ʴ��� �ݹ� �⸧�Ⱑ �ȴ�.";
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
					 Intent intent=new Intent(ActTypeTest9.this,ActTypeTest10.class);
				  final RadioButton rb=(RadioButton) findViewById(checkedId);
					    if(rb!=null){
					    	if(checkedId==R.id.yes_radiobtn){   
					    		mYesNocount.add(8,1);
					    		Toast.makeText(ActTypeTest9.this,"Yes select"+mYesNocount.size(), Toast.LENGTH_SHORT).show();
					    	}else if(checkedId==R.id.no_radiobtn){   
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