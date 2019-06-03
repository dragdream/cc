<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>套红模板</title>
	
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';

	
	
	window.onload=function(){
		//初始化的时候加载套红类型
		getSysCodeByParentCodeNo("THLX","wordModelType");
		
		//默认选列表第一个
		var options=$("select option");
		//alert(options.length);
		if(options.length>1){
			//alert(options[1]);
			$(options[1]).attr("selected","selected");
		}
		
		//获取当前的套红类型
		var wordModelType=$("#wordModelType").val();
		getModelList(wordModelType);
	}
	
	 //获取套红的列表
	function getModelList(wordModelType){
		var url = contextPath+"/wordModel/getWordModelListByPriv.action";
		var json = tools.requestJsonRs(url,{wordModelType:wordModelType});
		if(json.rtState){
			var list = json.rtData;
			var arr = [];
			for(var i=0;i<list.length;i++){
				arr.push("<div style='padding:5px;border-bottom:1px dotted gray;cursor:pointer;' class='hov' onclick='xparent.DOC_ADD_HEADER("+list[i].attachId+");CloseWindow();'>"+list[i].modelName+"</div>");
			}
			$("#content").html(arr.join(""));
		
	    }
	}
	 
	 
	 $(function(){
		//给套红类型绑定一个change事件
		$("#wordModelType").bind("change",function(){
			//获取套红类型的值
			var  type=$("#wordModelType").val();
			getModelList(type);		
		});
		 
		 
	 });
	</script>
<style>
.hov:hover{
background:#9dc2db;
}
</style>
</head>
<body>
<div style="text-align:center;padding:10px;background:#428bca;color:white">
	<b>套红模板列表&nbsp; &nbsp; &nbsp; &nbsp;  类型
	 <select id="wordModelType" name="wordModelType" >
	   <option value="">全部</option>
	</select></b>
</div>
<div id="content" style="font-size:12px;text-align:center">
	
</div>
</body>
</html>