/* extend function & Member function based Bootstrap and JQuery
 * v1.0
 * author 团子
 */
;(function ($) {
    $.extend({ //扩展函数
        bsAlert: function (id, title, type) {
            var alertStyle;
            if (type === undefined || type === "warning") {
                alertStyle = "alert alert-danger alert-dismissible";
            } else if (type === "success") {
                alertStyle = "alert alert-success alert-dismissible";
            }
            var alertDiv = "<div class='" + alertStyle + "' style='margin-bottom:0px; role='alert'>"
                + "<button type='button' class='close' data-dismiss='alert'"
                + "<span aria-hidden='true'>&times;</span><span class='sr-only'>Close</span></button>"
                + title + "</div>";
            $("#" + id).empty().append(alertDiv).hide().show("fast");
        }
    });
    $.fn.extend({ //扩展成员函数
        toUpper: function (options) {
            // 转换大写
            var _this = $(this);
            _this.unbind("keyup").bind("keyup", function () {
                var v = _this.val();
                _this.val(v.toUpperCase());
            });
        },
        isNotNull: function (options) {
            // 元素值不能为空
            var _this = $(this);
            _this.unbind("blur").blur(function () {
                var s = $(this);
                if (jQuery.trim(s.val()) === "") {
                    s.parent().next().html("不能为空!");
                    return false;
                } else {
                    s.parent().next().empty();
                    return true;
                }
            });
        }
    });
})(jQuery);