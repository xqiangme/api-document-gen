package com.tool.api.generate.core.util.helper;

import com.alibaba.fastjson.JSON;
import com.tool.api.generate.core.config.ApiConfig;
import com.tool.api.generate.core.config.custom.ApiGateWayField;
import com.tool.api.generate.core.constants.ApiSetConstant;
import com.tool.api.generate.core.constants.CommonConstant;
import com.tool.api.generate.core.constants.ValidationConstant;
import com.tool.api.generate.core.enums.ApiGenerateTypeEnum;
import com.tool.api.generate.core.enums.ApiParamTypeEnum;
import com.tool.api.generate.core.exception.ApiGenerateException;
import com.tool.api.generate.core.model.*;
import com.tool.api.generate.core.util.tool.*;
import com.tool.api.generate.start.ApiGenerateStart;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.*;
import freemarker.template.Template;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.util.*;

/**
 * 接口生成辅助类
 *
 * @author mengqiang
 */
public class ApiGenerateHelper {

    /**
     * 执行公共方法
     *
     * @author mengqiang
     */
    public static void doRun(String projectRootPath, ApiConfig config) {
        long start = System.currentTimeMillis();
        JavaProjectBuilder rootPathJavaFile = ApiGenerateHelper.getJavaFileByPath(projectRootPath);
        doRunBase(rootPathJavaFile, config, start);
    }

    /**
     * 执行公共方法
     *
     * @author mengqiang
     */
    public static void doRunXml(ApiConfig config) {
        long start = System.currentTimeMillis();
        JavaProjectBuilder rootPathJavaFile = ApiGenerateHelper.getJavaFileByPathList(config.getProjectScanPaths());
        doRunBase(rootPathJavaFile, config, start);
    }

    /**
     * 执行公共方法
     *
     * @author mengqiang
     */
    private static void doRunBase(JavaProjectBuilder rootPathJavaFile, ApiConfig config, long start) {
        //其它扫描包
        ApiGenerateHelper.buildOtherRootPathJavaFile(rootPathJavaFile, config);
        ApiLogUtil.info("扫描到的类总数量：{0} , 扫描耗时：{1} ms", rootPathJavaFile.getClasses().size(), (System.currentTimeMillis() - start));
        if (rootPathJavaFile.getClasses().size() > CommonConstant.ZERO) {
            String generateFilePath;
            //根据包地址生成
            if (ApiGenerateTypeEnum.PACKAGE.equals(config.getGenerateType())) {
                generateFilePath = ApiGenerateHelper.generateByPackage(rootPathJavaFile, ApiGenerateStart.class, config);
            } else {
                //根据接口类生成
                generateFilePath = ApiGenerateHelper.generateByClass(rootPathJavaFile, config);
            }
            ApiLogUtil.generateEnd();
            ApiLogUtil.info("文件地址：{0}", generateFilePath);
        } else {
            ApiLogUtil.generateEnd();
            ApiLogUtil.warn("扫描路径下未扫描到java文件，请确认扫描路径是否准确 !");
        }
        ApiLogUtil.info("执行耗时：{0} ms", (System.currentTimeMillis() - start));
    }


    /**
     * 校验必填参数
     */
    public static void checkRequiredConfig(Class runClazz, ApiConfig config) {
        if (null == runClazz) {
            throw new ApiGenerateException("参数 runClazz 不能为空 !");
        }
        if (null == config) {
            throw new ApiGenerateException("配置 ApiConfig 不能为空 !");
        }
        if (null == config.getGenerateType()) {
            throw new ApiGenerateException("生成类型 generateType 不能为空 !");
        }
        if (ApiGenerateTypeEnum.PACKAGE.equals(config.getGenerateType()) &&
                StringUtils.isBlank(config.getApiPackage())) {
            throw new ApiGenerateException("根据包地址生成 apiPackage 不能为空 !");
        }
        if (ApiGenerateTypeEnum.CLAZZ.equals(config.getGenerateType()) && ObjectUtils.isEmpty(config.getApiClassNameList())) {
            throw new ApiGenerateException("根据类生成 apiClassNameList 不能为空 !");
        }

    }

