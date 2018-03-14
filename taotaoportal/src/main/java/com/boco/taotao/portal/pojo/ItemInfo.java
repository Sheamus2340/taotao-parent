package com.boco.taotao.portal.pojo;

import com.boco.taotao.pojo.TbItem;

/**
 * 用于给前端接收的bean，因为前端要一个images的字段
 * Created by Sheamus on 2018/3/14.
 */
public class ItemInfo extends TbItem {

    public String[] getImages() {
        //继承父类的方法
        String image = getImage();
        if(image != null) {
            String[] images = image.split(",");
            return images;
        }
        return null;
    }
}
