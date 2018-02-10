package com.boco.taotao.vo;

import java.util.List;

/**
 * 对EasyUI的DataGrid组件的数据返回的类
 * Created by Sheamus on 2018/1/19.
 */
public class EUDataGridResult {
    //查询数据总条数
    private long total;
    //本次查询的结果
    private List<?> rows;

    public EUDataGridResult() {
    }

    public EUDataGridResult(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
