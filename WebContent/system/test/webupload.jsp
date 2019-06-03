<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<title>上传demo</title>
	<!--引入CSS-->
	<link rel="stylesheet" type="text/css" href="/common/webuploader/webuploader.css">
	
	<!--引入JS-->
	<script type="text/javascript" src="/common/webuploader/webuploader.js"></script>
	<script type="text/javascript" src="/common/js/md5.js"></script>
	
	<!--SWF在初始化的时候指定，在后面将展示-->
	<script type="text/javascript">
		var uploader;
		function doInit(){
			uploader = WebUploader.create({
			    // swf文件路径
			    swf: '/common/webuploader/Uploader.swf',
			    // 文件接收服务端。
			    server: '/attachmentController/uploadChunk.action',
			    // 选择文件的按钮。可选。
			    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
			    pick: '#picker',
			    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
			    resize: false,
			  //是否要分片处理大文件上传。
	            chunked: true,
	            // 如果要分片，分多大一片？ 默认大小为5M.
//             	chunkSize: 5,
	            // 如果某个分片由于网络问题出错，允许自动重传多少次？
	            chunkRetry: 3,
	            // 上传并发数。允许同时最大上传进程数[默认值：3]
	            threads: 1,
	         // 去重
	            duplicate: true,
	         // 上传本分片时预处理下一分片
	            prepareNextFile: true
			});

			// 当有文件被添加进队列的时候
			uploader.on( 'fileQueued', function( file ) {
			    $("#thelist").append( '<div id="' + file.id + '" class="item">' +
			        '<h4 class="info">' + file.name + '</h4>' +
			        '<p class="state">等待上传...</p>' +
			    '</div>' );
			});
			
			// 文件上传过程中创建进度条实时显示。
			uploader.on( 'uploadProgress', function( file, percentage ) {
			    var $li = $( '#'+file.id ),
			        $percent = $li.find('.progress .progress-bar');

			    // 避免重复创建
			    if ( !$percent.length ) {
			        $percent = $('<div class="progress progress-striped active">' +
			          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
			          '</div>' +
			        '</div>').appendTo( $li ).find('.progress-bar');
			    }

			    $li.find('p.state').text('上传中');

			    $percent.css( 'width', percentage * 100 + '%' );
			});
			
			uploader.on( 'uploadSuccess', function( file ) {
			    $( '#'+file.id ).find('p.state').text('已上传');
			});

			uploader.on( 'uploadError', function( file ) {
			    $( '#'+file.id ).find('p.state').text('上传出错');
			});

			uploader.on( 'uploadComplete', function( file ) {
			    $( '#'+file.id ).find('.progress').fadeOut();
			});

			// 文件上传过程中创建进度条实时显示。
			uploader.on( 'uploadProgress', function( file, percentage ) {
			    var $li = $( '#'+file.id ),
			        $percent = $li.find('.progress span');

			    // 避免重复创建
			    if ( !$percent.length ) {
			        $percent = $('<p class="progress"><span></span></p>')
			                .appendTo( $li )
			                .find('span');
			    }

			    $percent.css( 'width', percentage * 100 + '%' );
			});

			// 文件上传成功，给item添加成功class, 用样式标记上传成功。
			uploader.on( 'uploadSuccess', function( file ) {
			    $( '#'+file.id ).addClass('upload-state-done');
			});

			// 文件上传失败，显示上传出错。
			uploader.on( 'uploadError', function( file ) {
			    var $li = $( '#'+file.id ),
			        $error = $li.find('div.error');

			    // 避免重复创建
			    if ( !$error.length ) {
			        $error = $('<div class="error"></div>').appendTo( $li );
			    }

			    $error.text('上传失败');
			});

			// 完成上传完了，成功或者失败，先删除进度条。
			uploader.on( 'uploadComplete', function( file ) {
			    $( '#'+file.id ).find('.progress').remove();
			});

		}
		
		
		
		WebUploader.Uploader.register({
		    "before-send-file": "beforeSendFile",  // 整个文件上传前
		    "before-send": "beforeSend",           // 每个分片上传前
		    "after-send-file": "afterSendFile"     // 分片上传完毕
		}, {
		    beforeSendFile: function (file) {   
		    	
		    	var deferred = WebUploader.Deferred();  
                //1、计算文件的唯一标记，用于断点续传  
                (new WebUploader.Uploader()).md5File(file,0,10*1024*1024)  
                    .progress(function(percentage){
                        $('#'+file.id).find("p.state").text("正在读取文件信息...");  
                    })  
                    .then(function(val){  
                        fileMd5=val;  
                        //获取文件信息后进入下一步  
                        deferred.resolve();  
                    });  
                return deferred.promise();  
		    }
		    , beforeSend: function (block) {
		    	 var deferred = WebUploader.Deferred();  
                 
	                $.ajax({  
	                    type:"POST",  
	                    url:"/attachmentController/checkChunk.action",  
	                    async:false,
	                    data:{  
	                        //文件唯一标记  
	                        fileMd5:fileMd5,  
	                        //当前分块下标  
	                        chunk:block.chunk,  
	                        //当前分块大小  
	                        chunkSize:block.end-block.start  
	                    },  
	                    dataType:"json",  
	                    success:function(json){  
	                        if(json.rtState){  
	                            //分块存在，跳过  
	                            deferred.reject();  
	                        }else{  
	                            //分块不存在或不完整，重新发送该分块内容  
	                            deferred.resolve();  
	                        }  
	                    }  
	                });  
	                  
	                this.owner.options.formData.fileMd5 = fileMd5;
	                deferred.resolve();  
	                return deferred.promise();  
		    }
		    , afterSendFile: function (file) {
		    	 //如果分块上传成功，则通知后台合并分块  
                $.ajax({  
                    type:"POST",  
                    url:"/attachmentController/mergeChunks.action",  
                    data:{  
                        fileMd5:fileMd5,
                        fileName:file.name
                    },  
                    success:function(response){  
                        alert("上传成功");  
                       
                    }  
                });  
		    }
		});
	</script>
</head>
<body onload="doInit()">
	<div class="uploadDiv">
	    <!--用来存放文件信息-->
	    <div id="thelist" class="uploader-list"></div>
	    <div class="btns">
	        <a id="picker" style="width:40px;">选择文件</a>
	        <button id="ctlBtn" class="btn-win-white" onclick="uploader.upload()">上传</button>
	    </div>
	</div>
</body>
</html>
