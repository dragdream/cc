//存储人员Id、权限sid 
var personObjArray = new Array();

/**
 * 人员权限初始化
 */
function doInitUserPrivFunc(sid){
  /* 清空存储人员id容器 */
  personObjArray.length = 0;
  /* 创建人员权限table表头 */
  createUserPrivTable(sid);
  /* 初始化人员权限 */
  doInitUserPriv(sid);
  
}


/* 创建人员权限表头 */
function createUserPrivTable(fileId){
  $("#bodyDiv").html("");
  var table = "<table id='userPrivTab' class='TableList' width='100%' align='center'>" 
      + "<tr class='TableHeader' align='center'>"
      + "<td style='display:none;'></td>"
      + "<td nowrap  ><span style='text-indent:10px;display:block'>编号</span></td>"
      + "<td nowrap  >姓名 </td>"
      + "<td nowrap  ><input type='checkbox' id='userShowPriv' onclick='getSelectFunc(this,0)'><label for='userShowPriv'>浏览权限</label> </td>"
      + "<td nowrap  ><input type='checkbox' id='userEditPriv' onclick='getSelectFunc(this,1)'><label for='userEditPriv'>下载权限</label> </td>"
      + "<td nowrap  ><input type='checkbox' id='userDownPriv' onclick='getSelectFunc(this,2)'><label for='userDownPriv'>删除权限 </label> </td>"
      + "<td nowrap  ><input type='checkbox' id='userUploadPriv'onclick='getSelectFunc(this,3)'><label for='userUploadPriv'>编辑权限</label> </td>"
      + "<td nowrap  ><input type='checkbox' id='userDeletePriv'onclick='getSelectFunc(this,4)'><label for='userDeletePriv'>上传权限 </label> </td>"
      + "<td nowrap  ><input type='checkbox' id='userManagePriv'onclick='getSelectFunc(this,5)'><label for='userManagePriv'>管理权限 </label> </td>"
      + "<td nowrap  >操作 </td>"
      + "</tr></table>";
  $("#bodyDiv").append(table);
}
/**
 * 人员权限全选
 * @param obj
 * @param k
 */
function getSelectFunc(obj,k){
  if(obj.checked){
    $("input[name^=personCheckbox_" + k +"]").each(function(i,obj){
      if(!obj.checked){
        obj.checked = true;
      }
    });
  }else{
    $("input[name^=personCheckbox_" + k +"]").each(function(i,obj){
      if(obj.checked){
        obj.checked = false;
      }
    });
  }
}



/**
 * 添加人员
 */
