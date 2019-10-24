package com.my.api.generate.core.util.helper;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 类获取辅助工具类
 *
 * @author mengqiang
 */
public class ApiClassHelper {

    /**
     * 获取路径下的类集
     */
    public static Collection<JavaClass> getClassListByPath(String filePath) {
        JavaProjectBuilder currentBuilder = new JavaProjectBuilder();
        currentBuilder.addSourceTree(new File(filePath));
        return currentBuilder.getClasses();
    }

    /**
     * 获取所有属性
     * 包括父类属性
     */
    public static List<JavaField> getAllFields(JavaClass clazz) {
        List<JavaField> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(clazz.getFields());
            clazz = clazz.getSuperJavaClass();
        }
        return fieldList;
    }
}