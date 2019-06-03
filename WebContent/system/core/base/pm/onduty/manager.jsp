<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    int year=TeeStringUtil.getInteger(request.getParameter("year"), 0);
    int month=TeeStringUtil.getInteger(request.getParameter("month"), 0);
    %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<link href="<%=contextPath%>/common/jquery/ztree/css/demo.css"
	type="text/css" rel="stylesheet" />
<title>值班排班管理</title>
<style>
 body{ font-size:12px;}
.TableBlock1 {
	border-collapse: collapse;
}

.TableHeader1 td {
	border: 1px solid gray;
	background: #f0f0f0;
	height: 35px;
}

.TableData1 {
	height: 100px;
	border: 1px solid gray;
	vertical-align: top;
	padding: 3px;
}
.event:hover{
	cursor:pointer;
	background-color:azure;
}
</style>
<script>
var date=new Date();
var nowYear=date.getFullYear();
var nowMonth=date.getMonth()+1;
var beginYear=nowYear-3;
var year=<%=year%>;
var month=<%=month%>;
//初始化方法
function doInit(){
	//动态渲染年份
	var selector=$('#year');  
	for(var i=0;i<9;i++){
		 if(year==0||year=="undefined"){	
			 if(i==3){
				 selector.append('<option class="op" selected = "selected" value="'+(beginYear+i)+'">'+(beginYear+i)+'年</option>');   
			 }else{
				 selector.append('<option class="op"  value="'+(beginYear+i)+'">'+(beginYear+i)+'年</option>');   
			 }	 	 
		 }else{
			 if((beginYear+i)==year){
				 selector.append('<option class="op" selected = "selected" value="'+(beginYear+i)+'">'+(beginYear+i)+'年</option>');   
			 }else{
				 selector.append('<option class="op"  value="'+(beginYear+i)+'">'+(beginYear+i)+'年</option>');   
			 }		 
		 }
	 }
	//动态渲染月份
	var selector1=$('#month');  
	for(var i=1;i<=12;i++){
		 if(month==0||month=="undefined"){	
			 if(i==nowMonth){
				 selector1.append('<option class="op" selected = "selected" value="'+(i)+'">'+(i)+'月</option>');   
			 }else{
				 selector1.append('<option class="op"  value="'+(i)+'">'+(i)+'月</option>');   
			 }	 	 
		 }else{
			 if((i)==month){
				 selector1.append('<option class="op" selected = "selected" value="'+(i)+'">'+(i)+'月</option>');   
			 }else{
				 selector1.append('<option class="op"  value="'+(i)+'">'+(i)+'月</option>');   
			 }		 
		 }
	 }
	
	//动态渲染日历
	renderCalendar();
	//排班类型动态加载
	getSysCodeByParentCodeNo("PBLX","pblx");	
	//获取部门列表
	getDeptParent(1);
	
	//年份重新渲染日历
	$("#year").bind("change",function(){
		renderCalendar();
	});
	//月份改变重新渲染日历
	$("#month").bind("change",function(){
		renderCalendar();
	});

	//排班改变重新渲染日历
	$("#pblx").bind("change",function(){
		renderCalendar();
	});
	
	//点击按钮今天
	$("#today").bind("click",function(){
		var curDate = new Date();
		var curYear = curDate.getFullYear();
		var curMonth = curDate.getMonth()+1;
		$("#year").val(curYear);
		$("#month").val(curMonth);
		renderCalendar();
		
	});
}

