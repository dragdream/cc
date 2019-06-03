<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<%
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		Calendar c3 = Calendar.getInstance();
		Calendar c4 = Calendar.getInstance();
		Calendar c5 = Calendar.getInstance();
		Calendar c6 = Calendar.getInstance();
		Calendar c7 = Calendar.getInstance();
		Calendar c8 = Calendar.getInstance();
		Date date=new Date();//取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,-1);//
		date=calendar.getTime(); //
	    int weekday1 = c1.get(Calendar.DAY_OF_WEEK) - 2;
	    c1.add(Calendar.DATE, -weekday1);
	    int weekday2 = c2.get(Calendar.DAY_OF_WEEK);
	    c2.add(Calendar.DATE, 8 - weekday2);
	    int lastWeekday1 = c5.get(Calendar.DAY_OF_WEEK) - 2;
	    c5.add(Calendar.WEEK_OF_YEAR, -1);
	    c5.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	    c6.add(Calendar.WEEK_OF_YEAR,0);
	    c6.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	    c8.set(Calendar.DATE, 0);
	    c7.set(Calendar.DATE, 1);
	    c7.add(Calendar.MONTH, -1);
	    c3.set(Calendar.DATE, 1);
	    c4.set(Calendar.DATE, 1);
	    c4.add(Calendar.MONTH, 1);
	    c4.add(Calendar.DATE, -1);
	    String today = shortSdf.format(new Date());
	    String yesterday = shortSdf.format(date);
	    String monthDate1 = shortSdf.format(c3.getTime());
	    String monthDate2 = shortSdf.format(c4.getTime());
	    String weekDate1 = shortSdf.format(c1.getTime());
	    String weekDate2 = shortSdf.format(c2.getTime());
	    String lastWeekDate1 = shortSdf.format(c5.getTime());
	    String lastWeekDate2 = shortSdf.format(c6.getTime());
	    String lastMonthDate1 = shortSdf.format(c7.getTime());
	    String lastMonthDate2 = shortSdf.format(c8.getTime());
		//System.out.println(yesterday+","+today+","+weekDate1+","+weekDate2+","+monthDate1+","+monthDate2+","+lastWeekDate1+","+lastWeekDate2+","+lastMonthDate1+","+lastMonthDate2);

	%>
	<title>Tenee办公自动化智能管理平台</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css">
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery.portlet.css?v=1.1.2" type="text/css" rel="stylesheet" />
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
    <link  href="<%=contextPath%>/common/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" /> 
    <script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery.min_cxt.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script src="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.min.js" type="text/javascript"></script>
	<script src="<%=contextPath%>/system/core/workflow/workmanage/workquery/seniorQuery/js/seniorQuery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/js/src/orgselect.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		$(function() {
			if("${tplId}"!=""&&"${tplId}"!=null){
				var json = tools.strToJson("${queryEdit}");
				bindJsonObj2Cntrl(json);
				$("#tplId").val("${tplId}");
				var choseList = json.dispFld;
				for (var i = document.form1.sumFldList.options.length-1; i>=0; i--){
					var option_text=document.form1.sumFldList.options[i].text;
					var option_value=document.form1.sumFldList.options[i].value;
					if(choseList.indexOf(option_value)==-1){
					    var my_option = document.createElement("OPTION");
					    my_option.text=option_text;
					    my_option.value=option_value;
					    var pos=func_find(document.form1.standbyFldList,option_text);
					    document.form1.standbyFldList.add(my_option,pos);
					    document.form1.sumFldList.remove(i);
					}
				}
			}
		});
		
		function seniorQuery(){
			//跳转页面，springMVC 返回对象
			window.location = contextPath + "/seniorQuery/flowList.action";
		}
		
		function saveTpl(){
			document.form1.sumFld.value="";
			document.form1.dispFld.value="";
			for (var i = 0; i < document.form1.sumFldList.options.length; i++){
			    option_value = document.form1.sumFldList.options[i].value;
			    document.form1.dispFld.value+=option_value+",";
			    if(document.form1.sumFldList.options[i].selected){
			        document.form1.sumFld.value+=option_value+",";
			    }
			}
			var url = "<%=contextPath%>/seniorQuery/saveOrUpdateSeniorTpl.action";
			var para =  tools.formToJson($("#form1")) ;
			var jsonRs = tools.requestJsonRs(url,para);
			if(jsonRs.rtState){
				alert(jsonRs.rtMsg);
				CloseWindow();
			}
		}
		//左侧->右侧
		function func_delete(){
			 for (var i = document.form1.sumFldList.options.length-1; i>=0; i--){
				   option_text=document.form1.sumFldList.options[i].text;
				   option_value=document.form1.sumFldList.options[i].value;
				   if(document.form1.sumFldList.options[i].selected){
					     if(document.form1.groupFld.value==option_value){
					       alert("分组字段必须统计！");
					       return;
					     }
				     var my_option = document.createElement("OPTION");
				     my_option.text=option_text;
				     my_option.value=option_value;
		
				     var pos=func_find(document.form1.standbyFldList,option_text);
				     document.form1.standbyFldList.add(my_option,pos);
				     document.form1.sumFldList.remove(i);
				  }
			 }
		}
		
		function func_find(select_obj,option_text){
		   pos=option_text.indexOf("] ")+1;
		   option_text=option_text.substr(0,pos);

		   for (var j=0; j<select_obj.options.length; j++){
		       str=select_obj.options[j].text;
		       if(str.indexOf(option_text)>=0)
		       return j;
		   }

		   return j;
		}
		//右侧->左侧
		function func_insert(){
		    for (var i=document.form1.standbyFldList.options.length-1; i>=0; i--){
		        if(document.form1.standbyFldList.options[i].selected){
		     	    option_text=document.form1.standbyFldList.options[i].text;
		            option_value=document.form1.standbyFldList.options[i].value;
		     	    option_style_color=document.form1.standbyFldList.options[i].style.color;
			        var my_option = document.createElement("OPTION");
			        my_option.text=option_text;
			        my_option.value=option_value;
			        my_option.style.color=option_style_color;
			        var pos=func_find(document.form1.sumFldList,option_text);
			        document.form1.sumFldList.add(my_option,pos);
			        document.form1.standbyFldList.remove(i);
		  		 }
		    }
		}
		
		function select_all1(flag){
		    for (var i=0; i<document.form1.sumFldList.options.length; i++){
		        document.form1.sumFldList.options[i].selected=flag;
		    }
		}
		function select_all2(){
		    for (var i=0; i<document.form1.standbyFldList.options.length; i++){
		        document.form1.standbyFldList.options[i].selected=1;
		    }
		}
		
		function dataShowHide(){
		    if(document.getElementById("queryItem").style.display=="")
		    	document.getElementById("queryItem").style.display="none";
		    else
		    	document.getElementById("queryItem").style.display="";
		}
		
		function dateChange(opt,flag){
			if(flag==1){
			  var prcs_date1 = document.form1.prcsDate1;
			  var prcs_date2 = document.form1.prcsDate2;
			}
			else{
			  var prcs_date1=document.form1.endDate1;
			  var prcs_date2=document.form1.endDate2;
			}
			switch(opt){
				case '1':
					prcs_date1.value="<%=today%>";
					prcs_date2.value="<%=today%>";
					break;
				case '2':
					prcs_date1.value="<%=yesterday%>";
					prcs_date2.value="<%=yesterday%>";
					break;
				case '3':
					prcs_date1.value="<%=weekDate1%>";
					prcs_date2.value="<%=weekDate2%>";
					break;
				case '4':
					prcs_date1.value="<%=lastWeekDate1%>";
					prcs_date2.value="<%=lastWeekDate2%>";
					break;
				case '5':
					prcs_date1.value="<%=monthDate1%>";
					prcs_date2.value="<%=monthDate2%>";
					break;
				case '6':
					prcs_date1.value="<%=lastMonthDate1%>";
					prcs_date2.value="<%=lastMonthDate2%>";
					break;
			}
		}
		function changeRadio(name,value){
			$("#"+name).val(value);
		}
		function back(){
			window.history.go(-1);
		}
		function hide_item(obj,radio){

		}
	</script>
	
