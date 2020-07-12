var baseUrl = ctx + "sys/gen";

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
                tableName: $("#tableName").val(),
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
                field: 'tableName',
                title: '表名'
            },
            {
                field: 'engine',
                title: '引擎'
            },
            {
                field: 'tableComment',
                title: '表注释'
            },
            {
                field: 'createTime',
                title: '创建时间',
                sortable: true,
                sortName: 'create_time'
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
 * 查询
 */
function query() {
    reload();
}

/*
 * 生成代码
 */
function genCode() {
    var rows = $("#bsTable").bootstrapTable('getSelections');
    if (!rows.length) {
        layer.msg("请选择一条记录！", {time: 2000});
        return;
    }
    var tableNames = [];
    rows.forEach(function (e) {
        tableNames.push(e.tableName);
    });
    location.href = baseUrl + "/code?tables=" + tableNames.join(",");
}