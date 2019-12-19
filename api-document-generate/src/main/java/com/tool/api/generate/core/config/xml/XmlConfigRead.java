package com.tool.api.generate.core.config.xml;

import com.tool.api.generate.core.config.custom.ApiGateWayField;
import com.tool.api.generate.core.config.custom.ApiHeaderField;
import com.tool.api.generate.core.config.custom.ApiRootResField;
import com.tool.api.generate.core.constants.ApiSetConstant;
import com.tool.api.generate.core.constants.CommonConstant;
import com.tool.api.generate.core.constants.XmlConstant;
import com.tool.api.generate.core.enums.ApiGenerateTypeEnum;
import com.tool.api.generate.core.enums.ApiParamTypeEnum;
import com.tool.api.generate.core.exception.ApiGenerateException;
import com.tool.api.generate.core.util.tool.ApiLogUtil;
import com.tool.api.generate.core.util.tool.ApiStringPool;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * 配置xml 文件读取工具类
 *
 * @author mengqiang
 */
public class XmlConfigRead {

    /**
     * 获取配置 xml 文件 Document
     */
    public static Document getDocumentByPath(String configXmlPath) {
        Document document = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(new File(configXmlPath));
            //日志设置
            freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);
        } catch (FileNotFoundException e1) {
            ApiLogUtil.debug("自动获取配置xml文件失败，stack=", e1);
            return null;
        } catch (Exception e) {
            throw new ApiGenerateException("地址: {0} 获取配置文件失败！stack={1}", configXmlPath, e);
        }
        return document;
    }

    /**
     * 生成类型
     */
    public static ApiGenerateTypeEnum getGenerateType(Document document) {
        NodeList propertyList = document.getElementsByTagName(XmlConstant.GENERATE_TYPE);
        String typeValue = getValue(propertyList, XmlConstant.GENERATE_TYPE);
        ApiGenerateTypeEnum typeEnum = ApiGenerateTypeEnum.getGenerateType(typeValue);
        //必填校验
        if (ObjectUtils.isEmpty(typeEnum)) {
            throw new ApiGenerateException("生成类型 {0}  当前值 {1} 不存在 ！", XmlConstant.API_PARAM_TYPE, typeValue);
        }
        return typeEnum;
    }

    /**
     * 项目下类扫描路径
     */
    public static List<String> getProjectScanPaths(Document document) {
        List<String> xmlList = getXmlList(document, XmlConstant.PROJECT_SCAN_PATHS, XmlConstant.PATH);
        if (CollectionUtils.isEmpty(xmlList)) {
            throw new ApiGenerateException("项目下类扫描路径 {0} 子集  {1} 不能为空 ！", XmlConstant.PROJECT_SCAN_PATHS, XmlConstant.PATH);
        }
        return xmlList;
    }


    /**
     * 包文件夹-绝对路径
     */
    public static String getApiPackagePath(Document document) {
        NodeList propertyList = document.getElementsByTagName(XmlConstant.API_PACKAGE_PATH);
        return getValue(propertyList, XmlConstant.API_PACKAGE_PATH);
    }

    /**
     * 类文件地址（绝对路径）
     */
    public static List<String> getApiClassPaths(Document document) {
        return getXmlList(document, XmlConstant.API_CLASS_PATHS, XmlConstant.PATH);
    }

    /**
     * 类文件地址（绝对路径）
     * key:类地址
     * value:需要包含的方法名，默认导出类下所有公共方法
     */
    public static Map<String, Set<String>> getApiClassPathMap(Document document) {
        return getApiClassPathsXmlList(document);
    }

    /**
     * 获取 类地址 公共方法
     */
    private static Map<String, Set<String>> getApiClassPathsXmlList(Document document) {
        Map<String, Set<String>> hashMap = new HashMap<>();
        NodeList node = document.getElementsByTagName(XmlConstant.API_CLASS_PATHS);
        if (ObjectUtils.isEmpty(node) || node.getLength() == 0) {
            return new HashMap<>(0);
        }
        for (int i = CommonConstant.ZERO; i < node.getLength(); i++) {
            Element element = (Element) node.item(i);
            NamedNodeMap attributes = element.getAttributes();
            //地址公共前缀支持
            String pathPrefix = "";
            if (!ObjectUtils.isEmpty(attributes)) {
                ApiLogUtil.debug("");
                Node pathPrefixNode = attributes.getNamedItem(XmlConstant.PATH_PREFIX);
                if (!ObjectUtils.isEmpty(pathPrefixNode)) {
                    pathPrefix = pathPrefixNode.getNodeValue();
                    if (!pathPrefix.endsWith(ApiStringPool.SLASH)) {
                        pathPrefix = pathPrefix.concat(ApiStringPool.SLASH);
                    }
                }
            }

            NodeList value = element.getElementsByTagName(XmlConstant.PATH);
            if (ObjectUtils.isEmpty(value) || value.getLength() == 0) {
                return hashMap;
            }

            for (int j = CommonConstant.ZERO; j < value.getLength(); j++) {
                Node item = value.item(j);
                NamedNodeMap attrs = item.getAttributes();
                Node valueNode = attrs.getNamedItem(XmlConstant.VALUE);
                if (ObjectUtils.isEmpty(valueNode)) {
                    continue;
                }

                String valueStr = attrs.getNamedItem(XmlConstant.VALUE).getNodeValue();
                if (StringUtils.isBlank(valueStr)) {
                    continue;
                }

                if (!valueStr.endsWith(CommonConstant.JAVA_FILE_SUFFIX)) {
                    valueStr = valueStr + CommonConstant.JAVA_FILE_SUFFIX;
                }

                String key = pathPrefix.concat(valueStr);
                Set<String> mapValue = new HashSet<>();

                Node methodNode = attrs.getNamedItem(XmlConstant.METHOD);
                if (!ObjectUtils.isEmpty(methodNode)) {
                    String methodStr = attrs.getNamedItem(XmlConstant.METHOD).getNodeValue();
                    if (StringUtils.isNotBlank(methodStr)) {
                        mapValue = new HashSet<>(Arrays.asList(methodStr.split(ApiStringPool.COMMA)));
                    }
                }

                hashMap.put(key, mapValue);
            }
        }
        return hashMap;
    }

    /**
     * 生成文件地址
     */
    public static String getGenerateFilePath(Document document) {
        NodeList propertyList = document.getElementsByTagName(XmlConstant.GENERATE_FILE_PATH);
        return getValue(propertyList, XmlConstant.GENERATE_FILE_PATH);
    }


    /**
     * 参数类型
     */
    public static ApiParamTypeEnum getApiParamType(Document document) {
        NodeList propertyList = document.getElementsByTagName(XmlConstant.API_PARAM_TYPE);
        String typeValue = getValue(propertyList, XmlConstant.API_PARAM_TYPE);
        if (StringUtils.isBlank(typeValue)) {
            //默认类型
            return ApiParamTypeEnum.COMMON;
        }
        ApiParamTypeEnum typeEnum = ApiParamTypeEnum.getParamType(typeValue);
        if (ObjectUtils.isEmpty(typeEnum)) {
            throw new ApiGenerateException("参数类型 {0}  当前值 {1} 不存在 ！", XmlConstant.API_PARAM_TYPE, typeValue);
        }
        return typeEnum;
    }


    /**
     * 是否生成json开关
     */
    public static Boolean getGenerateJsonFlag(Document document) {
        NodeList propertyList = document.getElementsByTagName(XmlConstant.GENERATE_JSON_FLAG);
        String value = getValue(propertyList, XmlConstant.GENERATE_JSON_FLAG);
        //不为空，值为2时
        if (StringUtils.isNotBlank(value) && ApiStringPool.NUM_TWO.equals(value)) {
            return false;
        }
        return true;
    }

    /**
     * 是否展示接口路径地址
     */
    public static Boolean getInterfacePathShowFlag(Document document) {
        NodeList propertyList = document.getElementsByTagName(XmlConstant.INTERFACE_PATH_SHOW_FLAG);
        String value = getValue(propertyList, XmlConstant.INTERFACE_PATH_SHOW_FLAG);
        //不为空，值为1时 > 返回 true
        if (StringUtils.isNotBlank(value) && ApiStringPool.NUM_ONE.equals(value)) {
            return true;
        }
        return false;
    }


    /**
     * 参数排除-根据参数名排除
     */
    public static Set<String> getApiExcludeParamNames(Document document) {
        Set<String> paramExcludeSet = new HashSet<>();
        List<String> xmlList = getXmlList(document, XmlConstant.API_EXCLUDE_PARAM_NAMES, XmlConstant.PARAM_NAME);
        if (CollectionUtils.isNotEmpty(xmlList)) {
            paramExcludeSet.addAll(xmlList);
        }
        return paramExcludeSet;
    }

    /**
     * 参数排除-根据参数类全路径名排除
     */
    public static Set<String> getApiExcludeParamClassNames(Document document) {
        Set<String> paramExcludeSet = new HashSet<>();
        //默认配置
        paramExcludeSet.addAll(ApiSetConstant.EXCLUDE_PARAM_FULL_PATH_SET);
        List<String> xmlList = getXmlList(document, XmlConstant.API_EXCLUDE_PARAM_CLASS_NAMES, XmlConstant.PARAM_CLASS_NAME);
        if (CollectionUtils.isNotEmpty(xmlList)) {
            paramExcludeSet.addAll(xmlList);
        }
        return paramExcludeSet;
    }


    /**
     * 日志级别
     */
    public static String getLogLevel(Document document) {
        NodeList propertyList = document.getElementsByTagName(XmlConstant.LOG_LEVEL);
        return getValue(propertyList, XmlConstant.LOG_LEVEL);
    }

    /**
     * content-type
     */
    public static String getContentType(Document document) {
        NodeList propertyList = document.getElementsByTagName(XmlConstant.CONTENT_TYPE);
        return getValue(propertyList, XmlConstant.CONTENT_TYPE);
    }


    /**
     * 根路径
     */
    public static String getProjectRootUrl(Document document) {
        NodeList propertyList = document.getElementsByTagName(XmlConstant.PROJECT_ROOT_URL);
        String value = getValue(propertyList, XmlConstant.PROJECT_ROOT_URL);
        if (StringUtils.isBlank(value)) {
            return ApiStringPool.EMPTY;
        }
        return value;
    }

    /**
     * 开放接口注解类配置
     */
    public static ApiGateWayField getApiGateWayField(Document document) {
        NodeList propertyList = document.getElementsByTagName(XmlConstant.GATE_WAY_FIELD);
        if (ObjectUtils.isEmpty(propertyList) || propertyList.getLength() == 0) {
            return new ApiGateWayField(ApiStringPool.EMPTY);
        }
        try {
            String annotationName = ApiStringPool.EMPTY;
            String annotationFieldName = ApiStringPool.EMPTY;
            for (int i = CommonConstant.ZERO; i < propertyList.getLength(); i++) {
                Node table = propertyList.item(i);
                NamedNodeMap attrs = table.getAttributes();
                annotationName = attrs.getNamedItem(XmlConstant.ANNOTATION_NAME).getNodeValue();
                annotationFieldName = attrs.getNamedItem(XmlConstant.ANNOTATION_FIELD_NAME).getNodeValue();
                if (StringUtils.isBlank(annotationFieldName)) {
                    annotationFieldName = XmlConstant.METHOD;
                }
            }
            return new ApiGateWayField(annotationName, annotationFieldName);
        } catch (Exception e) {
            throw new ApiGenerateException("配置XML文件 > 开放接口注解类配置 获取失败 ！");
        }
    }

    /**
     * 自定义顶级对象属性
     */
    public static List<ApiRootResField> getRootRespFieldList(Document document) {
        List<ApiRootResField> rootRespFieldList = new ArrayList<>();
        NodeList propertyList = document.getElementsByTagName(XmlConstant.API_ROOT_RES_FIELD);
        if (ObjectUtils.isEmpty(propertyList) || propertyList.getLength() == 0) {
            return rootRespFieldList;
        }
        ApiRootResField resField = null;
        try {
            for (int i = CommonConstant.ZERO; i < propertyList.getLength(); i++) {
                Element element = (Element) propertyList.item(i);
                NodeList field = element.getElementsByTagName(XmlConstant.FIELD);
                if (ObjectUtils.isEmpty(field) || field.getLength() == 0) {
                    return rootRespFieldList;
                }
                for (int j = CommonConstant.ZERO; j < field.getLength(); j++) {
                    Node item = field.item(j);
                    NamedNodeMap attrs = item.getAttributes();
                    String name = getStringValue(attrs, XmlConstant.NAME);
                    String typeClassName = getStringValue(attrs, XmlConstant.TYPE_CLASS_NAME);
                    String desc = getStringValue(attrs, XmlConstant.DESC);
                    String isParent = getStringValue(attrs, XmlConstant.IS_PARENT);

                    if (StringUtils.isBlank(name)) {
                        throw new ApiGenerateException("配置XML文件 > 配置项 {0} > {1} > {2} 不能为空 ！",
                                XmlConstant.API_ROOT_RES_FIELD, XmlConstant.FIELD, XmlConstant.NAME);
                    }
                    if (StringUtils.isBlank(typeClassName)) {
                        throw new ApiGenerateException("配置XML文件 > 配置项 {0} > {1} > {2} 不能为空 ！",
                                XmlConstant.API_ROOT_RES_FIELD, XmlConstant.FIELD, XmlConstant.TYPE_CLASS_NAME);
                    }

                    if (StringUtils.isBlank(desc)) {
                        desc = ApiStringPool.EMPTY;
                    }
                    boolean isParentFlag = false;
                    if (StringUtils.isNotBlank(isParent)) {
                        isParentFlag = ApiStringPool.NUM_ONE.equals(isParent);
                    }
                    Class<?> aClass = Class.forName(typeClassName);
                    resField = new ApiRootResField(name, aClass, desc, isParentFlag);
                    rootRespFieldList.add(resField);
                }
            }
        } catch (Exception e) {
            throw new ApiGenerateException("配置XML文件 > {0} 获取失败 ！stack={1}", XmlConstant.API_ROOT_RES_FIELD, e);
        }
        return rootRespFieldList;
    }

    /**
     * 接口 header 头信息属性
     */
    public static List<ApiHeaderField> getHeaderFieldList(Document document) {
        List<ApiHeaderField> rootRespFieldList = new ArrayList<>();
        NodeList propertyList = document.getElementsByTagName(XmlConstant.API_HEADER_FIELD);
        if (ObjectUtils.isEmpty(propertyList) || propertyList.getLength() == 0) {
            return rootRespFieldList;
        }
        ApiHeaderField resField = null;
        try {
            for (int i = CommonConstant.ZERO; i < propertyList.getLength(); i++) {
                Element element = (Element) propertyList.item(i);
                NodeList field = element.getElementsByTagName(XmlConstant.FIELD);
                if (ObjectUtils.isEmpty(field) || field.getLength() == 0) {
                    return rootRespFieldList;
                }
                for (int j = CommonConstant.ZERO; j < field.getLength(); j++) {
                    Node item = field.item(j);
                    NamedNodeMap attrs = item.getAttributes();
                    String name = getStringValue(attrs, XmlConstant.NAME);
                    String desc = getStringValue(attrs, XmlConstant.DESC);
                    String type = getStringValue(attrs, XmlConstant.TYPE_STR);
                    String isRequired = getStringValue(attrs, XmlConstant.IS_REQUIRED);

                    resField = ApiHeaderField.createField(name, type, desc, true);
                    //非1
                    if (!ApiStringPool.NUM_ONE.equals(isRequired)) {
                        resField.setRequiredFlag(false);
                    }
                    rootRespFieldList.add(resField);
                }
            }
        } catch (Exception e) {
            throw new ApiGenerateException("配置XML文件 > {0} 获取失败 ！stack={1}", XmlConstant.API_HEADER_FIELD, e);
        }
        return rootRespFieldList;
    }

    /**
     * 获取值公共方法
     */
    private static String getValue(NodeList propertyList, String mess) {
        if (ObjectUtils.isEmpty(propertyList) || propertyList.getLength() == 0) {
            return ApiStringPool.EMPTY;
        }
        String value = ApiStringPool.EMPTY;
        try {
            for (int i = CommonConstant.ZERO; i < propertyList.getLength(); i++) {
                Node table = propertyList.item(i);
                NamedNodeMap attrs = table.getAttributes();
                value = attrs.getNamedItem("value").getNodeValue();
            }
        } catch (Exception e) {
            throw new ApiGenerateException("配置XML文件 > 配置项 {0} > value 获取失败了 ！", mess);
        }
        return value;
    }

    /**
     * 获取 xml list 公共方法
     */
    private static List<String> getXmlList(Document document, String parentTag, String childTag) {
        List<String> projectPathList = new ArrayList<>();
        NodeList node = document.getElementsByTagName(parentTag);
        if (ObjectUtils.isEmpty(node) || node.getLength() == 0) {
            return projectPathList;
        }
        for (int i = CommonConstant.ZERO; i < node.getLength(); i++) {
            Element element = (Element) node.item(i);
            NamedNodeMap attributes = element.getAttributes();
            //地址公共前缀支持
            String pathPrefix = "";
            if (!ObjectUtils.isEmpty(attributes)) {
                Node pathPrefixNode = attributes.getNamedItem(XmlConstant.PATH_PREFIX);
                if (!ObjectUtils.isEmpty(pathPrefixNode)) {
                    pathPrefix = pathPrefixNode.getNodeValue();
                    if (!pathPrefix.endsWith(ApiStringPool.SLASH)) {
                        pathPrefix = pathPrefix.concat(ApiStringPool.SLASH);
                    }
                }
            }

            NodeList value = element.getElementsByTagName(childTag);
            if (ObjectUtils.isEmpty(value) || value.getLength() == 0) {
                return projectPathList;
            }
            for (int j = CommonConstant.ZERO; j < value.getLength(); j++) {
                Node item = value.item(j);
                NamedNodeMap attrs = item.getAttributes();
                String valueStr = attrs.getNamedItem(XmlConstant.VALUE).getNodeValue();
                if (StringUtils.isBlank(valueStr)) {
                    continue;
                }
                projectPathList.add(pathPrefix.concat(valueStr));
            }
        }
        return projectPathList;
    }

    private static String getStringValue(NamedNodeMap attrs, String itemKey) {
        String value = ApiStringPool.EMPTY;
        if (null != attrs.getNamedItem(itemKey)) {
            value = attrs.getNamedItem(itemKey).getNodeValue();
        }
        return value;
    }

    public static void main(String[] args) throws Exception {
        String configXmlPath = "/Users/mengqiang/project/workproject/tool/api-document-generate/ApiGeneratorConfig.xml";
        Document document = getDocumentByPath(configXmlPath);

//        ApiGenerateTypeEnum generateType = getGenerateType(document);
//        ApiLogUtil.info("generateType : {0}", generateType);
        List<String> projectPaths = getProjectScanPaths(document);
        ApiLogUtil.info("projectPaths : {0}", projectPaths);
//
//        String apiPackagePath = getApiPackagePath(document);
//        ApiLogUtil.info("apiPackagePath : {0}", apiPackagePath);

        List<String> apiClassPaths = getApiClassPaths(document);
        ApiLogUtil.info("apiClassPaths : {0}", apiClassPaths);

//        String generateFilePath = getGenerateFilePath(document);
//        ApiLogUtil.info("generateFilePath : {0}", generateFilePath);
//
//        ApiParamTypeEnum apiParamType = getApiParamType(document);
//        ApiLogUtil.info("apiParamType : {0}", apiParamType);
//
//        Boolean generateJsonFlag = getGenerateJsonFlag(document);
//        ApiLogUtil.info("generateJsonFlag : {0}", generateJsonFlag);
//
        Set<String> apiExcludeParamNames = getApiExcludeParamNames(document);
        ApiLogUtil.info("apiExcludeParamNames : {0}", apiExcludeParamNames);

        Set<String> apiExcludeParamClassNames = getApiExcludeParamClassNames(document);
        ApiLogUtil.info("apiExcludeParamClassNames : {0}", apiExcludeParamClassNames);
//
//        String logLevel = getLogLevel(document);
//        ApiLogUtil.info("logLevel : {0}", logLevel);
//
//        String projectRootUrl = getProjectRootUrl(document);
//        ApiLogUtil.info("projectRootUrl : {0}", projectRootUrl);
//
//        ApiGateWayField apiGateWayField = getApiGateWayField(document);
//        ApiLogUtil.info("apiGateWayField : {0}", JSON.toJSONString(apiGateWayField));
//
//        List<ApiRootResField> rootRespFieldList = getRootRespFieldList(document);
//        ApiLogUtil.info("rootRespFieldList : {0}", ApiJsonFormatUtil.toFormatJson(rootRespFieldList));
    }

}
