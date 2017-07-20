package com.bart.easyshopping.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 作者：${Bart} on 2017/7/14 19:46
 * 邮箱：botao_zheng@163.com
 */

public class OkHttpHelper {

    // http://www.jianshu.com/p/9a1f37ba526b 使用单例，拦截器等优化

    public static final String TAG = "OkHttpHelper";

    private static OkHttpHelper mInstance;  //用于单例返回，static
    private OkHttpClient mClient;
    private Handler mHandler;
    private Gson mGson;

    static {
        mInstance = new OkHttpHelper();  // Helper 实例化，单例
    }

    private OkHttpHelper(){

        mHandler = new Handler(Looper.getMainLooper()); // Handler实例化，用于主线程
        mClient = new OkHttpClient();  // client 初始化
        mGson = new Gson();  // Gson实例化

        mClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mClient.setReadTimeout(10,TimeUnit.SECONDS);
        mClient.setWriteTimeout(30,TimeUnit.SECONDS);
    }

    public static OkHttpHelper getInstance(){
        return mInstance; //单例返回 Helper类实例
    }

    public void get(String url,BaseCallback callback){

        Request request = buildGetRequest(url);
        doRequest(request,callback);

    }

    public void post(String url, Map<String,String> params,BaseCallback callback){

    }


    public Request buildGetRequest(String url){

        return buildRequest(url,HttpMethodType.GET,null);
    }

    public Request buildPostRequest(String url,Map<String,String> params){

        return buildRequest(url,HttpMethodType.POST,params);
    }

    private Request buildRequest(String url,HttpMethodType type,Map<String,String> params){

        Request.Builder builder = new Request.Builder()
                .url(url);

        if (type == HttpMethodType.GET){

            builder.get();

        }else if (type == HttpMethodType.POST){

            RequestBody body = buildRequestBody(params);
            builder.post(body);
        }
        return builder.build();
    }


    public void doRequest(Request request, final BaseCallback callback){

        callback.onBeforeRequest(request);

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure(request,e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                callback.onResponse(response);

                if (response.isSuccessful()){
                    String resultStr = response.body().string();
                    Log.d(TAG, "resultStr: " + resultStr);

                    if (callback.mType == String.class){
                        callbackSuccess(callback,response,resultStr);
                    }else {

                        try {
                            Object object = mGson.fromJson(resultStr,callback.mType);
                            callbackSuccess(callback,response,object);

                        } catch (JsonParseException e) {
                            callback.onError(response,response.code(),e);
                        }
                    }
                }else {
                    callbackError(callback,response,null);
                }
            }
        });
    }

    private void callbackSuccess(final BaseCallback callback, final Response response, final Object object){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response,object);
            }
        });
    }

    private void callbackError(final BaseCallback callback, final Response response, final Exception e){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response,response.code(),e);
            }
        });
    }

    private RequestBody buildRequestBody(Map<String,String> params){

        FormEncodingBuilder builder = new FormEncodingBuilder();

        for (Map.Entry<String,String> entry : params.entrySet()){

            builder.add(entry.getKey(),entry.getValue());
        }

        return builder.build();
    }

    enum HttpMethodType{
        GET,
        POST
    }




}
