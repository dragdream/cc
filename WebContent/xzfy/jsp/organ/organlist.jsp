<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>机构列表</title>
	<script type="text/javascript" src="/xzfy/js/common/common.js"></script>
</head>

<body style="padding-left: 10px;padding-right: 10px" onload="doInit();">

   <div id="toolbar" class = " clearfix" style="margin-top: 5px">
	    <div class="fl" style="position:static">
		    <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/jhrw/icon_任务中心.png">
		    <span class="title">机构列表 </span>
	    </div>
	    <div class = "right fr clearfix">
	        <!--  
			<input type="button" class="btn-win-white" onclick="exportExcel()" value="导出"/>
	        -->
	    </div>
	    <span class="basic_border_grey" style="margin-top: 10px"></span>
	    
	    <div class="setHeight">
	        <form id="form1" style="">
	            <input type="hidden" id="treeId" value="" >
	            <table class="none_table" width="100%">
	 				<tr>
	 					<td class="TableData TableBG" >机关层级：</td>
 						<td class="TableData">
	 						<select class="BigSelect" id="organLevel" name="organLevel">
							
							</select>
	 					</td>
	 					<td class="TableData TableBG">机关名称：</td>
	 					<td class="TableData">
	 					    <input class="BigInput" type="text" name="organName" id="organName"
	 					        onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\w]/g,'')" />
	 					</td>
	 					<td class="TableData TableBG">联系人：</td>
	 					<td class="TableData">
	 					    <input class="BigInput" type="text" name="contacts" id="contacts"
	 					        onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\w]/g,'')" />
	 					</td>
	 					
 						<td class="TableData"><button class="btn-win-white" type="button" onclick="search()">查询</button></td>
	 				</tr>
				</table>
			</form>
		</div>
   	</div>
    <table id="datagrid" fit="true"></table> 
    <iframe id="exportIframe" style="display:none"></iframe>
    <script type="text/javascript" src="/xzfy/js/organ/organlist.js"></script>
    
</body>
</html>