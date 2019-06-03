<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<link rel="stylesheet" type="text/css" href="/xzfy/css/common/supervise.css" />
<!-- 中腾按钮框架 -->
<link rel="stylesheet" type="text/css" href="/common/zt_webframe/css/package1.css" />
<%
	String caseId = request.getParameter("caseId");
%>

<title>建议书</title>
<style> 
	tr:nth-child(even){
		 background-color:#F0F0F0;
	}
	textarea.textstyle {
		font-size: 13px;
	    color: #555555;
	    /* border: 1px solid #C0BBB4; */
    	border: 1px solid #cccccc;
    	border-radius: 3px;
	}
</style>

</head>
<body style="padding-left: 10px;padding-right: 10px;" onload="doInit();">
<!-- 菜单栏 -->

<div class="base_layout_top" style="position:static">
    <img class = 'fl' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/wdhy/我的会议.png">
	<span class="title" style="padding-top: 4px;">建议书</span>
	<span style="float:right">
		<input type="button" value="恢复审批" class="btn-win-white" onclick="bqsp()" style="margin-right:10px;">
		<input type="button" value="生成恢复通知书" class="btn-win-white" onclick="noticeBook()" style="margin-right:10px;">
		<input type="button" value="送达回证" class="btn-win-white" onclick="receipt()" style="margin-right:10px;">
		<input type="button" value="提交" class="btn-win-white" onclick="save()" style="margin-right:10px;">
		<input type="button" value="完成" class="btn-win-white" onclick="done()" style="margin-right:10px;">
	</span>
</div>

<form  method="post" name="form1" id="form1" >
<input type="hidden" id="caseId" name="caseId" value="<%=caseId %>">
<input type="hidden" id="id" name="id" >
<div class="easyui-panel" title="案件信息" style="width: 100%;" align="center" id="baseDiv">
<table align="center" width="100%" class="TableBlock">
	<tr>
		<td nowrap class="TableData"  width="15%;" >申请人信息：</td>
		<td colspan="5">
			<textarea class="textstyle" style="height:50px;width:900px" data-options="multiline:true" name="" id="" disabled></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >被申请人信息：</td>
		<td colspan="5">
			<textarea class="textstyle" style="height:50px;width:900px" data-options="multiline:true" name="respondentName" id="respondentName" disabled></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >具体行政行为：</td>
		<td colspan="5">
			<textarea class="textstyle" style="height:50px;width:900px" data-options="multiline:true" name="specificAdministrativeDetail" id="specificAdministrativeDetail" disabled></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >行政复议请求：</td>
		<td colspan="5">
			<textarea class="textstyle" style="height:50px;width:900px" data-options="multiline:true" name="requestForReconsideration" id="requestForReconsideration" disabled></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >发现的问题：</td>
		<td colspan="5">
			<textarea class="textstyle" style="height:60px;width:900px" data-options="multiline:true" name="discoveredProblems" id="discoveredProblems"></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >建议：</td>
		<td colspan="5">
			<textarea class="textstyle" style="height:60px;width:900px" data-options="multiline:true" name="proposalAdvise" id="proposalAdvise"></textarea>
		</td>
	</tr>
</table>
</div>
</form>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/xzfy/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=contextPath %>/xzfy/js/caseTrial/auxiliaryOperation/break/break.js"></script>
<script type="text/javascript">
var title = "";
var caseId = "<%= caseId%>";
function doInit(){
	//获取案件信息回显到页面
	var json = tools.requestJsonRs("/discussionController/getCaseInfoById.action", {caseId: caseId});
	if(json.rtState){
		//后台返回对象后绑定到form表单
		bindJsonObj2Cntrl(json.rtData);
	}else{
		$.jBox.tip("请求失败,请联系管理员！", 'info' , {timeout:1500});
	}
}

//选择中止理由zck
function choose(){
	var nowReason = $("#caseSubBreakReason").val();
	bsWindow("chooseReason.jsp?nowReason="+nowReason, "选择中止理由", {
		width : "600",
		height : "300",
			buttons:
			[
			 {name:"保存",classStyle:"btn btn-primary"},
		 	 {name:"关闭",classStyle:"btn btn-primary"}
			 ]
			,
			submit : function(v, h, c, b) {
				var result = h[0].contentWindow;
					if(v == "保存"){
						var reason = result.giveFatherReason();
						//将中止理由回显
						$("#caseSubBreakReason").val(reason);
						//关闭子页面
						return true; 
					}else{
						return true;  
					}
			}
	});  
}
//中止提交zck
function save(){
	 $(form1).ajaxSubmit({
		 type: 'post', // 提交方式 
       	 url: '<%=contextPath%>/discussionController/commend.action', // 需要提交的 url
      	 success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
       	 // 此处可对 data 作相关处理
      		$.jBox.tip("保存成功！", 'info' , {timeout:1500});
      	 },
       	 error:function(data){
       		$.jBox.tip("保存失败,请联系管理员！", 'info' , {timeout:1500});
       	 }
	 });
}
</script>
</body>
</html>