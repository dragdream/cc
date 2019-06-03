
/**
 * 获取管理范围的部门 ---- 日+ 周视图
 * @param deptId
 */
function getLeaderPostDept(deptId , moduleId){
	if(!fromView){
		fromView = 'week';
	}
	var url =  contextPath + "/calendarManage/getLeaderPostDept.action?deptId=" + deptId  
	 	+ "&fromView=" + fromView
	 	+ "&moduleId=" + moduleId;
	var config = {
				zTreeId:"deptIdZTree",
				requestURL:url,
	           	onClickFunc:onclickDept,
				async:false,
				onAsyncSuccess:setDeptParentSelect
	};
	zTreeObj = ZTreeTool.config(config);
} 

/**
 * 获取管理范围的部门 ---- 月视图
 * @param deptId
 */
function getLeaderMonthPostDept(moduleId){
	var url =  contextPath + "/calendarManage/getLeaderMonthPostDept.action?moduleId=" + moduleId;;
	var config = {
				zTreeId:"deptIdZTree",
				requestURL:url,
	           	onClickFunc:onclickUser,
				async:false,
				onAsyncSuccess:setPersonSelect
	};
	zTreeObj = ZTreeTool.config(config);
}


var userDatas = new Array();//用户数组
/**
 * 初始化后选中节点,上级部门
 * rtMsg : 当deptSize 为0为  此参数为人员id字符串，以逗号分隔
 */
function setDeptParentSelect(deptSize , rtMsg){//

	if(deptSize > 0){
		ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
		var nodeName = "";
	    if(ZTreeObj == null){
	    	setTimeout('setDeptParentSelect()',500);
	    }else{
	    	ZTreeObj.expandAll(true);
	    	var node ;
	    	if(rtMsg){
	    		node = ZTreeObj.getNodeByParam("id",rtMsg,null);
	    	}else{
	    		node = ZTreeObj.getNodeByParam("id",$("#deptId").val(),null);
	    	}
	    	
	    	if(node){
	    	   	ZTreeObj.selectNode(node);
	    	   	$("#deptId").val(node.id);
	    	   	nodeName = node.name;
	    	}
	    }  
	    ZTreeTool.inputBindZtree(ZTreeTool.zTreeId,'deptId',nodeName);
		
	}else{
		var dataJson = eval('(' + rtMsg + ')');
		userDatas = dataJson.userDatas;
		$("#userIds").val(dataJson.userIds);
	}
	getCalendar();
}

/**
 * 选择人员事件
 * @param dateList
 * @param rtMsg
 */
function setPersonSelect(dateList,rtMsg){
	ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
	var nodeName = "";
    if(ZTreeObj == null){
    	setTimeout('setPersonSelect()',500);
    }else{
    	ZTreeObj.expandAll(true);
    	var node ;
    	if(rtMsg){
    		node = ZTreeObj.getNodeByParam("id",rtMsg,null);
    	}else{
    		node = ZTreeObj.getNodeByParam("id",$("#deptId").val(),null);
    	}
    	
    	if(node){
    	   	ZTreeObj.selectNode(node);
    	   	$("#deptId").val(node.id);
    	   	nodeName = node.name;
    	}
    }  
    ZTreeTool.inputBindZtree(ZTreeTool.zTreeId,'deptId',nodeName);
	getCalendar();
}

/**
 * 点击部门
 * @param event
 * @param treeId
 * @param treeNode
 */
function onclickDept (event, treeId, treeNode) {
	if($("#deptId").val() != treeNode.id){//如果没改变
		$("#deptIdName").val(treeNode.name);
		$("#deptId").val(treeNode.id);
		getCalendar();
	}
	ZTreeTool.hideZtreeMenu();
}
/**
 * 点击人员
 * @param event
 * @param treeId
 * @param treeNode
 */
function onclickUser (event, treeId, treeNode) {
	if($("#deptId").val() != treeNode.id && treeNode.id.split(";").length <2){//如果没改变别是人员
		$("#deptIdName").val(treeNode.name);
		$("#deptId").val(treeNode.id);
		getCalendar();
		ZTreeTool.hideZtreeMenu();
	}
	
}



