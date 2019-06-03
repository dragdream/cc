//SWF多附件上传封装类
function TeeSWFUpload(settings){
	var random = Math.random();
	
	var fileContainer = document.getElementById(settings.fileContainer);
	var quickUpload = settings.quickUpload;
	
	var startBtn = document.createElement("input");
	startBtn.type="button";
	startBtn.className = "btn btn-one";
	startBtn.value="开始上传";
	var cancelBtn = document.createElement("input");
	cancelBtn.style.marginLeft="5px";
	cancelBtn.type="button";
	cancelBtn.className = "btn btn-one";
	cancelBtn.value="全部取消";
	
	var fileUploadProxy = document.createElement("div");
	var rand = Math.random();
	fileUploadProxy.setAttribute("id","fileUploadProxy"+rand);
	
	if(!settings.file_types){
		settings.file_types = "*.*";
		if(GLOBAL_ATTACH_TYPE!=null&&GLOBAL_ATTACH_TYPE!=""&&GLOBAL_ATTACH_TYPE!=undefined){
			var arr=GLOBAL_ATTACH_TYPE.split(",");
		    var types="";
		    for(var i=0;i<arr.length;i++){
		    	types+="*."+arr[i]+";";
		    }
		    
		    if(types.endsWith(";")){
		    	types.substring(0,types.length-1);
		    }
			settings.file_types=types;
		}
	}
	
	var fileContainerProxy = document.createElement("div");
	if(!fileContainer.proxy){
		fileContainer.proxy = fileContainerProxy;
		fileContainer.appendChild(fileContainerProxy);
	}
	fileContainer.appendChild(fileUploadProxy);
	
	var holderProxy = document.createElement("div");
	holderProxy.setAttribute("id","holderProxy"+rand);
	
	document.getElementById(settings.uploadHolder).appendChild(holderProxy);
	
	if(!settings.url){
		settings.url = commonUploadUrl;
	}
	
	var valuesArray = fileContainer.valuesArray;
	if(!valuesArray){
		valuesArray = new Array();
		fileContainer.valuesArray = valuesArray;
	}
	
	var namesArray = fileContainer.namesArray;
	if(!namesArray){
		namesArray = new Array();
		fileContainer.namesArray = namesArray;
	}
	
	var progressTarget = settings.renderContainer;
	if(!progressTarget){
		progressTarget = "fileUploadProxy"+rand;
	}
	
	var _default = {
			flash_url : uploadFlashUrl,
			upload_url: settings.url,
			button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
			button_cursor: SWFUpload.CURSOR.HAND,
			post_params: {"" : ""},
			file_size_limit : UPLOAD_ATTACH_LIMIT_GLOBAL,
			file_types : settings.file_types,
			file_types_description : "All Files",
			file_upload_limit : 100,
			file_queue_limit : 0,
			custom_settings : {
				settings:settings,
				progressTarget : progressTarget,
				cancelBtn:cancelBtn,
				startBtn:startBtn,
				fileContainer:fileContainer,
				valuesHolder:document.getElementById(settings.valuesHolder),
				namesHolder:document.getElementById(settings.namesHolder),
				valuesArray:valuesArray,
				namesArray:namesArray,
				queueComplele:settings.queueComplele,
				uploadSuccess:settings.uploadSuccess,
				renderFiles:settings.renderFiles,
				showUploadBtn:settings.showUploadBtn==undefined?true:settings.showUploadBtn,
				quickUpload:quickUpload,
				uploadStart:settings.uploadStart,
				swfUploadLoaded:settings.swfUploadLoaded
			},
			debug: false,

			// Button settings
			//button_image_url: "images/TestImageNoText_65x29.png",
			button_width: "88",
			button_height: "20",
			button_placeholder_id: "holderProxy"+rand,
			//button_text: '<span class="swfFont" onmouseover="alert()">批量上传</span>',
			button_text_style: ".swfFont {cursor:pointer;}",
			button_text_left_padding: 12,
			button_text_top_padding: 3,
			
			// The event handler functions are defined in handlers.js
			swfupload_loaded_handler : swfUploadLoaded,
			file_queued_handler : fileQueued,
			file_queue_error_handler : fileQueueError,
			file_dialog_complete_handler : fileDialogComplete,
			upload_start_handler : uploadStart,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadComplete,
			queue_complete_handler : queueComplete	// Queue plugin event
		};
	
	for(var key in _default){
		if(settings[key]){
			_default[key] = settings[key];
		}
	}
	
	var swf = new SWFUpload(_default);
	startBtn.onclick=function(){
		swf.startUpload();
	}
	
	this.doUpload = function(){
		swf.startUpload();
	};
	
	this.swf = swf;
	
	cancelBtn.onclick=function(){
		var filesQueued = swf.getStats().files_queued;
		swf.stopUpload();
		for(var i=0;i<filesQueued;i++){
			var file = swf.getFile(i);
			swf.cancelUpload(file.id);
		}
		startBtn.style.display = "none";
		cancelBtn.style.display = "none";
	}
	return this;
}

