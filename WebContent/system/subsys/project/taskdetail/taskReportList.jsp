<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="/common/rangesliderjquery/js/ion.rangeSlider.js"></script> 
<link rel="stylesheet" href="/common/rangesliderjquery/css/normalize.min.css" />
<link rel="stylesheet" href="/common/rangesliderjquery/css/ion.rangeSlider.css" />
<link rel="stylesheet" href="/common/rangesliderjquery/css/ion.rangeSlider.skinNice.css" id="styleSrc"/>
<title>任务汇报</title>
<script>
var sid=<%=sid%>;//任务主键
function doInit(){
	//获取任务
	var url=contextPath+"/taskController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		$("#historys").html("历史完成进度："+data.progress+"%");
		$("#range").ionRangeSlider({
			min: 0,
			max: 100,
			from:data.progress,
			prettify:0,
			type: 'single',//设置类型
			step: 1,
			prefix: "",//设置数值前缀
			postfix: "%",//设置数值后缀
			prettify: true,
			hasGrid: true
		});
	}
	//获取任务汇报列表
	getReportList();
}


//获取汇报内容列表
function  getReportList(){
	var url=contextPath+"/taskReportController/getReportListByTaskId.action";
	var json=tools.requestJsonRs(url,{taskId:sid});
	if(json.rtState){
		var data=json.rtData;
		for(var i=0;i<data.length;i++){
			$("#tbody").append("<tr style='line-height:35px;border-bottom:1px solid #f2f2f2;'>"
					+"<td nowrap align='center' style='width:20%;text-indent:10px;'>" +data[i].reporterName+ "</td>"
					+"<td nowrap align='center' style='width:40%;'>" + data[i].content + "</td>"
					+"<td nowrap align='center' style='width:20%;'>" + data[i].createTimeStr + "</td>"
					+"<td nowrap align='center' style='width:20%;'>" + data[i].progress+"%"+"</td>"                  
		  	+ "</tr>"); 
		}
	}
}

</script>

<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
<div class="clearfix" style="padding-top: 5px;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/project/img/icon_renwuhuibao.png">
		<span class="title">任务汇报</span>
	<span class="basic_border" style="padding-top: 5px;"></span>
	<div style="padding-top: 10px;">
     <table style="border-collapse: collapse;border: 1px solid #f2f2f2;" width="100%" align="center" >
      <tbody id="tbody">
        <tr style="line-height:35px;border-bottom:2px solid #b0deff!important;background-color: #f8f8f8; ">
     	    <td style="text-indent:10px;width:20%;">负责人</td>
     	    <td style="width:40%;">内容</td>
      		<td style="width:20%;">汇报时间</td>
      		<td style="width:20%;">进度百分比</td>
       </tr>
      </tbody>
   </table>
   </div>
</div>


</body>
</html>