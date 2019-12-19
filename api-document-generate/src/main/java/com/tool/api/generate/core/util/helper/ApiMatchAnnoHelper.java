package com.tool.api.generate.core.util.helper;

import com.tool.api.generate.core.constants.CommonConstant;
import com.tool.api.generate.core.constants.ValidationConstant;
import com.tool.api.generate.core.util.tool.ApiStringPool;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 匹配注解 工具
 *
 * @author mengqiang
 */
public class ApiMatchAnnoHelper {

    private static final String MIN_SUFFIX = "(min)";

    /**
     * 获取属性上的注解集
     */
    public static Map<String, JavaAnnotation> getMethodAnnotationMap(JavaMethod method) {
        return getAnnotationMap(method.getAnnotations());
    }


    /**
     * 获取参数上的注解集
     */
    public static Map<String, JavaAnnotation> getParameterAnnotationMap(JavaParameter parameter) {
        return getAnnotationMap(parameter.getAnnotations());
    }

    /**
     * 获取属性上的注解集
     */
    public static Map<String, JavaAnnotation> getFileAnnotationMap(JavaField field) {
        return getAnnotationMap(field.getAnnotations());
    }

    /**
     * 获取注解集 Map
     */
    private static Map<String, JavaAnnotation> getAnnotationMap(List<JavaAnnotation> annotationList) {
        if (CollectionUtils.isEmpty(annotationList)) {
            return new HashMap<>(CommonConstant.ZERO);
        }
        Map<String, JavaAnnotation> annoMap = new HashMap<>(annotationList.size());
        for (JavaAnnotation annotation : annotationList) {
            annoMap.put(String.valueOf(annotation.getType()), annotation);
        }
        return annoMap;
    }

    /**
     * 参数-是否必填标记
     * 注解是否存在 ->  @Notnull,@NotEmpty,@NotBlank
     */
    public static Integer getRequiredFlagFlag(Map<String, JavaAnnotation> fileAnnotationMap) {
        if (fileAnnotationMap.containsKey(ValidationConstant.NOT_NULL_STR)) {
            return CommonConstant.ONE;
        }
        if (fileAnnotationMap.containsKey(ValidationConstant.NOT_BLANK_STR)) {
            return CommonConstant.ONE;
        }
        if (fileAnnotationMap.containsKey(ValidationConstant.NOT_EMPTY_STR)) {
            return CommonConstant.ONE;
        }
        if (fileAnnotationMap.containsKey(ValidationConstant.JAVAX_NOT_EMPTY_STR)) {
            return CommonConstant.ONE;
        }
        if (fileAnnotationMap.containsKey(ValidationConstant.JAVAX_NOT_BLANK_STR)) {
            return CommonConstant.ONE;
        }
        return CommonConstant.ZERO;
    }


    /**
     * java包下的入参，是否必填校验
     */
    public static Integer getJavaTypeRequired(JavaParameter parameter) {
        Map<String, JavaAnnotation> parameterAnnotationMap = getParameterAnnotationMap(parameter);
        if (parameterAnnotationMap.containsKey(ValidationConstant.REQUESTPARAM_STR)) {
            JavaAnnotation annotation = parameterAnnotationMap.get(ValidationConstant.REQUESTPARAM_STR);
            Object required = annotation.getNamedParameter(ValidationConstant.REQUIRED_STR);
            if (ObjectUtils.isEmpty(required)) {
                return CommonConstant.ONE;
            }
            String strRequired = (String) required;
            if (CommonConstant.TRUE_STR.equals(strRequired)) {
                return CommonConstant.ONE;
            }
        }
        return CommonConstant.ZERO;
    }

