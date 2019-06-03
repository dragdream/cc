function doInit(){
// 	try{	
		//初始化金格签章
	if(openKinggrid==1){
		Signature.init({//初始化属性
			keysn:keySn,
			clientConfig:{//初始化客户端参数
				'SOFTTYPE':kinggridVer+''//0为：标准版， 1：网络版
			},
			 delCallBack: delCB,
			 timestamp: true,  //获取签章服务器时间
			 imgtag: 0, //签章类型：0：无; 1:公章; 2:私章; 3:法人章; 4:法人签名; 5:手写签名
			valid : false,    //签章和证书有效期判断， 缺省不做判断
	       icon_move : true, //移动签章按钮隐藏显示，缺省显示
	       icon_remove : true, //撤销签章按钮隐藏显示，缺省显示
	       icon_sign : true, //数字签名按钮隐藏显示，缺省显示
	       icon_signverify : true, //签名验证按钮隐藏显示，缺省显示
	       icon_sealinfo : true, //签章验证按钮隐藏显示，缺省显示
		   certType : kinggridVer==0?'client':'client',//设置证书在签章服务器
		   sealType : kinggridVer==0?'client':'server',//设置印章从签章服务器取
		   serverUrl : kinggridServerUrl,
			documentid:runId+'',//设置文档ID
			documentname:runId+'',//设置文档名称
			pw_timeout:'s1800' //s：秒；h:小时；d:天
		});
	}
		
		
		reload();
		
		bindSignEvent();//绑定签章事件
		
		var para = {runId:runId,frpSid:frpSid};
		//创建公共附件快速上传组件
		pubAttachUploader = new TeeSWFUpload({
			fileContainer:"pulicAttachments",//文件列表容器
			renderContainer:"pulicAttachmentsTemp",//临时列表容器
			uploadHolder:"pubUpfile",//上传按钮放置容器
			valuesHolder:"pubUpfileValues",//附件主键返回值容器，是个input
			showUploadBtn:false,//不显示上传按钮
			quickUpload:true,//快速上传
			queueComplele:function(){//队列上传成功回调函数
				
			},
			renderFiles:true,//渲染附件
			url:contextPath+"/teeWorkflowAttachmentController/addWorkFlowAttachments.action",
			post_params:para//后台传入值，model为模块标志
		});

		
		feedbackAttachUploader = new TeeSimpleUpload({
			fileContainer:"upfile",//文件列表容器
			uploadHolder:"upFileHolder",//上传按钮放置容器
			valuesHolder:"pubUpfileValues",//附件主键返回值容器，是个input
			showUploadBtn:false,
			form:"feedBackForm",
			url:contextPath+"/feedBack/addFeedBack.action",
			queueComplele:function(){
				
			},
			post_params:para//后台传入值，model为模块标志
		});
		
		
		editor = UE.getEditor('content');//获取编辑器对象
		editor.ready(function(){//初始化方法
			editor.setHeight(100);
		});
		editor.addListener("blur",function(ue){
			if(editor.getContentTxt()==""){
				$('#remarkShadowDiv').show();
				$('#uEditorDiv').hide();
			}
		});
		

// 	}catch(e){
		
// 	}
		
		
    //隐藏签章控件 《 电子签名 》按钮   因为在pc端不能进行手写签批
	$(".XsealHandWrite").hide();
	//隐藏会签控件  《电子签名》按钮   因为在pc端不能进行手写签批
    $(".XFeedBackSealHandWrite").hide();
}

function reload(){
	//top.$.jBox.tip("正在加载","loading");
	
	if(!loadHandlerInfo()){
		return;
	}
	loadOperations();
	/**
	 *获取会签意见 以及 会签附件等
	 */
	loadFeedBack();
	//initListView();
	//获取会签控件数据
	loadCtrlFeedbackDatas();
	/**
	 *获取公共附件
	 */
	loadWorkFlowAttach();
	//LoadSignData(); 签章数据回显
	
	LoadMobileSignData();
	LoadMobileHwData();
	LoadH5HwData();

	//获取相关数据数量
	loadRunRelDatas();
	
	//加载上传控件
	LoadXUploadCtrl();
	
	//加载图片控件
	LoadXImgCtrl();
	
	//将表单DIV渲染到最上层
	top.FORMDIV = $("#formDiv");
	//top.$.jBox.tip("加载完毕","info");
	$("[clazz=MOBILE]").hide();
	
	if(openKinggrid==1){
		Signature.loadSignatures(signatures);
	}
	
	//处理附件宏标记
	handleMacroAttach();
}


//处理附件宏标记
function handleMacroAttach(){
	var macroatt=$(".macroatt");
	if(macroatt.length>0){
		for(var i=0;i<macroatt.length;i++){
			var att={};
			att["fileName"]=$(macroatt[i]).attr("filename");
			att["ext"]=$(macroatt[i]).attr("ext");
			att["sid"]=$(macroatt[i]).attr("sid");
			att["priv"]=3;
			
			var attachElement = tools.getAttachElement(att,{});
			//attachElement.append("&nbsp;&nbsp;<span style=color:#0080ff;font-size:12px>("+$(macroatt[i]).attr("sizedesc")+")&nbsp;&nbsp;"+$(macroatt[i]).attr("username")+"&nbsp;&nbsp;"+$(macroatt[i]).attr("createtimedesc")+"</span>");
			$(macroatt[i]).html(attachElement);
		}
	}
}

//加载移动签批图片
function LoadH5HwData(){
	for(var i=0;i<h5HwArray.length;i++){
		var itemId = h5HwArray[i];
		var targetItem = $("#"+itemId);
		
		var targetObject = $("#H5_HW_POS_IMG_"+itemId);
		if(targetObject.length==0){
			targetObject = $("<img id=\"H5_HW_POS_IMG_"+itemId+"\" target=\""+itemId+"\" height=\""+targetItem.attr("h")+"\" onerror=\"this.style.display='none';\" width=\""+targetItem.attr("w")+"\"/>").appendTo($("#H5_HW_POS_"+itemId));
		}
		targetObject.attr("src",h5HwStores[i]).show();
		targetObject.css({height:targetItem.attr("h"),width:targetItem.attr("w")});
		
		var float = targetItem.attr("float");
		if(float=="0"){
			$("#H5_HW_POS_"+itemId).css({position:"inherit"});
			targetObject.css({position:"inherit",maxWidth:"100%"});
		}
		
		if(h5HwStores[i]==""){
			targetObject.hide();
		}
		
		if(typeof($("#"+itemId).attr("writable"))!="undefined"){
			try{
				  //加入下拉菜单
				  targetObject.TeeMenu({
					  menuData:[{name:"删除",action:function(itemId,obj){
						  if(window.confirm("是否删除该签章？")){
							  $("#"+itemId).val("");
							  obj.remove();
						  }
						} ,extData:[itemId,targetObject]}],eventPosition:true
				  });
			  }catch(e){
				  
			  }
		}
	}
}


function addH5HandSeal(itemId){
	  var IM_OA;
	  try{
	      IM_OA = window.external.IM_OA;
	  }catch(e){}
		var url = contextPath+"/system/core/workflow/flowrun/prcs/handSeals.jsp?itemId="+itemId;
	  if(window.showModelDialog || IM_OA){
	  	  dialogChangesize(url, 760, 400);
	    }else{
	  	  openWindow(url,"H5手写签批", 760, 400);
	    }
}

