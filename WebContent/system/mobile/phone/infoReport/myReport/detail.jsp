<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	//任务发布记录项
	int taskPubRecordItemId=TeeStringUtil.getInteger(request.getParameter("taskPubRecordItemId"),0);
	//任务模板名称
	String taskTemplateName=TeeStringUtil.getString(request.getParameter("taskTemplateName"));
	//任务模板主键
	int taskTemplateId=TeeStringUtil.getInteger(request.getParameter("taskTemplateId"),0);
%>

<!DOCTYPE HTML>
<html>
<head>
<title>汇报详情</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script src="<%=contextPath %>/common/js/sys2.0.js"></script>
<style>
#topPopover {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 120px;
}
#topPopover .mui-popover-arrow {
	left: auto;
	right: 6px;
}

.app-row{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
}
.app-row br{
	margin:0px;
}
.app-row-content{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
	color:gray;
}

</style>
</head>

<script type="text/javascript">
var taskPubRecordItemId=<%=taskPubRecordItemId %>;
var taskTemplateName="<%=taskTemplateName %>";
var taskTemplateId="<%=taskTemplateId %>";
//初始化方法
function doInit(){

	//获取汇报基础信息
	getRepDataByRecordItemId();
	//获取历史信息
	getHistoryReportList();
	
	
	//返回按钮
	backBtn.addEventListener("tap",function(){
		/* window.location = 'index.jsp'; */
		history.go(-1);
	});
}



//获取汇报的基础信息
function getRepDataByRecordItemId(){
	
	
	var url=contextPath+"/TeeTaskPubRecordItemController/getRepDataByRecordItemId.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{taskPubRecordItemId:taskPubRecordItemId},
		timeout:10000,
		async:false,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				var dataList=json.rtData[0];
				var attList=json.rtData[1];
				//基础信息
				if(dataList!=null&&dataList.length>0){
					var render=[];
					for(var i=0;i<dataList.length;i++){
						render.push("<div class=\"mui-input-group\" style='border-bottom:1px solid #f0f0f0'>");
						render.push("<div class=\"mui-input-row\">");
						render.push("<label>"+dataList[i].fieldName+"：</label>");
						render.push("<label>"+dataList[i].fieldValue+"</label>");
						render.push("</div>");
// 						render.push("<div class=\"app-row-content\" >");
						
// 						render.push("</div>");
						render.push("</div>");
						
					}
					$("#div1").append(render.join(""));
				}else{
					var render=[];
					render.push("<div id=\"mess\" align=\"center\"></div>");
					$("#div1").append(render.join(""));
				    messageMsg("该任务模板暂且没有基础信息！", "mess","info");
				}
				

				 //附件信息
				if(attList!=null&&attList.length>0){
					$.each(attList,function(index, item){
						 $("#attachList").append("<div ><a href='javascript:void(0);' onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "</a><div>");
					});
				}else{
					$("#attachList").attr("align","center");
				    messageMsg("暂无附件信息！", "attachList","info");
				}
			}
		}
	});

}


//获取历史汇报信息
function getHistoryReportList(){
    var url=contextPath+"/TeeTaskPubRecordItemController/getHistoryReportList.action?taskTemplateId="+taskTemplateId+"&taskPubRecordItemId="+taskPubRecordItemId;
    mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:null,
		timeout:10000,
		async:false,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
		    	var data=json.rtData;
		    	if(data!=null&&data.length>0){
		    		var render=[];
		    		for(var i=0;i<data.length;i++){
		    			render.push("<div><a href=\"#\" onclick=\"detail("+data[i].sid+")\">"+data[i].createTimeStr+"</a></div>");
		    		}
		    		$("#div3").append(render.join(""));
		    	}else{
		    		var render=[];
					render.push("<div id=\"mess2\" align=\"center\"></div>");
					$("#div3").append(render.join(""));
				    messageMsg("暂无历史汇报信息！", "mess2","info");
		    	}
		    }
		}
	});

}


//历史信息  查看详情
function detail(sid){
	window.location.href="detail.jsp?taskPubRecordItemId="+sid+"&taskTemplateName="+taskTemplateName+"&taskTemplateId="+taskTemplateId;
}
</script>
<body onload="doInit();">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn" ></span>
		<h1 class="mui-title">汇报详情</h1>
		<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover" id="moreOpt" style="display: none"></a>
	</header>
	<div id="muiContent" class="mui-content">
		<div class="mui-card">
			<div class="mui-card-header">基础信息</div>
			<div class="mui-card-content">
				<div class="mui-card-content-inner" id="div1">
				</div>
			</div>
		</div>
		
		<div class="mui-card">
			<div class="mui-card-header">附件信息</div>
			<div class="mui-card-content">
				<div class="mui-card-content-inner" id="attachList">
				</div>
			</div>
		</div>


		<div class="mui-card">
			<div class="mui-card-header">历史信息</div>
			<div class="mui-card-content">
				<div class="mui-card-content-inner" id="div3">
				</div>
			</div>
		</div>
		
	
</div>




</body>
</html>