//同步提交 render一下  zhp 20140110 渲染出 添加附件按钮 直接指定 fileContainer 一个div 即可
//var systemImagePath = "<%=systemImagePath%>"; 这句话 要在 你写的 jsp中声明
/**
 * upfileList 是一个div的id 你只要这样写就行了 在页面上请声明 var systemImagePath = "<%=systemImagePath%>";  这句话 以制定图片路径
 * new TeeSimpleUploadRender({
		fileContainer:"upfileList"
	});
 */
function TeeSimpleUploadRender(settings){
	if(!settings){
		settings = settings || {};
	}
	var  fileContainer = settings.fileContainer || {}; 
	var spanTemp = "<div></div>";
	var _t_a = "<a class='addfile' href='javascript:void(0);'>添加附件</a>";
	var tomThis = this;
	//渲染添加附件
	this.renderDomAndBinddingEvent = function(){
		var rand = Math.random() * 1000;
		var _t_input = "<input type='file'  name='file"+rand+"' class='addfile' size='1' style='display: inherit'  />";
		var wrapContainer = $(spanTemp);
		var inp = $(_t_input);
		inp.bind("change",function(event){
			var ext = GLOBAL_ATTACH_TYPE.split(",");
			if(!fileValidator(inp[0],ext)){
				$(this).remove();
				alert("仅允许上传["+ext.join(",")+"]的格式文件");
				return;
			}
			var sp = this.value.split("\\");
			var fileName = sp[sp.length-1];
			$(this).parent().hide();
			var aCon = $("<a>"+fileName+"</a>");
			var removeImg = $("<img src='"+systemImagePath+"/upload/remove.png' />");
			removeImg.bind("click",function(){
					$(this).parent().remove();
			});
			$(this).parent().parent().append(aCon).append(removeImg);

			//继续渲染 添加附件
			tomThis.renderDomAndBinddingEvent();
		});
		wrapContainer.append($(_t_a).append(inp));
		$("#"+fileContainer).append(wrapContainer);
	}
	this.renderDomAndBinddingEvent();
}

