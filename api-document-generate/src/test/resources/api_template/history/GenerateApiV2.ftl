<#if apiWriteList?? && (apiWriteList?size > 0) >
<#list apiWriteList as batchApiList>
<h3>${batchApiList_index+1}.<#if batchApiList.apiRootTitle??>${batchApiList.apiRootTitle}</#if></h3>
<#list batchApiList.apiBaseList as api>
<h4>${batchApiList_index+1}.${api_index+1}.<#if api.apiTitle??>${api.apiTitle}</#if></h4>
<ul>
    <#if api.apiAuthor?? && api.apiAuthor!="" && api.apiAuthor!=" "><li>作者 ：${api.apiAuthor}</li></#if><#if api.reqPath?? && api.reqPath!=""><li>请求地址 ：${api.reqPath}</li></#if><#if api.gateWayApiMethod?? && api.gateWayApiMethod!=""><li>请求方法名 ：${api.gateWayApiMethod}</li></#if><li>请求方式 ：<#if api.reqType??>${api.reqType}</#if></li>
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
    <#-- 入参遍历 -->
    <#list api.reqParam.paramList as firstLevel>
    <#-- 一级入参 -->
        <#-- 是否存在二级 -->
        <#if !firstLevel.children?? || (firstLevel.children?size == 0) >
          <tr>
            <td><#if firstLevel.name??><#if paramType==2>${camelToDashed(firstLevel.name)}<#else>${firstLevel.name}</#if></#if></td><#if api.reqParam.paramTotalLevel gt 1><#list 1..(api.reqParam.paramTotalLevel-1) as t><td></td></#list></#if>
            <td><#if firstLevel.type??>${firstLevel.type}</#if></td>
            <td><#if firstLevel.requiredFlag==1>是<#else>否</#if></td>
            <td><#if firstLevel.length?? && firstLevel.length!="">${firstLevel.length}</#if></td>
            <td><#if firstLevel.description??>${firstLevel.description}</#if> </td>
        <#-- 存在二级则向下合并单元格 -->
          </tr>
        <#else>
        <#-- 二级入参遍历 -->
                <#list firstLevel.children as secondLevel>
                        <#-- 存在三级则向下合并单元格 -->
                        <#if !secondLevel.children?? || (secondLevel.children?size == 0) >
                            <tr><#if secondLevel_index==0><td style="vertical-align: middle" rowspan="${firstLevel.childParamSize}"><#if firstLevel.name??><#if paramType==2>${camelToDashed(firstLevel.name)}<#else>${firstLevel.name}</#if></#if><#if firstLevel.type?index_of("List")!=-1> []<#else> {}</#if></td></#if>
                                <td><#if secondLevel.name??><#if paramType==2>${camelToDashed(secondLevel.name)}<#else>${secondLevel.name}</#if></#if></td><#if api.reqParam.paramTotalLevel gt 2><#list 1..(api.reqParam.paramTotalLevel-2) as t><td></td></#list></#if>
                                <td><#if secondLevel.type??>${secondLevel.type}</#if></td>
                                <td><#if secondLevel.requiredFlag==1>是<#else>否</#if></td>
                                <td><#if secondLevel.length?? && secondLevel.length!="">${secondLevel.length}</#if></td>
                                <td><#if secondLevel.description??>${secondLevel.description}</#if> </td>
                            </tr>
                        <#else>
                            <#list secondLevel.children as threeLevel>
                                <#if !threeLevel.children?? || (threeLevel.children?size ==0) >
                                    <tr><#if threeLevel_index==0><td style="vertical-align: middle" rowspan="${secondLevel.childParamSize}"><#if secondLevel.name??><#if paramType==2>${camelToDashed(secondLevel.name)}<#else>${secondLevel.name}</#if></#if><#if secondLevel.type?index_of("List")!=-1> []<#else> {}</#if></td></#if>
                                        <td><#if threeLevel.name??><#if paramType==2>${camelToDashed(threeLevel.name)}<#else>${threeLevel.name}</#if></#if></td><#if api.reqParam.paramTotalLevel gt 3><#list 1..(api.reqParam.paramTotalLevel-3) as t><td></td></#list></#if>
                                        <td><#if threeLevel.type??>${threeLevel.type}</#if></td>
                                        <td><#if threeLevel.requiredFlag==1>是<#else>否</#if></td>
                                        <td><#if threeLevel.length?? && threeLevel.length!="">${threeLevel.length}</#if></td>
                                        <td><#if threeLevel.description??>${threeLevel.description}</#if> </td>
                                    </tr>
                                <#else>
                                    <#list threeLevel.children as fourLevel>
                                        <tr><#if threeLevel_index==0><td style="vertical-align: middle" rowspan="${threeLevel.childParamSize}"><#if threeLevel.name??><#if paramType==2>${camelToDashed(threeLevel.name)}<#else>${threeLevel.name}</#if></#if><#if threeLevel.type?index_of("List")!=-1> []<#else> {}</#if></td></#if>
                                            <td><#if fourLevel.name??><#if paramType==2>${camelToDashed(fourLevel.name)}<#else>${fourLevel.name}</#if></#if></td>
                                            <td><#if fourLevel.type??>${fourLevel.type}</#if></td>
                                            <td><#if fourLevel.requiredFlag==1>是<#else>否</#if> </td>
                                            <td><#if fourLevel.length?? && fourLevel.length!="">${fourLevel.length}</#if></td>
                                            <td><#if fourLevel.description??>${fourLevel.description}</#if> </td>
                                        </tr>
                                    </#list>
                                </#if>
                            </#list>
                         </#if>
                </#list>
         </#if>
    </tr>
    </#list>
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
    <#-- 入参遍历 -->
    <#list api.resParam.paramList as repFirstLevel>
    <#-- 一级入参 -->
            <#if !repFirstLevel.children?? || (repFirstLevel.children?size = 0) >
            <tr>
                <td><#if repFirstLevel.name??><#if paramType==2>${camelToDashed(repFirstLevel.name)}<#else>${repFirstLevel.name}</#if></#if></td><#if api.resParam.paramTotalLevel gt 1><#list 1..(api.resParam.paramTotalLevel-1) as t><td></td></#list></#if>
                <td><#if repFirstLevel.type??>${repFirstLevel.type}</#if></td>
                <td>是</td>
                <td><#if repFirstLevel.length?? && repFirstLevel.length!="">${repFirstLevel.length}</#if></td>
                <td><#if repFirstLevel.description??>${repFirstLevel.description}</#if> </td>
            </tr>
           <#else>
           <#-- 三级参数总长度-->
           <#-- 存在二级则向下合并单元格 -->
           <#-- 二级入参遍历 -->
                   <#list repFirstLevel.children as repSecondLevel>
                           <#-- 存在三级则向下合并单元格 -->
                           <#if !repSecondLevel.children?? || (repSecondLevel.children?size == 0) >
                               <tr><#if repSecondLevel_index==0><td style="vertical-align: middle" rowspan="${repFirstLevel.childParamSize-repFirstLevel.hasChildParamSize}"><#if repFirstLevel.name??><#if paramType==2>${camelToDashed(repFirstLevel.name)}<#else>${repFirstLevel.name}</#if></#if><#if repFirstLevel.type?index_of("List")!=-1> []<#else> {}</#if></td></#if>
                                   <td><#if repSecondLevel.name??><#if paramType==2>${camelToDashed(repSecondLevel.name)}<#else>${repSecondLevel.name}</#if></#if></td><#if api.resParam.paramTotalLevel gt 2><#list 1..(api.resParam.paramTotalLevel-2) as t><td></td></#list></#if>
                                   <td><#if repSecondLevel.type??>${repSecondLevel.type}</#if></td>
                                   <td>是</td>
                                   <td><#if repSecondLevel.length?? && repSecondLevel.length!="">${repSecondLevel.length}</#if></td>
                                   <td><#if repSecondLevel.description??>${repSecondLevel.description}</#if> </td>
                               </tr>
                          <#else>
                                 <#list repSecondLevel.children as repThreeLevel>
                                      <#if !repThreeLevel.children?? || (repThreeLevel.children?size == 0) >
                                          <tr> <#if repSecondLevel.children?size == 1><td style="vertical-align: middle" rowspan="${repFirstLevel.childParamSize-repFirstLevel.hasChildParamSize}"><#if repFirstLevel.name??><#if paramType==2>${camelToDashed(repFirstLevel.name)}<#else>${repFirstLevel.name}</#if></#if><#if repFirstLevel.type?index_of("List")!=-1> []<#else> {}</#if></td></#if>
                                              <#if repThreeLevel_index==0><td style="vertical-align: middle" rowspan="${repSecondLevel.childParamSize-repSecondLevel.hasChildParamSize}"><#if repSecondLevel.name??><#if paramType==2>${camelToDashed(repSecondLevel.name)}<#else>${repSecondLevel.name}</#if></#if><#if repSecondLevel.type?index_of("List")!=-1> []<#else> {}</#if></td></#if>
                                              <td><#if repThreeLevel.name??><#if paramType==2>${camelToDashed(repThreeLevel.name)}<#else>${repThreeLevel.name}</#if></#if></td><#if api.resParam.paramTotalLevel gt 3><#list 1..(api.resParam.paramTotalLevel-3) as t><td></td></#list></#if>
                                              <td><#if repThreeLevel.type??>${repThreeLevel.type}</#if></td>
                                              <td>是</td>
                                              <td><#if repThreeLevel.length?? && repThreeLevel.length!="">${repThreeLevel.length}</#if></td>
                                              <td><#if repThreeLevel.description??>${repThreeLevel.description}</#if> </td>
                                          </tr>
                                      <#else>
                                          <#list repThreeLevel.children as repFourLevel>
                                                  <#if !repFourLevel.children?? || (repFourLevel.children?size == 0) >
                                                      <tr><#if repFourLevel_index==0><td style="vertical-align: middle" rowspan="${repThreeLevel.childParamSize-repThreeLevel.hasChildParamSize}"><#if repThreeLevel.name??><#if paramType==2>${camelToDashed(repThreeLevel.name)}<#else>${repThreeLevel.name}</#if></#if><#if repThreeLevel.type?index_of("List")!=-1> []<#else> {}</#if></td></#if>
                                                          <td><#if repFourLevel.name??><#if paramType==2>${camelToDashed(repFourLevel.name)}<#else>${repFourLevel.name}</#if></#if></td><#if api.resParam.paramTotalLevel gt 4><#list 1..(api.resParam.paramTotalLevel-4) as t><td></td></#list></#if>
                                                          <td><#if repFourLevel.type??>${repFourLevel.type}</#if></td>
                                                          <td>是</td>
                                                          <td><#if repFourLevel.length?? && repFourLevel.length!="">${repFourLevel.length}</#if></td>
                                                          <td><#if repFourLevel.description??>${repFourLevel.description}</#if> </td>
                                                      </tr>
                                                  <#else>
                                                      <#list repFourLevel.children as fiveLevel>
                                                          <tr>
                                                              <td><#if fiveLevel.name??><#if paramType==2>${camelToDashed(fiveLevel.name)}<#else>${fiveLevel.name}</#if></#if></td>
                                                              <td><#if fiveLevel.type??>${fiveLevel.type}</#if></td>
                                                              <td>是</td>
                                                              <td><#if fiveLevel.length?? && fiveLevel.length!="">${fiveLevel.length}</#if></td>
                                                              <td><#if fiveLevel.description??>${fiveLevel.description}</#if> </td>
                                                          </tr>
                                                      </#list>
                                                  </#if>
                                          </#list>
                                      </#if>
                                  </#list>
                           </#if>
                   </#list>
           </#if>
    </#list>
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