function renderCalendar(){
	//动态渲染日历
	var render = ["<tr>"];
	for(var i=1;i<43;i++){
		render.push("<td class='TableData1' id='td"+(i-1)+"'></td>");
		if(i%7==0){
			render.push("</tr>");
			render.push("<tr>");
		}
	}
	render.push("</tr>");
	
	$("#cal_table tbody").html(render.join(""));

	//获取当前月份的第一天是星期几
	//var curDate = new Date();
    var year = parseInt($("#year").val());
    var month = parseInt($("#month").val());

	var firstDate = new Date(year,month-1,1);
	var firstDateOfWeek = firstDate.getDay();//获取第一天是周几
	var totalOfMonthDays = getDaysOfMonth(year,month-1);//获取当月有多少天

	
	if(firstDateOfWeek==0){
		firstDateOfWeek = 6;
	}else{
		firstDateOfWeek --;
	}
	
	for(var i=0;i<totalOfMonthDays;i++){
		//$("#td"+(i+firstDateOfWeek)).append(i+1);
		$("#td"+(i+firstDateOfWeek)).append("<div align='right' class='date'>"+(i+1)+"</div>").attr("hasValue","1");
	}
	
	
	//当前日期div颜色变化
	var curDate = new Date();
	var curYear = curDate.getFullYear();
	var curMonth = curDate.getMonth()+1;
	if(year==curYear&&month==curMonth){
		for(var i=1;i<43;i++){
			var d=$("#td"+(i-1)).find("div").first().text();
			if(d==curDate.getDate()){
				$("#td"+(i-1)).attr("style","background-color: gainsboro;");			
			}	
	  }	
	}
	
	
 	 //给有日期的td添加双击事件
	for(var i=1;i<43;i++){
		var d=$("#td"+(i-1)).find("div").first().text();
		if(d!=""&&d!=null&&d!="undefined"){
			$("#td"+(i-1)).bind("dblclick",function(){			
				addEvent();
			});		
		}	
     }	 
	
	 //删除多余的tr
		var value=$("#td35").attr("hasValue"); 
		if(value!="1"){
			for(var i=35;i<43;i++){
			$("#td"+i).remove();
		    }
	    } 
		
		//动态在日历上渲染事件
		//获取后台数据
		var pbType=$("#pblx").val();
		var deptId=$("#deptParent").val();
		
		
		var url = contextPath + "/onDutyController/getDutyList.action";
		var json = tools.requestJsonRs(url,{year:year,month:month,pbType:pbType,deptId:deptId});
    
		//alert(tools.jsonObj2String(json));
		var dutyList = json.rtData;
		
		//获取所有的td
		var tds=$(".TableData1");
		for(var i=0;i<tds.length;i++){
			var hasValue=$("#td"+i).attr("hasValue");
			 if(hasValue=="1"){
				var day=$("#td"+(i)).find("div").first().text();
				var begin=year+"-"+month+"-"+day+" 00:00:00";
				var end=year+"-"+month+"-"+day+" 23:59:59";	
				begin = begin.replace(/-/g,"/");
			    var begin1 = new Date(begin);
			    end = end.replace(/-/g,"/");
			    var end1 = new Date(end);
			    //遍历从数据库中取到的数据
			    for(var j=0;j<dutyList.length;j++){
					var beginStr=dutyList[j].beginTimeStr;
					var endStr=dutyList[j].endTimeStr;
					var bindex=beginStr.indexOf(" ");
					var b=beginStr.substring(bindex,beginStr.length);//开始时间的时分秒
					var eindex=endStr.indexOf(" ");
					var e=endStr.substring(eindex,endStr.length);//结束时间的时分秒
					beginStr = beginStr.replace(/-/g,"/");
				    var begin2 = new Date(beginStr);
				    endStr = endStr.replace(/-/g,"/");
				    var end2 = new Date(endStr);
				    
				    var divObject = null;
				    var currend=null;
				    //通过比较日期范围   动态在td上追加事件	    
				    if(begin2>=begin1&&end2<=end1){//开始结束时间都在今日范围内	
				    	divObject = $("<div  align='left' class='event' >"+dutyList[j].userName+" "+b+" - "+e+"</div>");
				        currend=year+"-"+month+"-"+day+" "+e;
				    	$("#td"+i).append(divObject);
				    }else if(begin2<=begin1&&end2>=begin1&&end2<=end1){
				    	divObject = $("<div  align='left' class='event' >"+dutyList[j].userName+" 00:00:00 - "+e+"</div>");
				    	currend=year+"-"+month+"-"+day+" "+e;
				    	$("#td"+i).append(divObject);   	
				    }else if(begin2>=begin1&&begin2<=end1&&end2>=end1){
				    	divObject = $("<div  align='left' class='event' >"+dutyList[j].userName+" "+b+" - 23:59:59"+"</div>");
				    	currend=year+"-"+month+"-"+day+" 23:59:59";
				    	$("#td"+i).append(divObject);
				    }else if(begin2<=begin1&&end2>=end1){
				    	divObject = $("<div align='left' class='event' >"+dutyList[j].userName+" 00:00:00 - 23:59:59"+"</div>");
				    	currend=year+"-"+month+"-"+day+" 23:59:59";
				    	$("#td"+i).append(divObject);	    	
				    }
				    if(divObject!=null){
				    	var menuData = [];
				    	menuData.push({name:"查看",action:function(sid,currend){
				    		
				    		view(sid,currend);
						} ,extData:[dutyList[j].uuid,currend]});
				    	
						menuData.push({name:"编辑",action:function(sid){
							update(sid);
						} ,extData:[dutyList[j].uuid]});
						
						menuData.push({name:"删除",action:function(sid){
							del(sid);
						} ,extData:[dutyList[j].uuid]});
				    	
				    	divObject.TeeMenu({menuData:menuData,eventPosition:true});
				    }
			    }
			} 
		}
	
}

