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
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>通讯簿</title>
<%@ include file="/header/header2.0.jsp" %> 
<%@ include file="/header/easyui2.0.jsp" %> 
<style type="text/css">
.datagrid-header-check input{
    vertical-align: top;
}
</style>
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
	var para = {};
	para['groupId'] = groupId;
	para['seqIds'] = seqIds;
	$(function() {
		// var param= tools.formToJson($("#form1"));
        datagrid=$('#datagrid').datagrid({
        	url:contextPath + '/teeAddressController/getAddresesByGroupId.action?isPub=1&seqIds='+seqIds,
        	queryParams:para,
        	view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
    		pagination:true,
    		singleSelect:false,
    		toolbar:'#toolbar',//工具条对象
    		checkbox:false,
    		border:false,
    		//idField:'formId',//主键列
    		fitColumns:true,//列是否进行自动宽度适应
		    columns : [ [ 
		        		{field:'sid',checkbox:true,title:'ID',width:100},{
		        				field : 'psnName',
		        				title : '姓名',
		        				width : 200,
		        				//sortable : true
		        			},{
		        				field : 'ministration',
		        				title : '职务',
		        				width : 100,
		        				//sortable : true
		        			},{
		        				field : 'sex',
		        				title : '性别',
		        				width : 200,
		        				//sortable : true,
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
		        				//sortable : true
		        			},{
		        				field : 'telNoDept',
		        				title : '工作电话',
		        				width : 200,
		        				//sortable : true
		        			},{
		        				field : 'mobilNo',
		        				title : '手机',
		        				width : 200,
		        				//sortable : true
		        			},{
		        				field : 'email',
		        				title : '邮箱',
		        				width : 200,
		        				//sortable : true
		        			},{field:'_manage',title:'操作',width : 300,formatter:function(value,rowData,rowIndex){
		        				return '<a href="#" onclick="loadDetailAddress('+rowData.sid+')" value="详情" >详情</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="editeAddressPublic('+rowData.sid+')" value="编辑" >编辑</a>&nbsp;&nbsp;&nbsp;<a href="#" value="删除" onclick="deleteAdd('+rowData.sid+')" >删除</a>';
		        			}}
		        			] ]
		      });
		

	});

    function addNewAddress(){
    	window.location.href = contextPath + "/system/core/base/address/public/address/addAddress.jsp?groupId="+groupId;
     }


	
	</script>
</head>
<body style="padding-right: 10px;padding-left: 10px">
  <table id="datagrid" fit="true"></table>
  <div id="toolbar" class="topbar clearfix">
     <div class="fl left">
        <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_tongxunlu1.png">
        <span class="title">联系人列表</span>
     </div>
     <div class="fr right">
       
        <input type="button" id="addPerson" style="display: none;" class="btn-win-white" value="新建联系人" onclick="addNewAddress();">  
           
        <input type="button" class="btn-del-red" value="批量删除" onclick="deleteAddByIdsToPublic();"> 
     </div>
  </div>
  <script type="text/javascript">
     if(groupId!=""&&(seqIds==""||seqIds==null)){
    	 $("#addPerson").show();
     }
  
  </script>



</body>
</html>