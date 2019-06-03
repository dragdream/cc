<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeCrmContactUserController/datagrid.action',
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				if(rowData.addPersonId==loginPersonId){
					return "<a href='#' onclick='edit("+rowData.sid+")'>编辑</a>&nbsp;&nbsp;<a href='#' onclick='showDetail("+rowData.sid+")'>查看</a>";
				}else{
					return "<a href='#' onclick='showDetail("+rowData.sid+")'>查看</a>";
				}
			}},
			{field:'customerName',title:'客户名称',width:100},
			{field:'name',title:'姓名',width:100},
			{field:'department',title:'部门',width:100},
			{field:'mobilePhone',title:'移动电话',width:100},
			{field:'importantDesc',title:'重要程度',width:100},
			{field:'email',title:'邮箱',width:100},
			{field:'addPersonName',title:'创建人',width:100}
		]],
		onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
            if (data.rows.length > 0) {
                //循环判断操作为新增的不能选择
                for (var i = 0; i < data.rows.length; i++) {
                    //根据operate让某些行不可选
                    if (data.rows[i].addPersonId!=loginPersonId) {
                        $("input[type='checkbox']")[i + 1].disabled = true;
                        $("input[type='checkbox']")[i + 1].style.display = "none";
                    }
                }
            }
        },
        onClickRow: function(rowIndex, rowData){
            $("input[type='checkbox']").each(function(index, el){
                if (el.disabled == true) {
                	datagrid.datagrid('unselectRow', index - 1);
                }
            });
        },
        onSelectAll:function(rowIndex, rowData){
        	  $("input[type='checkbox']").each(function(index, el){
                  if (el.disabled == true) {
                  	datagrid.datagrid('unselectRow', index - 1);
                  	el.checked=false;
                  }
              });
        }
	});
}
function showDetail(sid){
	datagrid.datagrid("unselectAll");
	bsWindow(contextPath+"/system/subsys/crm/core/contactUser/detail.jsp?sid="+sid,"查看详情",{width:"700",height:"300",buttons:[{name:"关闭",classStyle:"btn btn-primary"}],submit:function(v,h){return true;}});
}
function add(){
	var url = contextPath+"/system/subsys/crm/core/contactUser/addOrEditContactUser.jsp";
	location.href=url;
}

function edit(sid){
	datagrid.datagrid("unselectAll");
	var url = contextPath+"/system/subsys/crm/core/contactUser/addOrEditContactUser.jsp?sid="+sid;
	location.href=url;
}

function del(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		$.jBox.tip("请选择需要删除项目","info");
		return;
	}
	$.jBox.confirm("确认删除选中信息吗","确认",function(v){
		if(v=="ok"){
			var sids="";
			for(var i=0;i<selections.length;i++){
			   sids += selections[i].sid+",";
			}
			var url = contextPath+"/TeeCrmContactUserController/deleteById.action";
			var json = tools.requestJsonRs(url,{sids:sids});
			if(json.rtState){
				$.jBox.tip(json.rtMsg,"success");
			}else{
				$.jBox.tip("删除失败！","error");
			}
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
		}
	});
}
//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
	  $('#searchDiv').modal('hide');
}

function exportContactUser(){
	var url = contextPath+"/TeeCrmContactUserController/exportContactUser.action";
	document.form1.action=url;
	document.form1.submit();
	datagrid.datagrid("reload");
	$('#searchDiv').modal('hide');
	return true;
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div style="text-align:left;margin-bottom:10px;margin-top: 10px;">
			<button class="btn btn-primary" onclick="add()">添加</button>
			<button class="btn btn-danger" onclick="del()">删除</button>
			<button class="btn btn-primary" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
		</div>
	<form id="form1" name="form1">
		<div class="modal fade" id="searchDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="myModalLabel">查询条件</h4>
		      </div>
		      <div class="modal-body">
		        <table class="SearchTable" style="text-align:left;">
								<tr>
									<td class="SearchTableTitle">所属客户：</td>
									<td colspan='3'>
										<input class="BigInput" type="text" id = "customerName" name='customerName' style="width:180px;"/>
										<input class="BigInput" type="hidden" id = "customerId" name='customerId' style="width:180px;"/>
										<a href="javascript:void(0);" class="orgAdd" onClick="selectCustomerInfo(['customerId','customerName'])">选择客户</a></a>&nbsp;&nbsp;
										<a href="javascript:void(0);" class="orgClear" onClick="clearData('customerId','customerName')">清空</a>
									</td>
								</tr>
								<tr>
									<td class="SearchTableTitle">姓名：</td>
									<td>
										<input type='text' class="BigInput" id='name' name='name' style="width:180px;">
									</td>
									<td class="SearchTableTitle">所属部门：</td>
									<td>
										<input type='text' class="BigInput" id='department' name='department' style="width:180px;"/>
									</td>
								</tr>
								<tr>
									<td class="SearchTableTitle">公司电话：</td>
									<td>
										<input type='text' class="BigInput" id='telephone' name='telephone' style="width:180px;"/>
									</td>
									<td class="SearchTableTitle"> 移动电话：</td>
									<td>
										<input type='text' class="BigInput" id='mobilePhone' name='mobilePhone' style="width:180px;"/>
									</td>
								</tr>
								<tr>
									<td colspan='4' align='right'>
										<input type="reset" class="btn btn-primary" value="清空" onClick="clearData('customerId','customerName');">
										<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
										<input type="button" class="btn btn-primary" onclick="exportContactUser();" value="导出">
									</td>
								</tr>
							</table>
		      </div>
		    </div>
		  </div>
		</div>
		</form>
	</div>
</body>
</html>