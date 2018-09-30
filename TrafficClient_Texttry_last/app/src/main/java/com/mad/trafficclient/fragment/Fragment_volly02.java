/**
 * 
 */
package com.mad.trafficclient.fragment;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mad.trafficclient.util.UrlBean;
import com.mad.trafficclient.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;


public class Fragment_volly02
{
	public static LinkedList<Integer> temp=new LinkedList<>();
	public static LinkedList<Integer> humi=new LinkedList<>();
	public static LinkedList<Integer> pm=new LinkedList<>();
	public static LinkedList<Integer> co=new LinkedList<>();
	public static LinkedList<Integer> light=new LinkedList<>();
	public static LinkedList<Integer> status=new LinkedList<>();
	public static UrlBean urlBean;


	//RequestQueue mQueue= Volley.newRequestQueue(context);

	public static void onSecond(final Context context, final RequestQueue mQueue){
			JSONObject params=new JSONObject();
			try {
				params.put("RoadId","1");
				params.put("UserName","user1");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			urlBean= Util.loadSetting(context);
			String seturl="http://"+urlBean.getUrl()+":8080/api/v2/get_road_status";
			//Log.e("TAGURLSENSE",seturl);
				if (context!=null){
					//Log.e("TAGURLSENSE",seturl);
					if (context!=null){
						//RequestQueue mQueue= Volley.newRequestQueue(context);
						JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, seturl, params, new Response.Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								try {
									if (response.getString("RESULT").equals("S")){
										//Log.e("TAGRESPONSE",response.toString());
										//Toast.makeText(context, "访问成功", Toast.LENGTH_SHORT).show();
										status.add(Integer.parseInt(response.getString("Status")));
										//Log.e("TAGLIGTHT",light.toString());
										if (status.size()<=20){
											onSecond(context,mQueue);
										}else {
											status.removeFirst();

										}

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
								Toast.makeText(context,volleyError.toString(), Toast.LENGTH_SHORT).show();

							}
						});
						mQueue.add(jsonObjectRequest);
					}
				}
			}

	public static void onStart(final Context context,final RequestQueue mQueue){
		JSONObject params=new JSONObject();
		try {
			params.put("UserName","user1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		urlBean=Util.loadSetting(context);
		String seturl="http://"+urlBean.getUrl()+":8080/api/v2/get_all_sense";
		Log.e("TAGURLSENSE",seturl);
		if (context!=null){
			Log.e("TAGURLSENSE",seturl);
			//RequestQueue mQueue= Volley.newRequestQueue(context);
			JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, seturl, params, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					try {
						if (response.getString("RESULT").equals("S")){
							//Log.e("TAGRESPONSE",response.toString());
							//Toast.makeText(context, "访问成功", Toast.LENGTH_SHORT).show();
							//JSONObject jsonObject=new JSONObject(response.toString());
							temp.add(Integer.parseInt(response.getString("temperature")));
							humi.add(Integer.parseInt(response.getString("humidity")));
							pm.add(Integer.parseInt(response.getString("pm2.5")));
							co.add(Integer.parseInt(response.getString("co2")));
							light.add(Integer.parseInt(response.getString("LightIntensity")));
							//Log.e("TAGLIGTHT",light.toString());
							if (light.size()<=20){
								onStart(context,mQueue);
							}else {
								light.removeFirst();
								humi.removeFirst();
								temp.removeFirst();
								co.removeFirst();
								pm.removeFirst();
							}

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
					Toast.makeText(context,volleyError.toString(), Toast.LENGTH_SHORT).show();

				}
			});
			mQueue.add(jsonObjectRequest);
		}
	}

}
