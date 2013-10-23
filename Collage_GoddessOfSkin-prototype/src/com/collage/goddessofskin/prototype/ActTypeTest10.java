package com.collage.goddessofskin.prototype;

import java.util.ArrayList;

import com.collage.goddessofskin.prototype.defined.Const.SkinType;
import com.collage.goddessofskin.prototype.manager.SharedPreferenceManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ActTypeTest10 extends Activity {
	ActTypeTest9 acttypetest9=(ActTypeTest9) ActTypeTest9.ActTypeTestActivity9;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_type_test);
		
		acttypetest9.finish();
		
		TextView txttest1=(TextView) findViewById(R.id.txttest1);
		String text="10.기름종이를 하루에 3번 이상 사용한다.";
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
					 SkinType type = SkinType.Normal;
				  final RadioButton rb=(RadioButton) findViewById(checkedId);
					    if(rb!=null){
					    	if(checkedId==R.id.yes_radiobtn){   
					    		mYesNocount.add(9,1);
					    		Toast.makeText(ActTypeTest10.this,"Yes select", Toast.LENGTH_SHORT).show();
					    	}else if(checkedId==R.id.no_radiobtn){   
					    		mYesNocount.add(9,0);
					    		Toast.makeText(ActTypeTest10.this,"no select", Toast.LENGTH_SHORT).show();
					    	}
					    	
					    	
					}else{
				
					}
					    
					    int mcount=mYesNocount.size();
						int scount=0;
						for(int i=0;i<mcount;i++){
							if(mYesNocount.get(i)==1)
								scount++;
						}
						if(scount<=3){
							type = SkinType.Dry; 		
							}else if(scount<=6){
							type = SkinType.Combimation;
							}else{
							type = SkinType.Oily;	
							}
						SharedPreferenceManager.getInstance(getApplicationContext()).setType(type);
						Intent intent=new Intent(ActTypeTest10.this,ActMain.class);
						startActivity(intent);
						
						finish();
						
				}
			
			}
			
		});
		
		
	}
}

