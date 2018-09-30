/**
 * 
 */
package com.mad.trafficclient.fragment;

import android.content.Context;
import android.os.Bundle;
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

import java.util.LinkedList;


public class Fragment_lift_volly extends Fragment
{
	public static LinkedList<Integer> list_pm=new LinkedList<>();
	public static LinkedList<Integer> list_temp=new LinkedList<>();
	public static LinkedList<Integer> list_humi=new LinkedList<>();
	public static LinkedList<Integer> list_co=new LinkedList<>();
	public static UrlBean urlBean;

	public static void getData(final Context context,final RequestQueue mQueue){
		JSONObject params=new JSONObject();
		try {
			params.put("UserName","user1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		urlBean= Util.loadSetting(context);
		String seturl="http://"+urlBean.getUrl()+":8080/api/v2/get_all_sense";
		//Log.e("TAGURLSENSE",seturl);
		if (context!=null){
			//Log.e("TAGURLSENSE",seturl);
			//RequestQueue mQueue= Volley.newRequestQueue(context);
			JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, seturl, params, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					try {
						if (response.getString("RESULT").equals("S")){
							//Log.e("TAGRESPONSE",response.toString());
							list_temp.add(Integer.parseInt(response.getString("temperature")));
							list_humi.add(Integer.parseInt(response.getString("humidity")));
							list_pm.add(Integer.parseInt(response.getString("pm2.5")));
							list_co.add(Integer.parseInt(response.getString("co2")));

							if (list_co.size()<=20){
								getData(context,mQueue);
								//Log.e("TAGSIZE",list_pm.size()+"");
							}else {
								list_humi.removeFirst();
								list_temp.removeFirst();
								list_pm.removeFirst();
								list_co.removeFirst();
								getData(context,mQueue);
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
