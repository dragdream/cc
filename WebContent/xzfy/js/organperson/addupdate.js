//获取详情
var GET_DETIAL = "/xzfy/organPerson/getDetial.action";

//字典表
var DICT_URL = "/xzfy/common/getDict.action";

//新增
var ADD_URL = "/xzfy/organPerson/add.action";

//修改
var UPDATE_URL = "/xzfy/organPerson/update.action";


function doInit(){
    //父节点ID
	var orgId = getQueryString("orgId");
	//父节点名称
	var orgName = getQueryString("orgName");
	//组织机构ID
	var personId = getQueryString("personId");
	//父节点赋值
	$("#orgId").val(orgId);
	$("#orgName").val(orgName);
	$("#personId").val(personId);

	
	//初始化
	initData()
	
	//修改
	if( personId != null && personId != "" && personId !="undefined"){
		$("#title").html("修改组织机构人员");
		
		//获取数据
		var url= GET_DETIAL;
		var param = {personId:personId};
		var json = tools.requestJsonRs(url,param);
		if(json.rtState == true){
			var data = json.rtData;
			if(data){
				//赋值
				bindJsonObj2Cntrl(data);
			}
		}else{
			$.MsgBox.Alert_auto("加载失败,请联系管理员!");
		}	
	}
	//新增
	else{
		$("#title").html("新增组织机构人员");
	}
	
}

//初始化
function initData(){
	
	//初始化性别
	var url = DICT_URL;
	var param = {type:"SEX_CODE"};
	var json = tools.requestJsonRs(url,param);
	var html = createSelectHtml(json);
	$("#sex").html(html);
	
	//初始化人员编制
	param = {type:"PERSON_TYPE_CODE"};
	json = tools.requestJsonRs(url,param);
	html = createSelectHtml(json);
	$("#staffing").html(html);
	
	//初始化职级
	param = {type:"LEVEL_CODE"};
	json = tools.requestJsonRs(url,param);
	html = createSelectHtml(json);
	$("#levelCode").html(html);
	
	//初始化学历
	param = {type:"EDUCATION_CODE"};
	json = tools.requestJsonRs(url,param);
	html = createSelectHtml(json);
	$("#educationCode").html(html);
	
}

//创建下拉单选框HTML
function createSelectHtml(json){
	
	var html = "<option value=''>--请选择--</option>";
	if( json.rtState == true ){
		var list = json.rtData;
		for(var i=0;i<list.length;i++){
			html = html + "<option value='"+list[i].code+"'>"+list[i].codeDesc+"</option>";
		}
	}
	return html;
}

//保存
function doSave(){

	if (check()){
		//地址
		var url = "";
		//参数
		var param =  tools.formToJson($("#form1")) ;
		//人员编制
		param['staffingName']= $("#staffing option:selected").text();
		//职级
		param['levelName']= $("#levelCode option:selected").text();
		//学历
		param['educationName']= $("#educationCode option:selected").text();
		
		//判断是新增还是编辑
		var personId = $("#personId").val();
		if( personId != null && personId != "" && personId !="undefined"){
			url = UPDATE_URL;
		}
		else{
			url = ADD_URL;
		}
		
		var json = tools.requestJsonRs(url,param);
		if(json.rtState == true ){
			$.MsgBox.Alert_auto('保存成功');
			var parent = window.parent;	
			parent.location.reload();

		}else{
			$.MsgBox.Alert_auto("保存失败,请与管理员联系!");
		} 
	}
}
//校验
function check() {
	return $("#form1").form('validate'); 
}

//选择单个部门
function selectSingleDept(retArray , moduleId,privNoFlag , noAllDept) {
    deptRetNameArray = retArray;
    objSelectType  = retArray[2] || "";
    var url = "/system/core/orgselect/selectMultiDeptPost.jsp?objSelectType=" + objSelectType + "&isSingle=1";
    var has = false;
    if (moduleId) {
    	url += "&moduleId=" + moduleId ;
	 }
	if (privNoFlag) {
		url += "&privNoFlag=" + privNoFlag ;
	}
	if (noAllDept) {
		url += "&noAllDept=" + noAllDept ;
	}
	var IM_OA;
	try{
		IM_OA = window.external.IM_OA;
	}catch(e)
	{}

	if(window.showModelDialog || IM_OA){
		dialogChangesize(url, 560, 400);
	}else{
		openWindow(url,"选择人员", 560, 400);
	}
}
