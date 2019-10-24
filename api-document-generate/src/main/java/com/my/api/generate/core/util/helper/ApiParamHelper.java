package com.my.api.generate.core.util.helper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.my.api.generate.core.config.custom.ApiRootResField;
import com.my.api.generate.core.constants.ApiSetConstant;
import com.my.api.generate.core.constants.CommonConstant;
import com.my.api.generate.core.enums.ApiParamTypeEnum;
import com.my.api.generate.core.model.ApiReqParamModel;
import com.my.api.generate.core.model.ParamModel;
import com.my.api.generate.core.model.convert.ApiAtomicCountModel;
import com.my.api.generate.core.model.convert.ApiFieldParamModel;
import com.my.api.generate.core.config.ApiConfig;
import com.my.api.generate.core.model.ApiResParamModel;
import com.my.api.generate.core.util.tool.*;
import com.google.common.base.CaseFormat;
import com.my.api.generate.core.util.tool.*;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 参数获取-辅助工具类
 *
 * @author mengqiang
 */
public class ApiParamHelper {
    private static final int ZERO = CommonConstant.ZERO;
    private static final int ONE = CommonConstant.ONE;

    /**
     * 入参-读取
     *
     * @param rootBuilder
     * @param method      方法
     * @author mengqiang
     */
    public static ApiReqParamModel getReqParam(JavaProjectBuilder rootBuilder, Set<String> lessPackageSet, JavaMethod method, Set<String> paramExcludeSet, ApiConfig config) {
        List<JavaParameter> parameterList = method.getParameters();
        ApiLogUtil.debug("【入参】>> 当前方法：{0} , 参数数量量：{1} ", method.getName(), parameterList.size());
        List<ParamModel> resultParamList = new ArrayList<>(ZERO);
        if (CollectionUtils.isEmpty(parameterList)) {
            return new ApiReqParamModel();
        }
        ParamModel paramModel = null;
        //最大层级 默认 1
        int paramTotalLevel = ONE;
        int paramTotalSize = ONE;
        List<String> jsonExampleList = new ArrayList<>(parameterList.size());
        for (JavaParameter parameter : parameterList) {
            //参数全名
            String parameterFullName = parameter.getFullyQualifiedName();

            ApiLogUtil.debug("【入参】>> 当前方法：{0} 参数：{1} ,name={2} ", method.getName(), parameterFullName, parameter.getName());
            //参数过滤
            if (paramExcludeSet.contains(parameterFullName) || config.getApiExcludeParamNames().contains(parameter.getName())) {
                ApiLogUtil.debug("【入参】>>【参数排除】当前方法：{0},参数名：{1} ,已经自动过滤", method.getName(), parameter.getName());
                continue;
            }
            //判断是否为-java.包下的类，或java基础类型
            if (ApiTypeMatchUtil.isJavaClass(parameterFullName) ||
                    ApiSetConstant.BASE_TYPE_SET.contains(parameterFullName)) {
                paramModel = convertReqParamModel(parameter);
                resultParamList.add(paramModel);
                continue;
            }
            ApiLogUtil.debug("【入参】>> 当前方法：{0} 参数：{1} , 进入自定义参数处理 ", method.getName(), parameterFullName, parameter.getName());
            //自定义参数类处理
            ApiFieldParamModel fieldParamModel = getFieldModel(rootBuilder, lessPackageSet, parameterFullName, Boolean.FALSE);
            resultParamList.addAll(fieldParamModel.getParamList());
            //参数类型判断-是否进行驼峰转下划线
            if (ApiParamTypeEnum.SNAKE_CASE.equals(config.getApiParamType())) {
                jsonExampleList.add(getSnakeCaseJson(fieldParamModel.getJsonObject()));
            } else {
                jsonExampleList.add(ApiJsonFormatUtil.toFormatJson(fieldParamModel.getJsonObject()));
            }
            //层级处理
            if (fieldParamModel.getParamTotalLevel() > paramTotalLevel) {
                paramTotalLevel = fieldParamModel.getParamTotalLevel();
            }
            //参数总数
            paramTotalSize = paramTotalSize + fieldParamModel.getParamTotalSize();
        }
        ApiLogUtil.debug("【入参】 >> 最大层级：{0} 参数总数：{1}", paramTotalLevel, paramTotalLevel);
        return new ApiReqParamModel(resultParamList, paramTotalSize, paramTotalLevel, jsonExampleList);
    }

