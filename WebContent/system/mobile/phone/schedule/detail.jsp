<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	String scheduleId = request.getParameter("scheduleId");
	TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>计划</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="history.go(-1)">
	    <span class="mui-icon mui-icon-left-nav"></span>返回
	</button>
	<a class="mui-pull-right app-title" href="#myPopover">操作
				<span class="mui-icon mui-icon-arrowdown"></span>
			</a>
	<h1 class="mui-title">计划详情</h1>
</header>

<div id="myPopover" class="mui-popover" style="width:80px">
		  <ul class="mui-table-view" id='controlDiv'>
		  </ul>
</div>

<style>
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
<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>计划名称</label>
		</div>
		<div class="app-row-content" id="title">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>计划类型</label>
		</div>
		<div class="app-row-content" id="type">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>计划周期</label>
		</div>
		<div class="app-row-content" id="rangeType">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>添加时间</label>
		</div>
		<div class="app-row-content" id="crTimeDesc" >
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>负责人</label>
		</div>
		<div class="app-row-content" id="managerUserName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>计划内容</label>
		</div>
		<div class="app-row-content" id='content'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>批阅人</label>
		</div>
		<div class="app-row-content" id='reportedRangesNames'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>共享人</label>
		</div>
		<div class="app-row-content" id='sharedRangesNames'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>相关附件</label>
		</div>
		<div class="app-row-content">
			<span id="attachments">
				
			</span>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>相关批示</label>
		</div>
		<div class="app-row-content">
			<div id="annotationsDiv">
			
			</div>
			<div id="piyueDiv" style='display:none;'>
				<textarea id="t1" style='border:1px solid #fff000;' placeholder="请填写批示内容"></textarea>
				<br/><br/>
				<button class="mui-btn mui-btn-primary" onclick="commitAnnotations()">提交批示</button>
			</div>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>相关评论</label>
		</div>
		<div class="app-row-content">
			<div id="commentDiv">
			
			</div>
			<div id="pinglunDiv" style='display:none;'>
				<textarea id="t2" style='border:1px solid #fff000;' placeholder="请填写评论内容"></textarea>
				<br/><br/>
				<button class="mui-btn mui-btn-primary" onclick="commitComment()">提交评论</button>
			</div>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>总结</label>
		</div>
		<div class="app-row-content">
			<div id="summarizeContent">
			
			</div>
			<div id="summarizeDiv" style='display:none;'>
				<textarea id="summerize" style='border:1px solid #fff000;' placeholder="请填写计划总结"></textarea>
				<br/><br/>
				<button class="mui-btn mui-btn-primary" onclick="ok()">提交总结</button>
			</div>
		</div>
	</div>
</div>


