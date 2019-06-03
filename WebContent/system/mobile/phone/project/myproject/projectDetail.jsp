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
	renderOptBtns();
	getInfoByUuid(uuid);
	
	
	//根据项目状态  判断是否存在任务列表
	if(status==3||status==4||status==5){
		$("#footerDiv").show();
	}
	
	
	//任务列表
	a2.addEventListener("tap",function(){
		window.location = 'taskList.jsp?projectId=<%=uuid%>&&status='+status;
	});
	
	//返回
	backBtn.addEventListener("tap",function(){
		window.location = 'index.jsp?status='+status;
	});
}



//渲染操作
function renderOptBtns(){
	//判断当前登陆的用户是不是项目负责人  或者  项目创建者
    var url=contextPath+"/projectController/isCreaterOrManager.action";
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
				if(data==1){//是项目负责人  或者  项目创建者
					
				    var url1=contextPath+"/projectController/getInfoByUuid.action";
				    mui.ajax(url1,{
						type:"POST",
						dataType:"JSON",
						data:{uuid:uuid},
						timeout:10000,
						async:false,
						success:function(text1){
							var json1 = eval("("+text1+")");
							if(json1.rtState){
						    	var data1=json1.rtData;
						    	if(data1.status==3){//办理中：创建任务、结束项目、挂起项目、销毁项目、变更项目
						    		$("#moreOpt").show();
						    		$("#createLi").show();
						    		$("#endLi").show();
						    		$("#hangLi").show();
						    		$("#destroyLi").show();
						    		$("#changeLi").show();
						    	}else if(data1.status==4){//挂起中：恢复挂起、销毁项目
						    		$("#moreOpt").show();
						    		$("#recoverLi").show();
						    		$("#destroyLi").show();
						    	}else if(data1.status==5){//已结束：销毁项目
						    		$("#moreOpt").show();
						    		$("#destroyLi").show();
						    	}
						    }
						}
				    });  
				}
			}
		}

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
//		 		alert(tds.length);
//		 		for(var i=0;i<tds.length;i++ ){
//		 			var tdId=tds[i].id;
//		 			$("#"+tdId).html(data[tdId]);
//		 		}
		 
		        if(data.approverName!=""&&data.approverName!=null){
		        	$("#approverDiv").show();
		        }
		        //alert(text);
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


//创建任务
function  createTask(uuid){
	var url=contextPath+"/system/mobile/phone/project/myproject/addTask.jsp?projectId="+uuid;
	window.location.href=url;
}
//结束项目
function end(uuid){
	
	if(window.confirm("是否确认结束该项目？")){
		var url=contextPath+"/projectController/finish.action";
		var param={uuid:uuid};
		//var json=tools.requestJsonRs(url,param);
		mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:param,
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				alert("已完成！");
				window.location.reload();
			}
		}
	});	
  }
}


//挂起任务
function  hang(uuid){
	if(window.confirm("是否确认挂起该项目？")){
		var url=contextPath+"/projectController/hang.action";
		var param={uuid:uuid,status:4};
		//var json=tools.requestJsonRs(url,param);
		mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:param,
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				alert("挂起成功！");
				window.location.reload();
			} 
		}
	});	
  }

}
//恢复项目
function recover(uuid){
	if(window.confirm("是否确认恢复该项目？")){
		var url=contextPath+"/projectController/restoreHang.action";
		var param={uuid:uuid,status:3};
		//var json=tools.requestJsonRs(url,param);
		mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:param,
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				alert("还原成功！");
				window.location.reload();
			} 
		}
	});	
  }
}
//变更项目
function  change(uuid){
	var url=contextPath+"/system/mobile/phone/project/myproject/addOrUpdate.jsp?uuid="+uuid;
	window.location.href=url;
}

//销毁项目
function destroy(uuid){
	
	if(window.confirm("是否确认销毁该项目？")){
		var url=contextPath+"/projectController/delByUUid.action";
		var param={uuid:uuid};
		//var json=tools.requestJsonRs(url,param);
		mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:param,
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				alert("销毁成功！");
				history.go(-1);
			}
		}
	});	
  }
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

<nav class="mui-bar mui-bar-tab" id="footerDiv" style="display: none">
		   <a id="a1" class="mui-tab-item mui-active" style="font-weight:bold">
				<!-- <span class="mui-icon mui-icon-compose"></span> -->
				<span class="mui-tab-label" >项目详情</span>
			</a>
			<a id="a2" class="mui-tab-item mui-active" onclick="">
				<!-- <span class="mui-icon mui-icon-search"></span> -->
				<span class="mui-tab-label" >任务列表</span>
			</a>
</nav>
	<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover" >
		<ul class="mui-table-view">
		      <li class="mui-table-view-cell" onclick="createTask('<%=uuid %>')" id="createLi" style="display: none">创建任务</li>
		      <li class="mui-table-view-cell" onclick="end('<%=uuid%>')" id="endLi" style="display: none">结束项目</li>
		      <li class="mui-table-view-cell" onclick="hang('<%=uuid %>')" id="hangLi" style="display: none">挂起项目</li>
		      <li class="mui-table-view-cell" onclick="recover('<%=uuid %>')" id="recoverLi" style="display: none">恢复挂起</li>
		      <li class="mui-table-view-cell" onclick="change('<%=uuid %>')" id="changeLi" style="display: none">变更项目</li>
		      <li class="mui-table-view-cell" onclick="destroy('<%=uuid %>')" id="destroyLi" style="display: none">销毁项目</li>
		
		  </ul>
	</div>
<br/><br/><br/>
</body>
</html>