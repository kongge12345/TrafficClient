/**
 * 
 */
package com.mad.trafficclient.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.mad.trafficclient.R;
import com.mad.trafficclient.login.LoginActivity;
import com.mad.trafficclient.util.UrlBean;
import com.mad.trafficclient.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_rule_oldoryear01 extends Fragment
{
	private BarChart barChart;
	String all_N;
	String all_A;
	String rows = null;
	private ArrayList<String> num_nine;
	private ArrayList<String> num_eight;
	private ArrayList<String> num_seven;
	private ArrayList<String> num_six;
	private ArrayList<String> num_five;
	private ArrayList<String> carnum_nine;
	private ArrayList<String> carnum_eight;
	private ArrayList<String> carnum_seven;
	private ArrayList<String> carnum_six;
	private ArrayList<String> carnum_five;
	private JSONArray jsonArray_N;
	private UrlBean urlBean;
	private Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what==1){
				all_A= (String) msg.obj;
			}else if (msg.what==2){
				all_N= (String) msg.obj;
				setData(all_A,all_N);
				setChartView();

			}


		}
	};

	private void setData(String all_a, String all_n) {
		carnum_nine=new ArrayList<>();
		carnum_eight=new ArrayList<>();
		carnum_seven=new ArrayList<>();
		carnum_six=new ArrayList<>();
		carnum_five=new ArrayList<>();
		num_nine=new ArrayList<>();
		num_eight=new ArrayList<>();
		num_seven=new ArrayList<>();
		num_six=new ArrayList<>();
		num_five=new ArrayList<>();
		/*num_nine=0;
		num_eight=0;
		num_seven=0;
		num_six=0;
		num_five=0;*/
		ArrayList<String> carnumber=new ArrayList<>();
		try {
			jsonArray_N=new JSONArray(all_n);
			for (int i=0;i<jsonArray_N.length();i++){
				carnumber.add(jsonArray_N.getJSONObject(i).getString("carnumber"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			JSONArray jsonArray_A=new JSONArray(all_a);
			for (int i=0;i<jsonArray_A.length();i++){
				String str=jsonArray_A.getJSONObject(i).optString("pcardid");
				int num=Integer.parseInt(str.substring(9,10));
				Log.e("TAGNUM",num+"");
				switch (num){
					case 9:
						String car_nine=jsonArray_A.getJSONObject(i).getString("carnumber");
						carnum_nine.add(car_nine);
						Log.e("111111",1111111111+"");
						n:for (int j=0;j<carnumber.size();j++){
							//Log.e("111111",j+"");
							if (car_nine.equals(carnumber.get(j))){
								num_nine.add(car_nine);
								break n;
								//Log.e("NUM_NINE",num_nine+"");
							}
						}
					case 8:
						String car_eight=jsonArray_A.getJSONObject(i).getString("carnumber");
						Log.e("2222",22222+"");
						carnum_eight.add(car_eight);
						n:for (int j=0;j<carnumber.size();j++){
							//Log.e("22222",j+"");
							if (car_eight.equals(carnumber.get(j))){
								num_eight.add(car_eight);
								break n;
							}
						}
					case 7:
						String car_seven=jsonArray_A.getJSONObject(i).getString("carnumber");
						carnum_seven.add(car_seven);
						n:for (int j=0;j<carnumber.size();j++){
							if (car_seven.equals(carnumber.get(j))){
								num_seven.add(car_seven);
								break n;
							}
						}
					case 6:
						String car_six=jsonArray_A.getJSONObject(i).getString("carnumber");
						carnum_six.add(car_six);
						n:for (int j=0;j<carnumber.size();j++){
							if (car_six.equals(carnumber.get(j))){
								num_six.add(car_six);
								break n;
							}
						}
					case 5:
						String car_five=jsonArray_A.getJSONObject(i).getString("carnumber");
						carnum_five.add(car_five);
						n:for (int j=0;j<carnumber.size();j++){
							if (car_five.equals(carnumber.get(j))){
								num_five.add(car_five);
								break n;
							}
						}
					default:
						break;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_oldoryear01, container, false);
		barChart= (BarChart) view.findViewById(R.id.bar_fragment04);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		urlBean=Util.loadSetting(getActivity());
		String setUrl_A="http://"+urlBean.getUrl()+":8080/api/v2/get_car_info";
		JSONObject params=new JSONObject();
		try {
			params.put("UserName","user1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String setUrl_N="http://"+urlBean.getUrl()+":8080/api/v2/get_all_car_peccancy";
		getData(setUrl_A,params,1);
		getData(setUrl_N,params,2);

	}

	private void setChartView() {
		YAxis leftAxis = barChart.getAxisLeft();
		leftAxis.setTextSize(30);
		leftAxis.setAxisMinValue(0f);


		barChart.getAxisRight().setEnabled(false);

		XAxis xLabels = barChart.getXAxis();
		xLabels.setDrawLimitLinesBehindData(true);
		xLabels.setTextSize(30);
		xLabels.setPosition(XAxis.XAxisPosition.TOP);

		Legend l = barChart.getLegend();
		l.setFormSize(20);
		l.setTextSize(20);
		l.setFormToTextSpace(4f);
		l.setXEntrySpace(6f);

		ArrayList<String> xValues=new ArrayList<>();
		xValues.add("90后");
		xValues.add("80后");
		xValues.add("70后");
		xValues.add("60后");
		xValues.add("50后");

		ArrayList<BarEntry> yValues = new ArrayList<>();
		yValues.add(new BarEntry(new float[]{num_nine.size(),carnum_nine.size()-num_nine.size()}, 0));
		yValues.add(new BarEntry(new float[]{num_eight.size(),carnum_eight.size()-num_eight.size()}, 1));
		yValues.add(new BarEntry(new float[]{num_seven.size(),carnum_seven.size()-num_seven.size()}, 2));
		yValues.add(new BarEntry(new float[]{num_six.size(),carnum_six.size()-num_six.size()},3));
		yValues.add(new BarEntry(new float[]{num_five.size(),carnum_five.size()-num_five.size()}, 4));
		Log.e("num_nine",num_nine.size()+"");
		Log.e("num_eigth",num_eight.size()+"");
		Log.e("num_seven",num_seven.size()+"");
		Log.e("num_six",num_six.size()+"");
		Log.e("num_five",num_five.size()+"");
		Log.e("carnum_nine",carnum_nine.size()+"");
		Log.e("carnum_eight",carnum_eight.size()+"");
		Log.e("carnum_seven",carnum_seven.size()+"");
		Log.e("carnum_six",carnum_six.size()+"");
		Log.e("carnum_five",carnum_five.size()+"");



		BarDataSet set = new BarDataSet(yValues, "");
		//set.setValueFormatter(new PercentFormatter());
		set.setValueTextSize(7f);
		set.setBarSpacePercent(60);


		ArrayList<Integer> colors=new ArrayList<>();
		colors.add(Color.RED);
		colors.add(Color.YELLOW);
		set.setAxisDependency(YAxis.AxisDependency.RIGHT);
		set.setColors(colors);
		set.setValueTextSize(20);
		set.setStackLabels(new String[]{"有违章", "无违章"});



		BarData data = new BarData(xValues,set);
		data.setValueFormatter(new PercentFormatter());
		data.setValueTextSize(20);
		barChart.setTouchEnabled(false);

		barChart.setData(data);
		barChart.invalidate();
	}

	private void getData(String setUrl, JSONObject params, final int info) {
		/*JSONObject params=new JSONObject();
		try {
			params.put("UserName","user1");
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
		//String setUrl="http://192.168.3.19;8080/api/v2/get_car_info";

		Log.e("TAG",setUrl);
		RequestQueue mQueue= Volley.newRequestQueue(getActivity());
		JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, setUrl, params, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.e("TAGRESPONSE",response.toString());
				if (response.optString("RESULT").equals("S")){
					try {
						rows= response.getString("ROWS_DETAIL");
						Message msg=new Message();
						msg.obj=rows;
						msg.what=info;
						mHandler.sendMessage(msg);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else {
					Toast.makeText(getActivity(),"未访问到结果",Toast.LENGTH_SHORT).show();
					Log.e("TAGERROR","F");
					//rows=response.optString("RESULT").toString();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				Log.e("TAGERROR","error");
 	}
		});
		mQueue.add(jsonObjectRequest);

	}
}

/*

	String setUrl_N="http://192.168.3.19:8080/api/v2/get_car_peccancy";

	ArrayList<String> allnum_nine=new ArrayList<>();
	ArrayList<Integer> album_nineL=new ArrayList<>();
	ArrayList<String> allnum_eight=new ArrayList<>();
	ArrayList<Integer> album_eightL=new ArrayList<>();
	ArrayList<String> allnum_seven=new ArrayList<>();
	ArrayList<Integer> album_sevenL=new ArrayList<>();
	ArrayList<String> allnum_six=new ArrayList<>();
	ArrayList<Integer> album_sixL=new ArrayList<>();
	ArrayList<String> allnum_five=new ArrayList<>();
	ArrayList<Integer> album_fiveL=new ArrayList<>();
try {
		JSONArray jsonArray=new JSONArray(all);
		Log.e("TAGJSONARRAY",jsonArray.toString());
		for (int i=0;i<jsonArray.length();i++){
		String str=jsonArray.getJSONObject(i).optString("pcardid");
				*/
/*allcarnumber.add(jsonArray.getJSONObject(i).getString("carnumber"));
				allpcardid.add(str);*//*

		int num=Integer.parseInt(str.substring(9,10));
		switch (num){
		case 9:
		allnum_nine.add(jsonArray.getJSONObject(i).getString("carnumber"));
		for (int j=0;j<allnum_nine.size();j++){

		JSONObject params_N=new JSONObject();
		try {
		params_N.put("UserName","user1");
		params_N.put("carnumber",allnum_nine.get(j));
		} catch (JSONException e) {
		e.printStackTrace();
		}
							*/
/*String all_N=getData(setUrl_N,params_N);
								if (all_N.length() > 0){
								number++;
								album_nineL.add(number);
							}*//*


		}
		case 8:
		allnum_eight.add(jsonArray.getJSONObject(i).getString("carnumber"));
		for (int j=0;j<allnum_eight.size();j++){
		int number=0;
		JSONObject params_N=new JSONObject();
		try {
		params_N.put("UserName","user1");
		params_N.put("carnumber",allnum_eight.get(j));
		} catch (JSONException e) {
		e.printStackTrace();
		}
		String all_N=getData(setUrl_N,params_N);
		if (all_N.length() > 0){
		number++;
		album_eightL.add(number);
		}
		}
		case 7:
		allnum_seven.add(jsonArray.getJSONObject(i).getString("carnumber"));
		for (int j=0;j<allnum_seven.size();j++){
		int number=0;
		JSONObject params_N=new JSONObject();
		try {
		params_N.put("UserName","user1");
		params_N.put("carnumber",allnum_seven.get(j));
		} catch (JSONException e) {
		e.printStackTrace();
		}
		String all_N=getData(setUrl_N,params_N);
		if (all_N.length() > 0){
		number++;
		album_sevenL.add(number);
		}
		}
		case 6:
		allnum_six.add(jsonArray.getJSONObject(i).getString("carnumber"));
		for (int j=0;j<allnum_six.size();j++){
		int number=0;
		JSONObject params_N=new JSONObject();
		try {
		params_N.put("UserName","user1");
		params_N.put("carnumber",allnum_six.get(j));
		} catch (JSONException e) {
		e.printStackTrace();
		}
		String all_N=getData(setUrl_N,params_N);
		if (all_N.length() > 0){
		number++;
		album_sixL.add(number);
		}
		}
		case 5:
		allnum_five.add(jsonArray.getJSONObject(i).getString("carnumber"));
		for (int j=0;j<allnum_five.size();j++){
		int number=0;
		JSONObject params_N=new JSONObject();
		try {
		params_N.put("UserName","user1");
		params_N.put("carnumber",allnum_five.get(j));
		} catch (JSONException e) {
		e.printStackTrace();
		}
		String all_N=getData(setUrl_N,params_N);
		if (all_N.length() > 0){
		number++;
		album_fiveL.add(number);
		}
		}
		break;
		}
		}
		} catch (JSONException e) {
		e.printStackTrace();
		}*/
