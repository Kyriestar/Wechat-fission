package com.example.wechat.util;

import com.alibaba.fastjson.JSON;

import java.util.Map;

public class JsonTool {

    public static Map json2Map(String jsonStr){
        Map maps = (Map) JSON.parse(jsonStr);
        return maps;
    }
}
