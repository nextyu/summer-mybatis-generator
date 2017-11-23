package com.nextyu.mybatis.generator.core.controller;

import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

/**
 * created on 2017-06-02 16:24
 *
 * @author nextyu
 */
public class ControllerListPageMethodGenerator extends AbstractJavaControllerMethodGenerator {
    private boolean isSimple;

    public ControllerListPageMethodGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = new Method();

        method.setReturnType(FullyQualifiedJavaType.getObjectInstance());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("listPage");

        // TODO 设置方法体
        String requestMappingObjectName = introspectedTable.getFullyQualifiedTable().getRequestMappingObjectName();
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        String serviceName = requestMappingObjectName + "Service";
        StringBuilder sb = new StringBuilder();
        sb.append("List<").append(domainObjectName).append("VO>").append(" ").append(requestMappingObjectName).append("VOS").append(" = ").append(serviceName).append(".").append("listPage(").append("query").append(");");
        method.addBodyLine(sb.toString());


        method.addBodyLine("return new Object();");

        // 注解
        method.addAnnotation("@RequestMapping(value = \"\", method = RequestMethod.GET)");

        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getQueryType());
        /*if (isSimple) {
            parameterType = new FullyQualifiedJavaType(
                    introspectedTable.getBaseRecordType());
        } else {
            parameterType = introspectedTable.getRules()
                    .calculateAllFieldsClass();
        }*/

        importedTypes.add(parameterType);
        method.addParameter(new Parameter(parameterType, "query")); //$NON-NLS-1$

        /*context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);*/


        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);

    }


}
