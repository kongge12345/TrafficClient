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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.mad.trafficclient.R;
import com.mad.trafficclient.util.UrlBean;
import com.mad.trafficclient.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/3.
 */

public class Fragment_rule_sex extends Fragment {
    private BarChart barChart;
    private String A_R;
    private String A_N;
    private ArrayList<String> GN_carnum;
    private ArrayList<String> BN_carnum;
    private ArrayList<String> G_carnum;
    private ArrayList<String> B_carnum;
    private ArrayList<String> N_carnum;
    private UrlBean urlBean;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                A_R= (String) msg.obj;
            }else if (msg.what==2){
                A_N=((String) msg.obj);
                setData(A_R,A_N);
                setChartView();
            }
        }
    };

    private void setData(String a_R, String a_N) {
        JSONArray jsonArray_R;
        JSONArray jsonArray_N;
        GN_carnum=new ArrayList<>();
        BN_carnum=new ArrayList<>();
        G_carnum=new ArrayList<>();
        B_carnum=new ArrayList<>();
        N_carnum=new ArrayList<>();
        try {
            jsonArray_N=new JSONArray(a_N);
            for (int i=0;i<jsonArray_N.length();i++){
                N_carnum.add(jsonArray_N.getJSONObject(i).getString("carnumber"));
            }
            jsonArray_R=new JSONArray(a_R);
            for (int i=0;i<jsonArray_R.length();i++){
                String pcardid=jsonArray_R.getJSONObject(i).getString("pcardid");
                String car=jsonArray_R.getJSONObject(i).getString("carnumber");
                int num=Integer.parseInt(pcardid.substring(17,18));
                if (num%2==0){
                    G_carnum.add(car);
                   // Log.e("TAG",G_carnum.size()+"");
                    Log.e("jsonArray_N",N_carnum.toString());
                    n:for (int j=0;j<N_carnum.size();j++){
                        if (car.equals(N_carnum.get(j))){
                            GN_carnum.add(car);
                           // Log.e("TAGN",GN_carnum.toString());
                            break n;
                        }
                    }
                }else {
                    B_carnum.add(car);
                    //Log.e("TAG",B_carnum.size()+"");
                    Log.e("jsonArray_N",N_carnum.toString());
                    n:for (int j=0;j<N_carnum.size();j++){
                        if (car.equals(N_carnum.get(j))){
                            BN_carnum.add(car);
                            //Log.e("TAGN",BN_carnum.toString());
                            break n;
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_rule_sex,container,false);
        barChart= (BarChart) view.findViewById(R.id.barChart_sex);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData("get_car_info",1);
        getData("get_all_car_peccancy",2);
    }

    private void getData(String str, final int info) {
        JSONObject params=new JSONObject();
        try {
            params.put("UserName","user1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        urlBean= Util.loadSetting(getActivity());
        String URL="http://"+urlBean.getUrl()+":8080/api/v2/";
        String setUrl=URL+str;
        RequestQueue mQueue= Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, setUrl, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.optString("RESULT").equals("S")){
                    Toast.makeText(getActivity(),"访问成功",Toast.LENGTH_SHORT).show();
                    try {
                        String str=response.getString("ROWS_DETAIL");
                        Message msg=new Message();
                        msg.obj=str;
                        msg.what=info;
                        mHandler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getActivity(),"访问失败",Toast.LENGTH_SHORT).show();
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


    private void setChartView() {
        XAxis xAxis=barChart.getXAxis();
        xAxis.setTextSize(20);

        YAxis yAxis=barChart.getAxisLeft();
        yAxis.setTextSize(20);

        ArrayList<String> xValues=new ArrayList<>();
        xValues.add("女性");
        xValues.add("男性");

        ArrayList<BarEntry> yValues=new ArrayList<>();
        yValues.add(new BarEntry(new float[]{GN_carnum.size(),G_carnum.size()-GN_carnum.size()},0));
        yValues.add(new BarEntry(new float[]{BN_carnum.size(),B_carnum.size()-BN_carnum.size()},1));
        Log.e("GN_CARNUM",GN_carnum.size()+"");
        Log.e("BN_CARNUM",BN_carnum.size()+"");
        Log.e("G_CARNUM",G_carnum.size()+"");
        Log.e("B_CARNUM",B_carnum.size()+"");

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.YELLOW);

        Legend legend=new Legend();
        legend.setFormSize(20);


        BarDataSet barDataSet =new BarDataSet(yValues,"");
        barDataSet.setStackLabels(new String[]{"有违章","无违章"});
        barDataSet.setValueTextSize(20);
        barDataSet.setColors(colors);
        barDataSet.setBarSpacePercent(60);
        BarData barData=new BarData(xValues,barDataSet);
        barChart.setTouchEnabled(false);

        barChart.setData(barData);
        barChart.invalidate();
    }
}
