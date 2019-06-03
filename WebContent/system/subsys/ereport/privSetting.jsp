<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
   int sid =TeeStringUtil.getInteger(request.getParameter("sid"),0);
   
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/header/header2.0.jsp" %>
<title>查看权限设置</title>
<script type="text/javascript">
var sid=<%=sid %>;
function doInit(){
	
	var url=contextPath+"/teeEreportController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}else{
		$.MsgBox.Alert_auto("数据获取失败！");
	}
	
}


//保存
function doSave(){
	var url=contextPath+"/teeEreportController/privSetting.action";
	var param=tools.formToJson("#form1");
	var json=tools.requestJsonRs(url,param);
	return json;
}


</script>
</head>
<body onload="doInit()" style="background-color: #f2f2f2">
    <form action="" id="form1" >
        <input type="hidden" name="sid"  id="sid" value="<%=sid %>" />
        <table class="TableBlock" style="width: 100%">
           <tr>
              <td>人员权限：</td>
              <td>
                <input type="hidden" name="userIds" id="userIds" />
                <textarea rows="5" cols="60" id="userNames" name="userNames" readonly="readonly"></textarea>
                <span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['userIds','userNames'],'14')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('userIds','userNames')" value="清空"/>
				</span>
              </td>
           </tr>
          <tr>
              <td>部门权限：</td>
              <td>
                <input type="hidden" name="deptIds" id="deptIds" />
                <textarea rows="5" cols="60" id="deptNames" name="deptNames" readonly="readonly"></textarea>
                 <span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectDept(['deptIds','deptNames'],'14')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('deptIds','deptNames')" value="清空"/>
				</span>
              </td>
           </tr>
           <tr>
              <td>角色权限：</td>
              <td>
                <input type="hidden" name="roleIds" id="roleIds" />
                <textarea rows="5" cols="60" id="roleNames" name="roleNames" readonly="readonly"></textarea>
                <span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectRole(['roleIds','roleNames'],'14')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('roleIds','roleNames')" value="清空"/>
				</span>
              </td>
           </tr>
        </table>
    </form>
</body>
</html>