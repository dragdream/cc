<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<title>视图管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/bisView/listBisView.action',
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'identity',title:'视图标识',width:100,sortable : true},
			{field:'name',title:'视图名称',width:100,sortable : true},
			{field:'type',title:'视图类型',width:100,sortable : true,formatter:function(e,rowData){
				var str = "";
				if(e ==1){
					str = "设计模式";
				}else{
					str = "SQL开发模式";
				}
				return str;
			}},
			{field:'_manage',title:'管理',width:100,formatter:function(e,rowData){
				var render = [];
				render.push("<a href='#' onclick=\"edit('"+rowData.identity+"',"+rowData.type+")\">编辑</a>");
				render.push("<a href='#' onclick=\"exportXml('"+rowData.identity+"')\">导出</a>");
				render.push("<a href='javascript:void(0)' onclick=\"del('"+rowData.identity+"')\">删除</a>");
				render.push("<a href='javascript:void(0)' onclick=\"preview('"+rowData.identity+"')\">预览</a>");
				return render.join("&nbsp;&nbsp;");
			}}
		]]
	});
	
	
}
//预览
function preview(identity){
	var url=contextPath+"/system/subsys/bisengine/view/preview.jsp?identity="+identity;
	bsWindow(url ,"视图预览",{width:"600",height:"300",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="关闭"){
			return true;
		}
	}}); 

}
function add(){
	var url=contextPath+"/system/subsys/bisengine/view/addView.jsp";
	bsWindow(url ,"创建视图",{width:"600",height:"180",buttons:
		[
         {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
		    cw.add();
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

function edit(identity,type){
	if(type==1){
		window.location = "view_mgr_senior.jsp?identity="+identity;
	}else{
		window.location = "view_mgr.jsp?identity="+identity;
	}
}

function del(identity){
	$.MsgBox.Confirm ("提示", "是否删除该视图？", function(){
		var json = tools.requestJsonRs(contextPath+"/bisView/delBisView.action",{identity:identity});
		if(json.rtState){
			$.MsgBox.Alert_auto("删除成功！",function(){
				datagrid.datagrid("reload");
			});
			
		} 
	  });
}

function exportXml(identity){
	$("#frame0").attr("src",contextPath+"/bisView/export.action?identity="+identity);
}

function doImport(obj){
	if(document.getElementById("file").value.indexOf(".xml")==-1){
		$.MsgBox.Alert_auto("仅能上传xml后缀名模板文件！");
		return false;
	}
	$("#uploadBtn").attr("value","上传中").attr("disabled","");
	return true;
}

function uploadSuccess(){
	$.MsgBox.Alert_auto("导入成功！",function(){
		window.location.reload();
	});
	
}

//根据条件查询
function doSearch(){
	var queryParams=tools.formToJson($("#form1"));
	//alert(queryParams.identity);
	datagrid.datagrid('options').queryParams=queryParams; 
	datagrid.datagrid("reload");
	//隐藏高级查询form表单
	$(".searchCancel").click();
}

//导入
function upload(){
	var url=contextPath+"/system/subsys/bisengine/view/uploadView.jsp";
	bsWindow(url ,"视图导入",{width:"600",height:"140",buttons:
		[
         {name:"上传",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="上传"){
		    cw.upload();
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

//重置form表单
function  resetForm(){
	document.getElementById("form1").reset(); 
}
</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left:10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
		<div class="fl left clearfix" style="position:static">
			<span><b>视图管理</b></span>
		</div>
		<div class="fr right clearfix">
			<input type="button" value="创建视图" onclick="add();" class="btn-win-white"/>&nbsp;
			<input id="importBtn" type="button" class="btn-win-white" value="导入" onclick="upload()"/>
			<button type="button" onclick="" class="advancedSearch btn-win-white">高级查询</button>	
		</div>
		
	<form id="form1" name="form1" class='ad_sea_Content'>
		    <table class="TableBlock" width="100%"  style="font-size:12px" id="searchTable">
          <tr>
             <td width="10%">视图标识：：</td>
             <td width="40%">
                <input class="BigInput" type="text" id = "identity1" name='identity1' size="20"/>
             </td>
             <td width="10%">视图名称：</td>
             <td width="40%">
                <input class="BigInput" type="text" id = "viewName2" name='viewName2' size="20"/>
             </td>
          </tr>
          <tr>
             <td>视图类型：</td>
             <td>
                <select class="BigSelect" id="type1" name="type1">
					<option value="">请选择</option>
					<option value="1">设计模式</option>
					<option value="2">SQL开发模式</option>
				</select>
             </td>
             <td></td>
             <td></td>
          </tr>
         
          
       </table>
       <div class='btn_search'>
			<input type='button' class='btn-win-white' value='查询' onclick="doSearch()">&nbsp;&nbsp;&nbsp;
			<input type='button' class='btn-win-white' value='重置' onclick='resetForm()'>&nbsp;&nbsp;&nbsp;
			<input type='button' class='btn-win-white searchCancel' value='取消'>
	   </div>
	</form>
</div>





<iframe style="display:none" id="frame0" name="frame0"></iframe>
</body>
<script>

var btn_top = $(".advancedSearch").offset().top;
var brn_height = $(".advancedSearch").outerHeight();
$(".ad_sea_Content").css('top',(btn_top + brn_height));
$(".advancedSearch").click(function(){
	$(".ad_sea_Content").slideToggle(200);
	if($(this).hasClass("searchOpen")){//显示前
	$(".serch_zhezhao").remove();
	$(this).removeClass("searchOpen");
	$(this).css({"border":"1px solid #0d93f6",});
	$(this).css('border-bottom','1px solid #0d93f6');
	}else{
	$(this).addClass("searchOpen");//显示时
	$(this).css({"border":"1px solid #dadada",'border-bottom':'1px solid #fff'});
	$('body').append('<div class="serch_zhezhao"></div>');
}
var _offsetTop = $("#form1").offset().top;
$(".serch_zhezhao").css("top",_offsetTop)
});
$(".searchCancel").click(function(){
	$(".advancedSearch").removeClass("searchOpen");
$("#form1").slideUp(200);
$(".serch_zhezhao").remove();
});

</script>
</html>
