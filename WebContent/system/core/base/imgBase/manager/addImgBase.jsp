<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String sid = request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script>
var sid = <%=sid%>;
function doInit(){
	//获取当前站点些模板列表
	if(sid>0){
		var url = "<%=contextPath%>/teeImgBaseController/getImgBase.action";
		var jsonRs = tools.requestJsonRs(url,{"sid":sid});
		if(jsonRs.rtState){
			var data = jsonRs.rtData;
			bindJsonObj2Cntrl(data);
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}

function commit(){
	var url;
	//获取页面上的图片库名称和图片库的路径
	var name=$("#imgDirName").val();
	var dir=$("#imgDir").val();
	var json={};
	if(name==null||name==""){
		json["rtMsg"]="图片库名称不能为空！";
		json["rtState"]=false;
		return json;
	}
	if(dir==null||dir==""){
		json["rtMsg"]="图片库路径不能为空！";
		json["rtState"]=false;
		return json;
	}
	
	if(sid!=null){//更新
		url = contextPath+"/teeImgBaseController/editImgBase.action";
	}else{//创建
		url = contextPath+"/teeImgBaseController/addImgBase.action";
	}
	var param = tools.formToJson($("#form"));
	json = tools.requestJsonRs(url,param);
	return json;
}

function explore(){
	dialog(contextPath+"/system/core/base/imgBase/manager/selectFolder.jsp",530,300);
}

</script>
<style>
table td{
padding:5px;
}
</style>
</head>
<body onload="doInit();" style="margin:0px;">
<form id="form">
	<table style="width:100%;font-size:12px" class="TableBlock">
		
		<tr>
			<td nowrap class="TableData" >图片库名称：</td>
			<td nowrap class="TableData" >
				<input type="text" class="BigInput" name="imgDirName" id="imgDirName" style="height: 23px"/>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" >图片库路径：</td>
			<td nowrap class="TableData" >
			<input type="text" class="BigInput" name="imgDir" id="imgDir" style="height:23px"/>
			&nbsp;例如：c:/员工风采&nbsp;&nbsp;<a href="javascript:void(0)" onclick="explore()">浏览</a>
			</td>
		</tr>
		  <tr >
   			 <td nowrap class="TableData"  >查看范围（部门）：</td>
   			 <td nowrap class="TableData" align="left">
      		 <input type="hidden" name="postDeptIds" id="postDeptIds" value=""/>
       		 <textarea cols=50 name="postDeptNames" id="postDeptNames" rows=4 class="SmallStatic BigTextarea" readonly  ></textarea>
       		 <span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectDept(['postDeptIds','postDeptNames'],'','1')" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('postDeptIds','postDeptNames')" value="清空"/>
			</span>
       		 
    </td>
   </tr>
   
 
   <tr>
    <td nowrap class="TableData" >查看范围（角色）：</td>
    <td nowrap class="TableData" align="left">
        <input type="hidden" name="postUserRoleIds" id="postUserRoleIds" value="">
        <textarea cols=50 name="postUserRoleNames" id="postUserRoleNames" rows=4 class="SmallStatic BigTextarea" readonly ></textarea>
        <span class='addSpan'>
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectRole(['postUserRoleIds','postUserRoleNames'],'','1')" value="选择"/>
		   &nbsp;&nbsp;
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('postUserRoleIds','postUserRoleNames')" value="清空"/>
	    </span>

    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">查看范围（人员）：</td>
    <td nowrap class="TableData" align="left">
       <input type="hidden" name="postUserIds" id="postUserIds" value="">
        <textarea cols=50 name="postUserNames" id="postUserNames" rows=4 class="SmallStatic BigTextarea" readonly ></textarea>
        <span class='addSpan'>
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['postUserIds', 'postUserNames'],'' , '1')" value="选择"/>
		   &nbsp;&nbsp;
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('postUserIds','postUserNames')" value="清空"/>
	    </span>
        
    </td>
   </tr>
	</table>
	<table style="width:100%;font-size:12px;display:none;" class="TableBlock">
		<tr>
			<td colspan="4" style="background:#f0f0f0" align="center"><b>上传权限</b></td>
		</tr>
		  <tr >
   			 <td nowrap class="TableData"  >授权范围（部门）：</td>
   			 <td nowrap class="TableData" align="left">
      		 <input type="hidden" name="uploadDeptIds" id="uploadDeptIds" value=""/>
       		 <textarea cols=60 name="uploadDeptNames" id="uploadDeptNames" rows=3 class="SmallStatic BigTextarea" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
       		 <a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['uploadDeptIds','uploadDeptNames'],'','1')">选择</a>
       		 <a href="javascript:void(0);" class="orgClear" onClick="clearData('uploadDeptIds','uploadDeptNames')">清空</a>
    </td>
   </tr>
   
 
   <tr>
    <td nowrap class="TableData" >授权范围（角色）：</td>
    <td nowrap class="TableData" align="left">
        <input type="hidden" name="uploadRoleIds" id="uploadRoleIds" value="">
        <textarea cols=60 name="uploadRoleNames" id="uploadRoleNames" rows=3 class="SmallStatic BigTextarea" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectRole(['uploadRoleIds','uploadRoleNames'],'','1')">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="clearData('uploadRoleIds','uploadRoleNames')">清空</a>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">授权范围（人员）：</td>
    <td nowrap class="TableData" align="left">
       <input type="hidden" name="uploadUserIds" id="uploadUserIds" value="">
        <textarea cols=60 name="uploadUserNames" id="uploadUserNames" rows=3 class="SmallStatic BigTextarea" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['uploadUserIds', 'uploadUserNames'],'' , '1')">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="clearData('uploadUserIds','uploadUserNames')">清空</a>
    </td>
   </tr>
		<tr>
			<td colspan="4" style="background:#f0f0f0" align="center"><b>管理权限</b></td>
		</tr>
		  <tr >
   			 <td nowrap class="TableData"  >授权范围范围（部门）：</td>
   			 <td nowrap class="TableData" align="left">
      		 <input type="hidden" name="managerDeptIds" id="managerDeptIds" value=""/>
       		 <textarea cols=60 name="managerDeptNames" id="managerDeptNames" rows=3 class="SmallStatic BigTextarea" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
       		 <a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['managerDeptIds','managerDeptNames'],'','1')">选择</a>
       		 <a href="javascript:void(0);" class="orgClear" onClick="clearData('managerDeptIds','managerDeptNames')">清空</a>
    </td>
   </tr>
   
 
   <tr>
    <td nowrap class="TableData" >授权范围范围（角色）：</td>
    <td nowrap class="TableData" align="left">
        <input type="hidden" name="managerRoleIds" id="managerRoleIds" value="">
        <textarea cols=60 name="managerRoleNames" id="managerRoleNames" rows=3 class="SmallStatic BigTextarea" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectRole(['managerRoleIds','managerRoleNames'],'','1')">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="clearData('managerRoleIds','managerRoleNames')">清空</a>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">授权范围范围（人员）：</td>
    <td nowrap class="TableData" align="left">
       <input type="hidden" name="managerUserIds" id="managerUserIds" value="">
        <textarea cols=60 name="managerUserNames" id="managerUserNames" rows=3 class="SmallStatic BigTextarea" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['managerUserIds', 'managerUserNames'],'' , '1')">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="clearData('managerUserIds','managerUserNames')">清空</a>
    </td>
   </tr>
	</table>
	<input type="hidden" id="sid" name="sid" value="<%=sid==null?0:sid%>"/>
</form>
</body>
</html>