/**
 * 
 */
package com.mad.trafficclient.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mad.trafficclient.MainActivity;
import com.mad.trafficclient.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_nowpm extends Fragment
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
				.inflate(R.layout.fragment_nowpm, container, false);
		lineChart= (LineChart) view.findViewById(R.id.line_now_pm);
		radioButton= (RadioButton) view.findViewById(R.id.rb_now_01);
		radioButton.setChecked(true);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Toast.makeText(getActivity(), "正在获取数据！请稍后-----", Toast.LENGTH_LONG).show();
		Runnable runnable=new Runnable() {
			@Override
			public void run() {
				RequestQueue mQueue;
				if (getActivity()==null || getActivity().equals("")){
					return;
				}else {
					mQueue=Volley.newRequestQueue(getActivity());
					Fragment_now_volly.getData(getActivity(),mQueue);
					if (Fragment_now_volly.pm_list.size()==6){
						setChartView();
					}
				}
				mHandler.postDelayed(this,3000);
			}
		};
		mHandler.postDelayed(runnable,3000);
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
		for (int i=0;i<Fragment_now_volly.pm_list.size();i++){
			xValues.add(i*3+"");
		}

		ArrayList<Entry> yValues=new ArrayList<>();
		for (int i=0;i<Fragment_now_volly.pm_list.size();i++){
			yValues.add(new Entry(Fragment_now_volly.pm_list.get(i),i));
		}

		ArrayList<Integer> colors=new ArrayList<>();
		for (int i=0;i<6;i++){
			if (Fragment_now_volly.pm_list.get(i)>200){
				colors.add(Color.RED);
			}else {
				colors.add(Color.GREEN);
			}
		}

		LineDataSet lineDataSet=new LineDataSet(yValues,"");
		lineDataSet.setCircleColors(colors);
		lineDataSet.setLineWidth(4);
		lineDataSet.setCircleSize(12);
		LineData lineData=new LineData(xValues,lineDataSet);
		lineChart.setDescription("秒");
		lineChart.setDescriptionTextSize(18);
		lineChart.setDrawBorders(false);
		lineChart.setData(lineData);
		lineChart.invalidate();

		for (int i=0;i<6;i++){
			if (lineChart.getData().getDataSetByIndex(0).getYVals().get(i).getVal()>200){
				/*Intent intent=new Intent(getActivity(), MainActivity.class);
				PendingIntent pi=PendingIntent.getActivity(getActivity(), 0, intent ,0);*/
				//获取状态通知栏管理：
				NotificationManager manager= (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
				//实例化通知栏构造器NotificationCompat.Builder：
				Notification notification=new NotificationCompat.Builder(getActivity()).setContentTitle("警告通知")
						//对Builder进行配置：
						.setContentText("当前PM2.5："+lineChart.getData().getDataSetByIndex(0).getYVals().get(i).getVal())
						.setSmallIcon(R.drawable.refresh)/*.setContentIntent(pi)*/.setAutoCancel(true).build();
				manager.notify(1,notification);

			}
		}




	}


}
