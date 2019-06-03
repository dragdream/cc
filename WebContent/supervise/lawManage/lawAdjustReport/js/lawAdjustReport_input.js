/**
 * 法律法规调整上报填报js
 */

var _id = '';
var swfUploadObj = null;
function doInit() {
	_id = $('#lawAdjustReportid').val();
	$('#easyui-radiobutton adjust-type-radio').val('01');
	initModifyLawCombobox();
	initLawTypeCombobox();
	initLawValidCombobox();

	doInitMultipleUpload();
	initReportTypeChange();
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
			if(_id != null && _id !=''){
				var json = tools.requestJsonRs(contextPath + "/lawAdjustReportCtrl/getFilelistById.action",{id: _id});
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
		file_types:"*.doc;*.docx",
		renderFiles:true,//渲染附件
		post_params:{model:"lawInfo",priv: 2+4}//后台传入值，model为模块标志
	});
	
	
}
//查询修改和废弃的法律
function initModifyLawCombobox(){
	var params = {
			initLawFlag: 1,
			lawId: $('#updateLawId_value').val()
	}
	$('#modifyLaw_select').combobox({
		prompt:'输入关键字后自动搜索',
		mode:'remote',
		url:contextPath + '/lawAdjustReportCtrl/getLawsByName.action?'+ $.param(params),
		valueField:'id',
		textField:'name',
		multiple:false,
		method:'post',
		panelHeight:'100px',
		//label: 'Language:',
		labelPosition: 'top',
		onLoadSuccess: function(){
			params.initLawFlag = 2;
			var updateLawId = $('#updateLawId_value').val();
        	if(updateLawId != null && updateLawId != ''){
        		$('#modifyLaw_select').combobox('setValue', updateLawId);
        	}
		},
		onClick:function(row){
			//$('#businessSupDeptName').textbox('setValue',row.name);
			SupDeptName = row.name;
			ComboboxCommonProcess($(this));
		},
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

function initLawTypeCombobox(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "LAW_TYPE"});
	if(json.rtState) {
		$('#submitlawLevel').combobox({
			data: json.rtData,
			prompt:'请选择',
			editable:false,
			panelHeight:'auto',
			valueField: 'codeNo',
            textField: 'codeName',
            onLoadSuccess: function(){
            	var submitlawLevel = $('#submitlawLevel_value').val();
            	if(submitlawLevel != null && submitlawLevel != ''){
            		$('#submitlawLevel').combobox('setValue', submitlawLevel);
            	}
            }
		});
	}
}

function initLawValidCombobox(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "LAW_TIMELINESS"});
	if(json.rtState) {
		$('#timeliness').combobox({
			data: json.rtData,
			prompt:'请选择',
			editable:false,
			panelHeight:'auto',
			valueField: 'codeNo',
            textField: 'codeName',
            onLoadSuccess: function(){
            	var timeliness = $('#timeliness_value').val();
            	if(timeliness != null && timeliness != ''){
            		$('#timeliness').combobox('setValue', timeliness);
            	}
            }
		});
	}
}

//通用的combobox处理方法
function ComboboxCommonProcess(obj){
	var values = $(obj).combobox("getValues");
	var getData = $(obj).combobox("getData");
	var valuesT = [];
	for(var i=0;i<values.length;i++){
		for(var ii=0;ii<getData.length;ii++){
			if(values[i]==getData[ii].id){
				valuesT.push(values[i]);
				break;
			}
		}
	}
	$(obj).combobox("setValues",valuesT);
}

