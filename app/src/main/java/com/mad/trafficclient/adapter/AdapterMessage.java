package com.mad.trafficclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mad.trafficclient.R;
import com.mad.trafficclient.bean.BeanMessage;

import java.util.List;

public class AdapterMessage extends BaseAdapter {
    Context context;
    List<BeanMessage> list;

    public AdapterMessage(Context context, List<BeanMessage> list) {
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
        view = ViewGroup.inflate(context, R.layout.item_mymessage, null);
        TextView tvId = (TextView) view.findViewById(R.id.tv_item_id);
        TextView tvType = (TextView) view.findViewById(R.id.tv_item_warmType);
        TextView tvThreshold = (TextView) view.findViewById(R.id.tv_item_threshold);
        TextView tvCurrent  = (TextView) view.findViewById(R.id.tv_item_current);

        BeanMessage beanMessage = list.get(i);
        tvId.setText(beanMessage.getId()+"");
        tvType.setText(beanMessage.getType());
        tvThreshold.setText(beanMessage.getThreshold()+"");
        tvCurrent.setText(beanMessage.getCurrent()+"");
        return view;
    }
}
