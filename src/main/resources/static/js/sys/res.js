var baseUrl = ctx + "sys/res";

$(function () {
    loadTable();
});

/*
 * 加载Bs Table
 */
function loadTable() {
    $('#bsTable').bootstrapTreeTable({
        id: 'id', // 选取记录返回的值
        code: 'id', // 用于设置父子关系
        parentCode: 'parentId', // 用于设置父子关系
        rootCodeValue: null, //设置根节点code值----可指定根节点，默认为null,"",0,"0"
        data: [], // 构造table的数据集合，如果是ajax请求则不必填写
        type: "GET", // 请求数据的ajax类型
        url: baseUrl + "/list", // 请求数据的ajax的url
        ajaxParams: {
            resName: $("#resName").val().trim()
        }, // 请求数据的ajax的data属性
        expandColumn: 2, // 在哪一列上面显示展开按
        expandAll: true, // 是否全部展开
        striped: true, // 是否各行渐变色
        columns: [
            {
                field: 'selectItem',
                checkbox: true
            },
            {
                title: '序号',
                field: 'id',
                width: '35px'
            },
            {
                field: 'resName',
                title: '资源名称',
                width: '80px'
            },
            {
                field: 'resDesc',
                title: '描述',
                width: '60px'
            },
            {
                field: 'resIcon',
                title: '图标',
                width: '40px',
                formatter: function (row, index) {
                    return '<i class="' + row.resIcon + ' fa-lg"></i>';
                }
            },
            {
                field: 'parentName',
                title: '上级资源',
                width: '60px',
            },
            {
                field: 'resType',
                title: '类型',
                width: '30px',
                formatter: function (row, index) {
                    if (row.resType === 0) {
                        return '<span class="label label-info">目录</span>';
                    }
                    if (row.resType === 1) {
                        return '<span class="label label-success">菜单</span>';
                    }
                    if (row.resType === 2) {
                        return '<span class="label label-warning">按钮</span>';
                    }
                }
            },
            {
                field: 'resUrl',
                title: '资源URL',
                width: '50px',
            },
            {
                field: 'resPerms',
                title: '资源权限标识',
                width: '50px',
            },
            {
                field: 'orderNo',
                title: '排序号',
                width: '35px',
            },
            {
                field: 'createTime',
                title: '创建时间',
                width: '30px',
            },
            {
                field: 'updateTime',
                title: '更新时间',
                width: '30px'
            }
        ],
        toolbar: '#toolbar', //顶部工具条
        height: 0,
        expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom', // 展开的按钮的图标
        expanderCollapsedClass: 'glyphicon glyphicon-triangle-right' // 缩起的按钮的图标
    });
}

/*
 * 查询
 */
function query() {
    loadTable();
}

/*
 * 刷新Bs Table
 */
function reload() {
    loadTable();
}


/*
 * 新增
 */
$("#btnAdd").click(function () {
    window.parent.fopen(baseUrl + "/add", "新增资源", 800, 500);
});

/*
 * 修改
 */
$("#btnEdit").click(function () {
    var rows = $("#bsTable").bootstrapTreeTable('getSelections');
    if (rows.length != 1) {
        layer.msg("请选择一条资源！", {time: 2000});
        return;
    }
    window.parent.fopen(baseUrl + "/edit/" + rows[0].id, "修改资源", 800, 500);
});

/*
 * 删除
 */
$("#btnDel").click(function () {
    var rows = $("#bsTable").bootstrapTreeTable('getSelections');
    if (!rows.length) {
        layer.msg("请选择要删除的资源！", {time: 2000});
        return;
    }
    var ids = [];
    rows.forEach(function (e) {
        ids.push(e.id);
    });
    parent.layer.confirm('确定要删除选中的资源吗？', {
        btn: ['确定', '取消']
    }, function (index) {
        parent.layer.close(index);
        $.ajax({
            type: "POST",
            cache: false,
            url: baseUrl + "/delete",
            data: JSON.stringify(ids),
            contentType: "application/json",
            dataType: "json",
            success: function (r) {
                if (r.code) {
                    reload();
                } else {
                    layer.msg(r.msg, {time: 2000});
                }
            }
        });
    })
});