function initReportTypeChange(){
	$('#adjustReport_type_table .radiobutton').on('click', function() {
		var value = $(this).find('.radiobutton-value').val();
		if(value == 1){
			//新法
			$('#modifyLaw_info_tr').hide();
		
			$('.law-baseInfo-tr').show();
			$.parser.parse($('#modifyLaw_info_tr'));
			$('#modifyLaw_select').combobox({required:false, novalidate:true});
			$('#name').textbox({required:true, novalidate:true, missingMessage:'请输入法律法规名称', validType:'length[0,200]'});
			$('#word').textbox({required:true, novalidate:true, missingMessage:'请输入发文字号', validType:'length[0,30]'});
            $('#organ').textbox({required:true, novalidate:true, missingMessage:'请输入发文机关', validType:'length[0,100]'});
            $('#submitlawLevel').combobox({required:true,  novalidate:true, missingMessage:'请确定法律类别'});
            $('#timeliness').combobox({required:true,  novalidate:true, missingMessage:'请确定法律时效性'});
            $('#promulgation').datebox({disabled:false,  novalidate:true, validType:'date', required:true, missingMessage:'请确定颁布日期' });
            $('#implementation').datebox({disabled:false,  novalidate:true, validType:'date', required:true, missingMessage:'请确定实施日期' });
		}else if(value == 2){
			//修订
		    
			$('#modifyLaw_info_tr .text-info-area').text("待修订法律法规：");
			$('#modifyLaw_info_tr').show();
//			$('#law_name_tr').show();
			$('.law-baseInfo-tr').show();
			$('#modifyLaw_select').combobox({required:true, novalidate:true, missingMessage:'请确定需要修订的法律法规'});
			$('#name').textbox({required:true, novalidate:true, missingMessage:'请输入法律法规名称', validType:'length[0,200]'});
            $('#word').textbox({required:true, novalidate:true, missingMessage:'请输入发文字号', validType:'length[0,30]'});
            $('#organ').textbox({required:true, novalidate:true, missingMessage:'请输入发文机关', validType:'length[0,100]'});
            $('#submitlawLevel').combobox({required:true, novalidate:true, missingMessage:'请确定法律类别'});
            $('#timeliness').combobox({required:true, novalidate:true, missingMessage:'请确定法律时效性'});
            $('#promulgation').datebox({disabled:false, novalidate:true, validType:'date', required:true, missingMessage:'请确定颁布日期' });
            $('#implementation').datebox({disabled:false, novalidate:true, validType:'date', required:true, missingMessage:'请确定实施日期' });
			$.parser.parse($('#modifyLaw_info_tr'));
			
		}else if(value == 3){
			//废止
			$('#modifyLaw_info_tr .text-info-area').text("待废止法律法规：");
			$('#modifyLaw_info_tr').show();
//			$('#law_name_tr').hide();
			$('.law-baseInfo-tr').hide();
			$.parser.parse($('#modifyLaw_info_tr'));
			$('#modifyLaw_select').combobox({required:true, novalidate:true, missingMessage:'请确定需要废止的法律法规'});
			$('#name').textbox({required:false, validType:''});
            $('#word').textbox({required:false, validType:''});
            $('#organ').textbox({required:false,validType:''});
            $('#submitlawLevel').combobox({required:false});
            $('#timeliness').combobox({required:false});
            $('#promulgation').datebox({disabled:true, required:false, validType:''});
            $('#implementation').datebox({disabled:true, required:false, validType:''});
		}
	})
}

function save(){
	if($("#lawReportForm").form('enableValidation').form('validate')){
		var param = tools.formToJson($("#lawReportForm"));
		var finalParams = {};
		if(param.controlType == 1){
			finalParams = param;
			finalParams.updateLawId = '';
			finalParams.implementationStr = param.implementation;
			finalParams.promulgationStr = param.promulgation;
		}else if (param.controlType == 2){
			finalParams = param;
			finalParams.implementationStr = param.implementation;
			finalParams.promulgationStr = param.promulgation;
		}else if (param.controlType == 3){
			finalParams.controlType = param.controlType;
			finalParams.updateLawId = param.updateLawId;
		}else{
		    $.MsgBox.Alert_auto("请选择申请法律调整类型");
		    $('html, body').animate({scrollTop: $("#adjustReport_type_td").offset().top}, 200);
		    return false;
		}
		finalParams.id = _id;
//		if(param.attaches == ""||param.attaches == null){
//			$.MsgBox.Alert_auto("请选择法律原文");
//			return false;
//		}
			var json = tools.requestJsonRs("/lawAdjustReportCtrl/save.action",finalParams);
			if(json.rtState){
				$.MsgBox.Alert_auto("保存成功");
				return true;
			}else{
				$.MsgBox.Alert_auto("保存失败");
				return false;
			}
	}else{
		return false;
	}
}
 