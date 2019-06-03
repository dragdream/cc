
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String meetStatus = TeeStringUtil.getString(request.getParameter("meetStatus"), "0");//查询会议状态
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>会议申请管理</title>
<style>
.base_layout_center{
	position:absolute;
	top:0;
	left:151px;
	right:0px;
}
</style>
<script  type="text/javascript"  src='<%=contextPath %>/system/core/base/meeting/js/meeting.js'></script>


<script type="text/javascript">
var meetStatus = "<%=meetStatus%>";
/**
 * 查询类型  meetingStatus
0、	待批
1、	已批准
2、	进行中
3、	未批准
4、已结束
*/

var datagrid;
function  doOnload(){
	queryMeeting(meetStatus);
	
	//选中样式
	$("#option" + meetStatus).addClass("active");
}
/**
 *查询待审批记录
 */

function queryMeeting(meetStatus){
	
	var para = {status:meetStatus};
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/meetManage/getLeaderMeetByStatusEasyui.action",
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
			{field:'meetName',title:'会议名称',width:120,
				    		   formatter : function(value, rowData, rowIndex) {
									return "<a  href='javascript:void(0);' onclick='meetingDetail(" +rowData.sid + ")'>" + value + "</a>" ;
							    }	    		   
			},
			{field:'userName',title:'申请人',width:80},
			{field:'createTimeStr',title:'申请时间',width:100},
			{field:'startTimeStr',title:'开始时间',width:80},
			{field:'endTimeStr',title:'结束时间',width:80},
			{field:'meetRoomName',title:'会议室',width:100},
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
			{field:'_manage',title:'操作',width:120,formatter:function(value,rowData,rowIndex){
				var opts = "";
				var status = rowData.status;
				var id = rowData.sid;
				var userId = rowData.userId;
				if(status == 0 || status == 3){//待批和未批准
					opts =  opts + "<a href='javascript:void(0);'  onclick='approv(\"" + id + "\",1," + userId +");'>批准</a>"
					+  "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='approv(\"" + id + "\",3," + userId +");'>不批准</a>";
				}
				if(status == 1 || status == 2){//已批准，进行中
					opts += "<a href='javascript:void(0);'  onclick='showMeetingAttendInfo("+ id +");'>参会情况</a>&nbsp;&nbsp;";
					opts += "<a href='javascript:void(0);'  onclick='showMeetingReadInfo("+ id +");'>签阅情况</a>&nbsp;&nbsp;";
					opts += "<a href='javascript:void(0);'  onclick='toEndMeet(\"" + id + "\",4," + userId +");'>结束</a>";
				} 
				if(status==1 || status ==0 || status == 3){
					opts = opts + "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='toEdit(\"" + id + "\"," + status +");'>修改</a>"
						+  "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='deleteMeetingById(\"" + id + "\"," + status +");'>删除</a>";
				}else if(status==4){
					opts += "<a href='javascript:void(0);'  onclick='showMeetingAttendInfo("+ id +");'>参会情况</a>&nbsp;&nbsp;";
					opts += "<a href='javascript:void(0);'  onclick='showMeetingReadInfo("+ id +");'>签阅情况</a>&nbsp;&nbsp;";
					opts += "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='deleteMeetingById(\"" + id + "\"," + status +");'>删除</a>";
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
			      	 + "<td width='130' align='center'>会议名称</td>"
			      	// + "<td width='' align='center'>会议主题</td>"
			     	 +"<td nowrap width='80' align='center'>申请人</td>"
			     	 +"<td nowrap  width='100'  align='center'>申请时间</td>"
			     	 +"<td nowrap  width='100'  align='center'>开始时间</td>"
			     	 +"<td nowrap  width='100'  align='center'>结束时间</td>"
			     	 +"<td nowrap width='120' align='center'>会议室</td>"
			      	 +"<td nowrap  width='80' align='center'>操作</td>"
			         +"</tr>";
			for(var i = 0;i<prcs.length ; i++){
				var prc = prcs[i];
				var id = prc.sid;
				var userId = prc.userId;
				var status = prc.status;
				var  fontStr = "";
				var opts = "";  
				if(status == 0 || status == 3){//待批和未批准
					opts =  opts + "<a href='javascript:void(0);'  onclick='approv(\"" + id + "\",1," + userId +");'>批准</a>"
					+  "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='approv(\"" + id + "\",3," + userId +");'>不批准</a>";
				}
				if(status == 1 || status == 2){//已批准，进行中
					opts += "<a href='javascript:void(0);'  onclick='showMeetingAttendInfo("+ id +");'>参会情况</a>&nbsp;&nbsp;";
					opts += "<a href='javascript:void(0);'  onclick='showMeetingReadInfo("+ id +");'>签阅情况</a>&nbsp;&nbsp;";
					opts += "<a href='javascript:void(0);'  onclick='toEndMeet(\"" + id + "\",4," + userId +");'>结束</a>";
				} */
				
				/* if(status == 3){//不批准
					opts =  opts + "<a href='javascript:void(0);'  onclick='approv(\"" + id + "\",1," + userId +");'>批准</a>";
				} */
				/* if(status==1 || status ==0 || status == 3){
					opts = opts + "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='toEdit(\"" + id + "\"," + status +");'>修改</a>"
						+  "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='deleteMeetingById(\"" + id + "\"," + status +");'>删除</a>";
				}else if(status==4){
					opts += "<a href='javascript:void(0);'  onclick='showMeetingAttendInfo("+ id +");'>参会情况</a>&nbsp;&nbsp;";
					opts += "<a href='javascript:void(0);'  onclick='showMeetingReadInfo("+ id +");'>签阅情况</a>&nbsp;&nbsp;";
					opts += "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='deleteMeetingById(\"" + id + "\"," + status +");'>删除</a>";
				}
			
				tableStr = tableStr +"<tr class=''>"
						 +"<td  align='left'><font color='" + fontStr + "'><a href='javascript:void(0);' onclick='meetingDetail("+id+")' >"+ prc.meetName +"</a></font></td>"
						// +"<td nowrap   align='center'><font color='" + fontStr + "'>" + prc.subject + "</font></td>"	
						 +"<td  align='center' ><font color='" + fontStr + "'>" + prc.userName + "</font></td>"
						 +"<td nowrap   align='center'><font color='" + fontStr + "'>"+ prc.createTimeStr +"</font></td>"
				      	 +"<td nowrap align='center' ><font color='" + fontStr + "'>" +  prc.startTimeStr + "</font></td>"
				     	 +"<td nowrap align='center'  ><font color='" + fontStr + "'>"  + prc.endTimeStr + "</font></td>"
				     	 +"<td nowrap align='center'  ><font color='" + fontStr + "'>" + prc.meetRoomName + "</font></td>"
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
		alert(jsonObj.rtMsg);
	} */
}
//显示参会情况
function showMeetingAttendInfo(sid){
	var url = contextPath + "/system/core/base/meeting/leader/showMeetingAttendInfo.jsp?meetingId=" + sid ;
	bsWindow(url ,"查看参会情况",{width:"800",height:"360",buttons:
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
//显示会议签阅情况
function showMeetingReadInfo(sid){
	var url = contextPath + "/system/core/base/meeting/leader/showMeetingReadInfo.jsp?meetingId=" + sid ;
	bsWindow(url ,"查看会议签阅情况",{width:"800",height:"360",buttons:
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
 * 删除会议记录 ById
 * @param id
 */
function deleteMeetingById(id){
	var submit = function () {
// 	    if (v == 'ok'){
	    	var url = contextPath + "/meetManage/deleteById.action";
	    	var jsonRs = tools.requestJsonRs(url,{sid:id});
	    	if(jsonRs.rtState){
// 	    		$.jBox.tip('删除成功','info',{timeout:1000});
	    		$.MsgBox.Alert_auto('删除成功');
	    		queryMeeting(meetStatus);
	    		refreshParentLeaderCount();
	    	}else{
// 	    		alert(jsonRs.rtMsg);
	    		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	    	}
// 	    }
	};
// 	$.jBox.confirm("确定要删除吗,删除后不可恢复？", "提示", submit);
	$.MsgBox.Confirm("提示", "确定删除所选记录,删除后将不可恢复！", submit);
	
}



/*
 * 弹出修改会议
 */
function toEdit(id){
	var title = "编辑会议申请";
	var url = contextPath + "/system/core/base/meeting/personal/apply/addOrUpdate.jsp?id=" + id  + "&isLeaderOpt=1" ;
	bsWindow(url ,title,{width:"700",height:"360",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate(function(){
// 				$.jBox.tip("保存成功！", "info", {timeout: 1800});
				$.MsgBox.Alert_auto('审批成功');
				queryMeeting(meetStatus);
	    		refreshParentLeaderCount();
	    		//BSWINDOW.modal("hide");
			});
		}else if(v=="关闭"){
			return true;
		}
	}});
}



/**
 * 批准或者不批准
 @param  sid -Id
 @param  allow -允许
 @param  userId -用户Id
 */
function approv(sid , allow , userId){
	var allowDesc  = "确定要批准该会议吗？";
	if(allow == 3){
		allowDesc = "确定不批准该会议吗？";
	}
	var submit = function () {
// 	    if (v == 'ok'){
	    	var url = contextPath + "/meetManage/approve.action";
	    	var jsonRs = tools.requestJsonRs(url,{sid:sid , status:allow , userId: userId});
	    	if(jsonRs.rtState){
	    		//alert(jsonRs.rtMsg);
// 	    		$.jBox.tip('审批成功','info',{timeout:1000});
	    		$.MsgBox.Alert_auto('审批成功');
	    		queryMeeting(meetStatus);
	    		refreshParentLeaderCount();
	    		return true;
	    	}else{
	    		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	    	}
// 	    }
	};
// 	$.jBox.confirm(allowDesc, "提示", submit);
	$.MsgBox.Confirm("提示", allowDesc, submit);
}

/**
 * 结束会议
 */
 function toEndMeet(sid , allow , userId){
	var allowDesc  = "确定要结束该会议吗？";
	var submit = function () {
// 	    if (v == 'ok'){
	    	var url = contextPath + "/meetManage/approve.action";
	    	var jsonRs = tools.requestJsonRs(url,{sid:sid , status:allow , userId: userId});
	    	if(jsonRs.rtState){
	    		//alert(jsonRs.rtMsg);
// 	    		$.jBox.tip('结束成功','info',{timeout:1000});
                $.MsgBox.Alert_auto("结束成功");
	    		queryMeeting(meetStatus);
	    		refreshParentLeaderCount();
	    		return true;
	    	}else{
	    		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	    	}
// 	    }
	};
	$.MsgBox.Confirm("提示", allowDesc, submit); 
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


/**
 * 执行上级页面 审批数量
 */
function refreshParentLeaderCount(){
	window.parent.getLeaderCount();
}





</script>
</head>
<body class="" style="font-size:12px;padding-left: 10px;padding-right: 10px;" onload="doOnload();">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
<%-- <div class="base_layout_top" style="position:static">
    <img class = 'fl' style="margin-right: 10px; margin-top: 1px;margin-left: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/hygl/icon_会议管理1.png">
		<p class="title" style="padding-top: 3px;">会议管理</p>
</div> --%>
<!-- <div class="base_layout_center"> -->
<div class="fl" style="position:static;">
		    <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/hygl/hygl/icon_会议管理1.png">
		    <span class="title">会议管理</span>
	    </div>
<!-- 	<br/> -->
<!-- 	<div id='listDiv'></div> -->
<!-- </div> -->
</div>
</body>
</html>