package com.mad.trafficclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mad.trafficclient.R;
import com.mad.trafficclient.bean.BeanTraffic;

import java.util.List;

public class AdapterTraffic extends BaseAdapter {
    Context context;
    List<BeanTraffic> list;

    public AdapterTraffic(Context context, List<BeanTraffic> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = ViewGroup.inflate(context, R.layout.item_traffic, null);
        TextView tvRoadId = (TextView) view.findViewById(R.id.tv_item_roadid);
        TextView tvRed = (TextView) view.findViewById(R.id.tv_item_red);
        TextView tvYellow = (TextView) view.findViewById(R.id.tv_item_yellow);
        TextView tvGreen = (TextView) view.findViewById(R.id.tv_item_green);

        BeanTraffic beanTraffic = list.get(i);
        tvRoadId.setText(beanTraffic.getRoadId()+"");
        tvRed.setText(beanTraffic.getRedTime()+"");
        tvYellow.setText(beanTraffic.getYellowTime()+"");
        tvGreen.setText(beanTraffic.getGreenTime()+"");
        return view;
    }
}
