<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
    String title="";
    if(sid>0){
    	title="编辑模板";
    }else{
    	title="新增模板";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
var sid = "<%=sid%>";
var editor;
function doInit(){
	CKEDITOR.replace('content',{
		width : 'auto',
		height:200
	});

	CKEDITOR.on('instanceReady', function (e) {
		editor = e.editor;
		 if(sid!="0"){
			var url = contextPath+"/pubTemplate/getTemplate.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				bindJsonObj2Cntrl(json.rtData);
				editor.setData(json.rtData.tplContent);
			}
		  }
	});
}

function commit(){
	if (checkForm()){
		var url = "";
		var para = tools.formToJson($("#form1"));
		para["tplContent"] = editor.getData();
		if(sid=="0"){
			url = contextPath+"/pubTemplate/addTemplate.action";
		}else{
			url = contextPath+"/pubTemplate/updateTemplate.action";
		}
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
			try{
				xparent.location.reload();
			}catch(e){}
			CloseWindow();
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
			return false;
		}
	}
}

function checkForm(){
	var tplName=$("#tplName").val();
	if(tplName==""||tplName==null){
		$.MsgBox.Alert_auto("请填写模板名称！");
		return false;
	}
    return true; 
}

</script>

</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
<div>
   <div class="fl" style="padding-top: 10px">
	   <span class="title"><%=title %></span>
   </div>
   <div class="fr" style="padding-top: 10px">
	<button class="btn-win-white" onclick="commit()">保存</button>
	&nbsp;&nbsp;
	<button class="btn-del-red" onclick="CloseWindow()">关闭</button>
   </div>
</div>

<span class="basic_border"></span>
<div class="base_layout_center">
	<form action="" id="form1">
	
	    <table class="TableBlock_page">
	      <tr>
	         <td class="TableData">模版名称：</td>
	         <td class="TableData"><input class="BigInput" type="text" required="true"  name="tplName" id="tplName"/></td>
	      </tr>
	      <tr>
	         <td class="TableData">模版描述 ：</td>
	         <td class="TableData"><textarea class="BigTextArea" style="height:100px" type="text" name="tplDesc" id="tplDesc"></textarea></td>
	      </tr>
	      <tr>
	         <td class="TableData">排序号 ：</td>
	         <td class="TableData"><input class="BigInput" style="width:100px" type="text" name="sortNo" id="sortNo"/></td>
	      </tr>
	      <tr>
	         <td class="TableData">模板内容 ：</td>
	         <td class="TableData">
	           <div id="content"></div>
		       </td>
	      </tr>
	      <input type="hidden" name="sid" value="<%=sid %>" />
	    </table>
	</form>
</div>
</body>
</html>
 