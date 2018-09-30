package com.mad.trafficclient.fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mad.trafficclient.MainActivity;
import com.mad.trafficclient.R;


import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by YangNan on 2018/3/12.
 */
//个人中心的阈值警告界面
public class Fragment_yuzhiset extends Fragment implements View.OnClickListener{
    TextView tvWarming;
    TextView tvPerson, tvRecord, tvWarmingSelf;
    final String strUrl = "http://192.168.3.32:8080/api/v2/get_car_account_balance";
    int balance;
    Button btnWarning;
    EditText etWarning;
    int carId;
    int intWarning;
    SharedPreferences sharedPreferences;

    Handler handle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                if (msg.arg1 < intWarning){
                    notification(1, balance);
                }
            }
            if (msg.what == 2){
                if (msg.arg1 < intWarning){
                    //Toast.makeText(getActivity(), "二号小车发出警告", Toast.LENGTH_SHORT).show();
                    notification(2, balance);
                }
            }

            if (msg.what == 3){
                if (msg.arg1 < intWarning){

                    notification(3, balance);
                }
            }

            if (msg.what == 4){
                if (msg.arg1 < intWarning){

                    notification(4, balance);
                }
            }
        }
    };

//阈值设置
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_yuzhiset,container,false);
        tvWarming = (TextView) view.findViewById(R.id.tv_warming);
        btnWarning = (Button) view.findViewById(R.id.btnWarning);
        btnWarning.setOnClickListener(this);
        etWarning = (EditText) view.findViewById(R.id.et_warming);
        tvPerson = (TextView) view.findViewById(R.id.tv_person);
        tvRecord = (TextView) view.findViewById(R.id.tv_pay_record);
        tvWarmingSelf = (TextView) view.findViewById(R.id.tv_warming_self);
        tvWarmingSelf.setOnClickListener(this);
        tvRecord.setOnClickListener(this);
        tvPerson.setOnClickListener(this);
        return view;
    }

    public void onClick(View view) {
        if (view == btnWarning){
            String etwarning = etWarning.getText().toString().trim();
            intWarning = Integer.valueOf(etwarning);
            //要把输入的值用偏好设置设置一下
            sharedPreferences = getActivity().getSharedPreferences("warming", 0);
            sharedPreferences.edit().putInt("warming", intWarning).commit();
            int money = sharedPreferences.getInt("warming", 50);
            //改变TextView的值
            tvWarming.setText(getString(R.string.warming, money+""));
            //获取用户输入的警告值，查询用户余额，拿我们输入的值和请求的值相比较
            //现在请求Volly方法中将每号小车的余额返回
            questVolley1(1);
            questVolley1(2);
        }
        if (view == tvPerson ){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontent,
                    new Fragment_person()).commit();
        }
        if (view == tvRecord){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontent,
                    new Fragment_order()).commit();
        }
        ;if (view == tvWarmingSelf){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontent,
                    new Fragment_yuzhiset()).commit();
        }
    }
    public void questVolley1(final int carId) {

        this.carId = carId;
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        JSONObject json = new JSONObject();
        try {
            json.put("CarId", carId);
            json.put("UserName", "user1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, strUrl, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("TAG", response.toString());
                //LoadingDialog.disDialog();
                if (response.optString("RESULT").equals("S")) {
                    balance = response.optInt("Balance");
                    Message message = new Message();
                    message.what = carId;
                    message.arg1 = balance;
                    handle.sendMessage(message);
                } else if (response.optString("RESULT").equals("F")) {
                    Toast.makeText(getActivity(), response.optString("ERRMSG"), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG volley error", error.toString());
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(jsonObjectRequest);
    }


    public void notification(int carId, int balance){

        Intent intent = new Intent(getActivity(), MainActivity.class);
       PendingIntent pi = PendingIntent.getActivity(getActivity(), 0, intent, 0);
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(getActivity()).setContentTitle("警告通知")
                .setContentText("车号：" + carId  +"余额："+ balance +  "告警值:"+intWarning)
                .setSmallIcon(R.drawable.bus_2).setContentIntent(pi).setAutoCancel(true).build();

        manager.notify(1, notification);
    }
}

