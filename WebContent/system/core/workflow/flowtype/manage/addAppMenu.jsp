<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
   int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"),0);
   String flowName=TeeStringUtil.getString(request.getParameter("flowName"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<title>移动端菜单定义</title>
<script type="text/javascript">
var flowId=<%=flowId %>;
var flowName="<%=flowName %>";
function doInit(){
	$("#appName").val(flowName);
	$("#url").val("/system/mobile/phone/workflow/custom/customIndex.jsp?flowTypeId="+flowId);
}
function add(){
	if(check()&&checkIdIsExists()){
		var para = tools.formToJson($("#form1"));
		var json = tools.requestJsonRs(contextPath+"/mobileModule/addOrUpdate.action",para);
		return json; 
	}
}

function check(){
	if($("#sid").val()==""){
		$("#sid").focus();
		alert("ID标识不能为空");
		return false;
	}else{
		var num = parseInt($("#sid").val());
		if(num<100 || num>255){
			$("#sid").focus();
			alert("ID标识范围必须在100-255之间");
			return false;
		}
	}
	return true;
}

//判断id标识是否已经存在
function  checkIdIsExists(){
	var sid=$("#sid").val();
	var url=contextPath+"/mobileModule/checkIdIsExists.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		if(data==1){
			$("#sid").focus();
			alert("ID标识已存在，请重新填写！");
			return false;
		}else{
			return true;
		}
	}
}

function closeBS(){
	parent.BSWINDOW.modal("hide");;
}
</script>
</head>
<body onload="doInit();">
   <form id="form1">
       	<div class="form-group">
		    <label for="sid">ID标识（100-255）：</label>
		    <input type="text" class="form-control easyui-validatebox" required id="sid" name="sid" placeholder="">
		</div>
      		<div class="form-group">
		    <label for="appName">APP名称：</label>
		    <input type="text" class="form-control" id="appName" name="appName" placeholder="">
		</div>
		<div class="form-group">
		    <label for="url">模块地址：</label>
		    <input type="text" class="form-control" id="url" name="url" readonly="readonly" placeholder="">
		</div>
		<div class="form-group">
		    <label for="pic">应用图标（图标目录/system/mobile/icons）：</label>
		    <input type="text" class="form-control" id="pic" name="pic" placeholder="">
		</div>
		<div class="form-group">
		    <label for="viewId">角标视图ID：</label>
		    <input type="text" class="form-control" id="viewId" name="viewId" placeholder="">
		</div>
       	<div class="form-group">
		    <label for="desc">模块描述：</label>
		    <textarea id="desc" name="desc" class="form-control" rows="3"></textarea>
		</div>
		<fieldset>
			<legend>查看权限</legend>
			<div class="form-group">
			    <label for="deptPriv">部门：</label>
			    <textarea id="deptPrivDesc" name="deptPrivDesc"  class="form-control" rows="3" readonly></textarea>
			    <input type="hidden" id="deptPriv"  name="deptPriv"/>
			    <a href="javascript:void(0)" onclick="selectDept(['deptPriv','deptPrivDesc'])">选择</a>
				&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="clearData('deptPriv','deptPrivDesc')">清空</a>
			</div>
			<div class="form-group">
			    <label for="userPriv">人员：</label>
			    <textarea id="userPrivDesc"  name="userPrivDesc" class="form-control" rows="3" readonly></textarea>
			    <input type="hidden" id="userPriv"  name="userPriv"/>
			    <a href="javascript:void(0)" onclick="selectUser(['userPriv','userPrivDesc'])">选择</a>
				&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="clearData('userPriv','userPrivDesc')">清空</a>
			</div>
			<div class="form-group">
			    <label for="rolePriv">角色：</label>
			    <textarea id="rolePrivDesc" class="form-control" name="rolePrivDesc" rows="3" readonly></textarea>
			    <input type="hidden" id="rolePriv"  name="rolePriv" />
			   	<a href="javascript:void(0)" onclick="selectRole(['rolePriv','rolePrivDesc'])">选择</a>
				&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="clearData('rolePriv','rolePrivDesc')">清空</a>
			</div>
		</fieldset>
       </form>
</body>
</html>