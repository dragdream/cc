<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
   TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
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
var loginUserId=<%=loginUser.getUuid()%>;
function doInit(){
	isCreaterAndOnProgress();
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
		if(data.length>0){
			for(var i=0;i<data.length;i++){
				$("#tbody").append("<tr style='line-height:35px;border-bottom:1px solid #f2f2f2;'>"
						+"<td nowrap align='center' style='width:20%;text-indent:10px;'>" +data[i].reporterName+ "</td>"
						+"<td nowrap align='center' style='width:40%;'>" + data[i].content + "</td>"
						+"<td nowrap align='center' style='width:20%;'>" + data[i].createTimeStr + "</td>"
						+"<td nowrap align='center' style='width:20%;'>" + data[i].progress+"%"+"</td>"                  
			  	+ "</tr>"); 
			}
		}else{
			messageMsg("暂无任何数据 ！","mess","info");
		}
		
	}
}


//判断当前登陆人是不是任务负责人  并且  当前任务的状态是不是进行中 （status=0）
function isCreaterAndOnProgress(){
	var url=contextPath+"/taskController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		var status=data.status;
		var managerId=data.managerId;
		if(status==0&&managerId==loginUserId){
			$("#reportDiv").show();
		}else{
			$("#reportDiv").hide();
		}
	}

}



//新增汇报
function save(){
		//获取任务汇报描述
		var content=$("#content").val();
		//获取任务进度
		var progress=$("#range").val();
		
		if(progress==100){
			$.MsgBox.Confirm ("提示", "您将要汇报的进度为100%，是否确认结束任务？", function(){
				var url=contextPath+"/taskReportController/addReport.action";
				var json=tools.requestJsonRs(url,{taskId:sid,content:content,progress:progress});
				if(json.rtState){
					$.MsgBox.Alert_auto("汇报成功！");
					parent.xparent.datagrid.datagrid("reload");
					window.location.reload();
				}
			 });
		}else{
			var url=contextPath+"/taskReportController/addReport.action";
			var json=tools.requestJsonRs(url,{taskId:sid,content:content,progress:progress});
			if(json.rtState){
				$.MsgBox.Alert_auto("汇报成功！");
				//刷新opener  的datagrid
				parent.xparent.datagrid.datagrid("reload");
				window.location.reload();
				
			}		
		}
}
</script>

<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
<div class="clearfix" style="padding-top: 5px;">
	<b style="font-size: 14px">汇报内容</b>
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
   <div id="mess" style="margin-top: 10px"></div>
</div>
<br />
<br />
<div class="clearfix" style="padding-top: 10px;display: none" id="reportDiv">
	<b style="font-size: 14px">进度汇报</b>
	<input  type="button" class="btn-win-white fr" value="保存"  onclick="save();"  />
	<span class="basic_border" style="padding-top: 5px;"></span>
	<div style="padding-top: 10px;">
        <table class="TableBlock_page" width="100%">
           <tr>
              <td width="15%">完成百分比：</td>
              <td width="85%">
                 <div style="position:relative; width:350px;font-size:12px;margin-top: 20px">
                      <input type="text" id="range" />
                      <br/>
                      <span id="historys"></span>&nbsp;&nbsp;&nbsp;<span>（注：估计完成量与总工作量的百分比）</span>
                 </div>
              </td>
           </tr>
           <tr>
              <td width="15%">进度详情描述：</td>
              <td width="85%">
                 <textarea rows="8" cols="200" style="width:800px" name="content" id="content"></textarea>
              </td>
           </tr>
        </table>
   </div>
</div>
</body>
</html>