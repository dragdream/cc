<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String personUuid = request.getParameter("personUuid") == null ? "" : request.getParameter("personUuid");//人员UUID
	String personName = request.getParameter("personName") == null ? "" : request.getParameter("personName");
	String moduleId = request.getParameter("moduleId") == null ? "1" :  request.getParameter("moduleId"); 
    String moduleName = TeeStringUtil.getString(request.getParameter("moduleName"), "在线人员");
    
    String modelDesc = TeeStringUtil.getString(request.getParameter("modelDesc"), personName + "可以看到所选范围的所有在线人员，为空则不限制");
    

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/system/core/person/js/person.js"></script>

<script type="text/javascript">
  var userName = '<%=personName%>';
 
  var modelId = parseInt('<%=moduleId%>',10);
  var moduleName = '<%=moduleName%>';
  var modelDesc = '<%=modelDesc%>';
  var personId = '<%=personUuid%>';
  var moduleArray = [['1','在线人员',userName+'可以看到所选范围的所有在线人员，为空则不限制'],
                     ['2','全部人员',userName+'可以看到所选范围的所有人员，为空则不限制'],
                     ['3','日程安排查询',userName+'可以看到所选范围内人员的日程安排，为空则只能看到自己管理范围内的比自己角色低的用户的日程安排'],
                     ['4','工作日志查询',userName+'可以看到所选范围内人员的工作日志，为空则只能看到自己管理范围内的比自己角色低的用户的工作日志'],
                     ['5','公告通知发布',userName+'可以向所选范围内的用户发布公告，为空则不限制'],
                     ['6','新闻发布',userName+'可以向所选范围内的用户发布新闻，为空则不限制'],
                     ['7','投票发布',userName+'可以向所选范围内的用户发布投票，为空则不限制'],
                     ['','管理简报',userName+'可以统计所选范围内的用户的日志数量等工作情况'],
                     ['8','人事档案管理',userName+'可以管理所选范围内的用户的人事档案信息'],
                     ['9','人事档案查询',userName+'可以查询统计所选范围内的用户的人事档案信息'],
                     ['10','邮件发送范围',userName+'可以向所选范围内的用户发送邮件，为空则不限制'],
                     ['11','短信发送范围',userName+'可以向所选范围内的用户发送短信，为空则不限制']
                    /*  ['13','人力资源统计范围',userName+'可以统计所选范围内的部门人力资源情况，为空则不限制'],
                     ['14','薪酬统计范围',userName+'可以统计所选范围内的部门的薪酬情况，为空则不限制'] */
                     ];

  function doOnInit() {
       var selectOtherAll = "";
      /*  var count = 0;
	   for(var i=0;i < moduleArray.length;i++) {
	         if(modelId==moduleArray[i][0]) {
	        	 temp = '<input type="checkbox" id="APPLY_TO_MODULE_'+moduleArray[i][0]+'" value="'+moduleArray[i][0]+'" onclick="AddModule(this.id);"/><label for="APPLY_TO_MODULE_'
		            +moduleArray[i][0]+'">'+moduleArray[i][1]+'</label>';
		         count ++;
		         if(count%3==0) {
		           temp += '<br/>';
		         }
		         selectOtherAll +=temp;
	        	 continue;
			 }
	        
	     } */
       	  document.getElementById("tableHeader").innerHTML = userName+' - '+moduleName;
       	  document.getElementById("moduleDiscripe").innerHTML =moduleName + modelDesc;
       	  document.getElementById("selectOther").innerHTML = selectOtherAll;

       	  var url = contextPath + "/modulePrivManager/getByUserId.action?personId="+personId+"&model=" + modelId ;
      	  var jsonObj = tools.requestJsonRs(url);
		  if(jsonObj.rtState){ 
			 var privData = jsonObj.rtData;
			 if(privData.sid > 0){
				 bindJsonObj2Cntrl(privData);
				 var deptPriv = privData.deptPriv;
				 var rolePriv = privData.rolePriv;
				 if(deptPriv=='2'){
					 document.getElementById("dept_tr").style.display = '';
				}
			     if(deptPriv == '3'){
			    	 document.getElementById("user_tr").style.display = '';
				 }
			     if(rolePriv == '3'){
			    	 document.getElementById("priv_tr").style.display = '';
				 } 
			 }
			 
		 }
   }
  function addOrUpdate(){
	  if(checkForm()){
		  var url = contextPath + "/modulePrivManager/addOrUpdate.action" ;
		  var para =  tools.formToJson($("#form1")) ;
	  	  var jsonObj = tools.requestJsonRs(url,para);
		  if(jsonObj.rtState){
			  var data = jsonObj.rtData;
// 			  $("#sid").val(data);
              //top.$.jBox.tip('保存成功！','info',{timeout:1500});
              parent.$.MsgBox.Alert_auto("保存成功！",function(){
            	  $(parent.parent.document.getElementsByClassName("bsWindow")).remove();
              });
             
             
			 /*  $.messager.show({
					msg : '保存成功！',
					title : '提示'
				}); */
		  }else{
			  parent.$.MsgBox.Alert_auto(jsonObj.setMsg()); 
		  } 
	  }
	 
  }
  /***
  *校验表单
  */
  function checkForm(){
	  var dept_priv = $("#deptPriv").val();//获取到DEPT_PRIV信息 
	  var role_priv = $("#rolePriv").val();//获取到ROLE_PRIV信息 
	  var dept_id = $("#deptIdStr").val();//获取到 DEPT_ID 信息 
	  var user_id = $("#userIdStr").val();//获取到USER_ID信息 
	  var priv_id = $("#roleIdStr").val();//获取到PRIV_ID信息 
	 /*  var apply_to_dept = document.form1.apply_to_dept.value;//获取到APPLY_TO_DEPT信息 
	  var apply_to_priv = document.form1.apply_to_priv.value;//获取到APPLY_TO_PRIV信息  */
	  if(dept_priv=="" &&  role_priv == "")//都为空时设置管理范围默认权限
	   {
		  $.MsgBox.Confirm ("提示", "您未指定人员范围，将恢复人员管理范围默认设置。",function(){
	      //msg='您未指定人员范围，将恢复人员管理范围默认设置。';
	     // if(window.confirm(msg)){
	    	  role_priv="";
	     });
	    /*   }else{
	    	  return false;
	      } */
	   }
	  if(dept_priv != ''){
		  if(dept_priv=="2" && dept_id=="")//指定部门不能为空
		   {
			  $.MsgBox.Alert_auto("请选择指定部门");
		      return false;
		   }
		   if(dept_priv=="3" && user_id=="")//指定人员不能为空
		   {
			   $.MsgBox.Alert_auto("请选择指定人员");
		      return false;
		   }
		   
		  if(role_priv == ""){
			  $.MsgBox.Alert_auto("请选择指定角色");
		      return false;
		  }
	  }
	   if(role_priv!="")//权限不为空
	   {
		   if(role_priv == "3" && priv_id=="")//指定角色不能为空
		   {
			   $.MsgBox.Alert_auto("请选择指定角色");
		      return false;
		   }
		   
		   if(dept_priv == ""){
			   $.MsgBox.Alert_auto("请选部门");
			      return false;
			}
	   }
	   return true;
	
	  /*  if(apply_to_dept=="" && apply_to_priv!="")
	   {
	      alert("请选择应用到的部门");
	      return false;
	   }
	   if(apply_to_dept!="" && apply_to_priv=="")
	   {
	      alert("请选择应用到的角色");
	      return false;
	   } */
	}

  function AddModule(id)
  {
     var obj = document.getElementById(id);
     var apply_to_str = document.form1.apply_to_module.value;
     if(obj.checked)
     {
        apply_to_str+=obj.value+",";
     }
     else
     {
        if(apply_to_str.indexOf(obj.value+",")==0)
           apply_to_str=apply_to_str.replace(obj.value+",","");
        else if(apply_to_str.indexOf(","+obj.value+",")>0)
           apply_to_str=apply_to_str.replace(","+obj.value+",",",");
     }
 
     document.form1.apply_to_module.value = apply_to_str;
  }
   
  function show_obj(obj) {
    if(document.getElementById(obj).style.display == "none"){
      document.getElementById(obj).style.display="";
    }else{
      document.getElementById(obj).style.display="none";
    }
    
  }

  function select_dept(obj)  //指定部门和指定人员 
  {
     var dept_i=document.getElementById("dept_tr");
     var user_i=document.getElementById("user_tr");
     if (obj.value=="2")
     {
         dept_i.style.display='';
         user_i.style.display='none';
     }
     else if (obj.value=="3")
     {
         dept_i.style.display="none";
         user_i.style.display='';
     }
     else
     {
         dept_i.style.display="none";
         user_i.style.display='none';
     }
  }

  function select_priv(obj) //指定人员的角色 
  {
     var priv_i=document.getElementById("priv_tr");
     if(obj.value=="3")
         priv_i.style.display="";
     else
         priv_i.style.display="none";
  }

  function addDept() {
	 	var URL="<%=contextPath %>/core/funcs/orgselect/MultiDeptSelect1.jsp";
	  	openDialog(URL,'520', '400');
	}

  function ClearUser(TO_ID, TO_NAME){
	  if(TO_ID=="" || TO_ID=="undefined" || TO_ID== null){
		TO_ID="TO_ID";
		TO_NAME="TO_NAME";
	  }
	  document.getElementsByName(TO_ID)[0].value="";
	  document.getElementsByName(TO_NAME)[0].value="";
  }

  function SelectUser(TO_ID, TO_NAME){ //指定角色 
	  URL=contextPath + "/core/funcs/dept/userselect.jsp?&TO_ID="+TO_ID+"&TO_NAME="+TO_NAME;
	  openDialog(URL,'400', '350');
  }

  function addPrivtemp(){
     
  }
  function closeBS(){
		$(parent.parent.document.getElementsByClassName("bsWindow")).remove();
	}

  

	


