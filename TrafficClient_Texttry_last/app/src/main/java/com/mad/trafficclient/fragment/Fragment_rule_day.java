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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.mad.trafficclient.util.UrlBean;
import com.mad.trafficclient.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 123 on 2018/4/12.
 */

public class Fragment_rule_day extends Fragment {
    private UrlBean urlBean;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_rule_day,container,false);
        barChart= (BarChart) view.findViewById(R.id.barChart_dayrule);
        return view;

    }
    private ArrayList<String> subtime02;
    private ArrayList<String> subtime04;
    private ArrayList<String> subtime06;
    private ArrayList<String> subtime08;
    private ArrayList<String> subtime10;
    private ArrayList<String> subtime12;
    private ArrayList<String> subtime14;
    private ArrayList<String> subtime16;
    private ArrayList<String> subtime18;
    private ArrayList<String> subtime20;
    private ArrayList<String> subtime22;
    private ArrayList<String> subtime24;
    private BarChart barChart;
    private ArrayList<String> timelist;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("TAGSETDATA",msg.obj.toString());
            setData(msg.obj.toString());
            setChartView();
        }
    };

    private void setData(String string) {
        subtime02=new ArrayList<>();
        subtime04=new ArrayList<>();
        subtime06=new ArrayList<>();
        subtime08=new ArrayList<>();
        subtime10=new ArrayList<>();
        subtime12=new ArrayList<>();
        subtime14=new ArrayList<>();
        subtime16=new ArrayList<>();
        subtime18=new ArrayList<>();
        subtime20=new ArrayList<>();
        subtime22=new ArrayList<>();
        subtime24=new ArrayList<>();
        timelist=new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(string);
            Log.e("TAGJSONARRAY",jsonArray.toString());
            for (int i=0;i<jsonArray.length();i++){
                String time=jsonArray.getJSONObject(i).getString("pdatetime");
                Log.e("TAGTIME",time);
                timelist.add(time);
                analyzeTime(time);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void analyzeTime(String time) {

        String subtime=time.substring(time.indexOf(" ")+1,time.length());
        Log.e("SUBTIME",subtime);
        String res = subtime.replace(":","");
        Log.e("SUBTIME---",res);
        //String hou=subtime.substring(subtime.indexOf(":")+1,subtime.length());
        int tim=Integer.parseInt(res);
        if (tim>=10000 && tim<20000){
            subtime02.add(subtime);
        }else if (tim>20001 && tim<40000){
            subtime04.add(subtime);
        }else if (tim>40001 && tim<60000){
            subtime06.add(subtime);
        }else if (tim>60001 && tim<80000){
            subtime08.add(subtime);
        }else if (tim>80000 && tim<100000){
            subtime10.add(subtime);
        }else if (tim>100000 && tim<120000){
            subtime12.add(subtime);
        }else if (tim>120001 && tim<140000){
            subtime14.add(subtime);
        }else if (tim>140001 && tim<160000){
            subtime16.add(subtime);
        }else if (tim>160001 && tim<180000){
            subtime18.add(subtime);
        }else if (tim>180001 && tim<200000){
            subtime20.add(subtime);
        }else if (tim>200001 && tim<220000){
            subtime22.add(subtime);
        }else if (tim>220001 && tim<240000){
            subtime24.add(subtime);
        }
        Log.e("Time",tim+"");
        Log.e("Time",time+"");


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
    }

    private void getData() {
        if (getActivity()!=null){
            JSONObject params=new JSONObject();
            try {
                params.put("UserName","user1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            urlBean= Util.loadSetting(getActivity());
            String setUrl="http://"+urlBean.getUrl()+":8080/api/v2/get_all_car_peccancy";
            Log.e("TAGURL",setUrl);
            RequestQueue mQueue= Volley.newRequestQueue(getActivity());
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, setUrl, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response.optString("RESULT").equals("S")){
                        Log.e("TAGRESPONSE",response.toString());
                        Toast.makeText(getActivity(),"访问成功",Toast.LENGTH_SHORT).show();
                        try {
                            String str=response.getString("ROWS_DETAIL");
                            Message msg=new Message();
                            msg.obj=str;
                            mHandler.sendMessage(msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Toast.makeText(getActivity(),"访问失败",Toast.LENGTH_SHORT).show();
                        Log.e("TAGRESPONSE",response.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getActivity(),volleyError.toString(),Toast.LENGTH_SHORT).show();
                    Log.e("TAGRESPONSE",volleyError.toString());
                }
            });
            mQueue.add(jsonObjectRequest);

        }
    }

    private void setChartView() {
        //设置Y轴上的单位
        //      mChart.setUnit("%");
        XAxis xAxis=barChart.getXAxis();
        //xAxis.setLabelsToSkip(-1);
        xAxis.setSpaceBetweenLabels(-6);
        //xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12);
       // xAxis.setValueFormatter((XAxisValueFormatter) new PercentFormatter());
        /*xAxis.setAvoidFirstLastClipping(false);
        xAxis.setTextSize(30);
        xAxis.setSpaceBetweenLabels(5);*/
        //xAxis.setEnabled(false);

        //xAxis.setGridLineWidth(10);
        //xAxis.setGranularity


        YAxis yAxis=barChart.getAxisLeft();
        yAxis.setTextSize(30);
        //yAxis.setShowOnlyMinMax(true);




        YAxis yAxis1=barChart.getAxisRight();
        yAxis1.setEnabled(false);


        ArrayList<String> xValues=new ArrayList<>();
        xValues.add("0:00:01-2:00:00");
        xValues.add("2:00:01-4:00:00");
        xValues.add("4:00:01-6:00:00");
        xValues.add("6:00:01-8:00:00");
        xValues.add("8:00:01-10:00:00");
        xValues.add("10:00:01-12:00:00");
        xValues.add("12:00:01-14:00:00");
        xValues.add("14:00:01-16:00:00");
        xValues.add("16:00:01-18:00:00");
        xValues.add("18:00:01-20:00:00");
        xValues.add("20:00:01-22:00:00");
        xValues.add("22:00:01-24:00:00");

        ArrayList<BarEntry> yValues=new ArrayList<>();
        float what=subtime02.size()/timelist.size();
        Log.e("TAGTIMELIST",what+"");
        int fie=subtime02.size()/timelist.size();
        float fie0=fie;
        float sec=subtime04.size()/timelist.size();
        Log.e("TAGTIMEfie",(fie+""));
        Log.e("TAGTIMEsec",(subtime04.size()/timelist.size()+""));
        yValues.add(new BarEntry(subtime02.size()*100/timelist.size(),0));
        yValues.add(new BarEntry(subtime04.size()*100/timelist.size(),1));
        yValues.add(new BarEntry(subtime06.size()*100/timelist.size(),2));
        yValues.add(new BarEntry(subtime08.size()*100/timelist.size(),3));
        yValues.add(new BarEntry(subtime10.size()*100/timelist.size(),4));
        yValues.add(new BarEntry(subtime12.size()*100/timelist.size(),5));
        yValues.add(new BarEntry(subtime14.size()*100/timelist.size(),6));
        yValues.add(new BarEntry(subtime16.size()*100/timelist.size(),7));
        yValues.add(new BarEntry(subtime18.size()*100/timelist.size(),8));
        yValues.add(new BarEntry(subtime20.size()*100/timelist.size(),9));
        yValues.add(new BarEntry(subtime22.size()*100/timelist.size(),10));
        yValues.add(new BarEntry(subtime24.size()*100/timelist.size(),11));

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.GRAY);
        colors.add(Color.MAGENTA);
        colors.add(Color.BLACK);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.DKGRAY);
        colors.add(Color.MAGENTA);

        Legend legend=barChart.getLegend();
        legend.setEnabled(false);

        BarDataSet barDataSet=new BarDataSet(yValues,"");
        barDataSet.setColors(colors);
        barDataSet.setValueTextSize(20);
        BarData barData =new BarData(xValues,barDataSet);
        barData.setValueFormatter(new PercentFormatter());


       /* MarkerView markerView= new MarkerView() {
            @Override
            public void refreshContent(Entry entry, Highlight highlight) {

            }

            @Override
            public int getXOffset() {
                return 0;
            }

            @Override
            public int getYOffset() {
                return 0;
            }
        };*/
        //barData.setValueFormatter(new DefaultValueFormatter(2));
       // mSaleNumberBarDateSet.setValueFormatter（new DefaultValueFormatter（2））;
       /* barDataSet.setBarSpacePercent(80);
        barData.setGroupSpace(3);*/
        

        barChart.setData(barData);
        barChart.setDescription("");
        barChart.invalidate();

       /* if (barDataSet.getBarSpacePercent()>20){
            barDataSet.setDrawValues(false);
        }*/

    }
}
