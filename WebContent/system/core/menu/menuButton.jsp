<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
	String uuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
	String menuId = request.getParameter("menuId") == null ? "" : request.getParameter("menuId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color:#f2f2f2;">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<title>菜单功能</title>


<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<style>
.BigInput{
  height:20px;
  width: 200px;
}
</style>


<script type="text/javascript">

var uuid = '<%=uuid%>';
var menuId = "<%=menuId%>";//菜单编号

function doInit(){

	if(uuid != ""){
		var url = "<%=contextPath %>/sysMenu/getSysMenuById.action";
		var para = {uuid:uuid,menuId:menuId};
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj){
			var json = jsonObj.rtData;
			if(json.uuid){
				
				bindJsonObj2Cntrl(json);
				var childMenuId = json.menuId.substring(json.menuId.length-3, json.menuId.length); 
				$("#menuId").val(childMenuId);
			}
			
		}else{
			$.MsgBox.Alert_auto(jsonObj.rtMsg);
		}
	}
	
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/menuButton/getButtonListByMenuId.action?menuId=' + uuid ,
		singleSelect:true,
		//toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'id',title:'ID',width:20,hidden:true},
			{field:'sortNo',title:'顺序号',width:50},
			{field:'buttonNo',title:'按钮编号',width:100},
			{field:'buttonName',title:'按钮名称',width:100},
			{field:'buttonProp',title:'按钮类型',width:100,formatter:function(data){
				if(data==1){
					return "编辑类";
				}else{
					return "查询类";
				}
			}},
			{field:'buttonUrl',title:'访问地址',width:300},
			{field:'remark',title:'备注信息',width:300},
			{field:'_manage',title:'管理',width:100,formatter:function(e,rowData){
				var dataType=rowData.dataSource;
				var render = [];
				render.push("<a href='#' onclick='toAddUpdateButton("+rowData.id+")'>编辑</a>");
				render.push("<a href='javascript:void(0)' onclick='deleteById("+rowData.id+")'>删除</a>");				
				return render.join("&nbsp;&nbsp;&nbsp;");
			}}
		]]
	});		
}

/**
 * 新增或者更新
 */
function toAddUpdateButton(id){
	var title = "新建按钮";
	if(id > 0){
		 title = "编辑按钮";
	}
	
	var  url = contextPath + "/system/core/menu/addEditButton.jsp?menuId=" + uuid + "&id=" + id;

	bsWindow(url ,title,{width:"500",height:"220",buttons:
		[{name:"保存（Save）",classStyle:"btn-win-white"},
	 	 {name:"关闭",classStyle:"btn-win-white"}]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存（Save）"){
			cw.doSaveOrUpdate(function(json){
				window.location.reload();
			});
		}else if(v=="关闭"){
			return true;
		}
	}});
}

function deleteById(id){
	if(confirm("确定要删除此系统吗？删除后将不可恢复！")){
		var url = "<%=contextPath %>/menuButton/deleteById.action";
		var para =  {'id':id};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$('#datagrid').datagrid("reload");
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}


</script>

</head>
<body onload="doInit()" style="background-color:#f2f2f2;overflow-y:hidden; ">

    <div  style="margin-top: 10px">
        <span class="" style="font-size: 12px;font-weight: bold;">子菜单按钮编辑</span>
        <span class="basic_border"></span>
    </div>
	<form method="post" name="form1" id="form1">
		<input type="text" id="uuid" name="uuid" style="display:none;" value='0'/>
		<input type="text" class="BigInput" name="pMenuId" id="pMenuId" style="display:none;" value=""/>
		<table class="TableBlock" width="100%" align="center">	
            <tr class="TableLine1">
				<td nowrap style="text-indent: 10px">上级菜单：</td>
				<td>
					<input type="text" class="BigInput" readonly name="pMenuIdDesc" id="pMenuIdDesc" value="" class="BigInput" required/>&nbsp;	
				</td>
			</tr>
			<tr class="TableLine1">
				<td nowrap style="text-indent: 10px">子菜单编号：</td>
				<td nowrap><INPUT type=text name="menuId" id="menuId"  class="BigInput" required  onlyNumLength="3" />
				 <br>说明：此代码为三位，作为排序之用。在同一父菜单下的平级菜单，该代码不能重复 
				</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap style="text-indent: 10px">子菜单名称：</td>
				<td nowrap><input type="text" name="menuName" id="menuName"  class="BigInput" required value="" >&nbsp;</td>
			</tr>
            <tr class="TableLine2">
				<td nowrap style="text-indent: 10px">图片：</td>
				<td nowrap>
					<input type="text" name="icon" id="icon"  class="BigInput" style="width:400px"> 
			    </td>
			</tr>
			<tr class="TableLine1">
				<td nowrap style="text-indent: 10px">路径:</td>
				<td nowrap><input type="text" name="menuCode" id="menuCode"   class="BigInput" value="" style="width: 400px"/>
			</tr>
		</table>
		<div class="fr" style="margin-top: 10px;margin-bottom:10px">
		    <input type="button" value="新增按钮" class="btn-alert-blue" onclick="toAddUpdateButton(0);">&nbsp;&nbsp;
		    <input type="button" value="返回" class="btn-alert-blue" onclick="history.go(-1);">&nbsp;&nbsp;
		</div>
	</form>
	<table id="datagrid" fit="true"></table>
	
	
</body>
</html>