/**
 * 根据部门Id 获取人员列表 ---带权限
 * @param deptId
 */
function getUserByDeptId(deptId , moduleId){
	var url =   contextPath + "/calendarManage/getSelectUserByDept.action";
	var para =  {deptId:deptId , moduleId: moduleId,privNoFlag:1 ,userFilter:'0' };
	var jsonObj = tools.requestJsonRs(url,para);
	if(jsonObj.rtState){
		return jsonObj;
	}else{
		alert(jsonObj.rtMsg);
	}
}

/**
 * 创建Table ---周视图
 * @param dateList  一周时间
 * @param currDate 系统当天时间
 */
function createWeekTable(dateList , currDate){
	var tableStr  = "<table id='cal_table' class='TableBlock' width='100%' align='center'>"+
			"<tbody id='tbody'>"+
				"<tr id='tbl_header' align='center' class='TableHeader'>"+
				"<td width='8%' nowrap='true'>姓名</td>";
	for(var i =0; i <dateList.length ; i++){
		var td = "<td id='th_"+dateList[i].simpDate +"' align='center' nowrap  width='13%' onclick='' title='双击为下边所有人员建立日事务'>"+
					"<span >"+ dateList[i].fullDate + "</span>"+
				  "</td>";
		tableStr = tableStr + td;
	}
	tableStr = tableStr +
			"</tr>"+
			"</tbody>"+
			"</table>";	
	$("#tableDiv").append(tableStr);
}

/**
 * 创建Table ---日视图
 * @param dateList  一周时间
 * @param currDate 系统当天时间
 */
function createDayTable(dateList , currDate){
	var tableStr  = "<table id='cal_table' class='TableBlock' width='100%' align='center'>"+
			"<tbody id='tbody'>"+
				"<tr id='tbl_header' align='center' class='TableHeader'>"+
				"<td width='8%' nowrap='true'>姓名</td>";
	for(var i =0; i <dateList.length ; i++){
		var td = "<td id='th_"+dateList[i].simpDate +"' align='center' nowrap  width='90%' onclick='' title='双击为下边所有人员建立日事务'>"+
					"<span >"+ dateList[i].fullDate + "</span>"+
				  "</td>";
		tableStr = tableStr + td;
	}
	tableStr = tableStr +
			"</tr>"+
			"</tbody>"+
			"</table>";	
	$("#tableDiv").append(tableStr);
}

/**
 * 新建日程表格 --- 月视图 ---
 * @param dateList  日期数组
 * @param currDate  当前日期
 */
function createMonthTable(dateList , currDateStr , userId){
	var table = "<table id= cal_table class='TableBlock' width=100% align=center'> <tbody id = 'tboday'><tr align='center' class='TableHeader'>"
	      +"<td width='2%' nowrap align='center' style='padding-left:0px;'><b>周数</b></td>"
	      +"<td width='14%'><b>星期一</b></td>"
	      +"<td width='14%'><b>星期二</b></td>"
	      +"<td width='14%'><b>星期三</b></td>"
	      +"<td width='14%'><b>星期四</b></td>"
	      +"<td width='14%'><b>星期五</b></td>"
	      +"<td width='14%'><b>星期六</b></td>"
	      +"<td width='14%'><b>星期日</b></td>"
	      +"</tr></tbody></table>";
	$('#tableDiv').append(table);
	  //跨天的tr
	var tr = "<tr id='spanMonth' class='TableData' align='' style='display:none'>"
	       +"<td class='TableContent' align='center'>跨天</td>"
	       +"<td id='spanMonthCalendar' colspan='7'></td>"
	     +"</tr>";
	$('#tboday').append(tr);
	for ( var i = 0; i < dateList.length ; i++) {
		var weekth = dateList[i].weekth;
		var trStr = "<tr id='' class='TableData' align='' style=''>";
		var dateArray  = dateList[i].dateList;
		var tdStr = "<td align='center' style='padding-left:0px;'>" + weekth+  "</td>";
	
		for ( var j = 0; j < dateArray.length; j++) {
			var date = dateArray[j];
			var day = date.day;
			var isCurrMonth = date.isCurrMonth;
			var tdClassStyle = "";
  	        if(date.simpDate ==currDateStr){//如果今天的话
  	            tdClassStyle= "background-color:rgb(252, 248, 227);";
  	        }
			if(isCurrMonth){
				tdStr = tdStr + 
			      "<td id='td_"+userId +"_" + date.simpDate +"' onclick='toNewCalendarByDay("+userId+",\""+(date.simpDate)+"\")' valign='top'   style='min-height:80px;height:80px;"+tdClassStyle+"' title='双击建立日事务'><div align='right' class='TableContent'  title='' style='cursor:pointer;width: 100%;height:20px;'><font color='blue'  align='right'><b >"+day+"</b></font></div></td>";
			}else{
				tdStr = tdStr + 
			      "<td  valign='top' style='min-height:80px;height:80px;"+tdClassStyle+"'><div align='right' class='TableContent'  style='width: 100%;height:20px;'><font color=''  align='right'>" + day + "</font></div></td>";
			}
		}
		trStr = trStr + tdStr + "</tr>";
		
		$('#tboday').append(trStr);
	}
	 
}



