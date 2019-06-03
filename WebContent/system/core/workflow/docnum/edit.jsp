<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color:#f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<style type="">
	 input{
	   height: 23px;
	   width: 200px;
	 
	 }
	 
	</style>
	<title>添加文号</title>
	<script>
		var sid = <%=sid%>;
		function doInit(){
			var url = contextPath + "/docNumController/getById.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				bindJsonObj2Cntrl(json.rtData);
				//proxy = ZTreeTool.comboCtrl($("#flowId"),{url:contextPath+"/workQuery/getFlowType2SelectCtrl.action"});

				var flowIds = json.rtData.flowIds;
				var flowNames = json.rtData.flowNames;
				if(flowIds!=""){
					flowIds = flowIds.split(",");
					flowNames = flowNames.split(",");

					for(var i=0;i<flowIds.length;i++){
						var arr = ["<div val='"+flowIds[i]+"'><span>"+flowNames[i]+"</span><img style='vertical-align:middle' onclick='$(this).parent().remove()' src='"+systemImagePath+"/upload/remove.png' /></div>"];
						$("#flowIdsDiv").append(arr.join(""));
					}
				}
			}
		}
	
		function commit(){
			if(check()){
				var para = tools.formToJson($("#form"));
				var flowIds = [];
				$("#flowIdsDiv div").each(function(i,obj){
					flowIds.push(obj.getAttribute("val"));
				});
				para["flowIds"] = flowIds.join(",");
				
				var url = contextPath+"/docNumController/updateDocNum.action";
				var json = tools.requestJsonRs(url,para);
				if(json.rtState){
					//$.MsgBox.Alert_auto("修改成功！");
					return true;
				}
				$.MsgBox.Alert_auto(json.rtMsg);
				return false;		
			}	
		}

		
		function check(){
			var docName=$("#docName").val();
			if(docName==""||docName==null){
				$.MsgBox.Alert_auto("请填写文号名称！");
				return false;
			}
			return true;
		}
		
		
		
		function addFlow(){
			var flowId = $("#flowId").val();
			var flowName=$("#flowName").val();
			if(flowId==""){
				$.MsgBox.Alert_auto("请选择所要绑定的流程");
				return;
			}

			var target = $("#flowIdsDiv").find("div[val="+flowId+"]");
			if(target.length!=0){
				$.MsgBox.Alert_auto("已添加该流程，请勿重复添加");
				return;
			}

			var arr = ["<div val='"+flowId+"'><span>"+flowName+"</span><img style=\'vertical-align:middle\' onclick='$(this).parent().remove()' src='"+systemImagePath+"/upload/remove.png' /></div>"];
			$("#flowIdsDiv").append(arr.join(""));
			
		}
		
		//选择所属流程
		function selectFlowType(){
			parent.bsWindow(contextPath+"/system/core/workflow/docnum/flowTree.jsp","选择流程",{width:"400",height:"250",buttons:
				[{name:"选择",classStyle:"btn-alert-blue"},
				 {name:"关闭",classStyle:"btn-alert-gray"}]
				,submit:function(v,h,f,d){
				 var cw =h[0].contentWindow;
	             if(v=="选择"){
	            	 if(isNaN(parseInt(h.contents().find("#flowId").val()))){//不是数字
	      		    	parent.$.MsgBox.Alert_auto("请选择流程！");
	      		        return;
	      		    }else{
	      		    	//获取弹出页面的流程的名称和流程的id
	                     $("#flowName").val(h.contents().find("#flowName").val());
	                     $("#flowId").val(h.contents().find("#flowId").val());  	
	      		    }
	                return true;
	             }else if(v=="关闭"){
	                return  true; 	 
	             }
			}});	
		}
	</script>
