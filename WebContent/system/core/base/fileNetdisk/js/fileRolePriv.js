//存储角色Id、权限sid 
var roleObjArray = new Array();

/**
 * 角色权限初始化
 */
function doInitRolePrivFunc(sid){
  /* 清空存储人员id容器 */
  roleObjArray.length = 0;
  /* 创建角色权限table表头 */
  createRolePrivTable(sid);
  /* 初始化角色权限 */
  getRoletPrivFunc(sid);
  
}


/* 创建角色权限表头 */
function createRolePrivTable(fileId){
  $("#roleBodyDiv").html("");
  var table = "<table id='rolePrivTab' class='TableList' width='100%' align='center'>" 
      + "<tr class='TableHeader' align='center'>"
      + "<td style='display:none;'></td>"
      + "<td nowrap style='text-indent:10px' >编号</td>"
      + "<td nowrap  >角色名称 </td>"
      + "<td nowrap  ><input type='checkbox' id='roleShowPriv'  onclick='getRoleSelectFunc(this,0)' ><label for='roleShowPriv'>浏览权限</label>  </td>"
      + "<td nowrap  ><input type='checkbox' id='roleEditPriv' onclick='getRoleSelectFunc(this,1)' ><label for='roleEditPriv'>下载权限</label> </td>"
      + "<td nowrap  ><input type='checkbox' id='roleDownPriv' onclick='getRoleSelectFunc(this,2)' ><label for='roleDownPriv'>删除权限</label> </td>"
      + "<td nowrap  ><input type='checkbox' id='roleUploadPriv' onclick='getRoleSelectFunc(this,3)' ><label for='roleUploadPriv'>编辑权限</label> </td>"
      + "<td nowrap  ><input type='checkbox' id='roleDeletePriv' onclick='getRoleSelectFunc(this,4)' ><label for='roleDeletePriv'>上传权限 </label> </td>"
      + "<td nowrap  ><input type='checkbox' id='roleManagePriv' onclick='getRoleSelectFunc(this,5)' ><label for='roleManagePriv'>管理权限 </label> </td>"
      + "<td nowrap  >操作 </td>"
      + "</tr></table>";
  $("#roleBodyDiv").append(table);
}


/**
 * 人员权限全选
 * @param obj
 * @param k
 */
function getRoleSelectFunc(obj,k){
  if(obj.checked){
    $("input[name^=roleCheckbox_" + k +"]").each(function(i,obj){
      if(!obj.checked){
        obj.checked = true;
      }
    });
  }else{
    $("input[name^=roleCheckbox_" + k +"]").each(function(i,obj){
      if(obj.checked){
        obj.checked = false;
      }
    });
  }
}



/**
 * 添加角色
 */
function showRolePriv(){
  var html = "<div style='padding:10px;' style='background-color:#f2f2f2'>" 
            +   "<table class=''>"
            +     "<tr class='TableData'>"
            +       "<td width='70px;'>角色设置：</td>"
            +       "<td width='' class='TableData'>"
            +         "<input type='hidden' id='roleIdStr' name='roleIdStr' />"
            +         "<textarea cols='35' name='roleIdStrName' id='roleIdStrName' rows='4' style='overflow-y: auto;' class='SmallStatic BigTextarea' wrap='yes' readonly/>"
            +         "<span class='addSpan'>"
            +          "<img src='/common/zt_webframe/imgs/zsjl/ggwp/icon_select.png' onClick=\"selectRole(['roleIdStr','roleIdStrName'],'1')\"/>"
            +          "&nbsp;&nbsp;<img src='/common/zt_webframe/imgs/zsjl/ggwp/icon_cancel.png' onClick=\"clearData('roleIdStr','roleIdStrName')\"/>"
            +         "</span>"
            +       "</td>"
            +      "</tr>"
            +  "</table>"
            +"</div>";
  
  window.parent.bsWindow(html ,"角色权限",{width:"500",height:"100",
		 buttons:[{name:"保存",classStyle:"btn-alert-blue"},
		 	      {name:"关闭",classStyle:"btn-alert-gray"}]
		,submit:function(v,h,f){
    var  rIds=f.find("#roleIdStr").val();
    
    if(v=="保存"){
    	 if(!rIds){
    	      $.MsgBox.Alert_auto("请选择角色！");
    	      return;
    	    }
    	    
    	    //return ;
    	    //点击保存选择的角色
    	    var selecteRoleArray = new Array();
    	    var roleIdArray = rIds.split(",");
    	    var roleNameArray = f.find("#roleIdStrName").val().split(",");
    	    if(roleIdArray.length>0){
    	      for(var i=0;i<roleIdArray.length;i++){
    	        if(roleIdArray[i]){
    	          var roleIdStr = roleIdArray[i];
    	          var roleIdName = roleNameArray[i];
    	          selecteRoleArray.push({privSid:'',roleIdStr:roleIdStr,roleIdStrName:roleIdName,privValues:''});
    	        }
    	      }
    	    }
    	    getSelecteRoleArray(selecteRoleArray);
    	      selecteRoleArray.length = 0;
    	      return true;
    }
    if(v=="关闭"){
    	return true;
      }
	}},"html");
   
}


