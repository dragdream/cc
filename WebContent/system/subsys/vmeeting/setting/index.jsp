<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<title>视频会议组件设置</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/colorPicker/css/colorPicker.css"/>
<script type="text/javascript">
function doInit(){
	var url = contextPath+"/vmeeting/getVmeetingConfigs.action";
	var json = tools.requestJsonRs(url);
	bindJsonObj2Cntrl(json.rtData);
}

/**
 * 保存
 */
function doSave(){
	//if($("#form1").form("validate")){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/vmeeting/saveVmeetingConfigs.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			
			$.MsgBox.Alert_auto("保存成功！",function(){
				
				location.reload();
			});
			
		}else{
			$.MsgBox.Alert_auto("保存错误！");
		}
	//}
}

function doSync(){
	$.MsgBox.Alert_auto("正在同步用户数据到视频会议组件，请稍候……");
	var url = contextPath+"/vmeeting/syncUserDatas.action";
	tools.requestJsonRs(url,{},true,function(json){
		if(json.rtState){
			$.MsgBox.Alert_auto("同步成功");
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	});
}
</script>
</head>
<body onload="doInit()"  style="padding-left: 10px;padding-right: 10px;">
<div id="toolbar" class="topbar clearfix">
	   <img class="title_img" src="/system/subsys/vmeeting/setting/img/icon_sphysz.png" alt="">
	   &nbsp;<span class="title">视频会议设置</span>
	   
	   <button class="btn-win-white fr" onclick="doSave()">保存</button>	   
</div>
<form  name="form1" id="form1">
  <table class="TableBlock_page" width="100%" align="center">
   <!-- <tr>
    <td colspan='2' class="TableHeader">视频会议组件接口</td>
   </tr> -->
   <tr>
    <td nowrap class="TableData" style="text-indent:10px">入口地址(例192.168.31.223)：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="VMT_IP" class="BigInput" name="VMT_IP" style="width:300px"/>
    </td>
   </tr>

   <tr>
    <td nowrap class="TableData" style="text-indent:10px">端口号：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="VMT_PORT" class="BigInput" name="VMT_PORT" style="width:300px"/>
    </td>
   </tr>
      <tr>
    <td nowrap class="TableData" style="text-indent:10px">管理员用户：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="VMT_ADMIN_ID" class="BigInput" name="VMT_ADMIN_ID" style="width:300px"/>
    </td>
   </tr> 
      <tr>
    <td nowrap class="TableData" style="text-indent:10px">管理员密码：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="VMT_ADMIN_PWD" class="BigInput" name="VMT_ADMIN_PWD" style="width:300px"/>
    </td>
   </tr> 
    <tr>
    <td nowrap class="TableData" style="text-indent:10px">普通用户：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="VMT_USER_ID" class="BigInput" name="VMT_USER_ID" style="width:300px"/>
    </td>
   </tr> 
    <tr>
    <td nowrap class="TableData" style="text-indent:10px">普通用户密码：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="VMT_USER_PWD" class="BigInput" name="VMT_USER_PWD" style="width:300px"/>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent:10px">会议创建权限：</td>
    <td  class="TableData" >
   	 <input type='hidden' id="VMT_CREATE_PRIV_IDS" class="BigInput" name="VMT_CREATE_PRIV_IDS" />
    	<textarea readonly id="VMT_CREATE_PRIV_NAMES" class="BigTextarea readonly" name="VMT_CREATE_PRIV_NAMES" style="height:100px;width:400px"></textarea>
    	<span class='addSpan'>
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['VMT_CREATE_PRIV_IDS','VMT_CREATE_PRIV_NAMES']);" value="选择"/>
		   &nbsp;&nbsp;
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('VMT_CREATE_PRIV_IDS','VMT_CREATE_PRIV_NAMES');" value="清空"/>
		</span>
    	
    </td>
   </tr>
    <tr>
    <td nowrap class="TableData" style="text-indent:10px">会议管理权限：</td>
    <td  class="TableData" >
   	 <input type='hidden' id="VMT_MGR_PRIV_IDS" class="BigInput" name="VMT_MGR_PRIV_IDS" />
    	<textarea readonly id="VMT_MGR_PRIV_NAMES" class="BigTextarea readonly" name="VMT_MGR_PRIV_NAMES" style="height:100px;width:400px"></textarea>
    	<span class='addSpan'>
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['VMT_MGR_PRIV_IDS','VMT_MGR_PRIV_NAMES']);" value="选择"/>
		   &nbsp;&nbsp;
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('VMT_MGR_PRIV_IDS','VMT_MGR_PRIV_NAMES');" value="清空"/>
		</span>
    	
    </td>
   </tr>
</table>
</form>
</body>
</html>