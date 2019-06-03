<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    
       int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
    
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增/编辑费用类型</title>
<script>
var sid=<%=sid%>;
//初始化
function doInit(){
	if(sid>0){
		getInfoBySid();	
	}

}

//根据主键获取详情
function getInfoBySid(){
	var url=contextPath+"/projectCostTypeController/getCostTypeBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
	}else{
		$.MsgBox.Alert_auto("数据获取失败！");
	}
		
}


//新增或者编辑
function commit(){
    if(check()){
		var url=contextPath+"/projectCostTypeController/addOrUpdate.action";
		var para=tools.formToJson($("#form1")) ;
		var json=tools.requestJsonRs(url,para);
		if(json.rtState){
			  parent.window.location.reload();
			  return true;
		}else{
			$.MsgBox.Alert_auto("操作失败！");
		}
	}	 
}


//验证
function check(){
	var typeName=$("#typeName").val();
	if(typeName==""){
		$.MsgBox.Alert_auto("请填写类型名称！");
		return false;
	}else{
		return true;
	}
	
}
</script>
<body onload="doInit();" style="background-color: #f2f2f2;overflow: hidden;">
<form id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>"/>
  <div style="margin-top: 10px">
     <table class="TableBlock" width="100%">
        <tr>
           <td style="text-indent: 10px">排序号：</td>
           <td><input type="text" name="orderNum" id="orderNum" style="height: 23px;width: 300px" /></td>
        </tr>
        <tr>
           <td style="text-indent: 10px">类型名称：</td>
           <td><input type="text" name="typeName" id="typeName" style="height: 23px;width: 300px"/></td>
        </tr>
     </table>
  </div>
</form>
</body>
</html>