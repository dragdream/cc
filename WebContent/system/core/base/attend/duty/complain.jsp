<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%
   String remarkTimeStr=TeeStringUtil.getString(request.getParameter("remarkTimeStr"));
   int registerNum=TeeStringUtil.getInteger(request.getParameter("registerNum"), 0);
   String desc="";
   if(registerNum==1){
	   desc="第一次登记";
   }else if(registerNum==2){
	   desc="第二次登记";
   }else if(registerNum==3){
	   desc="第三次登记";
   }else if(registerNum==4){
	   desc="第四次登记";
   }else if(registerNum==5){
	   desc="第五次登记";
   }else if(registerNum==6){
	   desc="第六次登记";
   }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<title>申诉页面</title>
<script type="text/javascript">
//初始化
function doInit(){
	/* 	审批人员 */
	getRuleApprovUser('approverId');
	
	
	//多附件快速上传
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"attachmentSidStr",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		renderFiles:true,//渲染附件
		post_params:{model:"dutyComplaint"}//后台传入值，model为模块标志
	});
}


function check(){
	var approverId=$("#approverId").val();
	var reason=$("#reason").val();
	if(approverId==0||approverId=="0"||approverId==""||approverId==null){
		parent.$.MsgBox.Alert_auto("请选择审批人员！");
		return false;
	}
	if(reason==""||reason==null){
		parent.$.MsgBox.Alert_auto("请填写申诉原因！");
		return false;
	}
	return  true;
}


function doSave(){
	if(check()){
		var url=contextPath+"/TeeDutyComplaintController/add.action";
		var param=tools.formToJson($("#form1"));
		var json=tools.requestJsonRs(url,param);
		return json;
	}
	
}
</script>
</head>
<body onload="doInit();" style="background-color: #f2f2f2">
  <form id="form1">
     <input type="hidden" name="remarkTimeStr" id="remarkTimeStr" value="<%=remarkTimeStr%>"/>
     <input type="hidden" name="registerNum" id="registerNum" value="<%=registerNum %>"/>
     <table class="TableBlock" width="100%">
        <tr>
           <td style="text-indent:15px;width: 20%">记录时间:</td>
           <td><%=remarkTimeStr %></td>
        </tr>
        <tr>
           <td style="text-indent:15px;width: 20%">登记次数:</td>
           <td><%=desc %></td>
        </tr>
        <tr>
           <td style="text-indent:15px;width: 20%">审批人员:</td>
           <td>
              <select class="BigSelect" name="approverId" id="approverId" >
			  </select>
           </td>
        </tr>
        <tr>
           <td style="text-indent:15px;width: 20%">申诉原因:</td>
           <td>
              <textarea rows="6" cols="60" id="reason" name="reason"></textarea>
           </td>
        </tr>
        <tr>
           <td style="text-indent:15px;width: 20%">附件:</td>
           <td>
                <div id="fileContainer2"></div>
				<a id="uploadHolder2" class="add_swfupload">
					<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
				</a>
				<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
           </td>
        </tr>
     </table>
  </form>
</body>
</html>