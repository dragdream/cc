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
	if(sid!=null){//更新
		url = contextPath+"/teeImgBaseController/editImgBase.action";
	}else{//创建
		url = contextPath+"/teeImgBaseController/addImgBase.action";
	}
	var param = tools.formToJson($("#form"));
	var json = tools.requestJsonRs(url,param);
	return json;
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
	<table style="width:100%;font-size:12px;display:none;" class="TableBlock" >
		<tr>
			<td colspan="4" style="background:#f0f0f0" align="center"><b>基础信息</b></td>
		</tr>
		<tr>
			<td nowrap class="TableData" >图片库名称：</td>
			<td nowrap class="TableData" >
				<input type="text" class="BigInput" name="imgDirName" id="imgDirName"/>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" >图片库路径：</td>
			<td nowrap class="TableData" >
			<input type="text" class="BigInput" name="imgDir" id="imgDir"/>
			</td>
		</tr>
		  <tr >
   			 <td nowrap class="TableData"  >查看范围（部门）：</td>
   			 <td nowrap class="TableData" align="left">
      		 <input type="hidden" name="postDeptIds" id="postDeptIds" value=""/>
       		 <textarea cols=60 name="postDeptNames" id="postDeptNames" rows=3 class="SmallStatic BigTextarea" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
       		 <a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['postDeptIds','postDeptNames'],'','1')">选择</a>
       		 <a href="javascript:void(0);" class="orgClear" onClick="clearData('postDeptIds','postDeptNames')">清空</a>
    </td>
   </tr>
   
 
   <tr>
    <td nowrap class="TableData" >查看范围（角色）：</td>
    <td nowrap class="TableData" align="left">
        <input type="hidden" name="postUserRoleIds" id="postUserRoleIds" value="">
        <textarea cols=60 name="postUserRoleNames" id="postUserRoleNames" rows=3 class="SmallStatic BigTextarea" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectRole(['postUserRoleIds','postUserRoleNames'],'','1')">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="clearData('postUserRoleIds','postUserRoleNames')">清空</a>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">查看范围（人员）：</td>
    <td nowrap class="TableData" align="left">
       <input type="hidden" name="postUserIds" id="postUserIds" value="">
        <textarea cols=60 name="postUserNames" id="postUserNames" rows=3 class="SmallStatic BigTextarea" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['postUserIds', 'postUserNames'],'' , '1')">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="clearData('postUserIds','postUserNames')">清空</a>
    </td>
   </tr>
	</table>
	<table style="width:100%;font-size:12px" class="TableBlock">
		<tr>
			<td colspan="4" style="background:#f0f0f0" align="center">
			   <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
			   <b>上传权限</b>
			</td>
		</tr>
		  <tr >
   			 <td nowrap class="TableData"  >授权范围（部门）：</td>
   			 <td nowrap class="TableData" align="left">
      		 <input type="hidden" name="uploadDeptIds" id="uploadDeptIds" value=""/>
       		 <textarea cols=50 name="uploadDeptNames" id="uploadDeptNames" rows=4 class="SmallStatic BigTextarea" readonly  ></textarea>
       		 <span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectDept(['uploadDeptIds','uploadDeptNames'],'','1')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('uploadDeptIds','uploadDeptNames')" value="清空"/>
	         </span>
    </td>
   </tr>
   
 
   <tr>
    <td nowrap class="TableData" >授权范围（角色）：</td>
    <td nowrap class="TableData" align="left">
        <input type="hidden" name="uploadRoleIds" id="uploadRoleIds" value="">
        <textarea cols=50 name="uploadRoleNames" id="uploadRoleNames" rows=4 class="SmallStatic BigTextarea" readonly  ></textarea>
        <span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectRole(['uploadRoleIds','uploadRoleNames'],'','1')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('uploadRoleIds','uploadRoleNames')" value="清空"/>
	    </span>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">授权范围（人员）：</td>
    <td nowrap class="TableData" align="left">
       <input type="hidden" name="uploadUserIds" id="uploadUserIds" value="">
        <textarea cols=50 name="uploadUserNames" id="uploadUserNames" rows=4 class="SmallStatic BigTextarea" readonly  ></textarea>
        <span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['uploadUserIds', 'uploadUserNames'],'' , '1')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('uploadUserIds','uploadUserNames')" value="清空"/>
	    </span>
    </td>
   </tr>
		<tr>
			<td colspan="4" style="background:#f0f0f0" align="center">
			   <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
			   <b>管理权限</b>（可以对图片库中的文件夹进行增删改，同时可对文件进行删除等操作）
			</td>
		</tr>
		  <tr >
   			 <td nowrap class="TableData"  >授权范围范围（部门）：</td>
   			 <td nowrap class="TableData" align="left">
      		 <input type="hidden" name="managerDeptIds" id="managerDeptIds" value=""/>
       		 <textarea cols=50 name="managerDeptNames" id="managerDeptNames" rows=4 class="SmallStatic BigTextarea" readonly  ></textarea>
       		 <span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectDept(['managerDeptIds','managerDeptNames'],'','1')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('managerDeptIds','managerDeptNames')" value="清空"/>
	         </span>
       		 
    </td>
   </tr>
   
 
   <tr>
    <td nowrap class="TableData" >授权范围范围（角色）：</td>
    <td nowrap class="TableData" align="left">
        <input type="hidden" name="managerRoleIds" id="managerRoleIds" value="">
        <textarea cols=50 name="managerRoleNames" id="managerRoleNames" rows=4 class="SmallStatic BigTextarea" readonly  ></textarea>
        <span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectRole(['managerRoleIds','managerRoleNames'],'','1')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('managerRoleIds','managerRoleNames')" value="清空"/>
	     </span>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">授权范围范围（人员）：</td>
    <td nowrap class="TableData" align="left">
       <input type="hidden" name="managerUserIds" id="managerUserIds" value="">
        <textarea cols=50 name="managerUserNames" id="managerUserNames" rows=4 class="SmallStatic BigTextarea" readonly  ></textarea>
        <span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['managerUserIds', 'managerUserNames'],'' , '1')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('managerUserIds','managerUserNames')" value="清空"/>
	     </span>
    </td>
   </tr>
	</table>
	<input type="hidden" id="sid" name="sid" value="<%=sid==null?0:sid%>"/>
</form>
</body>
</html>