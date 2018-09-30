package com.mad.trafficclient.bean;

public class BeanMessage {

    public BeanMessage() {
    }

    public BeanMessage(int id, String type, int threshold, int current) {
        this.id = id;
        this.type = type;
        this.threshold = threshold;
        this.current = current;
    }

    /**
     * id : 1
     * type : 报警
     * threshold : 80
     * current : 85
     */

    private int id;
    private String type;
    private int threshold;
    private int current;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
