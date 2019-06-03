<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//任务主键
  int  supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
//反馈主键
  int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发表反馈</title>
</head>
<script>
var supId=<%=supId%>;
var sid=<%=sid%>;

//初始化
function doInit(){
   if(sid>0){
	   getInfoBySid();
   }
}

//根据主键  获取详情
function getInfoBySid(){
	var url=contextPath+"/supFeedBackController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}
}
//保存
function save(){
	if(check()){
		var url=contextPath+"/supFeedBackController/addOrUpdate.action";
		var param=tools.formToJson("#form1");
		var json=tools.requestJsonRs(url,param);
		return json.rtState;
	}
}
//验证
function check(){
	var title=$("#title").val();
	var content=$("#content").val();
    if(title==""||title==null){
    	$.MsgBox.Alert_auto("请填写标题！");
    	return false;
    }
    if(content==""||content==null){
    	$.MsgBox.Alert_auto("请填写内容！");
    	return false;
    }
    return true;
}


</script>
<body  style="background-color: #f2f2f2" onload="doInit()">
<form id="form1">
   <input type="hidden" value="<%=supId %>" id="supId" name="supId" />
   <input type="hidden" value="<%=sid %>" id="sid" name="sid" />
   <table class="TableBlock" width="100%">
      <tr>
         <td style="text-indent: 10px">标题：</td>
         <td>
            <input type="text" name="title" id="title" style="height: 23px;width: 300px"/>
         </td>
      </tr>
      <tr>
         <td style="text-indent: 10px">缓急：</td>
         <td>
            <select id="level" name="level" style="height: 23px;width: 300px">
               <option value="0">普通</option>
               <option value="1">紧急</option>
            </select>
         </td>
      </tr>
      <tr>
         <td style="text-indent: 10px">内容：</td>
         <td>
           <textarea id="content" name="content" style="width: 400px;height: 80px"></textarea>
         </td>
      </tr>
      
   </table>
</body>

</form>
</html>