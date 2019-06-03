<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<title>新建/编辑固定排班</title>
<style>
.TableData td{
   height: 23px
}
.TableHeader td{
    height: 23px
}
</style>
<script type="text/javascript">
var sid=<%=sid  %>;//固定排班主键
function doInit(){
	if(sid>0){
		getInfoBySid();
	}
	
}

//根据主键获取详情
function getInfoBySid(){
	var url=contextPath+"/attendConfigRulesController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
		
		var configModelStr=tools.strToJson(data.configModelStr);
		//alert(data.configModelStr);
		for(var i=0;i<configModelStr.length;i++){
			 var trId="";
			 for(var key in configModelStr[i]){  
				  if(key.indexOf("week")!=-1){
	                	trId=key;
	               } 
	          } 

			 $("#"+trId).find("input:eq(0)").val(configModelStr[i][trId]);
			 $("#"+trId).find("td:eq(1)").html(configModelStr[i]["attendConfigTime1"]);
			 $("#"+trId).find("td:eq(2)").html(configModelStr[i]["attendConfigTime2"]); 
			 $("#"+trId).find("td:eq(3)").html(configModelStr[i]["attendConfigTime3"]);
			 
		}
		
	}else{
		$.MsgBox.Alert_auto("数据信息获取失败！");
	}
	
}



//保存
function doSave(){
	if(checkForm()){
		var param=tools.formToJson($("#form1"));
		
		//拼接model
		var modelTrs=$(".model");
		var configModel={};
		if(modelTrs!=null&&modelTrs.length>0){
			for(var i=0;i<modelTrs.length;i++){
				configModel[$(modelTrs[i]).attr("id")]=$(modelTrs[i]).find("input:eq(0)").val();
			}
		}
		
		param["configModel"]=tools.jsonObj2String(configModel);

		var url=contextPath+"/attendConfigRulesController/addOrUpdate.action";
		var json=tools.requestJsonRs(url,param);
		if(json.rtState){
			$.MsgBox.Alert_auto("保存成功！",function(){
				window.opener.location.reload();
				window.close();
			});
			
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}




//验证
function checkForm(){
	if($("#form1").valid()){
		//获取部门id
		var deptNames=$("#deptNames").val();
		var userNames=$("#userNames").val();
		var roleNames=$("#roleNames").val();
		if(deptNames==""&&userNames==""&&roleNames==""){
			$.MsgBox.Alert_auto("人员、部门、角色范围至少设置一项！");
			return false;
		}
		return true;
	}
}


//关闭
function closeWin(){
	window.close();
}


//设置排班
function setting(obj){
	var trId=$(obj).parent().parent().attr("id");
	var weekday=$(obj).parent().parent().find("td:eq(0)").text();
	var attendConfig=$(obj).parent().parent().find("input:eq(0)").val();
	var url=contextPath+"/system/core/base/attend/manager/setDutyConfig.jsp?weekday="+weekday+"&&attendConfig="+attendConfig+"&&trId="+trId;
	bsWindow(url ,"设置排班",{width:"550",height:"120",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		     cw.doSave();
		     return true;
		}else if(v=="关闭"){
			return true;
		}
	}}); 

}


//删除排班
function del(obj){
	 $.MsgBox.Confirm ("提示", "是否确认删除该排班类型？", function(){
		 $(obj).parent().parent().find("input:eq(0)").val("0");
			$(obj).parent().parent().find("td:eq(1)").html("");
			$(obj).parent().parent().find("td:eq(2)").html("");
			$(obj).parent().parent().find("td:eq(3)").html("");
	  });
	
}
</script>

