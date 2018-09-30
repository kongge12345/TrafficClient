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
import android.widget.TextView;
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
import com.mad.trafficclient.R;
import com.mad.trafficclient.util.UrlBean;
import com.mad.trafficclient.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_lift_together extends Fragment
{
	private PieChart pieChart;
	private RequestQueue mQueue;
	private UrlBean urlBean;
	private TextView textView;
	private Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			setData((String)msg.obj);
		}
	};

	private void setData(String obj) {
		try {
			JSONObject jsonObject=new JSONObject(obj);
			int pm=Integer.parseInt(jsonObject.getString("pm2.5"));
			int light=Integer.parseInt(jsonObject.getString("LightIntensity"));
			int temp=Integer.parseInt(jsonObject.getString("temperature"));
			int humi=Integer.parseInt(jsonObject.getString("humidity"));
			int co=Integer.parseInt(jsonObject.getString("co2"));

			if (pm>200){

			}else {
				setChartView(pm,light,temp,humi,co);
			}


		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void setChartView(int pm, int light, int temp, int humi, int co) {

		ArrayList<String> xValues=new ArrayList<>();
		xValues.add("pm2.5");
		xValues.add("光照");
		xValues.add("温度");
		xValues.add("湿度");
		xValues.add("co2");


		ArrayList<Entry> yValues=new ArrayList<>();
		yValues.add(new Entry(pm,0));
		yValues.add(new Entry(light,0));
		yValues.add(new Entry(temp,0));
		yValues.add(new Entry(humi,0));
		yValues.add(new Entry(co,0));

		ArrayList<Integer> colors=new ArrayList<>();
		colors.add(Color.CYAN);
		colors.add(Color.GRAY);
		colors.add(Color.DKGRAY);
		colors.add(Color.MAGENTA);
		colors.add(Color.LTGRAY);

		Legend legend=pieChart.getLegend();
		legend.setTextSize(26);
		legend.setFormSize(26);
		legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);

		PieDataSet pieDataSet=new PieDataSet(yValues,"");
		pieDataSet.setColors(colors);

		PieData pieData=new PieData(xValues,pieDataSet);
		pieData.setValueTextSize(26);

		pieChart.setTransparentCircleRadius(0);
		pieChart.setHoleRadius(0);
		pieChart.setData(pieData);
		pieChart.invalidate();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_lift, container, false);

		pieChart= (PieChart) view.findViewById(R.id.piechart_life);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Runnable runnable=new Runnable() {
			@Override
			public void run() {
				if (getActivity()!=null){
					mQueue=Volley.newRequestQueue(getActivity());
					getData(mQueue);
				}
				mHandler.postDelayed(this,3000);
			}
		};
		mHandler.postDelayed(runnable,200);


	}

	private void getData(final RequestQueue mQueue) {
		JSONObject params=new JSONObject();
		try {
			params.put("UserName","user1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		urlBean= Util.loadSetting(getActivity());
		String seturl="http://"+urlBean.getUrl()+":8080/api/v2/get_all_sense";
		Log.e("TAGURL",seturl);
		if (getActivity()!=null){
			//final RequestQueue mQueue= Volley.newRequestQueue(getActivity());
			JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, seturl, params, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					try {
						if (response.getString("RESULT").equals("S")){
						//	Toast.makeText(getActivity(), "访问成功", Toast.LENGTH_SHORT).show();
							Log.e("TAGRESPONSE",response.toString());
							Message msg=new Message();
							msg.obj=response.toString();
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
}
