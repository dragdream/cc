<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>编辑通讯组</title>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<%@ include file="/header/validator2.0.jsp" %>
<script type="text/javascript">

var id = '<%=id%>';
function doInit(){
	loadData(id);
}

function loadData(id){
	var url = "<%=contextPath%>/teeAddressGroupController/getAddressGroupById.action";
	var jsonRs = tools.requestJsonRs(url,{"id":id});
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		if(data){
			bindJsonObj2Cntrl(data);
		}
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}
/**
 * 保存
 */
function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/teeAddressGroupController/updatePublicAddressGroup.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			parent.$.MsgBox.Alert_auto("更新成功！");
			window.location.href ="<%=contextPath%>/system/core/base/address/public/group/index.jsp";
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
    var check= $("#form1").valid(); 
    if(!check){
    	return false;
    }
    return true;
}

</script>

</head>
<body onload="doInit()" style="padding-left:10px;padding-right: 10px;">
<div id="toolbar" class="setHeight clearfix" style="padding-top: 10px;padding-bottom:5px;">
    <div class="fl">
		<h4 style="font-size: 16px;font-family:MicroSoft YaHei;margin-left: 10px;">
				编辑分组
		</h4>
	</div>
		<span class="basic_border" style="padding-top: 10px;"></span>
</div>

	<form  method="post" name="form1" id="form1" >
<table class="TableBlock_page" width="90%" align="center">

   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData" width="120" >排序号：</td>
    <td nowrap class="TableData" align="left">
        <input style="font-family: MicroSoft YaHei;" positive_integer="true" type='text' name="orderNo"  newMaxLength='5' size='5' required  positive_integer="true"/>
    </td>
   </tr>
   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData" width="120">分组名称：</td>
    <td nowrap class="TableData" align="left">
        <input style="font-family: MicroSoft YaHei;font-size: 12px;" type='text' name="groupName"   newMaxLength="100" required />
    </td>
   </tr>
    <tr>
    <td style="text-indent: 10px;" nowrap class="TableData" width="220" >公布范围（部门）：</td>
    <td  class="TableData" align="left">
       <input type="hidden" name="toId" id=toDeptIds value="">
        <textarea style="font-family: MicroSoft YaHei;font-size: 12px;" cols=40 name="toName" id="toDeptNames" rows="6" class="readonly BigTextarea" wrap="yes" readonly ></textarea>
        <span class='addSpan'>
			<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/add.png" onClick="selectDept(['toDeptIds','toDeptNames'],'1')" value="选择"/>
			 &nbsp;&nbsp;
			 <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/clear.png" onClick="clearData('toDeptIds','toDeptNames')" value="清空"/>
		</span>
    </td>
   </tr>
   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData" width="220" >公布范围（角色）：</td>
    <td  class="TableData" align="left">
       <input type="hidden" name="privId" id="toRolesIds" value="">
        <textarea style="font-family: MicroSoft YaHei;font-size: 12px;" cols=40 name="privName" id="toRolesNames" rows="6" class="readonly BigTextarea" wrap="yes"  readonly ></textarea>
         <span class='addSpan'>
			<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/add.png" onClick="selectRole(['toRolesIds','toRolesNames'],'1')" value="选择"/>
			 &nbsp;&nbsp;
			 <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/clear.png" onClick="clearData('toRolesIds','toRolesNames')" value="清空"/>
		</span>
    </td>
   </tr>
   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData" width="220" >公布范围（人员)：
    </td>
    <td  class="TableData" align="left">
       <input type="hidden" name="userId" id="toUserIds" value="">
        <textarea style="font-family: MicroSoft YaHei;font-size: 12px;" cols=40 name="userName" id=toUserNames rows="6" class="readonly BigTextarea" wrap="yes"  readonly></textarea>
        <span class='addSpan'>
			<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/add.png" onClick="selectUser(['toUserIds', 'toUserNames'])" value="选择"/>
			 &nbsp;&nbsp;
			 <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/clear.png" onClick="clearData('toUserIds','toUserNames')" value="清空"/>
		</span>
    </td>
   </tr>
   
</table>
<div style="text-align: center;padding-top: 10px;">
	        <input style="width: 45px;height: 25px;" type="button" value="保存" class="btn-win-white" title="保存" onclick="doSave()" >&nbsp;&nbsp;
	        <input style="width: 45px;height: 25px;" type="button" value="返回" class="btn-win-white" title="返回" onClick="history.go(-1);">
	        <input type='hidden' value='<%=id %>' name='sid'/>

</div>
  </form>

</body>
</html>
 