<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.tree.js'></script>
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.treegrid.js'></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/js/fileManage.js?v=1"></script>

<title>会议上报列表</title>
</head>
<script>
//初始化
function doInit(){
	getList();
}

//获取分类列表数据
function getList(){
	var para = {} ;
	$("#treeGrid").datagrid({
		    url: contextPath+'/teePbDutyTypeController/findDutyTypeList.action',
		    idField: 'sid',
	        toolbar:"#toolbar",
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
	        pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
	        border:false,
			queryParams:para,
			fit : true,
			fitColumns : false,
			nowrap : true,
			sortOrder: 'desc',
			striped: true,
			remoteSort: true,
			/* rownumbers: true, */
			singleSelect:false,
            columns:[[
                {
      				field:'typeName',
      				title:'值班类型',
      				width:260
      			},{
      				field:'sease',
      				title:'值班描述',
      				width:560
      		    },{
      		    	field:'number',
      		    	title:'时段数里',
      		    	width:100
      		    },{
      		    	field:'_optmanage',
      		    	title:'操作',
      		    	width:100,
      				formatter : function(value, rowData, rowIndex) {
      					var optStr = "";
      				    optStr += "<a href='javascript:void(0);' onclick='addOrUpdate("+rowData.sid+")'>修改 </a>&nbsp;";
  						optStr += "<a href='javascript:void(0);' onclick='deleteDutyType("+rowData.sid+")'>删除 </a>&nbsp;";
      					return "<div align='center'>" + optStr + "</span>";
      				} 
      		    }
      		]]
        
	});
}


//删除
function deleteDutyType(sid){
	$.MsgBox.Confirm ("提示", "是否确认删除该类型？", function(){
		  var url = contextPath + "/teePbDutyTypeController/deleteDutyType.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){					
				$.MsgBox.Alert_auto("删除成功！");
				doInit();
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}  
	  });
}

//新建/编辑
function addOrUpdate(sid){
		var url=contextPath+"/system/core/base/onduty/dutyType/addOrUpdate.jsp?sid="+sid;
		var title="";
		var mess="";
		if(sid>0){
			title="编辑会议";
			mess="编辑成功！";
		}else{
			title="添加会议";
			mess="保存成功！";
		}
		
		bsWindow(url ,title,{width:"800",height:"400",buttons:
			[
	         {name:"保存",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
			   var a=cw.commit();
			   if(a){
				   $.MsgBox.Alert_auto(mess);
				   doInit();
				   return true;
			   }
			   return false;
			}else if(v=="关闭"){
				return true;
			}
		}});
		
}
</script>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
	<table id="treeGrid" fit="true"></table>
	<div id="toolbar" class = "topbar clearfix">
	   <div class="fl" style="position:static;">
		  <img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/exam/imgs/icon_tkgl.png">
		  <span class="title">值班类型维护</span>
	   </div>
	   <div class = "right fr clearfix">
	      <input type="button" class="btn-win-white fl" onclick="addOrUpdate(0)" value="添加"/>
	   </div>
	</div>
</body>


</html>