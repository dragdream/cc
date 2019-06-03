<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
	td{
		line-height:28px;
		min-height:28px;
	}
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>

<script>
var sid = "<%=sid%>";
function doInit(){
 	getSysCodeByParentCodeNo("TIMELINE_TYPE","type");
 	doInitUpload();
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeTimelineController/getById.action?uuid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			$("#tag").val("");
			//处理标签
 			var sp = json.rtData.tag.split("/");
			for(var i=0;i<sp.length;i++){
				if(sp[i]!=""){
					renderTag(sp[i]);
				}
			}
			
			/**
			*处理附件
			*/
			var  attachmodels = json.rtData.attacheModels;
			for(var i=0;i<attachmodels.length;i++){
				var fileItem = tools.getAttachElement(attachmodels[i]);
				$("#attachments").append(fileItem);
			}
		}
	}
}

function commit(){
	if($("#form1").valid()){
		var param = tools.formToJson($("#form1"));
		
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
		
		var url = contextPath+"/TeeTimelineController/addOrUpdate.action";
		var json = tools.requestJsonRs(url,param);
		return json;
		if(json.rtState){
			$.MsgBox.Alert_auto("保存成功！",function(){
			datagrid.datagrid('reload');
			});
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
// 		if(json.rtState){
// 			top.$.jBox.tip(json.rtMsg,"success");
// 			var url = contextPath+"/system/subsys/timeline/manage/index.jsp";
// 			location.href=url;
// 			return json.rtState;
// 		}else{
// 			top.$.jBox.tip(json.rtMsg,"error");
// 		}
	}
	return false;
}

/**
 * 初始化附件上传
 */
function doInitUpload(){
	//多附件快速上传
// 	swfUploadObj = new TeeSWFUpload({
// 		fileContainer:"fileContainer2",//文件列表容器
// 		uploadHolder:"uploadHolder2",//上传按钮放置容器
// 		valuesHolder:"attachmentSidStr",//附件主键返回值容器，是个input
// 		quickUpload:true,//快速上传
// 		showUploadBtn:false,//不显示上传按钮
// 		queueComplele:function(){//队列上传成功回调函数，可有可无
			
// 		},
// 		renderFiles:true,//渲染附件
// 		post_params:{model:"timeline"}//后台传入值，model为模块标志
// 	});
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
</script>
<style>
.tag{
	dispaly:inline-block;
	padding:5px;
	background:#fcf8e3;
	border:1px solid #faebcc;
	border-radius:5px;
	-webkit-border-radius:5px;
	-moz-border-radius:5px;
	color:#8a6d3b;
	cursor:pointer;
	margin:5px;
}
.TableBlock tr>td>textarea{
	margin:0;
}

</style>
</head>
<body onload="doInit();" style="padding-top: 10px;background-color: #f2f2f2">
<form id="form1" name="form1">
	<table  class='TableBlock' width="100%">
		<tr>
			<td class="TableData" style="text-indent: 10px;" colspan="2">
				<fieldset>
					<legend><b>基本信息：</b></legend>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px;padding: 5px 0;"  width="120px"> 
				标&nbsp;&nbsp;&nbsp;&nbsp;题&nbsp;<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td class="TableData">
				<input style="width:350px;height:25px;font-family: MicroSoft YaHei;" type="text" id="title" name="title"  required />
			</td>
			
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px;">
				类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型 ：
				</td>
			<td class="TableData">
				<select style="font-family: MicroSoft YaHei;"  id="type" name="type"></select>&nbsp;&nbsp;<span style="color:orange">（注：类型在系统管理-》系统编码-》大事记分类 中进行添加）</span>
			</td>
		</tr>
		<tr> 
			<td class="TableData" style="text-indent: 10px;">
				开始时间：
				</td>
			<td class="TableData">
				<input type="text" id='startTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTimeDesc\')}'})" name='startTimeDesc' class="Wdate BigInput"  required/>	
			</td>
		</tr >
		<tr>
			<td class="TableData" style="text-indent: 10px;">
				结束时间：
				</td>
			<td class="TableData">
				<input type="text" id='endTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTimeDesc\')}'})" name='endTimeDesc' class="Wdate BigInput"  required/>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px;">
				内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容：
				</td>
			<td class="TableData" >
				<textarea style="width:350px;resize:none;font-family: MicroSoft YaHei;"  id="content" name="content"  class="BigTextarea" cols='60' rows='8'  required></textarea>
				</textarea>
			</td>
		</tr >
		<tr>
			<td class="TableData" style="text-indent: 10px;">
				标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;签：
			</td>
			<td class="TableData" style="padding-bottom: 5px;">
				<div id="tagDiv" style="padding:5px;"></div>
				<input style="width:350px;height:25px;font-family: MicroSoft YaHei;" type="text" id="tag" name="tag" maxlength="100" validType="maxLength[100]"/>
				&nbsp;
				<a href="javascript:void(0)" onclick="addTag()">添加</a>
			</td>
		</tr >
		<tr style="display:none">
			<td  style="text-indent: 10px;" class='TableData' align='left'>附件上传：</td>
			<td align='left' class="TableData">
		       	<span id="attachments"></span>
					<input type="hidden" id="dbAttachSid" name="dbAttachSid" value="">
	
					<div id="fileContainer2"></div>
					<a id="uploadHolder2" class="add_swfupload">
						<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
					</a>
					<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
		    </td>
		</tr>
	</table>
	<table class='TableBlock' width="100%">
		<tr>
			<td class="TableData" style="text-indent: 10px;" colspan="2" >
				<fieldset>
					<legend><b>查看权限</b>（可查看大事记和相关事件）：</legend>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px;"  width="120px">
				人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;员：
			</td>
			<td class="TableData" style="padding-top: 5px;">
				<textarea style="width:350px;resize:none;font-family: MicroSoft YaHei;" readonly id='viewUserNames' name='viewUserNames' class="BigTextarea readonly" cols='60' rows='5'></textarea>
				<input id='viewUserIds' name='viewUserIds' class="BigInput" type='hidden'/>
				   <span class='addSpan'>
				   <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/dsj/add.png" onClick="selectUser(['viewUserIds','viewUserNames'])" value="选择"/>
			       &nbsp;&nbsp;
			       <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/dsj/clear.png" onClick="clearData('viewUserIds','viewUserNames')" value="清空"/>
			    </span>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px;">
				部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门：
			</td>
			<td class="TableData" style="padding-top: 5px;">
				<textarea style="width:350px;resize:none;font-family: MicroSoft YaHei;" readonly id='viewDeptNames' name='viewDeptNames' class="BigTextarea readonly" cols='60' rows='5'></textarea>
				<input id='viewDeptIds' name='viewDeptIds' class="BigInput" type='hidden'/>
			     <span class='addSpan'>
				   <img style="cursor: pointer;" src="/common/zt_webframe/imgs/xzbg/dsj/add.png" onClick="selectDept(['viewDeptIds','viewDeptNames'])" value="选择"/>
			       &nbsp;&nbsp;
			       <img style="cursor: pointer;" src="/common/zt_webframe/imgs/xzbg/dsj/clear.png" onClick="clearData('viewDeptIds','viewDeptNames')" value="清空"/>
			    </span>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px;">
				角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色：
			</td>
			<td class="TableData" style="padding-top: 5px;">
				<textarea style="width:350px;resize:none;font-family: MicroSoft YaHei;" readonly id='viewRoleNames' name='viewRoleNames' class="BigTextarea readonly" cols='60' rows='5'></textarea>
				<input id='viewRoleIds' name='viewRoleIds' class="BigInput" type='hidden'/>
			    <span class='addSpan'>
				   <img style="cursor: pointer;" src="/common/zt_webframe/imgs/xzbg/dsj/add.png" onClick="selectRole(['viewRoleIds','viewRoleNames'])" value="选择"/>
			       &nbsp;&nbsp;
			       <img style="cursor: pointer;" src="/common/zt_webframe/imgs/xzbg/dsj/clear.png" onClick="clearData('viewRoleIds','viewRoleNames')" value="清空"/>
			    </span>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px;" colspan="2">
				<fieldset>
					<legend><b>管理权限</b>（可编辑大事记和相关事件）：</legend>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px;">
				人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;员：
				</td>
			<td class="TableData" style="padding-top: 5px;">
				<textarea style="width:350px;resize:none;font-family: MicroSoft YaHei;" readonly id='postUserNames' name='postUserNames'  cols='60' rows='5'></textarea>
				<input id='postUserIds' name='postUserIds' class="BigInput" type='hidden'/>
			    <span class='addSpan'>
				   <img style="cursor: pointer;" src="/common/zt_webframe/imgs/xzbg/dsj/add.png" onClick="selectUser(['postUserIds','postUserNames'])" value="选择"/>
			       &nbsp;&nbsp;
			       <img style="cursor: pointer;" src="/common/zt_webframe/imgs/xzbg/dsj/clear.png" onClick="clearData('postUserIds','postUserNames')" value="清空"/>
			    </span>
			</td>
		</tr >
		<tr>
			<td class="TableData" style="text-indent: 10px;">
				部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门：
			</td>
			<td class="TableData" style="padding-top: 5px;">
				<textarea style="width:350px;resize:none;font-family: MicroSoft YaHei;" readonly id='postDeptNames' name='postDeptNames' class="BigTextarea readonly" cols='60' rows='5'></textarea>
				<input id='postDeptIds' name='postDeptIds' class="BigInput" type='hidden'/>
			     <span class='addSpan'>
				   <img style="cursor: pointer;" src="/common/zt_webframe/imgs/xzbg/dsj/add.png" onClick="selectDept(['postDeptIds','postDeptNames'])" value="选择"/>
			       &nbsp;&nbsp;
			       <img style="cursor: pointer;" src="/common/zt_webframe/imgs/xzbg/dsj/clear.png" onClick="clearData('postDeptIds','postDeptNames')" value="清空"/>
			    </span>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px;">
				角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色：
			</td>
			<td class="TableData" style="padding-top: 5px;">
				<textarea style="width:350px;resize:none;font-family: MicroSoft YaHei;" readonly id='postRoleNames' name='postRoleNames' class="readonly" cols='60' rows='5'></textarea>
				<input id='postRoleIds' name='postRoleIds' class="BigInput" type='hidden'/>
			     <span class='addSpan'>
				   <img style="cursor: pointer;" src="/common/zt_webframe/imgs/xzbg/dsj/add.png" onClick="selectRole(['postRoleIds','postRoleNames'])" value="选择"/>
			       &nbsp;&nbsp;
			       <img  style="cursor: pointer;" src="/common/zt_webframe/imgs/xzbg/dsj/clear.png" onClick="clearData('postRoleIds','postRoleNames')" value="清空"/>
			    </span>
			</td>
		</tr>
	</table>
	<br/>
	<div id="control" style='margin:0 auto;text-align:center; height:28px;line-height:28px;width:"90%"'>
		<input id="uuid" name="uuid" type='hidden'value="<%=sid %>"/>
	</div>
</form>
</body>
</html>