<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>附件中心</title>
<%@ include file="/header/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/js/seniorreport.js"></script>
<script src="<%=contextPath %>/common/highcharts/js/highcharts.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/jquery.highchartsTable.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/data.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/highcharts-more.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/funnel.js"></script>
<script type="text/javascript">
var attachId="";
$(function () {
	getAttachSeries();
	getAttachSeriesTable();
	getAttachSpaces();
});

function getAttachSeries(){
	 $('#container').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: '各模块所占空间比例'
	        },
	        tooltip: {
	    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: '百分比',
	            data: getAttachSeriesFunc()
	        }]
	    });
}


/* 获取附件模块百分比 */
function getAttachSeriesFunc(){
	var tmpSeries = new Array();
	var url = "<%=contextPath%>/attachCenterController/getAttachSeries.action";
	var jsonObj = tools.requestJsonRs(url);
	if (jsonObj.rtState) {
		var prcs = jsonObj.rtData;
		//alert(prc[0].modelName + ">>" + prc[0].modelCount);
		if(prcs.length>0){
			for(var i=0;i<prcs.length;i++){
				var arr = [];
				arr.push(prcs[i].modelName);
				arr.push(prcs[i].modelCount);
				tmpSeries.push(arr);
			}
		}
	} else {
		alert(jsonObj.rtMsg);
	}
	return tmpSeries;
}
/* 获取附件模块列表 */
function getAttachSeriesTable(){
	var url = "<%=contextPath%>/attachCenterController/getAttachSeriesList.action";
	var jsonObj = tools.requestJsonRs(url);
	if (jsonObj.rtState) {
		var prcs = jsonObj.rtData;
		//alert(prc[0].modelName + ">>" + prc[0].modelCount);
		if(prcs.length>0){
			jQuery.each(prcs,function(i,sysPara){
			$("#tbody").append("<tr class='TableData' align='center'>"
								+"<td >" + sysPara.modelName+ "</td>"
								+"<td >" + sysPara.attachModelCount + "</td>"
								+"<td >" + sysPara.validModelCount + "</td>"
								+"<td >" + sysPara.inValidModelCount + "</td>"
								+"<td >" + sysPara.attachModelSize + "</td>"
								+"<td >" + sysPara.validModelSize + "</td>"
								+"<td >" + sysPara.inValidModelSize + "</td>"
								+"<td ><a href=\"#\" onclick=\"clearAtt('"+sysPara.model+"')\">清除无效附件</a></td>"
					  		+ "</tr>"); 
			});
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}
function seriesFunc(){
	var tmpSeries = new Array();
	var dataArray = [{name:"流程",value:45.0},
	          	 {name:"任务",value:26.8},
	        	 {name:"公告网盘",value:12.8},
	        	 {name:"个人网盘",value:8.5},
	        	 {name:"会议",value:6.2},
	        	 {name:"投票",value:0.7},
	        	 {name:"车辆",value:10.7}];
	
	for(var i=0;i<dataArray.length;i++){
		var arr = [];
		arr.push(dataArray[i].name);
		arr.push(dataArray[i].value);
		tmpSeries.push(arr);
	}
	return tmpSeries;
}


function getAttachSpaces(){
	var url = "<%=contextPath%>/attachCenterController/getAttachSpaces.action";
	var jsonObj = tools.requestJsonRs(url);
	if (jsonObj.rtState) {
		var html = "<table id=\"tbody\" width=\"100%\"  class=\"TableBlock\"><tr class=\"TableHeader\"><td>ID</td><td>路径</td><td>操作</td></tr>";
		var data = jsonObj.rtData;
		for(var i = 0 ;i<data.length;i++){
			if(data[i].isDefault){
				html+="<tr class='TableData'><td >"+data[i].sid+"</td><td style='color:red;font-weight:bolder;'>"+data[i].spacePath+"</td ><td style='text-align:center;'><a href='javascript:void(0);' onclick=editSpace("+data[i].sid+")>编辑</a></td></tr>";
			}else{
				html+="<tr class='TableData'><td>"+data[i].sid+"</td><td>"+data[i].spacePath+"</td><td style='text-align:center;'><a href='javascript:void(0);' onclick=editSpace("+data[i].sid+")>编辑</a>&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick=setDefault("+data[i].sid+")>设置为默认</a></td></tr>";
			}
		}
		html+="</table>";
		$("#attachSpaces").html(html);
	}else{
		
	}
}

function addSpace(){
	attachId = "";
	$("#spacePath").val("");
	$('#spaceDetail').modal('show');
}

function editSpace(sid,spacePath){
	attachId = sid;
	var url = "<%=contextPath%>/attachCenterController/getAttachSpace.action?sid="+attachId;
	var jsonObj = tools.requestJsonRs(url);
	if (jsonObj.rtState) {
		$("#spacePath").val(jsonObj.rtData.spacePath);
		$('#spaceDetail').modal('show');
	}
}

function commit(){
	if(attachId!=""){
		var spacePath = $("#spacePath").val();
		var url = "<%=contextPath%>/attachCenterController/updateAttachSpace.action?sid="+attachId+"&spacePath="+spacePath;
		var jsonObj = tools.requestJsonRs(url);
		if (jsonObj.rtState) {
			top.$.jBox.tip(jsonObj.rtMsg,"success");
			getAttachSpaces();
			$('#spaceDetail').modal('hide');
		}else{
			top.$.jBox.tip("修改失败！","error");
		}
		top.$.jBox.tip(jsonObj.rtMsg,"info");
	}else{
		var spacePath = $("#spacePath").val();
		var url = "<%=contextPath%>/attachCenterController/addAttachSpace.action?spacePath="+spacePath;
		var jsonObj = tools.requestJsonRs(url);
		if (jsonObj.rtState) {
			top.$.jBox.tip(jsonObj.rtMsg,"success");
			getAttachSpaces();
			$('#spaceDetail').modal('hide');
		}else{
			top.$.jBox.tip("添加失败！","error");
		}
	}
}

function setDefault(sid){
	var url = "<%=contextPath%>/attachCenterController/setDefault.action?sid="+sid;
	var jsonObj = tools.requestJsonRs(url);
	if (jsonObj.rtState) {
		top.$.jBox.tip(jsonObj.rtMsg,"success");
		getAttachSpaces();
	}else{
		top.$.jBox.tip("设置成功！","error");
	}
}

function clearAttaches(){
	if(window.confirm("是否清理无效附件？该操作可能会占用过多时间和资源！")){
		var url = contextPath+"/attachCenterController/clearUnusefulAttachment.action";
		top.$.jBox.tip("正在清理中，请耐心等待……","loading");
		tools.requestJsonRs(url,{},true,function(jsonObj){
			top.$.jBox.tip("清理完毕","success");
			window.location.reload();
		});
	}
}

//清理无效的附件 
function clearAtt(model){
	if(model=="email"){
		if(window.confirm("是否确认清理无效附件？该操作可能会占用过多时间和资源！")){
			var url = contextPath+"/attachCenterController/clearUnusefulAttaches.action";
			top.$.jBox.tip("正在清理中，请耐心等待……","loading");
			tools.requestJsonRs(url,{});
			top.$.jBox.tip("清理完毕","success");
			window.location.reload();
			
		}
		
	}else{
		alert("该模块暂不支持该功能！");
	}
	
	
	
}
</script>
</head>
<body style="margin-top: 10px;margin-bottom: 10px;">
<div id="attachSpace" style='width:80%;height:auto;margin:0 auto;'>
	<div style='text-align:left;margin:10px 0;'>附件存储空间</div>
	<div style='margin:5px 0;'><input class='btn btn-success' type='button' onclick='addSpace()' value='添加'/></div>
	<div id="attachSpaces">
		
	</div>
	<h1><hr></h1>
</div>
<div id="container" style="min-width:700px;height:400px"></div>

<div align="center">
<br/>
<!-- <button class="btn btn-success" onclick="clearAttaches()">清理无效附件</button> -->
<br/><br/>
<table id="tbody" width="80%"  class="TableBlock">
	<tr class="TableHeader">
		<td>模块</td>
		<td>总文件数量</td>
		<td>有效文件数量</td>
		<td>无效文件数量</td>
		<td>总文件大小</td>
		<td>有效文件大小</td>
		<td>无效文件大小</td>
		<td>操作</td>
	</tr>
</table>
</div>
<div class="modal fade" id="spaceDetail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="myModalLabel">附件空间信息</h4>
		      </div>
		      <div class="modal-body" style='text-align:center;height:60px;'>
		       		附件存储路径：<input type='text' class="BigInput" style='width:300px;' id='spacePath' name="spacePath"/>
			      <button class="btn btn-primary" onclick="commit()">确定</button><br/>
			      (注：地址如D:/attach/folder)
		      </div>
		</div>
	</div>
</div>
</body>
</html>