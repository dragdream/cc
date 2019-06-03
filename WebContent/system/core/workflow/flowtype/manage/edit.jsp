<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.core.org.service.TeePersonService" %>
<%@ page import="com.tianee.oa.core.partthree.util.TeePartThreeUtil" %>
<%
	int flowTypeId = TeeStringUtil.getInteger(request.getParameter("flowTypeId"),0);

    //获取当前登录的用户名
	TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	String userId=loginUser.getUserId();
	//判断当前的用户是不是系统管理员
	boolean isAdmin=TeePersonService.checkIsSuperAdmin(loginUser, userId);
	
	//三员规则权限
	boolean hasPriv=TeePartThreeUtil.checkHasPriv(loginUser, "WORKFLOW_FLOW_TYPE");
	//流程发起权限
	boolean saferPriv=TeePartThreeUtil.checkHasPriv(loginUser, "WORKFLOW_USER_PRIV");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<%@ include file="/header/ztree.jsp" %>
	<%@ include file="/header/upload.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>Tenee办公自动化智能管理平台</title>
	<script type="text/javascript" src="<%=contextPath %>/common/services/TeeFlowTypeService.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/services/TeeFlowSortService.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/services/TeeFormSortService.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/js/layout/layout.js"></script>
	<script>
	    var hasPriv=<%=hasPriv %>;
		var contextPath = "<%=contextPath%>";
		var flowTypeId = <%=flowTypeId%>;
		var isAdmin=<%=isAdmin%>;
		var sortId;
		function doInit(){
			//初始化的时候先将选择和清空超链接隐藏
			$("#selDept").hide();
			$("#clearDept").hide();
			
			//判断是否系统管理员  如果是则显示选择和清空的超链接
			if(isAdmin){
				$("#selDept").show();
				$("#clearDept").show();
			}
			
			$("#layout").layout({auto:true});
			initExtraData();
			if($("#type")[0].value=="1"){//固定
				$("#prePrcsDiv").hide();
			}else{//自由
				$("#prePrcsDiv").show();
			}
			$("[title]").tooltips();
		}

		//初始化控件数据信息
		function initExtraData(){
			var flowTypeService = new TeeFlowTypeService();
			var flowType = flowTypeService.get(flowTypeId);
			if(!flowType){
				alert("该流程信息可能已经删除");
				window.location.href="new.jsp";
				return;
			}else{
				if(flowType.type==2){//如果是自由流程
// 					$("#prcsPrivBtn").show();
				}else{
					$("#flowDesignBtn").show();
					$("#exportBtn").show();
					$("#importBtn").show();
				}
			}
			

			//初始化流程分类控件
			var flowSortService = new TeeFlowSortService();
			var flowSortList = flowSortService.getList();
			if(flowSortList){
				var html = "<option value='0'>默认分类</option>";
				for(var i=0;i<flowSortList.length;i++){
					html+="<option value='"+flowSortList[i].sid+"'>"+flowSortList[i].sortName+"</option>";
				}
				$("#flowSortId").html(html);
			}

			bindJsonObj2Cntrl(flowType);
			//判断是否开启自动归档
			if(flowType.autoArchive==1){//开启
				$("#archiveItem").show();
			}else{//关闭
				$("#archiveItem").hide();
			}
			
			var autoArchiveValue=flowType.autoArchiveValue;
			if((autoArchiveValue&1)==1){
				$("#bd").attr("checked","checked");
			}
			if((autoArchiveValue&2)==2){
				$("#qpd").attr("checked","checked");
			}
			if((autoArchiveValue&4)==4){
				$("#zw").attr("checked","checked");
			}
			if((autoArchiveValue&8)==8){
				$("#bszw").attr("checked","checked");
			}
			if((autoArchiveValue&16)==16){
				$("#ggfj").attr("checked","checked");
			}
			
			
			//初始化表单控件数据
			ZTreeTool.comboCtrl($("#formId"),{url:contextPath+"/formSort/getSelectCtrlDataSource.action"});
			sortId = flowType.flowSortId;

			if(flowType.totalWorkNum!=0){
				$("#formId").attr("readonly","readonly");
				var option = $("#type option:selected")[0].outerHTML;
				$("#type").html(option);
			}
			$("#formId").attr("value",flowType.formId);
			
			var buttonStr = "<input  type='button' class='btn btn-one' value='菜单定义指南' onclick='addMenuFunc(\"" + sortId + "\",\"" + flowType.flowName + "\")' />&nbsp;";
			buttonStr += "<input  type='button' class='btn btn-one' value='移动端菜单定义' onclick='addMobileMenuFunc(" + flowType.sid + ",\"" + flowType.flowName + "\")' />";
			$("#addMenuSpan").html(buttonStr);
		}

		/**
		 * 菜单自定义
		 */
		function addMenuFunc(sid,menuName){
	     <%--  var childMenuName = menuName;
		  var menuURL = "/system/core/workflow/flowrun/list/index.jsp?flowSortId=" + sid + "&flowId=<%=flowTypeId%>";
		  var url = contextPath + "/system/core/menu/addupdatechild1.jsp?childMenuName=" + encodeURIComponent(childMenuName) + "&menuURL=" + encodeURIComponent(menuURL);
		  --%>
		  
		   var url = contextPath + "/system/core/workflow/flowtype/manage/addMenu.jsp?flowId=<%=flowTypeId%>";
		   bsWindow(url ,"菜单定义指南",{width:"760px",height:"360px",buttons:
			     [//{name:"关闭",classStyle:"btn btn-primary"}
			     ]
			  ,submit:function(v,h){
			    var cw = h[0].contentWindow;
			    if(v=="修改"){
			      
			    }else if(v == "删除"){
			      
			    }else if(v=="关闭"){
			      return true;
			    }
			  }}); 
		}
		
		function checkForm(){
			if(!$("#form").form('validate')){
				return false;
			}
			if($("#formId").val()=="0" || $("#formId").val()==""){
				alert("请选择流程表单");
				return false;
			}
			
			
			var autoArchive=$("#autoArchive").val();
			if(autoArchive=="1"||autoArchive==1){//开启
				//获取存档信息
				 var content=$(".archiveItems");
				 var autoArchiveValue=0;
				 for(var i=0;i<content.length;i++){
					if($(content[i]).prop("checked")==true){
						autoArchiveValue=autoArchiveValue+parseInt($(content[i]).val());
					}
				}
				if(autoArchiveValue==0){
					alert("请至少选择一项存档信息！");
					return false;
				}
			}
			return true;
		}
		
		function commit(){
			if(!checkForm()){
				return;
			}
			
			//获取存档信息
			 var content=$(".archiveItems");
			 var autoArchiveValue=0;
			 for(var i=0;i<content.length;i++){
				if($(content[i]).prop("checked")==true){
					autoArchiveValue=autoArchiveValue+parseInt($(content[i]).val());
				}
			}
			//这里是提交表单的方法
			var para = tools.formToJson($("#form"));
			para["autoArchiveValue"]=autoArchiveValue;
			var flowTypeService = new TeeFlowTypeService();
			if(flowTypeService.update(para)){
				$.jBox.tip("保存成功","success");
				parent.parent.document.getElementById("left").contentWindow.location.reload();
				window.location.reload();
			}
		}

		function setPrcsPriv(){
			var url = contextPath+"/system/core/workflow/flowtype/manage/setPrcsPriv.jsp?flowTypeId="+flowTypeId;
			var title = "设置办理权限";
			  bsWindow(url ,title,{width:"500",height:"360",buttons:
					[
					 {name:"保存",classStyle:"btn btn-primary"},
				 	 {name:"关闭",classStyle:"btn btn-default"}
					 ]
					,submit:function(v,h){
					var cw = h[0].contentWindow;
					if(v=="保存"){
						cw.commit(function(json){
							BSWINDOW.modal("hide");
						});
					}else if(v=="关闭"){
						return true;
					}
				}});
		}

		function flowDesign(){
			openFullWindow(contextPath+"/system/core/workflow/flowtype/flowdesign/index.jsp?flowId="+flowTypeId);
		}

		function getLatestVersionFormId(formId){
			var url = contextPath+"/flowForm/getLatestVersionFormId.action";
			var json = tools.requestJsonRs(url,{formId:formId});
			if(json.rtState){
				return json.rtData;
			}
			return 0;
		}
		
		function formPrintExplore(){
			var formId = $("#formId").val();
			formId = getLatestVersionFormId(formId);
			openFullWindow(contextPath+"/system/core/workflow/formdesign/printExplore.jsp?formId="+formId,"表单预览");
		}

		function exportXml(){
			$("#frame0").attr("src",contextPath+"/flowType/exportXml.action?flowId="+flowTypeId);
		}

		function importXml(){
			$("#fileBtn").click();
		}

		function delFlowType(){
			//判断该flowType是否存在关联的FlowRun
			var json=tools.requestJsonRs(contextPath+"/flowType/hasRelatedFlowRun.action",{sid:flowTypeId});
			if(json.rtState){
				if(json.rtData==1){
					$.jBox.tip("该流程类型下关联了流程数据，暂且不能删除！");
					return;
				}else if(json.rtData==0){
					$.jBox.confirm("确认要删除该流程类型吗？","删除流程定义",function(v){
						if(v=="ok"){
							var url = contextPath+"/flowType/delFlowType.action";
							var json = tools.requestJsonRs(url,{flowId:flowTypeId});
							$.jBox.tip(json.rtMsg,"info");
							if(json.rtState){
								parent.parent.document.getElementById("left").contentWindow.location.reload();
								parent.location = contextPath+"/system/core/workflow/flowtype/manage/list.jsp?sortId="+sortId;
							}	
						}
					});
				}
			}
		}

		function doImport(obj){
			if(document.getElementById("file").value.indexOf(".xml")==-1){
				alert("仅能上传xml后缀名模板文件！");
				return false;
			}
			$("#uploadBtn").attr("value","上传中").attr("disabled","");
			return true;
		}
		
		function uploadSuccess(){
			alert("导入流程成功！");
			window.location.reload();
		}

		
		
		//点击导入流程按钮
		function importFlowType(){
			//判断该flowType是否存在关联的FlowRun
			var json=tools.requestJsonRs(contextPath+"/flowType/hasRelatedFlowRun.action",{sid:flowTypeId});
			if(json.rtState){
				if(json.rtData==1){
					$.jBox.tip("该流程类型下关联了流程数据，暂且不能导入流程！");
					return;
				}else if(json.rtData==0){
					$('#uploadDiv').modal('show')
				}
		    }
		}
		
		
		
		function flowCopy(){
			$.jBox.confirm("是否将该流程复制出一份副本","确认",function(v){
				if(v=="ok"){
					var url = contextPath+"/flowType/flowCopy.action";
					$.jBox.tip("正在复制……","loading");
					tools.requestJsonRs(url,{flowId:flowTypeId},true,function(json){
						if(json.rtState){
							parent.parent.left.location.reload();
							$.jBox.tip("复制成功","success");
						}
					});
					return true;
				}
			});
		}
		
		function changed0(obj){
			if(obj.value=="1"){
				$("#prePrcsDiv").hide();
			}else{
				$("#prePrcsDiv").show();
			}
		}
		
		function clearRunDatas(){
			$.jBox.confirm("清空该流程下的所有工作待办数据，将不可恢复，确认要清空吗？（请慎重操作！）","警告！",function(v){
				if(v=="ok"){
					var url = contextPath+"/flowRun/clearRunDatas.action";
					$.jBox.tip("正在清空数据……","loading");
					tools.requestJsonRs(url,{flowId:flowTypeId},true,function(json){
						if(json.rtState){
							//parent.parent.document.getElementById("left").contentWindow.location.reload();
							window.location.reload();
							$.jBox.tip("清空完成","success");
						}
					});
					return true;
				}
			});
		}
		
		
		
		//改变是否在流程结束的时候自动归档
		function isAutoArchive(){
			var autoArchive=$("#autoArchive").val();
			if(autoArchive=="1"||autoArchive==1){//开启
				$("#archiveItem").show();
			}else{
				$("#archiveItem").hide();
			}
			
		}
		
		
		function addMobileMenuFunc(sid,flowName){ //移动端菜单定义指南
			var url=contextPath+"/system/core/workflow/flowtype/manage/addAppMenu.jsp?flowId="+sid+"&flowName="+flowName;
			bsWindow(url ,"移动端菜单定义",{width:"760px",height:"360px",buttons:
			     [{name:"关闭",classStyle:"btn btn-default"},
			      {name:"保存",classStyle:"btn btn-primary"}
			     ]
			  ,submit:function(v,h){
			    var cw = h[0].contentWindow;
			    if(v=="保存"){
			        var json=cw.add();
			        if(json.rtState){
			        	alert("保存成功！");
			        	return true;
			        }else{
			        	alert("保存失败！");
			        	return false;
			        }
			    }else if(v=="关闭"){
			      return true;
			    }
			  }});
		}
	</script>
