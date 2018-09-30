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
import android.widget.RadioButton;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mad.trafficclient.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;


public class Fragment_timelight extends Fragment
{

	private LineChart lineChart;
	private LinkedList<String> xlist=new LinkedList<>();
	private Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_timelight, container, false);
		lineChart= (LineChart) view.findViewById(R.id.line_light);
		RadioButton radioButton= (RadioButton) view.findViewById(R.id.rb_light);
		radioButton.setChecked(true);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Runnable runnable=new Runnable() {
			@Override
			public void run() {
				if (Fragment_volly02.light.size()==20){
					getData();
					getChartView();
				}
				mHandler.postDelayed(this,3000);
			}
		};
		mHandler.postDelayed(runnable,200);
	}

	private void getChartView() {
		XAxis xAxis=lineChart.getXAxis();
		xAxis.setTextSize(15);
		xAxis.setAvoidFirstLastClipping(true);
		xAxis.setSpaceBetweenLabels(-6);

		YAxis yAxis=lineChart.getAxisLeft();
		yAxis.setTextSize(26);

		YAxis yAxis1=lineChart.getAxisRight();
		yAxis1.setEnabled(false);

		ArrayList<String> xValues=new ArrayList<>();
		for (int i=0;i<xlist.size();i++){
			xValues.add(xlist.get(i));
		}

		ArrayList<Entry> yValues=new ArrayList<>();
		for (int i=0;i<Fragment_volly02.light.size();i++){
			yValues.add(new Entry(Fragment_volly02.light.get(i),i));
		}
		Log.e("TAGFRAGMENT",Fragment_volly02.temp.toString());
		LineDataSet lineDataSet=new LineDataSet(yValues,"");
		lineDataSet.setCircleSize(8);
		lineDataSet.setCircleColor(Color.RED);
		lineDataSet.setValueTextSize(20);
		LineData lineData=new LineData(xValues,lineDataSet);
		lineChart.setData(lineData);
		lineChart.invalidate();
	}

	private void getData() {
		xlist=new LinkedList<>();
		Date day=new Date();
		SimpleDateFormat df = new SimpleDateFormat("mm:ss");
		String time=df.format(day);
		String one=time.substring(0,3);
		int twe=Integer.parseInt(time.substring(4,time.length()));
		for (int i=0;i<20;i++){
			if (twe-(i*3)>=0){
				xlist.add(one+String.valueOf(twe-(i*3)));
				Log.e("XVALUES",one+String.valueOf(twe-(i*3)));
			}else {
				xlist.add(one+String.valueOf(twe+60-(i*3)));
				Log.e("XVALUES",one+String.valueOf(twe+60-(i*3)));
			}
			//xValues.add(one+String.valueOf(twe-(i*3)));


		}
		Log.e("TAGDATA",df.format(day));
		Log.e("TAGDATAXVALUES",xlist.toString());
	}

}