<script>
	var scheduleId = "<%=scheduleId%>";
	var userId = <%=loginUser.getUuid()%>;
	(function($) {
		//启动加载完毕的逻辑	
		getPriv();
		getScheduleInfo();
		getAnnotations();
		getComment();
		
	})(mui);
	
	
	function getScheduleInfo(){
		var url = contextPath+"/schedule/get.action";
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{uuid:scheduleId},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				if(json.rtState){
					var typeDesc="";
					var rangeTypeDesc="";
					if(json.rtData.rangeType==1){
						rangeTypeDesc = "日计划";
					}else if(json.rtData.rangeType==2){
						rangeTypeDesc="周计划";
					}else if(json.rtData.rangeType==3){
						rangeTypeDesc="月计划";
					}else if(json.rtData.rangeType==4){
						rangeTypeDesc="季度计划";
					}else if(json.rtData.rangeType==5){
						rangeTypeDesc="半年计划";
					}else if(json.rtData.rangeType==6){
						rangeTypeDesc="年计划";
					}
					if(json.rtData.type==1){
						typeDesc = "个人计划";
					}else if(json.rtData.type==2){
						typeDesc="部门计划";
					}else if(json.rtData.type==3){
						typeDesc="公司计划";
					}
					document.getElementById("title").innerHTML = json.rtData.title;
					document.getElementById("type").innerHTML = typeDesc;
					document.getElementById("rangeType").innerHTML = rangeTypeDesc;
					document.getElementById("crTimeDesc").innerHTML = json.rtData.crTimeDesc;
					document.getElementById("managerUserName").innerHTML = json.rtData.managerUserName;
					document.getElementById("content").innerHTML = json.rtData.content;
					document.getElementById("reportedRangesNames").innerHTML = json.rtData.reportedRangesNames;
					document.getElementById("sharedRangesNames").innerHTML = json.rtData.sharedRangesNames;
					var attachs = json.rtData.attachMentModel;
					for(var i = 0 ;i<attachs.length;i++){
						var attach = attachs[i];
						var att = "<div style='height:32px;line-height:32px;'><a href='javascript:void(0);' onclick=\"GetFile('"+attach.sid+"','"+attach.fileName+"','"+attach.attachmentName+"')\">"+attach.fileName + "</a></div>";
						document.getElementById("attachments").innerHTML+=att;
					}
					
					if(json.rtData.flag!=0){
						$("#controlDiv").html("<li class=\"mui-table-view-cell\"  onclick='window.location.reload();'>刷新</li>");
						$("#piyueDiv").css("display","none");
						$("#pinglunDiv").css("display","none");
					}
					
					if((userId==json.rtData.userId || userId==json.rtData.managerUserId )&& json.rtData.flag==0){
						$("#controlDiv").html($("#controlDiv").html()+"<li class=\"mui-table-view-cell\"  id='finishBtn' onclick='finish()'>完成</li>");
					}
					
					if(userId==json.rtData.userId && json.rtData.flag==0){
						$("#controlDiv").html($("#controlDiv").html()+"<li class=\"mui-table-view-cell\"  id='editBtn' onclick=\"window.location='addOrUpdate.jsp?scheduleId=<%=scheduleId %>'\">编辑</li>");
					}
					
					json.rtData.content = json.rtData.content.split("\n").join("<br/>");
					if(json.rtData.summarize==null){
						json.rtData.summarize="没有相关总结<br/>";
					}
					if(json.rtData.summarize){
						json.rtData.summarize = json.rtData.summarize.split("\n").join("<br/>");
					}
					document.getElementById("summarizeContent").innerHTML = json.rtData.summarize;
				}
			},
			error:function(){
				
			}
		});
	}
	
	
	function getPriv(){
		var url = contextPath+"/schedule/getPriv.action";
		mui.ajax(url,{
			type:"GET",
			dataType:"JSON",
			data:{scheduleId:scheduleId},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				if(json.rtData.shenpi){
					$("#piyueDiv").show();
				}
				if(json.rtData.pinglun){
					$("#pinglunDiv").show();
				}
			},
			error:function(){
				
			}
		});
	}
	
	
	function commitComment(){
		if($("#t2").val()==""){
			Alert("评论不能为空！");
			$("#t2").focus();
			return;
		}
		
		var url = contextPath+"/scheduleComment/save.action";
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{scheduleId:scheduleId,content:$("#t2").val()},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				if(json.rtState){
					$("#t2").val("");
					getComment();
				}
			},
			error:function(){
				
			}
		});
	}
	
	
	function getComment(){
		var url = contextPath+"/scheduleComment/datagrid.action";
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{uuid:scheduleId},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				var rows = json.rows;
				var render = [];
				for(var i=0;i<rows.length;i++){
					render.push("<div style='margin-bottom:10px;'>");
					render.push("#"+(i+1)+"&nbsp;&nbsp;评论人:"+rows[i].userName+"&nbsp;&nbsp;评论时间："+rows[i].crTimeDesc);
					if(userId==rows[i].userId){
						render.push("&nbsp;&nbsp;<a style='color:red' href='javascript:void(0)' onclick=\"del2('"+rows[i].uuid+"')\">删除</a>");
					}
					render.push("<br/>");
					render.push(rows[i].content);
					render.push("</div>");
				}
				if(render.length==0){
					$("#commentDiv").html("无相关评论");
				}else{
					$("#commentDiv").html(render.join(""));
				}
			},
			error:function(){
				
			}
		});
	}
	
	function commitAnnotations(){
		if($("#t1").val()==""){
			Alert("批示不能为空！");
			$("#t1").focus();
			return;
		}
		
		var url =contextPath+"/scheduleAnnotations/save.action";
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{scheduleId:scheduleId,content:$("#t1").val()},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				if(json.rtState){
					$("#t1").val("");
					getAnnotations();
				}
			},
			error:function(){
				
			}
		});
	}
	
	function getAnnotations(){
		var url = contextPath+"/scheduleAnnotations/datagrid.action";
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{uuid:scheduleId},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				var rows = json.rows;
				var render = "";
				for(var i=0;i<rows.length;i++){
					render+="<div style='margin-bottom:10px;'>";
					render+="#"+(i+1)+"&nbsp;&nbsp;批示人:"+rows[i].userName+"&nbsp;&nbsp;批示时间："+rows[i].crTimeDesc;
					if(userId==rows[i].userId){
						render+="&nbsp;&nbsp;<a style='color:red' href='javascript:void(0)' onclick=\"del1('"+rows[i].uuid+"')\">删除</a>";
					}
					render+="<br/>";
					render+=rows[i].content;
					render+="</div>";
				}
				if(render.length==0){
					$("#annotationsDiv").html("无相关批示");
				}else{
					$("#annotationsDiv").html(render);
				}
			},
			error:function(){
				
			}
		});
	}
	

	function del1(uuid){
		var url =contextPath+"/scheduleAnnotations/delete.action";
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{uuid:uuid},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				if(json.rtState){
					getAnnotations();
				}
			},
			error:function(){
				
			}
		});
	}

	function del2(uuid){
		var url = contextPath+"/scheduleComment/delete.action";
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{uuid:uuid},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				if(json.rtState){
					getComment();
				}
			},
			error:function(){
				
			}
		});
	}

	function finish(){
		$("#summarizeDiv").show();
		$("#summerize").focus();
	}
	
	
	function ok(){
		var url = contextPath+"/schedule/finish.action";
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{scheduleId:scheduleId,summerize:$("#summerize").val()},
			timeout:10000,
			success:function(text){
				window.location.reload();
			},
			error:function(){
				
			}
		});
	}
</script>
</body>
</html>