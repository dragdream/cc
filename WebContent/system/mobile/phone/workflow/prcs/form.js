function doInit(){
	if(openKinggrid==1){
		//加载金格签章组件
		Signature.init({//初始化属性
		    keysn:keySn,
		    delCallBack: delCB,
		    moveable:false,
		    imgtag: 0, //签章类型：0：无; 1:公章; 2:私章; 3:法人章; 4:法人签名; 5:手写签名
			certType : 'server',//设置证书在签章服务器
			sealType : 'server',//设置印章从签章服务器取
			serverUrl : kinggridServerUrl,
			documentid:runId+'',//设置文档ID
			documentname:runId+'',//设置文档名称
			pw_timeout:'s1800' //s：秒；h:小时；d:天
		});
	}
	

	tools.requestJsonRs(contextPath+"/mobileWorkflow/getFormHanderData.action",{runId:runId,flowId:flowId,frpSid:frpSid},true,function(json){
		
		if(!json.rtState){
			
			$("#content").html("<p align='center' style='color: red;margin-top:20px'>提示："+json.rtMsg+"</p>");	
		    $("#saveBtn").attr("style","display:none");
		}else{
			//流程信息控制模型
			var workFlowCtrModel=tools.string2JsonObj(json.rtData.workFlowCtrModel);
			runName=json.rtData.runName;//给流程名称赋值
			if(json.rtData.isOpen==1 && workFlowCtrModel!=""&&workFlowCtrModel!=null&&workFlowCtrModel!="null"){//固定流程
				if(workFlowCtrModel[0]["workName"]==true){
					$("#title").html("("+json.rtData.runId+")&nbsp;"+json.rtData.runName);
				}else{
					$("#gzmc").hide();
				}
				
				if(workFlowCtrModel[0]["fromUser"]==true){
					$("#beginUser").html(json.rtData.beginUser);
				}else{
					$("#lcfqr").hide();
				}
				if(workFlowCtrModel[0]["beginTime"]==true){
					$("#beginTime").html(json.rtData.beginTime);
				}else{
					$("#kssj").hide();
				}
				if(workFlowCtrModel[0]["currStep"]==true){
					$("#prcsDesc").html(json.rtData.prcsDesc);
				}else{
					$("#dqbz").hide();
				}
				if(workFlowCtrModel[0]["ysbd"]==true){
					
				}else{
					$("#ysbd").hide();
				}
				
				
			}else{//自由流程
				$("#title").html("("+json.rtData.runId+")&nbsp;"+json.rtData.runName);
				$("#beginUser").html(json.rtData.beginUser);
				$("#beginTime").html(json.rtData.beginTime);
				$("#prcsDesc").html(json.rtData.prcsDesc);
				
			}
			
			
			topFlag = json.rtData.topFlag;
			attachOtherPriv = json.rtData.attachOtherPriv;
			attachPriv=json.rtData.attachPriv;
			attachPrivLock=json.rtData.attachPrivLock;
			
			$("#form").html(json.rtData.form+"<script>"+json.rtData.script+"<\/script>");
			var afterRenderedScript = $("<script><\/script>");
			if(json.rtData.afterRenderedScript){
				afterRenderedScript.html(json.rtData.afterRenderedScript);
			}
			$("#form").append(afterRenderedScript);
			$("#form").find("input[type='text']").each(function(i,obj){
				$(obj).css({width:"98%",height:""});
			});
			$("#form").find("textarea").each(function(i,obj){
				$(obj).css({width:"98%",height:"100px"});
			});
			
			
			
			if(json.rtData.docId){
				if((json.rtData.officePriv & 1)==1 ){//查看权限
					if((json.rtData.officePriv & 4) == 4){//保存权限
						$("#docEditDiv").show().html("正文：<span onclick=\"EditFile("+json.rtData.docId+",'"+(json.rtData.docId+json.rtData.docFileName)+"','"+json.rtData.docAttachName+"')\" style='color:red'>(点击进行编辑)</span>");
					}else{
						$("#docDiv").show().html("正文：<span onclick=\"GetFile("+json.rtData.docId+",'"+(json.rtData.docId+json.rtData.docFileName)+"','"+json.rtData.docAttachName+"')\" style='color:red'>(点击查看)</span>");
					}
				}
				
			}

			
//			alert(json.rtData.aipDocId);
			if(json.rtData.aipDocId){
				if((json.rtData.officePriv & 1)==1 ){//查看权限
					if((json.rtData.officePriv & 4) == 4){//保存权限
						$("#aipEditDiv").show().html("版式正文：<span onclick=\"OpenAipFile("+json.rtData.aipDocId+",'"+(json.rtData.runId)+"','2')\" style='color:red'>(点击进行编辑)</span>");
					}else{
						$("#aipDiv").show().html("版式正文：<span onclick=\"OpenAipFile("+json.rtData.aipDocId+",'"+(json.rtData.runId)+"','1')\" style='color:red'>(点击查看)</span>");
					}
				}
				
			}
			
			if(json.rtData.isHasAipTemplate==1){//存在AIP打印模板
				$("#qpd").show();
				renderTemplate();
			}else{
				$("#qpd").hide();
			}
			
			
			delegate = json.rtData.delegate;
			
			try{
				initialize();//初始化动态表单
			}catch(e){}
			try{
				loadWorkFlowAttach();//加载公共附件
			}catch(e){}
			try{
				loadFeedBack();//加载会签意见
			}catch(e){}
			try{
				loadOperations();//初始化操作按钮
			}catch(e){}
			feedback = json.rtData.feedback;
			if(feedback!=0){//开放会签
				$("#feedbackForm").show();
			}
			try{
				formValidModel = eval("("+json.rtData.formValidModel+")");
			}catch(e){}
			
			//加载移动签章
			LoadMobileSignData();
			//加载手写签批
			LoadMobileHwData();
			//加载手写签批
			LoadH5HwData();
			//加载上传控件
			LoadXUploadCtrl();
			//加载会签控件数据
			loadCtrlFeedbackDatas();
			//加载金格签章
			LoadSignData();
			//加载图片上传控件
			LoadXImgCtrl();
			
			//flowProcess的公共附件权限
			if((json.rtData.attachPriv&16)==16){
				$("#publicAttachUploadContainer").show();
				$("#publicAttachUploadButtons").show();
			}else{
				$("#publicAttachUploadContainer").hide();
				$("#publicAttachUploadButtons").hide();
			}


			//flowType的附件区域控件
			if(attachPrivLock==1){//开启
				$("#ggfj").show();
			}else{//关闭
				$("#ggfj").hide();
			}
			//$(".mobilexlistoper").hide();
			
			$(".xlist_thead").attr("nowrap","nowrap").css({width:"100px"});
			
			if(openKinggrid==1){
				bindSignEvent();//绑定签章事件
				Signature.loadSignatures(signatures);
			}
		}
	});
	
	
	renderFlowStep();
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
			$("#img"+itemId).attr("itemId",itemId).bind("click",function(){
				var itemId = $(this).attr("itemId");
				TakePhoto(function(files){
					var paths = [];
					for(var j=0;j<files.length;j++){
						paths.push(files[j].path);
					}
					UploadPhoto(function(files0){
						$("#DATA_"+itemId).val(files0[0].id);
						$("#img"+itemId).attr("src",contextPath+"/attachmentController/downFile.action?id="+files0[0].id);
						Alert("图片上传成功");
					},paths,"imgupload",runId);
				},1,1);
			});
			
			/*//多附件SWF上传组件
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
				});*/
		}
		
	}
}


function LoadSignData() {
	$("input[xtype=xseal]").each(function(i,obj){
		var data = $(obj).val();
		if(data!=""){
			var sp = data.split(",");
			var key = sp[0];
			var value = sp[1];
			var writable = $(obj).attr("writable");
			if(writable!=undefined){
				signatures.push({extra:{
        		    icon_move: function(){
        		    	return true;
        		    }
                 },signatureid:key,signatureData:value});
			}else{
				signatures.push({extra:{
        		    icon_move: function(){
        		    	return false;
        		    }
                 },signatureid:key,signatureData:value});
			}
			
		}
	});
}

//渲染签批单
//渲染相关的aip签批模板
function renderTemplate(){
	var  url=contextPath+"/flowPrintTemplate/renderTemplate.action";
	var  json=tools.requestJsonRs(url,{flowId:flowId,frpSid:frpSid});
	if(json.rtState){
		var  data=json.rtData;
		var cy = data.cy;
		var qp = data.qp;
		var html = [];
		if(qp && qp.length>0){
			html.push("<optgroup label='可签批'>");
			for(var i=0;i<qp.length;i++){
				html.push("<option class='qp' value="+qp[i].sid+">"+qp[i].modulName+"</option>");
			}
			html.push("</optgroup>");
		}
		
		if(cy && cy.length>0){
			html.push("<optgroup label='可查阅'>");
			for(var n=0;n<cy.length;n++){
				html.push("<option class='cy' value="+cy[n].sid+">"+cy[n].modulName+"</option>");
			}
			html.push("</optgroup>");
		}
		$("#templateId").append(html.join(""));
	}
}

//选择签批模板
var attachmentId=0;
//改变模板
function changeTemplate(){
	
	//获取被选中的option的属性   是可查阅  还是可签批
	var optClass=$("#templateId option:selected").attr("class");
	
	if($("#templateId").val()==0){//未选择
		
	}else{//已选择
		
		var url=contextPath+"/flowRunAipTemplateController/isExist.action";
		var json=tools.requestJsonRs(url,{runId:runId,templateId:$("#templateId").val()});
		if(json.rtState){
			var data=json.rtData;
			attachmentId=data.attachId;
		    //关联表单数据
		    var  u=contextPath+"/flowRun/getFlowRunDatasOnTitle.action";
		    var j=tools.requestJsonRs(u,{runId:runId});
		    if(j.rtState){
		    	var d=j.rtData;
		    	if(optClass=="cy"){//查阅
		    		OpenAip(attachmentId,data.templateName+".aip",1,d);
				}else{//签批
					
					//获取当前步骤的签章数据
					var url = contextPath+"/flowRun/getSealRulesByPrcs.action?frpSid="+frpSid+"&aipId="+$("#templateId").val();
					var json = tools.requestJsonRs(url,{});
					var list = json.rtData;
					var arr = [];
					for(var i=0;i<list.length;i++){
						arr.push({id:list[i].sealId,name:list[i].sealName});
					}
					OpenAip(attachmentId,data.templateName+".aip",2,d,arr);
				}	
		    }
		}
	}
	
}



