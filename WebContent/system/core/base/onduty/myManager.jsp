<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    int year=TeeStringUtil.getInteger(request.getParameter("year"), 0);
    int month=TeeStringUtil.getInteger(request.getParameter("month"), 0);
%>
<!DOCTYPE html>
<html>
<head><meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<link href="<%=contextPath%>/common/jquery/ztree/css/demo.css"	type="text/css" rel="stylesheet" />
<title>值班排班管理</title>
<style>
 body{ font-size:12px;}
.TableBlock1 {
	border-collapse: collapse;
	border:2px solid #59acff;
}
.TableHeader1{
	border-bottom:1px solid #59acff;
	background-color: #f5fbf8;
}
.TableHeader1 td {
	border-left: 1px solid #b6b6b6;
	border-right: 1px solid #b6b6b6;
	height: 48px;
	line-height:48px;
	text-align:center;
}

.TableHeader1 b{
	font-size:16px;
}
.TableData1 {
	height: 100px;
	border: 1px solid #b6b6b6;
	border-top:none;
	vertical-align: top;
	padding: 3px;
}
.event:hover{
	cursor:pointer;
	background-color:azure;
}
.lastTr{
	
}
.tableArea{
	padding:8px;
	padding-top:0;
}
select{
    height: 26px;
}
</style>
<script>
var ZHIBAN=0;
var DEPTID=0;
var date=new Date();
var nowYear=date.getFullYear();
var nowMonth=date.getMonth()+1;
var beginYear=nowYear-3;
var year=<%=year%>;
var month=<%=month%>;
//初始化方法
function doInit(){
	//动态判断当前登录用户的角色   
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
	//值班类型动态加载
	//showZB();
	
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
	$("#today").click("click",function(){
		var curDate = new Date();
		var curYear = curDate.getFullYear();
		var curMonth = curDate.getMonth()+1;
		//这里出不来效果!!
		$("#year").val(curYear);
		$("#month").val(curMonth);
		renderCalendar();
		
	});
	
	//值班类型
	findDutyTypeAll();
	//所有部门
	findDeptAll();
}
//值班类型
function findDutyTypeAll(){
	var url = "<%=contextPath%>/teePbDutyTypeController/findDutyTypeAll.action";
	var json=tools.requestJsonRs(url,null);
	var data=json.rtData;
	if(data!=null && data.length>0){
		for(var i=0;i<data.length;i++){
			$("#dutyTypeName").append("<option value='"+data[i].sid+"'>"+data[i].typeName+"</option>");
		}
	}
}
//所有部门
function findDeptAll(){
	var url = "<%=contextPath%>/deptManager/getAllDept.action";
	var json=tools.requestJsonRs(url,null);
	var data=json.rtData;
	if(data!=null && data.length>0){
		for(var i=0;i<data.length;i++){
			$("#deptAll").append("<option value='"+data[i].uuid+"'>"+data[i].deptName+"</option>");
		}
	}
}
//导入
function toImport(){
	var url = contextPath + "/system/core/base/onduty/importpd.jsp";
	window.location.href=url;
}
function today(){
		var curDate = new Date();
		var curYear = curDate.getFullYear();
		var curMonth = curDate.getMonth()+1;
		//这里出不来效果!!
		$("#year").val(curYear);
		$("#month").val(curMonth);
		renderCalendar();
}
//批量处理 安排
function batch(){
	var orgNames = [];
	$('input[name="Fruit"]:checked').each(function(i,obj){
		orgNames.push(obj.getAttribute("value"));
	});
	var org="";
	for(var i=0;i<orgNames.length;i++){
		if (org =="") {  
			org = orgNames[i];  
        } else {  
        	org = orgNames[i] + "," + org;  
        } 
	}
	if(org==""){
		alert("请至少勾选一个日期!!!");
		return;
	}
	 var url = contextPath + "/system/core/base/onduty/addOrUpdate.jsp?curDateStr="+org;
		bsWindow(url ,"安排值班信息",{width:"650",height:"350",buttons:
		[
			 {name:"保存",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			
		 ]
		,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=='保存'){
				if(cw.commit()){
					$.MsgBox.Alert_auto("设置成功");
					renderCalendar();
					return true;
				}
				return false;
			}
			if(v=="关闭"){
				return  true;
			}
		}}); 
}


//全选
function quanXuan(){
	$('input[name="Fruit"]').attr("checked", true);  
}
//反选
function fanXuan() {  
	var arrLength=$('input[name="Fruit"]');
	for(var i=0;i<arrLength.length;i++){
		if($(arrLength[i]).is(":checked")){
			$(arrLength[i]).attr("checked",false);
		}else{
			$(arrLength[i]).attr("checked",true);
		}
	}
	
} 