</head>
<body style="padding-left: 10px;padding-right: 10px" onload="doInit();">
   <div class="topbar clearfix">
      <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_shoujianxiang.png">
		<span class="title">新建/编辑固定排班</span>
	  </div>
	  <div class = "right fr clearfix">
		<input type="button" class="btn-win-white fl"  value="保存" onclick="doSave();"/>
		<input type="button" class="btn-del-red fl"  value="关闭" onclick="closeWin();"/>
      </div>
   </div>
   <form  id="form1">
       <input type="hidden" name="sid" id="sid" value="<%=sid %>"/>
      <table class="TableBlock_page">
          <tr>
             <td>排班名称：</td>
             <td>
                <input type="text" name="configName" id="configName"  required/>
             </td>
          </tr>
         <tr>
             <td>设置范围（人员）：</td>
             <td>
                <input type="hidden" name="userIds" id="userIds" />
                <textarea rows="6" cols="80" id="userNames" name="userNames"  style="width: 600px" readonly="readonly"></textarea>
                <span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['userIds','userNames'],'14')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('userIds','userNames')" value="清空"/>
				</span>
             </td>
          </tr>
         <tr>
             <td>设置范围（部门）：</td>
             <td>
                <input type="hidden" name="deptIds" id="deptIds" />
                <textarea rows="6" cols="80" id="deptNames" name="deptNames" style="width: 600px" readonly="readonly"></textarea>
                <span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectDept(['deptIds','deptNames'],'14')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('deptIds','deptNames')" value="清空"/>
				</span>
             </td>
          </tr>
          <tr>
             <td>设置范围（角色）：</td>
             <td>
                <input type="hidden" name="roleIds" id="roleIds" />
                <textarea rows="6" cols="80" id="roleNames" name="roleNames" style="width: 600px" readonly="readonly"></textarea>
                <span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectRole(['roleIds','roleNames'],'14')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('roleIds','roleNames')" value="清空"/>
				</span>
             </td>
          </tr>
           <tr>
             <td>考勤类型：</td>
             <td>
                 <table class="TableBlock_page" style="border:#dddddd 2px solid;margin-top:3px;margin-left:5px;width:90%">
                    <tr class="TableHeader" style="background-color:#e8ecf9">
                       <td style="text-indent: 10px">考勤周期</td>
                       <td>第一次签到-第一次签退</td>
                       <td>第二次签到-第二次签退</td>
                       <td>第三次签到-第三次签退</td>
                       <td>操作</td>
                    </tr>
                    <tr class="TableData  model" id="week1">
                       <td style="text-indent: 10px">星期一<input type="hidden" value="0" /></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td><a href="#" onclick="setting(this);">设置</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#"  onclick="del(this)">删除</a></td>
                    </tr>
                    <tr class="TableData model" id="week2">
                       <td style="text-indent: 10px" >星期二<input type="hidden" value="0" /></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td><a href="#" onclick="setting(this);">设置</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="del(this)">删除</a></td>
                    </tr>
                    <tr class="TableData model" id="week3">
                       <td style="text-indent: 10px">星期三<input type="hidden" value="0" /></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td><a href="#" onclick="setting(this);">设置</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="del(this)">删除</a></td>
                    </tr>
                    <tr class="TableData model" id="week4">
                       <td style="text-indent: 10px">星期四<input type="hidden" value="0" /></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td><a href="#" onclick="setting(this);">设置</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="del(this)">删除</a></td>
                    </tr>
                    <tr class="TableData model" id="week5">
                       <td style="text-indent: 10px">星期五<input type="hidden" value="0" /></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td><a href="#" onclick="setting(this);">设置</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="del(this)">删除</a></td>
                    </tr>
                    <tr class="TableData model" id="week6">
                       <td style="text-indent: 10px">星期六<input type="hidden" value="0" /></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td><a href="#" onclick="setting(this);">设置</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="del(this)">删除</a></td>
                    </tr>
                    <tr class="TableData model" id="week7">
                       <td style="text-indent: 10px">星期日<input type="hidden" value="0" /></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td><a href="#" onclick="setting(this);">设置</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="del(this)">删除</a></td>
                    </tr>  
                 </table>
             </td>
          </tr>
      </table>
   </form>
<script type="text/javascript">
  $("#form1").validate();
</script>
</body>
</html>