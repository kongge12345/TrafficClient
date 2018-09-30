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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.mad.trafficclient.R;

import java.util.ArrayList;


public class Fragment_liftpm extends Fragment
{
	private TextView textView;
	private BarChart barChart;
	private int max;
	private RequestQueue mQueue;
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
				.inflate(R.layout.fragment_liftpm, container, false);
		textView= (TextView) view.findViewById(R.id.tv_liftpm);
		barChart= (BarChart) view.findViewById(R.id.bar_liftpm);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Toast.makeText(getActivity(), "正在获取数据!亲稍后...", Toast.LENGTH_LONG).show();
			Runnable runnable=new Runnable() {
				@Override
				public void run() {
					if (getActivity()==null){
						return;
					}else {
						mQueue= Volley.newRequestQueue(getActivity());
						Fragment_lift_volly.getData(getActivity(),mQueue);
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
					}
					mHandler.postDelayed(this,3000);
				}
			};
			mHandler.postDelayed(runnable,3000);

	}

	private void getChartView() {
		XAxis xAxis=barChart.getXAxis();

		YAxis yAxis=barChart.getAxisLeft();

		ArrayList<String> xValues=new ArrayList<>();
			for (int i=0;i<Fragment_lift_volly.list_pm.size();i++){
				xValues.add(i*3+"");
			}

		ArrayList<BarEntry> yValues=new ArrayList<>();
		for (int i=0;i<Fragment_lift_volly.list_pm.size();i++){
			yValues.add(new BarEntry(Fragment_lift_volly.list_pm.get(i),i));
		}

		BarDataSet barDataSet=new BarDataSet(yValues,"");

		BarData barData=new BarData(xValues, barDataSet);

		barChart.setData(barData);
		barChart.invalidate();
	}
}
