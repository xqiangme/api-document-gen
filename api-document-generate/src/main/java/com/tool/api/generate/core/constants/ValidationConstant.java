package com.tool.api.generate.core.constants;

/**
 * 参数校验注解-常量
 *
 * @author mengqiang
 */
public class ValidationConstant {

    /**
     * 非空校验注解全类名常量
     */
    public static final String NOT_BLANK_STR = "org.hibernate.validator.constraints.NotBlank";
    public static final String NOT_EMPTY_STR = "org.hibernate.validator.constraints.NotEmpty";
    public static final String NOT_NULL_STR = "javax.validation.constraints.NotNull";
    public static final String JAVAX_NOT_EMPTY_STR = "javax.validation.constraints.NotEmpty";
    public static final String JAVAX_NOT_BLANK_STR = "javax.validation.constraints.NotBlank";


    /**
     * 长度校验注解全类名常量
     */
    public static final String MIN_STR = "javax.validation.constraints.Min";
    public static final String MAX_STR = "javax.validation.constraints.Max";
    public static final String SIZE_STR = "javax.validation.constraints.Size";
    public static final String DECIMAL_MIN_STR = "javax.validation.constraints.DecimalMin";
    public static final String DECIMAL_MAX_STR = "javax.validation.constraints.DecimalMax";
    public static final String LENGTH_STR = "org.hibernate.validator.constraints.Length";
    public static final String RANGE_STR = "org.hibernate.validator.constraints.Range";
    /**
     * 请求方式注解常量
     */
    public static final String REQUEST_MAPPING_STR = "org.springframework.web.bind.annotation.RequestMapping";
    public static final String POST_MAPPING_STR = "org.springframework.web.bind.annotation.PostMapping";
    public static final String GET_MAPPING_STR = "org.springframework.web.bind.annotation.GetMapping";
    public static final String PUT_MAPPING_STR = "org.springframework.web.bind.annotation.PutMapping";
    public static final String DELETE_MAPPING_STR = "org.springframework.web.bind.annotation.DeleteMapping";

    /**
     * controller 注解
     */
    public static final String REQUESTPARAM_STR = "org.springframework.web.bind.annotation.RequestParam";



    /**
     * 注解中属性常量
     */
    public static final String VALUE_STR = "value";
    public static final String METHOD_STR = "method";
    public static final String NAME_STR = "name";
    public static final String MIN_VALUE_STR = "min";
    public static final String MAX_VALUE_STR = "max";
    public static final String REQUIRED_STR = "required";





}