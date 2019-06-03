<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String puuid = request.getParameter("puuid") == null ? "" : request.getParameter("puuid");
	String uuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
	String type = request.getParameter("type") == null ? "" : request.getParameter("type");
	String deptParent = request.getParameter("deptParent") == null? "" : request.getParameter("deptParent");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<title>部门管理</title>
<link href="<%=cssPath%>/style.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/common/jquery/ztree/css/demo.css" type="text/css" rel="stylesheet"/>


<script type="text/javascript">
var deptParent = '<%=deptParent%>';
var puuid = '<%=puuid%>';
var uuid = '<%=uuid%>';
var type = '<%=type%>';
//alert("deptParent:"+deptParent+","+"uuid:"+uuid+","+"puuid:"+puuid+","+"type:"+type);
function doInit(){
  //初始化所属单位列表
    initUnitsList();
  
	//添加例子一
	if(uuid != ""){//编辑
		var url = "<%=contextPath%>/deptManager/getDeptById.action?isSelectAllColumn=1";
		var para = {uuid:uuid};
		var jsonObj = tools.requestJsonRs(url,para);
		//alert(jsonObj.rtState);
		if(jsonObj.rtState){
			var json = jsonObj.rtData;
			//alert(json.pid);
			if(json.uuid){	
				bindJsonObj2Cntrl(json);
				$("#deptParent").val(json.pId);
				$("#deptParentName").val(json.pName);
				//判读当前登陆人对该部门有没有管理权限
				//获取当前登录人员的管理范围
		        var json = tools.requestJsonRs(contextPath+"/personManager/getPostDeptIds.action");
		        var postIds = ","+json.rtData+",";
		        if(postIds==",0," || postIds.indexOf(uuid)!=-1){//有管理权限
		        	$("#deleteDeptSpan").show();
		        	$("#saveBtn").show();
		        }
				
			}
		}else{
			//alert(jsonObj.rtMsrg);
			$.MsgBox.Alert_auto(jsonObj.rtMsrg);
		}
	}else{//新增
		$("#saveBtn").show();
	}
	//getDeptParent(uuid);
	
}

function addDept(){
	var parent = window.parent.contentFrame;
	var deptParent = uuid;
	var url = "<%=contextPath%>/system/core/dept/addupdate.jsp?puuid=" + uuid +"&type="+type

+"&deptParent="+deptParent;
	window.parent.changePage(url);		 
}


/**
 * 保存
 */
function doSave(){
	if (check()){
		
		//验证部门名称是不是空字符串
		var deptName=$("#deptName").val();
		var reg=/(^\s*)|(\s*$)/g; //去掉字符串左右的空格
		deptName=deptName.replace(reg,'');
		if(deptName==null||deptName==""){
			//alert("您输入的部门名称为空字符串，请重新输入");
			$.MsgBox.Alert_auto("您输入的部门名称为空字符串，请重新输入");
			return;
		}
		
		
		var url = "<%=contextPath%>/deptManager/addOrUpdateDept.action";
		var para =  tools.formToJson($("#form1")) ;
		//处理信息上报人员id
		if($("#infoReportUserId").val()==""){
			para['infoReportUserId']=0;
		}
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			var data = jsonRs.rtData;
		//	alert("保存成功！");
		
			//top.$.jBox.tip('保存成功','info',{timeout:2000});
			$.MsgBox.Alert_auto('保存成功');
			var parent = window.parent;
			parent.location.reload();

	       // window.location.reload();
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		} 
		
	}
	
}


function check() {
	return $("#form1").form('validate'); 
}
/**
 * 获取部门作为上级部门
 */
