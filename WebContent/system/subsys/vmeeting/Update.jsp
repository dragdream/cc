<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<title>视频会议组件设置</title>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
<%
	String meetingId = TeeStringUtil.getString(request.getParameter("meetingId"));
	
%>
var meetingId = "<%=meetingId%>";
var contextPath = '<%=contextPath%>';
var myDate = new Date();   
//查要改的数据
function doInit(){
	var url = contextPath+"/vmeeting/getMeeting.action";
	var json = tools.requestJsonRs(url,{meetingId:meetingId});
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}
}

/**
 * 修改
 */
function doUpdate(){
	if($("#form1").valid()){
		var param = tools.formToJson($("#form1"));

		  var s= new Date(document.getElementById("startT").value.replace(/-/g,'/'));
		  var e= new Date(document.getElementById("endT").value.replace(/-/g, '/'));
		if(s >= e){ 
			$.MsgBox.Alert_auto("开始时间要小于等于结束时间！"); 
		return false;
		}else if(e<=myDate){
			$.MsgBox.Alert_auto("结束时间要大于当前时间！");
			}else{
		
		var url = contextPath+"/vmeeting/updateMeetingInfo.action?";
		var jsonRs = tools.requestJsonRs(url,param);
		if(jsonRs.rtState){
		/* 	top.$.jBox.tip(jsonRs.rtMsg,"info");
			try{
				xparent.location.reload();
			}catch(e){}
			CloseWindow(); */
			$.MsgBox.Alert_auto("修改成功！");
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto("修改有误！");
			return false;
		}
	}
	}
}

</script>
</head>
<body id="body" onload="doInit()"  style="padding-left: 10px;padding-right: 10px;">
<div id="toolbar" class = "setHeight clearfix"  style="margin-bottom: 10px;margin-top: 10px;">
<div class=" fl">
   <h4 style="font-size: 16px;font-family:MicroSoft YaHei;margin-left: 10px;">
				编辑视频会议
				</h4>
</div>
   <div class=" fr">
	     <input type="button"  value="保存" class="btn-win-white" title="保存" onclick="doUpdate()" style="width:45px;height:25px;" />
	 	 <input type="button" value="返回" class="btn-win-white" title="返回" onclick="history.go(-1)" style="width:45px;height:25px;" />&nbsp;
   </div> 
   <span class="basic_border" style="margin-top: 10px;"></span>
</div>


<form  name="form1" id="form1" method="post" enctype="multipart/form-data">
  <table class="TableBlock_page"  align="center">
   <tr>
    <td nowrap class="TableData" style="width:200px;text-indent: 10px;">会议名称：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="meetingName" class="BigInput" required name="meetingName" style="width:300px;font-family: MicroSoft YaHei;"/>
    	<input  type="hidden" id="chairmanPwd" name="chairmanPwd"   value="111111"  />
         <input  type="hidden" id="confuserPwd" name="confuserPwd" value="222222"  />
    </td>
   </tr>
    <tr>
    <td style="text-indent: 10px;" nowrap class="TableData">开始时间：</td>
    <td nowrap class="TableData" >
    	<input style="width: 300px;" type='text' id="startT" class="Wdate BigInput" required name="startT" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endT\')}'})"/>
    </td>
   </tr>
   <tr>
   <td style="text-indent: 10px;" nowrap class="TableData">结束时间:</td>
    <td nowrap class="TableData">
     <input style="width: 300px;" type='text' id="endT" class="Wdate BigInput" required name="endT" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startT\')}'})"/>
    </td>
   </tr>
   <tr>
   <td style="text-indent: 10px;"  nowrap class="TableData">会议数量:</td>
    <td nowrap class="TableData">
     <input type='text' id="attendNum"  class="BigInput" required name="attendNum" style="width:300px;font-family: MicroSoft YaHei;"/>
    </td>
   </tr>
   <tr>
   <td style="text-indent: 10px;" nowrap class="TableData" style="width:100px">视屏数目:</td>
   <td nowrap class="TableData" >
   <input type="text" id="maxvideo" name="maxvideo" class="BigInput"   required  style="width:300px;font-family: MicroSoft YaHei;"/></td>
   </tr>
   <tr>
   <td nowrap class="TableData" style="text-indent: 10px;">发言人数:</td>
   <td nowrap class="TableData" >
   <input type="text" id="maxPersonspeak" name="maxPersonspeak" class="BigInput"   required style="width:300px;font-family: MicroSoft YaHei;"/></td>
   </tr>
<!--    <tr>
    <td nowrap class="TableData">主持人：</td>
    <td nowrap class="TableData" >
    	<input type='hidden' id="mainUser" class="BigInput" name="mainUser" />
    	<input type='text' readonly id="mainUserName" class="BigInput readonly easyui-validatebox" required name="mainUserName" />
    	&nbsp;<a href="#" onclick="selectSingleUser(['mainUser','mainUserName']);">选择</a>&nbsp;<a href="#" onclick="clearData('mainUser','mainUserName');">清空</a>
    </td>
   </tr> -->
   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData">与会人员:</td>
    <td  class="TableData" >
    	<input type='hidden' id="personIds" class="BigInput" name="personIds" />
    	  <textarea readonly id="personNames" class="BigTextarea readonly" name="personNames" style="height:100px;width:500px;font-family: MicroSoft YaHei;resize:none;"></textarea>
        <span class='addSpan'>
			<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/sphy/add.png" onclick="selectUser(['personIds','personNames']);" value="选择"/>
			 &nbsp;&nbsp;
			 <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/sphy/clear.png" onclick="clearData('personIds','personNames');" value="清空"/>
		</span>
    </td>
   </tr>
   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData">会议内容:</td>
    <td nowrap class="TableData" >
    	<!-- //<textarea readonly id="subject" name="subject"></textarea> -->
    	<textarea rows="" cols=""class="BigInput  easyui-validatebox" style="height:100px;width:500px;font-family: MicroSoft YaHei;" name="content" id="content"></textarea>
    	
    </td>
   </tr>
  
</table>
<input type="hidden" name=meetingId value="<%=meetingId%>"/>

</form>
</body>
</html>