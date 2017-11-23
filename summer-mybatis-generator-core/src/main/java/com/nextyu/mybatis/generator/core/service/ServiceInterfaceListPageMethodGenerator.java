package com.nextyu.mybatis.generator.core.service;

import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

/**
 * created on 2017-06-02 16:24
 *
 * @author nextyu
 */
public class ServiceInterfaceListPageMethodGenerator extends AbstractJavaServiceMethodGenerator {
    private boolean isSimple;

    public ServiceInterfaceListPageMethodGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = new Method();

//        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(introspectedTable.getBaseVOType());
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("java.util.List<"+introspectedTable.getBaseVOType()+">");

        //method.setReturnType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        method.setReturnType(returnType);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("listPage");

        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getQueryType());
        /*if (isSimple) {
            parameterType = new FullyQualifiedJavaType(
                    introspectedTable.getBaseRecordType());
        } else {
            parameterType = introspectedTable.getRules()
                    .calculateAllFieldsClass();
        }*/

        importedTypes.add(parameterType);


        // introspectedTable.getFullyQualifiedTable().getDomainObjectName()
        method.addParameter(new Parameter(parameterType, "query")); //$NON-NLS-1$

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        addMapperAnnotations(method);

        if (context.getPlugins().clientInsertMethodGenerated(method, interfaze,
                introspectedTable)) {
            addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    public void addMapperAnnotations(Method method) {
    }

    public void addExtraImports(Interface interfaze) {
    }
}
