
var res;

toastr.options.positionClass = 'toast-top-center';

/*
 * 防止被iframe嵌套
 */
if (top !== self) {
    top.location.href = self.location.href;
}


$(function () {

    validateRegisteredForm();

    /*
     * 初始化按钮loading功能
     * n秒后恢复原状
     */
    $("button[data-loading-text]").click(function () {
        var btn = $(this).button('loading');
        setTimeout(function () {
            btn.button('reset');
        }, 1000);
    });
});

/*
 * 验证和提交表单
 */
function validateRegisteredForm() {
    var icon = "<i class='fa fa-times-circle'></i>";
    $("#re-form-signin").validate({
        onfocusout: function (element) {
            $(element).valid();
        },
        rules: {
            uname: {
                required: true,
                maxlength: 50
            },
            npwd: {
                required: true,
                maxlength: 100
            },
            opwd: {
                required: true,
                maxlength: 100,
                equalTo: "#npwd"
            }
        },
        messages: {
            uname: {
                required: icon + "请输入账号!",
                maxlength: icon + "账号长度不能大于50!"
            },
            npwd: {
                required: icon + "请输入密码!",
                maxlength: icon + "密码长度不能大于50!"
            },
            opwd: {
                required: icon + "请输入密码!",
                maxlength: icon + "密码长度不能大于50!",
                equalTo: "两次密码输入不一致"
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
            // submit();
            res.validate();
        },
        invalidHandler: function () { //不通过回调
            return false;
        }
    });
}

vaptcha({
    vid: '验证单元id', // 必填
    type: 'invisible', // 必填 显示类型 隐藏式
    scene: 1,// 场景值 默认0
    offline_server: 'http://localhost:8080/offline', //离线模式服务端地址 必填
    //mode: 'offline', // 加入此参数直接进入离线模式，用于调试，上线请删除
    //可选参数
    //lang: 'zh-CN', // 语言 默认zh-CN,可选值zh-CN,en,zh-TW
    //https: false, // 使用https 默认false
    //style: 'dark' //按钮样式 默认dark，可选值 dark,light
    //color: '#57ABFF' //按钮颜色 默认值#57ABFF
}).then(function (vaptchaObj) {
    res = vaptchaObj;//将VAPTCHA验证实例保存到局部变量中

    //获取token的方式一:
    //vaptchaObj.renderTokenInput('.login-form')//以form的方式提交数据时，使用此函数向表单添加token值

    //获取token的方式二:
    vaptchaObj.listen('pass', function () {
        // 验证成功进行后续操作
        var data = {
            //表单数据
            token: vaptchaObj.getToken(),
            username: $("#uname").val(),
            password: $("#opwd").val()
        };
        $.ajax({
            type: 'POST',
            url: "registered",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(data),
            success: function (r) {
                console.log(r);
                // 防浏览器保存密码
                $("#npwd").val("").attr("type","text");
                $("#opwd").val("").attr("type","text");
                $("#uname").val("").attr("type","text");

                if (r.code !== 1) {
                    // alert("二次验证失败");
                    vaptchaObj.reset(); //重置验证码
                    $.bsAlert("re-msg",r.msg);
                } else {
                    // alert("二次验证成功");
                    toastr.success('注册成功')

                    // window.location.href = "login";

                }
            },

        });

    });

    //关闭验证弹窗时触发
    vaptchaObj.listen('close', function () {
        //验证弹窗关闭触发
    })
});