    /**
     * 校验必填参数
     */
    public static void checkRequiredConfigByXml(ApiConfig config) {
        if (ApiGenerateTypeEnum.PACKAGE.equals(config.getGenerateType()) &&
                StringUtils.isBlank(config.getApiPackagePath())) {
            throw new ApiGenerateException("根据包地址生成 apiPackagePath > value 不能为空 !");
        }
        if (ApiGenerateTypeEnum.CLAZZ.equals(config.getGenerateType()) && ObjectUtils.isEmpty(config.getApiClassPathMap())) {
            throw new ApiGenerateException("根据类生成 apiClassPaths 子集 path 不能为空 !");
        }
    }

    /**
     * 非必填参数默认值
     */
    public static void convertDefaultConfig(String projectRootPath, ApiConfig config) {
        //日志配置
        if (StringUtils.isNotBlank(config.getLogLevel())) {
            ApiLogUtil.LEVEL = ApiLogUtil.getLevelByName(config.getLogLevel());
        }
        //是否生成json 默认 是
        if (null == config.getGenerateJsonFlag()) {
            config.setGenerateJsonFlag(Boolean.TRUE);
        }
        //是否开启 ，根路径确认 默认 是
        if (null == config.getConfirmRootPath()) {
            config.setConfirmRootPath(Boolean.TRUE);
        }
        //默认项目根路径
        if (StringUtils.isBlank(config.getGenerateFilePath())) {
            config.setGenerateFilePath(config.getGenerateFilePath());
        }
        //网关自定义注解属性
        if (null == config.getGateWayField()) {
            config.setGateWayField(new ApiGateWayField(ApiStringPool.EMPTY));
        }
        ApiGateWayField gateWayField = config.getGateWayField();
        //开放接口注解配置
        boolean gateWayAnnotationExistFlag = StringUtils.isNotBlank(gateWayField.getAnnotationName());
        if (gateWayAnnotationExistFlag && StringUtils.isBlank(gateWayField.getAnnotationFieldName())) {
            config.getGateWayField().setAnnotationFieldName(CommonConstant.METHOD_STR);
        }
        //参数类型
        if (null == config.getApiParamType()) {
            config.setApiParamType(ApiParamTypeEnum.COMMON);
        }
        //需要排除的参数名称集
        if (CollectionUtils.isEmpty(config.getApiExcludeParamNames())) {
            config.setApiExcludeParamNames(new HashSet<>(CommonConstant.ZERO));
        }
    }

    /**
     * 扫描根路径下java文件
     */
    public static JavaProjectBuilder getJavaFileByPathList(List<String> pathList) {
        JavaProjectBuilder rootBuilder = new JavaProjectBuilder();
        rootBuilder.setEncoding(ApiStringPool.UTF_8);
        if (CollectionUtils.isEmpty(pathList)) {
            return rootBuilder;
        }
        for (String path : pathList) {
            rootBuilder.addSourceTree(new File(path));
        }
        return rootBuilder;
    }

    /**
     * 扫描根路径下java文件
     */
    public static JavaProjectBuilder getJavaFileByPath(String path) {
        JavaProjectBuilder rootBuilder = new JavaProjectBuilder();
        rootBuilder.setEncoding(ApiStringPool.UTF_8);
        rootBuilder.addSourceTree(new File(path));
        return rootBuilder;
    }

    /**
     * 扫描根路径下java文件
     */
    public static void buildOtherRootPathJavaFile(JavaProjectBuilder rootBuilder, ApiConfig config) {
        if (CollectionUtils.isEmpty(config.getOtherSourceCodePaths())) {
            return;
        }
        for (String filePath : config.getOtherSourceCodePaths()) {
            rootBuilder.addSourceTree(new File(filePath));
            ApiLogUtil.info("其它扫描路径：{0} ", filePath);
        }
    }

