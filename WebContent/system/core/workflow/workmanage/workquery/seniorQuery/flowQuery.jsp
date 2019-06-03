<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
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

		
		//判断是否是自定义菜单进来的
		int isCustom=TeeStringUtil.getInteger(request.getParameter("isCustom"),0);
	%>
	<title>高级查询</title>
	<script type="text/javascript"
	src="<%=request.getContextPath() %>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	    var isCustom=<%=isCustom %>;
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
			
			$("select[xtype=xselect]").each(function(i,obj){
				$(obj).find(":first").before("<option value='' selected></option>");
				$(obj).val("");
			});
			
			
			//渲染模板
			renderTemplate();
		});
		
		function seniorQuery(){
			if(isCustom==1){//自定义菜单跳过来的
				window.location = contextPath + "/system/core/workflow/workmanage/workquery/index.jsp?flowId="+flowId;
			}else{
				//跳转页面，springMVC 返回对象
				window.location = contextPath + "/seniorQuery/flowList.action";
			}
			
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
					       $.MsgBox.Alert_auto("分组字段必须统计！");
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
			$("#topbar").show();
			$("#datagrid").datagrid("getPanel").panel("close");
			
		}
		
		function my_submit1(){
			$("#datagrid").show();
			$("#topbar").hide();
			$("#form1").hide();

			
			
			var para =  tools.formToJson($("#form")) ;
			var opts = [{title:'流水号',
						field:'RUN_ID',
						ext:'@流水号',
						sortable:true,
						width:60,
						formatter:function(a,data,c){
							return data.runId;
						}
					},
					{field:'RUN_NAME',
						title:'工作名称',
						ext:'@工作名称',
						width:300,
						sortable:true,
						formatter:function(a,data,c){
							var render = "";
							if(data.level==1 || data.level==0){
								render = "<span style='color:green'>【普通】</span>";
							}else if(data.level==2){
								render = "<span style='color:orange'>【紧急】</span>";
							}else if(data.level==3){
								render = "<span style='color:red'>【加急】</span>";
							}
							//render = render+"<a href='javascript:void(0)' onclick='detailRun("+data.runId+","+data.frpSid+")' style='"+(data.timeoutFlag=="1"?"color:red":"")+"'>"+(data.timeoutFlag=="1"?"<img src='timeout_icon.png' title='超时'/>&nbsp;&nbsp;":"")+data.runName+"</a>";
							render = render+"<a href='javascript:void(0)' onclick=\"detail('"+data.runId+"')\" >"+data.runName+"</a>";
							if(data.backFlag==1){
								render += "&nbsp;&nbsp;<img src='/common/images/workflow/goback.png' title='退回步骤'/>";
							}
							return render;
						}
					},
					{field:'fr.END_TIME',
						title:'状态',
						ext:'@状态',
						width:60,
						sortable:true,
						formatter:function(a,data,c){
							if(data.endFlag==0){
								return "<span style='color:green'>进行中</span>";
							}
							return "<span style='color:red'>已结束</span>";
						}
					},{field:'_manage',
						title:'操作',
						ext:'@操作',
						width:200,
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
					}];
			
			var columns = [{
				field : 'runId',
				checkbox : true
			}];

			var flowId = <%=flowId%>
			//动态拼写列结构
			if(flowId!="0" && flowId!=""){
				var json = tools.requestJsonRs(contextPath+"/flowType/get.action",{flowTypeId:flowId});
				var queryFieldModel = json.rtData.queryFieldModel;
				queryFieldModel = queryFieldModel.substring(1,queryFieldModel.length-1);
				var json = tools.requestJsonRs(contextPath+"/flowForm/getFormItemsByFlowType.action",{flowId:flowId});
				var list = json.rtData;
				var sp = queryFieldModel.split(",");
				for(var i=0;i<sp.length;i++){
					if(sp[i].indexOf("@")!=-1){
						for(var j=0;j<opts.length;j++){
							if(opts[j].ext==sp[i]){
								columns.push(opts[j]);
								break;
							}
						}
					}else{
						for(var j=0;j<list.length;j++){
							if(list[j].title==sp[i]){
								columns.push({
									field:list[j].name,
									title:list[j].title,
									sortable:true,
									formatter:function(value,rowData,rowIndex){
										if(value.indexOf("[")!=-1){
											return "<a class='modal-menu-test' href='javascript:void(0)' onclick='$(this).modal(); showDetail("+value+")'>明细查看</a>";
										}else{
											return value;
										}
									}
								});
								break;
							}
						}
					}
				}
			}else{
				for(var j=0;j<opts.length;j++){
					columns.push(opts[j]);
				}
			}

			
			datagrid = $('#datagrid').datagrid({
				url : "<%=contextPath%>/seniorQuery/toSeniorQuery.action",
				view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
				toolbar : '#toolbar',
				queryParams:tools.formToJson($("#form1")),
				pagination : true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
				fit : true,
				fitColumns : false,
				nowrap : true,
				border : false,
				striped: true,
				remoteSort: true,
				singleSelect:false,
				columns:[columns],
				pagination:true,
				onLoadSuccess:function(){
					$(".attach").each(function(i,obj){
						var att = {priv:1+2,fileName:obj.getAttribute("fileName"),ext:obj.getAttribute("ext"),sid:obj.getAttribute("sid")};
						var attach = tools.getAttachElement(att,{});
						$(obj).append(attach);
					});
				}
			});
			
			$(".datagrid-header-row td div span").each(function(i,th){
				var val = $(th).text();
				 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
			});
			
			
			
			
// 			var datagrid = $('#datagrid').datagrid({
// 				view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
// 				toolbar : '#toolbar',
// 				title : '',
// 				queryParams:tools.formToJson($("#form1")),
// 				iconCls : 'icon-save',
// 				pagination : true,
// 				toolbar:"#toolbar",
// 				fit:true,
// 				pageSize : 10,
// 				/* pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ], */
// 				fitColumns : true,
// 				nowrap : true,
// 				border : false,
// 				idField : 'id',
// 				striped: true,
// 				remoteSort: true,
// 				singleSelect:true,
// 				columns : [ [ {
// 					title : 'id',
// 					field : 'sid',
// 					hidden:true
// 				},
// 				 {
// 					title : '流水号',
// 					field : 'RUN_ID',
// 					width : 30,
// 					sortable:true,
// 					formatter:function(value,rowData,rowIndex){
// 					var render = rowData.runId;
// 					return render;
// 				}
// 				}, {
// 					field : 'FLOW_NAME',
// 					title : '所属流程',
// 					width : 50,
// 					sortable : true,
// 					formatter:function(value,rowData,rowIndex){
// 						var render = rowData.flowName;
// 						return render;
// 					}
// 				},{
// 					field : 'RUN_NAME',
// 					title : '工作标题',
// 					width : 100,
// 					sortable : true,
// 					formatter:function(value,rowData,rowIndex){
// 						var render = rowData.runName;
// 						return "<a href='javascript:void(0)' onclick='detail("+rowData.runId+")'>"+render+"</a>";
// 					}
// 				} ,{
// 					field : 'BEGIN_TIME',
// 					title : '开始时间',
// 					width : 50,
// 					sortable : true,
// 					formatter:function(value,rowData,rowIndex){
// 						var render = rowData.beginTimeDesc;
// 						return render;
// 					}
// 				},{
// 					field : 'END_TIME',
// 					title : '状态',
// 					width : 30,
// 					sortable : true,
// 					formatter:function(value,rowData,rowIndex){
// 						var render = "";
// 						if(rowData.endTimeDesc!=""){
// 							render = "<span style=\"color:red\">已结束</span>";
// 						}else{
// 							render = "<span style=\"color:green\">执行中</span>";
// 						}
// 						return render;
// 					}
// 				},{
// 					field : 'operations',
// 					title : '操作',
// 					width : 100,
// 					formatter:function(value,rowData,rowIndex){
						
// 					}
// 				}
// 				]]
// 			});
		}
		
		
		
		function my_submit2(flag){
			if(flag==1){
				//跳转页面，springMVC 返回对象
				var url = contextPath + "/seniorQuery/flowQueryToHtml.action?"+tools.formToJson($("#form1"));
				window.open(url);
			}
			
		}
		function hide_item(obj,radio){

		}
		
		function detail(runId){
			openFullWindow(contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+runId+"&view=15");
		}
		
		
		//清空日期
		function emptyDate(type){
			if(type=="0"){
				$("#prcsDate1").val("");
				$("#prcsDate2").val("");
			}
			if(type=="1"){
				$("#endDate1").val("");
				$("#endDate2").val("");
			}
		}
		
		function set_user(obj){
			if($(obj).val()==6){
				$("#set_user_tr").show();
			}else{
				$("#set_user_tr").hide();
			}
		}
		
		function concern(runId){
			$.MsgBox.Confirm ("提示", "确认要关注此工作吗？", function(){
				var url = contextPath+"/flowRunConcern/concern.action";
				var json = tools.requestJsonRs(url,{runId:runId});
				$.MsgBox.Alert_auto("关注成功！");
				$('#datagrid').datagrid('reload');
			  });
		}

		function cancelConcern(runId){
			$.MsgBox.Confirm ("提示", "确认要取消关注此工作吗？", function(){
				var url = contextPath+"/flowRunConcern/cancelConcern.action";
				var json = tools.requestJsonRs(url,{runId:runId});
				$.MsgBox.Alert_auto("已取消关注！");
				$('#datagrid').datagrid('reload');
			  });
		}

		function endRun(runId){
			$.MsgBox.Confirm ("提示", "确认要结束此工作吗？", function(){
				var url = contextPath+"/workflowManage/endRun.action";
				var json = tools.requestJsonRs(url,{runId:runId});
				$.MsgBox.Alert_auto("已结束！");
				$('#datagrid').datagrid('reload');
			  });
		}

		function recoverRun(runId){
			$.MsgBox.Confirm ("提示", "确认要恢复此工作吗？", function(){
				var url = contextPath+"/workflowManage/recoverRun.action";
				var json = tools.requestJsonRs(url,{runId:runId});
				$.MsgBox.Alert_auto("已恢复！");
				$('#datagrid').datagrid('reload');
			  });
		}

		function delRun(runId){
			$.MsgBox.Confirm ("提示", "确认要删除此工作吗？", function(){
				var url = contextPath+"/workflowManage/delRun.action";
				var json = tools.requestJsonRs(url,{runId:runId});
				$.MsgBox.Alert_auto("删除成功！");
				$('#datagrid').datagrid('reload');
			  });
		}
		function generate_report(){
			window.reportParams = tools.formToJson($("#form1"));//请求参数
			window.group = $("#sort").val().split(".")[1];//分组字段
			window.groupDesc = $("#sort option:selected").html();//分组字段
			window.showField = [];//显示列
			window.sumField = [];//合计列
			window.showFieldDesc = [];//显示列
			window.sumFieldDesc = [];//显示列
			$("#statistics_show option").each(function(i,obj){
				window.showField.push(obj.value.split(".")[1]);
				window.showFieldDesc.push(obj.innerHTML);
			});
			$("#statistics_show option:selected").each(function(i,obj){
				window.sumField.push(obj.value.split(".")[1]);
				window.sumFieldDesc.push(obj.innerHTML);
			});
			var showFields = [];
			for(var i=0;i<window.showField.length;i++){
				if(window.showField[i].indexOf("DATA_")!=-1){
					showFields.push(window.showField[i]);
				}
			}
			window.reportParams["showFields"] = showFields.join(",");
			
			openFullWindow(contextPath+"/system/core/workflow/workmanage/workquery/seniorQuery/report.jsp");
		}
		
		
		//渲染模板
		function renderTemplate(){
			//先清空	$("#orderField").find("option").not(":first").remove();
			$("#templateId").find("option").not(":first").remove();
			var url=contextPath+"/flowSeniorQueryTemplateController/renderTempalte.action";
			var json=tools.requestJsonRs(url,{flowId:flowId});
			if(json.rtState){
				var data=json.rtData;
				if(data!=null&&data.length>0){
					for(var i=0;i<data.length;i++){
						$("#templateId").append("<option value="+data[i].sid+">"+data[i].templateName+"</option>");
					}	
				}
			}
		}
		
		
		
		//保存模板
		function saveTemplate(){
			//获取当前选择的模板id
			var templateId=$("#templateId").val();
			var basicInfo=getBasicInfo();
			var formInfo=getFormInfo();
			var statisticInfo=getStatisticInfo();
			
			var param={};
			param["flowId"]=flowId;
			param["templateId"]=templateId;
			param["formInfo"]=formInfo;
			param["basicInfo"]=basicInfo;
			param["statisticInfo"]=statisticInfo;
			if(templateId>0){//更新模板信息
				var url=contextPath+"/flowSeniorQueryTemplateController/addOrUpdate.action";
				var json=tools.requestJsonRs(url,param);
				if(json.rtState){
					$.MsgBox.Alert_auto("更新模板成功！",function(){
					  
					});				
				}	
			}else{//新建模板信息
				var url=contextPath+"/system/core/workflow/workmanage/workquery/seniorQuery/createTemplate.jsp?flowId="+flowId;
				bsWindow(url ,"新建模板信息",{width:"400",height:"100",buttons:
					[
                     {name:"保存",classStyle:"btn-alert-blue"},
				 	 {name:"关闭",classStyle:"btn-alert-gray"}
					 ]
					,submit:function(v,h){
					var cw = h[0].contentWindow;
					if(v=="保存"){
					    var json=cw.doSave();
					    if(json.rtState){
					    	$.MsgBox.Alert_auto("模板保存成功！");
					    	renderTemplate();
					    	return true;
					    }else{
					    	$.MsgBox.Alert_auto("模板保存失败！");
					    	return false;
					    }
					}else if(v=="关闭"){
						return true;
					}
				}}); 
			}
		}
		
		
		
		
		//获取基本信息
		function getBasicInfo(){
			var basicInfo={};
			//获取工作流状态
			var flowStatus=$("#flowStatus").val();
			//获取查询范围
			var flowQueryType=$("#flowQueryType").val();
			//获取流程发起人id name
			var beginUserId=$("#beginUserId").val();
			var beginUserName=$("#beginUserName").val();
			
			//获取runName相关
			var runNameRelation=$("#runNameRelation").val();
			var runName=$("#runName").val();
			
			//日期
			var prcsDate1=$("#prcsDate1").val();
			var prcsDate2=$("#prcsDate2").val();
			var dateRange=$("#dateRange").val();
			
			
			basicInfo["flowStatus"]=flowStatus;
			basicInfo["flowQueryType"]=flowQueryType;
			basicInfo["beginUserId"]=beginUserId;
			basicInfo["beginUserName"]=beginUserName;
			basicInfo["runNameRelation"]=runNameRelation;
			basicInfo["runName"]=runName;
			basicInfo["prcsDate1"]=prcsDate1;
			basicInfo["prcsDate2"]=prcsDate2;
			basicInfo["dateRange"]=dateRange;
			return tools.jsonObj2String(basicInfo);
		}
		
		
		//获取表单数据
		function getFormInfo(){
			var formInfo={};
			//获取表单数据行
			var itemTrs=$("#queryItem tr");
			for(var i=0;i<itemTrs.length;i++){
				if(i!=(itemTrs.length-1)){//不是最后一行
					var firstSelectId=$(itemTrs[i]).find("select:eq(0)").attr("id");
					var firstSelectValue=$("#"+firstSelectId).val();
					
					var secondId="DATA_"+firstSelectId.substr(9,firstSelectId.length-1);
					var secondValue=$("#"+secondId).val();
					
					formInfo[firstSelectId]=firstSelectValue;
					formInfo[secondId]=secondValue;
				}else{//最后一行
					//获取条件公式
					var condFormula=$("#condFormula").val();
					formInfo["condFormula"]=condFormula;		
				}
			}
			
			return tools.jsonObj2String(formInfo);
		}
		
		
		//获取统计数据
		function getStatisticInfo(){
			var statisticInfo={};
			var opt=[];
			//获取统计字段
			var sort=$("#sort").val();
			var order=$("#order").val();
			//获取被选中的
			var options=$("#statistics_show option");
			for(var i=0;i<options.length;i++){
				opt.push({value:$(options[i]).val(),name:$(options[i]).text()});
			}
			statisticInfo["sort"]=sort;
			statisticInfo["order"]=order;
			statisticInfo["showField"]=opt;
			
			return tools.jsonObj2String(statisticInfo);
		}
		
		
		//改变模板
		function changeTemplate(){
			//显示列  把左边的挪到右边
			$("#statistics_show option").appendTo($("#statistics_hide"));
			
			//获取当前的模板
			var templateId=$("#templateId").val();
			if(templateId==0){
				$("#form1")[0].reset();
			}else{
				var url=contextPath+"/flowSeniorQueryTemplateController/getInfoBySid.action";
				var json=tools.requestJsonRs(url,{templateId:templateId});
				if(json.rtState){
					var data=json.rtData;
					var basicInfo=tools.strToJson(data.basicInfo);
					var formInfo=tools.strToJson(data.formInfo);
					var statisticInfo=tools.strToJson(data.statisticInfo);
					
					//默认显示条件
					document.getElementById("queryItem").style.display="";
					
					bindJsonObj2Cntrl(basicInfo);
					bindJsonObj2Cntrl(formInfo);
					$("#sort").val(statisticInfo.sort);
					$("#order").val(statisticInfo.order);
					
					var showField=statisticInfo.showField;
					//渲染统计字段中的报表显示列
					if(showField.length>0){
						for(var i=0;i<showField.length;i++){
							//渲染显示列
							$("#statistics_show").append("<option value="+showField[i].value+">"+showField[i].name+"</option>");
							//并且删除字段备选中的相关
							$("#statistics_hide").find("option[value='"+showField[i].value+"']").remove();
						}
						
					}
					
					
				}
			}
			
			
			
		}
	</script>
	
