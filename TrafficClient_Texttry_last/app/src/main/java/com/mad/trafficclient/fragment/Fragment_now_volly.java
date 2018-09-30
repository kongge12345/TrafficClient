/**
 * 
 */
package com.mad.trafficclient.fragment;

import android.content.Context;
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
import com.mad.trafficclient.R;
import com.mad.trafficclient.util.UrlBean;
import com.mad.trafficclient.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;


public class Fragment_now_volly extends Fragment
{
	public static LinkedList<Integer> pm_list=new LinkedList<>();
	public static LinkedList<Integer> temp_list=new LinkedList<>();
	public static LinkedList<Integer> light_list=new LinkedList<>();
	public static UrlBean urlBean;
	public static Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

		}
	};

	public static void getData(final Context context,final RequestQueue mQueue) {
		JSONObject params=new JSONObject();
		try {
			params.put("UserName","user1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		urlBean= Util.loadSetting(context);
		String seturl="http://"+urlBean.getUrl()+":8080/api/v2/get_all_sense";
		Log.e("TAGURL",seturl);
		if (context!=null){
			//RequestQueue mQueue= Volley.newRequestQueue(context);
			JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, seturl, params, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					try {
						if (response.getString("RESULT").equals("S")){
							Toast.makeText(context, "访问成功", Toast.LENGTH_SHORT).show();
							pm_list.add(Integer.parseInt(response.getString("pm2.5")));
							temp_list.add(Integer.parseInt(response.getString("temperature")));
							light_list.add(Integer.parseInt(response.getString("LightIntensity")));
							if (temp_list.size()<=6){
								getData(context,mQueue);
							}else {
								pm_list.removeFirst();
								temp_list.removeFirst();
								light_list.removeFirst();
							}
							Message msg=new Message();
							msg.obj=response.toString();
							mHandler.sendMessage(msg);
						}else {
							Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError volleyError) {
					Toast.makeText(context, volleyError.toString(), Toast.LENGTH_SHORT).show();
				}
			});
			mQueue.add(jsonObjectRequest);
		}
	}

}
