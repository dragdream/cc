<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
  int isAdmin=0;//是否是超级管理员
  if(TeePersonService.checkIsAdminPriv(loginUser)){
	  isAdmin=1;
  }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- jQuery 布局器 -->
<script>
var isAdmin=<%=isAdmin%>;
function doInit(){
    //判断当前登陆人是不是系统管理员
    if(isAdmin==1){
    	$("#dcBtn").show();
    	$("#drBtn").show();
    }
	$("#group").group();
	changePage('<%=contextPath%>/system/core/dept/addupdate.jsp');
	getDeptTree();
	
	$(".dom").click(function(){
		$(".dom").removeClass("li_active");
		$(this).addClass("li_active");
	});
				$(".panel-heading").click(function(){
					if($(this).siblings().find('.panel-body').length==0){
						return false;
					}
					var $span = $(this).find('span');
					var isOpen = $span.hasClass("caret-down");
					if(isOpen){
						//$('.panel-body ul').slideUp();
						$(this).siblings('.collapse').slideUp(200);
						$span.attr("class","caret-right");
					}else{
						$(this).siblings('.collapse').slideDown(200);
						$span.attr("class","caret-down");
					}
			});
	
	
	
}
function  changePage(url){
	$("#frame0").attr("src", url);
}

/**
 * 马上加载
 */
 var zTreeObj ;
 function getDeptTree(){
		var url = "<%=contextPath %>/orgManager/checkOrg.action";
		var jsonObj = tools.requestJsonRs(url);
		if(jsonObj.rtState){
			var json = jsonObj.rtData;
			if(json.sid){
				var url = "<%=contextPath %>/deptManager/getOrgDeptTree.action";
				var config = {
						zTreeId:"orgZtree",
						requestURL:url,
						param:{"para1":"111"},
						onClickFunc:deptOnClick,
						onAsyncSuccess:onDeptAsyncSuccess
					};
				zTreeObj = ZTreeTool.config(config);
			}else{
				//alert("单位信息未录入，请您先填写单位信息！");
				$.MsgBox.Alert_auto("单位信息未录入，请您先填写单位信息！");
				return;
			}
		}
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
/* 	alert(nodes[0].id)
	for (var i=0, l=nodes.length; i<l; i++) {
		zTreeObj.expandNode(nodes[i], true, false, false);
		if (nodes[i].isParent && nodes[i].zAsync ) {
			expandNodes(nodes[i].children);
		}
	} */
}

/**
 * 点击节点
 */
function deptOnClick(event, treeId, treeNode) {
	var uuid = treeNode.id;
	if(uuid.split(";").length == 2){
		changePage("<%=contextPath%>/system/core/dept/addupdate.jsp?uuid=" + uuid.split(";")[0]) ;
	}
}

function userGroup() {
	changePage("<%=contextPath%>/system/core/dept/usergroup/personGroup.jsp");
}

/**
 * 设置菜单
 */
function setMenu(){
	$('#teemenulist ul li a').click(function(even){
	    if($(this).parent().children("div").is(':hidden')){//上级（LI）下面存在UL标签且为隐藏的 ，都展开，否则反之
			$(this).parent().children("div").show('slow');//显示   
			this.className = "aMenuVisited";
	     }else{
	    	$(this) .parent().children("div").hide('slow');//隐藏	
	    	this.className = "aMenulink";//css({'class':'aMenuVisited'});
	      } 
	   
	}).css({'cursor':'pointer'});
	 isFirst = true;
	//$("#teemenu ul li div").hide();//隐藏下级所有节点
}

function importDept(){
	changePage('import.jsp');
}
/**
 * 导出
 */
function exportDept(){
	var url = "<%=contextPath%>/orgImportExport/exportDept.action";
	window.location.href = url;
	
}
</script>
<style type="">
		body{
			background-color:#eaedf2;
		}
		.panel-heading > span{
			position:absolute;
		}
		.panel-heading{
		padding: 10px 5px;
		font-size: 14px;
		text-align: left;
		text-indent:20px;
		box-sizing: border-box;
		}
		.panel-title {
			margin-left:15px;
			display:inline;
		}
		.panel-title a{
		color:#000;
		}
		.groupContent li{
		height: 30px;
		line-height: 30px;
		font-size: 12px;
		text-align: left;
		text-indent:60px;
		cursor:pointer;
		}
		.groupContent li:hover{
		background-color:#fff;
		color:#fff;
		}
		.groupContent li a{
		color:#000;
		}
		.groupContent li a:hover{
		color:#000;
		}
		.li_active{
			background-color:#fff;
		}
		#orgZtree{
			padding-left:45px!important;
		}
</style>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;">
<div id="layout" >
	<div layout="north" width="100%" height="40px" style="overflow:hidden;position:absolute;left:283px;top:0px;height:40px;right: 10px;background-color: white">
	  <div class="clearfix fr" style="margin-top: 8px;">
	     <input type="button" value="新建部门/成员单位" class="btn-win-white" onClick="changePage('addupdate.jsp');" title="新建部门/成员单位"/>
         <input id="drBtn" style="display: none;" type="button" value="导入" class="btn-win-white" onClick="importDept();" title="导入部门/成员单位"/>
         <input id="dcBtn" style="display: none;" type="button" value="导出" class="btn-win-white" onClick="exportDept();" title="导出部门/成员单位"/>
	  </div>
      
      <span class="basic_border"></span>
	</div>
	<div layout="west" width="280px" style="overflow-y:auto;overflow-x:hidden;position:absolute;left:0px;top:0px;bottom:0px;width:280px">
		<br>
		<div id="group" class="list-group">
		 
		 	<div class="panel-group" class="list-group">
				  <div class="panel panel-default">
				   <div class="panel-heading menuList">
				     <span class='caret-down' ></span>
				      <h4 class="panel-title">
				        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
				          	部门列表
				        </a>
				      </h4>
				    </div>
				    <div id="collapseOne" class="panel-collapse collapse in">
				      	<div class="panel-body" style="padding:0px;">
				       		<ul id="orgZtree" class="ztree" style="overflow:auto;border:0px;margin-top:0px;width:100%;height:100%;min-height:330px; padding:2px;"></ul>
				   	    </div>
				    </div>
				  </div>
				  <div class="panel panel-default">
				    <div class="panel-heading menuList dom">
				      <h5 class="panel-title">
				        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#" onclick="userGroup();">
				         	公共自定义组
				        </a>
				      </h5>
				    </div>
				   
				  </div>
				  
			</div>
		  </div>
	</div>
	<div layout="center" style="padding-left:2px;position:absolute;left:281px;top:41px;bottom:0px;right:0px;">
		<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
	</div>
</div>
</body>
</html>