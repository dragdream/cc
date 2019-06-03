<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/header.jsp" %>
<title>Tenee办公自动化智能管理平台</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css">
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui-portal/jquery.portal.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui-portal/portal.css">
	
<script type="text/javascript" charset="UTF-8">
	var portal;
	var col;
	$(function() {
		col = $('#portal div').length;
		portal = $('#portal').portal({
			border : false,
			fit : true
		});
		$.ajax({
			url : '<%=request.getContextPath() %>/portalController.action?indexPortal&sort=order&order=asc',
			cache : false,
			dataType : "json",
			success : function(response) {
				if (response && response.rows) {
					var rows = response.rows;
					for ( var i = 0; i < rows.length; i++) {
						var src;
						if (/^\//.test(rows[i].src)) {/*以"/"符号开头的,说明是本项目地址*/
							src = rows[i].src.substr(1);/*获取相对路径*/
						} else {
							src = rows[i].src;
						}
						var p = $('<div style="overflow:hidden;"/>').appendTo('body').panel({
							title : rows[i].title,
							content : '<iframe src="' + src + '" frameborder="0" style="border:0;width:100%;height:99.2%;"></iframe>',
							height : rows[i].height,
							closable : rows[i].closable == true,
							collapsible : rows[i].collapsible == true
						});
						portal.portal('add', {
							panel : p,
							columnIndex : i % col
						});
					}
					portal.portal('resize');
				}
			}
		});
	});
</script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" style="overflow: hidden;" border="false">
		<div id="portal" style="position:relative;">
			<div></div>
			<div></div>
		</div>
	</div>
</body>
</html>