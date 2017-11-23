package com.nextyu.mybatis.generator.core.service;

import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.codegen.AbstractGenerator;

/**
 * created on 2017-06-02 17:26
 *
 * @author nextyu
 */
public abstract class AbstractJavaServiceMethodGenerator extends AbstractGenerator {
    public abstract void addInterfaceElements(Interface interfaze);

    public AbstractJavaServiceMethodGenerator() {
        super();
    }
}
