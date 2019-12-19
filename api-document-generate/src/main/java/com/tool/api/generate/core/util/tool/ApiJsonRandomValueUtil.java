package com.tool.api.generate.core.util.tool;

import com.tool.api.generate.core.constants.JavaTypeNameConstant;
import com.tool.api.generate.core.constants.JsonValueConstant;
import com.tool.api.generate.core.constants.CommonConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * json随机值生成工具
 *
 * @author mengqiang
 */
public class ApiJsonRandomValueUtil {

    /**
     * 获取属性随机值
     * 针对数组或者集合类型
     */
    public static Object randomValue(String[] fileTypes, String fileName) {
        if (CommonConstant.ONE == fileTypes.length) {
            return randomValue(fileTypes[0], fileName);
        }
        Object result = null;
        List<Object> objectList;
        Set<Object> objectSet;
        for (int i = fileTypes.length - CommonConstant.ONE; i >= CommonConstant.ZERO; i--) {
            String fileType = fileTypes[i];
            if (fileType.contains(CommonConstant.LIST_STR)) {
                if (null != result) {
                    objectList = new ArrayList<>();
                    objectList.add(result);
                    //重新赋值
                    result = objectList;
                }
            } else if (fileType.contains(CommonConstant.SET_STR)) {
                if (null != result) {
                    objectSet = new HashSet<>();
                    objectSet.add(result);
                    //重新赋值
                    result = objectSet;
                }
            } else {
                result = randomValue(fileType, fileName);
            }
        }
        return result;
    }


    /**
     * 根据属性类型与名称生成随机值
     *
     * @param
     * @return
     * @author mengqiang
     * @date 2019-05-11
     */
    public static Object randomValue(String fileType, String filedName) {
        if (StringUtils.isBlank(fileType) || StringUtils.isBlank(filedName)) {
            return ApiStringPool.EMPTY;
        }
        //替换数组标记
        fileType = fileType.trim().replaceAll("\\[]", ApiStringPool.EMPTY);
        fileType = fileType.contains(".") ? fileType.substring(fileType.lastIndexOf(".") + 1, fileType.length()) : fileType;

        //String
        if (JavaTypeNameConstant.STRING_STR.equals(fileType)) {
            return getRandomStringValue(filedName);
        }
        //Integer,int,BigInteger
        if (JavaTypeNameConstant.INTEGER_STR.equals(fileType) ||
                JavaTypeNameConstant.INT_STR.equals(fileType) || JavaTypeNameConstant.BIG_INTEGER_STR.equals(fileType)) {
            return getRandomIntValue(filedName);
        }
        //Date
        if (JavaTypeNameConstant.DATE_STR.equals(fileType) ||
                JavaTypeNameConstant.LOCAL_DATE_STR.equals(fileType) || JavaTypeNameConstant.LOCAL_DATE_TIME.equals(fileType)) {
            return new Date();
        }
        //Boolean
        if (JavaTypeNameConstant.BOOLEAN_STR.equalsIgnoreCase(fileType)) {
            return Boolean.TRUE;
        }
        //BigDecimal
        if (JavaTypeNameConstant.BIG_DECIMAL_STR.equals(fileType)) {
            return ApiRandomUtil.randomDouble();
        }
        //List
        if (JavaTypeNameConstant.LIST_STR.equals(fileType)) {
            return new ArrayList<>(0);
        }
        //Long,long
        if (JavaTypeNameConstant.LONG_STR.equalsIgnoreCase(fileType)) {
            return getRandomLongValue(filedName);
        }
        //Double,double
        if (JavaTypeNameConstant.DOUBLE_STR.equalsIgnoreCase(fileType)) {
            return ApiRandomUtil.randomDouble();
        }
        //Object
        if (JavaTypeNameConstant.OBJECT_STR.equals(fileType)) {
            return new Object();
        }
        //T
        if (JavaTypeNameConstant.T_CLASS_STR.equals(fileType)) {
            return new Object();
        }
        //Float,float
        if (JavaTypeNameConstant.FLOAT_STR.equalsIgnoreCase(fileType)) {
            return ApiRandomUtil.randomDouble();
        }
        //Byte,byte,Short,short
        if (JavaTypeNameConstant.BYTE_STR.equalsIgnoreCase(fileType) || JavaTypeNameConstant.SHORT_STR.equalsIgnoreCase(fileType)) {
            return getRandomIntValue(filedName);
        }
        //char,Character
        if (JavaTypeNameConstant.CHAR_STR.equals(fileType) || JavaTypeNameConstant.CHARACTER_STR.equals(fileType)) {
            return getRandomStringValue(filedName);
        }
        //Timestamp
        if (JavaTypeNameConstant.TIMESTAMP_STR.equals(fileType)) {
            return System.currentTimeMillis();
        }
        //默认10位字符串
        return ApiRandomUtil.randomString(CommonConstant.TEN);
    }

    private static Long getRandomLongValue(String filedName) {
        for (Map.Entry<String, Long> entry : JsonValueConstant.LONG_VALUE_MAP.entrySet()) {
            if (filedName.toLowerCase().contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return System.currentTimeMillis();
    }

    private static String getRandomStringValue(String filedName) {
        for (Map.Entry<String, String> entry : JsonValueConstant.getStringMap().entrySet()) {
            if (filedName.toLowerCase().contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return ApiRandomUtil.randomString(12);
    }

    private static Integer getRandomIntValue(String filedName) {
        for (Map.Entry<String, Integer> entry : JsonValueConstant.INT_VALUE_MAP.entrySet()) {
            if (filedName.toLowerCase().contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return ApiRandomUtil.randomInt(1000);
    }
}