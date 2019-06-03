<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@page import="com.tianee.oa.core.org.service.TeePersonService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
   int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"),0);
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<title>工作委托</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript"
	src="<%=contextPath%>/system/core/person/js/person.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>


<script type="text/javascript">
var flowId=<%=flowId %>;
function doInit(){
	if(flowId>0){
		$("#flowIdTr").hide();
		$("#flowId").val(flowId);
	}
	
	var url = contextPath+"/workQuery/getHandableFlowType2SelectCtrl.action";
	//ZTreeTool.comboCtrl($("#flowIdStr"),{url:url});
}

function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/flowRule/addRule.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			parent.$.MsgBox.Alert_auto(jsonRs.rtMsg);
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
	
	var userListNames = document.getElementById("user");
	    if (!userListNames.value) {
	    	 $.MsgBox.Alert_auto("委托人不能为空！");
	  	  userListNames.focus();
	  	  userListNames.select();
	  	  return false;
	    }
	    var toUserListNames = document.getElementById("toUser");
	    if (!toUserListNames.value) {
	    	 $.MsgBox.Alert_auto("被委托人不能为空！");
	  	  toUserListNames.focus();
	  	  toUserListNames.select();
	  	  return false;
	    }
	    var  flowId = document.getElementById("flowId");
	   	if(flowId.value==""){
	   	 $.MsgBox.Alert_auto("请选择流程定义！");
			return false;
		}
	   	
    return $("#form1").valid(); 
}

function backIndex(){

	window.location.href = "<%=contextPath %>/system/core/org/role/manageRole.jsp";
}


//选择所属流程
function selectFlowType(){
	bsWindow(contextPath+"/system/core/workflow/workmanage/flowRule/flowTree.jsp","选择流程",{width:"400",height:"250",buttons:
		[{name:"选择",classStyle:"btn-alert-blue"} ,
	 	 {name:"关闭",classStyle:"btn-alert-gray"}],submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="选择"){
			if(isNaN(parseInt(h.contents().find("#flowId").val()))){//不是数字
  		    	parent.$.MsgBox.Alert_auto("请选择流程！");
  		        return;
  		    }else{
  		    	//获取弹出页面的流程的名称和流程的id
                 $("#flowName").val(h.contents().find("#flowName").val());
                 $("#flowId").val(h.contents().find("#flowId").val());  	
  		    }
		    return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
	
	
}





</script>

</head>
<body onload="doInit()" style="font-family: MicroSoft YaHei;">
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
	boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);
	String uuid = loginPerson.getUuid()+"";
	String userName = loginPerson.getUserName();
	if(isAdmin){
		uuid = "";
		userName = "";
	}
%>

<form method="post" name="form1" id="form1">
<table class="TableBlock_page" width="90%">
<tr>
<td colspan="2" style="vertical-align: middle;padding-top: 10px;padding-bottom: 10px;text-indent: 10px;">
   <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzwt/icon_mmxg.png" align="absMiddle">
   <span style="font-size: 14px;margin-left: 10px;"> 新建委托规则 </span>
</td>
</tr>
	<tr style='<%=!isAdmin?"display:none":"" %>'>
		<td style="text-indent: 10px;" nowrap class="TableData">委托人：</td>
		<td class="TableData" nowrap class="TableData" style=";text-align:left;">
			<input type="hidden" name="userId" id="userId" required="true" value="<%=uuid%>"> 
			<input name="user" id="user" rows="1" style="overflow-y: auto;width: 200px;height:25px;border: 1px solid #dadada;font-family: MicroSoft YaHei;" class="BigInput" wrap="yes" readonly value="<%=userName%>"/> 
		    <span class='addSpan'>
			     <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzwt/add.png" onClick="selectSingleUser(['userId', 'user'])" value="选择"/>
			     &nbsp;
			     <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzwt/clear.png" onClick="clearData('userId', 'user')" value="清空"/>
	        </span>
		</td>
	</tr>
	<tr>
		<td style="text-indent: 10px;" class="TableData TableBG" nowrap class="TableData">被委托人：</td>
		<td class="TableData" nowrap class="TableData" style="text-align:left;">
		    <input type="hidden" name="toUserId" id="toUserId" required="true" value="">
			<input name="toUser" id="toUser" rows="1" style="overflow-y: auto;width: 200px;height:25px;border: 1px solid #dadada;font-family: MicroSoft YaHei;" class="BigInput" wrap="yes" readonly />
			<span class='addSpan'>
			     <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzwt/add.png" onClick="selectSingleUser(['toUserId', 'toUser'])" value="选择"/>
			     &nbsp;
			     <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzwt/clear.png" onClick="clearData('toUserId', 'toUser')" value="清空"/>
	        </span>
		</td>
	</tr>
	<tr id="flowIdTr">
		<td style="text-indent: 10px;" width="120">选择流程：<span style=""></span></td>
		<td>
		  <input style="width: 200px;height:25px;font-family: MicroSoft YaHei;" type="text" id="flowName" name="flowName" onclick="selectFlowType();" readonly/>
		  <input type="text" id="flowId" name="flowId" style="display: none;" />
		</td>
	</tr>
	<tr>
		<td style="text-indent: 10px;" class="TableData TableBG" nowrap class="TableData">生效时间：</td>
		<td class="TableData" nowrap class="TableData">
		<input style="width: 200px;height:25px;" type="text" name="beginDate" id="beginDate" size="20" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endDate\')}'})" class="Wdate BigInput">
		终止时间：
		<input style="width: 200px;height:25px;" type="text" name="endDate" id="endDate" size="20" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'beginDate\')}'})" class="Wdate BigInput">
		&nbsp;&nbsp;<input style="vertical-align:middle;" type="checkbox" name="status" id="status" value="1"/> <span style="vertical-align:middle;">一直生效</span></td>
	 </tr>
	 <tr>
			<td style="text-align:center;" align="center" colspan='2'>
				<input style="width: 45px;height: 25px;margin-right:10px;" type="button" value="保存" class="btn-win-white" title="新建规则" onclick="doSave()"/>
				<input style="width: 45px;height: 25px;" type="reset" value="重置" class="btn-win-white" title="重置" />
				&nbsp;&nbsp;
			</td>
		</tr>
</table>
<!-- <div style='text-align:center;width:90%;margin:0 auto;'>
	<input style="width: 45px;height: 25px;" type="button" value="保存" class="btn-win-white" title="新建规则" onclick="doSave()"/>
	<input style="width: 45px;height: 25px;" type="reset" value="重置" class="btn-win-white" title="重置" />
</div>> -->
</form>
</body>
<script>
	$("#form1").validate();
</script>
</html>