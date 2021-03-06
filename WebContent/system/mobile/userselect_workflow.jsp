<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.tianee.oa.oaconst.TeeConst"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson"%>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@page import="org.springframework.core.io.ClassPathResource"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson"%>
<%@ page import="com.tianee.oa.core.org.bean.TeeDepartment"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<%@ include file="/system/mobile/mui/header.jsp"%>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";
	String mobilePath = contextPath + "/system/mobile";

	TeePerson loginUser = (TeePerson) session
			.getAttribute(TeeConst.LOGIN_USER);
	int deptId = 0;
	if (loginUser.getDept() != null) {
		deptId = loginUser.getDept().getUuid();
	}
%>
<%
	String moduleId = request.getParameter("moduleId");
	if (moduleId == null) {
		moduleId = "";
	}
	String privNoFlag = request.getParameter("privNoFlag");
	if (privNoFlag == null || "".equals(privNoFlag)) {
		privNoFlag = "0";
	}
	String privOp = request.getParameter("privOp");
	if (privOp == null) {
		privOp = "";
	}

	String objSelectType = request.getParameter("objSelectType");
	if (objSelectType == null) {
		objSelectType = "";
	}

	//人员条件filter，目前工作流需要处理
	String userFilter = request.getParameter("userFilter") == null
			? "0"
			: request.getParameter("userFilter");

	String to_id_field = request.getParameter("to_id_field");
	String to_name_field = request.getParameter("to_name_field");
	String single_select = request.getParameter("single_select");
	String prcsId = request.getParameter("prcsId");
	String frpSid = request.getParameter("frpSid");
%>
<script type="text/javascript"
	src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/system/mobile/js/tools.js"></script>
<title></title>
</head>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left"
			onclick="doSelect()">
			<span class="mui-icon mui-icon-checkmarkempty"></span>确定
		</button>
		<h1 class="mui-title">选择人员</h1>
	</header>
	<div class="mui-input-group"
		style="position: fixed; top: 40px; left: 0px; right: 0px; height: 40px;">
		<div class="mui-input-row">
			<input id="searchBox" type="text" placeholder="请输入人员姓名或账号搜索"
				oninput="doSearch()" />
		</div>
	</div>
	<div class="mui-content"
		style="margin: 0px; padding: 0px; position: fixed; top: 95px; left: 0px; right: 130px; bottom: 0px; overflow: auto">
		<ul class="mui-table-view" id="deptList"
			style="margin: 0px; padding: 0px;">

		</ul>
	</div>
	<div class="mui-content"
		style="margin: 0px; padding: 0px; position: fixed; top: 95px; right: 0px; bottom: 0px; width: 128px; overflow: auto">
		<ul class="mui-table-view" id="userList"
			style="margin: 0px; padding: 0px;">

		</ul>
	</div>
	<style>
.selected {
	background: #428bca;
	color: white;
}

.item {
	font-size: 14px;
}
</style>

	<script>