    /**
     * 出参-读取
     *
     * @param rootBuilder
     * @param method      方法
     * @author mengqiang
     */
    public static ApiResParamModel getResParam(JavaProjectBuilder rootBuilder, Set<String> lessPackageSet, JavaMethod method, ApiConfig config) {
        //返回类
        String genericFullyReturnName = method.getReturns().getGenericFullyQualifiedName();
        ApiLogUtil.debug("【出参】>> 当前方法：{0} , 参数数量：{1}", method.getName(), genericFullyReturnName);
        //无返回值
        if (CommonConstant.VOID_STR.equals(genericFullyReturnName)) {
            //是否存在自定义返回值
            if (!CollectionUtils.isEmpty(config.getRootRespFieldList())) {
                return buildCustomApiResParam(config);
            }
            return null;
        }
        ApiResParamModel currentApi = null;
        boolean isArrayFlag = false;
        //返回值-是否是为 - 基本类型
        if (ApiSetConstant.BASE_TYPE_SET.contains(genericFullyReturnName)) {
            currentApi = buildApiResParamModel(method);
        }
        //返回值-是否是java 包下的返回值并且无泛型
        else if (ApiTypeMatchUtil.isJavaClass(genericFullyReturnName) && !ApiTypeMatchUtil.isObjectTClass(genericFullyReturnName)) {
            currentApi = buildApiResParamModel(method);
        } else {
            ApiResParamModel childModel = null;
            //匹配-返参字符串中的泛型
            String[] resClassArray = ApiTypeMatchUtil.getTArrayByTypeStr(genericFullyReturnName);
            //泛型类倒叙，从子参数开始
            for (int i = resClassArray.length - ONE; i >= ZERO; i--) {
                ApiLogUtil.debug("【出参】>> 方法：{0} , 返回参数当前：{1} ", method.getName(), resClassArray[i]);
                //返回值-是否是java 包下的返回值
                if (ApiTypeMatchUtil.isJavaClass(genericFullyReturnName) && ApiTypeMatchUtil.isStrArray(resClassArray[i])) {
                    isArrayFlag = true;
                    continue;
                }
                //获取参数
                ApiFieldParamModel currentParamModel = getFieldModel(rootBuilder, lessPackageSet, resClassArray[i], Boolean.TRUE);
                //构建当前参数
                currentApi = convertResApiParam(currentParamModel, config);
                //若存在子级 -> 添加到子集
                doResAddChildModel(childModel, currentApi);
                //子集赋值
                childModel = currentApi;
            }
            ApiLogUtil.debug("【出参】>> 泛型层级：{0}", resClassArray.length);
        }
        if (null == currentApi) {
            return null;
        }
        ApiLogUtil.debug("【出参】>> 参数最大层级：{0} , 参数总数：{1},", currentApi.getParamTotalLevel(), currentApi.getParamTotalSize());
        return buildResParamModel(currentApi, config, isArrayFlag);
    }


