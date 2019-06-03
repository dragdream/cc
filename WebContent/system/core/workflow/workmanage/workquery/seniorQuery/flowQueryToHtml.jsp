<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<%
		String flowId = request.getParameter("flowId") == null ? "" : request.getParameter("flowId");
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
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/bootstrap.min.css">
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery.portlet.css?v=1.1.2" type="text/css" rel="stylesheet" />
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
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
	<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		var flowId = "<%=flowId%>";
		$(function() {
			$("#radio_"+"${tplId}").attr("checked", true);
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
		
		//定义自己的模板
		function newQueryTpl(flowId){
		  	var URL = contextPath + "/seniorQuery/getQueryTplByFlowId.action?flowId="+flowId;
		  	myleft=(screen.availWidth-700)/2;
		  	mytop=(screen.availHeight-450)/2;
			window.open(URL, "", "status=0,toolbar=no,menubar=no,width=700,height=450,location=no,scrollbars=yes,resizable=yes,left="+myleft+",top="+mytop);
		}
		
		//选中一个查询模板
		function select_tpl(tplId){
			window.location = contextPath + "/seniorQuery/toFlowQuery.action?flowId="+flowId+"&tplId="+tplId;
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

		function backTo(){
			$("body").css({overflow:"auto"});
			$("#form1").show();
			$("#datagrid").datagrid("getPanel").panel("close");
		}
		
		function my_submit1(){
			$("#datagrid").show();
			$("#form1").hide();
			
			var datagrid = $('#datagrid').datagrid({
				url : "<%=contextPath%>/seniorQuery/toSeniorQuery.action",
				toolbar : '#toolbar',
				title : '',
				queryParams:tools.formToJson($("#form1")),
				iconCls : 'icon-save',
				pagination : true,
				toolbar:"#toolbar",
				fit:true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
				fitColumns : true,
				nowrap : true,
				border : true,
				idField : 'id',
				striped: true,
				remoteSort: true,
				singleSelect:true,
				columns : [ [ {
					title : 'id',
					field : 'sid',
					hidden:true
				},
				 {
					title : '流水号',
					field : 'RUN_ID',
					width : 30,
					sortable:true,
					formatter:function(value,rowData,rowIndex){
					var render = rowData.runId;
					return render;
				}
				}, {
					field : 'FLOW_NAME',
					title : '所属流程',
					width : 50,
					sortable : true,
					formatter:function(value,rowData,rowIndex){
						var render = rowData.flowName;
						return render;
					}
				},{
					field : 'RUN_NAME',
					title : '工作标题',
					width : 100,
					sortable : true,
					formatter:function(value,rowData,rowIndex){
						var render = rowData.runName;
						return render;
					}
				} ,{
					field : 'BEGIN_TIME',
					title : '开始时间',
					width : 50,
					sortable : true,
					formatter:function(value,rowData,rowIndex){
						var render = rowData.beginTimeDesc;
						return render;
					}
				},{
					field : 'END_TIME',
					title : '状态',
					width : 30,
					sortable : true,
					formatter:function(value,rowData,rowIndex){
						var render = "";
						if(rowData.endTimeDesc!=""){
							render = "<span style=\"color:red\">已结束</span>";
						}else{
							render = "<span style=\"color:green\">执行中</span>";
						}
						return render;
					}
				},{
					field : 'operations',
					title : '操作',
					width : 100,
					formatter:function(value,rowData,rowIndex){
						var render = '';
						var flowviewUrl = contextPath+'/system/core/workflow/flowrun/flowview/index.jsp?runId='+rowData.runId+'&flowId='+rowData.flowId;
						if(rowData.viewGraph){//流程图
							render+='<a href=\''+flowviewUrl+'\' target=\'_blank\'>流程图</a>&nbsp;&nbsp;';
						}
						if(rowData.concern){//关注
							render+='<a href=\'javascript:void(0)\' onclick=\'concern("'+rowData.runId+'")\'>关注</a>&nbsp;&nbsp;';
						}else if(rowData.cancelConcern){
							render+='<a href=\'javascript:void(0)\' onclick=\'cancelConcern("'+rowData.runId+'")\'>取消关注</a>&nbsp;&nbsp;';
						}
						if(rowData.end){//结束
							render+='<a href=\'javascript:void(0)\' onclick=\'endRun("'+rowData.runId+'")\'>结束</a>&nbsp;&nbsp;';
						}else if(rowData.recover){
							render+='<a href=\'javascript:void(0)\' onclick=\'recoverRun("'+rowData.runId+'")\'>恢复执行</a>&nbsp;&nbsp;';
						}
						if(rowData.doDelete){//删除
							render+='<a href=\'javascript:void(0)\' onclick=\'delRun("'+rowData.runId+'")\'>删除</a>&nbsp;&nbsp;';
						}
						return render;
					}
				}
				]]
			});
		}
	</script>
	
</head>
<body topmargin="5">
	<table cellspacing="0" width="100%" style="border-collapse:collapse" border=1 cellspacing=0 cellpadding=3 bordercolor='#000000' class="small">
	    <tr class="TableContent">
	    <tr style="BACKGROUND: #D3E5FA; color: #000000; font-weight: bold;">
	        <td class="TableControl" align="center" nowrap><b>分组：<?=$TITLE_ARRAY[$GROUP_FLD]?>&nbsp;</b></td>
	        <td  nowrap align="center"><b><?=$TITLE?>&nbsp;</b></td>
	    </tr>
	    <tr class="TableData">
	        <td rowspan="<?=$grouping_con?>" class="TableContent">
	        </td>
		    <td rowspan="<?=$MAX_ROWS3[$DATA_ARRAY[$ROW_ARRAY[$J]]["RUN_ID"]]?></td>
	    </tr>
	    <tr class="TableData">
	        <td class="TableControl" align=right><b>小计</b></td>
		    <td class="TableControl" align=right></td>
		    <td class="TableControl" align=right>&nbsp;</td>
	    </tr>
	    <tr class="TableControl">
	   	    <td align="center"><b>合计</b></td>
	    </tr>
	</table>
</body>
</html>
