<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<title>新建档案</title>
<script type="text/javascript">
//初始化
function doInit(){
	//初始化保管期限
	getSysCodeByParentCodeNo("DAM_RT" , "retentionPeriod");
}


//保存
function save(){
   if($("#form1").valid()){
	   var url=contextPath+"/TeeDamFilesController/addOrUpdate.action";	
	   var para =  tools.formToJson($("#form1")) ;
	   var json = tools.requestJsonRs(url,para);
	   if(json.rtState){
		   var sid=json.rtData;
		   $.MsgBox.Alert_auto("新建成功，即将跳转到整理页面！",function(){
			   //跳转到档案整理界面
			   window.location.href=contextPath+"/system/core/base/dam/preArchive/manage/edit.jsp?sid="+sid;
		   });
	   }else{
		   $.MsgBox.Alert_auto(json.rtMsg);
	   }
   }
}

//返回
function back(){
	var url=contextPath+"/system/core/base/dam/preArchive/manage/index.jsp";
	window.location.href=url;
}

</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
   <div id="toolbar" class="topbar clearfix">
       <div class="fl" style="position:static;">
		  <img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/dam/imgs/icon_xjda.png">
		  <span class="title">新建档案</span>
	    </div>
	    <div class = "right fr clearfix">
		   <input type="button" class="btn-win-white fl" onclick="save();" value="保存"/>
		   <input type="button" class="btn-win-white fl" onclick="back();" value="返回"/>
         </div>   
   </div>
<form id="form1" name="form1">
   <table class="TableBlock_page">
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">组织机构代码：</td>
         <td class="TableData">
           <input type="text"  name="orgCode" id="orgCode" class="BigInput" required="true"/>
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">全宗号：</td>
         <td class="TableData">
           <input type="text"  name="qzh" id="qzh" class="BigInput"  required="true"/>
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">件号：</td>
         <td class="TableData">
           <input type="text"  name="jh" id="jh" class="BigInput"  required="true"/>
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">年份：</td>
         <td class="TableData">
           <input type="text"  name="year" id="year" class="BigInput"  required="true"/>
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">保管期限：</td>
         <td class="TableData">
            <select id="retentionPeriod" name="retentionPeriod" class="BigSelect">
            </select>
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">文件标题：</td>
         <td class="TableData">
             <input type="text" name="title" id="title" class="BigInput" required="true"/>
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">发/来文单位：</td>
         <td class="TableData">
            <input type="text" name="unit" id="unit" class="BigInput" required="true"/>
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">文件编号：</td>
         <td class="TableData">
            <input type="text" name="number" id="number" class="BigInput" required="true"/>
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">密级：</td>
         <td class="TableData">
            <select id="mj" name="mj">
               <option value="">空</option>
               <option value="内部">内部</option>
               <option value="秘密">秘密</option>
               <option value="机密">机密</option>
               <option value="绝密">绝密</option>
            </select>
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">缓急：</td>
         <td class="TableData">
            <select id="hj" name="hj">
               <option value="">空</option>
               <option value="普通">普通</option>
               <option value="急">急</option>
               <option value="加急">加急</option>
               <option value="特急">特急</option>
               <option value="特提">特提</option>
               <option value="平急">平急</option>
            </select>
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">主题词：</td>
         <td class="TableData">
            <input type="text" name="subject" id="subject" class="BigInput" />
         </td>
      </tr>
      
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">备注：</td>
         <td class="TableData">
            <textarea rows="6" cols="50" id="remark" name="remark"></textarea>
         </td>
      </tr>
   </table>
</form>   
</body>
<script>
$("#form1").validate();
</script>
</html>