    /**
     * 获取参数集合- 基础公共方法
     * 使用递归方式，可层级获取，可获取继承的父级参数
     * 用途分类：入参，返参 通用
     *
     * @param rootBuilder   根路径类资源
     * @param clazzFullName 当前类全路径
     * @return 参数集合
     * @author mengqiang
     */
    private static ApiFieldParamModel getFieldModel(JavaProjectBuilder rootBuilder, Set<String> lessPackageSet, String clazzFullName, Boolean paramRequiredCheckFlag) {
        //参数总数
        AtomicInteger paramTotalSize = new AtomicInteger(ZERO);
        //最大层级
        AtomicInteger paramTotalLevel = new AtomicInteger(ZERO);
        //当前层级
        AtomicInteger currentLevel = new AtomicInteger(ONE);
        AtomicInteger hasChildParamTotalSize = new AtomicInteger(ZERO);
        ApiAtomicCountModel countModel = new ApiAtomicCountModel(paramTotalSize, paramTotalLevel, currentLevel);
        countModel.setHasChildParamTotalSize(hasChildParamTotalSize);
        ApiFieldParamModel fieldBase = getFieldBase(rootBuilder, countModel, null, null, clazzFullName, paramRequiredCheckFlag);
        //类名不包括 点，可能是因为导包中使用了 .* ,尝试再次获取
        if (!clazzFullName.contains(ApiStringPool.DOT) &&
                CollectionUtils.isEmpty(fieldBase.getParamList())
                && CollectionUtils.isNotEmpty(lessPackageSet)) {
            ApiLogUtil.debug("【参数获取-尝试二次获取】>> 类名:{0} 将尝试再次获取！", clazzFullName);
            for (String lessPackage : lessPackageSet) {
                String currentClassName = lessPackage.replace("*", "").concat(clazzFullName);
                ApiLogUtil.debug("【参数获取-尝试二次获取】>> 当前拼接后类名:{0}", currentClassName);
                ApiFieldParamModel currentBase = getFieldBase(rootBuilder, countModel, null, null, currentClassName, paramRequiredCheckFlag);
                if (CollectionUtils.isNotEmpty(currentBase.getParamList())) {
                    ApiLogUtil.debug("【参数获取-尝试二次获取】>> 当前类不为空将返回:{0}", currentClassName);
                    return currentBase;
                }
            }
        }
        return fieldBase;
    }

