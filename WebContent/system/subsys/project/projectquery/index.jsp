<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目查询</title>
</head>
<script>
var datagrid;
function doInit(){
	initProjectType();//初始化项目类型
	query();
	
	
}
//查询
function query(){
	var params = tools.formToJson($("#form1"));
	
	var columns =[
				/* {field:'sid',checkbox:true,title:'ID',width:100}, */
				{field:'projectName',title:'项目名称',width:120},
				{field:'projectNum',title:'项目编码',width:120},
				{field:'createrName',title:'创建人',width:100},
				{field:'managerName',title:'项目负责人',width:100},
				{field:'projectMemberNames',title:'项目成员',width:400},
				{field:'beginTime',title:'计划开始时间',width:90},
				{field:'endTime',title:'计划结束时间',width:90},
				{field:'progress',title:'进度',width:60,formatter:function(value,rowData,rowIndex){
					return rowData.progress+"%";
				}},
				{field:'status',title:'状态',width:60,formatter:function(value,rowData,rowIndex){
					var status=rowData.status;
					var desc="";
					if(status==1){
						desc="立项中";
					}else if(status==2){
						desc="审批中";
					}else if(status==3){
						desc="办理中";
					}else if(status==4){
						desc="挂起中";
					}else if(status==5){
						desc="已结束";
					}else if(status==6){
						desc="未批准";
					}
					return desc;
				}}/* ,
				{field:'opt_',title:'操作',width:100,formatter:function(value,rowData,rowIndex){			
					//判断当前登陆人是不是创建人  或者  负责人
					var isManagerOrCreater=rowData.isManagerOrCreater;
					var opt="";
					var status=rowData.status;
					if(status==1||status==2||status==6){
						opt+="<a href=\"#\" onclick=\"info('"+rowData.uuid+"')\">详情</a>";
					}else if(status==3||status==4||status==5){
						opt+="<a href=\"#\" onclick=\"detail('"+rowData.uuid+"')\">详情</a>";
					}
					
					if(isManagerOrCreater==1){//创建人  或者   负责人
						opt+="&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"del('"+rowData.uuid+"')\">删除</a>";			
					}	
					return opt;
				}} */
			]; 
	
	
	//获取项目类型的值
	var projectTypeId=$("#projectTypeId").val();
	if(projectTypeId!=0){//选择了项目类型   动态拼接自定义字段
		  var url1=contextPath+"/projectCustomFieldController/getShowFieldListByProjectTypeId.action";
	      var json1=tools.requestJsonRs(url1,{projectTypeId:projectTypeId});
	      if(json1.rtState){
	    	  var data1=json1.rtData;
	    	  if(data1.length>0){
	    		  for(var i=0;i<data1.length;i++){
	    			  columns.push({
							field:"FIELD_"+data1[i].sid,
							title:data1[i].fieldName});
	    		  }
	    	  }     	  
	      }
	}
	
	//拼接操作列
	columns.push({field:'opt_',title:'操作',width:100,formatter:function(value,rowData,rowIndex){			
		//判断当前登陆人是不是创建人  或者  负责人
		var isManagerOrCreater=rowData.isManagerOrCreater;
		var opt="";
		var status=rowData.status;
		if(status==1||status==2||status==6){
			opt+="<a href=\"#\" onclick=\"info('"+rowData.uuid+"')\">详情</a>";
		}else if(status==3||status==4||status==5){
			opt+="<a href=\"#\" onclick=\"detail('"+rowData.uuid+"')\">详情</a>";
		}
		
		if(isManagerOrCreater==1){//创建人  或者   负责人
			opt+="&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"del('"+rowData.uuid+"')\">删除</a>";			
		}	
		return opt;
	}});
	
	
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/projectController/query.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		queryParams:params,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[columns]
	});
	
	//隐藏高级查询form表单
	$(".searchCancel").click();
	/* //先移除之前渲染的自定义字段的数据
	$(".customTr").remove(); */
	
}


//初始化项目类型
function initProjectType(){
	var url=contextPath+"/projectTypeController/getTypeList.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
		for(var i=0;i<data.length;i++){
			$("#projectTypeId").append("<option value="+data[i].sid+">"+data[i].typeName+"</option>");
		}
	}
}


