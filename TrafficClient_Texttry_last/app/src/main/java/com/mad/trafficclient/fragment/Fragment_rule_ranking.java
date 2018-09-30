package com.mad.trafficclient.fragment;

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

public class Fragment_rule_ranking extends Fragment /*implements Comparator<String>*/{
    private HashSet<String> hashSet=new HashSet<>();
    private ArrayList<String> list=new ArrayList<>();
    private TreeMap<String,Integer> treeMap=new TreeMap<>();
    private BarChart barChart;
    private UrlBean urlBean;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setData((String)msg.obj);
            setChartView();

        }
    };

    private void setChartView() {
        XAxis xAxis=barChart.getXAxis();

        YAxis yAxis=barChart.getAxisLeft();

        YAxis yAxis1=barChart.getAxisRight();

        ArrayList<String> xValues=new ArrayList<>();
        for (int i=0;i<10;i++){


        }

    }

    private void setData(String obj) {
        try {
            JSONArray jsonArray=new JSONArray(obj);
            for (int i=0;i<jsonArray.length();i++){
                String res=jsonArray.getJSONObject(i).getString("premarks");
                list.add(res);
                hashSet.add(res);

               /* Log.e("list",list.toString());
                Log.e("list",hashSet.toString());*/

            }

            Iterator iterator=hashSet.iterator();
            String has= (String) iterator.next();
            while (iterator.hasNext()) {
                int number=0;
                String tri = (String) iterator.next();
                for (int i=0;i<list.size();i++){
                    if (tri.equals(list.get(i))){
                        int num=number++;
                        Log.e("num",num+"");
                    }
                }
                treeMap.put(tri,number);
                treeMap.descendingMap();
            }
            Log.e("treeMap",treeMap.toString());

            /*for (int i=0;i<list.size();i++){
                String str=list.get(i);
                Iterator iterator=hashSet.iterator();
                String has= (String) iterator.next();
                int number=0;
                while (iterator.hasNext()){
                    String tri= (String) iterator.next();
                   if (list.get(i).equals(tri)){
                       number++;
                   };
                    map.put(tri+"",number);
                }
                //map.put(iterator.next()+"",number);
            }
            Log.e("TAGLIEST",map.toString());*/



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
   /* @Override
    public int compare(String key1, String key2) {
        return key2.compareTo(key1);//降序排列：String作为api特供的类，实现了Comparable的compareTo方法发法被设计成小于，等于，大于分别返回负数，零，正数

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_rule_ranking,container,false);
        barChart= (BarChart) view.findViewById(R.id.bar_ranking);
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
        String url="http://"+urlBean.getUrl()+":8080/api/v2/get_peccancy_type";
        RequestQueue mQueue= Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("RESULT").equals("S")){
                        Toast.makeText(getActivity(), "访问成功", Toast.LENGTH_SHORT).show();
                        Message msg=new Message();
                        msg.obj=response.getString("ROWS_DETAIL").toString();
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
                Toast.makeText(getActivity(), "访问成功", Toast.LENGTH_SHORT).show();

            }
        });
        mQueue.add(jsonObjectRequest);
    }


}
