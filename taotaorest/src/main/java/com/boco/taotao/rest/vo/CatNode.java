package com.boco.taotao.rest.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 返回商品分类的节点的定义
 * Created by Sheamus on 2018/2/6.
 */
public class CatNode {
    @JsonProperty("u")
    private String url;//url
    @JsonProperty("n")
    private String name;//名字
    @JsonProperty("i")
    private List<?> item;//类别

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<?> getItem() {
        return item;
    }

    public void setItem(List<?> item) {
        this.item = item;
    }
}