//简单的详情页面
function info(uuid){
	var url=contextPath+"/system/subsys/project/projectdetail/info.jsp?uuid="+uuid;
	openFullWindow(url);
}
//复杂的详情页面
function detail(uuid){
	var url=contextPath+"/system/subsys/project/projectdetail/index.jsp?uuid="+uuid;
	openFullWindow(url);
}


//删除项目
function del(uuid){
	  $.MsgBox.Confirm ("提示", "是否确认删除该项目？", function(){
		  var url=contextPath+"/projectController/delByUUid.action";
		  var json=tools.requestJsonRs(url,{uuid:uuid}); 
		  if(json.rtState){
			  $.MsgBox.Alert_auto("删除成功！");
			  datagrid.datagrid("reload");
		  }
	  });

}


//渲染自定义字段 查询字段
function renderCostomFileds(){
	var projectTypeId=$("#projectTypeId").val();
	if(projectTypeId>0){
		var url=contextPath+"/projectCustomFieldController/getQueryFieldListByProjectTypeId.action";
		var json=tools.requestJsonRs(url,{projectTypeId:projectTypeId});
		if(json.rtState){
			var data=json.rtData;
			
			//先移除之前渲染的自定义字段的数据
			$(".customTr").remove();
			
			
			var html = ["<tr class='customTr'>"];	
// 			$("#searchTable").append("<tr class='customTr'>");
			for(var i=1;i<=data.length;i++){	
				
				var name="FIELD_"+data[i-1].sid;
				if(i%3==0){
					html.push("</tr><tr class='customTr'>");
				}
				
				
				if(data[i-1].fieldType=="单行输入框"){
					html.push("<td  width=\"10%\" >"+data[i-1].fieldName+"：</td>"
							   +"<td  width=\"40%\" >"
							   +"<input type=\"text\" name='"+name+"' id='"+name+"' style=\"height: 23px;width: 350px\" />"
							   +"</td>");
				}else if(data[i-1].fieldType=="多行输入框"){
					html.push("<td  width=\"10%\" >"+data[i-1].fieldName+"：</td>"
							   +"<td  width=\"40%\">"
							   +"<input type=\"text\" name='"+name+"' id='"+name+"' style=\"height: 23px;width: 350px\" />"
							   +"</td>");
				}else if(data[i-1].fieldType=="下拉列表"){
					var fieldCtrModel=data[i-1].fieldCtrModel;
					var j=tools.strToJson(fieldCtrModel);
					if(j.codeType=="系统编码"){
						html.push("<td   width=\"10%\" >"+data[i-1].fieldName+"：</td>"
								   +"<td   width=\"40%\">"
								   +    "<select  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" >");
						
						
						html.push("<option value=''>全部</option>");
						var url1 =   contextPath + "/sysCode/getSysCodeByParentCodeNo.action";
						var para = {codeNo:j.value};
						var jsonObj = tools.requestJsonRs(url1 ,para);
						if(jsonObj.rtState){
							var prcs = jsonObj.rtData;
							for ( var n = 0; n < prcs.length; n++) {
								html.push("<option value='"+prcs[n].codeNo+"'>" + prcs[n].codeName + "</option>");
							}				
						}
						
						html.push("</select></td>");
						/* getSysCodeByParentCodeNo(j.value,name); */
					}else if(j.codeType=="自定义选项"){
						
						var values=j.value;
						var optionNames=values[0].split(",");
						var optionValues=values[1].split(",");
						html.push("<td width=\"10%\" >"+data[i-1].fieldName+"：</td>"
								   +"<td   width=\"40%\">"
								   +    "<select  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" >");
						html.push("<option value=''>全部</option>");
						for(var j=0;j<optionNames.length;j++){
							html.push("<option value="+optionValues[j]+">"+optionNames[j]+"</option>");
						
						}	
						html.push("</select></td>");	
					}	
				}
			}
			html.push("</tr>");
			$("#searchTable").append(html.join(""));
		}
	}else{
		//先移除之前渲染的自定义字段的数据
		$(".customTr").remove();
	}
}


//重置form表单
function  resetForm(){
	document.getElementById("form1").reset(); 
	$(".customTr").remove();
}
</script>
<body  onload="doInit()"  style="padding-left: 10px;padding-right: 10px">
     <div id="toolbar" class="topbar clearfix">
        <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/project/img/icon_xiangmuchaxun.png">
		<span class="title">项目查询 <span id="totalMail" class="attach"></span></span>
	</div>
        <div class="fr right" style="margin-top: 5px">
		   <button type="button" onclick="" class="advancedSearch btn-win-white">高级查询</button>
	    </div>
    </div>
    
    <form id="form1" class='ad_sea_Content'>
       <table class="TableBlock" width="100%"  style="font-size:12px" id="searchTable">
          <tr>
             <td width="10%">项目名称：</td>
             <td width="40%">
                <input type="text" name="projectName" id="projectName"/>
             </td>
             <td width="10%">项目编码：</td>
             <td width="40%">
                <input type="text" name="projectNum" id="projectNum"/>
             </td>
          </tr>
          <tr>
             <td>项目类型：</td>
             <td>
                <select id="projectTypeId" name="projectTypeId" onchange="renderCostomFileds()">
                   <option value="0">全部</option>
                </select>
             </td>
             <td>项目成员：</td>
             <td>
                <input name="userId" id="userId" type="hidden"/>
			    <input class="BigInput readonly" type="text" id="userName" name="userName"   readonly/>
			    <span class='addSpan'>
			      <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectSingleUser(['userId','userName'],'14')" value="选择"/>
				    &nbsp;&nbsp;
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('userId','userName')" value="清空"/>
			    </span>
             </td>
          </tr>
          <tr>
             <td>起始时间：</td>
             <td>
                <input type="text" id='beginTime1' name='beginTime1' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'beginTime2\')}'})" class="Wdate BigInput" style="width:120px"/>
				&nbsp;至&nbsp;
				<input type="text" id='beginTime2' name='beginTime2' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTime1\')}'})" class="Wdate BigInput" style="width:120px"/>
             </td>
             <td>结束时间：</td>
             <td>
                <input type="text" id='endTime1' name='endTime1' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime2\')}'})" class="Wdate BigInput" style="width:120px"/>
				&nbsp;至&nbsp;
				<input type="text" id='endTime2' name='endTime2' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'endTime1\')}'})" class="Wdate BigInput" style="width:120px"/>
             </td>
          </tr>
          <tr>
             <td>项目状态：</td>
             <td>
               <select id="status" name="status">
                  <option value="0">全部</option>
                  <option value="1">立项中</option>
                  <option value="2">审批中</option>
                  <option value="3">办理中</option>
                  <option value="4">挂起中</option>
                  <option value="5">已结束</option>
                  <option value="6">未批准</option>
               </select>
             </td>
             <td>项目进度：</td>
             <td>
                 <input type="text" name="minProgress" id="minProgress" style="width:80px"/>&nbsp;~&nbsp;<input type="text" name="maxProgress" id="maxProgress" style="width:80px"/>
             </td>
          </tr>
          
       </table>
       <div class='btn_search'>
		<input type='button' class='btn-win-white' value='查询' onclick="query()">&nbsp;&nbsp;&nbsp;
		<input type='button' class='btn-win-white' value='重置' onclick='resetForm()'>&nbsp;&nbsp;&nbsp;
		<input type='button' class='btn-win-white searchCancel' value='取消'>
		</div>
    </form>
    
    
    <table id="datagrid" fit="true">
    
    </table>
    <script>

		var btn_top = $(".advancedSearch").offset().top;
		var brn_height = $(".advancedSearch").outerHeight();
		$(".ad_sea_Content").css('top',(btn_top + brn_height));
		$(".advancedSearch").click(function(){
			$(".ad_sea_Content").slideToggle(200);
			if($(this).hasClass("searchOpen")){//显示前
			$(".serch_zhezhao").remove();
			$(this).removeClass("searchOpen");
			$(this).css({"border":"1px solid #0d93f6",});
			$(this).css('border-bottom','1px solid #0d93f6');
			}else{
			$(this).addClass("searchOpen");//显示时
			$(this).css({"border":"1px solid #dadada",'border-bottom':'1px solid #fff'});
			$('body').append('<div class="serch_zhezhao"></div>');
		}
		var _offsetTop = $("#form1").offset().top;
		$(".serch_zhezhao").css("top",_offsetTop)
		});
		$(".searchCancel").click(function(){
			$(".advancedSearch").removeClass("searchOpen");
		$("#form1").slideUp(200);
		$(".serch_zhezhao").remove();
		});

		</script>
</body>
</html>