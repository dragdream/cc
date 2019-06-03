<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 	String sid = request.getParameter("sid");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>

var sid='<%=sid%>';
function doInit(){
	var url = contextPath+"/TeeStoreHouseController/getRoom.action?sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
	
}

function commit(){
	if($("#form1").valid()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeStoreHouseController/editRoom.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			$.MsgBox.Alert_auto(json.rtMsg,function(){
				parent.location.reload();
			});
			
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
			return false;
		}
	}
}



//选择所属卷库
function selectParent(){
	var url=contextPath+"/system/core/base/dam/room/privTree.jsp";
	bsWindow(url ,"选择所属卷库",{width:"600",height:"320",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			var parentName=h.contents().find("#parentName").val();
			var parentId=h.contents().find("#parentId").val();	
			if(!checkParent(parentId)){
				return false;			
			}else{
				//获取弹出页面的流程的名称和流程的id
	            $("#parentName").val(parentName);
	            $("#parentId").val(parentId);  
	            return  true;
			}  
		}else if(v=="关闭"){
			return true;
		}
	}});
	
}


//清空所属卷库信息
function clearParent(){
	$("#parentId").val("");
	$("#parentName").val("");
}


//判断所选择的卷库  是否是自己  或者 孩子
function  checkParent(parentId){
	if(parentId==sid){
		$.MsgBox.Alert_auto("不能选择当前编辑卷库为所属卷库！");
		return  false;
	}else{//判断所选择的所属卷库是不是当前编辑的卷库的子库
		var url=contextPath+"/TeeStoreHouseController/checkIsChildren.action";
	    var json=tools.requestJsonRs(url,{sid:sid,checkId:parentId});
	    if(json.rtState){
	    	$.MsgBox.Alert_auto("不能选择当前编辑卷库的子库为所属卷库！");
	    	return false;
	    }else{
	    	return true;
	    }
	}
	
}


//删除
function del(){	
	  $.MsgBox.Confirm ("提示", "删除之后不可恢复，是否确认删除该卷库？", function(){
		  var url = contextPath+"/TeeStoreHouseController/delRoom.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！",function(){
					parent.location.reload();
				});
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
	  });

}
</script>
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
</head>
<body onload='doInit()' style="padding-right: 20px;padding-left: 15px;">
<form id="form1" name="form1">
<input type="hidden" name="sid" id="sid"  value="<%=sid %>" />
	<table  style="width:100%;font-size:12px" class='TableBlock_page'>
		<tr>
		   <td colspan="2" style="vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;line-height: 30px;"><img src="/common/zt_webframe/imgs/common_img/icon_yhcx.png" align="absMiddle">&nbsp;&nbsp; 
                <span>编辑卷库</span> 
            </td>
		</tr>
		<tr class='TableData'>
			<td>
				卷库号：
			</td>
			<td>
				<input type="text"   id="roomCode" name="roomCode" required="true" class="BigInput"/>
			</td>
		</tr>
		<tr>
			<td>
				卷库名称：
			</td>
			<td>
				<input type="text" class="BigInput"  id="roomName" name="roomName"  required="true"/>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				所属卷库：
			</td>
			<td colspan="3">
				<input type="hidden" name="parentId" id="parentId"/>
				<input type="text" name="parentName" id="parentName" readonly="readonly"/>
				<span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectParent();" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearParent();" value="清空"/>
				</span>
			</td>
		</tr>
		<tr>
		    <td>
		                     所属人员：
		    </td>
		    <td colspan="3">
				<input name="userIds" id="userIds" type="hidden"/>
				<textarea class="BigTextarea readonly" id="userNames" name="userNames" style="height:60px;width:450px"  readonly></textarea>
				<span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['userIds','userNames'],'14')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('userIds','userNames')" value="清空"/>
				</span>
			</td>
		</tr>
		<tr>
		    <td>
		                     所属部门：
		    </td>
		    <td colspan="3">
				<input name="deptIds" id="deptIds" type="hidden"/>
				<textarea class="BigTextarea readonly" id="deptNames" name="deptNames" style="height:60px;width:450px"  readonly></textarea>
				<span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectDept(['deptIds','deptNames'],'14')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('deptIds','deptNames')" value="清空"/>
				</span>
			</td>
		</tr>
		<tr>
		    <td>
		                     所属角色：
		    </td>
		    <td colspan="3">
				<input name="roleIds" id="roleIds" type="hidden"/>
				<textarea class="BigTextarea readonly" id="roleNames" name="roleNames" style="height:60px;width:450px"  readonly></textarea>
				<span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectRole(['roleIds','roleNames'],'14')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('roleIds','roleNames')" value="清空"/>
				</span>
			</td>
		</tr>
		<tr>
		    <td>
		                     借阅管理员：
		    </td>
		    <td>
				<input name="borrowManagerId" id="borrowManagerId" type="hidden"/>
				<input type="text" name="borrowManagerName" id="borrowManagerName" style="height: 23px;width: 300px" required="true"/>
				<span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectSingleUser(['borrowManagerId','borrowManagerName'],'14')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('borrowManagerId','borrowManagerName')" value="清空"/>
				</span>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				备注：
			</td>
			<td colspan="3">
				<textarea style="width:450px;height:60px" class="BigTextarea" id="remark" name="remark"></textarea>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
			</td>
			<td>
				<span style="color: red">注意：创建人同时也拥有该目录权限</span>
			</td>
		</tr>
		<tr class='TableData'>
			<td colspan="2">
			   <div class="fr right">
			      <input type="button"  value="保存" class="btn-win-white" onclick="commit();" />
			      <input type="button"  value="删除" class="btn-del-red" onclick="del();" />
			   </div>
			</td>
		</tr>
	</table>
</form>
</body>
<script>
   $("#form1").validate();
</script>
</html>