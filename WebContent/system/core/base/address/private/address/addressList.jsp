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
		
		String userId = TeeStringUtil.getString(request.getParameter("userId"));//创建人，为空是公共
		String groupName = TeeStringUtil.getString(request.getParameter("groupName"));

%>
<%
response.addHeader("X-UA-Compatible", "IE=EmulateIE9");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>个人通讯簿</title>
	<%@ include file="/header/header2.0.jsp"%>
	<%@ include file="/header/easyui2.0.jsp"%>
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
	function doInit(){
		datagrid = $('#datagrid').datagrid({
		url:contextPath + '/teeAddressController/getAddresesByGroupId.action?seqIds='+seqIds,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
        queryParams:para,
        singleSelect: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			/*{field:'ck',checkbox:true,formatter:function(value,rowData,rowIndex){
			        			
		        				 if(!rowData.userId ){
// 	        	                       return {
// 	        	                    	   disabled: true 	   
// 	        	                       };
		        					return '$(".datagrid-row[datagrid-row-index=" '+ 0 + '"] input[type="checkbox"]")[0].disabled = true';
			                         }else{
// 			                        	 return {
// 		        	                    	   disabled: false	   
// 		        	                       };
			                        	 $(".datagrid-row[datagrid-row-index=" + rowIndex + "] input[type='checkbox']")[0].disabled = false;
			                         }
		        			 }
		        			},*/
            {field:'ck',checkbox:true},
			{field:'psnName',title:'姓名',width:80},
			{field:'ministration',title:'职务',width:120},
			{field:'sex',title:'性别',width:50,formatter:function(value,rowData,rowIndex){
		        				
		        				if(rowData.sex == 0)
		        					return "男";
		        				else
		        					return "女";
		        				}},
			{field:'deptName',title:'单位名称',width:150},
			{field:'telNoDept',title:'工作电话',width:100},
			{field:'mobilNo',title:'手机',width:100},
			{field:'email',title:'邮箱',width:150},
			{field:'_manage',title:'操作',width:150,formatter:function(value,rowData,rowIndex){
				var opts = "";
				if(rowData.userId && rowData.userId != ""){
					opts= '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="editeAddressPrivate('+rowData.sid+')"> <span style="color:#2285c6">编辑</span> </a>&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteAdd('+rowData.sid+')" > <span style="color:red">删除</span> </a>';
				}
				return '<a href="javascript:void(0);" onclick="loadDetailAddress('+rowData.sid+')"><span style="color:#2285c6">详情</span></a>'
				 + opts;
			}},
		]],
        onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
            if (data.rows.length > 0) {
                //循环判断操作为新增的不能选择
                for (var i = 0; i < data.rows.length; i++) {
                    //根据operate让某些行不可选
                    if (!data.rows[i].userId ) {
                        $("input[type='checkbox']")[i + 1].disabled = true;
                        $("input[type='checkbox']")[i + 1].style.display = "none";
                    }
                }
            }
        },
});
		$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});
		/* $('#table-bootstrap').bootstrapTable({
		        method: 'post',
		        pagination:true,
		        queryParams:para,
		        sidePagination: 'server', // client or server
		        totalRows: 10, // server side need to set
		        pageNumber: 1,
		        pageSize: 10,
		        pageList: [10, 20,30,50, 100],
		        url: contextPath + '/teeAddressController/getAddresesByGroupId.action?seqIds='+seqIds,
		        classes: 'table table-hover',
		        toolbar:"#toolbar",
		        columns : [ [ 
		        			 {field:'ck',checkbox:true,formatter:function(value,rowData,rowIndex){
			        			
		        				 if(!rowData.userId ){
	        	                       return {
	        	                    	   disabled: true 	   
	        	                       };
			                         }else{
			                        	 return {
		        	                    	   disabled: false	   
		        	                       };
			                         }
		        			 }
		        			},{
		        				field : 'psnName',
		        				title : '姓名',
		        			},{
		        				field : 'ministration',
		        				title : '职务',
		        			},{
		        				field : 'sex',
		        				title : '性别',
		        				formatter:function(value,rowData,rowIndex){
		        				
		        				if(rowData.sex == 0)
		        					return "男";
		        				else
		        					return "女";
		        				}
		        			},{
		        				field : 'deptName',
		        				title : '单位名称',
		        			},{
		        				field : 'telNoDept',
		        				title : '工作电话',
		        			},{
		        				field : 'mobilNo',
		        				title : '手机',
		        			},{
		        				field : 'email',
		        				title : '邮箱',
		        			},{field:'_manage',title:'操作',formatter:function(value,rowData,rowIndex){
		        		
		        				var opts = "";
		        				if(rowData.userId && rowData.userId != ""){
		        					opts= '&nbsp;&nbsp;<input type="button" class="btn btn-primary btn-xs" onclick="editeAddressPrivate('+rowData.sid+')" value="编辑" >&nbsp;&nbsp;<input type="button" class="btn btn-danger btn-xs" value="删除" onclick="deleteAdd('+rowData.sid+')" >';
		        				}
		        				return '<input type="button" class="btn btn-primary btn-xs" onclick="loadDetailAddress('+rowData.sid+')" value="详情" >'
		        				 + opts;
		        			}}
		        			] ]
		      });*/
		
	};

	/* 新增 */
    function addNewAddress(){
    	window.location.href = contextPath + "/system/core/base/address/private/address/addAddress.jsp?groupId="+groupId;
    }


    
	</script>
	
<style>
hr
{ 
height：10px;
border-top:2px solid #185598;
}
.datagrid-header-check input
{
	vertical-align:top;
} 

	</style>
	
</head>
<body style="margin:0px;overflow:hidden;padding-top:5px;" onload="doInit();">

<div id="toolbar" class = "topbar clearfix">
    <div class="moduleHeader">
	    <%if(groupName.equals("")){ %><b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;我的分组</b>
	    <%} else{%><b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;<%=groupName %>分组</b><%} %>
		
		<%if(!userId.equals("")){ %>
		<input type="button" class="btn-del-red fr" value="批量删除" onclick="deleteAddByIdsToPrivate();" style="margin-right:70px;">  
        <input type="button" class="btn-win-white fr" value="新建联系人" onclick="addNewAddress();" style="margin-right:10px;">   
		<%} %>
		
		
	</div>

</div>
	  	
	<!--<table id="table-bootstrap"
	         data-show-refresh="true"
	         data-show-columns="true" ></table>-->
	<table id="datagrid" fit="true"></table>

</body>
</html>