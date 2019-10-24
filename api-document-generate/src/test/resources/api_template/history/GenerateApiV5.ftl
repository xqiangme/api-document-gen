<#if apiWriteList?? && (apiWriteList?size > 0) >
<#list apiWriteList as batchApiList>
<h3>${batchApiList_index+1}.<#if batchApiList.apiRootTitle??>${batchApiList.apiRootTitle}</#if></h3>
<#list batchApiList.apiBaseList as api>
<h4>${batchApiList_index+1}.${api_index+1}.<#if api.apiTitle??>${api.apiTitle}</#if></h4>
<ul>
    <#if api.apiAuthor?? && api.apiAuthor!="" && api.apiAuthor!=" "><li>作者 ：${api.apiAuthor}</li></#if><#if api.reqPath?? && api.reqPath!=""><li>请求地址 ：${api.reqPath}</li></#if><#if api.gateWayApiMethod?? && api.gateWayApiMethod!=""><li>请求方法名 ：${api.gateWayApiMethod}</li></#if><li>请求方式 ：<#if api.reqType??>${api.reqType}</#if></li>
    <li><strong>请求参数：</strong></li>
</ul>
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
    <@bpTree paramList=api.resParam.paramList level=1 paramTotalLevel=api.resParam.paramTotalLevel />
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
<#macro bpTree paramList level paramTotalLevel>
    <#if paramList?? && paramList?size gt 0>
        <#list paramList as child>
            <#if child.children?? && child.children?size gt 0>
                <tr>
                <td style="vertical-align: middle" rowspan="${child.childParamSize+1}"><#if child.name??>${level}-${child.name}</#if><#if child.type?index_of("List")!=-1> []<#else> {}</#if></td>
                </tr>
                <@bpTree paramList=child.children level=level+1 paramTotalLevel=paramTotalLevel />
            <#else>
                <tr>
                    <td><#if child.name??>${level}-${child.name}</#if></td><#if (paramTotalLevel-level) gt 0><#list 1..(paramTotalLevel-level) as t><td></td></#list></#if>
                    <td><#if child.type??>${child.type}</#if></td>
                    <td><#if child.requiredFlag==1>是<#else>否</#if> </td>
                    <td><#if child.length?? && child.length!="">${child.length}</#if></td>
                    <td><#if child.description??>${child.description}</#if> </td>
                </tr>
            </#if>
        </#list>
    </#if>
</#macro>