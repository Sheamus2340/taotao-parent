package com.boco.taotao.rest.vo;

import java.util.List;

/**
 * 返回给其他调用的JSON数据
 * Created by Sheamus on 2018/2/6.
 */
public class CatResult {
    private List<?> data;

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
