<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
		String groupId = request.getParameter("groupId");
		String seqIds = request.getParameter("seqIds");
		if(groupId == null || "".equals(groupId)){
			groupId = "0";
		}
		String typeId = request.getParameter("typeId");
		typeId=typeId==null?"":typeId;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>未读公告</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
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
	var typeId = "<%=typeId%>";
	var para = {};
	para['groupId'] = groupId;
	para['seqIds'] = seqIds;
	para['typeId'] = typeId;
	$(function() {
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/teeNotifyController/query.action',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		//	toolbar : '#toolbar',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			/* pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ], */
			fit : true,
			queryParams:para,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'sid',
			sortOrder: 'desc',
			striped: true,
			singleSelect:true,
			remoteSort: true,
			toolbar: '#toolbar',
			columns : [ [ 
			 {field:'ck',checkbox:true},{
				title : 'id',
				field : 'sid',
				width : 200,
				hidden:true
				//sortable:true
				
			}, {
				field : 'fromPersonName',
				title : '发布人',
				width : 100,
				sortable : false,
				formatter:function(value,rowData,rowIndex){
				
				return "<B>"+rowData.fromPersonName+"</B>";
				}
			},{
				field : 'typeId',
				title : '类型',
				width : 100,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
					return rowData.typeDesc;
				}
			}
			,{
				field : 'typeDesc',
				title : '类型描述',
				width : 100,
				hidden : true
			},{
				field : 'subject',
				title : '标题',
				width : 300,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
					var top = rowData.top;
					var topDesc = "";
					if(top == '1'){
						topDesc = "red";
					}
					return "<B>标题：</B>&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick=loadDetail('"+rowData.sid+"') style='color:" + topDesc + "'>"+rowData.subject+"</a>";
				}
			},{
				field : 'userId',
				title : '发布范围',
				width : 200,
				formatter:function(value,rowData,rowIndex){
				var deptNames = rowData.toDeptNames;
				var personNames = rowData.toUserNames;
				var toRolesNames = rowData.toRolesNames;
				var renderHtml = "";
				if(deptNames && deptNames != ""){
					renderHtml = renderHtml + "<div title="+deptNames+"><B>部门：</B>&nbsp;&nbsp;"+deptNames+""+"</div>";
				}
				if(personNames && personNames != ""){
					renderHtml = renderHtml + "<div title="+personNames+"><B>人员：</B>&nbsp;&nbsp;"+personNames+""+"</div>";
				}
				if(toRolesNames && toRolesNames != ""){
					renderHtml = renderHtml + "<div title="+toRolesNames+"><B>角色：</B>&nbsp;&nbsp;"+toRolesNames+""+"</div>";
				}
				if(rowData.allPriv==1){
					renderHtml="<div>全体人员</div>";
				}
				return "<div>"+renderHtml+"</div>";
				}
			},{
				field : 'sendTime',
				title : '发布时间',
				width : 200,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				 return getFormatDateStr(rowData.sendTime , 'yyyy-MM-dd HH:mm');
				}
			}
			] ]
		});
		
		//$("[title]").tooltips();
		
		
		//公告类型
		var prcs = getSysCodeByParentCodeNo("NOTIFY_TYPE" , "typeId");
		
		if(typeId!=""){
			$("#typeId option").each(function(i,obj){
				if(typeId!=$(obj).attr("value")){
					$(obj).remove();
				}
			});
		}
	});


     /* 查看公告详情 */
    function loadDetail(seqId){
        var url = contextPath + "/system/core/base/notify/person/readNotify.jsp?id="+seqId+"&isLooked=1";
        openFullWindow(url);
//         top.bsWindow(url,"公告查看",{buttons:[{name:"关闭"}],submit:function(v,h){
// 	        if(v=="关闭"){
// 	        	//$('#datagrid').datagrid("reload");
// 				return true;
// 	        }
//         }});
     }

    function query(){
    		var para =  tools.formToJson($("#form1")) ;
    		$('#datagrid').datagrid('load', para);

    	
    }

	</script>
</head>
<body style="margin:0px;overflow:auto;" onload="">

  <div id="toolbar" class = "clearfix">
  <form  method="post" name="form1" id="form1" >
     <div class="left fl setHeight">
         <!-- 格式：<select id="format" name="format" class="BigSelect">
				<option value="">全部</option>
	 			<option value="0">普通格式</option>
	 		    <option value="1">MHT格式</option>
	       </select>&nbsp;&nbsp;&nbsp; -->
	      类型：<select id="typeId" name="typeId" class="BigSelect" style="height:20px">
					<option value="">全部</option>
		  </select>&nbsp;&nbsp;
                 标题：<input type='text' name="subject" id="subject" class="" maxlength="50"  size="38"  style="height:20px" />&nbsp;&nbsp;
                 内容：<input type='text' name="content" class="" style="height:20px" />&nbsp;&nbsp;
		  <input type="button" value="查询" class="btn-win-white" title="查询" onclick="query()" >&nbsp;&nbsp;
		  <input type="reset" value="重置" class="btn-win-white" title="重置" >
	  
     </div>
     </form> 
  </div>
 
  <table id="datagrid" fit="true"></table>

</body>

</html>