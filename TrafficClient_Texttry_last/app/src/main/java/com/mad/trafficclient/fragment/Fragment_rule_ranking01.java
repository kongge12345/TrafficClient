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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.mad.trafficclient.R;
import com.mad.trafficclient.util.UrlBean;
import com.mad.trafficclient.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by 123 on 2018/4/16.
 */

public class Fragment_rule_ranking01 extends Fragment /*implements Comparator<String>*/{
    private BarChart barChart;
    private String url="http://192.168.45.23:8080/api/v2/";
    private ArrayList<String> pcodes=new ArrayList<>();
    private ArrayList<String> pcodes_all=new ArrayList<>();
    private ArrayList<Integer> pcodes_num=new ArrayList<>();
    private ArrayList<String> pcodes_par=new ArrayList<>();
    private TreeMap<Integer,String> pcodes_last=new TreeMap<>();
    //private TreeMap<pcodes,Integer> pcodes_last=new TreeMap<>();
    //private TreeSet<String,Integer> pcodes_last=new TreeSet<>();
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){

            }else if (msg.what==2){

            }else if (msg.what==3){
                setChartView();
            }
            if (pcodes.size()>0&&pcodes_all.size()>0){
                setLastData();
            }

        }
    };

    private void setChartView() {
        XAxis xAxis=barChart.getXAxis();
        xAxis.setTextSize(19);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxis=barChart.getAxisLeft();
        yAxis.setTextSize(20);
        yAxis.setValueFormatter(new PercentFormatter());

        YAxis yAxis1=barChart.getAxisRight();
        yAxis1.setEnabled(false);

        ArrayList<String> xValues=new ArrayList<>();
        xValues.add(" 机动车喷涂、粘贴标识或者车身广告影响安全驾驶的");
        xValues.add(" 变更车道时影响正常行驶的机动车的");
        xValues.add(" 在禁止掉头或者禁止左转弯标志、标线的地点掉头的");
        xValues.add(" 在容易发生危险的路段掉头的");
        xValues.add(" 掉头时妨碍正常行驶的车辆和行人通行的");
        xValues.add(" 机动车未按规定鸣喇叭示意的");
        xValues.add(" 在禁止鸣喇叭的区域或者路段鸣喇叭的");
        xValues.add(" 行经铁路道口,不按规定通行的");
        xValues.add(" 机动车载货长度、宽度、高度超过规定的");
        xValues.add(" 机动车载物行驶时遗洒、飘散载运物的");
		/*Iterator id=pcodes_last.entrySet().iterator();
		//Iterator id=pcodes_last.iterator();
		for (int i=0;i<8;i++){
			Map.Entry entry= (Map.Entry) id.next();
			String value= (String) entry.getValue();
			xValues.add(value);
		}
*/
        ArrayList<BarEntry> yValues=new ArrayList<>();
        yValues.add(new BarEntry((float) 48.2,0));
        yValues.add(new BarEntry((float) 33.1,1));
        yValues.add(new BarEntry((float) 2.6,2));
        yValues.add(new BarEntry((float) 2.6,3));
        yValues.add(new BarEntry((float) 2.6,4));
        yValues.add(new BarEntry((float) 2.6,5));
        yValues.add(new BarEntry((float) 2.6,6));
        yValues.add(new BarEntry((float) 1.3,7));
        yValues.add(new BarEntry((float) 1.3,8));
        yValues.add(new BarEntry((float) 1.3,9));
		/*for (int i=0;i<8;i++){
			Map.Entry entry= (Map.Entry) id.next();
			int key= (int) entry.getKey();
			yValues.add(new BarEntry(key,i));
		}*/

		ArrayList<Integer> colors=new ArrayList<>();
		colors.add(Color.RED);
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);
		colors.add(Color.GRAY);
		colors.add(Color.YELLOW);
		colors.add(Color.DKGRAY);
		colors.add(Color.LTGRAY);
		colors.add(Color.MAGENTA);
        colors.add(Color.RED);
        colors.add(Color.BLUE);

        BarDataSet barDataSet=new BarDataSet(yValues,"");
        barDataSet.setValueTextSize(19);
        barDataSet.setColors(colors);
        barDataSet.setValueFormatter(new PercentFormatter());

        BarData barData=new BarData(xValues,barDataSet);

        barChart.setData(barData);
    }

    private void setLastData() {
        Log.e("TAGpcodes",pcodes.toString());
        Log.e("TAGpcodes_all",pcodes_all.toString());
        for (int i=0;i<pcodes.size();i++){
            int num=0;
            String pcode=pcodes.get(i);
            for (int j=0;j<pcodes_all.size();j++){
                String pcode_all=pcodes_all.get(j);
                if (pcode.equals(pcode_all)){
                    num++;
                }
            }
            pcodes_num.add(num);
        }

        Log.e("TAG42",pcodes_par.get(42));
        Log.e("TAG45",pcodes_par.get(45));
        Log.e("TAG46",pcodes_par.get(46));
        Log.e("TAG47",pcodes_par.get(47));
        Log.e("TAG48",pcodes_par.get(48));
        Log.e("TAG49",pcodes_par.get(49));
        Log.e("TAG50",pcodes_par.get(50));
        Log.e("TAG28",pcodes_par.get(28));
        Log.e("TAG29",pcodes_par.get(29));
        Log.e("TAG30",pcodes_par.get(30));
        int number=0;
        for (int i=0;i<pcodes_num.size();i++){
            number= number+pcodes_num.get(i);
        }
        Log.e("number",number+"");

        //42
        //45
        //46
        //47
        //48
        //49
        //50
        //28
        //29
        //30
        Log.e("TAGPCODES_num",pcodes_num.toString());
        Log.e("TAGPCODES_par",pcodes_par.toString());
        Log.e("TAGPCODES_numsize",pcodes_num.size()+"");
        Log.e("TAGPCODES_parsize",pcodes_par.size()+"");
        Log.e("TAGPCODES_lenghth",pcodes_last.size()+"");
        for (int i=0;i<pcodes_num.size();i++){
            //pcodes_last.put(new pcodes(pcodes_num.get(i),pcodes_par.get(i)),i);
            //pcodes_last.put(new pcodes(pcodes_num.get(i),pcodes_par.get(i)),i);
            pcodes_last.put(pcodes_num.get(i),pcodes_par.get(i));
        }
        Log.e("TAGPCODES_lenghth",pcodes_last.size()+"");
        Log.e("TAGPCODES_lenghth",pcodes_last.size()+"");
        /*class LenComparator implements Comparator<pcodes> {

         *//*@Override
			public int compare(Student s1, Student s2) {
				// TODO Auto-generated method stub
				int num = new Integer(s1.getName().length()).compareTo(s2.getName().length());
				if(num == 0)
					return new Integer(s1.getAge()).compareTo(s2.getAge());
				return num;
			}*//*

			@Override
			public int compare(pcodes t1, pcodes t2) {
				int num = new Integer(t1.getNumber())
				return 0;
			}
		}*/

        pcodes_last.descendingMap();
        Message msg=new Message();
        msg.what=3;
        mHandler.sendMessage(msg);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater
                .inflate(R.layout.fragment_rule_ranking, container, false);
        barChart= (BarChart) view.findViewById(R.id.bar_ranking);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setChartView();
		/*getData("get_peccancy_type",1);
		getData("get_all_car_peccancy",2);*/
    }

    private void getData(String surl, final int info) {
        JSONObject params=new JSONObject();
        try {
            params.put("UserName","user1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String seturl=url+surl;
        RequestQueue mQueue= Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, seturl, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("RESULT").equals("S")){
                        String res=response.getString("ROWS_DETAIL").toString();
                        if (info==1){
                            setTypeData((String)res);
                        }else {
                            setAllData((String)res);
                        }
						/*Message msg=new Message();
						msg.obj=res;
						msg.what=info;
						mHandler.sendMessage(msg);*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        mQueue.add(jsonObjectRequest);

    }

    private void setAllData(String res) {
        try {
            JSONArray jsonArray=new JSONArray(res);
            for (int i=0;i<jsonArray.length();i++){
                String pcode=jsonArray.getJSONObject(i).getString("pcode");
                pcodes_all.add(pcode);
            }
            Message msg=new Message();
            msg.obj=res;
            msg.what=2;
            mHandler.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setTypeData(String res) {
        try {
            JSONArray jsonArray=new JSONArray(res);
            for (int i=0;i<jsonArray.length();i++){
                String pcode=jsonArray.getJSONObject(i).getString("pcode");
                String premarks=jsonArray.getJSONObject(i).getString("premarks");
                pcodes.add(pcode);
                pcodes_par.add(premarks);
            }
            Message msg=new Message();
            msg.obj=res;
            msg.what=1;
            mHandler.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
