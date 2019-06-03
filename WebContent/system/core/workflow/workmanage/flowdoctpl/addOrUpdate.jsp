<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   int flowTypeId=TeeStringUtil.getInteger(request.getParameter("flowTypeId"), 0);
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<title>新建/编辑页面</title>
<script type="text/javascript">
var flowTypeId=<%=flowTypeId %>;
var sid=<%=sid %>;
//初始化
function doInit(){
	//初始化数据
	if(sid>0){
		getInfoBySid();
	}
	
	
	//单附件简单上传组件
	new TeeSingleUpload({
		uploadBtn:"uploadBtn",
		callback:function(json){//回调函数，json.rtData
			$.MsgBox.Alert_auto("上传成功！",function(){
				$("#attachId").val(json.rtData[0].sid);
			});
		},
		post_params:{model:"flowDocTpl"}//后台传入值，model为模块标志
	});
	
}

//根据主键获取详情
function getInfoBySid(){
	var url=contextPath+"/teeFlowDocTemplateController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
	}
}



//保存
function doSave(){
	if(check()){
		var url=contextPath+"/teeFlowDocTemplateController/addOrUpdate.action";
		var param=tools.formToJson($("#form1"));
		param["flowTypeId"]=flowTypeId;
		param["sid"]=sid;
		var json=tools.requestJsonRs(url,param);
		return  json.rtState;
	}
}



function check(){
	var templateName=$("#templateName").val();
	var attachId=$("#attachId").val();
	if(templateName==null||templateName==""){
		$.MsgBox.Alert_auto("请填写模板名称！");
		return false;
	}
	if(attachId==null||attachId==""||attachId=="null"){
		$.MsgBox.Alert_auto("请上传模板附件！");
		return false;
	}
	return true;
}
</script>
</head>
<body onload="doInit();">
  <form id="form1">
   <table class="TableBlock_page" width="100%">
      <tr>
         <td style="width:20%">模板名称：</td>
         <td style="width:80%">
            <input  type="text" name="templateName" id="templateName" style="width: 300px"/>
         </td>
      </tr>
      <tr>
         <td>插件类路径：</td>
         <td>
            <input type="text" name="pluginClassPath" id="pluginClassPath" style="width: 300px"/>
         </td>
      </tr>
      <tr>
         <td>上传附件：</td>
         <td>
             <input type="hidden" name="attachId" id="attachId"/>
             <input class="btn-win-white" id="uploadBtn" type="button" value="上传"/>
         </td>
      </tr>
   </table>
  </form>
</body>
</html>