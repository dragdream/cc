<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>自定义字段管理</title>
<script>
function doInit(){
	getFieldList();
	
}

//获取所有的项目类型列表
function getFieldList(){
	var url=contextPath+"/projectCustomFieldController/getFieldList.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
		if(data.length>0){		
			for(var i=0;i<data.length;i++){
				var isQuery=data[i].isQuery;
				var isShow=data[i].isShow;
				var queryStr="";
				var showStr="";
				if(isQuery==1){
					queryStr="<td nowrap align='center' style='width:10%;'>√</td>";
				}else{
					queryStr="<td nowrap align='center' style='width:10%;'>×</td>";
				}
				
				if(isShow==1){
					showStr="<td nowrap align='center' style='width:10%;'>√</td>";
				}else{
					showStr="<td nowrap align='center' style='width:10%;'>×</td>";
				}
				$("#tbody").append("<tr style='line-height:35px;border-bottom:1px solid #f2f2f2;'>"
						+"<td nowrap align='center' style='width:20%;text-indent:10px;'>" +data[i].orderNum+ "</td>"
						+"<td nowrap align='center' style='width:10%;'>" + data[i].fieldName + "</td>"
						+"<td nowrap align='center' style='width:14%;'>" + data[i].projectTypeName + "</td>"
						+"<td nowrap align='center' style='width:14%;'>" + data[i].fieldType + "</td>"
						+queryStr+showStr
						+"<td nowrap align='center' style='width:22%;'><a href='#' onclick='addOrUpdate("+data[i].sid+");'>编辑</a>&nbsp;&nbsp;<a href='#' onclick='deleteBySid("+data[i].sid+")'>删除</a></td>"
	                  
			  	+ "</tr>"); 		
			}
		}else{
			messageMsg("暂无自定义字段！", "message" ,'info');	
		}
		
	}
}


//新增或者编辑
function  addOrUpdate(sid){
	window.location.href=contextPath+"/system/subsys/project/setting/fieldsetting/addOrUpdate.jsp?sid="+sid;
}


//删除
function deleteBySid(sid){
	$.MsgBox.Confirm ("提示", "是否确认删除该自定义字段?", function(){
		var url=contextPath+"/projectCustomFieldController/deleteBySid.action";
		var para={sid:sid};
		var json=tools.requestJsonRs(url,para);
		if(json.rtState){
			$.MsgBox.Alert_auto("删除成功！");
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto("删除失败！");
		}
	  });	
}
</script>

<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
  <div class="clearfix" style="padding-top: 5px;">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/project/img/icon_fieldsetting.png">
		<span class="title">自定义字段</span>
	</div>
	<input   type="button" class="btn-win-white fr" value="增加字段" onclick="addOrUpdate(0);"/>
	<span class="basic_border" style="padding-top: 5px;"></span>
	<div style="padding-top: 10px;">
     <table style="border-collapse: collapse;border: 1px solid #f2f2f2;" width="100%" align="center" >
      <tbody id="tbody">
        <tr style="line-height:35px;border-bottom:2px solid #b0deff!important;background-color: #f8f8f8; ">
     	    <td style="text-indent:10px;width:10%;">排序号</td>
     	    <td style="width:20%;">字段名称</td>
     	    <td style="width:14%;">所属项目类型</td>
     	    <td style="width:14%;">字段类型</td>
     	    <td style="width:10%;">查询字段</td>
     	    <td style="width:10%;">列表显示字段</td>
      		<td style="width:22%;">操作</td>
       </tr>
      </tbody>
   </table>
   <div id="message" style="margin-top: 15px"></div>
   </div>
</div>

</body>
</html>