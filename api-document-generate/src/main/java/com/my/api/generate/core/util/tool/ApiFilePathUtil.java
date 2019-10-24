package com.my.api.generate.core.util.tool;

import com.my.api.generate.core.constants.CommonConstant;

/**
 * 文件路径转换工具
 *
 * @author mengqiang
 */
public class ApiFilePathUtil {

    private static final String MAIN_JAVA_PATH = "/main/java/";
    private static final String JAVA_SUFFIX = ".java";
    private static final String FILE_PREFIX = "file:";
    private static final String TARGET_STR = "target";
    private static final String JAR_SUFFIX = ".jar";

    /**
     * 当前根目录
     */
    public static String getRootPath() {
        return System.getProperty("user.dir");
    }


    /**
     * 当前运行类父路径
     */
    public static String getClassParentPath(Class clazz) {
        if (null == clazz) {
            return ApiStringPool.SLASH;
        }
        String targetPath = clazz.getResource(ApiStringPool.EMPTY).getPath();
        if (targetPath.contains(FILE_PREFIX)) {
            targetPath = targetPath.replaceAll(FILE_PREFIX, "");
        }
        if (targetPath.contains(TARGET_STR)) {
            return targetPath.substring(0, targetPath.indexOf(TARGET_STR));
        }
        if (targetPath.contains(JAR_SUFFIX)) {
            String substring = targetPath.substring(0, targetPath.indexOf(JAR_SUFFIX));
            ApiLogUtil.debug("【Jar文件】绝对路径 ：{0}", substring);
            return targetPath.substring(0, substring.lastIndexOf(ApiStringPool.SLASH));
        }
        return getRootPath();
    }

    /**
     * 当前运行类 -> 所属父级项目
     */
    public static String getClassProjectPath(Class clazz) {
        if (null == clazz) {
            return ApiStringPool.SLASH;
        }
        String targetPath = clazz.getResource(ApiStringPool.SLASH).getPath();
        return targetPath.substring(0, targetPath.indexOf("target"));
    }

    /**
     * 当前运行类 -> 所属父级项目 java root文件地址
     */
    public static String getClassProjectJavaPath(Class clazz) {
        return getClassProjectPath(clazz).concat("src/main/java/");
    }

    /**
     * 当前运行类 -> 所属父级项目 java src文件地址
     */
    public static String getClassProjectJavaSrcPath(Class clazz) {
        return getClassProjectPath(clazz).concat("src/");
    }

    /**
     * 当前运行类 -> 所属父级项目 java root文件地址
     */
    public static String getClassProjectJavaPath(Class clazz, String className) {
        return ApiFilePathUtil.getJavaPathBySrcAndPackage(getClassProjectJavaSrcPath(clazz), className);
    }

    /**
     * 当前运行类 -> 所属父级项目 -下某包地址
     */
    public static String getClassProjectPackagePath(Class clazz, String packageName) {
        return getClassProjectJavaPath(clazz).concat(packageName.replaceAll("\\.", "/"));
    }

    /**
     * 通过src 与包地址获取java 文件地址
     *
     * @param srcPath     src地址
     * @param packagePath 包地址
     */
    public static String getJavaPathBySrcAndPackage(String srcPath, String packagePath) {
        StringBuilder path = new StringBuilder(srcPath);
        path.append(MAIN_JAVA_PATH)
                //替换 包地址中的点> /
                .append(packagePath.replaceAll(ApiStringPool.BACK_SLASH_POINT, ApiStringPool.SLASH))
                .append(JAVA_SUFFIX);
        String rPath = String.valueOf(path);
        //内部类处理,若存在内部类
        if (rPath.contains(CommonConstant.STR_$)) {
            String[] split = rPath.split(ApiStringPool.SLASH_$_STR);
            return split[CommonConstant.ZERO].concat(CommonConstant.JAVA_FILE_SUFFIX);
        }
        return rPath;
    }

}