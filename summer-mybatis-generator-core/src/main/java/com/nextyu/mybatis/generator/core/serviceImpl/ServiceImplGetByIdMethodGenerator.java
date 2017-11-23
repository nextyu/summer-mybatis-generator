package com.nextyu.mybatis.generator.core.serviceImpl;

import com.nextyu.mybatis.generator.core.controller.AbstractJavaControllerMethodGenerator;
import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

/**
 * created on 2017-06-02 16:24
 *
 * @author nextyu
 */
public class ServiceImplGetByIdMethodGenerator extends AbstractJavaControllerMethodGenerator {
    private boolean isSimple;

    public ServiceImplGetByIdMethodGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = new Method();

        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(introspectedTable.getBaseVOType());
        method.setReturnType(returnType);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("getById");
        // TODO 设置方法体
        method.addBodyLine("return null;");

        // 注解
        method.addAnnotation("@Override");

        FullyQualifiedJavaType parameterType = PrimitiveTypeWrapper.getLongInstance();
        /*if (isSimple) {
            parameterType = new FullyQualifiedJavaType(
                    introspectedTable.getBaseRecordType());
        } else {
            parameterType = introspectedTable.getRules()
                    .calculateAllFieldsClass();
        }*/

        importedTypes.add(parameterType);
        importedTypes.add(returnType);
        method.addParameter(new Parameter(parameterType, "id")); //$NON-NLS-1$

        /*context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);*/


        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);

    }


}
