<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建/编辑分类</title>
<%@ include file="/header/header2.0.jsp" %>
<script type="text/javascript">
var sid="<%=sid %>";
function doInit(){
	renderParentCat();
	if(sid>0){
		getInfoBySid();
	}else{
		 $("#parentTr").show();
	}
	
}

//渲染父级分类
function renderParentCat(){
	var url=contextPath+"/zhiDaoCatController/getFirstLevelCat.action";
	var json=tools.requestJsonRs(url);
	if(json.rtState){
		var data=json.rtData;
		var html=[];
		for(var i=0;i<data.length;i++){
			html.push("<option value="+data[i].sid+">"+data[i].catName+"</option>")
		}
		$("#parentCatId").append(html.join(""));
	}
}

//根据主键获取详情、
function getInfoBySid(){
	var url=contextPath+"/zhiDaoCatController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
	   bindJsonObj2Cntrl(json.rtData);
	   if(json.rtData.parentCatId==0){
		   $("#parentTr").hide();
	   }else{
		   $("#parentTr").show();
	   }
	}
}

function doSaveOrUpdate(){
	if(check()){
	    var para = tools.formToJson($("#form1"));
		var url=contextPath+"/zhiDaoCatController/addOrUpdate.action";
		var json=tools.requestJsonRs(url,para);
		return json.rtState; 
	}
}


function check(){
	var catName=$("#catName").val(); 
	if(catName==""||catName=="null"||catName==null){
		$.MsgBox.Alert_auto("请填写分类名称！");
		return false;
	}
	return true;
}
</script>
</head>
<body style="background-color: #f2f2f2" onload="doInit();">
   <form id="form1">
     <input type="hidden" name="sid" id="sid" value="<%=sid %>" />
    <table class="TableBlock" width="100%">
      <tr>
         <td class="TableData" style="text-indent: 10px">分类名称：</td>
         <td class="TableData" >
            <input type="text" name="catName" id="catName"  style="height: 23px;width:300px"/>
         </td>
      </tr>
      <tr id="parentTr" style="display: none">
         <td class="TableData" style="text-indent: 10px">父级分类：</td>
         <td class="TableData" >
            <select id="parentCatId" name="parentCatId" >
               <option value="0">请选择父级分类</option>
            </select>
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px">分类管理员：</td>
         <td class="TableData" >
           <input name="managerIds" id="managerIds" type="hidden"/>
			<textarea class="BigTextarea readonly" id="managerNames" name="managerNames" style="height:80px;width:350px"  readonly></textarea>
			<span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['managerIds','managerNames'],'14')" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('managerIds','managerNames')" value="清空"/>
			</span>
         </td>
      </tr>
    </table> 
   </form>
</body>
</html>