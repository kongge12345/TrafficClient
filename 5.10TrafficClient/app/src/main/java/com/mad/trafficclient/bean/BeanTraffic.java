package com.mad.trafficclient.bean;

public class BeanTraffic {


    public BeanTraffic(int roadId, int redTime, int greenTime, int yellowTime) {
        this.roadId = roadId;
        this.redTime = redTime;
        this.greenTime = greenTime;
        this.yellowTime = yellowTime;
    }

    /**
     * roadId : 1
     * redTime : 1
     * greenTime : 1
     * yellowTime : 1
     */

    private int roadId;
    private int redTime;
    private int greenTime;
    private int yellowTime;

    public int getRoadId() {
        return roadId;
    }

    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }

    public int getRedTime() {
        return redTime;
    }

    public void setRedTime(int redTime) {
        this.redTime = redTime;
    }

    public int getGreenTime() {
        return greenTime;
    }

    public void setGreenTime(int greenTime) {
        this.greenTime = greenTime;
    }

    public int getYellowTime() {
        return yellowTime;
    }

    public void setYellowTime(int yellowTime) {
        this.yellowTime = yellowTime;
    }
}
