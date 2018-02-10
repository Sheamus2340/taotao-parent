package com.boco.taotao.portal.vo;

/**
 * 返回给前台的广告对象
 * Created by Sheamus on 2018/2/7.
 */
public class ADResult {
    private String src;
    private int width;
    private int height;
    private String alt;
    private String srcB;
    private int widthB;
    private int heightB;
    private String href;

    public String getSrcB() {
        return srcB;
    }

    public void setSrcB(String srcB) {
        this.srcB = srcB;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getWidthB() {
        return widthB;
    }

    public void setWidthB(int widthB) {
        this.widthB = widthB;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getHeightB() {
        return heightB;
    }

    public void setHeightB(int heightB) {
        this.heightB = heightB;
    }
}