//批量删除
function batchDelete(){
	var orgNames = [];
	$('input[name="Fruit"]:checked').each(function(i,obj){
		orgNames.push(obj.getAttribute("value"));
	});
	var org="";
	for(var i=0;i<orgNames.length;i++){
		if (org =="") {  
			org = orgNames[i];  
        } else {  
        	org = orgNames[i] + "," + org;  
        } 
	}
	if(org==""){
		alert("请至少勾选一个日期!!!");
		return;
	}
	  $.MsgBox.Confirm("提示","是否确定删除？",function(){
			var url = contextPath+ "/teePbOnDutyController/batchDelete.action";
			var json = tools.requestJsonRs(url,{org:org});
			if(json.rtState){
				$.MsgBox.Alert_auto(json.rtMsg);
				renderCalendar();
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
		    return true;
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
    var dutyTypeName=parseInt($("#dutyTypeName").val());
    var deptAll=parseInt($("#deptAll").val());
	var firstDate = new Date(year,month-1,1);
	var firstDateOfWeek = firstDate.getDay();					//获取第一天是周几
	var totalOfMonthDays = getDaysOfMonth(year,month-1);		//获取当月有多少天
	
	if(firstDateOfWeek==0){
		firstDateOfWeek = 6;
	}else{
		firstDateOfWeek --;
	}
	//获取当月所有的法定节假日
	var arr=getFdjr(year,month,totalOfMonthDays);
	//获取当前月的所有值班信息
	var url = "<%=contextPath%>/teePbOnDutyController/findDutyByDate2.action";
	var para = {year : year,month : month,totalOfMonthDays : totalOfMonthDays};
	var json=tools.requestJsonRs(url,para);
    var data=json.rtData;
	var arrVal="";
	for(var i=0;i<totalOfMonthDays;i++){
		arrVal=year+"-"+month+"-"+(i+1);
		//新增功能---如果:是周六日,需要价格背景色.
		if((i+firstDateOfWeek)% 7==5 || (i+firstDateOfWeek)% 7==6){
			$("#td"+(i+firstDateOfWeek)).attr("style","background:#FFF0F0");
			if(arr != null && arr.length >0){
				for(var j=0; j<arr.length;j++){
					if(i-firstDateOfWeek+1 == arr[j]){
						$("#td"+i).attr("style","background:#FFF0F0");
					}
				}
			}
			$("#td"+(i+firstDateOfWeek)).append("<div align='center' style='float:left;font-family: 方正仿宋_GBK;font-weight:bold;font-size:20px;margin-top: 10px' class='date'>"+(i+1)+"</div>").attr("hasValue","1");
			//$("#td"+(i+firstDateOfWeek)).append("<div><input style='float:right;'  name='Fruit' type='checkbox' value='"+arrVal+"' /></div><br/>");
			
		}else{
			if(arr != null && arr.length >0){
				for(var k=0; k<arr.length;k++){
					if(i-firstDateOfWeek+1 == arr[k]){
						$("#td"+i).attr("style","background:#FFF0F0");
					}
				}
			}
			$("#td"+(i+firstDateOfWeek)).append("<div align='center' style='float:left;font-family: 方正仿宋_GBK;font-weight:bold;font-size:20px;margin-top: 10px' class='date'>"+(i+1)+"</div>").attr("hasValue","1");
		}
			$("#td"+(i+firstDateOfWeek)).append("<div><input style='float:right;'  name='Fruit' type='checkbox' value='"+arrVal+"' /></div>");
            var dataStr="";
			if(i+1<10){
            	dataStr="0"+(i+1);
            }else{
            	dataStr=i+1;
            }
			if(data!=null){
				for(var m=0;m<data.length;m++){
					if(data[m].creTime==dataStr){
		    			$("#td"+(i+firstDateOfWeek)).append("<div style='font-family: 方正仿宋_GBK;font-weight: bold;font-size: 16px; margin-top: 36px;color: orange;text-align: center;'>部门："+data[m].deptName+"</div>");
		    			$("#td"+(i+firstDateOfWeek)).append("<div style='text-align: center;font-family: 方正仿宋_GBK;font-weight: bold;font-size: 15px;margin-top: 10px;'>带班："+data[m].userName+"</div>");
		    			var list=data[m].mapList;
		    			if(list!=null && list.length>0){
		    				for(var j=0;j<list.length;j++){
		    	    			$("#td"+(i+firstDateOfWeek)).append("<div style='text-align: center;font-family: 方正仿宋_GBK;font-size: 14px;'>"+list[j].childName+"："+list[j].userName+"</div>");
		    				}
		    			}
		    			var allUuid=data[m].sid;
		    			var time=year+"-"+month+"-"+data[m].creTime;
		    			var menuData = [];
					    	menuData.push({name:"查看",action:function(sid){
					    		view(sid);
							} ,extData:[allUuid]});
					   $("#td"+(i+firstDateOfWeek)).TeeMenu({menuData:menuData,eventPosition:true});

					}
				}
            }
	}
	
	//当前日期div颜色变化
	var curDate = new Date();
	var curYear = curDate.getFullYear();
	var curMonth = curDate.getMonth()+1;
	if(year==curYear&&month==curMonth){
		for(var i=1;i<43;i++){
			var d=$("#td"+(i-1)).find("div").first().text();
			if(d==curDate.getDate()){
				$("#td"+(i-1)).attr("style","background-color: #e3fbd3;border: 2px solid #4CAF50;");			
			}	
	  }	
	}
	
 	 //给有日期的td添加双击事件
	for(var i=1;i<43;i++){
		var d=$("#td"+(i-1)).find("div").first().text();
		if(d!=""&&d!=null&&d!="undefined"){
			//判断角色 是否拥有更改值班权限的角色
			if(Number(ZHIBAN)==1){
			//获取当前框的值   加入到日期框
			var year = parseInt($("#year").val());
			var month = parseInt($("#month").val());
			var day=$("#td"+(i-1)).find("div").first().text();
			var selectDay= year+"-"+month+"-"+day;
			var htm="<input style='float:right;'  name='Fruit' type='checkbox' value='"+selectDay+"' />";
			$("#td"+(i-1)).find(".date").css({"text-align":"left","padding":"0 10px"}).append(htm);
			/* //添加点击事件   单机就选中复选框
			$("#td"+(i-1)).on("click",function(){
				var checkbox  = $(this).find("input[type='checkbox']");
			    var isChecked = checkbox.is(":checked");
			    if (isChecked) {
			        checkbox.removeAttr("checked");
			    } else {
			        checkbox.attr("checked","true");
			    }
			}); */
			
			//添加双击事件
			$("#td"+(i-1)).bind("dblclick",function(){
					addEvent(this);
			});		
			}
		}	
     }	 
	 //删除多余的tr
		var value=$("#td35").attr("hasValue"); 
		if(value!="1"){
			for(var i=35;i<43;i++){
			$("#td"+i).remove();
		    }
	    } 
		/*删除多余的tr*/
		var remainTr = $("#cal_table tbody").find("tr");
		remainTr.each(function(index,element){
			if($(this).find("td").length == 0){
				$(this).remove();
			}
		});
		$("#cal_table tbody").find("tr:last").addClass("lastTr");
	
}
//查看信息
function view(sidStr){
	 var url = contextPath + "/system/core/base/onduty/view.jsp?sidStr="+sidStr;
 	 bsWindow(url ,"值班详情",{width:"600",height:"280",buttons:
 		 [{name:"关闭",classStyle:"btn-alert-gray"}]
		,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="关闭"){
				return  true;
			}
		}}); 
}
//删除事件
function del(sidStr){
	  $.MsgBox.Confirm("提示","是否确定删除？",function(){
			var url = contextPath+ "/teePbOnDutyController/deleteDutyById.action";
			var json = tools.requestJsonRs(url,{sid:sidStr});
			if(json.rtState){
				$.MsgBox.Alert_auto(json.rtMsg);
				renderCalendar();
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
		    return true;
	    });
}

//编辑
function update(allUuid,time){
	var url = contextPath + "/system/core/base/onduty/update.jsp?sidStr="+allUuid+"&curDateStr="+time;
	bsWindow(url ,"修改值班信息",{width:"600",height:"350",buttons:
	[
		 {name:"保存",classStyle:"btn-alert-blue"},
		 {name:"关闭",classStyle:"btn-alert-gray"}
	 ]
	,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=='保存'){
			if(cw.commit()){
				$.MsgBox.Alert_auto("编辑成功");
				renderCalendar();
				return true;
			}
			return false;
		}
		if(v=="关闭"){
			return  true;
		}
	}}); 
}

