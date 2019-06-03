
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>会议申请管理</title>
<link  href="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="<%=contextPath%>/system/core/base/meeting/js/meeting.js"></script>
<script type="text/javascript">
function  doOnload(){
	query();
}
/**
 *查询待审批记录
 */
function query(){
<%-- 	var url =   "<%=contextPath %>/meetRoomManage/getAllRoom.action"; --%>
	var para = {};
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/meetRoomManage/getAllRoomEasyui.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		rownumbers:true,
        queryParams:para,
        singleSelect: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		nowrap:false,
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			/*{field:'ck',checkbox:true,formatter:function(value,rowData,rowIndex){
			        			
		        				 if(!rowData.userId ){
// 	        	                       return {
// 	        	                    	   disabled: true 	   
// 	        	                       };
		        					return '$(".datagrid-row[datagrid-row-index=" '+ 0 + '"] input[type="checkbox"]")[0].disabled = true';
			                         }else{
// 			                        	 return {
// 		        	                    	   disabled: false	   
// 		        	                       };
			                        	 $(".datagrid-row[datagrid-row-index=" + rowIndex + "] input[type='checkbox']")[0].disabled = false;
			                         }
		        			 }
		        			},*/
            //{field:'ck',checkbox:true},
            {field:'mrName',title:'会议室名称',width:100,
				    		   formatter : function(value, rowData, rowIndex) {
									return "<a href='javascript:void(0);'  onclick='meetingRoomDetail(\"" + rowData.sid + "\");'>"+ value +"</a>" ;
							    }	    		   
			},
			{field:'mrPlace',title:'所在地址',width:80},
			{field:'mrCapacity',title:'可容纳人数',width:100},
// 			{field:'mrdesc',title:'会议室描述',width:100},
			{field:'mrDevice',title:'设备情况',width:100},
			{field:'managerNames',title:'会议室管理员',width:120},
			
 			    {field:'postDeptNames',title:'申请权限(部门)',width:400},
 			    {field:'postUserNames',title:'申请权限(人员)',width:400},
			
			/* {field:'sex',title:'性别',width:50,formatter:function(value,rowData,rowIndex){
		        				
		        				if(rowData.sex == 0)
		        					return "男";
		        				else
		        					return "女";
		        				}}, */
			/* {field:'deptName',title:'单位名称',width:150},
			{field:'telNoDept',title:'工作电话',width:100},
			{field:'mobilNo',title:'手机',width:100},
			{field:'email',title:'邮箱',width:150}, */
			{field:'_manage',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
				var opts = "";
				var id = rowData.sid;
				opts = opts+"<a href='javascript:void(0);'  onclick='toAddOrUpdate(\"" + id + "\");'>编辑</a>"
		      	 +"&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleteRoom(" + id+ ");' >删除</a>";
				return  opts;
			}}
		]],
        /* onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
            if (data.rows.length > 0) {
                //循环判断操作为新增的不能选择
                for (var i = 0; i < data.rows.length; i++) {
                    //根据operate让某些行不可选
                    if (!data.rows[i].userId ) {
                        $("input[type='checkbox']")[i + 1].disabled = true;
                        $("input[type='checkbox']")[i + 1].style.display = "none";
                    }
                }
            }
        }, */
        
	 
});
	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});
