var baseUrl = ctx + "sys/res";
var isAdd = !$("#resId").val();
var zNodes;
var setting = {
    view: {
        showLine: true,
        selectedMulti: false,
        nameIsHTML: false,
        dblClickExpand: false
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            name: "resName",
            url: ""
        }
    },
    callback: {
        onClick: onNodeClick
    }
};

/*
 * 单击展开节点
 */
function onNodeClick(event, treeId, treeNode) {
    $.fn.zTree.getZTreeObj("resTree").expandNode(treeNode);
}

$(function () {
    validateResForm();
});

/*
 * 验证资源表单
 */
function validateResForm() {
    var icon = "<i class='fa fa-times-circle'></i>";
    $("#resForm").validate({
        onfocusout: function (element) {
            $(element).valid();
        },
        ignore: '',
        rules: {
            resType: {
                required: true
            },
            resName: {
                required: true,
                maxlength: 50,
                remote: {
                    url: baseUrl + "/hasRes",
                    cache: false,
                    type: "post",
                    dataType: "json",
                    data: {
                        resId: function () {
                            return $("#resId").val();
                        },
                        resName: function () {
                            return $("#resName").val();
                        }
                    }
                }
            },
            resDesc: {
                required: false,
                maxlength: 200
            },
            parentName: {
                required: true
            },
            orderNo: {
                required: true,
                range: [1, 1000]
            }
        },
        messages: {
            resType: {
                required: icon + "请选择资源类型!"
            },
            resName: {
                required: icon + "请输入资源名称!",
                maxlength: icon + "资源名称长度不能大于50!",
                remote: icon + "资源名称已存在!"
            },
            resIcon: {
                required: icon + "请选择资源图标!"
            },
            parentName: {
                required: icon + "请选择上级资源!"
            },
            orderNo: {
                required: icon + "请输入排序号!",
                range: icon + "排序号介于1到10000之间!"
            }
        },
        highlight: function (element) {
            $(element).siblings('.error').removeClass("hide");
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
            $(element).closest('.form-line').removeClass('has-success').addClass('has-error');
        },
        success: function (label) {
            label.closest('.form-group').removeClass('has-error').addClass('has-success');
            label.closest('.form-line').removeClass('has-error').addClass('has-success');
            label.addClass('hide');
        },
        errorPlacement: function (error, element) {
            if (element.is(":radio") || element.is(":checkbox")) {
                error.appendTo(element.parent().parent().parent());
            } else {
                error.appendTo(element.parent());
            }
        },
        submitHandler: function () { //通过后回调
            resSubmit();
        },
        invalidHandler: function () { //不通过回调
            return false;
        }
    });
    // 动态修改验证规则
    var type = $('input[name="resType"]:checked').val();
    if (type === '0') {
        categoryRule();
    } else if (type === '1') {
        menuRule();
    } else {
        btnRule();
    }
}

function categoryRule() {
    var icon = "<i class='fa fa-times-circle'></i>";
    $("#resIcon").rules("remove");
    $('#resIcon').rules('add', {
        required: true,
        messages: {
            required: icon + "请选择资源图标!"
        }
    });
    $("#resUrl").rules("remove");
    $("#resPerms").rules("remove");
}

function menuRule() {
    var icon = "<i class='fa fa-times-circle'></i>";
    $("#resIcon").rules("remove");
    $('#resIcon').rules('add', {
        required: true,
        messages: {
            required: icon + "请选择资源图标!"
        }
    });
    $("#resUrl").rules("remove");
    $('#resUrl').rules('add', {
        required: true,
        messages: {
            required: icon + "请输入资源URL!"
        }
    });
    $("#resPerms").rules("remove");
}

function btnRule() {
    var icon = "<i class='fa fa-times-circle'></i>";
    $("#resUrl").rules("remove");
    $("#resIcon").rules("remove");
    $("#resPerms").rules("remove");
    $('#resPerms').rules('add', {
        required: true,
        messages: {
            required: icon + "请输入资源权限标识!"
        }
    });
}

/*
 * 选择资源类型
 */
$("input[name='resType']:radio").on('ifChecked', function () {
    $('.form-group .error').hide();
    $('.form-group').removeClass('has-success has-error');
    $('.form-line .error').hide();
    $('.form-line').removeClass('has-success has-error');
    var checked = $(this).val();
    if (checked === '0') { //目录
        $("#urlDiv").hide();
        if (isAdd) {
            $("#resUrl").val('');
        }
        $("#permsDiv").hide();
        if (isAdd) {
            $("#resPerms").val('');
        }
        $("#iconDiv").show();
        categoryRule();
    } else if (checked === '1') { //菜单
        $("#iconDiv").show();
        $("#urlDiv").show();
        $("#permsDiv").show();
        menuRule();
    } else { //按钮
        $("#urlDiv").hide();
        if (isAdd) {
            $("#resUrl").val('');
        }
        $("#iconDiv").hide();
        if (isAdd) {
            $("#resIcon").val('');
        }
        $("#permsDiv").show();
        btnRule();
    }
});


/*
 * ajax提交资源表单
 */
function resSubmit() {
    var type = $('input[name="resType"]:checked').val();
    if (type === '0') {
        $("#resUrl").val('');
        $("#resPerms").val('');
    } else if (type === '2') {
        $("#resUrl").val('');
        $("#resIcon").val('');
    }
    var url = isAdd ? baseUrl + "/save" : baseUrl + "/update";
    var data = JSON.stringify($('#resForm').serializeJSON());
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        contentType: "application/json",
        dataType: "json",
        success: function (r) {
            if (r.code) {
                refreshTb("bsTable");
                closeFW();
                window.parent.sMsg("您操作成功了!");
            } else {
                toastr.error(r.msg);
            }
        }
    });
}

/*
 * 获取zTree
 */
function getResTree() {
    $.ajax({
        type: "POST",
        cache: false,
        url: baseUrl + "/tree",
        data: {resId:$("#resId").val()},
        dataType: "json",
        success: function (r) {
            if (r.code) {
                zNodes = $.fn.zTree.init($("#resTree"), setting, r.resList);
            }
        }
    });
}

/*
 * 选择资源
 */
function selectRes() {
    getResTree();
    lopen($("#resLayer"), "选择资源", 350, 400, resCb);
}


function resCb(index) {
    var nodes = zNodes.getSelectedNodes();
    if (nodes.length == 0)
        layer.msg("请选择资源", {time: 2000});
    $("#parentName").val(nodes[0].resName);
    $("#parentId").val(nodes[0].id);
    layer.close(index);
}

$('#iconLayer').find('i').on('click', function () {
    $('#iconLayer').find('i').not(this).removeClass("active");
    $(this).addClass("active");
});


function selectIcon() {
    lopen($("#iconLayer"), "图标列表", 500, 313, iconCb);
}

function iconCb(index) {
    if ($("#iconLayer .active").length === 0) {
        layer.msg("请选择图标", {time: 2000});
    } else {
        //擦去选择
        var css = $("#iconLayer").find(".active").removeClass("active").attr('class');
        $("#resIcon").focus().val(css).blur();
        $("#icon").removeClass().addClass(css);
        layer.close(index);
    }
}

