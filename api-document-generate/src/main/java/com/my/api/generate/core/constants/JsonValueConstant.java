package com.my.api.generate.core.constants;

import com.my.api.generate.core.util.tool.ApiDateTimeUtil;
import com.my.api.generate.core.util.tool.ApiRandomUtil;
import com.github.javafaker.Faker;

import java.util.*;

/**
 * java 模拟值常量
 *
 * @author mengqiang
 */
public class JsonValueConstant {

    private static Faker cnFaker = new Faker(new Locale("zh", "CN"));
    private static Faker usFaker = new Faker(new Locale("en", "US"));
    public static Map<String, String> STRING_VALUE_MAP = new LinkedHashMap<>();
    public static Map<String, Integer> INT_VALUE_MAP = new LinkedHashMap<>();
    public static Map<String, Long> LONG_VALUE_MAP = new LinkedHashMap<>();

    /**
     * 需要小写
     */
    static {
        /** 字符串类型参数  **/
        STRING_VALUE_MAP.put("errormsg", "");
        STRING_VALUE_MAP.put("msg", "this is msg ".concat(ApiRandomUtil.randomString(6)));
        STRING_VALUE_MAP.put("remark", "this is msg ".concat(ApiRandomUtil.randomString(6)));

        STRING_VALUE_MAP.put("url", "http://" + cnFaker.internet().url().replaceAll("-", ""));
        STRING_VALUE_MAP.put("email", cnFaker.internet().emailAddress());
        STRING_VALUE_MAP.put("domain", cnFaker.internet().domainName());
        STRING_VALUE_MAP.put("telephone", cnFaker.phoneNumber().phoneNumber());
        STRING_VALUE_MAP.put("phone", cnFaker.phoneNumber().cellPhone());
        STRING_VALUE_MAP.put("mobile", cnFaker.phoneNumber().cellPhone());

        STRING_VALUE_MAP.put("address", cnFaker.address().fullAddress().replace(",", "，"));
        STRING_VALUE_MAP.put("ipv4", cnFaker.internet().ipV4Address());
        STRING_VALUE_MAP.put("ipv6", cnFaker.internet().ipV6Address());
        STRING_VALUE_MAP.put("ip", cnFaker.internet().ipV4Address());
        STRING_VALUE_MAP.put("birthday", ApiDateTimeUtil.formatDate(cnFaker.date().birthday()));
        STRING_VALUE_MAP.put("time", ApiDateTimeUtil.formatDateTime(new Date()));
        STRING_VALUE_MAP.put("date", ApiDateTimeUtil.formatDate(new Date()));
        STRING_VALUE_MAP.put("idcard", cnFaker.number().digits(10));
        STRING_VALUE_MAP.put("account", cnFaker.number().digits(10));
        STRING_VALUE_MAP.put("timestamp", String.valueOf(System.currentTimeMillis()));
        STRING_VALUE_MAP.put("long", String.valueOf(System.currentTimeMillis()));
        STRING_VALUE_MAP.put("type", String.valueOf(ApiRandomUtil.randomInt(1, 3)));
        STRING_VALUE_MAP.put("status", String.valueOf(ApiRandomUtil.randomInt(1, 3)));
        STRING_VALUE_MAP.put("company", cnFaker.company().name());
        STRING_VALUE_MAP.put("bank", CommonConstant.BANK_NAME_ARRAY[ApiRandomUtil.randomInt(0, 5)]);
        STRING_VALUE_MAP.put("file", cnFaker.file().fileName());


        /** 数字类型参数  **/
        INT_VALUE_MAP.put("pagesize", CommonConstant.TEN);
        INT_VALUE_MAP.put("total", CommonConstant.TEN);
        INT_VALUE_MAP.put("page", CommonConstant.ONE);
        INT_VALUE_MAP.put("age", cnFaker.number().numberBetween(10, 70));
        INT_VALUE_MAP.put("sex", ApiRandomUtil.randomInt(1, 3));
        INT_VALUE_MAP.put("is", ApiRandomUtil.randomInt(1, 3));
        INT_VALUE_MAP.put("gender", ApiRandomUtil.randomInt(1, 3));
        INT_VALUE_MAP.put("state", ApiRandomUtil.randomInt(1, 6));
        INT_VALUE_MAP.put("status", ApiRandomUtil.randomInt(1, 6));
        INT_VALUE_MAP.put("valid", ApiRandomUtil.randomInt(1, 3));
        INT_VALUE_MAP.put("type", ApiRandomUtil.randomInt(1, 6));

        /** long 类型参数  **/
        LONG_VALUE_MAP.put("total", (long) CommonConstant.TEN);

    }

    public static Map<String, String> getStringMap() {
        Map<String, String> stringMap = new LinkedHashMap<>();
        stringMap.put("errorcode", "");
        stringMap.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
        //名称相关实时随机获取
        stringMap.put("nickname", usFaker.name().username());
        stringMap.put("username", usFaker.name().username());
        stringMap.put("realname", cnFaker.name().username());
        stringMap.put("name", cnFaker.name().username());
        //id 刷新
        stringMap.put("id", cnFaker.number().digits(10));
        stringMap.put("num", ApiDateTimeUtil.formatShortDate(new Date()).concat(cnFaker.number().digits(10)));
        stringMap.put("code", ApiDateTimeUtil.formatShortDate(new Date()).concat(cnFaker.number().digits(10)));
        stringMap.put("no", ApiDateTimeUtil.formatShortDate(new Date()).concat(cnFaker.number().digits(10)));

        //默认常量值
        stringMap.putAll(STRING_VALUE_MAP);
        return stringMap;
    }

}