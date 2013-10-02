package com.example.skintest;


import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class SelectActivity extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skin_select);
		TabHost mTabHost=getTabHost();
		
		TabSpec spec;
		spec=mTabHost.newTabSpec("tag");
		spec.setIndicator("건성");
		spec.setContent(R.id.dry);
		mTabHost.addTab(spec);
		
		spec=mTabHost.newTabSpec("tag");
		spec.setIndicator("지성");
		spec.setContent(R.id.oily);
		mTabHost.addTab(spec);
		
		spec=mTabHost.newTabSpec("tag");
		spec.setIndicator("복합성");
		spec.setContent(R.id.complexity);
		mTabHost.addTab(spec);
	}
}
