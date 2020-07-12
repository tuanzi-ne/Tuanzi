/*
 * layer 自定义弹层
 */
function lopen(element, title, width, height, cb) {
    width = width == null ? "800px" : width + "px";
    height = height == null ? "600px" : height + "px";
    layer.open({
        type: 1,
        skin: 'layui-layer-molv',
        content: element,
        title: title,
        shadeClose: false, // 点击遮罩关闭层
        area: [width, height],
        btn: ['确定','取消'],
        yes: cb
    });
}

/*
 * 关闭IFrame弹窗
 */
function closeFW() {
    parent.layer.close(parent.layer.getFrameIndex(window.name));
}

/*
 * 刷新IFrame父页面表单
 */
function refreshFrm(frmId) {
    var frames = window.parent.frames;
    for (var i = 0; i < frames.length; i++) {
        var form = frames[i].document.forms[frmId];
        if (form != undefined)
            form.submit();
    }
}

/*
 * 刷新IFrame父页面表格
 */
function refreshTb(eId) {
    var frames = window.parent.frames;
    for (var i = 0; i < frames.length; i++) {
        if (frames[i].document.getElementById(eId) != undefined)
            frames[i].window.reload();
    }
}