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
import com.mad.trafficclient.adapter.AdapterTraffic;
import com.mad.trafficclient.bean.BeanTraffic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Fragment_TrafficLight extends Fragment
{
    int spflag;
	public static String urlTraffic = "http://192.168.3.32:8080/api/v2/get_trafficlight_config";
	ImageSwitcher imageSwitcher;
	int[] images = {R.drawable.hong1, R.drawable.lv1, R.drawable.huang1};
    Spinner spinner;
    String[] resource  = {"路口升序", "路口降序", "红灯升序","红灯降序"};
    ListView listView;
    AdapterTraffic adapterTraffic;
    ArrayList<BeanTraffic> list = new ArrayList<>();
    BeanTraffic beanTraffic ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_layout03, container, false);
		initView(view);
		imageSwitcher = (ImageSwitcher) view.findViewById(R.id.imageS);
		imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
			@Override
			public View makeView() {

				return new ImageView(getActivity());
			}
		});
		//这里用了一种不是无限循环的方式
		imageSwitcher.postDelayed(new Runnable() {
			int index = 0;

			@Override
			public void run() {
				//设置控件的图片为数组的序号
				imageSwitcher.setImageResource(images[index]);
				//用一个if判断完成循环
               if (index == images.length-1)
               	 index = 0;
               else
               	index ++;
               imageSwitcher.postDelayed(this, 1000);
			}
		}, 2000);
		listView = (ListView) view.findViewById(R.id.lv_Traffic);
		adapterTraffic = new AdapterTraffic(getActivity(), list);
		listView.setAdapter(adapterTraffic);
		/*for (int i = 1; i<5; i++){
			volleyTraffic(i);
		}*/
		return view;
	}

	private void initView(View view) {
		spinner = (Spinner) view.findViewById(R.id.spinner1);
		ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,
				android.R.id.text1, resource);
		spinner.setAdapter(adapter);
		//为什么会一进来就执行ListView的点击事件??????
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				Toast.makeText(getActivity(), "Spinner点击事件", Toast.LENGTH_SHORT).show();
				spflag  = i;
				list.clear();
				for (int k = 1; k<5; k++){
					volleyTraffic(k);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
	}

	//请求红绿灯
	public void volleyTraffic(final int i){
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("TrafficLightId", i);
			jsonObject.put("UserName", "user1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlTraffic,
				jsonObject, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject jsonObject) {
				String redTime = jsonObject.optString("RedTime");
				String greenTime = jsonObject.optString("GreenTime");
				String yellowTime = jsonObject.optString("YellowTime");
				beanTraffic = new BeanTraffic(i,
						Integer.valueOf(redTime), Integer.valueOf(greenTime), Integer.valueOf(yellowTime));
				list.add(beanTraffic);
				Collections.sort(list, new para(spflag));
				adapterTraffic.notifyDataSetChanged();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {

			}
		});
		queue.add(jsonObjectRequest);
	}
	//写排序
	public class para implements Comparator<BeanTraffic>{
        int spflag;

		public para(int spflag) {
			this.spflag = spflag;
		}

		@Override
		public int compare(BeanTraffic beanT1, BeanTraffic beanT2) {
			if (spflag ==0){
				//路口升序
				return  beanT1.getRoadId() - beanT2.getRoadId();
			}else if (spflag == 1){
				return beanT2.getRoadId() - beanT1.getRoadId();
			}else if (spflag == 2){
				return beanT1.getRedTime() - beanT2.getRedTime();
			}else {
				return beanT2.getRedTime() - beanT1.getRedTime();
			}
		}
	}
}
