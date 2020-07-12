/*!
 * common js based JQuery and layer
 * Copyright 2018
 * author 团子
 * Licensed under MIT
 */

/*
 * layer IFrame弹层
 */
function fopen(url, title, width, height) {
    width = width == null ? "800px" : width + "px";
    height = height == null ? "600px" : height + "px";
    layer.open({
        type: 2,
        content: url,
        title: title,
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: [width, height]
    });
}

/*
 * layer IFrame固定弹层
 */
function fixopen(url, title, width, height) {
    width = width == null ? "800px" : width + "px";
    height = height == null ? "600px" : height + "px";
    layer.open({
        type: 2,
        content: url,
        title: title,
        maxmin: false,
        shadeClose: false, // 点击遮罩关闭层
        area: [width, height]
    });
}

/*成功提示框设置*/
toastr.options = {
    "closeButton": false,
    "debug": false,
    "positionClass": "toast-top-right",
    "showDuration": "500", //显示动画时间
    "hideDuration": "500", //隐藏动画时间
    "timeOut": "2000", //自动关闭超时时间
    "extendedTimeOut": "2000", //加长超时时间
    "showEasing": "swing", //显示动画缓冲方式
    "hideEasing": "swing", //隐藏动画缓冲方式
    "showMethod": "fadeIn", //显示动画方式
    "hideMethod": "fadeOut" //隐藏动画方式
};
/*
 * 成功消息
 */
function sMsg(msg) {
    toastr.success(msg);
}