<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
  	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<title>模块权限新建/编辑</title>

<script type="text/javascript">

var sid = '<%=sid%>';
function doInit(){
	//添加例子一
	if(sid > 0){
		var url = "<%=contextPath %>/sysMenuPrivManager/selectById.action";
		var para = {sid:sid};
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj.rtState){
			var json = jsonObj.rtData;
			bindJsonObj2Cntrl(json);
		}else{
			alert(jsonObj.rtMsg);
		}
	}
	
}

/**
 * 保存
 */
function doSave(callback){
	if (check()){
		var url = "<%=contextPath %>/sysMenuPrivManager/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			callback();
		}else{
			alert(jsonRs.rtMsg);
		}
	}
	
}
function check() {
	return $("#form1").form('validate'); 
}
</script>

</head>
<body onload="doInit()" style="padding-top:10px;">

 
  <form   method="post" name="form1" id="form1">
			<table class="TableBlock"  width="99%">
				
				<tr class="TableData">
					<td>页面/控制权限名称<font color='red'>*</font></td>
					<td>
						<input type="text" name="privName" class="easyui-validatebox BigInput" required="true"  maxlength="200"/>
					</td>
				</tr>
				<tr  class="TableData">
					<td>页面路径<font color='red'>*</font></td> 
					<td>
						<input  type="text" name="privUrl" class="easyui-validatebox BigInput"  required="true"   maxlength="250" >
					</td>
				</tr>
				<tr  class="TableData">
					<td>是否校验<font color='red'>*</font></td> 
					<td>
						<select name="privFlag" class="BigSelect">
							<option value='0'>不校验</option>
							<option value='1'>校验</option>
						</select>
					</td>
				</tr>
				<tr  class="TableData">
					<td>菜单</td> 
					<td>
						<input  type="hidden" name="sysMenuIds" id="sysMenuIds" />
							<textarea name="sysMenuNames"" id="sysMenuNames" class="SmallStatic BigTextarea" rows="4" cols="35"readonly="readonly"></textarea> &nbsp;&nbsp;
							<a href="javascript:void(0);" class="orgAdd" onClick="selectSysMenu(['sysMenuIds','sysMenuNames'],'')">选择</a>&nbsp;&nbsp; 
							<a href="javascript:void(0);" class="orgClear"onClick="clearData('sysMenuIds','sysMenuNames')">清空</a>
			
					</td>
				</tr>
		</table>
		<input type="hidden" name="sid" value="<%=sid %>">
   </form>
   
   
</body>
</html>