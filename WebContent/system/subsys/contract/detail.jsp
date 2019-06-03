<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%
	String sid = request.getParameter("sid");
%>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/upload.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>合同详细</title>
	<script type="text/javascript" charset="UTF-8">
	var sid = "<%=sid%>";
	
	$(function() {
		if(sid!="null"){
			var json = tools.requestJsonRs(contextPath+"/contract/get.action",{sid:sid});
			bindJsonObj2Cntrl(json.rtData);
			$("#startTime").html(getFormatDateStr(json.rtData.startTime,"yyyy-MM-dd"));
			$("#endTime").html(getFormatDateStr(json.rtData.endTime,"yyyy-MM-dd"));
			$("#verTime").html(getFormatDateStr(json.rtData.verTime,"yyyy-MM-dd"));
			$("#addFav").addFav("(合同详情)"+json.rtData.contractName,"/system/subsys/contract/detail.jsp?sid="+sid);
			
			//获取附件
			var json = tools.requestJsonRs(contextPath+"/attachmentController/getAttachmentModelsByIds.action",{attachIds:json.rtData.attachIds});
			for(var i=0;i<json.rtData.length;i++){
				var item = json.rtData[i];
				item.priv = 1+2;
				var fileItem = tools.getAttachElement(item);
				$("#fileContainer").append(fileItem);
			}
		}
	});
	</script>
</head>
<body >
<center>
<table class="TableBlock" width="760" align="center">

  <tr>

    <td nowrap class="TableHeader " colspan="6"><b>&nbsp;合同基本信息</b></td>

  </tr>

  <tr>
        
	<td nowrap align="left" width="120" class="TableContent">合同名称：</td>

    <td nowrap align="left" class="TableData" width="180" id="contractName"></td>  

  	<td nowrap align="left" width="120" class="TableContent">合同编号：</td>

    <td nowrap align="left" class="TableData" width="180" id="contractCode"></td>  

  </tr>

  <tr>

  	<td nowrap align="left" width="120" class="TableContent">合同类型：</td>

    <td class="TableData" width="180" id="categoryName"></td>  	

    <td nowrap align="left" width="120" class="TableContent">业务人员：</td>

    <td class="TableData" width="180" colspan="3"  id="bisUser"></td>         

  </tr>

  <tr>

  	<td nowrap align="left" width="120" class="TableContent">所在部门：</td>

    <td class="TableData" width="180"  id="deptName"></td>  

    <td nowrap align="left" width="120" class="TableContent">合同金额：</td>

    <td class="TableData" width="180" colspan="3"  id="amount"></td>             

  </tr>

  <tr>

    <td nowrap align="left" width="120" class="TableContent">合同开始时间：</td>

    <td class="TableData" width="180"  id="startTime"></td>

    <td nowrap align="left" width="120" class="TableContent">合同结束时间:</td>

    <td class="TableData" width="180" colspan="3"  id="endTime"></td>       

  </tr>

  <tr>

    <td nowrap align="left" width="120" class="TableContent">合同签订时间：</td>

    <td class="TableData" width="180"  id="verTime"></td>   

  </tr>

  <tr>
    <td nowrap align="left" colspan="6" class="TableHeader">合同双方信息：</td>

  </tr>   

  <tr>

    <td nowrap align="left" width="120" class="TableContent">甲方单位：</td>

    <td class="TableData" width="180"  id="partyAUnit"></td>

    <td nowrap align="left" width="120" class="TableContent">乙方单位:</td>

    <td class="TableData" width="180" colspan="3"  id="partyBUnit"></td>       

  </tr>

  <tr>

    <td nowrap align="left" width="120" class="TableContent">甲方负责人：</td>

    <td class="TableData" width="180"  id="partyACharger"></td>

    <td nowrap align="left" width="120" class="TableContent">乙方负责人:</td>

    <td class="TableData" width="180" colspan="3"  id="partyBCharger"></td>       

  </tr>

  <tr>

    <td nowrap align="left" width="120" class="TableContent">甲方联系方式：</td>

    <td class="TableData" width="180"  id="partyAContact"></td>

    <td nowrap align="left" width="120" class="TableContent">乙方联系方式:</td>

    <td class="TableData" width="180" colspan="3"  id="partyBContact"></td>       

  </tr>
  
  <tr>
    <td nowrap align="left" colspan="6" class="TableHeader">合同主要内容：</td>

  </tr>   

  <tr>

    <td class="TableData" colspan="6" id="content"></td>               

  </tr>

  <tr>
    <td nowrap align="left" colspan="6" class="TableHeader">备注：</td>

  </tr>   

  <tr>

    <td class="TableData" colspan="6" id="remark"></td>               

  </tr>                                    

  <tr>

  	<td nowrap  class="TableHeader" colspan="6">附件文档：</td>

  </tr>

  <tr>    
    <td nowrap align="left" class="TableData" colspan="6">
    	<div id="fileContainer"></div>
    </td>
  </tr>

  <!-- <tr align="center" class="TableControl">

    <td colspan="6">

      <input type="button" value="关闭" class="BigButton" onClick="CloseWindow();" title="关闭窗口">

    </td>

  </tr>  -->                    

</table>

</center>
<img src="<%=systemImagePath %>/favorite_click.png" class="favStyle" title="添加收藏夹" id="addFav"/>
</body>
</html>