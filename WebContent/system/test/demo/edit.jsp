<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<%@ include file="/header/upload.jsp" %>
	<%
		String sid = request.getParameter("sid");
	%>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<title></title>
	<script type="text/javascript">
	var sid = "<%=sid%>";
	function doInit(){
		//通过id获取demo实体数据
		var json = tools.requestJsonRs(contextPath+"/demo/get.action",{sid:sid});
		//绑定实体数据到表单字段上
		bindJsonObj2Cntrl(json.rtData);
		//渲染附件
		var attachs = json.rtData.attachs;
		//遍历附件
		for(var i=0;i<attachs.length;i++){
			var atta = attachs[i];
			atta.priv = 1+2+4+8;//设置附件权限1：查看  2：下载  4：删除  8：编辑
			//创建附件元素对象
			var attachObject = tools.getAttachElement(atta);
			//将附件元素对象append到指定的元素内
			$("#attachDiv").append(attachObject);
		}
	}
	
	function doSave(){
		$("#form").doUpload({
			url:contextPath+"/demo/update.action",
			success:function(json){
				if(json.rtState){//如果更新成功
					window.location = "index.jsp";
				}else{
					alert(json.rtMsg);
				}
			},
			post_params:{}
		});
	}
	</script>
</head>
<body onload="doInit()">
<form id="form" name="form" method="post" enctype="multipart/form-data">
	<input type="hidden" name="sid" id="sid" value="<%=sid %>" />
	<table>
		<tr>
			<td>姓名：</td>
			<td><input type="text" name="userName" id="userName" /></td>
		</tr>
		<tr>
			<td>密码：</td>
			<td><input type="text" name="passWord" id="passWord" /></td>
		</tr>
		<tr>
			<td>年龄：</td>
			<td><input type="text" name="age" id="age" value="21"/></td>
		</tr>
		<tr>
			<td>性别：</td>
			<td>
				<select id="gender" name="gender">
					<option value="1">男</option>
					<option value="2">女</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>所属部门：</td>
			<td>
				<input type="hidden" name="deptId" id="deptId" />
				<input type="text" name="deptName" id="deptName" />
				<a href="#" onclick="selectSingleDept(['deptId','deptName'])">选择</a>
			</td>
		</tr>
		<tr>
			<td>相关附件：</td>
			<td id="attachDiv">
				<input type="file" name="file1" id="file1" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" onclick="doSave()">保存</button>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
