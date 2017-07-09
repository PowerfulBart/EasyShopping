package com.bart.easyshopping.bean;

/**
 * 作者：${Bart} on 2017/7/7 16:17
 * 邮箱：botao_zheng@163.com
 */

public class Tab {

    private int icon;
    private int title;
        private Class fragment;

    public Tab(int icon, int title, Class fragment) {
        this.icon = icon;
        this.title = title;
        this.fragment = fragment;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