</head>
<body style="margin-top: 1em;overflow-x:hidden;">
<form method="post" id="form1" name="form1">
<table style="border:'0' ;width:'100%' ;cellspacing:'0' ;cellpadding:'3'" class="small">
  	<tr>
    	<td class="Big"><img src="<%=imgPath%>/green_arrow.gif" align="absMiddle"><span class="big3">${type}（流程名称：${flow.flowName}）</span>
    	</td>
  	</tr>
</table>

<table style="border:'0'; width:'90%' ;text-align:'center'" class="TableBlock">
    <tr>
      <td nowrap class="TableHeader" height=25 colspan="3"><img src="<%=imgPath%>/green_arrow.gif" align="absmiddle"> 模板基本信息&nbsp;&nbsp;
      </td>
    </tr>
    <tr>
      	<td nowrap class="TableContent" width="33%"><b>模板名称：</b></td>
      	<td class="TableData" colspan="2">
			<input type="text" name="tplName" id="tplName" value="" size="30" maxlength="100" class="SmallInput">
      	</td>
    </tr>
    <tr>
      	<td nowrap class="TableHeader" height=25 colspan="3"> <div style="float:left;font-weight:bold"><img src="<%=imgPath%>/green_arrow.gif" align="absmiddle"> 工作流程基本属性</div></td>
    </tr>
    <tr>
      	<td nowrap class="TableContent"><b>工作流状态：</b></td>
      	<td class="TableData" colspan="2">
        	<select id = "flowStatus" name="flowStatus" class="SmallSelect" style="width:100px" onChange="selStatus(this.value)">
          		<option value="0" >所有</option>
          		<option value="1" >已结束</option>
          		<option value="0" >执行中</option>
        	</select>
      	</td>
    </tr>
    <tr>
      	<td nowrap class="TableContent"><b>查询范围：</b></td>
      	<td class="TableData" colspan="2">
        	<select id= "flowQueryType" name="flowQueryType" class="SmallSelect" onChange="set_user();" style="width:100px">
          		<option value="0" >所有</option>
          		<option value="1" >我发起的</option>
          		<option value="2" >我经办的</option>
          		<option value="3" >我管理的</option>
        	</select>
      	</td>
    </tr>
    <tr>
		<td nowrap class="TableContent"><b>流程发起人：</b></td>
		<td nowrap class="TableData" colspan="2">
			<input type="hidden" name="beginUserId" id="beginUserId" required="true" value=""> 
			<textarea cols="20" name="beginUserName" id="beginUserName" rows="1" style="overflow-y: auto;"  class="SmallStatic" wrap="yes" readonly></textarea>
			<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['beginUserId', 'beginUserName'])">添加</a> <a href="javascript:void(0);" class="orgClear" onClick="clearData('beginUserId', 'beginUserName')">清空</a>
		</td>
    </tr>
     <tr>
      	<td nowrap class="TableContent"><b>名称/文号：</b></td>
      	<td nowrap class="TableData" colspan=2>
           	<select name="runNameRelation" id="runNameRelation" class="SmallSelect" style="width:100px">
           	   	<option value="1" >等于</option>
		        <option value="2" >大于</option>
		        <option value="3" >小于</option>
		        <option value="4" >大于等于</option>
		        <option value="5" >小于等于</option>
		        <option value="6" >不等于</option>
		        <option value="7" >开始为</option>
		        <option value="8" >包含</option>
		        <option value="9" >结束为</option>
       		</select>
        <input type="text" name="runName" id="runName" value="" size="30" maxlength="100" class="SmallInput">
      </td>
    </tr>
    <tr>
      	<td nowrap class="TableContent"><b>流程开始日期范围：</b></td>
      	<td class="TableData" colspan="2">
      		从<input type="text" name="prcsDate1" id="prcsDate1" size="10" class="SmallInput" value="" onClick="WdatePicker()">
      		至<input type="text" name="prcsDate2" id="prcsDate2" size="10" class="SmallInput" value="" onClick="WdatePicker()">
        	<a href="javascript:emptyDate('0');" class="orgClear" onClick="emptyDate('0');">清空</a>&nbsp;
        	<select name="dateRange" id = "dateRange" class="SmallSelect" onChange="dateChange(this.value,'1')">
        		<option value="0">快捷选择</option>
        		<option value="1">今日</option>
        		<option value="2">昨日</option>
        		<option value="3">本周</option>
        		<option value="4">上周</option>
        		<option value="5">本月</option>
        		<option value="6">上月</option>
        	</select>
      	</td>
    </tr>
    <tbody style="display:none" id="endTimeArea">
	    <tr>
	      	<td nowrap class="TableContent"><b>流程结束日期范围：</b></td>
	      	<td class="TableData" colspan="2">
	        	从<input type="text" name="endDate1" id="endDate1" size="10" class="SmallInput" value="" onClick="WdatePicker()">
	       		 至<input type="text" name="endDate2" id="endDate2" size="10" class="SmallInput" value="" onClick="WdatePicker()">
	        	<a href="javascript:emptyDate('1');" class="orgClear" onClick="emptyDate('1');">清空</a>&nbsp;
	        	<select name="dateRange1" id="dateRange1" class="SmallSelect" onChange="dateChange(this.value,'2')">
		        	<option value="0">快捷选择</option>
		        	<option value="1">今日</option>
		        	<option value="2">昨日</option>
		        	<option value="3">本周</option>
		        	<option value="4">上周</option>
		        	<option value="5">本月</option>
		        	<option value="6">上月</option>
	        	</select>
	      	</td>
	    </tr>
   </tbody>
   <tr>
       	<td nowrap class="TableContent"><b>公共附件名称：</b></td>
      	<td nowrap class="TableData" colspan=2>
      	包含<input type="text" name="attachmentName" id = "attachmentName" value="" size="30" class="SmallInput">
      	</td>
   </tr>
   <tr>
      	<td nowrap class="TableHeader" height=25 colspan="3">
        	<div style="float:left;font-weight:bold"><img src="<%=imgPath%>/green_arrow.gif" align="absmiddle"> 表单数据条件 <a href="javascript:dataShowHide()">显示/隐藏条件</a></div>
      	</td>
   </tr>
   <tbody id="queryItem" >
     	<c:forEach items="${formItem}" var="formItemSort" varStatus="formItemStatus">
     		<c:if test="${formItem[formItemStatus.index].tag ne 'IMG'}">
	     		<tr >
	     			<td nowrap class="TableData" >[${formItem[formItemStatus.index].itemId}] ${formItem[formItemStatus.index].title}：</td>
	     			<td nowrap class="TableData" colspan=2>
	     				<select id="relation_${formItem[formItemStatus.index].itemId}" name="relation_${formItem[formItemStatus.index].itemId}" class="SmallSelect" onchange="hide_item(this,'${formItem[formItemStatus.index].xtype}');">
	     					<option value="1">等于</option>
	     					<c:choose>  
								<c:when test="${formItem[formItemStatus.index].tag ne 'SELECT' && formItem[formItemStatus.index].xtype ne 'xradio'}"> 
									<option value="2">大于</option>
		        					<option value="3">小于</option>
							        <option value="4">大于等于</option>
							        <option value="5">小于等于</option>
							        <option value="6">不等于</option>
							        <option value="7">开始为</option>
							        <option value="8" selected>包含</option>
							        <option value="9">结束为</option>
							        <option value="10">为空</option>
								</c:when> 
								<c:otherwise> 
									<option value="10">为空</option>
								</c:otherwise> 
							</c:choose>
	     				</select>&nbsp;
	     				<c:if test="${formItem[formItemStatus.index].tag eq 'INPUT'}">
							<c:if test="${formItem[formItemStatus.index].xtype eq 'xcheckbox'}">
							 	<select id="${formItem[formItemStatus.index].name}" name="${formItem[formItemStatus.index].name}"  class="SmallSelect">
							 		<option value='SELECT_ALL_VALUE'>所有</option>
							 		<option value='CHECKBOX_ON'>是</option>
							 		<option value='CHECKBOX_OFF'>否</option>
						 		</select>
							</c:if>
							<c:if test="${formItem[formItemStatus.index].xtype eq 'xradio'}">
								<c:forEach items="${formItem[formItemStatus.index].radioList}" var="radioItemSort" varStatus="radioItemStatus">
									<c:choose>  
										<c:when test="${type eq '新建查询模板'}"> 
											<input type="radio"  name="${formItem[formItemStatus.index].name}"  value="${formItem[formItemStatus.index].radioList[radioItemStatus.index].data}" onchange="changeRadio('${formItem[formItemStatus.index].name}','${formItem[formItemStatus.index].radioList[radioItemStatus.index].data}');" ${formItem[formItemStatus.index].radioList[radioItemStatus.index].checked}>${formItem[formItemStatus.index].radioList[radioItemStatus.index].data}
										</c:when>
										<c:otherwise> 
											<input type="radio"  name="${formItem[formItemStatus.index].name}"  value="${formItem[formItemStatus.index].radioList[radioItemStatus.index].data}" onchange="changeRadio('${formItem[formItemStatus.index].name}','${formItem[formItemStatus.index].radioList[radioItemStatus.index].data}');" >${formItem[formItemStatus.index].radioList[radioItemStatus.index].data}
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<!-- 遗留的问题，无法在刚进入页面时给初始值 -->
								<input type="hidden" id = "${formItem[formItemStatus.index].name}" name="${formItem[formItemStatus.index].name}" value="${formItem[formItemStatus.index].radioList[0].data}">
							</c:if>
							<c:if test="${formItem[formItemStatus.index].xtype ne 'xcheckbox' && formItem[formItemStatus.index].xtype ne 'xradio'}"> 
								<input id="${formItem[formItemStatus.index].name}" name="${formItem[formItemStatus.index].name}" type="text" size="30" class="SmallInput" />
							</c:if>
						</c:if>
						<c:if test="${formItem[formItemStatus.index].tag eq 'SELECT'}">
							${formItem[formItemStatus.index].content}
						</c:if>
						<c:if test="${formItem[formItemStatus.index].tag eq 'TEXTAREA'}">
							<input id="${formItem[formItemStatus.index].name}" name="${formItem[formItemStatus.index].name}" type="text" size="30" class="SmallInput" />
						</c:if>
	     			</td>
	     		</tr>
     		</c:if>
   		</c:forEach>
   		<c:if test="${fn:length(formItem) > 0}">
		   	<tr >
		      	<td nowrap class="TableData"><b>表单字段条件公式：</b></td>
		      	<td nowrap class="TableData" colspan="2">
		        	<input type="text" id= "condFormula" name="condFormula" value="" size="35" class="SmallInput" onBlur="check_condition_formula(this);">&nbsp;形如：([1] or [2]) and [3]
		      	</td>
		    </tr>
	    </c:if>
   </tbody>
   <tr>
      	<td nowrap class="TableHeader" height=25 colspan="3"><div style="float:left;font-weight:bold"><img src="<%=imgPath%>/green_arrow.gif" align="absmiddle"> 统计报表选项</div></td>
   </tr>
  
   <!-- 显示分组字段 -->
   <tr>
      	<td nowrap class="TableContent"><b>分组字段：</b></td>
      	<td class="TableData" colspan="2">
        	<select name="groupFld" id = "groupFld" class="SmallSelect">
     		    <option value="runId" >流水号</option>
     		    <option value="runName" >名称/文号</option>
     		    <option value="runStatus" >流程状态</option>
     		    <option value="runDate" >流程开始日期</option>
     		    <option value="runTime" >流程开始时间</option>
     		    <c:forEach items="${formItem}" var="formItemSort1" varStatus="formItemStatus1">
     				<c:if test="${formItem[formItemStatus1.index].tag ne 'IMG'}">
     					<option value="${formItem[formItemStatus1.index].name}" >${formItem[formItemStatus1.index].title}</option>
     				</c:if>
    			</c:forEach>
        	</select>&nbsp;
        	<select name="groupSort" id = "groupSort" class="SmallSelect">
          		<option value="ASC" >升序</option>
          		<option value="DESC" >降序</option>
        	</select>
      	</td>
    </tr>
    <!-- 显示统计字段 -->
    <tr>
      	<td class="TableContent" valign="top"><br><b>统计字段：</b>如需进行合计请在统计字段中选中按住CTRL键可多选<br><br><br><br></td>
      	<td class="TableData" colspan="2">
        	<table style="border:'0'; width:'350px'"  class="TableBlock">
          		<tr class="TableData">
            		<td align="center" width="150px"><b>显示以下字段作为表格列</b>
            		</td>
            		<td align="center" width="20px">&nbsp;</td>
            		<td align="center" valign="top" width="150px"><b>不显示以下字段</b></td>
          		</tr>
          		<tr class="TableData">
            		<td valign="top" align="center" bgcolor="#CCCCCC">
            			<select  name="sumFldList" id = "sumFldList" style="width:150px; height:150px" ondblclick="func_delete();"  multiple class='form-control' >
			            	<option value="runId" >流水号</option>
				            <option value="runName" >名称/文号</option>
				            <option value="runStatus" >流程状态</option>
				            <option value="runDate" >流程开始日期</option>
				            <option value="runTime" >流程开始时间</option>
				            <c:forEach items="${formItem}" var="formItemSort2" varStatus="formItemStatus2">
     							<c:if test="${formItem[formItemStatus2.index].tag ne 'IMG'}">
     								<option value="${formItem[formItemStatus2.index].name}" >${formItem[formItemStatus2.index].title}</option>
     							</c:if>
    						</c:forEach>
           				</select>
            			<input type="button" onClick="select_all1(1);" value="全选" class="btn btn-success btn-xs">
            			<input type="button" onClick="select_all1(0);" value="取消选择" class="btn btn-warning btn-xs">
            		</td>

            		<td align="center" bgcolor="#999999">
              			<button type="button" class="btn btn-info btn-xs"  onClick="func_insert();"> ← </button>
              			<br><br>
              			<button type="button" class="btn btn-info btn-xs"  onClick="func_delete();"> → </button>
            		</td>

            		<td align="center" valign="top" bgcolor="#CCCCCC">
            			<select  name="standbyFldList" id = "standbyFldList" style="width:150px; height:150px" ondblclick="func_insert();"  multiple class='form-control' >
            			</select>
            			<input type="button" onClick="select_all2();" value="全选" class="btn btn-success btn-xs">
            		</td>
          		</tr>
        	</table>
		</td>
	</tr>
    
    <tr align="center" class="TableFooter">
      	<td colspan="3" nowrap>
	        <input type="hidden" name="op" id="op">
	        <input type="hidden" value="" id= "listView" name="listView">
	        <input type="hidden" value="" id="dispFld" name="dispFld">
	        <input type="hidden" value="" id="sumFld" name="sumFld">
	        <input type="hidden" value="" name="runId" id="runId">
	        <input type="hidden" value="" name="menuFlag" id="menuFlag">
	        <input type="hidden" value="${tplId}" name="tplId" id="tplId">
	        <input type="hidden" value="${flow.sid}" name="flowId" id="flowId">
			<input type="button" value="保存" class="SmallButtonA" name="save" id="save" onClick="saveTpl();">
	        <input type="button" value="返回" class="SmallButtonA" name="back1" id="back1" onClick="back();">
      	</td>
    </tr>
</table>
</form>
<br>
</body>
</html>
