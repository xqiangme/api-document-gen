package com.tool.api.generate.main;

import com.tool.api.generate.core.config.ApiConfig;
import com.tool.api.generate.core.config.xml.XmlConfigRead;
import com.tool.api.generate.core.constants.ApiSetConstant;
import com.tool.api.generate.core.constants.CommonConstant;
import com.tool.api.generate.core.constants.XmlConstant;
import com.tool.api.generate.core.util.helper.ApiGenerateHelper;
import com.tool.api.generate.core.util.tool.ApiFileNameUtil;
import com.tool.api.generate.core.util.tool.ApiFilePathUtil;
import com.tool.api.generate.core.util.tool.ApiLogUtil;
import com.tool.api.generate.core.util.tool.ApiStringPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.Document;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

/**
 * jar方式执行 -执行主类
 *
 * @author mengqiang
 */
public class ApiGenerateMain {

    /**
     * 执行主类 main方法
     */
    public static void main(String[] args) {
        ApiLogUtil.generateStart();
        try {
            //创建配置参数对象
            ApiConfig config = new ApiConfig();
            //获取xml Document
            Document document = getDocument(config, getCustomXmlName(args));
            //构建配置信息,以及默认值赋值
            buildApiConfig(config, document);
            //校验必填参数
            ApiGenerateHelper.checkRequiredConfigByXml(config);
            //非必填参数默认值转换
            ApiGenerateHelper.doRunXml(config);
        } catch (Exception e) {
            //打印异常信息
            ApiLogUtil.printlnError(e);
        }
    }

    /**
     * 获取自定义配置的xml配置文件
     */
    private static String getCustomXmlName(String[] args) {
        if (ObjectUtils.isEmpty(args)) {
            ApiLogUtil.info("配置文件默认 ：{0}", XmlConstant.CONFIG_XML_NAME_PATH);
            return ApiStringPool.EMPTY;
        }
        String xmlFileName = args[CommonConstant.ZERO];
        ApiLogUtil.info("自定义启动参数配置 ：{0} , 自定义配置文件名：{1}", Arrays.asList(args), xmlFileName);
        //自定义配置文件参数名
        return args[CommonConstant.ZERO];
    }

    /**
     * 构建xml解析 Document
     *
     * @param apiConfig
     * @param customXmlName 自定义配置文件参数
     */
    private static Document getDocument(ApiConfig apiConfig, String customXmlName) {
        String xmlParentPath = ApiFilePathUtil.getClassParentPath(ApiGenerateMain.class);
        ApiLogUtil.info("【Xml文件自动读取】自动读取配置文件路径 ：{0}", xmlParentPath);
        String autoXmlPath = xmlParentPath.concat(XmlConstant.getXmlFleName(customXmlName));
        Document document = XmlConfigRead.getDocumentByPath(autoXmlPath);
        StringBuilder generateFilePath = new StringBuilder();
        generateFilePath.append(xmlParentPath);
        if (null == document) {
            Scanner scanner = new Scanner(System.in);
            ApiLogUtil.warn("文件路径: {0} 下未获取到配置文件", autoXmlPath);
            ApiLogUtil.info("请输入配置Xml文件绝对路径");
            autoXmlPath = scanner.nextLine();
            ApiLogUtil.info("您输入的配置文件绝对路径为：{0}", autoXmlPath);
            document = XmlConfigRead.getDocumentByPath(autoXmlPath);
            generateFilePath = new StringBuilder(new File(autoXmlPath).getParent());
        }
        apiConfig.setGenerateFilePath(ApiFileNameUtil.getGenerate(String.valueOf(generateFilePath)));
        return document;
    }

    /**
     * 构建配置参数
     */
    private static void buildApiConfig(ApiConfig config, Document document) {
        //生成类型
        config.setGenerateType(XmlConfigRead.getGenerateType(document));
        //项目扫描地址
        config.setProjectScanPaths(XmlConfigRead.getProjectScanPaths(document));
        //生成包地址
        config.setApiPackagePath(XmlConfigRead.getApiPackagePath(document));
        //类绝对地址
        config.setApiClassPathMap(XmlConfigRead.getApiClassPathMap(document));
        //参数类型
        config.setApiParamType(XmlConfigRead.getApiParamType(document));
        //是否生成json
        config.setGenerateJsonFlag(XmlConfigRead.getGenerateJsonFlag(document));
        //是否输出接口路径地址
        config.setInterfacePathShowFlag(XmlConfigRead.getInterfacePathShowFlag(document));
        //参数排除 - 参数名集
        config.setApiExcludeParamNames(XmlConfigRead.getApiExcludeParamNames(document));
        //添加到需要过滤的参数名集中
        ApiSetConstant.FIELD_EXCLUDE_SET.addAll(config.getApiExcludeParamNames());
        //参数排除 - 参数类名集¬
        config.setApiExcludeParamClassNames(XmlConfigRead.getApiExcludeParamClassNames(document));
        //日志级别
        config.setLogLevel(XmlConfigRead.getLogLevel(document));
        //content - type
        config.setContentType(XmlConfigRead.getContentType(document));
        //网关注解属性
        config.setGateWayField(XmlConfigRead.getApiGateWayField(document));
        //根路径属性集
        config.setRootRespFieldList(XmlConfigRead.getRootRespFieldList(document));
        //接口文档头信息属性
        config.setHeaderFieldList(XmlConfigRead.getHeaderFieldList(document));
        //项目访问根路径
        config.setProjectRootUrl(XmlConfigRead.getProjectRootUrl(document));
        //配置的文件地址
        String generateFilePath = XmlConfigRead.getGenerateFilePath(document);
        //生成文件根路径处理
        if (StringUtils.isNotBlank(XmlConfigRead.getGenerateFilePath(document))) {
            config.setGenerateFilePath(generateFilePath);
        }
        //执行环境-日志级别
        if (StringUtils.isNotBlank(config.getLogLevel())) {
            ApiLogUtil.LEVEL = ApiLogUtil.getLevelByName(config.getLogLevel());
        }
    }
}