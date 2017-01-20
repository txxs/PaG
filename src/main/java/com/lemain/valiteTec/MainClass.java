package com.lemain.valiteTec;

/**
 * Created by jianghuimin on 2017/1/19.
 */
public class MainClass {
    public static void main(String args[]){
        //类不用new，就可以直接调用static代码块
        System.out.print(ValiteStatic.getName());
    }
}
