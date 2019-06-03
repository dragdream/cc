//获取详情
var GET_DETAIL_URL = "/xzfy/organ/getDetial.action";

//字典表
var DICT_URL = "/xzfy/common/getDict.action";

//更新地址
var UPDATE_URL = "/xzfy/organ/update.action";

function doInit(){
    //节点ID
	var treeId = getQueryString("treeId");
	//节点名称
	var treeName = (getQueryString("treeName"));

	//节点赋值
	$("#deptId").val(treeId);
	$("#orgName").val(treeName);
	
	//初始化
	initData();
	
	//修改
	if( treeId != null && treeId != "" && treeId !="undefined"){
		$("#title").html("修改组织机构");
		//获取数据
		var url = GET_DETAIL_URL;
		var param = {
				deptId:treeId
			};
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
	
}

//初始化
function initData(){
	
	//初始化机关层级
	var url = DICT_URL;
	var param = {type:"RESPONDENT_TYPE_CODE"};
	var json = tools.requestJsonRs(url,param);
	//拼接类型
	var html = createSelectHtml(json);
	$("#orgLevelCode").html(html);
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
	
	var treeId = getQueryString("treeId");
	if( treeId != null && treeId != "" && treeId !="undefined"){
		
	}
	else{
		$.MsgBox.Alert_auto('请选择组织机构');
		return;
	}
	if (check()){
		//地址
		var url = "";
		//参数
		var param =  tools.formToJson($("#form1")) ;
		//下拉选择的文本值
		param['orgLevelName']= $("#orgLevelCode option:selected").text();
		
		//更新
		url = UPDATE_URL;
		
		var json = tools.requestJsonRs(url,param);
		if(json.rtState == true){
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

