package com.szj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author shenggongjie
 * @date 2021/3/6 0:21
 */
@SpringBootApplication
public class UtilsApplication {
    public static void main(String[] args) {
        SpringApplication.run(UtilsApplication.class, args);
        System.out.println("访问地址：http://localhost:8080/test/reportOut");
    }
}
