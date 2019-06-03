var title = "";
var caseId = $('#caseId').val(); //案件ID
function doInit(){
	//获取案件信息回显到页面
	var json = tools.requestJsonRs("/discussionController/getCaseInfoById.action", {caseId: caseId});
	if(json.rtState){
		//后台返回对象后绑定到form表单
		bindJsonObj2Cntrl(json.rtData);
		//初始化文件上传控件
		documentCtralInit("trial_break");
		//展示文件信息
		initFiling("trial_break",caseId);
	}else{
		$.jBox.tip("请求失败,请联系管理员！", 'info' , {timeout:1500});
	}
}

//选择中止理由zck
function choose(){
	var nowReason = $("#caseSubBreakReason").val();
	bsWindow("chooseReason.jsp?nowReason="+nowReason, "选择中止理由", {
		width : "1050",
		height : "250",
			buttons:
			[
			 {name:"保存",classStyle:"btn btn-primary"},
		 	 {name:"关闭",classStyle:"btn btn-primary"}
			 ]
			,
			submit : function(v, h, c, b) {
				var result = h[0].contentWindow;
					if(v == "保存"){
						var reason = result.giveFatherReason();
						//将中止理由回显
						$("#caseSubBreakReason").val(reason);
						//关闭子页面
						return true; 
					}else{
						return true;  
					}
			}
	});  
}
//中止提交zck
function save(){
	 $(form1).ajaxSubmit({
		 type: 'post', // 提交方式 
       	 url: '/discussionController/suspension.action', // 需要提交的 url
      	 success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
       	 // 此处可对 data 作相关处理
      		$.jBox.tip("保存成功！", 'info' , {timeout:1500});
      	 },
       	 error:function(data){
       		$.jBox.tip("保存失败,请联系管理员！", 'info' , {timeout:1500});
       	 }
	 });
}