var selectedColor = "rgb(0, 51, 255)";
var RoleId,RoleName;
var moduleId = "<%=moduleId%>";
objSelectType = '<%=objSelectType%>';
var privOp = '<%=privOp%>';
var privNoFlag = "<%=privNoFlag%>";
var userFilter = parent.RANDOM_USER_FILTER;
var deptId = <%=deptId%>;
var contextPath = "<%=contextPath%>";
var parentWindowObj = window.dialogArguments;
var to_id_field = parent.document.getElementById("<%=to_id_field%>");//父级文本框对象Id
var to_name_field = parent.document.getElementById("<%=to_name_field%>");//父级文本框对象Name
var prcsId = "<%=prcsId%>";
var frpSid = "<%=frpSid%>";
var single_select = "<%=single_select%>";//是否是单用户选择
		var idsArray = [];//选中的ids
		var namesArray = [];//选中的names
		var userFilterArray = [];//用户过滤数组
		var loadedUsers = [];//已加载的用户
		var deptMap = {};
		var pidMap = {};

		function doSelect() {
			//window.top.userSelectDiv.style.display = "none";
			window.top.$("#userSelectDiv").remove();

		}

		function doInit() {

			if (single_select == "1") {
				single_select = true;
			} else {
				single_select = false;
			}

			initDepartmentsTree();

			//userFilterArray = eval("("+window.external.findPersonDatas(userFilter)+")");//获取用户过滤信息的数组，带排序

			//初始化
			if (to_id_field.value != "") {
				var sp = to_id_field.value.split(",");
				for (var i = 0; i < sp.length; i++) {
					idsArray.push(sp[i]);
				}

				var sp = to_name_field.value.split(",");
				for (var i = 0; i < sp.length; i++) {
					namesArray.push(sp[i]);
				}

				for (var i = 0; i < idsArray.length; i++) {
					renderItem({
						id : idsArray[i],
						name : namesArray[i]
					});
					loadedUsers.push(idsArray[i]);//加入已加载的用户
				}

			} else {
				doSearch();
			}
		}

		function initDepartmentsTree() {
			//var json = tools.requestJsonRs(contextPath+"/deptManager/getDeptTreeAll.action",{});
			var json = tools.requestJsonRs(contextPath
					+ "/orgSelectManager/getSelectDeptTreeWorkFlow.action", {
				privNoFlag : privNoFlag,
				moduleId : moduleId,
				prcsId:prcsId,frpSid:frpSid
			});
			//alert("人员过滤："+userFilter);
			var data = json.rtData;
			var rows = data.ztreeData;
			for (var i = 0; i < rows.length; i++) {
				if (!rows[i].children) {
					rows[i].children = [];
				}
				if (!pidMap[rows[i].pId]) {
					pidMap[rows[i].pId] = [];
				}
				//将其加入到集合中
				pidMap[rows[i].pId].push(rows[i]);

				if (!deptMap[rows[i].pId]) {//如果其父节点不存在，则进入到顶级节点中
					deptMap[rows[i].id] = rows[i];
				} else {//如果找到了父节点，则将当前节点以及之前节点加入父节点中
					deptMap[rows[i].id] = rows[i];
					var pNode = deptMap[rows[i].pId];
					if (!pNode.children) {
						pNode.children = [];
					}
					var array = pidMap[rows[i].pId];
					for (var j = 0; j < array.length; j++) {
						pNode.children.push(array[j]);
						array[j].hasParent = true;
					}
					pidMap[rows[i].pId] = [];
				}
			}

			//清空掉有父节点的数据
			//定义一个集合   用来存放部门集合
			var deptArray = [];
			var url1;
			var json1;
			var dept;
			for ( var node in deptMap) {
				if (!deptMap[node].hasParent) {
					//部门id
					url1 = contextPath + "/deptManager/getDeptByUuid.action";
					json1 = tools.requestJsonRs(url1, {uuid : deptMap[node].id});
					dept = json1.rtData;
					//alert(dept);
					deptArray.push(dept);

					//renderDept(0,deptMap[node]);
				}
			}
			//将deptArray中的数据根据deptNo排序
            deptArray=deptArray.sort(compare("deptNo")); 
			//循环遍历deptArray  渲染
			//alert(deptArray.length);
			for(var m=0;m<deptArray.length;m++){
				for ( var node in deptMap) {
				    if(deptArray[m].uuid==deptMap[node].id){    	
				    	renderDept(0,deptMap[node]);
				    }
				}
			}
			
		}



		
		
		
//定义一个比较器 
function compare(propertyName) { 
	return function (object1, object2) { 
	var value1 = object1[propertyName]; 
	var value2 = object2[propertyName]; 
	if (value2 > value1) { 
		return -1; 
	} 
	else if (value2 < value1) { 
		return 1; 
	} 
	else { 
		return 0; 
	} 
 } 
} 
		
