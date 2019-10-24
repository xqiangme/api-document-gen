package com.my.api.generate.core.constants;

import com.my.api.generate.core.util.tool.ApiStringPool;
import org.apache.commons.lang3.StringUtils;

/**
 * xml 常量类
 *
 * @author mengqiang
 */
public class XmlConstant {

    /**
     * 配置文件名称
     */
    public static final String CONFIG_XML_NAME_PATH = "/ApiGeneratorConfig.xml";
    public static final String XML_FILE_SUFFIX = ".xml";

    public static final String PROJECT_SCAN_PATHS = "projectScanPaths";
    public static final String API_PACKAGE_PATH = "apiPackagePath";
    public static final String API_CLASS_PATHS = "apiClassPaths";
    public static final String GENERATE_FILE_PATH = "generateFilePath";

    public static final String GENERATE_TYPE = "generateType";
    public static final String API_PARAM_TYPE = "apiParamType";
    public static final String GENERATE_JSON_FLAG = "generateJsonFlag";
    public static final String INTERFACE_PATH_SHOW_FLAG = "interfacePathShowFlag";
    public static final String API_EXCLUDE_PARAM_NAMES = "apiExcludeParamNames";
    public static final String API_EXCLUDE_PARAM_CLASS_NAMES = "apiExcludeParamClassNames";
    public static final String LOG_LEVEL = "logLevel";
    public static final String CONTENT_TYPE = "contentType";
    public static final String PROJECT_ROOT_URL = "projectRootUrl";
    public static final String GATE_WAY_FIELD = "gateWayField";
    public static final String API_ROOT_RES_FIELD = "apiRootResField";
    public static final String API_HEADER_FIELD = "apiHeaderField";


    public static final String PATH = "path";
    public static final String METHOD = "method";
    public static final String VALUE = "value";
    public static final String PATH_PREFIX = "pathPrefix";
    public static final String FIELD = "field";
    public static final String NAME = "name";
    public static final String DESC = "desc";
    public static final String TYPE_CLASS_NAME = "typeClassName";
    public static final String TYPE_STR = "type";
    public static final String IS_PARENT = "isParent";
    public static final String IS_REQUIRED = "isRequired";
    public static final String PARAM_NAME = "paramName";
    public static final String PARAM_CLASS_NAME = "paramClassName";
    public static final String ANNOTATION_NAME = "annotationName";
    public static final String ANNOTATION_FIELD_NAME = "annotationFieldName";


    /**
     * 获取 xml 文件地址
     */
    public static String getXmlFleName(String customXmlName) {
        if (StringUtils.isBlank(customXmlName)) {
            return CONFIG_XML_NAME_PATH;
        }
        StringBuilder xmlName = new StringBuilder();
        if (!customXmlName.startsWith(ApiStringPool.SLASH)) {
            xmlName.append(ApiStringPool.SLASH);
        }
        xmlName.append(customXmlName);
        //文件后缀
        xmlName.append(XML_FILE_SUFFIX);
        return String.valueOf(xmlName);
    }

}