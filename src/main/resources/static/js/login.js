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
            // captcha: {
            //     required: true,
            //     rangelength: [4, 4]
            // }
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
            // captcha: {
            //     required: icon + "请输入验证码!",
            //     rangelength: icon + "验证码长度是4位!"
            // }
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
            obj.validate();
        },
        invalidHandler: function () { //不通过回调
            return false;
        }
    });
}



    vaptcha({
        vid: '5e7b1276bdd837290c9e440e', // 必填
        type: 'invisible', // 必填 显示类型 隐藏式
        scene: 1,// 场景值 默认0
        offline_server: 'http://localhost:8080/offline', //离线模式服务端地址 必填
        // mode: 'offline', // 加入此参数直接进入离线模式，用于调试，上线请删除
        //可选参数
        //lang: 'zh-CN', // 语言 默认zh-CN,可选值zh-CN,en,zh-TW
        //https: false, // 使用https 默认false
        //style: 'dark' //按钮样式 默认dark，可选值 dark,light
        //color: '#57ABFF' //按钮颜色 默认值#57ABFF
    }).then(function (vaptchaObj) {
        obj = vaptchaObj;//将VAPTCHA验证实例保存到局部变量中

        //获取token的方式一:
        //vaptchaObj.renderTokenInput('.login-form')//以form的方式提交数据时，使用此函数向表单添加token值

        //获取token的方式二:
        vaptchaObj.listen('pass', function () {
            // 验证成功进行后续操作
            var data = {
                //表单数据
                token: vaptchaObj.getToken(),
                username: $("#username").val(),
                password: $("#password").val()
            };

            console.log(data);
            $.ajax({
                type: 'POST',
                url: "login?t=" + $.now(),
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(data),
                success: function (r) {
                    console.log(r);
                    // 防浏览器保存密码
                    $("#password").val("").attr("type","text");

                    if (r.code !== 1) {
                        // alert("二次验证失败");
                        vaptchaObj.reset(); //重置验证码
                        $.bsAlert("msg",r.msg);
                    } else {
                        // alert("二次验证成功");
                        window.location.href = "admin";

                    }
                },

            });
        });

        //关闭验证弹窗时触发
        vaptchaObj.listen('close', function () {
            //验证弹窗关闭触发
        })
    });

    // //VAPTCHA实例初始化完成后，等到用户点击登录按钮时执行validate()方法
    // $('#btnLogin').on('click', function () {
    //     console.log("登录");
    //     obj.validate();
    // })






// /*
//  * ajax提交表单
//  */
// function submit() {
//     $.ajax({
//         type: "POST",
//         url: "login?t=" + $.now(),
//         data: $('#form-signin').serialize(),
//         dataType: "json",
//         success: function (result) {
//             // 防浏览器保存密码
//             $("#password").val("").attr("type","text");
//             if (result.code) { //登录成功
//                 window.location.href = "admin";
//             } else {
//                 changeCaptcha();
//                 $.bsAlert("msg",result.msg);
//             }
//         }
//     });
// }
//
// /*
//  * 点击图片
//  */
// function changeCaptcha() {
//     $("#kaptcha").attr("src","captcha?t=" + $.now());
// }

/*
 * 回车事件
 */
function onKeyDown() {
    if (event.keyCode === 13) {
        validateForm();
    }
}