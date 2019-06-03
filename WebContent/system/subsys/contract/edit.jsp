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
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/workflowUtils.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/ckeditor/ckeditor.js"></script>
	
	<title>合同编辑</title>
	<script type="text/javascript" charset="UTF-8">
	var sid = "<%=sid%>";
	//strDate2Date
	$(function() {
		var json = tools.requestJsonRs(contextPath+"/contractCategory/getCategoryTreeByManagePriv.action");
		var render = [];
		for(var i=0;i<json.rtData.length;i++){
			render.push("<option value='"+json.rtData[i].id+"'>"+json.rtData[i].title+"</option>");
		}
		$("#categoryId").html(render.join(""));
		changeSort($("#categoryId").val());
		
		if(sid!="null"){
			var json = tools.requestJsonRs(contextPath+"/contract/get.action",{sid:sid});
			bindJsonObj2Cntrl(json.rtData);
			$("#startTime").val(getFormatDateStr(json.rtData.startTime,"yyyy-MM-dd"));
			$("#endTime").val(getFormatDateStr(json.rtData.endTime,"yyyy-MM-dd"));
			$("#verTime").val(getFormatDateStr(json.rtData.verTime,"yyyy-MM-dd"));
			
			//获取附件
			var json = tools.requestJsonRs(contextPath+"/attachmentController/getAttachmentModelsByIds.action",{attachIds:json.rtData.attachIds});
			for(var i=0;i<json.rtData.length;i++){
				var item = json.rtData[i];
				item.priv = 1+2+4+8;
				var fileItem = tools.getAttachElement(item);
				$("#fileContainer").append(fileItem);
			}
		}
		
		//多附件快速上传
		swfUploadObj = new TeeSWFUpload({
			fileContainer:"fileContainer2",//文件列表容器
			renderContainer:"renderContainer2",//渲染容器
			uploadHolder:"uploadHolder2",//上传按钮放置容器
			valuesHolder:"valuesHolder2",//附件主键返回值容器，是个input
			quickUpload:true,//快速上传
			showUploadBtn:false,//不显示上传按钮
			queueComplele:function(){//队列上传成功回调函数，可有可无
				
			},
			renderFiles:true,//渲染附件
			post_params:{model:"contract"}//后台传入值，model为模块标志
			});
		
	});
	
	function changeSort(val){
		var json = tools.requestJsonRs(contextPath+"/contractSort/getSortByCatId.action",{catId:val});
		var render = [];
		for(var i=0;i<json.rtData.length;i++){
			render.push("<option value='"+json.rtData[i].sid+"'>"+json.rtData[i].name+"</option>");
		}
		$("#contractSortId").html(render.join(""));
	}
	
	function save(){
		var para = tools.formToJson($("#form1"));
		//组合数据
		para["startTime"] = strDate2Date(para["startTime"]).getTime();
		para["endTime"] = strDate2Date(para["endTime"]).getTime();
		para["verTime"] = strDate2Date(para["verTime"]).getTime();
		
		para["content"] = editor.getData();
		
		var json = tools.requestJsonRs(contextPath+"/contract/update.action",para);
		alert(json.rtMsg);
		if(json.rtState){
			try{
				opener.doPageHandler();
			}catch(e){}
			CloseWindow();
		}
	}
	</script>
</head>
<body >
<center>
<table width="760" align="center">
	<tr>
		<td style="text-align:right">
			<button class="btn btn-primary" onclick="save()">保存</button>
		</td>
	</tr>