    /**
     * 参数-长度限制
     * 注解是否存在 ->  @Min,@Max,@Size,@Length
     */
    public static String getLength(Map<String, JavaAnnotation> fileAnnotationMap) {
        StringBuilder sizeStr = new StringBuilder();
        boolean mainExistFlag = fileAnnotationMap.containsKey(ValidationConstant.MIN_STR);
        boolean maxExistFlag = fileAnnotationMap.containsKey(ValidationConstant.MAX_STR);
        //@Min + @Max
        if (mainExistFlag && maxExistFlag) {
            sizeStr.append(getAnnoValue(fileAnnotationMap, ValidationConstant.MIN_STR));
            sizeStr.append(ApiStringPool.DASH);
            sizeStr.append(getAnnoValue(fileAnnotationMap, ValidationConstant.MAX_STR));
            return String.valueOf(sizeStr);
        }
        //@Min
        if (mainExistFlag) {
            sizeStr.append(getAnnoValue(fileAnnotationMap, ValidationConstant.MAX_STR));
            if (StringUtils.isNotBlank(sizeStr)) {
                sizeStr.append(MIN_SUFFIX);
            }
            return String.valueOf(sizeStr);
        }
        //@Max
        if (maxExistFlag) {
            sizeStr.append(getAnnoValue(fileAnnotationMap, ValidationConstant.MAX_STR));
            return String.valueOf(sizeStr);
        }
        boolean decimalMinExistFlag = fileAnnotationMap.containsKey(ValidationConstant.DECIMAL_MIN_STR);
        boolean decimalMaxExistFlag = fileAnnotationMap.containsKey(ValidationConstant.DECIMAL_MAX_STR);
        //@DecimalMin，@DecimalMax
        if (decimalMinExistFlag && decimalMaxExistFlag) {
            sizeStr.append(getAnnoValue(fileAnnotationMap, ValidationConstant.DECIMAL_MIN_STR));
            sizeStr.append(ApiStringPool.DASH);
            sizeStr.append(getAnnoValue(fileAnnotationMap, ValidationConstant.DECIMAL_MAX_STR));
            return String.valueOf(sizeStr);
        }
        //@DecimalMin
        if (decimalMinExistFlag) {
            sizeStr.append(getAnnoValue(fileAnnotationMap, ValidationConstant.DECIMAL_MIN_STR));
            if (StringUtils.isNotBlank(sizeStr)) {
                sizeStr.append(MIN_SUFFIX);
            }
            return String.valueOf(sizeStr);
        }
        //@DecimalMax
        if (decimalMaxExistFlag) {
            sizeStr.append(getAnnoValue(fileAnnotationMap, ValidationConstant.DECIMAL_MAX_STR));
            return String.valueOf(sizeStr);
        }
        //@Size
        if (fileAnnotationMap.containsKey(ValidationConstant.SIZE_STR)) {
            return getSize(fileAnnotationMap, ValidationConstant.SIZE_STR);
        }
        //@Range
        if (fileAnnotationMap.containsKey(ValidationConstant.RANGE_STR)) {
            return getSize(fileAnnotationMap, ValidationConstant.RANGE_STR);
        }
        //@Length
        if (fileAnnotationMap.containsKey(ValidationConstant.LENGTH_STR)) {
            return getSize(fileAnnotationMap, ValidationConstant.LENGTH_STR);
        }
        return ApiStringPool.EMPTY;
    }

    private static String getAnnoValue(Map<String, JavaAnnotation> fileAnnotationMap, String key) {
        JavaAnnotation maxAnnotation = fileAnnotationMap.get(key);
        if (null != maxAnnotation && null != maxAnnotation.getNamedParameter(ValidationConstant.VALUE_STR)) {
            return (String) maxAnnotation.getNamedParameter(ValidationConstant.VALUE_STR);
        }
        return ApiStringPool.EMPTY;
    }

    private static String getSize(Map<String, JavaAnnotation> fileAnnotationMap, String key) {
        JavaAnnotation sizeAnno = fileAnnotationMap.get(key);
        if (null == sizeAnno) {
            return ApiStringPool.EMPTY;
        }
        StringBuilder sizeStr = new StringBuilder();
        String minValue = ValidationConstant.MIN_VALUE_STR;
        String maxValue = ValidationConstant.MAX_VALUE_STR;
        boolean minFlag = !ObjectUtils.isEmpty(sizeAnno.getNamedParameter(minValue));
        boolean maxFlag = !ObjectUtils.isEmpty(sizeAnno.getNamedParameter(maxValue));
        if (minFlag && maxFlag) {
            sizeStr.append(sizeAnno.getNamedParameter(minValue));
            sizeStr.append(ApiStringPool.DASH);
            sizeStr.append(sizeAnno.getNamedParameter(maxValue));
            return String.valueOf(sizeStr);
        }
        if (minFlag) {
            sizeStr.append(sizeAnno.getNamedParameter(minValue));
            if (StringUtils.isNotBlank(sizeStr)) {
                sizeStr.append(MIN_SUFFIX);
            }
            return String.valueOf(sizeStr);
        }
        if (maxFlag) {
            sizeStr.append(sizeAnno.getNamedParameter(maxValue));
            return String.valueOf(sizeStr);
        }
        return ApiStringPool.EMPTY;
    }

}