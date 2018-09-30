/**
 *
 */
package com.mad.trafficclient.fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mad.trafficclient.R;
import com.mad.trafficclient.fragment_text.Fragment_volly;

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
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;


public class Fragment_timetemp extends Fragment
{
	Fragment_volly fragment_volly;
	private LineChart lineChart;
	private Context context;
	private LinkedList<String> xlist;
	private RequestQueue mQueue;
	private Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Log.e("HAHAHAH","收到消息");

			//setChartView();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_timetemp, container, false);
		lineChart= (LineChart) view.findViewById(R.id.line_encirtar);
		RadioButton radioButton= (RadioButton) view.findViewById(R.id.rb_temp);
		radioButton.setChecked(true);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Toast.makeText(getActivity(), "正在获取数据，请稍后···", Toast.LENGTH_LONG).show();
		Runnable runnable=new Runnable() {
			@Override
			public void run() {

				if (getActivity()==null|| getActivity().equals("")){
					return;
				}else {
					mQueue=Volley.newRequestQueue(getActivity());
					Fragment_volly02.onStart(getActivity(),mQueue);
					Fragment_volly02.onSecond(getActivity(),mQueue);
					if (Fragment_volly02.light.size()==20){
						Log.e("11111111111111111","发送消息");
						getData();
						setChartView();
					}
				}

//				Toast.makeText(getActivity(), "正在获取数据，请稍后", Toast.LENGTH_SHORT).show();
				Log.e("111111111111",Fragment_volly02.temp.size()+"");
				mHandler.sendMessage(new Message());


				mHandler.postDelayed(this,3000);
			}

		};
		mHandler.postDelayed(runnable,200);


	}

	private void setChartView() {
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
		for (int i=0;i<Fragment_volly02.temp.size();i++){
			yValues.add(new Entry(Fragment_volly02.temp.get(i),i));
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
		// 2018-04-24 03:03:31
		/*String time=df.format(day);
		String tim=time.substring()*/
		/*Calendar calendar = Calendar.getInstance();
		Log.e("TAGDATA", String.valueOf(calendar.getTime()));*/
	}
}
