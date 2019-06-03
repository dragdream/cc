<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);//报表主键
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script src="/common/highcharts/js/highcharts.src.js"></script>
<script type="text/javascript" src="/common/highcharts/js/jquery.highchartsTable.js"></script>
<script type="text/javascript" src="/common/highcharts/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="/common/highcharts/js/modules/data.src.js"></script>
<script type="text/javascript" src="/common/highcharts/js/highcharts-more.js"></script>
<script type="text/javascript" src="/common/highcharts/js/modules/funnel.js"></script>
<script type="text/javascript" src="script.js"></script>
<style>
.sz{
	border-radius:5px;
	padding:5px;
	background:#edecfc;
	margin-top:5px;
	margin-bottom:5px;
	height:20px;
}
.sz span{
	float:left;
}
.modal-test{
	width: 300px;
	height: 230px;
	position: absolute;
	display: none;
	z-index: 999;
}
.modal-test .modal-header{
	width: 100%;
	height: 35px;
	background-color:#8ab0e6;
}
.modal-test .modal-header .modal-title{
	color: #fff;
	font-size: 14px;
	line-height:35px;
	margin-left:20px;
	float: left;
}
.modal-test .modal-header .modal-win-close{
	color:#fff;
	font-size: 14px;
	line-height:35px;
	margin-right:20px;
	float: right;
	cursor: pointer;
}
.modal-test .modal-body{
	height: 120px;
	background-color:#f4f4f4;
	padding:10px;
}
.modal-test .modal-footer{
	width: 100%;
	height: 50px;
	background-color:#f4f4f4;
}
.modal-test .modal-footer input{
	margin-top:12px;
	float: right;
	margin-right:20px;
}


.modal-test1{
	width: 430px;
	height: 260px;
	position: absolute;
	display: none;
	z-index: 999;
}
.modal-test1 .modal-header{
	width: 100%;
	height: 35px;
	background-color:#8ab0e6;
}
.modal-test1 .modal-header .modal-title{
	color: #fff;
	font-size: 14px;
	line-height:35px;
	margin-left:20px;
	float: left;
}
.modal-test1 .modal-header .modal-win-close{
	color:#fff;
	font-size: 14px;
	line-height:35px;
	margin-right:20px;
	float: right;
	cursor: pointer;
}
.modal-test1 .modal-body{
	height: 120px;
	background-color:#f4f4f4;
	padding:10px;
	overflow: auto;
}
.modal-test1 .modal-footer{
	width: 100%;
	height: 50px;
	background-color:#f4f4f4;
}
.modal-test1 .modal-footer input{
	margin-top:12px;
	float: right;
	margin-right:20px;
}

.setting{
	width:15px;
	float:right;
	margin-left:5px;
}
.adding{
	width:15px;
	margin-left:5px;
}
.TableBlock_page{
	background:none;
}
.and{
	margin-top:5px;
}
</style>
<script>
var sid=<%=sid %>;//报表主键
var flag = 1;//1:主轴 2:次轴
function doInit(){
	
	//渲染视图列表
	renderBisView();
	//渲染报表分类
	renderCat();
	
	if(sid>0){//获取详情
		getInfoBySid();
	}
	
	
	//渲染图表
	renderGraphics();
	
	//测试是如何加入之前选择的过滤条件
	//addNewFilter("余额","等于","23");
	//addNewFilter("日期","区间","2017-12-01","2017-12-31");
}

