package com.example.wechat.msg;

import java.util.Map;

public class TempMessage {
    private String touser;
    private String template_id;
    private Map<String, TempMessageData> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public Map<String, TempMessageData> getData() {
        return data;
    }

    public void setData(Map<String, TempMessageData> data) {
        this.data = data;
    }
}
