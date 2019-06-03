<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   //任务主键
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>

<!DOCTYPE HTML>
<html>
<head>
<title>督办管理</title>
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
//督办任务主键
var sid=<%=sid%>;
var typeName;
var typeSid;
//初始化方法
function doInit(){
	renderOptBtns();
	getInfoBySid(sid);
	
	
	//返回
	backBtn.addEventListener("tap",function(){
		window.location = '../list.jsp?typeName='+typeName+"&&typeSid="+typeSid;
	});
	
	//基本详情
	b1.addEventListener("tap",function(){
		window.location='index.jsp?sid=<%=sid%>';
	});
	
	//办理情况
	b2.addEventListener("tap",function(){
		window.location='feedBackRecords.jsp?supId=<%=sid%>'+'&&typeName='+typeName+'&&typeSid='+typeSid;
	});
	
	//催办记录
	b3.addEventListener("tap",function(){
		window.location='urgeRecords.jsp?supId=<%=sid%>'+'&&typeName='+typeName+'&&typeSid='+typeSid;
	});
	
	//办结申请记录
	b4.addEventListener("tap",function(){
		window.location='endApplyRecords.jsp?supId=<%=sid%>'+'&&typeName='+typeName+'&&typeSid='+typeSid;
	});
	//暂停恢复申请记录
	b5.addEventListener("tap",function(){
		window.location='pauseRecoverApplyRecords.jsp?supId=<%=sid%>'+'&&typeName='+typeName+'&&typeSid='+typeSid;
	});
}



//渲染操作
function renderOptBtns(){
	var url=contextPath+"/supervisionController/getStatusAndRole.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
		    	var data=json.rtData;
		    	var status=data.status;
		    	var isCreater=data.isCreater;
		    	var isLeader=data.isLeader;
		    	var isManager=data.isManager;
		    	var isAssist=data.isAssist;
		    	
		    	if(status==0){//未发布
		    		if(isCreater==1){
		    			$("#optButton").show();
		    			$("#publish").show();
		    		}
		    	}else if(status==1){//办理中
		    		if(isCreater==1){
		    			$("#optButton").show();
		    			$("#create").show();
		    			$("#urge").show();
		    		}
		    		if(isLeader==1){
		    			$("#optButton").show();
		    			$("#urge").show();
		    		}
		    		if(isManager==1){
		    			$("#optButton").show();
		    			$("#feedback").show();
		    			$("#applyPause").show();
		    			$("#applyFinish").show();
		    		}
		    		if(isAssist==1){
		    			$("#optButton").show();
		    			$("#feedback").show();
		    		}
		    	}else if(status==2||status==4||status==5||status==6){//暂停申请中      恢复申请中    办结申请中 已办结
		    		
		    	}else if(status==3){//暂停中
		    		if(isManager==1){
		    			$("#optButton").show();
		    			$("#applyRecover").show();	
		    		}
		    	}else if(status==7){//带接收
		    		if(isManager==1){
		    			$("#optButton").show();
		    			$("#receive").show();
		    		}
		    	    if(isLeader){
		    	    	$("#optButton").show();
		    			$("#create").show();
		    			$("#urge").show();
		    	    }
		    	}	
		    }
		},
		error:function(){
			
		}
	});

}
//根据主键获取详情
function getInfoBySid(sid){
	var url=contextPath+"/supervisionController/getInfoBySid.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{sid:sid},
		timeout:10000,
		async:false,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				var data=json.rtData;
		        bindJsonObj2Cntrl(data);
				if(data.parentName==""||data.parentName==null){
					$("#parentName").html("无");
				}
				var status=data.status;
				var desc="";
				if(status==0){
					desc="未发布";
				}else if(status==1){
					desc="办理中";
				}else if(status==2){
					desc="暂停申请";
				}else if(status==3){
					desc="暂停中";
				}else if(status==4){
					desc="恢复申请中";
				}else if(status==5){
					desc="办结申请中";
				}else if(status==6){
					desc="已办结";
				}else if(status==7){
					desc="未接收";
				}
				$("#status").html(desc);
				
				var  attachments = data.attachmentsMode;
				  if(attachments.length > 0){
					  $.each(attachments, function(index, item){  
						  $("#attachList").append("<div ><a href='javascript:void(0);' onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "&nbsp;&nbsp;"+"</a><div>");
					  });
				  }
			  //给全局变量赋值
			  typeName=data.typeName;
			  typeSid=data.typeId;
			}
		}
	});
}

