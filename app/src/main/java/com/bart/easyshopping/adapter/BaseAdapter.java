package com.bart.easyshopping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：${Bart} on 2017/7/24 19:37
 * 邮箱：botao_zheng@163.com
 * 将 数据 和 ViewHolder 抽象化
 */

public abstract class BaseAdapter<T,H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder>{

    protected static final String TAG = BaseAdapter.class.getSimpleName();
    protected final Context mContext;
    protected final int layoutResId;
    protected List<T> datas;

    private OnItemClickListener mOnItemClickListener = null;

    // 自定义 回调函数
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public BaseAdapter(Context context,int layoutResId){
        this(context,layoutResId,null);
    }

    public BaseAdapter(Context context,int layoutResId,List<T> datas){
        this.datas = datas == null ? new ArrayList<T>() : datas;
        this.mContext = context;
        this.layoutResId = layoutResId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(layoutResId,parent,false);

        return new BaseViewHolder(view,mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        T item = getItem(position);
        convert((H)holder,item);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public T getItem(int position){
        if (position >= datas.size()) return null;
        return datas.get(position);
    }

    // 下面几个方法用来在RefreshLayout中更新数据时调用
    public void clear(){

        int itemCount = datas.size();
        datas.clear();
        this.notifyItemRangeRemoved(0,itemCount);   // 更新一般用 notifyItemRangeChanged，删除一般用 remove
    }

    public List<T> getData(){

        return datas;
    }

    public void addData(List<T> datas){
        addData(0,datas);
    }

    /*
    add是将传入的参数作为当前List中的一个Item存储，即使你传入一个List也只会另当前的List增加1个元素
    addAll是传入一个List，将此List中的所有元素加入到当前List中，也就是当前List会增加的元素个数为传入的List的大小
     */
    public void addData(int position, List<T> datas){

        if (datas != null && datas.size() >0){

            this.datas.addAll(datas);
            notifyItemRangeChanged(position,datas.size());
        }
    }


    // abstract方法，给子类去进行具体的 控件&数据 绑定，
    public abstract void convert(H viewHolder,T item);

    // 完成监听器的绑定
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
}
