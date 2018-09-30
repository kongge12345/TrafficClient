package com.mad.trafficclient.bean;

/**
 * Created by Administrator on 2018/3/17 0017.
 */

public class PayRecord {
    private String current;
    private int carId;
    private String pay;
    private String balance;
    private String payTime;


    public PayRecord(){
        super();
    }
    public PayRecord(String current, int carId, String pay, String balance, String payTime) {
        this.current = current;
        this.carId = carId;
        this.pay = pay;
        this.balance = balance;
        this.payTime = payTime;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
}
