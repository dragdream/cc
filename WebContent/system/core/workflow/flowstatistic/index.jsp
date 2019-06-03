<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.text.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=contextPath %>/common/highcharts/js/highcharts.src.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	Calendar t1 = Calendar.getInstance();
	t1.set(Calendar.MONTH, 0);
	
	Calendar t2 = Calendar.getInstance();
	t2.set(Calendar.MONTH, t2.getActualMaximum(Calendar.MONTH));
%>
<script>
function doInit(){
	/* ZTreeTool.comboCtrl($("#flowId"),{url:contextPath+"/workQuery/getFlowType2SelectCtrl.action"}); */
}

function change0(val){
	if(val=="1"){//人员
		$("#user").show();
		$("#dept").hide();
	}else if(val=="2"){//部门
		$("#user").hide();
		$("#dept").show();
	}else if(val=="3"){//月份
		$("#user").hide();
		$("#dept").hide();
	}
}

//选择所属流程
function selectFlowType(){
	bsWindow(contextPath+"/system/core/workflow/workmanage/flowRule/flowTree.jsp","选择流程",{width:"400",height:"250",buttons:
		[{name:"选择",classStyle:"btn-alert-blue"} ,
	 	 {name:"关闭",classStyle:"btn-alert-gray"}],submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="选择"){
			if(isNaN(parseInt(h.contents().find("#flowId").val()))){//不是数字
  		    	$.MsgBox.Alert_auto("请选择流程！");
  		        return;
  		    }else{
  		    	//获取弹出页面的流程的名称和流程的id
                 $("#flowName").val(h.contents().find("#flowName").val());
                 $("#flowId").val(h.contents().find("#flowId").val());  	
  		    }
		    return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
	
	
}


function commit(type){
	if(type==1){//超时统计
		$("#form").attr("action","timeoutStatistics.jsp");
	}else if(type==2){//处理情况统计
		$("#form").attr("action","handleStatistics.jsp");
	}else if(type==3){//办理情况统计
		$("#form").attr("action","handle0Statistics.jsp");
	}
	
	 var  flowId = document.getElementById("flowId");
	   	if(flowId.value==""){
	   		$.MsgBox.Alert_auto("请选择一个流程！");
			return false;
		}
	
	if($("#target").val()=="1" && $("#userIds").val()==""){
		$.MsgBox.Alert_auto("请选择至少一个用户！");
		return;
	}
	if($("#target").val()=="2" && $("#deptIds").val()==""){
		$.MsgBox.Alert_auto("请选择至少一个部门！");
		return;
	}
	$("#form").submit();
}
</script>
<style>
table td{
padding:5px;
}
</style>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;font-family: MicroSoft YaHei;">
<div id="toolbar" class = "topbar clearfix" >
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gzl/lctj/icon_lctj.png">&nbsp;&nbsp;
		<span class="title">流程统计</span>
	</div>
</div>

<div class="base_layout_center" style="padding:10px">
	<form id="form" method="post" >
			<table class="TableBlock_page" style="width:90%">
				<tr>
					<td style="text-indent: 10px;" class="TableData">选择时间段：</td>
					<td class="TableData">
						<input style="width: 200px;height:25px;" type="text" name="beginTime" id="beginTime" value="<%=sdf.format(t1.getTime()) %>" onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'endTime\')}'})" class="Wdate BigInput" />
						~
						<input style="width: 200px;height:25px;" type="text" name="endTime" id="endTime"  value="<%=sdf.format(t2.getTime()) %>" onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'beginTime\')}'})" class="Wdate BigInput" />
					</td>
				</tr>
				<tr>
					<td style="text-indent: 10px;" class="TableData">选择流程：</td>
					<td class="TableData">
					    <input style="width: 200px;height:25px;font-family: MicroSoft YaHei;" type="text" readonly id="flowName" name="flowName"  onclick="selectFlowType();"/>
		                <input type="text" readonly id="flowId" name="flowId" style="display: none;" />
					
					</td>
				</tr>
				<tr>
					<td style="text-indent: 10px;" class="TableData">分组对象：</td>
					<td class="TableData">
						<select style="width: 200px;height:25px;font-family: MicroSoft YaHei;" class="BigSelect" onchange="change0(this.value)" id="target" name="target">
							<option value="1">人员</option>
							<option value="2">部门</option>
							<option value="3">月份</option>
						</select>
					</td>
				</tr>
				<tr id="user">
					<td style="text-indent: 10px;" class="TableData">人员范围：</td>
					<td class="TableData">
						<input type="hidden" name="userIds" id="userIds"/>
						<textarea type="hidden" name="userNames" readonly id="userNames" style="height:90px;width:320px" class="readonly BigTextarea"></textarea>
						<span class='addSpan'>
			                 <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/lctj/add.png" onClick="selectUser(['userIds','userNames'])" value="选择"/>
			                 &nbsp;
			                 <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/lctj/clear.png" onClick="clearData('userIds','userNames')" value="清空"/>
	                   </span>	
					</td>
				</tr>
				<tr style="display:none" id="dept">
					<td style="text-indent: 10px;" class="TableData">部门范围：</td>
					<td class="TableData">
						<input type="hidden" name="deptIds" id="deptIds"/>
						<textarea type="hidden" name="deptNames" readonly id="deptNames" style="height:90px;width:320px" class="readonly BigTextarea"></textarea>
						&nbsp;<a href="javascript:void(0)" onclick="selectDept(['deptIds','deptNames'])">选择</a>
						&nbsp;<a href="javascript:void(0)" onclick="clearData('deptIds','deptNames')">清空</a>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center">
						<button style="height: 25px;" class="btn-win-white" type="button" onclick="commit(1)">超时统计</button>
						&nbsp;
						<button style="height: 25px;" class="btn-win-white" type="button" onclick="commit(2)">流程处理情况统计</button>
						&nbsp;
						<button style="height: 25px;" class="btn-win-white" type="button" onclick="commit(3)">流程办理情况统计</button>
					</td>
				</tr>
			</table>
	</form>
	</div>
	<div id="container">
		
	</div>
</body>
</html>