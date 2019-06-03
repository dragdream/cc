//字典表
var DICT_URL = "/xzfy/common/getDict.action";
//案件详情地址
var CASEINFO_URL = "/xzfy/caseInfo/getCaseInfo.action";
//案件ID
var caseId = "";
//初始化
function doInit() {
    	
    //获取案件ID
    caseId = getQueryString("caseId");
	
    caseId = 'aef07dddbf264e89951a2151aef50a70';
    
	//展示第0页
    showpage(0);
    
}

//页签切换
function showpage(index) {
	$(".content").hide();
	$(".content").eq(index).show();
	
	//登记信息模块
	if( index == 0){
		registerDiv(index);
	}
	//案件信息模块
	else if( index == 1){
		caseDiv(index);
	}
}


$(document).ready(function(){
	$(".lanky-tab").click(function(){
        showpage($(this).index());
	});
});

/***********************************************************/

//登记信息-div
function registerDiv(index){
	//清空
	$("#register-div").html("");
	$("#case-div").html("");
	//案件登记信息
	var register_data = "";
	//请求参数
    var param = {
    	caseId:caseId,
    	type:index
    };
	var json = tools.requestJsonRs(CASEINFO_URL,param);
	if( json.rtState == true ){
		register_data = json.rtData;
	}
	else{
		$.MsgBox.Alert_auto("查询失败,请联系管理员！");
	}
	if( register_data.applicationTypeCode == "02"){
		$("#register-div").append(juicer(a_tpl, register_data));
	}
	else{
		$("#register-div").append(juicer(b_tpl, register_data));
	}
	$("#register-div").append(juicer(c_tpl, register_data));
	$("#register-div").append(juicer(d_tpl, register_data));
	
	$("input").attr("disabled", "disabled");
	$("textarea").attr("disabled", "disabled");
	$("select").attr("disabled", "disabled");
	
	initOptions(register_data);

}

//案件信息-div
function caseDiv(index){
	//清空
	$("#register-div").html("");
	$("#case-div").html("");

	//案件登记信息
	var case_data = "";
	//请求参数
    var param = {
    	caseId:caseId,
    	type:index
    };
	var json = tools.requestJsonRs(CASEINFO_URL,param);
	if( json.rtState == true ){
		case_data = json.rtData;
	}
	$("#case-div").append(juicer(c_tpl, case_data));
	$("#case-div").append(juicer(d_tpl, case_data));
	
	// 案件审理
	if(case_data.caseHandling[0].caseStatusCode == "03" ){
		$("#case-div").append(juicer(e_tpl, case_data));
	} 
	// 案件结案 ,归档,归档完成
	else if(case_data.caseHandling[0].caseStatusCode == "04" ||
			case_data.caseHandling[0].caseStatusCode == "05" ||
			case_data.caseHandling[0].caseStatusCode == "06" ){
		$("#case-div").append(juicer(e_tpl, case_data));
		$("#case-div").append(juicer(f_tpl, case_data));
	}
	// 初始化下拉框
	initOptions(case_data);

}




/********************************************************/

//初始化下拉选择框
function initOptions(data){
	
	//当事人-申请人类型
	var url = DICT_URL;
	var param = {type:"APPLICANT_TYPE_CODE"};
	var json = tools.requestJsonRs(url,param);
	var html = createSelectHtml(json,data.clientInfo.applicantTypeCode);
	$("#applicantType").html(html);
	
	//复议事项-行政类别管理
	param = {type:"CATEGORY_CODE"};
	json = tools.requestJsonRs(url,param);
	html = createSelectHtml(json,data.clientInfo.applicantTypeCode);
	$("#category").html(html);
	//复议事项-申请事项
	param = {type:"APPLICATION_ITEM_CODE"};
	json = tools.requestJsonRs(url,param);
	html = createSelectHtml(json,data.clientInfo.applicantTypeCode);
	$("#applicationItem").html(html);
	
	//复议事项-赔偿类型
	param = {type:"COMPENSATION_TYPE_CODE"};
	json = tools.requestJsonRs(url,param);
	html = createSelectHtml(json,data.caseHandling[0].compensationTypeCode);
	$("#compensationTypeCode").html(html);
}


//创建下拉单选框HTML
function createSelectHtml(json,code){
	var html = "<option value=''>--请选择--</option>";
	if( json.rtState == true ){
		var list = json.rtData;
		for(var i=0;i<list.length;i++){
			html = html + "<option value='"+list[i].code+"'";
			if(list[i].code == code){
				html = html + " selected='selected'";
			}
			html = html + ">"+list[i].codeDesc+"</option>";
		}
	}
	return html;
}
