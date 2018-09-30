/**
 * 
 */
package com.mad.trafficclient.fragment;

import com.mad.trafficclient.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class Fragment_rule_change extends Fragment
{

	private ViewPager viewPager;
	private ArrayList<Fragment> fra_list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_rule_change, container, false);
		viewPager= (ViewPager) view.findViewById(R.id.vp_rule_count);
		PagerAdapter pagerAdapter=new PagerAdapter(getChildFragmentManager());
		setfraView();
		viewPager.setAdapter(pagerAdapter);
		return view;
	}

	private void setfraView() {
		fra_list=new ArrayList<>();
		Fragment_rule_yesorno_1 fragment_rule_yesorno=new Fragment_rule_yesorno_1();
		Fragment_rule_repeat_2 fragment_rule_repeat=new Fragment_rule_repeat_2();
		Fragment_rule_time_3 fragment_rule_time=new Fragment_rule_time_3();
		Fragment_rule_olds_4 fragment_rule_olds=new Fragment_rule_olds_4();
		Fragment_rule_sex_5 fragment_rule_sex=new Fragment_rule_sex_5();
		Fragment_rule_duration_6 fragment_rule_duration=new Fragment_rule_duration_6();
		Fragment_rule_traffic_7 fragment_rule_traffic=new Fragment_rule_traffic_7();
		fra_list.add(fragment_rule_yesorno);
		fra_list.add(fragment_rule_repeat);
		fra_list.add(fragment_rule_time);
		fra_list.add(fragment_rule_olds);
		fra_list.add(fragment_rule_sex);
		fra_list.add(fragment_rule_duration);
		fra_list.add(fragment_rule_traffic);
	}

	class PagerAdapter extends FragmentPagerAdapter {

		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fra_list.get(position);
		}

		@Override
		public int getCount() {
			return fra_list.size();
		}
	}

}
