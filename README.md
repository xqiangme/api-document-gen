# api-document-gen
博客地址 https://blog.csdn.net/qq_38011415/article/details/102734266

本文主要分享一个基于个人兴趣，旨在提高工作效率，开发了一个基于文档注释，接口文档生成工具，欢迎大佬指点。

<a name="9be6bd22"></a>
# 1.前置介绍
<a name="5d4ec228"></a>
### 1.1前世
现在大多数项目都走向了前后端分离，前后端并行开发，这就需要后端同学在开发前先写好接口文档。很多时候开发人员一般都会选择，自己手写文档，或者使用目前开源的API工具，包括现在比较火的swagger但劣势也不少

Swagger分析：<br />    1).为生成规范的接口文档，需要添加各种各样的注解，**代码侵入性太强**；<br />    2).增加了人工成本，做了多余的事;<br />    3).增加复杂度，代码越多复杂性越差；<br />    4).需要遵守其特殊的规范；

图示示例：<br />![image.png](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jZG4ubmxhcmsuY29tL3l1cXVlLzQ0NS8yMDE5L3BuZy8xNzE5OTIvMTU1NTgzNTkzMTc3Ni03Zjk4MTYyMS03ZDhmLTQyMzUtODE3Ny0zZDYxNWFlZDRhNjcucG5n?x-oss-process=image/format,png#align=left&display=inline&height=185&name=image.png&originHeight=500&originWidth=1242&search=&size=108961&status=done&width=460)

### 1.2深思
**问题：能不能根据参数类中的文档注释生成接口文档呢 ？作为程序员，代码不应该即是文档吗？**<br />那我们现在的需求是什么呢 ？<br />1).代码侵入性小，配置简单 、层级结构清晰、可输出参数示例demo；<br />2).无需太复杂的功能，只要可以生成文档即可 ；<br />3).最好是支持转换成语雀，方便存档；

带着好奇心与兴趣开始考虑？如何获取文档注释？<br />遇到的最大难点就是：**代码编译后会自动去除注释；**<br />从编译后的代码里获取肯定是行不通的。思维转变，编译后取不到，那就从未编译的文件里获取。

<a name="31d6784d"></a>
### 1.3今生

根据文档注释生成接口文档工具，在空余时间研究并开发，已经基本完成分享使用。
<br />主要用到的技术点<br />
IO、反射、Freemarker模板引擎、Markdown语法

# 2.功能介绍
## 2.1主要功能
**(1).读取文档注释**

- 读取参数类中属性文档注释；
- 读取controller方法文档注释；
- 读取接口类文档注释；

**(2).支持复杂参数**

- 参数层级嵌套，继承父类；
- 支持返回值，泛型格式输出（根据一些关键词 T、Object、<T>、等判断的）；

**(3).统一网关类接口文档生成**

- 支持生成统一网关接口的文档，自定义@OpenApi注解的读取；

**(4).根据注解获取请求类型**

- 支持根据接口注解获取请求类型，示例：@PostMapping；

**(5).根据注解判断参数是否非空**

- 支持根据注解**[@NotBlank,@NotNull,@NotEmpty]**判断入参是否必填，**返回参数默认非空**；

**(6).根据注解判断参数长度**

- 支持根据注解**[****@Max,@Min,@Size,@Length,@DecimalMax,@****Range,****@DecimalMin****] **输出入参长度；

**(7).生成JSON示例**

- 支持输出json格式参数Demo示例；
- 支持根据类型以及名称模拟值；

**(8).生成统一结构MD接口文档**

-  生成MD接口文档；
-  支持拷贝到语雀平台，文档格式依然存在；

**(9).文档参数类型转换**

- 支持参数驼峰转下划线转换 ;

**(10).排序规则**

- 参数属性排序 > 以类中属性从上往下顺序排序；
- 接口方法排序 > 以类中方法从上往下顺序排序；

<a name="xtl4M"></a>
## 2.2.优缺点
**优点:**<br />(1).代码非侵入性，无需添加一些无用的注解；<br />(2).无需引入依赖生成，减少与业务项目耦合度；<br />(3).可以自定义一些功能；<br />(4).早日实现代码即文档的期盼，减少人工编写接口文档的时间；

