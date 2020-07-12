var baseUrl = ctx + "sys/user";
$(function () {
    validatePwdForm();
});

/*
 * 验证表单
 */
function validatePwdForm() {
    var icon = "<i class='fa fa-times-circle'></i>";
    $("#pwdForm").validate({
        onfocusout: function (element) {
            $(element).valid();
        },
        rules: {
            oldPwd: {
                required: true,
                maxlength: 100,
                remote: {
                    url: baseUrl + "/hasPwd",
                    cache: false,
                    type: "post",
                    dataType: "json",
                    data: {
                        oldPwd: function () {
                            return $("#oldPwd").val();
                        }
                    }
                }
            },
            newPwd: {
                required: true,
                maxlength: 100
            },
            confirmPwd: {
                required: true,
                maxlength: 100,
                equalTo: "#newPwd"
            }
        },
        messages: {
            oldPwd: {
                required: icon + "请输入旧密码!",
                maxlength: icon + "密码长度不能大于100!",
                remote: icon + "旧密码错误!"
            },
            newPwd: {
                required: icon + "请输入新密码!",
                maxlength: icon + "密码长度不能大于100!"
            },
            confirmPwd: {
                required: icon + "请输入确认密码!",
                maxlength: icon + "密码长度不能大于100!",
                equalTo: icon + "两次输入的密码不一致!"
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
            error.appendTo(element.parent().parent());
        },
        submitHandler: function () { //通过后回调
            pwdSubmit();
        },
        invalidHandler: function () { //不通过回调
            return false;
        }
    });
}

/*
 * ajax提交表单
 */
function pwdSubmit() {
    var url = baseUrl + "/updatePwd";
    var data = $('#pwdForm').serialize();
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        dataType: "json",
        success: function (r) {
            if (r.code) {
                closeFW();
                refreshFrm("pwdFrm");
                window.parent.sMsg("您操作成功了!");
            } else {
                toastr.error(r.msg);
            }
        }
    });
}

$('#oldPwd').hideShowPassword({
    innerToggle: true, // Create an inner toggle
    touchSupport: Modernizr.touch // Enable touch enhancements
});
$('#newPwd').hideShowPassword({
    innerToggle: true, // Create an inner toggle
    touchSupport: Modernizr.touch // Enable touch enhancements
});
$('#confirmPwd').hideShowPassword({
    innerToggle: true, // Create an inner toggle
    touchSupport: Modernizr.touch // Enable touch enhancements
});