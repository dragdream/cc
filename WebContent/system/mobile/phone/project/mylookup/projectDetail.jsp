<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   //项目主键
   String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
%>

<!DOCTYPE HTML>
<html>
<head>
<title>项目管理</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
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
//项目主键
var uuid="<%=uuid%>";
var status=0;//项目状态
//初始化方法
function doInit(){

	getInfoByUuid(uuid);
	

	
	//返回按钮
	backBtn.addEventListener("tap",function(){
		/* window.location = 'index.jsp'; */
		history.go(-1);
	});
}




//根据主键获取详情
function getInfoByUuid(uuid){
	var url=contextPath+"/projectController/getBasicInfoBySid.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{uuid:uuid},
		timeout:10000,
		async:false,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				var data=json.rtData;
				var projectTypeId=json.rtData.projectTypeId;
				renderCustomField(projectTypeId);
				//获取所有class=val  的td
				var divs=$("div[class=val]");
		 
		        if(data.approverName!=""&&data.approverName!=null){
		        	$("#approverDiv").show();
		        }
				bindJsonObj2Cntrl(data);
				
				//项目状态
				status=data.status;
			}
		}
	});
}


//动态渲染自定义字段
function renderCustomField(projectTypeId){
	if(projectTypeId==0){
		projectTypeId=$("#projectTypeId").val();
	}
	var url=contextPath+"/projectCustomFieldController/getListByProjectType.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		async:false,
		data:{projectTypeId:projectTypeId},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				var data=json.rtData;
				for(var i=0;i<data.length;i++){
					var name="FIELD_"+data[i].sid;
					if(data[i].fieldType=="单行输入框"){
						$("#muiContent").append("<div class=\"mui-input-group\">"
								   +"<div class=\"mui-input-row\">"
								   +"<label>"+data[i].fieldName+"</label>"
								   +"</div>"
						           +"<div class=\"app-row-content\" id="+name+" name="+name+" class='val'>"
								   +"</div>"
							       +"</div>");
					}else if(data[i].fieldType=="多行输入框"){
						$("#muiContent").append("<div class=\"mui-input-group\">"
								   +"<div class=\"mui-input-row\">"
								   +"<label>"+data[i].fieldName+"</label>"
								   +"</div>"
						           +"<div class=\"app-row-content\" id="+name+" name="+name+" class='val'>"
								   +"</div>"
							       +"</div>");
					}else if(data[i].fieldType=="下拉列表"){
						var fieldCtrModel=data[i].fieldCtrModel;
						var j= eval("("+fieldCtrModel+")");
						if(j.codeType=="系统编码"){
							$("#muiContent").append("<div class=\"mui-input-group\">"
									   +"<div class=\"mui-input-row\">"
									   +"<label>"+data[i].fieldName+"</label>"
									   +"</div>"
							           +"<div class=\"app-row-content\" id="+name+" name="+name+" class='val'>"
									   +"</div>"
								       +"</div>");
						}else if(j.codeType=="自定义选项"){
							var values=j.value;
							/* var optionNames=values[0].split(",");
							var optionValues=values[1].split(","); */
							$("#muiContent").append("<div class=\"mui-input-group\">"
									   +"<div class=\"mui-input-row\">"
									   +"<label>"+data[i].fieldName+"</label>"
									   +"</div>"
							           +"<div class=\"app-row-content\" id="+name+" name="+name+" class='val'>"
									   +"</div>"
								       +"</div>");	
						}	
					}	
				}
			}
		}
   });
}






</script>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn" ></span>
		<h1 class="mui-title">项目详情</h1>
		<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover" id="moreOpt" style="display: none"></a>
	</header>
	<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目编号</label>
		</div>
		<div class="app-row-content" id="projectNum">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目类型</label>
		</div>
		<div class="app-row-content" id="projectTypeName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目名称</label>
		</div>
		<div class="app-row-content" id="projectName">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目周期</label>
		</div>
		<div class="app-row-content" id="time" >
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目区域</label>
		</div>
		<div class="app-row-content" id="address">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目级别</label>
		</div>
		<div class="app-row-content" id='projectLevel'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目预算</label>
		</div>
		<div class="app-row-content" id='projectBudget'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目负责人</label>
		</div>
		<div class="app-row-content" id='managerName'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目成员</label>
		</div>
		<div class="app-row-content" id='projectMemberNames'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目观察者</label>
		</div>
		<div class="app-row-content" id='projectViewNames'>
		
		</div>
	</div>
	<div class="mui-input-group" id="approverDiv" style="display: none;">
		<div class="mui-input-row">
			<label>项目审批人</label>
		</div>
		<div class="app-row-content" id='approverName'>
		
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目内容</label>
		</div>
		<div class="app-row-content" id='projectContent'>
			
		</div>
			
	</div>
</div>




</body>
</html>