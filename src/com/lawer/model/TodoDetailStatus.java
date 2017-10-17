package com.lawer.model;

import java.util.Date;

public class TodoDetailStatus {

    private String name;

    private int order;

    private Integer lptId;

    private String status;

    private Date time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Integer getLptId() {
        return lptId;
    }

    public void setLptId(Integer lptId) {
        this.lptId = lptId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
