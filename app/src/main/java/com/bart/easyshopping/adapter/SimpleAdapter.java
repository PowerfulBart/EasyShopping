package com.bart.easyshopping.adapter;

import android.content.Context;

import java.util.List;

/**
 * 作者：${Bart} on 2017/7/24 11:01
 * 邮箱：botao_zheng@163.com
 */

public abstract class SimpleAdapter<T> extends BaseAdapter<T,BaseViewHolder> {

    public SimpleAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public SimpleAdapter(Context context, int layoutResId, List<T> datas) {
        super(context, layoutResId, datas);
    }
}
