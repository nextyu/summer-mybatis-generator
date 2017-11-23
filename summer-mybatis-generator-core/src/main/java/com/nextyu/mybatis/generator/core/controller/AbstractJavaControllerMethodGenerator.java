package com.nextyu.mybatis.generator.core.controller;

import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractGenerator;

/**
 * created on 2017-06-05 10:55
 *
 * @author nextyu
 */
public abstract class AbstractJavaControllerMethodGenerator extends AbstractGenerator {
    public abstract void addClassElements(TopLevelClass topLevelClass);

    public AbstractJavaControllerMethodGenerator() {
        super();
    }
}
