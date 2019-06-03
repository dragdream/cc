<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	List<Map<String,String>> calendarList = (List<Map<String,String>>)request.getAttribute("calendarList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp"%>
<title>日程安排</title>
<script type="text/javascript">
function my_note(sid, status) {
	top.$.jBox("iframe:" + contextPath
			+ "/system/core/base/calendar/detail.jsp?id=" + sid, {
		title : "查看日程",
		width : 400,
		height : 300,
		buttons : {}
	});
}

/**
* 获取所有日程
*/
function check_all(){
	var calList =document.getElementsByName("cal_select");
	for (i=0;i<calList.length;i++){
		 if(document.getElementsByName("cal_select_all")[0].checked){
		     calList.item(i).checked=true;
		 }else{
		     calList.item(i).checked=false;
		 }
	}
}
	
/**
*点击单个
*/
function check_one(el){
	if(!el.checked){
		 document.getElementsByName("cal_select_all")[0].checked=false;
	}
}
function get_checked(){
 	checked_str="";
	for(i=0;i<document.getElementsByName("cal_select").length;i++){
		el=document.getElementsByName("cal_select").item(i);
		if(el.checked){  
		      val=el.value;
		      checked_str+=val + ",";
		    }
		  }
		 if(i==0) {
		 el=document.getElementsByName("cal_select");
		 if(el.checked){  
		      val=el.value;
		      checked_str+=val + ",";
		 }
	}
	return checked_str;
}
/**
 * 删除
 */
function deleleCal(){
	var delete_str=get_checked();
	if(delete_str==""){
		alert("要删除日程，请至少选择其中一条。");
		return;
	}
	msg='确认要删除所选日程吗？';
	if(window.confirm(msg)){
		  var url =  contextPath +  "/calendarManage/delByIds.action";
		  var para = {sids:delete_str};
		  var jsonObj = tools.requestJsonRs(url,para);
			//alert(jsonObj.rtState);
		  if(jsonObj.rtState){
				//window.location.reload();
			//删除
			  $("input[name='cal_select']:checked").each(function(i){
				 $(this).parent().parent().remove();
				// alert($(this).parent().parent()[0]);
			  });
			  $("#cal_select_all")[0].checked = false;
			
		  }else{
			  alert(jsonObj.rtMsg);
		  }
	}
}

</script>
</head>
<body class="" topmargin="5">
	<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small">
		<tr>
			<td class="Big"><span class="Big3">&nbsp;&nbsp; 日程安排查询结果</span><br>
			</td>
		</tr>
	</table>
	<br>
	<%
		if (calendarList != null) {
			if (calendarList.size() > 0) {
	%>
			<table class="TableList" width="95%" align="center">
				<thead class="TableHeader">
					<td style="width:30px;" align="center"></td>
					<td nowrap align="center" width="120px;">开始时间</td>
					<td nowrap align="center" width="120px;">结束时间</td>
					<td nowrap align="center" width="120px;">事务类型</td>
					<td nowrap align="center">事务内容</td>
					<td nowrap align="center">安排人</td>
				</thead>
		<%
				for (int i = 0; i < calendarList.size(); i++) {
						Map<String, String> map = new HashMap<String, String>();
						map = calendarList.get(i);
						String status = map.get("status");
					/* 	String[] calLevelNames = { "未指定", "重要/紧急", "重要/不紧急",
								"不重要/紧急", "不重要/不紧急" }; */
						String calTypeName = "工作事务";
						String calLevelStr = map.get("calLevel");
						int calLevelInt = 0;
						if (calLevelStr != null
								&& !calLevelStr.trim().equals("")) {
							calLevelInt = Integer.parseInt(calLevelStr);
						}
						if (map.get("calType") != null
								&& map.get("calType").equals("2")) {
							calTypeName = "个人事务";
						}
						String overStatus = TeeStringUtil.getString(map.get("overStatus")) ;
						String title = "状态：已完成";
						String color = "#00AA00";
						if(overStatus.equals("1")){
							title = "状态：已完成";
						}else{
							if (status != null && status.equals("0")) {
								 title = "状态：未开始";
								 color = "#0000FF";
							}
							if (status != null && status.equals("2")) {
								 title = "状态：已超时";
								 color = "#FF0000";
							}
						}
						/* if (map.get("overStatus") != null && map.get("overStatus").equals("1")) { */
			%>
				<tr class="TableData">
					<td  align="center"> 
					<%if(map.get("isDelete").equals("1")){
						 
				      %>
				      	<input type="checkbox" name="cal_select" value="<%=map.get("seqId") %>" onclick="check_one(this);"></td>
				
				      <%} %>
					
						<td nowrap align="center"><%=map.get("calTime")%></td>
					<td nowrap align="center"><%=map.get("endTime")%></td>
					<td nowrap align="center"><%=calTypeName%></td>
					<td title="<%=title %>">
						<%-- <span class="CalLevel<%=map.get("calLevel")%>" title="<%=calLevelNames[calLevelInt]%>">&nbsp</span> --%>
						<a href="javascript:my_note(<%=map.get("seqId")%>,<%=map.get("overStatus")%>);" style="color: <%=color%>;"><%=map.get("content")%></a>
					</td>
						
					<td nowrap align="center"><%=map.get("managerName")%></td>
				</tr>

		<%
		}
		%>
		<tr>
			<td colspan="6">
				<input type="checkbox" name="cal_select_all"  id="cal_select_all" style="padding:0px 6px 0px 6px ;" onclick="check_all()"><label for="cal_select_all">全选</label>
				<input type="button" class="btn btn-danger" value="删除" onclick="deleleCal();">
			</td>
		</tr>
	</table>
		
	<%
		} else {
	%>
		<table class="MessageBox" align="center" width="340">
			<tr>
				<td class="msg info">
					<div class="content" style="font-size: 12pt">无符合条件的日程安排</div>
				</td>
			</tr>
		</table>
	<%
		}
	} else {
	%>
		<table class="MessageBox" align="center" width="340">
			<tr>
				<td class="msg info">
					<div class="content" style="font-size: 12pt">无符合条件的日程安排</div>
				</td>
			</tr>
		</table>
	<%
	}
	%>
	<br>
	<center style="padding-bottom:6px;">
		<!-- <input type="button" value="打印" class="btn btn-primary" onclick="document.execCommand('Print');"> -->
		<input type="button" class="btn btn-primary" value="返回" onclick=" history.go(-1);">
	</center>
</body>

</html>