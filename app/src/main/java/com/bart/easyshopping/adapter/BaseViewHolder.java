package com.bart.easyshopping.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 作者：${Bart} on 2017/7/24 19:45
 * 邮箱：botao_zheng@163.com
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private SparseArray<View> views;  // views 数组 存放view 实例，避免重复创建实例
    private BaseAdapter.OnItemClickListener mOnItemClickListener;

    public BaseViewHolder(View itemView, BaseAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        itemView.setOnClickListener(this);

        this.mOnItemClickListener = onItemClickListener;
        this.views = new SparseArray<View>();
    }

    // 对各种控件进行 实例化 并 返回
    public ImageView getImageView(int viewId){
        return retrieveView(viewId);
    }
    public TextView getTextView(int viewId){
        return retrieveView(viewId);
    }
    public Button getButton(int viewId){
        return retrieveView(viewId);
    }
    public View getView(int viewId){
        return retrieveView(viewId);
    }


    // 将 view及view的子类转成相对应的 view类型（取决于view的返回类型）
    public <T extends View> T retrieveView(int resId){

        View view = views.get(resId);
        if (view == null){
            view = itemView.findViewById(resId);
            views.put(resId,view);
        }
        return (T) view;
    }

    @Override
    public void onClick(View v) {

        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v,getLayoutPosition());
        }
    }
}
