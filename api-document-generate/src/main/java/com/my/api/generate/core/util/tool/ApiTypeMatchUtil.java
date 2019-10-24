package com.my.api.generate.core.util.tool;

import com.my.api.generate.core.constants.CommonConstant;
import org.apache.commons.lang3.StringUtils;

/**
 * 类型匹配 工具
 *
 * @author mengqiang
 */
public class ApiTypeMatchUtil {


    /**
     * 是否为 java. 下的类
     */
    public static boolean isJavaClass(String genericType) {
        return genericType.contains(CommonConstant.JAVA_PACKAGE);
    }

    /**
     * 是否为数组
     */
    public static boolean isArray(String genericType) {
        //是否是数组
        return genericType.contains(CommonConstant.JAVA_ARRAY_STR);
    }

    /**
     * 字符串 > 根据字符串匹配泛型
     */
    public static String[] getTArrayByTypeStr(String typeStr) {
        if (null == typeStr) {
            return new String[0];
        }
        return typeStr.replaceAll(">", "").split("<");
    }

    /**
     * 字符串是否是集合类型
     */
    public static boolean isStrArray(String strType) {
        if (StringUtils.isBlank(strType)) {
            return false;
        }
        if (strType.contains(CommonConstant.LIST_STR)) {
            return true;
        }
        if (strType.contains(CommonConstant.SET_STR)) {
            return true;
        }
        return false;
    }


    /**
     * 是否为 对象 Object 类型
     */
    public static boolean isObject(String genericType) {
        //是否是对象
        return genericType.contains(CommonConstant.O_OBJECT_STR);
    }

    /**
     * 是否为 泛型 示例 <>
     */
    public static boolean isObjectTClass(String genericType) {
        //是否是泛型
        return (genericType.contains("<") && genericType.contains(">"));
    }


    /**
     * 是否为 泛型 示例 <T>
     */
    public static boolean isTClass(String genericType) {
        //是否是泛型
        return (genericType.contains(CommonConstant.TCLASS_STR));
    }

    /**
     * 是否为 泛型 示例 T
     */
    public static boolean isTSimpleClass(String genericType) {
        //是否是泛型
        return (genericType.contains(CommonConstant.TCLASS_SIMPLE_STR));
    }

    /**
     * 是否为 自定义类
     */
    public static boolean isSelfClass(String genericType) {
        try {
            //反射 > 获取入参类 class对象
            Class paramClazz = Class.forName(genericType);
            return (null != paramClazz);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否为 集合 List
     */
    public static boolean isList(String genericType) {
        //是否是集合
        return genericType.contains(CommonConstant.LIST_STR);
    }

    /**
     * 是否为 集合 Set
     */
    public static boolean isSet(String genericType) {
        //是否是集合
        return genericType.contains(CommonConstant.SET_STR);
    }


}