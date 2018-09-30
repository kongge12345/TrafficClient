/**
 * 
 */
package com.mad.trafficclient.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mad.trafficclient.R;

import java.util.ArrayList;


public class Fragment_now_envir extends Fragment
{
	private ArrayList<Fragment> fra_list=new ArrayList<>();
	private ViewPager viewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_now_envir, container, false);
		viewPager= (ViewPager) view.findViewById(R.id.now_viewpager);
		getfraView();
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
			return fra_list.get(position);
		}

		@Override
		public int getCount() {
			return fra_list.size();
		}
	}

	private void getfraView() {
		Fragment_nowpm fragment_nowpm=new Fragment_nowpm();
		Fragment_nowtemp fragment_nowtemp=new Fragment_nowtemp();
		Fragment_nowlight fragment_nowlight=new Fragment_nowlight();
		fra_list.add(fragment_nowpm);
		fra_list.add(fragment_nowtemp);
		fra_list.add(fragment_nowlight);

	}

}
