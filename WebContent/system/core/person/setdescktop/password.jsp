<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<%@ include file="/header/validator2.0.jsp"%> 
<title>修改密码</title>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>

<script type="text/javascript">

function doInit(){
	//判断密码校验
	var personObj = getPersonInfo(loginPersonId);
	if(personObj && personObj.uuid){
		 bindJsonObj2Cntrl(personObj);
	}else{
		$.MsgBox.Alert_auto("没有相关人员！");
		//alert("没有相关人员！");
	}
	//获取系统日志
	getSysLog();
}


/**
 * 修改密码
 */

 function savePersonDeskTop(){
 	if (checkForm()){
 		var url = "<%=contextPath %>/personManager/updatePassword.action";
 		var para =  tools.formToJson($("#form1")) ;
 		var jsonRs = tools.requestJsonRs(url,para);
 		if(jsonRs.rtState){
 			$.MsgBox.Alert_auto("修改密码成功！",function(){
 				/*  //清空三个密码输入框
 				$("#oldPassword").val("");
 				$("#newPassword").val("");
 				$("#confirmNewPassword").val("");
 				
 				getSysLog(); */
 				window.location.reload();
 			});
 				//window.location.reload();
 			//$.jBox.tip("修改密码成功！" ,'info',{timeout:1500});
 			/* $.messager.show({
 				msg : '修改密码成功！！',
 				title : '提示'
 			}); */
 		}else{
 			$.MsgBox.Alert_auto(jsonRs.rtMsg);
 			//alert(jsonRs.rtMsg);

 		}
 	}	
 }


/**
 * 获取更新密码列表
 */
function getSysLog(){
	var url = "<%=contextPath %>/sysLogManage/getLogByLoginPerson.action";
	var para =  {count:10 , type :'003G'} ;
	var jsonRs = tools.requestJsonRs(url,para);
	//alert(jsonRs);
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		if(data.length >0){
			var tableStr = "<table width='90%' align='center' class='TableBlock_page'>"
		      + " <tbody id='tbody'>"
		      + "<tr >"
		      + "<td style='font-weight:bold;text-indent: 20px;' >操作用户 </td>"
		      + "<td style='font-weight:bold;text-indent: 20px;'> 修改时间</td>"
		     // + "<td > 所属部门</td>"
		      + "<td style='font-weight:bold;text-indent: 20px;'> IP地址</td>"
		      + "<td style='font-weight:bold;text-indent: 20px;'> 备注</td>"
		      +"</tr>";
			for(var i = 0;i<data.length ; i++){
				var obj = data[i];
				var id = obj.sid;
				var time = obj.time + "";
				var timeStr = getFormatDateStr(time , 'yyyy-MM-dd HH:mm');
				tableStr = tableStr + "<tr  align='center'>"
				+"<td style='text-indent: 20px;'>" + obj.userName + "</td>"
				+"<td style='text-indent: 20px;'>" + timeStr+ "</td>"
				//+"<td>" + seal.deptName + "</td>"
				+"<td style='text-indent: 20px;'>" + obj.ip + "</td>"
				+"<td style='text-indent: 20px;'>" + obj.remark + "</td>"
			
				+ "</tr>";		
			}
		    tableStr = tableStr + " </tbody></table>";
			$("#sysLog").append(tableStr);
		}else{
			messageMsg("没有相关数据", "sysLog" ,'' ,280);
		}
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
		//alert(jsonRs.rtMsg);
	}
	
}

function checkForm(){
    var check= $("#form1").valid(); 
    if(!check){
    	return false;
    }
    return true;
}
/* function check() {

	return $("#form1").form('validate'); 
  }  */
</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
<div id="toolbar" class="topbar clearfix" style="line-height:25px;border-bottom:1px solid #f2f2f2;border-bottom:2px solid #b0deff!important;background-color: #f8f8f8; ">
     <div class="fl" >
         <img class="title_img" src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_xgmm.png" alt="" />
         &nbsp;<span class="title">修改密码</span>
     </div>
</div>

	<br>
	<form  method="post" name="form1" id="form1" >
<table class="TableBlock_page" width="95%" align="center">

   <tr>
    <td colspan="2" style='vertical-align: middle;font-weight:bold;'><img src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_mmxg.png" align="absMiddle">&nbsp;&nbsp; <span  class="imgMiddleSpan">修改密码</span></td>
   </tr>
   <tr>
    <td style="text-indent: 20px;width:200px">用户名：<span style=""></span></td>
    <td id="userId">
      </td>
   </tr>
   <tr>
    <td style="text-indent: 20px">真实姓名：</td>
    <td >
    <input style="width: 200px;height: 25px;border: 1px solid #dadada;font-family: MicroSoft YaHei;font-size: 12px;" readonly type="text" name="userName" id="userName" newMaxLength="30">
         </td>
   </tr>
   
  <tr>
    <td style="text-indent: 20px" >原密码：</td>
    <td >
        <input style="width: 200px;height: 25px;border: 1px solid #dadada;" type="password" name="oldPassword" id="oldPassword">
    </td>
   </tr>

   <tr>
    <td style="text-indent: 20px">新密码：</td>
    <td >
        <input style="width: 200px;height: 25px;border: 1px solid #dadada;" type="password" name="newPassword" id="newPassword" notEqualTo="#oldPassword"  newMaxLength=20 > <span id="passMessage"></span>
    </td>
   </tr>
   <tr>
    <td style="text-indent: 20px">确认新密码：</td>
    <td >
        <input style="width: 200px;height: 25px;border: 1px solid #dadada;" equalTo="#newPassword" type="password" name="confirmNewPassword" id="confirmNewPassword"  newMaxLength=20> <span id="passMessage2"></span>
    </td>
   </tr>
    <td style="text-indent: 20px;padding-left: 500px;" colspan="2">
        <input type="hidden" id="uuid" name="uuid"  value="">
        <input style="width: 45px;height: 25px;" type="button" value="保存" class="btn-win-white" title="保存" onclick="savePersonDeskTop()" >&nbsp;&nbsp;
    </td>

</table>
  </form>
  
  
  <div style='padding-top:5px;'>
    <table  class="TableBlock_page" width="95%" align="center">
		<tr class='TableHeader'>
			<td colspan="2" style='vertical-align: middle;font-weight:bold;'>
			<img src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_mmxg.png" align="absMiddle">&nbsp;&nbsp;<span> 最近10条修改密码日志</span>
			</td>
		</tr>
	</table>
	<div id="sysLog"> 
	
	</div>
  </div>
  <br>
</body>
</html>