function showUserPriv(){
  var html = "<div style='padding:10px;' style='background-color:#f2f2f2'>" 
            +   "<table class=''>"
            +     "<tr class='TableData'>"
            +       "<td width='90px;'>人员设置：</td>"
            +       "<td width='' class='TableData'>"
            +         "<input type='hidden' id='personIds' name='personIds' />"
            +         "<textarea cols='35' name='personIdsName' id='personIdsName' rows='4' style='overflow-y: auto;' class='SmallStatic BigTextarea' wrap='yes' readonly/>"
            +         "<span class='addSpan'>"
            +          "<img src='/common/zt_webframe/imgs/zsjl/ggwp/icon_select.png' onClick=\"selectUser(['personIds','personIdsName'],'1')\"/>"
            +          "&nbsp;&nbsp;<img src='/common/zt_webframe/imgs/zsjl/ggwp/icon_cancel.png' onClick=\"clearData('personIds','personIdsName')\"/>"
            +         "</span>"
            +       "</td>"
            +      "</tr>"
            +  "</table>"
            +"</div>";
  
  
  window.parent.bsWindow(html ,"人员权限",{width:"500",height:"100",
	    buttons:[
	    	{name:"保存",classStyle:"btn-alert-blue"},
	    	{name:"关闭",classStyle:"btn-alert-gray"}]
		,submit:function(v,h,f){
			var pIds=f.find("#personIds").val();
			if(v=="保存"){
				if(!pIds){
					 parent.$.MsgBox.Alert_auto("请选择人员！");
				      return;
				    }
				    //return ;
				    //点击保存选择的人员
				    var selectePersonArray = new Array();
				    var personIdArray = pIds.split(",");
				    var personIdsNameArray = f.find("#personIdsName").val().split(",");
				    if(personIdArray.length>0){
				      for(var i=0;i<personIdArray.length;i++){
				        if(personIdArray[i]){
				          var personIds = personIdArray[i];
				          var personIdsName = personIdsNameArray[i];
				          selectePersonArray.push({privSid:'0',personIds:personIds,personNames:personIdsName,privValues:''});
				        }
				      }
				    }
				    getSelectePersonArray(selectePersonArray);
				    selectePersonArray.length = 0;
				    return true;
			}
			if(v=="关闭"){
				return true;
			}
			
	    }},"html");
  
  
  
  
  
/*  $.jBox.open("html:"+ html ,"人员权限",500,200,{ submit:function(v,h,f){
    //alert(v + ">>" +h + ">>" +f.deptIdStr + ">>" + f.deptIdStrName);
    if(!f.personIds){
      $.MsgBox.Alert_auto("请选择人员！");
      return;
    }
    
    //return ;
    //点击保存选择的人员
    var selectePersonArray = new Array();
    var personIdArray = f.personIds.split(",");
    var personIdsNameArray = f.personIdsName.split(",");
    if(personIdArray.length>0){
      for(var i=0;i<personIdArray.length;i++){
        if(personIdArray[i]){
          var personIds = personIdArray[i];
          var personIdsName = personIdsNameArray[i];
          selectePersonArray.push({privSid:'0',personIds:personIds,personNames:personIdsName,privValues:''});
        }
      }
    }
    getSelectePersonArray(selectePersonArray);
    selectePersonArray.length = 0;
  },closed:function(){
    //parent.location.reload();
  }});*/
}


/* 显示人员权限，并默认选中值 */
function getSelectePersonArray(selectePersonArray){
  var privTableObj = document.getElementById("userPrivTab");
  if(privTableObj){
    var trCount = privTableObj.rows.length-1;
    //alert(privTableObj.rows.length);
    for(var i=0;i<selectePersonArray.length;i++){
      var personIds = selectePersonArray[i].personIds;//id
      var personNameStr = selectePersonArray[i].personNames;//名称
      var privValues = selectePersonArray[i].privValues;//权限值
      var privSid = selectePersonArray[i].privSid;//权限sid
      //alert(personIds + " >>" + personNameStr + " >>" + privValues);
      if(personIds && personNameStr){
        if(!isContainPerson (personObjArray,personIds)){
          trCount++;
          var curTr = "<tr>"
            //+   "<td><input type='text' id='' name='' value='' > </td>"
            +   "<td>" + trCount + "</td>"
            +   "<td>"+ personNameStr + "</td>"
            +   "<td><input type='checkbox'  name='personCheckbox_0_" + personIds + "' value='1'></td>"
            +   "<td><input type='checkbox'  name='personCheckbox_1_" + personIds + "' value='2'></td>"
            +   "<td><input type='checkbox'  name='personCheckbox_2_" + personIds + "' value='4'></td>"
            +   "<td><input type='checkbox'  name='personCheckbox_3_" + personIds + "' value='8'></td>"
            +   "<td><input type='checkbox'  name='personCheckbox_4_" + personIds + "' value='32'></td>"
            +   "<td><input type='checkbox'  name='personCheckbox_5_" + personIds + "' value='64'></td>"
            +   "<td><a href='javascript:void(0);' onclick=\"deletePersonFunc(this,'" + privSid + "','" + personIds + "');\">删除</a></td>"
            + "</tr>";
          $("#userPrivTab").append(curTr);
          personObjArray.push({personId:personIds,privSid:privSid});
          checkedPersonValueFunc(personIds,privValues);//获取权限时，有权限则默认选择
        }
      }
    }
  }
}


/**
 * 删除网盘部门权限信息
 * @param sid
 */