function getDeptParent(uuid){
	var url =  "<%=contextPath %>/deptManager/getDeptTree.action";
		var config = {
				zTreeId:"deptParentZTree",
				requestURL:url,
				param:{uuid:uuid},
	           	onClickFunc:onclickDept,
					async:false,
					onAsyncSuccess:setDeptParentSelct
			};
		zTreeObj = ZTreeTool.config(config);
	
		//showMenu();
		
	//	$("#deptParentName").bind("click", showParentTree);
		//$("#deptParentNameSpan").bind("click", showParentTree);
		


		//
} 
/**
 * 初始化后选中节点,上级部门
 */
function setDeptParentSelct(){
	var deptName = "";
	ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
    if(ZTreeObj == null){
    	//setTimeout('setDeptParentSelct()',500);
    	setDeptParentSelct();
    }else{
    	///ZTreeObj.expandAll(true);全部展开
    	 var node = ZTreeObj.getNodeByParam("id",$("#deptParent").val(),null);
    	    if(node){
    	    	ZTreeObj.selectNode(node);
    	    	deptName = node.name;
    	  }
    }  
    ZTreeTool.inputBindZtree(ZTreeTool.zTreeId,'deptParent',deptName);
}

//点击树执行事件
function onclickDept (event, treeId, treeNode) {
	$("#deptParentName").val(treeNode.name);
	$("#deptParent").val(treeNode.id);
	ZTreeTool.hideZtreeMenu();
}

/**
 * 删除部门一级下级部门
 */
function deleteDeptAndSubDept(){
	if($("#uuid").val() <= 0){
		$.MsgBox.Alert_auto("没有选择部门");
		//alert("没有选择部门");
		return;
	}
	
	
	 $.MsgBox.Confirm ("提示", "确定要删除当前部门,这样删除会删除下级所有部门,删除后将不可恢复！", function(){
		 var url = "<%=contextPath%>/deptManager/deleteDeptAndSubDept.action";
			var para =  {id: $("#uuid").val() } ;
			var jsonRs = tools.requestJsonRs(url,para);
			if(jsonRs.rtState){
				//var data = jsonRs.rtData;
				//alert("删除成功！");
				$.MsgBox.Alert_auto("删除成功！",function(){
					var parent = window.parent;
					parent.location.reload();
					
				});
				
		   	   //window.location.href = "<%=contextPath%>/system/core/dept/addupdate.jsp";
				
			}else{
				//alert(jsonRs.rtMsg);
				$.MsgBox.Alert_auto(jsonRs.rtMsg);
			}  
	  });
	
}

//初始化所属单位列表
function initUnitsList() {
    var url="<%=contextPath%>/sysCode/getSysCodeByParentCodeNo.action";
    var jsonObj = tools.requestJsonRs(url, {codeNo : "UNIT_LIST"});
    var str = "<option value=''></option>";
    if (jsonObj.rtState) {
        $("#subordinateUnitsCode").find('option').remove();
        for (var i = 0; i < jsonObj.rtData.length; i++) {
            str += "<option value='"+jsonObj.rtData[i].codeNo+"'>" + jsonObj.rtData[i].codeName + "</option>";
        }
    }
    $("#subordinateUnitsCode").append(str);
    
    $("#subordinateUnitsCode").change(function(){
        var checkText = $("#subordinateUnitsCode").find("option:selected").text(); 
        $("#subordinateUnits").val(checkText);
    }); 
}
</script>

