<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
     	String sid = request.getParameter("sid");
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp"%>
<%@include file="/header/easyui2.0.jsp"%>
<%@include file="/header/validator2.0.jsp"%>
<%@include file="/header/ztree.jsp"%>
<%@include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
.ztree{
margin-top:0;
height:200px; 
width:150px; 
display:none;
background:white;
border:1px solid gray;
overflow:auto;
}
</style>
<script>
var sid = "<%=sid%>";
var deptName;
function doInit(){
	getAssetsType();
	getDeptParent();
    new TeeSimpleUpload({
	 	fileContainer:"fileContainer",//文件列表容器
		uploadHolder:"uploadHolder",//上传按钮放置容器
		valuesHolder:"valuesHolder",//附件主键返回值容器，是个input
		showUploadBtn:false,
		form:"form1",
		post_params:{model:"assets"}//后台传入值，model为模块标志
	});
    getAssetsInfo();
}
function commitInfo(){
	var para =  tools.formToJson($("#form1")) ;
	para["model"] = "assets";
	if($("#form1").valid() && checkForm()){
		$("#form1").doUpload({
			url:"<%=contextPath%>/TeeFixedAssetsInfoController/editAssetsInfo.action",
			success:function(json){
				parent.$.MsgBox.Alert_auto(json.rtMsg);
				location.href=contextPath+"/system/core/base/fixedAssets/manage/index.jsp";
			},
			post_params:para
		});
	}
}
function getAssetsType(){
	var url =contextPath+"/TeeFixedAssetsCategoryController/datagrid.action"
	var json = tools.requestJsonRs(url);
	var typeNames = json.rows;
	var html = "<option value=\"0\"></option>";
	for(var i=0;i<typeNames.length;i++){
		html+="<option value=\""+typeNames[i].sid+"\">"+typeNames[i].name+"</option>";
	}
	$("#typeId").html(html);
}
function setDeptName(){
	$("#deptIdName").val(deptName);
}

/**
 * 获取部门
 */
function getDeptParent(){
	var url =  "<%=contextPath %>/deptManager/getDeptTreeAll.action";
		var config = {
				zTreeId:"deptIdZTree",
				requestURL:url,
	           	onClickFunc:onclickDept,
				async:false,
				onAsyncSuccess:setDeptParentSelct
			};
		zTreeObj = ZTreeTool.config(config);
		//setTimeout('setDeptParentSelct()',500);
} 
/**
 * 初始化后选中节点,上级部门
 */
function setDeptParentSelct(){
	ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
    if(ZTreeObj == null){
    	setTimeout('setDeptParentSelct()',500);
    }else{
    	ZTreeObj.expandAll(true);
    	 var node = ZTreeObj.getNodeByParam("id",$("#deptId").val(),null);
    	    if(node){
    	    	ZTreeObj.selectNode(node);
    	  }
    }  
    ZTreeTool.inputBindZtree(ZTreeTool.zTreeId,'deptId','');
}

//点击树执行事件
function onclickDept (event, treeId, treeNode) {
	$("#deptIdName").val(treeNode.name);
	$("#deptId").val(treeNode.id);
	ZTreeTool.hideZtreeMenu();
}


function getAssetsInfo(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeFixedAssetsInfoController/getAssetsInfoById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			deptName=json.rtData.deptName;
			setTimeout('setDeptName()',500);
			var attaches = json.rtData.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachs").append(fileItem);
			}
			
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}

function checkForm(){
    var check= $("#form1").valid();
    if($("#typeId").val()=="" || $("#typeId").val()==null || $("#typeId").val()==0){
    	$.MsgBox.Alert_auto("资产类型不能为空,请选择相应类型！");
		return false;
	}
    if(!check){
    	return false;
    }
    return true;
}

</script>

</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;">
<div id="toolbar" class = "setHeight clearfix" style="margin-bottom: 10px;margin-top: 10px;">
<div class=" fl ">
   <h4 style="font-size: 16px;font-family:MicroSoft YaHei;margin-left: 10px;">
				修改资产
				</h4>
</div>
   <div class=" fr">
	        <input type="button" id="addAssetsInfo" name="addAssetsInfo" value="保存" onclick="commitInfo();" class="btn-win-white" title="保存" style="width:45px;height:25px;"/>&nbsp;
			<input type="reset" id="reset" name="reset"  value="重填" onclick="$('.reset').click();" class="btn-win-white" title="重填" style="width:45px;height:25px;" />&nbsp;
	 	    <input type="button" id="back" name="back"  value="返回" onclick="history.go(-1);" class="btn-win-white" title="返回" style="width:45px;height:25px;"/>&nbsp;&nbsp; 
   </div> 
   <span class="basic_border" style="margin-top: 10px;"></span>