//简单上传类
function TeeSimpleUpload(settings){
	var fileContainer = document.getElementById(settings.fileContainer);
	var uploadHolder = document.getElementById(settings.uploadHolder);
	var valuesArray = fileContainer.valuesArray;
	var ext = settings.ext||[];
	if(!valuesArray){
		valuesArray = new Array();
		fileContainer.valuesArray = valuesArray;
	}
	var valuesHolder = document.getElementById(settings.valuesHolder);
	
	if(!settings.url){
		settings.url = commonUploadUrl;
	}
	
	//创建form表单
	var form;
	if(settings.form){
		form = document.getElementById(settings.form);
	}else{
		form = document.createElement("form");
		form.setAttribute("method","post");
		form.setAttribute("enctype","multipart/form-data");
		form.setAttribute("action",settings.url);
	}
	
	var fileContainerProxy = document.createElement("div");
	if(!fileContainer.proxy){
		fileContainer.proxy = fileContainerProxy;
		fileContainer.appendChild(fileContainerProxy);
	}
	if(!settings.form){
		var parent = fileContainer.parentNode;
		parent.insertBefore(form,fileContainer);
		form.appendChild(fileContainer);
	}
	
	//创建上传文件按钮
	var uploadBtn = document.createElement("a");
	//是否显示上传按钮
	if(settings.showUploadBtn==true){
		uploadBtn.innerHTML = "上传文件";
	}
	uploadBtn.setAttribute("href","javascript:void(0)");
	uploadBtn.style.display="none";
	
	//选择文件回调函数，用于判断文件类型，并对其渲染文件列表
	var selectCallback=function(event){
		var sp = this.value.split("\\");
		var obj = this;
		form.appendChild(obj);
		
		//获取文件名
		var fileName = sp[sp.length-1];
		var fileItem = $("<a>"+fileName+"</a>");
		var removeBtn = $("<img src='"+systemImagePath+"/upload/remove.png' />");
		removeBtn.click(function(){
			fileItem.remove();
			removeBtn.remove();
			$(obj).remove();
		});
		
		if(!fileValidator(obj,ext)){
			removeBtn.click();
			alert("仅允许上传["+ext.join(",")+"]的格式文件");
			return;
		}
		
		$(fileContainer).append(fileItem);
		$(fileContainer).append(removeBtn);
		fileContainer.appendChild(uploadBtn);
		uploadBtn.style.display="";
		
	};
	
	var fileInput = document.createElement("input");
	var rand = Math.random();
	fileInput.setAttribute("type","file");
	fileInput.setAttribute("name","file"+rand);
	fileInput.style.position="absolute";
	fileInput.style.top = "0px";
	fileInput.style.width = "70px";
	fileInput.style.height = $(uploadHolder).outerHeight()+"px";
	fileInput.onchange=selectCallback;
	uploadHolder.appendChild(fileInput);
	fileInput.style.left = "0px";
	$(fileInput).css({opacity:0});
	
	//注册按钮事件
	uploadHolder.onclick=function(){
		var fileInput = document.createElement("input");
		var rand = Math.random();
		fileInput.setAttribute("type","file");
		fileInput.setAttribute("name","file"+rand);
		fileInput.style.position="absolute";
		fileInput.style.top = "0px";
		fileInput.style.width = "70px";
		fileInput.style.height = $(uploadHolder).outerHeight()+"px";
		fileInput.onchange=selectCallback;
		uploadHolder.appendChild(fileInput);
		fileInput.style.left = "0px";
		$(fileInput).css({opacity:0});
		
	};
	
	this.doUpload = function(){
		$(form).ajaxSubmit({
           url: settings.url,
           iframe: true,
           data: settings.post_params,
           success: function(json) {
        	   json = eval("("+json+")");
 				if(json.rtState){
 					if(settings.queueComplele){
 						settings.queueComplele(json);
 						return;
 					}
 					var lists = json.rtData;
 					for(var i=0;i<lists.length;i++){
 						var fileInfo = lists[i];
 						renderFilesForFileLoader(fileContainer,fileInfo);
 						valuesArray.push(fileInfo.sid);
 						valuesHolder.value = valuesArray;
 					}
 				}else{
 					alert(json.rtMsg);
 					return;
 				}
 				fileContainer.innerHTML = "";
 				//fileContainer.appendChild(uploadBtn);
 				uploadBtn.style.display="none";
 				fileContainer.appendChild(uploadBtn);
 				//fileContainer.appendChild(form);
 				if(settings.queueComplele){
 					settings.queueComplele();
 				}
           },
           error: function(arg1, arg2, ex) {
	           alert("文件传输错误");
           },
           dataType: 'html'});
	}
	
	var doUpload = this.doUpload;
	
	//注册上传事件
	uploadBtn.onclick=function(){
		doUpload();
	}
}

/**
 * 单附件上传组件
 * @return
 */
function TeeSingleUpload(settings){
	var uploadBtn = document.getElementById(settings.uploadBtn);
	var file_types = settings.file_types;//文件类型
	var callback = settings.callback;
	if(!settings.url){
		settings.url = commonUploadUrl;
	}
	
	//声明表单
	var form = document.createElement("form");
	form.setAttribute("method","post");
	form.setAttribute("enctype","multipart/form-data");
	form.setAttribute("action",settings.url);
	
	document.body.appendChild(form);
	
	var file = document.createElement("input");
	file.setAttribute("type","file");
	file.setAttribute("name","file"+Math.random());
	file.style.display="none";
	file.onchange=function(){
		$(form).ajaxSubmit({
	           url: settings.url,
	           iframe: false,
	           data: settings.post_params,
	           success: function(json) {
					settings.callback(json);
					file.value="";
	           },
	           error: function(arg1, arg2, ex) {
		           alert("文件传输错误");
		           file.value="";
	           },
	           dataType: 'json'});
	}
	
	form.appendChild(file);
	
	uploadBtn.onclick=function(){
		$(file).click();
	}
}

/**
 * 渲染附件
 * @param fileContainer
 * @param fileInfo
 * @return
 */
