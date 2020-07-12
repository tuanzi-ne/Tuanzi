var baseUrl = "${pathName!}";

$(function () {
    validate${webPageName!}Form();
});

/*
 * 验证表单
 */
function validate${webPageName!}Form() {
    var icon = "<i class='fa fa-times-circle'></i>";
    $("#${webPagename!}Form").validate({
        onfocusout: function (element) {
            $(element).valid();
        },
        rules: {
    <#if columns??>
        <#list columns as column>
            <#if (column.attrname)?? && (pk.columnName)?? && (column.attrname) != (pk.columnName)
            && (column.attrname)?index_of("time") == -1>
                ${(column.attrname)!}:{
                required:true<#if column.columnLength??>,
                maxlength:${column.columnLength}</#if>
            }<#if (column_index) != (columns?size-1)>,</#if>
            </#if>
        </#list>
    </#if>
        },
        messages: {
    <#list columns as column>
    <#if (column.attrname)?? && (pk.columnName)?? && (column.attrname) != (pk.columnName)
    && (column.attrname)?index_of("Time") == -1>
        ${(column.attrname)!}:{
                required:icon + "请输入${column.columnComment}!"<#if column.columnLength??>,
                maxlength:icon + "${column.columnComment}长度不能大于${column.columnLength}"</#if>
            }<#if (column_index) != (columns?size-1)>,</#if>
    </#if>
    </#list>
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
            ${webPagename!}Submit();
        },
        invalidHandler: function () { //不通过回调
            return false;
        }
    });
}


/*
 * ajax提交用户表单
 */
function ${webPagename!}Submit() {
    var url = $("#${webPagename!}Id").val() == null || $("#${webPagename!}Id").val() == "" ? baseUrl + "/save" : baseUrl + "/update";
    var data = JSON.stringify($('#${webPagename!}Form').serializeJSON());
    $.ajax({
        type: "POST",
        cache: false,
        url: url,
        data: data,
        dataType: "json",
        contentType: "application/json",
        success: function (r) {
            if (r.code) {
                closeFW();
                refreshFrm("${webPagename!}Frm");
            } else {
                toastr.error(r.msg);
            }
        }
    });
}