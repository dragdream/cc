//存储部门Id、权限sid 
var deptObjArray = new Array();

/**
 * 部门权限初始化
 */
function doInitDeptPrivFunc(sid){
  /* 清空存储人员id容器 */
  deptObjArray.length = 0;
  /* 创建部门权限table表头 */
  createDeptPrivTable(sid);
  /* 初始化部门权限 */
  getDeptPrivFunc(sid);
  
}



/* 创建部门权限表头 */
function createDeptPrivTable(fileId){
  $("#deptBodyDiv").html("");
  var table = "<table id='deptPrivTab' class='TableList' width='100%' align='center'>" 
      + "<tr class='TableHeader' align='center'>"
      + "<td style='display:none;'></td>"
      + "<td nowrap  style='text-indent:10px' >编号</td>"
      + "<td nowrap  >部门名称 </td>"
      + "<td nowrap  ><input type='checkbox' id='deptShowPriv' onclick='getDeptSelectFunc(this,0)' ><label for='deptShowPriv'>浏览权限</label> </td>"
      + "<td nowrap  ><input type='checkbox' id='deptEditPriv' onclick='getDeptSelectFunc(this,1)' ><label for='deptEditPriv'>下载权限</label></td>"
      + "<td nowrap  ><input type='checkbox' id='deptDownPriv' onclick='getDeptSelectFunc(this,2)' ><label for='deptDownPriv'>删除权限 </label></td>"
      + "<td nowrap  ><input type='checkbox' id='deptUploadPriv' onclick='getDeptSelectFunc(this,3)' ><label for='deptUploadPriv'>编辑权限</label></td>"
      + "<td nowrap  ><input type='checkbox' id='deptDeletePriv' onclick='getDeptSelectFunc(this,4)' ><label for='deptDeletePriv'>上传权限 </label></td>"
      + "<td nowrap  ><input type='checkbox' id='deptManagePriv' onclick='getDeptSelectFunc(this,5)' ><label for='deptManagePriv'>管理权限 </label></td>"
      + "<td nowrap  >操作 </td>"
      + "</tr></table>";
  $("#deptBodyDiv").append(table);
}

/**
 * 部门权限全选
 * @param obj
 * @param k
 */
function getDeptSelectFunc(obj,k){
  if(obj.checked){
    $("input[name^=deptCheckbox_" + k +"]").each(function(i,obj){
      if(!obj.checked){
        obj.checked = true;
      }
    });
  }else{
    $("input[name^=deptCheckbox_" + k +"]").each(function(i,obj){
      if(obj.checked){
        obj.checked = false;
      }
    });
  }
}



/**
 * 添加部门
 */
