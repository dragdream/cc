<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>草稿箱</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%-- <!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/system/core/email/js/email.js"></script>
<script>
var datagrid ;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/emailController/getEmailDraftBoxList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		/* pageList: [50,60,70,80,90,100], */
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'toUserNameAll',title:'收件人',width:120,formatter:function(value,rowData,rowIndex){
				var emailImg = "<img src='<%=stylePath%>/imgs/message_open.gif'/>&nbsp;";
				var attachImg = "&nbsp;&nbsp;";
				var readFlagStr = rowData.readFlag;
				if(readFlagStr =="0"){
					value = "<span title='"+value+"'>" + value + "</span>";
					emailImg = "<img src='<%=stylePath%>/imgs/message.gif'/>&nbsp;";
				}
				if(rowData.attachMentModel.length>0){
					attachImg = "<span class='glyphicon glyphicon-paperclip' />&nbsp;";
				}
				return "<a href='#' onclick='sendEmailOpt(3," + rowData.sid + ",1)'>" + value + "</a>";
			}},
			{field:'toWebmail',title:'外部收件人',width:100},
			{field:'subject',title:'主题',width:200,formatter:function(value,rowData,rowIndex){
				var level = rowData.emailLevelDesc;
				if(level!=null&&level!=""){
					if(level!="普通"){
						levelDesc = "<span style='color:red;'>（"+level+"）</span>&nbsp;&nbsp;"; 
					}else{
						levelDesc = "<span>（"+level+"）</span>&nbsp;&nbsp;";
					}
				}else{
					levelDesc = "<span></span>&nbsp;&nbsp;"; 
				}
				return levelDesc+"<a href='#' onclick='sendEmailOpt(3," + rowData.sid + ",1)'>" + value + "</a>";
			}},
			{field:'sendTimeStr',title:'时间',width:80}
		]],onLoadSuccess:onLoadSuccessFunc
	});
	//getEmailBoxList();
}

function onLoadSuccessFunc(){
	var data=$('#datagrid').datagrid('getData');
	$("#totalMail").text("(共" + data.total +"封)");
}


function exportEml(){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("未选中任何邮件！");
		return ;
	}
	//alert(contextPath);
	$("#exportIframe").attr("src",contextPath+"/mail/exportEmls.action?bodyIds="+ids);

}


</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">

<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
	<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_caogaoxiang.png">
		<span class="title">草稿箱  <span id="totalMail" class="attach"></span></span>
	</div>
	<div class="right fr clearfix">
		<input type="button" class="btn-del-red fl" onclick="batchDestroyEmailDraftBox(1)" value="删除草稿"/>
		<input type="button" class="btn-del-red fl" style="display:none;" value="彻底删除"/>
		<input type="button" class="btn-win-white fl" style="display:none;" value="转发"/>
		<input type="button" class="btn-win-white fl" style="display:none;" value="全部标记已读"/>
		
		<div class="btn-group" style="display:none;" >
		  <button type="button" class="btn-win-white btn-menu" >
		    标记为<span class="caret"></span>
		  </button>
		  <ul class="btn-content">
		    <li><a href="#">已读邮件</a></li>
		    <li><a href="#">未读邮件</a></li>
		    <li class="divider"></li>
		    <li><a href="#">星标邮件</a></li>
		    <li><a href="#">取消星标</a></li>
		  </ul>
		</div>
		
		<div class="btn-group" style="display:none;">
		  <button type="button" class="btn-win-white btn-menu" >
		    移动到<span class="caret"></span>
		  </button>
		  <ul class="btn-content"  id="mailBoxList">
		    <li><a href="#" style="display:none;">收件箱</a></li>
		    <li><a href="#" style="display:none;">已发送</a></li>
		  </ul>
		</div>
		<input type="button" class="btn-win-white fl" onclick="exportEml();" value="导出"/>
	</div>
</div>
<table id="datagrid" fit="true" ></table>
<iframe id="exportIframe" style="display:none"></iframe>
</body>
</html>