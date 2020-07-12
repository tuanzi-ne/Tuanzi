<!DOCTYPE html>
<html lang="zh-CN" xmlns:th="http://www.thymeleaf.org">
<#assign $header='<head th:include="include :: header"></head>'/>
<#assign $footer='<div th:include="include :: footer"></div>'/>
<#assign $str='$'/>
<#assign $str2='#'/>
<title>${comments!}</title>
${$header}
<body>
<div style="margin:auto">
    <div class="panel-body">
        <form id="${webPagename!}Form">
            <input id="${webPagename!}Id" name="${(pk.columnName)!}" type="hidden" th:value="${$str}{${(webPagename)!}?.${(pk.columnName)!}}">
        <#if columns??>
            <#list columns as column>
                <#if (column.attrname)?? && (pk.columnName)?? && (column.attrname) != (pk.columnName)
                && (column.attrname)?index_of("Time") == -1>
                <div class="form-group">
                    <label for="${(column.attrname)!}" class="control-label">${(column.columnComment)!}：</label>
                    <input id="${(column.attrname)!}" name="${(column.attrname)!}" class="form-control" type="text"
                           placeholder="${(column.columnComment)!}"
                           th:value="${$str}{${(webPagename)!}?.${(column.attrname)!}}">
                </div>
                </#if>
            </#list>
        </#if>
            <button type="submit" class="btn btn-primary">提交</button>
        </form>
    </div>
</div>
${$footer}
<script th:src="@{/js/${packageDir!}/${webPagename!}Edit.js(t=${$str}{${$str2}dates.createNow().getTime()})}"></script>
</body>
</html>