**缺点：**<br />(1).返回参数多级需要使用泛型；<br />(2).拷贝到语雀后样式兼容差强人意；

## 2.3.示例图![image.png](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jZG4ubmxhcmsuY29tL3l1cXVlLzAvMjAxOS9wbmcvMTcxOTkxLzE1NzE5MjEyNDA0ODAtNzRlY2ExZDctODdlNi00NWIwLTllOTQtYTYyY2QwNTczYzA0LnBuZw?x-oss-process=image/format,png#align=left&display=inline&height=535&name=image.png&originHeight=1070&originWidth=2136&search=&size=1090000&status=done&width=1068)

# 3.特殊规则
** 注：未按照如下规则，可能会导致无法生成完整文档或失败。**

**3.1参数类文档注释匹配-特殊规则**<br />(1).内部类支持不太完善；

**3.2Controller接口方法类规则**<br />(1).返回值支持自定义类，统一返回Response,需要加泛型，否则获取不到嵌套的返回值，示例：Response<T> ;<br />(2).同一个controller类方法名称建议不相同，重名情况下可能会导致方法注释匹配错误问题 ;<br />(3).java.包下的返回值暂不支持 ;<br />(4).private方法会自动过滤 ;<br />(5).相同方法名，重载的方法，可能会出现注释匹配错误 ;

<a name="02c0cf4b"></a>
# 4.使用方式

- 文档生成的是md文件支持Markdown语法
- 目前支持**两种使用方式**，
       方式一：外置工具方式
       方式二：依赖jar包+启动类方式
       两种方式功能一致，可选择性使用。推荐使用侵入性较小的外置工具方式。

## 4.1外置工具生成
注：外置工具生成，指的是无需引入工具jar包方式生成