function deletePersonFunc(obj,sid,personId){
  //alert("sid>>" + sid + "  obj>>" + obj + " personId>>" + personId);
  if(sid && sid>0){
    var para = {fileId:sid};
    var url = contextPath + "/fileUserPriv/deleteFileUserPriv.action";
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){
      $.MsgBox.Alert_auto("删除成功！");
      $(obj).parent().parent().remove();
      removePersonItemFunc(personObjArray,personId);
    }else{
      $.MsgBox.Alert_auto(jsonRs.rtMsg);
    }
  }else{
    $(obj).parent().parent().remove();
    removePersonItemFunc(personObjArray,personId);
  }
}

/**
 * 删除数组数据
 * @param deptObjArray
 * @param item
 */
function removePersonItemFunc(personObjArray,item){
  for(var i=0;i < personObjArray.length;i++) {  
      if(item == personObjArray[i].personId) {
        //personObjArray.pop(i);  
        personObjArray.splice(i,1)
        
      }
  } 
}
/**
 * 是否存在人员
 * @param arrayObj
 * @param idstr
 * @returns {Boolean}
 */
function isContainPerson(arrayObj,idstr){
  var containFlag = false;
  if(arrayObj.length>0){
    for(var i=0;i<arrayObj.length;i++){
      if(arrayObj[i].personId == idstr){
        containFlag = true;
        break;
      }
    }
  }
  return containFlag;
}


/* 根据人员权限选中checkbox */
function checkedPersonValueFunc(personIds,privValueStr){
	
  var privValueArray = privValueStr.split(",");
  for(var k=0;k<6;k++){
    $("input[name=personCheckbox_" + k + "_" + personIds +"]").each(function(i,obj){
      if(obj.value == privValueArray[k]){
        obj.checked = true;
      }
    });
  }
}

/* 获取选中值 */
function getPersonPrivCheck(arrayObj){
  var userPrivStr = "[";
  if(arrayObj.length>0){
    for(var i=0;i<arrayObj.length;i++){
      var personId = arrayObj[i].personId;
      var privSid = arrayObj[i].privSid;
      var userPriv = 0;
      for(var k=0;k<6;k++){
        //alert("name=personCheckbox_" +k + "_" + roleId + " userPriv>>" + userPriv );
        $("input[name=personCheckbox_" + k + "_" + personId + "]").each(function(id,obj){
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
      //alert("name=personCheckbox_" +i + "_" + privSid + " userPriv>>" + userPriv );
      userPrivStr +="{privSid:\"" + privSid + "\", userId:\"" + personId + "\",userPriv:\"" + userPriv + "\"},";
    }
    if(userPrivStr.length>5){
      userPrivStr = userPrivStr.substring(0,userPrivStr.length-1);
    }
  }
  userPrivStr += "]";
  return userPrivStr
}





/* 提交用户权限 */
function submitPersonPriv(){
  //文件夹id
  var fileId = $("#fileFolderSid").val();
  //获取选择的人员id串
  var userPrivJson = getPersonPrivCheck(personObjArray);
  if(userPrivJson.length>0){
    var para = {userPrivJson:userPrivJson,fileId:fileId,extend:$("#userPrivMulti").attr("checked")};
    var url = contextPath + "/fileUserPriv/addFileUserPriv.action";
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
 * 根据文件夹sid获取人员权限初始值，并选中
 */
function doInitUserPriv(sid){
  var fileId = $("#fileFolderSid").val();
  var para = {fileId:fileId};
  var url = contextPath + "/fileUserPriv/getFileUserPriv.action";
  var jsonRs = tools.requestJsonRs(url,para);
  //alert("数据>>" + jsonRs.rtData);//这个是字符串。。。不是json格式。。
  //var datas = eval(jsonRs.rtData);
  //alert(datas.length);
  if(jsonRs.rtState){	
    var json = eval(jsonRs.rtData);
    var selectePersonArray = new Array();
    jQuery.each(json,function(i,prc){
      //alert(prc.privValue);
      //alert("id>>" + prc.roleId + " name>>" + prc.roleName);
      selectePersonArray.push({privSid:prc.sid,personIds:prc.userId,personNames:prc.userName,privValues:prc.privValue});
    }); 
    getSelectePersonArray(selectePersonArray);
    selectePersonArray.length = 0;
  }else{
	 $.MsgBox.Alert_auto(jsonRs.rtMsg);
  }
  
}





