<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<title>三员设置</title>
<script type="text/javascript">
//初始化
function doInit(){
	//渲染权限组
	renderPriv();
	//初始化原有数据
	initData();
	
	changeOpen();
}


//初始化数据
function initData(){
    var url=contextPath+"/teePartThreeController/getInitData.action";
    var json=tools.requestJsonRs(url,null);
    if(json.rtState){
    	var data=json.rtData;
    	bindJsonObj2Cntrl(data);
    }
}

//保存
function doSave(){
	var url=contextPath+"/teePartThreeController/doSave.action";
	var param=tools.formToJson($("#form1"));
	var json=tools.requestJsonRs(url,param);
	if(json.rtState){
		$.MsgBox.Alert_auto("保存成功！");
	}else{
		$.MsgBox.Alert_auto("保存失败！");
	}
}


//渲染权限组
function renderPriv(){
	var url=contextPath+"/teeMenuGroup/getAllMenuGroupList.action";
	var json=tools.requestJsonRs(url,null);
	if(json.rtState){
	    var  data=json.rtData;
	    if(data.length>0){
	    	for(var i=0;i<data.length;i++){
		    	$("#PART_THREE_DEFAULT_PRIV").append("<option value="+data[i].uuid+">"+data[i].menuGroupName+"</option>");
		    }
	    }
	}
}


//改变开启状态
function  changeOpen(){
	var isOpen=$('input[name="IS_OPEN_PART_THREE"]:checked').val();
	if(isOpen==1){//开启
		$("#tr1").show();
		$("#tr2").show();
		$("#tr3").show();
		$("#tr4").show();
	}else{//停用
		$("#tr1").hide();
		$("#tr2").hide();
		$("#tr3").hide();
		$("#tr4").hide();
	}
}


//设置超级密码
function setPassWord(){
	window.location.href=contextPath+"/system/core/system/partthree/setPwd.jsp";
}
</script>
</head>
<body onload="doInit()">
<form  id="form1">
   <table class="TableBlock_page">
       <tr>
          <td style="width: 20%;text-indent: 10px">是否启用三员设置</td>
          <td>
             <input type="radio" value="1" name="IS_OPEN_PART_THREE"  onchange="changeOpen();"/>开启&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <input type="radio" value="0" name="IS_OPEN_PART_THREE"  onchange="changeOpen();"/>停用
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <a href="#" onclick="setPassWord();">三员超级密码设置</a> 
          </td>
       </tr>
       <tr id="tr1">
          <td style="width: 20%;text-indent: 10px">系统管理员</td>
          <td>
            <input name="ADMIN_PRIV" id="ADMIN_PRIV" type="hidden"/>
			<textarea class="BigTextarea readonly" id="ADMIN_PRIV_STR" name="ADMIN_PRIV_STR" style="height:70px;width:500px"  readonly></textarea>
			<span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['ADMIN_PRIV','ADMIN_PRIV_STR'],'14')" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('ADMIN_PRIV','ADMIN_PRIV_STR')" value="清空"/>
			</span>
          </td>
       </tr>
       <tr id="tr2">
          <td style="width: 20%;text-indent: 10px">系统安全员</td>
          <td>
               <input name="SAFER_PRIV" id="SAFER_PRIV" type="hidden"/>
			<textarea class="BigTextarea readonly" id="SAFER_PRIV_STR" name="SAFER_PRIV_STR" style="height:70px;width:500px"  readonly></textarea>
			<span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['SAFER_PRIV','SAFER_PRIV_STR'],'14')" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('SAFER_PRIV','SAFER_PRIV_STR')" value="清空"/>
			</span>
          </td>
       </tr>
       <tr id="tr3">
          <td style="width: 20%;text-indent: 10px">安全审计员</td>
          <td>
               <input name="AUDITOR_PRIV" id="AUDITOR_PRIV" type="hidden"/>
			<textarea class="BigTextarea readonly" id="AUDITOR_PRIV_STR" name="AUDITOR_PRIV_STR" style="height:70px;width:500px"  readonly></textarea>
			<span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['AUDITOR_PRIV','AUDITOR_PRIV_STR'],'14')" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('AUDITOR_PRIV','AUDITOR_PRIV_STR')" value="清空"/>
			</span>
          </td>
       </tr>
       <tr id="tr4">
          <td style="width: 20%;text-indent: 10px">新建用户默认菜单权限组</td>
          <td>
             <select name="PART_THREE_DEFAULT_PRIV" id="PART_THREE_DEFAULT_PRIV">
             
             </select>
          </td>
       </tr>
       <tr>
          <td colspan="2">
             <div style="width: 100%;text-align: center;">
               <input type="button" class="btn-win-white" value="保存"  onclick="doSave();"/>
             </div>   
          </td>
       </tr>
   </table>
</form>
</body>
</html>