//获取详情
function getInfoBySid(){
	
	var url=contextPath+"/teeEreportController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		
		var data=json.rtData;
		//先渲染所属视图
		$("#bisViewIdentity").val(data.bisViewIdentity);
		//先渲染图标类型
		$("#chartType").val(data.chartType);
		renderDimension();//渲染维度
		renderStatisticsField();//渲染统计字段
		
		changeChartType();//根据图标类型判断是否要显示次轴数值
		
		//渲染主轴数值  和  次轴数值
		var zz=tools.strToJson(data.spindle);
		var cz=tools.strToJson(data.shaft);
		
		for(var i in zz){
			$("<div class=\"sz\" title=\""+zz[i].title+"\" filedValue=\""+zz[i].fieldValue+"\" statistics=\""+zz[i].statistics+"\"><span>"+zz[i].title+"("+zz[i].statistics+")</span><img onclick=\"$(this).parent().remove();renderOrderFields();\" src=\"imgs/stop.png\" class=\"setting\"/><img src=\"imgs/setting.png\" onclick=\"setting(this)\" class=\"setting modal-menu-test\"/></div>").appendTo($("#zzdiv"));
		}
		
		for(n in cz){
			$("<div class=\"sz\" title=\""+cz[n].title+"\" filedValue=\""+cz[n].fieldValue+"\" statistics=\""+cz[n].statistics+"\"><span>"+cz[n].title+"("+cz[n].statistics+")</span><img onclick=\"$(this).parent().remove();renderOrderFields();\" src=\"imgs/stop.png\" class=\"setting\"/><img src=\"imgs/setting.png\" onclick=\"setting(this)\" class=\"setting modal-menu-test\"/></div>").appendTo($("#czdiv"));
		}
		
		//渲染排序字段
		var orderStr=tools.strToJson(data.orderStr);
		if(orderStr!=null&&orderStr!="null"){
			data["orderField"]=orderStr.orderField;
			data["orderDesc"]=orderStr.orderDesc;
			//alert(orderStr.orderField);
			renderOrderFields();
		}
		
		
		//渲染过滤条件
		var conditionItems=data.conditionItems;
		var conditionItemsArray=tools.strToJson(conditionItems);
		for(m in conditionItemsArray){
			var oper=conditionItemsArray[m].oper;
			var field=conditionItemsArray[m].field;
			var v1=conditionItemsArray[m].v1;
			var v2=conditionItemsArray[m].v2;
			if(oper=="区间"){
				addNewFilter(field,oper,v1,v2);
			}else if(oper=="上年"||oper=="上月"||oper=="上季"||oper=="上周"
					||oper=="昨日"||oper=="本年"||oper=="本季"||oper=="本月"||oper=="本周"){
				addNewFilter(field,oper);
			}else{
				addNewFilter(field,oper,v1);
			}
		}
		
		bindJsonObj2Cntrl(data);
		
	}else{
		$.MsgBox.Alert_auto("数据获取失败！");
	}
}




//渲染报表分类
function renderCat(){
	  var url=contextPath+"/seniorReportCat/getAllCat.action";
	  var json=tools.requestJsonRs(url);
	  if(json.rtState){
		  var data=json.rtData;
		  if(data.length>0){
			  for(var i=0;i<data.length;i++){
				  $("#catId").append("<option value="+data[i].sid+">"+data[i].name+"</option>");
			  }
		  }
	  }
}

//渲染视图列表
function renderBisView(){
	var url=contextPath+"/bisView/getAllBisViewList.action";
	var json=tools.requestJsonRs(url);
	if(json.rtState){
		var data=json.rtData;
		if(data!=null&&data.length>0){
			for(var i=0;i<data.length;i++){
				$("#bisViewIdentity").append("<option value='"+data[i].identity+"'>"+data[i].name+"</option>");
			}
		}
	}else{
		$.MsgBox.Alert_auto("视图列表获取失败！");
	}
}

//修改视图
function changeBisView(){
	//渲染维度
	renderDimension();
	//渲染统计字段
	renderStatisticsField();
	
	//清空已选的主轴数值  和  次轴数值
	$(".sz").remove();
	
	//清空排序字段  和  升序降序
	$("#orderField").find("option").not(":first").remove();
	$("#orderDesc").val("");
}

