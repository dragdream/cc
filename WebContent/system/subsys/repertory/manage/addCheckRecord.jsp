<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/upload.jsp" %>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<%
TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
int userSid = person.getUuid();
String deposId = request.getParameter("deposId");
String time1 = request.getParameter("time1");
String time2 = request.getParameter("time2");
%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	
	<title>新建盘点记录</title>
	<script type="text/javascript" charset="UTF-8">
	$(function() {
		var json = tools.requestJsonRs(contextPath+"/repDepository/depositoryList.action");
		for(var i=0;i<json.rows.length;i++){
			<%
				if(!TeePersonService.checkIsAdminPriv(person)){//如果不是管理员，则判断分库权限
					%>
					if(json.rows[i].managerId==<%=userSid%>){
				<%
				}else{
					%>
					if(true){
						if(i==0){
// 							$("#deposId").append("<option value='0'>全部仓库</option>");
						}
					<%
				}
			%>
				$("#deposId").append("<option value='"+json.rows[i].sid+"'>"+json.rows[i].name+"</option>");
			}
		}
			
			autoTitle();
	});
	
	function autoTitle(){
		var year = new Date().getFullYear();
		var month = new Date().getMonth()+1;
		var date = new Date();
        var lastdate = year + '-' + (month<10?"0"+month:month) + '-' + (date.getDate()<10?"0"+date.getDate():date.getDate());
        
        $("#title").val(lastdate+""+$("#deposId").find("option:selected").html()+"盘库记录");
	}
	
	function save(){
		if(!$("#form1").form("validate")){
			return;
		}
		
		var para = tools.formToJson($("#form1"));
		var url = contextPath+"/deposCheckController/addCheckRecord.action";
		var json = tools.requestJsonRs(url,para);
		alert(json.rtMsg);
		if(json.rtState){
			window.location = "check_item_list.jsp?sid="+json.rtData;
		}
	}
	</script>
</head>
<body >
<div class="base_layout_top" style="position:static">
	<span class="easyui_h1">新建盘库记录</span>
</div>
<br/>
<table class="TableBlock" id="form1" style='margin:0 auto;width:50%;'>
  <tr>
	<td nowrap align="left" width="30%" class="TableContent">盘点名称：</td>
    <td nowrap align="left" class="TableData" width="70%">
    	<input type="text" class="BigInput easyui-validatebox" style="width:200px" required id="title" name="title"/>
    </td>  
  </tr>
  <tr>
  	<td nowrap align="left" width="120" class="TableContent">盘点仓库：</td>
    <td class="TableData" width="180">
    	<select class="BigSelect" id="deposId" name="deposId" onchange="autoTitle()">
    	</select>
    </td>  	
  </tr>
  <tr>
  	<td colspan="2" style="text-align:center">
  		<button class="btn btn-primary" onclick="save()">保存</button>
  		&nbsp;&nbsp;
  		<button class="btn btn-default" onclick="window.location = 'check_record_list.jsp'">返回</button>
  		<input type="hidden" name="sid" value="<%=sid %>" />
  	</td>
  </tr>
</table>
</body>
</html>