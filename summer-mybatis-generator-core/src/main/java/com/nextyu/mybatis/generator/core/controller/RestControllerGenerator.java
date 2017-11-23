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
package com.nextyu.mybatis.generator.core.controller;

import com.nextyu.mybatis.generator.core.Constants;
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
public class RestControllerGenerator extends AbstractJavaGenerator {

    public RestControllerGenerator() {
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString("Progress.8", table.toString())); //$NON-NLS-1$
        Plugin plugins = context.getPlugins();
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getControllerType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);


        // 字段
        Field serviceMapper = new Field(introspectedTable.getFullyQualifiedTable().getRequestMappingObjectName() + "Service", new FullyQualifiedJavaType(
                introspectedTable.getServiceInterfaceType()));
        serviceMapper.addAnnotation("@Autowired");
        serviceMapper.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(serviceMapper);

        topLevelClass.addImportedType(introspectedTable.getServiceInterfaceType());
        topLevelClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");


        // 父类
        /*FullyQualifiedJavaType superClass = new FullyQualifiedJavaType(Constants.baseControllerFullName);
        if (superClass != null) {
            topLevelClass.setSuperClass(superClass);
            topLevelClass.addImportedType(superClass);
        }*/



        // 注解
        topLevelClass.addAnnotation("@RestController");
        topLevelClass.addAnnotation("@RequestMapping(\"/"+introspectedTable.getFullyQualifiedTable().getRequestMappingObjectName()+"\")");

        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController"));
        topLevelClass.addImportedType("org.springframework.web.bind.annotation.RequestMapping");
        topLevelClass.addImportedType("org.springframework.web.bind.annotation.RequestMethod");
        topLevelClass.addImportedType("org.springframework.web.bind.annotation.PathVariable");

        commentGenerator.addModelClassComment(topLevelClass, introspectedTable);

        // 方法
        addSaveMethod(topLevelClass);
        addUpdateMethod(topLevelClass);
        addGetByIdMethod(topLevelClass);
        addListPageMethod(topLevelClass);
        addListAllMethod(topLevelClass);

        // #### 需要导入的包
        topLevelClass.addImportedType(FullyQualifiedJavaType.getNewListInstance());


        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().modelBaseRecordClassGenerated(topLevelClass,
                introspectedTable)) {
            answer.add(topLevelClass);
        }
        return answer;
    }

    private void addSaveMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new ControllerSaveMethodGenerator(false);
        initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    private void addUpdateMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new ControllerUpdateMethodGenerator(false);
        initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    private void addGetByIdMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new ControllerGetByIdMethodGenerator(false);
        initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    private void addListPageMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new ControllerListPageMethodGenerator(false);
        initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    private void addListAllMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new ControllerListAllMethodGenerator(false);
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