/**
 * 获取日期星期几
 */
function setWeekDate(date)
{
	if(!date)
	{
		return;
	}
	date = date.substr(0, 10);
	var ymd = date.split('-');
	var day = new Date();
	day.setFullYear(ymd[0], parseInt(ymd[1] ,10 ) - 1,parseInt(ymd[2]));
	var result = day.getDay();
	if(result == 0)
	{
		result = 7;
	}
	return result;
}


/**
 * 设置视图
 * @param view  跳转至视图
 * #param currView 当前视图
 */
function setCalendarView(view , currView){
	var deptId = $("#deptId").val();
	var dateStr = "";
	if(currView && currView == 'month'){//如果是月视图
		//dateStr = dateStr[0].dateList[0].simpDate;
		dateStr = $("#year").val() + "-" + $("#month").val() + "-01"; 
	}else{
		dateStr = dateList[0].simpDate;
	}

	if(view == 'day'){
		window.location.href = contextPath + "/system/mobile/phone/calendar_search/leader/day.jsp?deptId=" + deptId + "&dateStr=" + dateStr + "&fromView=" + currView;
	}else if(view == 'week'){
		window.location.href = contextPath + "/system/mobile/phone/calendar_search/leader/week.jsp?deptId=" + deptId + "&dateStr=" + dateStr + "&fromView=" + currView;
	}else if(view == 'month'){
		
		window.location.href = contextPath + "/system/mobile/phone/calendar_search/leader/month.jsp?deptId=" + deptId + "&dateStr=" + dateStr + "&fromView=" + currView;
	}

}
/**
 * 弹出新建日程
 * @param userId
 * @param date
 */
function toNewCalendarByDay(userId , date )
{
	var userName = $("#td_"+userId+"_name").html();//得到用户姓名
	if(!userName){
		userName = $("#deptIdName").val();//从月视图获取
	}
	var url = contextPath + "/system/mobile/phone/calendar_search/leader/addOrUpdate.jsp?userId=" + userId + "&date=" + date + "&userName=" + encodeURIComponent(userName);
	bsWindow(url ,"新建日程/周期性事务",{width:"600",height:"300",buttons:[
	    {name:"保存",classStyle:"btn btn-primary"},
	    {name:"关闭",classStyle:"btn btn-primary"}
	    ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			//cw.commit();
			var isStatus = cw.doSaveOrUpdateAffair();
			if(isStatus){
				getCalendar();
				return true;
			}
		}else if(v=="关闭"){
			return true;
		}
	}});
}


/**
 * 弹出修改日程
 * @param userId
 * @param date
 */
function toUpdateCalendarByDay(id)
{
	var url = contextPath + "/system/mobile/phone/calendar_search/leader/addOrUpdate.jsp?sid=" + id;
	bsWindow(url ,"修改日程/周期性事务",{width:"600",height:"300",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			//cw.commit();
			var isStatus = cw.doSaveOrUpdateAffair();
			if(isStatus){
				getCalendar();
				return true;
			}
		}else if(v=="关闭"){
			return true;
		}
	}});
}
/**
 * 点击日程
 * @param cal
 */
