package com.bart.easyshopping.bean;

import java.io.Serializable;

/**
 * 作者：${Bart} on 2017/7/13 14:19
 * 邮箱：botao_zheng@163.com
 */

public class BaseBean implements Serializable{

    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
