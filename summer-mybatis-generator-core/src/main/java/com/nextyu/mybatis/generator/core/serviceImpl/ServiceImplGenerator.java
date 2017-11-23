/**
 * Copyright 2006-2016 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nextyu.mybatis.generator.core.serviceImpl;

import com.nextyu.mybatis.generator.core.controller.AbstractJavaControllerMethodGenerator;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @author Jeff Butler
 */
public class ServiceImplGenerator extends AbstractJavaGenerator {

    public ServiceImplGenerator() {
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString("Progress.8", table.toString())); //$NON-NLS-1$
        Plugin plugins = context.getPlugins();
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getServiceImplType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);

        // 实现的接口
        FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType(introspectedTable.getServiceInterfaceType());
        if (superInterface != null) {
            topLevelClass.addSuperInterface(superInterface);
            topLevelClass.addImportedType(superInterface);
        }

        // #### 需要导入的包
        topLevelClass.addImportedType(introspectedTable.getBaseRecordType());
        topLevelClass.addImportedType(FullyQualifiedJavaType.getNewListInstance());
        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.github.pagehelper.PageInfo"));
        topLevelClass.addImportedType("org.springframework.beans.BeanUtils");
        topLevelClass.addImportedType("com.github.pagehelper.PageHelper");


        // 注解

        topLevelClass.addAnnotation("@Service");
        topLevelClass.addImportedType("org.springframework.stereotype.Service");

        commentGenerator.addModelClassComment(topLevelClass, introspectedTable);

        // 方法
        addSaveMethod(topLevelClass);
        addUpdateMethod(topLevelClass);
        addGetByIdMethod(topLevelClass);
        addListPageMethod(topLevelClass);
        addListAllMethod(topLevelClass);
        addGetPageInfoMethod(topLevelClass);

        // 字段
        Field mapper = new Field(introspectedTable.getFullyQualifiedTable().getRequestMappingObjectName() + "Mapper", new FullyQualifiedJavaType(
                introspectedTable.getMyBatis3JavaMapperType()));
        mapper.addAnnotation("@Autowired");
        mapper.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(mapper);

        topLevelClass.addImportedType(introspectedTable.getMyBatis3JavaMapperType());
        topLevelClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().modelBaseRecordClassGenerated(topLevelClass,
                introspectedTable)) {
            answer.add(topLevelClass);
        }
        return answer;
    }

    private void addSaveMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new ServiceImplSaveMethodGenerator(false);
        initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    private void addUpdateMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new ServiceImplUpdateMethodGenerator(false);
        initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    private void addGetByIdMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new ServiceImplGetByIdMethodGenerator(false);
        initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    private void addListPageMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new ServiceImplListPageMethodGenerator(false);
        initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    private void addListAllMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new ServiceImplListAllMethodGenerator(false);
        initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    private void addGetPageInfoMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new ServiceImplGetPageInfoMethodGenerator(false);
        initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    private void initializeAndExecuteGenerator(AbstractJavaControllerMethodGenerator methodGenerator, TopLevelClass topLevelClass) {
        methodGenerator.setContext(context);
        methodGenerator.setIntrospectedTable(introspectedTable);
        methodGenerator.setProgressCallback(progressCallback);
        methodGenerator.setWarnings(warnings);
        methodGenerator.addClassElements(topLevelClass);
    }

    private FullyQualifiedJavaType getSuperClass() {
        FullyQualifiedJavaType superClass;
        String rootClass = getRootClass();
        if (rootClass != null) {
            superClass = new FullyQualifiedJavaType(rootClass);
        } else {
            superClass = null;
        }

        return superClass;
    }

    private void addParameterizedConstructor(TopLevelClass topLevelClass) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName(topLevelClass.getType().getShortName());
        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        List<IntrospectedColumn> constructorColumns = introspectedTable
                .getAllColumns();

        for (IntrospectedColumn introspectedColumn : constructorColumns) {
            method.addParameter(new Parameter(introspectedColumn
                    .getFullyQualifiedJavaType(), introspectedColumn
                    .getJavaProperty()));
        }

        StringBuilder sb = new StringBuilder();
        if (introspectedTable.getRules().generatePrimaryKeyClass()) {
            boolean comma = false;
            sb.append("super("); //$NON-NLS-1$
            for (IntrospectedColumn introspectedColumn : introspectedTable
                    .getPrimaryKeyColumns()) {
                if (comma) {
                    sb.append(", "); //$NON-NLS-1$
                } else {
                    comma = true;
                }
                sb.append(introspectedColumn.getJavaProperty());
            }
            sb.append(");"); //$NON-NLS-1$
            method.addBodyLine(sb.toString());
        }

        List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();

        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            sb.setLength(0);
            sb.append("this."); //$NON-NLS-1$
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" = "); //$NON-NLS-1$
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(';');
            method.addBodyLine(sb.toString());
        }

        topLevelClass.addMethod(method);
    }
}
