package com.bart.easyshopping.http;

import android.content.Context;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import dmax.dialog.SpotsDialog;

/**
 * 作者：${Bart} on 2017/7/19 15:26
 * 邮箱：botao_zheng@163.com
 */

public abstract class SpotsCallBack<T> extends BaseCallback<T> {

    private Context mContext;
    private SpotsDialog mDialog;

    public SpotsCallBack(Context context){

        mContext = context;
        initSpotsDialog();
    }

    private void initSpotsDialog(){

        mDialog = new SpotsDialog(mContext,"亲爱的别急，正在加载中...");
    }

    public void showDialog(){
        mDialog.show();
    }

    public void dismissDialog(){
        mDialog.dismiss();
    }

    public void setLoadMessage(int resId){
        mDialog.setMessage(mContext.getString(resId));
    }

    @Override
    public void onBeforeRequest(Request request) {
        showDialog();
    }

    @Override
    public void onFailure(Request request, Exception e) {
        dismissDialog();
    }

    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }
}
