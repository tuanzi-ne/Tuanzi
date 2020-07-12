var baseUrl = "sys/user";
/*
 * 修改密码
 */
function modifyPwd() {
    window.parent.fopen(baseUrl + "/modifyPwd", "修改密码", 533, 400);
}
/*
 * 更换头像
 */
function changeHead() {
    window.parent.fixopen("sys/head", "更换头像", 700, 525);
}
// 提示工具
$('[data-toggle="tooltip"]').tooltip();