/* 显示角色权限，并默认选中值 */
function getSelecteRoleArray(selecteRoleArray){
  var privTableObj = document.getElementById("rolePrivTab");
  if(privTableObj){
    var trCount = privTableObj.rows.length-1;
    //alert(privTableObj.rows.length);
    for(var i=0;i<selecteRoleArray.length;i++){
      var roleIdStr = selecteRoleArray[i].roleIdStr;//id
      var roleNameStr = selecteRoleArray[i].roleIdStrName;//名称
      var privValues = selecteRoleArray[i].privValues;//权限值
      var privSid = selecteRoleArray[i].privSid;//权限sid
      //alert(roleIdStr + " >>" + deptNameStr + " >>" + privValues);
      if(roleIdStr && roleNameStr){
        if(!isContainRole (roleObjArray,roleIdStr)){
          trCount++;
          var curTr = "<tr>"
            //+   "<td><input type='text' id='' name='' value='' > </td>"
            +   "<td>" + trCount + "</td>"
            +   "<td>"+ roleNameStr + "</td>"
            +   "<td><input type='checkbox'  name='roleCheckbox_0_" + roleIdStr + "' value='1'></td>"
            +   "<td><input type='checkbox'  name='roleCheckbox_1_" + roleIdStr + "' value='2'></td>"
            +   "<td><input type='checkbox'  name='roleCheckbox_2_" + roleIdStr + "' value='4'></td>"
            +   "<td><input type='checkbox'  name='roleCheckbox_3_" + roleIdStr + "' value='8'></td>"
            +   "<td><input type='checkbox'  name='roleCheckbox_4_" + roleIdStr + "' value='32'></td>"
            +   "<td><input type='checkbox'  name='roleCheckbox_5_" + roleIdStr + "' value='64'></td>"
            +   "<td><a href='javascript:void(0);' onclick=\"deleteRoleFunc(this,'" + privSid + "','" + roleIdStr + "');\">删除</a></td>"
            + "</tr>";
          $("#rolePrivTab").append(curTr);
          roleObjArray.push({roleId:roleIdStr,privSid:privSid});
          checkedRoleValueFunc(roleIdStr,privValues);//获取权限时，有权限则默认选择
        }
      }
    }
  }
}


/**
 * 删除网盘角色权限信息
 * @param sid
 */
function deleteRoleFunc(obj,sid,roleId){
  //alert("sid>>" + sid + "  obj>>" + obj + " roleId>>" + roleId);
  if(sid && sid>0){
    var para = {fileId:sid};
    var url = contextPath + "/fileRolePriv/deleteFileRolePriv.action";
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){
    	$.MsgBox.Alert_auto("删除成功！");
      $(obj).parent().parent().remove();
      removeRoleItemFunc(roleObjArray,roleId);
    }else{
    	$.MsgBox.Alert_auto(jsonRs.rtMsg);
    }
  }else{
    $(obj).parent().parent().remove();
    removeRoleItemFunc(roleObjArray,roleId);
  }
}
/**
 * 删除数组数据
 * @param deptObjArray
 * @param item
 */
function removeRoleItemFunc(roleObjArray,item){
  for(var i=0;i < roleObjArray.length;i++) {  
      if(item == roleObjArray[i].roleId) {
        //roleObjArray.pop(i);  
        roleObjArray.splice(i,1);
      }
  } 
}

