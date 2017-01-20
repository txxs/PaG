package com.lemain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by jianghuimin on 2017/1/18.
 */

@EnableAutoConfiguration
@SpringBootApplication
public class SpringMain {
    public static void main(String... arg){
        SpringApplication.run(SpringMain.class,arg);
    }
}
