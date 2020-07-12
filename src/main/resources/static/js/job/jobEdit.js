var baseUrl = ctx + "sys/job";
$(function () {
    validateJobForm();
});

/*
 * 验证用户表单
 */
function validateJobForm() {
    var icon = "<i class='fa fa-times-circle'></i>";
    $("#jobForm").validate({
        onfocusout: function (element) {
            $(element).valid();
        },
        rules: {
            beanName: {
                required: true,
                maxlength: 200
            },
            methodName: {
                required: true,
                maxlength: 200
            },
            params: {
                required: false,
                maxlength: 1000
            },
            cronExpression: {
                required: true,
                maxlength: 100,
                remote: {
                    url: baseUrl + "/isValidExpression",
                    cache: false,
                    type: "POST",
                    dataType: "json",
                    data: {
                        cronExp: function () {
                            return $("#cronExpression").val();
                        }
                    }
                }
            },
            remark: {
                required: false,
                maxlength: 500
            }
        },
        messages: {
            beanName: {
                required: icon + "请输入bean名称!",
                maxlength: icon + "bean名称长度不能大于200!"
            },
            methodName: {
                required: icon + "请输入方法名!",
                maxlength: icon + "方法名长度不能大于200!"
            },
            params: {
                maxlength: icon + "参数长度不能大于1000!"
            },
            cronExpression: {
                required: icon + "请输入cron表达式!",
                maxlength: icon + "cron表达式长度不能大于100!",
                remote: icon + "cron表达式不合法!"
            },
            remark: {
                maxlength: icon + "备注长度不能大于500!"
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
            jobSubmit();
        },
        invalidHandler: function () { //不通过回调
            return false;
        }
    });
}


/*
 * ajax提交用户表单
 */
function jobSubmit() {
    var url = $("#jobId").val() == null || $("#jobId").val() == "" ? baseUrl + "/save" : baseUrl + "/update";
    var data = JSON.stringify($('#jobForm').serializeJSON());
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
                refreshFrm("jobFrm");
            } else {
                toastr.error(r.msg);
            }
        }
    });
}