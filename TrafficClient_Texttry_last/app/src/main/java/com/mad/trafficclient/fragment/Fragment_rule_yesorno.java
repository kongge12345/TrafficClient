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
import android.widget.Button;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.mad.trafficclient.R;
import com.mad.trafficclient.util.UrlBean;
import com.mad.trafficclient.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;


public class Fragment_rule_yesorno extends Fragment
{
	private PieChart pieChart;
	private Button button;
	private int Arule,Nrule;
	private UrlBean urlBean;

	private Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what==1){
				Arule=setData((String)msg.obj);
			}else {
				Nrule=setData((String)msg.obj);
			}
			setChartView(Arule,Nrule);
		}
	};

	private void setChartView(int arule, int nrule) {
		ArrayList<String> xValues=new ArrayList<>();
		xValues.add("有违章的车辆");
		xValues.add("无违章的车辆");

		ArrayList<Entry> yValues=new ArrayList<>();
		yValues.add(new Entry(Nrule,0));
		yValues.add(new Entry(Arule-Nrule,1));

		ArrayList<Integer> colors=new ArrayList<>();
		colors.add(Color.RED);
		colors.add(Color.YELLOW);

		PieDataSet pieDataSet=new PieDataSet(yValues,"");
		pieDataSet.setColors(colors);

		Legend legend=pieChart.getLegend();

		PieData pieData=new PieData(xValues,pieDataSet);
		//pieChart.animateXY(2000,2000);
		pieChart.setTransparentCircleRadius(0f);
		pieChart.setHoleRadius(0f);
		pieData.setValueTextSize(20f);
		pieChart.setUsePercentValues(true);
		pieData.setValueFormatter(new PercentFormatter());
		pieChart.setData(pieData);
		pieChart.setDragDecelerationEnabled(false);
		pieChart.setTouchEnabled(false);
		pieChart.invalidate();
		/*pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
			@Override
			public void onValueSelected(Entry entry, int i, Highlight highlight) {
				if (pieChart.getData()!=null){
					pieChart.getData().setHighlightEnabled(!pieChart.getData().isHighlightEnabled());
					pieChart.invalidate();
				}
			}

			@Override
			public void onNothingSelected() {

			}
		});*/
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_rule_yesorno, container, false);
		pieChart= (PieChart) view.findViewById(R.id.pc_rule);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getData("get_car_info",1);
		getData("get_all_car_peccancy",2);
	}

	private void getData(String string, final int info) {
		JSONObject params=new JSONObject();
		try {
			params.put("UserName","user1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		urlBean= Util.loadSetting(getActivity());
		String seturl="http://"+urlBean.getUrl()+":8080/api/v2/";
		String url=seturl+string;
		Log.e("TAGURL",url);

		RequestQueue mQueue= Volley.newRequestQueue(getActivity());
		JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.e("TAGRESPONSE",response.toString());
				if (response.optString("RESULT").equals("S")){
					Toast.makeText(getActivity(),"访问成功",Toast.LENGTH_SHORT).show();
					try {
						String main=response.getString("ROWS_DETAIL");
						Message msg=new Message();
						msg.obj=main;
						msg.what=info;
						mHandler.sendMessage(msg);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else {
					Toast.makeText(getActivity(),"成功",Toast.LENGTH_SHORT).show();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				Toast.makeText(getActivity(),volleyError.toString(),Toast.LENGTH_SHORT).show();
			}
		});
		mQueue.add(jsonObjectRequest);
	}

	private int setData(String main) {
		HashSet<String> list_car=new HashSet<>();
		int number=0;
		try {
			JSONArray jsonArray=new JSONArray(main);
			for (int i=0;i<jsonArray.length();i++){
				list_car.add(jsonArray.getJSONObject(i).getString("carnumber"));
			}
			number=list_car.size();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return number;
	}
}
