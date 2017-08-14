package com.bart.easyshopping.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bart.easyshopping.R;
import com.bart.easyshopping.bean.ShoppingCart;
import com.bart.easyshopping.utils.CartProvider;
import com.bart.easyshopping.widget.NumberAddSubView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Iterator;
import java.util.List;

/**
 * 作者：${Bart} on 2017/8/2 15:30
 * 邮箱：botao_zheng@163.com
 */

public class CartAdapter extends SimpleAdapter<ShoppingCart> implements BaseAdapter.OnItemClickListener{

    private CheckBox mCheckBoxAll;
    private TextView totalPriceTv;
    private CartProvider mCartProvider;

    // 将Fragment的checkbox和价格Textview传进来是因为数据都在adapter，在这里进行全选和总价格计算方便点
    public CartAdapter(Context context, List<ShoppingCart> datas,
                       CheckBox checkBoxAll,TextView totalPrice) {

        super(context, R.layout.template_cart,datas);
        this.mCheckBoxAll = checkBoxAll;
        this.totalPriceTv = totalPrice;

        mCartProvider = new CartProvider(context);

        setCheckBox();
        setOnItemClickListener(this);
        showTotalPrice();
    }


    @Override
    public void convert(BaseViewHolder viewHolder, final ShoppingCart item) {

        CheckBox checkBox = (CheckBox) viewHolder.getView(R.id.checkbox);
        checkBox.setChecked(item.isChecked());

        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) viewHolder.getView(R.id.cart_drawee_view);
        simpleDraweeView.setImageURI(Uri.parse(item.getImgUrl()));

        viewHolder.getTextView(R.id.cart_text_title).setText(item.getName());
        viewHolder.getTextView(R.id.cart_text_price).setText("" + item.getPrice() + "元");

        NumberAddSubView numAddSub = (NumberAddSubView) viewHolder.getView(R.id.num_control);
        numAddSub.setValue(item.getCount());

        // 检测用户对添加减少商品数量按钮的点击
        numAddSub.setOnButtonClickListener(new NumberAddSubView.OnButtonClickListener() {
            @Override
            public void onButtonAddClick(View view, int value) {

                item.setCount(value);
                mCartProvider.update(item);
                showTotalPrice();
            }

            @Override
            public void onButtonSubClick(View view, int value) {

                item.setCount(value);
                mCartProvider.update(item);
                showTotalPrice();
            }
        });

    }

    private float getTotalPrice(){

        float totalPrice = 0;


        if (!(datas != null && datas.size()>0)){
            return totalPrice;
        }
        // 将购物车中勾选状态的价格加上
        for (ShoppingCart cart : datas){

            if (cart.isChecked())
                totalPrice += cart.getPrice() * cart.getCount();
        }
        return totalPrice;
    }

    public void showTotalPrice(){

        float totalPrice = getTotalPrice();
        totalPriceTv.setText(""+totalPrice);
    }


    @Override
    public void onItemClick(View view, int position) {

        ShoppingCart cart = getItem(position);
        cart.setChecked(!cart.isChecked());
        notifyItemChanged(position);
        showTotalPrice();

        checkAllListen();

    }

    // 购物车中删除按钮的处理
    public void delCart(){

        if (!(datas != null && datas.size()>0))
            return;


        for (Iterator iterator = datas.iterator();iterator.hasNext();){

            ShoppingCart cart = (ShoppingCart) iterator.next();

            if (cart.isChecked()){
                int position = datas.indexOf(cart);
                mCartProvider.delete(cart);
                iterator.remove();
                notifyItemRemoved(position);
            }
        }

    }

    // 全选checkbox的状态监听
    private void checkAllListen() {

        int checkNum = 0;
        int dataLength = 0;

        if (datas != null){

            dataLength = datas.size();
            for (ShoppingCart cart : datas){
                if (!cart.isChecked()){
                    mCheckBoxAll.setChecked(false);
                    break;
                }
                else {
                    checkNum += 1;
                }
            }

            if (dataLength == checkNum){
                mCheckBoxAll.setChecked(true);
            }
        }
    }

    public void setCheckBox(){

       mCheckBoxAll.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               checkAllOrNot(mCheckBoxAll.isChecked());
               showTotalPrice();
           }
       });
    }

    // 根据参数设置全选或全不选
    public void checkAllOrNot(boolean isChecked){

        if (!(datas != null && datas.size()>0)){
            return ;
        }

        int i = 0;
        for (ShoppingCart cart : datas){

            cart.setChecked(isChecked);
            notifyItemChanged(i);
            i++;
        }

    }
}