</head>
<body onload="doInit()" style="background-color:#f2f2f2">
<br/>
<form id="form">
<table width="100%" class="TableBlock"  align="center" style="font-size:12px;">
   <tr>
    <td nowrap class="TableData TableBG" style="text-indent: 15px">文号名称<font color="red">*</font>：</td>
    <td nowrap class="TableData">
       <input type="text" name="docName" id="docName" class="BigInput"/>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData TableBG" style="text-indent: 15px">排序号：</td>
    <td nowrap class="TableData">
       <input type="text" name="orderNo" id="orderNo" class="BigInput"/>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData TableBG" style="text-indent: 15px">文号样式：</td>
    <td nowrap class="TableData">
       <input type="text" name="docStyle" id="docStyle" class="BigInput"/>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData TableBG" style="text-indent: 15px">文号当前值：</td>
    <td nowrap class="TableData">
       <input type="text" name="currNum" id="currNum"  class="BigInput"/>
       &nbsp;默认应从0开始
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData TableBG" style="text-indent: 15px">文号计数值：</td>
    <td nowrap class="TableData">
       <input type="text" name="countNum" id="countNum"  class="BigInput"/>
       &nbsp;默认应从0开始
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData TableBG" style="text-indent: 15px">所属流程：</td>
    <td nowrap class="TableData" style="">
       <div style="display: inline-block;vertical-align: middle;line-height: 25px" >
        <input type="text" id="flowName" name="flowName" onclick="selectFlowType();" value=""/>
       	<input id="flowId" name="flowId" style="display: none" value=""/>
       </div>
       <div style="display: inline-block;padding-left: 5px">
       	<a href="javascript:void(0)" onclick="addFlow()">添加</a>
       </div>
       <div style="clear:both"></div>
       
       <div id="flowIdsDiv">
       	
       </div>
       <div style="color:red;line-height: 25px">
       	添加所属流程，表明该文号可以允许在哪些流程上使用
       </div>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData TableBG" style="text-indent: 15px">部门权限：</td>
    <td  class="TableData">
       <textarea id="deptPrivNames" readonly style="width:400px;height:100px" class="BigTextarea"></textarea>
       <input type="hidden" id="deptPrivIds" name="deptPrivIds"/>
       
       <span class='addSpan'>
		  <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/wenhao/icon_select.png" onclick="selectDept(['deptPrivIds','deptPrivNames'])" value="选择"/>
		  &nbsp;&nbsp;
		  <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/wenhao/icon_cancel.png" onclick="clearData('deptPrivIds','deptPrivNames')" value="清空"/>
	   </span>
       
       <div style="color:red">
       	添加部门权限，表明该文号可以允许哪些部门使用
       </div>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData TableBG" style="text-indent: 15px">角色权限：</td>
    <td  class="TableData">
       <textarea id="rolePrivNames" readonly  style="width:400px;height:100px" class="BigTextarea"></textarea>
       <input type="hidden" id="rolePrivIds" name="rolePrivIds"/>
       
        <span class='addSpan'>
		  <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/wenhao/icon_select.png" onclick="selectRole(['rolePrivIds','rolePrivNames'])" value="选择"/>
		  &nbsp;&nbsp;
		  <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/wenhao/icon_cancel.png" onclick="clearData('rolePrivIds','rolePrivNames')" value="清空"/>
	   </span>
       
       <div style="color:red">
       	添加角色权限，表明该文号可以允许哪些角色使用
       </div>
    </td>
   </tr>
    <tr>
    <td nowrap class="TableData TableBG" style="text-indent: 15px">人员权限：</td>
    <td  class="TableData">
       <textarea id="userPrivNames" readonly  style="width:400px;height:100px" class="BigTextarea"></textarea>
       <input type="hidden" id="userPrivIds" name="userPrivIds"/>
       <span class='addSpan'>
		  <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/wenhao/icon_select.png" onclick="selectUser(['userPrivIds','userPrivNames'])" value="选择"/>
		  &nbsp;&nbsp;
		  <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/wenhao/icon_cancel.png" onclick="clearData('userPrivIds','userPrivNames')" value="清空"/>
	   </span>
       
       <div style="color:red">
       	添加人员权限，表明该文号可以允许哪些人员使用
       </div>
    </td>
   </tr>
</table>
<input type="hidden" value="<%=sid %>" name="sid"/>
</form>
</body>

</html>