</script>
<style>
	html{
		overflow:hidden;
	}
</style>
</head>
<body onload="doOnInit()" style="padding-left: 10px;padding-right: 10px;">

<form action=""  method="post" name="form1" id="form1" onSubmit="">
  <table class="TableBlock_page" width="90%" align="center" style="margin-top:8px;">
   <tr>
    <td class="TableData" colspan="2" style="font-size: 14px;text-align: center;line-height: 25px;"><div id="tableHeader"></div></td>
   </tr>
   <tr>
    <td class="TableData" width="100">人员范围：</td>
    <td class="TableData">
      <select style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" name="deptPriv" id="deptPriv" class="BigSelect" OnChange="select_dept(this)">
          <option value=""></option>
          <option value="0">本部门</option>
          <option value="1">全体</option>
          <option value="2">指定部门</option>
          <option value="3">指定人员</option>
      </select>
    </td>
   </tr>
   <tr id="dept_tr" style="display:none">
    <td class="TableData" width="100">指定部门：</td>
    <td class="TableData">
        <input type="hidden" name="deptIdStr" id="deptIdStr" value="">
        <textarea cols=28 name="deptIdsName" id="deptIdsName" rows=3 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="javascript:selectDept(['deptIdStr','deptIdsName'],'','');">添加</a>
        <a href="javascript:void(0);" class="orgClear" onClick="javascript:clearData('deptIdStr', 'deptIdsName');">清空</a>
    </td>
   </tr>
   <tr id="user_tr" style="display:none">
    <td class="TableData" width="100">指定人员：</td>
    <td class="TableData">
        <input type="hidden" name="userIdStr" id="userIdStr" value="">
        <textarea cols=28 name="userIdsName" id="userIdsName" rows=3 class="BigStatic BigTextarea"  wrap="yes" readonly></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="javascript:selectUser(['userIdStr', 'userIdsName']);">添加</a>
        <a href="javascript:void(0);" class="orgClear" onClick="javascript:clearData('userIdStr', 'userIdsName');">清空</a>
    </td>
   </tr>
   <tr>
    <td class="TableData" width="100">人员角色：</td>
    <td class="TableData">
      <select style="width:300px; height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" name="rolePriv" id="rolePriv" class="BigSelect" OnChange="select_priv(this)">
          <option value=""></option>
    <%if ( !"5".equals(moduleId) && !"6".equals(moduleId) ) {%> 
          <option value="0">低角色的用户</option>
          <option value="1">同角色和低角色的用户</option>
           <% } %> 
          <option value="2">所有角色的用户</option>
          <%if ( !"5".equals(moduleId) && !"6".equals(moduleId) ) {%> 
          <option value="3">指定角色的用户</option>
           <% } %> 
      </select>
    </td>
   </tr>
   <tr id="priv_tr" style="display:none">
    <td class="TableData" width="100">指定角色：</td>
    <td class="TableData">
        <input type="hidden" id="roleIdStr" name="roleIdStr" value="">
        <textarea cols=28 id="roleIdsName" name="roleIdsName" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="javascript:selectRole(['roleIdStr','roleIdsName'],'','','1');">添加</a>
        <a href="javascript:void(0);" class="orgClear" onClick="javascript:clearData('roleIdStr', 'roleIdsName');">清空</a>
    </td>
   </tr>
   <tr>
    <td class="TableData" width="100">说明：</td>
    <td class="TableData"><div id="moduleDiscripe"></div></td>
   </tr>
   
   <tr>
      <td nowrap class="TableData">批量设置到其他人员：</td>
      <td class="TableData">
        <input type="hidden" name="userIds1" id="userIds1" value="">
        <textarea style="width: 300px;font-family: MicroSoft YaHei;font-size: 12px;" cols=30 name="userNameStr" id="userNameStr" rows=4 class="BigTextarea" wrap="yes" readonly></textarea>
        <span class='addSpan'>
		         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectUser(['userIds1', 'userNameStr'])" value="选择"/>
			        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('userIds1','userNameStr');" value="清空"/>
		 </span>
      </td>
   </tr>
   
   
   
   
   <tr onClick="show_obj('APPLAY_TBODY');" style="cursor:pointer;display:none;" title="点击选择应用到的模块和人员范围">
    <td class="TableData" colspan="2">以上设置应用到其它模块、其他用户 &gt;&gt;</td>
   </tr>
   <tbody id="APPLAY_TBODY" style="display:none;">
   <tr>
    <td class="TableData" width="100">应用到其它模块：</td>
    <td class="TableData">
    <div id="selectOther"></div>
    </td>
   </tr>
   <tr>
    <td class="TableData" colspan="2">应用到其他用户（必须同时满足以下的部门、角色限制）</td>
   </tr>
   <tr>
    <td class="TableData" width="100">所在部门：</td>
    <td class="TableData">
        <input type="hidden" name="apply_to_dept" id="apply_to_dept" value="">
        <textarea cols=22 name="apply_to_dept_name" id="apply_to_dept_name" rows=3 class="BigStatic" wrap="yes" readonly></textarea>
        <span class='addSpan'>
		         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="javascript:selectDept(['apply_to_dept','apply_to_dept_name']);" value="选择"/>
			        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="javascript:ClearUser('apply_to_dept', 'apply_to_dept_name');" value="清空"/>
		 </span>
    </td>
   </tr>
   <tr>
    <td class="TableData" width="100">所属角色：</td>
    <td class="TableData">
        <input type="hidden" name="apply_to_priv" id="privApply" value="">
        <textarea cols=28 name="apply_to_priv_name" id="privApplyDesc" rows=3 class="BigStatic" wrap="yes" readonly></textarea>
         <span class='addSpan'>
		         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="javascript:selectRolePriv(['privApply','privApplyDesc']);" value="选择"/>
			        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="javascript:ClearUser('apply_to_priv', 'apply_to_priv_name');" value="清空"/>
		 </span>
        
    </td>
   </tr>
   </tbody>
   <tr>
    <td nowrap style="text-align: center;" colspan="3" align="center">
        <input type="hidden" name="personId" id="personId" value="<%=personUuid%>">
        <input type="hidden" value="0" name="sid" id="sid">
        <input type="hidden" value="<%=moduleId%>" name="moduleId" id="moduleId">
         <input style="width: 45px;height: 25px;" type="button" value="保存" class="btn-win-white" name="button" onclick="addOrUpdate();">&nbsp;&nbsp;
        <input style="width: 45px;height: 25px;" type="button" value="关闭" class="btn-win-white" onClick="closeBS();">  
    </td>
  </tr>
</table>
 </form>
</body>
</html>
