package com.nextyu.mybatis.generator.core.controller;

import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

/**
 * created on 2017-06-02 16:24
 *
 * @author nextyu
 */
public class ControllerUpdateMethodGenerator extends AbstractJavaControllerMethodGenerator {
    private boolean isSimple;

    public ControllerUpdateMethodGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = new Method();

        method.setReturnType(FullyQualifiedJavaType.getObjectInstance());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("update");
        // TODO 设置方法体

        String requestMappingObjectName = introspectedTable.getFullyQualifiedTable().getRequestMappingObjectName();
        String serviceName = requestMappingObjectName + "Service";
        StringBuilder sb = new StringBuilder();
        sb.append("Boolean isSuccess = ").append(serviceName).append(".").append("update(").append(requestMappingObjectName).append("VO").append(");");
        method.addBodyLine(sb.toString());
        method.addBodyLine("return new Object();");

        // 注解
        method.addAnnotation("@RequestMapping(value = \"/{id}\", method = RequestMethod.PUT)");

        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseVOType());
        /*if (isSimple) {
            parameterType = new FullyQualifiedJavaType(
                    introspectedTable.getBaseRecordType());
        } else {
            parameterType = introspectedTable.getRules()
                    .calculateAllFieldsClass();
        }*/

        importedTypes.add(parameterType);
        method.addParameter(new Parameter(PrimitiveTypeWrapper.getLongInstance(), "id", "@PathVariable"));
        method.addParameter(new Parameter(parameterType, introspectedTable.getFullyQualifiedTable().getRequestMappingObjectName() + "VO")); //$NON-NLS-1$

        /*context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);*/


        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);

    }


}
