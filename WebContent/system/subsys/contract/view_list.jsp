<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title></title>
	<script type="text/javascript" charset="UTF-8">
	$(function() {
		//获取合同分类
		var json = tools.requestJsonRs(contextPath+"/contractCategory/getCategoryTreeByViewPriv.action");
		var render = ["<option value='0'>所有分类</option>"];
		for(var i=0;i<json.rtData.length;i++){
			render.push("<option value='"+json.rtData[i].id+"'>"+json.rtData[i].title+"</option>");
		}
		$("#catId").html(render.join(""));
		
		$('#datagrid').datagrid({
			url:contextPath+'/contract/datagridByView.action',
			pagination:true,
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			border:false,
			idField:'sid',//主键列
			fitColumns:false,
			striped: true,
			remoteSort: true,
			columns:[[
				{field:'_manage',title:'操作',formatter:function(value,rowData,rowIndex){
					var render = [];
					render.push("<a href='javascript:void(0)' onclick='detail("+rowData.sid+")'>查看详情</a>");
					render.push("<a href='javascript:void(0)' onclick='lookRun("+rowData.runId+")'>查看审批</a>");
					return render.join("&nbsp;&nbsp;");
				}},
				{field:'contractCode',title:'合同编号',width:100},
				{field:'contractName',title:'合同名称',width:200},
				{field:'contractSortName',title:'合同类型',width:200},
				{field:'deptName',title:'所在部门',width:200},
				{field:'startTime',title:'合同开始时间',width:200,formatter:function(data,row,index){
					var render = "";
					if(data!=0){
						render = getFormatDateStr(data,"yyyy年MM月dd日");
					}
					return render;
				}},
				{field:'endTime',title:'合同结束时间',width:200,formatter:function(data,row,index){
					var render = "";
					if(data!=0){
						render = getFormatDateStr(data,"yyyy年MM月dd日");
					}
					return render;
				}},
				{field:'partyACharger',title:'甲方负责人',width:200},
				{field:'partyBCharger',title:'乙方负责人',width:200},
				{field:'partyBUnit',title:'乙方单位',width:200},
				{field:'partyBContact',title:'乙方联系方式',width:200}
			]]
		});
	});
	
	function detail(sid){
		openFullWindow(contextPath+"/system/subsys/contract/detail.jsp?sid="+sid);
	}
	
	function lookRun(runId){
		openFullWindow(contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+runId+"&view=15");
	}
	
	function changeCat(val){
		$('#datagrid').datagrid("reload",{catId:val});
		$('#datagrid').datagrid("unselectAll");
	}
	
	function doSearch(){
		var para = tools.formToJson($("#form1"));
		para["catId"] = $("#catId").val();
		$('#datagrid').datagrid("reload",para);
		$('#datagrid').datagrid("unselectAll");
		$("#searchDiv").modal("hide");
	}
	</script>
</head>
<body style="overflow:hidden">
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<span class="easyui_h1">合同查看</span>
	</div>
	
	<div style="padding:10px">
		所属分类：<select class="BigSelect" id="catId" onchange="changeCat(this.value)"></select>
		&nbsp;
		<button class="btn btn-primary" onclick="$('#searchDiv').modal('show');">高级查询</button>
	</div>
	
</div>
<table id="datagrid" fit="true"></table>



<div class="modal fade" id="searchDiv" style="display:none;">
  <div class="modal-dialog"  style="width:600px">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">高级查询</h4>
      </div>
      <div class="modal-body" style="font-size:12px;">
        <form id="form1" name="form1" method="post">
			<table  style="width:100%;font-size:12px" >
					<tr>
						<td>
							合同名称：
						</td>
						<td>
							<input type="text"  id="contractName" name="contractName"  class="BigInput"/>
						</td>
						<td>
							业务员：
						</td>
						<td>
							<input type="text"  id="bisUser" name="bisUser"  class="BigInput"/>
						</td>
					</tr>
					<tr>
						<td>
							合同编号：
						</td>
						<td>
							<input type="text"  id="contractCode" name="contractCode"  class="BigInput"/>
							
						</td>
						<td>
							所在部门：
						</td>
						<td>
							<input type="hidden" id="deptId" name="deptId"/>
							<input type="text" readonly id="deptName" class="BigInput"/>
							<a href="javascript:void(0)" onclick="selectSingleDept(['deptId','deptName'])">选择</a>
							&nbsp;
							<a href="javascript:void(0)" onclick="clearData('deptId','deptName')">清空</a>
						</td>
					</tr>
					<tr>
						<td>
							甲方单位：
						</td>
						<td>
							<input type="text"  id="partyAUnit" name="partyAUnit"  class="BigInput"/>
						</td>
						<td>
							乙方单位：
						</td>
						<td>
							<input type="text" id="partyBUnit" name="partyBUnit" class="BigInput"/>
						</td>
					</tr>
					<tr>
						<td>
							甲方负责人：
						</td>
						<td>
							<input type="text"  id="partyACharger" name="partyACharger"  class="BigInput"/>
						</td>
						<td>
							乙方负责人：
						</td>
						<td>
							<input type="text" id="partyBCharger" name="partyBCharger" class="BigInput"/>
						</td>
					</tr>
					<tr>
						<td>
							开始时间：
						</td>
						<td>
							<input type="text"  id="startTime1" name="startTime1"  class="Wdate BigInput " onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td>
							至
						</td>
						<td>
							<input type="text" id="startTime2" name="startTime2"  class=" Wdate BigInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						</td>
					</tr>
					<tr>
						<td>
							结束时间：
						</td>
						<td>
							<input type="text"  id="endTime1" name="endTime1"  class="Wdate BigInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td>
							至
						</td>
						<td>
							<input type="text" id="endTime2" name="endTime2"  class="Wdate BigInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						</td>
					</tr>
					<tr>
						<td>
							签订时间：
						</td>
						<td>
							<input type="text"  id="verTime1" name="verTime1"  class="Wdate BigInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td>
							至
						</td>
						<td>
							<input type="text" id="verTime2" name="verTime2"  class="Wdate BigInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						</td>
					</tr>
			</table>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-default" onclick="$('#form1')[0].reset();">重置</button>
        <button type="button" class="btn btn-primary" onclick="doSearch();">查询</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>