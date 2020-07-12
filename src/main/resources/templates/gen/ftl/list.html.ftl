<!DOCTYPE html>
<html lang="zh-CN" xmlns:th="http://www.thymeleaf.org"
      xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
<#assign $header='<head th:include="include :: header"></head>'/>
<#assign $footer='<div th:include="include :: footer"></div>'/>
<#assign $list='shiro:hasPermission="'+permPrefix+':list"'/>
<#assign $add='shiro:hasPermission="'+permPrefix+':add"'/>
<#assign $edit='shiro:hasPermission="'+permPrefix+':edit"'/>
<#assign $delete='shiro:hasPermission="'+permPrefix+':delete"'/>
<#assign $str='$'/>
<#assign $str2='#'/>
<title>${comments!}</title>
${$header}
<body>
<div class="row" style="margin:auto">
    <div class="panel-body">
        <!-- 过滤条件 -->
        <div class="panel panel-default">
            <div class="panel-heading">查询条件</div>
            <div class="panel-body">
                <form id="${webPagename!}Frm" class="form-horizontal">
                    <div class="form-group" ${$list}>
                        <div class="col-md-3">
                            <input style="display:none"/>
                            <input type="text" class="form-control" id="${(pk.columnName)!}" name="${(pk.attrname)!}"
                                   placeholder="${(pk.columnComment)!}"
                                   onkeydown="if(event.keyCode===13)query();">
                        </div>
                        <div class="btn-group">
                            <button type="button" class="btn btn-primary" id="btnQuery" onclick="query()">
                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查询
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <!-- bootstrap-table工具栏 -->
        <div id="toolbar" class="btn-group">
            <button ${$add} id="btnAdd" type="button" class="btn btn-success">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;新增
            </button>
            <button ${$edit} id="btnEdit" type="button" class="btn btn-info">
                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>&nbsp;修改
            </button>
            <button ${$delete} id="btnDel" type="button" class="btn btn-danger">
                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>&nbsp;删除
            </button>
        </div>
        <table id="bsTable"></table>
    </div>
</div>
${$footer}
<script th:src="@{/js/${packageDir!}/${webPagename!}.js(t=${$str}{${$str2}dates.createNow().getTime()})}"></script>
</body>
</html>