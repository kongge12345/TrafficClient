/**
 * 
 */
package com.mad.trafficclient.fragment_text;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.mad.trafficclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;


public class Fragment_volly_status extends Fragment
{
	private static LinkedList<Integer> temp=new LinkedList<>();
	private static LinkedList<Integer> humi=new LinkedList<>();
	private static LinkedList<Integer> pm=new LinkedList<>();
	private static LinkedList<Integer> co=new LinkedList<>();
	private static LinkedList<Integer> light=new LinkedList<>();
	private static LinkedList<Integer> status=new LinkedList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.fragment_volly02,container,false);
		return view;
	}

	public void onStart(){
		while (light.size()<20){
			JSONObject params=new JSONObject();
			try {
				params.put("ReadId","1");
				params.put("UserName","user1");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			String seturl="http://192.168.98.64:8080/api/v2/get_road_status";
			RequestQueue mQueue= Volley.newRequestQueue(getActivity());
			JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, seturl, params, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					try {
						if (response.getString("RESULT").equals("S")){
							Toast.makeText(getActivity(), "访问成功", Toast.LENGTH_SHORT).show();
							JSONObject jsonObject=new JSONObject(response.toString());
							temp.add(Integer.parseInt(jsonObject.getString("temperature")));
							humi.add(Integer.parseInt(jsonObject.getString("humidity")));
							pm.add(Integer.parseInt(jsonObject.getString("pm2.5")));
							co.add(Integer.parseInt(jsonObject.getString("co2")));
							light.add(Integer.parseInt(jsonObject.getString("LightIntensity")));
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

}
