<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   //项目主键
   String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
%>

<!DOCTYPE HTML>
<html>
<head>
<title>大事记详情</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>

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
.tag{
    padding: 5px;
    background: #fcf8e3;
    border: 1px solid #faebcc;
    -webkit-border-radius: 5px;
    color: #8a6d3b;
    cursor: pointer;
    margin: 5px;
}
</style>
</head>

<script type="text/javascript">
//项目主键
var uuid="<%=uuid%>";
//初始化方法
function doInit(){
	getSysCodeByParentCodeNo("TIMELINE_TYPE","type");
	getInfoByUuid(uuid);
	//开始时间
	startTimeDesc.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
		startTimeDesc.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
	//结束时间
	endTimeDesc.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
		endTimeDesc.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
	//查看权限（人员）
	viewUserNames.addEventListener('tap', function() {
		selectUser("viewUserIds","viewUserNames");
	}, false);
	//部门
	viewDeptNames.addEventListener('tap', function() {
		selectDept("viewDeptIds","viewDeptNames");
	}, false);
	//角色
	viewRoleNames.addEventListener('tap', function() {
		selectRole("viewRoleIds","viewRoleNames");
	}, false);
	//管理权限（人员）postRoleNames
	postUserNames.addEventListener('tap', function() {
		selectUser("postUserIds","postUserNames");
	}, false);
	//部门
	postDeptNames.addEventListener('tap', function() {
		selectDept("postDeptIds","postDeptNames");
	}, false);
	//角色
	postRoleNames.addEventListener('tap', function() {
		selectRole("postRoleIds","postRoleNames");
	}, false);
	
	
}




//根据主键获取详情
function getInfoByUuid(sid){
	var url=contextPath+"/TeeTimelineController/getById.action";
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
				//var sendTime =  getFormatDateStr(data.sendTime , 'yyyy-MM-dd HH:mm');
				bindJsonObj2Cntrl(data);
				$("#tag").val("");
				//处理标签
	 			var sp = data.tag.split("/");
				for(var i=0;i<sp.length;i++){
					if(sp[i]!=""){
						renderTag(sp[i]);
					}
				}
				/* 
				var  attachments = data.attachmentsMode;
				if(attachments.length > 0){
					 $.each(attachments, function(index, item){  
						 $("#attachList").append("<div ><a href='javascript:void(0);' onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "&nbsp;&nbsp;"+"</a><img style='vertical-align:middle' src='/common/images/upload/remove.png' onclick='delAttach("+item.sid+")' /><div>");
					 });
				} */
				//立项中1       审批中2      办理中3        挂起中4       已办结5     已拒绝6
			}
		}
	});
}

//同意
function approve(uuid){
	if(window.confirm("是否确认批准该公告？")){
		var param=formToJson("#form1");
		param["publish"] = 1;
		var url=contextPath+"/teeNotifyController/audNotify.action";
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("已批准！");
					window.location.replace("index.jsp"); 
				}else{
					alert("操作失败！");
				}
			}
		});
	}
}





//拒绝
function noApprove(uuid){ 
		if(window.confirm("是否确认不批准该公告？")){
			var param=formToJson("#form1");
			param["publish"] = 3;
			var url=contextPath+"/teeNotifyController/audNotify.action";
			mui.ajax(url,{
				type:"post",
				dataType:"html",
				data:param,
				timeout:10000,
				success:function(json){
					json = eval("("+json+")");
					if(json.rtState){
						alert("操作成功！");
						window.location.replace("index.jsp"); 
					}else{
						alert("操作失败！");
					}
				}
			});
		}
	
}
function addTag(){
	if($("#tag").val()==""){
		alert("标签不能为空");
		return;
	}
	renderTag($("#tag").val());
	$("#tag").val("");
}

function renderTag(tagName){
	$("<span class='tag' title='点击移除该标签' onclick='$(this).remove();'>"+tagName+"<span style='margin-left:5px;color:red;text-valign:top;font-size:10px;'>X</span></span>").appendTo($("#tagDiv"));
}
//上传公共附件
function doUploadPublicAttach(){
	TakePhoto(function(files){
		$("<p class='img' path=\""+files[0].path+"\">"+files[0].name+"&nbsp;&nbsp;<img style='vertical-align:middle' src='/common/images/upload/remove.png' onclick='removePublicAttachImg(this)' /></p>").appendTo($("#upfileList"));
	});
}