    /**
     * 包地址 -> 生成接口文档
     * 注：生成包下所有接口类
     *
     * @param rootPathJavaFile 项目目录下java文件
     * @param config           生成相关配置
     * @return 生成文件地址
     * @author mengqiang
     */
    public static String generateByPackage(JavaProjectBuilder rootPathJavaFile, Class runClazz, ApiConfig config) {
        //生成包地址
        String generatePath;
        if (StringUtils.isNotBlank(config.getApiPackagePath())) {
            generatePath = config.getApiPackagePath();
        } else {
            generatePath = ApiFilePathUtil.getClassProjectPackagePath(runClazz, config.getApiPackage());
        }
        //包下的文件集
        Collection<JavaClass> generateClassList = ApiClassHelper.getClassListByPath(generatePath);
        if (CollectionUtils.isEmpty(generateClassList)) {
            throw new ApiGenerateException("根据包地址生成接口文档 , 当前包地址：{0} , 包下无java文件！", generatePath);
        }
        if (CollectionUtils.isEmpty(generateClassList)) {
            throw new ApiGenerateException("根据包地址生成接口文档 , 当前包地址：{0} , 包下无java文件！", generatePath);
        }
        ApiLogUtil.info("根据包地址生成接口文档 , 当前包地址：{0} , 包下类数量：{1}", generatePath, generateClassList.size());
        //需要排除的参数全路径集
        Set<String> paramExcludeSet = getExcludeParamFullPathSet(config);
        List<ApiOutModel> apiOutModelList = new ArrayList<>();
        int apiSize = CommonConstant.ZERO;
        for (JavaClass clazz : generateClassList) {
            ApiOutModel apiOutModel = getApiOutModel(rootPathJavaFile, clazz, paramExcludeSet, config, new HashSet<>(0));
            if (null == apiOutModel) {
                continue;
            }
            apiSize = apiSize + apiOutModel.getApiBaseList().size();
            apiOutModelList.add(apiOutModel);
        }
        if (CollectionUtils.isEmpty(apiOutModelList)) {
            throw new ApiGenerateException("根据包地址生成接口文档 , 当前包地址：{0} , 无需要生成的方法", generatePath);
        }
        ApiLogUtil.info("本次生成参接口总数：{0} ", apiSize);
        return writeApiFile(apiOutModelList, config);
    }

    /**
     * 类地址 -> 生成接口文档
     * 注：生成包下所有接口类
     *
     * @param rootPathJavaFile 项目目录下java文件
     * @param config           生成相关配置
     * @return 生成文件地址
     * @author mengqiang
     */
    public static String generateByClass(JavaProjectBuilder rootPathJavaFile, ApiConfig config) {
        //是否使用绝对路径方式
        boolean isPathWay = !ObjectUtils.isEmpty(config.getApiClassPathMap());
        Map<String, Set<String>> apiClassMap;
        if (isPathWay) {
            apiClassMap = config.getApiClassPathMap();
        } else {
            apiClassMap = config.getApiClassNameList();
        }
        ApiLogUtil.info("文档生成类型：根据接口类集生成接口文档 , 数量：{0}", apiClassMap.size());
        List<ApiOutModel> apiOutModelList = new ArrayList<>();
        //需要排除的参数全路径集
        Set<String> paramExcludeSet = getExcludeParamFullPathSet(config);
        int apiSize = CommonConstant.ZERO;
        for (Map.Entry<String, Set<String>> clazzName : apiClassMap.entrySet()) {
            String generatePath;
            if (isPathWay) {
                generatePath = clazzName.getKey();
            } else {
                Class<?> aClass = null;
                try {
                    aClass = Class.forName(clazzName.getKey());
                } catch (Exception e) {
                    throw new ApiGenerateException("根据类地址生成接口文档 , 当前类地址：{0} , 获取类失败！", clazzName);
                }
                //生成类地址
                generatePath = ApiFilePathUtil.getClassProjectJavaPath(aClass, clazzName.getKey());
            }
            ApiLogUtil.info("生成类地址：{0} ", generatePath);
            //包下的文件集
            Collection<JavaClass> generateClassList = ApiClassHelper.getClassListByPath(generatePath);
            if (CollectionUtils.isEmpty(generateClassList)) {
                throw new ApiGenerateException("根据类地址生成接口文档 , 当前类地址：{0} , 文件不存在！", generatePath);
            }
            //构建生成集合
            for (JavaClass clazz : generateClassList) {
                ApiOutModel apiOutModel = getApiOutModel(rootPathJavaFile, clazz, paramExcludeSet, config, clazzName.getValue());
                if (null == apiOutModel) {
                    continue;
                }
                apiSize = apiSize + apiOutModel.getApiBaseList().size();
                apiOutModelList.add(apiOutModel);
            }
        }
        if (CollectionUtils.isEmpty(apiOutModelList)) {
            throw new ApiGenerateException("根据类地址生成接口文档 ,类地址：{0} 无需要生成的方法", config.getApiClassNameList());
        }
        ApiLogUtil.info("本次生成参接口总数：{0} ", apiSize);
        return writeApiFile(apiOutModelList, config);
    }