function doAddH5Seal(itemId,sealData){
	
	if(itemId.indexOf("HAND_")!=-1){
		itemId = itemId.replace("HAND_","");
		var obj = $("#"+itemId);
		obj.focus();
		var h5float = obj.attr("h5float");
		var h5w = obj.attr("h5w");
		var h5h = obj.attr("h5h");
		
		var targetObject = $("#CTRL_H5SEAL_IMG_"+itemId);
		  if(targetObject.length==0){
			  targetObject = $("<img onerror=\"this.style.display='none';\" id=\"CTRL_H5SEAL_IMG_"+itemId+"\" target=\""+itemId+"\" />");
		  }
		  
		  targetObject.css({height:h5h,width:h5w});
		  
		  if(h5float=="0"){
			  $(".SIGN_POS_CTRL_"+itemId).before(targetObject);
			  targetObject.css({maxWidth:"100%"});
		  }else{
			  targetObject.appendTo($(".SIGN_POS_CTRL_"+itemId));
			  targetObject.css({"position":"absolute",left:0,top:0}).show();
			  
			  try{
				  targetObject.draggable({
					  stop: function() {
						  var target = $("#CTRL_H5_DATA_INPUT_"+$(this).attr("target"));
						  var sp = target.val().split(",");
						  sp[3] = $(this).position().left;
						  sp[4] = $(this).position().top;
						  target.val(sp.join(","));
					  }
				  });
			  }catch(e){
				  
			  }
		  }
		  
		  try{
			//加入下拉菜单
			  targetObject.TeeMenu({
				  menuData:[{name:"删除",action:function(itemId,obj){
					  if(window.confirm("是否删除？")){
						  $("#CTRL_H5_DATA_INPUT_"+itemId).val("");
						  obj.remove();
					  }
					} ,extData:[itemId,targetObject]}],eventPosition:true
			  });
		  }catch(e){}
		  
		  
		  var ctrl = $("#CTRL_H5_DATA_INPUT_"+itemId);
		  ctrl.val(sealData+",_,0,0");
		  
		  targetObject.attr("src",sealData);
		
	}else{

		var targetObject = $("#H5_HW_POS_IMG_"+itemId);
		var itemObj = $("#"+itemId);
		itemObj.focus();
		var width = parseInt(itemObj.attr("w"));
		var height = parseInt(itemObj.attr("h"));
		if(targetObject.length==0){
			targetObject = $("<img onerror=\"this.style.display='none';\" id=\"H5_HW_POS_IMG_"+itemId+"\" height=\""+height+"px\" width=\""+width+"px\"/>").appendTo($("#H5_HW_POS_"+itemId));
		}else{
			targetObject.css({height:height,width:width});
		}
		
		var float = itemObj.attr("float");
		if(float=="0"){
			$("#H5_HW_POS_"+itemId).css({position:"inherit"});
			targetObject.css({position:"inherit",maxWidth:"100%"});
		}
		
		try{
		  //加入下拉菜单
		  targetObject.TeeMenu({
			  menuData:[{name:"删除",action:function(itemId,obj){
				  if(window.confirm("是否删除该签章？")){
					  $("#"+itemId).val("");
					  obj.remove();
				  }
				} ,extData:[itemId,targetObject]}],eventPosition:true
		  });
		  }catch(e){
			  
		  }
		targetObject.attr("src",sealData).show();
		$("#"+itemId).val(sealData);
	}
}

//下载excel模版
function downloadExcelTemplate(itemId){
	$("#frame007").attr("src",contextPath+"/flowRun/downloadXlistTemplate.action?itemId="+itemId);
}

//加载上传控件
function LoadXUploadCtrl(){
	var xuploadCtrls = $("input[xtype=xupload]");
	for(var i=0;i<xuploadCtrls.length;i++){
		//var writable = xuploadCtrls[i].getAttribute("writable");//是否可写
		var id = xuploadCtrls[i].getAttribute("id");
		var itemId = id.split("_")[1];
		var val = xuploadCtrls[i].getAttribute("value");
		var priv = xuploadCtrls[i].getAttribute("priv");
		
		//渲染附件
		if((priv&16)==16){
			
			//priv = 1+2+4+8+16+32+64+128;
			$("#xuploadDiv"+itemId).after("<span id=\"uploadHolder"+itemId+"\" style='font-size:12px;color:blue' class=\"add_swfupload\"><img src=\""+systemImagePath+"/upload/batch_upload.png\"/>上传附件</span>");
			
			//多附件SWF上传组件
			new TeeSWFUpload({
				fileContainer:"xuploadDiv"+itemId,//文件列表容器
				uploadHolder:"uploadHolder"+itemId,//上传按钮放置容器
				valuesHolder:id,//附件主键返回值容器，是个input
				queueComplele:function(){//队列上传成功回调函数，可有可无
					
				},
				renderFiles:true,//渲染附件
				post_params:{model:"workFlowUploadCtrl",modelId:itemId+"_"+runId}//后台传入值，model为模块标志
				});
		}
		if(val!="" && val!=null && val!=undefined){
			var attach = val.split("|");
			for(var j=0;j<attach.length;j++){
				var item = attach[j].split("^");
				var attachObj = tools.getAttachElement({fileName:item[1],ext:item[3],priv:priv,sid:item[0]});
				$("#xuploadDiv"+itemId).append(attachObj);
			}
		}
		
	}
	
	
	
	/*var xuploadCtrls = $("input[xtype=xupload]");
	for(var i=0;i<xuploadCtrls.length;i++){
		var writable = xuploadCtrls[i].getAttribute("writable");//是否可写
		var id = xuploadCtrls[i].getAttribute("id");
		var itemId = id.split("_")[1];
		var val = xuploadCtrls[i].getAttribute("value");
		var priv = 2;
		//渲染附件
		if(writable=="writable"){
			priv = 1+2+4+8+16+32+64+128;
			$("#xuploadDiv"+itemId).after("<span id=\"uploadHolder"+itemId+"\" style='font-size:12px;color:blue' class=\"add_swfupload\"><img src=\""+systemImagePath+"/upload/batch_upload.png\"/>上传附件</span>");
			
			//多附件SWF上传组件
			new TeeSWFUpload({
				fileContainer:"xuploadDiv"+itemId,//文件列表容器
				uploadHolder:"uploadHolder"+itemId,//上传按钮放置容器
				valuesHolder:id,//附件主键返回值容器，是个input
				queueComplele:function(){//队列上传成功回调函数，可有可无
					
				},
				renderFiles:true,//渲染附件
				post_params:{model:"workFlowUploadCtrl",modelId:itemId+"_"+runId}//后台传入值，model为模块标志
				});
		}
		
		
		
		if(val!="" && val!=null && val!=undefined){
			var attach = val.split("|");
			for(var j=0;j<attach.length;j++){
				var item = attach[j].split("^");
				var attachObj = tools.getAttachElement({fileName:item[1],ext:item[3],priv:priv,sid:item[0]});
				$("#xuploadDiv"+itemId).append(attachObj);
			}
		}
		
	}*/
}

//加载图片控件
function LoadXImgCtrl(){
	var xuploadCtrls = $("input[xtype=ximg]");
	for(var i=0;i<xuploadCtrls.length;i++){
		var writable = xuploadCtrls[i].getAttribute("writable");//是否可写
		var id = xuploadCtrls[i].getAttribute("id");
		var itemId = id.split("_")[1];
		var val = xuploadCtrls[i].getAttribute("value");
		
		//渲染附件
		if(writable=="writable"){
			//多附件SWF上传组件
			new TeeSWFUpload({
				fileContainer:"xuploadDivTmp",//文件列表容器
				uploadHolder:"ximguploader"+itemId,//上传按钮放置容器
				valuesHolder:id+"11",//附件主键返回值容器，是个input
				file_types:"*.jpg;*.png;*.gif;*.bmp",
				itemId:itemId,
				id:id,
				quickUpload:true,//快速上传
				uploadSuccess:function(file,customSettings){//队列上传成功回调函数，可有可无
					$("#"+customSettings.settings.id).val(file.sid);
					$("#img"+customSettings.settings.itemId).attr("src",contextPath+"/attachmentController/downFile.action?id="+file.sid);
					top.$.jBox.tip("图片上传成功","success");
				},
				uploadStart:function(){
					top.$.jBox.tip("正在上传图片…","loading");
				},
				renderFiles:false,//渲染附件
				post_params:{model:"imgupload",modelId:runId}//后台传入值，model为模块标志
				});
		}
		
	}
}