<a name="8gV0d"></a>
### 4.1.1精简版使用示例
<a name="WmAqo"></a>
#### 1).下载工具包
  [ 源码地址](https://github.com/mengq0815/api-document-gen)<br />
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191024230430498.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MDExNDE1,size_16,color_FFFFFF,t_70)

  **注意：下载后解压路径，最好不要有中文路径**<br />   
  可执行jar解压后文件示例<br />   ![image.png](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jZG4ubmxhcmsuY29tL3l1cXVlLzAvMjAxOS9wbmcvMTcxOTkxLzE1NzE5Mjg4Njg5ODktNTY5ZDU3NmYtODcwNi00MjIwLWJmZTEtMmY1MzkzYjRmZmQyLnBuZw?x-oss-process=image/format,png#align=left&display=inline&height=132&name=image.png&originHeight=264&originWidth=600&search=&size=40648&status=done&width=300)

文件介绍

-  ApiGeneratorConfig.xml  > 当前配置文件
-  ApiGeneratorConfig_orgin.xml > 全配置文件
-  api-document-generate.jar > 生成工具Jar
-  start.bat > Win 系统下启动
-  start.command > ios 系统下启动

下载完工具后，可打开源码可执行jar目录下ApiGeneratorConfig.xml配置成相应的自己电脑的示例工程目录即可开始生成<br />![image.png](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jZG4ubmxhcmsuY29tL3l1cXVlLzAvMjAxOS9wbmcvMTcxOTkxLzE1NzE5MjkxNzI2MDItZTgyY2ZmYjctMjNiMC00NjZmLTlmYTctMDIwM2IyOTIxZGJlLnBuZw?x-oss-process=image/format,png#align=left&display=inline&height=507&name=image.png&originHeight=1014&originWidth=2004&search=&size=750229&status=done&width=1002)

#### 2).配置ApiGeneratorConfig.XML
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<config>
    <!-- 生成类型 1-根据包地址生成，包下所有类的接口 2-自定义类生成 -->
    <generateType value="1"/>

    <!-- 项目下类扫描路径（绝对路径）, 支持多个 , 一般是项目根路径 -->
    <projectScanPaths>
        <path value="/Users/xx/project/api-generate-demo"/>
    </projectScanPaths>

    <!-- 包文件夹地址（绝对路径,生成类型为1时必填） -->
    <apiPackagePath
            value="/Users/xx/project/api-generate-demo/api-generate-web02/src/main/java/com/example/controller"/>

    <!-- 类文件地址（绝对路径,生成类型为2时必填）-->
    <apiClassPaths>
        <path value=""/>
    </apiClassPaths>

</config>

```

**启动注意事项**

- 启动前需要配置好XML文件中的内容；
- 配置文件获取路径默认是当前运行路径下名称为 ApiGeneratorConfig.xml；
- 生成文件默认地址 ：当前运行路径下 generate 文件夹下；

 Win 系统下启动 双击 start.bat<br /> ios 系统下启动  双击 start.command 

<a name="HHTYM"></a>
### 4.1.2全功能配置指南
<a name="fi2eP"></a>
#### 1).根据包地址生成
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<config>
    <!-- 生成类型 1-根据包地址生成，包下所有类的接口 2-自定义类生成 -->
    <generateType value="1"/>

    <!-- 项目下类扫描路径（绝对路径）, 支持多个 -->
    <projectScanPaths>
        <path value="/Users/xx/project/api-generate-demo"/>
    </projectScanPaths>

    <!-- 包文件夹地址（绝对路径,生成类型为1时必填） -->
    <apiPackagePath
            value="/Users/xx/project/api-generate-demo/api-generate-web02/src/main/java/com/example/controller"/>

</config>

```

<a name="sULgi"></a>
#### 2).根据类地址生成
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<config>
    <!-- 生成类型 1-根据包地址生成，包下所有类的接口 2-自定义类生成 -->
    <generateType value="2"/>

    <!-- 项目下类扫描路径（绝对路径）, 支持多个 -->
    <projectScanPaths>
        <path value="/Users/xx/project/workproject/fshows-finance"/>
    </projectScanPaths>

    <!-- 类文件地址（绝对路径,生成类型为2时必填）-->
    <apiClassPaths>
        <path value="/Users/xx/project/workproject/fshows-finance/fshows-finance-openapi/src/main/java/com/fshows/finance/service/FeeCodeApiService.java"/>
        <path value="/Users/xx/project/workproject/fshows-finance/fshows-finance-openapi/src/main/java/com/fshows/finance/service/PayableApiService.java"/>
    </apiClassPaths>
</config>
```

<a name="qstTX"></a>
#### 3).统一网关类型接口配置
```xml
<!-- 开放接口注解类配置, annotationFieldName 默认 "method" -->
<gateWayField annotationName="com.fshows.finance.common.annotation.OpenApi" annotationFieldName="method"/>
```

<a name="InLp3"></a>
#### 4).根据参数名排除
```xml
<!-- 排除参数名配置（支持多个） -->
<apiExcludeParamNames>
  <paramName value="nonce"/>
  <paramName value="sourceType"/>
</apiExcludeParamNames>
```

<a name="YMQXO"></a>
#### 5).根据参数全路径排除
```xml
  <!-- 排除参数全类名配置（支持多个） -->
    <apiExcludeParamClassNames>
        <paramClassName value="com.demo.param.UserLoginResult"/>
    </apiExcludeParamClassNames>
```

<a name="P4NzF"></a>
#### 6).Json示例生成开关
```xml
<!-- 是否生成 1-是,2-否 默认1 -->
<generateJsonFlag value="2"/>
```
<a name="yBUoD"></a>
#### 7).参数类型转换
```xml
<!-- 参数类型 1-默认,2-驼峰转下划线 -->
<apiParamType value="2"/>
```

<a name="6qTZE"></a>
#### 8).生成文件路径配置
 注：生成文件默认名称，Api-generate-MMddHHmmss.md<br />        默认路径是当前项目路径,路径可自定义
```xml
<!-- 生成文件地址(默认：当前目录) -->
<generateFilePath value="/Users/mengqiang/Desktop/api-generate-tool/finance/openapi"/>
```
<a name="ucYvQ"></a>
#### 9).项目请求地址根路径
```xml
<!-- 项目接口访问根路径 默认无 -->
<projectRootUrl value=""/>
```

<a name="vvfNr"></a>
#### 10).自定义统一返回对象
注：一些接口中未包含统一返回对象，但是希望生成的文档包含此结构<br />参数含义依次为： 参数名,  参数类型, 参数描述,  是否作为父级<br />是否作为父级标识为是情况下将会，把原先的返回参数添加到该父级下

```xml
<!-- 自定义顶级返回对象属性 -->
<apiRootResField>
  <field name="success" typeClassName="java.lang.Boolean" desc="请求是否成功" isParent="0"/>
  <field name="data" typeClassName="java.lang.Object" desc="" isParent="1"/>
  <field name="errorCode" typeClassName="java.lang.String" desc="错误码" isParent="0"/>
  <field name="errorMsg" typeClassName="java.lang.String" desc="错误信息" isParent="0"/>
</apiRootResField>
```

<a name="8fQgl"></a>
#### 11).接口头信息输出
针对接口文档存在统一头信息情况下使用，可以在每一个接口中添加头信息参数

```xml
<!-- 请求 contentType -->
<contentType value="application/x-www-form-urlencoded"/>

<!-- 头信息 -->
<apiHeaderField>
  <field name="token" type="String" desc="当前登录用户token" isRequired="1"/>
</apiHeaderField>
```

<a name="lSSqW"></a>
## 5.文件查看
<a name="HYnS8"></a>
## 5.1.[推荐]打开工具
| 系统 | 工具名称 | 官网地址 |  |
| --- | --- | --- | --- |
| Windows | **Typora** | [https://www.typora.io/](https://www.typora.io/) |  |
| IOS  | **MacDown** | [https://macdown.uranusjr.com/](https://macdown.uranusjr.com/) |  |


Demo示例图：<br />![image.png](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jZG4ubmxhcmsuY29tL3l1cXVlLzQ0NS8yMDE5L3BuZy8xNzE5OTIvMTU1NTgzMzkzNjg2Mi05YmQxMTEwYi02MmUzLTQ2MjctYWZjNC1lYTA0YWVkMDJjOWEucG5n?x-oss-process=image/format,png#align=left&display=inline&height=785&name=image.png&originHeight=1742&originWidth=1658&search=&size=698996&status=done&width=747)
<a name="d41d8cd9"></a>
### 
<a name="7215ac5c"></a>
### 4.2.2.文本形式查看
支持以文本形式打开文件，直接拷贝内容粘贴到语雀文档中<br />**<br />**![image.png](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jZG4ubmxhcmsuY29tL3l1cXVlLzQ0NS8yMDE5L3BuZy8xNzE5OTIvMTU1ODM0MTM0NjAwMS1jY2FhMjFjNC00ODE4LTRmYjQtOGZiMi02N2ZhODkyYjE3MDkucG5n?x-oss-process=image/format,png#align=left&display=inline&height=473&name=image.png&originHeight=946&originWidth=2670&search=&size=239719&status=done&width=1335)**<br />**注：输出使用的是Markdown 语法，若拷贝到语雀粘贴时请点击进行转换，**<br />**        建议采用工具打开后，再拷贝到羽雀，**<br />**        使用文本格式拷贝到语雀后会被重新编译，内容会存在多余的空行。**

**转换后示例：**<br />**![image.png](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jZG4ubmxhcmsuY29tL3l1cXVlLzQ0NS8yMDE5L3BuZy8xNzE5OTIvMTU1ODM0MTI5NjkyNS1iMTg0ZmI1Yi1hZWMzLTRkZWMtOWMwMC03NTU1YTM4YzE0YWQucG5n?x-oss-process=image/format,png#align=left&display=inline&height=596&name=image.png&originHeight=1192&originWidth=1208&search=&size=194568&status=done&width=604)**

<a name="zoSxV"></a>
# 6.附言

万物有痕，可能会有一些未考虑到场景或问题，欢迎大佬指点。
