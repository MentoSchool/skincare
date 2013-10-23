package com.collage.goddessofskin.prototype.fragment;

import com.collage.goddessofskin.prototype.R;
import com.collage.goddessofskin.prototype.utils.custom.CustemGallery;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragMain_Pack extends Fragment {

	private CustemGallery FunSub;
	
	private FragMain_Pack_CustemCalleryAdapter fragMain_Pack_CustemCalleryAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.frag_main_pak, container, false);

		init(view);
		
		
		return view;

	}

	private void init(View view) {
		// TODO Auto-generated method stub
		
		FunSub = (CustemGallery)view.findViewById(R.id.FragMain_Pack);
		
		fragMain_Pack_CustemCalleryAdapter = new FragMain_Pack_CustemCalleryAdapter(getActivity());
		
		FunSub.setAdapter(fragMain_Pack_CustemCalleryAdapter);
		
		
	}

}
