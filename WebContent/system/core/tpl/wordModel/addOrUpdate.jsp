<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script src="<%=contextPath%>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath%>/common/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript">
var sid = "<%=sid%>";
var editor;
function doInit(){
	//初始化的时候加载套红类型
	getSysCodeByParentCodeNo("THLX","wordModelType");
	
	if(sid!="0"){
		var url = contextPath+"/wordModel/getModel.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			
			var attach = {sid:json.rtData.attachId,fileName:json.rtData.fileName,priv:2,ext:"dot"};
			var fileItem = tools.getAttachElement(attach,{});
			$("#modelFile").append(fileItem);
			if(json.rtData.privRanges==2){
				selectRange();
			}
		}
	  }
}

function commit(callback){
	if (checkForm()){
		var url = "";
		var para = tools.formToJson($("#form1"));
		para["privRanges"] = $("input[name=privRanges]:checked:first").val();
		if($("#attachId").val()=="" && $("#file").val()==""){
			$.MsgBox.Alert_auto("请上传模版");
			$("#file").focus();
			return false;
		}
		
		var url = "";
		if(sid=="0"){
			url = contextPath+"/wordModel/addModel.action";
		}else{
			url = contextPath+"/wordModel/updateModel.action";
		}
		
		$("#form1").ajaxSubmit({
			  url :url,
	          iframe: true,
	          data: para,
	          type:"post",
	          success: function(res) {
			 		callback(res);
	            },
	          dataType: 'json'});
		
	}
}

function checkForm(){
	var modelName=$("#modelName").val();
	if(modelName==""||modelName==null){
		$.MsgBox.Alert_auto("请填写模板名称！");
		return false;
	}
    return true; 
}

function selectAll(){
	$("#rangesDiv").hide();
}

function selectRange(){
	$("#rangesDiv").show();
}

</script>

</head>
<body onload="doInit()" style="font-size:12px;background-color: #f2f2f2">
<form method="post" enctype="multipart/form-data" id="form1">
    <table class="TableBlock"  width="100%">
     <tr>
        <td style="text-indent: 10px">模版名称：</td>
        <td><input style="width: 300px;height: 23px;" type="text" required="true"  name="modelName" id="modelName"/></td>
     </tr>
     <tr>
        <td style="text-indent: 10px">模版描述 ：</td>
        <td><textarea  style="height:100px;width: 400px" type="text" name="modelDesc" id="modelDesc"></textarea></td>
     </tr>
	 <tr>
        <td style="text-indent: 10px">排序号 ：</td>
        <td><input style="width: 300px;height: 23px;" type="text" name="sortNo" id="sortNo"/></td>
     </tr>
     <tr>
        <td style="text-indent: 10px">套红类型：</td>
        <td><select name="wordModelType" id="wordModelType" style="width:300px;height: 23px;"></select></td>
     </tr>
	<tr>
        <td style="text-indent: 10px">模版文件 ：</td>
        <td>
          <div id="modelFile"></div>
	      <input style="height:30px;width:300px"  type="file" name="file" id="file"  onchange="fileValidator(this,['dot'])" />
        </td>
     </tr>
	 <tr>
        <td style="text-indent: 10px">授权范围：</td>
        <td>
            <input type="radio" name="privRanges" value="1" id="all" checked onclick="selectAll()"><label for="all">全体人员</label>
	        <input type="radio" name="privRanges" value="2" id="range" onclick="selectRange()"><label for="range">指定授权</label>
        </td>
     </tr>
	<tr id="rangesDiv" style="display:none">
        <td colspan="2">
        <div style="margin-left: 10px">
		<b>人员权限：</b>
		<br/>
		<textarea id="userPrivNames" style="height:100px;width:400px" class="BigTextarea" readonly></textarea>
		<input type="hidden" name="userPrivIds" id="userPrivIds" />
		<span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['userPrivIds','userPrivNames'])" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('userPrivIds','userPrivNames')" value="清空"/>
		</span>
		
		<br/>
		<br/>
		<b>部门权限：</b>
		<br/>
		<textarea id="deptPrivNames" style="height:100px;width:400px" class="BigTextarea" readonly></textarea>
		<input type="hidden" name="deptPrivIds" id="deptPrivIds" />
		<span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectDept(['deptPrivIds','deptPrivNames'])" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('deptPrivIds','deptPrivNames')" value="清空"/>
		</span>
		
		<br/>
		<br/>
		<b>角色权限：</b>
		<br/>
		<textarea id="rolePrivNames" style="height:100px;width:400px" class="BigTextarea" readonly></textarea>
		<input type="hidden" name="rolePrivIds" id="rolePrivIds" />
		
		<span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectRole(['rolePrivIds','rolePrivNames'])" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('rolePrivIds','rolePrivNames')" value="清空"/>
		</span>
	</div>
        </td>
     </tr>
	
    
    </table>
  
	<input type="hidden" name="sid" value="<%=sid %>" />
	<input type="hidden" name="attachId" id="attachId"/>
</form>
</body>
</html>
 