<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
body{
	font-size:12px;
}
.c{
	width:150px;
	float:left;
	margin-left:5px;
	margin-right:5px;
	padding:5px;
}
.cc{
	margin-left:10px;
	padding:2px;
}
td{
	border:1px solid #66ccff;
	background:#f0f0f0;
}
</style>
<script>
var uuid = "<%=request.getParameter("uuid") == null ? "" : request.getParameter("uuid")%>";
function doInit(){
	$.MsgBox.Loading();
	var width = 0;
	var json = tools.requestJsonRs(contextPath+"/teeMenuGroup/getMenuGroupPriv.action?uuid="+uuid);
	//遍历出所有的一级菜单
	var list = json.rtData;
	for(var i=0;i<list.length;i++){
		var item = list[i];
		if(item.id.length==3){
			$("<td class='c' nowrap ><input type='checkbox' "+(item.checked?"checked":"")+" id='"+item.id+"' onclick=\"click0(this)\"/><b style='font-size:14px'>"+item.name+"</b><div id='pc"+item.id+"' class='cc'></div></td>").appendTo($("#tr"));
			width+=190;
		}else if(item.id.length==6){
			var parentId = item.id.substring(0,3);
			$("<div class='cc'><input type='checkbox' "+(item.checked?"checked":"")+" id='"+item.id+"' lv1='"+parentId+"'  onclick=\"click0(this)\"/>"+item.name+"<div id='pc"+item.id+"' class='cc'></div></div>").appendTo($("#pc"+parentId));
		}else if(item.id.length==9){
			var parentId = item.id.substring(0,6);
			var lv1 = item.id.substring(0,3);
			$("<div class='cc'><input type='checkbox' "+(item.checked?"checked":"")+" id='"+item.id+"' lv1='"+lv1+"' lv2='"+parentId+"' onclick=\"click0(this)\"/>"+item.name+"<div id='pc"+item.id+"' class='cc'></div></div>").appendTo($("#pc"+parentId));
		}
	}
	$("#tr").css({width:width});
	$.MsgBox.CloseLoading();
}


function click0(obj){
	var id = obj.getAttribute("id");
	var isChecked = obj.checked;
	if(isChecked){
		if(id.length==3){
			$("[lv1="+id+"]").prop("checked",true);
		}else if(id.length==6){
			var lv1 = id.substring(0,3);
			$("[lv2="+id+"]").prop("checked",true);
			$("#"+lv1).prop("checked",true);
		}else if(id.length==9){
			var lv1 = id.substring(0,3);
			var lv2 = id.substring(0,6);
			$("#"+lv1).prop("checked",true);
			$("#"+lv2).prop("checked",true);
		}
	}else{
		if(id.length==3){
			$("[lv1="+id+"]").prop("checked",false);
		}else if(id.length==6){
			var lv1 = id.substring(0,3);
			$("[lv2="+id+"]").prop("checked",false);
			if($("[lv1="+lv1+"]:checked").length==0){
				$("#"+lv1).prop("checked",false);
			}
		}else if(id.length==9){
			var lv1 = id.substring(0,3);
			var lv2 = id.substring(0,6);
			if($("[lv2="+lv2+"]:checked").length==0){
				$("#"+lv2).prop("checked",false);
			}
			if($("[lv1="+lv1+"]:checked").length==0){
				$("#"+lv1).prop("checked",false);
			}
		}
	}
}

/**
 * 保存
 */
function doSubmit(){
	var menuUuids = [];
	$("input:checked").each(function(i,obj){
		menuUuids.push(obj.getAttribute("id"));
	});
	var url = contextPath+"/teeMenuGroup/saveMenuGroupPriv.action";
	var jsonObj = tools.requestJsonRs(url,{uuid:uuid,menuIds:menuUuids.join(",")});
	if(jsonObj.rtState){
		location='manageGroup.jsp';
	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}

</script>

</head>
<body onload="doInit()" style="overflow:hidden">
<div id="toolbar" class="topbar clearfix" style="position:absolute;top:0px;left:0px;right:0px;height:20px">
   <div class="fl">
   	&nbsp;&nbsp;<button type="button" class="btn-win-white"  onclick="doSubmit();">保存</button>&nbsp;&nbsp;<button type="button" class="btn-win-white" onclick="location='manageGroup.jsp'">返回</button>
   	&nbsp;&nbsp;&nbsp;&nbsp;
   </div>
</div>
<div style="position:absolute;top:60px;left:0px;right:0px;bottom:0px;overflow:auto">
	<div id="tr">
		
	</div>
</div>
</body>
</html>