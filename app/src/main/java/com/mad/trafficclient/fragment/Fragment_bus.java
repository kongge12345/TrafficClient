package com.mad.trafficclient.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mad.trafficclient.R;
import com.mad.trafficclient.adapter.BusAdapter;

import java.util.ArrayList;
import java.util.List;

public class Fragment_bus extends Fragment {
    ListView listView;
    BusAdapter adapter;
    List list=new ArrayList();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_zhidingcar,container,false);
        listView= (ListView) view.findViewById(R.id.lv_custom_bus);
        for (int i=0;i<4;i++){
            list.add(i);
        }
        adapter=new BusAdapter(getActivity(),list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontent,new Fragment_picture()).commit();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
