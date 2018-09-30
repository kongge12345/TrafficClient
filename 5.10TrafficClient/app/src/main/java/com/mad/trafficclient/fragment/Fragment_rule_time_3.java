/**
 * 
 */
package com.mad.trafficclient.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.mad.trafficclient.R;
import com.mad.trafficclient.util.UrlBean;
import com.mad.trafficclient.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;


public class Fragment_rule_time_3 extends Fragment
{
	public static HorizontalBarChart barChart;
	public static ArrayList<Integer> big=new ArrayList<>();
	public static ArrayList<Integer> big_s=new ArrayList<>();
	public static ArrayList<Integer> big_ss=new ArrayList<>();
	public static ArrayList<Integer> noRepeat=new ArrayList<>();
	public static UrlBean urlBean;
	public static HashSet<String> breakRules_nums=new HashSet<>();
	public static LinkedList<String> breakRulesList_nums=new LinkedList<>();
	public static ArrayList<Integer> numbers=new ArrayList<>();
	public static Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what==1){
				setRepeatData(msg.obj);
			}
		}
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_rule_time, container, false);
		barChart= (HorizontalBarChart) view.findViewById(R.id.pie_rule_time);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		allrule(getActivity(),"get_all_car_peccancy",1);
	}

	public static void setRepeatData(Object obj) {
		try {
			JSONArray jsonArray=new JSONArray(String.valueOf(obj));
			for (int i=0;i<jsonArray.length();i++){
				String breakRule_num=jsonArray.getJSONObject(i).getString("carnumber");
				breakRulesList_nums.add(breakRule_num);
				breakRules_nums.add(breakRule_num);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Iterator iterator= breakRules_nums.iterator();
		//Log.e("TAGBREAK",iterator.hasNext()+"");
		while (iterator.hasNext()){
			int number=0;
			String breakRules_num= (String) iterator.next();
			for (int i = 0; i< breakRulesList_nums.size(); i++){
				String breakRulesList_num= breakRulesList_nums.get(i);
				//Log.e("TAGBREAK",breakRulesList_num);
				if (breakRules_num.equals(breakRulesList_num)){

					number++;
				}
			}
			numbers.add(number);

		}

		//Log.e("TAGNUMBER",numbers.toString());

		for (int i=0;i<numbers.size();i++){
			int num=numbers.get(i);
			if (num==1){
				noRepeat.add(num);
			}
		}
		setTimeData();

	}

	public static void setTimeData() {
		Log.e("TAGtIME", numbers.toString());
		for (int i = 0; i< numbers.size(); i++){
			int num= numbers.get(i);
			if (num<3){
				;big_ss.add(num);
			}else if (num<5){
				big_s.add(num);
			}else {
				big.add(num);
			}
		}
		Log.e("TAGtime",big+"");
		Log.e("TAGtime_s",big_s+"");
		Log.e("TAGtime_ss",big_ss+"");
		setTimeChartView();
	}

	public static void setTimeChartView(){
		XAxis xAxis=barChart.getXAxis();
		xAxis.setTextSize(19);

		YAxis yAxis=barChart.getAxisLeft();
		yAxis.setEnabled(true);

		YAxis yAxis1=barChart.getAxisRight();
		yAxis1.setTextSize(19);

		ArrayList<String> xValues=new ArrayList<>();
		xValues.add("5条以上违章");
		xValues.add("3-5条违章");
		xValues.add("1-2条违章");

		ArrayList<BarEntry> yValues=new ArrayList<>();
		yValues.add(new BarEntry(big.size(),0));
		yValues.add(new BarEntry(big_s.size(),1));
		yValues.add(new BarEntry(big_ss.size(),2));

		ArrayList<Integer> colors=new ArrayList<>();
		colors.add(Color.RED);
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);

		BarDataSet barDataSet=new BarDataSet(yValues,"");
		barDataSet.setColors(colors);
		barDataSet.setValueFormatter(new PercentFormatter());
		BarData barData=new BarData(xValues,barDataSet);
		barChart.setData(barData);
		barChart.invalidate();
	}

	public static void allrule(Context context, String url, final int info){
		JSONObject params=new JSONObject();
		try {
			params.put("UserName","user1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		urlBean= Util.loadSetting(context);
		String seturl="http://"+urlBean.getUrl()+":8080/api/v2/"+url;
		RequestQueue mQueue= Volley.newRequestQueue(context);
		JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, seturl, params, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					if (response.getString("RESULT").equals("S")){
						String rows=response.getString("ROWS_DETAIL");
						Message msg=new Message();
						msg.obj=rows;
						msg.what=info;
						mHandler.sendMessage(msg);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {

			}
		});
		mQueue.add(jsonObjectRequest);

	}

}
