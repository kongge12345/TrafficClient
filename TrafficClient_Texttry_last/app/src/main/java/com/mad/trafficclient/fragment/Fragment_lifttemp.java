/**
 * 
 */
package com.mad.trafficclient.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mad.trafficclient.R;

import java.util.ArrayList;


public class Fragment_lifttemp extends Fragment
{
	private LineChart lineChart;
	private TextView textView;
	private int max;
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
				.inflate(R.layout.fragment_lifttemp, container, false);
		lineChart= (LineChart) view.findViewById(R.id.line_lifttemp);
		textView= (TextView) view.findViewById(R.id.tv_lifttemp);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Runnable runnable=new Runnable() {
			@Override
			public void run() {
				if (Fragment_lift_volly.list_pm.size()==20){
					max=Fragment_lift_volly.list_pm.get(1);
					for (int i=0;i<Fragment_lift_volly.list_pm.size();i++){
						Log.e("TAG",Fragment_lift_volly.list_pm.get(i)+"");
						int c;
						if (max<Fragment_lift_volly.list_pm.get(i)){
							c=max;
							max=Fragment_lift_volly.list_pm.get(i);
						}

					}
					textView.setText(max+"");
					getChartView();
				}
				mHandler.postDelayed(this,3000);
			}
		};
		mHandler.postDelayed(runnable,3000);
	}

	private void getChartView() {
		XAxis xAxis=lineChart.getXAxis();

		YAxis yAxis=lineChart.getAxisLeft();

		ArrayList<String> xValues=new ArrayList<>();
		for (int i=0;i<Fragment_lift_volly.list_pm.size();i++){
			xValues.add(i*3+"");
		}

		ArrayList<Entry> yValues=new ArrayList<>();
		for (int i=0;i<Fragment_lift_volly.list_pm.size();i++){
			yValues.add(new Entry(Fragment_lift_volly.list_pm.get(i),i));
		}

		LineDataSet lineDataSet=new LineDataSet(yValues,"");

		LineData lineData=new LineData(xValues, lineDataSet);

		lineChart.setData(lineData);
		lineChart.invalidate();
	}
}
