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
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>未读公告</title>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	
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
				title : '新闻类型',
				width : 80,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
					return  rowData.typeDesc;
				}
			},
			{
				field : 'typeDesc',
				title : '类型描述',
				width : 20,
				hidden:true
			},{
				field : 'subject',
				title : '新闻标题',
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
				width : 160,
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
				width : 130,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				 return "<center>"+getFormatDateStr(value , 'yyyy-MM-dd HH:mm')+"</center>";
				}
			},
			{	field : 'clickCount',
				title : '点击次数',
				width : 100,
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
			},{field:'_manage',title:'操作',width : 220,formatter:function(value,rowData,rowIndex){
				var publish = rowData.publish;
				var opts = "";
				if(publish && publish ==1){
					opts =  '<a href="#" onclick="editNews('+rowIndex+')" value="编辑" >编辑</a>&nbsp;&nbsp;<a href="#"  onclick="manageComment('+rowIndex+')" value="管理评论" >管理评论</a>&nbsp;&nbsp;<a href="#"  value="终止" onclick="updateNewsState('+rowData.sid+',2)" >终止</a>';
				}else{
					opts = '<a href="#"  onclick="editNews('+rowIndex+')" value="编辑" >编辑</a>&nbsp;&nbsp;<a href="#"  onclick="manageComment('+rowIndex+')" value="管理评论" >管理评论</a>&nbsp;&nbsp;<a href="#"  value="生效" onclick="updateNewsState('+rowData.sid+',1)" >生效</a>';
				}
				opts = opts + '&nbsp;&nbsp;<a href="#"  onclick="lookNewDetail('+rowData.sid+')" value="查阅情况" >查阅情况</a>';
				opts = opts + '&nbsp;&nbsp;<a href="#"  onclick="deleteById('+rowData.sid+')" value="删除" >删除</a>';
			
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
		 parent.$.MsgBox.Confirm ("提示", "确定删除所选记录？删除后不可恢复！", function(){
			var url = "<%=contextPath %>/teeNewsController/deleteById.action";
			var jsonRs = tools.requestJsonRs(url,para);
			if(jsonRs.rtState){
				parent.$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid('reload');
			}else{
				$.MsgBox.Alert_auto(jsonRs.rtMsg);
			}
		});
    	
	}
	
	

    /**
     * 查看新闻详情
     * @param seqId 
     */
    function loadDetail(seqId){
        var url = contextPath + "/system/core/base/news/person/readNews.jsp?id="+seqId+"&isLooked=0";
        openFullWindow(url);
//         top.bsWindow(url,"查看新闻", {width:"900px", height:"400px",buttons:[{name:"关闭"}],submit:function(v,h){
//             if(v=="关闭"){
//             	$('#datagrid').datagrid("reload");
//     			return true;
//             }
//         }});
     }
    
    /**
     * 查看新闻阅读情况
     * @param sid 
     */
    function lookNewDetail(sid){
    	var url = contextPath + "/system/core/base/news/manage/lookDetail.jsp?id=" + sid;
        bsWindow(url,"查看新闻阅读情况", {width:"600", height:"300",buttons:[{name:"清空查阅详情",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}],
        	submit:function(v,h){
        		var cw = h[0].contentWindow;
            	if(v=="清空查阅详情"){
            		cw.clearReadInfo();
    				//return true;
                }
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
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
    }
  //编辑 新闻
    function editNews(rowIndex){
    	var rows = $('#datagrid').datagrid('getRows');
    	var seqId = rows[rowIndex].sid;
    	window.location.href ="<%=contextPath%>/system/core/base/news/manage/addNews.jsp?id="+seqId+"&typeId="+typeId;
    }
  /* 管理评论 */
    function manageComment(rowIndex){
    	var rows = $('#datagrid').datagrid('getRows');
        var seqId = rows[rowIndex].sid;
        var url = contextPath + "/system/core/base/news/manage/readNewsReplayManage.jsp?id="+seqId;
        bsWindow(url,"评论新闻管理",{width:"700", height:"400",
        	buttons:[{name:"关闭",classStyle:"btn-alert-gray"}],
        	submit:function(v,h){
        	var cw = h[0].contentWindow;
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
<body >
<div id="toolbar" class ="clearfix" style="margin-top: 8px">

</div>


<table id="datagrid" fit="true"></table>
</body>
</html>