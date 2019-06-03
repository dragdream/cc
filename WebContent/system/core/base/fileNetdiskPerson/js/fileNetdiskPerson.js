/**
 * 新建文件夹
 */
function newFolderFunc(folderSid){
  createFolderFunc("新建文件夹",folderSid);
  $("#folderName").focus();
}



/**
 * 弹出新建或编辑文件夹对话框
 * @param titleStr
 * @param sid
 * @param editFlag 默认-新建文件夹；1-重命名文件夹；2-重命名文件
 */
function createFolderFunc(titleStr,sid,editFlag){
  var submitStr = "<input type='button' value='保存' class='btn-alert-blue' onclick='submitNewFolder(" + sid + ");'>&nbsp;&nbsp;";
  if(editFlag =="1"){
    submitStr = "<input type='button' value='保存' class='btn-alert-blue' onclick='submitEditFolder(" + sid + ");'>&nbsp;&nbsp;";
  }else if(editFlag =="2"){
    submitStr = "<input type='button' value='保存' class='btn-alert-blue' onclick='submitEditFile(" + sid + ");'>&nbsp;&nbsp;";
  }
  var html = "<br><form method='post' name='form1' id='form1'>"
           +   "<div style='margin-left:10px'>文件夹名称："
           +   "<input type='hidden' name='folderNameTemp' id='folderNameTemp'><input type='text' name='folderName' id='folderName' maxlength='100' class='' style='width:250px;height:20px' ></td>"
           +   "</div>"
           +   "<div align='right' style='margin-top:30px;margin-right:20px'>"
           +       submitStr
           +       "<input type='button' value='关闭' class='btn-alert-gray' onclick='$("+'"#win_ico"'+").click();'/>&nbsp;&nbsp;"
           +   "</div>"
           + "</form>";
  
    
    bsWindow(html,titleStr,{width:"380",height:"90",buttons:[]},"html");
}

/**
 * 提交新建文件夹信息
 * @param sid
 */
function submitNewFolder(sid){
  if(!$("#folderName").val()){
    $.MsgBox.Alert_auto("请输入文件夹名称！");
    $("#folderName").focus();
    return false;
  }
  if(isValidateFilePath($("#folderName").val())){
	$.MsgBox.Alert_auto("名称不能包含有以下字符/\:*<>?\"|");
    $("#folderName").focus();
    return false;
  }
  var url = contextPath + "/fileNetdiskPerson/newSubFolderById.action?sid=" + sid;
  var para =  tools.formToJson($("#form1"));
  var jsonRs = tools.requestJsonRs(url,para);
  if(jsonRs.rtState){
    var prc = jsonRs.rtData;
    var nodeId = prc.nodeId;
    var nodeName = prc.nodeName;
    var iconSkin = prc.iconSkin;
    var nodeParentId = prc.nodeParentId;
    
    $.MsgBox.Alert_auto("新建文件夹成功！");
    //关闭bsWindow
    $("#win_ico").click();
    
    $("#datagrid").datagrid("unselectAll");
    $("#datagrid").datagrid("reload");
    var nodeObj = {id:nodeId,name:nodeName,iconSkin:iconSkin,pid:nodeParentId};
    parent.frames["file_tree"].createZTreeNode(sid,nodeObj);  
  }else{
    $.MsgBox.Alert_auto(jsonRs.rtMsg);
  }
}



/**
 * 获取文件夹目录级别完整路径
 * @param sid
 */
function getFolderPathFunc(sid){
  var url = contextPath + "/fileNetdiskPerson/getFolderPathBySid.action?sid=" + sid;
  var jsonRs = tools.requestJsonRs(url);
  if(jsonRs.rtState){
    var prcs = jsonRs.rtData;
    var previous = prcs.previous;
    //var rootFolder = prcs.rootFolder;
    var folderPathList = prcs.folderPath;
    if(previous !='0'){
      //$("#previousSpan").show();
      $("#previousSpan").append("<a href='#' class='filePathClass' onclick='openFolderFunc(" + previous + ")'>返回上一级</a>|");
    }
    var folderPathLength = folderPathList.length-1;
    if(folderPathList.length>0){
      //$("#folderPathSpan").append("<a href='javascript:void(0)' class='filePathClass' onclick='openFolderFunc(0)'>全部文件</a>&gt;");
      $.each(folderPathList,function(i,prc){
        //alert("folderPathLength>>" + folderPathLength + "  i>>" +i + " prc.sid>>" + prc.sid + "  prc.folderName>" + prc.folderName);
        if(i !=folderPathLength){
          $("#folderPathSpan").append("<a  style='cursor:pointer' class='filePathClass' onclick='openFolderFunc(" + prc.sid + ")'>" + prc.folderName + "</a>&gt;");
        }else{
          $("#folderPathSpan").append("<font  class='filePathClass' color='#000000;' >" + prc.folderName + "</font>");
          $("#addFav").addFav("(个人网盘) "+prc.folderName,"/system/core/base/fileNetdiskPerson/fileNetdiskPersonList.jsp?sid="+prc.sid);
        }
      });
      
    }else{
      //$("#folderPathSpan").append("<a href='javascript:void(0)' class='filePathClass' color='#000000;'>全部文件</a>");
    }
    
  }else{
	  $.MsgBox.Alert_auto(jsonRs.rtMsg);
  }
}