</head>
<body  onload="doInit()" style="margin:0px;padding:0px;">
<div id="layout">
	<div layout="north" height="50" style="padding:10px;">
		<center>
		   <%
		   	  if(hasPriv){
		   	%>
		   	    <input type="button" class="btn btn-success" value="保存" onclick="commit()"/> 	
		   	<%	  
		   	  }
		   %>
			
			<%
			   if(saferPriv){
				   
				   %>
				   <input id="prcsPrivBtn" type="button" class="btn btn-one" value="发起权限" onclick="setPrcsPriv()" />	   
				   <%	   
			   }
			%>
			
			
			<input id="flowDesignBtn" type="button" class="btn btn-one" value="流程设计" onclick="flowDesign()" style="display:none"/>
			
			 <%
		   	  if(hasPriv){
		   	%>
		   	    <input id="exportBtn" type="button" class="btn btn-one" value="导出流程" onclick="exportXml()" style="display:none"/>
			<input id="importBtn" type="button" class="btn btn-one" value="导入流程" onclick="importFlowType()" style="display:none"/>
			<input type="button" class="btn btn-one" value="复制" onclick="flowCopy()"/>
			<input id="importBtn" type="button" class="btn btn-warning" value="清空流程数据" onclick="clearRunDatas()" />
			<input id="importBtn" type="button" class="btn btn-danger" value="删除" onclick="delFlowType()" />
		   	<%	  
		   	  }
		   %>
			
			<span id="addMenuSpan"></span>
		</center>
	</div>
	<div layout="center" style="overflow:auto">
		<form id="form">
		<input type="hidden" name="sid" value="<%=flowTypeId %>"/>
		<center>
		<table class="TableBlock" style="width:90%;font-size:12px;background:white;text-align:left;">
			<tr>
				<td class="TableHeader" colspan="4" style="font-size:14px;">
					编辑流程定义&nbsp;&nbsp;流程ID[<%=flowTypeId %>]
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG" style="width:170px">流程名称<font color="red">*</font>：</td>
				<td class="TableData"style="">
					<input type="text" name="flowName" class="BigInput easyui-validatebox" size="40" maxlength="100" required="true" />
				</td>
				<td nowrap class="TableData TableBG" style="width:170px">排序号：</td>
				<td class="TableData"style="">
					<input title="控制同一分类下流程的排序" class="BigInput easyui-validatebox" type="text" name="orderNo" id="orderNo" validType ='number[]' size="4" maxlength="100" value="" />
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG">流程类型：</td>
				<td class="TableData">
					<select name="type"  id="type" class="BigSelect" onchange="changed0(this)">
				      <option value="1" >固定流程</option>
				      <option value="2" >自由流程</option>
				    </select>
				    <span id="prePrcsDiv">
					    允许步骤预设：
						<select name="freePreset" class="BigSelect">
					     <option value="1">是</option>
					     <option value="0" selected>否</option>
					    </select>
				    </span>
				</td>
				<td nowrap class="TableData TableBG">委托设置：
				</td>
				<td class="TableData">
					<select name="delegate" id="delegate" class="BigSelect">
					        <option value="2" >允许委托</option>
					        <option value="0" >禁止委托</option>