//添加事件---->到添加或者修改的页面
function addEvent(obj){
	var year = parseInt($("#year").val());
	var month = parseInt($("#month").val());
	var day=$(obj).find("div").first().text();
	var selectDay= year+"-"+month+"-"+day;
	
    var url = contextPath + "/system/core/base/onDuty/onDutyManger/addOrUpdate.jsp?curDateStr="+selectDay;
	bsWindow(url ,"安排值班信息",{width:"600",height:"350",buttons:
	[
		{name:"保存",classStyle:"btn-win-white fl"},
 	 	{name:"关闭",classStyle:"btn-win-white fl"}
	 ]
	,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=='保存'){
			if(cw.save()){
				
				renderCalendar();
				return true;
			}
			return false;
		}
		if(v=="关闭"){
			return  true;
		}
	}}); 
	
	
	
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
 * 显示值班信息
 */
function showZB(){
		var url1="<%=contextPath%>/onDutyController/getZBType.action";
		var json1=tools.requestJsonRs(url1);
		if(json1.rtState){
			var str="";
			for(var i=0;i<json1.rtData.length;i++){
				str+="<option value='"+json1.rtData[i].uuid+"' onclick='selectPB("+json1.rtData[i].uuid+")'>"+json1.rtData[i].dutyTypeName+"</option>";
			}
			$("#pblx").append(str);
		}
}
/**
 * 获取当月的法定假日---新增功能:当前年,当前月,与当前月的天数
 */
