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
<title>界面主题</title>
<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>

<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>
<script type="text/javascript">
var otheme;
function doInit(){
	var personObj = getPersonInfo(loginPersonId);
	if(personObj && personObj.uuid){
		 bindJsonObj2Cntrl(personObj);
		 otheme = personObj.theme;
	}else{
		$.MsgBox.Alert_auto("没有相关人员！");

		//$.MsgBox.Alert("提示","没有相关人员！");
	}
}

function getSysMenuByLoginPerson(){
	var url = "<%=contextPath %>/teeMenuGroup/getSysMenuByLoginPerson.action";
	var jsonRs = tools.requestJsonRs(url);
	//alert(jsonRs);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		var selects = document.getElementById("menuExpand");
		for(var i = 0; i < dataList.length; i++){
		    var prc = dataList[i];
		    var option = document.createElement("option"); 
		    option.value = prc.uuid; 
		    option.innerHTML = prc.menuName; 
		   /* if(prc.value == ){
		      option.selected = true;
		    }*/
		    selects.appendChild(option);
		}
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);

	}
}

/**
 * 保存
 */
function savePersonDeskTop(){
	if (check()){
		var url = "<%=contextPath %>/personManager/updatePersonDeskTopType.action?updatePersonDeskTopType=0";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		//alert(jsonRs);
		if(jsonRs.rtState){
			try{
				window.external.IM_SET_AUTO_POP($("#autoPopSms").val());
			}catch(e){}
			if(otheme!=$("#theme").val()){
				top.location = "<%=contextPath%>/system/frame/"+$("#theme").val()+"/index.jsp";
			}else{
				$.MsgBox.Alert_auto("保存成功！");
			}
		}else{
			 $.MsgBox.Alert_auto(jsonRs.rtMsg);

		}
	}	
}


function check() {

    return true;
  }

</script>


</head>
<body onload="doInit();" style="overflow:hidden;padding-left: 10px;padding-right: 10px">
<div id="toolbar" class="topbar clearfix">
   <img class="title_img" src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_zmzt.png" alt="" />
   &nbsp;<span class="title">界面主题设置</span>
</div>

<div>
	<br/>
	<form   method="post" name="form1" id="form1" >
	<table class="TableBlock_page" width="80%" align="center">
	
	    <tr>
	      <td class="TableData TableBG" width="300px" style="text-indent: 10px">界面主题</td>
	      <td class="TableData">
	        <select name="theme" id="theme" class="BigSelect" onChange="" style="width: 120px">
	  		<option value="4" >现代门户</option>
	  		<option value="8" >智慧政务</option>
	  		<option value="classic" selected>2017版经典</option>
	        </select>
	        <br>
	      </td>
	    </tr>
	    <tr id="tr_sms" >
	      <td nowrap class="TableData TableBG"  width="300px" style="text-indent: 10px">消息提醒窗口弹出方式</td>
	      <td class="TableData">
	        <select name="autoPopSms" class="BigSelect" id="autoPopSms" style="width: 120px">
	          <option value="1" selected>自动</option>
	          <option value="0" >手动</option>
	        </select>
	      </td>
	    </tr>
	   </tbody>
	    <!-- <tr align="center" class="TableControl">
	      <td colspan="2" nowrap>
	        <input type="button" value="保存并应用" class="btn btn-success" onclick="savePersonDeskTop();">&nbsp;&nbsp;
	      </td>
	    </tr> -->
	  </table>
	  <div align="center" style="margin-top: 15px">
	     <input type="button" value="保存并应用" class="btn-win-white" onclick="savePersonDeskTop();">
	  </div>
	  
	</form>
	</div>
</body>
</html>
