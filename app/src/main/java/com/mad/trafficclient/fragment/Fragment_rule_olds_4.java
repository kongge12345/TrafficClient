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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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


public class Fragment_rule_olds_4 extends Fragment
{
	private static BarChart barChart;
	public static UrlBean urlBean;
	private static ArrayList<Integer> Yrule_5=new ArrayList<>();
	private static ArrayList<Integer> Yrule_6=new ArrayList<>();
	private static ArrayList<Integer> Yrule_7=new ArrayList<>();
	private static ArrayList<Integer> Yrule_8=new ArrayList<>();
	private static ArrayList<Integer> Yrule_9=new ArrayList<>();
	private static ArrayList<Integer> Nrule_5=new ArrayList<>();
	private static ArrayList<Integer> Nrule_6=new ArrayList<>();
	private static ArrayList<Integer> Nrule_7=new ArrayList<>();
	private static ArrayList<Integer> Nrule_8=new ArrayList<>();
	private static ArrayList<Integer> Nrule_9=new ArrayList<>();
	private static HashSet<String> All_carnum=new HashSet<>();
	private static Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what==3){
				setoldsData_all(msg.obj);
				setoldChartView();
			}else if (msg.what==4){
				setoldsData_no(msg.obj);
			}

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_rule_olds, container, false);
		barChart= (BarChart) view.findViewById(R.id.pie_rule_olds);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		allrule(getActivity(),"get_all_car_peccancy",4);
		allrule(getActivity(),"get_car_info",3);



	}

	public static void setoldsData_all(Object obj){

		try {
			JSONArray jsonArray=new JSONArray((String)obj);
			for(int i=0;i<jsonArray.length();i++){
				String pcardid=jsonArray.getJSONObject(i).getString("pcardid");
				int pi_num=Integer.parseInt(pcardid.substring(9,10));
				switch (pi_num){
					case 5:
						Yrule_5.add(pi_num);
					case 6:
						Yrule_6.add(pi_num);
					case 7:
						Yrule_7.add(pi_num);
					case 8:
						Yrule_8.add(pi_num);
					case 9:
						Yrule_9.add(pi_num);
				}
			}
			for (int j=0;j<jsonArray.length();j++){
				String carnum=jsonArray.getJSONObject(j).getString("carnumber");
				Iterator iterator=All_carnum.iterator();
				if (iterator.hasNext()){
					String carnum_set= (String) iterator.next();
					if (carnum==carnum_set){
						String car_num=jsonArray.getJSONObject(j).getString("pcardid");
						int car_num_pi=Integer.parseInt(car_num.substring(9,10));
						Log.e("TAGcar_num_pi",car_num_pi+"");
						switch (car_num_pi){
							case 5:
								Nrule_5.add(car_num_pi);
							case 6:
								Nrule_6.add(car_num_pi);
							case 7:
								Nrule_7.add(car_num_pi);
							case 8:
								Nrule_8.add(car_num_pi);
							case 9:
								Nrule_9.add(car_num_pi);
						}
					}
				}
			}
			Log.e("TAGYRULE5",Yrule_5.size()+"");
			Log.e("TAGYRULE6",Yrule_6.size()+"");
			Log.e("TAGYRULE7",Yrule_7.size()+"");
			Log.e("TAGYRULE8",Yrule_8.size()+"");
			Log.e("TAGYRULE9",Yrule_9.size()+"");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public static void setoldsData_no(Object obj){
		try {
			JSONArray jsonArray=new JSONArray((String)obj);
			for (int i=0;i<jsonArray.length();i++){
				String carnum=jsonArray.getJSONObject(i).getString("carnumber");
				All_carnum.add(carnum);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public static void setoldsData_last(Object obj){
	/*	try {
			JSONArray jsonArray=new JSONArray((String)obj);
			for (int j=0;j<All_carnum.size();j++){
				for (int i=0;i<jsonArray.length();i++){
					String carnum=All_carnum.get(j);
					String pcarid=jsonArray.getJSONObject(i).getString("pcardid");
					int pcarid_one=Integer.parseInt(pcarid.substring(9,10));
					String carnum_no=jsonArray.getJSONObject(i).getString("carnumber");
					if (carnum==carnum_no){
						switch (pcarid_one){
							case 5:
								Nrule_5.add(pcarid_one);
							case 6:
								Nrule_6.add(pcarid_one);
							case 7:
								Nrule_7.add(pcarid_one);
							case 8:
								Nrule_8.add(pcarid_one);
							case 9:
								Nrule_9.add(pcarid_one);
						}
					}
				}
			}

			setoldChartView();
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
	}

	private static void setoldChartView() {
        Log.e("TAGNRULE5",Nrule_5.size()+"");
        Log.e("TAGNRULE6",Nrule_6.size()+"");
        Log.e("TAGNRULE7",Nrule_7.size()+"");
        Log.e("TAGNRULE8",Nrule_8.size()+"");
        Log.e("TAGNRULE9",Nrule_9.size()+"");
        Log.e("TAGYRULE5",Yrule_5.size()+"");
        Log.e("TAGYRULE6",Yrule_6.size()+"");
        Log.e("TAGYRULE7",Yrule_7.size()+"");
        Log.e("TAGYRULE8",Yrule_8.size()+"");
        Log.e("TAGYRULE9",Yrule_9.size()+"");

		XAxis xAxis=barChart.getXAxis();

		YAxis yAxis=barChart.getAxisLeft();

		YAxis yAxis1=barChart.getAxisRight();

		ArrayList<String> xValues=new ArrayList<>();
		xValues.add("50后");
		xValues.add("60后");
		xValues.add("70后");
		xValues.add("80后");
		xValues.add("90后");

		ArrayList<BarEntry> yValues=new ArrayList<>();
		yValues.add(new BarEntry(Nrule_5.size(),Yrule_5.size()-Nrule_5.size(),0));
		yValues.add(new BarEntry(Nrule_6.size(),Yrule_6.size()-Nrule_6.size(),1));
		yValues.add(new BarEntry(Nrule_7.size(),Yrule_7.size()-Nrule_7.size(),2));
		yValues.add(new BarEntry(Nrule_8.size(),Yrule_8.size()-Nrule_8.size(),3));
		yValues.add(new BarEntry(Nrule_9.size(),Yrule_9.size()-Nrule_9.size(),4));

		ArrayList<Integer> colors=new ArrayList<>();
		colors.add(Color.BLUE);
		colors.add(Color.RED);

		BarDataSet barDataSet=new BarDataSet(yValues,"");
		barDataSet.setColors(colors);


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
	public static void allrule01(Context context, String url, final int info){
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
