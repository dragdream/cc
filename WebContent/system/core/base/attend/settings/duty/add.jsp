<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
.setting{
   height: 18px;
   width: 18px;
   vertical-align: middle;
}

</style>
<script>
function commit(){
	if($("#form1").valid() && checkForm()){
		var param = tools.formToJson($("#form1"));
		
		//获取签到区域
		var addrJson=[];//签到区域
		var addressTr=$(".addressTr");
		
		for(var i=0;i<addressTr.length;i++){
			var desc=$(addressTr[i]).find("input:eq(0)").val();
			var position=$(addressTr[i]).find("input:eq(1)").val();
			var radius=$(addressTr[i]).next().find("input:eq(0)").val();
			
			addrJson.push({centerPositionDesc:desc,centerPosition:position,radius:radius});
			
		}
		param["addrJson"]=tools.jsonArray2String(addrJson);
		
		var url = contextPath+"/TeeAttendConfigController/addConfig.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
// 			top.$.jBox.tip(json.rtMsg,"info");
			parent.$.MsgBox.Alert_auto(json.rtMsg);
			return true;
		}
// 		top.$.jBox.tip(json.rtMsg,"error");
        parent.$.MsgBox.Alert_auto(json.rtMsg);
		return false;
	}
}
function checkForm(){
	//判断所有签到点的中心点不能为空
	var addressTr=$(".addressTr");
	for(var i=0;i<addressTr.length;i++){
		var desc=$(addressTr[i]).find("input:eq(0)").val();
		var position=$(addressTr[i]).find("input:eq(1)").val();
		
		if(position==null||position==""||position=="null"){
			$.MsgBox.Alert_auto("所有的签到区域中心点都不能为空！");
			return  false;
		}	
	}
	return true;
	
}

function doInit(){
	
}


//地图选点
function selectMapPoint(obj){
	var descId=$(obj).parent().parent().find("input:eq(0)").attr("id");
	var positionId=$(obj).parent().parent().find("input:eq(1)").attr("id");
	
	var url=contextPath+"/system/core/base/attend/settings/duty/map.jsp?descId="+descId+"&positionId="+positionId;
	dialog(url, "800px", "400px"); 
}

//清空位置
function clearAddr(obj){
	$(obj).parent().parent().find("input:eq(0)").val("");
	$(obj).parent().parent().find("input:eq(1)").val("");
}


//删除
function removeAddrTr(obj){
	  parent.$.MsgBox.Confirm ("提示", "是否确认删除该签到点？", function(){
			$(obj).parent().parent().next().remove();
			$(obj).parent().parent().remove();
	  });
}


//添加签到
var totalId = 0;
function addAddressTr(){
  var html=[];
  html.push("<tr class='TableData addressTr' >"+
			"<td>"+
			 "<img onclick=\"removeAddrTr(this);\" src=\"/system/subsys/ereport/imgs/stop.png\" class=\"setting\">"+
		    "签到区域中心点："+
		    "</td>"+
		    "<td>"+
			"<input type=\"text\"  style=\"height:21px;width: 300px;\" id='centerPositionDesc"+totalId+"'  name='centerPositionDesc"+totalId+"'   />"+
			"<input type=\"hidden\"  style=\"height:21px;\"   id='centerPosition"+totalId+"'  name='centerPosition"+totalId+"'    />"+
			"<label><a href=\"javascript:void(0)\" onclick=\"selectMapPoint(this);\">地图选点</a></label>&nbsp;&nbsp;&nbsp;"+
			"<label><a href=\"javascript:void(0)\" onclick=\"clearAddr(this);\">清空位置</a></label>"+
		    "</td>"+
	        "</tr>"+
	        "<tr class='TableData'>"+
		    "<td style=\"text-indent:18px\">"+
		    "签到区域半径："+
		    "</td>"+
		    "<td>"+
			"<input type=\"text\"  style=\"height:21px\"  id='radius"+totalId+"'  name='radius"+totalId+"'  /> （米）"+		
		    "</td>"
	        +"</tr>");
  
       $("#tb1").append(html.join(""));
      
       totalId++;
}
</script>

