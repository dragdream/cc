<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<%
   //项目主键
   String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<title>项目抄送</title>
</head>
<style>
input{
   height:23px;
   }
</style>
<script>
var uuid="<%=uuid%>";
function commit(){
	//获取抄送人员Id串
	if(check()){
		var userIds=$("#userIds").val();
		var url=contextPath+"/projectCopyController/copy.action";
		var json=tools.requestJsonRs(url,{projectId:uuid,userIds:userIds});
		return json.rtState;
	}
	
}

//验证
function check(){
	 var uIds=$("#userIds").val();
	 if(uIds==null||uIds==""){
		 $.MsgBox.Alert_auto("请选择抄送人 ！");
		 return false;
	 }
	 return true;
}
</script>
<body onload="doInit()" style="background-color: #f2f2f2;overflow: hidden">
   <table class="TableBlock" width="100%" style="margin-top: 10px">
       <tr>
          <td style="text-indent: 10px">被抄送人：</td>
          <td>
            <input name="userIds" id="userIds" type="hidden"/>
			<textarea class="BigTextarea readonly" id="userNames" name="userNames" style="height:90px;width:400px"  readonly></textarea>
			<span class='addSpan'>
			   <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectUser(['userIds','userNames'],'14')" value="选择"/>
				 &nbsp;&nbsp;
				<img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('userIds','userNames')" value="清空"/>
			</span>
          </td>
       </tr>
   </table>
</body>
</html>