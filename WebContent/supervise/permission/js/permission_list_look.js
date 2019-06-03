var id = $("#id").val();
/**
 * 初始化
 */
function doInit(){
	var url = contextPath + "/permissionListCtrl/getPermissionListById.action";
    var json = tools.requestJsonRs(url,{id:$("#id").val()});	
    if(json.rtState){
    	bindJsonObj2Cntrl(json.rtData);
    }
}