/**
 * 打开文件夹
 * @param sid
 */
function openFolderFunc(sid){
  location.href = contextPath + "/system/core/base/fileNetdiskPerson/fileNetdiskPersonList.jsp?sid=" + sid;
}


/**
 * 重命名
 * @param sid
 * @param fileType
 */
function reNameFunc(sid,fileType){
  if(fileType =='0'){
    reNameFolderFunc(sid);
  }else {
    reNameFileFunc(sid);
  }
}


function reNameFileFunc(sid){
  createFolderFunc("重命名文件",sid,2);
  var jsonObj = getFileNetdiskByIdFunc(sid);
  if(jsonObj){
    var json = jsonObj.rtData;
    if(json.sid){
      $("#folderName").val(json.fileName);
    }
  }
  $("#folderName").focus();
  $("#folderName").select();
}



/**
 * 重命名文件夹
 * @param sid
 */
function reNameFolderFunc(sid){
  createFolderFunc("重命名文件夹",sid,1);
  var jsonObj = getFileNetdiskByIdFunc(sid);
  if(jsonObj){
    var json = jsonObj.rtData;
    if(json.sid){
      $("#folderName").val(json.fileName);
      $("#folderNameTemp").val(json.fileName);
    }
  }
  $("#folderName").focus();
  $("#folderName").select();
}

/**
 * 获取文件对象
 * @param sid
 */
function getFileNetdiskByIdFunc(sid){
  var url = contextPath + "/fileNetdiskPerson/getFileNetdiskById.action";
  var para = {sid:sid};
  var jsonObj = tools.requestJsonRs(url,para);
  return jsonObj;
}

/**
 * 提交重命名文件夹
 * @param sid
 */
function submitEditFolder(sid){
  if(!$("#folderName").val()){
    //$.jBox.tip("请输入文件名称！");
	$.MsgBox.Alert_auto("请输入文件夹名称！");
    $("#folderName").focus();
    return false;
  }
  if(isValidateFilePath($("#folderName").val())){
	$.MsgBox.Alert_auto("名称不能包含有以下字符/\:*<>?\"|");
    $("#folderName").focus();
    return false;
  }
  if($("#folderName").val() == $("#folderNameTemp").val()){
    $.MsgBox.Alert_auto("文件夹重命名成功！");
    //关闭bsWindow
    $("#win_ico").click();
    $("#datagrid").datagrid("unselectAll");
    $("#datagrid").datagrid("reload");
    return false;
  }
  var url = contextPath + "/fileNetdiskPerson/reNameFolderById.action?sid=" + sid;
  var para =  tools.formToJson($("#form1"));
  var jsonRs = tools.requestJsonRs(url,para);
  if(jsonRs.rtState){
    var prc = jsonRs.rtData;
    var nodeId = prc.nodeId;
    var nodeName = prc.nodeName;
    var iconSkin = prc.iconSkin;
    var nodeParentId = prc.nodeParentId;
    
    $.MsgBox.Alert_auto("文件夹重命名成功！");
  //关闭bsWindow
    $("#win_ico").click();
    $("#datagrid").datagrid("unselectAll");
    $("#datagrid").datagrid("reload");
    var nodeObj = {id:nodeId,name:nodeName,iconSkin:iconSkin,pid:nodeParentId};
    parent.frames["file_tree"].updateZTreeNode(nodeObj);  
  }else{
	  $.MsgBox.Alert_auto(jsonRs.rtMsg);
  }
}


/**
 * 提交重命名文件
 * @param sid
 */
