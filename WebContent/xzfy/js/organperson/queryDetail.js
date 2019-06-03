//获取详情
var GET_DETIAL = "/xzfy/organPerson/getDetial.action";

var personId = "";
//初始化加载
function doInit(){
	personId = getQueryString("personId");
	loadData(personId);
}

//加载页面
function loadData(personId){
	var url = GET_DETIAL;
	var json = tools.requestJsonRs(url,{"personId":personId});
	if(json.rtState == true){
		var data = json.rtData;
		if(data){
			bindJsonObj2Cntrl(data);
			//性别
			var sexDesc = "男";
			if(data.sex == '02'){
				sexDesc = "女";
			}
			else if(data.sex == '09'){
				sexDesc = "其他";
			}
			$("#sex").html(sexDesc);
			
			//是否获取法律证书
			var isLaw = "是";
			if(data.sex == "0"){
				isLaw = "否";
			}
			$("#isLaw").html(isLaw);
			
			//是否党员
			var isParty = "是";
			if(data.sex == "0"){
				isParty = "否";
			}
			$("#isParty").html(isParty);
		}
	}else{
		$.MsgBox.Alert_auto("加载失败,请联系管理员!");
	}
}