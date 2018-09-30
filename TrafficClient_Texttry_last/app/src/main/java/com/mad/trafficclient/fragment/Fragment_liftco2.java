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
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mad.trafficclient.R;

import java.util.ArrayList;


public class Fragment_liftco2 extends Fragment
{
	private TextView textView;
	private LineChart lineChart;
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
				.inflate(R.layout.fragment_liftco, container, false);
		textView= (TextView) view.findViewById(R.id.tv_liftco);
		lineChart= (LineChart) view.findViewById(R.id.line_liftco);
		return view;
	}




	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Runnable runnable=new Runnable() {
			@Override
			public void run() {
					if (Fragment_lift_volly.list_co.size()==20){
						Integer max=Fragment_lift_volly.list_co.get(0);
						for (int i=0;i<19;i++){
							if (max<Fragment_lift_volly.list_co.get(i+1)){
								int a=max;
								max=Fragment_lift_volly.list_co.get(i+1);
							}
						}
						textView.setText(max+"");
					}
					getChartView();
				mHandler.postDelayed(this,3000);
			}
		};
		mHandler.postDelayed(runnable,200);
	}

	private void getChartView() {
		XAxis xAxis=lineChart.getXAxis();

		YAxis yAxis=lineChart.getAxisLeft();

		ArrayList<String> xValues=new ArrayList<>();
		for (int i=0;i<Fragment_lift_volly.list_co.size();i++){
			xValues.add(i*3+"");
		}

		ArrayList<Entry> yValues=new ArrayList<>();
		for (int i=0;i<Fragment_lift_volly.list_co.size();i++){
			yValues.add(new Entry(Fragment_lift_volly.list_co.get(i),i));
		}

		LineDataSet lineDataSet=new LineDataSet(yValues,"");

		LineData lineData=new LineData(xValues, lineDataSet);

		lineChart.setData(lineData);
		lineChart.invalidate();
	}

}
