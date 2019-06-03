
/**
 * Ztree右击触发事件
 * @param event
 * @param treeId
 * @param treeNode
 */
function onRightClickFunc(event, treeId, treeNode) {
	 if(!zTreeObj){
		 zTreeObj = $.fn.zTree.getZTreeObj("orgUserZtree"); 
 	}
	 //alert(treeNode.tId)
	if (treeNode && treeNode.onRight) {
		var id = treeNode.id;
		var name = treeNode.name;
		var value = id.split(";")[0] ;
		zTreeObj.selectNode(treeNode);
		//showOptMenu(treeNode.tId,value,name);
		showRMenu(treeNode.tId, event.pageX, event.pageY,value,name);
	}
}

/**
 * 显示菜单
 * @param noteId:节点Id
 * @param x  
 * @param y
 * @param value  人员Id
 * @param name 人员姓民警
 */
function showRMenu(noteId, x, y,value,name) {
	$("#orgRightMenu").show();
	var rMenu = $("#orgRightMenu");
	rMenu.show();
	rMenu.css({"top":(y)+"px", "left":x+"px", "position":"absolute"});
	$("#orgRightMenu").empty();
	$("#orgRightMenu").append("<li ><a href='javascript:void();' onclick=\"sendMessage("+ value+" , '" + name + "');\">短信</a></li>"+
    "<li  ><a href='javascript:void();' onclick=\"sendEmail("+ value+", '" + name + "');\">邮件</a></li>");
	$(document).bind("mousedown", onBodyMouseDown);
}
function hideRMenu() {
	var rMenu = $("#orgRightMenu");
	rMenu.hide();
	$(document).unbind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event){
	var rMenu = $("#orgRightMenu");
	if (!(event.target.id == "orgRightMenu" || $(event.target).parents("#orgRightMenu").length>0)) {
		rMenu.hide();
	}
}


/**
 * 加载完毕后触发事件
 */
function callBackOrgFunc(){
	//alert("setDeptParentSelct");
}
/**
 * 右击显示菜单项
 * id 绑定Id
 * value 人员Id
 * name 姓名
 *  extend 扩展
 */
function showOptMenu(id,value,name , extend){
	var menuDataFont = [
	         { name:'<div  style="padding-top:5px;margin-left:10px">消息</div>',action:sendMessage,extData:[value ,name , '消息']}
		    ,{ name:'<div  style="font-family:黑体;padding-top:5px;margin-left:10px">邮件</div>',action:sendEmail,extData:[value ,name ,'邮件']}
			];
	//menuData.push({ name:'查看',action: getFieldFont,extData:['ss','test']});
	//$("#" + id + "_a").TeeMenu({menuData:menuDataFont,eventPosition:false});
	$("#" + id + "_ico").TeeMenu({menuData:menuDataFont,eventPosition:true});
}
/**
 * 消息
 * @param value
 * @param name
 * @param type
 */
function sendMessage(value ,name , type){
	//alert(value +":"+name + ":" + type);
	var url = contextPath + "/system/core/sms/addSms.jsp?userId=" + value + ",&userName=" + encodeURIComponent(name) + ",";

	top.bsWindow(url ,"发送内部短信",{width:"700",height:"280",buttons:
		[
	 	/* {name:"关闭",classStyle:"btn btn-primary"}*/
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		
		}else if(v=="关闭"){
			return true;
		}
	}});
	hideRMenu();
}
/***
 * 邮件
 * @param value
 * @param name
 * @param type
 */
function sendEmail(value ,name , type){
	top.bsWindow(contextPath+"/mail/otherMail.action?userIds="+value,"发送邮件");
	hideRMenu();
}
/**
 * 点击节点
 */
function personOnClick(event, treeId, treeNode) {
	var uuid = treeNode.id;
	if(uuid.split(";").length == 2 && uuid.split(";")[1] == 'dept'){
		//parent.personinput.location = "<%=contextPath%>/system/core/person/personList.jsp?deptId=" + uuid.split(";")[0] + "&deptName=" + encodeURIComponent(treeNode.name);
	}else if(uuid.split(";").length == 2 && uuid.split(";")[1] == 'personId'){
		//parent.personinput.location = "<%=contextPath%>/system/core/person/personList.jsp?uuid=" + uuid.split(";")[0] ;
		//toAddUpdatePerson(uuid.split(";")[0]);
	}
};



