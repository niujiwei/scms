function uploader_file(option) {
	var option = option;// 传递的属性
	var listSHow = option.listSHow;// 展示信息dom
	var url = option.url;// 请求路径
	var fileVal = option.fileVal;// 请求提交文件参数名
	var uploader = WebUploader.create({

		// swf文件路径
		swf : 'Uploader.swf',

		// 文件接收服务端。
		server : url,

		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : {
			id : '#picker',
			multiple : false

		},
		fileNumLimit : 1,
		fileVal : fileVal,
		// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
		resize : false,

	});
	// 当有文件被添加进队列的时候
	uploader.on('fileQueued', function(file) {
		$(listSHow).append(
				'<div id="' + file.id + '" class="item">' + '<h4 class="info">'
						+ file.name + '</h4>' + '<p class="state">等待上传...</p>'
						+ '</div>');
	});
	uploader
			.on(
					'uploadProgress',
					function(file, percentage) {
						var $li = $('#' + file.id), $percent = $li
								.find('.progress .progress-bar');

						// 避免重复创建
						if (!$percent.length) {
							$percent = $(
									'<div class="progress progress-striped active">'
											+ '<div class="progress-bar" role="progressbar" style="width: 0%">'
											+ '</div>' + '</div>')
									.appendTo($li).find('.progress-bar');
						}

						$li.find('p.state').text('上传中');

						$percent.css('width', percentage * 100 + '%');

					});
	uploader.on('uploadSuccess', function(file) {// 上传成功
	});

	uploader.on('uploadError', function(file) {// 上传失败
		$('#' + file.id).find('p.state').text('上传出错');
	});

	uploader.on('uploadComplete', function(file) {// 最终都会走的方法
		$('#' + file.id).find('.progress').fadeOut();
	});

	return uploader;

}
