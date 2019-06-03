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
	getInfoByUuid(uuid);

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
//编辑
function addOrUpdateTimeLine(){
	window.location.href="/system/mobile/phone/timeline/addOrUpdatetimeline.jsp?uuid="+uuid;
}
</script>
<body onload="doInit()">
	 <header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn" onclick="javascript:history.back(-1);"></span>
		<h1 class="mui-title">大事记详情</h1>
		<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover" id="moreOpt" style=""></a>
	</header>
<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>标题</label>
		</div>
		   <input type="hidden" value="<%=uuid %>" id="uuid"  name="sid">
		   <textarea id="title" readonly="readonly" name="title"  required ></textarea>
	       
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>类型</label>
		</div>
			<input type="text" id="typeDesc" name="typeDesc" readonly="readonly"/>（注：类型在系统管理-》系统编码-》大事记分类 中进行添加）</span>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>开始时间</label>
		</div>
			<input readonly="readonly" type="text" id='startTimeDesc' class="Wdate BigInput" />
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>结束时间</label>
		</div>
			<input readonly="readonly" type="text" id='endTimeDesc' class="Wdate BigInput"/>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>内容</label>
		</div>
			<textarea readonly="readonly" id="content" name="content"  class="BigTextarea" cols='60' rows='8'  required></textarea>
	</div>
	<div class="mui-input-group">
        <div id="tagDiv" style="padding:5px;"></div>
		<div class="mui-input-row">
			<label>标签</label>
		</div>
		   <input readonly="readonly" type="text" id="tag" name="tag" maxlength="100" validType="maxLength[100]"/>
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
		<textarea type="text" id="viewUserNames" name="viewUserNames" readonly></textarea>
		<input type="hidden" id="viewUserIds" name="viewUserIds"/>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>部门</label>
		</div>
		<textarea type="text" id="viewDeptNames" name="viewDeptNames" readonly></textarea>
		<input type="hidden" id="viewDeptIds" name="viewDeptIds"/>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>角色</label>
		</div>
		<textarea type="text" id="viewRoleNames" name="viewRoleNames" readonly></textarea>
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
		   <textarea type="text" id="postUserNames" name="postUserNames" readonly></textarea>
		   <input type="hidden" id="postUserIds" name="postUserIds"/>
		</div>
		<div class="mui-input-group">
		<div class="mui-input-row">
			<label>部门</label>
		</div>
		    <textarea type="text" id="postDeptNames" name="postDeptNames" readonly></textarea>
		    <input type="hidden" id="postDeptIds" name="postDeptIds"/>
	    </div>
		<div class="mui-input-group">
		<div class="mui-input-row">
			<label>角色</label>
		</div>
		    <textarea type="text" id="postRoleNames" name="postRoleNames" readonly></textarea>
		    <input type="hidden" id="postRoleIds" name="postRoleIds"/>
	</div>

  </form>	
</div>

	<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover" >
		<ul class="mui-table-view">
		      <li class="mui-table-view-cell" onclick="addOrUpdateTimeLine();">编辑</li>
		      <li class="mui-table-view-cell" onclick="deleteTimeLine();">删除</li>
		      <li class="mui-table-view-cell" onclick="">事件管理</li>
		      <li class="mui-table-view-cell" onclick="">事件查看</li>
		  </ul>
	</div>

</body>
</html>