//渲染统计字段
function renderStatisticsField(){
	//清空原先的选项
	document.getElementById("statisticsField").options.length = 0;
	//获取所属视图
	var bisViewIdentity=$("#bisViewIdentity").val();
	var url=contextPath+"/bisView/getBisViewListItemList.action";
	var json=tools.requestJsonRs(url,{identity:bisViewIdentity});
	if(json.rtState){
		var data=json.rtData;
		if(data!=null&&data.length>0){
			for(var i=0;i<data.length;i++){
				if(data[i].fieldType=="NUMBER"){
					$("#statisticsField").append("<option value='"+data[i].searchField+"'>"+data[i].title+"</option>");
				}
				
			}
		}
	}
}
//渲染维度 （日期加文本类型）
function renderDimension(){
	//清空原先的选项
	 document.getElementById("dimension").options.length = 0;
	//获取所属视图
	var bisViewIdentity=$("#bisViewIdentity").val();
	var url=contextPath+"/bisView/getBisViewListItemList.action";
	var json=tools.requestJsonRs(url,{identity:bisViewIdentity});
	if(json.rtState){
		var data=json.rtData;
		if(data!=null&&data.length>0){
			for(var i=0;i<data.length;i++){
				if(data[i].fieldType=="TEXT"){
					$("#dimension").append("<option value='"+data[i].searchField+"'>"+data[i].title+"</option>");
				}else if(data[i].fieldType=="DATE"){
					$("#dimension").append("<option value='{Y}"+data[i].searchField+"'>"+data[i].title+"(年)</option>");
					$("#dimension").append("<option value='{M}"+data[i].searchField+"'>"+data[i].title+"(月)</option>");
					$("#dimension").append("<option value='{D}"+data[i].searchField+"'>"+data[i].title+"(日)</option>");
				}
					
				
			}
		}
	}
}


//修改图表格类型
function changeChartType(){
	var chartType=$("#chartType").val();
	if(chartType==5){//双轴图  显示次轴
		$("#czTr").show();
		$("#czTr1").show();
	}else{
		$("#czTr").hide();
		$("#czTr1").hide();
		$("#czTr1 .sz").remove();
		renderOrderFields();
	}
	
	//饼状图   只能有一个主轴数值
	if(chartType==4){
		var zz=$("#zzdiv .sz");
		if(zz.length>1){
			for(var i=1;i<zz.length;i++){
				$(zz[i]).remove();
			}
		}
	}
	
	
}


//增加主轴数值   次轴数值
function  addSz(){
	var title = $("#statisticsField option:selected").html();
	var filedValue = $("#statisticsField").val();
	var statistics = $("#statisticsMethod").val();
	
	if(flag==1){//主轴添加
		$("<div class=\"sz\" title=\""+title+"\" filedValue=\""+filedValue+"\" statistics=\""+statistics+"\"><span>"+title+"("+statistics+")</span><img onclick=\"$(this).parent().remove();renderOrderFields();renderGraphics();\" src=\"imgs/stop.png\" class=\"setting\"/><img src=\"imgs/setting.png\" onclick=\"setting(this)\" class=\"setting modal-menu-test\"/></div>").appendTo($("#zzdiv"));
	}else if(flag==2){//次轴添加
		$("<div class=\"sz\" title=\""+title+"\" filedValue=\""+filedValue+"\" statistics=\""+statistics+"\"><span>"+title+"("+statistics+")</span><img onclick=\"$(this).parent().remove();renderOrderFields();renderGraphics();\" src=\"imgs/stop.png\" class=\"setting\"/><img src=\"imgs/setting.png\" onclick=\"setting(this)\" class=\"setting modal-menu-test\"/></div>").appendTo($("#czdiv"));
	}else if(flag==3){//编辑数值
		targetSzItem.attr("title",title);
		targetSzItem.attr("filedValue",filedValue);
		targetSzItem.attr("statistics",statistics);
		targetSzItem.find("span").html(title+"("+statistics+")");
	}
	
	//获取排序字段
	renderOrderFields();
	
	//关闭modal窗口
	$(".modal-win-close").click();
	
	renderGraphics();
}

var targetSzItem;//全局变量，用于标记当前设置的是哪个数值的div对象
function setting(obj){
	$(obj).modal();//先显示对话框
	flag = 3;//调用的编辑
	targetSzItem = $(obj).parent();
	var filedValue = targetSzItem.attr("filedValue");
	var statistics = targetSzItem.attr("statistics");
	
	$("#statisticsField").val(filedValue);
	$("#statisticsMethod").val(statistics);
}

