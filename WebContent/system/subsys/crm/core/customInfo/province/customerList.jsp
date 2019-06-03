<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String cityCode = request.getParameter("cityCode")==null?"":request.getParameter("cityCode");
	String cityName = request.getParameter("cityName")==null?"":request.getParameter("cityName");
	String type=request.getParameter("type")==null?"":request.getParameter("type");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var datagrid;
var cityCode="<%=cityCode%>";
var cityName="<%=cityName%>";
var type="<%=type%>";//type=2 代表所有客户
function doInit(){
	getCrmCodeByParentCodeNo("CUSTOMER_TYPE","customerType");
	getCrmCodeByParentCodeNo("CUSTOMER_SOURCE","customerSource");
	getCrmCodeByParentCodeNo("INDUSTRY_TYPE","industry");
	
	if(cityName!=""){
		$("#areaName").html(cityName);
	}
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeCrmCustomerInfoController/datagrid.action?cityCode='+cityCode+"&type="+type,
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
				if(type==2){
					return "<a href='#' onclick='showDetail("+rowData.sid+")'>查看</a>";
				}else{
					if(loginPersonId==rowData.managePersonId){
						return "<a href='#' onclick='edit("+rowData.sid+")'>编辑</a>&nbsp;&nbsp;<a href='#' onclick='addContactUser("+rowData.sid+")'>添加联系人</a>&nbsp;&nbsp;<a href='#' onclick='showDetail("+rowData.sid+")'>查看</a>";
					}else{
						return "<a href='#' onclick='addContactUser("+rowData.sid+")'>添加联系人</a>&nbsp;&nbsp;<a href='#' onclick='showDetail("+rowData.sid+")'>查看</a>";
					}
				}
			}},
			{field:'customerName',title:'客户名称',width:100},
			{field:'type',title:'客户性质',width:80,formatter:function(data){
				if(data==1){
					return "客户";
				}else{
					return "供应商";
				}
			}},
			{field:'unitTypeDesc',title:'单位性质',width:80},
			{field:'managePersonName',title:'负责人',width:100},
			{field:'companyPhone',title:'固定电话',width:100},
			{field:'companyMobile',title:'移动电话',width:100},
			{field:'customerTypeDesc',title:'客户类型',width:100},
			{field:'industryDesc',title:'所属行业',width:100},
			{field:'customerSourceDesc',title:'客户来源',width:100},
			{field:'addTimeDesc',title:'录入日期',width:100}
		]],
		onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
            if (data.rows.length > 0) {
                //循环判断操作为新增的不能选择
                for (var i = 0; i < data.rows.length; i++) {
                    //根据operate让某些行不可选
                    if (data.rows[i].managePersonId != loginPersonId && type!=2) {
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
	var url = contextPath+"/system/subsys/crm/core/customInfo/detail.jsp?sid="+sid+"&cityCode="+cityCode+"&type="+type;
	location.href=url;
}
function add(){
	var url = contextPath+"/system/subsys/crm/core/customInfo/addOrEditCustomer.jsp?cityCode="+cityCode;
	location.href=url;
}

function edit(sid){
	datagrid.datagrid("unselectAll");
	var url = contextPath+"/system/subsys/crm/core/customInfo/addOrEditCustomer.jsp?sid="+sid+"&cityCode="+cityCode;
	location.href=url;
}


function addContactUser(sid){
	datagrid.datagrid("unselectAll");
	var url = contextPath+"/system/subsys/crm/core/customInfo/addContactUser.jsp?customerSid="+sid;
	bsWindow(url,"联系人信息管理",{width:"700",height:"300",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;//返回true就是关闭窗口
		}
		return false;//返回false不关闭窗口
	}});
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
			var url = contextPath+"/TeeCrmCustomerInfoController/deleteById.action";
			var json = tools.requestJsonRs(url,{sids:sids});
			if(json.rtState){
				$.jBox.tip(json.rtMsg,"success");
			}else{
				$.jBox.tip(json.rtMsg,"error");
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
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div class="moduleHeader">
			<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;客户管理(<span id="areaName">所有地区</span>)</b>
		</div>
		<div style="text-align:left;margin-bottom:5px;">
			<%
			if(!type.equals("2"))
			{
			%>
			<button class="btn btn-primary" onclick="add()">添加</button>
			<button class="btn btn-danger" onclick="del()">删除</button>
			<%
			}
			%>
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
									<td class="SearchTableTitle">客户名称：</td>
									<td>
										<input class="BigInput" type="text" id = "customerName" name='customerName' style="width:180px;"/>
									</td>
									<td class="SearchTableTitle">客户类型：</td>
									<td>
										<select class="BigSelect" id='customerType' name='customerType' style="width:180px;">
											<option value='0'>全部</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="SearchTableTitle">时间范围：</td>
									<td colspan='3'>
										<input type="text" id='fromTime' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='fromTime' class="Wdate BigInput" />至
										<input type="text" id='toTime' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='toTime' class="Wdate BigInput" />
									</td>
								</tr>
								<tr>
									<td colspan='4' align='right'>
										<input type="reset" class="btn btn-primary" value="清空">
										<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
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