    /**
     * 写出文件
     */
    private static String writeApiFile(List<ApiOutModel> apiOutModelList, ApiConfig config) {
        ApiWriteModel apiWriteModel = new ApiWriteModel();
        //参数类型
        apiWriteModel.setParamType(config.getApiParamType().getType());
        //日志级别
        apiWriteModel.setLogLevel(ApiLogUtil.LEVEL);
        apiWriteModel.setGenerateJsonFlag(config.getGenerateJsonFlag());
        apiWriteModel.setInterfacePathShowFlag(config.getInterfacePathShowFlag());
        apiWriteModel.setApiWriteList(apiOutModelList);
        //头信息配置
        if (CollectionUtils.isNotEmpty(config.getHeaderFieldList())) {
            apiWriteModel.setHeaderFieldList(config.getHeaderFieldList());
        }
        if (StringUtils.isNotBlank(config.getContentType())) {
            apiWriteModel.setContentType(config.getContentType());
        }
        //写出文件
        String filePath = doWriteApi(config.getGenerateFilePath(), apiWriteModel);
        ApiLogUtil.debug("生成的构建参数：{0} ", JSON.toJSONString(apiWriteModel));
        return filePath;
    }

    /**
     * 根据模板创建文件
     */
    private static String doWriteApi(String generateFilePath, ApiWriteModel apiWriteModel) {
        //使用模板名称
        String templateName = CommonConstant.TEMPLATE_NAME;
        //读取模板文件
        Template template = ApiTemplateUtils.getTemplate(templateName);
        //组装生成文件地址
        String filePath = generateFilePath.concat(ApiFileNameUtil.getFileName());
        //写出文件
        ApiWriteUtil.write(template, apiWriteModel, filePath);
        return filePath;
    }

    /**
     * 获取生成文件参数model
     */
    private static ApiOutModel getApiOutModel(JavaProjectBuilder rootBuilder, JavaClass clazz, Set<String> paramExcludeSet, ApiConfig config, Set<String> methodSets) {
        //接口根路径描述
        String apiRootComment = StringUtils.isBlank(clazz.getComment()) ? clazz.getName() : clazz.getComment();

        //获取基础路径
        String apiRootUrl = ApiControllerHelper.getApiRootUrl(clazz, config);
        //项目基础路径
        String projectRootUrl = ApiControllerHelper.getProjectRootUrl(config);
        List<JavaMethod> methodList = clazz.getMethods();
        //源文件
        JavaSource javaSource = clazz.getSource();
        ApiLogUtil.info("当前类：{0} 描述：{1} , Api根路径：{2},方法数量：{3}", clazz.getGenericFullyQualifiedName(),
                apiRootComment, apiRootUrl, methodList.size());
        if (CollectionUtils.isEmpty(methodList)) {
            return null;
        }
        //生成接口基础参数model
        ApiOutBaseModel apiOutBaseModel = null;
        List<ApiOutBaseModel> apiBaseList = new ArrayList<>();
        ApiLogUtil.debug(">>>>>>>>>>>>>>> method start >>>>>>>>>>>>>>> ");
        for (JavaMethod method : methodList) {
            if (method.isPrivate()) {
                ApiLogUtil.info("当前类：{0},{1} 私有方法,无需处理", clazz.getGenericFullyQualifiedName(), method.getName());
                continue;
            }
            //若需要生成的方法名不为空
            if (CollectionUtils.isNotEmpty(methodSets) && !methodSets.contains(method.getName())) {
                continue;
            }
            //方法上的标签
            Map<String, String> methodTag = getMethodTag(method);
            //方法是否需要过滤
            if (methodTag.containsKey(CommonConstant.IGNORE_STR)) {
                ApiLogUtil.info("当前类：{0},{1} 带有过滤标签@ignore ,自动过滤，无需处理", clazz.getGenericFullyQualifiedName(), method.getName());
                continue;
            }
            //构建输出参数
            apiOutBaseModel = buildApiOutBaseModel(rootBuilder, javaSource, method, config, paramExcludeSet, projectRootUrl, apiRootUrl);
            //接口类名
            apiOutBaseModel.setInterfaceClassName(clazz.getGenericFullyQualifiedName());
            //接口方法名
            apiOutBaseModel.setInterfaceMethodName(method.getName());

            //若配置了 自定义开放接口参数 >并且方法上未加则过滤当前方法
            if (StringUtils.isNotBlank(config.getGateWayField().getAnnotationName())
                    && StringUtils.isBlank(apiOutBaseModel.getGateWayApiMethod())) {
                ApiLogUtil.info("当前类：{0},{1} 开放接口配置但方法上未加开放接口配置 ,自动过滤，无需处理", clazz.getGenericFullyQualifiedName(), method.getName());
                continue;
            }
            apiBaseList.add(apiOutBaseModel);
        }
        ApiLogUtil.debug(">>>>>>>>>>>>>>> method end >>>>>>>>>>>>>>> ");
        ApiOutModel apiOutModel = new ApiOutModel();
        apiOutModel.setApiRootTitle(apiRootComment);
        apiOutModel.setApiBaseList(apiBaseList);
        return apiOutModel;
    }