function renderFilesForFileLoader(fileContainer,fileInfo){
	var fileItem = $("<div>"+fileInfo.fileName+"&nbsp;<span>("+(fileInfo.sizeDesc)+")</span></div>");
	$(fileContainer.proxy).append(fileItem);
	
	var menuData = new Array();
	menuData.push({ name:'移除',action:function(sid){
		var arrays = fileContainer.valuesArray;
	},extData:[fileInfo.sid]});
	fileItem.TeeMenu({menuData:menuData,eventPosition:false});
	
//	var valuesArray = new Array();
//	var sp = valuesHolder.value;
//	if(sp!=""){
//		sp = sp.split(",");
//		for(var i=0;i<sp.length;i++){
//			valuesArray.push(sp[i]);
//		}
//	}
//	valuesArray.push(fileInfo.sid);
//	valuesHolder.value = valuesArray;
}

/**
 * 文件加载器
 * @param settings
 * @return
 */
function TeeFileLoader(settings){
	var fileContainer = settings.fileContainer;
	fileContainer = $("#"+fileContainer);
	
	var proxy = settings.proxy;
	var dataProxy = new TeeDataProxy(proxy);
	var dataModel = dataProxy.getData();
	if(dataModel.rtState){
		dataModel = dataModel.rtData;
	}
	for(var i=0;i<dataModel.length;i++){
		var item = dataModel[i];
		
	}
}

/**
 * 数据代理
 * @param opts
 * @return
 */
function TeeDataProxy(opts){
	var url = opts.url;
	var params = opts.params;
	var dataModel = opts.dataModel;
	var dataFilter = settings.dataFilter;//数据过滤器
	
	this.getData=function(){
		var data;
		if(url){
			data = tools.requestJsonRs(url,params);
		}else{
			data = dataModel;
		}
		if(dataFilter){
			data = dataFilter(data);
		}
		return data;
	}
}
 
$.fn.doUpload=function(settings){
	if(!settings.url){
		settings.url = commonUploadUrl;
	}
	$(this).ajaxSubmit({
        url: settings.url,
        iframe: true,
        data: settings.post_params,
        success: function(json) {
			if(settings.success){
				settings.success(eval("("+json+")"));
			}
        },
        error: function(arg1, arg2, ex) {
        	if(settings.error){
				settings.error(arg1, arg2, ex);
			}
        },
        dataType: 'html'});
}



/**
 * 单文件校验
 * @param obj
 * @param exts
 * @return
 */
function fileValidator(obj,exts){
	if(!exts){
		exts = [];
		if(GLOBAL_ATTACH_TYPE!=null&&GLOBAL_ATTACH_TYPE!=""&&GLOBAL_ATTACH_TYPE!=undefined){
			exts=GLOBAL_ATTACH_TYPE.split(",");
		}
		
	}
	if(exts.length==0){
		return true;
	}
	var valid = false;
	for(var i=0;i<exts.length;i++){
		if(obj.value.toLowerCase().indexOf("."+exts[i])!=-1){
			valid = true;
			break;
		}
	}
	return valid;
}

//绑定元素快速flash上传
$.fn.bingUpload=function(settings){
	var placeHolder = $(this);
	if(!settings){
		settings = {model:''};
	}
	var html = settings.html||"";
	
	var rand = new Date().getTime();
	var fileContainerId = "fileContainer"+rand;
	var uploadHolderId = "uploadHolder"+rand;
	var valuesHolderId = "valuesHolder"+rand;
	
	var render = "<div id='"+fileContainerId+"'></div>";
	render+="<a id='"+uploadHolderId+"' class='add_swfupload'>";
	render+="<img src='"+systemImagePath+"/upload/batch_upload.png'/>批量上传";
	render+="</a>";
	render+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"$('#"+fileContainerId+"').parent().hide();\">关闭</a>";
	render+="<input id='"+valuesHolderId+"' type='hidden'/>";
	render+="<div>"+html+"</div>";
	
	var panel = $("<div></div>").css({background:'#fafafa',border:"1px solid gray",height:280,width:380,"overflow":"auto",padding:5,fontSize:12});
	panel.appendTo($("body"));
	panel.append(render);
	panel.hide();
	panel.css({position:'absolute',left:placeHolder.offset().left,top:placeHolder.outerHeight()+placeHolder.offset().top});
	
	//多附件SWF上传组件
	var swfUploadObj = new TeeSWFUpload({
		fileContainer:fileContainerId,//文件列表容器
		uploadHolder:uploadHolderId,//上传按钮放置容器
		valuesHolder:valuesHolderId,//附件主键返回值容器，是个input
		queueComplele:settings.queueComplele,
		uploadSuccess:settings.uploadSuccess,
		renderFiles:settings.renderFiles,//是否去渲染文件
		post_params:{model:settings.model}//后台传入值，model为模块标志
		});
	
	//注册绑定事件
	placeHolder.click(function(){
		panel.show();
	});
	
}