function submitEditFile(sid){
  if(!$("#folderName").val()){
	$.MsgBox.Alert_auto("请输入文件夹名称！");
    $("#folderName").focus();
    return false;
  }
  if(isValidateFilePath($("#folderName").val())){
    $.MsgBox.Alert_auto("名称不能包含有以下字符/\:*<>?\"|");
    $("#folderName").focus();
    return false;
  }
  if($("#folderName").val() == $("#folderNameTemp").val()){
	$.MsgBox.Alert_auto("文件重命名成功！");
    $("#datagrid").datagrid("unselectAll");
    $("#datagrid").datagrid("reload");
    return false;
  }
  var url = contextPath + "/fileNetdiskPerson/reNameFileById.action?sid=" + sid;
  var para =  tools.formToJson($("#form1"));
  var jsonRs = tools.requestJsonRs(url,para);
  if(jsonRs.rtState){
	$.MsgBox.Alert_auto("文件重命名成功！");
	//关闭bsWindow
	$("#win_ico").click();
    $("#datagrid").datagrid("unselectAll");
    $("#datagrid").datagrid("reload");
  }else{
	$.MsgBox.Alert_auto(jsonRs.rtMsg);
  }
}


function copyFunc(rootFolderSid){
  var ids = getSelectItem();
  if(ids.length==0){
	$.MsgBox.Alert_auto("至少选择一项");
    return ;
  }
  
  var url = contextPath + "/system/core/base/fileNetdiskPerson/copyAndCutFolderTree.jsp?optionFlag=0&rootFolderSid=" + rootFolderSid + "&sid=" + ids;
  bsWindow(url,"复制到",{width:"600",height:"220",
	buttons:[{name:"保存",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}],submit:function(v,h){
    var cw = h[0].contentWindow;
    if(v=="保存"){
      if(cw.doCopySubmit()){
    	  $.MsgBox.Alert_auto("文件复制成功！");
    	  $("#datagrid").datagrid("unselectAll");
    	  $("#datagrid").datagrid("reload");
    	  return true;
      }
    }else if(v=="关闭"){
      return true;
    }
    
  }});
}


/**
 * 移动到
 */
function batchCutFunc(rootFolderSid){
  var ids = getSelectItem();
  if(ids.length==0){
	$.MsgBox.Alert_auto("至少选择一项");
    return ;
  }
  var url = contextPath + "/system/core/base/fileNetdiskPerson/copyAndCutFolderTree.jsp?optionFlag=1&rootFolderSid=" + rootFolderSid + "&sid=" + ids;
  bsWindow(url,"移动到",{width:"600",height:"220",
	  buttons:[{name:"保存",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}],submit:function(v,h){
    var cw = h[0].contentWindow;
    if(v=="保存"){
      if(cw.doCutSubmit()){
    	  $.MsgBox.Alert_auto("文件移动成功！");
    	  $("#datagrid").datagrid("unselectAll");
    	  $("#datagrid").datagrid("reload");
    	  return true;
      }
    }else if(v=="关闭"){
      return true;
    }
    
  }});
}


/**打包下载
 * 
 * @param sids
 */
function downLoadFilesFunc(folderSid){
  var ids = getSelectItem();
  if(ids.length==0){
	$.MsgBox.Alert_auto("至少选择一项");
    return ;
  }
  var url = contextPath + "/fileNetdiskPerson/downFileToZipBySid.action?sids=" + ids + "&folderSid=" + folderSid ;
  window.location.href = url;
}


/**
 * 共享文件夹权限设置
 * @param folderSid
 */
function shareFolderFunc(folderSid){
  var url = contextPath+"/system/core/base/fileNetdiskPerson/shareFolderPriv.jsp?sid="+folderSid;
  bsWindow(url,"共享权限设置",{width:"650",height:"330",
		buttons:[],
		submit:function(v,h){
	    var cw = h[0].contentWindow;
	    if(v=="保存"){
	      if(cw.doCopySubmit()){
	    	  $.MsgBox.Alert_auto("文件夹共享成功！");
	    	  $("#datagrid").datagrid("unselectAll");
	    	  $("#datagrid").datagrid("reload");
	    	  return true;
	      }
	    }else if(v=="关闭"){
	      return true;
	    }
	    
	  }});
  //$.jBox.open("iframe:"+url,"共享权限设置",650,330,{buttons:{} });
}


/**
 * 获取模块文件使用大小
 */
function getModuleFileSize(){
  var fileSize = 0;
  var url = contextPath + "/fileNetdiskPerson/getModuleFileSize.action";
  var jsonRs = tools.requestJsonRs(url);
  if(jsonRs.rtState){
    fileSize = jsonRs.rtData.fileSize;
  }else{
    $.MsgBox.Alert_auto(jsonRs.rtMsg);
  }
  return fileSize;
}






