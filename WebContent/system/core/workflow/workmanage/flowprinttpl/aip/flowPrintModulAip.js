
/**
 * 点击获取模版文件按钮 获取文件进行转换AIP -- 支持office系列、图片、pdf等
 */

var obj ;
var isLoad = false;
function selFile()
{
	 obj = document.getElementById("TeeHWPostil");//$("#TeeHWPostil")[0];
    obj.LoadFile("");
    
    isLoad = true;
}


/**
 * 获取数据并保存
 * @returns {Boolean}
 */
function convertAndSave()
{
	var modelName = $("#modulName")[0];
	if($.trim(modelName.value) == '')
	{
		modelName.focus();
		modelName.select();
		alert("请输入模板名称！");
		return;
	}

    if(!isLoad)
	{
		alert("请先选择模板文件！");
		return;
	}
 
    var content = obj.GetCurrFileBase64();//获取base64码
    
    /**
     * 上传AIP数据
     */
     var url = contextPath + "/flowPrintTemplate/addOrUpdateModul.action"  ;
     
 	//var para =  //tools.formToJson($("#form1")) ;
 	var para = {modulName:modelName.value , modulType:2,flowTypeId:flowTypeId,modulContent:content};
	var rtJson = tools.requestJsonRs(url,para); 
	if (rtJson.rtState) {
       alert("保存成功");
       return true;
     }else {
       alert(rtJson.rtMsg);
       return false;
     }
}


/***
 * 保存设计模版
 */
function saveModulDesigner()
{
	obj = document.getElementById("TeeHWPostil");//$("#TeeHWPostil")[0];
    var content = obj.GetCurrFileBase64();//获取base64码
    if(!content || content == ""){
    	alert("模版信息为空！保存失败！");
    	return;
    }
    var newPrintModulNote = new Array();
    newPrintModulNote = getAllAipNote();//获取所有节点信息，保存至数据库
    	
    var modulFieldDesc = JSON.stringify(newPrintModulNote) ;
//  alert(modulFieldDesc)
    /**
     * 上传AIP数据
     */
     var url = contextPath + "/flowPrintTemplate/updateModulDesigner.action"  ;
 	var para = {sid:sid,modulContent:content,modulField:modulFieldDesc};
	var rtJson = tools.requestJsonRs(url,para);
     if (rtJson.rtState) {
       alert("保存成功");
      // window.location.reload();
       return true;
     }else {
       alert(rtJson.rtMsg);
       return false;
     }
}

/**
 * 保存基本信息，包括流程步骤打印权限
 */
function updateModulInfo()
{
	 
	 var url = contextPath + "/flowPrintTemplate/addOrUpdateModul.action" ;
	 var prcsIds = "";
	 $("form").find("input[name='prcsId'][type=checkbox]").each(function(i,obj){
		if($(obj).attr("checked")){
			//json[$(obj).attr("name")] = 1;
			prcsIds = prcsIds + $(obj).val() + ","; 
		}else{
			//json[$(obj).attr("name")] = 0;
		}
	});
		//alert(prcsIds)
	 var para = {sid:sid,modulName:$("#modulName")[0].value , modulType:2,flowTypeId:flowTypeId,flowPrcsIds : prcsIds};
		
	 var rtJson = tools.requestJsonRs(url,para);
	 if (rtJson.rtState) {
	     alert("保存成功");
	      // window.location.reload();
	     return true;
	 }else {
	     alert(rtJson.rtMsg);
	     return false;
	 }
}

/***
 * 保存更换模版
 */
function saveUpdateModul()
{
	obj = document.getElementById("TeeHWPostil");//$("#TeeHWPostil")[0];
    var content = obj.GetCurrFileBase64();//获取base64码
    if(!content || content == ""){
    	alert("模版信息为空！保存失败！");
    	return;
    }
    var newPrintModulNote = new Array();

    /**
     * 上传AIP数据
     */
     var url = contextPath + "/flowPrintTemplate/updateModul.action"  ;
 	var para = {sid:sid,modulContent:content};
	var rtJson = tools.requestJsonRs(url,para);
     if (rtJson.rtState) {
       alert("保存成功");
       window.location.reload();
     }else {
       alert(rtJson.rtMsg);
       return false;
     }
}