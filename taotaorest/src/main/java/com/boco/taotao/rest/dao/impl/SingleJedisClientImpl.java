package com.boco.taotao.rest.dao.impl;

import com.boco.taotao.rest.dao.JedisClient;

/**
 * Created by Sheamus on 2018/2/9.
 */
public class SingleJedisClientImpl implements JedisClient {

    public static final String NAME = "zhansan";
    private static String name;

    public static void main(String[] args) {
        System.out.println();
        int[] intArrays = {1,1,1,2,3,2};
        sos("ss","ss",1);
        for (int s : intArrays) {
            System.out.println(s);
        }
    }

    private static void printName(String zhansan) {
        System.out.println(zhansan);
        System.out.println(zhansan);
    }

    private static void sos(String ss, String ss1, int age) {
        sos(ss, ss1, age, name);
    }

    private static void sos(String ss, String ss1, int age, String name) {
        System.out.println(ss);
        System.out.println(ss1);
        System.out.println(name);
    }


}