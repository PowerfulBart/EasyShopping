package com.bart.easyshopping.bean;

/**
 * 作者：${Bart} on 2017/7/13 14:19
 * 邮箱：botao_zheng@163.com
 */

public class Category extends BaseBean{

    private String name;

    public Category(String name) {
        this.name = name;
    }

    public Category(String name,long id){
        this.id = id;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