function getFdjr(year,month,totalOfMonthDays){
	//拼接成数组--返回
	<%-- var jrArr = new Array();
	var url="<%=contextPath%>/teeFdjrWhController/getAllJrByMonth.action";
	var param = {year:year,month:month}
	var json=tools.requestJsonRs(url,param);
	if(json.rtState){
		var prcs = json.rtData;
		if(prcs != null && prcs.length>0){
			
			for(var i=0;i<prcs.length;i++){
				var prc = prcs[i];
				//存在2中情况---开始月份,结束月份在同一个月----开始月份,结束月份不在同一个月内.
				if( prc.startMouth == prc.endMouth && prc.startMouth==month){
					//1--月份一样
					var startDay = prc.startDate;
					var endDay = prc.endDate;
					var num = endDay-startDay+1;
					for(var j=0;j<num;j++){
						jrArr[jrArr.length] = startDay+j;
					}
				}else if(prc.startMouth != prc.endMouth){
					//2--月份不一样
					if(prc.startMouth == month){
						//开始月份在本月
						var startDay = prc.startDate;
						var num=totalOfMonthDays-startDay+1;
						for(var k=0;k<num;k++){
							jrArr[jrArr.length] = startDay+k;
						}
					}else if(prc.endMouth == month){
						//结束月份在本月
						var endDate = prc.endDate;
						var num = endDate;
						for(var l=1;l<=num;l++){
							jrArr[jrArr.length] = l;
						}
					}
				}
			}
			return jrArr;
		}
	} --%>
	return null;
}
//发送代办
function sendDaiB(){
	//renderCalendar();
var zhiBanTime = '';
$('input:checkbox[name="abc"]:checked').each(function(k){
    if(k == 0){
    	zhiBanTime = $(this).val();
    }else{
    	zhiBanTime += ','+$(this).val();
    }
})
	//alert(zhiBanTime);
	top.$.jBox
	.confirm(
			"是否确认发送？",
			"确认",
			function(v) {
				if (v == "ok") {
					 var url = "<%=contextPath%>/onDutyController/sendDDaiBan.action";
					 var para = {org : zhiBanTime};
					 var jsonObj = tools.requestJsonRs(url, para);
					 if (jsonObj.rtState) {
							top.$.jBox.tip("发送成功！", 'success');
							renderCalendar();
							return;
						}else{
							top.$.jBox.tip("暂无可代办日期", 'error');
							return;
						}
				}
			});
	
	
}
</script>
</head>
<body onload="doInit()">
	<div id="toolbar" class = "topbar clearfix" style="border-bottom: 1px solid #74c5ff!important;">
	   <div class="fl" style="position:static;">
		  <img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/exam/imgs/icon_tkgl.png">
		  <span class="title">个人值班情况</span>
	   </div>
	   <div class = "right fr clearfix" style="margin-right:8px;">
			<!-- 年份 -->
			<select class="BigSelect" id="year" onchange="renderCalendar();">
			</select>&nbsp;
			<!-- 月份 -->
			<select id="month" class="BigSelect" onchange="renderCalendar();">
			</select>&nbsp;
			<!-- 值班类型 -->
	   </div>
	</div>
	
	
	<div class="base_layout_center" style="margin-top:10px;">

	<div class="tableArea">
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
	
</div>
</body>
</html>