//获取排序字段
function renderOrderFields(){
	//清空
	$("#orderField").find("option").not(":first").remove();
	
	//获取已经渲染的主轴   次轴数值
	var sz=$("div .sz");
    for(var i=0;i<sz.length;i++){
    	
    	var statistics=$(sz[i]).attr("statistics");
    	var s="";
    	if(statistics=="最大值"){
    		s="MAX";
    	}else if(statistics=="总计"){
    		s="SUM";
    	}else if(statistics=="平均值"){
    		s="AVG";
    	}else if(statistics=="计数"){
    		s="COUNT";
    	}else if(statistics=="最小值"){
    		s="MIN";
    	}
    	$("#orderField").append("<option value="+s+"("+$(sz[i]).attr("filedValue")+")"+" >"+$(sz[i]).attr("title")+"("+$(sz[i]).attr("statistics")+")</option>");
    }
}



//权限设置
function privSetting(){
	
	var url=contextPath+"/system/subsys/ereport/privSetting.jsp?sid="+sid;
	
	bsWindow(url ,"查看权限设置",{width:"600",height:"320",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		     var  json=cw.doSave();
		     if(json.rtState){
		    	   $.MsgBox.Alert_auto("权限设置成功！");
			       return true;
			    }else{
			       $.MsgBox.Alert_auto("权限设置失败！");
			       return false;
			    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

//保存
function doUpdate(){
	if(check()){
		var param=getParams();
		
		var url=contextPath+"/teeEreportController/update.action";
		var json=tools.requestJsonRs(url,param);
		if(json.rtState){
			$.MsgBox.Alert_auto("数据保存成功！",function(){
				history.go(-1);
			});
		}else{
			$.MsgBox.Alert_auto("数据保存失败！");
		}
	}
}


//获取页面上的参数
function getParams(){
	//拼接主轴数值json数组   次轴数值json数组     排序json字符串
	var spindle=[];//主轴数值
	var shaft=[];//次轴数值
	var orderStr={};//排序
	var zz=$("#zzdiv .sz");
	if(zz.length>0){
		for(var i=0;i<zz.length;i++){
			spindle.push({title:$(zz[i]).attr("title"),statistics:$(zz[i]).attr("statistics"),fieldValue:$(zz[i]).attr("filedValue")});
		}	
	}
	
	var cz=$("#czdiv .sz");
	if(cz.length>0){
		for(var i=0;i<cz.length;i++){
			shaft.push({title:$(cz[i]).attr("title"),statistics:$(cz[i]).attr("statistics"),fieldValue:$(cz[i]).attr("filedValue")});
		}	
	}
	
	//获取排序字段   排序方式
	 var orderField=$("#orderField").val();
	 var orderDesc=$("#orderDesc").val();
	 orderStr["orderField"]=orderField;
	 orderStr["orderDesc"]=orderDesc;

	 
	 //获取过滤条件
	 var conditionExp=$("#conditionExp").val();
	 
	 //获取条件项目
	 var conditionItems=[]; //条件项
	 //获取所有的条件项
	 var ands=$(".and");
	 for(var i=0;i<ands.length;i++){
		 //第一个select
		 var firstSelect=$(ands[i]).find("select:eq(0)");
		 var field=$(firstSelect).val();
		 var type=$(firstSelect).find("option:selected").attr("type");
		 //第二个select
		 var secondSelect=$(ands[i]).find("select:eq(1)"); 
		 var oper=$(secondSelect).val();
		 
		 //第一个input
		 var firstInput=$(ands[i]).find("input:eq(0)"); 
		 var v1=$(firstInput).val();
		 if(v1==undefined){
			 v1="";
		 }
		 
		 //第二个input
		 var secondInput=$(ands[i]).find("input:eq(1)"); 
		 var v2=$(secondInput).val();
		 if(v2==undefined){
			 v2="";
		 }
		
		 conditionItems.push({field:field,type:type,oper:oper,v1:v1,v2:v2}); 
	 }
	 
	
	var param=tools.formToJson("#form1");
	param["sid"]=sid;
	param["spindle"]=tools.jsonArray2String(spindle);
	param["shaft"]=tools.jsonArray2String(shaft);
	param["orderStr"]=tools.jsonObj2String(orderStr);
	param["conditionExp"]=conditionExp;
	param["conditionItems"]=tools.jsonArray2String(conditionItems);
	
	return param;
}

//验证视图    维度    主轴 是必填的 
function check(){
	var title=$("#title").val();
	var bisViewIdentity=$("#bisViewIdentity").val();
	var dimension=$("#dimension").val();
	//标题
	if(title==""||title==null||title=="null"){
		$.MsgBox.Alert_auto("请填写报表名称！");
		return false;
	}
	
	//主轴数值
	var zc=$("#zzdiv .sz");
	if(bisViewIdentity==""||bisViewIdentity==null||bisViewIdentity=="null"){
		$.MsgBox.Alert_auto("请选择所属视图！");
		return false;
	}
	
	if(dimension==""||dimension==null||dimension=="null"){
		$.MsgBox.Alert_auto("请选择维度！");
		return false;
	}
	
	if(zc.length<=0){
		$.MsgBox.Alert_auto("请至少添加一项主轴数值！");
		return false;
	}
	
	return true;
}

//验证是否是饼状图
function checkIsPie(){//饼状图    只能有一个主轴数值
	var  chartType=$("#chartType").val();
	if(chartType==4){//饼状图
		var zz=$("#zzdiv .sz");
		if(zz.length>0){
			$.MsgBox.Alert_auto("饼状图只能选择一项主轴数值！");
			return false;
		}else{
			return true;
		}
	}else{
		return true;
	}
}



function renderGraphics(){
	var param=getParams();
	var url=contextPath+"/teeEreportController/renderGraphics.action";
	var json=tools.requestJsonRs(url,param);
	if(json.rtState){
		var data=json.rtData;
		
		if(data.chartTypeDesc=="pie"){//饼状图
			$('#container').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
		        },
		        title: {
		            text: data.title
		        },
		        tooltip: {
		            headerFormat: '{series.name}<br>',
		            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
		                    style: {
		                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
		                    }
		                }
		            }
		        },
		        series: [{
		            type: 'pie',
		            name: data.title,
		            data: data.series
		        }]
		    });
		}else if(data.chartTypeDesc=="xy"){//双轴图
			$('#container').highcharts({
		        chart: {
		            zoomType: 'xy'
		        },
		        title: {
		            text: data.title
		        },
		        xAxis: [{
		            categories: data.categories,
		            crosshair: true
		        }],
		        yAxis: [{
		            labels: {
		                format: '{value}'
		            },
		            title: {
		                text: '主轴数值'   
		            }
		        }, {
		            
		            labels: {
		                format: '{value}'
		            },
		            title: {
		                text: '次轴数值'
		            },opposite:true
		        }],
		        
		        tooltip: {
		            shared: true
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'left',
		            x: 80,
		            verticalAlign: 'top',
		            y: 55,
		            floating: true,
		            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
		        },
		        series:data.series
		        	
		    });
		}else{
			$("#container").highcharts({
		        chart: {
		            type: data.chartTypeDesc   //column 柱狀  bar 條形圖  pie 餅圖  line 折綫圖 
		        },
		        title: {
		            text: data.title
		        },
		        xAxis: {
		            categories: data.categories
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '统计值'
		            }
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        tooltip: {
		            headerFormat: '<span style="font-size:12px;font-weight:bold">{point.key}</span><table style="font-size:12px">',
		            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		                '<td style="padding:0"><b>{point.y}</b></td></tr>',
		            footerFormat: '</table>',
		            shared: true,
		            useHTML: true
		        },
		        credits: {
		            enabled: false
		        },
		        plotOptions: {
		            column: {
		            	cursor: 'pointer',
		                pointPadding: 0.2,
		                borderWidth: 0,
		                events: {
		                    click: function (event) {
		                    	//curObj.clickEvent(this.name,event);
		                    }
		                }
		            },
		            bar: {
		            	cursor: 'pointer',
		                pointPadding: 0.2,
		                borderWidth: 0,
		                events: {
		                    click: function (event) {
		                    	//curObj.clickEvent(this.name,event);
		                    }
		                }
		            },
		            line: {
		            	cursor: 'pointer',
		                pointPadding: 0.2,
		                borderWidth: 0,
		                events: {
		                    click: function (event) {
		                    	//curObj.clickEvent(this.name,event);
		                    }
		                }
		            },
		            scatter: {
		            	cursor: 'pointer',
		                pointPadding: 0.2,
		                borderWidth: 0,
		                events: {
		                    click: function (event) {
		                    	//curObj.clickEvent(this.name,event);
		                    }
		                }
		            },
		            area: {
		            	cursor: 'pointer',
		                pointPadding: 0.2,
		                borderWidth: 0,
		                events: {
		                    click: function (event) {
		                    	//curObj.clickEvent(this.name,event);
		                    }
		                }
		            }
		        },
		        series: data.series
		    });
		}
		
	}
	
}



