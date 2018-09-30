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
import com.mad.trafficclient.R;
import com.mad.trafficclient.adapter.AdapterMessage;
import com.mad.trafficclient.bean.BeanMessage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//实现第18题我的消息,需要和沈慧慧协作完成
public class Fragment_MyMessage extends Fragment_18
{

	public final String url = "http://192.168.3.32:8080/api/v2/get_all_sense";
	int spinnerFlag = 1;
	Spinner spinner;
	ListView lvMessage;
	AdapterMessage adapterMessage;
	BeanMessage beanMessage;
	List<BeanMessage> list = new ArrayList<>();
    String[] type = {"全部", "湿度", "温度", "co2", "光照", "PM2.5"};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_mymessage, container, false);
		spinner = (Spinner) view.findViewById(R.id.spinner1);
		ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,
				android.R.id.text1, type);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				spinnerFlag = i + 1;
				volleySense();
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
		lvMessage = (ListView) view.findViewById(R.id.lv_message);
		adapterMessage = new AdapterMessage(getActivity(), list);
		lvMessage.setAdapter(adapterMessage);
		volleySense();
		return view;
	}

		//请求全部传感器
		public void volleySense(){
			RequestQueue queue = Volley.newRequestQueue(getActivity());
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("UserName", "user1");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
					jsonObject, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject jsonObject) {
					int temp = jsonObject.optInt("temperature");
					int co2 = jsonObject.optInt("co2");
					int light = jsonObject.optInt("LightIntensity");
					int humi = jsonObject.optInt("humidity");
					int pm25 = jsonObject.optInt("pm2.5");
					if (spinnerFlag == 1){
						list.clear();
						beanMessage = new BeanMessage(1, "温度报警", 80, temp);
						list.add(beanMessage);
						beanMessage = new BeanMessage(2, "co2报警", 80, co2);
						list.add(beanMessage);
						beanMessage = new BeanMessage(3, "光照报警", 80, light);
						list.add(beanMessage);
						beanMessage = new BeanMessage(4, "湿度报警", 80, humi);
						list.add(beanMessage);
						beanMessage = new BeanMessage(5, "pm2.5报警", 80, pm25);
						list.add(beanMessage);
						adapterMessage.notifyDataSetChanged();
					}
					if (spinnerFlag == 2){
						list.clear();
						beanMessage = new BeanMessage(4, "湿度报警", 80, humi);
						list.add(beanMessage);
						adapterMessage.notifyDataSetChanged();
					}
					if (spinnerFlag == 3){
						list.clear();
						beanMessage = new BeanMessage(3, "温度报警", 80, temp);
						list.add(beanMessage);
						adapterMessage.notifyDataSetChanged();
					}
					if (spinnerFlag == 4){
						list.clear();
						beanMessage = new BeanMessage(4, "co2报警", 80, light);
						list.add(beanMessage);
						adapterMessage.notifyDataSetChanged();
					}
					if (spinnerFlag == 5){
						list.clear();
						beanMessage = new BeanMessage(5, "光照报警", 80, light);
						list.add(beanMessage);
						adapterMessage.notifyDataSetChanged();
					}
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError volleyError) {

				}
			});
			queue.add(jsonObjectRequest);
		}
	}

