package com.bart.easyshopping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bart.easyshopping.R;
import com.bart.easyshopping.bean.HomeCampaign;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 作者：${Bart} on 2017/7/20 16:18
 * 邮箱：botao_zheng@163.com
 */

public class HomeCampaignAdapter extends RecyclerView.Adapter<HomeCampaignAdapter.ViewHolder> {

    private static int VIEW_TYPE_L;
    private static int VIEW_TYPE_R;

    private LayoutInflater mInflater;
    private List<HomeCampaign> mList;
    private Context mContext;

    // 构造，将数据传进来
    public HomeCampaignAdapter(List<HomeCampaign> list, Context context) {
        mList = list;
        mContext = context;
    }


    // ViewHolder 根据 onCreateViewHolder 传过来的View，对View中子View进行实例化
    // 在Fragment中只要通过
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle;
        ImageView imageviewBig;
        ImageView imageviewSmallTop;
        ImageView imageviewSmallBottom;

        public ViewHolder(View itemView) {
            super(itemView);  //调父的构造，在父中 this.itemView = itemView;

            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            imageviewBig = (ImageView) itemView.findViewById(R.id.imgview_big);
            imageviewSmallTop = (ImageView) itemView.findViewById(R.id.imgview_small_top);
            imageviewSmallBottom = (ImageView) itemView.findViewById(R.id.imgview_small_bottom);
        }
    }

    // 参数二即为 要加载的布局的类型
    // onCreateViewHolder的作用为：将欲显示的布局加载进来，
    // 并作为参数传进ViewHolder的构造函数中，最后将New出来的ViewHolder实例返回,接下来执行onBindViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mInflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_L){

            View leftView = mInflater.inflate(R.layout.template_home_cardview,parent,false);
            return new ViewHolder(leftView);
        }

        View rightView = mInflater.inflate(R.layout.template_home_cardview1,parent,false);
        return new ViewHolder(rightView);
    }

    // onCreateViewHolder执行完就调用该函数，将holder传了过来，绑定数据
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        HomeCampaign homeCampaign = mList.get(position);
        holder.textTitle.setText(homeCampaign.getTitle());

        Picasso.with(mContext).load(homeCampaign.getCpOne().getImgUrl()).into(holder.imageviewBig);
        Picasso.with(mContext).load(homeCampaign.getCpTwo().getImgUrl()).into(holder.imageviewSmallTop);
        Picasso.with(mContext).load(homeCampaign.getCpThree().getImgUrl()).into(holder.imageviewSmallBottom);

    }

    // return data.size()
    @Override
    public int getItemCount() {
        return mList.size();
    }


}
