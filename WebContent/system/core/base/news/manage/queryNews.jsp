<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
		String groupId = request.getParameter("groupId");
		String seqIds = request.getParameter("seqIds");
		if(groupId == null || "".equals(groupId)){
			groupId = "0";
		}

%>
<%
String typeId = request.getParameter("typeId");
typeId=typeId==null?"":typeId;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>查看新闻</title>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<%@ include file="/header/validator2.0.jsp"%>
	<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
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
	var typeId = '<%=typeId%>';
	var para = {};
	para['groupId'] = groupId;
	para['seqIds'] = seqIds;
	para['typeId'] = typeId;
	$(function() {
	
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/teeNewsController/getManageNews.action',
		//	toolbar : '#toolbar',
			title : '',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			//pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			queryParams:para,
			fitColumns : true,
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
				title : '新闻类型',
				width : 120,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
					return  rowData.typeDesc;
				}
			},
			{
				field : 'typeDesc',
				title : '类型描述',
				width : 120,
				hidden:true
			},
			{
				field : 'subject',
				title : '新闻标题',
				width : 280,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
					var top = rowData.top;
					var topDesc = "";
					if(top == '1'){
						topDesc = "red";
					}
					return "<B>标题：</B>&nbsp;&nbsp;<a href='#' onclick=loadDetail('"+rowData.sid+"') style='color:"+topDesc+"'>"+rowData.subject+"</a>";
				}
			},{
				field : 'userId',
				title : '发布范围',
				width : 280,
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
			},{
				field : 'newsTime',
				title : '发布时间',
				width : 120,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				 return "<center>"+getFormatDateStr(value , 'yyyy-MM-dd HH:mm')+"</center>";
				}
			},
			{	field : 'clickCount',
				title : '点击次数',
				width : 60,
				align:'center',
				sortable : true
			}
			] ]
		});
		
		
		
		//新闻类型
		var prcs = getSysCodeByParentCodeNo("NEWS_TYPE" , "typeId");
		
		
		if(typeId!=""){
			$("#typeId option").each(function(i,obj){
				if(typeId!=$(obj).attr("value")){
					$(obj).remove();
				}
			});
		}
	});

    function deleteAdd(id){
		var para = {};
		para['sid'] = id;
    	var url = "<%=contextPath %>/teeAddressController/delAddress.action";
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("删除成功!",function(){
		        datagrid.datagrid('reload');
			});
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
    }

  /**
  **查看新闻详情
  */
    function loadDetail(seqId){
    	//var rows = $('#datagrid').datagrid('getRows');
        //var seqId = 0;// rows[rowIndex].sid;
         var url = contextPath + "/system/core/base/news/person/readNews.jsp?id="+seqId+"&isLooked=0";
         openFullWindow(url);
//         top.bsWindow(url,"查看新闻",{width:"900px", height:"400px",buttons:[{name:"关闭"}],submit:function(v,h){
// 	        if(v=="关闭"){
// 	        	$('#datagrid').datagrid("reload");
// 				return true;
// 	        }
//         }});
       
    	//$.jBox.open("iframe:"+url,"查看公告",800,400,{buttons: { '关闭': true }
    	//});
     }

    /**
     * 条件查询
     */
    function query(){
    		var para =  tools.formToJson($("#form1")) ;
    		$('#datagrid').datagrid('load', para);
    	
    }

	</script>
</head>
<body>
<table id="datagrid" fit="true"></table>
<div id="toolbar" class="clearfix">
	<form  method="post" name="form1" id="form1" style="padding:5px">
	<table >
	  <tr>
	     <td class="TableData ">新闻类型：</td>
	    <td  class=" TableData" align="left" style="width:100px;">
		    <select id="typeId" name="typeId" style="height:25px;font-family: MicroSoft YaHei;">
					<option value="">全部</option>
		     </select>
	    </td>
	      <td class="TableData ">发布状态：</td>
	    <td  class="TableData" align="left" style="width:100px;">
	       <select id="state" name="state" style="height:25px;font-family: MicroSoft YaHei;">
	      			 <option value="">全部</option>
	       			<option value="0">未发布</option>
	       		    <option value="1">已发布</option>
	       </select>
	    </td>
	  
	   
	     <td class="TableData ">标题：</td>
			<td  class="TableData" style="width:130px;">
				<input type='text' name="subject" id="subject" style="width:120px;line-height:25px;font-family: MicroSoft YaHei;" maxlength="50"  size="38"  />
			</td>
	 
			 <td class="TableData ">内容：</td>
			<td  class="TableData" colspan="" style="width:100px;">
				<input type="text" name="content"  style="width:120px;line-height:25px;font-family: MicroSoft YaHei;"/>
			</td>
			<td style="padding-left: 30px;">
				<input type="button" value="查询" class="btn-win-white" title="查询" onclick="query()" style="width:45px;height:25px;" >&nbsp;&nbsp;
		        <input type="reset" value="重置" class="btn-win-white" title="重置" style="width:45px;height:25px;" >
			</td>
		</tr>
	</table>
	  </form>
</div>

</body>
</html>