function renderDept(level, node) {

			var children = node.children;

			//渲染node
			var render = [];
			render.push("<li class=\"mui-table-view-cell mui-media\" id=\"node"
					+ node.id + "\" onclick=\"showRightUser(" + node.id
					+ ")\" >");
			render.push("<div class=\"mui-media-body\">");
			var blank = "";
			var icon = contextPath
					+ "/common/jquery/ztree/css/zTreeStyle/img/diy/node_dept.gif";
			if (node.iconSkin == "pIconHome") {
				icon = contextPath
						+ "/common/jquery/ztree/css/zTreeStyle/img/diy/1_open.png";
			}
			icon = "<img src='"+icon+"' />&nbsp;&nbsp;";
			for (var i = 0; i < level; i++) {
				blank += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			}
			if (children.length != 0) {
				render.push(blank + icon + node.title);
			} else {
				render.push(blank + icon + node.title);
			}

			render.push("</div>");
			render.push("</li>");
			$("#deptList").append(render.join(""));

			//渲染子节点
			for (var i = 0; i < children.length; i++) {
				renderDept(level + 1, children[i]);
			}
		}

		function showRightUser(deptId) {
			$("#userList").html("");
			var json = tools.requestJsonRs(contextPath
					+ "/orgSelectManager/getSelectUserByDeptWorkFlow.action", {
				deptId : deptId,
				prcsId:prcsId,frpSid:frpSid
			});
			var rows = json.rtData;
			for (var i = 0; i < rows.length; i++) {
				renderItem({
					name : rows[i].userName,
					id : rows[i].uuid
				});
			}
		}

		/**
		 * 渲染项
		 */
		function renderItem(item) {
			var exists = false;
			for (var i = 0; i < idsArray.length; i++) {
				if (parseInt(idsArray[i]) == parseInt(item.id)) {
					exists = true;
					break;
				}
			}
			var render = [];
			render
					.push("<li uuid=" + item.id
							+ " class='item mui-table-view-cell "
							+ (exists ? "selected" : "")
							+ "' onclick='clickIt(this)'>");
			render.push("<a >" + item.name + "</a>");
			render.push("</li>");
			$("#userList").append(render.join(""));
		}

		function clickIt(obj) {
			var uuid = $(obj).attr("uuid");
			if ($(obj).attr("class").indexOf("selected") != -1) {//如果是已被选中的，则取消选中
				for (var i = 0; i < idsArray.length; i++) {
					if (parseInt(idsArray[i]) == parseInt(uuid)) {
						idsArray.splice(i, 1);
						namesArray.splice(i, 1);
						break;
					}
				}
				$(obj).removeClass("selected");
			} else {//如果未被选中，则开启选中
				if (single_select) {
					cancelAll();
				}
				$(obj).addClass("selected");
				idsArray.push(uuid);
				namesArray.push($(obj).text());
			}
			to_id_field.value = idsArray.join(",");
			to_name_field.value = namesArray.join(",");
		}

		/**
		 * 取消选择所有项
		 */
		function cancelAll() {
			$("#userList .selected").each(function(i, obj) {
				$(obj).removeClass("selected");
			});
			idsArray = [];
			namesArray = [];
			to_id_field.value = "";
			to_name_field.value = "";
		}

		/***
		 * 按人员ID和用户名模糊查询
		 */
		function doSearch() {
			var name = $("#searchBox").val();
			$("#userList").html("");//清空面板
			if (name == "") {
				return;
			}
			loadedUsers = [];//清空已加载的记录

			var hitCount = 0;
			var json = tools
					.requestJsonRs(
							contextPath
									+ "/orgSelectManager/getSelectUserByUserIdOrUserNameWorkflow.action",
							{
								user : name,
								prcsId:prcsId,frpSid:frpSid,
								privNoFlag : 0
							});

			for (var i = 0; i < json.rtData.length; i++) {
				loadedUsers.push(json.rtData[i].uuid);//加载当前用户
				renderItem({
					id : json.rtData[i].uuid,
					name : json.rtData[i].userName
				});//渲染当前用户
				hitCount = 1;
			}
			if (hitCount == 0) {
				$("#userList").html(
						"<br/><center style='font-size:12px'>请选择人员</center>");
			}
		}

		function loadMore() {
			var exists = false;
			for (var i = 0; i < userFilterArray.length; i++) {
				exists = false;
				for (var j = 0; j < loadedUsers.length; j++) {
					if (userFilterArray[i].uuid == loadedUsers[j]) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					renderItem({
						id : userFilterArray[i].uuid,
						name : userFilterArray[i].userName
					});//渲染当前用户
				}
			}
			$("#moreDiv").hide();
		}
	</script>
</body>
</html>