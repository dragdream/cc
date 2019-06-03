
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String meetStatus = TeeStringUtil.getString(request.getParameter("meetStatus"), "");//查询会议状态
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>会议申请管理</title>
<script  type="text/javascript"  src='<%=contextPath %>/system/core/base/meeting/js/meeting.js'></script>


<script type="text/javascript">
var meetStatus = "<%=meetStatus%>";
function  doOnload(){
	queryMeeting(meetStatus);
	
	//选中样式
	$("#option" + meetStatus).addClass("active");
}
/**
 *查询待审批记录
 */
function queryMeeting(meetStatus){
	var datagrid;
<%-- 	var url =   "<%=contextPath %>/meetManage/getPersonalMeetByStatus.action"; --%>
	var para = {};
	para['status']=meetStatus;
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/meetManage/getPersonalMeetByStatusEasyui.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
        queryParams:para,
        singleSelect: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
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
			{field:'meetName',title:'会议名称',width:120},
			{field:'subject',title:'会议标题',width:120},
			{field:'createTimeStr',title:'申请时间',width:100},
			{field:'startTimeStr',title:'开始时间',width:100},
			{field:'endTimeStr',title:'结束时间',width:100},
			{field:'meetRoomName',title:'会议室',width:80},
			{field:'managerName',title:'审批人',width:80},
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
			{field:'_manage',title:'操作',width:80,formatter:function(value,rowData,rowIndex){
				var opts = "";
				var status = rowData.status;
				var id = rowData.sid;
				if(status == 0 || status == 3){
					opts = opts + "<a href='javascript:void(0);'  onclick='toEdit(\"" + id + "\"," + status +");'>修改</a>"
					+  "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='deleteMeetingById(\"" + id + "\"," + status +");'>删除</a>";
				}
				//已审批的会议撤销操作
				if(status == 1 ){
					opts = opts + "<a href='javascript:void(0);'  onclick='cancel(\"" + id + "\");'>撤销</a>&nbsp;&nbsp;";
					opts += "<a href='javascript:void(0);'  onclick='showMeetingAttendInfo("+ id +");'>参会情况</a>&nbsp;&nbsp;";
					opts += "<a href='javascript:void(0);'  onclick='showMeetingReadInfo("+ id +");'>签阅情况</a>&nbsp;&nbsp;";
				}
				//进行中的会议操作
				if(status == 2 ){
					opts += "<a href='javascript:void(0);'  onclick='showMeetingAttendInfo("+ id +");'>参会情况</a>&nbsp;&nbsp;";
					opts += "<a href='javascript:void(0);'  onclick='showMeetingReadInfo("+ id +");'>签阅情况</a>&nbsp;&nbsp;";
				}
				return opts;
			}}
		]]
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

	/* var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		$("#listDiv").empty();
		if(prcs.length > 0){
			var tableStr = "<table class='TableList' width='99%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
			      	 + "<td width='100' align='center'>会议名称</td>"
			    	 + "<td width='100' align='center'>会议标题</td>"
			     	 +"<td nowrap  width='100'  align='center'>申请时间</td>"
			     	 +"<td nowrap  width='90'  align='center'>开始时间</td>"
			     	 +"<td nowrap  width='90'  align='center'>结束时间</td>"
			     	 +"<td nowrap width='80' align='center'>会议室</td>"
			      	 +"<td nowrap width='80' align='center'>审批人</td>"
			      	 //+"<td nowrap width='' align='center'>出席人</td>"
			      	 +"<td nowrap  width='60' align='center'>操作</td>"
			         +"</tr>";
			for(var i = 0;i<prcs.length ; i++){
				var prc = prcs[i];
				var id = prc.sid;
				var userId = prc.userId;
				var status = prc.status;
				var  fontStr = "";
				var opts = "";  
				if(status == 0 || status == 3){
					opts = opts + "<a href='javascript:void(0);'  onclick='toEdit(\"" + id + "\"," + status +");'>修改</a>"
					+  "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='deleteMeetingById(\"" + id + "\"," + status +");'>删除</a>";
				}
				//已审批的会议撤销操作
				if(status == 1 ){
					opts = opts + "<a href='javascript:void(0);'  onclick='cancel(\"" + id + "\");'>撤销</a>&nbsp;&nbsp;";
					opts += "<a href='javascript:void(0);'  onclick='showMeetingAttendInfo("+ id +");'>参会情况</a>&nbsp;&nbsp;";
					opts += "<a href='javascript:void(0);'  onclick='showMeetingReadInfo("+ id +");'>签阅情况</a>&nbsp;&nbsp;";
				}
				
				//进行中的会议操作
				if(status == 2 ){
					opts += "<a href='javascript:void(0);'  onclick='showMeetingAttendInfo("+ id +");'>参会情况</a>&nbsp;&nbsp;";
					opts += "<a href='javascript:void(0);'  onclick='showMeetingReadInfo("+ id +");'>签阅情况</a>&nbsp;&nbsp;";
				}
				
				var attendDesc = "<span class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>外部人员:</span>" + prc.attendeeOut ;
				attendDesc = attendDesc +  "<br><font class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>内部人员:</font>" + prc.attendeeName;
			
				tableStr = tableStr +"<tr class=''>"
						 +"<td nowrap  align='left'><font color='" + fontStr + "'><a href='javascript:void(0);' onclick='meetingDetail("+id+")' >"+ prc.meetName +"</a></font></td>"
						 +"<td nowrap   align='left'><font color='" + fontStr + "'>" + prc.subject + "</font></td>"	
						 +"<td nowrap   align='center'><font color='" + fontStr + "'>"+ prc.createTimeStr +"</font></td>"
				      	 +"<td nowrap align='center' ><font color='" + fontStr + "'>" + prc.startTimeStr + "</font></td>"
				     	 +"<td nowrap align='center'  ><font color='" + fontStr + "'>" + prc.endTimeStr + "</font></td>"
				     	 +"<td nowrap align='center'  ><font color='" + fontStr + "'>" + prc.meetRoomName + "</font></td>"
				    	
				     	 +"<td nowrap align='center' ><font color='" + fontStr + "'>" + prc.managerName + "</font></td>"
					     	//+"<td  align='left'  ><font color='" + fontStr + "'>" + attendDesc + "</font></td>"
					     +"<td nowrap align='center'  >"
					     +opts
					     +"</td>"
				         +"</tr>";
			}
			tableStr = tableStr + "</tbody></table>";	
			$("#listDiv").append(tableStr);	
		}else{
		 	messageMsg("暂无" + MEETING_STATUS_TYPE_DESC[meetStatus] + "会议记录", "listDiv" ,'' ,380);
		}
	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	} */
}


