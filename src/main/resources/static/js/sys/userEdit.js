var baseUrl = ctx + "sys/user";
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
    validateUserForm();
});

/*
 * 验证用户表单
 */
function validateUserForm() {
    var editFlag = !$("#userId").val();
    var icon = "<i class='fa fa-times-circle'></i>";
    $("#userForm").validate({
        onfocusout: function (element) {
            $(element).valid();
        },
        rules: {
            username: {
                required: true,
                maxlength: 50,
                remote: {
                    url: baseUrl + "/isHasUser",
                    cache: false,
                    type: "post",
                    dataType: "json",
                    data: {
                        userId: function () {
                            return $("#userId").val();
                        },
                        username: function () {
                            return $("#username").val();
                        }
                    }
                }
            },
            password: {
                required: editFlag,
                maxlength: 50
            },
            confirm_password: {
                required: editFlag,
                maxlength: 50,
                equalTo: "#password"
            },
            realname: {
                required: false,
                maxlength: 50
            },
            phone: {
                required: true,
                isMobile: true
            },
            email: {
                required: true,
                maxlength: 50,
                email: true
            },
            status: {
                required: true
            }
        },
        messages: {
            username: {
                required: icon + "请输入账号!",
                maxlength: icon + "账号长度不能大于50!",
                remote: icon + "用户名已存在!"
            },
            password: {
                required: icon + "请输入密码!",
                maxlength: icon + "密码长度不能大于50!"
            },
            confirm_password: {
                required: icon + "请输入确认密码!",
                maxlength: icon + "确认密码长度不能大于50!",
                equalTo: icon + "两次输入的密码不一致!"
            },
            realname: {
                maxlength: icon + "姓名长度不能大于50!"
            },
            phone: {
                required: icon + "请输入手机号码!",
                isMobile: icon + "请正确填写11位手机号码!"
            },
            email: {
                required: icon + "请输入邮箱地址!",
                email: icon + "请正确填写邮箱地址!",
                maxlength: icon + "邮箱地址长度不能大于50!"
            },
            status: {
                required: icon + "请选择用户状态!"
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
            if (element.is(":radio") || element.is(":checkbox")) {
                error.appendTo(element.parent().parent().parent());
            } else {
                error.appendTo(element.parent());
            }
        },
        submitHandler: function () { //通过后回调
            userSubmit();
        },
        invalidHandler: function () { //不通过回调
            return false;
        }
    });
}

/*
 * 获取选中角色
 */
function getCheckedRoles() {
    var roleArr = [];
    $("input:checkbox[name='role']:checked").each(function () {
        roleArr.push($(this).val());
    });
    return roleArr;
}

/*
 * ajax提交用户表单
 */
function userSubmit() {
    var url = $("#userId").val() == null || $("#userId").val() == "" ? baseUrl + "/save" : baseUrl + "/update";
    $("#roleIds").val(getCheckedRoles());
    $.ajax({
        type: "POST",
        cache: false,
        url: url,
        data: $('#userForm').serialize(),
        dataType: "json",
        success: function (r) {
            if (r.code) {
                closeFW();
                refreshFrm("userFrm");
            } else {
                toastr.error(r.msg);
            }
        }
    });
}

// 手机号码验证
jQuery.validator.addMethod("isMobile", function (value, element) {
    var length = value.length;
    var mobile = /^(\d{11})$/;
    return this.optional(element) || (length === 11 && mobile.test(value));
}, "请正确填写您的手机号码!");

/*
 * 获取zTree
 */
function getDeptTree() {
    $.ajax({
        type: "GET",
        cache: false,
        url: ctx + "sys/dept/tree",
        dataType: "json",
        success: function (r) {
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
    lopen($("#deptLayer"), "选择部门", 350, 400, yesCb);
}

function yesCb(index) {
    var nodes = zNodes.getSelectedNodes();
    if (nodes.length === 0)
        layer.msg("请选择部门", {time: 2000});
    $("#deptName").val(nodes[0].deptName);
    $("#deptId").val(nodes[0].id);
    layer.close(index);
}