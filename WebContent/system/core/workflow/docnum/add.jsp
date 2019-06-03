<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="background-color:#f2f2f2">
<head>
<style>
input{
  height: 23px;
  width:200px;
}
</style>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>添加文号</title>
	<script>
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
	
	
	
		/* function doInit(){
			proxy = ZTreeTool.comboCtrl($("#flowId"),{url:contextPath+"/workQuery/getFlowType2SelectCtrl.action"});
		} */
	
		function commit(){
			if(check()){
				var para = tools.formToJson($("#form"));
				var flowIds = [];
				$("#flowIdsDiv div").each(function(i,obj){
					flowIds.push(obj.getAttribute("val"));
				});
				para["flowIds"] = flowIds.join(",");
				var url = contextPath+"/docNumController/saveDocNum.action";
				var json = tools.requestJsonRs(url,para);
				if(json.rtState){
					//$.MsgBox.Alert_auto("添加成功！");
					return true;
				}
				$.MsgBox.Alert_auto(json.rtMsg);
				return false;
				
			}
			
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

			var arr = ["<div style='' val='"+flowId+"'><span>"+flowName+"</span><img style=\'vertical-align:middle\' onclick='$(this).parent().remove()' src='"+systemImagePath+"/upload/remove.png' /></div>"];
			$("#flowIdsDiv").append(arr.join(""));
			
		}
		
		function check(){
			var docName=$("#docName").val();
			if(docName==""||docName==null){
				$.MsgBox.Alert_auto("请填写文号名称！");
				return false;
			}
			return true;	
		}
	</script>
</head>
<body onload="" style="background-color:#f2f2f2">
<br/>
<form id="form">
<table width="100%" align="center" class="TableBlock" style="font-size:12px;">
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
       <input type="text" name="docStyle" id="docStyle" value="<%="${名称}[${年}]${文号}" %>" class="BigInput"/>
       <br />
       <span style="color:red">
         <%
         out.print("${名称}：代表文号名称；${年}：代表当前年份；${文号}：当前文号数值；${计数}：代表文号计数值；");
         %>
       </span>
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
    <td nowrap class="TableData TableBG" style="text-indent: 15px;">所属流程：</td>
    <td nowrap class="TableData" style="">
       <div style="display: inline-block;vertical-align: middle;line-height: 25px">
       	  <!-- <input id="flowId" name="flowId" onclick="selectFlowType();"/> -->
       	  <input type="text" id="flowName" name="flowName" onclick="selectFlowType();" value=""/>
       	  <input id="flowId" name="flowId" value="" style="display: none;"/>
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
</form>
</body>

</html>