function filterFieldChange(obj){
	var clazz = $(obj).attr("class");
	var type = $(obj).find("option:selected").attr("type");
	var operhtml = [];
	if(type=="TEXT"){
		operhtml.push("<option value='等于'>等于</option>");
		operhtml.push("<option value='不等于'>不等于</option>");
		operhtml.push("<option value='开头包含'>开头包含</option>");
		operhtml.push("<option value='结尾包含'>结尾包含</option>");
		operhtml.push("<option value='包含'>包含</option>");
		operhtml.push("<option value='不包含'>不包含</option>");
	}else if(type=="NUMBER"){
		operhtml.push("<option value='等于'>等于</option>");
		operhtml.push("<option value='不等于'>不等于</option>");
		operhtml.push("<option value='大于'>大于</option>");
		operhtml.push("<option value='大于等于'>大于等于</option>");
		operhtml.push("<option value='小于'>小于</option>");
		operhtml.push("<option value='小于等于'>小于等于</option>");
		operhtml.push("<option value='区间'>区间</option>");
	}else if(type=="DATE"){
		operhtml.push("<option value='等于'>等于</option>");
		operhtml.push("<option value='不等于'>不等于</option>");
		operhtml.push("<option value='大于'>大于</option>");
		operhtml.push("<option value='大于等于'>大于等于</option>");
		operhtml.push("<option value='小于'>小于</option>");
		operhtml.push("<option value='小于等于'>小于等于</option>");
		operhtml.push("<option value='区间'>区间</option>");
		operhtml.push("<option value='上年'>上年</option>");
		operhtml.push("<option value='上季'>上季</option>");
		operhtml.push("<option value='上月'>上月</option>");
		operhtml.push("<option value='上周'>上周</option>");
		operhtml.push("<option value='昨日'>昨日</option>");
		operhtml.push("<option value='本年'>本年</option>");
		operhtml.push("<option value='本季'>本季</option>");
		operhtml.push("<option value='本月'>本月</option>");
		operhtml.push("<option value='本周'>本周</option>");
		operhtml.push("<option value='最近天数'>最近天数</option>");
	}
	
	$("."+clazz+"_oper").html(operhtml.join(""));
	filterOperChange($("."+clazz+"_oper")[0]);
}