<!-- 					        <option value="3" >按步骤办理权限委托</option> -->
<!-- 					        <option value="1" >委托当前步骤经办人</option> -->
					    </select>
<%-- 					    <i class="glyphicon glyphicon-question-sign" style="color:#428bca" title="[委托类型说明：]<br> --%>
<!-- 					    <b>1.自由委托：</b>用户可以在工作委托模块中设置委托规则,可以为委托给任何人。<br> -->
<!-- 					    <b>2.按步骤设置的经办权限委托：</b>仅能委托给流程步骤设置中经办权限范围内的人员<br> -->
<!-- 					    <b>3.按实际经办人委托：</b>仅能委托给步骤实际经办人员。<br> -->
<!-- 					    <b>4.禁止委托：</b>办理过程中不能使用委托功能。<br> -->
<%-- 					    <b>注意：</b>只有自由委托才允许定义委托规则，委托后更新自己步骤为办理完毕，主办人变为经办人"></i> --%>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG"style="">流程分类：</td>
				<td class="TableData"style="">
					<select name="flowSortId" id="flowSortId" class="BigSelect" ></select>
				</td>
				<td nowrap class="TableData TableBG"style="">流程附件：</td>
				<td class="TableData"style="">
					<select name="attachPriv"  id="attachPriv" class="BigSelect">
				      <option value="1" >开启</option>
				      <option value="0" >关闭</option>
				    </select>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG">流程表单：</td>
				<td class="TableData">
					<input type="text" id="formId" name="formId" class="BigInput"/><a href="javascript:void(0);" onclick="formPrintExplore()">预览表单</a>
				</td>
				<td nowrap class="TableData TableBG">
				传阅设置&nbsp;<i class="glyphicon glyphicon-question-sign" style="color:#428bca" title="
				      			开启并设置后，当流程结束时，将该流程信息传阅给预设的指定人员。
				      		"></i>：
				</td>
				<td class="TableData">
				是否传阅:<input type="checkbox" name="viewPriv" value='1'>
			    <br>
				<textarea  class="BigTextarea" name="viewPersonNames" id="viewPersonNames" rows="3" cols="25" class="SmallStatic" wrap="yes" readonly></textarea>
				<input type="hidden" name="viewPersonIds" id="viewPersonIds" />
				<br>
				  <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['viewPersonIds','viewPersonNames'])">选择</a>
				  <a href="javascript:void(0);" class="orgClear" onClick="clearData('viewPersonIds','viewPersonNames')">清空</a>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG" style="">文档类型：</td>
				<td class="TableData" style="">
					<select class="BigSelect" id="hasDoc" name="hasDoc">
						<option value="0">表单</option>
						<option value="1">表单+正文</option>
						<option value="2">表单+版式正文</option>
						<option value="3">表单+正文+版式正文</option>
					</select>	
				</td>
				<td nowrap class="TableData TableBG" style="">会签：</td>
				<td class="TableData" style="">
				    <select name="feedbackPriv"  id="feedbackPriv" class="BigSelect">
				      <option value="1" >开启</option>
				      <option value="0" >关闭</option>
				    </select>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG">工作名称设置：</td>
				<td class="TableData">
					表&nbsp;&nbsp;达&nbsp;&nbsp;式：
				      		<input class="BigInput" type="text" name="fileCodeExpression" id="fileCodeExpression" />
				      		<i class="glyphicon glyphicon-question-sign" style="color:#428bca" title="
				      			<b>{年}</b>：当前年份<br/>
				      			<b>{月}</b>：当前月份<br/>
				      			<b>{日}</b>：当前日期<br/>
				      			<b>{时}</b>：当前小时<br/>
				      			<b>{分}</b>：当前分钟<br/>
				      			<b>{秒}</b>：当前秒数<br/>
				      			<b>{流程名称}</b>：该流程定义的名称<br/>
				      			<b>{用户姓名}</b>：当前发起人的用户姓名<br/>
				      			<b>{部门}</b>：当前发起人的所在部门<br/>
				      			<b>{部门全称}</b>：当前发起人的所在部门全称<br/>
				      			<b>{角色}</b>：当前发起人的所属角色<br/>
				      			<b>{流水号}</b>：当前流程实例的流水号<br/>
				      			<b>{编号}</b>：当前流程实例的编号<br/>
				      			<b>{DATA_控件名称}</b>：动态获取表单字段内容作为工作名称<br/>
				      		"></i>
				      		<br/>
				      		当前编号：
				      		<input class="BigInput readonly" type="text" name="numbering" id="numbering" readonly value="1" />
				      		<br/>
				      		编号位数：
				      		<input class="BigInput" type="text" name="numberingLength" id="numberingLength" value="0" />
				</td>
				<td nowrap class="TableData TableBG" style="">流程描述：</td>
				<td class="TableData" style="">
					<textarea name="comment" id="comment" style="width:300px;height:100px" class="BigTextarea"></textarea>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG" style="">工作名称权限：</td>
				<td class="TableData" style="">
					新建时
					<select class="BigSelect" id="runNamePriv" name="runNamePriv">
						<option value="0">不允许修改</option>
						<option value="1">允许修改</option>
						<option value="2">仅允许输入前缀</option>
						<option value="3">仅允许输入后缀</option>
						<option value="4">仅允许输入前缀和后缀</option>
					</select>
				</td>
				<td nowrap class="TableData TableBG" style="" ><font id="deptTd">所属部门：</font></td>
				<td class="TableData" style="">
				   <input type="hidden" name="deptId" id="deptId"/>
		           <input  name="deptName" id="deptName" class="readonly BigInput" readonly />
		           <a href="javascript:void(0)" id="selDept" onclick="selectSingleDept(['deptId','deptName'])">选择</a>
		           &nbsp;
		           <a href="javascript:void(0)" id="clearDept" onclick="clearData('deptId','deptName')">清除</a>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG" style="">流程结束自动归档：</td>
				<td class="TableData" style="" colspan="3">
				    <select class="BigSelect" name="autoArchive" id="autoArchive"  onchange="isAutoArchive();">
				        <option value="0">关闭</option>
				        <option value="1">开启</option>
				    </select>
				    <span id="archiveItem" style="margin-left: 20px;display: none;">
				                        请选择存档信息：<input type="checkbox" class="archiveItems"  value="1" id="bd"/>表单&nbsp;
				        <input type="checkbox" class="archiveItems" value="2" id="qpd"/>签批单&nbsp;
				        <input type="checkbox" class="archiveItems" value="4" id="zw"/>正文&nbsp;
				        <input type="checkbox" class="archiveItems" value="8" id="bszw"/>版式正文&nbsp;
				        <input type="checkbox" class="archiveItems" value="16" id="ggfj"/>公共附件
				    </span>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG" style="">外部页面：</td>
				<td class="TableData" style="" colspan="3">
					<input type="text" name="outerPage" class="BigInput" style="width:70%" id="outerPage" />
				</td>
			</tr>
		</table>
		</center>
		</form>
		<iframe id="frame0" style="display:none"></iframe>
	</div>
</div>

<form id="uploadForm" onsubmit="return doImport()" target="frame" action="<%=contextPath %>/flowType/importXml.action?flowId=<%=flowTypeId %>" name="uploadForm" method="post" enctype="multipart/form-data" >
<div class="modal fade" id="uploadDiv">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">流程导入</h4>
      </div>
      <div class="modal-body">
        <!-- 导入专用 -->
        	<span style="color:red">1.导入的格式为*.xml，且必须是兼容本系统的流程文件格式。</span><br/><br/>
			<input style="border:0px" type="file" name="file" id="file"/>
			<br/>
			<input type="checkbox" id="importOrg" name="importOrg" value="1"/>&nbsp;&nbsp;导入用户相关信息(若组织结构不同，请勿勾选此项) 
			<iframe id="frame" name="frame" style="display:none" src="" ></iframe>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="submit" class="btn btn-primary" id="uploadBtn" >上传</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>
</body>

</html>