//查看信息
function view(sid,currend){
	 var url = contextPath + "/system/core/base/pm/onduty/view.jsp?sid="+sid+"&currEnd="+currend;
 	 bsWindow(url ,"值班详情",{width:"600",height:"280",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-default"}
		 ]
		,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="关闭"){
				return  true;
			}
		}}); 
}
//删除事件
function del(sid){
	$.jBox
	.confirm(
			"是否确认删除？",
			"确认",
			function(v) {
				if (v == "ok") {
					var url = contextPath
							+ "/onDutyController/deleteDuty.action";
					var json = tools.requestJsonRs(url, {
						uuid : sid
					});
					if (json.rtState) {
						$.jBox.tip("删除成功!",'success',{timeout:3000});
						//删除之后重新 渲染日历
						renderCalendar();
						//return true;
					}
					//top.$.jBox.tip(json.rtMsg, "error");
				}
			});
	
}
//编辑
function update(sid){
	var url = contextPath + "/system/core/base/pm/onduty/index.jsp?sid="+sid;
 
	$('#myModal').modal('show');
	$("#iframe0").attr("src",url);
	
}

//添加事件
function addEvent(){
    var url = contextPath + "/system/core/base/pm/onduty/index.jsp";
// 	bsWindow(url ,"值班安排",{width:"600",height:"330",buttons:
// 		[
// 		 {name:"保存",classStyle:"btn btn-primary"},
// 	 	 {name:"关闭",classStyle:"btn btn-default"}
// 		 ]
// 		,submit:function(v,h){
// 			var cw = h[0].contentWindow;
// 			if(v=="保存"){
// 				//cw.commit();
// 				var isStatus = cw.doSaveOrUpdate();
// 				if(isStatus){
// 					window.location.reload();
// 					return true;
// 				}
// 			}else if(v=="关闭"){
// 				return true;
// 			}
// 		}}); 
	$('#myModal').modal('show');
	$("#iframe0").attr("src",url);
}




function getDaysOfMonth(year,month){
	//获取当前月份
	var mouth = month + 1;

	//定义当月的天数；
	var days ;

	//当月份为二月时，根据闰年还是非闰年判断天数
	if(mouth == 2){
		days = year % 4 == 0 ? 29 : 28;
	}
	else if(mouth == 1 || mouth == 3 || mouth == 5 || mouth == 7 || mouth == 8 || mouth == 10 || mouth == 12){
		//月份为：1,3,5,7,8,10,12 时，为大月.则天数为31；
		days= 31;
	}
	else{
		//其他月份，天数为：30.
		days= 30;
	}
	return days;
}


