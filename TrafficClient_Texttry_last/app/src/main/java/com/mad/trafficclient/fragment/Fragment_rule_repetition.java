/**
 * 
 */
package com.mad.trafficclient.fragment;

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
import com.github.mikephil.charting.components.Legend;
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
import java.util.Iterator;


public class Fragment_rule_repetition extends Fragment
{
	private PieChart pieChart;
	int nn=0;
	HashSet<String> set=new HashSet<>();
	ArrayList<Integer> number=new ArrayList<>();
	ArrayList<String> A_list=new ArrayList<>();
	private UrlBean urlBean;
	//ArrayList<String> N_list=new ArrayList<>();
	private Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			setData((String)msg.obj);

		}
	};

	private void setData(String obj) {
		try {
			JSONArray jsonArray=new JSONArray(obj);
			for (int i=0;i<jsonArray.length();i++){
				String carnum=jsonArray.getJSONObject(i).getString("carnumber");
				set.add(carnum);
				A_list.add(carnum);
			}
			Iterator iterator=set.iterator();
			while (iterator.hasNext()){
				int num=0;
				String it= (String) iterator.next();
				for (int j=0;j<A_list.size();j++){
					if (A_list.get(j).equals(it)){
						num++;
						Log.e("tagnum",num+"");
					}
				}
				number.add(num);
			}
			for (int i=0;i<number.size();i++){
				if (number.get(i)==1){
					nn++;
				}
			}

			setChartView();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void setChartView() {


		ArrayList<String> xValues=new ArrayList<>();
		xValues.add("有重复违章");
		xValues.add("无重复违章");

		ArrayList<Entry> yValues=new ArrayList<>();
		yValues.add(new Entry(number.size()-nn,0));
		yValues.add(new Entry(nn,1));
		Log.e("TAGa",number.size()+"");
		Log.e("TAGa",nn+"");
		/*Log.e("TAGa",A_list.size()-set.size()+"");
		Log.e("TAGa",A_list.size()+"");
		Log.e("TAGa",set.size()+"");
		Log.e("TAGa",set.size()-(A_list.size()-set.size())+"");*/

		ArrayList<Integer> colors=new ArrayList<>();
		colors.add(Color.GRAY);
		colors.add(Color.CYAN);

		Legend legend=pieChart.getLegend();
		legend.setTextSize(40);

		PieDataSet pieDataSet=new PieDataSet(yValues,"");
		pieDataSet.setColors(colors);

		PieData pieData=new PieData(xValues,pieDataSet);
		pieData.setValueTextSize(40);
		pieData.setValueFormatter(new PercentFormatter());

		pieChart.setData(pieData);
		pieChart.setHoleRadius(0);
		pieChart.setTransparentCircleRadius(0);
		pieChart.setUsePercentValues(true);
		pieChart.invalidate();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_rule_repetition, container, false);
		pieChart= (PieChart) view.findViewById(R.id.piechart_rule01);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getData("get_all_car_peccancy");
	}

	private void getData(String url) {
		JSONObject params=new JSONObject();
		try {
			params.put("UserName","user1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		urlBean= Util.loadSetting(getActivity());
		String seturl="http://"+urlBean.getUrl()+":8080/api/v2/"+url;
		Log.e("TAGURL",seturl);
		final RequestQueue mQueue= Volley.newRequestQueue(getActivity());
		JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, seturl, params, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					if (response.getString("RESULT").equals("S")){
						Toast.makeText(getActivity(), "访问成功", Toast.LENGTH_SHORT).show();
						Log.e("TAGRESPONSE",response.toString());
						Message msg=new Message();
						msg.obj=response.getString("ROWS_DETAIL").toString();
						mHandler.sendMessage(msg);
					}else {
						Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_SHORT).show();
			}
		});
		mQueue.add(jsonObjectRequest);

	}
}
