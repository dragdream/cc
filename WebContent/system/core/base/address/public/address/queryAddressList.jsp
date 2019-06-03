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
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" charset="UTF-8" src="<%=contextPath%>/system/core/base/address/public/js/address.js"></script>
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
	function queryAddress(){
		var para=tools.formToJson($("#form1"));
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/teeAddressController/getAddresesByGroupId.action?isPub=1&seqIds='+seqIds,
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			toolbar : '#toolbar',
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
			 {field:'ck',checkbox:true , hidden:true},{
				title : 'id',
				field : 'sid',
				width : 200,
				hidden:true,
				sortable:true
				
			},{
				field : 'psnName',
				title : '姓名',
				width : 200,
				sortable : true
			},{
				field : 'sex',
				title : '性别',
				width : 200,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				
				if(rowData.sex == 0)
					return "男";
				else
					return "女";
				}
			},{
				field : 'deptName',
				title : '单位名称',
				width : 200,
				sortable : true
			},{
				field : 'telNoDept',
				title : '工作电话',
				width : 200,
				sortable : true
			},{
				field : 'mobilNo',
				title : '手机',
				width : 200,
				sortable : true
			},{
				field : 'email',
				title : '邮箱',
				width : 200,
				sortable : true
			},{field:'_manage',title:'操作',width : 300,formatter:function(value,rowData,rowIndex){
				return '<a href="#" onclick="loadDetailAddress('+rowData.sid+')" value="详情" >详情</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="editeAddressPublic('+rowData.sid+')" value="编辑" >编辑</a>&nbsp;&nbsp;&nbsp;<a href="#" value="删除" onclick="deleteAdd('+rowData.sid+')">删除</a>';
			}}
			] ]
		});
	}

    /**
     * zhp 20130108 删除通讯组
     */
    function deleteAdd(id) {
    	$.MsgBox.Confirm ("提示", "确定删除所选记录,删除后将不可恢复！", function(){
    		  var para = {};
    	  		para['sid'] = id;
    	      	var url = "<%=contextPath %>/teeAddressController/delAddress.action";
    	  		var jsonRs = tools.requestJsonRs(url,para);
    	  		if(jsonRs.rtState){
    	  			$.MsgBox.Alert_auto("删除成功！");
    	  		    datagrid.datagrid('reload');
    	  		}else{
    	  			$.MsgBox.Alert_auto(jsonRs.rtMsg);
    	  		}
    	  });
    }
    
    
    
    
    function addNewAddress(){
    	window.location.href = contextPath + "/system/core/base/address/private/address/addAddress.jsp?groupId="+groupId;
     }

    function doInit(){
		getAddressGroup();
		if(quickSearch!="null"){
			$("#sex").attr("value",sex);
			query();
		}
	}
    function getAddressGroup(){
    	var url = "<%=contextPath%>/teeAddressGroupController/getPubAddressGroups.action";
    	var para = {};
    	var jsonRs = tools.requestJsonRs(url,para);
    	if(jsonRs.rtState){
    		createSelect(jsonRs.rtData);
    	}else{
    		alert(jsonRs.rtMsg);
    	}
    }

    function createSelect(rtData){
    	var selectObj = document.createElement("select");
    	selectObj.setAttribute("class", "BigSelect");
    	//$("<select name='groupId' ></select>");
    	selectObj.setAttribute("name", "groupId");
    	selectObj.setAttribute("style", "width:150px");
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
    	
    		//var para =  tools.formToJson($("#form1")) ;
    		$("#searchDiv").hide();
    		$("#cz").hide();
    		$("#cx").hide();
    		$("#fh").show();
    		queryAddress();
    		//$('#datagrid').datagrid('load', para);

    	
    }
    
    function goBack(){
    	window.location.reload();
    }
	</script>
</head>
<body onload="doInit();" style="padding-right: 10px;padding-left:10px">
<table id="datagrid" fit="true" ></table>
   <div id="toolbar" class="topbar clearfix">
      <div class="fl left" style="vertical-align: middle;">
         <span style="font-size: 14px;font-weight: bold;vertical-align: middle;">查找（关键字）</span>     
      </div>
      <div class="fr right">
         <input id="cx" type="button" value="查询" class="btn-win-white" title="查询" onclick="query()" />
		 <input id="cz" type="button"  value="重置" class="btn-win-white" title="重置" onclick="$('.reset').click();"/> 
		 <input id='fh' style="display: none;" type="button" class="btn-win-white" onclick="goBack();" value="返回"/>
      </div>
      <div style="text-align:left;display:none;" id="optItem">
			<button class="btn-win-white" onclick="window.location = window.location;">返回</button>&nbsp;&nbsp;
	  </div>
      
      
   </div>
<form id="form1" name="form1" method="post">
			<div style="margin-top:10px;" id="searchDiv">
				<table class="TableBlock_page" width="100%" align="center" id="queryContent">
				  <tr>
				    <td nowrap class="TableData" width="120" style='text-indent:15px'>分组：</td>
				    <td nowrap class="TableData" align="left" id="groupSelect">
				    </td>
				    <td nowrap class="TableData" width="120">姓名：</td>
				    <td nowrap class="TableData" align="left">
				        <input type='text' name="psnName" value="<%=psnName%>" class="BigInput"   />
				    </td>
				   </tr>
				   <tr>
				    <td nowrap class="TableData" width="120" style='text-indent:15px'>性别：</td>
				    <td nowrap class="TableData" align="left">
				       <select id="sex" name="sex" class="BigSelect" style="width:150px">
				      			<option value=""></option>
				       			<option value="0">男</option>
				       		    <option value="1">女</option>
				       </select>
				    </td>
				    <td nowrap class="TableData" width="120">生日：</td>
						<td nowrap class="TableData">
						<input type="text" name="birthday" value="<%=birthday%>"  id="birthday" size="16" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput">
						</td>
				   </tr>
				   <tr>
						<td nowrap class="TableData" width="120" style='text-indent:15px'>昵称：</td>
						<td nowrap class="TableData">
						<input type='text' name="nickName" value="<%=nickName%>" class="BigInput" style="width: 200px"  />
						</td>
						<td nowrap class="TableData" width="120">单位名称：</td>
						<td nowrap class="TableData">
						<input type='text' name="deptName" class="easyui-validatebox BigInput" maxlength="50"  size="20"  />
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData" width="120" style='text-indent:15px'>工作电话：</td>
						<td nowrap class="TableData">
						<input type='text' name="telNoDept" class="BigInput" style="width: 200px" />
						</td>
						<td nowrap class="TableData" width="120">单位地址：</td>
						<td nowrap class="TableData">
						<input type='text' name="addDept" class="BigInput" maxlength="100"  size="45" />
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData" width="120" style='text-indent:15px'>家庭电话：</td>
						<td nowrap class="TableData">
						<input type='text' name="telNoHome" class="BigInput" style="width: 200px" />
						</td>
						<td nowrap class="TableData" width="120">家庭住址：</td>
						<td nowrap class="TableData">
						<input type='text' name="addHome" class="BigInput" maxlength="50"  size="50" />
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData" width="120" style='text-indent:15px'>手机：</td>
						<td nowrap class="TableData">
						<input type='text' name="mobilNo"  value="<%=mobilNo%>" class="BigInput" style="width: 200px"/>
						</td>
						<td nowrap class="TableData" width="120">备注：</td>
						<td nowrap class="TableData">
						<input type='text' name="notes" value="<%=notes%>" class="BigInput" maxlength="50"  size="20" />
						</td>
					</tr>
					<input hidden class='reset' type='reset'></input>
				</table>
			</div>
		</form>
		
		
		
</body>
</html>