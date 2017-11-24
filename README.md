# 基于 mybatis generator 的代码生成器

项目的由来，代码敲久了，发现从 Controller 到 Dao 代码结构都差不多，就想着写个代码生成器。然后就站在巨人（mybatis generator）的肩膀上，加上了一些功能，写成了这个代码生成器。

目前仅支持 MySQL。

## 简介

指定一个或多个表名，可以生成对应的如下文件（默认会覆盖以前的文件）。

- Entity
- VO
- Query
- Controller (包含部分常用方法)
- Service (包含部分常用方法)
- ServiceImpl (包含部分常用方法)
- Mapper
- Mapper.xml


## 使用方法

1. 打开 summer-mybatis-generator-demo
2. 运行 `SummerMybatisGeneratorDemoApplicationTests.testGenerate()` 方法

## 运行效果图

![](result.png)

## 历史版本

- 1.0.0
    - 基本功能实现