    /**
     * 获取参数集合- 基础公共方法
     * 使用递归方式，可层级获取，可获取继承的父级参数
     *
     * @param rootBuilder   根路径类资源
     * @param parentModel   父级参数（第一层父级为空）
     * @param clazzFullName 当前类全路径
     * @return 参数集合
     * @author mengqiang
     */
    private static ApiFieldParamModel getFieldBase(JavaProjectBuilder rootBuilder, ApiAtomicCountModel countModel,
                                                   ParamModel parentModel, JSONObject parentJsonObject, String clazzFullName, Boolean paramRequiredCheckFlag) {
        //读取当前类
        JavaClass currentClass = rootBuilder.getClassByName(clazzFullName);
        if (null == currentClass) {
            ApiLogUtil.debug("【参数获取】>> 类名:{0} 当前类为空！", clazzFullName);
            return new ApiFieldParamModel(new ArrayList<>(CommonConstant.ZERO));
        }
        //当前类所有属性
        List<JavaField> fieldList = ApiClassHelper.getAllFields(currentClass);
        if (CollectionUtils.isEmpty(fieldList)) {
            ApiLogUtil.debug("【参数获取】>> 类名:{0} 当前类无属性！currentClass:{1}", clazzFullName, currentClass);
            return new ApiFieldParamModel(new ArrayList<>(CommonConstant.ZERO));
        }
        //记录当前级别
        int level = countModel.getCurrentLevel().intValue();
        if (countModel.getCurrentLevel().intValue() > countModel.getParamTotalLevel().intValue()) {
            countModel.getParamTotalLevel().set(countModel.getCurrentLevel().intValue());
        }
        ParamModel currentParam = null;
        List<ParamModel> paramList = new ArrayList<>();
        //创建有序的 JSONObject 对象
        JSONObject currentJsonObject = new JSONObject(true);
        for (JavaField field : fieldList) {
            //参数全名
            String fullFileTypeName = field.getType().getGenericFullyQualifiedName();
            //需要移除的属性名
            if (ApiSetConstant.FIELD_EXCLUDE_SET.contains(field.getName())) {
                ApiLogUtil.debug("【参数获取】>>【参数过滤】参数名:{0} 参数描述:{1},参数类型:{2}", field.getName(), field.getComment(), fullFileTypeName);
                continue;
            }
            //构建参数
            currentParam = buildFileModel(currentJsonObject, field, paramRequiredCheckFlag);
            ApiLogUtil.debug("【参数获取】>> 当前参数名:{0} 参数描述:{1},参数类型:{2},当前层级：{3}-{4}-{5}", field.getName(), field.getComment(),
                    fullFileTypeName, countModel.getCurrentLevel(), countModel.getParamTotalLevel(), field.getType().getFullyQualifiedName());
            //累加当前子集数量
            if (parentModel != null) {
                parentModel.setChildParamSize(parentModel.getChildParamSize() + ONE);
            }
            //根据参数类型处理参数
            boolean hasChildFlag = doProcessParamByType(rootBuilder, countModel, currentParam, currentJsonObject, clazzFullName, fullFileTypeName, paramRequiredCheckFlag);
            // 拥有子集，计数
            if (parentModel != null && hasChildFlag) {
                parentModel.setHasChildParamSize(parentModel.getHasChildParamSize() + ONE);
                ApiLogUtil.debug("【参数获取】>>【存在父级】名称：{0},当前Size:{1}", parentModel.getName(), parentModel.getHasChildParamSize());
            }
            //同级别属性，当前层级相同
            countModel.getCurrentLevel().set(level);
            //父级
            if (null != parentModel) {
                if (CollectionUtils.isEmpty(parentModel.getChildren())) {
                    List<ParamModel> paramModelList = new ArrayList<>();
                    paramModelList.add(currentParam);
                    parentModel.setChildren(paramModelList);
                } else {
                    parentModel.getChildren().add(currentParam);
                }
                //跨级+累加子集数量
                parentModel.setChildParamSize(parentModel.getChildParamSize() + currentParam.getChildParamSize());
                parentModel.setHasChildParamSize(parentModel.getHasChildParamSize() + currentParam.getHasChildParamSize());
                ApiLogUtil.debug("【参数获取】>>【递归后-存在父级】父名称：{0},父当前Size:{1}，子名称：{2},子当前Size:{3}", parentModel.getName(),
                        parentModel.getHasChildParamSize(), currentParam.getName(), currentParam.getHasChildParamSize());
                //转换-处理json
                convertJson(parentModel, parentJsonObject, currentJsonObject);
            }
            countModel.getParamTotalSize().incrementAndGet();
            paramList.add(currentParam);
        }
        return convertApiFieldParamModel(paramList, currentJsonObject, countModel);
    }

    /**
     * 根据参数类型处理参数
     * 注：自定义包，递归获取属性
     */
    private static boolean doProcessParamByType(JavaProjectBuilder rootBuilder, ApiAtomicCountModel countModel, ParamModel currentParam,
                                                JSONObject currentJsonObject, String clazzFullName, String fullFileTypeName, Boolean paramRequiredCheckFlag) {
        //非java包下的数组
        if (!ApiTypeMatchUtil.isJavaClass(fullFileTypeName) && ApiTypeMatchUtil.isArray(fullFileTypeName)) {
            String currentClassName = ApiStringUtil.replaceInBracket(fullFileTypeName);
            countModel.getCurrentLevel().incrementAndGet();
            //进入递归
            getFieldBase(rootBuilder, countModel, currentParam, currentJsonObject, currentClassName, paramRequiredCheckFlag);
            return true;
        }
        //java 包下的泛型 例如：List<User>,List<String>
        else if (ApiTypeMatchUtil.isJavaClass(fullFileTypeName) && ApiTypeMatchUtil.isObjectTClass(fullFileTypeName)) {
            //匹配字符串中的泛型
            String[] tArrayByTypeStr = ApiTypeMatchUtil.getTArrayByTypeStr(fullFileTypeName);
            ApiLogUtil.debug("【参数获取】>> 属性匹配字符串中的泛型：{0} ", Arrays.asList(tArrayByTypeStr));
            for (String type : tArrayByTypeStr) {
                //不是当前类，并且非java包下的类
                if (!clazzFullName.equals(type) && !ApiTypeMatchUtil.isJavaClass(type)) {
                    countModel.getCurrentLevel().incrementAndGet();
                    getFieldBase(rootBuilder, countModel, currentParam, currentJsonObject, type, paramRequiredCheckFlag);
                    return true;
                } else {
                    //非自定义泛型类
                    currentParam.setType(currentParam.getType().concat(CommonConstant.TCLASS_STR));
                }
            }
        }
        //当前参数-非java.包下的类
        else if (!ApiTypeMatchUtil.isJavaClass(fullFileTypeName) && !ApiSetConstant.BASE_TYPE_SET.contains(fullFileTypeName)) {
            //总层级 ++
            countModel.getCurrentLevel().incrementAndGet();
            ApiLogUtil.debug("【参数获取】>> 【递归获取属性】自定义类：{0} 当前层级：{1}", fullFileTypeName, countModel.getCurrentLevel().intValue());
            //进入递归
            getFieldBase(rootBuilder, countModel, currentParam, currentJsonObject, fullFileTypeName, paramRequiredCheckFlag);
            return true;
        }
        return false;
    }

