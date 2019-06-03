<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//车辆
int status = TeeStringUtil.getInteger(request.getParameter("status"), 0);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>用车申请详情</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	<span class="mui-icon mui-icon-back" onclick="window.location.href='daiPi/index.jsp?status=<%=status%>'"></span>
	<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover"></a>
	<h1 class="mui-title">用车申请</h1>
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>司机</label>
		</div>
		<div class="mui-input-row">
			<input type="hidden" name="sid" id="sid" value="0">
			<input type="text" readonly="readonly" placeholder="请填写司机" name="vuDriver" id="vuDriver">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>目的地</label>
		</div>
		<div class="mui-input-row">
			<input type="text" readonly="readonly" placeholder="请填写目的地" name="vuDestination" id="vuDestination">
       
		</div>
	</div>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>里程</label>
		</div>
		<div class="mui-input-row">
			<input type="text" readonly="readonly" placeholder="请填写里程" name="vuMileage" id="vuMileage">
		</div>
	</div>
		<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>选择车辆</label>
		</div>
		<div class="mui-input-row">
		   <!--  <select id="vehicleId" name="vehicleId">
			  <option value="0">请选择</option>
		   </select> -->
		   <input type="text" readonly="readonly" name="vehicleId" id="vehicleId" />
		</div>
	</div>
		<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>调度员</label>
		</div>
		<div class="mui-input-row">
			<!-- <select id="vuOperatorId" name="vuOperatorId">
				<option value="0">请选择</option>
			</select>(注：负责审批)  -->	
			<input type="text" readonly="readonly" name="vuOperatorId" id="vuOperatorId"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>起始时间</label>
		</div>
		<div class="mui-input-row">
			<input type="text" readonly="readonly" id='vuStartStr' name='vuStartStr' class="Wdate BigInput" style="width: 160px" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>结束时间</label>
		</div>
		<div class="mui-input-row">
			<input type="text" readonly="readonly" id='vuEndStr' name='vuEndStr'  class="Wdate BigInput" style="width: 160px" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>事由</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		    <textarea id="vuReason" readonly="readonly" name="vuReason" rows="5" cols="60" class="BigTextarea  easyui-validatebox"  required="true" ></textarea>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<textarea rows="5" readonly="readonly" cols="60" id="vuRemark" name="vuRemark" class="BigTextarea" ></textarea>
		</div>
	</div>
</form>	
</div>


<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover">
		<ul class="mui-table-view">
		   <li class="mui-table-view-cell" style="display: none;" id="updateCheLiang">修改</li>
		   <li class="mui-table-view-cell" style="display: none;" id="deleteCheLiang">删除</li>
		   <li class="mui-table-view-cell" style="display: none;" id="backCheLiang">归还</li>
		  </ul>
	</div>

<script>
var sid=<%=sid%>;
var status=<%=status%>;
var vehicleId="";
var vuOperatorId="";
function doInit(){
	getInfoById(sid);
	getVehicleList();
	getVuOperatorId();
	if(status==0 || status==3){//修改和删除
		$("#updateCheLiang").show();
		$("#deleteCheLiang").show();
	}else if(status==2){//归还
		$("#backCheLiang").show();
	}else{//无
		
	}
	 $("body").on("tap","#updateCheLiang",function(){//修改
		 window.location.href="addOrUpdate.jsp?sid="+sid+"&status="+status;
	});
	 $("body").on("tap","#deleteCheLiang",function(){//删除
		 if(window.confirm("确定删除吗?")){
		    	$.ajax({
					  type: 'POST',
					  url: contextPath + "/vehicleUsageManage/deleteById.action",
					  data: {sid:sid},
					  timeout: 10000,
					  async:false,
					  success: function(json){
						  //alert("删除成功");
						 window.location.href="daiPi/index.jsp?status="+status;
					  },
					  error: function(xhr, type){
					    //alert('服务器请求超时!');
					  }
					});
		    }
	});
	 $("body").on("tap","#backCheLiang",function(){//归还
		 if(window.confirm("确定要归还该车辆吗?")){
		    	$.ajax({
					  type: 'POST',
					  url:contextPath + "/vehicleUsageManage/toEnd.action",
					  data: {sid:sid},
					  timeout: 10000,
					  async:false,
					  success: function(json){
						  alert("归还成功");
						 window.location.href="daiPi/index.jsp?status="+status;
					  },
					  error: function(xhr, type){
					    //alert('服务器请求超时!');
					  }
					});
		    }
	});
}

function getInfoById(sid){
	var url =   "<%=contextPath%>/vehicleUsageManage/getInfoById.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var prc = json.rtData;
			if (prc && prc.sid) {
				bindJsonObj2Cntrl(prc);
				vuOperatorId=prc.vuOperatorId;
				vehicleId=prc.vehicleId;
				$("#vuUserId").text(prc.vuUserName);
				$("#vehicleId").text(prc.vehicleName);
				$("#vuOperatorId").text(prc.vuOperatorName);
			}
		}
	});
}
function getVehicleList(){
	var url =   contextPath + "/vehicleManage/selectPostVehicle.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		//data:{state:-1,rows:20,page:page++},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var vehicleObj=json.rtData;
				//var optionsStr = "";
				for(var i =0; i < vehicleObj.length ; i ++){
					
					if(vehicleId==vehicleObj[i].sid){
					 // optionsStr = "<option value='" + vehicleObj[i].sid +  "'>" + vehicleObj[i].vModel + "</option>"; 
				      $("#vehicleId").val(vehicleObj[i].vModel);
					  break;
					}
				}
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
		}
	});
	
}
function getVuOperatorId(){
//审批人员
	var paramName = "VEHICLE_MANAGER_TYPE";
	//获取参数
	var url =   contextPath + "/sysPara/getSysParaList.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{paraNames:paramName},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var params=json.rtData;
			if(json.rtState){
				if(params.length > 0){
					var param = params[0];
					if(param.paraValue != "" ){
						var url =   contextPath + "/personManager/getPersonListByUuids.action";
						mui.ajax(url,{
							type:"post",
							dataType:"html",
							data:{uuid:param.paraValue},
							timeout:10000,
							success:function(json){
								json = eval("("+json+")");
								//var personOptions = "";
								for(var i =0; i < json.rtData.length ; i ++){
									if(vuOperatorId==json.rtData[i].uuid){
									   //personOptions = "<option value='" + json.rtData[i].uuid +  "'>" + json.rtData[i].userName + "</option>";
								       $("#vuOperatorId").val(json.rtData[i].userName);
									   break;
									}
								}
							}
						});
						
					}
				}
			}
		}
	});
	
}
function getPersonListByUUids(uuid){
	var url =   contextPath + "/personManager/getPersonListByUuids.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{uuid:uuid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				data=json.rtData;
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
		}
	});
}

</script>
<div id="mapFrameDiv" style="display:none;z-index:10000000;position:fixed;top:0px;bottom:0px;left:0px;right:0px;background:white">
<iframe id="mapFrame" frameborder="no" style="width:100%;height:100%"></iframe>
</div>

</body>
</html>