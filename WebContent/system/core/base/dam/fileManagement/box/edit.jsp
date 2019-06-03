<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<title>整理档案</title>
<style>
.attTable {
	border-collapse: collapse;
    border: 1px solid #f2f2f2;
    width:100%;
}
.attTable tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
.attTable tr td:first-child{
	text-indent:10px;
}
.attTable tr:first-child td{
	font-weight:bold;
}
.attTable tr:first-child{
	border-bottom:2px solid #b0deff!important;
	background-color: #f8f8f8;
}
</style>
<script type="text/javascript">
var sid=<%=sid %>;
//初始化
function doInit(){
	//初始化保管期限
	getSysCodeByParentCodeNo("DAM_RT" , "retentionPeriod");
	if(sid>0){
		getInfoBySid();
		getAttchList();
	}
}

//获取附件列表
function getAttchList(){
	//清空除首行外的其他内容
	$("#attList  tr:not(:first)").empty("");
	var url=contextPath+"/TeeFileAttchController/getFileAttchListByFileId.action";
	var json=tools.requestJsonRs(url,{fileId:sid});
	var attList=json.rtData;
	var html="";
	if(attList==null||attList.length==0){
		html="<tr><td colspan='8' id='mess'></td></tr>";
		$("#attList").append(html);
		messageMsg("暂无附件信息","mess","info" ,"" );
	}else{
		for(var i=0;i<attList.length;i++){
			var tdId="td"+i;
			var attModel=attList[i].attModel;
			if(attModel!=null){
				attModel['priv']=3;
			}
			
			var attObj=tools.getAttachElement(attModel,{});
			html="<tr>"
			+"<td nowrap>"+attList[i].sortNo+"</td>"
			+"<td nowrap>"+attList[i].manager+"</td>"
			+"<td nowrap>"+attList[i].wjz+"</td>"
			+"<td nowrap>"+attList[i].title+"</td>"
			+"<td nowrap>"+attList[i].pageNum+"</td>"
			+"<td nowrap>"+attList[i].pubTimeStr+"</td>"
			+"<td nowrap>"+attList[i].remark+"</td>"
			+"<td nowrap id="+tdId+"></td>"
			+"<td nowrap><a href='#' onclick='eidtFileAtt("+attList[i].sid+")'>编辑</a>&nbsp;&nbsp;&nbsp;<a href='#' onclick='delFileAtt("+attList[i].sid+")'>删除</a></td>";
			$("#attList").append(html);
			$("#"+tdId).append(attObj);
		}
		
	}
}

//编辑档案附件
function eidtFileAtt(sid1){
	var url=contextPath+"/system/core/base/dam/preArchive/manage/addOrUpdateAttach.jsp?fileId="+sid+"&sid="+sid1;
	bsWindow(url ,"编辑档案附件",{width:"600",height:"360",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.commit();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("编辑成功！",function(){	
		    		//刷新附件列表
		    		getAttchList();
		    	});
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto("编辑失败！");
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

//删除档案附件信息
function delFileAtt(sid1){
	
	$.MsgBox.Confirm ("提示", "是否确认删除该档案附件信息？", function(){
		var url=contextPath+"/TeeFileAttchController/delBySid.action";
		var json=tools.requestJsonRs(url,{sid:sid1});
		if(json.rtState){
			$.MsgBox.Alert_auto("档案信息删除成功！",function(){
				//刷新附件列表
	    		getAttchList();
			});
		}else{
			$.MsgBox.Alert_auto("档案信息删除失败！");
		}
	});
	
		
}



//根据主键获取详情
function getInfoBySid(){
	var url=contextPath+"/TeeDamFilesController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var prc = json.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
		}
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
	
	
}

//保存
function save(){
   if($("#form1").valid()){
	   var url=contextPath+"/TeeDamFilesController/addOrUpdate.action";	
	   var para =  tools.formToJson($("#form1")) ;
	   var json = tools.requestJsonRs(url,para);
	   if(json.rtState){
		   $.MsgBox.Alert_auto("保存成功！",function(){ 
			  history.go(-1);   
		   });
	   }else{
		   $.MsgBox.Alert_auto(json.rtMsg);
	   }
   }
}




//添加附件
function addAttach(){
	var url=contextPath+"/system/core/base/dam/preArchive/manage/addOrUpdateAttach.jsp?fileId="+sid;
	bsWindow(url ,"添加档案附件",{width:"600",height:"360",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.commit();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("添加成功！",function(){	
		    		//刷新附件列表
		    		getAttchList();
		    	});
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto("添加失败！");
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
	
}



</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
   <div id="toolbar" class="topbar clearfix">
       <div class="fl" style="position:static;">
		  <img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/dam/imgs/icon_dawjgl.png">
		  <span class="title">档案整理</span>
	    </div>
	    <div class = "right fr clearfix">
		   <input type="button" class="btn-win-white fl" onclick="save();" value="保存"/>
		    <input type="button" class="btn-win-white fl" onclick="history.go(-1);" value="返回"/>
         </div>   
   </div>
<form id="form1" name="form1">
   <table class="TableBlock_page">
   <input type="hidden" name="sid" id="sid" value="<%=sid %>"/>
      <tr>
        <td class="TableHeader" colspan="2" nowrap="">
		   <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		    <b style="color: #0050aa">基础信息</b>
		</td>
      </tr>
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
      
      <tr>
        <td class="TableHeader" colspan="2" nowrap="">
		   <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		    <b style="color: #0050aa">附件信息</b>
		    <div style="float: right">
		       <input type="button" value="添加附件" class="btn-win-white" onclick="addAttach();"/>
		    </div>
		    
		</td>
      </tr>
      <tr>
         <td colspan="2">
             <table class="attTable" id="attList">
                 <tr class='TableHeader'>
                    <td nowrap width="5%">顺序号</td>
                    <td nowrap>责任者</td>
                    <td nowrap>文件字</td>
                    <td nowrap>标题</td>
                    <td nowrap width="5%">页数</td>
                    <td nowrap width="15%">生成日期</td>
                    <td nowrap width="15%">备注</td>
                    <td nowrap width="20%">文件</td>
                    <td nowrap>操作</td>
                 </tr>
                
             </table>
         </td>
      </tr>
   </table>
</form>   
</body>
<script>
$("#form1").validate();
</script>
