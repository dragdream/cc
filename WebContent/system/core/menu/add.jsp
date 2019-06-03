<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp"%>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<script type="text/javascript"
	src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript"
	src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript">
function doInit(){
	if (check()){
		//添加例子一
		var url = "<%=contextPath %>/sysMenu/addMenu.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			alert("保存成功！");
	        window.location.reload();
		}else{
			alert(jsonRs.rtMsg);
		}
	}
	
}


function checkStr(str){ 
    var re=/[\\"']/; 
    return str.match(re); 
  }
 
function check() {
    var menuId = document.getElementById("menuId");
    if (!menuId.value) {
  	  alert("菜单分类代码不能为空！");
  	  menuId.focus();
  	  menuId.select();
  	  return false;
    }
    if (!/[0-9]{3}/.test(menuId.value)) {
  	    alert("菜单分类代码必须为3位数字！");
  	    menuId.focus();
 	    menuId.select();
  	    return false;
    }
    var cntrl = document.getElementById("menuName"); 
    if (!$.trim(cntrl.value)) {
  	    alert("菜单名称不能为空！");
  	    cntrl.focus();
  	    cntrl.focus();
  	    return false;
    }
 
    
    if(checkStr(cntrl.value)){
      alert("您输入的菜单名称含有'双引号'、'单引号 '或者 '\\' 请从新填写");
      $('#menuName').focus();
      $('#menuName').select();
      return false;
    }
    
 
  /*   cntrl = document.getElementById("image"); 
    if (!$.trim(cntrl.value)) {
  	  alert("图片名不能为空！");
  	  cntrl.focus();
  	  return false;
    } */
    
    /**if(checkStr($("image").value)){
      alert("您输入的图片名称含有'双引号'、'单引号 '或者 '\\' 请从新填写");
      $('image').focus();
      $('image').select();
      return false;
    }*/
    return true;
  }


</script>

</head>
<body onload="">
	<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small" align="center">
		<tr>
			<td class="Big"><img src="<%=imgPath%>/infofind.gif"
				align="absmiddle"><span class="big3">菜单新增</span></td>
		</tr>
	</table>
	<br>
	<form method="post" name="form1" id="form1">
		<table class="TableBlock" width="80%" align="center">
			<tr class="TableLine1">
				<td nowrap>菜单编号：</td>
				<td nowrap><INPUT type=text name="menuId" id="menuId" size="10" maxlength="3" ></td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>菜单名称：</td>
				<td nowrap><input type="text" name="menuName" id="menuName"
					>&nbsp;</td>
			</tr>

			<tr class="TableLine1">
				<td nowrap>路径:：</td>
				<td nowrap><input type="text" name="menuCode" id="menuCode">
			</tr>
			<tr class="TableLine2">
				<td nowrap>图片：</td>
				<td nowrap><input type="text" name="icon" id="icon"></td>
			</tr>

			<tr class="TableControl">
				<td colspan="9" align="center"><input type="button" value="保存"
					class="BigButton" onclick="doInit();">&nbsp;&nbsp;</td>
			</tr>
		</table>
	</form>
</body>
</html>