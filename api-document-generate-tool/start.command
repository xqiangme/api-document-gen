# 打印当前文件夹路径
echo "currentPath: "$(cd `dirname $0`; pwd)  

#执行方式
#1.默认执行  -- 执行当前命令文件下的 jar 
java -Dfile.encoding=utf-8 -jar $(cd `dirname $0`; pwd)/api-document-generate.jar

#2.自定义jar位置执行 -- 启动参数代表xml文件相对jar文件读取位置， 示例：/Users/xx/api-generate-tool/finance/admin/fxx.xml
#java -Dfile.encoding=utf-8 -jar /Users/xx/api-generate-tool/api-document-generate /finance/admin/xx