    /**
     * 构建属性model
     */
    private static ParamModel buildFileModel(JSONObject currentJsonObject, JavaField field, Boolean paramRequiredFlag) {
        //当前属性注解
        Map<String, JavaAnnotation> fileAnnotationMap = ApiMatchAnnoHelper.getFileAnnotationMap(field);

        ParamModel currentParam = new ParamModel();
        currentParam.setType(field.getType().getName());
        currentParam.setName(field.getName());
        //属性注释处理。替换换行符
        if (StringUtils.isNotBlank(field.getComment())) {
            String replace = field.getComment()
                    .replaceAll("\\n", "")
                    .replaceAll("\\r", "");
            currentParam.setDescription(replace);
        }
        //是否必填
        currentParam.setRequiredFlag(ApiMatchAnnoHelper.getRequiredFlagFlag(fileAnnotationMap));
        if (paramRequiredFlag) {
            //是否必填
            currentParam.setRequiredFlag(CommonConstant.ONE);
        } else {
            //是否必填
            currentParam.setRequiredFlag(ApiMatchAnnoHelper.getRequiredFlagFlag(fileAnnotationMap));
        }
        //长度处理
        currentParam.setLength(ApiMatchAnnoHelper.getLength(fileAnnotationMap).replaceAll("\"", ""));
        ApiLogUtil.debug("【参数获取】>> 当前参数名:{0},是否必填:{1},长度:{2},注解集size:{3},注解集:{4}", field.getName(), currentParam.getRequiredFlag(),
                currentParam.getLength(), fileAnnotationMap.size(), fileAnnotationMap);
        //json 参数构建
        currentJsonObject.put(field.getName(), getJsonValueByType(field));
        return currentParam;
    }

    /**
     * 转换参数
     */
    private static ApiFieldParamModel convertApiFieldParamModel(List<ParamModel> paramList, JSONObject currentJsonObject, ApiAtomicCountModel countModel) {
        ApiFieldParamModel paramModel = new ApiFieldParamModel();
        paramModel.setParamList(paramList);
        paramModel.setJsonObject(currentJsonObject);
        paramModel.setParamTotalLevel(countModel.getParamTotalLevel().intValue());
        paramModel.setParamTotalSize(countModel.getParamTotalSize().intValue());
        return paramModel;
    }


