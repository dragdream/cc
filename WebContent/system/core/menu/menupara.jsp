<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>菜单功能</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

function doInit(){
	var url = "<%=contextPath %>/sysPara/getSysPara.action";
	var paraName = "MENU_EXPAND_SINGLE";
	
	var jsonRs = tools.requestJsonRs(url,{paraName:paraName});
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		if(data){
			if(data.paraValue == "1"){
				$("#MENU_EXPAND_SINGLE")[0].checked = true;
			}
		}
	}else{
		alert(jsonRs.rtMsg);
	}
  
  
}

function commit(){
	var url = "<%=contextPath %>/sysPara/addOrUpdateSysPara.action";
	var paraName = "MENU_EXPAND_SINGLE";
	var paraValue = "0";
	if($("#MENU_EXPAND_SINGLE")[0].checked){
		paraValue = "1";
	}
	var jsonRs = tools.requestJsonRs(url,{paraName:paraName,paraValue:paraValue});
	if(jsonRs.rtState){
		$.jBox.tip("保存成功！",'info',{timeout:1000});
	}else{
		alert(jsonRs.rtMsg);
	}
}

</script>
</head>
<body topmargin="5" onload="doInit()">
<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big3"><span class="big3">&nbsp;菜单设置</span>
    </td>
  </tr>
</table>
<br>
<div align="center">

<form name="form1" method="post" id="form1">
<table class="TableList" width="95%">
  <tr class="TableHeader" align="center">
    <td width="150">参数</td>
    <td>数值</td>

    <td width="300">备注</td>
  </tr>
  <tr class="TableData" align="center" height="30">
    <td><b>导航菜单</b></td>

    <td nowrap align="left">
        <input type="checkbox" name="MENU_EXPAND_SINGLE" id="MENU_EXPAND_SINGLE"><label for="MENU_EXPAND_SINGLE">同时只能展开一个一级菜单</label><br>
    </td>
    <td align="left">

       左侧导航菜单是否显示菜单快捷组

    </td>
  </tr>
  <tr class="TableControl" align="center">
    <td colspan="3">
       <Input type="button"  class="btn btn-primary" value="保存" OnClick="commit()">
    </td>
  </tr>
</table>
</form>

</div>

</body>
</html>