//上传公共附件
function doUploadPublicAttach(){
	TakePhoto(function(files){
		$("<p class='img' path=\""+files[0].path+"\">"+files[0].name+"&nbsp;&nbsp;<img src='/common/images/upload/remove.png' onclick='removePublicAttachImg(this)' /></p>").appendTo($("#publicAttachUploadContainer"));
	});
}
//移除公共附件项
function removePublicAttachImg(obj){
	$(obj).parent().remove();
}
//上传会签附件
function doUploadFeedbackAttach(){
	TakePhoto(function(files){
		$("<p class='img' path=\""+files[0].path+"\">"+files[0].name+"&nbsp;&nbsp;<img src='/common/images/upload/remove.png' onclick='removeFeedbackImg(this)' /></p>").appendTo($("#publicFeedbackUploadContainer"));
	});
}
//移除会签附件
function removeFeedbackImg(obj){
	$(obj).parent().remove();
}

function DelFile(fileId,obj){
	if(window.confirm("确认要删除该附件吗？")){
		var url = contextPath +"/attachmentController/deleteFile.action?attachIds="+fileId;
		var json = tools.requestJsonRs(url);
		window.location.reload();
	}
}


function DelFile1(fileId,obj){
	if(window.confirm("确认要删除该附件吗？")){
		var url = contextPath +"/attachmentController/deleteFile.action?attachIds="+fileId;
		var json = tools.requestJsonRs(url);
		//window.location.reload();
		//移除对象
		$("img[attachid="+fileId+"]").parent().remove();
		var buttons=$("button[attachid="+fileId+"]");
		if(buttons!=null&&buttons.length>0){
			for(var i=0;i<buttons.length;i++){
				$(buttons[i]).remove();
			}
		}
	}
}

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
		for(var j=0;j<attachList.length;j++){
			var att = attachList[j];
			var attUserId = att.userId;
			var domHtml="";
			if(this.isImage(att.ext)){//如果是图片
				domHtml = ["<p style=color:#0080ff;font-size:12px><img class='pic' attachId="+att.sid+"  attachName='"+att.attachmentName+"'   src='"+window.contextPath+"/common/images/filetype/defaut.gif' />&nbsp;"+att.fileName+"("+att.sizeDesc+")"+"</p>"];
			}else{
				domHtml = ["<p style=color:#0080ff;font-size:12px><img  attachId="+att.sid+"  attachName='"+att.attachmentName+"'  src='"+window.contextPath+"/common/images/filetype/defaut.gif' />&nbsp;"+att.fileName+"("+att.sizeDesc+")"+"</p>"];
			}
			
			if(att.userId==userId){//是当前登录人自己的附件
			   if((attachPriv&1)==1){//查看权限
				   if(this.isImage(att.ext)){//如果是图片
						domHtml.push("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;' onclick=\"PrePicExplore("+att.sid+")\">查看</button>");
					}else{
						domHtml.push("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;' onclick=\"GetFile("+att.sid+",'"+att.fileName+"','"+att.attachmentName+"')\">查看</button>");
						if(att.ext.indexOf("doc")!=-1 ||att.ext.indexOf("docx")!=-1||att.ext.indexOf("xls")!=-1 ||att.ext.indexOf("xlsx")!=-1){
					    	domHtml.push("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;' onclick=\"quickView("+att.sid+")\">快速预览</button>");    	
					    }
					}
			   }
			   
				if((attachPriv&4)==4){//删除权限
					domHtml.push("<button class=\"btn btn-danger btn-sm\" style='font-size:12px;padding:2px;' onclick=\"DelFile("+att.sid+",this)\">删除</button>");			   
				}
				if((attachPriv&8)==8){//编辑权限
					if(att.ext.indexOf("doc")!=-1 || att.ext.indexOf("ppt")!=-1 || att.ext.indexOf("xls")!=-1){
						domHtml.push("<button class=\"btn btn-primary btn-sm\" style='font-size:12px;padding:2px;' onclick=\"EditFile("+att.sid+",'"+att.fileName+"','"+att.attachmentName+"')\">编辑</button>");
					}  
				}
			}else{//不是当前登陆人自己的附件
				if(attachOtherPriv==1){//允许修改
					if((attachPriv&1)==1){//查看权限
						   if(this.isImage(att.ext)){//如果是图片
								domHtml.push("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;' onclick=\"PrePicExplore("+att.sid+")\">查看</button>");
							}else{
								domHtml.push("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;' onclick=\"GetFile("+att.sid+",'"+att.fileName+"','"+att.attachmentName+"')\">查看</button>");
								if(att.ext.indexOf("doc")!=-1 ||att.ext.indexOf("docx")!=-1||att.ext.indexOf("xls")!=-1 ||att.ext.indexOf("xlsx")!=-1){
							    	domHtml.push("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;' onclick=\"quickView("+att.sid+")\">快速预览</button>");    	
							    }
							}
					   }
					   
						if((attachPriv&4)==4){//删除权限
							domHtml.push("<button class=\"btn btn-danger btn-sm\" style='font-size:12px;padding:2px;' onclick=\"DelFile("+att.sid+",this)\">删除</button>");			   
						}
						if((attachPriv&8)==8){//编辑权限
							if(att.ext.indexOf("doc")!=-1 || att.ext.indexOf("ppt")!=-1 || att.ext.indexOf("xls")!=-1){
								domHtml.push("<button class=\"btn btn-primary btn-sm\" style='font-size:12px;padding:2px;' onclick=\"EditFile("+att.sid+",'"+att.fileName+"','"+att.attachmentName+"')\">编辑</button>");
							}  
						}
				}else{//不允许修改    不能编辑删除
					if((attachPriv&1)==1){//查看权限
						   if(this.isImage(att.ext)){//如果是图片
								domHtml.push("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;' onclick=\"PrePicExplore("+att.sid+")\">查看</button>");
							}else{
								domHtml.push("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;' onclick=\"GetFile("+att.sid+",'"+att.fileName+"','"+att.attachmentName+"')\">查看</button>");
								if(att.ext.indexOf("doc")!=-1 ||att.ext.indexOf("docx")!=-1||att.ext.indexOf("xls")!=-1 ||att.ext.indexOf("xlsx")!=-1){
							    	domHtml.push("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;' onclick=\"quickView("+att.sid+")\">快速预览</button>");    	
							    }
							}
					   }
					
				}	
			}
			
			
			$("#pulicAttachments").append(domHtml.join("&nbsp;&nbsp;"));
		}
	}else{
		Alert(json.rtMsg);
	}
}

//加载上传控件
function LoadXUploadCtrl(){
	var xuploadCtrls = $("input[xtype=xupload]");
	for(var i=0;i<xuploadCtrls.length;i++){
		var writable = xuploadCtrls[i].getAttribute("writable");//是否可写
		var id = xuploadCtrls[i].getAttribute("id");
		var itemId = id.split("_")[1];
		var val = xuploadCtrls[i].getAttribute("value");
		var priv = xuploadCtrls[i].getAttribute("priv");
		
		
		if(val!="" && val!=null && val!=undefined){
			var attach = val.split("|");
			for(var j=0;j<attach.length;j++){
				var item = attach[j].split("^");
				if(this.isImage(item[3])){
					$("#xuploadDiv"+itemId).append("<p onclick=\"PrePicExplore("+item[0]+")\" style=color:#0080ff;font-size:12px><img class='pic' attachId="+item[0]+" attachName='"+item[1]+"' src='"+window.contextPath+"/common/images/filetype/defaut.gif' />&nbsp;"+item[1]+"("+item[2]+")</p>");
				}else{
					$("#xuploadDiv"+itemId).append("<p onclick=\"GetFile("+item[0]+",'"+item[0]+"_"+item[1]+"','"+item[0]+"_"+item[1]+"')\" style=color:#0080ff;font-size:12px><img attachId="+item[0]+" attachName='"+item[1]+"'  src='"+window.contextPath+"/common/images/filetype/defaut.gif' />&nbsp;"+item[1]+"("+item[2]+")</p>");
				}
				
				
				
				//var attachObj = tools.getAttachElement({fileName:item[1],ext:item[3],priv:priv,sid:item[0]});
				if((priv&1)==1){//查看
					if(this.isImage(item[3])){
						$("#xuploadDiv"+itemId).append("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;'   attachId="+item[0]+"  onclick=\"PrePicExplore("+item[0]+")\">查看</button>&nbsp;");
					}else{
						$("#xuploadDiv"+itemId).append("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;'   attachId="+item[0]+"   onclick=\"GetFile("+item[0]+",'"+item[1]+"','"+item[1]+"')\">查看</button>&nbsp;");
					    if(item[3]=="doc"||item[3]=="docx"||item[3]=="xls"||item[3]=="xlsx"){
					    	
					    	$("#xuploadDiv"+itemId).append("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;'  attachId="+item[0]+"  onclick=\"quickView("+item[0]+");\">快速预览</button>&nbsp;");
					    }
					}
					
				}
				if((priv&4)==4){//删除
					$("#xuploadDiv"+itemId).append("<button class=\"btn btn-danger btn-sm\" style='font-size:12px;padding:2px;'  attachId="+item[0]+"   onclick=\"DelFile1("+item[0]+",this)\">删除</button>&nbsp;");			
				   
				}
				if((priv&8)==8){//编辑
					if(item[3]=="doc"||item[3]=="docx"||item[3]=="xls"||item[3]=="xlsx"||item[3]=="ppt"||item[3]=="pptx"||item[3]=="txt"){
						$("#xuploadDiv"+itemId).append("<button class=\"btn btn-primary btn-sm\" style='font-size:12px;padding:2px;'  attachId="+item[0]+"  onclick=\"EditFile("+item[0]+",'"+item[1]+"','"+item[1]+"')\">编辑</button>");
					}
					
				}		
			}
		}
		
		if((priv&16)==16){//上传
			$("#xuploadDiv"+itemId).append(""
                       +"<div  style=\"padding:10px;\">"
	                   +     "<button class=\"btn btn-primary\" onclick=\"doXUpload('"+itemId+"')\">上传附件</button>"
                       +"</div>");
		}
		
		
	}
}




