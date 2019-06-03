<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<title>组织管理</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">

function doInit(){
	var url = "<%=contextPath %>/orgManager/getOrg.action";
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		if(json.sid){
			bindJsonObj2Cntrl(json);
		}
			
	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsrg);
	}
}

/**
 * 保存
 */
function doSave(){
	if (check()){
		var url = "<%=contextPath %>/orgManager/addOrUpdateOrg.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
		 	/* $.messager.show({
				msg : '保存成功！！',
				title : '提示'
			}); */
		   $.MsgBox.Alert_auto("保存成功！",function(){
			   window.location.reload();
		   });
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
	
}


function checkStr(str){ 
    var re=/[\\"']/; 
    return str.match(re); 
  }
/***
 * 检查
 */
function check() {
   
    var cntrl = document.getElementById("unitName"); 
    if (!$.trim(cntrl.value)) {
  	    $.MsgBox.Alert_auto("单位名称不能为空！");
  	    cntrl.focus();
  	    cntrl.focus();
  	    return false;
    }
    if(checkStr(cntrl.value)){
      $.MsgBox.Alert_auto("您输入的单位名称含有'双引号'、'单引号 '或者 '\\' 请重新填写");
      $('#unitName').focus();
      $('#unitName').select();
      return false;
    }
    
 
    return true;
  }

</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
 <div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/org/img/icon_dwgl.png">
		<span class="title">单位管理</span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class="btn-win-white fl" onclick="doSave();" value="保存"/>
    </div>
 </div>
 <form   method="post" name="form1" id="form1">
<table class="TableBlock_page" width="100%" align="center">
			<tr class="TableLine2">
				<td nowrap style="text-indent: 10px;">单位名称：<font color="red"></font></td>
				<td nowrap><INPUT type=text name="unitName" id="unitName" size="50" maxlength="50" class="BigInput"></td>
			</tr>
			<tr class="TableLine2">
				<td nowrap style="text-indent: 10px;">单位地址：</td>
				<td nowrap><input type="text" name="address" id="address"
					 size="50"  class="BigInput" maxlength="100" >&nbsp;</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap style="text-indent: 10px;">开户行名称：</td>
				<td nowrap><input type="text" name="bankName" id="bankName" size="50"  maxlength="50"  class="BigInput"></td>
			</tr>
			<tr class="TableLine2">
				<td nowrap style="text-indent: 10px;">开户行账号：</td>
				<td nowrap><input type="text" name="bankNo" id="bankNo"
					 size="50" maxlength="50"  class="BigInput">&nbsp;</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap style="text-indent: 10px;">电话号码：</td>
				<td nowrap><input type="text" name="telNo" id="telNo" size="50"  class="BigInput" maxlength="20" ></td>
			</tr>
			<tr class="TableLine2">
				<td nowrap style="text-indent: 10px;">传真号码：</td>
				<td nowrap><input type="text" name="faxNo" id="faxNo"
					 size="50"  class="BigInput" maxlength="20" >&nbsp;</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap style="text-indent: 10px;">邮箱：</td>
				<td nowrap><input type="text" name="email" id="email" size="50"  maxlength="50"  class="BigInput"></td>
			</tr>
			<tr class="TableLine2">
				<td nowrap style="text-indent: 10px;">邮政编码：</td>
				<td nowrap><input type="text" name="postNo" id="postNo"
					 size="50"  class="BigInput" maxlength="20" >&nbsp;</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap style="text-indent: 10px;">网站：</td>
				<td nowrap><input type="text" name="url" id="url" size="50"  maxlength="50" class="BigInput"></td>
			</tr>
			
			<input type="text" id="sid" name="sid" style="display:none;"/>
		</table>
   </form>
</body>
</html>