//加载移动签章
function LoadMobileSignData(){
	for(var i=0;i<mobileSignArray.length;i++){
		var itemId = mobileSignArray[i];
		var datas = [];
		var validFields = mobileSignJson[itemId];
		if(validFields!=""){
			var sp = validFields.split(",");
			for(var j=0;j<sp.length;j++){
				datas.push($("#"+sp[j]).val());
			}
		}
		var md5Code = MD5(datas.join(""));
		var sealDatas = $("#"+itemId).val().split(",");
		
		var targetObject = $("#MOBILE_SEAL_IMG_"+itemId);
		if(targetObject.length==0){
		 targetObject = $("<img id=\"MOBILE_SEAL_IMG_"+itemId+"\" target=\""+itemId+"\" onerror=\"this.style.display = 'none'\"/>").appendTo($("#MOBILE_SIGN_POS_"+itemId));
		}
		targetObject.attr("src",sealDatas[0]+","+sealDatas[1]).css({"position":"absolute",left:sealDatas[3]+"px",top:sealDatas[4]+"px"});
		
		if(md5Code!=sealDatas[2]){//验章失效，颜色变浅
			grayscale(targetObject);
		}else{
			if(typeof($("#"+itemId).attr("writable"))!="undefined"){
				try{
					  targetObject.draggable({
						  stop: function() {
							  var target = $("#"+$(this).attr("target"));
							  var sp = target.val().split(",");
							  sp[3] = $(this).position().left;
							  sp[4] = $(this).position().top;
							  target.val(sp.join(","));
						  }
					  });
					  
					  //加入下拉菜单
					  targetObject.TeeMenu({
						  menuData:[{name:"删除",action:function(itemId,obj){
							  if(window.confirm("是否删除该签章？")){
								  $("#"+itemId).val("");
								  obj.remove();
							  }
							} ,extData:[itemId,targetObject]}],eventPosition:true
					  });
				  }catch(e){
					  
				  }
			}
		}
	}
}


//加载移动签批图片
function LoadMobileHwData(){
	for(var i=0;i<mobileHwArray.length;i++){
		var itemId = mobileHwArray[i];
		
		var targetObject = $("#MOBILE_HW_POS_IMG_"+itemId);
		
		if(targetObject.length==0){
		 targetObject = $("<img id=\"MOBILE_HW_POS_IMG_"+itemId+"\" onerror=\"this.style.display = 'none'\"/>").appendTo($("#MOBILE_HW_POS_"+itemId));
		}
		targetObject.attr("src",mobileHwStores[i]).css({"position":"absolute"}).show();
		
		var float = $("#"+itemId).attr("float");
		if(float=="0"){
			$("#MOBILE_HW_POS_"+itemId).css({position:"inherit"});
			targetObject.css({position:"inherit"});
		}
		
		try{
			  //加入下拉菜单
			  targetObject.TeeMenu({
				  menuData:[{name:"删除",action:function(itemId,obj){
					  if(window.confirm("是否删除该签章？")){
						  $("#"+itemId).val("");
						  obj.remove();
					  }
					} ,extData:[itemId,targetObject]}],eventPosition:true
			  });
		  }catch(e){
			  
		  }
	}
}

function loadRunRelDatas(){
	var json = tools.requestJsonRs(contextPath+"/runRel/getDataCounts.action?runId="+runId,{});
	top.$("#schedule_count").html(json.rtData.scheduleCount);
	top.$("#task_count").html(json.rtData.taskCount);
	top.$("#customer_count").html(json.rtData.customerCount);
	top.$("#run_count").html(json.rtData.runCount);
}

//移动签章盖章操作
function picCtrlSeal(ctrlName){
	dialog0(contextPath+"/system/core/workflow/flowrun/prcs/mobileSealsCtrl.jsp?itemId="+ctrlName,800,600);
}

/**
 * 签章格式事例   data;image/png;base64,签章数据,表单数据MD5加密,左边距,上边据
 */

window.doAddMobileSealCtrl=function(ctrlName,sealData){
	  var targetObject = $("#CTRL_PICSEAL_IMG_"+ctrlName);
	  if(targetObject.length==0){
		  targetObject = $("<img onerror=\"this.style.display='none';\" id=\"CTRL_PICSEAL_IMG_"+ctrlName+"\" target=\""+ctrlName+"\" />").appendTo($(".SIGN_POS_CTRL_"+ctrlName));
	  }
	  targetObject.css({opacity:1,"position":"absolute",left:0,top:0}).show();
	  try{
		  targetObject.draggable({
			  stop: function() {
				  var target = $("#CTRL_SIGN_DATA_INPUT_"+$(this).attr("target"));
				  var sp = target.val().split(",");
				  sp[3] = $(this).position().left;
				  sp[4] = $(this).position().top;
				  target.val(sp.join(","));
			  }
		  });
		  
		//加入下拉菜单
		  targetObject.TeeMenu({
			  menuData:[{name:"删除",action:function(itemId,obj){
				  if(window.confirm("是否删除该签章？")){
					  $("#CTRL_SIGN_DATA_INPUT_"+itemId).val("");
					  obj.remove();
				  }
				} ,extData:[ctrlName,targetObject]}],eventPosition:true
		  });
	  }catch(e){
		  
	  }
	  
	  var ctrl = $("#CTRL_SIGN_DATA_INPUT_"+ctrlName);
	  ctrl.val(sealData+",_,0,0");
	  
	  targetObject.attr("src",sealData);
}