    /**
     * 获取方法上的标签
     */
    private static Map<String, String> getMethodTag(JavaMethod method) {
        List<DocletTag> tagList = method.getTags();
        Map<String, String> tagMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(tagList)) {
            for (DocletTag tag : tagList) {
                tagMap.put(tag.getName(), tag.getValue());
            }
        }
        return tagMap;
    }

    /**
     * 构建 ApiOutBaseModel
     */
    private static ApiOutBaseModel buildApiOutBaseModel(JavaProjectBuilder rootBuilder, JavaSource javaSource, JavaMethod method, ApiConfig config,
                                                        Set<String> paramExcludeSet, String projectRootUrl, String apiRootUrl) {
        //方法绑定注解
        Map<String, JavaAnnotation> annotationMap = ApiMatchAnnoHelper.getMethodAnnotationMap(method);
        //方法作者
        String methodAuthor = getMethodAuthor(method);
        ApiLogUtil.debug("方法绑定的注解： >> {0}", annotationMap);
        //访问路径+访问类型
        String[] methodPathAndType = getMethodPathAndType(annotationMap);
        ApiLogUtil.debug("方法访问路径与访问类型： >> {0}", Arrays.asList(methodPathAndType));
        //自定义网关-接口地址
        String gateWayApiMethod = getGateWayApiMethod(annotationMap, config);

        //获取以*结尾导包的数据
        Set<String> lessPackageSet = getLessPackageSet(javaSource);
        //请求入参
        ApiReqParamModel reqParam = ApiParamHelper.getReqParam(rootBuilder, lessPackageSet, method, paramExcludeSet, config);
        ApiLogUtil.debug("请求参数输出： >> {0}", JSON.parseObject(JSON.toJSONString(reqParam)));
        //请求出参
        ApiResParamModel resParam = ApiParamHelper.getResParam(rootBuilder, lessPackageSet, method, config);
        ApiLogUtil.debug("返回参数输出： >> {0}", JSON.parseObject(JSON.toJSONString(resParam)));
        //请求路径
        String reqPath = projectRootUrl.concat(apiRootUrl.concat(methodPathAndType[CommonConstant.ONE]));
        //方法标题，默认取方法注释，注释不存在情况下取方法名
        String methodComment = StringUtils.isBlank(method.getComment()) ? method.getName() : method.getComment();
        ApiOutBaseModel apiOutBaseModel = new ApiOutBaseModel();
        //根目录标题
        apiOutBaseModel.setApiTitle(methodComment);
        //作者
        apiOutBaseModel.setApiAuthor(methodAuthor);
        //请求参数
        apiOutBaseModel.setReqParam(reqParam);
        //返回参数
        apiOutBaseModel.setResParam(resParam);
        //请求类型
        apiOutBaseModel.setReqType(methodPathAndType[CommonConstant.ZERO]);
        //请求地址
        apiOutBaseModel.setReqPath(reqPath);
        //开放接口方法名
        apiOutBaseModel.setGateWayApiMethod(gateWayApiMethod);
        return apiOutBaseModel;
    }

    /**
     * 获取存在 .* 的导包
     */
    private static Set<String> getLessPackageSet(JavaSource javaSource) {
        if (CollectionUtils.isEmpty(javaSource.getImports())) {
            return new HashSet<>();
        }
        List<String> importNameList = javaSource.getImports();
        Set<String> lessPackageSet = new HashSet<>();
        for (String importClass : importNameList) {
            if (importClass.endsWith(".*") &&
                    !ApiSetConstant.STAR_FLOWER_PACKAGE_EXCLUDE_SET.contains(importClass)) {
                lessPackageSet.add(importClass);
            }
        }
        return lessPackageSet;
    }

    /**
     * 自定义网关-接口地址
     * 注：从方法注解中获取
     */
    private static String getGateWayApiMethod(Map<String, JavaAnnotation> annotationMap, ApiConfig config) {
        ApiGateWayField gateWayField = config.getGateWayField();
        if (StringUtils.isBlank(gateWayField.getAnnotationName()) ||
                !annotationMap.containsKey(gateWayField.getAnnotationName())) {
            return ApiStringPool.EMPTY;
        }
        JavaAnnotation annotation = annotationMap.get(gateWayField.getAnnotationName());
        if (null != annotation.getNamedParameter(gateWayField.getAnnotationFieldName())) {
            return String.valueOf(annotation.getNamedParameter(gateWayField.getAnnotationFieldName()))
                    .replaceAll("\"", "");
        }
        return ApiStringPool.EMPTY;
    }

    /**
     * 获取方法作者
     */
    private static String getMethodAuthor(JavaMethod method) {
        List<DocletTag> tagList = method.getTags();
        if (CollectionUtils.isEmpty(tagList)) {
            return ApiStringPool.EMPTY;
        }
        for (DocletTag tag : tagList) {
            if (CommonConstant.AUTHOR.equals(tag.getName())) {
                return tag.getValue();
            }
        }
        return ApiStringPool.EMPTY;
    }


    /**
     * 从方法 method 对象中 > 获取请求类型与请求地址
     *
     * @return String[] [0] -请求类型 [1]-请求地址
     */
    private static String[] getMethodPathAndType(Map<String, JavaAnnotation> annotationMap) {
        //@RequestMapping
        if (annotationMap.containsKey(ValidationConstant.REQUEST_MAPPING_STR)) {
            return getReqPath(annotationMap, ValidationConstant.REQUEST_MAPPING_STR, ApiStringPool.EMPTY);
        }
        //@GetMapping
        if (annotationMap.containsKey(ValidationConstant.GET_MAPPING_STR)) {
            return getReqPath(annotationMap, ValidationConstant.GET_MAPPING_STR, RequestMethod.GET.name());
        }
        //@PostMappings
        if (annotationMap.containsKey(ValidationConstant.POST_MAPPING_STR)) {
            return getReqPath(annotationMap, ValidationConstant.POST_MAPPING_STR, RequestMethod.POST.name());
        }
        //@PutMapping
        if (annotationMap.containsKey(ValidationConstant.PUT_MAPPING_STR)) {
            return getReqPath(annotationMap, ValidationConstant.PUT_MAPPING_STR, RequestMethod.PUT.name());
        }
        //@DeleteMapping
        if (annotationMap.containsKey(ValidationConstant.DELETE_MAPPING_STR)) {
            return getReqPath(annotationMap, ValidationConstant.DELETE_MAPPING_STR, RequestMethod.DELETE.name());
        }
        return new String[]{ApiStringPool.POST_STR, ApiStringPool.EMPTY};
    }

    /**
     * 获取请求地址
     */
    private static String[] getReqPath(Map<String, JavaAnnotation> annotationMap, String key, String currentReqType) {
        //请求类型默认 post
        String reqPath = ApiStringPool.EMPTY;
        if (annotationMap.containsKey(key)) {
            JavaAnnotation annotation = annotationMap.get(key);
            if (null == annotation) {
                return new String[]{currentReqType, reqPath};
            }
            if (!ObjectUtils.isEmpty(annotation.getNamedParameter(ValidationConstant.VALUE_STR))) {
                reqPath = String.valueOf(annotation.getNamedParameter(ValidationConstant.VALUE_STR)).replaceAll("\"", "");
            }
            if (!ObjectUtils.isEmpty(annotation.getNamedParameter(ValidationConstant.METHOD_STR)) && StringUtils.isBlank(currentReqType)) {
                currentReqType = String.valueOf(annotation.getNamedParameter(ValidationConstant.METHOD_STR))
                        .replaceAll("\"", "").replaceAll("RequestMethod.", "");
            }
        }
        return new String[]{currentReqType, reqPath};
    }

    /**
     * 需要排除的参数全路径
     */
    private static Set<String> getExcludeParamFullPathSet(ApiConfig config) {
        Set<String> paramExcludeSet = new HashSet<>(ApiSetConstant.EXCLUDE_PARAM_FULL_PATH_SET);
        //默认排除的参数
        if (CollectionUtils.isNotEmpty(config.getApiExcludeParamClassNames())) {
            paramExcludeSet.addAll(config.getApiExcludeParamClassNames());
        }
        return paramExcludeSet;
    }


}