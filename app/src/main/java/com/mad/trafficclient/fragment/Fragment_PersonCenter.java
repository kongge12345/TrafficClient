/**
 * 
 */
package com.mad.trafficclient.fragment;

import com.mad.trafficclient.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//个人中心
//需要用到的接口：2.9.2获取所有用户信息
public class Fragment_PersonCenter extends Fragment
{

	public static String urlAllPeople = "http://192.168.3.32:8080/api/v2/get_all_user_info";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_layout02, container, false);

		return view;
	}

}
