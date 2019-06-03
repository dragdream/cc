<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
   int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"),0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header.jsp" %>
<title>菜单定义指南</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/sys.js"></script>
<script type="text/javascript">

/**
 * 保存
 */
function doSave(){
	if (check()){
		var url = "<%=contextPath %>/sysMenu/addFlowMenu.action";
		var pMenuId=$("#pMenuId").val();
		var menus=[];
		menus.push({menuId:pMenuId+$("#menuId1").val(),menuName:$("#menuName1").val(),icon:$("#icon1").val(),menuCode:$("#menuCode1").val()});
		menus.push({menuId:pMenuId+$("#menuId2").val(),menuName:$("#menuName2").val(),icon:$("#icon2").val(),menuCode:$("#menuCode2").val()});
		menus.push({menuId:pMenuId+$("#menuId3").val(),menuName:$("#menuName3").val(),icon:$("#icon3").val(),menuCode:$("#menuCode3").val()});
		menus.push({menuId:pMenuId+$("#menuId4").val(),menuName:$("#menuName4").val(),icon:$("#icon4").val(),menuCode:$("#menuCode4").val()});
		menus.push({menuId:pMenuId+$("#menuId5").val(),menuName:$("#menuName5").val(),icon:$("#icon5").val(),menuCode:$("#menuCode5").val()});
		var para =  tools.formToJson($("#form1"));
		
		para["menus"]=tools.jsonArray2String(menus);
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			//top.$.jBox.tip('保存成功','info',{timeout:1500});
			alert("保存成功！");
			history.go(-1);
		}else{
			alert(jsonRs.rtMsg);
		}
	}
	
}