function showDeptPriv(){
  /*var html = "<div style='padding:10px;'>输入点什么：<input type='text' id='some' name='some' /></div>";
   var submit = function (v, h, f) {
      if (f.some == '') {
          // f.some 或 h.find('#some').val() 等于 top.$('#some').val()
          top.$.jBox.tip("请输入点什么。", 'error', { focusId: "some" }); // 关闭设置 some 为焦点
          return false;
      }
      top.$.jBox.info("你输入了：" + f.some);
      return true;
  };
  top.$.jBox(html, { title: "输入", submit: submit });
  return; */
  
  var html = "<div style='padding:10px;' style='background-color:#f2f2f2'>" 
            +   "<table class=''>"
            +     "<tr class='TableData'>"
            +       "<td width='70px;'>部门设置：</td>"
            +       "<td width='' class='TableData'>"
            +         "<input type='hidden' id='deptIdStr' name='deptIdStr' />"
            +         "<textarea cols='35' name='deptIdStrName' id='deptIdStrName' rows='4' style='overflow-y: auto;' class='SmallStatic BigTextarea' wrap='yes' readonly/>"
            +         "<span class='addSpan'>"
            +          "<img src='/common/zt_webframe/imgs/zsjl/ggwp/icon_select.png' onClick=\"selectDept(['deptIdStr','deptIdStrName'],'1')\"/>"
            +          "&nbsp;&nbsp;<img src='/common/zt_webframe/imgs/zsjl/ggwp/icon_cancel.png' onClick=\"clearData('deptIdStr','deptIdStrName')\"/>"
            +         "</span>"
            +       "</td>"
            +      "</tr>"
            +  "</table>"
            +"</div>";
  
  window.parent.bsWindow(html ,"部门权限",{width:"500",height:"100",
	 buttons:[{name:"保存",classStyle:"btn-alert-blue"},
	 	      {name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h,f){
	     var dIds=f.find("#deptIdStr").val();
	     if(v=="保存"){
            if(!dIds){
              $.MsgBox.Alert_auto("请选择部门！");
              return;
            }
    
           //点击保存选择的部门
         var selecteDeptArray = new Array();
         var deptIdArray = dIds.split(",");
         var deptNameArray =f.find("#deptIdStrName").val().split(",");
         if(deptIdArray.length>0){
           for(var i=0;i<deptIdArray.length;i++){
             if(deptIdArray[i]){
              var deptIdStr = deptIdArray[i];
              var deptIdStrName = deptNameArray[i];
              selecteDeptArray.push({privSid:'0',deptIdStr:deptIdStr,deptIdStrName:deptIdStrName,privValues:''});
             }
           }
        }
       getSelecteDeptArray(selecteDeptArray);
       selecteDeptArray.length = 0;
       return  true;
  }
	if(v=="关闭"){
		return true;
	}
	
   }},"html");
}


/* 显示部门权限，并默认选中值 */
function getSelecteDeptArray(selecteDeptArray){
  var privTableObj = document.getElementById("deptPrivTab");
  if(privTableObj){
    var trCount = privTableObj.rows.length-1;
    //alert(privTableObj.rows.length);
    for(var i=0;i<selecteDeptArray.length;i++){
      var deptIdStr = selecteDeptArray[i].deptIdStr;//id
      var deptNameStr = selecteDeptArray[i].deptIdStrName;//名称
      var privValues = selecteDeptArray[i].privValues;//权限值
      var privSid = selecteDeptArray[i].privSid;//权限sid
      //alert(deptIdStr + " >>" + deptNameStr + " >>" + privValues);
      if(deptIdStr && deptNameStr){
        if(!isContainDept (deptObjArray,deptIdStr)){
          trCount++;
          var curTr = "<tr>"
            //+   "<td><input type='text' id='' name='' value='' > </td>"
            +   "<td>" + trCount + "</td>"
            +   "<td>"+ deptNameStr + "</td>"
            +   "<td><input type='checkbox'  name='deptCheckbox_0_" + deptIdStr + "' value='1'></td>"
            +   "<td><input type='checkbox'  name='deptCheckbox_1_" + deptIdStr + "' value='2'></td>"
            +   "<td><input type='checkbox'  name='deptCheckbox_2_" + deptIdStr + "' value='4'></td>"
            +   "<td><input type='checkbox'  name='deptCheckbox_3_" + deptIdStr + "' value='8'></td>"
            +   "<td><input type='checkbox'  name='deptCheckbox_4_" + deptIdStr + "' value='32'></td>"
            +   "<td><input type='checkbox'  name='deptCheckbox_5_" + deptIdStr + "' value='64'></td>"
            +   "<td><a href='javascript:void(0);' onclick=\"deleteDeptFunc(this,'" + privSid + "','" + deptIdStr + "');\">删除</a></td>"
            + "</tr>";
          $("#deptPrivTab").append(curTr);
          deptObjArray.push({deptId:deptIdStr,privSid:privSid});
          checkedDeptValueFunc(deptIdStr,privValues);//获取权限时，有权限则默认选择
        }
      }
    }
  }
}

/**
 * 删除网盘部门权限信息
 * @param sid
 */
function deleteDeptFunc(obj,sid,deptId){
  //alert("sid>>" + sid + "  obj>>" + obj + " deptId>>" + deptId);
  if(sid && sid>0){
    var para = {fileId:sid};
    var url = contextPath + "/fileDeptPriv/deleteFileDeptPriv.action";
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){
      $.MsgBox.Alert_auto("删除成功！");
      $(obj).parent().parent().remove();
      removeDeptItemFunc(deptObjArray,deptId);
    }else{
    	$.MsgBox.Alert_auto(jsonRs.rtMsg);
    }
  }else{
    $(obj).parent().parent().remove();
    removeDeptItemFunc(deptObjArray,deptId);
  }
}

