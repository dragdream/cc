<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String itemId = request.getParameter("itemId");
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header1.0.jsp" %>
<%@ include file="/header/bsgrid.jsp" %>
	<script type="text/javascript" charset="UTF-8">
	var itemId = '<%=itemId%>';
	var datagrid;
	
	$(function() {
		
		$('#table-bootstrap').bootstrapTable({
	        method: 'post',
	        url: contextPath + '/CommonWord/testDatagrid.action',
	        classes: 'table table-hover',
	        toolbar:"#toolbar",
	        columns: [{
				field : 'cyy',
				title : '常用语'
			},{
				field : 'cis',
				title : '使用频次'
			},{
				field : '_optmanage',
				title : '操作',
				width : 120,
				formatter : function(value, rowData, rowIndex) {
				     return "<button class='btn btn-default btn-xs' onclick=\"use("+rowIndex+")\">回填</button>";
				} 
			 }]
	      });
	});
	
	function use(index){
		var rowData = $('#table-bootstrap').bootstrapTable("getData")[index];
		var sid = rowData.sid;
		var cyy = rowData.cyy;
		
		var url = contextPath + "/CommonWord/wordCountPlus.action?sid="+sid;
		tools.requestJsonRs(url,{});
		
		xparent.CK_EDITOR_OBJ.setData(cyy);
		window.close();
	}

	</script>
</head>
<body>
<div class="GridContainer">
	  <div id="toolbar">
	  </div>
	  <table id="table-bootstrap"
	         data-pagination="true"
	         data-side-pagination="server"
	         data-page-list="[20,30,40,60,80,100]"
	         data-show-refresh="true"
	         data-show-columns="true" ></table>
</div>

</body>
</html>