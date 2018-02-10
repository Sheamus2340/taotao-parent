package com.boco.taotao.vo;

/**
 * EasyUI的树形空间的单个节点
 * Created by Sheamus on 2018/1/21.
 */
public class EUTreeNode {
    private long id;
    private String text;
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
