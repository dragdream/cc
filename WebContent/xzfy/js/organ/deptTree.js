//初始化
function doInit(){
	$.MsgBox.Loading();
	var width = 0;
	var url = "/xzfy/organTree/selectFyTree.action";
	var jsonRs = tools.requestJsonRs(url,null);
	var setting = {
		check: {
			enable: true,
			chkboxType: { "Y": "", "N": ""}
		},			
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	var zNodes = jsonRs.rtData;
	$.fn.zTree.init($("#tree"), setting, zNodes);
	$.MsgBox.CloseLoading();
}

/**
 * 保存
 */
function doSubmit(){
	//保存ID和名称
	var checkedIdArr = new Array();	
	var checkedNameArr = new Array();	
	var zTreeObj = $.fn.zTree.getZTreeObj("tree");  
	var checkedNodes = zTreeObj.getCheckedNodes();
	//获取勾选的组织机构ID
	for(var j=0 ; j < checkedNodes.length ; j++){
		checkedIdArr.push(checkedNodes[j].id);
		checkedNameArr.push(checkedNodes[j].name);
	}
	//保存
	var url = "/xzfy/organ/addBatch.action";
	var param = {
		organIds:checkedIdArr.join(","),
		organNames:checkedNameArr.join(",")
	};
	//请求接口
	var jsonObj = tools.requestJsonRs(url,param);
	if(jsonObj.rtState){
		//location='manageGroup.jsp';
		$.MsgBox.Alert_auto("保存成功")
	}else{
		$.MsgBox.Alert_auto("保存失败,请联系管理员");
	}

}