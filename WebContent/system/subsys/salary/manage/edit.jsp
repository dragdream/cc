<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid=request.getParameter("sid")==null?"0":request.getParameter("sid");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<script>
 
function doInit(){
	var url = contextPath+"/teeSalItemController/getSalaryTitle.action";
	var json = tools.requestJsonRs(url);
	var html="";
	if(json.rtState){
		var data = json.rtData;
		if(data.length>0){
			//表头字段字段名称
			var tableHeader = json.rtData[0].tableHeaderName;
			var titleFieldName = json.rtData[1].titleFieldName;
			var headers = tableHeader.split(",");
			var fields = titleFieldName.split(",");
			for(var n=0;n<headers.length;n++){
				if(n<3){
					var div=$("<div>",{
						style:'display:none;'
					});
					var headDiv=$("<span>",{
						style:'width:30%;text-align:left;float:left;'
					}).html(headers[n]);
					var fieldDiv=$("<span>",{
						style:'width:70%;text-align:left;float:left;'
					});
					var input = $("<input>",{
						id:fields[n],
						name:fields[n],
						type:'hidden',
					}).addClass("BigInput");
					fieldDiv.append(input);
					div.append(headDiv);
					div.append(fieldDiv);
					$("#salaryInfo").append(div);
				}else if(n==3){
					var div=$("<div>",{
						style:'height:32px;line-height:32px;'
					});
					var headDiv=$("<span>",{
						style:'width:30%;text-align:left;float:left;'
					}).html(headers[n]);
					var fieldDiv=$("<span>",{
						style:'width:70%;text-align:left;float:left;'
					});
					var input = $("<input>",{
						id:fields[n],
						name:fields[n],
						type:'text',
						disabled:'disabled'
					}).addClass("BigInput");
					fieldDiv.append(input);
					div.append(headDiv);
					div.append(fieldDiv);
					$("#salaryInfo").append(div);
				}else{
					var div=$("<div>",{
						style:'height:32px;line-height:32px;'
					});
					var headDiv=$("<span>",{
						style:'width:30%;text-align:left;float:left;'
					}).html(headers[n]);
					var fieldDiv=$("<span>",{
						style:'width:70%;text-align:left;float:left;'
					});
					var input = $("<input>",{
						id:fields[n],
						name:fields[n],
						type:'text',
						maxLength:'10'
					}).addClass("BigInput easyui-validatebox").validatebox({
						validType:'pointTwoNumber[]',
						required:true 
					});
					fieldDiv.append(input);
					div.append(headDiv);
					div.append(fieldDiv);
					$("#salaryInfo").append(div);
				}
				
			}
		}
	}
	getSalaryInfo();
}

function getSalaryInfo(){
	var url = "<%=contextPath%>/salDataPersonManage/getById.action?sid=<%=sid%>";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}
}


function commit(callback){
	if($("#form1").form("validate")){
		var param = tools.formToJson($("#form1"));
		var url = "<%=contextPath%>/salDataPersonManage/update.action";
		var json = tools.requestJsonRs(url,param);
		
		if(json.rtState){
			callback(json.rtDate);
			return true;
		}else{
			alert(json.rtMsg);
			return false;
		}
	}
}

</script>

</head>
<body onload="doInit()" style='margin:0 auto;text-align:center;'>
	<form id="form1" name="form1">
		<div id='salaryInfo' name='salaryInfo' style="margin-top:10px;text-align:center;">
		
		</div>
		<input type='hidden' class='btn btn-primary' id='updateSalary' name='updateSalary' onclick="updateSalaryInfo()" value="保存"/>
	</form>
</body>
</html>