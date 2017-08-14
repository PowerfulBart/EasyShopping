package com.bart.easyshopping.utils;

import android.content.Context;
import android.util.SparseArray;

import com.bart.easyshopping.bean.ShoppingCart;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：${Bart} on 2017/7/30 12:15
 * 邮箱：botao_zheng@163.com
 */

public class CartProvider  {

    // 用SparseArray替代Hashmap来提高效率
    private SparseArray<ShoppingCart> datas = null;

    // SP中购物车数据的的代号
    public static final String CART_JSON = "cart_json";
    private Context mContext;

    public CartProvider(Context context) {

        datas = new SparseArray<>(10);
        mContext = context;
        listToSparse(); // 获取之前存在SP中的购物车数据
    }

    public void put(ShoppingCart cart){

        if (cart != null){

// 获得该条数据的id值（即存在SparseArray中的key），然后去 datas中查看该key是否已经存在，已经存在则直接 count+1
            ShoppingCart temp = datas.get(cart.getId().intValue());

            if (temp != null){
                temp.setCount(temp.getCount()+1);
            }
            else {
                // 如果购物车里还没有该商品，将商品count置为1
                temp = cart;
                temp.setCount(1);

            }
            datas.put(cart.getId().intValue(),temp);
            commit();  // 更新SharedPreferences中的数据

        }
    }

    public void update(ShoppingCart cart){

        if (cart != null){

            // 如果SparseArray中已经存在该数据，默认会更新该数据（覆盖）
            datas.put(cart.getId().intValue(),cart);
            commit();  // 更新SharedPreferences中的数据
        }

    }

    public void delete(ShoppingCart cart){

        datas.delete(cart.getId().intValue());
        commit();  // 更新SharedPreferences中的数据
    }

    public List<ShoppingCart> getAll(){

        return getDataFromLocal();


    }

    // 从SharedPreferences中获取本地数据（json -> 对象）
    public List<ShoppingCart> getDataFromLocal() {

        String json = PreferencesUtils.getString(mContext,CART_JSON);

        List<ShoppingCart> cart = null;
        if (json != null){
            cart = JsonUtils.fromJson(json,new TypeToken<List<ShoppingCart>>(){}.getType());
        }

        return cart;
    }

    // 将 SP中获取的List型的数据转为SparseArray形式，为后续CURD的基础
    private void listToSparse(){

        List<ShoppingCart> carts = getDataFromLocal();

        if (carts != null && carts.size() > 0){

            for (ShoppingCart cart : carts){
                datas.put(cart.getId().intValue(),cart);
            }
        }
    }

    // 将 SparseArray中的数据转为List形式,以便后面转为json
    private List<ShoppingCart> sparseToList(){

        int size = datas.size();
        List<ShoppingCart> carts = new ArrayList<>(size);

        for (int i = 0; i < size; i++){
            carts.add(datas.valueAt(i));
        }
        return carts;
    }

    // 用于每次操作datas后将数据更新到SP中
    private void commit() {

        // 先将SparseArray中的数据转为List形式，再转为json存进SP
        List<ShoppingCart> carts = sparseToList();

        // 将购物车对象数据转为json数据后存进SP中代号为CART_JSON的本地内存中
        PreferencesUtils.putString(mContext,CART_JSON,JsonUtils.toJson(carts)); // 成功转为json


    }
}
