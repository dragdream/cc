<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
		String groupId = request.getParameter("groupId");
		String seqIds = request.getParameter("seqIds");
		if(groupId == null || "".equals(groupId)){
			groupId = "-1";
		}
		if(seqIds == null || "".equals(seqIds)){
			seqIds = "";
		}
		
		//快速查询
		String quickSearch = request.getParameter("quickSearch");
		String psnName = TeeStringUtil.getString(request.getParameter("psnName"), "");
		String sex = TeeStringUtil.getString(request.getParameter("sex"), "");
		String nickName = TeeStringUtil.getString(request.getParameter("nickName"), "");
		String birthday = TeeStringUtil.getString(request.getParameter("birthday"), "");
		String mobilNo = TeeStringUtil.getString(request.getParameter("mobilNo"), "");
		String notes = TeeStringUtil.getString(request.getParameter("notes"), "");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>通讯簿</title>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<%@ include file="/header/validator2.0.jsp"%>
	
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" charset="UTF-8" src="<%=contextPath%>/system/core/base/address/public/js/address.js"></script>
	<style>
#timesTips{
	color:#ffa800;
	font-weight:bold;
	margin-left:5px;
}
.time_info{
	line-height:30px;
	font-weight:bold;
}
.info img,.info span{
	vertical-align:middle;
}
table{
	border-collapse: collapse;
/*     border: 1px solid #f2f2f2; 
    width:100%;*/
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	/*font-weight:bold;*/
}
table tr:first-child{
	/* background-color: #f8f8f8;  */
}
</style>
	<script type="text/javascript" charset="UTF-8">
	
	
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	var seqIds = '<%=seqIds%>';
	var groupId = '<%=groupId%>';
	var quickSearch = "<%=quickSearch%>";
	var sex = "<%=sex%>";
	function queryAddress() {
		var para=tools.formToJson($("#form1"));
		//var para = {};
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/teeAddressController/getAddresesByGroupId.action?seqIds='+seqIds,
		//	toolbar : '#toolbar',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			queryParams:para,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'seqId',
			sortOrder: 'desc',
			striped: true,
			singleSelect:true,
			remoteSort: false,
			toolbar: '#toolbar',
			queryParams:para,
			columns : [ [ 
			 {field:'ck',checkbox:true,hidden:true},{
				title : 'id',
				field : 'sid',
				width : 200,
				hidden:true,
				sortable:true
				
			},{
				field : 'psnName',
				title : '姓名',
				width : 100,
				sortable : true
			},{
				field : 'sex',
				title : '性别',
				width : 60,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				
				if(rowData.sex == 0)
					return "男";
				else
					return "女";
				}
			}
			,{
				field : 'ministration',
				title : '职务',
				width : 100,
				sortable : true
			},{
				field : 'deptName',
				title : '单位名称',
				width : 220,
				sortable : true
			},{
				field : 'telNoDept',
				title : '工作电话',
				width : 120,
				sortable : true
			},{
				field : 'mobilNo',
				title : '手机',
				width : 150,
				sortable : true
			},{
				field : 'email',
				title : '邮箱',
				width : 200,
				sortable : true
			},{
				field : 'userId',
				title : '用户Id',
				width : 20,
				sortable : true,
				hidden:true
			},{field:'_manage',title:'操作',width : 300,formatter:function(value,rowData,rowIndex){
		
				var opts = "";
				if(rowData.userId && rowData.userId != ""){
					opts= '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="editeAddressPrivate('+rowData.sid+')"> <span style="color:#2285c6">编辑</span> </a>&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteAdd('+rowData.sid+')" > <span style="color:red">删除</span> </a';
				}
				return '<a href="javascript:void(0);" onclick="loadDetailAddress('+rowData.sid+')"> <span style="color:#2285c6">详情</span></a>'
				 + opts;
			}}
			] ]
		});
	
	};

	function doInit(){
		getAddressGroup();
		if(quickSearch!="null"){
			$("#sex").attr("value",sex);
			query();
		}
	}
    function getAddressGroup(){
    	var url = "<%=contextPath%>/teeAddressGroupController/getAddressGroups.action";
    	var para = {};
    	var jsonRs = tools.requestJsonRs(url,para);
    	if(jsonRs.rtState){
    		createSelect(jsonRs.rtData);
    	}else{
    		//alert(jsonRs.rtMsg);
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
    	}
    }

    function createSelect(rtData){
    	var selectObj = document.createElement("select");
    	selectObj.setAttribute("class", "BigSelect");
    	//$("<select name='groupId' ></select>");
    	selectObj.setAttribute("name", "groupId");
    	  var vOption=document.createElement("option");
    	     vOption.setAttribute("value","-1"); 
    	     vOption.appendChild(document.createTextNode("全部"));
    	     selectObj.appendChild(vOption);
    	$.each(rtData,function(i,v){
    	     var vOption=document.createElement("option");
    	     vOption.setAttribute("value",v.seqId);
    	     vOption.appendChild(document.createTextNode(v.groupName)); 
    	     selectObj.appendChild(vOption);
    	});
    	$("#groupSelect").append(selectObj);
    }

    /**
     * 条件查询
     */
    function query(){
    	if(checkForm()){
    		//var para =  tools.formToJson($("#form1")) ;
    		$("#searchDiv").hide();
    		$("#optItem").show();
    		queryAddress();
    		//$('#datagrid').datagrid('load', para);
    	}
    	
    }

    function checkForm(){
    	  return $("#form1").valid();
    }
	</script>
