<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp"%> 
<title>超级密码设置页面</title>
</head>
<script type="text/javascript">
var relOldPwd="";
//初始化
function doInit(){
	//从系统参数表中获取原密码
	var url=contextPath+"/teePartThreeController/getPartThreePwd.action";
	var json=tools.requestJsonRs(url);
	if(json.rtState){
		relOldPwd=json.rtData;
	}
}

//保存
function doSave(){
	if(check()){
		var url=contextPath+"/teePartThreeController/updatePartThreePwd.action";
		var newPwd=$("#newPwd").val();
		var json=tools.requestJsonRs(url,{newPwd:newPwd});
		if(json.rtState){
			$.MsgBox.Alert_auto("修改成功！",function(){
				back();
			});
		}
	}
	
}
//验证
function check(){
	var oldPwd=$("#oldPwd").val();
	var newPwd=$("#newPwd").val();
	var newPwd1=$("#newPwd1").val();
	
	
   	if(oldPwd!=relOldPwd){
   		$.MsgBox.Alert_auto("原密码不正确！");
   		return false;
   	}else{
   		if(newPwd!=newPwd1){
   			$.MsgBox.Alert_auto("确认密码和新密码输入不一致！");
   	   		return false;
   		}else{
   			return true;
   		}
   	}
    	
     return true;
}


//返回
function back(){
   window.location.href=contextPath+"/system/core/system/partthree/partthreeSetting.jsp";	
}
</script>

<body onload="doInit();">
   <form id="form1">
      <table class="TableBlock_page" >
          <tr>
             <td style="width: 15%;text-indent: 10px">原密码：</td>
             <td>
                <input type="password" id="oldPwd" name="oldPwd"  style="width: 250px;height: 25px;border: 1px solid #dadada;"/>
             </td>
          </tr>
          <tr>
             <td style="width: 15%;text-indent: 10px">新密码：</td>
             <td>
                <input type="password" id="newPwd"  name="newPwd"  style="width: 250px;height: 25px;border: 1px solid #dadada;"/>
             </td>
          </tr>
          <tr>
             <td style="width: 15%;text-indent: 10px">确认密码：</td>
             <td>
                <input type="password" id="newPwd1" name="newPwd1" equalTo="#newPwd" style="width: 250px;height: 25px;border: 1px solid #dadada;" />
             </td>
          </tr>
           <tr>
             <td colspan="2">
                <div style="text-align: center;">
                   <input type="button" class="btn-win-white" value="保存" onclick="doSave();"/>&nbsp;&nbsp;&nbsp;
                   <input type="button" class="btn-del-red" value="返回" onclick="back()"/>
                </div>
             </td>
          </tr>
      </table>
   </form>
</body>
</html>