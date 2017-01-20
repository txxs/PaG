package com.lemain.valiteTec;

/**
 * Created by jianghuimin on 2017/1/19.
 */
public class ValiteStatic {
    private static String name = "jianghuimin";
    static{
        name="zhanghao";
    }
    public static String getName(){
        name = "32";
        return name;
    }
}
