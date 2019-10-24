<#if apiWriteList?? && (apiWriteList?size > 0) >
<#list apiWriteList as batchApiList>
<h2>${batchApiList_index+1}.<#if batchApiList.apiRootTitle??>${batchApiList.apiRootTitle}</#if></h2>
<#list batchApiList.apiBaseList as api>
<h3>${batchApiList_index+1}.${api_index+1}.<#if api.apiTitle??>${api.apiTitle}</#if></h3>
<ul>
    <#if api.apiAuthor?? && api.apiAuthor!="" && api.apiAuthor!=" "><li>作者 ：${api.apiAuthor}</li></#if><#if api.reqPath?? && api.reqPath!=""><li>请求地址 ：${api.reqPath}</li></#if><#if api.gateWayApiMethod?? && api.gateWayApiMethod!=""><li>请求方法名 ：${api.gateWayApiMethod}</li></#if>
    <#if interfacePathShowFlag?? && interfacePathShowFlag==true >
    <li>接口类名：<#if api.interfaceClassName??>${api.interfaceClassName}</#if></li>
    <li>接口方法名：<#if api.interfaceMethodName??>${api.interfaceMethodName}</#if></li>
    <#else>
    <#if api.reqType??  && api.reqType!=""  ><li>请求方式 ：${api.reqType}</li></#if>
    </#if>
    <#if contentType?? && contentType !="" >
        <li>Content-Type：${contentType}</li>
    </#if>
    <#if headerFieldList?? && (headerFieldList?size > 0) >
    <li>请求头：</li>
    <table>
    <thead>
    <tr style="background-color: #D4EEFC;">
        <th width="25%"><strong>参数</strong></th>
        <th width="25%"><strong>类型</strong></th>
        <th width="25%"><strong>必须</strong></th>
        <th width="25%"><strong>描述</strong></th>
    </tr>
    </thead>
    <tbody>
    <#list headerFieldList as headers>
        <tr>
            <td><#if headers.name??>${headers.name}</#if></td>
            <td><#if headers.type??>${headers.type}</#if></td>
            <td><#if headers.requiredFlag?? && headers.requiredFlag== true>是<#else>否</#if></td>
            <td><#if headers.desc??>${headers.desc}</#if></td>
        </tr>
    </#list>
    </tbody>
    </table>
    </#if>
    <li><strong>请求参数：</strong></li>
</ul>
<#if api.reqParam?? && api.reqParam.paramList?? && (api.reqParam.paramList?size > 0) >
<table>
    <thead>
    <tr style="background-color: #D4EEFC;">
        <th colspan="${api.reqParam.paramTotalLevel}"><strong>参数</strong></th>
        <th width="10%"><strong>类型</strong></th>
        <th width="10%"><strong>必须</strong></th>
        <th width="10%"><strong>长度</strong></th>
        <th><strong>描述</strong></th>
    </tr>
    </thead>
    <tbody>
    <#if api.reqParam?? && api.reqParam.paramList?? && (api.reqParam.paramList?size > 0)>
        <@bpTree paramList=api.reqParam.paramList level=1 paramTotalLevel=api.reqParam.paramTotalLevel parent="" exist=0 logLevel=logLevel paramType=paramType/>
    </#if>
    </tbody>
</table>
<#if api.reqParam.jsonExampleList?? && (api.reqParam.jsonExampleList?size > 0) >
<#if generateJsonFlag?? && generateJsonFlag== true>
<ul><li>请求示例：</li></ul>
<#list api.reqParam.jsonExampleList as json>
<#if json?? && json!='null' >
<pre data-lang="json"><code>
${json}
</code></pre>
</#if>
</#list>
</#if>
</#if>
<#else>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无
</#if>
<ul><li><strong>响应参数：</strong></li></ul>
<#-- 返回参数 -->
<#if api.resParam?? && api.resParam.paramList?? && (api.resParam.paramList?size > 0)>
<table>
    <thead>
    <tr style="background-color: #D4EEFC;">
        <th colspan="${api.resParam.paramTotalLevel}"><strong>参数</strong></th>
        <th width="10%"><strong>类型</strong></th>
        <th width="10%"><strong>必须</strong></th>
        <th width="10%"><strong>长度</strong></th>
        <th><strong>描述</strong></th>
    </tr>
    </thead>
    <tbody>
    <#if api.resParam?? && api.resParam.paramList?? && (api.resParam.paramList?size > 0)>
    <#-- 返参遍历 -->
        <@bpTree paramList=api.resParam.paramList level=1 paramTotalLevel=api.resParam.paramTotalLevel parent="" exist=0 logLevel=logLevel paramType=paramType/>
    </#if>
    </tbody>
</table>
<#if generateJsonFlag?? && generateJsonFlag== true>
<#if api.resParam.jsonExample??>
<ul><li>响应示例：</li></ul>
<pre data-lang="json"><code>
${api.resParam.jsonExample}
</code></pre>
</#if>
</#if>
<#else>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无
</#if>
</#list>
</#list>
</#if>
<#function camelToDashed(s)>
    <#return s
    <#-- "fooBar" to "foo_bar": -->
    ?replace('([a-z])([A-Z])', '$1_$2', 'r')
    <#-- "FOOBar" to "FOO_Bar": -->
    ?replace('([A-Z])([A-Z][a-z])', '$1_$2', 'r')
    <#-- All of those to "FOO_BAR": upper_case  -->
    ?lower_case
    >
</#function>
<#assign existValue=0>
<#macro bpTree paramList level paramTotalLevel parent exist logLevel paramType>
    <#if paramList?? && paramList?size gt 0>
        <#list paramList as child>
            <#if child.children?? && child.children?size gt 0>
                <#if child.children[0].children?? && child.children[0].children?size gt 0>
                    <tr>
                    <td style="vertical-align: middle" rowspan="${child.childParamSize-child.hasChildParamSize}"><#if child.name??><#if logLevel?? && logLevel ==1>${level-1}-</#if><#if paramType==2>${camelToDashed(child.name)}<#else>${child.name}</#if></#if><#if child.type?index_of("List")!=-1> []<#else> {}</#if></td>
                    <#assign existValue=1>
                </#if>
                <@bpTree paramList=child.children level=level+1 paramTotalLevel=paramTotalLevel parent=child exist=existValue logLevel=logLevel paramType=paramType/>
            <#else>
                <#if exist?? && exist ==0><tr><#else><#if existValue?? && existValue==0><tr></#if></#if><#if parent?? && parent !="" && child_index == 0><td style="vertical-align: middle" rowspan="${parent.childParamSize-parent.hasChildParamSize}"><#if parent.name??><#if logLevel?? && logLevel ==1>${level-1}-</#if><#if paramType==2>${camelToDashed(parent.name)}<#else>${parent.name}</#if></#if><#if parent.type?index_of("List")!=-1> []<#else> {}</#if></td></#if>
                <td><#if child.name??><#if logLevel?? && logLevel ==1>${level}-</#if><#if paramType==2>${camelToDashed(child.name)}<#else>${child.name}</#if></#if></td><#if (paramTotalLevel-level) gt 0><#list 1..(paramTotalLevel-level) as t><td></td></#list></#if>
                <td><#if child.type??>${child.type}</#if></td>
                <td><#if child.requiredFlag==1>是<#else>否</#if> </td>
                <td><#if child.length?? && child.length!="">${child.length}</#if></td>
                <td><#if child.description??>${child.description}</#if> </td>
                <#assign  exist=0 existValue=0>
                </tr>
            </#if>
        </#list>
    </#if>
</#macro>