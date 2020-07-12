var baseUrl =  ctx + "sys/param";

$(function () {
    validateParamForm();
});

/*
 * 验证表单
 */
function validateParamForm() {
    var icon = "<i class='fa fa-times-circle'></i>";
    $("#paramForm").validate({
        onfocusout: function (element) {
            $(element).valid();
        },
        rules: {
            paramKey:{
                required:true,
                maxlength:200,
                remote: {
                    url: baseUrl + "/isHasKey",
                    cache: false,
                    type: "POST",
                    dataType: "json",
                    data: {
                        paramId: function () {
                            return $("#paramId").val();
                        },
                        paramKey: function () {
                            return $("#paramKey").val();
                        }
                    }
                }
            },
            paramValue:{
                required:true,
                maxlength:2000
            },
            paramDesc:{
                maxlength:200
            }
        },
        messages: {
            paramKey:{
                required:icon + "请输入参数键名!",
                maxlength:icon + "参数键名长度不能大于200",
                remote: icon + "参数键名已存在!"
            },
            paramValue:{
                required:icon + "请输入参数键值!",
                maxlength:icon + "参数键值长度不能大于2000"
            },
            paramDesc:{
                required:icon + "请输入参数描述!",
                maxlength:icon + "参数描述长度不能大于200"
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
            paramSubmit();
        },
        invalidHandler: function () { //不通过回调
            return false;
        }
    });
}


/*
 * ajax提交用户表单
 */
function paramSubmit() {
    var url = $("#paramId").val() == null || $("#paramId").val() == "" ? baseUrl + "/save" : baseUrl + "/update";
    var data = JSON.stringify($('#paramForm').serializeJSON());
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
                refreshFrm("paramFrm");
            } else {
                toastr.error(r.msg);
            }
        }
    });
}