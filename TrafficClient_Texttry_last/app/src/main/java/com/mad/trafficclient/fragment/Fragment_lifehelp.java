/**
 * 
 */
package com.mad.trafficclient.fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mad.trafficclient.R;
import com.mad.trafficclient.util.UrlBean;
import com.mad.trafficclient.util.Util;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_lifehelp extends Fragment
{
	private TextView tv_lift,tv_tody;
	private android.support.v4.view.ViewPager vp_lift;
	private ImageView iv_refesh;
	private LineChart line_lift;
	private ArrayList<Fragment> F_list=new ArrayList<>();
	private TextView tv_lift_100,tv_lift_101;
	private TextView tv_lift_200,tv_lift_201;
	private TextView tv_lift_300,tv_lift_301;
	private TextView tv_lift_400,tv_lift_401;
	private TextView tv_lift_500,tv_lift_501;
	private ArrayList<Integer> max;
	private ArrayList<Integer> min;
	private UrlBean urlBean;
	private Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what==1){
				Log.e("TAGRESPONSE",msg.toString());
				try {
					JSONObject jsonObject=new JSONObject((String) msg.obj);
					int LightIntensity=Integer.parseInt(jsonObject.getString("LightIntensity"));
					int temperature=Integer.parseInt(jsonObject.getString("temperature"));
					int co2=Integer.parseInt(jsonObject.getString("co2"));
					int pm=Integer.parseInt(jsonObject.getString("pm2.5"));
					if (temperature<8){
						tv_lift_200.setText("较易发（"+temperature+"）");
						tv_lift_201.setText("温度低，风较大，较易发生感冒，注意防护");
					}else if (temperature>=8){
						tv_lift_200.setText("少发（"+LightIntensity+"）");
						tv_lift_201.setText("无明显降温，感冒几率较低");
					}

					if (temperature<12 ){
						tv_lift_300.setText("冷（"+temperature+"）");
						tv_lift_301.setText("建议穿长袖衬衫，单裤等服装");
					}else if (temperature>=12 && temperature<=21){
						tv_lift_300.setText("舒适（"+LightIntensity+"）");
						tv_lift_301.setText("建议穿短袖衬衫，单裤等服饰");
					}else if (temperature>21){
						tv_lift_300.setText("热（"+LightIntensity+"）");
						tv_lift_301.setText("适合穿短袖 T恤，短薄外套等夏装");
					}


					if (LightIntensity>0 && LightIntensity<1000){
						tv_lift_100.setText("弱（"+LightIntensity+"）");
						tv_lift_101.setText("辐射较弱，涂擦SPF12-15,PA+护肤品");
					}else if (LightIntensity>=1000 && LightIntensity<=3000){
						tv_lift_100.setText("中等（"+LightIntensity+"）");
						tv_lift_101.setText("涂擦SPF大于15，PA+防晒护肤品");
					}else if (LightIntensity>3000){
						tv_lift_100.setText("中等（"+LightIntensity+"）");
						tv_lift_101.setText("涂擦SPF大于15，PA+防晒护肤品");
					}

					if (co2<3000){
						tv_lift_400.setText("适宜（"+co2+"）");
						tv_lift_401.setText("气候适宜，推荐您进行户外运动");
					}else if (co2>=3000 && co2<=6000 ){
						tv_lift_400.setText("中（"+co2+"）");
						tv_lift_401.setText("易感人群应适当减少室外活动");
					}else if (co2>6000){
						tv_lift_400.setText("较不宜（"+co2+"）");
						tv_lift_401.setText("空气氧气含量低，请在室外进行休闲运动");
					}

					if (pm<30){
						tv_lift_500.setText("优（"+pm+"）");
						tv_lift_501.setText("空气质量非常好，非常适合户外活动，趁机出去多呼吸新鲜空气");
					}else if (pm>=30 && pm<=100 ){
						tv_lift_500.setText("良（"+pm+"）");
						tv_lift_501.setText("易感人群应适当减少室外活动");
					}else if (pm>100){
						tv_lift_500.setText("污染（"+pm+"）");
						tv_lift_501.setText("空气质量差，不适合户外活动");
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else {
				setData((String)msg.obj);
			}
		}
	};

	private void setData(String obj) {
		max=new ArrayList<>();
		min=new ArrayList<>();
		try {
			JSONArray jsonArray=new JSONArray(obj);
			for (int i=0;i<jsonArray.length();i++){
				String main=jsonArray.getJSONObject(i).getString("temperature");
				if (i==1){
					tv_tody.setText("今天:"+main+"°C");
					max.add(Integer.parseInt(main.substring(main.indexOf("~")+1,main.length())));
					min.add(Integer.parseInt(main.substring(0,main.indexOf("~"))));
				}
				max.add(Integer.parseInt(main.substring(main.indexOf("~")+1,main.length())));
				min.add(Integer.parseInt(main.substring(0,main.indexOf("~"))));
			}
			Log.e("TAGMAX",max.toString());
			Log.e("TAGMAX",min.toString());
			setChartView();

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void setChartView() {
		XAxis xAxis=line_lift.getXAxis();
		xAxis.setTextSize(30);

		YAxis leftAxis=line_lift.getAxisLeft();
		leftAxis.removeAllLimitLines();
		/*
			leftAxis.setAxisMaxValue(200f);//最大值
			leftAxis.setAxisMinValue(10f); //最小值
		*/
		Legend l=line_lift.getLegend();
		l.setFormSize(30);


		ArrayList<String> xValues=new ArrayList<>();
		xValues.add("昨天");
		xValues.add("今天");
		xValues.add("明天");
		xValues.add("周一");
		xValues.add("周二");
		xValues.add("周三");
		xValues.add("周四");

		ArrayList<Entry> yValues_max=new ArrayList<>();
		for (int i=0;i<max.size();i++){
			yValues_max.add(new Entry(max.get(i),i));
		}

		ArrayList<Entry> yValues_min=new ArrayList<>();
		for (int i=0;i<min.size();i++){
			yValues_min.add(new Entry(min.get(i),i));
		}

		ArrayList<LineDataSet> lineDataSets=new ArrayList<>();
		LineDataSet lineDataSet_max=new LineDataSet(yValues_max,"最大值折线图");
		//lineDataSet_max.setValueFormatter(new PercentFormatter());//设置为百分比表示
		lineDataSet_max.setDrawCircleHole(false);//是否显示圆点

		LineDataSet lineDataSet_min=new LineDataSet(yValues_min,"最小值折线图");
		lineDataSet_min.setDrawCubic(true);//设置曲线为圆滑的线
		//lineDataSet_min.setValueFormatter(new PercentFormatter());//设置为百分比表示
		//lineDataSet_min.setFillAlpha(110);// 设置填充颜色透明度
		//lineDataSet_min.setDrawCircleHole(true);// 点的圆圈样式,true为空心点,flase为实心点


		lineDataSets.add(lineDataSet_max);
		lineDataSets.add(lineDataSet_min);


		LineData lineData=new LineData(xValues,lineDataSets);


		line_lift.setData(lineData);
		line_lift.setDescription("数据描述");
		line_lift.setDescriptionTextSize(30);
		line_lift.setDescriptionColor(Color.RED);
		line_lift.invalidate();


	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getData("get_all_sense",1);
		getViewFrag();
		ViewPager viewpager=new ViewPager(getChildFragmentManager());
		vp_lift.setAdapter(viewpager);

		getData("get_weather",2);


	}
	class ViewPager extends FragmentPagerAdapter{

		public ViewPager(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return F_list.get(position);
		}

		@Override
		public int getCount() {
			return F_list.size();
		}
	}

	private void getViewFrag() {
		Fragment_liftco2 fragment_liftco2=new Fragment_liftco2();
		Fragment_lifttemp fragment_lifttemp=new Fragment_lifttemp();
		Fragment_lifthumi fragment_lifthumi=new Fragment_lifthumi();
		Fragment_liftpm fragment_liftpm=new Fragment_liftpm();
		F_list.add(fragment_liftpm);
		F_list.add(fragment_lifttemp);
		F_list.add(fragment_lifthumi);
		F_list.add(fragment_liftco2);



	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_lifthelp, container, false);
		tv_lift= (TextView) view.findViewById(R.id.tv_lift);
		tv_tody= (TextView) view.findViewById(R.id.tv_tady);
		iv_refesh= (ImageView) view.findViewById(R.id.iv_refresh);
		iv_refesh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getData("get_weather",2);
				setChartView();
			}
		});
		line_lift= (LineChart) view.findViewById(R.id.line_lifthelp);
		tv_lift_100= (TextView) view.findViewById(R.id.tv_lift_100);
		tv_lift_101= (TextView) view.findViewById(R.id.tv_lift_101);
		tv_lift_200= (TextView) view.findViewById(R.id.tv_lift_200);
		tv_lift_201= (TextView) view.findViewById(R.id.tv_lift_201);
		tv_lift_300= (TextView) view.findViewById(R.id.tv_lift_300);
		tv_lift_301= (TextView) view.findViewById(R.id.tv_lift_301);
		tv_lift_400= (TextView) view.findViewById(R.id.tv_lift_400);
		tv_lift_401= (TextView) view.findViewById(R.id.tv_lift_401);
		tv_lift_500= (TextView) view.findViewById(R.id.tv_lift_500);
		tv_lift_501= (TextView) view.findViewById(R.id.tv_lift_501);
		vp_lift= (android.support.v4.view.ViewPager) view.findViewById(R.id.viewpager_lifthelp);
		return view;
	}

	public void getData(String url, final int info){
		final JSONObject params=new JSONObject();
		try {
			params.put("UserName","user1");
			// params.put("")
		} catch (JSONException e) {
			e.printStackTrace();
		}
		urlBean= Util.loadSetting(getActivity());
		String seturl="http://"+urlBean.getUrl()+":8080/api/v2/"+url;
		Log.e("TAGPARAMS",params.toString());
		RequestQueue mQueue= Volley.newRequestQueue(getActivity());
		JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, seturl, params, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					if (response.getString("RESULT").equals("S")){
            //	Toast.makeText(getActivity(), "访问成功", Toast.LENGTH_SHORT).show();
						Message msg=new Message();
						if (info==1){
							msg.obj=response.toString();
						}else if (info==2){
							String main=response.getString("ROWS_DETAIL");
							msg.obj=main;
						}
						msg.what=info;
						mHandler.sendMessage(msg);

					}else {
						Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				Toast.makeText(getActivity(),volleyError.toString(), Toast.LENGTH_SHORT).show();

			}
		});
		mQueue.add(jsonObjectRequest);

	}

}
