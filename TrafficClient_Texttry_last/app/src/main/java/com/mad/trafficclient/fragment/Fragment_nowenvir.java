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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.mad.trafficclient.R;
import com.mad.trafficclient.util.UrlBean;
import com.mad.trafficclient.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Fragment_nowenvir extends Fragment
{
	private TextView Ttemp_tv,Ntemp_tv;
	private LineChart lineChart;
	private ImageView imageView;
	private ArrayList<Integer> max;
	private ArrayList<Integer> min;
	private UrlBean urlBean;
	private Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			setChartView();
		}
	};

	private void setChartView() {
		XAxis xAxis=lineChart.getXAxis();
		xAxis.setTextSize(30);

		//setLabelRotationAngle
		YAxis leftAxis=lineChart.getAxisLeft();
		leftAxis.removeAllLimitLines();
		/*
			leftAxis.setAxisMaxValue(200f);//最大值
			leftAxis.setAxisMinValue(10f); //最小值
		*/
		Legend l=lineChart.getLegend();
		l.setFormSize(30);


		ArrayList<String> xValues=new ArrayList<>();
		xValues.add("昨天");
		xValues.add("今天");
		xValues.add("明天");
		xValues.add("周一");
		xValues.add("周二");
		xValues.add("周三");
		xValues.add("周四");

		ArrayList<Entry> yValues_max=new ArrayList<>();
		for (int i=0;i<max.size();i++){
			yValues_max.add(new Entry(max.get(i),i));
		}

		ArrayList<Entry> yValues_min=new ArrayList<>();
		for (int i=0;i<min.size();i++){
			Entry entry=new Entry(min.get(i),i);
			yValues_min.add(new Entry(min.get(i),i));
		}

		ArrayList<LineDataSet> lineDataSets=new ArrayList<>();
		LineDataSet lineDataSet_max=new LineDataSet(yValues_max,"最大值折线图");
		//lineDataSet_max.setValueFormatter(new PercentFormatter());//设置为百分比表示
		lineDataSet_max.setDrawCircleHole(false);//是否显示圆点

		LineDataSet lineDataSet_min=new LineDataSet(yValues_min,"最小值折线图");
		lineDataSet_min.setDrawCubic(true);//设置曲线为圆滑的线
		//lineDataSet_min.setValueFormatter(new PercentFormatter());//设置为百分比表示
		//lineDataSet_min.setFillAlpha(110);// 设置填充颜色透明度
		//lineDataSet_min.setDrawCircleHole(true);// 点的圆圈样式,true为空心点,flase为实心点


		lineDataSets.add(lineDataSet_max);
		lineDataSet_max.setLineWidth(2.5f);
		lineDataSet_min.setCircleColor(Color.RED);

		//lineDataSets.add(lineDataSet_min);


		LineData lineData=new LineData(xValues,lineDataSets);


		lineChart.setData(lineData);
		lineChart.setDescription("数据描述");
		lineChart.setDescriptionTextSize(30);
		lineChart.setDescriptionColor(Color.RED);
		lineChart.invalidate();

		Log.e("lineChart",lineChart.getLineData().toString());

		List<LineDataSet> sets=lineChart.getData().getDataSets();
		Log.e("TAGLIST",sets.toString());



		List<Integer> color  = new ArrayList<>();
		/*color.add(Color.BLUE);
		color.add(Color.BLUE);
		color.add(Color.BLUE);
		color.add(Color.BLUE);
		color.add(Color.BLUE);
		color.add(Color.BLUE);
		color.add(Color.BLUE);*/






		for (int i=0;i<7;i++) {
			if (max.get(i)>13) {
				color.add(Color.RED);
				/*sets.get(0).setHighLightColor(Color.RED);
				sets.get(0).getYVals().get(i).getVal()
				sets.get(0).getCircleColor(i);
				sets.get(0).setCircleColorHole(Color.BLACK);
				sets.get(0).setCircleColors(color);*/
				}else {
				color.add(Color.BLACK);
			}
		}
		sets.get(0).setCircleColors(color);



		//	lineChart.getData().getDataSetByIndex(i).setCircleColor(Color.BLUE);
			//lineDataSet_min.setCircleColor(Color.RED);
			//Log.e("TAGLIST",lineChart.getData().getIndexOfDataSet(i));
			/*if (max.get(i)<17){
				LineDataSet set=sets.get(i);
				set.getEntriesForXIndex(i)
			}else {*/
				//lineChart.getData().getDataSetByIndex(i).setCircleColor(Color.BLUE);
			/*LineDataSet set=sets.get(0);
			if (set.getEntriesForXIndex(i)>)
			}*/


		/*for (LineDataSet iSet: sets){
			LineDataSet set = (LineDataSet) iSet;
			//切换显示/隐藏
			set.setDrawValues(!set.isDrawValuesEnabled());
		}*/
		lineChart.invalidate();



	}
	class MyMarkerView extends MarkerView {
		private TextView tvContent;
		private DecimalFormat format=new DecimalFormat("##0");

		public MyMarkerView(Context context, int layoutResource) {
			super(context, layoutResource);
		}

		@Override
		public void refreshContent(Entry entry, Highlight highlight) {

		}

		@Override
		public int getXOffset() {
			return 0;
		}

		@Override
		public int getYOffset() {
			return 0;
		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_nowenvir, container, false);
		lineChart= (LineChart) view.findViewById(R.id.lc_life);
		Ttemp_tv= (TextView) view.findViewById(R.id.Ttemp_tv);
		Ntemp_tv=(TextView)view.findViewById(R.id.Ntemp_tv);
		imageView= (ImageView) view.findViewById(R.id.refresh);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getData();
				setChartView();
			}
		});
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getData();
	}

	private void getData() {
		JSONObject params=new JSONObject();
		try {
			params.put("UserName","user1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		urlBean= Util.loadSetting(getActivity());
		String seturl="http://"+urlBean.getUrl()+":8080/api/v2/get_weather";
		Log.e("TAGURL",seturl);

		RequestQueue mQueue= Volley.newRequestQueue(getActivity());
		JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, seturl, params, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.e("TAGRESPONSE",response.toString());
				if (response.optString("RESULT").equals("S")){
					Toast.makeText(getActivity(),"访问成功",Toast.LENGTH_SHORT).show();
					try {
						Ntemp_tv.setText(response.getString("WCurrent"));
						String main=response.getString("ROWS_DETAIL");
						setData(main);
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

	private void setData(String str) {
		max=new ArrayList<>();
		min=new ArrayList<>();
		try {
			JSONArray jsonArray=new JSONArray(str);
			for (int i=0;i<jsonArray.length();i++){
				String main=jsonArray.getJSONObject(i).getString("temperature");
				if (i==1){
					Ttemp_tv.setText("今天:"+main+"°C");
					max.add(Integer.parseInt(main.substring(main.indexOf("~")+1,main.length())));
					min.add(Integer.parseInt(main.substring(0,main.indexOf("~"))));
				}
				max.add(Integer.parseInt(main.substring(main.indexOf("~")+1,main.length())));
				min.add(Integer.parseInt(main.substring(0,main.indexOf("~"))));
			}
			Log.e("TAGMAX",max.toString());
			Log.e("TAGMAX",min.toString());
			mHandler.sendMessage(new Message());

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
