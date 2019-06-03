
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>车辆信息管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript" src="<%=contextPath%>/system/core/base/vehicle/js/vehicle.js"></script>
<script type="text/javascript">
var datagrid ;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:"<%=contextPath %>/vehicleManage/getAllVehicle.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:200}, */
			{field:'vModel',title:'车牌号',width:100,formatter:function(value,rowData,rowIndex){
				   return "<a href='javascript:void(0);' onclick='detail("+rowData.sid+");'>"+ rowData.vModel +"</a>";
				}},
			{field:'vNum',title:'厂牌型号',width:100,formatter:function(value,rowData,rowIndex){
				return "<a href='javascript:void(0);' onclick='detail("+rowData.sid+");'>"+ rowData.vNum +"</a>";
				}},
			{field:'vDriver',title:'司机',width:100},
			{field:'vType',title:'类型',width:100,formatter:function(value,rowData,rowIndex){
               var vType=rowData.vType;
			   return getvTypeFunc(vType);
			}},
			{field:'status',title:'状态',width:100,formatter:function(value,rowData,rowIndex){
	               var status=rowData.status;
				   return VEHICLE_STAUS[status];
		    }},
			{field:'postDesc',title:'申请权限',width:200,formatter:function(value,rowData,rowIndex){
				   var postDesc = "<span  style='font-weight:bold;'>部门:</span>" + rowData.postDeptNames ;
			       postDesc = postDesc +  "<br><font style='font-weight:bold;'>人员:</font>" + rowData.postUserNames;
		           return postDesc;
			}},
			{field:'ope_',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
				var html="<a href='javascript:void(0);'  onclick='toAddOrUpdate(\"" + rowData.sid + "\");'>编辑</a>"
		      	 +"&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleteById(" + rowData.sid+ ");' >删除</a>";
		      	return html;
			}}
		]]
	});
}



function getvTypeFunc(id){
	var str = "";
	if(id == "01"){
		str = "轿车";
	}else if(id == "02"){
		str = "面包车";
	}else if(id == "03"){
		str = "越野车";
	}else if(id == "04"){
		str = "吉普车";
	}else if(id == "05"){
		str = "巴士";
	}else if(id == "06"){
		str = "工具车";
	}else if(id == "07"){
		str = "卡车";
	}
	return str;
}


/**
 * 新增或者修改
 */
function toAddOrUpdate(id){
	var url = contextPath + "/system/core/base/vehicle/vehicleinfo/addOrUpdate.jsp?sid=" + id;
	var title = "新增车辆";
	if(id>0){
		title = "编辑车辆";
	}
	bsWindow(url ,title,{width:"650",height:"350",buttons:
		[
		{name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h,f,d){
			var cw = h[0].contentWindow;
			if(v=="保存"){
				cw.doSaveOrUpdate(function(flag){
					if(!flag){
						return false;
					}
					$.MsgBox.Alert_auto("保存成功！");
					datagrid.datagrid("reload");
					d.remove();
					
				});
				
			}else if(v=="关闭"){
				return true;
			}
		}
	});
}

/**
 * 单个删除
 */
function deleteById(id){
	 $.MsgBox.Confirm ("提示", "确定删除该车辆吗，删除后将不可恢复！", function(){
		    var url =   "<%=contextPath %>/vehicleManage/deleteById.action";
			var para = {sid:id};
			var jsonObj = tools.requestJsonRs(url , para);
			if(jsonObj.rtState){
				 $.MsgBox.Alert_auto("删除成功！");
				 datagrid.datagrid("reload");
			}else{
				$.MsgBox.Alert_auto(jsonObj.rtMsg);
			} 
	  });
}
/**
 * 删除所有会议室
 */
function deleteAll(){
	 $.MsgBox.Confirm ("提示", "确定删除所有车辆信息吗，删除后将不可恢复！", function(){
		 var url =   "<%=contextPath %>/vehicleManage/deleteAll.action";
			var para = {};
			var jsonObj = tools.requestJsonRs(url , para);
			if(jsonObj.rtState){
				 query();
				 $.MsgBox.Alert_auto("全部删除成功！");
			}else{
				$.MsgBox.Alert_auto(jsonObj.rtMsg);
			}  
	  });
}

function detail(sid){
	var url=contextPath+"/system/core/base/vehicle/vehicleinfo/detail.jsp?sid="+sid;
	bsWindow(url ,"车辆信息详情",{width:"800",height:"350",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
			if(v=="保存"){
				return true;
			}else if(v=="关闭"){
				return true;
			}
		}
	});
}
 
</script>
</head>
<body onload="doInit();" style="padding-right: 10px;padding-left: 10px">
  <table id="datagrid" fit="true"></table>
  <div id="toolbar" class = "topbar clearfix">
     <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/clgl/clxxgl.png">
		<span class="title">车辆信息管理</span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class='btn-win-white' value="新增车辆" onclick="toAddOrUpdate(0);">
     </div>
  </div>
</body>


</html>