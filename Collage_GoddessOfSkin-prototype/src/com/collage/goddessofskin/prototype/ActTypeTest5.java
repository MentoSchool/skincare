package com.collage.goddessofskin.prototype;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ActTypeTest5 extends Activity {
	public static Activity ActTypeTestActivity5;
	ActTypeTest4 acttypetest4=(ActTypeTest4) ActTypeTest4.ActTypeTestActivity4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_type_test);
		
		ActTypeTestActivity5 = ActTypeTest5.this;
		acttypetest4.finish();
		
		TextView txttest1=(TextView) findViewById(R.id.txttest1);
		String text="5.화장품 형태는 촉촉한것 보다는 산뜻한 젤 타입이 좋다.";
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
					 Intent intent=new Intent(ActTypeTest5.this,ActTypeTest6.class);
				  final RadioButton rb=(RadioButton) findViewById(checkedId);
					    if(rb!=null){
					    	if(checkedId==R.id.yes_radiobtn){   
					    		mYesNocount.add(4,1);
					    		Toast.makeText(ActTypeTest5.this,"Yes select", Toast.LENGTH_SHORT).show();
					    	}else if(checkedId==R.id.no_radiobtn){   
					    		mYesNocount.add(4,0);
					    		Toast.makeText(ActTypeTest5.this,"no select", Toast.LENGTH_SHORT).show();
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
