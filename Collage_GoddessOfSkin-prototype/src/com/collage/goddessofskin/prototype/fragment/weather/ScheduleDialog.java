package com.collage.goddessofskin.prototype.fragment.weather;

import com.collage.goddessofskin.prototype.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import android.view.LayoutInflater;

public class ScheduleDialog extends DialogFragment {

	public ScheduleDialog() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		 AlertDialog.Builder mBuilder = new AlertDialog.Builder(
					 getActivity());
					 LayoutInflater mLayoutInflater =
					 getActivity().getLayoutInflater();
					 
					 mBuilder.setView(mLayoutInflater
					 .inflate(R.layout.frag_weather_today_scheduledialog, null));
					 mBuilder.setTitle("SCHEDUKE 자외선 차단제 TIME");
					 mBuilder.setMessage("UV 지수를 선택해 주세요.");
					 return mBuilder.create();
					// the content
}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
   
	
	
	
	
}