function toClickCalendar(cal){

	var url = contextPath + "/system/core/base/calendar/detail.jsp?id=" + cal.sid ;
	if(cal.calAffType == 1){
		url =  contextPath + "/system/core/base/calendar/affair/detail.jsp?id=" + cal.sid ;
	}
	bsWindow(url ,"日程/周期性事务查看",{width:"500px",height:"200px",buttons:
		[
		 {name:"修改",classStyle:"btn btn-primary"},
		 {name:"删除",classStyle:"btn btn-danger"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
	,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="修改"){
			toUpdateCalendarByDay(cal.sid);
		}else if(v == "删除"){
			var isClose = false;
			var submit = function (v, h, f) {
			    if (v == 'ok'){
			    	isClose = deleteCalAff(cal.sid);
			    	if(isClose){
			    		window.BSWINDOW.modal("hide");
			    		getCalendar();
			    	}
			    	isClose =  true;
			    }
			    isClose =  true; //close
			};
			$.jBox.confirm("确定要删除吗？", "提示", submit);
			//return isClose;
		}else if(v=="关闭"){
			return true;
		}
	}});
	
}





/**
**创建周期性事务
*/
function createAffairList(affairList){


	$.each(affairList , function(i , prc){
		//for ( var i = 0; i < calendarList.length; i++) {
			//var prc = calendarList[i];
	        var seqId = prc.sid;
	        var userId = prc.userId;
	        var userName = prc.userName;
	        var managerName = prc.managerName;
	        var overStatus = prc.overStatus;
	        var status = prc.status;
	        var calLevel = prc.calLevel;
	        var calType = prc.calType;
	        var content = prc.content;
	        var startTimeStr = prc.startTimeStr;
	        var startTimeTempStr = prc.startTimeTempStr;
	        var endTime = prc.endTimeStr;
	        var remindType = prc.remindType;
	        var remindTime = prc.remindTimeStr;
	
	  	  	var week_day_month = '';
			var time = startTimeStr.substr(0,19)+" 至 " + endTime;//affairTypeNames
		  	if(remindType == '3'){
		        week_day_month = weekNames[parseInt(prc.remindDate , 10)-1];  
		    }
		    if(remindType == '4'){
		        week_day_month= prc.remindDate+'日';
		    }
		    if(remindType == '5'){
		        week_day_month = prc.remindDate.split('-')[0]+'月'+prc.remindDate.split('-')[1]+'日';
		    }
	        var calTypeName = "类型:工作事务";
	        if(calType=='2'){
	            calTypeName = "类型: 个人事务";
	        }
	        if(managerName!=''){
	            managerName = "\n安排人 ："+managerName;
	        }
	        var  title = calTypeName + "\n起始时间:" + startTimeStr +"\n" +  affairTypeNames[remindType-2] + week_day_month +" " + remindTime + managerName;
	        var div = "<div title='"+ title +"' id='" + i + "_" + seqId + "' style='min-height: 30px;  margin-top:3px;margin-bottom:3px;' class='fc-event fc-event-vert fc-event-draggable fc-event-start fc-event-end fc-event-color-affair ui-draggable ui-resizable'>"+
         	 " <div class=\"fc-event-inner\">"+
          	  "<div class=\"fc-event-time\">"+ affairTypeNames[remindType-2] + week_day_month +" " + remindTime+ "</div>"+
         	 	  "<div class=\"fc-event-title\">" + content + "</div>"+
         	 	  "</div>"+
         	 	
     	     "</div>"; 
 
           $("#td_"+ userId +"_" + startTimeTempStr.substring(0,10)).append(div);
           
           $("#" + i + "_" + seqId).click(function(event){
           		toClickCalendar(prc);
           });
	       
		}); 
}


/**
 * 跳转至查询界面
 */
function selectCalendar(){
	window.location.href = "query.jsp";
}
