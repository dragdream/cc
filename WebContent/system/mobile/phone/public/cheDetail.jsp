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
	//确认归还
	 $("body").on("tap","#guiHuan",function(){
		 doObtain(sid);
	});
	//作废
	 $("body").on("tap","#zuoFei",function(){
		 doInvalid(sid);
	});
}

//作废
function doInvalid(sid){
	 if(window.confirm("是否作废此登记单吗?")){
	    	$.ajax({
				  type: 'POST',
				  url: contextPath+"/officeStockBillController/doInvalid.action",
				  data: {billIds:sid},
				  timeout: 10000,
				  async:false,
				  success: function(json){
					 json = eval("("+json+")");
					 if(json.rtState){
						 window.location.href="list/index.jsp?status="+status;
						 alert("已经作废");
					 }
				  },
				  error: function(xhr, type){
				    
				  }
				});
	    }
}

//确认领取、确认归还
function doObtain(sid){
	var url = contextPath+"/officeStockBillController/doObtain.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{billId:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				window.location.href="list/index.jsp?status="+status;
				alert(json.rtMsg);
			}else{
				alert(json.rtMsg);
			}
		}
	});	
	
}

//
//用品申请信息
function getInfoById(sid){
	var url =   "<%=contextPath%>/officeStockBillController/findStockPublic.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var prc = json.rtData;
				bindJsonObj2Cntrl(prc);
				var operFlagDesc=prc.operFlagDesc;
				if(operFlagDesc.indexOf("正在调度中") != -1){
					$("#operFlagDesc").val("正在调度中");
				}
				var render="";
				if(status==1){
					$("#zuoFei").show();
				}
				else if(status==2 || status==4){
					if(prc.obtainFlag==1){//已领取
						render = "已领取";
					}else if(prc.obtainFlag==0){//未领取
						if(prc.operFlag==1){
							render="未领取";
							$("#lingQu").show();
						}else{
							render= "---";
						}
					}else if(prc.obtainFlag==3){//归还中的话
						if(prc.operFlag==1){
							render="归还中";
							$("#guiHuan").show();
						}else{
							render="---";
						}
					}else if(prc.obtainFlag==4){
						render="已归还";
					}else{
						render="---";
					}
				}else{
					render="---";
				}
				
				$("#showFlag").val(render);
		}
	});
}

</script>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	<span class="mui-icon mui-icon-back" onclick="window.location.href='list/index.jsp?status=<%=status %>'"></span>
	<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover"></a>
	<h1 class="mui-title">用品登记详情</h1>
<!-- 	<div style="float: right;color: #0070ffc7;margin-top: 12px;" onclick="doSaveOrUpdate()">保存</div>
 --></header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>用品编码</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" name="productCode" id="productCode" readonly="readonly"/>
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
			<label>申请类型</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id="regTypeDesc" name="regTypeDesc" readonly="readonly"/>
		</div>
	</div>
		<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>申请/归还数量</label>
		</div>
		<div class="mui-input-row">
           	<input type="text" id="regCount" name="regCount" readonly="readonly"/>	
		</div>
	</div>
		<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>审核用户</label>
		</div>
		<div class="mui-input-row">
            <input type="text" name="auditorName" id="auditorName"  readonly="readonly"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>调度员</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" id="operatorName" name="operatorName" readonly="readonly"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>调度状态</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" id="operFlagDesc" name="operFlagDesc" readonly="readonly"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>操作</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" id="showFlag" name="showFlag" readonly="readonly"/>
		</div>
	</div>
		<div class="mui-input-group">
		<div class="mui-input-row">
			<label>登记时间</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" id="regTimeDesc" name="regTimeDesc" readonly="readonly"/>
		</div>
	</div>
		<div class="mui-input-group">
		<div class="mui-input-row">
			<label>审核时间</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" id="verTimeDesc" name="verTimeDesc" readonly="readonly"/>
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