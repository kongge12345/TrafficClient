package com.mad.trafficclient.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.mad.trafficclient.R;

public class Fragment_cal extends Fragment {
    Button next;
    CalendarView calendar;
    TextView date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cal,container,false);
        next= (Button) view.findViewById(R.id.frag_btn_next);
        calendar= (CalendarView) view.findViewById(R.id.frag_cal);
        date= (TextView) view.findViewById(R.id.frag_tv_date);
        calendar.getDate();
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                date.append(i+"-"+(i1+1)+"-"+i2);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

}