</div>

<form method="post" name="form1" id="form1" enctype="multipart/form-data">
	<table class="TableBlock_page">
		<tr>
			<td style="text-indent:10px;" class="TableData">
				资产编号<span style="color:red;">*</span>：
			</td>
			<td class="TableData">
				<input type='text' required id="assetCode" name="assetCode" style="width:165px;font-family:MicroSoft YaHei;"/>
			</td>
		
			<td style="text-indent:10px;" class="TableData TableBG">
				资产名称<span style="color:red;">*</span>：
				</td>
			<td  colspan="3" class="TableData">
				<input type='text' required id="assetName" name="assetName" style="width:165px;font-family:MicroSoft YaHei;"/>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px;" class="TableData TableBG">
				资产类别<span style="color:red;">*</span>：
				</td>
			<td class="TableData">
				<select class="BigSelect"  id="typeId" name="typeId" style="width:165px;height: 30px;font-family:MicroSoft YaHei;">
				</select>
			</td>
			
			<td style="text-indent:10px;" class="TableData TableBG">
				增加类别<span style="color:red;">*</span>：
				</td>
			<td class="TableData">
				<select class="BigSelect"  id="addKind" name="addKind" style="width:165px;height: 30px;font-family:MicroSoft YaHei;" >
					<option value='1'>购入不需安装的固定资产</option>
					<option value='2'>购入需安装已完工的固定资产</option>
					<option value='3'>其他单位转入的固定资产(新设备)</option>
					<option value='4'>其他单位转入的固定资产(旧设备)</option>
					<option value='5'>捐赠的固定资产</option>
					<option value='6'>融资租赁的固定资产</option>
					<option value='7'>固定资产盘盈</option>
				</select>
			</td>
		</tr>

		<tr>
			<td style="text-indent:10px;" class="TableData">
				规格型号：
				</td>
			<td  class="TableData">
				<input type="text"  type='text' name="assetsVersion" maxlength="200" style="width:165px;font-family:MicroSoft YaHei;">
			</td>
			<td style="text-indent:10px;" class="TableData">
				所属部门：
				</td>
			<td  class="TableData">
				<input id="deptId" name="deptId"  type="text" style="display:none;width:165px;"/>
				<input type="text" id="deptName" name="deptName" style="font-family:MicroSoft YaHei;"/>
				<span class='addSpan'>
		         	<img src="/common/zt_webframe/imgs/xzbg/gdzc/add.png" onClick="selectSingleDept(['deptId','deptName'],'1')" value="选择"/>&nbsp;
			        <img src="/common/zt_webframe/imgs/xzbg/gdzc/clear.png"  onClick="clearData('deptId','deptName')" value="清空"/>
		        </span>
				<!-- <ul id="deptIdZTree" class="ztree" style="margin-top:0; width:247px; display:none;"></ul> -->
				
			</td>
		</tr>
		
		<tr>
			<td style="text-indent:10px;" class="TableData TableBG">
				制造商：
				</td>
			<td  class="TableData">
				<input type="text"  type='text' name="madein"  maxlength="200" style="width:165px;font-family:MicroSoft YaHei;">
			</td>
			<td style="text-indent:10px;" class="TableData TableBG">
				保管人<span style="color:red;">*</span>：
			</td>
			<td colspan="3" class="TableData" style="vertical-align: bottom;">
				<input type='hidden' class="BigInput" id="keeperId" name="keeperId"/>
				<input type="text" id="keeperName" name="keeperName" readonly style="width:165px;font-family:MicroSoft YaHei;"/>
			      <span class='addSpan'>
		         	<img src="/common/zt_webframe/imgs/xzbg/gdzc/add.png" onclick="selectSingleUser(['keeperId','keeperName'])" value="选择"/>
			        <img src="/common/zt_webframe/imgs/xzbg/gdzc/clear.png"  onclick="clearData('keeperId','keeperName')" value="清空"/>
		          </span>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px;" class="TableData">
				资产性质：
				</td>
			<td colspan="3" class="TableData">
				<select class="BigSelect"  id="assetKind" name="assetKind" style="width:165px;height:30px;font-family:MicroSoft YaHei;">
					<option value='1'>资产</option>
					<option value="2">费用</option>
				</select>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px;" class="TableData">
				经销商：
				</td>
			<td  class="TableData">
				<input type="text"  name="dealer" type='text' maxlength="200" style="width:165px;font-family:MicroSoft YaHei;">
			</td>
			<td style="text-indent:10px;" class="TableData">
				使用年限：
			</td>
			<td colspan="" class="TableData"  style="vertical-align: bottom;">
				<input type="text"  name="useYears" maxlength="200" validType='number[]' style="width:165px;font-family:MicroSoft YaHei;">
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px;" class="TableData">
				资产原值<span style="color:red;">*</span>：
				</td>
			<td  class="TableData">
				<input type='text' required  id="assetVal" name="assetVal" style="width:165px;font-family:MicroSoft YaHei;" validType='number[]' maxlength="9"/>
			</td>
			<td style="text-indent:10px;" class="TableData">
				发票日期：
				</td>
			<td  class="TableData">
				<input type='text'  name="receiptDateStr" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="BigInput Wdate" style="width:165px;" />
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px;" class="TableData TableBG">
				残值<span style="color:red;">*</span>：
				</td>
			<td  class="TableData">
				<input type='text' required id="assetBal" name="assetBal" style="width:165px;font-family:MicroSoft YaHei;" validType='number[]' maxlength="9" />
			</td>
	
			<td style="text-indent:10px;" class="TableData TableBG">
				折旧年限<span style="color:red;">*</span>：
				</td>
			<td  class="TableData">
				<input type='text' required id="assetYear" name="assetYear" style="width:165px;font-family:MicroSoft YaHei;" validType='number[]' maxlength="9"/ >
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px;" class="TableData TableBG">
				残值率<span style="color:red;">*</span>：
				</td>
			<td  class="TableData">
				<input type='text' required id="assetBalRate" name="assetBalRate" style="width:165px;font-family:MicroSoft YaHei;" validType='number[]' maxlength="9" />
			</td>
	
			<td style="text-indent:10px;" class="TableData TableBG">
				资产状态：
			</td>
			<td class="TableData">
				<select class="BigSelect" id="useState" name="useState" style="width:165px;height: 30px;font-family:MicroSoft YaHei;">
					<option value="0">在库</option>
					<option value="1">领用</option>
					<option value="2">维修中</option>
					<option value="3">已报废</option>
				</select>
			</td>
		</tr>
			<tr style="display:none">
			<td style="text-indent:10px;" class="TableData">
				折旧方式：
				</td>
			<td colspan="3" class="TableData">
				<select class="BigSelect"  id="depreciation" name="depreciation" style="width:165px;font-family:MicroSoft YaHei;">
					<option value='1'>年限平均法（也称直线法）</option>
					<option value='2'>工作量法</option>
					<option value='3'>双倍余额递减法（加速折旧法）</option>
					<option value='4'>年数总合法（加速折旧法）</option>
				</select>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px;" class="TableData TableBG">
				启用时间<span style="color:red;">*</span>：
				</td>
			<td   class="TableData">
				<input type="text" id='valideTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='valideTimeDesc' class="Wdate BigInput easyui-validatebox" required style="width:165px;"/>
			</td>
		
			<td style="text-indent:10px;" class="TableData TableBG">
				物理位置：
				</td>
			<td  class="TableData">
				<input type="text" name="physicalLocation"  name='valideTimeDesc' style="width:165px;font-family:MicroSoft YaHei;"/>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px;" class="TableData">
				资产折旧说明：
				</td>
			<td  colspan="3" class="TableData">
				资产折旧方式为平均年限法，月折旧额=固定资产原值*（1-残值率）/（12*折旧年限）
				<br/>
				折旧计算是从资产 “启用时间” 开始折旧。
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px;" class="TableData">
				资产图片：
				</td>
			<td  colspan="3" class="TableData">
				<div id ='attachs'></div>
			</td>
		</tr>
		
		<tr>
			<td style="text-indent:10px;" class="TableData">
				资产图片上传：
				</td>
			<td colspan="3" class="TableData">
			<div id="fileContainer"></div> <a id="uploadHolder"class="add_swfupload">附件上传</a> 
			<input id="valuesHolder" type="hidden" />
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px;" class="TableData">
				备注：
				</td>
			<td colspan="3" class="TableData">
				<textarea  id="remark" name="remark" style="width:600px;height:100px;font-family:MicroSoft YaHei;"></textarea>
			</td>
		</tr>
	</table>
	<input type='hidden' class="BigInput" id="sid" name="sid" value="<%=sid%>"/>
	<input hidden class='reset' type='reset'></input>
</form>

</body>
</html>