package com.bart.easyshopping.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.bart.easyshopping.R;
import com.bart.easyshopping.bean.ShoppingCart;
import com.bart.easyshopping.bean.Wares;
import com.bart.easyshopping.utils.CartProvider;
import com.bart.easyshopping.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * 作者：${Bart} on 2017/7/24 10:58
 * 邮箱：botao_zheng@163.com
 */

public class HWAdapter extends com.bart.easyshopping.adapter.SimpleAdapter<Wares> {

    private CartProvider mCartProvider;

    public HWAdapter(Context context, List<Wares>wares){
        super(context, R.layout.template_hot_wares,wares);
        mCartProvider = new CartProvider(context);
    }

    @Override
    public void convert(BaseViewHolder viewHolder, final Wares item) {

        SimpleDraweeView draweeView = (SimpleDraweeView) viewHolder.getView(R.id.hot_wares_drawee_view);
        draweeView.setImageURI(Uri.parse(item.getImgUrl()));

//        // 若需要给布局中的控件设置点击事件，这样写就可以了
//        draweeView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        viewHolder.getTextView(R.id.text_title).setText(item.getName());
        viewHolder.getTextView(R.id.text_price).setText(""+item.getPrice() + "元");

        Button addToCartBtn = (Button) viewHolder.getView(R.id.add_to_cart_btn);
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCartProvider.put(convertWaresToShoppingCart(item));
                ToastUtils.show(mContext,"已添加至购物车" + convertWaresToShoppingCart(item).getName(),1);
            }
        });
    }

    public ShoppingCart convertWaresToShoppingCart(Wares item){

        ShoppingCart cart = new ShoppingCart();

        cart.setId(item.getId());
        cart.setDescription(item.getDescription());
        cart.setImgUrl(item.getImgUrl());
        cart.setName(item.getName());
        cart.setPrice(item.getPrice());


        return cart;

    }
}
