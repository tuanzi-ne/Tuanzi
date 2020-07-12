$(function () {

    var file;

    /**
     * cropper初始化图片
     */
    function cropper() {
        var options = {
            viewMode: 1,
            aspectRatio: 1, //裁剪比例,NaN-自由选择区域
            preview:".img-preview", //预览
            background: true, //是否在容器上显示网格背景
            autoCropArea: 0.8, //初始裁剪区域占图片比例
            crop: function (e) { //裁剪操作回调
                // console.log(Math.round(e.x));
                // console.log(Math.round(e.y));
                // console.log(Math.round(e.height));
                // console.log(Math.round(e.width));
                // console.log(e.rotate);
                // console.log(e.scaleX);
                // console.log(e.scaleY);
            }
        };
        $('#img-upload').cropper(options);
    }

    /**
     * 上传图片按钮功能
     */
    $('#input').change(function () {
        var files = this.files;
        var done = function (url) {
            $('#input').val('');
            $('.img-upload-container div').hide();
            $('.toolbars').show();
            $('#img-upload').attr("src", url).show();
            cropper();
        };
        if (files && files.length > 0) {
            file = files[0];
            // console.log(file);
            if (!isImg(file.type)) {
                return false;
            }
            if (!isMaxFileSize(file.size)) {
                return false;
            }
            if (URL) {
                done(URL.createObjectURL(file));
            } else if (FileReader) {
                var reader = new FileReader();
                reader.onload = function () {
                    done(reader.result);
                };
                reader.readAsDataURL(file);
            }
        }
    });

    /**
     * 图片格式不合法,必须是gif,jpg,png中的一种
     * @param fileType
     */
    function isImg(fileType) {
        if(!/^(image\/gif|image\/jpeg|image\/png)$/.test(fileType.toLowerCase())) {
            layer.msg('图片格式不合法,须为gif|jpg|jpeg|png！', {time: 3000});
            return false;
        }
        return true;
    }

    /**
     * 文件大小超过最大限制值
     * @param filesize
     */
    function isMaxFileSize(filesize) {
        if (filesize > 2*1024*1024) {
            layer.msg('该图片大小超过2M！', {time: 3000});
            return false;
        }
        return true;
    }

    /**
     * toolbar按钮功能
     */
    $(".btn-cropper button").click(function () {
        var $img = $('#img-upload');
        var method = $(this).data("method");
        switch (method) {
            case 'zoom':
            case 'rotate':
                $img.cropper(method, $(this).data('option'));
                break;
            case 'move':
                $img.cropper(method, $(this).data('option'), $(this).data('second-option'));
                break;
            case 'scaleX':
            case 'scaleY':
                $img.cropper(method, $(this).data('option'));
                $(this).data('option',-$(this).data('option'));
                break;
            case 'reset':
                $img.cropper(method);
                break;
        }
    });

    /**
     * 取消
     */
    $('#btnCancel').click(function () {
        $('#img-upload').cropper("destroy").removeAttr("src").attr("style","");
        $('.toolbars').hide();
        $('.img-upload-container div').show();
    });

    /**
     * 确定
     */
    $('#btnUpload').click(function () {
        var canvas = $('#img-upload').cropper("getCroppedCanvas",{width:80,height:80});
        if (canvas == null) {
            layer.msg('请上传正确格式的图片！', {time: 3000});
            return false;
        }
        canvas.toBlob(function(blob) {
            var formData = new FormData();
            formData.append("uploadFile", blob, "face-img");
            $.ajax({
                type: "POST",
                url: ctx + 'sys/head/upload',
                data: formData,
                contentType: false, //必须
                processData: false, //必须
                dataType: "json",
                success: function(r){
                    //清空上传文件值
                    $('#input').val('');
                    //上传成功
                    if (r.code) {
                        // 刷新父窗口
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        window.parent.location.reload();
                    }
                    //上传失败
                    else {
                        layer.msg(r.msg, {time: 3000});
                    }
                },
                error : function() {
                    //清空上传文件值
                    $('#input').val('');
                }
            });
        }, file.type);
    })


});