    /**
     * 存在子集，添加到子集
     * 用途分类：返参
     */
    private static void doResAddChildModel(ApiResParamModel childModel, ApiResParamModel currentApi) {
        if (null == childModel || CollectionUtils.isEmpty(currentApi.getParamList())) {
            return;
        }
        //存在子级
        //遍历当前父级找到附加子集的属性
        for (ParamModel paramModel : currentApi.getParamList()) {
            ApiLogUtil.debug("【出参】>> 【泛型层级处理】当前参数：{0}", paramModel.getType());
            //属性类型为Object或 T
            if (ApiTypeMatchUtil.isObject(paramModel.getType()) ||
                    ApiTypeMatchUtil.isTSimpleClass(paramModel.getType()) ||
                    ApiTypeMatchUtil.isTClass(paramModel.getType())) {
                //子参数数量
                paramModel.setChildParamSize(childModel.getParamTotalSize());
                paramModel.setChildren(childModel.getParamList());
                //替换<T> 为空
                paramModel.setType(paramModel.getType().replace(CommonConstant.TCLASS_STR, ApiStringPool.EMPTY));
                JSONObject jsonObject = JSONObject.parseObject(currentApi.getJsonExample(), Feature.OrderedField);
                //json 示例处理
                //是否为集合类型
                if (ApiTypeMatchUtil.isStrArray(paramModel.getType())) {
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.add(JSONObject.parseObject(childModel.getJsonExample(), Feature.OrderedField));
                    jsonObject.put(paramModel.getName(), jsonArray);
                } else {
                    jsonObject.put(paramModel.getName(), JSONObject.parseObject(childModel.getJsonExample(), Feature.OrderedField));
                }
                currentApi.setJsonExample(ApiJsonFormatUtil.toFormatJson(jsonObject));
                //参数总数累加
                currentApi.setParamTotalSize(currentApi.getParamTotalSize() + childModel.getParamTotalSize());
                //参数层级累加
                currentApi.setParamTotalLevel(currentApi.getParamTotalLevel() + childModel.getParamTotalLevel());
                break;
            }
        }
        //处理返参中，子参数数量
        doBuildHasChildParamSize(currentApi);
    }

    /**
     * 构建自定义-返回参数结构
     */
    private static ApiResParamModel buildCustomApiResParam(ApiConfig config) {
        ApiResParamModel resParamModel = new ApiResParamModel();
        ParamModel paramModel;
        JSONObject jsonObject = new JSONObject(true);
        List<ParamModel> paramList = new ArrayList<>(config.getRootRespFieldList().size());
        for (ApiRootResField rootRespField : config.getRootRespFieldList()) {
            Class typeClass = rootRespField.getTypeClass();
            paramModel = new ParamModel();
            paramModel.setDescription(rootRespField.getDesc());
            paramModel.setName(rootRespField.getName());
            paramModel.setType(typeClass.getSimpleName());
            paramModel.setRequiredFlag(ONE);
            jsonObject.put(rootRespField.getName(), ApiJsonRandomValueUtil.randomValue(typeClass.getTypeName(), rootRespField.getName()));
            paramList.add(paramModel);
        }
        resParamModel.setParamList(paramList);
        if (ApiParamTypeEnum.SNAKE_CASE.equals(config.getApiParamType())) {
            resParamModel.setJsonExample(getSnakeCaseJson(jsonObject));
        } else {
            resParamModel.setJsonExample(ApiJsonFormatUtil.toFormatJson(jsonObject));
        }
        resParamModel.setParamTotalLevel(ONE);
        resParamModel.setParamTotalSize(config.getRootRespFieldList().size());
        return resParamModel;
    }