/**
 * 删除会议记录 ById
 * @param id
 */
function deleteMeetingById(id){
	var submit = function () {
	    //if (v == 'ok'){
	    	var url = contextPath + "/meetManage/deleteById.action";
	    	var jsonRs = tools.requestJsonRs(url,{sid:id});
	    	if(jsonRs.rtState){
	    		//$.jBox.tip('删除成功','info',{timeout:1000});
                $.MsgBox.Alert_auto('删除成功');
	    		queryMeeting(meetStatus);
	    	
	    	}else{
	    		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	    	}
	    //}
	};
	//$.jBox.confirm("确定要删除吗,删除后不可恢复？", "提示", submit);
    $.MsgBox.Confirm("提示", "确定删除所选记录,删除后将不可恢复！", submit);
	
}


/*
 * 弹出修改会议
 */
function toEdit(id){
	var title = "编辑会议申请";
	var url = contextPath + "/system/core/base/meeting/personal/apply/addOrUpdate.jsp?id=" + id ;
	bsWindow(url ,title,{width:"700",height:"360",buttons:
		[
		 {name:"保存",classStyle:"modal-save btn-alert-blue"},
	 	 {name:"关闭",classStyle:"modal-btn-close btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			var isOk = cw.doSaveOrUpdate();
			return isOk;
		}else if(v=="关闭"){
			window.location.reload();
			return true;
		}
	}});
}