function loadCtrlFeedbackDatas(){
	try{
		for(var i=0;i<ctrlSignArray.length;i++){
			var item = ctrlSignArray[i];
			var itemId = item.replace("DATA_","");
			var template = $("#"+item).attr("template");
			template = tools.unicode2String(template);
			template = template.replace("{C}","<span id=\"FEEDBACK_CTRL_DEMO_CONTENT_"+item+"\"></span>")
			.replace("{U}",userName)
			.replace("{D}",deptName)
			.replace("{DD}",deptFullName)
			.replace("{R}",roleName)
			.replace("{T}",new Date().pattern("yyyy-MM-dd HH:mm"))
			.replace("{O}","")
			.replace("{P}","<span id=\"SIGN_POS_CTRL_"+item+"_"+rand+"\" class='SIGN_POS_CTRL_"+item+"' style='position:absolute;color:gray'>{章}</span>");
			$("#"+item).before("<span id=\"FEEDBACK_CTRL_DEMO_"+item+"\" style='display:none'>"+template+"</span>");
		}
	
		
		/*for(var i=0;i<ctrlRandArray.length;i++){
			
			var name = ctrlRandArray[i];
			
			var ctrlSign = $("#CTRL_SIGN_"+name).val();
			
			
			//取到对应的签章数据
			var tmpObj = eval("window.SignObj_"+name);
			if(ctrlSign!=""){
				tmpObj = CreateWebSignObject("SignObj_"+name, undefined, "",  undefined, "", undefined, "");
				
				//设置位置
				tmpObj.SetOffsetPos("SIGN_POS_CTRL_"+name, 0, 0);
				
				var ret = tmpObj.Verify(" ", ctrlSign);//原文 签章值
				
			}
		}*/
	}catch(e){}
	
	//加载会签图章
	for(var i=0;i<ctrlRandArray.length;i++){
		var name = ctrlRandArray[i];
		var picData = $("#CTRL_PIC_SIGN_"+name).val();
		$("#SIGN_POS_CTRL_"+name).css({position:"absolute"});
		if(picData!=""){
			//加载图章
			var targetObject = $("#CTRL_PICSEAL_IMG_"+name);
			var sealDatas = picData.split(",");
			if(targetObject.length==0){
			 targetObject = $("<img id=\"CTRL_PICSEAL_IMG_"+name+"\" target=\""+name+"\" onerror=\"this.style.display = 'none'\"/>").appendTo($("#SIGN_POS_CTRL_"+name));
			}
			targetObject.attr("src",sealDatas[0]+","+sealDatas[1]).css({"position":"absolute",left:sealDatas[3]+"px",top:sealDatas[4]+"px"});
			
			//加入下拉菜单
			  targetObject.TeeMenu({
				  menuData:[{name:"隐藏",action:function(itemId,obj){
					  obj.remove();
					} ,extData:[name,targetObject]}],eventPosition:true
			  });
		}
	}
	
	//加载H5手写图片
	for(var i=0;i<ctrlRandArray.length;i++){
		var name = ctrlRandArray[i];
		var sp = name.split("_");
		var obj = $("#DATA_"+sp[1]);
		var h5float = obj.attr("h5float");
		var h5w = obj.attr("h5w");
		var h5h = obj.attr("h5h");
		
		var h5Data = $("#CTRL_H5HAND_"+name).val();
		$("#SIGN_POS_CTRL_"+name).css({position:"absolute"});
		if(h5Data!=""){
			//加载图章
			var targetObject = $("#CTRL_H5SEAL_IMG_"+name);
			var sealDatas = h5Data.split(",");
			if(targetObject.length==0){
			 targetObject = $("<img id=\"CTRL_H5SEAL_IMG_"+name+"\" target=\""+name+"\" onerror=\"this.style.display = 'none'\"/>").appendTo($("#SIGN_POS_CTRL_"+name));
			}
			targetObject.attr("src",sealDatas[0]+","+sealDatas[1]).css({width:h5w,height:h5h});
			
			if(h5float=="0"){
				$("#SIGN_POS_CTRL_"+name).before(targetObject);
				targetObject.css({maxWidth:"100%"});
			}else{
				targetObject.appendTo($("#SIGN_POS_CTRL_"+name));
				targetObject.css({"position":"absolute",left:sealDatas[3]+"px",top:sealDatas[4]+"px"});
			}
			
			//加入下拉菜单
			  targetObject.TeeMenu({
				  menuData:[{name:"隐藏",action:function(itemId,obj){
					  obj.remove();
					} ,extData:[name,targetObject]}],eventPosition:true
			  });
		}
	}
	
	
	
	//加载移动手写签批图
	for(var i=0;i<ctrlRandArray.length;i++){
		var name = ctrlRandArray[i];
		var sp = name.split("_");
		var obj = $("#DATA_"+sp[1]);
		var mobifloat = obj.attr("mobifloat");
		var mobiw = obj.attr("mobiw");
		var mobih = obj.attr("mobih");
		
		var mobiData = $("#CTRL_MOBIHAND_"+name).val();
		$("#SIGN_POS_CTRL_"+name).css({position:"absolute"});
		if(mobiData!=""){
			//加载图章
			var targetObject = $("#CTRL_MOBISEAL_IMG_"+name);
			if(targetObject.length==0){
			 targetObject = $("<img id=\"CTRL_MOBISEAL_IMG_"+name+"\" target=\""+name+"\" style=\"height:"+mobih+"px;width:"+mobiw+"px\" onerror=\"this.style.display = 'none'\"/>").appendTo($("#SIGN_POS_CTRL_"+name));
			}
			targetObject.attr("src",mobiData);
			
			if(mobifloat=="0"){
				$("#SIGN_POS_CTRL_"+name).before(targetObject);
				targetObject.css({maxWidth:"100%"});
			}else{
				targetObject.appendTo($("#SIGN_POS_CTRL_"+name));
				targetObject.css({"position":"absolute",left:"0px",top:"0px"});
			}
			
			//加入下拉菜单
			  targetObject.TeeMenu({
				  menuData:[{name:"隐藏",action:function(itemId,obj){
					  obj.remove();
					} ,extData:[name,targetObject]}],eventPosition:true
			  });
		}
	}
}

//加载处理数据
function loadHandlerInfo(){
	var url = contextPath+"/flowRun/getHandlerData.action";
	var json = tools.requestJsonRs(url,{runId:runId,frpSid:frpSid});
	if(json.rtState){
		$("#formDiv").html(json.rtData.form+"<script>"+json.rtData.script+"<\/script><style>"+json.rtData.css+"<\/style>");
		var afterRenderedScript = $("<script>"+json.rtData.afterRenderedScript+"<\/script>");
		if(json.rtData.afterRenderedScript){
			$("#formDiv").append(afterRenderedScript);
		}
		
		$(top.child_count).html(json.rtData.childCount);
		flowType = json.rtData.flowType;
		formValidModel = eval("("+json.rtData.formValidModel+")");//表单校验模版
		if(loadFirst){
			initialize();
			loadFirst = false;
		}
		return true;
	}else{
		messageMsg(json.rtMsg,"center-container","error");
		return false;
	}
}

