package com.nextyu.mybatis.generator.core;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * created on 2017-11-22 17:30
 *
 * @author nextyu
 */
public class Generator {

    public static Generator newGenerator() {
        return new Generator();
    }

    public Generator ip(String ip) {
        Constants.ip = ip;
        return this;
    }

    public Generator port(String port) {
        Constants.port = port;
        return this;
    }

    public Generator db(String db) {
        Constants.db = db;
        return this;
    }

    public Generator userName(String userName) {
        Constants.userName = userName;
        return this;
    }

    public Generator password(String password) {
        Constants.password = password;
        return this;
    }

    public Generator tableNames(String... tableNames) {
        Constants.tableNames = tableNames;
        return this;
    }

    public Generator entityPackageName(String entityPackageName) {
        Constants.entityPackageName = entityPackageName;
        return this;
    }

    public Generator voPackageName(String voPackageName) {
        Constants.voPackageName = voPackageName;
        return this;
    }

    public Generator queryPackageName(String queryPackageName) {
        Constants.queryPackageName = queryPackageName;
        return this;
    }

    public Generator controllerPackageName(String controllerPackageName) {
        Constants.controllerPackageName = controllerPackageName;
        return this;
    }

    public Generator servicePackageName(String servicePackageName) {
        Constants.servicePackageName = servicePackageName;
        return this;
    }

    public Generator serviceImplPackageName(String serviceImplPackageName) {
        Constants.serviceImplPackageName = serviceImplPackageName;
        return this;
    }

    public Generator daoPackageName(String daoPackageName) {
        Constants.daoPackageName = daoPackageName;
        return this;
    }

    public Generator mapperPath(String mapperPath) {
        Constants.mapperPath = mapperPath;
        return this;
    }

    public  void generate() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;

        Context context = new Context(ModelType.FLAT);
        context.setId("context1");
        context.setTargetRuntime("MyBatis3");
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.setConfigurationType("com.nextyu.mybatis.generator.core.ZHCommentGenerator");
        commentGeneratorConfiguration.addProperty("suppressDate", "true");
        commentGeneratorConfiguration.addProperty("suppressAllComments", "false");
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);

        Configuration config = new Configuration();
        config.addContext(context);


        // db
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        String connection = "jdbc:mysql://" + Constants.ip + ":" + Constants.port + "/" + Constants.db + "?useUnicode=true&amp;characterEncoding=utf-8&amp;autoReconnect=true&amp;zeroDateTimeBehavior=convertToNull";
        jdbcConnectionConfiguration.setConnectionURL(connection);
        jdbcConnectionConfiguration.setUserId(Constants.userName);
        jdbcConnectionConfiguration.setPassword(Constants.password);
        jdbcConnectionConfiguration.setDriverClass("com.mysql.jdbc.Driver");

        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        // model 配置
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetPackage(Constants.entityPackageName);
        javaModelGeneratorConfiguration.setTargetProject("src/main/java");

        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        // DAO 配置
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetPackage(Constants.daoPackageName);
        javaClientGeneratorConfiguration.setTargetProject("src/main/java");
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");

        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        // Mapper
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetPackage(Constants.mapperPath);
        sqlMapGeneratorConfiguration.setTargetProject("src/main/resources");

        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);


        String[] tableNames = Constants.tableNames;

        // 表
        for (int i = 0; i < tableNames.length; i++) {
            if (!tableNames[i].isEmpty()) {
                TableConfiguration tableConfiguration = new TableConfiguration(context);
                tableConfiguration.setTableName(tableNames[i]);
                tableConfiguration.setCountByExampleStatementEnabled(false);
                tableConfiguration.setDeleteByExampleStatementEnabled(false);
                tableConfiguration.setSelectByExampleStatementEnabled(false);
                tableConfiguration.setUpdateByExampleStatementEnabled(false);

                tableConfiguration.setGeneratedKey(new GeneratedKey("id", "MySql"
                        , true, "post"));

                context.addTableConfiguration(tableConfiguration);

            }
        }


        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
