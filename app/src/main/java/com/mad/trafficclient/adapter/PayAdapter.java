package com.mad.trafficclient.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mad.trafficclient.R;
import com.mad.trafficclient.bean.PayRecord;
import com.mad.trafficclient.util.Util;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/17 0017.
 */

 public class PayAdapter extends BaseAdapter {
    ArrayList<PayRecord> listPay;
    Context context;
    
    public PayAdapter(Context context, ArrayList<PayRecord> listPay){
        this.context = context;
        this.listPay = listPay;
    }
    
    @Override
    public int getCount() {
        return listPay.size();
    }

    @Override
    public Object getItem(int i) {
        return listPay.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder2 holder2= null;
       
        if (view == null){
            //实例化各控件
            holder2 = new ViewHolder2();
            view = LayoutInflater.from(context).inflate(R.layout.item_pay, null);
            holder2.payTime = (TextView) view.findViewById(R.id.tv_item_pay_time);
            holder2.current = (TextView) view.findViewById(R.id.tv_item_curren);
            holder2.carId = (TextView) view.findViewById(R.id.tv_item_carid);
            holder2.pay = (TextView) view.findViewById(R.id.tv_item_pay);
            holder2.balance = (TextView) view.findViewById(R.id.tv_item_balance);
            view.setTag(holder2);
        }else {
            holder2 = (ViewHolder2) view.getTag();
        }
        //要获取实体类对象
        PayRecord payRecord = listPay.get(i);
        //获取到item对应的控件之后在这里改变成实体类的响应get方法
        Log.e("TAG", payRecord.getCurrent() + "");
       // holder2.payTime.setText(payRecord.getCurrent());
        holder2.payTime.setText(Util.getTimeFromLong(Long.valueOf(payRecord.getCurrent())));
        holder2.carId.setText(payRecord.getCarId() + "");
        holder2.pay.setText(payRecord.getPay());
        holder2.balance.setText(payRecord.getBalance());
        //在这里显示的时候要调用公共类中的转化
       // holder2.payTime.setText(Util.getTimeFromLong(Long.valueOf(payRecord.getPayTime())));
        return view;
    }
    
    class ViewHolder2{
        TextView current;
        TextView carId;
        TextView pay;
        TextView balance;
        TextView payTime;
    }
}
