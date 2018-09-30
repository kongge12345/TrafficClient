/**
 * 
 */
package com.mad.trafficclient.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mad.trafficclient.R;
import com.mad.trafficclient.adapter.PayAdapter;
import com.mad.trafficclient.bean.PayRecord;
import com.mad.trafficclient.db.DBHelper;

import java.util.ArrayList;


public class Fragment_order extends Fragment implements View.OnClickListener
{
	TextView tvPerson, tvRecord, tvWarming;
	ListView lvPay;
	PayAdapter  payAdapter;
	ArrayList<PayRecord> listPay = new ArrayList<>();
	PayRecord payRecord;
	int id;
	int carId;
	int money;
	String person;
	String time;

	DBHelper dbHelper;
	SQLiteDatabase db;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_order, container, false);
		lvPay = (ListView) view.findViewById(R.id.lv_pay);

		tvPerson = (TextView) view.findViewById(R.id.tv_person);
		tvRecord = (TextView) view.findViewById(R.id.tv_pay_record);
		tvWarming = (TextView) view.findViewById(R.id.tv_warming);
		tvWarming.setOnClickListener(this);
		tvRecord.setOnClickListener(this);
		tvPerson.setOnClickListener(this);

		payAdapter = new PayAdapter(getActivity(), listPay);
		lvPay.setAdapter(payAdapter);

		getData();
		return view;
	}


	private void getData() {
		dbHelper=new DBHelper(getActivity());
		db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query("account", null, null, null, null, null, "time desc");
		while (cursor.moveToNext()) {
			id = cursor.getInt(cursor.getColumnIndex("id"));
			carId = cursor.getInt(cursor.getColumnIndex("car_id"));
			money = cursor.getInt(cursor.getColumnIndex("money"));
			person = cursor.getString(cursor.getColumnIndex("person"));
			time = (cursor.getString(cursor.getColumnIndex("time")));
			payRecord = new PayRecord();
			payRecord.setCurrent(time);
			payRecord.setCarId(id);
			payRecord.setPay("充值: "+money+ "元");
			payRecord.setBalance(person);
			payRecord.setPayTime(time);
			listPay.add(payRecord);

			payAdapter.notifyDataSetChanged();
		}
	}
    //模拟Fragament跳转
	@Override
	public void onClick(View view) {
		if (view == tvPerson ){
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontent,
					new Fragment_person()).commit();
		}
		if (view == tvRecord){
			//跳转充值记录
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontent,
					new Fragment_order()).commit();
		}
		;if (view == tvWarming){
			//跳转阈值警告
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontent,
					new Fragment_yuzhiset()).commit();
		}
	}
}
