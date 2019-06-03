<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script src="<%=contextPath %>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/KinSlideshow/jquery.KinSlideshow-1.2.1.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/seniorreport.js"></script>
<script src="<%=contextPath %>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
<script src="<%=contextPath %>/common/highcharts/js/highcharts.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/jquery.highchartsTable.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/data.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/highcharts-more.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/funnel.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/lazyloader.js"></script>

<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/frame/6/css/datepicker.css">
<script type="text/javascript" src="<%=contextPath%>/system/frame/6/js/jquery.datePicker-min.js"></script>

<title>主界面</title>

<link href="css/main.css" type="text/css" rel="stylesheet" />
<style>
body{
	margin:5px;
}
.column {
  float: left;
  padding-bottom: 20px;
}

.portlet {
  background:#fff;
  margin: 0 1em 1em 0;
  padding:0;
}

.portlet-header {
  position: relative;
}

.portlet-toggle {
  position: absolute;
  top: 50%;
  right: 0;
  margin-top: -8px;
}

.portlet-content {
  padding: 0px;
}

.portlet-placeholder {
  border: 1px dotted black;
  margin:10px;
  height: 50px;
}

/*主体中心区右侧域*/
/* #main_conetnt{ width:100%;  height:800px;  overflow-y:auto;} */
.desktop .modular{ border:1px #cdcdcd solid; margin:10px;}
.desktop .modular .modular_hd{ height:36px; line-height:36px; overflow:hidden; background:#f9f9f9;}
.desktop .modular .modular_hd .toggle{ float:left; width:37px; height:36px; background:url(images/desktop_ico.png) no-repeat left top; border-right:1px #cdcdcd solid; text-indent:-9999px; cursor:pointer;}
.desktop .modular .modular_hd .toggle.on{ background:url(images/desktop_ico.png) no-repeat left -38px;}
.desktop .modular .modular_hd .delete{ float:right; width:38px; height:36px; background:url(images/desktop_ico.png) no-repeat left -105px; border-left:1px #cdcdcd solid; text-indent:-9999px; cursor:pointer;}
.desktop .modular .modular_hd .renovate{ float:right;  width:38px; height:36px; background:url(images/desktop_ico.png) no-repeat left -75px; text-indent:-9999px; cursor:pointer;}
.desktop .modular .modular_hd .more{ float:right;  width:38px; height:36px; background:url(images/desktop_ico.png) no-repeat left -135px; text-indent:-9999px; cursor:pointer;}
.desktop .modular .modular_hd .title{ margin:0 100px 0 50px; font-size:14px; color:#7b7b7b; font-family:"Microsoft YaHei";font-weight:bold}


.desktop .modular .modular_bd{ border-top:1px #cdcdcd solid; overflow:hidden; background:#FFF; padding:12px 15px 8px 15px;min-height:150px} 
.desktop .modular .modular_bd .time{ color:#959595; font-size:13px;}
.desktop .modular .modular_bd .more{ height:24px; margin-top:5px; text-indent:-9999px;}
.desktop .modular .modular_bd .more a{ float:right; display:block; width:30px; height:24px; background:url(images/desktop_ico.png) no-repeat -2px -148px; text-indent:-9999px; }
.desktop .modular .modular_bd .news_headlines{ overflow:hidden; margin:0px;}
.desktop .modular .modular_bd .news_headlines dt{ float:left; width:222px; height:145px; overflow:hidden;}
.desktop .modular .modular_bd .news_headlines dd{ margin-left:238px;}
.desktop .modular .modular_bd .news_headlines dd h6{ line-height:30px; background:url(images/desktop_ico.png) no-repeat -15px -230px; padding-left:12px; margin:0px;}
.desktop .modular .modular_bd .news_headlines dd h6 a{ font-size:14px; color:#313131;}
.desktop .modular .modular_bd .news_headlines dd p{ line-height:22px; max-height:88px; overflow:hidden; font-size:12px; color:#898989; padding-left:12px; font-family:"Microsoft YaHei";}
.desktop .modular .modular_bd .news_headlines dd span.time{ padding-left:12px;}

.desktop .modular .modular_bd .news_list{ overflow:hidden; margin-top:5px; margin-bottom:0px;font-family:"Microsoft YaHei";}
.desktop .modular .modular_bd .news_list li{ line-height:30px; border-bottom:1px #c3c3c3 dashed; background:url(images/desktop_ico.png) no-repeat -15px -226px; text-indent:15px;}
.desktop .modular .modular_bd .news_list li a{ font-size:12px; color:#000;}
.desktop .modular .modular_bd .news_list li span.time{ float:right;font-family:"Microsoft YaHei";font-size:12px;color:#777}
.desktop .modular .modular_bd .workflow_list{ overflow:hidden; margin-top:5px; margin-bottom:0px;}
.desktop .modular .modular_bd .workflow_list li{ line-height:38px; border-bottom:1px #c3c3c3 dashed;}
.desktop .modular .modular_bd .workflow_list li a{ font-size:14px; color:#313131;}
.desktop .modular .modular_bd .workflow_list li a .red{ color:#F00;}
.desktop .modular .modular_bd .workflow_list li a .yellow{ color:#f39800;}
.desktop .modular .modular_bd .workflow_list li a .grey{ color:#313131;}
.desktop .modular .modular_bd .workflow_list li span.time{ float:right;}


.desktop .modular .modular_bd .schedule{ overflow:hidden; height:281px;}
.desktop .modular .modular_bd .schedule .schedule_content{ margin-left:285px; margin-top:10px;}
.desktop .modular .modular_bd .schedule .schedule_content h3{ font-size:14px;font-family:"Microsoft YaHei"; font-weight:bold;}
.desktop .modular .modular_bd .schedule .schedule_content h4{ font-size:30px;font-family:"Microsoft YaHei"; text-indent:20px;}
.desktop .modular .modular_bd .schedule .schedule_content li{ border-bottom:1px #CCC dashed; line-height:22px; margin:10px 0 0 0; font-size:12px;}
.desktop .modular .modular_bd .schedule .schedule_content li span.schedule_time{ font-weight:bold; margin-right:10px;}
.desktop .modular .modular_bd .schedule .schedule_content li span.red{ color:#F00;}
.desktop .modular .modular_bd .schedule .schedule_content li span.green{ color:#009e96;}
.list_div_content{padding:0px;margin:0px}
.list_div_content .clearfix{border-bottom:1px dashed #c3c3c3;padding-bottom:5px;background:url(images/desktop_ico.png) no-repeat -15px -230px; padding-left:12px;}
.list_div_content li a{ color:#000; font-size:12px; width:65%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;font-family:"Microsoft YaHei";}
.list_div_content li i{ font-style:normal;  font-size:12px; color:#777;font-family:"Microsoft YaHei";}
#dp_list{font-size:12px;height:185px;overflow-x:hidden;overflow-y:auto;width:1000px}
#picnewsdiv{min-height:280px}

 .div1{ 
    	margin-bottom:5px;background-color:#2388da;height:35px;width:150px;text-align:center;line-height:35px; color:white ;font-weight: bolder;
 		opacity:0.5;cursor:pointer;border-radius:5px;font-size:14px;font-family:微软雅黑;
 		filter:alpha(opacity=50)
 	}
 	.div1:hover{
 		opacity:1;
 		filter:alpha(opacity=100)
 	}
   .div2{ 
    margin-bottom:5px;background-color:	#ad183c;height:35px;width:150px;text-align:center;line-height:35px; color:white ;font-weight: bolder;
    opacity:0.5;cursor:pointer;border-radius:5px;font-size:14px;font-family:微软雅黑;
    filter:alpha(opacity=50)
 }
 .div2:hover{
 		opacity:1;
 		filter:alpha(opacity=100)
 	}
   .div3{ 
    margin-bottom:5px;background-color: #ff8d12;height:35px;width:150px;text-align:center;line-height:35px; color:white ;font-weight: bolder;
    opacity:0.5;cursor:pointer;border-radius:5px;font-size:14px;font-family:微软雅黑;
    filter:alpha(opacity=50)
 }
 .div3:hover{
 		opacity:1;filter:alpha(opacity=100)
 	}
   .div4{ 
    margin-bottom:5px;background-color: #42a42b;height:35px;width:150px;text-align:center;line-height:35px; color:white ;font-weight: bolder;
    opacity:0.5;cursor:pointer;border-radius:5px;font-size:14px;font-family:微软雅黑;
    filter:alpha(opacity=50)
 }
 .div4:hover{
 		opacity:1;filter:alpha(opacity=100)
 	}




</style>


<script type="text/javascript">
var cols = 1;

function switchDesktop(sid){
	var url = contextPath+"/teePortalTemplateUserDataController/setDefault.action?templateId="+sid;
	var json = tools.requestJsonRs(url);
	window.location.reload();
}

function doInit(){
	var url = contextPath+"/teePortalTemplateController/datagrid.action?type=1";
	var json = tools.requestJsonRs(url,{});

//获取个人桌面信息
var desktop ="";
var json= tools.requestJsonRs(contextPath+"/teePortalTemplateUserDataController/getTemplateUserData.action");
desktop=json.rtData.data; 
cols = json.rtData.cols;
try{
	desktop = eval("("+desktop+")");
}catch(e){
	return;
}


switch(cols){
case 1:
	$("body").append("<div id=\"column0\" class=\"column desktop\" style='width:33%'></div>");
	$("body").append("<div id=\"column1\" class=\"column desktop\" style='width:66%'></div>");
	break;
case 2:
	$("body").append("<div id=\"column0\" class=\"column desktop\" style='width:66%'></div>");
	$("body").append("<div id=\"column1\" class=\"column desktop\" style='width:33%'></div>");
	break;
case 3:
	$("body").append("<div id=\"column0\" class=\"column desktop\" style='width:50%'></div>");
	$("body").append("<div id=\"column1\" class=\"column desktop\" style='width:50%'></div>");
	break;
case 4:
	$("body").append("<div id=\"column0\" style='width:100%' class='desktop'></div>");
	$("body").append("<div style=\"clear:both\"></div>");
	$("body").append("<div id=\"column1\" class=\"column desktop\" style='width:50%'></div>");
	$("body").append("<div id=\"column2\" class=\"column desktop\" style='width:50%'></div>");
	break;
case 5:
	$("body").append("<div id=\"column0\" style='width:100%' class='desktop'></div>");
	$("body").append("<div style=\"clear:both\"></div>");
	$("body").append("<div id=\"column1\" class=\"column desktop\" style='width:33%'></div>");
	$("body").append("<div id=\"column2\" class=\"column desktop\" style='width:66%'></div>");
	break;
case 6:
	$("body").append("<div id=\"column0\" style='width:100%' class='desktop'></div>");
	$("body").append("<div style=\"clear:both\"></div>");
	$("body").append("<div id=\"column1\" class=\"column desktop\" style='width:66%'></div>");
	$("body").append("<div id=\"column2\" class=\"column desktop\" style='width:33%'></div>");
	break;
default:
	break;
}

var json = tools.requestJsonRs(contextPath+"/portlet/getUseablePortlet.action",{});
var useablePortlet = json.rtData;
var sp = useablePortlet.split(",");

//插入值
for(var i=0;i<desktop.length;i++){
	for(var j=0;j<desktop[i].length;j++){
		var exist = false;
		for(var k=0;k<sp.length;k++){
			if((sp[k])==(desktop[i][j].id+"")){
				exist = true;
				break;
			}
		}
		if(exist){
			renderPortlet(i,desktop[i][j]);
		}
	}
}

//
$( ".column" ).sortable({
  connectWith: ".column",
  handle: ".portlet-header",
  cancel: ".portlet-toggle",
  placeholder: "portlet-placeholder ui-corner-all",
  beforeStop: function( event, ui ) {
	  changeDesktop();
  }
});


$(function(){
	$('.modular').find('.toggle').click(function(){
		$(this).toggleClass('on');
		$(this).parent('.modular_hd').nextAll('.modular_bd').slideToggle();
	});
});

}

function changeDesktop(){
	var desktop = [];
	  $("body").children(".column").each(function(i,obj){
		  desktop.push([]);
		  $(obj).children(".portlet").each(function(j,o){
			  desktop[i].push({id:o.getAttribute("pid"),rows:15});
		  });
	  });
	  desktop = tools.jsonArray2String(desktop);
	  
	  var json = tools.requestJsonRs(contextPath+"/teePortalTemplateUserDataController/updateTemplateUserData.action",{desktop:desktop},true);
}

function delPortlet(id){
	if(window.confirm("是否删除该桌面模块？删除后可以从控制面板→桌面模块中重置。")){
		$("#wrap"+id).hide(200,function(){
			$(this).remove();
			changeDesktop();
		});
	}
}

function renderPortlet(col,item){
	var render = [];
// 	render.push("<div class='portlet' style='padding:5px;' pid="+item.id+"><p class=three></p> <p class=four></p> ");
// 	render.push("<div class='header portlet-header'>");
// 	render.push("<table style='width:98%;'>");
// 		render.push("<tr class=''>");
// 				render.push("<td>");
// 			 	render.push("<h5 style='display:inline-block;margin-top:10px;margin-left:10px'>");
// 			 	render.push("<img style='display:inline-block;height:20px;margin-top:0px;' onerror=\"this.style.display='none'\" id='pIcon"+item.id+"' alt='' />&nbsp;<a href='javascript:void(0);' id='pTitle"+item.id+"' style='font-weight:bold;margin-top:4px;'></a>");
// 			 	render.push("</h5>");
// 			 	render.push("</td>");
// 			 	render.push("<td style='text-align:right;'>");
// 			 	render.push("<img title='刷新' src='/oaop/common/images/other/icon_refresh.png' style='height:10px;;margin-right:10px;cursor:pointer;display:inline-block;' onclick=\"refreshPortlet('"+item.id+"')\"/>");
// 				render.push("<div style='display:inline-block;' id='pMore"+item.id+"'></div>");
// 			 	render.push("</td>");
// 		render.push("</tr>");
// 		render.push("</table>");
// 		render.push("</div>");
// 		render.push("<div class='content'>");
// 		render.push("<table style='width:100%;'>");
// 		render.push("<tbody>");
// 			render.push("<tr>");
// 				render.push("<td id='pContent"+item.id+"'>");
// 				render.push("<center><img src='"+contextPath+"/common/styles/imgs/loading01.gif' /></center>");
// 				render.push("</td>");
// 			render.push("</tr>");
// 		render.push("</tbody>");
// 	render.push("</table>");
// 	render.push("</div>");
// 	render.push(" <p class='four'></p> <p class='three'></p>   </div> ");
	render.push("<div class=\"modular portlet\" pid="+item.id+" id=\"wrap"+item.id+"\">");
		render.push("<div class=\"modular_hd portlet-header\">");
			render.push("<div class=\"toggle\" title=\"展开收起\">展开收起</div>");
			render.push("<div class=\"delete\" title=\"删除\" onclick=\"delPortlet("+item.id+")\">删除</div>");
			render.push("<div class=\"renovate\" title=\"刷新\"  onclick=\"refreshPortlet('"+item.id+"')\">刷新</div>");
			render.push("<div class=\"more\" title=\"更多\"  id='pMore"+item.id+"'>更多</div>");
			render.push("<div class=\"title\" id='pTitle"+item.id+"'></div>");
		render.push("</div>");
		render.push("<div class=\"modular_bd\">");
			render.push("<div id='pContent"+item.id+"'></div>");
		render.push("</div>");
	render.push("</div>");

	$("#column"+col).append(render.join(""));
	refreshPortlet(item.id);
	
}

function refreshPortlet(itemId){
	$("#pContent"+itemId).html("<center>正在加载，请稍候</center>");
	tools.requestJsonRs(contextPath+"/portlet/renderPortlet.action",{sid:itemId},true,function(json){
		if(json!=null){
			$("#pContent"+itemId).html(json.content);
			$("#pTitle"+itemId).html(json.title);
			if(json.moreUrl){
				$("#pMore"+itemId).attr("onclick","openFullWindow('"+json.moreUrl+"','查看全部')");
			}else{
				$("#pMore"+itemId).hide();
			}
			$("#pIcon"+itemId).attr("src",json.icons);
			
			if(json.autoRefresh!=0){
				if(!window["P_AUTO_REFRESH_"+itemId]){
					window["P_AUTO_REFRESH_"+itemId] = "1";
					//setInterval("refreshPortlet("+itemId+")",json.autoRefresh*1000);
					setTimeout("refreshPortletsTimeout("+itemId+","+json.autoRefresh*1000+")",json.autoRefresh*1000);
				}
			}
			
		}
	});
}

function refreshPortletsTimeout(itemId,time){
	refreshPortlet(itemId);
	setTimeout("refreshPortletsTimeout("+itemId+","+time+")",time);
}

function forward(runId,flowId,frpSid){
	var para = "runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId;
	window.openFullWindow(contextPath +'/system/core/workflow/flowrun/prcs/index.jsp?'+para,"流程办理");
}

function doPageHandler(){
	window.location.reload();
}


</script>
</head>

<body style="background:#ffffff;" onload="doInit()">



</body>
</html>