</head>
<body onload='doInit()' style="background:#f4f4f4;">
<form id="form1" name="form1">
	<table  style="width:100%;font-size:12px" class='TableBlock' id="tb1">
		<tr class='TableData'>
			<td style="text-indent:15px">
				排班类型描述：
			</td>
			<td>
				<input type="text"   id="dutyName" name="dutyName" required="true" class="BigInput" style="height: 21px"/>
			</td>
		</tr>
		<tr class='TableData'>
			<td style="text-indent:15px">
				第一次登记：
			</td>
			<td>
				<input type="text" id='dutyTimeDesc1' onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" name='dutyTimeDesc1' class="Wdate BigInput" />
				<select id="dutyType1" name="dutyType1" class="BigSelect">
					<option value="0" selected="selected">上班</option>
				</select>
			</td>
		</tr>
		<tr class='TableData'>
			<td style="text-indent:15px">
				第二次登记：
			</td>
			<td>
				<input type="text" id='dutyTimeDesc2' onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" name='dutyTimeDesc2' class="Wdate BigInput" />
				<select id="dutyType2" name="dutyType2" class="BigSelect">
					<option value="1" selected="selected">下班</option>
				</select>
			</td>
		</tr>
		<tr class='TableData'>
			<td style="text-indent:15px">
				第三次登记：
			</td>
			<td>
				<input type="text" id='dutyTimeDesc3' onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" name='dutyTimeDesc3' class="Wdate BigInput" />
				<select id="dutyType3" name="dutyType3" class="BigSelect">
					<option value="0" selected="selected">上班</option>
				</select>
			</td>
		</tr>
		<tr class='TableData'>
			<td style="text-indent:15px">
				第四次登记：
			</td>
			<td>
				<input type="text" id='dutyTimeDesc4' onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" name='dutyTimeDesc4' class="Wdate BigInput" />
				<select id="dutyType4" name="dutyType4" class="BigSelect">
					<option value="1" selected="selected">下班</option>
				</select>
			</td>
		</tr>
		<tr class='TableData'>
			<td style="text-indent:15px">
				第五次登记：
			</td>
			<td>
				<input type="text" id='dutyTimeDesc5' onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" name='dutyTimeDesc5' class="Wdate BigInput" />
				<select id="dutyType5" name="dutyType5" class="BigSelect">
					<option value="0" selected="selected">上班</option>
				</select>
			</td>
		</tr>
		<tr class='TableData'>
			<td style="text-indent:15px">
				第六次登记：
			</td>
			<td>
				<input type="text" id='dutyTimeDesc6' onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" name='dutyTimeDesc6' class="Wdate BigInput" />
				<select id="dutyType6" name="dutyType6" class="BigSelect">
					<option value="1" selected="selected">下班</option>
				</select>
			</td>
		</tr>
		<tr style="border-bottom-color: #8ab0e6;border-bottom-width: 2px">
		   <td colspan="2" style="padding-left: 15px;padding-right: 15px">
		      <div>
		          <span class="fl" style="font-weight: bold;">签到地点</span>  
		          <input type="button" class=" fr btn-alert-blue setHeight" value="添加"  onclick="addAddressTr();"/>              
		      </div>
		     
		   </td>
		</tr>
		<!-- <tr class='TableData'>
			<td style="text-indent:15px">
			    签到区域中心点：
			</td>
			<td>
				<input type="text" id='centerPositionDesc' style="height:21px;width: 300px"  name="centerPositionDesc" />
				<input type="hidden" id='centerPosition' style="height:21px"  name="centerPosition" />
				<label><a href="javascript:void(0)" onclick="selectMapPoint()">地图选点</a></label>&nbsp;&nbsp;&nbsp;
				<label><a href="javascript:void(0)" onclick="clearAddr()">清空位置</a></label>
			</td>
		</tr>
		<tr class='TableData'>
			<td style="text-indent:15px">
			    签到区域半径：
			</td>
			<td>
				<input type="text" id='radius' name="radius" style="height:21px"/> （米）		
			</td>
		</tr> -->
	</table>
</form>
</body>
</html>