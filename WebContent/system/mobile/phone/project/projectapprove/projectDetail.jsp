<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   //项目主键
   String uuid=TeeStringUtil.getString(request.getParameter("uuid"));

   //审批状态
   int status=TeeStringUtil.getInteger(request.getParameter("status"),0);// 0未审批   1已审批
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
var status=<%=status%>;
//初始化方法
function doInit(){
	getInfoByUuid(uuid);
	if(status==0){//未审批
		$("#moreOpt").show();
	}

	
	backBtn.addEventListener("tap",function(){
		window.location.href="index.jsp";
	});//返回
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
		        //alert(text);
				bindJsonObj2Cntrl(data);
				
				
				
				var status=data.status;
				var statusDesc="";
				//立项中1       审批中2      办理中3        挂起中4       已办结5     已拒绝6
				if(status==1){
					statusDesc="立项中";
				}else if(status==2){
					statusDesc="审批中";
				}else if(status==3){
					statusDesc="办理中";
				}else if(status==4){
					statusDesc="挂起中";
				}else if(status==5){
					statusDesc="已办结";
				}else if(status==6){
					statusDesc="已拒绝";
				}
				$("#status").html(statusDesc);
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



//同意
function approve(uuid){
	if(window.confirm("是否确认同意该项目？")){
		var url=contextPath+"/projectController/approveProject.action";
		
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{uuid:uuid,status:3},
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("已同意！");
					window.location.href="projectDetail.jsp?uuid="+uuid+"&status=1";
				}else{
					alert("操作失败！");
				}
			}
		});
	}
}





//拒绝
function noApprove(uuid){ 
	window.location.href="refusedReason.jsp?uuid="+uuid;
	
}

</script>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn" onclick=""></span>
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
			<label>项目状态</label>
		</div>
		<div class="app-row-content" id="status">
			
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

	<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover" >
		<ul class="mui-table-view">
		      <li class="mui-table-view-cell" onclick="approve('<%=uuid %>')" >批准</li>
		      <li class="mui-table-view-cell" onclick="noApprove('<%=uuid%>')">拒绝</li>
		
		  </ul>
	</div>

</body>
</html>