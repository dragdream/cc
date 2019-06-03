<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp"%> 
<%@ include file="/header/userheader.jsp" %>
<title>个人资料</title>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>


<script type="text/javascript">

function doInit(){
	var personObj = getPersonInfo(loginPersonId);
	if(personObj && personObj.uuid){
		 bindJsonObj2Cntrl(personObj);
	}else{
		$.MsgBox.Alert_auto("没有相关人员！");
		//alert("没有相关人员！");
	}
}


/**
 * 保存
 */

 function savePersonDeskTop(){
 	if (checkForm()){
 		var url = "<%=contextPath %>/personManager/updatePersonDeskTopType.action?updatePersonDeskTopType=4";
 		var para =  tools.formToJson($("#form1")) ;
 		var jsonRs = tools.requestJsonRs(url,para);
 		//alert(jsonRs);
 		if(jsonRs.rtState){
 			$.MsgBox.Alert_auto("保存成功！");
 			/* $.messager.show({
 				msg : '保存成功！！',
 				title : '提示'
 			}); */
 		}else{
 			$.MsgBox.Alert_auto(jsonRs.rtMsg);
 			//alert(jsonRs.rtMsg);

 		}
 	}	
 }

 function checkForm(){
	    var check= $("#form1").valid(); 
	    if(!check){
	    	return false;
	    }
	    return true;
	}

</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;font-size:12px;" >
<div id="toolbar" class="topbar clearfix" style="line-height:25px;border-bottom:1px solid #f2f2f2;border-bottom:2px solid #b0deff!important;background-color: #f8f8f8; ">
     <div class="fl" >
         <img class="title_img" src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_yhqx.png" alt="" />
         &nbsp;<span class="title" >个人资料</span>
     </div>
</div>

	<form  method="post" name="form1" id="form1" >
<table class="TableBlock_page" width="95%" align="center">

   <tr>
    <td colspan="2" style='vertical-align: middle;font-weight:bold;'><img src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_mmxg.png" align="absMiddle">&nbsp;&nbsp; <span>用户别名</span></td>
   </tr>
   <tr>
    <td style="text-indent: 20px;width:200px">用户名：<span style=""></span></td>
    <td id="userId">
      </td>
   </tr>
   <tr>
    <td style="text-indent: 20px" >真实姓名：</td>
    <td >
    <input style="font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="userName" id="userName" newMaxLength="30">
         </td>
   </tr>
   
  <tr>
    <td style="text-indent: 20px" >性别：</td>
    <td >
        <select style="font-family: MicroSoft YaHei;font-size: 12px;" name="sex" >
        <option value="0">男</option>
        <option value="1">女</option>
        </select>
    </td>
   </tr>
       <tr>
    <td style="text-indent: 20px" >生日：</td>
    <td>
        <input readonly type="text" name="birthdayStr" newMaxLength="10" value=""  onClick="WdatePicker()"/>&nbsp;&nbsp;
       <!--  <input type="checkbox" name="isLunar" id="isLunar"><label for="isLunar">是农历生日</label>      -->
    </td>
   </tr>
   <tr>
    <td colspan="2" style='vertical-align: middle;font-weight:bold;'><img src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_mmxg.png" align="absMiddle">&nbsp;&nbsp; <span>用户联系方式</span></td>
   </tr>
   
 <tbody id="isNotSuperAdmin" style="">
   <tr>
      <td style="text-indent: 20px;width:200px"> 手机：</td>
      <td >
        <input type="text" name="mobilNo" phone="true"  newMaxLength="23">
        <input type="checkbox" name="mobilNoHidden" id="mobilNoHidden" value='1'><label for="mobilNoHidden" style="cursor: hand;">手机号码不公开</label><br>
        （填写后可接收OA系统发送的手机短信，手机号码不公开仍可接收短信）      </td>
    </tr>
    <tr>
      <td style="text-indent: 20px" > 电子邮件：</td>
      <td >
        <input type="text" name="email" newMaxLength="50" " value="">
      </td>
    </tr>
     <tr>
      <td style="text-indent: 20px" > QQ：</td>
      <td >
        <input type="text" name="oicqNo" newMaxLength="20"  value="">
      </td>
    </tr>
      <tr>
      <td style="text-indent: 20px" > 微信号：</td>
      <td>
        <input type="text" name="weixinNo" newMaxLength="23"  value="">
      </td>
    </tr>
   <tr>
      <td style="text-indent: 20px" > 工作电话：</td>
      <td>
        <input type="text" name="telNoDept"  newMaxLength="23"  value="">
      </td>
    </tr>
     
   <tr>
      <td style="text-indent: 20px" > 工作传真：</td>
      <td >
        <input type="text" name="faxNoDept" newMaxLength="23"  value="">
      </td>
    </tr>
    
</tbody>


   </tbody>
    <tr>
    <td colspan="2" style='vertical-align: middle;font-weight:bold;'><img src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_mmxg.png" align="absMiddle">&nbsp;&nbsp; <span>家庭联系方式</span></td>
   </tr>

  <tr>
      <td style="text-indent: 20px;width:200px"> 家庭住址：</td>
      <td >
        <input style="font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="addHome" newMaxLength="23"  value="">
      </td>
    </tr>
     
   <tr>
      <td style="text-indent: 20px" > 家庭邮编：</td>
      <td>
        <input type="text" name="postNoHome"  newMaxLength="23"  value="">
      </td>
    </tr>
    <tr>
      <td style="text-indent: 20px" > 家庭电话：</td>
      <td >
        <input type="text" name="telNoHome"  newMaxLength="23"  value="">
      </td>
    </tr>
    
   <tr>
    <td style="text-indent: 20px;padding-left: 500px;" colspan="2">
        <input type="hidden" id="uuid" name="uuid"  value="">
        <input style="width: 45px;height: 25px;" type="button" value="保存" class="btn-win-white" title="保存" onclick="savePersonDeskTop()" >&nbsp;&nbsp;
    </td>
</tr>
</table>
  </form>
</body>
<script>
	$("#form1").validate();
</script>
</html>