/**
 * 一次性加载
 */
 var zTreeObj ;
 function onlineUser(){
		var url = contextPath + "/orgManager/checkOrg.action";
		var jsonObj = tools.requestJsonRs(url);
		//alert(jsonObj);
		if(jsonObj.rtState){
			var json = jsonObj.rtData;
			if(json.sid){
				var url = contextPath + "/orgSelectManager/getOnlineOrgUserTree.action";
				var config = {
					zTreeId:"orgUserZtree",
					requestURL:url,
					param:{"para1":"111"},
//					onClickFunc:personOnClick,
					async:false,
					onClickFunc:function(event, treeId, treeNode) {
						if (treeNode) {
							var id = treeNode.id;
							var name = treeNode.name;
							var value = id.split(";")[0] ;
							var desc = id.split(";")[1] ;
							
							if(desc!="person"){
								return;
							}
							
							var menus = [{name:'站内消息',action:function(uuid,name){
								var url = contextPath + "/system/core/sms/addSms.jsp?userId=" + uuid + ",&userName=" + encodeURIComponent(name) + ",";
								openFullWindow(url,"站内消息");
							},extData:[value,name]}
							,/*{name:'即时通讯',action:function(userId){
								top.msgFrame.createDlg(userId);
							},extData:[treeNode.params.userId]}
							,*/{name:'内部邮件',action:function(uuid){
								openFullWindow(contextPath+"/system/core/email/send.jsp?toUsers="+uuid,"发送邮件");
							},extData:[value]}];
							$.TeeMenu(menus,{left:event.pageX,top:event.pageY,width:71,height:80});
						}
						window.event.cancelBubble = true;
					},
					onAsyncSuccess:callBackOrgFunc
			};
	
			zTreeObj = ZTreeTool.config(config);
				//expandNodes(zTreeObj);
			}else{
				alert("单位信息未录入，请您先填写单位信息！");
				return;
			}
		}
 }
 
 function allUser(){
	 var url = "/personManager/getOrgTreeAll.action";
		var config = {
				zTreeId:"orgUserZtree",
				requestURL:url,
				onClickFunc:function(event, treeId, treeNode) {
					if (treeNode && treeNode.onRight) {
						var id = treeNode.id;
						var name = treeNode.name;
						var value = id.split(";")[0] ;
						
						var menus = [{name:'站内消息',action:function(uuid,name){
							var url = contextPath + "/system/core/sms/addSms.jsp?userId=" + uuid + ",&userName=" + encodeURIComponent(name) + ",";
							openFullWindow(url,"站内消息");
						},extData:[value,name]}
						,/*{name:'即时通讯',action:function(userId){
							top.msgFrame.createDlg(userId);
						},extData:[treeNode.params.userId]}
						,*/{name:'内部邮件',action:function(uuid){
							openFullWindow(contextPath+"/system/core/email/send.jsp?toUsers="+uuid,"发送邮件");
						},extData:[value]}];
						$.TeeMenu(menus,{left:event.pageX,top:event.pageY,width:71,height:80});
					}
					window.event.cancelBubble = true;
				},
				onAsyncSuccess:onDeptAsyncSuccess
				
			};
		zTreeObj = ZTreeTool.config(config);

 }
 
 
 function onDeptAsyncSuccess(event, treeId, treeNode, msg) {//异步执行成功后

	 expandNodes();
	 
}
/**
  *第一级展开部门
  */
function expandNodes() {
	 if(!zTreeObj){
		zTreeObj = $.fn.zTree.getZTreeObj("orgZtree"); 
	 }
	var nodes = zTreeObj.getNodes();
	zTreeObj.expandNode(nodes[0], true, false, false);
	if (nodes[0].isParent && nodes[0].zAsync  && nodes[0].id =='0') {//是第一级节点
		expandNodes(nodes[0].children);
	}
}

 
 
// function allUser(){
//		var url = contextPath + "/orgManager/checkOrg.action";
//		var jsonObj = tools.requestJsonRs(url);
//		//alert(jsonObj);
//		if(jsonObj.rtState){
//			var json = jsonObj.rtData;
//			if(json.sid){
//				var url = contextPath + "/orgSelectManager/getAllOrgUserTree.action";
//				var config = {
//					zTreeId:"orgUserZtree",
//					requestURL:url,
//					param:{"para1":"111"},
////					onClickFunc:personOnClick,
//					async:true,
//					onClickFunc:function(event, treeId, treeNode) {
//						if (treeNode && treeNode.onRight) {
//							var id = treeNode.id;
//							var name = treeNode.name;
//							var value = id.split(";")[0] ;
//							
//							var menus = [{name:'站内消息',action:function(uuid,name){
//								var url = contextPath + "/system/core/sms/addSms.jsp?userId=" + uuid + ",&userName=" + encodeURIComponent(name) + ",";
//								openFullWindow(url,"站内消息");
//							},extData:[value,name]}
//							,{name:'即时通讯',action:function(userId){
//								top.msgFrame.createDlg(userId);
//							},extData:[treeNode.params.userId]}
//							,{name:'内部邮件',action:function(uuid){
//								openFullWindow(contextPath+"/system/core/email/send.jsp?toUsers="+uuid,"发送邮件");
//							},extData:[value]}];
//							$.TeeMenu(menus,{left:event.pageX,top:event.pageY,width:71,height:80});
//						}
//						window.event.cancelBubble = true;
//					},
//					onAsyncSuccess:callBackOrgFunc
//			};
//	
//			zTreeObj = ZTreeTool.config(config);
//				//expandNodes(zTreeObj);
//			}else{
//				alert("单位信息未录入，请您先填写单位信息！");
//				return;
//			}
//		}
//}