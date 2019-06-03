<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String channelId = request.getParameter("channelId");
	String siteId = request.getParameter("siteId");
	
    TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
    
	boolean isAdmin=TeePersonService.checkIsAdminPriv(loginUser);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>

<script>
var datagrid ;
var channelId = <%=channelId%>;
var siteId = <%=siteId%>;
var loginUserUuid=<%=loginUser.getUuid()%>;
var isAdmin=<%=isAdmin%>;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/cmsDocument/datagrid.action?channelId='+channelId,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		pageList: [10,20,30],
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'docTitle',title:'文档标题',width:200,formatter:function(data,rowData){
				if(rowData.top==1){
					data = "<span style='color:red'>[置顶]&nbsp;"+data+"</span>";
				}				
				return "<a href='javascript:void(0)' onclick='edDoc("+rowData.docId+")'>"+data+"</a>";
			}},
			{field:'crTime',title:'创建时间',width:100,formatter:function(data,rowData){
				return getFormatDateStr(rowData.crTime,"yyyy-MM-dd HH:mm:ss");
			}},
			{field:'crUserName',title:'创建人',width:100},
			{field:'chnlName',title:'所在栏目',width:100},
			{field:'status',title:'状态',width:100,formatter:function(data,rowData){
				var render = "";
				switch(data){
				case 1:
					render = "新稿";
					break;
				case 2:
					render = "待审";
					break;
				case 3:
					render = "已审";
					break;
				case 4:
					render = "已发";
					break;
				case 5:
					render = "已拒";
					break;
				}
				return render;
			}},
			{field:'2',title:'操作',formatter:function(e,rowData){
				var html = [];
				var status=rowData.status;
				html.push("&nbsp;<a href='javascript:void(0)' onclick='toPreview("+rowData.docId+")'>预览</a>");
				if(status==3||status==4){//已发  待发 可以发布
				    if(rowData.crUserId==loginUserUuid||isAdmin=="true"||isAdmin==true){
				    	html.push("&nbsp;<a href='javascript:void(0)' onclick='toSinglePub("+rowData.docId+")'>发布</a>");
				    }
				}
				
				if(rowData.crUserId==loginUserUuid||isAdmin=="true"||isAdmin==true){
					html.push("&nbsp;<a href='javascript:void(0)' onclick='edDoc("+rowData.docId+")'>编辑</a>");
				}
				
				return html.join("");
			}}
		]], 
		onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
            if (data.rows.length > 0) {
                //循环判断操作为新增的不能选择
                for (var i = 0; i < data.rows.length; i++) {
                    //根据operate让某些行不可选
                    if (data.rows[i].crUserId!=loginUserUuid&&isAdmin!="true"&&isAdmin!=true) {
                        $("input[type='checkbox']")[i + 1].disabled = true;
                        $("input[type='checkbox']")[i + 1].style.display = "none";
                    }
                }
            }
        },
        onClickRow: function(rowIndex, rowData){
            $("input[type='checkbox']").each(function(index, el){
                if (el.disabled == true) {
                	datagrid.datagrid('unselectRow', index - 1);
                }
            });
        },
        onSelectAll:function(rowIndex, rowData){
        	  $("input[type='checkbox']").each(function(index, el){
                  if (el.disabled == true) {
                  	datagrid.datagrid('unselectRow', index - 1);
                  	el.checked=false;
                  }
              });
        }
	});
}

function crDoc(){
	openFullWindow(contextPath+"/system/subsys/cms/docmgr.jsp?isNew=true&channelId="+channelId,"创建文档");
}

function edDoc(docId){
	openFullWindow(contextPath+"/system/subsys/cms/docmgr.jsp?channelId="+channelId+"&documentId="+docId,"编辑文档");
}

function del(){
	var rows = datagrid.datagrid('getSelections');
	if(rows.length==0){
		$.MsgBox.Alert_auto("请选择至少一篇文档!");
		return;
	}
	
	 $.MsgBox.Confirm ("提示", "是否要将所选文档放入回收站？", function(){
		 for(var i=0;i<rows.length;i++){
				tools.requestJsonRs(contextPath+"/cmsDocument/moveToTrash.action",{docId:rows[i].docId});
			}
		    $.MsgBox.Alert_auto("删除成功！");
			datagrid.datagrid("reload");   
	  });
}

function toPub(){
	var rows = datagrid.datagrid('getSelections');
	if(rows.length==0){
		$.MsgBox.Alert_auto("请选择至少一篇文档!");
		return;
	}
	var docIds="";
	for(var i=0;i<rows.length;i++){
		docIds+=rows[i].docId+",";
	}
	
	$.MsgBox.Confirm ("提示", "确认要发布选中文章吗？", function(){
		var url = contextPath+"/cmsDocumentPub/toPub.action?docIds="+docIds+"&pubAll=1&chnlId="+channelId;
		bsWindow(url,"文章发布",{buttons:[],width:"300px",height:"200px"});
	  });
}
function toPreview(docId){
	var url = contextPath+"/cmsPub/portal.action?siteId="+siteId+"&channelId="+channelId+"&docId="+docId;
	window.open(url);
}

function toSinglePub(docId){
	$.MsgBox.Confirm ("提示", "确认要发布该文章吗？", function(){
		var url = contextPath+"/cmsDocumentPub/toPub.action?docIds="+docId+"&pubAll=1&chnlId="+channelId;
		bsWindow(url,"文章发布",{buttons:[],width:"300px",height:"200px"});
	  });
}

</script>
</head>
<body onload="doInit();" style="margin:0px;overflow:hidden;padding-left: 10px;padding-right: 10px;">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="left fl clearfix">
		<button class="btn-win-white" onclick="crDoc()">创建文档</button>
		<button class="btn-del-red" onclick="del()">删除文档</button>
		<button class="btn-win-white" onclick="toPub()">发布指定文档</button>
		<button class="btn-del-red" onclick="window.location='docTrash.jsp?channelId=<%=channelId%>'">文档回收站</button>
	</div>
</div>
</body>

</html>