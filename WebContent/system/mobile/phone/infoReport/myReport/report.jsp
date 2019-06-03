<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   int  pubRecordItemId=TeeStringUtil.getInteger(request.getParameter("pubRecordItemId"), 0);
   int  pubRecordId=TeeStringUtil.getInteger(request.getParameter("pubRecordId"), 0);
   int  taskTemplateId=TeeStringUtil.getInteger(request.getParameter("taskTemplateId"), 0);
   String taskTemplateName=TeeStringUtil.getString(request.getParameter("taskTemplateName"));
%>
<!DOCTYPE HTML>
<html>
<head>
<title>信息汇报</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<script src="<%=contextPath %>/common/js/sys2.0.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn"></span>
	<span class="mui-icon mui-icon-checkmarkempty mui-pull-right " onclick="save()" id="saveBtn"></span>
	<h1 class="mui-title"><%=taskTemplateName %>&nbsp;-&nbsp;信息汇报</h1>
	
</header>

<div id="muiContent" class="mui-content">
	<form id="form1" name="form1" enctype="multipart/form-data">
		<input type="hidden" name="pubRecordItemId" value="<%=pubRecordItemId %>" />
        <input type="hidden" name="pubRecordId" value="<%=pubRecordId %>" />
        <input type="hidden" name="taskTemplateId" value="<%=taskTemplateId %>" />
		
		
		
	</form>	
</div>


<script>
var pubRecordItemId=<%=pubRecordItemId %>;
var pubRecordId=<%=pubRecordId %>;
var taskTemplateId=<%=taskTemplateId %>;
var taskTemplateName="<%=taskTemplateName %>";



function doInit(){
	renderBasicInfo();
}


mui.ready(function() {
	
	backBtn.addEventListener("tap",function(){
		history.go(-1);
	});//返回
	
	
});


//获取汇报的基础信息
function renderBasicInfo(){
	
	var url=contextPath+contextPath+"/TeeTaskTemplateItemController/getListByTemplateId.action";;
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{taskTemplateId:taskTemplateId},
		timeout:10000,
		async:false,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				var data=json.rtData;
				if(data!=null&&data.length>0){
					for(var i=0;i<data.length;i++){
						var render=[];
						var ctrId="DATA_"+data[i].sid;
						if(data[i].fieldType==1){//单行文本 
							render.push("<div class=\"mui-input-group\">");
							render.push("<div class=\"mui-input-row\">");
							render.push("<label>"+data[i].fieldName+"</label>");
							render.push("</div>");
							
							render.push("<div class=\"mui-input-row\" style=\"height:inherit\">");
							render.push("<input type=\"text\" name='"+ctrId+"' id='"+ctrId+"'  />");
							render.push("</div>");			
							
							render.push("</div>");	
						}else if(data[i].fieldType==3){// 数字文本 
							render.push("<div class=\"mui-input-group\">");
							render.push("<div class=\"mui-input-row\">");
							render.push("<label>"+data[i].fieldName+"</label>");
							render.push("</div>");
							
							render.push("<div class=\"mui-input-row\" style=\"height:inherit\">");
							render.push("<input type=\"text\" name='"+ctrId+"' id='"+ctrId+"'   isNumber=\"true\"  />");
							render.push("</div>");			
							
							render.push("</div>");	
						
						}else if(data[i].fieldType==2){//多行文本框
							render.push("<div class=\"mui-input-group\">");
							render.push("<div class=\"mui-input-row\">");
							render.push("<label>"+data[i].fieldName+"</label>");
							render.push("</div>");
							
							render.push("<div class=\"mui-input-row\" style=\"height:inherit\">");
							render.push("<textarea name='"+ctrId+"' id='"+ctrId+"'  ></textarea>");
							render.push("</div>");			
							
							render.push("</div>");	
							
						}else if(data[i].fieldType==5){//下拉列表
							render.push("<div class=\"mui-input-group\">");
							render.push("<div class=\"mui-input-row\">");
							render.push("<label>"+data[i].fieldName+"</label>");
							render.push("</div>");
							
							render.push("<div class=\"mui-input-row\" style=\"height:inherit\">");
							render.push("<select name='"+ctrId+"' id='"+ctrId+"'  ></select>");
							render.push("</div>");			
							
							render.push("</div>");	
						}else if(data[i].fieldType==4){//日期时间
							render.push("<div class=\"mui-input-group\">");
							render.push("<div class=\"mui-input-row\">");
							render.push("<label>"+data[i].fieldName+"</label>");
							render.push("</div>");
							
							render.push("<div class=\"mui-input-row\" style=\"height:inherit\">");
							render.push("<input type=\"text\" name='"+ctrId+"' id='"+ctrId+"'   onClick=\"WdatePicker({dateFmt:'"+data[i].showType+"'})\"  class=\"Wdate\" />");
							render.push("</div>");			
							
							render.push("</div>");
							
						}	
						
						//渲染一个大概
						$("#form1").append(render.join(""));
					
						//处理下拉列表
						if(data[i].fieldType==5){
							var showType=data[i].showType.split(",");
						    for(var j=0;j<showType.length;j++){
						    	$("#"+ctrId).append("<option value='"+showType[j]+"'>"+showType[j]+"</option>");
						    } 
						}
					}
					
				}else{
					var render=[];
					render.push("<div id=\"mess\" align=\"center\" style=\"padding:20px;\"></div>");
					$("#form1").append(render.join(""));
					$("#saveBtn").hide();
				    messageMsg("该任务模板暂且没有基础信息，上传附件请在pc端进行操作！", "mess","info");
				}
			}
		}
	});
		
}



//新增汇报
function save(){
	if($("#form1").valid()){
		var url=contextPath+"/TeeTaskPubRecordItemController/report.action";
		var para=formToJson("#form1") ;
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:para,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					window.location = "index.jsp";
				}
			}
		});
		

	}
	

}
</script>
 <script>
      $("#form1").validate();
 </script>

</body>
</html>