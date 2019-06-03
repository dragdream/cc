<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<title>安全日志</title>
<style>
table{
	border-collapse: collapse;
    border: 1px solid #f2f2f2;
    width:100%;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	font-weight:bold;
}
table tr:first-child{
	border-bottom:2px solid #b0deff!important;
	background-color: #f8f8f8; 
}
</style>

<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>



<script type="text/javascript">

function doInit(){
	//获取系统日志
	getSysLog();
}



/**
 * 获取更新密码列表
 */
function getSysLog(){
	var url = "<%=contextPath %>/sysLogManage/getLogByLoginPerson.action";
	var para =  {count:50 , type :''} ;
	var jsonRs = tools.requestJsonRs(url,para);
	//alert(jsonRs);
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		if(data.length >0){
			var tableStr = "<table width='90%' align='center' style='font-size:12px;' >"
		      + " <tbody id='tbody'>"
		      + "<tr >"
		      + "<td width='15%'>操作用户 </td>"
		      + "<td width='20%'> 修改时间</td>"
		     // + "<td > 所属部门</td>"
		      + "<td width='15%' > IP地址</td>"
		      + "<td width='40%'> 备注</td>"
		      +"</tr>";
			for(var i = 0;i<data.length ; i++){
				var obj = data[i];
				var id = obj.sid;
				var time = obj.time + "";
				var timeStr = getFormatDateStr(time , 'yyyy-MM-dd HH:mm');
				tableStr = tableStr + "<tr  align='center'>"
				+"<td >" + obj.userName + "</td>"
				+"<td>" + timeStr+ "</td>"
				//+"<td>" + seal.deptName + "</td>"
				+"<td >" + obj.ip + "</td>"
				+"<td>" + obj.remark + "</td>"
			
				+ "</tr>";		
			}
		    tableStr = tableStr + " </tbody></table>";
			$("#sysLog").append(tableStr);
		}else{
			messageMsg("没有相关数据", "sysLog" ,'' ,280);
		}
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
	
}

</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
  <div id="toolbar" class="clearfix" style="margin:  10px 0">
   <img class="title_img" src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_aqrz.png" alt="" />
   &nbsp;<span class="title">最近50条安全日志</span>
</div>
  

  
  <div style='padding-top:5px;'>
  
	<div id="sysLog"> 
	
	</div>
  </div>
</body>
</html>