package com.symbio.dashboard.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {
    public String code;
    public String enUs;
    public String zhCn;
    public String formatter;

    public Message(String ec, String en, String zh, String formatter){
        this.code = ec;
        this.enUs = en;
        this.zhCn = zh;
        this.formatter = formatter;
    }
}
