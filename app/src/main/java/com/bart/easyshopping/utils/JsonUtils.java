package com.bart.easyshopping.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * 作者：${Bart} on 2017/7/30 13:51
 * 邮箱：botao_zheng@163.com
 */

public class JsonUtils {

    // HH:mm:ss 24小时制
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    // 单例
    public static Gson getGson(){
        return gson;
    }

    public static <T> T fromJson(String json,Class<T> clz){
        return gson.fromJson(json,clz);
    }

    public static <T> T fromJson(String json, Type type){
        return gson.fromJson(json,type);
    }

    public static String toJson(Object object){
        return gson.toJson(object);
    }

}
