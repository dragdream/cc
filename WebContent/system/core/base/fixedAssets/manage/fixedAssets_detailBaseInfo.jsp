<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
     	String sid = request.getParameter("sid");
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp"%>
<%@include file="/header/easyui2.0.jsp"%>
<%@include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/fixedAssets/js/assets.js"></script>

<script>
var sid = "<%=sid%>";
var deptName;
function doInit(){
    getAssetsInfo();
}

function getAssetsInfo(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeFixedAssetsInfoController/getAssetsInfo.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			deptName=json.rtData.deptName;
			var attaches = json.rtData.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachs").append(fileItem);
			}
			
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}



</script>

</head>
<body onload="doInit();" style="font-size:12px;padding:10px;">
<form method="post" name="form1" id="form1"  enctype="multipart/form-data">
	<table class="TableBlock_page">
		<tr>
		  <td colspan="2" style='vertical-align: middle;font-weight:bold;'><img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/gdzc/icon_mmxg.png" align="absMiddle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span>资产基本信息</span></td>
		  </td>
		</tr>
		<tr>
			<td style="text-indent: 35px;width: 250px;" class="TableData">二维码：</td>
			<td class="TableData">
			   <img id="erweima" style="display:inline-block;margin:0 auto;" src="<%=contextPath %>/TeeFixedAssetsInfoController/qrCodeDownload.action?sid=<%=sid%>"/>
			</td>
		</tr>
		<tr>
			<td style="text-indent: 35px;width: 250px;" class="TableData">资产编号：</td>
			<td class="TableData"><div id="assetCode" ></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">资产名称：</td>
			<td class="TableData"><div  id="assetName" ></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">资产类别：</td>
			<td class="TableData"><div id="typeName" ></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">增加类别：</td>
			<td  class="TableData"><div id="addKindDesc"></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">规格型号：</td>
			<td  class="TableData"><div id="assetsVersion"></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">所属部门：</td>
			<td  class="TableData"><div id="deptName"></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">制造商：</td>
			<td  class="TableData"><div id="madein"></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">保管人：</td>
			<td class="TableData" style="vertical-align: bottom;"><div id="keeperName"></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">资产性质：</td>
			<td class="TableData"><div id="assetKindDesc" ></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">经销商：</td>
			<td  class="TableData"><div id="dealer" ></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">使用年限：</td>
			<td class="TableData"  style="vertical-align: bottom;"><div id="useYears" ></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">资产原值：</td>
			<td  class="TableData"><div id="assetVal" ></div></td>
			</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">发票日期：</td>
			<td  class="TableData"><div id="receiptDateStr" ></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">残值：</td>
			<td  class="TableData"><div id="assetBal" ></div></td>
	    </tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">折旧年限：</td>
			<td  class="TableData"><div id="assetYear" ></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">残值率：</td>
			<td  class="TableData"><div id="assetBalRate" ></div></td>
		</tr>
		<tr style="display:none;text-indent: 35px;">
			<td class="TableData">折旧方式：</td>
			<td class="TableData"><div id="depreciationDesc" ></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">启用时间：</td>
			<td class="TableData"><div id="valideTimeDesc" ></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">物理位置：</td>
			<td  class="TableData"><div id="physicalLocation" ></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">资产图片：</td>
			<td class="TableData"><div id ='attachs'></div></td>
		</tr>
		<tr>
			<td style="text-indent: 35px;" class="TableData">备注：</td>
			<td class="TableData"><div id="remark" ></div></td>
		</tr>
		
	</table>
	<br>
	
</form>
</body>
</html>