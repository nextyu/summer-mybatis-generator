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
public class ServiceImplSaveMethodGenerator extends AbstractJavaControllerMethodGenerator {
    private boolean isSimple;

    public ServiceImplSaveMethodGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = new Method();

        method.setReturnType(PrimitiveTypeWrapper.getBooleanInstance());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("save");
        // TODO 设置方法体
        StringBuilder sb = new StringBuilder();

        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        String requestMappingObjectName = introspectedTable.getFullyQualifiedTable().getRequestMappingObjectName();
        String mapperName = requestMappingObjectName + "Mapper";

        sb.append(domainObjectName).append(" ").append(requestMappingObjectName).append(" = ").append("new ")
                .append(domainObjectName).append("();");
        method.addBodyLine(sb.toString());

        sb.setLength(0);

        sb.append("BeanUtils.copyProperties(").append(requestMappingObjectName).append("VO").append(", ").append(requestMappingObjectName).append(");");
        method.addBodyLine(sb.toString());

        sb.setLength(0);

        sb.append("int rows = ").append(mapperName).append(".").append("insertSelective(").append(requestMappingObjectName).append(");");
        method.addBodyLine(sb.toString());
        method.addBodyLine("return rows > 0;");

        // 注解
        method.addAnnotation("@Override");

        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseVOType());
        /*if (isSimple) {
            parameterType = new FullyQualifiedJavaType(
                    introspectedTable.getBaseRecordType());
        } else {
            parameterType = introspectedTable.getRules()
                    .calculateAllFieldsClass();
        }*/

        importedTypes.add(parameterType);
        method.addParameter(new Parameter(parameterType, introspectedTable.getFullyQualifiedTable().getRequestMappingObjectName() + "VO")); //$NON-NLS-1$

        /*context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);*/


        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);

    }


}
