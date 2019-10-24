
<#assign username="李四">

${test}-${username}
<#assign username="王五">

${test}-${username}
<#if list?? && (list?size > 0) >
    ${list[0]}
</#if>

<#if list?? && (list?size > 0) >
 <#list list as item>
    <#if item_index gt 0>
    ${item}
    </#if>
 </#list>
</#if>


