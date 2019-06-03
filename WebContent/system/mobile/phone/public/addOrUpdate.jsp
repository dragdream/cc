<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
int status = TeeStringUtil.getInteger(request.getParameter("status"), 0);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>用品登记</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	<span class="mui-icon mui-icon-back" onclick="window.location.href='index.jsp'"></span>
	<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover"></a>
	<h1 class="mui-title">用品登记</h1>
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>登记类型</label>
		</div>
		<div class="mui-input-row">
		  <select name="regType" id="regType">
		     <option value="1">领用</option>
			 <option value="2">借用</option>
			 <option value="3">归还</option>
		  </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>用品库</label>
		</div>
		<div class="mui-input-row">
			<select id="deposId" name="deposId" onchange="catList(this.value)" style="width:300px">
			  
			</select>
		</div>
	</div>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>用品类别</label>
		</div>
		<div class="mui-input-row">
			<select id="catId" name="catId" onchange="productList(this.value)" style="width:300px">
			
		   </select>
		</div>
	</div>
		<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>用品</label>
		</div>
		<div class="mui-input-row">
           	<select id="productId" name="productId" style="width:300px">
			
		    </select>		
		</div>
	</div>
		<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>申请/归还数量</label>
		</div>
		<div class="mui-input-row">
            <input type="text" name="regCount" id="regCount" class="BigInput easyui-validatebox" required="true" validType="number[] & integeBetweenLength[1,2147483647]"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="mui-input-row">
		   <textarea type="text" name="remark" id="remark"></textarea>
		</div>
	</div>
</form>	
</div>


<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover">
		<ul class="mui-table-view">
		   <li id="saveBtn" class="mui-table-view-cell" onclick="save(1);" style="display: none">保存</li>
		   <li class="mui-table-view-cell" onclick="commit()">保存</li>
		  </ul>
	</div>

<script>
var status=<%=status%>;
var sid=<%=sid%>;
function doInit(){
	 yongPin();
}

function yongPin(){
	//加载可用库信息
	var url = contextPath+"/officeDepositoryController/getDeposListWithNoPriv.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var depositories = json.rtData;
			var html = "<option value=\"0\"></option>";
			for(var i=0;i<depositories.length;i++){
				html+="<option value=\""+depositories[i].sid+"\">"+depositories[i].deposName+"</option>";
			}
			$("#deposId").html(html);
		}
	});	
}

//用品库
function catList(deposId){
	var url = contextPath+"/officeCategoryController/getCatListWithNoPriv.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{deposId:deposId},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var cats = json.rtData;
			var html = "<option value=\"0\"></option>";
			for(var i=0;i<cats.length;i++){
				html+="<option value=\""+cats[i].sid+"\">"+cats[i].catName+"</option>";
			}
			$("#catId").html(html);
		}
	});
	
}

//用品类别
function productList(catId){
	var url = contextPath+"/officeProductController/getProductWithPriv.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{catId:catId,regType:$("#regType")[0].value},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var rows = json.rtData;
			var html = "<option value=\"0\"></option>";
			for(var i=0;i<rows.length;i++){
				html+="<option value=\""+rows[i].sid+"\">"+rows[i].proName+"/(当前库存"+rows[i].curStock+")</option>";
			}
			$("#productId").html(html);
		}
	});
	
}

function commit(){
	if($("#deposId")[0].value=="0"){
        $.MsgBox.Alert_auto("请选择所属用品库");
		$("#deposId").focus();
		return;
	}
	if($("#catId")[0].value=="0" || $("#catId")[0].value==""){
		$.MsgBox.Alert_auto("请选择用品类型");
		$("#catId").focus();
		return;
	}
	if($("#productId")[0].value=="0" || $("#productId")[0].value==""){
        $.MsgBox.Alert_auto("请选择用品");
		$("#productId").focus();
		return;
	}

	//判断是否超出库存量
	if($("#regType").val()!="3"){
		var url = contextPath +"/officeStockBillController/checkOutOfStockCount.action";
		 mui.ajax(url,{
				type:"post",
				dataType:"html",
				data:{productId:$("#productId").val(),regCount:$("#regCount").val()},
				timeout:10000,
				success:function(json){
					json = eval("("+json+")");
					if(!json.rtData){
						alert("申请数量已超出库存量！");
						return ;
					}
				}
			});
	}
	 var param=formToJson("#form1");
	 var url = contextPath+"/officeStockBillController/addStockBill.action";
	 mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:param,
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
               alert("保存成功");
               window.location.href="list/index.jsp?status=1";
			}
		}
	});
}

function regTypeChanged(val){
	productList($("#catId").val());
}

</script>
<div id="mapFrameDiv" style="display:none;z-index:10000000;position:fixed;top:0px;bottom:0px;left:0px;right:0px;background:white">
<iframe id="mapFrame" frameborder="no" style="width:100%;height:100%"></iframe>
</div>

</body>
</html>