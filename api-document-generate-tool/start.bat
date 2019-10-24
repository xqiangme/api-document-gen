title  api-document-generate
@echo off

::执行方式
::1.默认执行 -- 默认当前位置执行 
java -Dfile.encoding=utf-8 -jar api-document-generate

::2.自定义jar位置执行 -- 启动参数代表xml文件相对jar文件读取位置， 示例：/Users/xx/Desktop/api-generate-tool/generate/finance/admin/xxx.xml
:: java -Dfile.encoding=utf-8 -jar /Users/xx/Desktop/api-generate-tool/generate/api-document-generate /finance/admin/xxx

pause