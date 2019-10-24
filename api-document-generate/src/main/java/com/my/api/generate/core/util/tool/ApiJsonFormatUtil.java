package com.my.api.generate.core.util.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * json 格式化 工具
 *
 * @author mengqiang
 */
public class ApiJsonFormatUtil {

    /**
     * json 格式化-可输为空的属性
     *
     * @author mengqiang
     */
    public static String toFormatJson(Object object) {
        if (null == object) {
            return ApiStringPool.EMPTY;
        }
        String content = JSON.toJSONString(object,
                SerializerFeature.SortField,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.PrettyFormat);
        return content;
    }

}