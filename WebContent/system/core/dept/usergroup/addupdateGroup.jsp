
<%@page import="com.tianee.webframe.util.str.TeeUtility"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
  	String uuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header/header2.0.jsp" %>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>自定义分组</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/src/orgselect.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys2.0.js"></script>

<script type="text/javascript">

var uuid = '<%=uuid%>';

function doInit(){
	//添加例子一
	if(uuid != ""){
		var url = "<%=contextPath %>/userGroup/getUserGroupById.action";
		var para = {uuid:uuid};
		//alert(uuid);
		var jsonObj = tools.requestJsonRs(url,para);
		
		if(jsonObj){
			var json = jsonObj.rtData;
			//alert(json.uuid);
			if(json.uuid){
				bindJsonObj2Cntrl(json);
			}
			
		}else{
			$.MsgBox.Alert_auto(jsonObj.rtMsg);
		}
	}
	
}

/**
 * 保存
 */
function doSave(){
	if(check()){
		var url = "<%=contextPath %>/userGroup/addUpdateUserGroup.action";
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		//alert(jsonRs);
		if(jsonRs.rtState){
			parent.$.MsgBox.Alert_auto(jsonRs.rtMsg);
			window.location = "<%=contextPath%>/system/core/dept/usergroup/personGroup.jsp";
		    //window.location.reload();
		}else{
			parent.$.MsgBox.Alert_auto(jsonRs.rtMsg);;
		}
	}
	
}


function checkStr(str){ 
    var re=/[\\"']/; 
    return str.match(re); 
  }
 
function check() {
	return $("#form1").form('validate'); 
	
  }
  
function doReturn() {
	window.location.href = "<%=contextPath%>/system/core/dept/usergroup/personGroup.jsp";
	
  }

</script>

</head>
<body onload="doInit()">
 <div style="margin-top: 5px;margin-bottom: 5px;margin-left: 5px;">
   <img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/icon_yhcx.png" align="absMiddle">&nbsp;&nbsp; 
   <span>
      
        <%
         if(TeeUtility.isNullorEmpty(uuid)){
        	
        	  %>
        	  新建分组
        	  
        	  <%
         }else{
        	 %>	 
        	 编辑分组
        	 
         <%}
        %></span>
    </div>
  <form   method="post" name="form1" id="form1">
<table class="TableBlock_page" width="80%" align="center">
			<tr class="TableLine2">
				<td nowrap style="text-indent:15px;">分组名称：</td>
				<td nowrap><input type="text" name="groupName" id="groupName" class="easyui-validatebox BigInput" required="true" >&nbsp;</td>
			</tr>

			<tr class="TableLine1">
				<td nowrap style="text-indent:15px;">排序号：</td>
				<td nowrap><INPUT type=text name="orderNo" id="orderNo" class="easyui-validatebox BigInput" required="true" size="10" validType ='intege[]'></td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent:15px;">分配用户：</td>
				<td nowrap class="TableData"><input type="hidden"
					name="userListIds" id="userListIds" value=""> <textarea cols="60"
						name="userListNames" id="userListNames" rows="6"
						style="overflow-y: auto;" class="BigTextarea" wrap="yes" readonly></textarea>
					
					<span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectUser(['userListIds', 'userListNames'])" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="clearData('userListIds', 'userListNames')" value="清空"/>
					</span>
					</td>
			</tr>

			<tr class="TableControl"> 
				<td colspan="2" align="center">
				   <div align="center">
				    <input type="button" value="保存"
					class="btn-win-white" onclick="doSave();">&nbsp;&nbsp;
					
					<input type="button" value="返回"
					class="btn-win-white" onclick="doReturn();">&nbsp;&nbsp;
					<input type="text" id="uuid" name="uuid" style="display:none;"/>	
					<input type="hidden" id="userId" name="userId" value="0"/>
					<input type="text" id="userUuid" name="userUuid" style="display:none;"/>				
				  </div>
				</td>
				
			</tr>
		</table>
   </form>
</body>
</html>