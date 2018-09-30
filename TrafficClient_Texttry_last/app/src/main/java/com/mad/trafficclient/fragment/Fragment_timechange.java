/**
 * 
 */
package com.mad.trafficclient.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.trafficclient.R;
import com.mad.trafficclient.util.UrlBean;

import java.util.ArrayList;


public class Fragment_timechange extends Fragment
{
	private ViewPager viewPager;
	private ArrayList<Fragment> list=new ArrayList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_timechange, container, false);
		viewPager= (ViewPager) view.findViewById(R.id.viewpager_now);
		getFragmentView();

		PagerAdapter pagerAdapter=new PagerAdapter(getChildFragmentManager());

		viewPager.setAdapter(pagerAdapter);

		return view;
	}

	class PagerAdapter extends FragmentPagerAdapter{

		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return list.get(position);
		}

		@Override
		public int getCount() {
			return list.size();
		}
	}

	private void getFragmentView() {
		Fragment_timetemp fragment_timetemp =new Fragment_timetemp();
		Fragment_timehumi fragment_timehumi =new Fragment_timehumi();
		Fragment_timepm fragment_timepm =new Fragment_timepm();
		Fragment_timeco fragment_timeco =new Fragment_timeco();
		Fragment_timelight fragment_timelight =new Fragment_timelight();
		Fragment_timestatus fragment_timestatus =new Fragment_timestatus();
		list.add(fragment_timetemp);
		list.add(fragment_timehumi);
		list.add(fragment_timepm);
		list.add(fragment_timeco);
		list.add(fragment_timelight);
		list.add(fragment_timestatus);

	}

}
