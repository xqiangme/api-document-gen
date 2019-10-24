package com.my.api.generate.core.constants;

import com.my.api.generate.core.util.tool.ApiStringPool;

import java.nio.charset.Charset;

/**
 * 公共常量类
 *
 * @author mengqiang
 */
public class CommonConstant {

    /**
     * 模板名称
     */
    public static final String TEMPLATE_NAME = "GenerateApi.ftl";

    /**
     * 首字母大写 Object
     */
    public static final String O_OBJECT_STR = "Object";

    public static final String VOID_STR = "void";
    public static final String LIST_STR = "List";
    public static final String SET_STR = "Set";
    public static final String TRUE_STR = "true";
    public static final String IGNORE_STR = "ignore";

    /********** javadoc 关键字 **********/
    public static final String AUTHOR = "author";
    public static final String PARAM = "param";
    public static final String VERSION = "version";
    public static final String RETURN = "return";

    public static final String AT_AUTHOR = "@author";
    public static final String AT_PARAM = "@param";
    public static final String AT_VERSION = "@version";
    public static final String AT_RETURN = "@return";
    public static final String AT_IGNORE = "@ignore";


    /********** 其它常量 **********/
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int TEN = 10;
    public static final int DEFAULT_MAP_SIZE = 64;
    public static final Charset DEFAULT_CHARSET = Charset.forName(ApiStringPool.UTF_8);

    public static final String JAVA_FILE_SUFFIX = ".java";
    public static final String JAVA_PACKAGE = "java.";
    public static final String JAVA_ARRAY_STR = "[]";
    public static final String TCLASS_STR = "<T>";
    public static final String TCLASS_SIMPLE_STR = "T";
    public static final String STR_$ = "$";
    public static final String MAIN_STR = "main";
    public static final String METHOD_STR = "method";

    public static final String[] BANK_NAME_ARRAY = new String[]{"中国银行", "建设银行", "平安银行", "杭州银行", "交通银行", "xx银行"};
}