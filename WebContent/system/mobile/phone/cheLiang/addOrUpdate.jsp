<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//车辆Id
int status = TeeStringUtil.getInteger(request.getParameter("status"), 0);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>用车申请</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	<span class="mui-icon mui-icon-back" onclick="window.location.href='index.jsp'"></span>
	<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover"></a>
	<h1 class="mui-title">用车申请</h1>
<!-- 	<div style="float: right;color: #0070ffc7;margin-top: 12px;" onclick="doSaveOrUpdate()">保存</div>
 --></header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>司机</label>
		</div>
		<div class="mui-input-row">
			<input type="hidden" name="sid" id="sid" value="0">
			<input type="text" placeholder="请填写司机" name="vuDriver" id="vuDriver">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>目的地</label>
		</div>
		<div class="mui-input-row">
			<input type="text" placeholder="请填写目的地" name="vuDestination" id="vuDestination">
		</div>
	</div>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>里程</label>
		</div>
		<div class="mui-input-row">
			<input type="text" placeholder="请填写里程" name="vuMileage" id="vuMileage">
		</div>
	</div>
		<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>选择车辆</label>
		</div>
		<div class="mui-input-row">
           <select id="vehicleId" name="vehicleId">
			  <option value="0">请选择</option>
		   </select>		
		</div>
	</div>
		<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>调度员</label>
		</div>
		<div class="mui-input-row">
            <select id="vuOperatorId" name="vuOperatorId">
				<option value="0">请选择</option>
			</select>(注：负责审批) 		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>起始时间</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id='vuStartStr' name='vuStartStr' class="Wdate BigInput" style="width: 160px" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>结束时间</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id='vuEndStr' name='vuEndStr'  class="Wdate BigInput" style="width: 160px" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>事由</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		    <textarea id="vuReason" name="vuReason" rows="5" cols="60" class="BigTextarea  easyui-validatebox"  required="true" ></textarea>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<textarea rows="5" cols="60" id="vuRemark" name="vuRemark" class="BigTextarea" ></textarea>
		</div>
	</div>
</form>	
</div>


<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover">
		<ul class="mui-table-view">
		   <li id="saveBtn" class="mui-table-view-cell" onclick="save(1);" style="display: none">保存</li>
		   <li class="mui-table-view-cell" onclick="doSaveOrUpdate()">保存</li>
		  </ul>
	</div>

<script>
var vehicleId="";
var vuOperatorId="";
var status=<%=status%>;
var sid=<%=sid%>;
function doInit(){
	if(sid>0){
		getInfoById(sid);
	}
	getVehicleList();
	getVuOperatorId();
	vuStartStr.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"datetime"});
		picker.show(function(rs) {
		vuStartStr.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
	//结束时间
	vuEndStr.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"datetime"});
		picker.show(function(rs) {
		vuEndStr.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
}
//查看车辆申请详情
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
//编辑
function doSaveOrUpdate(){
	  if(checkForm()){
		  var vehicleId = $("#vehicleId").val();
			var vuStartStr = $("#vuStartStr").val();
			var url = "<%=contextPath %>/vehicleUsageManage/isApply.action";
			mui.ajax(url,{
				type:"post",
				dataType:"html",
				data:{vehicleId:vehicleId,vuStartStr:vuStartStr},
				timeout:10000,
				success:function(json){
					json = eval("("+json+")");
					  if(json.rtState){
					    	if(json.rtData.type=='maintenancing'){
					    		alert(json.rtMsg);
					    		$("#vuStartStr").focus();
					    	}
					    	if(json.rtData.type=='using'){
					    		alert(json.rtMsg);
					    	}
					
					    }else{
					    	url = "<%=contextPath %>/vehicleUsageManage/addOrUpdate.action";
					   	    var param=formToJson("#form1");
					   		mui.ajax(url,{
					   			type:"post",
					   			dataType:"html",
					   			data:param,
					   			timeout:10000,
					   			success:function(json){
					   				json = eval("("+json+")");
					   				 if(json.rtState){
					   				    alert("保存成功");
					   				 window.location.href="daiPi/index.jsp?status="+status;
					   				 }
					   			}
					   		});
					    }
				}
			});
		  
		  
		  
		  
		  
	 
	  }
	}

//判断是否满足申请条件 1.车辆申请时间段是否在使用中，2.车辆申请时间段是否车辆正在进行维护
function isApply(){
	var vehicleId = $("#vehicleId").val();
	var vuStartStr = $("#vuStartStr").val();
	var url = "<%=contextPath %>/vehicleUsageManage/isApply.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{vehicleId:vehicleId,vuStartStr:vuStartStr},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			  if(json.rtState){
			    	if(json.rtData.type=='maintenancing'){
			    		alert(json.rtMsg);
			    		$("#vuStartStr").focus();
			    		
			    	}
			    	if(json.rtData.type=='using'){
			    		alert(json.rtMsg);
			    		
			    	}
			
			    }
		}
	});
}

function checkForm(){
	var vuOperatorId = $("#vuOperatorId").val();
	if(!vuOperatorId){
		//top.$.jBox.tip("调度人员不能为空！",'info',{timeout:1500});
		alert("调度人员不能为空！");
		return false;
	}
	return true;
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
								var personOptions = "";
								for(var i =0; i < json.rtData.length ; i ++){
									if(vuOperatorId==json.rtData[i].uuid){
										personOptions = personOptions +  "<option selected value='" + json.rtData[i].uuid +  "'>" + json.rtData[i].userName + "</option>"; 
									}else{
									    personOptions = personOptions +  "<option value='" + json.rtData[i].uuid +  "'>" + json.rtData[i].userName + "</option>"; 
									}
								}
								$("#vuOperatorId").append(personOptions);
							}
						});
						
					}
				}
			}
		}
	});
	
}
function getPersonListByUUids2(uuid){
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
function getSysParamByNames2(paramName){
	var url =   contextPath + "/sysPara/getSysParaList.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{paraNames:paramName},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				return json.rtData;
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
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
				var optionsStr = "";
				for(var i =0; i < vehicleObj.length ; i ++){
					if(vehicleId==vehicleObj[i].sid){
					   optionsStr += "<option selected value='" + vehicleObj[i].sid +  "'>" + vehicleObj[i].vModel + "</option>"; 
					}else{
					   optionsStr += "<option value='" + vehicleObj[i].sid +  "'>" + vehicleObj[i].vModel + "</option>"; 
					}
				}
				$("#vehicleId").append(optionsStr);
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
		}
	});
	
}

function selectPostVehicle2(){
	var data="";
	var url =   contextPath + "/vehicleManage/selectPostVehicle.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		//data:{state:-1,rows:20,page:page++},
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
	return data;
}
//初始化项目审批人
function initApprover(){
	var url=contextPath+"/projectApprovalRuleController/getApproverByLoginUser.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		//data:{state:-1,rows:20,page:page++},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				for(var i=0;i<data.length;i++){
					$("#approverId").append("<option value="+data[i].uuid+" >"+data[i].userName+"</option>");
				}
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