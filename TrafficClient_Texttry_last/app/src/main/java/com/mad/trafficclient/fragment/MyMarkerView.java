package com.mad.trafficclient.fragment;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.mad.trafficclient.R;

/**
 * Created by 123 on 2018/4/25.
 */

public class MyMarkerView extends MarkerView {
    private TextView textView;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        textView= (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry entry, Highlight highlight) {
        textView.setText("" + entry.getData());


    }

    @Override
    public int getXOffset() {
        return -(getWidth() / 2);

    }

    @Override
    public int getYOffset() {
        return -getHeight();
    }
}
