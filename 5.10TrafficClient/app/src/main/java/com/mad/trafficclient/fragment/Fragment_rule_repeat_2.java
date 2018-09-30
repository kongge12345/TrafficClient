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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
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


public class Fragment_rule_repeat_2 extends Fragment
{
	public static PieChart pieChart;
	public static UrlBean urlBean;
	public static HashSet<String> breakRules_nums=new HashSet<>();
	public static LinkedList<String> breakRulesList_nums=new LinkedList<>();
	public static ArrayList<Integer> numbers=new ArrayList<>();
	public static ArrayList<Integer> noRepeat=new ArrayList<>();

	public static Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1){
				setRepeatData(msg.obj);
			}else if (msg.what==2){

			}
		}
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_rule_repeat, container, false);
		pieChart= (PieChart) view.findViewById(R.id.pie_rule_repeat);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		allrule(getActivity(),"get_all_car_peccancy",1);
		//Fragment_rule_volly.setTimeData();
		//Fragment_rule_volly.allrule(getActivity(),"get_car_info",3);
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

		Iterator iterator= Fragment_rule_yesorno_1.breakRules_nums.iterator();
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
		setRepeatChartView(noRepeat.size(),numbers.size()-noRepeat.size());
	}

	public static void setRepeatChartView(int noRepeat, int Repeat) {
		Log.e("TAG",noRepeat+"");
		Log.e("TAG",Repeat+"");
		ArrayList<String> xValues=new ArrayList<>();
		xValues.add("无重复违章");
		xValues.add("有重复违章");

		ArrayList<Entry> yValues=new ArrayList<>();
		yValues.add(new Entry(noRepeat,0));
		yValues.add(new Entry(Repeat,1));

		ArrayList<Integer> colors=new ArrayList<>();
		colors.add(Color.RED);
		colors.add(Color.BLUE);

		PieDataSet pieDataSet=new PieDataSet(yValues,"");
		pieDataSet.setColors(colors);
		pieDataSet.setValueTextSize(19);
		pieDataSet.setSliceSpace(0);

		PieData pieData=new PieData(xValues,pieDataSet);
		pieChart.setData(pieData);
		pieChart.setUsePercentValues(true);
		pieChart.setHoleRadius(0);
		pieChart.setTransparentCircleRadius(0);
		pieChart.invalidate();
	}

	public static void allrule(final Context context, String url, final int info){
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
						Toast.makeText(context, "访问成功", Toast.LENGTH_SHORT).show();
						String rows=response.getString("ROWS_DETAIL");
						Message msg=new Message();
						msg.obj=rows;
						msg.what=info;
						mHandler.sendMessage(msg);
					}else {
						Toast.makeText(context, "访问成功", Toast.LENGTH_SHORT).show();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				Toast.makeText(context, volleyError.toString(), Toast.LENGTH_SHORT).show();
			}
		});
		mQueue.add(jsonObjectRequest);

	}
}
