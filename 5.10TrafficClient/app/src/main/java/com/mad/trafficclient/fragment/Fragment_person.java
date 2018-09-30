/**
 * 
 */
package com.mad.trafficclient.fragment;

import com.mad.trafficclient.R;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//个人中心的个人信息界面
public class Fragment_person extends Fragment implements View.OnClickListener
{
	TextView tvPerson, tvRecord, tvWarming;

	@Override
	//个人中心
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate( R.layout.fragment_person, container, false);
		tvPerson = (TextView) view.findViewById(R.id.tv_person);
		tvRecord = (TextView) view.findViewById(R.id.tv_pay_record);
		tvWarming = (TextView) view.findViewById(R.id.tv_warming);
		tvWarming.setOnClickListener(this);
		tvRecord.setOnClickListener(this);
		tvPerson.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View view) {
		if (view == tvPerson ){
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontent,
					new Fragment_person()).commit();
		}
		if (view == tvRecord){
			//跳转充值记录
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontent,
					new Fragment_order()).commit();
		}
		;if (view == tvWarming){
			//跳转阈值警告
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontent,
					new Fragment_yuzhiset()).commit();
		}
	}
}