/**
 * 删除数组数据
 * @param deptObjArray
 * @param item
 */
function removeDeptItemFunc(deptObjArray,item){
  for(var i=0;i < deptObjArray.length;i++) {  
      if(item == deptObjArray[i].deptId) {
        //deptObjArray.pop(i);  
        deptObjArray.splice(i,1);
      }
  } 
}

/**
 * 是否存在部门
 * @param arrayObj
 * @param idstr
 * @returns {Boolean}
 */
function isContainDept(arrayObj,idstr){
  var containFlag = false;
  if(arrayObj.length>0){
    for(var i=0;i<arrayObj.length;i++){
      if(arrayObj[i].deptId == idstr){
        containFlag = true;
        break;
      }
    }
  }
  return containFlag;
}


/* 根据人员权限选中checkbox */
function checkedDeptValueFunc(deptId,privValueStr){
  var privValueArray = privValueStr.split(",");
  for(var k=0;k<6;k++){
    $("input[name=deptCheckbox_" + k + "_" + deptId +"]").each(function(i,obj){
      if(obj.value == privValueArray[k]){
        obj.checked = true;
      }
    });
  }
}

/* 获取选中值 */
function getDeptPrivCheck(arrayObj){
  var userPrivStr = "[";
  if(arrayObj.length>0){
    for(var i=0;i<arrayObj.length;i++){
      var deptId = arrayObj[i].deptId;
      var privSid = arrayObj[i].privSid;
      var userPriv = 0;
      for(var k=0;k<6;k++){
        //alert("name=deptCheckbox_" +k + "_" + deptId + " userPriv>>" + userPriv );
        $("input[name=deptCheckbox_" + k + "_" + deptId + "]").each(function(id,obj){
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
      //alert("name=deptCheckbox_" +i + "_" + deptId + " userPriv>>" + userPriv );
      userPrivStr +="{privSid:\"" + privSid + "\", deptId:\"" + deptId + "\",deptPriv:\"" + userPriv + "\"},";
    }
    if(userPrivStr.length>5){
      userPrivStr = userPrivStr.substring(0,userPrivStr.length-1);
    }
  }
  userPrivStr += "]";
  return userPrivStr;
}





/* 提交部门权限 */
function submitDeptPriv(){
  //文件夹id
  var fileId = $("#fileFolderSid").val();
  //获取选择的人员id串
  var userPrivJson = getDeptPrivCheck(deptObjArray);
  if(userPrivJson.length>0){
    var para = {userPrivJson:userPrivJson,fileId:fileId,extend:$("#deptPrivMulti").attr("checked")};
    var url = contextPath + "/fileDeptPriv/addOrUpdateFileDeptPriv.action";
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
 * 根据文件夹sid获取部门权限初始值，并选中
 */
function getDeptPrivFunc(sid){
  var fileId = $("#fileFolderSid").val();
  var para = {fileId:fileId};
  var url = contextPath + "/fileDeptPriv/getFileDeptPriv.action";
  var jsonRs = tools.requestJsonRs(url,para);
  //alert("数据>>" + jsonRs.rtData);//这个是字符串。。。不是json格式。。
  //var datas = eval(jsonRs.rtData);
  //alert(datas.length);
  if(jsonRs.rtState){
    var json = eval(jsonRs.rtData);
    var selecteDeptArray = new Array();
    jQuery.each(json,function(i,prc){
      //alert(prc.privValue);
      //alert("id>>" + prc.userId + " name>>" + prc.userName);
      selecteDeptArray.push({privSid:prc.sid,deptIdStr:prc.deptId,deptIdStrName:prc.deptName,privValues:prc.privValue});
    }); 
    getSelecteDeptArray(selecteDeptArray);
    selecteDeptArray.length = 0;
    
    
    //alert(jsonRs.rtMsg);
    //window.location.reload();
  }else{
	$.MsgBox.Alert_auto(jsonRs.rtMsg);
  }
  
}