function doXUpload(itemId){
		TakePhoto(function(files){
			if(files.length!=0){
				var attachIds = [];
				for(var i=0;i<files.length;i++){
					attachIds.push(files[i].path);
				}
				UploadPhoto(function(files0){
					for(var n=0;n<files0.length;n++){
						renderOneAttach(itemId,files0[n].id);
					}
				},attachIds,"workFlowUploadCtrl",itemId+"_"+runId);
			}
		});
}

/**
 * 附件上传控件  渲染单个附件
 */
function renderOneAttach(itemId,attachId){
	
	var priv = $("#DATA_"+itemId).attr("priv");
	//根据附件id  获取附件信息
	var url=contextPath+"/attachmentController/getAttachInfo.action";
	var json=tools.requestJsonRs(url,{id:attachId});
	if(json.rtState){
		
		var attachName=json.rtData.attachmentName;
		var ext=json.rtData.ext;
		var size=json.rtData.size;
		
		if(this.isImage(ext)){
			$("#xuploadDiv"+itemId+" > div:last-child").before("<p onclick=\"PrePicExplore("+attachId+")\" style=color:#0080ff;font-size:12px><img class='pic' attachId="+attachId+" attachName='"+attachId+"' src='"+window.contextPath+"/common/images/filetype/defaut.gif' />&nbsp;"+attachName+"("+size+")</p>");
		}else{
			$("#xuploadDiv"+itemId+" > div:last-child").before("<p onclick=\"GetFile("+attachId+",'"+attachId+"_"+attachName+"','"+attachId+"_"+attachName+"')\" style=color:#0080ff;font-size:12px><img attachId="+attachId+" attachName='"+attachName+"'  src='"+window.contextPath+"/common/images/filetype/defaut.gif' />&nbsp;"+attachName+"("+size+")</p>");
		}
		if((priv&1)==1){//查看
			if(this.isImage(ext)){
				$("#xuploadDiv"+itemId+" > div:last-child").before("<button class=\"btn btn-warning btn-sm\"   attachId="+attachId+"   style='font-size:12px;padding:2px;' onclick=\"PrePicExplore("+attachId+")\">查看</button>&nbsp;");
			}else{
				$("#xuploadDiv"+itemId+" > div:last-child").before("<button class=\"btn btn-warning btn-sm\"  attachId="+attachId+"   style='font-size:12px;padding:2px;' onclick=\"GetFile("+attachId+",'"+attachName+"','"+attachName+"')\">查看</button>&nbsp;");
			    if(ext=="doc"||ext=="docx"||ext=="xls"||ext=="xlsx"){
			    	$("#xuploadDiv"+itemId+" > div:last-child").before("<button class=\"btn btn-warning btn-sm\"   attachId="+attachId+"  style='font-size:12px;padding:2px;' onclick=\"quickView("+attachId+");\">快速预览</button>&nbsp;");
			    }
			}
		}
		if((priv&4)==4){//删除
			$("#xuploadDiv"+itemId+" > div:last-child").before("<button class=\"btn btn-danger btn-sm\" attachId="+attachId+"  style='font-size:12px;padding:2px;' onclick=\"DelFile1("+attachId+",this)\">删除</button>&nbsp;");			
		}
		if((priv&8)==8){//编辑
			if(ext=="doc"||ext=="docx"||ext=="xls"||ext=="xlsx"||ext=="ppt"||ext=="pptx"||ext=="txt"){
				$("#xuploadDiv"+itemId+" > div:last-child").before("<button class=\"btn btn-primary btn-sm\"  attachId="+attachId+"  style='font-size:12px;padding:2px;' onclick=\"EditFile("+attachId+",'"+attachName+"','"+attachName+"')\">编辑</button>");
			}
			
		}
	}
}


