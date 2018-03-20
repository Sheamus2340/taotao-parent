package com.boco.taotao.portal.service.impl;

import com.boco.taotao.pojo.TbItem;
import com.boco.taotao.portal.service.CartService;
import com.boco.taotao.portal.vo.CartItem;
import com.boco.taotao.util.CookieUtils;
import com.boco.taotao.util.HttpClientUtil;
import com.boco.taotao.vo.JsonUtils;
import com.boco.taotao.vo.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车的service
 * Created by Sheamus on 2018/3/19.
 */
@Service
public class CartServiceImpl implements CartService {

    @Value(value = "${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value(value = "${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;

    @Override
    public TaotaoResult addCartItem(HttpServletRequest request, HttpServletResponse response, long itemId, int num) {
        CartItem cartItem = null;
        //加入购物车之前先判断商品列表中是否已经包含该商品，如果包含只要修改商品数量即可
        //1. 从cookie中取商品列表
        List<CartItem> cartItems = getCartItemList(request);
        //2. 判断购物车是否存在该商品，如果包含只要修改商品数量即可
        for(CartItem cItem : cartItems) {
            //如果购物车存在此商品
            if(cItem.getId() == itemId) {
                //直接修改数量就可以
                cItem.setNum(cItem.getNum() + num);
                cartItem = cItem;
                break;
            }
        }
        //如果不包含然后再添加
        if(cartItem == null) {
            cartItem = new CartItem();
            //取购物车商品列表
            //根据商品id查询商品基本信息
            String url = REST_BASE_URL + ITEM_INFO_URL + itemId;
            String json = HttpClientUtil.doGet(url);
            TaotaoResult result = TaotaoResult.formatToPojo(json, TbItem.class);
            if(result.getStatus() == 200) {
                TbItem item = (TbItem) result.getData();
                cartItem.setId(item.getId());
                //取图片的时候应该这样处理
                cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
                cartItem.setPrice(item.getPrice());
                cartItem.setTitle(item.getTitle());
                cartItem.setNum(num);
            }
            //没有的该商品的时候把该商品添加到购物车
            cartItems.add(cartItem);
        }
        //最后，将购物车列表重新写入cookie中
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(cartItems),true);
        return TaotaoResult.ok();
    }

    @Override
    public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
        return getCartItemList(request);
    }

    @Override
    public TaotaoResult deleteCartItem(HttpServletRequest request, HttpServletResponse response, long itemId) {
        try {
            // 1. 从cookie中查询该商品
            List<CartItem> cartItems = getCartItemList(request, response);
            for(CartItem item : cartItems) {
                if(item.getId() == itemId) {
                    cartItems.remove(item);
                    break;
                }
            }
            //最后，将购物车列表重新写入cookie中
            CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(cartItems),true);
            return TaotaoResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.fail(e);
        }
    }

    /**
     * 从cookie中取商品列表
     * @return
     */
    private List<CartItem> getCartItemList(HttpServletRequest request) {
        //从cookie中取商品列表
        String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);//当有编码的时候，默认是UTF-8
        if(StringUtils.isBlank(cartJson)) {
            return new ArrayList<>();
        }
        try {
            //把Json数据转换成商品列表
            List<CartItem> cartItems = JsonUtils.jsonToList(cartJson, CartItem.class);
            return cartItems;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
