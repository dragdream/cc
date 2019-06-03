<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String deptId = request.getParameter("deptId");
	String deptName = request.getParameter("deptName");
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>
<script>
var datagrid;
var deptId = "<%=deptId%>";
var deptName = "<%=deptName%>";
function doInit(){
	getDeptParent();
	getHrCodeByParentCodeNo("PM_STATUS_TYPE","statusType");
	getHrCodeByParentCodeNo("PM_HOUSEHOLD","household");//户口类型
	getHrCodeByParentCodeNo("PM_MARRIAGE","marriage");
	getHrCodeByParentCodeNo("PM_POLITICS","politics");
	getHrCodeByParentCodeNo("PM_EMPLOYEE_TYPE","employeeType");
	query();
}	
function query(params){
	
	var columns = [
	   		{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'personName',title:'员工姓名',width:100,formatter:function(e,rowData,index){
				return "<a href='#' onclick=\"showDetail("+rowData.sid+",'"+rowData.personName+"')\">"+rowData.personName+"</a>";
			}},
			{field:'userName',title:'系统用户',width:120,formatter:function(e,rowData){
				return rowData.userName;
			}},
			{field:'gender',title:'性别',width:50},
			{field:'deptName',title:'所属部门',width:100,align:'center',formatter:function(e,rowData){
				return rowData.deptName;
			}},
			{field:'statusTypeDesc',title:'状态',width:100,align:'center'},
			{field:'mobileNo',title:'手机号码',width:100,align:'center'},
			{field:'email',title:'电子邮箱',width:100,align:'center'},
			{field:'qqNo',title:'qq号码',width:100,align:'center'},
			{field:'joinDateDesc',title:'入职时间',width:100,align:'center'}
	   		];
	
			//获取自定义字段在列表中显示的值
		    var url1=contextPath+"/humanDocController/getShowFieldListById.action";
		    var json1=tools.requestJsonRs(url1,null);
		    if(json1.rtState){
		  	  var data1=json1.rtData;
		  	  if(data1.length>0){
		  		  for(var i=0;i<data1.length;i++){
		  			  columns.push({
								field:"EXTRA_"+data1[i].sid,
								title:data1[i].extendFiledName});
		  		  }
		  	  }     	  
		    }
		    
		    columns.push({field:'2',title:'操作',align: 'center',width:150,formatter:function(value,rowData,rowIndex){
		    	return "<a href='#' onclick=\"openDeailInfo("+rowData.sid+",'"+rowData.personName+"')\">完善</a>&nbsp;&nbsp;<a href='#' onclick='edit("+rowData.sid+")'>编辑</a>&nbsp;&nbsp;<a href='#' onclick='del("+rowData.sid+")'>删除</a>";
			}});
		    
		    
			datagrid = $('#datagrid').datagrid({
				url:contextPath+"/humanDocController/datagrid2.action?deptId="+deptId,
		  		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		  		pagination:true,
		  		singleSelect:false,
		  		queryParams:params,
		  		toolbar:'#toolbar',//工具条对象
		  		checkbox:false,
		  		border:false,
		  		idField:'formId',//主键列
		  		fitColumns:true,//列是否进行自动宽度适应
		  		columns:[columns]
		  	});

}

//完善个人信息
function openDeailInfo(sid,name){
	datagrid.datagrid("unselectAll");
	var url = contextPath + "/system/core/base/pm/archivesManage/detail_index.jsp?sid="+sid+"&personName="+encodeURI(name);
	openFullWindow(url);
}

//详情
function showDetail(sid,name){
	datagrid.datagrid("unselectAll");
	var url = contextPath + "/system/core/base/pm/archivesManage/basic_person.jsp?sid="+sid+"&personName="+encodeURI(name);
	openFullWindow(url,"查看详情");
}


function edit(sid){
	datagrid.datagrid("unselectAll");
	var url = contextPath+"/system/core/base/pm/archivesManage/edit_person.jsp?deptName="+deptName+"&deptId="+deptId+"&sid="+sid;
	openFullWindow(url,"修改员工信息");
}

