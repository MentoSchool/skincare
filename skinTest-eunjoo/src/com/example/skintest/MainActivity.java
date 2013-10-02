package com.example.skintest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button testbtn=(Button) findViewById(R.id.testBtn);
    	Button selectbtn=(Button)findViewById(R.id.selectBtn);
       
        testbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,TestActivity.class);
				startActivity(intent);
				
			}
		});
        selectbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,SelectActivity.class);
				startActivity(intent);
				
			}
		});
    }

    
}
