<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.partthree.util.TeePartThreeUtil" %>
<%
	String sortId = request.getParameter("sortId");
	String sortName = request.getParameter("sortName");
	
	
	TeePerson  loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	boolean hasPriv=TeePartThreeUtil.checkHasPriv(loginUser, "WORKFLOW_FLOW_TYPE");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>Tenee办公自动化智能管理平台</title>
	<script>
	var hasPriv=<%=hasPriv %>;
	var contextPath = "<%=contextPath%>";
	var sortId = "<%=sortId%>";
		$(function(){
			var url = "<%=contextPath%>/flowSort/get.action";
			var json = tools.requestJsonRs(url,{flowSortId:<%=sortId%>});
			sortName = json.rtData.sortName;

			$("#sortNameSpan").html(sortName);
			
			$('#datagrid').datagrid({
				url:contextPath+'/flowType/datagrid.action',
				queryParams:{sortId:sortId},
				pagination:true,
				singleSelect:true,
				striped: true,
				border: false,
				toolbar:'#toolbar',//工具条对象
				checkbox:true,
				fitColumns:true,//列是否进行自动宽度适应
				columns:[[
					{field:'flowName',title:'流程名称',width:100,sortable : true},
					{field:'typeDesc',title:'类型',width:100,sortable : true},
					{field:'formName',title:'表单',width:100,sortable : true},
					{field:'totalWorkNum',title:'已建工作数',width:50,sortable : true},
					{field:'_manage',title:'管理',width:200,formatter:function(value,rowData,rowIndex){
						var render = "<a href='#' onclick='edit("+rowData.sid+")'>详细设置</a>";
						if(rowData.type==1){//固定流程
							render += "&nbsp;&nbsp;<a href='javascript:void(0)' onclick='flowDesign("+rowData.sid+")'>流程设计器</a>"
						}else{//自由流程
							//render += "&nbsp;&nbsp;<a href='javascript:void(0)' onclick='setPrcsPriv("+rowData.sid+")'>经办权限</a>";
						}
						if(hasPriv){
							render += "&nbsp;&nbsp;<a href='javascript:void(0)' onclick='delFlowType("+rowData.sid+")'>删除</a>";
						}
						render+="&nbsp;&nbsp;<a href='javascript:addMenuFunc(\"" + rowData.sid + "\",\"" + rowData.formName + "\")'>菜单定义指南</a>";
						return render;
					}}
				]]
			});
		});

		/**
		 * 菜单自定义
		 */
		function addMenuFunc(sid,menuName){
		  var childMenuName = menuName;
		  var menuURL = "/system/core/workflow/flowrun/list/index.jsp?flowSortId=<%=sortId%>&flowId=" + sid;
		  var url = contextPath + "/system/core/menu/addupdatechild1.jsp?childMenuName=" + encodeURIComponent(childMenuName) + "&menuURL=" + encodeURIComponent(menuURL);
		  bsWindow(url ,"菜单定义指南",{width:"760px",height:"360px",buttons:
		     [//{name:"关闭",classStyle:"btn btn-primary"}
		     ]
		  ,submit:function(v,h){
		    var cw = h[0].contentWindow;
		    if(v=="修改"){
		      
		    }else if(v == "删除"){
		      
		    }else if(v=="关闭"){
		      return true;
		    }
		  }});
		}

		function delFlowType(sid){
			//判断该flowType是否存在关联的FlowRun
			var json=tools.requestJsonRs(contextPath+"/flowType/hasRelatedFlowRun.action",{sid:sid});
			if(json.rtState){
				if(json.rtData==1){
					$.jBox.tip("该流程类型下关联了流程数据，暂且不能删除！");
					return;
				}else if(json.rtData==0){
					$.jBox.confirm("确认要删除该流程类型吗？","删除流程定义",function(v){
						if(v=="ok"){
									var url = contextPath+"/flowType/delFlowType.action";
									var json = tools.requestJsonRs(url,{flowId:sid});
									$.jBox.tip(json.rtMsg,"info");
									if(json.rtState){
										parent.$("#left")[0].contentWindow.location.reload();
										window.location.reload();
									}	
						}
					});
				}
			}
		}
		
		function setPrcsPriv(sid){
			var url = contextPath+"/system/core/workflow/flowtype/manage/setPrcsPriv.jsp?flowTypeId="+sid;
			var title = "设置办理权限";
			  bsWindow(url ,title,{width:"500",height:"360",buttons:
					[
					 {name:"保存",classStyle:"btn btn-primary"},
				 	 {name:"关闭",classStyle:"btn btn-default"}
					 ]
					,submit:function(v,h){
					var cw = h[0].contentWindow;
					if(v=="保存"){
						cw.commit(function(json){
							BSWINDOW.modal("hide");
						});
					}else if(v=="关闭"){
						return true;
					}
				}});
		}
		
		function refresh(){
			window.location.reload();
		}

		function add(){
			window.location.href="new.jsp?sortId="+sortId;
		}

		function flowDesign(sid){
			openFullWindow(contextPath+"/system/core/workflow/flowtype/flowdesign/index.jsp?flowId="+sid,"流程设计器");
			
		}

		function edit(sid){
			window.location="tabindex.jsp?flowTypeId="+sid;
		}
	</script>
</head>
<body style="overflow:hidden">
	<table id="datagrid" fit="true"></table>
	
	<!-- 声明工具条 -->
	<div id="toolbar">
		<div class="base_layout_top" style="position:static">
		<table width="100%">
			<tr>
				<td>
					<span class="easyui_h1">流程列表&nbsp;/&nbsp;<span id="sortNameSpan"></span></span>
				</td>
				<td align=right>
				    <%
				       if(hasPriv){
				    	   %> 
				    	   
				    	   <button onclick="add()" class="btn btn-success">新建流程</button>
					      &nbsp;&nbsp;
				    	      <%  
				       }
				    %>
					
				</td>
			</tr>
		</table>
	</div>
	<br/>
	</div>
</body>

</html>