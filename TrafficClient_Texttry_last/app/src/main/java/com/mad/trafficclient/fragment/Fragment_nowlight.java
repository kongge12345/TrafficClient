/**
 * 
 */
package com.mad.trafficclient.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mad.trafficclient.R;

import java.util.ArrayList;


public class Fragment_nowlight extends Fragment
{
	private LineChart lineChart;
	private RadioButton radioButton;
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
				.inflate(R.layout.fragment_nowlight, container, false);
		lineChart= (LineChart) view.findViewById(R.id.line_now_light);
		radioButton= (RadioButton) view.findViewById(R.id.rb_now_03);
		radioButton.setChecked(true);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Runnable runnable=new Runnable() {
			@Override
			public void run() {
				mHandler.postDelayed(this,3000);
				if (Fragment_now_volly.light_list.size()==6){
					setChartView();
				}
			}
		};
		mHandler.postDelayed(runnable,200);

	}

	private void setChartView() {
		XAxis xAxis=lineChart.getXAxis();
		xAxis.setTextSize(16);
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

		YAxis yAxis=lineChart.getAxisLeft();
		yAxis.setTextSize(17);

		YAxis yAxis1=lineChart.getAxisRight();
		yAxis1.setEnabled(false);



		ArrayList<String> xValues=new ArrayList<>();
		for (int i=0;i<6;i++){
			xValues.add(i*3+"");
		}

		ArrayList<Entry> yValues=new ArrayList<>();
		for (int i=0;i<6;i++){
			yValues.add(new Entry(Fragment_now_volly.light_list.get(i),i));
		}

		LineDataSet lineDataSet=new LineDataSet(yValues,"");
		lineDataSet.setValueTextSize(18);
		LineData lineData=new LineData(xValues,lineDataSet);
		lineChart.setData(lineData);
		lineChart.invalidate();



	}
}
