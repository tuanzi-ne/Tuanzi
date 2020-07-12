/*
 * 防止被iframe嵌套
 */
if (top !== self) {
    top.location.href = self.location.href;
}

$(function () {
    validateForm();

    /*
     * 初始化按钮loading功能
     * n秒后恢复原状
     */
    $("input[data-loading-text]").click(function () {
        var btn = $(this).button('loading');
        setTimeout(function () {
            btn.button('reset');
        }, 1000);
    });
});



/*
 * 验证和提交表单
 */
function validateForm() {
    var icon = "<i class='fa fa-times-circle'></i>";
    $("#form-signin").validate({
        onfocusout: function (element) {
            $(element).valid();
        },
        rules: {
            username: {
                required: true,
                maxlength: 50
            },
            password: {
                required: true,
                maxlength: 100
            },
            captcha: {
                required: true,
                rangelength: [4, 4]
            }
        },
        messages: {
            username: {
                required: icon + "请输入账号!",
                maxlength: icon + "账号长度不能大于50!"
            },
            password: {
                required: icon + "请输入密码!",
                maxlength: icon + "密码长度不能大于50!"
            },
            captcha: {
                required: icon + "请输入验证码!",
                rangelength: icon + "验证码长度是4位!"
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
            submit();
        },
        invalidHandler: function () { //不通过回调
            return false;
        }
    });
}

/*
 * ajax提交表单
 */
function submit() {
    $.ajax({
        type: "POST",
        url: "login?t=" + $.now(),
        data: $('#form-signin').serialize(),
        dataType: "json",
        success: function (result) {
            // 防浏览器保存密码
            $("#password").val("").attr("type","text");
            if (result.code) { //登录成功
                window.location.href = "admin";
            } else {
                changeCaptcha();
                $.bsAlert("msg",result.msg);
            }
        }
    });
}

/*
 * 点击图片
 */
function changeCaptcha() {
    $("#kaptcha").attr("src","captcha?t=" + $.now());
}

/*
 * 回车事件
 */
function onKeyDown() {
    if (event.keyCode === 13) {
        validateForm();
    }
}