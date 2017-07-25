package com.bart.easyshopping.adapter;

import android.content.Context;
import android.net.Uri;

import com.bart.easyshopping.R;
import com.bart.easyshopping.bean.Wares;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * 作者：${Bart} on 2017/7/24 10:58
 * 邮箱：botao_zheng@163.com
 */

public class HWAdapter extends com.bart.easyshopping.adapter.SimpleAdapter<Wares> {


    public HWAdapter(Context context, List<Wares>wares){
        super(context, R.layout.template_hot_wares,wares);
    }

    @Override
    public void convert(BaseViewHolder viewHolder, Wares item) {

        SimpleDraweeView draweeView = (SimpleDraweeView) viewHolder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(item.getImgUrl()));

//        // 若需要给布局中的控件设置点击事件，这样写就可以了
//        draweeView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        viewHolder.getTextView(R.id.text_title).setText(item.getName());
        viewHolder.getTextView(R.id.text_price).setText(""+item.getPrice());
    }
}
