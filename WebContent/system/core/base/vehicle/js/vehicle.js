/**
 * 车辆审批状态
 */
var VEHICLE_STAUS_TYPE = ["待审批" , "已批准" ,"进行中" ,"未审批","已结束"];

/**
 * 车辆状态
 */
var VEHICLE_STAUS = ["可用" , "损坏" ,"维修中" ,"报废"];

/**
 * 获取车辆 ---  根据权限
 * @param 
 *
 */
function selectPostVehicle(){

	var url =   contextPath + "/vehicleManage/selectPostVehicle.action";
	var para = {};
	var jsonRs = tools.requestJsonRs(url , para);
	if(jsonRs.rtState){
		return jsonRs.rtData;
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}

/**
 * 获取所有车辆 、
 * @param 
 *
 */
function getAllVehicle(){
	var url =   contextPath + "/vehicleManage/getAllVehicles.action";
	var para = {};
	var jsonRs = tools.requestJsonRs(url , para);
	if(jsonRs.rtState){
		return jsonRs.rtData;
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}



/**
 * 查看车辆申请详情
 * @param id
 */
function vehicleApplyDetail(id){
  var url = contextPath + "/system/core/base/vehicle/personal/applyVehicleDetail.jsp?id=" + id ;
  bsWindow(url ,"查看车辆申请详情",{width:"750",height:"360",buttons:
     [{name:"关闭",classStyle:"btn-alert-gray"}]
  ,submit:function(v,h){
    var cw = h[0].contentWindow;
    if(v=="修改"){
      
    }else if(v == "删除"){
      
    }else if(v=="关闭"){
      return true;
    }
  }});
}

/**
 * 查看车辆申请详情
 * @param id
 */
function vehicleJBoxDetail(id){
  var url = contextPath + "/system/core/base/vehicle/personal/applyVehicleDetail.jsp?id=" + id ;
  $.jBox("iframe:"+url,
      {title:"查看车辆申请详情",width:700,height:500,buttons: {"关闭":true}});

}

/**
 * 编辑车辆申请详情
 * @param id
 */
function editVehicleApply(id,vehicleSid){
  var url = contextPath + "/system/core/base/vehicle/personal/applyVehicle.jsp?sid=" + id+"&vehicleSid="+vehicleSid;
  bsWindow(url ,"查看车辆申请详情",{width:"750px",height:"360px",buttons:
     [{name:"保存",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
  ,submit:function(v,h){
    var cw = h[0].contentWindow;
    if(v=="保存"){
    	cw.doSaveOrUpdate(function(){
			$.MsgBox.Alert_auto("修改成功！");
		    datagrid.datagrid("reload");
		   
		});
    	 return true;
    }else if(v == "删除"){
      
    }else if(v=="关闭"){
      return true;
    }
  }});
}


/**
 * 删除车辆申请详情
 * @param id
 */
function deleteVehicleApply(id){
	
	  $.MsgBox.Confirm ("提示","确定要删除该数据？", function(){
		  var url = contextPath + "/vehicleUsageManage/deleteById.action?sid=" + id;
	      var json = tools.requestJsonRs(url);
	      if(json.rtState){
	    	 $.MsgBox.Alert_auto("删除成功！"); 
	         datagrid.datagrid("reload");
	      }
	  });
}

/**
 * 审批处理
 * @param sid
 * @param status
 */
function approvalFunc(sid,status){
  var remaindStr = "确定要批准该车辆申请吗？";
  var mess="已批准";
  if(status ==3){
    remaindStr = "确定不批准该车辆申请吗？";
    mess="已拒绝";
  }
  
  
  $.MsgBox.Confirm ("提示", remaindStr, function(){
	  var para = {sid:sid,status:status};
      var url = contextPath + "/vehicleUsageManage/approval.action";
      var json = tools.requestJsonRs(url,para);
      if(json.rtState){
    	window.parent.getLeaderCount();
    	$.MsgBox.Alert_auto(mess);
        datagrid.datagrid("reload");
      }  
  });

}

/**
 * 查看车辆申请详情
 */
function vehicleUseDetail(){
  var url = contextPath + "/system/core/base/vehicle/leader/vehicleUseManage.jsp" ;
  bsWindow(url ,"查看车辆使用情况",{width:"900",height:"400",buttons:
     [{name:"关闭",classStyle:"btn-alert-gray"}]
  ,submit:function(v,h){
    var cw = h[0].contentWindow;
    if(v=="修改"){
      
    }else if(v == "删除"){
      
    }else if(v=="关闭"){
      return true;
    }
  }});
}