function checkStr(str){ 
    var re=/[\\"']/;
    return str.match(re); 
}
/****
 * 检查
 */
function check() {
	 var status =  $("#form1").form('validate'); 
	 if(status == false){
		 return false;
	 }
  	var menuName1 = $("#menuName1").val(); 
  	var menuName2 = $("#menuName2").val(); 
  	var menuName3 = $("#menuName3").val(); 
  	var menuName4 = $("#menuName4").val(); 
  	var menuName5 = $("#menuName5").val();
  	
  	var map={};
  	var menuId1 = $("#menuId1").val(); 
  	var menuId2 = $("#menuId2").val(); 
  	var menuId3 = $("#menuId3").val(); 
  	var menuId4 = $("#menuId4").val(); 
  	var menuId5 = $("#menuId5").val();
  	
  	map[menuId1]=menuId1;
  	map[menuId2]=menuId2;
  	map[menuId3]=menuId3;
  	map[menuId4]=menuId4;
  	map[menuId5]=menuId5;
  	
  	if(Object.getOwnPropertyNames(map).length!=5){
  		 alert("子菜单编号不能重复，请重新检查填写！");
  		 return false;
  	}
  	
    if(checkStr(menuName1)){
      alert("您输入的菜单名称含有'双引号'、'单引号 '或者 '\\' 请从新填写");
      $('#menuName1').focus();
      $('#menuName1').select();
      return false;
    }
    
    if(checkStr(menuName2)){
        alert("您输入的菜单名称含有'双引号'、'单引号 '或者 '\\' 请从新填写");
        $('#menuName2').focus();
        $('#menuName2').select();
        return false;
     }
    
    
    if(checkStr(menuName3)){
        alert("您输入的菜单名称含有'双引号'、'单引号 '或者 '\\' 请从新填写");
        $('#menuName3').focus();
        $('#menuName3').select();
        return false;
     }
    
    if(checkStr(menuName4)){
        alert("您输入的菜单名称含有'双引号'、'单引号 '或者 '\\' 请从新填写");
        $('#menuName4').focus();
        $('#menuName4').select();
        return false;
     }
    
    if(checkStr(menuName5)){
        alert("您输入的菜单名称含有'双引号'、'单引号 '或者 '\\' 请从新填写");
        $('#menuName5').focus();
        $('#menuName5').select();
        return false;
     }
    return true;
  }



/**
 * 选择菜单
 * @return
 */
var menuArray = null ;
function toSelectTreeMenu(retArray) {
  menuArray = retArray;
  var url =  "<%=contextPath%>/system/core/menu/menuTree.jsp?sid=0";
  dialogChangesize(url , 300, 450);
}


function closeBS(){
	parent.BSWINDOW.modal("hide");;
}
</script>
</head>
<body onload="doInit()">
   <table border="0" width="100%" cellspacing="0" cellpadding="3" class="small" align="center">
    <tr>
       <td class="Big">
         <span style="color: red;font-size: 14px">（说明：子菜单编号长度为三位，作为排序之用。在同一父菜单下的平级菜单，该代码不能重复） </span>
       </td>
    </tr>
  </table><br>
<form  method="post" name="form1" id="form1">
	<table class="TableBlock" width="88%" align="center">
	   <tr class="TableLine1">
			<td nowrap width="15%">上级菜单：</td>
			<td>
				<input type="text"  name="pMenuId" id="pMenuId" style="display:none;" value=""/>
				<input type="text"  readonly name="pMenuIdDesc" id="pMenuIdDesc" value="" class="easyui-validatebox BigInput" required="true"/>&nbsp;	
				<a href ="javascript:toSelectTreeMenu('0')">添加</a>
			</td>
		</tr>
	</table>
	<table class="TableBlock" width="80%" align="center" style="margin-top: 10px"> 
		<tr class="TableLine1">
			<td nowrap>子菜单编号：</td>
			<td nowrap><INPUT type=text name="menuId1" id="menuId1" size="10" maxlength="3" class="easyui-validatebox BigInput" required="true" validType ="numberstrLength[3]" >
			</td>
		</tr>
		<tr class="TableLine1">
			<td nowrap>子菜单名称：</td>
			<td nowrap>
				<input type="text" name="menuName1" id="menuName1" maxlength="100" class="easyui-validatebox BigInput" required="true" value="我的工作" >
			</td>
		</tr>
       	<tr class="TableLine1">
			<td nowrap>图片：</td>
			<td nowrap>
				<input type="text" name="icon1" id="icon1" size="40" maxlength="100" class="BigInput"> 
			</td>
		</tr>
		<tr class="TableLine1">
			<td nowrap>路径:</td>
			<td nowrap><input type="text" name="menuCode1" id="menuCode1" size="60"  class="BigInput" readonly="readonly"  value="/system/core/workflow/flowrun/list/index.jsp?flowId=<%=flowId%>">
			</td>
		</tr>
	</table>
		
		<table class="TableBlock" width="80%" align="center" style="margin-top: 10px"> 
            
			<tr class="TableLine1">
				<td nowrap>子菜单编号：</td>
				<td nowrap><INPUT type=text name="menuId2" id="menuId2" size="10" maxlength="3" class="easyui-validatebox BigInput" required="true" validType ="numberstrLength[3]" >
				</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>子菜单名称：</td>
				<td nowrap><input type="text" name="menuName2" id="menuName2" maxlength="100" class="easyui-validatebox BigInput" required="true" value="工作查询" >&nbsp;</td>
			</tr>
        <tr class="TableLine2">
				<td nowrap>图片：</td>
				<td nowrap>
					<input type="text" name="icon2" id="icon2" size="40" maxlength="100" class="BigInput"> 
				</td>
			</tr>
			<tr class="TableLine1">
				<td nowrap>路径:</td>
				<td nowrap><input type="text" name="menuCode2" id="menuCode2" size="60" readonly="readonly"  class="BigInput" value="/system/core/workflow/workmanage/workquery/index.jsp?flowId=<%=flowId%>">
				</td>
			</tr>
		</table>
		
		
		<table class="TableBlock" width="80%" align="center" style="margin-top: 10px"> 
            
			<tr class="TableLine1">
				<td nowrap>子菜单编号：</td>
				<td nowrap><INPUT type=text name="menuId3" id="menuId3" size="10" maxlength="3" class="easyui-validatebox BigInput" required="true" validType ="numberstrLength[3]" >
				</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>子菜单名称：</td>
				<td nowrap><input type="text" name="menuName3" id="menuName3" maxlength="100" class="easyui-validatebox BigInput" required="true" value="工作委托" >&nbsp;</td>
			</tr>
        <tr class="TableLine2">
				<td nowrap>图片：</td>
				<td nowrap>
					<input type="text" name="icon3" id="icon3" size="40" maxlength="100" class="BigInput"> 
				</td>
			</tr>
			<tr class="TableLine1">
				<td nowrap>路径:</td>
				<td nowrap><input type="text" name="menuCode3" id="menuCode3"  size="60"  class="BigInput"  readonly="readonly" value="/system/core/workflow/workmanage/flowRule/index.jsp?flowId=<%=flowId%>">
				</td>
			</tr>
		</table>
		
		
		<table class="TableBlock" width="80%" align="center" style="margin-top: 10px"> 
            
			<tr class="TableLine1">
				<td nowrap>子菜单编号：</td>
				<td nowrap><INPUT type=text name="menuId4" id="menuId4" size="10" maxlength="3" class="easyui-validatebox BigInput" required="true" validType ="numberstrLength[3]" >
				</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>子菜单名称：</td>
				<td nowrap><input type="text" name="menuName4" id="menuName4" maxlength="100" class="easyui-validatebox BigInput" required="true" value="工作监控" >&nbsp;</td>
			</tr>
        <tr class="TableLine2">
				<td nowrap>图片：</td>
				<td nowrap>
					<input type="text" name="icon4" id="icon4" size="40" maxlength="100" class="BigInput"> 
				</td>
			</tr>
			<tr class="TableLine1">
				<td nowrap>路径:</td>
				<td nowrap><input type="text" name="menuCode4" id="menuCode4" size="60" readonly="readonly" class="BigInput" value="/system/core/workflow/workmanage/flowPriv/monitor.jsp?flowId=<%=flowId%>">
				</td>
			</tr>
		</table>
		
		
		<table class="TableBlock" width="80%" align="center" style="margin-top: 10px"> 
            
			<tr class="TableLine1">
				<td nowrap>子菜单编号：</td>
				<td nowrap><INPUT type=text name="menuId5" id="menuId5" size="10" maxlength="3" class="easyui-validatebox BigInput" required="true" validType ="numberstrLength[3]" >
				</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>子菜单名称：</td>
				<td nowrap><input type="text" name="menuName5" id="menuName5" maxlength="100" class="easyui-validatebox BigInput" required="true" value="工作销毁" >&nbsp;</td>
			</tr>
           <tr class="TableLine2">
				<td nowrap>图片：</td>
				<td nowrap>
					<input type="text" name="icon5" id="icon5" size="40" maxlength="100" class="BigInput"> 
				</td>
			</tr>
			<tr class="TableLine1">
				<td nowrap>路径:</td>
				<td nowrap><input type="text" name="menuCode5" id="menuCode5" size="60"  class="BigInput"  readonly="readonly" value="/system/core/workflow/workmanage/workdestroy/index.jsp?flowId=<%=flowId%>">
				</td>
			</tr>
		</table>
		
		
		
		<div style="width: 100%;padding-top: 10px" align="center">
		   <input type="button" value="保存"
					class="btn btn-primary" onclick="doSave();">&nbsp;&nbsp;
		<input type="button" value="关闭" class="btn btn-primary" onclick="closeBS()">&nbsp;&nbsp;
		</div>
		
				
   </form>
</body>
</html>