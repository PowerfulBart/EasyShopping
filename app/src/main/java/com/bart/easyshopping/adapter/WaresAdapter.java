package com.bart.easyshopping.adapter;

import android.content.Context;
import android.net.Uri;

import com.bart.easyshopping.R;
import com.bart.easyshopping.bean.Wares;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * 作者：${Bart} on 2017/7/25 18:56
 * 邮箱：botao_zheng@163.com
 */

public class WaresAdapter extends SimpleAdapter<Wares> {

    public WaresAdapter(Context context,List<Wares> datas) {
        super(context, R.layout.template_grid_wares, datas);
    }

    @Override
    public void convert(BaseViewHolder viewHolder, Wares item) {

        SimpleDraweeView draweeView = (SimpleDraweeView) viewHolder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(item.getImgUrl()));

        viewHolder.getTextView(R.id.text_title).setText(item.getName());
        viewHolder.getTextView(R.id.text_price).setText(""+item.getPrice() + "元");

    }
}
