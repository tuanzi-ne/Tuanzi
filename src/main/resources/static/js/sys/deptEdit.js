var baseUrl = ctx + "sys/dept";
var setting = {
    view: {
        showLine: true,
        selectedMulti: false,
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
            name: "deptName",
            url: ""
        }
    }
};
var zNodes;

$(function () {
    validateDeptForm();
});

/*
 * 验证部门表单
 */
function validateDeptForm() {
    var icon = "<i class='fa fa-times-circle'></i>";
    $("#deptForm").validate({
        rules: {
            deptName: {
                required: true,
                maxlength: 50,
                remote: {
                    url: baseUrl + "/hasDept",
                    cache: false,
                    type: "post",
                    dataType: "json",
                    data: {
                        deptId: function () {
                            return $("#deptId").val();
                        },
                        deptName: function () {
                            return $("#deptName").val();
                        }
                    }
                }
            },
            deptDesc: {
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
            deptName: {
                required: icon + "请输入部门名称!",
                maxlength: icon + "部门名称长度不能大于50!",
                remote: icon + "部门名称已存在!"
            },
            deptDesc: {
                maxlength: icon + "部门描述长度不能大于200!"
            },
            parentName: {
                required: icon + "请选择上级部门!"
            },
            orderNo: {
                required: icon + "请输入排序号!",
                range: icon + "排序号介于1到1000之间!"
            }
        },
        highlight: function (element) {
            $(element).siblings('.error').removeClass("hide");
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        success: function (label) {
            label.closest('.form-group').removeClass('has-error').addClass('has-success');
            label.addClass("hide");
        },
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        submitHandler: function () { //通过后回调
            deptSubmit();
        },
        invalidHandler: function () { //不通过回调
            return false;
        }
    });
}

/*
 * ajax提交部门表单
 */
function deptSubmit() {
    var url = !$("#deptId").val()? baseUrl + "/save" : baseUrl + "/update";
    var data = JSON.stringify($('#deptForm').serializeJSON());
    $.ajax({
        type: "POST",
        cache: false,
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
function getDeptTree() {
    console.log(JSON.stringify({deptId:$("#deptId").val()}));
    $.ajax({
        type: "POST",
        cache: false,
        url: baseUrl + "/select",
        data: {deptId:$("#deptId").val()},
        dataType: "json",
        success: function (r) {
            // console.log(r.deptList);
            if (r.code) {
                zNodes = $.fn.zTree.init($("#deptTree"), setting, r.deptList);
            }
        }
    });
}

/*
 * 选择部门
 */
function selectDept() {
    getDeptTree();
    lopen($("#deptLayer"), "选择部门", 350, 400, deptCb);
}


function deptCb(index) {
    var nodes = zNodes.getSelectedNodes();
    if (nodes.length === 0)
        layer.msg("请选择部门", {time: 2000});
    $("#parentName").val(nodes[0].deptName);
    $("#parentId").val(nodes[0].id);
    layer.close(index);
}