package com.nextyu.mybatis.generator.demo;

import com.nextyu.mybatis.generator.core.Generator;
import org.junit.Test;


public class SummerMybatisGeneratorDemoApplicationTests {

    @Test
    public void testGenerate() throws Exception {

        String basePackageName = "com.nextyu.spring.transaction.demo";

        Generator.newGenerator()
                .ip("127.0.0.1").port("3306").db("test")
                .userName("root").password("123456").tableNames("user")
                .entityPackageName(basePackageName + ".entity")
                .voPackageName(basePackageName + ".vo")
                .queryPackageName(basePackageName + ".query")
                .controllerPackageName(basePackageName + ".web.controller")
                .servicePackageName(basePackageName + ".service")
                .serviceImplPackageName(basePackageName + ".service.impl")
                .daoPackageName(basePackageName + ".dao")
                .mapperPath("mapper")
                .generate();
    }

}