function filterOperChange(obj){
	var clazz = $(obj).attr("class");
	var oper = $(obj).val();
	var prefix = clazz.split("_")[0];
	var type = $("."+prefix).find("option:selected").attr("type");
	
	if(type=="TEXT"){
		$("."+clazz+"_span").html("<input noclear type='text' style='width:80px;height:17px'/>");
	}else if(type=="NUMBER"){
		if(oper=="区间"){
			$("."+clazz+"_span").html("<input noclear type='text'  style='width:80px;height:17px'/>-<input noclear style='width:80px;height:17px' type='text' />");
		}else{
			$("."+clazz+"_span").html("<input noclear type='text'  style='width:80px;height:17px'/>");
		}
	}else if(type=="DATE"){
		if(oper=="区间"){
			$("."+clazz+"_span").html("<input noclear type='text' class='Wdate' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\" style='width:80px;height:17px'/>-<input noclear style='width:80px;height:17px' type='text' class='Wdate' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\"/>");
		}else if(oper=="等于" || oper=="不等于" || oper=="大于" || oper=="大于等于" || oper=="小于" || oper=="小于等于"){
			$("."+clazz+"_span").html("<input noclear type='text'  class='Wdate' style='width:80px;height:17px' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\"/>");
		}else if(oper=="最近天数"){
			$("."+clazz+"_span").html("<input noclear type='text'  style='width:80px;height:17px'/>");
		}else{
			$("."+clazz+"_span").html("");
		}
	}
}

