package com.tool.api.generate.core.constants;

import java.util.HashSet;
import java.util.Set;

/**
 * 公共常量类
 *
 * @author mengqiang
 */
public class ApiSetConstant {

    /**
     * 需要排除的属性集
     */
    public static final Set<String> FIELD_EXCLUDE_SET = new HashSet<>(16);

    /**
     * 默认需要排除的参数类地址集
     */
    public static final Set<String> EXCLUDE_PARAM_FULL_PATH_SET = new HashSet<>(16);

    /**
     * java 基本类型集
     */
    public static final Set<String> BASE_TYPE_SET = new HashSet<>(16);

    /**
     * java 基础类型
     */
    public static final Set<String> SIMPLE_JAVA_VALUE = new HashSet<>(16);

    /**
     * 集合类型
     */
    public static final Set<String> ARRAY_TYPE = new HashSet<>(16);


    /**
     * 星花导入 的包需要排除的属性集
     */
    public static final Set<String> STAR_FLOWER_PACKAGE_EXCLUDE_SET = new HashSet<>(16);


    static {
        //需要移除的属性常量
        FIELD_EXCLUDE_SET.add("serialVersionUID");

        //需要排除的参数属性常量
        EXCLUDE_PARAM_FULL_PATH_SET.add("javax.servlet.http.HttpServletRequest");
        EXCLUDE_PARAM_FULL_PATH_SET.add("javax.servlet.http.HttpServletResponse");

        //java 基本数据类型
        BASE_TYPE_SET.add("int");
        BASE_TYPE_SET.add("double");
        BASE_TYPE_SET.add("long");
        BASE_TYPE_SET.add("short");
        BASE_TYPE_SET.add("byte");
        BASE_TYPE_SET.add("boolean");
        BASE_TYPE_SET.add("char");
        BASE_TYPE_SET.add("float");

        //java 基础
        SIMPLE_JAVA_VALUE.add("int");
        SIMPLE_JAVA_VALUE.add("Integer");
        SIMPLE_JAVA_VALUE.add("BigInteger");
        SIMPLE_JAVA_VALUE.add("long");
        SIMPLE_JAVA_VALUE.add("Long");
        SIMPLE_JAVA_VALUE.add("double");
        SIMPLE_JAVA_VALUE.add("Double");
        SIMPLE_JAVA_VALUE.add("float");
        SIMPLE_JAVA_VALUE.add("Float");
        SIMPLE_JAVA_VALUE.add("BigDecimal");
        SIMPLE_JAVA_VALUE.add("boolean");
        SIMPLE_JAVA_VALUE.add("Boolean");
        SIMPLE_JAVA_VALUE.add("Short");

        //集合类型
        ARRAY_TYPE.add("List");
        ARRAY_TYPE.add("Set");

        STAR_FLOWER_PACKAGE_EXCLUDE_SET.add("org.springframework.web.bind.annotation.*");
    }

}