/**
 * 是否存在角色
 * @param arrayObj
 * @param idstr
 * @returns {Boolean}
 */
function isContainRole(arrayObj,idstr){
  var containFlag = false;
  if(arrayObj.length>0){
    for(var i=0;i<arrayObj.length;i++){
      if(arrayObj[i].roleId == idstr){
        containFlag = true;
        break;
      }
    }
  }
  return containFlag;
}


/* 根据人员权限选中checkbox */
function checkedRoleValueFunc(deptId,privValueStr){
  var privValueArray = privValueStr.split(",");
  for(var k=0;k<6;k++){
    $("input[name=roleCheckbox_" + k + "_" + deptId +"]").each(function(i,obj){
      if(obj.value == privValueArray[k]){
        obj.checked = true;
      }
    });
  }
}

/* 获取选中值 */
function getRolePrivCheck(arrayObj){
  var userPrivStr = "[";
  if(arrayObj.length>0){
    for(var i=0;i<arrayObj.length;i++){
      var roleId = arrayObj[i].roleId;
      var privSid = arrayObj[i].privSid;
      var userPriv = 0;
      for(var k=0;k<6;k++){
        //alert("name=roleCheckbox_" +k + "_" + roleId + " userPriv>>" + userPriv );
        $("input[name=roleCheckbox_" + k + "_" + roleId + "]").each(function(id,obj){
          if(obj.checked){
        	  var privValue = parseInt(obj.value,10);
              if(privValue == 64){
              	userPriv = 1+2+4+8+32+64;
              }else{
              	userPriv += privValue;
              }
          }
          //alert(id + ">>" +obj.name + ">>" + obj.value + ">>" + obj.checked);
        });
      }
      //alert("name=roleCheckbox_" +i + "_" + roleId + " userPriv>>" + userPriv );
      userPrivStr +="{privSid:\"" + privSid + "\", roleId:\"" + roleId + "\",rolePriv:\"" + userPriv + "\"},";
    }
    if(userPrivStr.length>5){
      userPrivStr = userPrivStr.substring(0,userPrivStr.length-1);
    }
  }
  userPrivStr += "]";
  return userPrivStr
}





/* 提交角色权限 */
function submitRolePriv(){
  //文件夹id
  var fileId = $("#fileFolderSid").val();
  //获取选择的人员id串
  var userPrivJson = getRolePrivCheck(roleObjArray);
  if(userPrivJson.length>0){
    var para = {userPrivJson:userPrivJson,fileId:fileId,extend:$("#rolePrivMulti").attr("checked")};
    var url = contextPath + "/fileRolePriv/addOrUpdateFileRolePriv.action";
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){
    	$.MsgBox.Alert_auto(jsonRs.rtMsg);
//      window.location.reload();
    }else{
    	$.MsgBox.Alert_auto(jsonRs.rtMsg);
    }
  }
}

/**
 * 根据文件夹sid获取角色权限初始值，并选中
 */
function getRoletPrivFunc(sid){
  var fileId = $("#fileFolderSid").val();
  var para = {fileId:fileId};
  var url = contextPath + "/fileRolePriv/getFileRolePriv.action";
  var jsonRs = tools.requestJsonRs(url,para);
  //alert("数据>>" + jsonRs.rtData);//这个是字符串。。。不是json格式。。
  //var datas = eval(jsonRs.rtData);
  //alert(datas.length);
  if(jsonRs.rtState){
    var json = eval(jsonRs.rtData);
    var selecteRoleArray = new Array();
    jQuery.each(json,function(i,prc){
      //alert(prc.privValue);
      //alert("id>>" + prc.roleId + " name>>" + prc.roleName);
      selecteRoleArray.push({privSid:prc.sid,roleIdStr:prc.roleId,roleIdStrName:prc.roleName,privValues:prc.privValue});
    }); 
    getSelecteRoleArray(selecteRoleArray);
    selecteRoleArray.length = 0;
    
    //alert(jsonRs.rtMsg);
    //window.location.reload();
  }else{
	  $.MsgBox.Alert_auto(jsonRs.rtMsg);
  }
  
}





