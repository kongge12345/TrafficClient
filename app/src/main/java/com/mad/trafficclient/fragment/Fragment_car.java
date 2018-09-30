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
import com.mad.trafficclient.util.LoadingDialog;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//查询公交到站距离
public class Fragment_car extends Fragment
{
	private Handler mHandle = new Handler();
	Expandable adapter;
	int dis1, dis2, dis3, dis4;
	ExpandableListView expandableListView;
	final String distanceUrl = "http://192.168.3.32:8080/api/v2/get_bus_station_info";
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1){
				dis1 = msg.arg1;
				dis2 = msg.arg2;
				adapter = new Expandable(dis1, dis2, dis3, dis4);
				expandableListView.setAdapter(adapter);
			}
			if (msg.what == 2){
				dis3 = msg.arg1;
				dis4 = msg.arg2;
				adapter = new Expandable(dis1, dis2, dis3, dis4);
				expandableListView.setAdapter(adapter);
			}
		}
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_car, container, false);
		expandableListView = (ExpandableListView) view.findViewById(R.id.province);
		adapter = new Expandable(dis1, dis2, dis3, dis4);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		three();
	}


	public void three(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				distanceVolley(1);

				distanceVolley(2);

				mHandle.postDelayed(this, 3 * 1000);
			}
		};
		mHandle.postDelayed(runnable, 50);
	}

	public void distanceVolley(final int stationId){
		if (getActivity() == null){
			return;
		}
		int[] dis = null;
		RequestQueue mQueue = Volley.newRequestQueue(getActivity());
		final JSONObject json = new JSONObject();
		try {
			json.put("BusStationId", stationId);
			json.put("UserName", "user1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, distanceUrl, json, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				if (response.optString("RESULT").equals("S")) {
					String detail = response.optString("ROWS_DETAIL");
					int d1 = 0, d2 = 0;
					try {
						JSONArray jsonArray = new JSONArray(detail);
						for (int i=0; i<2; i++){
							JSONObject jsonObject  = (JSONObject) jsonArray.getJSONObject(i);
							if (jsonObject.optInt("BusId") == 1){//一号公交
								d1 = jsonObject.optInt("Distance");
							}
							if (jsonObject.optInt("BusId") == 2){//二号公交
								d2 = jsonObject.optInt("Distance");
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Message message = handler.obtainMessage();
					message.what = stationId;
					message.arg1 = d1;
					message.arg2 = d2;
					handler.sendMessage(message);
				} else if (response.optString("RESULT").equals("F")) {
					Toast.makeText(getActivity(), response.optString("ERRMSG"), Toast.LENGTH_LONG).show();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
			}
		});
		mQueue.add(jsonObjectRequest);
	}

	class Expandable extends BaseExpandableListAdapter{
		int dis1, dis2, dis3, dis4;
		String[] station = {"一号站台",  "二号站台"};
		String[][] distance = null;
		public Expandable(int dis1, int dis2, int dis3, int dis4){
			this.dis1 = dis1;
			this.dis2 = dis2;
			this.dis3 = dis3;
			this.dis4 = dis4;
		}

		TextView getTextView(){
			distance = new String[][]{
					{"一号公交距离一号站台:" + dis1, "二号公交距离一号站台:" + dis2},
					{"二号公交距离一号站台:" + dis3, "二号公交距离二号站台:" + dis4}
			};
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, 64);
			TextView textView = new TextView(getActivity());
			textView.setLayoutParams(lp);
			textView.setGravity(Gravity.CENTER_VERTICAL);
			textView.setPadding(36, 0, 0, 0);
			textView.setTextSize(20);
			return textView;
		}



		@Override
		public int getGroupCount() {
			return station.length;
		}

		@Override
		public int getChildrenCount(int i) {
			return distance[i].length;
		}

		@Override
		public Object getGroup(int i) {
			return station[i];
		}

		@Override
		public Object getChild(int i, int i1) {
			return distance[i][i1];
		}

		@Override
		public long getGroupId(int i) {
			return i;
		}

		@Override
		public long getChildId(int i, int i1) {
			return i1;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
			LinearLayout l1 = new LinearLayout(getActivity());
			l1.setOrientation(LinearLayout.HORIZONTAL);
			TextView textView = getTextView();
			textView.setTextColor(Color.BLACK);
			textView.setText(getGroup(i).toString());
			l1.addView(textView);
			return l1;
		}

		@Override
		public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
			LinearLayout l1 = new LinearLayout(getActivity());
			l1.setOrientation(LinearLayout.HORIZONTAL);
			ImageView imageView= new ImageView(getActivity());
			imageView.setBackgroundResource(R.drawable.bus_2);
			l1.addView(imageView);
			TextView textView = getTextView();
			textView.setText(getChild(i, i1).toString());
			l1.addView(textView);
			return l1;
		}

		@Override
		public boolean isChildSelectable(int i, int i1) {
			return true;
		}
	}
}
