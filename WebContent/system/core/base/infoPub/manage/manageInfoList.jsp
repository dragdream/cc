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
	<title>未读信息</title>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	
	<script type="text/javascript" src="<%=contextPath %>/system/core/base/news/js/news.js"></script>
	
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
			url : contextPath + '/teeNewsController/getManageNews.action?typeId=<%=infoType%>',
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
			 {field:'ck',checkbox:true},
			 {
				title : 'id',
				field : 'sid',
				width : 60,
				hidden:true
				//sortable:true
				
			}, {
				field : 'provider1',
				title : '发布人',
				width : 70,
				sortable : true
			},{
				field : 'typeId',
				title : '信息类型',
				width : 70,
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
				width : 220,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
					var top = rowData.top;
					var topDesc = "";
					if(top == '1'){
						topDesc = "red";
					}
					return "<B>标题：</B>&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick=loadDetail('"+rowData.sid+"') style='color:"+topDesc+"'>"+rowData.subject+"</a>";
				}
			},
			 {
				title : '置顶',
				field : 'top',
				width : 10,
				hidden:true
				
			},{
				field : 'userId',
				title : '发布范围',
				width : 220,
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
			}
			,{
				field : 'newsTime',
				title : '发布时间',
				width : 120,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				 return "<center>"+getFormatDateStr(value , 'yyyy-MM-dd HH:mm:ss')+"</center>";
				}
			},
			{	field : 'clickCount',
				title : '点击次数',
				width : 60,
				align:'center',
				sortable : true
			},{
				field : 'publish',
				title : '状态',
				width : 60,
				formatter:function(value,rowData,rowIndex){
				var publish = rowData.publish;
				var text = "未发布";
				if(publish == 1){
					text = "已发布";
				}
				if(publish == 2){
					text = "终止";
				}
				 return "<center>"+text+"</center>";
				}
			},{field:'_manage',title:'操作',width : 320,formatter:function(value,rowData,rowIndex){
				var publish = rowData.publish;
				var opts = "";
				if(publish && publish ==1){
					opts =  '<input type="button" class="btn btn-info btn-xs" onclick="editNews('+rowIndex+')" value="编辑" >&nbsp;&nbsp;<input type="button" class="btn btn-info btn-xs" onclick="manageComment('+rowIndex+')" value="管理评论" >&nbsp;&nbsp;<input type="button" class="btn btn-info btn-xs" value="终止" onclick="updateNewsState('+rowData.sid+',2)" >';
				}else{
					opts = '<input type="button" class="btn btn-info btn-xs" onclick="editNews('+rowIndex+')" value="编辑" >&nbsp;&nbsp;<input type="button" class="btn btn-info btn-xs" onclick="manageComment('+rowIndex+')" value="管理评论" >&nbsp;&nbsp;<input type="button" class="btn btn-info btn-xs" value="生效" onclick="updateNewsState('+rowData.sid+',1)" >';
				}
				opts = opts + '&nbsp;&nbsp;<input type="button" class="btn btn-info btn-xs" onclick="lookNewDetail('+rowData.sid+')" value="查阅情况" >';
				opts = opts + '&nbsp;&nbsp;<input type="button" class="btn btn-danger btn-xs" onclick="deleteById('+rowData.sid+')" value="删除" >';
			
				return opts;
			}}
			] ]
		});
		//$("[title]").tooltips();
	});

	
	
	/**
	*
	*根据Id 删除新闻
	*/
    function deleteById(id){
		var para = {};
		para['id'] = id;
		if(confirm("确定删除所选记录，删除后不可恢复")){
			var url = "<%=contextPath %>/teeNewsController/deleteById.action";
			var jsonRs = tools.requestJsonRs(url,para);
			if(jsonRs.rtState){
				$.jBox.tip("删除成功!" , "info" , {timeout:1500});
				datagrid.datagrid('reload');
			}else{
				alert(jsonRs.rtMsg);
			}
		}
    	
    }
	
	

    /**
     * 查看新闻详情
     * @param seqId 
     */
    function loadDetail(seqId){
        var url = contextPath + "/system/core/base/infoPub/person/readInfo.jsp?id="+seqId+"&isLooked=1";
        top.bsWindow(url,"查看信息", {width:"900px", height:"400px",buttons:[{name:"关闭"}],submit:function(v,h){
            if(v=="关闭"){
            	$('#datagrid').datagrid("reload");
    			return true;
            }
        }});
     }
    
    /**
     * 查看新闻阅读情况
     * @param sid 
     */
    function lookNewDetail(sid){
    	var url = contextPath + "/system/core/base/infoPub/manage/lookDetail.jsp?id=" + sid;
        top.bsWindow(url,"查看阅读情况", {width:"800px", height:"380px",buttons:[{name:"关闭"}],submit:function(v,h){
            if(v=="关闭"){
    			return true;
            }
        }});
    }
	
    /**
    *
    *更新状态
    */
    function updateNewsState(id,state){
    	var para = {};
		para['id'] = id;
		para['state'] = state;
    	var url = "<%=contextPath %>/teeNewsController/updateNewsState.action";
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			datagrid.datagrid('reload');
		}else{
			alert(jsonRs.rtMsg);
		}
    }
  //编辑 新闻
    function editNews(rowIndex){
    	var rows = $('#datagrid').datagrid('getRows');
    	var seqId = rows[rowIndex].sid;
    	window.location.href ="<%=contextPath%>/system/core/base/infoPub/manage/addInfo.jsp?id="+seqId+"&infoType=<%=infoType%>";
    }
  /* 管理评论 */
    function manageComment(rowIndex){
    	var rows = $('#datagrid').datagrid('getRows');
        var seqId = rows[rowIndex].sid;
         var url = contextPath + "/system/core/base/infoPub/manage/readInfoReplayManage.jsp?id="+seqId;
        top.bsWindow(url,"评论管理",{width:"1000px", height:"400px",buttons:[{name:"关闭"}],submit:function(v,h){
	        if(v=="关闭"){
	        	$('#datagrid').datagrid("reload");
				return true;
	        }
        }});
       
    	//$.jBox.open("iframe:"+url,"查看公告",800,400,{buttons: { '关闭': true }
    	//});
     }
	</script>
</head>
<body>
<div id="toolbar">
	<div id="toolbar">
<div class="base_layout_top" style="position:static">
	<span class="easyui_h1"><%=infoTypeDesc %>></>管理</span>
</div>
<br/>
</div>

</div>
<table id="datagrid" ></table>
</body>
</html>