function del(sid){
	/* var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		$.jBox.tip("请选择需要删除项目","info");
		return;
	} */
	 $.MsgBox.Confirm ("提示", "是否删除选中信息嘛？删除后不可恢复！",function(){
		//if(v=="ok"){
		/* 	for(var i=0;i<selections.length;i++){
				var sid = selections[i].sid; */
				var url = contextPath+"/humanDocController/delHumanDoc.action";
				var json = tools.requestJsonRs(url,{sid:sid});
				if(json.rtState){
					$.MsgBox.Alert_auto(json.rtMsg);
					datagrid.datagrid("unselectAll");
					datagrid.datagrid("reload");
					return true;				
					}
					$.MsgBox.Alert_auto(json.rtMsg);
			//}
		//}
	});
}

/**
 * 获取部门
 */
function getDeptParent(){
	var url =  "<%=contextPath %>/deptManager/getDeptTreeAll.action";
		var config = {
				zTreeId:"deptIdZTree",
				requestURL:url,
	           	onClickFunc:onclickDept,
				async:false
			};
		zTreeObj = ZTreeTool.config(config);
		setTimeout('setDeptParentSelct()',500);
} 
/**
 * 初始化后选中节点,上级部门
 */
function setDeptParentSelct(){
	ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
    if(ZTreeObj == null){
    	setTimeout('setDeptParentSelct()',500);
    }else{
    	ZTreeObj.expandAll(true);
    	 var node = ZTreeObj.getNodeByParam("id",$("#deptId").val(),null);
    	    if(node){
    	    	ZTreeObj.selectNode(node);
    	  }
    }  
    ZTreeTool.inputBindZtree(ZTreeTool.zTreeId,'deptId','');
}

//点击树执行事件
function onclickDept (event, treeId, treeNode) {
	$("#deptIdName").val(treeNode.name);
	$("#deptId").val(treeNode.id);
	ZTreeTool.hideZtreeMenu();
}

//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
	  $('#searchDiv').modal('hide');
}



function importPersonInfo(){
	bsWindow(contextPath+"/system/core/base/pm/manage/import.jsp","批量导入人员信息",{width:"500",height:"80",submit:function(v,h){
		var cw = h[0].contentWindow;
		cw.commit(function(json){
			if(json.rtState){
				$.jBox.tip("添加成功","success");
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
				window.BSWINDOW.modal("hide");
				return true;
			}
		});
	}});
}

function exportHumanDoc(){
	var url =contextPath+"/humanDocController/exportHumanDoc.action";
    document.form1.action=url;
    document.form1.submit();
    return true;
}

function queryContract(){
	var url = contextPath+"/system/core/base/pm/public/contract.jsp";
	location.href = url;
}

function except(){
	var selections = datagrid.datagrid("getSelections");
	if (selections == null || selections == undefined
			|| selections.length <= 0) {
		if(confirm("确定全部导出吗")){
			 $("#frame0").attr("src","<%=contextPath%>/humanDocController/exportExcel.action");
		}
			}
	else{
		   if(confirm("确定导出选中的吗")){
				var sids=[];
				for(var i = 0; i < selections.length; i++){
					sids.push(selections[i].sid);	
				}
			 $("#frame0").attr("src","<%=contextPath%>/humanDocController/exportExcel.action?ids="+sids);
		   }

	}
}
function insurance(){
	var selections = datagrid.datagrid("getSelections");
	var sids=[];
	if(selections == null || selections == undefined|| selections.length <= 0){
		
	}else{
		for(var i = 0; i < selections.length; i++){
			sids.push(selections[i].sid);	
		}
	}
	var url = contextPath+"/system/core/base/pm/archivesManage/insurances.jsp?ids="+sids;
	var title = "设置保险账套";
	bsWindow(url,title,{width:"500",height:"150",buttons:
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
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div style="text-align:left;margin:5px 0px;">
	         <button class="advancedSearch btn-win-white"  onclick="insurance();">批量设置保险系数</button>
	         <button class="advancedSearch btn-win-white"  onclick="except();">导出</button>&nbsp;&nbsp;
	
	        <!-- 
	        <br/>
			 <button class="btn btn-primary" onclick="add()">添加员工</button>
			<button  onclick="exportHumanDoc()">导出</button>
			 <button class="btn btn-primary" onclick="importPersonInfo()">导入</button>
			<button class="btn btn-danger" onclick="del()">批量删除</button>
			<button class="btn btn-primary" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
			<button class="btn btn-danger" onclick="queryContract()">到期合同</button>
		 -->
	</div>
	</div>
	<iframe id="frame0" style="display:none"></iframe>
</body>
</html>