//签收任务
function receive(){
	if(window.confirm("是否确认签收该任务？")){
		 var url = contextPath + "/supervisionController/receive.action";
		 var para = {sid:sid};
		//var json=tools.requestJsonRs(url,param);
		mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:para,
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				alert("签收成功！");
				window.location.reload();
			}
		}
	});	
  }
}

//发布任务
function publish(){
	if(window.confirm("是否确认发布该督办任务？")){
		var url=contextPath+"/supervisionController/publish.action";
		 var para = {sid:sid};
		//var json=tools.requestJsonRs(url,param);
		mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:para,
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				alert("发布成功！");
				window.location.reload();
			}
		}
	});	
 }
}



//创建子任务
function create(){
	var url="../addOrUpdate.jsp?parentId="+sid;
	window.location=url;
}


//任务催办
function urge(){
	var url="doUrge.jsp?supId="+sid;
	window.location=url;
	
}



//发表反馈
function feedback(){
	var url="doFeedBack.jsp?supId="+sid;
	window.location=url;
}


//申请暂停   //申请恢复    //申请办结
function apply(type){
	var url="apply.jsp?supId="+sid+"&&type="+type;
	window.location=url;
}
</script>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn" onclick=""></span>
		<h1 class="mui-title">督办任务详情</h1>
		<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover" id="optButton" style="display: none"></a>
	</header>
	
	<!-- 底部选项卡 -->
	<nav class="mui-bar mui-bar-tab ">
			<a id="b1" class="mui-tab-item mui-active" href="#" onclick="" style="font-weight:bold">基本详情</a>
			<a id="b2" class="mui-tab-item " href="#" onclick="">办理情况</a>
			<a class="mui-tab-item" href="#Popover_2">申请记录</a>
    </nav>
		
	<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>工作事项</label>
		</div>
		<div class="app-row-content" id="supName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>责任领导</label>
		</div>
		<div class="app-row-content" id="leaderName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>主办人</label>
		</div>
		<div class="app-row-content" id="managerName">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>协办人</label>
		</div>
		<div class="app-row-content" id="assistNames" >
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>开始时间</label>
		</div>
		<div class="app-row-content" id="beginTimeStr">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>结束时间</label>
		</div>
		<div class="app-row-content" id='endTimeStr'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>工作状态</label>
		</div>
		<div class="app-row-content" id='status'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>创建人员</label>
		</div>
		<div class="app-row-content" id='createrName'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>工作描述</label>
		</div>
		<div class="app-row-content" id='content'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>上级任务</label>
		</div>
		<div class="app-row-content" id='parentName'>
		
		</div>
	</div>
	<div class="mui-input-group"  style="">
		<div class="mui-input-row">
			<label>附件</label>
		</div>
		<div class="app-row-content" id='attachList'>
		
		</div>
	</div>

</div>

	<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover" >
		<ul class="mui-table-view">
		      <li class="mui-table-view-cell"  onclick="receive()" id="receive" style="display: none">签收任务</li>
		      <li class="mui-table-view-cell"  onclick="publish()" id="publish" style="display: none">发布任务</li>
		      <li class="mui-table-view-cell"  onclick="create()" id="create" style="display: none">创建子任务</li>
		      <li class="mui-table-view-cell"  onclick="urge()" id="urge" style="display: none">任务催办</li>
		      <li class="mui-table-view-cell"  onclick="feedback()" id="feedback" style="display: none">发表反馈</li>
		      <li class="mui-table-view-cell"  onclick="apply(1)" id="applyPause" style="display: none">申请暂停</li>
		      <li class="mui-table-view-cell"  onclick="apply(2)" id="applyRecover" style="display: none">申请恢复</li>
		      <li class="mui-table-view-cell"  onclick="apply(3)" id="applyFinish" style="display: none">申请办结</li>
		  </ul>
	</div>
	
	
	<div id="Popover_2" class="mui-popover">
			<ul class="mui-table-view">
				<li id="b3" class="mui-table-view-cell">催办申请记录</li>
				<li id="b4" class="mui-table-view-cell">办结申请记录</li>
				<li id="b5" class="mui-table-view-cell">暂停恢复申请记录</li>
			</ul>
	</div>
	
	
<br/><br/><br/>
</body>
</html>