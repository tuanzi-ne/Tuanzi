var baseUrl = ctx + "sys/role";
$(function () {
    validateRoleForm();
});

/*
 * 验证角色表单
 */
function validateRoleForm() {
    var icon = "<i class='fa fa-times-circle'></i>";
    $("#roleForm").validate({
        onfocusout: function (element) {
            $(element).valid();
        },
        rules: {
            roleName: {
                required: true,
                maxlength: 50,
                remote: {
                    url: baseUrl + "/hasRole",
                    cache: false,
                    type: "POST",
                    dataType: "json",
                    data: {
                        roleId: function () {
                            return $("#roleId").val();
                        },
                        roleName: function () {
                            return $("#roleName").val();
                        }
                    }
                }
            },
            roleDesc: {
                required: false,
                maxlength: 200
            }
        },
        messages: {
            roleName: {
                required: icon + "请输入角色名称!",
                maxlength: icon + "角色名称长度不能大于50!",
                remote: icon + "该角色名称已存在!"
            },
            roleDesc: {
                maxlength: icon + "角色描述长度不能超过200!"
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
            roleSubmit();
        },
        invalidHandler: function () { //不通过回调
            return false;
        }
    });
}

/*
 * ajax提交用户表单
 */
function roleSubmit() {
    var url = !$("#roleId").val() ? baseUrl + "/save" : baseUrl + "/update";
    var data = JSON.stringify($('#roleForm').serializeJSON());
    $.ajax({
        type: "POST",
        cache: false,
        url: url,
        data: data,
        contentType: "application/json",
        dataType: "json",
        success: function (r) {
            if (r.code) {
                closeFW();
                refreshFrm("roleFrm");
                window.parent.sMsg("您操作成功了!");
            } else {
                toastr.error(r.msg);
            }
        }
    });
}