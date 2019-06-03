var title = "";
var caseId = $("#caseId").val();
function doInit(){
	//获取案件信息回显到页面
	var json = tools.requestJsonRs("/discussionController/getCaseInfoById.action", {caseId: caseId});
	if(json.rtState){
		//后台返回对象后绑定到form表单
		bindJsonObj2Cntrl(json.rtData);
		//初始化文件上传控件
		documentCtralInit("trial_delay");
		//展示文件信息
		initFiling("trial_delay",caseId);
	}else{
		$.jBox.tip("请求失败,请联系管理员！", 'info' , {timeout:1500});
	}
}
//中止提交zck
function save(){
	 $(form1).ajaxSubmit({
		 type: 'post', // 提交方式 
       	 url: '/discussionController/delay.action', // 需要提交的 url
      	 success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
       	 	 //此处可对 data 作相关处理
      		$.jBox.tip("保存成功！", 'info' , {timeout:1500});
      	 },
       	 error:function(data){
       		$.jBox.tip("保存失败,请联系管理员！", 'info' , {timeout:1500});
       	 }
	 });
}