</head>
<body onload="doInit()" style="font-family: MicroSoft YaHei;font-size: 12px;">
	<form method="post" name="form1" id="form1" class="tableForm" style="padding-bottom:5px;">
		<table class="TableBlock_page" width="100%" align="center">
            <tr>
               <td colspan="2" style='vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;line-height: 30px;'><img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/icon_yhcx.png" align="absMiddle">&nbsp;&nbsp; 
                  <%
					if (TeeUtility.isNullorEmpty(uuid)) {
				  %> <span> 新增部门</span> <%
 	             } else {
                 %><span> 编辑部门</span> <%
 	             }
                %>
               </td>
            </tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">部门排序号：</td>
				<td nowrap class="TableData"><input type="text" name="deptNo"
					id="deptNo" class="easyui-validatebox BigInput" required="true" size="10"
					maxlength="3" validType="integeBetweenLength[1,999]">&nbsp;整数类型，用于同一级次部门排序
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">部门名称：</td>
				<td nowrap class="TableData"><input type="text" name="deptName"
					id="deptName" class="easyui-validatebox BigInput" required="true" size="25"
					maxlength="25">&nbsp;</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">部门类型：</td>
				<td nowrap class="TableData">
					<select class="BigSelect" name="deptType" id="deptType" >
						<option value="2">单位</option>
						<option value="1">部门</option>
					</select>
				</td>
			</tr>
            <tr>
                <td nowrap class="TableData" style="text-indent: 10px">所属单位：</td>
                <td nowrap class="TableData">
                    <input type="text" name="UnitName" id="unitName" class="BigInput" readonly="readonly">
                    <!-- 
                    <input type="hidden" name="subordinateUnits" id="subordinateUnits" value="" />
                    <select class="BigSelect" name="subordinateUnitsCode" id="subordinateUnitsCode" >
                    </select>
                     -->
                </td>
            </tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">电话：</td>
				<td nowrap class="TableData"><input type="text" name="telNo"
					id="telNo" class="easyui-validatebox BigInput" size="25" maxlength="25">&nbsp;
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">传真：</td>
				<td nowrap class="TableData"><input type="text" name="faxNo"
					id="faxNo" class="BigInput" size="25" maxlength="25" >&nbsp;
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">部门地址：</td>
				<td nowrap class="TableData"><input type="text"
					name="address" class="BigInput" size="40" maxlength="40">&nbsp;
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">上级部门：</td>
				<td class="TableData">
				
				<input type="text" name="deptParentName" readonly  id="deptParentName">&nbsp;
		        <input type="hidden" name="deptParent" id="deptParent"  />
		       
		        <span class='addSpan'>
		         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectSingleDept(['deptParent','deptParentName'],'0')" value="选择"/>
			        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('deptParent','deptParentName')" value="清空"/>
		        </span>
	
               </td>
			</tr>
            <tr>
				<td nowrap class="TableData" style="text-indent: 10px">部门信息上报员：</td>
				<td class="TableData">
				
				<input type="text" name="infoReportUserName" readonly  id="infoReportUserName">&nbsp;
		        <input type="hidden" name="infoReportUserId" id="infoReportUserId"/>
		       
		        <span class='addSpan'>
		         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectSingleUser(['infoReportUserId','infoReportUserName'],'0')" value="选择"/>
			        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('infoReportUserId','infoReportUserName')" value="清空"/>
		        </span>
	
               </td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">部门主管领导(选填)：</td>
				<td nowrap class="TableData">
				<input type="hidden" name="manager" id="manager" value=""> 
					 <textarea cols="45"
						name="managerName" id="managerName" rows="2"
						style="overflow-y: auto;" class="SmallStatic BigTextarea" wrap="yes" readonly  ></textarea>
					
					<span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectUser(['manager', 'managerName'] )" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="clearData('manager', 'managerName')" value="清空"/>
					</span>
					
			</tr>

	<tr>
				<td nowrap class="TableData"  style="text-indent: 10px">部门分管领导(选填)：</td>
				<td nowrap class="TableData">
				     
				<input type="hidden" name="manager2" id="manager2" value=""> 
					 <textarea cols="45"
						name="managerName2" id="manager2Name" rows="2"
						style="overflow-y: auto;" class="SmallStatic BigTextarea" wrap="yes" readonly  ></textarea>
					
					<span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectUser(['manager2', 'manager2Name'] )" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="clearData('manager2', 'manager2Name')" value="清空"/>
					</span>
					
			</tr>

			<tr style="display:none">
				<td nowrap class="TableData" style="text-indent: 10px">上级主管领导(选填)：</td>
				<td nowrap class="TableData"><input type="hidden"
					name="leader1" id="leader1" value=""> <textarea cols="45"
						name="leader1Name" id="leader1Name" rows="2"
						style="overflow-y: auto;" class="SmallStatic BigTextarea" wrap="yes" readonly></textarea>
					<a href="javascript:void(0);" class="orgAdd"
					onClick="selectUser(['leader1', 'leader1Name'])">添加</a> <a
					href="javascript:void(0);" class="orgClear"
					onClick="clearData('leader1', 'leader1Name')">清空</a></td>
			</tr>
			<tr style="display:none">
				<td nowrap class="TableData" style="text-indent: 10px">上级分管领导(选填)：</td>
				<td nowrap class="TableData">
					
					 <input id="leader2" type="hidden" name="leader2" style="width:100%"/>
			
					 <textarea cols="45"
						name="leader2Name" id="leader2Name" rows="2"
						style="overflow-y: auto;" class="SmallStatic BigTextarea" wrap="yes" readonly></textarea>
			<a href="javascript:void(0);" class="orgAdd"
					onClick="selectUser(['leader2', 'leader2Name'])">添加</a> <a
					href="javascript:void(0);" class="orgClear"
					onClick="clearData('leader2', 'leader2Name')">清空</a></td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">部门职能：</td>
				<td nowrap class="TableData"><textarea name="deptFunc"
						id="deptFunc" class="BigTextarea" cols="60" rows="5"></textarea></td>
			</tr>


			<tr>
				<td colspan="2">
				 <div  align="center">
					<input id="saveBtn" style="display: none;" type="button" value="保存" class="btn-win-white" onclick="doSave();">&nbsp;&nbsp;  
					<input type="text" id="uuid" name="uuid" value="0" style="display: none;" />
			
					<span id="deleteDeptSpan" style="display:none;"> <input type="button" value="删除当前部门/下级部门" class="btn-win-white" onclick="deleteDeptAndSubDept();">&nbsp;&nbsp;  </span>
				  </div>
				</td>
			</tr>
		</table>
	</form>
