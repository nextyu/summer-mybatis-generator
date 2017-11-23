package com.nextyu.mybatis.generator.core;

/**
 * created on 2017-06-02 15:45
 *
 * @author nextyu
 */
public class Constants {

    // --------------------主要修改---------------------------
    public static String[] tableNames = {""};

    public static String ip = "";
    public static String port = "";
    public static String db = "";
    public static String userName = "";
    public static String password = "";
    // --------------------------------------------------

    public static String basePackageName = "";

    public static String entityPackageName = basePackageName + ".domain";
    public static String voPackageName = basePackageName + ".vo";
    public static String queryPackageName = basePackageName + ".query";


    public static String controllerPackageName = basePackageName + ".web.controller";
    public static String servicePackageName = basePackageName + ".service";
    public static String serviceImplPackageName = basePackageName + ".service.impl";

    public static String daoPackageName = basePackageName + ".dao";
    public static String mapperPath = "mapper";


    public static String baseQueryFullName = basePackageName + "query.BaseQuery";
    public static String baseControllerFullName = basePackageName + ".web.controller.BaseController";

}
