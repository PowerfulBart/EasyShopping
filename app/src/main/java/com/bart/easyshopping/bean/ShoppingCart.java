package com.bart.easyshopping.bean;

/**
 * 作者：${Bart} on 2017/7/30 12:11
 * 邮箱：botao_zheng@163.com
 */

public class ShoppingCart extends Wares {

    private int count;
    private boolean isChecked = true;



    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
