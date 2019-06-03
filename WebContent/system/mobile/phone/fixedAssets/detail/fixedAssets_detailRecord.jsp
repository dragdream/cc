<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>   
<%
  int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>

<!DOCTYPE HTML>
<html>
<head>
<title>折旧记录</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
#topPopover {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 120px;
}
#topPopover .mui-popover-arrow {
	left: auto;
	right: 6px;
}
</style>
</head>
<body onload="getList();">
	<header class="mui-bar mui-bar-nav">
		<h1 class="mui-title">折旧记录</h1>
		<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover"></a>
	</header>
	<div class="mui-content">
		    <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed" id="data">
		        
		        
		    </ul>
		</div>
	
	<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover">
		<ul class="mui-table-view">
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?sid=<%=sid %>';">资产详情</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'fixedAssets_detailTurn.jsp?sid=<%=sid %>';">资产流向</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'fixedAssets_detailRecord.jsp?sid=<%=sid%>';">折旧记录</li>
		  </ul>
	</div>
	
	<script>
	var sid=<%=sid %>;
	
	
	//获取详情
	function getList(){
		var url=contextPath+"/teeFixedAssetsRecordController/selectDeprecRecordsByAssetsId.action?assetId="+sid;
		
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:null,
			timeout:10000,
			async:false,
			success:function(text){
				var json = eval("("+text+")");
				//if(json.rtState){
					var html=[];
					var prcs=json.rtData;
					if(prcs.length>0){
						for(var i=0;i<prcs.length;i++){
							var prc = prcs[i];
							html.push("<li class=\"mui-table-view-cell\">"+
						            "<div class=\"mui-table\">"+
			                        "<div class=\"mui-table-cell mui-col-xs-10\">"+
			                        "<h5>资产原值："+prc.original+"</h4>"+
			                        "<h5>本月净值："+prc.deprecRemainValue+"</h5>"+
			                        "<h5>折旧年月："+getFormatDateStr(prc.deprecTime,"yyyy-MM-dd")+"</h5>"+
			                        "</div>"+
			                        "</div>"+
			                        "</li>");
						}
						$("#data").append(html.join(""));
					}
					
				//}else{
				//	alert("数据获取失败！");
				//}
			}
		});

	}

	</script>
</body>
</html>