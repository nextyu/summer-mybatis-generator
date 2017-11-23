package com.nextyu.mybatis.generator.core.serviceImpl;

import com.nextyu.mybatis.generator.core.controller.AbstractJavaControllerMethodGenerator;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.Set;
import java.util.TreeSet;

/**
 * created on 2017-06-02 16:24
 *
 * @author nextyu
 */
public class ServiceImplListAllMethodGenerator extends AbstractJavaControllerMethodGenerator {
    private boolean isSimple;

    public ServiceImplListAllMethodGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = new Method();

        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("java.util.List<"+introspectedTable.getBaseVOType()+">");


        method.setReturnType(returnType);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("listAll");
        // TODO 设置方法体
        StringBuilder sb = new StringBuilder();

        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        String requestMappingObjectName = introspectedTable.getFullyQualifiedTable().getRequestMappingObjectName();
        String mapperName = requestMappingObjectName + "Mapper";

        sb.setLength(0);

        sb.append("List<").append(domainObjectName).append("VO>").append(" ").append(requestMappingObjectName).append("VOS").append(" = ").append("null;");
        method.addBodyLine(sb.toString());

        method.addBodyLine("return " + requestMappingObjectName + "VOS;");

        // 注解
        method.addAnnotation("@Override");

        //FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseVOType());
        /*if (isSimple) {
            parameterType = new FullyQualifiedJavaType(
                    introspectedTable.getBaseRecordType());
        } else {
            parameterType = introspectedTable.getRules()
                    .calculateAllFieldsClass();
        }*/

        //importedTypes.add(parameterType);
        //method.addParameter(new Parameter(parameterType, introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "VO")); //$NON-NLS-1$

        /*context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);*/


        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);

    }


}
