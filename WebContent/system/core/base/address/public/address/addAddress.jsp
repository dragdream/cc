<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
	String groupId = request.getParameter("groupId");
	if(groupId == null || "".equals(groupId)){
		groupId = "0";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加通讯组</title>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<%@ include file="/header/validator2.0.jsp" %>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

var id = '<%=id%>';
var groupId = <%=groupId%>;

function doInit(){
	getAddressGroup();
}
/**
 * 保存
 */
function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/teeAddressController/addAddress.action?isPub=1";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		 groupId = $("#groupId ").val(); 
		if(jsonRs.rtState){
			 $.MsgBox.Alert_auto("保存成功！",function(){
				 history.go(-1);
				 //window.location.href ="<%=contextPath%>/system/core/base/address/public/address/addressList.jsp?groupId="+groupId;
			 });
			
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
    return $("#form1").valid(); 
}

function getAddressGroup(){
	var url = "<%=contextPath%>/teeAddressGroupController/getPublicAddressGroups2Priv.action";
	var para = {};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		createSelect(jsonRs.rtData);
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}

function createSelect(rtData){
	var selectObj = document.createElement("select");
	//$("<select name='groupId' ></select>");
	selectObj.setAttribute("name", "groupId");
	selectObj.setAttribute("id", "groupId");
		selectObj.setAttribute("class", "BigSelect");
	
	$.each(rtData,function(i,v){
	     var vOption=document.createElement("option");
	     vOption.setAttribute("value",v.seqId);
	     if(v.seqId == groupId){
	    	 vOption.setAttribute("selected","selected");
		    }
	     vOption.appendChild(document.createTextNode(v.groupName));
	     selectObj.appendChild(vOption);
	});
	$("#groupSelect").append(selectObj);
}


</script>
 
</head>
<body onload="doInit()" style="padding-left: 10px">
   <div id="toorbar"  class="topbar clearfix">
       <div class="fl left">
            <span  style="font-size: 12px;font-weight: bold;">新建联系人</span>
       </div>
       <div class="fr right">
            <input type="button" value="保存" class="btn-win-white" title="保存" onclick="doSave()" >
	        <input type="button" value="返回" class="btn-win-white" title="返回" onClick="history.go(-1);">
       </div>
   </div>

<form  method="post" name="form1" id="form1" >
<table class="TableBlock_page" width="60%" align="center">
	<tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">分组</B></TD>
	</tr>
	<tr>
    <td nowrap class="TableData" width="120" style="text-indent:15px">分组：</td>
    <td nowrap class="TableData" align="left" id="groupSelect">
       <!-- <select name="groupId" id="groupId">
       </select> -->
    </td>
   </tr>
   <tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">个人信息</B></TD>
	</tr>
   <tr>
    <td nowrap class="TableData" width="120" style="text-indent:15px">排序号：</td>
    <td nowrap class="TableData" align="left">
        <input type='text' name="psnNo" id="psnNo" class="BigInput"  size='5' required   positive_integer="true"/>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120" style="text-indent:15px">姓名：</td>
    <td nowrap class="TableData" align="left">
        <input type='text' name="psnName" id="psnName" class="BigInput" maxlength="50"  size="20" required/>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120" style="text-indent:15px">性别：</td>
    <td nowrap class="TableData" align="left">
       <select id="sex" name="sex">
       			<option value="0">男</option>
       		    <option value="1">女</option>
       </select>
    </td>
   </tr>
   <tr>
		<td nowrap class="TableData" width="120" style="text-indent:15px">生日：</td>
		<td nowrap class="TableData">
		<input type="text" name="birthday" id="birthday" size="16" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput">
		</td>
	</tr>
   <tr>
		<td nowrap class="TableData" width="120" style="text-indent:15px">昵称：</td>
		<td nowrap class="TableData">
		<input type='text' name="nickName" id="nickName"  class="BigInput" maxlength="50"  size="20"  />
		</td>
	</tr>
	 <tr>
		<td nowrap class="TableData" width="120" style="text-indent:15px">职务：</td>
		<td nowrap class="TableData">
		<input type='text' name="ministration"  id="ministration"  class="BigInput" maxlength="50"  size="20"  />
		</td>
	</tr>
	<tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">联系方式（单位）</B></TD>
	</tr>
	 <tr>
		<td nowrap class="TableData" width="120" style="text-indent:15px">单位名称：</td>
		<td nowrap class="TableData">
		<input type='text' name="deptName"  id="deptName" class="BigInput" maxlength="50"  size="20"  />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120" style="text-indent:15px">单位地址：</td>
		<td nowrap class="TableData">
		<input type='text' name="addDept"   id="addDept"  class="BigInput" maxlength="100"  size="45" />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120" style="text-indent:15px">邮编：</td>
		<td nowrap class="TableData">
		<input type='text' name="postNoDept" id="postNoDept"  class="BigInput" maxlength="50"  size="20"  />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120" style="text-indent:15px">工作电话：</td>
		<td nowrap class="TableData">
		<input type='text' name="telNoDept"  id="telNoDept"  class="BigInput" maxlength="50"  size="20" />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120" style="text-indent:15px">工作传真：</td>
		<td nowrap class="TableData">
		<input type='text' name="faxNoDept" id="faxNoDept" class=" BigInput" maxlength="50"  size="20" />
		</td>
	</tr>
	<tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">联系方式（个人）</B></TD>
	</tr>
	
	<tr>
		<td nowrap class="TableData" width="120" style="text-indent:15px">个人住址：</td>
		<td nowrap class="TableData">
		<input type='text' name="addHome" id="addHome" class="BigInput" maxlength="50"  size="50" />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120" style="text-indent:15px">个人邮编：</td>
		<td nowrap class="TableData">
		<input type='text' name="postNoHome" id="postNoHome" class="BigInput" maxlength="50"  size="20" />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120" style="text-indent:15px">个人电话：</td>
		<td nowrap class="TableData">
		<input type='text' name="telNoHome" id="telNoHome" class="BigInput" maxlength="50"  size="20" />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120" style="text-indent:15px">手机：</td>
		<td nowrap class="TableData">
		<input type='text' name="mobilNo" id="mobilNo"  class="BigInput" maxlength="50"  size="20"/>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120" style="text-indent:15px">邮箱：</td>
		<td nowrap class="TableData">
		<input type='text' name="email" id="email"   class="BigInput" maxlength="50"  size="20" />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120" style="text-indent:15px">QQ号码：</td>
		<td nowrap class="TableData">
		<input type='text' name="oicqNo" id="oicqNo" class="BigInput" maxlength="50"  size="20"  />
		</td>
	</tr>
	<tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">备 注</B></TD>
	</tr>
	<tr>
	    <td width="120" style="text-indent:15px">备注：</td>
		<td class="TableData"   noWrap>
		<textarea name="notes" id="notes"  class="BigInput" id="notes" rows="5" cols="60" wrap="on"></textarea>
		</td>
	</tr>
	<input type='hidden'  name="id" value="<%=id %>"  />
	  <input type='hidden' value='<%=id %>' name='sid'/>
</table>

  </form>

</body>
</html>
 