</table>
<br/>
<table class="TableBlock" width="760" align="center" id="form1">
  <tr>

    <td nowrap class="TableHeader " colspan="6"><b>&nbsp;合同基本信息</b></td>

  </tr>

  <tr>
        
	<td nowrap align="left" width="120" class="TableContent">合同名称：</td>

    <td nowrap align="left" class="TableData" width="180">
    	<input type="text" class="BigInput " id="contractName" name="contractName"/>
    </td>  

  	<td nowrap align="left" width="120" class="TableContent">合同编号：</td>

    <td nowrap align="left" class="TableData" width="180"  colspan="3" >
    	<input type="text" class="BigInput " id="contractCode" name="contractCode"/>
    </td>  

  </tr>

  <tr>

  	<td nowrap align="left" width="120" class="TableContent">合同分类：</td>

    <td class="TableData" width="180">
    	<select id="categoryId" name="categoryId" onchange="changeSort(this.value)" class="BigSelect">
    	</select>
    </td>  	

    <td nowrap align="left" width="120" class="TableContent">合同类型：</td>

    <td class="TableData" width="180" colspan="3">
    	<select id="contractSortId" name="contractSortId" class="BigSelect">
    	</select>
    </td>         

  </tr>

  <tr>

  	<td nowrap align="left" width="120" class="TableContent">所在部门：</td>

    <td class="TableData" width="180">
    	<input type="text" class="BigInput" id="deptName" name="deptName" readonly/>
    	<input type="hidden" id="deptId" name="deptId"/>
    	<br/>
    	<a href="javascript:void(0)" onclick="selectSingleDept(['deptId','deptName'])">选择</a>
    	&nbsp;
    	<a href="javascript:void(0)" onclick="clearData('deptId','deptName')">清空</a>
    </td>  

    <td nowrap align="left" width="120" class="TableContent">合同金额：</td>

    <td class="TableData" width="180" colspan="3" >
    	<input type="text" class="BigInput easyui-validatebox"  id="amount" name="amount" />
    </td>             

  </tr>

  <tr>

    <td nowrap align="left" width="120" class="TableContent">合同开始时间：</td>

    <td class="TableData" width="180" >
    	<input type="text" class="BigInput Wdate" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
    </td>

    <td nowrap align="left" width="120" class="TableContent">合同结束时间:</td>

    <td class="TableData" width="180" colspan="3" >
    	<input type="text" class="BigInput Wdate" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
    </td>       

  </tr>

  <tr>

    <td nowrap align="left" width="120" class="TableContent">合同签订时间：</td>

    <td class="TableData" width="180">
    	<input type="text" class="BigInput Wdate" id="verTime" name="verTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
    </td>   
    
    <td nowrap align="left" width="120" class="TableContent">业务员：</td>

    <td class="TableData" width="180"  colspan="3" >
    	<input type="text" class="BigInput " id="bisUser" name="bisUser"/>
    </td>  
	
  </tr>

  <tr>
    <td nowrap align="left" colspan="6" class="TableHeader">合同双方信息：</td>
  </tr>   

  <tr>

    <td nowrap align="left" width="120" class="TableContent">甲方单位：</td>

    <td class="TableData" width="180"  id="">
    	<input type="text" class="BigInput " id="partyAUnit" name="partyAUnit"/>
    </td>

    <td nowrap align="left" width="120" class="TableContent">乙方单位:</td>

    <td class="TableData" width="180" colspan="3"  id="">
    	<input type="text" class="BigInput " id="partyBUnit" name="partyBUnit"/>
    </td>       

  </tr>

  <tr>

    <td nowrap align="left" width="120" class="TableContent">甲方负责人：</td>

    <td class="TableData" width="180"  id="">
    	<input type="text" class="BigInput " id="partyACharger" name="partyACharger"/>
    </td>

    <td nowrap align="left" width="120" class="TableContent">乙方负责人:</td>

    <td class="TableData" width="180" colspan="3"  id="">
    	<input type="text" class="BigInput " id="partyBCharger" name="partyBCharger"/>
    </td>       

  </tr>

  <tr>

    <td nowrap align="left" width="120" class="TableContent">甲方联系方式：</td>

    <td class="TableData" width="180"  id="">
    	<input type="text" class="BigInput " id="partyAContact" name="partyAContact"/>
    </td>

    <td nowrap align="left" width="120" class="TableContent">乙方联系方式:</td>

    <td class="TableData" width="180" colspan="3"  id="">
    	<input type="text" class="BigInput " id="partyBContact" name="partyBContact"/>
    </td>       

  </tr>
  
  <tr>
    <td nowrap align="left" colspan="6" class="TableHeader">合同主要内容：</td>

  </tr>   

  <tr>

    <td class="TableData" colspan="6">
    	<textarea id="content" name="content"></textarea>
    	<script>
    		editor = CKEDITOR.replace( 'content' );
    	</script>
    </td>               

  </tr>

  <tr>
    <td nowrap align="left" colspan="6" class="TableHeader">备注：</td>

  </tr>   

  <tr>

    <td class="TableData" colspan="6" id="remark">
    	<textarea id="remark" name="remark" class="BigTextarea" style="width:730px;height:100px;"></textarea>
    </td>               

  </tr>                                    

  <tr>

  	<td nowrap  class="TableHeader" colspan="6">附件文档：</td>

  </tr>

  <tr>    
    <td nowrap align="left" class="TableData" colspan="6">
    	<div id="fileContainer"></div>
    	<div id="fileContainer2"></div>
		<div id="renderContainer2"></div>
		<a id="uploadHolder2" class="add_swfupload">
			<img src="<%=systemImagePath %>/upload/batch_upload.png"/>快速上传
		</a>
		<input id="valuesHolder2" type="hidden" name="attachIds"/>
    </td>
  </tr>

  <!-- <tr align="center" class="TableControl">

    <td colspan="6">

      <input type="button" value="关闭" class="BigButton" onClick="CloseWindow();" title="关闭窗口">

    </td>

  </tr>  -->                    
<input type="hidden" value="<%=sid %>" name="sid" id="sid" />
</table>
<br/>
<table width="760" align="center">
	<tr>
		<td style="text-align:right">
			<button class="btn btn-primary" onclick="save()">保存</button>
		</td>
	</tr>
</table>
</center>
</body>
</html>