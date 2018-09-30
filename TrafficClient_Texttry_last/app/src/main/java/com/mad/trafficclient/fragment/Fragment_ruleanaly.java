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

/**
 * Created by 123 on 2018/4/26.
 */

public class Fragment_ruleanaly extends Fragment{
    private ViewPager viewPager;
    private ArrayList<Fragment> fra_list=new ArrayList<>();
    private PagerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater
                .inflate(R.layout.fragment_ruleanaly, container, false);
        viewPager= (ViewPager) view.findViewById(R.id.viewpager_rule);
        getDataView();
        mAdapter= new PagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        return view;
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

    private void getDataView() {
        Fragment fragment_rule_yesorno=new Fragment_rule_yesorno();
        Fragment fragment_rule_repetition=new Fragment_rule_repetition();
        Fragment fragment_rule_count=new Fragment_rule_count();
        Fragment fragment_rule_oldoryear01=new Fragment_rule_oldoryear01();
        Fragment fragment_rule_sex=new Fragment_rule_sex();
        Fragment fragment_rule_day=new Fragment_rule_day();
        Fragment fragment_rule_ranking=new Fragment_rule_ranking01();
        fra_list.add(fragment_rule_yesorno);
        fra_list.add(fragment_rule_repetition);
        fra_list.add(fragment_rule_count);
        fra_list.add(fragment_rule_oldoryear01);
        fra_list.add(fragment_rule_sex);
        fra_list.add(fragment_rule_day);
        fra_list.add(fragment_rule_ranking);


    }

}
