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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mad.trafficclient.R;

import java.util.ArrayList;


public class Fragment_nowtemp extends Fragment
{
	private LineChart lineChart;
	private RadioButton radioButton;
	private Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
          //  Log.d("进入handler处理方法","！！！！！");


        }

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_nowtemp, container, false);
		lineChart= (LineChart) view.findViewById(R.id.line_now_temp);
		radioButton= (RadioButton) view.findViewById(R.id.rb_now_02);
		radioButton.setChecked(true);
     //   Log.d("TAGONCREATE","进入onCreateView方法！！！！！");
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
      //  Log.d("TAGONACT","进入onActivity方法！！！！！");
		Runnable runnable=new Runnable() {
			@Override
			public void run() {
			    Log.e("TAGSIZE",Fragment_now_volly.temp_list.size()+"");
				if (Fragment_now_volly.temp_list.size()==6){
       //             Log.d("TAGRUN","进入RUN方法！！！！！");
				    setChartView();

                }
                mHandler.postDelayed(this,3000);
			}
		};
		mHandler.postDelayed(runnable,200);
	}

	private void setChartView() {
        Log.d("TAGSETCHART","进入制图方法！！！！！");
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
			yValues.add(new Entry(Fragment_now_volly.temp_list.get(i),i));
		}

		LineDataSet lineDataSet=new LineDataSet(yValues,"");
		lineDataSet.setValueTextSize(18);
		LineData lineData=new LineData(xValues,lineDataSet);
		lineChart.setData(lineData);
		lineChart.invalidate();




	}
}
