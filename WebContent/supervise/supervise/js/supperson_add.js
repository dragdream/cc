var birthday = "";
var deptId = "";
var swfUploadObj = null;
function doInit(){
	politiveInit();
	nationInit();
	educationInit();
	jobClassInit();
	//根据身份证获取性别
	personId();
	//上传照片
	doInitMultipleUpload();

	if(id!=0){//编辑
		var json = tools.requestJsonRs("/SupPersonController/get.action",{id:id});
 		bindJsonObj2Easyui(json.rtData , "form1");
 		deptId = json.rtData.departmentId;
 		//所属部门
 		$('#departmentName').combobox({
			prompt:'输入关键字后自动搜索',
			mode:'remote',
			url:contextPath + '/SuperviseController/getSysCodeTempById.action?id='+json.rtData.departmentCode,
			valueField:'id',
			textField:'name',
			multiple:false,
			method:'post',
			panelHeight:'120px',
			labelPosition: 'top',
			onHidePanel:function(){
			var _options = $(this).combobox('options');
		    var _data = $(this).combobox('getData');/* 下拉框所有选项 */
		    var _value = $(this).combobox('getValue');/* 用户输入的值 */
		    var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
		    for (var i = 0; i < _data.length; i++) {
		        if (_data[i][_options.valueField] == _value) {
		            _b = true;
		            break;
		        }
		    }
		    if (!_b) {
		        $(this).combobox('setValue', '');
		    }
		    deptId = "";
		}
		});
 		//是否取得过执法证
 		if(json.rtData.isGetcode==1){
 			//document.getElementById("isGetcode").checked="true";
 			$(":radio[name='isGetcode'][value='1']").prop("checked", "checked");
 		}
 		if(json.rtData.sex == '01'){
 			$('#sex').textbox('setValue','男');
 		}else{
 			$('#sex').textbox('setValue','女');
 		}
	}else{
		//所属部门
		departmentadd();
		//默认执法证选择否
		$(":radio[name='isGetcode'][value='0']").prop("checked", "checked");
	}
}
function save(){
	
	if($('#form1').form('enableValidation').form('validate')){	
		var param = tools.formToJson($("#form1"));
		//校验姓名
		var text = /^((([\u4e00-\u9fa5]+[·]{0,10}[\u4e00-\u9fa5]){0,50})|([a-zA-Z]{2,50}))$/;
		var flag = text.test(param.name);
		if(!flag){
			$.MsgBox.Alert_auto("姓名输入不正确"); 
			return false;
		}
		//校验身份证
		var text = /^(([1][1-5])|([2][1-3])|([3][1-7])|([4][1-6])|([5][0-4])|([6][1-5])|([7][1])|([8][1-2]))\d{4}(([1][9]\d{2})|([2]\d{3}))(([0][1-9])|([1][0-2]))(([0][1-9])|([1-2][0-9])|([3][0-1]))\d{3}[0-9xX]$/;
		var flag = text.test(param.personId);
		if(!flag){
			$.MsgBox.Alert_auto("身份证输入不正确"); 
			return false;
		}
		
		//校验出生日期
		var thetime = param.birthStr;
		var   d=new   Date(Date.parse(thetime .replace(/-/g,"/")));
		var   curDate=new   Date();
		if(d > curDate){
			$.MsgBox.Alert_auto("出生日期最大值为当日");
			return false;
		}
		//是否公职律师
		if(param.isLawyer != '1' && param.isLawyer != '0'){
			$.MsgBox.Alert_auto("请选择是否公职律师");
			return false;
		}
		//校验联系电话
		if($("#telephone").val()!=""){
			var text = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$|(^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$)/;
			var flag = text.test(param.telephone);
			if(!flag){
				$.MsgBox.Alert_auto("联系电话输入不正确");
				return false;
			}
		}
		//性别
		if(param.sex == '男'){
			param.sex = '01';
		}else{
			param.sex = '02';
		}
		
		if(id!=0){//编辑
			param.isDelete = 0;
			param.Examine = 0;
			param.departmentId = deptId;
			var json = tools.requestJsonRs("/SupPersonController/update.action",param);
			if(json.rtMsg == 1){
				$.MsgBox.Alert_auto("该身份证号已存在");
			}
		    return json.rtState;
		}else{//新增
			param.isDelete = 0;
			param.Examine = 0;
			//param.isGetCode = $("#isGetCode").val();
			var json = tools.requestJsonRs("/SupPersonController/save.action",param);
			if(json.rtMsg == 1){
				$.MsgBox.Alert_auto("该身份证号已存在");
			}
		    return json.rtState;
		}
	}else{
		return false;
		
	}
	
}
function politiveInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "SYSTEM_CODE_POLITIVE"});
    if(json.rtState) {
        $('#politive').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'120px',
        });
    }
}
function nationInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "ENROLMENT_CODE_NATION"});
    if(json.rtState) {
        $('#nation').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'120px',
            onHidePanel:function(){
    			var _options = $(this).combobox('options');
    		    var _data = $(this).combobox('getData');/* 下拉框所有选项 */
    		    var _value = $(this).combobox('getValue');/* 用户输入的值 */
    		    var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
    		    for (var i = 0; i < _data.length; i++) {
    		        if (_data[i][_options.valueField] == _value) {
    		            _b = true;
    		            break;
    		        }
    		    }
    		    if (!_b) {
    		        $(this).combobox('setValue', '');
    		    }
    		}
        });
    }
}
function educationInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "SYSTEM_CODE_EDUCATION"});
    if(json.rtState) {
        $('#education').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'120px',
        });
    }
}
//职级
function jobClassInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "JOB_CLASS"});
    if(json.rtState) {
        $('#jobClass').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'120px',
        });
    }
}
//根据身份证判断性别及出生日期
function personId(){
	$('#personId').textbox({   
		"onChange":function(){
			var age = document.getElementById("personId").value;
			if (age.length == 18){
				if (parseInt(age.substr(16, 1)) % 2 == 0) {     
			        $('#sex').textbox('setValue','女');
				}else{                               
			        $('#sex').textbox('setValue','男');
				}
				birthday = age.substr(6,8);
				birthday = birthday.replace(/(.{4})(.{2})/,"$1-$2-");
				$('#birthStr').textbox('setValue',birthday);
			}
			else if (age.length == 15){
				if (parseInt(age.substr(14, 1)) % 2 == 0) {     
			        $('#sex').textbox('setValue','女');
				}else{                               
			        $('#sex').textbox('setValue','男');
				}
				birthday = "19"+age.substr(6,6);
				birthday = birthday.replace(/(.{4})(.{2})/,"$1-$2-");
				$('#birthStr').textbox('setValue',birthday);
			}else{
				$.MsgBox.Alert_auto("身份证位数错误");
			}
		}
	});
}
//所属部门
function departmentadd(){
	$('#departmentName').combobox({
			prompt:'输入关键字后自动搜索',
			mode:'remote',
			url:contextPath + '/SuperviseController/getSysCodeTempById.action',
			valueField:'id',
			textField:'name',
			multiple:false,
			method:'post',
			panelHeight:'120px',
			labelPosition: 'top',
			onHidePanel:function(){
			var _options = $(this).combobox('options');
		    var _data = $(this).combobox('getData');/* 下拉框所有选项 */
		    var _value = $(this).combobox('getValue');/* 用户输入的值 */
		    var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
		    for (var i = 0; i < _data.length; i++) {
		        if (_data[i][_options.valueField] == _value) {
		            _b = true;
		            break;
		        }
		    }
		    if (!_b) {
		        $(this).combobox('setValue', '');
		    }
		}
		});
}
//是否取得过执法证
function certificate(){
	var getCode = $("input[type='checkbox']").is(':checked')
	if(getCode==false){
		document.getElementById("isGetcode").checked="true";
	}else{
		document.getElementById("isGetcode").checked="";
	}
}
/**
 * 附件上传
 * @param model 后台文件路径文件夹
 * @param modelId 查询文件关键字
 * @param elemId 前台展示元素ID
 * @returns
 */
function doInitMultipleUpload() {
  //多附件快速上传
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		renderContainer:"renderContainer2",//渲染容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"attaches",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		swfUploadLoaded:function(){
			var swf = swfUploadObj.swf;
			
		 	//多附件快速上传
			var successfulUploads = 0;
			if(id != null && id !=''){
		 		//处理附件
		 		var json = tools.requestJsonRs(contextPath + "/SupPersonController/getFilelistById.action",{id:id});
				var attachModels = json.rtData;
				if(attachModels != null){
					successfulUploads = attachModels.length;
				    for(var i=0;i<attachModels.length;i++){
				        attachModels[i].priv = 2+4;
				        var attachElement = tools.getAttachElement(attachModels[i],{swfObj:swf});
				        $("#attachDiv").append(attachElement);
				    }
				}
			}			
			
			var stats = swf.getStats();
			stats.successful_uploads = successfulUploads;
			swf.setStats(stats);
			//alert(swfUploadObj.swf.getStats().successful_uploads);
		},
		file_upload_limit : 1,//上传文件限制
		file_types:"*.png;*.jpg",
		renderFiles:true,//渲染附件
		post_params:{model:"supperson",priv: 2+4}//后台传入值，model为模块标志
	});
}