package com.collage.goddessofskin.prototype;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.collage.goddessofskin.prototype.defined.Const.SkinType;
import com.collage.goddessofskin.prototype.manager.SharedPreferenceManager;

public class ActTypeTest10 extends Activity {
	ActTypeTest9 acttypetest9=(ActTypeTest9) ActTypeTest9.ActTypeTestActivity9;
	SkinType type;
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
		
		Button testBackbutton=(Button) findViewById(R.id.testbackbtn);
		testBackbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ActTypeTest10.this,ActTypeTest9.class);
				startActivity(intent);
			}
		});
		
		final RadioGroup radiogroup=(RadioGroup) findViewById(R.id.RadioGroup1);
		
		radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					  type = SkinType.Normal;
				  final RadioButton rb=(RadioButton) findViewById(checkedId);
					    if(rb!=null){
					    	if(checkedId==R.id.yes_radiobtn){   
					    		mYesNocount.remove(9);
					    		mYesNocount.add(9,1);
					    		
					    	}else if(checkedId==R.id.no_radiobtn){  
					    		mYesNocount.remove(9);
					    		mYesNocount.add(9,0);
					    		
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
							buttonDialog2();	
							}else if(scount<=6){
							buttonDialog4();
							}else{
							buttonDialog3();
							}
						//SharedPreferenceManager.getInstance(getApplicationContext()).setType(type);
						//Intent intent=new Intent(ActTypeTest10.this,ActMain.class);
						//startActivity(intent);
						
						//finish();
						
				}
			
			}
			
		});
		
		
	}
	/*건성일때*/
	void buttonDialog2(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("피부타입 설정");
		builder.setMessage("테스트 결과 : 당신은 건성입니다.");
		builder.setPositiveButton("피부타입 지정하기", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				type = SkinType.Dry; 
				SharedPreferenceManager.getInstance(getApplicationContext()).setType(type);
				Intent intent=new Intent(ActTypeTest10.this,ActMain.class);
				startActivity(intent);
				
				finish();
				//ActTypeTest10.this.finish();
			}
		});
		builder.setNeutralButton("다시 지정하기", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent=new Intent(ActTypeTest10.this,ActSplash.class);
				startActivity(intent);
				ActTypeTest10.this.finish();
			}
		});
		AlertDialog dialog=builder.create();
		dialog.show();
			
	}
	/*지성일때*/
	void buttonDialog3(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("피부타입 설정");
		builder.setMessage("테스트 결과 : 당신은 지성입니다.");
		builder.setPositiveButton("피부타입 지정하기", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				type = SkinType.Oily; 
				SharedPreferenceManager.getInstance(getApplicationContext()).setType(type);
				Intent intent=new Intent(ActTypeTest10.this,ActMain.class);
				startActivity(intent);
				
				finish();
				//ActTypeTest10.this.finish();
			}
		});
		builder.setNeutralButton("다시 지정하기", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent=new Intent(ActTypeTest10.this,ActSplash.class);
				startActivity(intent);
				ActTypeTest10.this.finish();
			}
		});
		AlertDialog dialog=builder.create();
		dialog.show();
			
	}
	/*복합성일때*/
	void buttonDialog4(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("피부타입 설정");
		builder.setMessage("테스트 결과 : 당신은 복합성입니다.");
		builder.setPositiveButton("피부타입 지정하기", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				type = SkinType.Combimation; 
				SharedPreferenceManager.getInstance(getApplicationContext()).setType(type);
				Intent intent=new Intent(ActTypeTest10.this,ActMain.class);
				startActivity(intent);
				
				finish();
				//ActTypeTest10.this.finish();
			}
		});
		builder.setNeutralButton("다시 지정하기", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent=new Intent(ActTypeTest10.this,ActSplash.class);
				startActivity(intent);
				ActTypeTest10.this.finish();
			}
		});
		AlertDialog dialog=builder.create();
		dialog.show();
			
	}
		
}