    /**
     * 自定义返参基类，处理
     */
    private static ApiResParamModel buildResParamModel(ApiResParamModel currentApi, ApiConfig config, boolean isArrayFlag) {
        //是否存在自定义返参基类
        if (null == currentApi || CollectionUtils.isEmpty(currentApi.getParamList())) {
            return currentApi;
        }
        boolean rootRespFieldEmpty = CollectionUtils.isEmpty(config.getRootRespFieldList());
        if (isArrayFlag && rootRespFieldEmpty) {
            List<JSONObject> jsonObjectList = new ArrayList<>(ONE);
            jsonObjectList.add(JSONObject.parseObject(currentApi.getJsonExample(), Feature.OrderedField));
            currentApi.setJsonExample(ApiJsonFormatUtil.toFormatJson(jsonObjectList));
            return currentApi;
        }
        if (rootRespFieldEmpty) {
            return currentApi;
        }
        ApiLogUtil.debug("【自定义返回基类】 >> 属性集大小：{0},当前参数集大小：{1}",
                config.getRootRespFieldList().size(), currentApi.getParamList());
        ApiResParamModel resParamModel = new ApiResParamModel();
        JSONObject jsonObject = new JSONObject(true);
        ParamModel paramModel;
        List<ParamModel> paramList = new ArrayList<>(config.getRootRespFieldList().size());
        for (ApiRootResField rootRespField : config.getRootRespFieldList()) {
            Class typeClass = rootRespField.getTypeClass();
            paramModel = new ParamModel();
            paramModel.setName(rootRespField.getName());
            paramModel.setType(typeClass.getSimpleName());
            paramModel.setDescription(rootRespField.getDesc());
            jsonObject.put(rootRespField.getName(), ApiJsonRandomValueUtil.randomValue(typeClass.getTypeName(), rootRespField.getName()));
            //是否为父级参数
            if (rootRespField.getParent()) {
                paramModel.setChildren(currentApi.getParamList());
                paramModel.setChildParamSize(currentApi.getParamTotalSize());
                if (typeClass.getSimpleName().equalsIgnoreCase(CommonConstant.LIST_STR) || isArrayFlag) {
                    List<JSONObject> jsonObjectList = new ArrayList<>(ONE);
                    jsonObjectList.add(JSONObject.parseObject(currentApi.getJsonExample(), Feature.OrderedField));
                    jsonObject.put(rootRespField.getName(), jsonObjectList);
                } else {
                    jsonObject.put(rootRespField.getName(), JSONObject.parseObject(currentApi.getJsonExample(), Feature.OrderedField));
                }
            }
            paramList.add(paramModel);
        }
        if (ApiParamTypeEnum.SNAKE_CASE.equals(config.getApiParamType())) {
            resParamModel.setJsonExample(getSnakeCaseJson(jsonObject));
        } else {
            resParamModel.setJsonExample(ApiJsonFormatUtil.toFormatJson(jsonObject));
        }
        resParamModel.setParamList(paramList);
        resParamModel.setParamTotalLevel(currentApi.getParamTotalLevel() + ONE);
        ApiLogUtil.debug("【自定义返回基类】 >> 当前集合大小：{0}", paramList.size());
        resParamModel.setParamTotalSize(currentApi.getParamTotalSize() + paramList.size());
        //处理返参中，子参数数量
        doBuildHasChildParamSize(resParamModel);
        return resParamModel;
    }

    /**
     * 泛型处理后循环前两层计算返参中存在子参数的数量
     * 用途分类：出参
     */
    private static void doBuildHasChildParamSize(ApiResParamModel currentApi) {
        if (null == currentApi || CollectionUtils.isEmpty(currentApi.getParamList())) {
            return;
        }
        ApiLogUtil.newDebugLine();
        //data
        for (ParamModel paramModel : currentApi.getParamList()) {
            if (CollectionUtils.isNotEmpty(paramModel.getChildren())) {
                //拥有孙子集
                boolean hasGrandSonFlag = false;
                boolean hasChildParamSizeFlag = false;
                for (ParamModel childM : paramModel.getChildren()) {
                    if (CollectionUtils.isNotEmpty(childM.getChildren())) {
                        hasGrandSonFlag = true;
                        if (childM.getHasChildParamSize() == ZERO) {
                            paramModel.setHasChildParamSize(paramModel.getHasChildParamSize() + ONE);
                        }
                        if (childM.getHasChildParamSize() > ZERO) {
                            hasChildParamSizeFlag = true;
                            paramModel.setHasChildParamSize(paramModel.getHasChildParamSize() + childM.getHasChildParamSize());
                        }
                        ApiLogUtil.debug("【返参处理】>> 【拥有自参数的参数数量】父级名：{0} 父当前层级：{1},子集名：{2}",
                                paramModel.getName(), paramModel.getHasChildParamSize(), childM.getName());
                    }
                }
                if (!hasChildParamSizeFlag && hasGrandSonFlag) {
                    paramModel.setHasChildParamSize(paramModel.getHasChildParamSize() - ONE);
                }
                if (hasGrandSonFlag) {
                    paramModel.setHasChildParamSize(paramModel.getHasChildParamSize() + ONE);
                }
                ApiLogUtil.debug("【返参处理】>> 【匹配后层级】自定义类：{0} 最大层级：{1},hasChildChild：{2}",
                        paramModel.getName(), paramModel.getHasChildParamSize(), hasGrandSonFlag);
            }
        }
    }