//移除公共附件项
function removePublicAttachImg(obj){
	$(obj).parent().remove();
}
//编辑
function addOrUpdateTimeLine(){
	var param=formToJson("#form1");
	var tagContent = "";
	$("#tagDiv span").each(function(i,obj){
		var index = $(obj).html().indexOf("<");
		var value="";
		if(index!=-1){
			value=$(obj).html().substring(0,index);
		}
		tagContent += "/"+value;
	});
	if(tagContent!=""){
		tagContent+="/";
	}
	param["tag"] = tagContent;
	var url=contextPath+"/TeeTimelineController/addOrUpdate.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:param,
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				alert("操作成功！");
				window.location.replace("index.jsp"); 
			}else{
				alert("操作失败！");
			}
		}
	});
}
</script>
<body onload="doInit()">
	 <header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn" onclick="javascript:history.back(-1);"></span>
		<h1 class="mui-title">编辑大事记</h1>
		<!-- <a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover" id="moreOpt" style=""></a> -->
		<div style="float: right;color: #0070ffc7;margin-top: 12px;" onclick="addOrUpdateTimeLine()">保存</div>
	</header>
<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>标题</label>
		</div>
		   <input type="hidden" value="<%=uuid %>" id="uuid"  name="uuid">
		   <textarea id="title" name="title"  placeholder="请填写标题"></textarea>
	       
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>类型</label>
		</div>
			<select id="type" name="type"></select>&nbsp;&nbsp;<span style="color:orange">（注：类型在系统管理-》系统编码-》大事记分类 中进行添加）</span>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>开始时间</label>
		</div>
			<input type="text" id='startTimeDesc' name="'startTimeDesc'" class="Wdate BigInput" readonly placeholder="请选择开始时间"/>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>结束时间</label>
		</div>
			<input type="text" id='endTimeDesc' name="endTimeDesc" class="Wdate BigInput" readonly placeholder="请选择结束时间"/>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>内容</label>
		</div>
			<textarea id="content" name="content"  class="BigTextarea" cols='60' rows='8' placeholder="请填写内容"></textarea>
	</div>
	<div class="mui-input-group">
        <div id="tagDiv" style="padding:5px;"></div>
		<div class="mui-input-row">
			<label>标签</label><a style="float: right;margin-top: 7px;margin-right: 9px;" href="javascript:void(0)" onclick="addTag()">添加</a>	
		</div>
		   <input type="text" id="tag" name="tag" maxlength="100" validType="maxLength[100]"  placeholder="请添加标签"/>
			&nbsp;
			
	</div>
	<div class="mui-input-group" style="display: none">
		<div class="mui-input-row">
			<label>相关附件</label>
			<label><a href="javascript:void(0)" onclick="doUploadPublicAttach()" >上传附件</a></label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		   <div id="attachments" style="padding-left: 15px"></div>
		   <div id="upfileList" style="padding-left: 15px"></div>
		    
		</div>
	</div>
	
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label style="width:80%;">查看权限（可查看大事记和相关事件）：</label>
		</div>
	</div>
    <div class="mui-input-group">
		<div class="mui-input-row">
			<label>人员</label>
		</div>
		<textarea type="text" id="viewUserNames" name="viewUserNames" readonly placeholder="请选择人员"></textarea>
		<input type="hidden" id="viewUserIds" name="viewUserIds"/>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>部门</label>
		</div>
		<textarea type="text" id="viewDeptNames" name="viewDeptNames" readonly placeholder="请选择部门"></textarea>
		<input type="hidden" id="viewDeptIds" name="viewDeptIds"/>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>角色</label>
		</div>
		<textarea type="text" id="viewRoleNames" name="viewRoleNames" readonly placeholder="请选择角色"></textarea>
		<input type="hidden" id="viewRoleIds" name="viewRoleIds"/>
	</div>
	<div class="mui-input-group">
		    <div class="mui-input-row">
				<label style="width:80%;">管理权限（可编辑大事记和相关事件）：</label>
			</div>
	   </div>
		<div class="mui-input-group">
		<div class="mui-input-row">
			<label>人员</label>
		</div>
		   <textarea type="text" id="postUserNames" name="postUserNames" readonly placeholder="请选择人员"></textarea>
		   <input type="hidden" id="postUserIds" name="postUserIds"/>
		</div>
		<div class="mui-input-group">
		<div class="mui-input-row">
			<label>部门</label>
		</div>
		    <textarea type="text" id="postDeptNames" name="postDeptNames" readonly placeholder="请选择部门"></textarea>
		    <input type="hidden" id="postDeptIds" name="postDeptIds"/>
	    </div>
		<div class="mui-input-group">
		<div class="mui-input-row">
			<label>角色</label>
		</div>
		    <textarea type="text" id="postRoleNames" name="postRoleNames" readonly placeholder="请选择角色"></textarea>
		    <input type="hidden" id="postRoleIds" name="postRoleIds"/>
	</div>

  </form>	
</div>

	<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover" >
		<ul class="mui-table-view">
		      <li class="mui-table-view-cell" onclick="approve('<%=uuid %>')" >批准</li>
		      <li class="mui-table-view-cell" onclick="noApprove('<%=uuid%>')">不批准</li>
		  </ul>
	</div>

</body>
</html>