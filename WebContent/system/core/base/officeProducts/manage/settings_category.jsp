<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/officeCategoryController/datagrid.action',
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'catName',title:'类名称',width:100},
			{field:'officeDepositoryDesc',title:'所属库',width:100},
			{field:'createUserDesc',title:'创建人',width:100}
		]]
	});
	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});
}

function add(){
	/* bsWindow(contextPath+"/system/core/base/officeProducts/manage/add_category.jsp","添加用品分类",{width:"400",height:"200",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;
		}
	}}); */
	var url = contextPath+"/system/core/base/officeProducts/manage/add_category.jsp";
	var title = "添加用品分类";
	bsWindow(url,title,{width:"350",height:"150",buttons:
		 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v == "确定"){
			if(cw.commit()){
				d.remove();
				$('#datagrid').datagrid('unselectAll');
				$('#datagrid').datagrid('reload');
			};
		}else if(v=="关闭"){
			return true;
		}
	}});
}

function edit(){
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
// 		top.$.jBox.tip("请选择一个用品类","info");
        $.MsgBox.Alert_auto("请选择一个用品类");
		return;
	}
	var sid = selection.sid;
	/* bsWindow( contextPath+"/system/core/base/officeProducts/manage/edit_category.jsp?sid="+sid,"修改用品分类",{width:"400",height:"200",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;
		}
	}}); */
	var url = contextPath+"/system/core/base/officeProducts/manage/edit_category.jsp?sid="+sid;
	var title = "修改用品分类";
	bsWindow(url,title,{width:"350",height:"150",buttons:
		 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v == "确定"){
			if(cw.commit()){
				d.remove();
				$('#datagrid').datagrid('unselectAll');
				$('#datagrid').datagrid('reload');
			};
		}else if(v=="关闭"){
			return true;
		}
	}});
}

function del(){
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
// 		top.$.jBox.tip("请选择一个用品类","info");
		$.MsgBox.Alert_auto("请选择一个用品类");
		return;
	}
	var sid = selection.sid;
	$.MsgBox.Confirm("提示","确认删除该用品类吗？<br/>注：如果删除则会清空该类下的所有数据信息",function(v){
// 		if(v=="ok"){
			var url = contextPath+"/officeCategoryController/delCategory.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
// 				top.$.jBox.tip(json.rtMsg,"success");
				$.MsgBox.Alert_auto(json.rtMsg);
				window.location.reload();
			}else{
// 				top.$.jBox.tip(json.rtMsg,"error");
				$.MsgBox.Alert_auto(json.rtMsg);
			}
// 		}
	});
}
function query(showFlag){
// 	window.showFlag = showFlag;
	if(showFlag==1){
		window.location.href = contextPath+"/system/core/base/officeProducts/manage/settings_depository.jsp";
	}else if(showFlag==2){
		$('#datagrid').datagrid('load',{url:contextPath+'/officeDepositoryController/datagrid.action'});
	}
	
} 

</script>
<style>
	.tab li{
		float: left;
	    margin-right: -35px;
        line-height: 25px;
	    margin-top: -21px;
	    margin-left: 50px;
	    cursor: pointer;
	}
	.datagrid-header-check input
	{
	   vertical-align:top; 
	}
</style>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-right: 10px;padding-left: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<!-- <div class="base_layout_top" style="position:static">
			<span class="easyui_h1">用品类别管理</span>
		</div> -->
		<div class="titlebar clearfix">
<!-- 			<span class="easyui_h1"><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;用品库管理</span> -->
		     <img class = 'tit_img' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/ypgl/icon_类别设置.png">
			 <p class="title">用品类别管理</p>
		
		<ul id = 'tab' class = 'tab clearfix' style='clear:both;'>
				<li id="option1" onclick='query(1)'>用品库设置</li>
				<li id="option2" class='select' onclick='query(2)'>用品类别设置</li>
		     </ul>
		    <button class="btn-del-red fr" onclick="del()" style="margin-right:30px;margin-top:-30px;">删除类别</button>
			<button class="btn-win-white fr" onclick="edit()" style="margin-right:105px;margin-top: -30px;">编辑类别</button>
			<button class="btn-win-white fr" onclick="add()" style="margin-right:185px;margin-top: -30px;">添加类别</button>
		</div>
		<span class="basic_border_grey fl"></span>
	</div>
	<script>
       $(".tab li").click(function(){
		   $('.tab li').removeClass('select');
		   $(this).addClass('select');
    	});
</script>
</body>
</html>