    /**
     * 根据参数类型-随机生成json值
     *
     * @return string
     */
    private static Object getJsonValueByType(JavaField field) {
        String paramType = field.getType().getGenericFullyQualifiedName();
        String paramName = field.getName();
        //泛型直接返回
        if (ApiTypeMatchUtil.isObjectTClass(paramType)) {
            //匹配字符串中的泛型
            String[] paramArray = ApiTypeMatchUtil.getTArrayByTypeStr(paramType);
            return ApiJsonRandomValueUtil.randomValue(paramArray, paramName);
        }
        //非泛型
        Object jsonValue = ApiJsonRandomValueUtil.randomValue(new String[]{paramType}, paramName);
        //是否为数组
        if (ApiTypeMatchUtil.isArray(paramType)) {
            List<Object> list = new ArrayList<>();
            list.add(jsonValue);
            return list;
        }
        return jsonValue;
    }

    /**
     * 转换 json 参数处理
     * 注：判断参数是对象还是集合
     */
    private static void convertJson(ParamModel parentModel, JSONObject parentJsonObject, JSONObject currentJsonObject) {
        //是否为集合类型
        if (ApiTypeMatchUtil.isStrArray(parentModel.getType())) {
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(currentJsonObject);
            parentJsonObject.put(parentModel.getName(), jsonArray);
        } else {
            parentJsonObject.put(parentModel.getName(), currentJsonObject);
        }
    }

    /**
     * 转换参数model
     * 用途分类：入参
     */
    private static ParamModel convertReqParamModel(JavaParameter parameter) {
        ParamModel paramModel = new ParamModel();
        paramModel.setName(parameter.getName());
        paramModel.setType(parameter.getType().getGenericValue());
        //java包下的入参-是否必填校验
        paramModel.setRequiredFlag(ApiMatchAnnoHelper.getJavaTypeRequired(parameter));
        return paramModel;
    }

    /**
     * 返回参数为java包下的参数时，处理返回值
     * 用途分类：返参
     */
    private static ApiResParamModel buildApiResParamModel(JavaMethod method) {
        List<ParamModel> paramList = new ArrayList<>();
        ParamModel paramModel = new ParamModel();
        paramModel.setName(ApiStringPool.DASH);
        paramModel.setType(method.getReturns().getName());
        paramModel.setRequiredFlag(CommonConstant.ONE);
        paramList.add(paramModel);
        ApiResParamModel currentApi = new ApiResParamModel();
        currentApi.setParamTotalSize(CommonConstant.ONE);
        currentApi.setParamList(paramList);
        return currentApi;
    }

    /**
     * 转换基础参数
     * 用途分类：返参
     */
    private static ApiResParamModel convertResApiParam(ApiFieldParamModel currentParamModel, ApiConfig config) {
        ApiResParamModel currentApi = new ApiResParamModel();
        currentApi.setParamList(currentParamModel.getParamList());
        currentApi.setParamTotalLevel(currentParamModel.getParamTotalLevel());
        currentApi.setParamTotalSize(currentParamModel.getParamTotalSize());
        //参数-驼峰转下划线
        if (ApiParamTypeEnum.SNAKE_CASE.equals(config.getApiParamType())) {
            currentApi.setJsonExample(getSnakeCaseJson(currentParamModel.getJsonObject()));
        } else {
            currentApi.setJsonExample(ApiJsonFormatUtil.toFormatJson(currentParamModel.getJsonObject()));
        }
        return currentApi;
    }


    /**
     * json 驼峰转下划线
     */
    private static String getSnakeCaseJson(Object object) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
                ApiJsonFormatUtil.toFormatJson(object));
    }

}