package com.bart.easyshopping.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bart.easyshopping.R;
import com.bart.easyshopping.bean.Wares;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * 作者：${Bart} on 2017/7/21 12:25
 * 邮箱：botao_zheng@163.com
 */

public class HotWaresAdapter extends RecyclerView.Adapter<HotWaresAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<Wares> mList;

    public HotWaresAdapter(Context context, List<Wares> list) {
        mContext = context;
        mList = list;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView mSimpleDraweeView;
        private TextView titleTv;
        private TextView priceTv;

        public ViewHolder(View itemView) {
            super(itemView);

            mSimpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.hot_wares_drawee_view);
            titleTv = (TextView) itemView.findViewById(R.id.text_title);
            priceTv = (TextView) itemView.findViewById(R.id.text_price);

        }
    }


    @Override
    public HotWaresAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.from(mContext).inflate(R.layout.template_hot_wares,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotWaresAdapter.ViewHolder holder, int position) {

        holder.mSimpleDraweeView.setImageURI(Uri.parse(mList.get(position).getImgUrl()));
        holder.titleTv.setText(mList.get(position).getName());
        holder.priceTv.setText(""+mList.get(position).getPrice() + "元");


    }

    @Override
    public int getItemCount() {

        if (mList != null && mList.size()>0)
            return mList.size();
        return 0;
    }

    public List<Wares> getDatas(){
        return mList;
    }

    public Wares getData(int position){
        return mList.get(position);
    }


    public void clearData(){
//        mList.clear();
//        // 错误：mList没清空后，mList.size 已为0，
//        notifyItemRangeChanged(0,mList.size());

        int itemCount = mList.size();
        mList.clear();
        this.notifyItemRangeChanged(0,itemCount);
    }


    public void addData(List<Wares> mList){

        addData(0,mList);
    }


    public void addData(int position,List<Wares> mList){

        if (mList != null && mList.size()>0){
            mList.addAll(mList);
            notifyItemRangeChanged(position,mList.size());
        }
    }
}