function addNewFilter(a,b,c,d){
	var rand = new Date().getTime();
	var html = [];
	html.push("<div class=\"and\"><span></span>");
	html.push("<select class=\"and"+rand+"\" onchange=\"filterFieldChange(this)\">");
	
	//获取所属视图
	var bisViewIdentity=$("#bisViewIdentity").val();
	var url=contextPath+"/bisView/getBisViewListItemList.action";
	var json=tools.requestJsonRs(url,{identity:bisViewIdentity});
	if(json.rtState){
		var data=json.rtData;
		if(data!=null&&data.length>0){
			for(var i=0;i<data.length;i++){
				if(data[i].fieldType=="TEXT"){
					html.push("<option value='"+data[i].searchField+"' type='TEXT'>"+data[i].title+"</option>");
				}else if(data[i].fieldType=="DATE"){
					html.push("<option value='${DATE}"+data[i].searchField+"' type='DATE'>"+data[i].title+"</option>");
				}else{
					html.push("<option value='"+data[i].searchField+"' type='NUMBER'>"+data[i].title+"</option>");
				}
			}
		}
	}
	
	html.push("</select>&nbsp;&nbsp;");
	html.push("<select class=\"and"+rand+"_oper\" onchange=\"filterOperChange(this)\">");
	html.push("</select>&nbsp;&nbsp;");
	html.push("<span class=\"and"+rand+"_oper_span\">");
	html.push("</span>");
	html.push("<img onclick=\"$(this).parent().remove();reCalFilterSeq();\" src=\"imgs/stop.png\" class=\"setting\"/>");
	html.push("</div>");
	
	$("#filterDiv").append(html.join(""));
	
	//重新计算条件序号
	reCalFilterSeq();
	
	filterFieldChange($(".and"+rand)[0]);
	if(a){
		$(".and"+rand).val(a);
		filterFieldChange($(".and"+rand)[0]);
	}
	
	if(b){
		$(".and"+rand+"_oper").val(b);
		filterOperChange($(".and"+rand+"_oper")[0]);
	}
	
	if(c){
		$(".and"+rand+"_oper_span input:eq(0)").val(c);
	}
	
	if(d){
		$(".and"+rand+"_oper_span input:eq(1)").val(d);
	}
}

function reCalFilterSeq(){
	$(".and").each(function(i,obj){
		$(obj).find("span:first").html("["+(++i)+"]&nbsp;&nbsp;");
	});
}

//保存过滤条件
function saveCondition(){
	//关闭modal窗口
	$(".modal-win-close").click();
	
	renderGraphics();
}
</script>
</head>
<body onload="doInit();" style="overflow:hidden;">
<form id="form1">
<div id="toolbar" class = "topbar clearfix" style="height:20px;position:absolute;top:0px;left:10px;right:10px;">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="imgs/icon_bbgl.png">
		<span class="title">报表设计</span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class="btn-win-white fl" onclick="doUpdate();" value="保存"/>
		<input type="button" class="btn-del-red fl" onclick="history.go(-1)" value="返回"/>
    </div>
</div>
<div style="position:absolute;top:48px;left:10px;bottom:0px;right:230px;overflow:hidden;border-right:1px solid #74c5ff">
	<p>报表名称：<input id="title" name="title" type="text" style="width:200px;height:20px" class="BigInput"></p>
	<div id="container">
		
	</div>