//加载操作按钮
function loadOperations(){
	var url = contextPath+"/flowRun/getHandlerOptPrivData.action";
	var json = tools.requestJsonRs(url,{runId:runId,frpSid:frpSid});
	if(json.rtState){
		//加载按钮
		$(parent.saveBtn).show();
		
		//加载流程标题
		runName = json.rtData.runName;
		top.runName = json.rtData.runName;
		level = json.rtData.level;
		if(json.rtData.flowPrcs && json.rtData.flowPrcs.runNamePriv==1){//允许修改流程名称
			$(parent.runNameDiv).html("&nbsp;&nbsp;流水号："+runId+"&nbsp;&nbsp;"+"<input id='runNameInput' type='text' class='BigInput' value='"+runName+"' style='width:400px'/>");
		}else{
			$(parent.runNameDiv).html("&nbsp;&nbsp;流水号："+runId+"&nbsp;&nbsp;"+runName);
		}
		
		if(json.rtData.turnState && json.rtData.turnState == 1){
			if(json.rtData.flowPrcs){//固定流程专用
				var prcsEventDef = json.rtData.flowPrcs.prcsEventDef;
				if(prcsEventDef!="" && prcsEventDef!=null){
					var sp = prcsEventDef.split(",");
					var render = [];
					for(var j=0;j<sp.length;j++){
						render.push("<button id=\"turnNextBtn\" class=\"btn-win-white\" style=\"margin-right:10px;\" onclick=\"turnNext('"+sp[j]+"')\">"+sp[j]+"</button>");
					}
					$(parent.prcsEventDefSpan).html(render.join(""));
				}else{
					$(parent.turnNextBtn).show();
				}
			}else{
				$(parent.turnNextBtn).show();
			}
		}else if(json.rtData.turnState && json.rtData.turnState == 2){//countersignHandler
			$(parent.prcsFinishBtn).show();
		}else{
			top.$.jBox.tip("子流程需办理完成后主流程方可继续流转","info");
		}
		parent.hasChildUnhandledFlow = json.rtData.hasChildUnhandledFlow;
		if(json.rtData.turnBack == 1){
			$(parent.backToBtn).show();
		}else if(json.rtData.turnBack == 2){
			$(parent.backToOtherBtn).show();
		}else if(json.rtData.turnBack == 3){
			$(parent.backToFixedBtn).show();
		}
		
		if(json.rtData.turnEndId){
			window.turnEndId = json.rtData.turnEndId;
			$(parent.turnEndIdBtn).show();
		}
		
		if(json.rtData.turnEnd && json.rtData.turnEnd == 1){
			$(parent.endBtn).show();
		}
		frpModel = json.rtData.flowRunPrcs;

		top.$("#level").val(level);
		if(frpModel.prcsId==1){//如果是第一步，则允许修改流程级别
			top.$("#level").removeAttr("disabled");
		}
		
		//判断经办与主办
		if(frpModel.topFlag==0){
			$(top.prcsUserBtn).css({opacity:0.2});
			$(top.prcsUserBtn)[0].onclick=function(){
				return;
			};
		}
		
		flowPrcs = json.rtData.flowPrcs;
		if(flowPrcs && flowPrcs!=null){
			officePriv = flowPrcs.officePriv;
	    }
		top.officePriv = officePriv;
	    
		window.archivesPriv = 1;
		if(flowPrcs!=null){
			feedback = flowPrcs.feedback;
			attachPriv = flowPrcs.attachPriv;
			archivesPriv = flowPrcs.archivesPriv;
			attachOtherPriv = flowPrcs.attachOtherPriv;
		}
		
		//归档按钮显示
		if(archivesPriv==0){
			top.$("#archivesBtn")[0].onclick=function(){};
			top.$("#archivesBtn").css({opacity:0.5});
			top.$("#repeatBtn")[0].onclick=function(){};
			top.$("#repeatBtn").css({opacity:0.5});
		}
		
		if(feedback==0){//禁止会签
			$("#feedbackDiv").hide();
			$("#feedbackSealDiv").hide();
			$("#feedbackAttachDiv").hide();
			$("#feedbackSealBtn").hide();
		}else if(feedback==1 || feedback==2){//允许或强制会签
			$("#feedbackDiv").show();
			$("#feedbackSealDiv").show();
			$("#feedbackAttachDiv").show();
			$("#feedbackSealBtn").show();
		}

		if(flowType.attachPriv==0){//禁止公共附件
			$("#pulicAttachmentForm").hide();
			parent.$("#attach").hide();
		}else{//允许公共附件
			$("#pulicAttachmentForm").show();
		}
		
		if(flowType.feedbackPriv==0){//禁止会签
			$("#feedbackTitleDiv").hide();
			parent.$("#feedback").hide();
		}else{//允许会签
			$("#feedbackTitleDiv").show();
		}

		//附件权限控制 ---- 上传权限
		if((attachPriv&16)==16){
			$("#newAttachTR").show();
			$("#addAttachTR").show();
		}else{
			$("#newAttachTR").hide();
			$("#addAttachTR").hide();
		}

		//判断是否显示签批单页签
		var isExistsAipTemplate=json.rtData.isExistsAipTemplate;
		if(isExistsAipTemplate=="1"||isExistsAipTemplate==1){//存在相关的签批单
			$(top.aipForm).show();
		}else{
			$(top.aipForm).hide();
		}
		
		
		//判断officePriv
		if((flowType.hasDoc==1||flowType.hasDoc==3)&&((officePriv & 1)==1)){//查看正文权限
			$(top.flowRunDoc).show();
		}else{
			$(top.flowRunDoc).hide();
		}
		
		if((flowType.hasDoc==2||flowType.hasDoc==3)&&((officePriv & 1)==1)){//版式正文查看权限
			$(top.flowRunDocAip).show();
		}else{
			$(top.flowRunDocAip).hide();
		}
		
		if(flowType.delegate==0){
			$(top.delegateBtn).css({opacity:0.2});
			$(top.delegateBtn)[0].onclick=function(){
				return;
			};
		}
		
		//启动手写签章按钮 绑定时间
		$("#startHandWrite").bind("click",function(){
			var hwValue = $("#startHandWrite").attr("checked");
			if(hwValue && hwValue == "checked"){
				$("#signTR").show();
			}else{
				$("#signTR").hide();
			}
		});
	    return true;
	}else{
		//alert(json.rtMsg);
		return false;
	}
}

//当前步骤是否进行过会签
function hasFeedback(){
	var url = contextPath+"/feedBack/hasFeedback.action";
	var json = tools.requestJsonRs(url,{frpSid:frpSid});
	if(json.rtState){
		return json.rtData;
	}
	return false;
}

/**
 * 删除会签意见 zhp
 */
function delFeedBack(id){
	if(window.confirm("删除该会签意见，会级联删除其下的回复意见，是否确认删除？")){
		var url = contextPath+"/feedBack/deleteFeedBack.action";
		var para = {};
		para['fid'] = id;
		var json = tools.requestJsonRs(url,para);
		if(json.rtState){
			loadFeedBack();
		}else{
			alert(json.rtMsg);
		}
	}
}

/**
 * 表单校验
 /**
	 * 
	 * itemId 控件ID
	 * tType 1：输入值  2：表单字段
	 * oType 1: 字符串  2：数字  3：日期
	 * oper: 
	 * <option value="1">等于</option>
			<option value="2">不等于</option>
			<option value="3">大于</option>
			<option value="4">大于等于</option>
			<option value="5">小于</option>
			<option value="6">小于等于</option>
			<option value="7">以字符开头</option>
			<option value="8">以字符结尾</option>
			<option value="9">包含</option>
			<option value="10">不包含</option>
			
	 * val: 值
	 * info: 提示信息
	 * [{itemId:'5',type:'1',tType:'',oType:'',oper:'',val:'',info:'提示信息'}]
	 */
