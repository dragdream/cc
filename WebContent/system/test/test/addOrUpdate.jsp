 <%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
var userId = "<%=userId%>";
var editor;
function doInit(){

		 if(userId!="0"){
			var url = contextPath+"/user/getUserModel.action";
			var json = tools.requestJsonRs(url,{userId:userId});
			if(json.rtState){
				bindJsonObj2Cntrl(json.rtData);
//				$("#userName").val(json.rtData.userName);
//				$("#gender").val(json.rtData.gender);
//				$("#age").val(json.rtData.age);
			}
		  }else{
				$("#tr1").after("<tr>"+
						"<td><b>密码 ：</b></td>"+
						"<td><input  style='width:100px' class='easyui-validatebox' type='password' name='passwd' id='passwd' validtype='equalTo[repwd]'/></td>"+
						"</tr>"+
						
						"<tr>"+
						"<td><b>确认密码 ：</b></td>"+
						"<td><input  style='width:100px' class='easyui-validatebox' type='password' name='repwd' id='repwd'  validtype='equalTo[passwd]'/></td>"+
						"</tr>");		
			}

}

function commit(){
	if (checkForm()){
		var url = "";
		var para = tools.formToJson($("#form1"));//将表单控件转换成json请求参数
		
		//para["tplContent"] = editor.getData();
		if(userId=="0"){
			//alert(tools.jsonObj2String(para));//序列化json对象
			url = contextPath+"/user/addUser.action";
		}else{		
			url = contextPath+"/user/updateUser.action";
		}
		var jsonRs = tools.requestJsonRs(url,para);//ajax请求
		
		if(jsonRs.rtState){
			top.$.jBox.tip(jsonRs.rtMsg,"info");//本页面弹出提示窗体
			try{
				xparent.location.reload();//刷新父页面
			}catch(e){}
			CloseWindow();//关闭当前浏览器页面
		}else{
			top.$.jBox.tip(jsonRs.rtMsg,"error");
			return false;
		}
	}
}

function checkForm(){
	//alert($("#form1").form('validate'));
    return $("#form1").form('validate'); 
}

</script>

</head>
<body onload="doInit()" style="margin:0px;font-size:12px;">
<div class="base_layout_top">
	<button class="btn btn-primary" onclick="commit()">保存</button>
	&nbsp;&nbsp;
	<button class="btn btn-default" onclick="CloseWindow()">关闭</button>
</div>
<div class="base_layout_center">
	<form action="" id="form1">
	    <table>
	    <tr id="tr1">
		<td><b>用 户 名 ：</b></td>
		<td><input class="easyui-validatebox" type="text" required="true"  name="userName" id="userName"/></td>
		</tr>
		
		<!--
		<tr>
		<td><b>密码 ：</b></td>
		<td><input  style="width:100px" class="easyui-validatebox" type="password" name="passwd" id="passwd" validtype="equalTo[repwd]"/></td>
		</tr>
		
		<tr>
		<td><b>确认密码 ：</b></td>
		<td><input  style="width:100px" class="easyui-validatebox" type="password" name="repwd" id="repwd"  validtype="equalTo[passwd]"/></td>
		</tr>
		  -->
		
		<tr>
		<td><b>年 龄 ：</b></td>
		<td><input style="width:100px" class="easyui-validatebox" type="text" name="age" id="age" validtype="positivIntege[age]"/></td>
		</tr>
		
		<tr>
		<td><b>性 别 ：</b></td>
		<td>
		   男<input  type="radio" name="gender"  value="男" checked="checked"/>
		   女<input  type="radio" name="gender"  value="女"/>
		</td>
		</tr>
		
		<tr>
		<td><b>所属部门：</b></td>
		<td>
			<input type="hidden" name="deptId" id="deptId"/>
			<input style="width:200px" type="text" readonly name="deptName" id="deptName" />
			<a onclick="selectSingleDept(['deptId','deptName'],'0')">选择</a>
		</td>
		</tr>
		<input type="hidden" name="userId" value="<%=userId %>" />
	    </table>
	</form>
</div>
</body>
</html>
 