function loadFeedBack(){
	/*var url = contextPath+"/feedBack/getFeedBackList.action";
	var para  = {};
	para["runId"] = runId;
	para["frpSid"] = frpSid;
	$("#feedbackDiv").html("");
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		var fdDate = json.rtData;
		var sHtml = [];
		for(var i=0;i<fdDate.length;i++){
			var attachList = fdDate[i].attachList;
			sHtml = ["<div style='border-bottom:1px solid #e2e2e2'>"];
			sHtml.push("<p style='border-bottom:1px solid #e2e2e2'><span style='color:orange'>●</span>&nbsp;第"+fdDate[i].prcsId+"步&nbsp;"+fdDate[i].prcsName+"&nbsp;&nbsp;");
			if(fdDate[i].voiceId!="" && fdDate[i].voiceId && fdDate[i].voiceId!=null){
				sHtml.push("<video type='video/mp3' style=\"cursor:pointer\" onclick='this.play();' src='/attachmentController/downFile.action?id="+fdDate[i].voiceId+"' title=\"点击播放语音\" width='20px' height='20px' poster=\"/common/images/workflow/voice_play.png\"></video>");
			}
			sHtml.push("</p><p style='padding-top:0px;'><p style='float:left;font-size:14px'>"+fdDate[i].userName+"</p><p style='float:right;font-size:14px'>"+fdDate[i].editTimeDesc+"</p></p>");
			sHtml.push("<p style='clear:both;padding-bottom:0px'></p>"); 
			sHtml.push("<div style='font-size:14px;padding:5px;padding-top:0px'>"+fdDate[i].content+"</div>");
			sHtml.push("</div>");
			$("#feedbackDiv").append(sHtml.join(""));
		}
	}else{
		Alert(json.rtMsg);
	}*/
	var url = contextPath+"/feedBack/getFeedBackList.action";
	var para  = {};
	para["runId"] = runId;
	para["frpSid"] = frpSid;
	$("#feedbackDiv").html("");
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		var fdDate = json.rtData;
		var sHtml = "";
		//$(parent.feedback_count).html(fdDate.length);
		for(var i=0;i<fdDate.length;i++){
			//先渲染第一级 	
			if(fdDate[i].replayId==0){	
				var attachList = fdDate[i].attachList;
				sHtml+="<div style=\"font-size:14px;padding:10px;background:#f2f2f2;border-left:2px solid #176fd1;margin-top:10px;\">";
				sHtml+="<div>"
					 +"<div><span style=\"font-weight:bold;\">第"+fdDate[i].prcsId+"步&nbsp;&nbsp;"+fdDate[i].prcsName+"</span>&nbsp;&nbsp;"+(fdDate[i].backFlag==1?"<img src='/common/images/workflow/goback.png' title='退回步骤'/>":"")+"&nbsp;&nbsp;<span style=\"font-weight:bold;color:#4188d6;\">"+fdDate[i].userName+"</span>";
				if(fdDate[i].voiceId!="" && fdDate[i].voiceId && fdDate[i].voiceId!=null){
					sHtml = sHtml+"&nbsp;&nbsp;<video type='video/mp3' style=\"cursor:pointer\" onclick='this.play();' src='/attachmentController/downFile.action?id="+fdDate[i].voiceId+"' title=\"点击播放语音\" width='20px' height='20px' poster=\"/common/images/workflow/voice_play.png\"></video>";
				}
				sHtml+="</div>";  
				
				sHtml+="<div>"+fdDate[i].content+"</div>";
				sHtml+="<div id=\"attach_"+fdDate[i].sid+"\"></div>";
				
				sHtml+="<div><span style=\"color:#cbcbcb;font-size:12px;\">"+fdDate[i].editTimeDesc+"</span>";
				
				if(fdDate[i].userId==userId){
					sHtml+="&nbsp;&nbsp;&nbsp;<a href=\"#\" style=\"cursor:pointer;color:#818181;font-size:12px;\" onclick=\"delFeedBack("+fdDate[i].sid+")\">删除</a>";
				}
				sHtml+="&nbsp;&nbsp;&nbsp;<a href=\"#\" style=\"cursor:pointer;color:#818181;font-size:12px;\" onclick=\"replyFeedBack("+fdDate[i].sid+")\" >回复</a>";
				sHtml+="</div>";
				sHtml+="</div>";
				
				//渲染回复
				sHtml+=renderFeedBackReply(fdDate,fdDate[i].sid);
				
				sHtml+="</div>";
				
				$("#feedbackDiv").append(sHtml);
				sHtml="";
				for(var j=0;j<attachList.length;j++){
					var att = attachList[j];
					var attUserId = att.userId;
					var domHtml="";
					if(this.isImage(att.ext)){//如果是图片
						domHtml = ["<p style=color:#0080ff;font-size:12px><img class='pic' attachId="+att.sid+"  attachName='"+att.attachmentName+"'   src='"+window.contextPath+"/common/images/filetype/defaut.gif' />&nbsp;"+att.fileName+"("+att.sizeDesc+")"+"</p>"];
					}else{
						domHtml = ["<p style=color:#0080ff;font-size:12px><img  attachId="+att.sid+"  attachName='"+att.attachmentName+"'  src='"+window.contextPath+"/common/images/filetype/defaut.gif' />&nbsp;"+att.fileName+"("+att.sizeDesc+")"+"</p>"];
					}
					
					if((userId==att.userId) || (attachOtherPriv && attachOtherPriv==1)){//如果是当前办理人 或者 当前办理人是主办人且当前步骤开启了允许他人编辑，则拥有所有权限
						if(this.isImage(att.ext)){//如果是图片
							domHtml.push("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;' onclick=\"PrePicExplore("+att.sid+")\">查看</button>");
						}else{
							domHtml.push("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;' onclick=\"GetFile("+att.sid+",'"+att.fileName+"','"+att.attachmentName+"')\">查看</button>");
							if(att.ext.indexOf("doc")!=-1 ||att.ext.indexOf("docx")!=-1||att.ext.indexOf("xls")!=-1 ||att.ext.indexOf("xlsx")!=-1){
						    	domHtml.push("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;' onclick=\"quickView("+att.sid+")\">快速预览</button>");    	
						    }
						}
						
						if(att.ext.indexOf("doc")!=-1 || att.ext.indexOf("ppt")!=-1 || att.ext.indexOf("xls")!=-1){
							domHtml.push("<button class=\"btn btn-primary btn-sm\" style='font-size:12px;padding:2px;' onclick=\"EditFile("+att.sid+",'"+att.fileName+"','"+att.attachmentName+"')\">编辑</button>");
						}
						domHtml.push("<button class=\"btn btn-danger btn-sm\" style='font-size:12px;padding:2px;' onclick=\"DelFile("+att.sid+",this)\">删除</button>");
					}else{//
						domHtml.push("<button class=\"btn btn-primary btn-sm\" style='font-size:12px;padding:2px;' onclick=\"GetFile("+att.sid+",'"+att.fileName+"','"+att.attachmentName+"')\">查看</button>");
					    if(att.ext.indexOf("doc")!=-1 ||att.ext.indexOf("docx")!=-1||att.ext.indexOf("xls")!=-1 ||att.ext.indexOf("xlsx")!=-1){
					    	domHtml.push("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;' onclick=\"quickView("+att.sid+")\">快速预览</button>");    	
					    }
					}
					
					$("#attach_"+fdDate[i].sid).append(domHtml.join("&nbsp;&nbsp;"));
					
					
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
			sHtml+="<div><span style=\"color:#cbcbcb;font-size:12px;\">"+fdDate[i].editTimeDesc+"</span>";
			if(fdDate[i].userId==userId){
				sHtml+="&nbsp;&nbsp;&nbsp;<a href=\"#\" style=\"cursor:pointer;color:#818181;font-size:12px;\" onclick=\"delFeedBack("+fdDate[i].sid+")\">删除</a>";
			}
			sHtml+="&nbsp;&nbsp;&nbsp;<a href=\"#\" style=\"cursor:pointer;color:#818181;font-size:12px;\" onclick=\"replyFeedBack("+fdDate[i].sid+")\" >回复</a>";
			
			sHtml+="</div>";
			sHtml+="</div>";
			//渲染回复
			sHtml+=renderFeedBackReply(fdDate,fdDate[i].sid);
			
			sHtml+="</div>";
		}
	}
	return sHtml;
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

//回复会签意见
function replyFeedBack(fbSid){
	/*var url=contextPath+"/system/core/workflow/flowrun/prcs/replyFeedBack.jsp?replyId="+fbSid+"&frpSid="+frpSid;
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
	}});*/ 
	var url=contextPath+"/system/mobile/phone/workflow/prcs/replyFeedBack.jsp?replyId="+fbSid+"&frpSid="+frpSid;
	layer.open({
		  type: 1
		  ,content: "<iframe style='position:fixed;top:0px;left:0px;width:100%;height:100%' src='"+url+"'></iframe>"
		  ,anim: 'up'
		  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
		});
}

//加载操作按钮
function loadOperations(){
	var url = contextPath+"/flowRun/getHandlerOptPrivData.action";
	var json = tools.requestJsonRs(url,{runId:runId,frpSid:frpSid});
	if(json.rtState){
		if(json.rtData.turnState && json.rtData.turnState == 1){
			if(json.rtData.flowPrcs){//固定流程专用
				var prcsEventDef = json.rtData.flowPrcs.prcsEventDef;
				if(prcsEventDef!="" && prcsEventDef!=null){
					var sp = prcsEventDef.split(",");
					var render = [];
					for(var j=0;j<sp.length;j++){
						render.push("<button id=\"turnNextBtn\" class=\"btn btn-primary\" style=\"margin-right:10px;\" onclick=\"turnNext('"+sp[j]+"')\">"+sp[j]+"</button>");
					}
					$("#prcsEventDefSpan").html(render.join(""));
				}else{
					$("#turnBtn").show();
				}
			}else{
				$("#turnBtn").show();
			}
		}else if(json.rtData.turnState && json.rtData.turnState == 2){//countersignHandler
			$("#turnBtn").show();
			//结束按钮
		}else{
			$("body").html("<center style='font-size:16px;margin-top:30px'>子流程需办理完成后主流程方可继续流转</center>");
		}
		
		//回退按钮
		if(json.rtData.turnBack == 1){
			$("#backToBtn").show();
		}else if(json.rtData.turnBack == 2){
			$("#backToOtherBtn").show();
		}else if(json.rtData.turnBack == 3){
			$("#backToFixedBtn").show();
		}
		
		//委托按钮
		if(delegate!=0){
			$("#delegateBtn").show();
		}
		
		//归档按钮
		var flowPrcs = json.rtData.flowPrcs;
		if(flowPrcs!=null){
			archivesPriv = flowPrcs.archivesPriv;
			if(archivesPriv!=0){
				$("#archivesBtn").show();
			}
		}
		
		
		frpModel = json.rtData.flowRunPrcs;
		top.$("#level").val(json.rtData.level);
		if(frpModel.prcsId==1){//如果是第一步，则允许修改流程级别
			top.$("#level").removeAttr("disabled");
		}
		
	    return true;
	}else{
		return false;
	}
}

function save(flag,type,validation){
	xCalculating();
	xlistCalculating();
	
	var levelSelectCtrl = parent.$("#level");
	if(levelSelectCtrl.length!=0 && !levelSelectCtrl.attr("disabled")){
		//保存流程级别
		tools.requestJsonRs(contextPath+"/flowRun/updateRunLevel.action",{runId:runId,level:levelSelectCtrl.val()});
	}
	
	if(type==1){//转交  保存
		if(validation && !formValidExec()){
			return;
		}
		
		var validateList = listViewPreSaving($("#form"));
		if(validation && validateList==false){
			return;
		}
		
		//控件校验
		if(validation && !ctrlValidate("#form")){
			return false;
		}
	}
	
	
	
	
	if(feedback==2){//强制会签
		if(hasFeedback()==false){
			if($("#feedbackTextarea").val()==""){
				Alert("该步骤为强制会签，请填写会签意见");
				return;
			}
		}
	}
	
	
	
	//保存会签意见
	addFeedBack();
	
	

	var url = contextPath+"/flowRun/saveFlowRunData.action";
	var para = tools.formToJson($("#form"));
	para["runId"] = runId;
	para["frpSid"] = frpSid;
	para["flowId"] = flowId;
	
	var json = tools.requestJsonRs(url,para);
	if(!json.rtState){
		Alert(json.rtMsg);
		return;
	}
	
	//表单控件会签意见保存
	for(var i=0;i<ctrlSignArray.length;i++){
		var item = ctrlSignArray[i];
		var itemId = ctrlSignArray[i].replace("DATA_","");
		var itemData = $("#"+item).val();
		var signData = $("#CTRL_SIGN_DATA_INPUT_O_"+item).val();
		var sealData = $("#CTRL_SIGN_DATA_INPUT_I_"+item).val();
		var picData = $("#CTRL_SIGN_DATA_INPUT_"+item).val();
		var h5Data = $("#CTRL_H5_DATA_INPUT_"+item).val();
		var mobiData = $("#CTRL_MOBI_DATA_INPUT_"+item).val();
		
		if(itemData!="" || h5Data!="" || picData!="" || mobiData!=""){//保存盖章数据
			var url = contextPath+"/flowRun/saveCtrlFeedback.action";
			var json = tools.requestJsonRs(url,{runId:runId,itemId:itemId,content:itemData,rand:rand,signData:signData,sealData:sealData,frpSid:frpSid,picData:picData,h5Data:h5Data,mobiData:mobiData});
			$("#"+item).val("");
		}
	}
	
	//获取流程标题控件，反向保存流程标题
	$("[save_run_name][writable]").each(function(i,obj){
		tools.requestJsonRs(contextPath+"/flowRun/updateRunName.action",{runId:runId,runName:obj.value});
	});
	
	if(flag==1){
		var attachIds = [];
		$("#publicAttachUploadContainer p").each(function(i,obj){
			attachIds.push($(obj).attr("path"));
		});
		//alert(attachIds);
		UploadPhoto(function(files){
			Alert("保存成功");
			window.location.reload();
		},attachIds,"workFlow",runId);
	}
	
	return true;
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

function addFeedBack(){
	if($("#feedbackTextarea").val()!="" || $("#FEEDBACK_VOICE").val()!=""){
		var url = contextPath+"/mobileWorkflow/addFeedBack.action";
		var json = tools.requestJsonRs(url,{frpSid:frpSid,runId:runId,flowId:flowId,content:$("#feedbackTextarea").val(),voiceId:$("#FEEDBACK_VOICE").val()});
		$("#feedbackTextarea").val("");
		$("#FEEDBACK_VOICE").val("");
		loadFeedBack();
	}
}

//记录语音
function RecordVoice(itemId){
	Voice(function(result){
		$("#"+itemId).val(result);
	},"workFlowVoice",runId);
}


//检查会签意见必填
function   checkFbRequired(){
    var url=contextPath+"/flowRun/checkFbRequired.action";
	var json=tools.requestJsonRs(url,{runId:runId,frpSid:frpSid});
	return json.rtState; 
}

window.ctrlValidate=function(form){
	var ctrls = $(form).find("[required=true]");
	for(var i=0;i<ctrls.length;i++){
		var obj = $(ctrls[i]);
		var type=obj.attr("xtype");
        if(type=="xfeedback"){
        	if(obj.val()==""){//先判断会签框中是否有内容
        		if(checkFbRequired()==false){
        			Alert("["+obj.attr("title")+"]不能为空");
	        		return  false;
	        	}else{
	        		return true;
	        	}	
        		
        	}else{
        		return true;
        	}        	
        	
        }else{
        	if(obj.val()==""){
				Alert("["+obj.attr("title")+"]不能为空");
				obj.focus();
				return false;
			}	
        	
        }
	}
	return true;
}

function delCtrlFeedbackData(sid){
	if(window.confirm("是否删除该会签数据？")){
		var url = contextPath+"/flowRun/delCtrlFeedbacks.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		window.location.reload();
	}
}

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
					Alert(info);
					return false;
				}
			}else if(oper=="2"){//不等于
				if(val1!=val2){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="3"){//大于
				if(val1>val2){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="4"){//大于等于
				if(val1>=val2){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="5"){//小于
				if(val1<val2){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="6"){//小于等于
				if(val1<=val2){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="7"){//以字符开头
				if(val1.StartWith(val2)){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="8"){//以字符结尾
				if(val1.EndWith(val2)){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="9"){//包含
				if(val1.indexOf(val2)!=-1){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="10"){//不包含
				if(val1.indexOf(val2)==-1){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}
		}else if(validItem.oType=="2"){//数字
			if(oper=="1"){//等于
				if(Number(xParseNumber(val1))==Number(xParseNumber(val2))){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="2"){//不等于
				if(Number(xParseNumber(val1))!=Number(xParseNumber(val2))){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="3"){//大于
				if(Number(xParseNumber(val1))>Number(xParseNumber(val2))){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="4"){//大于等于
				if(Number(xParseNumber(val1))>=Number(xParseNumber(val2))){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="5"){//小于
				if(Number(xParseNumber(val1))<Number(xParseNumber(val2))){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="6"){//小于等于
				if(Number(xParseNumber(val1))<=Number(xParseNumber(val2))){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="7"){//以字符开头
				if(val1.StartWith(val2)){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="8"){//以字符结尾
				if(val1.EndWith(val2)){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="9"){//包含
				if(val1.indexOf(val2)!=-1){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="10"){//不包含
				if(val1.indexOf(val2)==-1){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}
		}else if(validItem.oType=="3"){//日期
			if(oper=="1"){//等于
				if(strDate2Date(val1).getTime()==strDate2Date(val2).getTime()){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="2"){//不等于
				if(strDate2Date(val1).getTime()!=strDate2Date(val2).getTime()){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="3"){//大于
				if(strDate2Date(val1).getTime()>strDate2Date(val2).getTime()){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="4"){//大于等于
				if(strDate2Date(val1).getTime()>=strDate2Date(val2).getTime()){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="5"){//小于
				if(strDate2Date(val1).getTime()<strDate2Date(val2).getTime()){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="6"){//小于等于
				if(strDate2Date(val1).getTime()<=strDate2Date(val2).getTime()){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="7"){//以字符开头
				if(val1.StartWith(val2)){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="8"){//以字符结尾
				if(val1.EndWith(val2)){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="9"){//包含
				if(val1.indexOf(val2)!=-1){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}else if(oper=="10"){//不包含
				if(val1.indexOf(val2)==-1){
					$("#DATA_"+validItem.itemId).focus();
					Alert(info);
					return false;
				}
			}
		}
	}
	return true;
}


function turnNext(prcsEvent){
	if(!save(0,1,true)){
		return;
	}
	
	var attachIds = [];
	$("#publicAttachUploadContainer p").each(function(i,obj){
		attachIds.push($(obj).attr("path"));
	});
	UploadPhoto(function(files){
		var url = contextPath+"/flowRun/preTurnHandlerData.action";
		var para = "runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId;
		if(prcsEvent){
			para+="&prcsEvent="+encodeURI(prcsEvent);
		}
		var json = tools.requestJsonRs(url,{runId:runId,frpSid:frpSid,flowId:flowId});
		if(json.rtState){
			var opCode = json.rtData.opCode;
			if(opCode=="1"){//固定流程转交
				var url = contextPath + "/system/mobile/phone/workflow/prcs/turn/turnFixedNext.jsp?"+para;
				window.location = url;
			}else if(opCode=="2"){//自由流程转交
				var url = contextPath + "/system/mobile/phone/workflow/prcs/turn/turnFreeNext.jsp?"+para;
				window.location = url;
			}else{//直接返回列表页面
				if(userAgent.indexOf("DingTalk")!=-1 || userAgent.indexOf("MicroMessenger")!=-1){
					window.location = "../../index.jsp";
				}else{
					CloseWindow();
				}
			}
		}else{
			//无操作
		}
	},attachIds,"workFlow",runId);
}

function selectDataSource(dfid,itemId,title){
	layer.open({
		  type: 1
		  ,content: "<iframe style='position:fixed;top:0px;left:0px;width:100%;height:100%' src='"+contextPath+"/system/mobile/phone/workflow/prcs/datalist.jsp?dfid="+dfid+"&itemName="+itemId+"&runId="+runId+"&title="+encodeURI(title)+"'></iframe>"
		  ,anim: 'up'
		  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
		});
}


function selectFlowDataSource(obj){
	 var flowId=$(obj).attr("flowid");
	 var mappingstr=$(obj).attr("mappingstr");
	 layer.open({
		  type: 1
		  ,content: "<iframe style='position:fixed;top:0px;left:0px;width:100%;height:100%' src='"+contextPath+"/system/mobile/phone/workflow/prcs/flowDataList.jsp?flowId="+flowId+"&mappingstr="+mappingstr+"'></iframe>"
		  ,anim: 'up'
		  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
		});
}

window.selectFlowDataSourceXList=function(obj){
	  var flowId=$(obj).attr("flowid");
	  var mappingstr=$(obj).attr("mappingstr");
	  var itemId=$(obj).attr("itemid");
	  layer.open({
		  type: 1
		  ,content: "<iframe style='position:fixed;top:0px;left:0px;width:100%;height:100%' src='"+contextPath+"/system/mobile/phone/workflow/prcs/flowDataForXList.jsp?flowId="+flowId+"&itemId="+itemId+"&mappingstr="+mappingstr+"'></iframe>"
		  ,anim: 'up'
		  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
	 });
}

function selectListDataSource(dfid,itemId,title){
	layer.open({
		  type: 1
		  ,content: "<iframe style='position:fixed;top:0px;left:0px;width:100%;height:100%' src='"+contextPath+"/system/mobile/phone/workflow/prcs/xlistctrldatalist.jsp?dfid="+dfid+"&itemId="+itemId+"&runId="+runId+"&title="+encodeURI(title)+"'></iframe>"
		  ,anim: 'up'
		  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
		});
}

//移动签章盖章操作
window.addMobileSeal=function(ctrlName){
	var url = contextPath+"/system/mobile/phone/workflow/prcs/mobileSeals.jsp?itemId="+ctrlName;
	
	layer.open({
		  type: 1
		  ,content: "<iframe style='position:fixed;top:0px;left:0px;width:100%;height:100%' src='"+url+"'></iframe>"
		  ,anim: 'up'
		  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
		}); 
}

//移动签章盖章操作
window.picCtrlSeal=function(ctrlName){
	var url = contextPath+"/system/mobile/phone/workflow/prcs/mobileSealsCtrl.jsp?itemId="+ctrlName;
	layer.open({
		  type: 1
		  ,content: "<iframe style='position:fixed;top:0px;left:0px;width:100%;height:100%' src='"+url+"'></iframe>"
		  ,anim: 'up'
		  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
		}); 
}

/**
 * 签章格式事例   data;image/png;base64,签章数据,表单数据MD5加密,左边距,上边据
 */

window.doAddMobileSealCtrl=function(ctrlName,sealData){
	  var targetObject = $("#CTRL_PICSEAL_IMG_"+ctrlName);
	  if(targetObject.length==0){
		  targetObject = $("<img id=\"CTRL_PICSEAL_IMG_"+ctrlName+"\" target=\""+ctrlName+"\" />").appendTo($(".SIGN_POS_CTRL_"+ctrlName));
	  }
	  
	  targetObject.css({opacity:1}).show();
	  
	  var ctrl = $("#CTRL_SIGN_DATA_INPUT_"+ctrlName);
	  ctrl.val(sealData+",_,0,0");
	  
	  targetObject.attr("src",sealData);
}

function loadCtrlFeedbackDatas(){
	try{
		for(var i=0;i<ctrlSignArray.length;i++){
			var item = ctrlSignArray[i];
			var itemId = item.replace("DATA_","");
			$("#"+item).before($("<div id=\"SIGN_POS_CTRL_"+item+"_"+rand+"\" class='SIGN_POS_CTRL_"+item+"' ></div>"));
		}
		
		//加载金格签章
		for(var i=0;i<ctrlRandArray.length;i++){
			var name = ctrlRandArray[i];
			var ctrlSign = $("#CTRL_SIGN_"+name).val();
			if(ctrlSign!=""){
				var sp = ctrlSign.split(",");
				signatures.push({extra:{
	    		    icon_move: function(){
	    		    	return false;
	    		    }
	             },signatureid:sp[0],signatureData:sp[1]});
			}
		}
		
		//加载会签图章
		for(var i=0;i<ctrlRandArray.length;i++){
			var name = ctrlRandArray[i];
			var picData = $("#CTRL_PIC_SIGN_"+name).val();
			$("#SIGN_POS_CTRL_"+name);
			if(picData!=""){
				//加载图章
				var targetObject = $("#CTRL_PICSEAL_IMG_"+name);
				var sealDatas = picData.split(",");
				if(targetObject.length==0){
				 targetObject = $("<img id=\"CTRL_PICSEAL_IMG_"+name+"\" target=\""+name+"\" onerror=\"this.style.display = 'none'\"/>").appendTo($("#SIGN_POS_CTRL_"+name));
				}
				targetObject.attr("src",sealDatas[0]+","+sealDatas[1]).css({left:sealDatas[3]+"px",top:sealDatas[4]+"px"});
			}
		}
		
		//加载会签图章
		for(var i=0;i<ctrlRandArray.length;i++){
			var name = ctrlRandArray[i];
			var picData = $("#CTRL_PIC_SIGN_"+name).val();
			$("#SIGN_POS_CTRL_"+name);
			if(picData!=""){
				//加载图章
				var targetObject = $("#CTRL_PICSEAL_IMG_"+name);
				var sealDatas = picData.split(",");
				if(targetObject.length==0){
				 targetObject = $("<img id=\"CTRL_PICSEAL_IMG_"+name+"\" target=\""+name+"\" onerror=\"this.style.display = 'none'\"/>").appendTo($("#SIGN_POS_CTRL_"+name));
				}
				targetObject.attr("src",sealDatas[0]+","+sealDatas[1]).css({left:sealDatas[3]+"px",top:sealDatas[4]+"px"});
			}
		}
		
		//加载H5手写图章
		for(var i=0;i<ctrlRandArray.length;i++){
			var name = ctrlRandArray[i];
			var picData = $("#CTRL_H5HAND_"+name).val();
			$("#SIGN_POS_CTRL_"+name);
			if(picData!=""){
				//加载图章
				var targetObject = $("#CTRL_H5SEAL_IMG_"+name);
				var sealDatas = picData.split(",");
				if(targetObject.length==0){
				 targetObject = $("<img id=\"CTRL_H5SEAL_IMG_"+name+"\" target=\""+name+"\" onerror=\"this.style.display = 'none'\" style=\"max-width:100%\"/>").appendTo($("#SIGN_POS_CTRL_"+name));
				}
				targetObject.attr("src",sealDatas[0]+","+sealDatas[1]);
			}
		}
		
		//加载移动签批图
		for(var i=0;i<ctrlRandArray.length;i++){
			var name = ctrlRandArray[i];
			var picData = $("#CTRL_MOBIHAND_"+name).val();
			$("#SIGN_POS_CTRL_"+name);
			if(picData!=""){
				//加载图章
				var targetObject = $("#CTRL_MOBISEAL_IMG_"+name);
				if(targetObject.length==0){
				 targetObject = $("<img id=\"CTRL_MOBISEAL_IMG_"+name+"\" target=\""+name+"\" onerror=\"this.style.display = 'none'\" style=\"max-width:100%\"/>").appendTo($("#SIGN_POS_CTRL_"+name));
				}
				targetObject.attr("src",picData);
			}
		}
	}catch(e){alert(e);}
}


function viewlist(itemId){
	window.location = "viewlist.jsp?itemId="+itemId+"&frpSid="+frpSid;
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
		 targetObject = $("<img id=\"MOBILE_SEAL_IMG_"+itemId+"\" target=\""+itemId+"\" onerror=\"this.style.display='none'\" />").appendTo($("#MOBILE_SIGN_POS_"+itemId).css({position:"static"}));
		}
		targetObject.attr("src",sealDatas[0]+","+sealDatas[1]);
		
		if(md5Code!=sealDatas[2]){//验章失效，颜色变浅
			grayscale(targetObject);
		}else{
			
		}
	}
}

window.doAddMobileSeal=function(ctrlName,sealData){
	  var targetObject = $("#MOBILE_SEAL_IMG_"+ctrlName);
	  if(targetObject.length==0){
		  targetObject = $("<img id=\"MOBILE_SEAL_IMG_"+ctrlName+"\" target=\""+ctrlName+"\" />").appendTo($("#MOBILE_SIGN_POS_"+ctrlName).css({position:"static"}));
	  }
	  targetObject.css({opacity:1});
	 
	  
	  var ctrl = $("#"+ctrlName);
	  var fieldValid = ctrl.attr("validField");
	  var sp = fieldValid.split(",");
	  var datas = [];
	  if(fieldValid!=""){
		  for(var i=0;i<sp.length;i++){
			  datas.push(findTargetByTitle(sp[i]).val());
		  }
	  }
	  var md5Code = MD5(datas.join(""));
	  ctrl.val(sealData+","+md5Code+",0,0");
	  
	  targetObject.attr("src",sealData);
}


function doDelegate(){
	window.location = "delegate.jsp?frpSid="+frpSid+"&runId="+runId+"&flowId="+flowId;
}


//归档
function  archives(){

	if(window.confirm("是否要将该工作归档？（归档前请注意保存表单）")){
	//获取流程附件
		var json = tools.requestJsonRs(contextPath+"/flowRun/getFlowRunAttaches4Archives.action?runId="+runId);
		var attaches = [];
		for(var i=0;i<json.rtData.length;i++){
			attaches.push(json.rtData[i].sid);
		}
		window.location = "archives.jsp?title="+runName+"&attaches="+attaches+"&runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId+"&flowTypeId="+flowTypeId;
	}
	
}

function backTo(){
	if(!save(0,2)){
		return;
	}
	var url = contextPath+"/system/mobile/phone/workflow/prcs/backTo.jsp?frpSid="+frpSid+"&runId="+runId+"&flowId="+flowId;
	window.location = url;
}

function backToOther(){
	if(!save(0,2)){
		return;
	}
	var url = contextPath+"/system/mobile/phone/workflow/prcs/backToOther.jsp?frpSid="+frpSid+"&runId="+runId+"&flowId="+flowId;
	window.location = url;
}


function goBack(){
	if(userAgent.indexOf("DingTalk")!=-1 || userAgent.indexOf("MicroMessenger")!=-1){
		window.location = "../index.jsp?flowTypeId="+flowTypeId;
	}else{
		CloseWindow();
	}
}

function selectSingleUser(array){
	var a = array[0];
	var b = array[1];
	var iframe;
	if($("#selectUserIframe").length){
		iframe = $("#selectUserIframe");
	}else{
		iframe = $("<iframe id='selectUserIframe' src='' frameborder=0></iframe>");
		$("body").append(iframe);
	}
	
	$("#selectUserIframe").css({
		position: "fixed",
		left:0,
		top:0,
		right:0,
		bottom:0,
		width: '100%',
		height :'100%',
		zIndex:100000000000000
	}).show();
	var url = '/system/mobile/userselect/selectPage.jsp?inputName='+b+'&hiddenUserId='+a+'&single=true';
	$("#selectUserIframe").attr("src",url);
}

function selectUser(array){
	var a = array[0];
	var b = array[1];
	var iframe;
	if($("#selectUserIframe").length){
		iframe = $("#selectUserIframe");
	}else{
		iframe = $("<iframe id='selectUserIframe' src=''  frameborder=0></iframe>");
		$("body").append(iframe);
	}
	
	$("#selectUserIframe").css({
		position: "fixed",
		left:0,
		top:0,
		right:0,
		bottom:0,
		width: '100%',
		height :'100%',
		zIndex:100000000000000
	}).show();
	var url = '/system/mobile/userselect/selectPage.jsp?inputName='+b+'&hiddenUserId='+a+'&single=false&call_back_name=';
	$("#selectUserIframe").attr("src",url);
}

function selectDept(array){
	var a = array[0];
	var b = array[1];
	var iframe;
	if($("#selectDepartIframe").length){
		iframe = $("#selectDepartIframe");
	}else{
		iframe = $("<iframe id='selectDepartIframe' src='' frameborder=0 ></iframe>");
		$("body").append(iframe);
	}
	$("body").append(iframe);
	$("#selectDepartIframe").css({
		position: "fixed",
		left:0,
		top:0,
		right:0,
		bottom:0,
		width: '100%',
		height :'100%',
		zIndex:100000000000000
	}).show();
	var url = '/system/mobile/userselect/selectDepart.jsp?inputName='+b+'&hiddenUserId='+a+'&single=false&callback=';
	$("#selectDepartIframe").attr("src",url);
}

function selectSingleDept(array){
	var a = array[0];
	var b = array[1];
	var iframe;
	if($("#selectDepartIframe").length){
		iframe = $("#selectDepartIframe");
	}else{
		iframe = $("<iframe id='selectDepartIframe' src='' frameborder=0 ></iframe>");
		$("body").append(iframe);
	}
	$("body").append(iframe);
	$("#selectDepartIframe").css({
		position: "fixed",
		left:0,
		top:0,
		right:0,
		bottom:0,
		width: '100%',
		height :'100%',
		zIndex:100000000000000
	}).show();
	var url = '/system/mobile/userselect/selectDepart.jsp?inputName='+b+'&hiddenUserId='+a+'&single=true&callback=';
	$("#selectDepartIframe").attr("src",url);
}

function selectRole(array){
	var a = array[0];
	var b = array[1];
	var iframe;
	if($("#selectRoleIframe").length){
		iframe = $("#selectRoleIframe");
	}else{
		iframe = $("<iframe id='selectRoleIframe' src='' frameborder=0 ></iframe>");
		$("body").append(iframe);
	}
	$("body").append(iframe);
	$("#selectRoleIframe").css({
		position: "fixed",
		left:0,
		top:0,
		right:0,
		bottom:0,
		width: '100%',
		height :'100%',
		zIndex:100000000000000
	}).show();
	var url = '/system/mobile/userselect/selectRole.jsp?inputName='+b+'&hiddenUserId='+a+'&single=false&callback=';
	$("#selectRoleIframe").attr("src",url);
}

function selectSingleRole(array){
	var a = array[0];
	var b = array[1];
	var iframe;
	if($("#selectRoleIframe").length){
		iframe = $("#selectRoleIframe");
	}else{
		iframe = $("<iframe id='selectRoleIframe' src='' frameborder=0 ></iframe>");
		$("body").append(iframe);
	}
	$("body").append(iframe);
	$("#selectRoleIframe").css({
		position: "fixed",
		left:0,
		top:0,
		right:0,
		bottom:0,
		width: '100%',
		height :'100%',
		zIndex:100000000000000
	}).show();
	var url = '/system/mobile/userselect/selectRole.jsp?inputName='+b+'&hiddenUserId='+a+'&single=true&callback=';
	$("#selectRoleIframe").attr("src",url);
}


var dbclickCount = 0;
function quickFillData(ctrl){
	dbclickCount++;
	if(dbclickCount>1){
		var itemId = ctrl.getAttribute("id");
		var url = contextPath+"/system/mobile/phone/workflow/prcs/commonwords.jsp?itemId="+itemId;
		
		var wrapDiv = document.getElementById("userSelectDiv");
		var wrapFrame = document.getElementById("userSelectFrame");
		if(!wrapDiv){
			wrapDiv = document.createElement("div");
			wrapDiv.setAttribute("id", "userSelectDiv");
			wrapDiv.className = "userSelectDiv";
			wrapDiv.style.background="white";
			document.body.appendChild(wrapDiv);
			
			wrapFrame = document.createElement("iframe");
			wrapFrame.setAttribute("id", "userSelectFrame");
			wrapFrame.className = "userSelectFrame";
			wrapDiv.appendChild(wrapFrame);
			
			wrapDiv = document.getElementById("userSelectDiv");
			wrapFrame = document.getElementById("userSelectFrame");
		}
		
		wrapDiv.style.display = "";
		wrapFrame.contentWindow.location = url;
	}
}
setInterval("dbclickCount=0",400);







//加载移动签批图片
function LoadMobileHwData(){
	for(var i=0;i<mobileHwArray.length;i++){
		var itemId = mobileHwArray[i];
		$("#MOBILE_HW_POS_"+itemId).css({position:"inherit"});
		var targetObject = $("#MOBILE_HW_POS_IMG_"+itemId);
		if(targetObject.length==0){
		 targetObject = $("<img id=\"MOBILE_HW_POS_IMG_"+itemId+"\" />").appendTo($("#MOBILE_HW_POS_"+itemId));
		}
		targetObject.attr("src",mobileHwStores[i]).show();
		
	}
}

window.doAddH5Seal=function(itemId,sealData){
	if(itemId.indexOf("HAND_")!=-1){
		
		itemId = itemId.replace("HAND_","");
		var obj = $("#"+itemId);
		
		 var targetObject = $("#CTRL_H5SEAL_IMG_"+itemId);
		  if(targetObject.length==0){
			  targetObject = $("<img id=\"CTRL_H5SEAL_IMG_"+itemId+"\" target=\""+itemId+"\" style=\"max-width:100%\"/>").appendTo($(".SIGN_POS_CTRL_"+itemId));
		  }
		  
		  targetObject.css({opacity:1}).show();
		  
		  var ctrl = $("#CTRL_H5_DATA_INPUT_"+itemId);
		  ctrl.val(sealData+",_,0,0");
		  
		  targetObject.attr("src",sealData);
		
	}else{
		$("#H5_HW_POS_"+itemId).css({position:"inherit"});
		var targetObject = $("#H5_HW_POS_IMG_"+itemId);
		var itemObj = $("#"+itemId);
		var width = parseInt(itemObj.attr("w"));
		var height = parseInt(itemObj.attr("h"));
		if(targetObject.length==0){
			targetObject = $("<img id=\"H5_HW_POS_IMG_"+itemId+"\" height=\""+height+"px\" width=\""+width+"px\"/>").appendTo($("#H5_HW_POS_"+itemId));
		}else{
			targetObject.css({height:height,width:width});
		}
		targetObject.attr("src",sealData).show();
		$("#"+itemId).val(sealData);
	}
}

//加载移动签批图片
function LoadH5HwData(){
	for(var i=0;i<h5HwArray.length;i++){
		var itemId = h5HwArray[i];
		var targetItem = $("#"+itemId);
		$("#H5_HW_POS_"+itemId).css({position:"inherit"});
		var targetObject = $("#H5_HW_POS_IMG_"+itemId);
		if(targetObject.length==0){
			targetObject = $("<img id=\"H5_HW_POS_IMG_"+itemId+"\" style=\"padding-left:50px\" height=\""+targetItem.attr("h")+"\" width=\""+targetItem.attr("w")+"\"/>").appendTo($("#H5_HW_POS_"+itemId));
		}
		targetObject.attr("src",h5HwStores[i]).show();
		targetObject.css({height:targetItem.attr("h"),width:targetItem.attr("w")});
	}
}

function addH5HandSeal(itemId){
	var url = contextPath+"/system/mobile/phone/workflow/prcs/h5hw.jsp?itemId="+itemId;
	layer.open({
		  type: 1
		  ,content: "<iframe style='position:fixed;top:0px;left:0px;width:100%;height:100%' src='"+url+"'></iframe>"
		  ,anim: 'up'
		  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
		}); 
}

function h5CtrlSeal(itemId){
	addH5HandSeal("HAND_"+itemId);
}

function addMobileHandSeal(itemId){
	HandWrite(itemId);
}

function mobiCtrlSeal(itemId){
	var nItemId = "HAND_"+itemId;
	if(userAgent.indexOf("DingTalk")!=-1){//钉钉
		alert("手写签批暂不支持钉钉");
	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信
		alert("手写签批暂不支持微信");
	}else{
		if(window.external && window.external.open){//安卓
			var height = $("#"+itemId).attr("mobih");
			var width = $("#"+itemId).attr("mobiw");
			window.external.HandWrite(nItemId,width+"",height+"");
		}else{//ios
			var height = $("#"+itemId).attr("mobih");
			var width = $("#"+itemId).attr("mobiw");
			document.location = "zatp:{\"method\":\"HandWrite:\",\"params\":[\""+nItemId+"\",\""+width+"\",\""+height+"\"]}";
		}
	}
}

function RenderSingleHandWrite(base64Data,itemId){
	if(itemId.indexOf("HAND_")!=-1){
		itemId = itemId.replace("HAND_","");
		
		 var targetObject = $("#CTRL_MOBISEAL_IMG_"+itemId);
		  if(targetObject.length==0){
			  targetObject = $("<img id=\"CTRL_MOBISEAL_IMG_"+itemId+"\" target=\""+itemId+"\" style=\"max-width:100%\"/>").appendTo($(".SIGN_POS_CTRL_"+itemId));
		  }
		  
		  targetObject.css({opacity:1}).show();
		  
		  var ctrl = $("#CTRL_MOBI_DATA_INPUT_"+itemId);
		  ctrl.val(base64Data);
		  
		  targetObject.attr("src",base64Data);
	}else{
		$("#MOBILE_HW_POS_"+itemId).css({position:"inherit"});
		var targetObject = $("#MOBILE_HW_POS_IMG_"+itemId);
		if(targetObject.length==0){
		 targetObject = $("<img id=\"MOBILE_HW_POS_IMG_"+itemId+"\" />").appendTo($("#MOBILE_HW_POS_"+itemId));
		}
		targetObject.attr("src",base64Data).show();
		$("#"+itemId).val(base64Data);
	}
	
}

/**
 * 手写签批
 * @param callback
 * @param filesArray
 */
function HandWrite(itemId){
	if(userAgent.indexOf("DingTalk")!=-1){//钉钉
		alert("手写签批暂不支持钉钉");
	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信
		alert("手写签批暂不支持微信");
	}else{
		if(window.external && window.external.open){//安卓
			var height = $("#"+itemId).attr("h");
			var width = $("#"+itemId).attr("w");
			window.external.HandWrite(itemId,width+"",height+"");
		}else{//ios
			var height = $("#"+itemId).attr("h");
			var width = $("#"+itemId).attr("w");
			document.location = "zatp:{\"method\":\"HandWrite:\",\"params\":[\""+itemId+"\",\""+width+"\",\""+height+"\"]}";
		}
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



function isImage(ext){
	ext = ext.toLowerCase();
	if(ext=="gif" || ext=="jpg" || ext=="png" || ext=="bmp"){
		return true;
	}
	return false;
}


function PrePicExplore(curId){
	var attachIds = [];
	var attachNames=[];
	$("img[class=pic]").each(function(i,obj){
		attachIds.push(obj.getAttribute("attachId"));
		attachNames.push(obj.getAttribute("attachName"));
	});
	PicExplore(attachIds.join(","),attachNames.join(","),curId);
}



//快速预览
function quickView(attId){
    var url =contextPath+"/system/core/ntko/officePreview.jsp?attachId="+attId;
    OpenWindow("快速预览",url);

}

//移动签章盖章操作
window.docNumOpening=function(ctrl){
	$(window).scrollLeft(0);
	var width = parseInt($(window).width());
	var height = parseInt($(window).height());
	var scrollTop = parseInt($(window).scrollTop());
	var frmWidth = width-50;
	var frmHeight = height-300;
	$("#mobileSealFrm").css({height:frmHeight,width:frmWidth})
	.css({top:scrollTop+(height-frmHeight)/2,left:(width-frmWidth)/2});
	
	var json = tools.requestJsonRs(contextPath+"/docNumController/checkExistsDocNum.action?flowId="+flowId+"&runId="+runId);
	  if(json.rtData==true){//如果存在，则弹出修改文号界面
		  var url = contextPath+"/system/mobile/phone/workflow/prcs/docnumEdit.jsp?flowId="+flowId+"&runId="+runId+"&itemId="+ctrl.getAttribute("id");
		  $("#mobileSealFrm").attr("src",url).show();
	  }else{//不存在，则弹出文号生成界面
		  var url = contextPath+"/system/mobile/phone/workflow/prcs/docnum.jsp?flowId="+flowId+"&runId="+runId+"&itemId="+ctrl.getAttribute("id");
		  $("#mobileSealFrm").attr("src",url).show();
	  }
}



window.alert = function(name){
    var iframe = document.createElement("IFRAME");
    iframe.style.display="none";
    iframe.setAttribute("src", 'blank');
    document.documentElement.appendChild(iframe);
    window.frames[0].window.alert(name);
    iframe.parentNode.removeChild(iframe);
};
window.confirm = function (message) {
    var iframe = document.createElement("IFRAME");
    iframe.style.display = "none";
    iframe.setAttribute("src", 'blank');
    document.documentElement.appendChild(iframe);
    var alertFrame = window.frames[0];
    var result = alertFrame.window.confirm(message);
    iframe.parentNode.removeChild(iframe);
    return result;
};




//渲染流程办理情况
function renderFlowStep(){
	
	var url = contextPath+"/flowInfoChar/getFlowRunViewGraphicsData.action";
	var json = tools.requestJsonRs(url,{runId:runId});
	if(json.rtState){
		//渲染步骤信息
		var prcsList = json.rtData.prcsList;
		//alert(prcsList.length);
		render(prcsList);
		
	}
}
var tbody;
function render(prcsList){
	tbody = $("#tbody");
	var group = groupBy(prcsList);
	var render="";
	render += "<div class='buzhou'>";
	for(var i=0;i<group.length;i++){
		var set = group[i];
		var rows = 0;
		for(var key in set){
			rows++;
		}
		render+="<div class='buzhou_head'>第"+(i+1)+"步：";
		for(var key in set){
			var arr = set[key];
			if(arr[0].flowPrcsId!=0){
				render+=arr[0].prcsName+"</div>";
			}
			render+="<div class='buzhou_content'>";
			var title = "";
			for(var j=0;j<arr.length;j++){
				var prcsInfo = arr[j];
				title+="<ul style='padding-left: 20px;border-bottom: 1px solid #F0F0F0;padding-bottom: 20px;'>";
				if(prcsInfo.topFlag==1){//主办
					title+="<li>主办："+prcsInfo.prcsUserName+"</li>";
				}else{//经办
					title+="<li>经办："+prcsInfo.prcsUserName+"</li>";
				}

				if(prcsInfo.flag==1){//未接收
					title+="<li><span style='color:gray;margin-right: 20px;'>未接收</span></li>";
				}else if(prcsInfo.flag==2){//办理中
					title+="<li><span style='color:red;margin-right: 20px;'>办理中</span> 用时："+prcsInfo.passedTime+"</li>";
				}else if(prcsInfo.flag==3 || prcsInfo.flag==4){//已办结
					title+="<li><span style='color:green;margin-right: 20px;'>已办结</span> 用时："+prcsInfo.passedTime+"</li>";
				}

				if(prcsInfo.beginTimeDesc!=""){
					title+="<li>接收时间："+prcsInfo.beginTimeDesc+"</li>";
				}
				if(prcsInfo.endTimeDesc!=""){
					title+="<li>办结时间："+prcsInfo.endTimeDesc+"</li>";
				}
				title+="</ul>";
			}
			render+=title;
			
			render+="</div>";
			
		}
		
		render+="</div>";	  
		
		
	}
	render+="</div>";	  
   // alert(render);
	tbody.html(render);
}

function groupBy(prcsList){
	var group = new Array;
	var maxPrcsId = prcsList[prcsList.length-1].prcsId;
	for(var i=1;i<=maxPrcsId;i++){
		var set = {};
		for(var j=0;j<prcsList.length;j++){
			var prcsId = prcsList[j].prcsId;
			var flowPrcsId = prcsList[j].flowPrcsId;
			if(prcsId!=i){
				continue;
			}
			var id = prcsId+""+flowPrcsId;
			var arr = set[id];
			if(!arr){
				arr = new Array();
			}
			arr.push(prcsList[j]);
			set[id] = arr;
		}
		group.push(set);
	}
	return group;
}



//列表控件单个item选择数据
window.xListItemSelectData=function(obj,itemId){
	  var valueModel=$(obj).attr("valueModel");
	  var tdIndex=$(obj).parent().attr("index");
	  var trIndex=$(obj).parent().parent().attr("index");
	  
	  var model=$("#XLIST_"+itemId).attr("model");
	  var modelJson=tools.strToJson(model);
	  var  title=modelJson[tdIndex-1].title;
	  
	  //alert("valueModel:"+valueModel+",tdIndex:"+tdIndex+",trIndex:"+trIndex+",title:"+title);
	  var url=contextPath+"/system/mobile/phone/workflow/prcs/xListDataList.jsp?valueModel="+valueModel+"&tdIndex="+tdIndex+"&trIndex="+trIndex+"&title="+title+"&itemId="+itemId;
	  
	  layer.open({
		  type: 1
		  ,content: "<iframe style='position:fixed;top:0px;left:0px;width:100%;height:100%' src='"+url+"'></iframe>"
		  ,anim: 'up'
		  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
		}); 
	  
	  
}



function delCB(signatureid, signData){
   if(Signature.list != null && Signature.list[signatureid] != null){
	   var signatureCreator = Signature.create();
	   signatureCreator.removeSignature(signData.documentid, signatureid);
   }
   return true;
}

function addSeal(item,seal_id) {
	if($("#"+item).val()!=""){
		alert("已存在签章数据");
		return;
	}
	var itemId = item.split("_")[1];
	var signatureCreator = Signature.create();
	var fieldString = $("#"+item).attr("fieldString");
	if(fieldString==""){
		fieldString = [];
	}else{
		fieldString = fieldString.split(",");
	}
	signatureCreator.run({
		protectedItems : fieldString,//设置定位页面DOM的id，自动查找ID，自动获取保护DOM的kg-desc属性作为保护项描述，value属性为保护数据。不设置，表示不保护数据，签章永远有效。
		position : "SIGN_POS_"+item,//设置盖章定位dom的ID，必须设置
		autoCert : false,
		okCall : function(fn, image) {//点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
			var data = this.getSignatureid()+","+this.getSignatureData();
			fn(true);
			$("#"+item).val(data);
			//kg-img-div-152527746582307091-SIGN_POS_DATA_6
			var imgData = $("#kg-img-"+this.getSignatureid()).attr("src");
			var top = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_"+item).css("marginTop");
			var left = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_"+item).css("marginLeft");
			var width = $("#kg-img-"+this.getSignatureid()).width()+"px";
			var height = $("#kg-img-"+this.getSignatureid()).height()+"px";
			$("#SEAL_"+itemId).val(imgData+"|"+left+","+top+"|"+width+","+height);
			bindSignEvent();//绑定签章事件
		},
		cancelCall : function() {//点击取消后的回调方法
			
		}
	});
}


function addCtrlSeal(item,seal_id) {
	if($("#CTRL_SIGN_DATA_INPUT_O_"+item).val()!=""){
		alert("已存在签章数据");
		return;
	}
	
	var signatureCreator = Signature.create();
	signatureCreator.run({
		position : "SIGN_POS_CTRL_"+item+"_"+rand,//设置盖章定位dom的ID，必须设置
		autoCert : false,
		okCall : function(fn, image) {//点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
			var data = this.getSignatureid()+","+this.getSignatureData();
			fn(true);
			$("#"+item).attr("rand",rand);
			$("#CTRL_SIGN_DATA_INPUT_O_"+item).val(data);
			var imgData = $("#kg-img-"+this.getSignatureid()).attr("src");
			var top = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_CTRL_"+item+"_"+rand).css("marginTop");
			var left = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_CTRL_"+item+"_"+rand).css("marginLeft");
			var width = $("#kg-img-"+this.getSignatureid()).width()+"px";
			var height = $("#kg-img-"+this.getSignatureid()).height()+"px";
			$("#CTRL_SIGN_DATA_INPUT_I_"+item).val(imgData+"|"+left+","+top+"|"+width+","+height);
			bindSignEvent();//绑定签章事件
		},
		cancelCall : function() {//点击取消后的回调方法
			
		}
	});
}

function handWrite(item  , color) {
	if($("#"+item).val()!=""){
		alert("已存在签章数据");
		return;
	}
	var itemId = item.split("_")[1];
	var signatureCreator = Signature.create();
	var fieldString = $("#"+item).attr("fieldString");
	if(fieldString==""){
		fieldString = [];
	}else{
		fieldString = fieldString.split(",");
	}
	
	signatureCreator.handWriteDlg({
		image_height: "2",
		image_width: "4",
		onBegin: function() {
			
		},
		onEnd: function() {
			
		}
	}, function(param){
		//alert(param.imageData);
		signatureCreator.runHW(param, {
			protectedItems:fieldString,//设置定位页面DOM的id，自动查找ID，自动获取保护DOM的kg-desc属性作为保护项描述，value属性为保护数据。不设置，表示不保护数据，签章永远有效。
			position: "SIGN_POS_"+item,//设置盖章定位dom的ID，必须设置
			okCall: function(fn) {//点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
				var data = this.getSignatureid()+","+this.getSignatureData();
				fn(true);
				$("#"+item).val(data);
				var imgData = $("#kg-img-"+this.getSignatureid()).attr("src");
				var top = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_"+item).position().top;
				var left = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_"+item).position().left;
				var width = $("#kg-img-"+this.getSignatureid()).width()+"px";
				var height = $("#kg-img-"+this.getSignatureid()).height()+"px";
				$("#SEAL_"+itemId).val(imgData+"|"+left+","+top+"|"+width+","+height);
				bindSignEvent();//绑定签章事件
			},
			cancelCall : function() {//点击取消后的回调方法
				
			}
		});
	});
}

function handCtrlWrite(item ,color) {
	if($("#CTRL_SIGN_DATA_INPUT_O_"+item).val()!=""){
		alert("已存在签章数据");
		return;
	}
	
	var signatureCreator = Signature.create();
	signatureCreator.handWriteDlg({
		image_height: "2",
		image_width: "4",
		onBegin: function() {
			
		},
		onEnd: function() {
			
		}
	}, function(param){
		signatureCreator.runHW(param, {
			position: "SIGN_POS_CTRL_"+item+"_"+rand,//设置盖章定位dom的ID，必须设置
			okCall: function(fn) {//点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
				var data = this.getSignatureid()+","+this.getSignatureData();
				fn(true);
				$("#"+item).attr("rand",rand);
				$("#CTRL_SIGN_DATA_INPUT_O_"+item).val(data);
				var imgData = $("#kg-img-"+this.getSignatureid()).attr("src");
				var top = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_CTRL_"+item+"_"+rand).css("marginTop");
				var left = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_CTRL_"+item+"_"+rand).css("marginLeft");
				var width = $("#kg-img-"+this.getSignatureid()).width()+"px";
				var height = $("#kg-img-"+this.getSignatureid()).height()+"px";
				$("#CTRL_SIGN_DATA_INPUT_I_"+item).val(imgData+"|"+left+","+top+"|"+width+","+height);
				bindSignEvent();//绑定签章事件
			},
			cancelCall : function() {//点击取消后的回调方法
				
			}
		});
	});
	
}


function bindSignEvent(){
	Signature.bind({
		remove:function(fn){//签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,
			var signatureid = this.getSignatureid();
			$("input[xtype=xseal][value!='']").each(function(i,obj){
				var sid = $(obj).val().split(",");
				if(sid[0]==signatureid){
					
					$(obj).val("");
					var itemId = $(obj).attr("id").split("_")[1];
					$("#SEAL_"+itemId).val("");
				}
			});
			$("textarea[xtype=xfeedback]").each(function(i,obj){
				var item = $(obj).attr("id");
				var sid = $("#CTRL_SIGN_DATA_INPUT_O_"+item).val().split(",");
				if(sid[0]==signatureid){
					$("#CTRL_SIGN_DATA_INPUT_O_"+item).val("");
					$("#CTRL_SIGN_DATA_INPUT_I_"+item).val("");
				}
			});
			fn(true);//保存成功后必须回调fn(true/false)传入true/false分别表示保存成功和失败
		},
		update:function(fn){//签章数据有变动时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,执行后必须回调fn(true/false)，传入true/false分别表示保存成功和失败
			fn(true);
			var signatureid = this.getSignatureid();
			var data = this.getSignatureData();
			$("input[xtype=xseal][value!='']").each(function(i,obj){
				var sid = $(obj).val().split(",");
				if(sid[0]==signatureid){
					$(obj).val(signatureid+","+data);
					var itemId = $(obj).attr("id").split("_")[1];
					var imgData = $("#kg-img-"+signatureid).attr("src");
					var top = $("#kg-img-div-"+signatureid+"-SIGN_POS_"+$(obj).attr("id")).css("marginTop");
					var left = $("#kg-img-div-"+signatureid+"-SIGN_POS_"+$(obj).attr("id")).css("marginLeft");
					var width = $("#kg-img-"+signatureid).width()+"px";
					var height = $("#kg-img-"+signatureid).height()+"px";
					$("#SEAL_"+itemId).val(imgData+"|"+left+","+top+"|"+width+","+height);
				}
			});
			$("textarea[xtype=xfeedback]").each(function(i,obj){
				var item = $(obj).attr("id");
				var sid = $("#CTRL_SIGN_DATA_INPUT_O_"+item).val().split(",");
				var rand = $(obj).attr("rand");
				if(sid[0]==signatureid){
					$("#CTRL_SIGN_DATA_INPUT_O_"+item).val(signatureid+","+data);
					var imgData = $("#kg-img-"+signatureid).attr("src");
					var top = $("#kg-img-div-"+signatureid+"-SIGN_POS_CTRL_"+item+"_"+rand).css("marginTop");
					var left = $("#kg-img-div-"+signatureid+"-SIGN_POS_CTRL_"+item+"_"+rand).css("marginLeft");
					var width = $("#kg-img-"+signatureid).width()+"px";
					var height = $("#kg-img-"+signatureid).height()+"px";
					$("#CTRL_SIGN_DATA_INPUT_I_"+item).val(imgData+"|"+left+","+top+"|"+width+","+height);
				}
			});
		}
	});
}