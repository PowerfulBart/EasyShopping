package com.bart.easyshopping.bean;

import java.util.List;

/**
 * 作者：${Bart} on 2017/7/21 12:24
 * 邮箱：botao_zheng@163.com
 */

public class Page<T> {
    private  int currentPage;
    private  int pageSize;
    private  int totalPage;
    private  int totalCount;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    private List<T> list;



    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
