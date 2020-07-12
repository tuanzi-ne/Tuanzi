var baseUrl = ctx + "sys/role";
var zNodes;
var setting = {
    view: {
        showLine: true,
        selectedMulti: false,
        dblClickExpand: true,
        nameIsHTML: false
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: 0
        },
        key: {
            name: "resName",
            url: ""
        }
    },
    check: {
        enable: true,
        chkStyle: "checkbox"
    },
    callback: {
        onClick: onNodeClick
    }
};

/*
 * 点击node选中复选框
 */
function onNodeClick(event, treeId, treeNode) {
    $.fn.zTree.getZTreeObj("resTree").checkNode(treeNode, !treeNode.checked, true, true);
}

$(function () {
    loadTable();
    getRoleResTree();
});

/*
 * 加载Bs Table
 */
function loadTable() {
    $('#bsTable').bootstrapTable({
        method: 'get',
        url: baseUrl + "/list",
        cache: false,
        toolbar: '#toolbar', //工具按钮用哪个容器
        showRefresh: true, //是否显示刷新按钮
        showToggle: true, //是否显示切换试图按钮
        showColumns: true, //是否显示内容列下拉框
        iconSize: 'outline',
        icons: {
            paginationSwitchDown: 'glyphicon-collapse-down icon-chevron-down',
            paginationSwitchUp: 'glyphicon-collapse-up icon-chevron-up',
            refresh: 'glyphicon-refresh icon-refresh',
            toggle: 'glyphicon-list-alt icon-list-alt',
            columns: 'glyphicon-th icon-th',
            detailOpen: 'glyphicon-plus icon-plus',
            detailClose: 'glyphicon-minus icon-minus',
            export: 'glyphicon-export  icon-share'
        },
        reorderableColumns: true, // 是否列调序
        showExport: true, //是否导出
        exportDataType: "all", //basic', 'all', 'selected'
        striped: true, // 是否隔行变色
        singleSelect: false, // 是否多选
        clickToSelect: false, // 点击行自动选择
        maintainSelected: true, // 记住checkbox选择项
        sortName: "id", // 初始排序字段
        sortOrder: "asc", // 初始排序顺序
        pagination: true, // 启动分页
        sidePagination: "server", // 分页方式,client或server
        pageSize: 10, // 初始分页条数
        pageNumber: 1, // 初始分页页码
        pageList: [10, 15, 20, 25],  //记录数可选列表
        search: false, // 是否显示搜索框
        searchOnEnterKey: false,
        queryParamsType: "undefined",
        queryParams: function (params) {
            return {
                pageSize: params.pageSize,
                pageNumber: params.pageNumber,
                sortName: params.sortName,
                sortOrder: params.sortOrder,
                rolename: $("#rolename").val(),
                status: $("#status").val()
            };
        },
        onClickRow: function (row, element) {
            //改变选中行背景色
            $('.success').removeClass('success');
            $(element).addClass("success");
            getResByRId(row.id);
        },
        uniqueId: "id",
        idField: "id",
        columns: [
            {
                checkbox: true
            },
            {
                title: '序号',
                formatter: function (value, row, index) {
                    var pageSize = $('#bsTable').bootstrapTable('getOptions').pageSize;
                    var pageNumber = $('#bsTable').bootstrapTable('getOptions').pageNumber;
                    return pageSize * (pageNumber - 1) + index + 1;    //返回每条序号:每页条数 * (当前页 - 1)+序号
                }
            },
            {
                field: 'roleName',
                title: '角色名称'
            },
            {
                field: 'roleDesc',
                title: '角色描述'
            },
            {
                field: 'createTime',
                title: '创建时间',
                sortable: true,
                sortName: 'create_time'
            },
            {
                field: 'updateTime',
                title: '更新时间',
                sortable: true,
                sortName: 'update_time'
            }]
    });
}


/*
 * 获取zTree
 */
function getRoleResTree() {
    $.ajax({
        type: "GET",
        cache: false,
        url: baseUrl + "/tree",
        dataType: "json",
        success: function (r) {
            if (r.code) {
                zNodes = $.fn.zTree.init($("#resTree"), setting, r.resList);
            }
        }
    });
}

function getResByRId(roleId) {
    $.get(baseUrl + "/roleRes/" + roleId, function (r) {
        var resIds = r.resIds;
        zNodes.checkAllNodes(false);
        if (undefined != resIds && resIds.length) {
            //勾选角色资源
            for (var i = 0; i < resIds.length; i++) {
                var node = zNodes.getNodeByParam("id", resIds[i]);
                zNodes.checkNode(node, true, false);
            }
        }
    }, 'json');
    //展开所有节点
    zNodes.expandAll(true);
}

/*
 * 刷新Bs Table
 */
function reload() {
    $('#bsTable').bootstrapTable('refresh');
}


/*
 * 查询
 */
function query() {
    reload();
}

/*
 * 新增
 */
$("#btnAdd").click(function () {
    window.parent.fopen(baseUrl + "/add", "新增角色", 560, 350);
});

/*
 * 修改
 */
$("#btnEdit").click(function () {
    var rows = $("#bsTable").bootstrapTable('getSelections');
    if (rows.length != 1) {
        layer.msg("请勾选一个角色！", {time: 2000});
        return;
    }
    window.parent.fopen(baseUrl + "/edit/" + rows[0].id, "修改角色", 560, 350);
});

/*
 * 删除
 */
$("#btnDel").click(function () {
    var rows = $("#bsTable").bootstrapTable('getSelections');
    if (!rows.length) {
        layer.msg("请选择要删除的角色！", {time: 2000});
        return;
    }
    var ids = [];
    rows.forEach(function (e) {
        ids.push(e.id);
    });
    parent.layer.confirm('确定要删除选中的角色吗？', {
        btn: ['确定', '取消']
    }, function (index) {
        parent.layer.close(index);
        $.ajax({
            type: "POST",
            url: baseUrl + "/delete",
            data: JSON.stringify(ids),
            dataType: "json",
            contentType: "application/json",
            success: function (r) {
                if (r.code) {
                    reload();
                } else {
                    parent.layer.msg(r.msg, {time: 2000});
                }
            }
        });
    })
});

/*
 * 分配资源权限
 */
$("#btnPerms").click(function () {
    var rows = $("#bsTable").bootstrapTable('getSelections');
    if (rows.length != 1) {
        layer.msg("请勾选一个角色!", {time: 2000});
        return;
    }
    var nodes = zNodes.getCheckedNodes(true);
    if (!nodes.length) {
        layer.msg("请勾选资源!", {time: 2000});
        return;
    }
    var resIds = [];
    nodes.forEach(function (e) {
        resIds.push(e.id);
    });
    $.ajax({
        type: "POST",
        url: baseUrl + "/savePerms",
        data: JSON.stringify({id: rows[0].id, list: resIds}),
        dataType: "json",
        contentType: "application/json",
        success: function (r) {
            if (r.code) {
                layer.msg("分配权限成功!", {time: 2000});
            }
        }
    });
});