function formValidExec(){
	for(var i=0;i<formValidModel.length;i++){
		var validItem = formValidModel[i];//获取校验项目
		var val1 = $("#DATA_"+validItem.itemId).val();//对比数据1
		var val2;//对比数据2
		if(validItem.tType=="1"){//输入值
			val2 = validItem.val;
		}else{
			val2 = $("#DATA_"+validItem.val).val();
		}
		var info = validItem.info;
		var oper = validItem.oper;
		if(validItem.oType=="1"){//字符串
			if(oper=="1"){//等于
				if(val1==val2){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="2"){//不等于
				if(val1!=val2){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="3"){//大于
				if(val1>val2){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="4"){//大于等于
				if(val1>=val2){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="5"){//小于
				if(val1<val2){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="6"){//小于等于
				if(val1<=val2){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="7"){//以字符开头
				if(val1.StartWith(val2)){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="8"){//以字符结尾
				if(val1.EndWith(val2)){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="9"){//包含
				if(val1.indexOf(val2)!=-1){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="10"){//不包含
				if(val1.indexOf(val2)==-1){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}
		}else if(validItem.oType=="2"){//数字
			if(oper=="1"){//等于
				if(Number(xParseNumber(val1))==Number(xParseNumber(val2))){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="2"){//不等于
				if(Number(xParseNumber(val1))!=Number(xParseNumber(val2))){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="3"){//大于
				if(Number(xParseNumber(val1))>Number(xParseNumber(val2))){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="4"){//大于等于
				if(Number(xParseNumber(val1))>=Number(xParseNumber(val2))){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="5"){//小于
				if(Number(xParseNumber(val1))<Number(xParseNumber(val2))){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="6"){//小于等于
				if(Number(xParseNumber(val1))<=Number(xParseNumber(val2))){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="7"){//以字符开头
				if(val1.StartWith(val2)){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="8"){//以字符结尾
				if(val1.EndWith(val2)){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="9"){//包含
				if(val1.indexOf(val2)!=-1){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="10"){//不包含
				if(val1.indexOf(val2)==-1){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}
		}else if(validItem.oType=="3"){//日期
			if(oper=="1"){//等于
				if(strDate2Date(val1).getTime()==strDate2Date(val2).getTime()){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="2"){//不等于
				if(strDate2Date(val1).getTime()!=strDate2Date(val2).getTime()){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="3"){//大于
				if(strDate2Date(val1).getTime()>strDate2Date(val2).getTime()){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="4"){//大于等于
				if(strDate2Date(val1).getTime()>=strDate2Date(val2).getTime()){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="5"){//小于
				if(strDate2Date(val1).getTime()<strDate2Date(val2).getTime()){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="6"){//小于等于
				if(strDate2Date(val1).getTime()<=strDate2Date(val2).getTime()){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="7"){//以字符开头
				if(val1.StartWith(val2)){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="8"){//以字符结尾
				if(val1.EndWith(val2)){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="9"){//包含
				if(val1.indexOf(val2)!=-1){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}else if(oper=="10"){//不包含
				if(val1.indexOf(val2)==-1){
					$("#DATA_"+validItem.itemId).focus();
					top.$.jBox.tip(info,"error");
					return false;
				}
			}
		}
	}
	return true;
}

function saveFlowRunData(flag,type,parentCallback,validation){
	//组合列表数据控件
	xCalculating();
	xlistCalculating();
	
	return save(flag,type,parentCallback,validation);
}

function save(flag,type,parentCallback,validation){
	if(type==1){//转交  保存
		if(validation && !formValidExec()){
			return;
		}
		
		var validateList = listViewPreSaving($("#formDiv"));
		if(validation && validateList==false){
			return;
		}

		//控件校验
		if(validation && !ctrlValidate("#form")){
			return false;
		}
	}
	
	
	if(feedback==2){//强制会签
		if(validation && hasFeedback()==false){
			if(editor.getContent()==""){
				top.$.jBox.tip("该步骤为强制会签，请填写会签意见","info");
				return;
			}
		}
	}
	
	


	top.$.jBox.tip("正在提交数据","loading");
		
	//获取流程标题控件，反向保存流程标题
	$("[save_run_name][writable]").each(function(i,obj){
		tools.requestJsonRs(contextPath+"/flowRun/updateRunName.action",{runId:runId,runName:obj.value});
	});

	var url = contextPath+"/flowRun/saveFlowRunData.action";
	var para = tools.formToJson($("#form"));
	para["runId"] = runId;
	para["frpSid"] = frpSid;
	para["flowId"] = flowId;

	//处理含有ckeditor的textarea
	var txts = $("textarea[disabled!=disabled][rich=1][ck][xtype='xtextarea']");
	for(var i=0;i<txts.length;i++){
		var tmp = $(txts[i]);
		var ckInstance = eval("window."+tmp.attr("name")+"_CK");
		para[tmp.attr("name")] = ckInstance.getContent();
	}
	
	tools.requestJsonRs(url,para,true,function(json){
		if(!json.rtState){
			top.$.jBox.tip(json.rtMsg,"error");
			return;
		}
		
		//xsy表单控件会签意见保存
		$("textarea[xtype=xfeedback]").each(function(i,obj){
			var item = $(obj).attr("id");
			var itemId = item.replace("DATA_","");
			var itemData = $(obj).val();
			var signData = $("#CTRL_SIGN_DATA_INPUT_O_"+item).val();
			var sealData = $("#CTRL_SIGN_DATA_INPUT_I_"+item).val();
			var picData = $("#CTRL_SIGN_DATA_INPUT_"+item).val();
			var h5Data = $("#CTRL_H5_DATA_INPUT_"+item).val();
			var mobiData = $("#CTRL_MOBI_DATA_INPUT_"+item).val();
			
			if(itemData!="" || h5Data!="" || mobiData!="" || signData!="" || sealData!="" || picData!=""){//保存盖章数据
				var url = contextPath+"/flowRun/saveCtrlFeedback.action";
				var json = tools.requestJsonRs(url,{runId:runId,itemId:itemId,content:itemData,rand:rand,signData:signData,sealData:sealData,hwData:"",frpSid:frpSid,picData:picData,h5Data:h5Data,mobiData:mobiData});
			}
		});
		
		
//		for(var i=0;i<ctrlSignArray.length;i++){
//			var item = ctrlSignArray[i];
//			var itemId = ctrlSignArray[i].replace("DATA_","");
//			
//			var itemData = $("#"+item).val();
//			var picData = $("#CTRL_SIGN_DATA_INPUT_"+item).val();
//			var h5Data = $("#CTRL_H5_DATA_INPUT_"+item).val();
//			var mobiData = $("#CTRL_MOBI_DATA_INPUT_"+item).val();
//			var objName_hw = "";
//			var objName_seal = "";
//			var sign_val = "";
//			var seal_val = "";
//			var hw_val = "";
//			try{
//				objName_hw = DWebSignSeal.FindSeal(item+"_hw_"+rand,2);
//			    objName_seal = DWebSignSeal.FindSeal(item+"_seal_"+rand,2);
//			    if(objName_hw=="" && objName_seal==""){
//			    	sign_val = "";
//				}else{
//					sign_val = DWebSignSeal.GetStoreDataEx(item+"_hw_"+rand+";"+item+"_seal_"+rand);
//				}
//			  	//生成gif格式文件，并记录位置
//				if(objName_hw!=""){
//					hw_val = DWebSignSeal.GetSealBmpString(objName_hw,"gif")+";"+DWebSignSeal.GetSealPosX(objName_hw)+","+DWebSignSeal.GetSealPosY(objName_hw);
//				}
//				if(objName_seal!=""){
//					seal_val = DWebSignSeal.GetSealBmpString(objName_seal,"gif")+";"+DWebSignSeal.GetSealPosX(objName_seal)+","+DWebSignSeal.GetSealPosY(objName_seal);
//				}
//			}catch(e){
//				
//			}
//			
//			if(itemData!="" || h5Data!="" || mobiData!=""){//保存盖章数据
//			    //alert("保存会签意见");
//				var url = contextPath+"/flowRun/saveCtrlFeedback.action";
//				var json = tools.requestJsonRs(url,{runId:runId,itemId:itemId,content:itemData,rand:rand,signData:sign_val,sealData:seal_val,hwData:hw_val,frpSid:frpSid,picData:picData,h5Data:h5Data,mobiData:mobiData});
//			}
//		}
		
		submitForm(parentCallback);

	});
	
	
	return true;
}


/**
 * 获取工作流 公共附件 zhp 20131020
 */
function loadWorkFlowAttach(){
	var url = contextPath+"/teeWorkflowAttachmentController/getTeeWorkFlowAttachment.action";
	var para  = {};
	para["runId"] = runId;
	para["frpSid"] = frpSid;
	$("#pulicAttachments").html("");
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		var attachList = json.rtData;
		var attStrHtml = "";
		$(parent.attach_count).html(attachList.length);
		for(var j=0;j<attachList.length;j++){
			var att = attachList[j];
			
				if(att.userId==userId){//是当前登录人自己的附件
					att.priv =attachPriv;
				}else{//不是当前登陆人自己的附件
					if(attachOtherPriv==1){//允许修改
						att.priv =attachPriv;
					}else{//不允许修改    不能编辑删除
						var priv = 0;
						if((attachPriv&1)==1){//查看
							priv=priv+1;
						}
						if((attachPriv&2)==2){//下载
							priv=priv+2;
						}
						att.priv = priv;
					}
					
				}
			
			var attachElement = tools.getAttachElement(att,{});
			attachElement.append("&nbsp;&nbsp;<span style=color:#0080ff;font-size:12px>("+att.sizeDesc+")&nbsp;&nbsp;"+att.userName+"&nbsp;&nbsp;"+att.createTimeDesc+"</span>");
			$("#pulicAttachments").append(attachElement);
		}
	}else{
		alert(json.rtMsg);
	}
}
/**
 *获取会签意见 zhp 20131020
 */
function loadFeedBack(){
	var url = contextPath+"/feedBack/getFeedBackList.action";
	var para  = {};
	para["runId"] = runId;
	para["frpSid"] = frpSid;
	$("#feedBackContest").html("");
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		var fdDate = json.rtData;
		var sHtml = "";
		//$(parent.feedback_count).html(fdDate.length);
		for(var i=0;i<fdDate.length;i++){
			//先渲染第一级 	
			if(fdDate[i].replayId==0){	
				/*var attachList = fdDate[i].attachList;
				sHtml = sHtml + "<div style='font-size:12px'><div style='font-size:12px'><b>第"+fdDate[i].prcsId+"步</b>&nbsp;&nbsp;"+fdDate[i].prcsName+"&nbsp;&nbsp;"+(fdDate[i].backFlag==1?"<img src='/common/images/workflow/goback.png' title='退回步骤'/>":"")+"&nbsp;&nbsp;"+fdDate[i].userName+"&nbsp;&nbsp;"+fdDate[i].editTimeDesc;
				if(fdDate[i].voiceId!="" && fdDate[i].voiceId && fdDate[i].voiceId!=null){
					sHtml = sHtml+"&nbsp;&nbsp;<video type='video/mp3' style=\"cursor:pointer\" onclick='this.play();' src='/attachmentController/downFile.action?id="+fdDate[i].voiceId+"' title=\"点击播放语音\" width='20px' height='20px' poster=\"/common/images/workflow/voice_play.png\"></video>";
				}
				//回复功能
				sHtml+="&nbsp;&nbsp;<a href=\"#\" onclick=\"replyFeedBack("+fdDate[i].sid+")\">回复</a>";
				if(fdDate[i].userId==userId){
					sHtml = sHtml+"&nbsp;&nbsp;<img src='"+systemImagePath+"/upload/remove.png"+"' onclick='delFeedBack("+fdDate[i].sid+")' style='cursor:pointer' title='删除会签意见'/>"
				}
				if(fdDate[i].havaSignData){
					sHtml = sHtml+"&nbsp;&nbsp;<img src='"+lookfeedbackPath+"' onclick='lookSeal("+fdDate[i].sid+")' />"
				}
				sHtml+="</div>";
				sHtml+="<div>"+fdDate[i].content+"</div>";;
				sHtml = sHtml+"</div>";
				$("#feedBackContest").append(sHtml);
				sHtml="";
				var attStrHtml = "";
				for(var j=0;j<attachList.length;j++){
					var att = attachList[j];
					if(att.userId==userId){
						att.priv = 1+2+4+8+16+32;
					}else{
						att.priv = 1+2;
					}
					var attachElement = tools.getAttachElement(att,{params:[fdDate[i].sid,att.sid],deleteEvent:function(attachModel,params){
						var url = contextPath+"/feedBack/deleteFeedBackAttach.action";
						var para = {};
						para['fid'] = params[0];
						para['aid'] = params[1];
						var json = tools.requestJsonRs(url,para);
						if(json.rtState){
							window.location.reload();
						}else{
							alert(json.rtMsg);
						}
					}});
					$("#feedBackContest").append(attachElement);
				}
				$("#feedBackContest").append("<hr/>");*/
				var attachList = fdDate[i].attachList;
				sHtml+="<div style=\"font-size:12px;padding:10px;background:#f2f2f2;border-left:2px solid #176fd1;margin-top:10px;\">";
				sHtml+="<div>"
					 +"<div><span style=\"font-weight:bold;\">第"+fdDate[i].prcsId+"步&nbsp;&nbsp;"+fdDate[i].prcsName+"</span>&nbsp;&nbsp;"+(fdDate[i].backFlag==1?"<img src='/common/images/workflow/goback.png' title='退回步骤'/>":"")+"&nbsp;&nbsp;<span style=\"font-weight:bold;color:#4188d6;\">"+fdDate[i].userName+"</span>";
				if(fdDate[i].voiceId!="" && fdDate[i].voiceId && fdDate[i].voiceId!=null){
					sHtml = sHtml+"&nbsp;&nbsp;<video type='video/mp3' style=\"cursor:pointer\" onclick='this.play();' src='/attachmentController/downFile.action?id="+fdDate[i].voiceId+"' title=\"点击播放语音\" width='20px' height='20px' poster=\"/common/images/workflow/voice_play.png\"></video>";
				}
				sHtml+="</div>";  
				
				sHtml+="<div>"+fdDate[i].content+"</div>";
				sHtml+="<div id=\"attach_"+fdDate[i].sid+"\"></div>";
				
				sHtml+="<div><span style=\"color:#cbcbcb;\">"+fdDate[i].editTimeDesc+"</span>";
				
				if(fdDate[i].userId==userId){
					sHtml+="&nbsp;&nbsp;<a href=\"#\" style=\"cursor:pointer;color:#818181;\" onclick=\"delFeedBack("+fdDate[i].sid+")\">删除</a>";
				}
				sHtml+="&nbsp;&nbsp;<a href=\"#\" style=\"cursor:pointer;color:#818181;\" onclick=\"replyFeedBack("+fdDate[i].sid+")\" >回复</a>";
				sHtml+="</div>";
				sHtml+="</div>";
				
				//渲染回复
				sHtml+=renderFeedBackReply(fdDate,fdDate[i].sid);
				
				sHtml+="</div>";
				
				$("#feedBackContest").append(sHtml);
				sHtml="";
				for(var j=0;j<attachList.length;j++){
					var att = attachList[j];
					if(att.userId==userId){
						att.priv = 1+2+4+8+16+32;
					}else{
						att.priv = 1+2;
					}
					var attachElement = tools.getAttachElement(att,{params:[fdDate[i].sid,att.sid],deleteEvent:function(attachModel,params){
						var url = contextPath+"/feedBack/deleteFeedBackAttach.action";
						var para = {};
						para['fid'] = params[0];
						para['aid'] = params[1];
						var json = tools.requestJsonRs(url,para);
						if(json.rtState){
							window.location.reload();
						}else{
							alert(json.rtMsg);
						}
					}});
					$("#attach_"+fdDate[i].sid).append(attachElement);
				}
			}
			
		}
		
	}else{
		alert(json.rtMsg);
		
	}
}


function renderFeedBackReply(fdDate,pSid){
	var sHtml="";
	for(var i=0;i<fdDate.length;i++){
		if(fdDate[i].replayId==pSid){
			sHtml+="<div style=\"margin-left:20px;border-left:1px dotted #bbbbbb;padding:10px;\">"
				 +"<div>"
				 +"<div><span style=\"font-weight:bold;color:#4188d6;\">"+fdDate[i].userName+"</span>&nbsp;&nbsp;<span style=\"font-weight:bold;\">回复：<span></div>"
				 +"<div>"+fdDate[i].content+"</div>";
			sHtml+="<div><span style=\"color:#cbcbcb;\">"+fdDate[i].editTimeDesc+"</span>";
			if(fdDate[i].userId==userId){
				sHtml+="&nbsp;&nbsp;<a href=\"#\" style=\"cursor:pointer;color:#818181;\" onclick=\"delFeedBack("+fdDate[i].sid+")\">删除</a>";
			}
			sHtml+="&nbsp;&nbsp;<a href=\"#\" style=\"cursor:pointer;color:#818181;\" onclick=\"replyFeedBack("+fdDate[i].sid+")\" >回复</a>";
			
			sHtml+="</div>";
			sHtml+="</div>";
			//渲染回复
			sHtml+=renderFeedBackReply(fdDate,fdDate[i].sid);
			
			sHtml+="</div>";
		}
	}
	return sHtml;
}


//检查会签意见必填
function   checkFbRequired(){
    var url=contextPath+"/flowRun/checkFbRequired.action";
	var json=tools.requestJsonRs(url,{runId:runId,frpSid:frpSid});
	return json.rtState; 
}
/**
 * 保存会签意见 zhp 20131020
 */
window.parentCallback;
function submitForm(parentCallback){
	
//	signSubmit();
	if(editor){
		$("#content").attr("value",editor.getContent());
	}
	window.parentCallback = parentCallback;
	$("#feedBackForm").submit();
// 	 $("#feedBackForm").ajaxSubmit({
//            url: contextPath+'/feedBack/addFeedBack.action',
//            iframe: false,
//            data: para,
//            success: function(res) {
//         	   		try{
//         	   			parentCallback();
//         	   		}catch(e){}
//         	   		window.location.reload();
//                  },
//           error: function(arg1, arg2, ex) {
//                  alert("添加会签意见出错！");
//            },
//            dataType: 'json'});
		
}

/**
 * 新建附件 zhp 20131020
 */
function newAttach(){
	var newType = $("#new_type").val();
	var newName = $("#new_name").val();

	var para  = {};
	para["runId"] = runId;
	para["frpSid"] = frpSid;
	para["newType"] = newType;
	para["newName"] = newName;

	var newAttachUrl = contextPath +"/teeWorkflowAttachmentController/createNewAttach.action";
	var json = tools.requestJsonRs(newAttachUrl,para);
	if(json.rtState){
		loadWorkFlowAttach();
		var attachId = json.rtData.attachId;
		var attachName = json.rtData.attachName;
		var ntkoURL = contextPath +  "/system/core/ntko/indexNtko.jsp?attachmentId="+attachId+"&attachmentName="+attachName+"&moudle=workFlow&op=4";
// 		top.bsWindow(encodeURI(ntkoURL),"在线文档编辑",{buttons:[]});
		openFullWindow(encodeURI(ntkoURL),"在线文档编辑");
	}else{
		top.$.jBox.tip(json.rtMsg,"info");
	}
}

/**
*转交下一步
*/
function turnNext(){
	//已移至index页面
}

/**
*结束流程
*/
function turnEnd(){
	if(window.confirm("确认要结束此工作吗？")){
		saveFlowRunData(1,1,function(){
			var url = contextPath+"/flowRun/turnEnd.action";
			var json = tools.requestJsonRs(url,{frpSid:frpSid,flowId:flowId});
			if(parent.doPageHandler){
				parent.doPageHandler();
			}
		},true);
	}
}

/**
*结束流程
*/
function turnEndIdFunc(){
	if(window.confirm("确认要结束当前任务吗？")){
		saveFlowRunData(1,1,function(){
			var url = contextPath+"/flowRun/turnNextHandler.action";
			var json = tools.requestJsonRs(url,{runId:runId,frpSid:frpSid,flowId:flowId,turnModel:"[{\"prcsId\":"+window.turnEndId+"}]"});
			if(parent.doPageHandler){
				parent.doPageHandler();
			}
		},true);
	}
}
/**
*回退
*/
function turnBack(){
	alert("回退");
}

/**
 * 聚焦会签意见
 */
function focusFeedback(){
	$('body,html').animate({scrollTop:$("#feedbackTitleDiv").offset().top}, 800);
}

/**
* 聚焦公共附件
*/
function focusAttachment(){
	$('body,html').animate({scrollTop:$("#pulicAttachmentForm").offset().top}, 800);
}

/**
* 聚焦表单
*/
function focusForm(){
	$('body,html').animate({scrollTop:0}, 800);
}

function delCtrlFeedbackData(sid){
	if(window.confirm("是否删除该会签数据？")){
		var url = contextPath+"/flowRun/delCtrlFeedbacks.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		window.location.reload();
	}
}

//记录语音
function RecordVoice(itemId){
// 	Voice(function(result){
// 		$("#"+itemId).val(result);
// 	},"workFlowVoice",runId);
	//dialog("voice_record.jsp?itemId="+itemId+"&runId="+runId+"&model=workFlowVoice&modelId="+runId,808,140);
}

function doUploadXlistData(fileCtrl){
	var ext = fileCtrl.value.split(".");
	if(ext[1]!="xls" && ext[1]!="xlsx"){
		alert("请上传正确的Excel格式文件");
		$(fileCtrl).parent().parent().parent()[0].reset();
		return;
	}
	try{
		$(fileCtrl).parent().parent().parent()[0].submit();
		$(fileCtrl).parent().parent().parent()[0].reset();
	}catch(e){alert(e);}
	
}

function doUploadXlistDataCallback(json){
	if(json.state){
		for(var i=0;i<json.datas.length;i++){
			addRow(json.itemId,json.datas[i]);
		}
	}else{
		alert("解析数据失败！");
	}
}

function addMobileHandSeal(){
	alert("移动签批仅支持在移动APP上使用");
}

function quickFillData(ctrl){
	var itemId = ctrl.getAttribute("id").split("_")[1];
	var  url = contextPath+"/system/core/workflow/flowrun/prcs/quick_fill.jsp?frpSid="+frpSid+"&itemId="+itemId;
	var IM_OA;
	  try{
	      IM_OA = window.external.IM_OA;
	  }catch(e){}

	  if(window.showModelDialog || IM_OA){
	  	  dialogChangesize(url, 760, 400);
	    }else{
	  	  openWindow(url,"快速数据回填", 760, 400);
	    }
}


//点击赋值按钮  给自动编号控件赋值
function generateAutoNum(itemId,numberId,model,modelId){
	var url=contextPath+"/cusNumberController/generateCustomNumber.action";
	var json=tools.requestJsonRs(url,{uuid:numberId,model:model,modelId:modelId});
	if(json.rtState){
		//给控件赋值
		$("#DATA_"+itemId).val(json.rtData);
	}
	
}

function h5CtrlSeal(itemId){
	addH5HandSeal("HAND_"+itemId);
}


window.xListItemSelectData=function(obj,itemId){
	  var valueModel=$(obj).attr("valueModel");
	  var tdIndex=$(obj).parent().attr("index");
	  var trIndex=$(obj).parent().parent().attr("index");
	  
	  var model=$("#XLIST_"+itemId).attr("model");
	  var modelJson=tools.strToJson(model);
	  var  title=modelJson[tdIndex-1].title;
	  
	  //alert("valueModel:"+valueModel+",tdIndex:"+tdIndex+",trIndex:"+trIndex+",title:"+title);
	  var url=contextPath+"/system/core/workflow/flowrun/prcs/xListDataList.jsp?valueModel="+valueModel+"&tdIndex="+tdIndex+"&trIndex="+trIndex+"&title="+title+"&itemId="+itemId;
	  
	  openWindow(url,600,800);  
}


//回复会签意见
function replyFeedBack(fbSid){
	var url=contextPath+"/system/core/workflow/flowrun/prcs/replyFeedBack.jsp?replyId="+fbSid+"&frpSid="+frpSid;
	top.bsWindow(url ,"回复会签意见",{width:"800",height:"300",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.save();
		    //alert(tools.jsonObj2String(json));
		    if(json.rtState){
		    	parent.$.MsgBox.Alert_auto("回复成功！",function(){
		    		loadFeedBack();
            	});
		    	return  true;
		    }else{
		    	parent.$.MsgBox.Alert_auto("回复失败！");
		    	return false;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}