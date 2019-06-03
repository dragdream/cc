<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//车辆Id
int status = TeeStringUtil.getInteger(request.getParameter("status"), 0);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>用品登记</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>

<script>
var status=<%=status%>;
var sid=<%=sid%>;
function doInit(){
	getInfoById(sid);
	//确认领取
	 $("body").on("tap","#lingQu",function(){
		 doObtain(sid);
	});

}
//调度
function doOperated(sid){
	var url = contextPath+"/officeStockBillController/doOperated.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{billId:sid},
		timeout:10000,
		success:function(json){
			//json = eval("("+json+")");
	    window.location.reload();
		}
	});
}

//用品申请信息
function getInfoById(sid){
	var url =   "<%=contextPath%>/officeStockBillController/findPublicDiaoDau.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var prc = json.rtData;
			bindJsonObj2Cntrl(prc);
			var obtainFlag="";
			if(prc.obtainFlag==1){
				obtainFlag= "已领取";
			}else if(prc.obtainFlag==0){
				obtainFlag= "未领取";
			}else if(prc.obtainFlag==3){
				obtainFlag= "归还中";
			}else if(prc.obtainFlag==4){
				obtainFlag= "已归还";
			}
			$("#status").val(obtainFlag);
			var operFlag="";
			if(prc.operFlag==0){
				operFlag= "<a href='javascript:void(0)' onclick='doOperated("+prc.sid+")'>开始调度</a>";
			}else if(prc.operFlag==1){
				operFlag= "<span style='color:green'>正在调度中</span>";
			}else{
				operFlag= "调度完毕";
			}
			$("#operFlag").html(operFlag);
		}
	});
}

</script>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	<span class="mui-icon mui-icon-back" onclick="window.location.href='list/diaoDuindex.jsp?status=<%=status %>'"></span>
	<a style="display: none;" class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover"></a>
	<h1 class="mui-title">用品调度详情</h1>
<!-- 	<div style="float: right;color: #0070ffc7;margin-top: 12px;" onclick="doSaveOrUpdate()">保存</div>
 --></header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>用品库</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" name="depositoryName" id="depositoryName" readonly="readonly"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>用品类别</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id="categoryName" name="categoryName" readonly="readonly"/>
		</div>
	</div>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>用品名称</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id="productName" name="productName" readonly="readonly"/>
		</div>
	</div>
		<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>类型</label>
		</div>
		<div class="mui-input-row">
           	<input type="text" id="regTypeDesc" name="regTypeDesc" readonly="readonly"/>	
		</div>
	</div>
		<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>数量</label>
		</div>
		<div class="mui-input-row">
            <input type="text" name="regCount" id="regCount"  readonly="readonly"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>操作</label>
		</div>
		<div class="mui-input-row" id="operFlag" style="margin-left: 4%">
<!-- 		   <input type="text" id="operatorName" name="operatorName" readonly="readonly"/>
 -->		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>物品登记员</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" id="regUserName" name="regUserName" readonly="readonly"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>登记状态</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" id="status" name="status" readonly="readonly"/>
		</div>
	</div>
		<div class="mui-input-group">
		<div class="mui-input-row">
			<label>完成时间</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" id="finishTimeDesc" name="finishTimeDesc" readonly="readonly"/>
		</div>
	</div>
</form>	
</div>
<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover">
		<ul class="mui-table-view">
		   <li class="mui-table-view-cell" id="lingQu" style="display: none;">确认领取</li>
		   <li class="mui-table-view-cell" id="guiHuan" style="display: none;">确认归还</li>
		   <li class="mui-table-view-cell" id="zuoFei" style="display: none;">作废</li>
		</ul>
	</div>

<div id="mapFrameDiv" style="display:none;z-index:10000000;position:fixed;top:0px;bottom:0px;left:0px;right:0px;background:white">
<iframe id="mapFrame" frameborder="no" style="width:100%;height:100%"></iframe>
</div>

</body>
</html>