<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.general.model.TeeSmsModel" %>
<%@ page import="com.tianee.webframe.util.str.TeeJsonUtil" %>
<%@ page import="com.tianee.webframe.util.date.TeeLunarCalendarUtils" %>
<%@ page import="com.tianee.webframe.util.date.TeeWeather" %>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.tianee.webframe.util.date.*"%>
<%@ page import="com.tianee.webframe.util.auth.TeeAuthUtil"%>

<%
	String userOptType = TeeStringUtil.getString(request.getParameter("userOptType"), "1");
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/smsHeader.jsp" %>
<%@ include file="/header/ztree.jsp" %>

	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/frame/im/style/im.css" />
<script type="text/javascript" src="<%=contextPath%>/system/frame/im/js/TeeMenu.js"></script> 
<script type="text/javascript" charset="UTF-8">
var userOptType = <%=userOptType%>;
var zTreeObj ;
function doInit(){
	doCore();
	setInterval("doCore()",1000*30);
}

function doCore(){
	var url = "<%=contextPath %>/orgManager/checkOrg.action";
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		if(json.sid){
			var url =  "<%=contextPath %>/orgSelectManager/getAllOrgUserTree.action";
			if(userOptType == '2'){
			    url = "<%=contextPath %>/orgSelectManager/getOnlineOrgUserTree.action";
			}
			var config = {
				zTreeId:"orgUserZtree",
				requestURL:url,
				param:{"para1":"111"},
				onClickFunc:function(event, treeId, treeNode){
					if(!zTreeObj){
						 zTreeObj = $.fn.zTree.getZTreeObj("orgUserZtree"); 
				 	 }
					if (treeNode && treeNode.onRight) {
						var id = treeNode.id;
						var name = treeNode.name;
						var value = id.split(";")[0] ;
						var userId = treeNode.params.userId;
						window.external.IM_OpenDialog(userId,name);
					}
				},
				async:false,
				onRightClickFunc:function(){
					
				},
				onAsyncSuccess:function(){
					setMouseFunc();
				}
		};

		zTreeObj = ZTreeTool.config(config);
		}else{
			alert("单位信息未录入，请您先填写单位信息！");
			return;
		}
	}
}

function reld(){
	window.location.reload();
}

function setMouseFunc(){
	var zTreeObj = $.fn.zTree.getZTreeObj("orgUserZtree"); 

	/*获取所有节点*/
	var allPersonNote = zTreeObj.getNodesByParamFuzzy("id", ";personId");
	$.each(allPersonNote , function(i,node){
		var id = node.id;
		var name = node.name;
		var userId = node.params.userId;
		$("#" + node.tId + "_a").bind("mouseover",function(){
			var value = id.split(";")[0] ;
			//if(id.split(";").length == 2 && id.split(";")[1] == 'personId'){
				
				var cityObj = $(this);
				var cityOffset = cityObj.offset();
				var leftLength = cityOffset.left;
				var topLength = cityOffset.top;
			 	/* 	if (isBrowserVersonTop()) {  //判断是否需要处理兼容模式
			 			leftLength = leftLength + xScrollLength;
			 			topLength = topLength + yScrollLength;
			 		} */
				 var left = (leftLength + cityObj.outerWidth()) ;
			   //  var tp = { left:left };
			  
				var menus = [{name:'发送内部邮件',className:'orgSendEmail',action:function(value){
				 				openFullWindow(contextPath+"/system/core/email/send.jsp?toUsers="+value,"发送邮件");
				 			},extData:[value]}];
				 $.TeeMenu(menus,{left:left,top:topLength - 5,width:70,height:80,eventPosition:true});

			//}
			
		});
		
		$("#" + node.tId + "_a").bind("mouseout",function(){
			//$(this).css({"font-size":"14px"});
			//$(".dropdown-menu").hide();
			$("#mouseOverMenu").hide();
			//alert(panel)
		});
		//alert(node.innerHTML)
		//$(node).mousemove(alert("ddd"));
		
    });
}
</script>
</head>
<body onload="doInit()" style="overflow-y:auto;">
<div>
<div style="padding-left:10px;">
<ul id="orgUserZtree" class="ztree" style="border:0px;width:180px;height:auto;"></ul>

</div>
</div>
</body>
</html>
		        