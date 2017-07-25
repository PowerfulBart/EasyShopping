package com.bart.easyshopping;

/**
 * 作者：${Bart} on 2017/7/20 17:24
 * 邮箱：botao_zheng@163.com
 */

public class Constants {


    public class API{

        public static final String BASE_API = "http://112.124.22.238:8081/course_api/";   // 基本 URI

        public static final String CAMPAIGN_HOME = BASE_API + "campaign/recommend";  // "首页"数据

        public static final String WARES_HOT = BASE_API + "wares/hot";  // "热卖"数据

        public static final String CATEGORY_LIST = BASE_API +"category/list";    // "分类"一级标题

        public static final String CATEGORY_BANNER = BASE_API +"banner/query";    // "分类"轮播广告

        public static final String CATEGORY_WARES = BASE_API +"wares/list";     // "分类"二级内容


    }

}
