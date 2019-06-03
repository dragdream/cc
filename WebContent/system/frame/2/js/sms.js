//消息常量
var QType = {
		Device:{
			Web:1,
			Im:2,
			Android:3,
			Iphone:4
		},
		REGIST_IDENTITY:1,
		MESSAGE_HANDLE:2,
		SMS_HANDLE:3,
		FILE_HANDLE:4,
		BREAK:5,
		USER_ONLINE:6,
		USER_OFFLINE:7
};

var msgFrame = {
	messageDlgs:{},//消息对话框
	messageDlgTips:{},//消息提示框
	messageQueue:{},//消息队列
	identities:{},//身份队列
	imageComplete:{},//图片加载完毕队列
	/**
	 * 创建对话框提醒
	 */
	createDlgTip:function(msgJson){
		//初始化身份
		var identity = this.identities[msgJson.from];
		if(!identity){
			var url = contextPath+"/personManager/getPsersonInfoByUserId.action";
			var json = tools.requestJsonRs(url,{userId:msgJson.from});
			this.identities[msgJson.from] = json.rtData;
			identity = json.rtData;
		}
		
		var dlgTipDiv = $('<div style="margin: 0px 5px 5px 0px; width: 180px; height: 24px; position:relative;z-index:1000000000" class="fc-event fc-event-vert fc-event-draggable fc-event-start fc-event-end fc-event-color-red ui-draggable ui-resizable">'
	 			+  ' <div class="fc-event-inner"> ' 
	 			 	+ ' <div class="fc-event-time" style="line-height:24px;">  ' 
						+ ' <span ><i class="icon-user"></i>'+identity.userName+'</span> ' 
						+ ' <span class=""> &nbsp;&nbsp;&nbsp;&nbsp;发来新消息</span> ' 
						+ ' <span class="count">1</span></div> '
					+ ' </div> ' 
					+ '  <div class="fc-event-bg"></div> '
			+ '  </div>') ;
		this.messageDlgTips[identity.userId] = dlgTipDiv;
		dlgTipDiv.click(this.tipClickEvent);
		dlgTipDiv.attr("qUserId",identity.userId);
		$("#smsInfo").prepend(dlgTipDiv);
		
	},
	updateDlgTip:function(msgJson){
		var dlgTipDiv = this.messageDlgTips[msgJson.from];
		var countSpan = dlgTipDiv.find(".count:first");
		countSpan.html(this.messageQueue[msgJson.from].length);
	},
	tipClickEvent:function(){
		var qUserId = $(this).attr("qUserId");
		msgFrame.createDlg(qUserId);
		$(this).remove();
		msgFrame.messageDlgTips[qUserId] = undefined;
		msgFrame.renderMsgContent(qUserId);
	},
	createDlg:function(qUserId){
		
		//初始化身份
		var identity = this.identities[qUserId];
		if(!identity){
			var url = contextPath+"/personManager/getPsersonInfoByUserId.action";
			var json = tools.requestJsonRs(url,{userId:qUserId});
			this.identities[qUserId] = json.rtData;
			identity = json.rtData;
		}
		
		//先判断是否有dlg对话框
		var dlg = msgFrame.messageDlgs[qUserId];
		if(dlg){//如果对话框不存在
			msgFrame.renderMsgContent(qUserId);
			return ;
		}
		
		var avatar = identity.avatar;
		if(avatar==null || avatar=="" || avatar=="0"){
			avatar = systemImagePath+"/default_avatar.gif";
		}else{
			avatar = contextPath+"/attachmentController/downFile.action?id="+avatar+"&model=person";
		}
		
		var dlg = $("<div class='sms_frame'>" +
				"<div class='sms_top_layout'>" +
					"<div class='sms_quick_pic'><img style='height:35px;width:35px;border:1px solid #706c6a' src='"+avatar+"' /></div>" +
					"<div class='sms_title'>与 "+identity.userName+" 对话中</div>" +
					"<div class='sms_win_opers'></div>" +
					"<div style='clear:both'></div>" +
				"</div>" +
				"<div class='sms_left_layout'>" +
					"<div class='sms_datacontent'>" +
					"</div>" +
					"<div class='sms_toolbar'>" +
					"</div>" +
					"<textarea class='sms_entercontent' onkeydown='msgFrame.enterMsg(\""+qUserId+"\")' contentEditable='true'>" +
					"</textarea>" +
				"</div>" +
				"<div class='sms_footer_layout'>" +
					"<button class='sms_close_btn' onclick='msgFrame.closeDlgEvent(\""+qUserId+"\")'>关闭</button>&nbsp;&nbsp;" +
					"<button class='sms_enter_btn' onclick='msgFrame.sendMsgEvent(\""+qUserId+"\")'>发送(Ctrl+Enter)</button>" +
				"</div>" +
				"</div>");
		$("body").append(dlg);
		dlg.css({left:($(window).width()-dlg.width()-250),top:($(window).height()-dlg.height()-10)});
		dlg.draggable({handle:".sms_top_layout",containment:"parent",start:function(){
			$(this).css({opacity:0.5}).children("div").hide();
		},stop:function(){
			$(this).css({opacity:1}).children("div").show();
			var dataContentDiv = dlg.find(".sms_datacontent:first");
			dataContentDiv.scrollTop(10000000);
		}}).mousedown(function(){
			for(var key in msgFrame.messageDlgs){
				if(msgFrame.messageDlgs[key]){
					msgFrame.messageDlgs[key].css({zIndex:1000000});
				}
			}
			$(this).css({zIndex:1000001});
			var contentDiv = $(this).find(".sms_entercontent:first");
			contentDiv.focus();
		});
		this.messageDlgs[qUserId] = dlg;
	},
	closeDlgEvent:function(qUserId){
		var dlg = msgFrame.messageDlgs[qUserId];
		msgFrame.messageDlgTips[qUserId] = undefined;
		msgFrame.messageDlgs[qUserId] = undefined;
		msgFrame.messageQueue[qUserId] = undefined;
		dlg.remove();
	},
	sendMsgEvent:function(qUserId){
		var date = new Date();
		var dateStr = date.pattern("yyyy-MM-dd hh:mm:ss");
		
		var dlg = msgFrame.messageDlgs[qUserId];
		var contentDiv = dlg.find(".sms_entercontent:first");
		var dataContentDiv = dlg.find(".sms_datacontent:first");
		
		var url = contextPath+"/messageManage/sendMessage.action";
		
		if($.trim(contentDiv.val())==""){
			return ;
		}
		var fontStyle = dlg.find(".sms_fontstyle:first").val();
		var fontSize = dlg.find(".sms_fontsize:first").val();
		
		var content = contentDiv.val();
		var msg = {fs:fontSize,ff:fontStyle,c:content};
		dataContentDiv.append("<div style='color:#008040;margin-bottom:5px;'>"+window.userName+"&nbsp;"+dateStr+"</div>");
		dataContentDiv.append($("<div style='margin-bottom:10px;'></div>").append(this.msgReceiveFilter(msg)));
		
		
		tools.requestJsonRs(url,{content:this.msgSendFilter(content),toUserId:qUserId,fontSize:fontSize,fontFmly:fontStyle},true);
		
		contentDiv.html("");
		contentDiv.attr("value","");
		contentDiv.focus();
		dataContentDiv.scrollTop(10000000);
	},
	renderMsgContent:function(qUserId){
		var dlg = msgFrame.messageDlgs[qUserId];
		var dataContentDiv = dlg.find(".sms_datacontent:first");
		var messageQueue = this.messageQueue[qUserId];
		if(messageQueue){
			for(var i=0;i<messageQueue.length;i++){
				var device = "";
				switch(parseInt(messageQueue[i].dvc))
				{
				case 1:device="网页在线";break;
				case 2:device="PC在线";break;
				case 3:device="安卓在线";break;
				case 4:device="iOS在线";break;
				}
				var identity = this.identities[messageQueue[i].from];
				
				dataContentDiv.append("<div style='color:#006efe;margin-bottom:5px;'>"+identity.userName+"&nbsp;"+messageQueue[i].time+"&nbsp;来自&nbsp;"+device+"</div>");
				dataContentDiv.append($("<div style='margin-bottom:10px;'></div>").append(this.msgReceiveFilter(messageQueue[i])));
				dataContentDiv.scrollTop(10000000);
			}
		}
		this.messageQueue[qUserId] = new Array();
	},
	enterMsg:function(qUserId){
		if(window.event.ctrlKey && window.event.keyCode == 13){
			msgFrame.sendMsgEvent(qUserId);
		}
	},
	msgSendFilter:function(msg){
		msg = msg.replace(/&lt;/gi,"<").replace(/&gt;/gi,">");
		return msg;
	},
	msgReceiveFilter:function(msg){
		var msgFlag = msg.msgFlag||0;//如果没有msgFlag，则认为是普通消息监听
		var render = "";
		switch(msgFlag)
		{
		case 0://普通消息渲染
			var regexp = /\[#FILE:[^\]]+:[0-9]+\]/gi;
			var content = msg.c;
			var fontSize = msg.fs;
			var fontStyle = msg.ff;
			
			content = content.replace(/</gi,"&lt;").replace(/>/gi,"&gt;").replace(/\n\r/gi,"<br/>");
			content = content.replace(/\n/gi,"<br/>");
			
			var imgBlock;
			while(imgBlock=regexp.exec(msg.c)){
				imgBlock+="";
				var sp = imgBlock.split(":");
				content = content.replace(imgBlock,"<span style=\"cursor:pointer;color:red\" onclick=\"msgFrame.downloadFile('" + sp[2].replace("]","") + "')\">" + sp[1] + "</span>");
			}
			render = "<span style='font-size:"+fontSize+"px;font-family:"+fontStyle+"'>"+content+"</span>";
			break;
			
		case 1://对方传输文件，暂不做处理
			/**
			var fileName = msg.fileName;
			var fileSize = msg.fileSize;
			var fileGuid = msg.fileGuid;//文件唯一标识位置
			render+="<span id=''>";
			render+="";
			render+="</span>";
			*/
			break;
		}
		
		
		return render;
	},
	fontStyleChange:function(userId,obj){
		var dlg = msgFrame.messageDlgs[userId];
		var contentDiv = dlg.find(".sms_entercontent:first");
		contentDiv.css({fontFamily:obj.value});
	},
	fontSizeChange:function(userId,obj){
		var dlg = msgFrame.messageDlgs[userId];
		var contentDiv = dlg.find(".sms_entercontent:first");
		contentDiv.css({fontSize:obj.value+"px"});
	},
	playSound:function(type){
		if(type==1){//消息提示音
			try{
				var sound = $("#__sound");
				if(sound.length==0){
					sound = $("<embed id='__sound' src='"+contextPath+"/system/frame/inc/msg.wav' autostart=true ></embed>").hide();
					$("body").append(sound);
				}else{
					document.getElementById("__sound").play();
				}
		    }catch(e){
		    	
		    }
		}else if(type==2){//内部事务提示音
			try{
		    	var sound = $("#__sound1");
				if(sound.length==0){
					sound = $("<embed id='__sound1' src='"+contextPath+"/system/frame/inc/alert.mp3' autostart=true ></embed>").hide();
					$("body").append(sound);
				}else{
					document.getElementById("__sound1").play();
				}
		    }catch(e){
		    	
		    }
		}
	},
	showImage:function(id){
		$("#"+id).html("<img style='cursor:pointer;width:35px;height:35px'  src='"+systemImagePath+"/filetype/image_loading.gif' />");
		var json = tools.requestJsonRs(contextPath+"/imAttachment/checkFileComplele.action?id="+id+"&ext=jpg",{});
		switch(json.rtData){
		case -1://图片不存在
			$("#"+id).html("<img style='cursor:pointer'  src='"+systemImagePath+"/filetype/not_found.jpg' />");
			break;
		case 0://图片传输中
			msgFrame.showImage0(id);//继续轮询
			break;
		case 1://已传输完毕
			$("#"+id).html("<img style='cursor:pointer' onclick='msgFrame.showImage(\""+id+"\")' src='"+systemImagePath+"/filetype/file_extension_jpg.png' />");
			top.$.picExplore({src:contextPath+"/imAttachment/downFile.action?id="+id+"&ext=jpg"});
			break;
		}
	},
	showImage0:function(id){
		setTimeout(function(){
			var json = tools.requestJsonRs(contextPath+"/imAttachment/checkFileComplele.action?id="+id+"&ext=jpg",{});
			switch(json.rtData){
			case -1://图片不存在
				$("#"+id).html("<img style='cursor:pointer'  src='"+systemImagePath+"/filetype/not_found.jpg' />");
				break;
			case 0://图片传输中
				msgFrame.showImage0(id);//继续轮询
				break;
			case 1://已传输完毕
				$("#"+id).html("<img style='cursor:pointer' onclick='msgFrame.showImage(\""+id+"\")' src='"+systemImagePath+"/filetype/file_extension_jpg.png' />");
				top.$.picExplore({src:contextPath+"/imAttachment/downFile.action?id="+id+"&ext=jpg"});
				break;
			}
		},2000);
	},
	downloadFile:function(sid){
		var downloadFrame = $("#DOWN_LOAD_FRAME___");
		if(downloadFrame.length==0){
			downloadFrame = $("<iframe style='display:none'></iframe>");
			$("body").append(downloadFrame);
		}
		downloadFrame.attr("src",contextPath+"/attachmentController/downFile.action?id="+sid);
	}
};

$(function(){
	var url = contextPath+"/messageManage/getOfflineMessages.action";
	tools.requestJsonRs(url,{},true,function(json){
		var list = json.rtData;
		for(var i=0;i<list.length;i++){
			var data = {};
			data["t"] = "MESSAGE_HANDLE";
			data["from"] = list[i].fromId;
			data["to"] = list[i].toId;
			data["dvc"] = 1;
			data["time"] = list[i].sendTimeDesc;
			data["c"] = list[i].content;
			var queue = msgFrame.messageQueue[list[i].fromId];
			if(!queue){
				queue = new Array();
			}
			queue.push(data);
			msgFrame.messageQueue[list[i].fromId] = queue;
			var dlgTip = msgFrame.messageDlgTips[data["from"]];
			if(!dlgTip){//如果不存在tip，则创建一个tip
				msgFrame.createDlgTip(data);
			}else{
				msgFrame.updateDlgTip(data);
			}
		}
		if(list.length!=0){
			msgFrame.playSound(1);
		}
	});
});

function socketHandler(jsonStr){
	jsonStr = jsonStr.replace(/\n/gi,"\\n");
	var json = eval("("+jsonStr+")");
	if(json.t==QType.MESSAGE_HANDLE){//消息提醒
		var userId = json.from;
		var array = msgFrame.messageQueue[userId];
		if(!array){
			array = new Array();
		}
		msgFrame.messageQueue[userId] = array;
		array.push(json);
		
		//先判断是否有dlg对话框
		var dlg = msgFrame.messageDlgs[userId];
		var dlgTip = msgFrame.messageDlgTips[userId];
		if(!dlg){//如果对话框不存在
			if(!dlgTip){//如果不存在tip，则创建一个tip
				msgFrame.createDlgTip(json);
			}else{
				msgFrame.updateDlgTip(json);
			}
		}else{//如果对话框存在，则渲染对话框的消息
			msgFrame.renderMsgContent(userId);
		}
		msgFrame.playSound(1);
	}
	else if(json.t==QType.SMS_HANDLE){//短消息发送
		smsAlert();
	}
	
	//接口自定义扩展，针对客户进行消息二次开发的回调函数
	//if(socketHandlerInterface){
	//	socketHandlerInterface(json);
	//}
}

function openDetail(url,sid,obj){
	$(obj).remove();
	tools.requestJsonRs(contextPath+"/sms/updateReadFlag.action?ids="+sid);
	
	//获取数据
	var total = loader.getTotal();
	if(total==0){
		$("#smsbox").hide();
		$(".tip_zhezhao").hide();
	}
	if(url==undefined || url==null || url==""){
		return;
	}
	window.openFullWindow(url,"");
}

//弹出短消息框
function smsAlert(){
    var url = contextPath+"/sms/popup.action";
    tools.requestJsonRs(url,{},true);
    if(!window.loader){
    	window.loader = new lazyLoader({
			url:contextPath+'/sms/getSmsBoxDatas.action',
			placeHolder:'loadMore',
			contentHolder:'smsBoxContent',
			rowRender:function(rowData){
				var render = [];
				render.push("<div class=\"item\" onclick=\"openDetail('"+rowData.remindUrl+"','"+rowData.smsSid+"',this)\">");
				render.push("<div>");
				render.push("<span class=\"left0\">"+(rowData.fromUser==""?"系统":rowData.fromUser)+"</span>");
				render.push("<span class=\"right0\">"+(rowData.moduleNoDesc?"["+rowData.moduleNoDesc+"]":"")+"&nbsp;&nbsp;"+rowData.remindTimeDesc+"</span>");
				render.push("<div style=\"clear:both\"></div>");
				render.push("</div>");
				render.push("<div style=\"margin-top:5px\">"+rowData.content+"</div>");
				render.push("</div>");
				
				return render.join("");
			},
			onLoadSuccess:function(){
				$("#smsBoxContent").append($("#loadMore").show());
			    $(".tip_zhezhao").show();
				if($(".tip_zhezhao").length==0){
					$("body").append("<div class='tip_zhezhao' style='width:100%;height:100%;z-index: 9999;position: fixed;filter:Alpha(opacity=60);background-color: #000;top: 0;left: 0;opacity: 0.6;'></div>");
				}
				$(".smsbox").fadeIn(200);
			},
			onNoData:function(){
				$("#loadMore").hide();
			}
		});
    }else{
    	loader.reload();
    }
    //$("#smsbox").css({top:($(window).height()-$("#smsbox").height())/2,left:($(window).width()-$("#smsbox").width())/2}).show();
}


function smsDetails(){
	addNewTabs('消息事务', contextPath+'/system/core/sms/index.jsp');
	$("#smsbox").hide();
}

function smsViewAlls(){
	var url = contextPath+"/sms/viewAll.action";
    tools.requestJsonRs(url,{},true);
    $("#smsbox").hide();
}