// 	var jsonObj = tools.requestJsonRs(url , para);
// 	if(jsonObj.rtState){
// 		var prcs = jsonObj.rtData;
// 		$("#listDiv").empty();
// 		if(prcs.length > 0){
// 			var tableStr = "<table class='TableList' width='99%' align='center'><tbody id='listTbody'>";
// 		    	 tableStr = tableStr + "<tr class='TableHeader'>"
// 		    		 + "<td width='40' align='center'  nowrap=nowrap'>序号</td>"
// 			      	 + "<td width='100' align='center' nowrap=nowrap'>会议室名称</td>"
// 			      	 +"<td nowrap width='80' align='center' nowrap=nowrap'>所在地址 </td>"
// 			     	 +"<td nowrap  width='100'  align='center' nowrap=nowrap'>可容纳人数</td>"
// 		/* 	     	 +"<td nowrap  width='90'  align='center'nowrap=nowrap' >会议室描述</td>" */
// 			     	 +"<td nowrap  width='90'  align='center'nowrap=nowrap'>设备情况</td>"
// 			      	 +"<td nowrap  width=''  align='center' nowrap=nowrap'>会议室管理员</td>"
// 			      	 +"<td nowrap  width=''  align='center' nowrap=nowrap'>申请权限</td>"
// 			      	 +"<td nowrap  width='80' align='center' nowrap=nowrap'>操作</td>"
// 			         +"</tr>";
// 			for(var i = 0;i<prcs.length ; i++){
// 				var prc = prcs[i];
// 				var id = prc.sid;
// 				var userId = prc.userId;
// 				var  fontStr = "";
// 				var postDesc = "<span class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>部门:</span>" + prc.postDeptNames ;
// 		    	postDesc = postDesc +  "<br><font class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>人员:</font>" + prc.postUserNames;
// 				tableStr = tableStr +"<tr class='TableData'>"
// 						 +"<td nowrap  align='center'><font color='" + fontStr + "'>"+ (i+1) +"</font></td>"
// 						 +"<td nowrap  align='center'><font color='" + fontStr + "'><a href='javascript:void(0);'  onclick='meetingRoomDetail(\"" + id + "\");'>"+ prc.mrName +"</a></font></td>"
// 						 +"<td nowrap   align='center'><font color='" + fontStr + "'>" + prc.mrPlace + "</font></td>"	
// 						 +"<td nowrap   align='center'><font color='" + fontStr + "'>"+ prc.mrCapacity +"</font></td>"
// 			/* 	      	 +"<td nowrap align='center' ><font color='" + fontStr + "'>" + prc.mrdesc + "</font></td>" */
// 				     	 +"<td nowrap align='center'  ><font color='" + fontStr + "'>" + prc.mrDevice + "</font></td>"
// 				     	 +"<td nowrap align='center' ><font color='" + fontStr + "'>" + prc.managerNames + "</font></td>"
// 				     	 +"<td nowrap align=''  ><font color='" + fontStr + "'>" + postDesc + "</font></td>"
// 				         +"<td nowrap align='center'  >"
// 				      	 +"<a href='javascript:void(0);'  onclick='toAddOrUpdate(\"" + id + "\");'>编辑</a>"
// 				      	 +"&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleteRoom(" + id+ ");' >删除</a>"
// 				      	 +"</td>"
// 				         +"</tr>";
// 			}
// 			tableStr = tableStr + "</tbody></table>";	
// 			$("#listDiv").append(tableStr);	
// 		}else{
// 		 	messageMsg("暂无相关会议室信息", "listDiv" ,'' ,380);
// 		}
// 	}else{
// 		alert(jsonObj.rtMsg);
// 	}
}

/**
 * 新增或者修改
 */
function toAddOrUpdate(id){
	var url = contextPath + "/system/core/base/meeting/room/addOrUpdate.jsp?sid=" + id;
	var title = "新增会议室";
	if(id>0){
		title = "编辑会议室";
	}
	bsWindow(url ,title,{width:"650",height:"370",buttons:
		[
		{name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			var isTrue = cw.doSaveOrUpdate();
			if(isTrue){
				window.location.reload();
				return true;
			}
			
		}else if(v=="关闭"){
			return true;
		}
	}});
}

/**
 * 单个删除
 */
function deleteRoom(id){
	
	var submit = function(){
		deSigle(id);
	}
	
	var deSigle = function () 
	 {
		var url="<%=contextPath %>/meetRoomManage/isExistUnfinishedMeeting.action";
		var para = {sid:id};
		var json=tools.requestJsonRs(url,para);
		
		if(json.rtState){
			var url =   "<%=contextPath %>/meetRoomManage/deleteById.action";
			var jsonObj = tools.requestJsonRs(url , para);
			if(jsonObj.rtState){
				$.MsgBox.Alert_auto("删除成功");
				window.location.reload();
			}else{
				$.MsgBox.Alert_auto(jsonObj.rtMsg);
			} 	
		}else{
			$.MsgBox.Alert_auto("该会议室正在被申请或正在使用中，暂且不可删除！");
		}
	
	}
	$.MsgBox.Confirm("提示", "确定删除所选记录,删除后将不可恢复！", submit);
}
/**
 * 删除所有会议室
 */
function deleteAll(){
	var submit = function(){
		deSigle(id);
	}
	var deSigle = function ()
	{
		var url =   "<%=contextPath %>/meetRoomManage/deleteAll.action";
		var para = {};
		var jsonObj = tools.requestJsonRs(url , para);
		if(jsonObj.rtState){
			$.MsgBox.Alert_auto("删除成功");
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto(jsonObj.rtMsg);
		}
	}
	$.MsgBox.Confirm("提示", "确定删除所有会议室吗，删除后将不可恢复！", submit);
}
 
</script>
</head>
<body class="" topmargin="5" onload="doOnload();" style="padding-left: 10px;padding-right: 10px;">
<table id="datagrid" fit="true"></table>
<div id="toolbar" style="min-width:950px;border-bottom:none;">
	<table width="100%">
		<tr style="height:40px;">
			<td>
				 <img class = 'fl' style="margin-right: 10px; margin-top: 3px;margin-left: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/hygl/icon_会议管理1.png">
		         <p class="title" style="padding-top: 4px;">管理会议室</p>
			</td>
			<td align=right>
				<input type="button" class='btn-win-white fr' style="margin-right:70px;margin-bottom: 0px;" value="新增会议室" onclick="toAddOrUpdate(0);">
				&nbsp;
			</td>
		</tr>
	</table>

<span class="basic_border fl" style="margin-top:-1px;"></span>
</div>
<!-- <div class="base_layout_center" style="margin-bottom: -9px;"> -->
<!-- 	<br/> -->
<!-- 	<div id='listDiv'></div> -->
<!-- </div> -->
</body>

</html>