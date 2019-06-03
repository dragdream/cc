<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%
TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>索引管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/core/base/fileNetdisk/dialog/css/dialog.css"/>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/dialog/js/dialog.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/js/fileUserPriv.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/js/deptPriv.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/js/fileRolePriv.js"></script>
<script type="text/javascript">

function doInit(){
  getFileNetdiskList();
  
  var luceneSpace = getSysParamByNames("LUCENE_SPACE");
  $("#luceneSpace").val(luceneSpace[0].paraValue);
  
  var luceneKey = getSysParamByNames("LUCENE_KEY");
  $("#luceneKey").val(luceneKey[0].paraValue);
}

/* 获取网络硬盘信息 */
function getFileNetdiskList(){
  var url = "<%=contextPath %>/fileNetdisk/getFileNetdiskList.action";
  var jsonObj = tools.requestJsonRs(url);
  if(jsonObj.rtState){
    var json = jsonObj.rtData;
    jQuery.each(json,function(i,sysPara){
    	var ms="";
    	var auto = sysPara.autoIndex;
		if(auto==1){
			ms = "√";
		}else if(sysPara.autoIndex==0){
			ms = "×";
		} 
    //
      $("#tbody").append("<tr style='line-height:35px;border-bottom:1px solid #f2f2f2;'>"
					+"<td nowrap align='center' style='width:10%;text-indent:10px;'>" +sysPara.fileNo+ "</td>"
					+"<td nowrap align='center' style='width:30%;text-indent:10px;'>" + sysPara.fileName + "</td>"
					+"<td nowrap align='center' style='width:10%;text-indent:10px;'>" + ms + "</td>"
					+"<td nowrap align='center' style='width:50%;text-indent:10px;'>"
					 
					 +"<a href='javascript:void(0);'onclick='kq(\"" +sysPara.sid + "\" );'>开启</a>"
					 +"&nbsp;&nbsp;<a href='javascript:void(0);'onclick='guan(\""+sysPara.sid+"\");'>关闭</a>"
					 +"&nbsp;&nbsp;<a href='javascript:createIndex(\"" + sysPara.sid  + "\")'>创建索引</a>"
					 +"</td>"
                     
		  	+ "</tr>"); 
		 	
    });
  }
}

function saveLuceneSpace(){
	tools.requestJsonRs(contextPath+"/sysPara/updateSysPara.action",{paraName:"LUCENE_SPACE",paraValue:$("#luceneSpace").val()});
	$.MsgBox.Alert_auto("保存成功！");
}

//创建索引
function createIndex(sid){
	 $.MsgBox.Confirm ("提示", "是否确认将此文件夹下的所有文件创建索引?",function(){
					var url = contextPath
							+ "/netDiskLuceneController/createNetDiskIndex.action";
					var json = tools.requestJsonRs(url, {
						sid : sid
					});
					if (json.rtState) {
						$.MsgBox.Alert_auto(json.rtMsg);
						return;
						
					}
					$.MsgBox.Alert_auto(json.rtMsg);
			});
}


//开启
function kq(sid){
	$.MsgBox.Confirm ("提示", "是否确认将此文件夹下的所有文件创建索引?",function(){
					var url = contextPath
							+ "/fileNetdisk/updateAutoIndex.action";
					var json = tools.requestJsonRs(url,{sid:sid});
			         
					if (json.rtState) {
						window.location.reload();
						parent.$.MsgBox.Alert_auto("开启成功！");
						return;
					}else{
						parent.$.MsgBox.Alert_auto("失败！");
					return;
					}
			});
}
//关闭
function guan(sid){
	$.MsgBox.Confirm ("提示", "是否确认将此文件夹下的所有文件关闭索引?",function(){
						var url = contextPath
								+ "/fileNetdisk/updateAutoIndexgb.action";
						var json = tools.requestJsonRs(url,{sid:sid});
				
						if (json.rtState) {
							window.location.reload();
							parent.$.MsgBox.Alert_auto("关闭成功！");
							return;
						}else{
							parent.$.MsgBox.Alert_auto("失败");
							return;
						}
				});
}
</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
<div id="toolbar" class="clearfix" style="padding-top: 10px;">
		<div class="fl" style="position: static;">
			<img id="img1" class='title_img' src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/qwjszx/iocn_sygl.png">&nbsp;&nbsp;
				<span class="title">索引管理</span>
		</div>
		<div class="fr">
		   <input class="btn-win-white" type="button" value="返回" onclick="window.location='search.jsp';" />
		</div>
		<span class="basic_border" style="padding-top: 5px;"></span>
</div>
<br/>
<div class="clearfix" style="padding-top: 5px;">
	<b>相关设置</b>
	<span class="basic_border" style="padding-top: 5px;"></span>
	<table class="TableBlock_page">
		<tr>
			<td style="text-indent: 10px; width: 200px;">
				索引目录位置：
			</td>
			<td>
				<input type="text" id="luceneSpace" name="luceneSpace" class="BigInput"/>
			</td>
		</tr>
		<tr>
			<td style="text-indent: 10px;">
				API密钥：
			</td>
			<td>
				<input type="text" id="luceneKey" name="luceneKey" class="BigInput"/>
				<input style="width: 45px;height: 25px;" class="btn-win-white" onclick="saveLuceneSpace()" type="button" value="保存"/>
			</td>
		</tr>
	</table>
</div>
<br />
<div class="clearfix" style="padding-top: 5px;">
	<b>公共网盘</b>
	<span class="basic_border" style="padding-top: 5px;"></span>
	<div style="padding-top: 10px;">
     <table style="border-collapse: collapse;border: 1px solid #f2f2f2;" width="100%" align="center" >
      <tbody id="tbody">
        <tr style="line-height:35px;border-bottom:2px solid #b0deff!important;background-color: #f8f8f8; ">
      		<td style="text-indent:10px;font-weight:bold;">文件排序号</td>
     	    <td style="text-indent:10px;">文件名称</td>
     	    <td style="text-indent:10px;">状态</td>
      		<td style="text-indent:10px;">操作</td>
       </tr>
      </tbody>
   </table>
   </div>
</div>



</body>
</html>