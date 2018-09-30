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
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.mad.trafficclient.R;
import com.mad.trafficclient.util.UrlBean;
import com.mad.trafficclient.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;


public class Fragment_rule_yesorno_1 extends Fragment
{
	public static HashSet<String> breakRules_nums=new HashSet<>();
	//public static LinkedList<String> breakRulesList_nums=new LinkedList<>();
	public static LinkedList<String> allCar_nums=new LinkedList<>();
	public static PieChart pieChart;
	public static UrlBean urlBean;
	public static Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what==1){
				setYesorNoData_01(msg.obj);
			}else if (msg.what==2){
				setYesorNoData_02(msg.obj);
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_rule_yesorno, container, false);
		pieChart= (PieChart) view.findViewById(R.id.pie_rule_yesorno);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		allrule(getActivity(),"get_all_car_peccancy",1);
		allrule(getActivity(),"get_car_info",2);
	}

	public static void setYesorNoData_01(Object obj) {

		try {
			JSONArray jsonArray=new JSONArray(String.valueOf(obj));
			for (int i=0;i<jsonArray.length();i++){
				String breakRule_num=jsonArray.getJSONObject(i).getString("carnumber");
				//breakRulesList_nums.add(breakRule_num);
				breakRules_nums.add(breakRule_num);
			}
			Fragment_rule_yesorno_1.setChartView();

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static void setYesorNoData_02(Object obj) {
		try {
			JSONArray jsonArray=new JSONArray(String.valueOf(obj));
			for (int i=0;i<jsonArray.length();i++){
				String allcar_num=jsonArray.getJSONObject(i).getString("carnumber");
				allCar_nums.add(allcar_num);

			}
			setChartView();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static void setChartView() {

		ArrayList<String> xValues=new ArrayList<>();
		xValues.add("有违章");
		xValues.add("无违章");

		ArrayList<Integer> colors=new ArrayList<>();
		colors.add(Color.RED);
		colors.add(Color.BLUE);

		ArrayList<Entry> yValues=new ArrayList<>();
		yValues.add(new Entry(breakRules_nums.size(),0));
		yValues.add(new Entry(allCar_nums.size()-breakRules_nums.size(),1));

		PieDataSet pieDataSet=new PieDataSet(yValues,"");
		pieDataSet.setValueTextSize(19);
		pieDataSet.setColors(colors);
		pieDataSet.setValueFormatter(new PercentFormatter());
		PieData pieData=new PieData(xValues,pieDataSet);
		pieChart.setData(pieData);
		pieChart.setHoleRadius(0);
		pieChart.setUsePercentValues(true);
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