</head>
<body style="overflow:auto;padding-left: 10px;padding-right: 10px">
  <div id="topbar" class="topbar clearfix" >
      <div class="fl left">
           <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzcx/icon_gzltjcx.png">
		   <span class="title">工作流高级查询 </span>（请指定查询条件 ）
      </div>
      <div class="fr right">
           <!-- <input type="button" style="display: none;" id="returnSearch" class="btn-win-white" value="返回查询" onclick="backTo()" /> -->
           <input type="button"  value="返回" class="btn-win-white" onClick="seniorQuery();"/>
      </div>
  </div>
  
  <div class="">
	<form method="post" id="form1" name="form1" role="form" class="form-inline">  
		
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		</div>
		<table   class="TableBlock_page">
	    <tr style="">
	      	<td nowrap class="TableHeader" height=25 colspan="3" >
	      	     <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		         <B style="color: #0050aa">查询模板</B>
		    </td>
	    </tr>
	     <tr>
	      	<td nowrap class="TableContent" style="width:33%;text-indent: 15px" ><h6><label for="templateId">选择查询模板：</label></h6></td>
	      	<td class="TableData" colspan="2">
	        	<select id = "templateId" name="templateId" class="form-control" style="width:200px;height:23px" onChange="changeTemplate();">
	          		  <option value="0">请选择</option>
	        	</select>
	        	
	        	<input type="button" class="btn-win-white" value="保存模板" onclick="saveTemplate()"/>
	      	</td>
	      	
	    </tr>
	   	
	     <tr style="">
	      	<td nowrap class="TableHeader" height=25 colspan="3" >
	      	     <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		         <B style="color: #0050aa">工作流程基本属性</B>
		    </td>
	    </tr>
	    <tr>
	      	<td nowrap class="TableContent" style="width:33%;text-indent: 15px" ><h6><label for="flowStatus">工作流状态：</label></h6></td>
	      	<td class="TableData" colspan="2">
	        	<select id = "flowStatus" name="flowStatus" class="form-control" style="width:200px;height:23px" onChange="selStatus(this.value)">
	          		<option value="0" >所有</option>
	          		<option value="1" >执行中</option>
	          		<option value="2" >已结束</option>
	        	</select>
	      	</td>
	      	
	    </tr>
	    <tr>
	      	<td nowrap class="TableContent" style="text-indent: 15px"><h6><label for="flowQueryType">查询范围：</label></h6></td>
	      	<td class="TableData" colspan="2">
	        	<select id= "flowQueryType" name="flowQueryType" class="form-control" onChange="set_user(this);" style="width:200px;height:23px">
	          		<option value="0" >所有</option>
	          		<option value="1" >我发起的</option>
	          		<option value="2" >我经办的</option>
	          		<option value="3" >我管理的</option>
	          		<option value="4" >我关注的</option>
	          		<option value="5" >我查阅的</option>
	          		<option value="6" >指定发起人</option>
	        	</select>
	      	</td>
	    </tr>
	    <tr id="set_user_tr" style="display:none">
			<td nowrap class="TableContent" style="text-indent: 15px"><h6><label for="beginUserName">流程发起人：</label></h6></td>
			<td nowrap class="TableData" colspan="2">
				<input type="hidden" name="beginUserId" id="beginUserId" required="true"  value=""> 
				<input  name="beginUserName" id="beginUserName" style="width:198px;height: 20px" wrap="yes" readonly />
				<span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectSingleUser(['beginUserId', 'beginUserName'])" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="clearData('beginUserId', 'beginUserName')" value="清空"/>
				</span>
			</td>
	    </tr>
	    
	     <tr>
	      	<td nowrap class="TableContent" style="text-indent: 15px"><p class="text-primary"><h6><label for="runNameRelation">名称/文号：</label></h6>
	      	<td nowrap class="TableData" colspan=2>
	           	<select name="runNameRelation" id="runNameRelation" class="form-control" style="width:200px;height:23px">
	           	   	<option value="1" >等于</option>
			        <option value="6" >不等于</option>
			        <option value="7" >开始为</option>
			        <option value="8" >包含</option>
			        <option value="9" >结束为</option>
	       		</select>
	        <span><input type="text" name="runName" id="runName" value=""  style="width:300px;" class="form-control"></span>
	      </td>
	    </tr>
	    <tr>
	      	<td nowrap class="TableContent" style="text-indent: 15px"><h6><label for="prcsDate1">流程开始日期范围：</label></h6></td>
	      	<td class="TableData" colspan="2">
	      		<p><input type="text" name="prcsDate1" id="prcsDate1" size="10" class="Wdate form-control" style="width:120px;height: 20px" value="" readonly onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'prcsDate2\')}'})">
	      		&nbsp;至&nbsp;<input type="text" name="prcsDate2" id="prcsDate2" size="10" class="Wdate form-control" style="width:120px;height: 20px" value="" readonly onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'prcsDate1\')}'})">
	        	<a href="javascript:emptyDate('0');" class="orgClear" onClick="emptyDate('0');">清空</a>&nbsp;
	        	<select name="dateRange" id = "dateRange" class="form-control" onChange="dateChange(this.value,'1')" style="width:200px;height:23px">
	        		<option value="0">快捷选择</option>
	        		<option value="1">今日</option>
	        		<option value="2">昨日</option>
	        		<option value="3">本周</option>
	        		<option value="4">上周</option>
	        		<option value="5">本月</option>
	        		<option value="6">上月</option>
	        	</select>
	        	</p>
	      	</td>
	    </tr>
	    <tr style="display:none" id="endTimeArea">
	      	<td nowrap class="TableContent" style="text-indent: 15px"><h6><label for="prcsDate1">流程结束日期范围：</label></h6></td>
	      	<td class="TableData" colspan="2">
	        	<p>从<input type="text" name="endDate1" id="endDate1" size="10" class="SmallInput" value="" onClick="WdatePicker()">
	       		 至<input type="text" name="endDate2" id="endDate2" size="10" class="SmallInput" value="" onClick="WdatePicker()">
	        	<a href="javascript:emptyDate('1');" class="orgClear" onClick="emptyDate('1');">清空</a>&nbsp;
	        	<select name="dateRange1" id="dateRange1" class="form-control" onChange="dateChange(this.value,'2')" >
		        	<option value="0">快捷选择</option>
		        	<option value="1">今日</option>
		        	<option value="2">昨日</option>
		        	<option value="3">本周</option>
		        	<option value="4">上周</option>
		        	<option value="5">本月</option>
		        	<option value="6">上月</option>
	        	</select>
	        	</p>
	      	</td>
	    </tr>

	   <tr style="">
	      	<td nowrap class="TableHeader" height=25 colspan="3" >
	      	
	      	    <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		         <B style="color: #0050aa">表单数据条件</B>&nbsp;&nbsp;<a href="javascript:dataShowHide()">显示/隐藏条件</a>
	      	</td>
	   </tr>
	   <tbody id="queryItem" style="display:none">
	     	<c:forEach items="${formItem}" var="formItemSort" varStatus="formItemStatus">
	     		<c:if test="${formItem[formItemStatus.index].tag ne 'IMG'}">
		     		<tr >
		     			<td nowrap class="TableData" style="text-indent: 15px"><label for="relation_${formItem[formItemStatus.index].itemId}">[${formItem[formItemStatus.index].itemId}] ${formItem[formItemStatus.index].title}：</label></td>
		     			<td nowrap class="TableData" colspan=2>
		     				<select style="width:200px;height:23px;" id="relation_${formItem[formItemStatus.index].itemId}" name="relation_${formItem[formItemStatus.index].itemId}" class="form-control" onchange="hide_item(this,'${formItem[formItemStatus.index].xtype}');">
		     					<option value="1">等于</option>
		     					<c:choose>  
									<c:when test="${formItem[formItemStatus.index].tag ne 'SELECT' && formItem[formItemStatus.index].xtype ne 'xradio'}"> 
								        <option value="6">不等于</option>
								        <option value="7">开始为</option>
								        <option value="8" selected>包含</option>
								        <option value="9">结束为</option>
								        <option value="10">为空</option>
									</c:when> 
									<c:otherwise> 
										<option value="10" selected>为空</option>
									</c:otherwise> 
								</c:choose>
		     				</select>&nbsp;
		     				<c:if test="${formItem[formItemStatus.index].tag eq 'INPUT'}">
								<c:if test="${formItem[formItemStatus.index].xtype eq 'xcheckbox'}">
								 	<select style="width:300px;height: 23px" id="${formItem[formItemStatus.index].name}" name="${formItem[formItemStatus.index].name}"  class="form-control">
								 		<option value='SELECT_ALL_VALUE'>所有</option>
								 		<option value='CHECKBOX_ON'>是</option>
								 		<option value='CHECKBOX_OFF'>否</option>
							 		</select>
								</c:if>
								<c:if test="${formItem[formItemStatus.index].xtype eq 'xradio'}">
									<c:forEach items="${formItem[formItemStatus.index].radioList}" var="radioItemSort" varStatus="radioItemStatus">
										<c:choose>  
											<c:when test="${type eq '新建查询模板'}"> 
												<input type="radio" style="width: 300px;height: 20px"  name="${formItem[formItemStatus.index].name}" class="form-control" value="${formItem[formItemStatus.index].radioList[radioItemStatus.index].data}" onchange="changeRadio('${formItem[formItemStatus.index].name}','${formItem[formItemStatus.index].radioList[radioItemStatus.index].data}');" ${formItem[formItemStatus.index].radioList[radioItemStatus.index].checked}>${formItem[formItemStatus.index].radioList[radioItemStatus.index].data}
											</c:when>
											<c:otherwise> 
												<input type="radio" style="width: 300px;height: 20px" name="${formItem[formItemStatus.index].name}" class="form-control" value="${formItem[formItemStatus.index].radioList[radioItemStatus.index].data}" onchange="changeRadio('${formItem[formItemStatus.index].name}','${formItem[formItemStatus.index].radioList[radioItemStatus.index].data}');" >${formItem[formItemStatus.index].radioList[radioItemStatus.index].data}
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<input type="hidden" id = "${formItem[formItemStatus.index].name}" name="${formItem[formItemStatus.index].name}" class="form-control" value="${formItem[formItemStatus.index].radioList[0].data}">
								</c:if>
								<c:if test="${formItem[formItemStatus.index].xtype ne 'xcheckbox' && formItem[formItemStatus.index].xtype ne 'xradio'}"> 
									<input style="width: 300px;height: 20px" id="${formItem[formItemStatus.index].name}" name="${formItem[formItemStatus.index].name}" type="text" size="30" class="form-control" />
								</c:if>
							</c:if>
							<c:if test="${formItem[formItemStatus.index].tag eq 'SELECT'}">
								${formItem[formItemStatus.index].content}
							</c:if>
							<c:if test="${formItem[formItemStatus.index].tag eq 'TEXTAREA'}">
								<input style="width: 300px;height: 20px" id="${formItem[formItemStatus.index].name}" name="${formItem[formItemStatus.index].name}" type="text" size="30" class="form-control" />
							</c:if>
		     			</td>
		     		</tr>
	     		</c:if>
	   		</c:forEach>
	   		<c:if test="${fn:length(formItem) > 0}">
			   	<tr >
			      	<td nowrap class="TableData" style="text-indent: 15px"><h6><label for="condFormula">表单字段条件公式：</label></h6></td>
			      	<td nowrap class="TableData" colspan="2">
			        	<p><input type="text" style="width:200px;height: 20px;" id= "condFormula" name="condFormula" value="" size="35" class="form-control" onBlur="check_condition_formula(this);">&nbsp;形如：([1] or [2]) and [3]</p>
			      	</td>
			    </tr>
		    </c:if>
	   </tbody>
	
	    
	    <tr align="center" class="TableFooter" style="display: none" >
	      	<td colspan="3" nowrap style="text-algin:center">
		        <input type="hidden" name="op" id="op">
		        <input type="hidden" value="" id= "listView" name="listView">
		        <input type="hidden" value="" id="dispFld" name="dispFld">
		        <input type="hidden" value="" id="sumFld" name="sumFld">
		        <input type="hidden" value="" name="runId" id="runId">
		        <input type="hidden" value="" name="menuFlag" id="menuFlag">
		        <input type="hidden" value="${tplId}" name="sid" id="sid">
		        <input type="hidden" value="${flow.sid}" name="flowId" id="flowId">
	      	</td>
	    </tr>
	    <tr>
	    	<td colspan="3" style="text-align:center">
	    		<input  type="button" value="查询" class="btn-win-white" onClick="my_submit1()"/>
	    	</td>
	    </tr>
	    
	    <tr style="">
      	<td nowrap class="TableHeader" height=25 colspan="3" >
      	
      	    <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
	         <B style="color: #0050aa">统计相关</B>
      	</td>
   </tr>
   <tr>
      	<td nowrap class="TableContent" style="width:33%;text-indent: 15px" ><h6><label for="flowStatus">分组字段：</label></h6></td>
      	<td class="TableData" colspan="2">
        	<select id = "sort" name="sort" class="form-control" style="width:200px;height:23px" >
          		<option value="fr.RUN_ID">流水号</option>
          		<option value="fr.RUN_NAME">流程名称/标题</option>
          		<option value="fr.END_FLAG">流程状态</option>
          		<option value="fr.BEGIN_TIME">流程开始日期</option>
          		<option value="fr.END_TIME">流程结束日期</option>
          		<c:forEach items="${formItem}" var="formItemSort" varStatus="formItemStatus">
					<option value="frd.${formItem[formItemStatus.index].name}" >[${formItem[formItemStatus.index].title}]</option>
	   			</c:forEach>
        	</select>
        	
        	<select id = "order" name="order" class="form-control" style="width:100px;height:23px" >
          		<option value="ASC">升序</option>
          		<option value="DESC">降序</option>
        	</select>
      	</td>
    </tr>
    <tr>
      	<td  class="TableContent" style="width:33%;text-indent: 15px" ><h6><label for="flowStatus">统计字段：</label></h6>
      	</td>
      	<td class="TableData" colspan="2">
        	<table>
        		<tr>
        			<td><b>报表显示列</b></td>
        			<td width="50px"></td>
        			<td><b>字段备选</b></td>
        		</tr>
        		<tr>
        			<td>
        				<select id = "statistics_show" class="form-control" style="width:150px;height:200px" multiple>
			          		
			        	</select>
        			</td>
        			<td style="text-align:center">
        				<p><button type="button" class="btn-win-white" onclick="$('#statistics_show option:selected').appendTo($('#statistics_hide'));">&gt;</button></p>
        				<br/>
        				<p><button type="button" class="btn-win-white" onclick="$('#statistics_hide option:selected').appendTo($('#statistics_show'));">&lt;</button></p>
        			</td>
        			<td>
        				<select id = "statistics_hide" class="form-control" style="width:150px;height:200px" multiple>
			          		<option value="fr.RUN_ID">流水号</option>
			          		<option value="fr.RUN_NAME">流程名称/标题</option>
			          		<option value="fr.END_FLAG">流程状态</option>
			          		<option value="fr.BEGIN_TIME">流程开始日期</option>
			          		<option value="fr.END_TIME">流程结束日期</option>
			          		<c:forEach items="${formItem}" var="formItemSort" varStatus="formItemStatus">
								<option value="frd.${formItem[formItemStatus.index].name}" >[${formItem[formItemStatus.index].title}]</option>
				   			</c:forEach>
			        	</select>
        			</td>
        		</tr>
        	</table>
        	<h6>如需进行合计，请在“列表显示列”中选中，按住CTRL键可多选</h6>
      	</td>
    </tr>
	</table>
    
	<div style="text-align:center;padding:10px 0" >
		<input  type="button" value="生成统计报表" class="btn-win-white" onClick="generate_report()"/>
	</div>
	</form>  
	
</div> 
   <table id="datagrid" fit="true" ></table>
   <div id="toolbar" class="topbar clearfix" style="display:none;padding:10px">
         <div class="fl left">
           <span class="title">结果列表</span>
        </div>
        <div class="fr right">
           <input type="button" class="btn-win-white" value="返回查询" onclick="backTo()" />
        </div>
		
	</div> 
</body>
</html>
