<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
  //获取分类主键
  int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建/编辑页面</title>
</head>
<script>
var sid=<%=sid%>;

//初始化方法
function doInit(){
  	if(sid>0){
  		getInfoBySid(sid);
  	}
	
}

//根据主键  获取分类详情
function  getInfoBySid(sid){
	var url=contextPath+"/supTypeController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
	}
	
}

//提交
function commit(){
	if(check()){
		var url=contextPath+"/supTypeController/addOrUpdate.action";
		var param=tools.formToJson("#form1");
		var parentTypeSid=$("#parentTypeSid").val();
		var orderNum=$("#orderNum").val();
		if(parentTypeSid==""||parentTypeSid==null){
			param['parentTypeSid']=0;	
		}
		if(orderNum==""||orderNum==null){
			param['orderNum']=0;	
		}
		var json=tools.requestJsonRs(url,param);
		return json.rtState;
	}
	
}


//验证
function check(){
	var orderNum=$("#orderNum").val();
	var reg=/^[0-9]+$/;
	if(!reg.test(orderNum)){
		$.MsgBox.Alert_auto("排序号应为非负整数，请重新填写");
		return false;
	}
	
	var typeName=$("#typeName").val();
	if(typeName==""||typeName==null){
		$.MsgBox.Alert_auto("请填写分类名称！");
		return false;
	}
	return true;
}

//选择父分类
function selectParentType(){
	var url=contextPath+"/system/subsys/supervise/supervisionType/selectParent.jsp?sid="+sid;
	top.bsWindow(url ,"选择父分类",{width:"400",height:"200",buttons:
		[
		 {name:"选择",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="选择"){
			
			var selections =cw.$('#treeGrid').treegrid('getSelections');
			$("#parentTypeSid").val(selections[0].sid);
			$("#parentTypeName").val(selections[0].typeName);
			return true;
		}else if(v=="关闭"){
			return true;
		}
	}}); 
	
}


//清空已经选择的父分类的数据
function clearParentType(){
	$("#parentTypeName").val("");
	$("#parentTypeSid").val("");
}
</script>
<body onload="doInit()" style="background-color: #f2f2f2">
<form id="form1">
   <input type="hidden" name="sid" value="<%=sid %>" id="sid" />
   
   <table class="TableBlock" style="width: 100%">
      <tr>
         <td style="text-indent: 10px">分类名称：</td>
         <td>
            <input type="text" name="typeName" id="typeName" style="width: 300px;height: 23px" />
         </td>
      </tr>
      <tr>
         <td style="text-indent: 10px">排序号：</td>
         <td>
            <input type="text" name="orderNum" id="orderNum" style="width: 300px;height: 23px" />
         </td>
      </tr>
      <tr>
         <td style="text-indent: 10px">父级分类：</td>
         <td>
            <input type="hidden" name="parentTypeSid" id="parentTypeSid"  />
            <input type="text" name="parentTypeName" id="parentTypeName"  style="width: 300px;height: 23px" readonly="readonly"/>
            &nbsp;&nbsp;<a href="#" onclick="selectParentType()">选择父分类</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="clearParentType()">清空</a>
         </td>
      </tr>
      <tr>
         <td style="text-indent: 10px">所属人员：</td>
         <td>
            <input name="userIds" id="userIds" type="hidden"/>
			  <textarea class="BigTextarea readonly" id="userNames" name="userNames" style="height:80px;width: 80%"  readonly></textarea>
			  <span class='addSpan'>
				  <img src="<%=contextPath %>/system/subsys/supervise/imgs/icon_select.png" onclick="selectUser(['userIds','userNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
				  <img src="<%=contextPath %>/system/subsys/supervise/imgs/icon_cancel.png" onclick="clearData('userIds','userNames')" value="清空"/>
			  </span>
         </td>
      </tr>
      <tr>
         <td style="text-indent: 10px">所属角色：</td>
         <td>
            <input name="roleIds" id="roleIds" type="hidden"/>
			  <textarea class="BigTextarea readonly" id="roleNames" name="roleNames" style="height:80px;width: 80%"  readonly></textarea>
			  <span class='addSpan'>
				  <img src="<%=contextPath %>/system/subsys/supervise/imgs/icon_select.png" onclick="selectRole(['roleIds','roleNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
				  <img src="<%=contextPath %>/system/subsys/supervise/imgs/icon_cancel.png" onclick="clearData('roleIds','roleNames')" value="清空"/>
			  </span>
         </td>
      </tr>
      <tr>
         <td style="text-indent: 10px">所属部门：</td>
         <td>
            <input name="deptIds" id="deptIds" type="hidden"/>
			  <textarea class="BigTextarea readonly" id="deptNames" name="deptNames" style="height:80px;width: 80%"  readonly></textarea>
			  <span class='addSpan'>
				  <img src="<%=contextPath %>/system/subsys/supervise/imgs/icon_select.png" onclick="selectDept(['deptIds','deptNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
				  <img src="<%=contextPath %>/system/subsys/supervise/imgs/icon_cancel.png" onclick="clearData('deptIds','deptNames')" value="清空"/>
			  </span>
         </td>
      </tr>
   
   </table>
</form>
</body>
</html>