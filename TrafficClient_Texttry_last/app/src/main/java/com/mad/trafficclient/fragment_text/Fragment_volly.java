/**
 * 
 */
package com.mad.trafficclient.fragment_text;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.mad.trafficclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class Fragment_volly extends Fragment
{
	private LinkedList<Integer> temp=new LinkedList<>();
	private LinkedList<Integer> humi=new LinkedList<>();
	private LinkedList<Integer> pm=new LinkedList<>();
	private LinkedList<Integer> co=new LinkedList<>();
	private LinkedList<Integer> light=new LinkedList<>();
	private LinkedList<Integer> status=new LinkedList<>();
	private  JSONObject params01=new JSONObject();
	private  JSONObject params02=new JSONObject();


	private Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what==1){
				try {
					JSONObject jsonObject=new JSONObject(msg.obj.toString());
					temp.add(Integer.parseInt(jsonObject.getString("temperature")));
					humi.add(Integer.parseInt(jsonObject.getString("humidity")));
					pm.add(Integer.parseInt(jsonObject.getString("pm2.5")));
					co.add(Integer.parseInt(jsonObject.getString("co2")));
					light.add(Integer.parseInt(jsonObject.getString("LightIntensity")));
					while (status.size()<20 || light.size()<20){
						params01.put("UserName","user1");
						params02.put("RoadId","1");
						params02.put("UserName","user1");
						getData(params01,"get_sense_by_name",1);
						getData(params02,"get_road_status",2);
					}
					status.removeFirst();
					humi.removeFirst();
					temp.removeFirst();
					pm.removeFirst();
					co.removeFirst();
					light.removeFirst();
					Log.e("status",status.toString());
					Log.e("light",light.toString());
					Log.e("temp",temp.toString());
					Log.e("co",co.toString());
					Log.e("pm",pm.toString());
					Log.e("humi",humi.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else {
				try {
					JSONObject jsonObject=new JSONObject(msg.obj.toString());
					status.add(Integer.parseInt(jsonObject.getString("Status")));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Log.e("TAGRESPONSE",temp.toString());
			Log.e("TAGRESPONSE",status.toString());
		}
	};



		public void onstart(){
			try {
				params01.put("UserName","user1");
				params02.put("RoadId","1");
				params02.put("UserName","user1");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			getData(params01,"get_sense_by_name",1);
			getData(params02,"get_road_status",2);

		}



	private void getData(JSONObject params, String url, final int info) {
		String seturl="http://192.168.98.64:8080/api/v2/"+url;
		Log.e("TAGPARAMS",params.toString());
		RequestQueue mQueue= Volley.newRequestQueue(getActivity());
		JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, seturl, params, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					if (response.getString("RESULT").equals("S")){
						Toast.makeText(getActivity(), "访问成功", Toast.LENGTH_SHORT).show();
						Message msg=new Message();
						msg.obj=response.toString();
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