</body>
<script>
/**
 * 选择部门
 * @return
 */
function selectDept(retArray , moduleId,privNoFlag , noAllDept,callBackFunc) {
  deptRetNameArray = retArray;
  objSelectType  = retArray[2] || "";
  var url = contextPath + "/system/core/orgselect/selectMultiDeptPost.jsp?objSelectType=" + objSelectType;
  var has = false;
  if (moduleId) {
    url += "&moduleId=" + moduleId ;
  }
  if (privNoFlag) {
    url += "&privNoFlag=" + privNoFlag ;
  }
  if (noAllDept) {
    url += "&noAllDept=" + noAllDept ;
  }
  if(callBackFunc){
	  url += "&callBackPara=" + callBackFunc ;
  }
  var IM_OA;
try{
    IM_OA = window.external.IM_OA;
}catch(e){}

if(window.showModelDialog || IM_OA){
	  dialogChangesize(url, 560, 400);
  }else{
	  openWindow(url,"选择人员", 560, 400);
  }
  
}


/**
 * 选择单个部门
 * @return
 */
function selectSingleDept(retArray , moduleId,privNoFlag , noAllDept) {
  deptRetNameArray = retArray;
  objSelectType  = retArray[2] || "";
  var url = contextPath + "/system/core/orgselect/selectMultiDeptPost.jsp?objSelectType=" + objSelectType + "&isSingle=1";
  var has = false;
  if (moduleId) {
    url += "&moduleId=" + moduleId ;
  }
  if (privNoFlag) {
    url += "&privNoFlag=" + privNoFlag ;
  }
  if (noAllDept) {
    url += "&noAllDept=" + noAllDept ;
  }
  var IM_OA;
try{
    IM_OA = window.external.IM_OA;
}catch(e){}

if(window.showModelDialog || IM_OA){
	  dialogChangesize(url, 560, 400);
  }else{
	  openWindow(url,"选择人员", 560, 400);
  }
  
}

</script>
</html>