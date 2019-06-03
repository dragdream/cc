<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
  //项目主键
  String  uuid=TeeStringUtil.getString(request.getParameter("uuid"));
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>预算申请</title>
</head>
<script>
var uuid="<%=uuid%>";
//初始化
function doInit(){
	getCostType();
	getApprover();
}


//获取费用类型
function getCostType(){
	var url=contextPath+"/projectCostTypeController/getCostTypeList.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
	    for(var i=0;i<data.length;i++){
	    	$("#costTypeId").append("<option value="+data[i].sid+">"+data[i].typeName+"</option>");
	    }
	}
}
//获取费用的审批人员
function getApprover(){
	var url=contextPath+"/costApprovalController/getApprover.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
		for(var i=0;i<data.length;i++){
			$("#approverId").append("<option value="+data[i].uuid+">"+data[i].userName+"</option>");
		}
	}	
}

//保存
function commit(){
	if(check()){
		var url=contextPath+"/projectCostController/add.action";
		var param=tools.formToJson("#form1");
		param["projectId"]=uuid;
		var json=tools.requestJsonRs(url,param);
		return json.rtState;
		
	}
}
//验证
function check(){
	var costTypeId=$("#costTypeId").val();
	var approverId=$("#approverId").val();
	var amount=$("#amount").val();
	if(costTypeId==0||costTypeId==null||costTypeId==""){
		$.MsgBox.Alert_auto("请选择费用类型！");
		return false;
	}
	if(approverId==0||approverId==null||approverId==""){
		$.MsgBox.Alert_auto("请选择审批人员！");
		return false;
	}
	if(amount==null||amount==""){
		$.MsgBox.Alert_auto("请填写金额！");
		return false;
	}
	return true;
}
</script>
<body style="background-color: #f2f2f2" onload="doInit()">
<form id="form1">
   <table class="TableBlock" width="100%">
      <tr>
          <td style="text-indent: 10px">费用类型：</td>
          <td>
             <select id="costTypeId" name="costTypeId" style="height: 23px">  
            
             </select>
          </td>
      </tr>
      <tr>
          <td style="text-indent: 10px">费用金额：</td>
          <td>
             <input type="text" name="amount" id="amount" style="height: 23px"/>
          </td>
      </tr>
      <tr>
          <td style="text-indent: 10px">审批人员：</td>
          <td>
             <select id="approverId" name="approverId" style="height: 23px">  
            
             </select>
          </td>
      </tr>
      <tr>
          <td style="text-indent: 10px">费用说明：</td>
          <td>
             <textarea rows="6" cols="60" id="desc" name="desc" class="BigTextArea"></textarea>
          </td>
      </tr>
   </table>
</form>
</body>
</html>