/**
 * 获取部门作为上级部门
 */
function getDeptParent(uuid){
	var url =  "<%=contextPath %>/orgSelectManager/getSelectDeptTreeAll.action";
// 		var config = {
// 			zTreeId : "deptParentZTree",
// 			requestURL : url,
// 			param : {
// 				uuid : uuid,
// 				moduleId:"19"
// 			},
// 			onClickFunc : onclickDept,
// 			async : false,
// 			onAsyncSuccess : setDeptParentSelct
// 		};
		
		config = {
			zTreeId:"deptParentZTree",
			requestURL:url,
			param:{"moduleId":"19",isSingle:1},
           	onClickFunc:onclickDept,
			async:false,
			onAsyncSuccess:setDeptParentSelct
		};
		zTreeObj = ZTreeTool.config(config);
	}
	
/**
 * 初始化后选中节点,上级部门
 */
function setDeptParentSelct(){
	var deptName = "";
	ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
    if(ZTreeObj == null){
    	//setTimeout('setDeptParentSelct()',500);
    	setDeptParentSelct();
    }else{
    	///ZTreeObj.expandAll(true);全部展开
    	 var node = ZTreeObj.getNodeByParam("id",$("#deptParent").val(),null);
    	    if(node){
    	    	ZTreeObj.selectNode(node);
    	    	deptName = node.name;
    	  }
    }  
    ZTreeTool.inputBindZtree(ZTreeTool.zTreeId,'deptParent',deptName);
}
//点击树执行事件
function onclickDept (event, treeId, treeNode) {
	$("#deptParentName").val(treeNode.name);
	$("#deptParent").val(treeNode.id);
	ZTreeTool.hideZtreeMenu();
	
	renderCalendar();
	
}
//清空部门
function clearDept(){
	
}



/*计算两个时间之间的天数差
function DateDiff(sDate1, sDate2)
{
    var iDays;
    sDate1 = sDate1.replace(/-/g,"/");
    var aDate = new Date(sDate1 );
    sDate2 = sDate2.replace(/-/g,"/");
    var oDate = new Date(sDate2 );
    iDays = parseInt(Math.abs(oDate - aDate) / 1000 / 60 / 60 /24); //把相差的毫秒數轉抽象為天數
    return iDays;
} */
</script>
</head>
<body onload="doInit()">

	<div>
		<input type="button" id="today" value="今天">&nbsp;
		<!-- 年份 -->
		<select id="year">
		</select>&nbsp;
		<!-- 月份 -->
		<select id="month">
		</select>&nbsp;
		<!-- 值班类型 -->
		<select id="pblx">
			<option value="000">全部值班</option>
		</select>

		<div style="float: right">
			<a href="javascript:addEvent();">安排值班</a>
			<!-- 部门列表 -->
			<input id="deptParent" name="deptParent" type="text"
				style="display: none;" />
			<ul id="deptParentZTree" class="ztree"
				style="margin-top: 0; width: 247px; display: none;"></ul>
			<a href="javascript:clearDept();">清空部门</a>
		</div>
	</div>

	<div>
		<table id="cal_table" class="TableBlock1" width="100%" align="center">
			<thead>
				<tr align="center" class="TableHeader1" height="20">
					<td width="14%"><b>星期一</b></td>
					<td width="14%"><b>星期二</b></td>
					<td width="14%"><b>星期三</b></td>
					<td width="14%"><b>星期四</b></td>
					<td width="13%"><b>星期五</b></td>
					<td width="13%"><b>星期六</b></td>
					<td width="13%"><b>星期日</b></td>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table>
	</div>



	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">快速新建</h4>
				</div>
				<div class="modal-body">
					<iframe id="iframe0" frame-border="0"
						style="width: 100%; height: 400px;"></iframe>
				</div>
			</div>
		</div>
	</div>
</body>
</html>