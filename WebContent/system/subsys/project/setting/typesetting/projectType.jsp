<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目类型</title>
<script>
function doInit(){
	getTypeList();
	
}

//获取所有的项目类型列表
function getTypeList(){
	var url=contextPath+"/projectTypeController/getTypeList.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
		if(data.length>0){	
			for(var i=0;i<data.length;i++){
				$("#tbody").append("<tr style='line-height:35px;border-bottom:1px solid #f2f2f2;'>"
						+"<td nowrap align='center' style='width:10%;text-indent:10px;'>" +data[i].orderNum+ "</td>"
						+"<td nowrap align='center' style='width:30%;'>" + data[i].typeName + "</td>"
						+"<td nowrap align='center' style='width:10%;'><a href='#' onclick='addOrUpdate("+data[i].sid+");'>编辑</a>&nbsp;&nbsp;<a href='#' onclick='deleteBySid("+data[i].sid+")'>删除</a></td>"
	                  
			  	+ "</tr>"); 		
			}
		}else{
			messageMsg("暂无项目类型！", "message" ,'info');	
		}
		
	}
}

//增加或者编辑项目类型
function addOrUpdate(sid){
	    var title="";
		var url=contextPath+"/system/subsys/project/setting/typesetting/addOrUpdateProjectType.jsp?sid="+sid;
		if(sid>0){
			title="编辑项目类型";
		}else{
			title="新增项目类型";
		}
		bsWindow(url ,title,{width:"500",height:"100",buttons:
			[
	         {name:"保存",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
			    cw.commit();
			   
			}else if(v=="关闭"){
				return true;
			}
		}});
		
}


//删除项目类型
function deleteBySid(sid){
	$.MsgBox.Confirm ("提示", "是否确认删除该项目类型?", function(){
		//判断该项目类型下是否有项目
		var url1=contextPath+"/projectController/getProjectListByTypeId.action";
		var json1=tools.requestJsonRs(url1,{sid:sid});
		if(json1.rtState){
			var data=json1.rtData;
			if(data.length>0){
				$.MsgBox.Alert_auto("存在该类型的项目，暂且不能删除该类型！");
			}else{
				 var url=contextPath+"/projectTypeController/deleteBySid.action";
				 var json=tools.requestJsonRs(url,{sid:sid});
					if(json.rtState){
						$.MsgBox.Alert_auto("删除成功！");
						window.location.reload();
					}
			}
		}
  
	  });
	
}
</script>

<body onload="doInit()">
  <div class="clearfix" style="padding-top: 5px;">
	<input   type="button" class="btn-win-white fr" value="增加项目类型" onclick="addOrUpdate(0);"/>
	<span class="basic_border" style="padding-top: 5px;"></span>
	<div style="padding-top: 10px;">
     <table style="border-collapse: collapse;border: 1px solid #f2f2f2;" width="100%" align="center" >
      <tbody id="tbody">
        <tr style="line-height:35px;border-bottom:2px solid #b0deff!important;background-color: #f8f8f8; ">
     	    <td style="text-indent:10px;width:33%;width:33.3333%;">排序号</td>
     	    <td style="width:33%;width:33.3333%;">项目类型</td>
      		<td style="width:33%;width:33.3333%;">操作</td>
       </tr>
      </tbody>
   </table>
   <div id="message" style="margin-top: 15px"></div>
   </div>
</div>

</body>
</html>