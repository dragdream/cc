//获取详情
var GET_DETIAL = "/xzfy/organ/getDetial.action";

var personId = "";
//初始化加载
function doInit(){
	deptId = getQueryString("deptId");
	loadData(deptId);
}

//加载页面
function loadData(deptId){
	var url = GET_DETIAL;
	var json = tools.requestJsonRs(url,{"deptId":deptId});
	if(json.rtState == true){
		var data = json.rtData;
		if(data){
			bindJsonObj2Cntrl(data);
			
		}
	}else{
		$.MsgBox.Alert_auto("加载失败,请联系管理员!");
	}
}