<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基本详情</title>
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

<script type="text/javascript">
var sid = <%=sid%>;

function doInit(){
	if(sid > 0){
		getInfoBySid(sid);
	}
}




//获取详情
function getInfoBySid(){
	var url = "<%=contextPath%>/TeeFixedAssetsInfoController/getAssetsInfoById.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{sid:sid},
		timeout:10000,
		async:false,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){	
				bindJsonObj2Cntrl(json.rtData);
				var attaches = json.rtData.attacheModels;
			
				var html="";
				for(var i=0;i<attaches.length;i++){
					var item =attaches[i];
					html+="<p><a href='javascript:void(0);' onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "</a></p>";
				}
				$("#attachs").append(html);
				
			}else{
				alert("数据获取失败！");
			}
		}
	});

}
</script>
</head>

<body onload="doInit();" style="overflow:auto; margin-bottom: 10px;">
    <header class="mui-bar mui-bar-nav">
		<h1 class="mui-title">资产详情</h1>
		<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover"></a>
	</header>
	<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>资产编号</label>
			<span id="assetCode" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>资产名称</label>
			<span id="assetName" style="line-height:40px"></span>
		</div>	
		<div class="mui-input-row">
			<label>资产类别</label>
			<span id="typeName" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>增加类别</label>
			<span id="addKindDesc" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>规格型号</label>
			<span id="assetsVersion" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>所属部门</label>
			<span id="deptName" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>制造商</label>
			<span id="madein" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>保管人</label>
			<span id="keeperName" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>资产性质</label>
			<span id="assetKindDesc" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>经销商</label>
			<span id="dealer" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>使用年限</label>
			<span id="useYears" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>资产原值</label>
			<span id="assetVal" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>发票日期</label>
			<span id="receiptDateStr" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>残值</label>
			<span id="assetBal" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>折旧年限</label>
			<span id="assetYear" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>残值率</label>
			<span id="assetBalRate" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>折旧方式</label>
			<span id="depreciationDesc" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>启用时间</label>
			<span id="valideTimeDesc" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>物理位置</label>
			<span id="physicalLocation" style="line-height:40px"></span>
		</div>
		<div class="mui-input-row">
			<label>备注</label>
			<span id="remark" style="line-height:40px"></span>
		</div>
		
	</div>
	
	<div class="mui-input-group">
	   <div class="mui-input-row">
			<label>资产图片</label>
		</div>
		<div id="attachs" style="margin-left: 15px;">
		
		</div>
	</div>
</div>


<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover">
		<ul class="mui-table-view">
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?sid=<%=sid %>';">资产详情</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'fixedAssets_detailTurn.jsp?sid=<%=sid %>';">资产流向</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'fixedAssets_detailRecord.jsp?sid=<%=sid%>';">折旧记录</li>
		  </ul>
	</div>
</body>
</html>