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
		url:contextPath+'/officeDepositoryController/datagrid.action',
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		nowrap:false,
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'deposName',title:'用品库',width:100},
			{field:'deptsNames',title:'所属部门',width:100},
			{field:'adminsNames',title:'仓库管理员',width:200},
			{field:'operatorsNames',title:'物品调度员',width:200},
			{field:"ctr",title:"操作",width:200,formatter:function(value,rowData,rowIndex){
				
				return "<a href='javascript:void(0);' onclick='addCategory("+rowData.sid+")'>添加用品类别</a>";
			}}
		]]
	});
	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});
}

function addDepository(){
	/* bsWindow(contextPath+"/system/core/base/officeProducts/manage/add_depository.jsp","添加用品库",{width:"800",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;
		}
	}}); */
	
	var url = contextPath+"/system/core/base/officeProducts/manage/add_depository.jsp";
	var title = "添加用品库";
	bsWindow(url,title,{width:"600",height:"450",buttons:
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

function addCategory(depositoryId){
	/* bsWindow(contextPath+"/system/core/base/officeProducts/manage/add_category.jsp?depositoryId="+depositoryId,"添加用品分类",{width:"400",height:"200",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;
		}
	}}); */
	
	var url = contextPath+"/system/core/base/officeProducts/manage/add_category.jsp?depositoryId="+depositoryId;
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
// 		top.$.jBox.tip("请选择一个用品库","info");
        $.MsgBox.Alert_auto("请选择一个用品库");
		return;
	}
	/* bsWindow(contextPath+"/system/core/base/officeProducts/manage/edit_depository.jsp?sid="+selection.sid,"修改用品库",{width:"800",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;
		}
	}}); */
	var url = contextPath+"/system/core/base/officeProducts/manage/edit_depository.jsp?sid="+selection.sid;
	var title = "修改用品库";
	bsWindow(url,title,{width:"600",height:"450",buttons:
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
// 		top.$.jBox.tip("请选择一个用品库","info");
        $.MsgBox.Alert_auto("请选择一个用品库");
		return;
	}
	var sid = selection.sid;
	$.MsgBox.Confirm("提示","确认删除该用品库吗？<br/>注：如果删除则会清空该库的所有数据信息",function(v){
// 		if(v=="ok"){
			var url = contextPath+"/officeDepositoryController/delDepository.action";
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
		$('#datagrid').datagrid('load',{url:contextPath+'/officeDepositoryController/datagrid.action'});
	}else if(showFlag==2){
		window.location.href = contextPath+"/system/core/base/officeProducts/manage/settings_category.jsp";
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
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" style="">
		<div class="clearfix">
<!-- 			<span class="easyui_h1"><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;用品库管理</span> -->
		     <img class = 'tit_img' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/ypgl/icon_类别设置.png">
			 <p class="title">用品库管理</p>
			 <ul id = 'tab' class = 'tab clearfix'  style='clear:both;'>
				<li id="option1" class='select' onclick='query(1)'>用品库设置</li>
				<li id="option2" onclick='query(2)'>用品类别设置</li>
			</ul>
			<button class="btn-del-red fr" onclick="del()" style="margin-right:30px;margin-top:-30px;">删除</button>
		    <button class="btn-win-white fr" onclick="edit()" style="margin-right:75px;margin-top: -30px;">修改库信息</button>
			<button class="btn-win-white fr" onclick="addDepository()" style="margin-right:165px;margin-top: -30px;">添加用品库</button>
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