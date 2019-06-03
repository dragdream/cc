<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.tianee.oa.core.general.TeeSysCodeManager"%>
<%
		String groupId = request.getParameter("groupId");
		String seqIds = request.getParameter("seqIds");
		if(groupId == null || "".equals(groupId)){
			groupId = "0";
		}
		String infoType=request.getParameter("infoType");
		String infoTypeDesc = TeeSysCodeManager.getChildSysCodeNameCodeNo("NEWS_TYPE",infoType);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>查看新闻</title>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
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
	
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/teeNewsController/getReadNews.action?typeId=<%=infoType%>',
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
				field : 'provider1',
				title : '发布人',
				width : 100,
				sortable : true
			},{
				field : 'typeId',
				title : '类型',
				width : 80,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
					return  rowData.typeDesc;
				}
			},
			{
				field : 'typeDesc',
				title : '类型描述',
				width : 10,
				hidden:true
			},{
				field : 'subject',
				title : '标题',
				width : 200,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				return "<B>标题：</B>&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick=loadDetail('"+rowData.sid+"')>"+rowData.subject+"</a>";
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
					renderHtml = renderHtml + "<div><B>部门：</B>&nbsp;&nbsp;"+deptNames+""+"</div>";
				}
				if(personNames && personNames != ""){
					renderHtml = renderHtml + "<div><B>人员：</B>&nbsp;&nbsp;"+personNames+""+"</div>";
				}
				if(toRolesNames && toRolesNames != ""){
					renderHtml = renderHtml + "<div><B>角色：</B>&nbsp;&nbsp;"+toRolesNames+""+"</div>";
				}
				if(rowData.allPriv==1){
					renderHtml="<div>全体人员</div>";
				}
				return "<div>"+renderHtml+"</div>";
				}
			},
			{	field : 'clickCount',
				title : '点击次数',
				width : 60,
				align:'center',
				sortable : true
			}
			,{
				field : 'newsTime',
				title : '发布时间',
				width : 120,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				 return "<center>"+getFormatDateStr(value, 'yyyy-MM-dd HH:mm:ss')+"</center>";
				}
			}
			] ]
		});
		//$("[title]").tooltips();
		
		
		//新闻类型
		var prcs = getSysCodeByParentCodeNo("NEWS_TYPE" , "typeId");
	});

    function loadDetail(seqId){
    	//var rows = $('#datagrid').datagrid('getRows');
        //var seqId = 0;// rows[rowIndex].sid;
         var url = contextPath + "/system/core/base/infoPub/person/readInfoReplay.jsp?id="+seqId+"&isLooked=0";
        top.bsWindow(url,"查看信息",{width:"900px", height:"400px",buttons:[{name:"关闭"}],submit:function(v,h){
	        if(v=="关闭"){
	        	$('#datagrid').datagrid("reload");
				return true;
	        }
        }});
       
    	//$.jBox.open("iframe:"+url,"查看公告",800,400,{buttons: { '关闭': true }
    	//});
     }

    /**
     * 条件查询
     */
    function query(){
    	if(checkForm()){
    		var para =  tools.formToJson($("#form1")) ;
    		$('#datagrid').datagrid('load', para);
    	}
    	
    }

    function checkForm(){
  	  return $("#form1").form('validate'); 
  }
	</script>
</head>
<body>
<div id="toolbar">
		<div class="base_layout_top" style="position:static">
		<span class="easyui_h1">查询</span>
	</div>
	<form  method="post" name="form1" id="form1" style="padding:5px;">
	<table >
	  <tr>
	    <td nowrap class="" >类型：</td>
	    <td nowrap class="" align="left">
		   <!--  <select id="typeId" name="typeId" class="BigSelect" >
					<option value="">全部</option>
		 			
		     </select> -->
		     <%=infoTypeDesc %>
	    </td>
	    
	      <td nowrap class="" >已读/未读类型：</td>
	    <td nowrap class="" align="left">
	       <select id="state" name="state" class="BigSelect">
	      			 <option value="">全部</option>
	       			<option value="0">未读</option>
	       		    <option value="1">已读</option>
	       </select>
	    </td>
	   </tr>
	   <tr>
	  
	    <td nowrap class="" > 标题：</td>
			<td nowrap class="">
			<input type='text' name="subject" id="subject" class="easyui-validatebox BigInput" maxlength="50"  size="38"  />
			</td>
	 
			<td nowrap class="" >内容：</td>
			<td nowrap class="" colspan="">
				<input type="text" name="content" class="BigInput"></input>
				<input type="button" value="查询" class="btn btn-primary" title="查询" onclick="query()" >&nbsp;&nbsp;
		        <input type="reset" value="重置" class="btn btn-default" title="重置" >
			</td>
		</tr>
	</table>
	  </form>
</div>
<table id="datagrid" ></table>
</body>
</html>