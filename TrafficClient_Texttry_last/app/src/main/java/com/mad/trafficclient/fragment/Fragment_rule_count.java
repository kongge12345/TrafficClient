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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.mad.trafficclient.R;
import com.mad.trafficclient.util.UrlBean;
import com.mad.trafficclient.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_rule_count extends Fragment
{
	private BarChart barChart;
	private Button button;
	private ArrayList<Integer> big;
	private ArrayList<Integer> big_s;
	private ArrayList<Integer> big_ss;
	private UrlBean urlBean;
	private Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			setChartView();
		}
	};

	class MyMarkerView extends MarkerView {
		private TextView textView;

		public MyMarkerView(Fragment_rule_count context, int layoutResource) {
			super(getActivity(), layoutResource);
			textView= (TextView) findViewById(R.id.tvContent);
		}

		@Override
		public void refreshContent(Entry entry, Highlight highlight) {
			textView.setText("" + entry.getVal());
			textView.setTextSize(30);
			textView.setTextColor(Color.RED);
			textView.setBackgroundColor(Color.BLUE);


		}

		@Override
		public int getXOffset() {
			return -(getWidth() / 2);

		}

		@Override
		public int getYOffset() {
			return -getHeight();

		}
	}




	private void setChartView() {

		MyMarkerView myMarkerView=new MyMarkerView(this, R.layout.custom_marker_view);
		barChart.setMarkerView(myMarkerView);

		YAxis yAxis=barChart.getAxisLeft();
		//yAxis.setValueFormatter(new PercentFormatter());//y轴设为百分比显示
		yAxis.setTextSize(30);//y轴字体大小



		barChart.setDrawBarShadow(false);//柱状图柱子底部的阴影
		barChart.setTouchEnabled(true);//是否可以触碰
		barChart.setScaleEnabled(false);//是否可以缩放
		barChart.setDragEnabled(false);//是否可以拖拽
		barChart.setDescription("数据描述");//数据描述
		barChart.setDescriptionPosition(100,30);//数据描述的位置
		barChart.setDescriptionColor(Color.RED);//数据描述的颜色
		barChart.setDescriptionTextSize(30);//数据描述的字体大小
		//barChart.setPinchZoom(true);
		//barChart.setDrawGridBackground(false);
		//barChart.setDragDecelerationEnabled(true);



		XAxis xAxis=barChart.getXAxis();
		xAxis.setLabelsToSkip(30);
		xAxis.setTextSize(30);//x轴字体大小
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
		xAxis.setDrawGridLines(false);

		ArrayList<String> xValues=new ArrayList<>();
		xValues.add("5次以上违章车辆");
		xValues.add("2-5次违章车辆");
		xValues.add("1-3次违章车辆");

		ArrayList<BarEntry> yValues=new ArrayList<>();
		yValues.add(new BarEntry(big.size(),0));
		yValues.add(new BarEntry(big_s.size(),1));
		yValues.add(new BarEntry(big_ss.size(),2));

		ArrayList<Integer> colors=new ArrayList<>();
		colors.add(Color.RED);
		colors.add(Color.YELLOW);
		colors.add(Color.GREEN);

		BarDataSet barDataSet=new BarDataSet(yValues,"yyyyyyyy");
		barDataSet.setColors(colors);//设置颜色
		BarData barData=new BarData(xValues,barDataSet);
		barDataSet.setVisible(true);//是否显示柱子
		barDataSet.setBarSpacePercent(40f);//值越大，柱状图的宽度就越小
		barChart.setData(barData);
		/*//设置横坐标倾斜角度
		barChart.getXAxis().setLabelsToSkip(90);*/
		barChart.animateXY(2000,2000);//xy轴动画
		barChart.setHighlightEnabled(true);
		barChart.invalidate();//刷新

		/*//设置横坐标显示的间隔数
		barChart.getXAxis().setLabelsToSkip(10);
*/
		Legend l = barChart.getLegend();
		l.setFormSize(20);//设置下角颜色提示的大小
		l.setEnabled(true);//是否显示下角
		barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
		//点击高光的监听
			@Override
			public void onValueSelected(Entry entry, int i, Highlight highlight) {
				if (barChart.getData() != null) {
					barChart.getData().setHighlightEnabled(
							!barChart.getData().isHighlightEnabled());
					barChart.invalidate();
				}

			}

			@Override
			public void onNothingSelected() {

			}
		});

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_rule_count, container, false);
		barChart= (BarChart) view.findViewById(R.id.bc_rule);
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
		String seturl="http://"+urlBean.getUrl()+":8080/api/v2/get_all_car_peccancy";
		Log.e("TAGURL",seturl);

		RequestQueue mQueue= Volley.newRequestQueue(getActivity());
		JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, seturl, params, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.e("TAGRESPONSE",response.toString());
				if (response.optString("RESULT").equals("S")){
					Toast.makeText(getActivity(),"访问成功",Toast.LENGTH_SHORT).show();
					try {
						String str=response.getString("ROWS_DETAIL");
						setData(str);
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
		big=new ArrayList<>();
		big_s=new ArrayList<>();
		big_ss=new ArrayList<>();
		ArrayList<String> list_car=new ArrayList<>();
		ArrayList<Integer> list_number=new ArrayList<>();
		try {
			JSONArray jsonArray=new JSONArray(str);
			for (int i=0;i<jsonArray.length();i++){
				list_car.add(jsonArray.getJSONObject(i).getString("carnumber"));
			}
			Log.e("TAGLIST_CAR",list_car.toString());

			for (int i=0;i<list_car.size();i++){
				int number=0;
				String main=list_car.get(i);
				for (int j=i;j<list_car.size();j++){
					if (main.equals(list_car.get(j))){
						number++;
						list_car.remove(list_car.get(j));
					}
				}
				list_number.add(number);
			}
			Log.e("TAGLIST_NUMBER",list_number.toString());

			for (int i=0;i<list_number.size();i++){
				int info=list_number.get(i);
				if (info>5){
					big.add(info);
				}else if (info<=5 && info>=3){
					big_s.add(info);
				}else {
					big_ss.add(info);
				}
			}
			Log.e("TAGBIG",big.toString());
			Log.e("TAGBIG",big_s.toString());
			Log.e("TAGBIG",big_ss.toString());
			mHandler.sendMessage(new Message());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