</div>
<div style="position:absolute;top:48px;bottom:0px;right:0px;width:220px;overflow-x:hidden">
	<table style="width:100%" class="TableBlock_page">
	    <tr>
	        <td width="80px;" align=right nowrap="nowrap">报表分类：</td>
	        <td>
	           <select id="catId" name="catId" style="width:95%" >
	               <option value="0" >请选择</option>
	           </select>
	        </td>
	    </tr>
	    <tr>
	        <td width="80px;" align=right nowrap="nowrap">所属视图：</td>
	        <td>
	           <select id="bisViewIdentity" name="bisViewIdentity" style="width:95%" onchange="changeBisView();renderGraphics();">
	              <option value="" >请选择</option>
	           </select>
	        </td>
	    </tr>
	    <tr>
	        <td align=right nowrap="nowrap">图表类型：</td>
	        <td>
	           <select id="chartType" name="chartType" onchange="changeChartType();renderGraphics();">
	              <option value="1">柱状图</option>
	              <option value="2">条形图</option>
	              <option value="3">折线图</option>
	              <option value="4">饼状图</option>
	              <option value="5">双轴图</option>
	           </select>
	        </td>
	    </tr>
	    <tr>
	        <td align=right nowrap="nowrap">维度：</td>
	        <td>
	           <select  id="dimension" name="dimension" onchange="renderGraphics();">
	              
	           </select>
	        </td>
	    </tr>
	    <tr>
	        <td align=right nowrap="nowrap">主轴数值：</td>
	        <td>
	          	<img class="modal-menu-test adding" onclick='if(checkIsPie()){flag=1;$(this).modal();}' src="imgs/add.png"></img>
	        </td>
	    </tr>
	    <tr>
	        <td colspan="2" id="zzdiv">
	        	
	        </td>
	    </tr>
	    <tr id="czTr" style="display: none">
	        <td align=right nowrap="nowrap">次轴数值：</td>
	        <td>
	          	<img class="modal-menu-test adding" onclick='flag=2;$(this).modal();' src="imgs/add.png"></img>
	        </td>
	    </tr>
	    <tr id="czTr1" style="display: none">
	        <td colspan="2"  id="czdiv">
	        </td>
	    </tr>
	    <tr>
	        <td align=right nowrap="nowrap">排序：</td>
	        <td>
	           <select id="orderField" name="orderField" style="width:100px" onchange="renderGraphics();">
	              <option value="" >无</option>
	           </select>
	           <br>
	           <select id="orderDesc" name="orderDesc" style="width:100px;margin-top: 3px" onchange="renderGraphics();">
	              <option value="" >无</option>
	              <option value="asc" >升序</option>
	              <option value="desc" >降序</option>
	           </select>
	        </td>
	    </tr>
	    <!-- <tr>
	        <td align=right>过滤：</td>
	        <td>
	          	<img class="adding" onclick='' src="imgs/add.png"></img>
	        </td>
	    </tr> -->
	    <tr>
	        <td align=right nowrap="nowrap">查看权限：</td>
	        <td>
	          	<img class="adding"  src="imgs/add.png" onclick="privSetting();"></img>
	        </td>
	    </tr>
	    <tr>
	        <td align=right nowrap="nowrap">过滤条件：</td>
	        <td>
	          	<img class="adding modal-menu-test1"  src="imgs/add.png" onclick="$(this).modal();"></img>
	        </td>
	    </tr>
	</table>
</div>

<!-- Modal -->
   <div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			新建数值
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body">
		<table style="width:100%" class="TableBlock_page">
		    <tr>
		        <td width="80px;" align=right>统计字段：</td>
		        <td>
		           <select id="statisticsField" name="statisticsField" >
						
					</select>
		        </td>
		    </tr>
		    <tr>
		        <td width="80px;" align=right>类型：</td>
		        <td>
		           <select id="statisticsMethod" name="statisticsField"> 
						<option value="总计">总计</option>
						<option value="平均值">平均值</option>
						<option value="计数">计数</option>
						<option value="最大值">最大值</option>
						<option value="最小值">最小值</option>
					</select>
		        </td>
		    </tr>
	    </table>
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="addSz();" value = '保存'/>
	</div>
</div>

</form>

<!-- Modal -->
   <div class="modal-test1">
	<div class="modal-header">
		<p class="modal-title">
			过滤条件
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body">
		条件公式：<input noclear type="text" id="conditionExp" name="conditionExp"  style="width:250px;height: 23px;" align="left" /><img class="adding setting" src="imgs/add.png" onclick="addNewFilter()" align="left"></img>
		<div id="filterDiv">
			
		</div>
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="saveCondition();" value = '保存'/>
	</div>
</div>

</body>
</html>