var baseUrl = ctx + "sys/user";

$(function () {
    loadTable();
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
        clickToSelect: true, // 点击行自动选择
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
                username: $("#username").val(),
                status: $("#status").val()
            };
        },
        onCheck: function (Item, element) {
            //改变选中行背景色
            $(element).parent().parent().addClass("success");
            return !1
        },
        onUncheck: function (Item, element) {
            $(element).parent().parent().removeClass('success');
            return !1
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
                field: 'username',
                title: '用户名称'
            },
            {
                field: 'realname',
                title: '姓名',
            },
            {
                field: 'deptName',
                title: '所属部门'
            },
            {
                field: 'phone',
                title: '手机'
            },
            {
                field: 'email',
                title: '邮箱'
            },
            {
                field: 'status',
                title: '状态',
                align: 'center',
                formatter: function (value) {
                    if (value === 1) {
                        return '<span class="label label-success">正常</span>';
                    } else {
                        return '<span class="label label-danger">禁用</span>';
                    }
                }
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
 * 刷新Bs Table
 */
function reload() {
    $('#bsTable').bootstrapTable('refresh');
}


/*
 * 带状态查询
 */
function query(value) {
    $("#status").val(value);
    reload();
}

/*
 * 新增
 */
$("#btnAdd").click(function () {
    window.parent.fopen(baseUrl + "/add", "新增用户", 800, 500);
});

/*
 * 修改
 */
$("#btnEdit").click(function () {
    var rows = $("#bsTable").bootstrapTable('getSelections');
    if (rows.length != 1) {
        layer.msg("请选择一个用户！", {time: 2000});
        return;
    }
    window.parent.fopen(baseUrl + "/edit/" + rows[0].id, "修改用户", 800, 500);
});

/*
 * 删除
 */
$("#btnDel").click(function () {
    var rows = $("#bsTable").bootstrapTable('getSelections');
    if (!rows.length) {
        layer.msg("请选择要删除的用户！", {time: 2000});
        return;
    }
    var ids = [];
    rows.forEach(function (e) {
        ids.push(e.id);
    });
    parent.layer.confirm('确定要删除选中的用户吗？', {
        btn: ['确定', '取消']
    }, function (index) {
        parent.layer.close(index);
        $.ajax({
            type: "POST",
            cache: false,
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
 * 重置密码
 */
$("#btnResetPwd").click(function () {
    var rows = $("#bsTable").bootstrapTable('getSelections');
    if (!rows.length) {
        layer.msg("请选择要重置密码的用户！", {time: 2000});
        return;
    }
    var ids = [];
    rows.forEach(function (e) {
        ids.push(e.id);
    });
    parent.layer.confirm('确定要重置选中用户密码吗？', {
        btn: ['确定', '取消']
    }, function (index) {
        parent.layer.close(index);
        $.ajax({
            type: "POST",
            cache: false,
            url: baseUrl + "/resetPwd",
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