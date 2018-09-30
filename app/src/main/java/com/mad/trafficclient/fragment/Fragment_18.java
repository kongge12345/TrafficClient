/**
 * 
 */
package com.mad.trafficclient.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.trafficclient.R;


public class Fragment_18 extends Fragment implements View.OnClickListener
{
    TextView tvSkipQuery, tvSkpAnalyze;
	@SuppressLint("ResourceAsColor")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_18, container, false);
		tvSkipQuery  = (TextView) view.findViewById(R.id.tv_skip_query);
		tvSkpAnalyze = (TextView) view.findViewById(R.id.tv_skip_analyze);
		tvSkpAnalyze.setOnClickListener(this);
		tvSkipQuery.setOnClickListener(this);
		tvSkipQuery.setBackgroundColor(R.color.light_gray);
		getChildFragmentManager().beginTransaction().replace(R.id.rl_content,
				new Fragment_MyMessage()).commit();

		return view;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View view) {
		if (view == tvSkipQuery){
			tvSkipQuery.setBackgroundColor(R.color.light_gray);
			tvSkpAnalyze.setBackgroundResource(android.R.color.white);
			getChildFragmentManager().beginTransaction().replace(R.id.rl_content,
					new Fragment_MyMessage()).commit();
		}else {
			tvSkpAnalyze.setBackgroundColor(R.color.light_gray);
			tvSkipQuery.setBackgroundResource(android.R.color.white);
			getChildFragmentManager().beginTransaction().replace(R.id.rl_content,
					new Fragment_lift_together()).commit();
		}
	}
}