/**
 * 点击查询会议类型
 */
function setMeetType(meetStatus){
	if(meetStatus || meetStatus == 0){
		queryMeeting(meetStatus);
	}else{
		window.location.href = "<%=contextPath%>/system/core/base/meeting/personal/index.jsp";
		
	}
	
}


function cancel(id){
	
			var url = contextPath + "/system/core/base/meeting/personal/cancelReason.jsp";
			bsWindow(url ,"会议撤销",{width:"600",height:"200",buttons:
				[
				 {name:"撤销",classStyle:"btn btn-primary"},
			 	 {name:"关闭",classStyle:"modal-btn-close btn-alert-gray"}
				 ]
				,submit:function(v,h){
				var cw = h[0].contentWindow;	
				if(v=="撤销"){
					if(cw.checkFrom()){
						var reason=cw.form1.reason.value;
						var url = contextPath + "/meetManage/cancel.action";
						var jsonRs = tools.requestJsonRs(url,{sid:id,reason:reason});
						if(jsonRs.rtState){
							//$.jBox.tip('撤销成功','info',{timeout:1000});
                            $.MsgBox.Alert_auto('撤销成功');
						}
						doOnload();
						return true;
					}
					
				}else if(v=="关闭"){
					return true;
				}
			}});

}



//显示参会情况
function showMeetingAttendInfo(sid){
	var url = contextPath + "/system/core/base/meeting/leader/showMeetingAttendInfo.jsp?meetingId=" + sid ;
	bsWindow(url ,"查看参会情况",{width:"800",height:"360",buttons:
	   [{name:"关闭",classStyle:"modal-btn-close btn-alert-gray"}]
	,submit:function(v,h){
	  var cw = h[0].contentWindow;
	  if(v=="修改"){
	    
	  }else if(v == "删除"){
	    
	  }else if(v=="关闭"){
	    return true;
	  }
	}});
	
}
//显示会议签阅情况
function showMeetingReadInfo(sid){
	var url = contextPath + "/system/core/base/meeting/leader/showMeetingReadInfo.jsp?meetingId=" + sid ;
	bsWindow(url ,"查看会议签阅情况",{width:"800px",height:"360px",buttons:
	   [{name:"关闭",classStyle:"modal-btn-close btn-alert-gray"}]
	,submit:function(v,h){
	  var cw = h[0].contentWindow;
	  if(v=="修改"){
	    
	  }else if(v == "删除"){
	    
	  }else if(v=="关闭"){
	    return true;
	  }
	}});
	
}

</script>
</head>
<body class="" topmargin="5" onload="doOnload();">


<table id="datagrid" fit="true"></table>
	<div id="toolbar" style="border-width: 0 0 0px 0;!important;">
		<!-- <div class="moduleHeader">
			<b class="pull-left"><i class="glyphicon glyphicon-user"></i>&nbsp;会议申请管理</b>
			
			<div class="btn-group pull-right" data-toggle="buttons">
			  <label class="btn btn-default " onclick="setMeetType();">
			    <input type="radio" name="options" id="option" >会议申请
			  </label>
			   <label class="btn btn-default"  onclick="setMeetType(0);"  id="option0">
			    <input type="radio" name="options">待审批会议
			  </label>
			  <label class="btn btn-default"  onclick="setMeetType(1);" id="option1">
			    <input type="radio" name="options" id="option1">已审批会议
			  </label>
			   <label class="btn btn-default"  onclick="setMeetType(2);"  id="option2">
			    <input type="radio" name="options">进行中会议
			  </label>
			   <label class="btn btn-default"  onclick="setMeetType(3);"  id="option3">
			    <input type="radio" name="options" >未批会议
			  </label>
			</div>
			<div style="clear:both"></div>
		</div> -->

	<div id='listDiv'>
		

	

		<!-- <input type="hidden" name="meetRoomSid" id="meetRoomSid" /> -->
	</div>
		

</body>

</html>