</head>
<body class="easyui-layout" fit="true" onload="doInit();">


<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		<!-- <div class="moduleHeader">
			<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;管理人才库</b>
		</div> -->
		
		<form id="form1" name="form1" method="post">
			<div id="searchDiv" class="time_info" >
				<div class="topbar clearfix" style="position:static;">
					<div class="easyui_h1" >
					    <b><i class="glyphicon glyphicon-sound-stereo"></i>查询（关键字）</b>
						<input type="reset" value="重置" class="btn-win-white fr" title="重置" style="margin-right:70px;">
						<input type="button" value="查询" class="btn-win-white fr" title="查询" onclick="query()" style="margin-right:10px;">
					</div>
				</div>
				
				<table class="none-table" id="queryContent" style="width:100%;">
				  <tr>
				    <td nowrap class="TableData" width="120" >分组：</td>
				    <td nowrap class="TableData" align="left" id="groupSelect">
				    </td>
				    <td nowrap class="TableData" width="120">姓名：</td>
				    <td nowrap class="TableData" align="left">
				        <input type='text' name="psnName" value="<%=psnName%>" class="easyui-validatebox BigInput" maxlength="50"  size="20" />
				    </td>
				   </tr>
				   <tr>
				    <td nowrap class="TableData" width="120">性别：</td>
				    <td nowrap class="TableData" align="left">
				       <select id="sex" name="sex" class="BigSelect">
				      			 <option value=""></option>
				       			<option value="0">男</option>
				       		    <option value="1">女</option>
				       </select>
				    </td>
				    <td nowrap class="TableData" width="120">生日：</td>
						<td nowrap class="TableData">
						<input type="text" name="birthday" value="<%=birthday%>" id="birthday" size="16" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput">
						</td>
				   </tr>
				   <tr>
						<td nowrap class="TableData" width="120">昵称：</td>
						<td nowrap class="TableData">
						<input type='text' name="nickName" value="<%=nickName%>" class="easyui-validatebox BigInput" maxlength="50"  size="20"  />
						</td>
						<td nowrap class="TableData" width="120">单位名称：</td>
						<td nowrap class="TableData">
						<input type='text' name="deptName" class="easyui-validatebox BigInput" maxlength="50"  size="20"  />
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData" width="120">工作电话：</td>
						<td nowrap class="TableData">
						<input type='text' name="telNoDept" class="easyui-validatebox BigInput" maxlength="50"  size="20" />
						</td>
						<td nowrap class="TableData" width="120">单位地址：</td>
						<td nowrap class="TableData">
						<input type='text' name="addDept" class="easyui-validatebox BigInput" maxlength="100"  size="45" />
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData" width="120">家庭电话：</td>
						<td nowrap class="TableData">
						<input type='text' name="telNoHome" class="easyui-validatebox BigInput" maxlength="50"  size="20" />
						</td>
						<td nowrap class="TableData" width="120">家庭住址：</td>
						<td nowrap class="TableData">
						<input type='text' name="addHome" class="easyui-validatebox BigInput" maxlength="50"  size="50" />
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData" width="120">手机：</td>
						<td nowrap class="TableData">
						<input type='text' name="mobilNo" value="<%=mobilNo%>" class="easyui-validatebox BigInput" maxlength="50"  size="20"/>
						</td>
						<td nowrap class="TableData" width="120">备注：</td>
						<td nowrap class="TableData">
						<input type='text' name="notes" value="<%=notes%>" class="easyui-validatebox BigInput" maxlength="50"  size="20" />
						</td>
					</tr>
				</table>
			</div>
		
		
		</form>
		<div class="base_layout_top " id="optItem" style="position:static;display:none;padding:10px 0;box-sizing:border-box;">
			<div class="easyui_h1" >
				&nbsp;<button class="btn-win-white" onclick="window.location.reload();">返回</button>
			</div>
		</div>
	</div>
</body>
</html>