/**
 * 查询条件，从代码表获取，高度自适应，不可搜索，单选
 */
function initCodeListSelect(codeNo,id) {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: codeNo});
    json.rtData.unshift({codeNo: "", codeName: "全部"});
    if(json.rtState) {
        $('#'+id).combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            editable: false,
            onHidePanel:function(){
            	var _options = $(this).combobox('options');
                var _data = $(this).combobox('getData');/* 下拉框所有选项 */
                var _value = $(this).combobox('getText');/* 用户输入的值 */
                var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
                for (var i = 0; i < _data.length; i++) {
                    if (_data[i][_options.textField] == _value) {
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

/**
 * 填报，给定json，单选
 */
function initJsonListRadio(json,id,/*param,*/value) {
	var page = "";
    for(var i=0 ; i < json.length; i++){
        page = page + '<input type="radio" name="'+id+'" id="'+id+json[i].codeNo+'" class="easyui-radiobutton" '
	        + 'style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="50px"'	
	        + 'required value="'+json[i].codeNo+'" label="'+json[i].codeName+'"/>';

    }
    $('#'+id+'Td').html(page);
	$.parser.parse($('#'+id+'Td'));
	/*if(typeof(param)!='undefined'){
		
		$('#' + id +'1').radiobutton({
	        onLoadSuccess:function(){
	            if(value != null && value != "" && value != '0'){
	                $(this).radiobutton('setValue',value);
	            }
	        },
	        onchange:function(check){
	        	param.show(check);
	        }
		});
	}*/
}

/**
 * 填报，单选按钮必选校验
 */
function radioValidate(id,name,msg) {
	var value = $("#" + id + " input[name='" + name + "']:checked").val();
    if (value == null || value == ''){
        top.$.MsgBox.Alert_auto(msg);
        $('html, body').animate({scrollTop: $("#" +id).offset().top}, 500);
        return false;
    }else{
    	return true;
    }
}

/**
 * 页面隐藏
 * @param ids id数组
 * @param types input框类型
 * @param thisVal 多选框value
 * @returns
 */
function inputRefresh(ids, types, thisVal){
    for(var i=0;i<ids.length;i++){
    	if(types[i] == 'textbox'){
    		$("#"+ids[i]).textbox({disabled:true,required: false});
        }else if(types[i] == 'combobox'){
        	$("#"+ids[i]).combobox({disabled:true,required: false});
        }else if(types[i] == 'checkbox'){
            var val = thisVal.split(",");
            for(var j=0 ; j < val.length; i++){
                $("#"+types[i]+val[j]).checkbox({checked: false});
            }
        }else if(types[i] == 'datebox'){
        	$("#"+ids[i]).datebox({disabled:true,required: false});
        }
    	$("#"+ids[i]).form('disableValidation');
    }
}

/**
 * 页面value清空
 * @param ids id数组
 * @param types input框类型
 * @param thisVal 多选框value
 */
function inputValidate(ids, types, thisVal){
    for(var i=0;i<ids.length;i++){
    	if(types[i] == 'textbox'){
            $("#"+ids[i]).textbox("setValue","");
        }else if(types[i] == 'combobox'){
            $("#"+ids[i]).combobox("setValue","");
        }else if(types[i] == 'checkbox'){
            var val = thisVal.split(",");
            for(var j=0 ; j < val.length; i++){
                $("#"+types[i]+val[j]).checkbox({checked: false});
            }
        }else if(types[i] == 'datebox'){
            $("#"+ids[i]).datebox("setValue","");
        }
    }
}

/**
 * 填报，从代码表获取，高度自适应，不可搜索，单选，必填
 */
function initCodeListInput(codeNo,id,value) {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: codeNo});
    if(json.rtState) {
        $('#'+id).combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            editable: false,
            prompt : '请选择',
            onLoadSuccess:function(){
                if(value != null && value != "" && value != '0'){
                    $(this).combobox('setValue',value);
                }
            },
            onHidePanel:function(){
            	var _options = $(this).combobox('options');
                var _data = $(this).combobox('getData');/* 下拉框所有选项 */
                var _value = $(this).combobox('getText');/* 用户输入的值 */
                var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
                for (var i = 0; i < _data.length; i++) {
                    if (_data[i][_options.textField] == _value) {
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
    return json.rtData;
}

/**
 * 填报，从代码表获取，高度自适应，可编辑，单选，非必填
 */
function initCodeListInputNoRequired(codeNo,id,value) {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: codeNo});
    if(json.rtState) {
        $('#'+id).combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            editable: true,
            prompt : '请选择',
            onLoadSuccess:function(){
                if(value != null && value != "" && value != '0'){
                    $(this).combobox('setValue',value);
                }
            },
            onHidePanel:function(){
            	var _options = $(this).combobox('options');
                var _data = $(this).combobox('getData');/* 下拉框所有选项 */
                var _value = $(this).combobox('getText');/* 用户输入的值 */
                var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
                for (var i = 0; i < _data.length; i++) {
                    if (_data[i][_options.textField] == _value) {
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
    return json.rtData;
}

/**
 * 查询条件，从代码表获取，高度自适应，不可搜索，多选
 */
function initCodeListMultipleSelect(codeNo,id,val){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: codeNo});
	if(json.rtState) {
        $('#'+id).combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
    		panelHeight:'auto',
    		multiple:true,
    		editable: false,
    		prompt : '全部',
            formatter: function (row) {
                var opts = $(this).combobox('options');
                return '<input type="checkbox" class="combobox-checkbox" style="margin-right:5px;cursor:pointer;">' + row[opts.textField];
            },
            onShowPanel: function () {
                var opts = $(this).combobox('options');
                var target = this;
                var values = $(target).combobox('getValues');
                $.map(values, function (value) {
                    var el = opts.finder.getEl(target, value);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                })
            },
            onLoadSuccess: function () {
                var opts = $(this).combobox('options');
                var target = this;
                var values = $(target).combobox('getValues');
                $.map(values, function (value) {
                    var el = opts.finder.getEl(target, value);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                })
                if(val != null && val != "" && val != '0'){
                    $(this).combobox('setValues',val.split(","));
                }
            },
            onSelect: function (row) {
                var opts = $(this).combobox('options');
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            },
            onUnselect: function (row) {
                var opts = $(this).combobox('options');
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', false);
            }
        });
    }
}

/**
 * 填报，从代码表获取，高度自适应，不可搜索，多选
 */
function initCodeListMultipleInput(codeNo,id,val){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: codeNo});
	if(json.rtState) {
        $('#'+id).combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
    		panelHeight:'auto',
    		multiple:true,
    		editable: false,
    		prompt : '请选择',
            formatter: function (row) {
                var opts = $(this).combobox('options');
                return '<input type="checkbox" class="combobox-checkbox" style="margin-right:5px;cursor:pointer;">' + row[opts.textField];
            },
            onShowPanel: function () {
                var opts = $(this).combobox('options');
                var target = this;
                var values = $(target).combobox('getValues');
                $.map(values, function (value) {
                    var el = opts.finder.getEl(target, value);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                })
            },
            onLoadSuccess: function () {
                var opts = $(this).combobox('options');
                var target = this;
                var values = $(target).combobox('getValues');
                $.map(values, function (value) {
                    var el = opts.finder.getEl(target, value);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                })
                if(val != null && val != "" && val != '0'){
                    $(this).combobox('setValues',val.split(","));
                }
            },
            onSelect: function (row) {
                var opts = $(this).combobox('options');
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            },
            onUnselect: function (row) {
                var opts = $(this).combobox('options');
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', false);
            }
        });
    }
}

/**
 * 初始化本单位对应许可主体，查询条件，不可搜索，单选
 */
function initSubjectSelect(){
    //所属主体
    var json = tools.requestJsonRs("/permissionItemCtrl/getSubjectListByOneself.action");
    json.rtData.unshift({id: "", subName: "全部"});
    if(json.rtState) {
    	$('#subjectId').combobox({
            data: json.rtData,
            valueField: 'id',
            textField: 'subName',
            panelHeight: 'auto',
            editable: false,
            onHidePanel:function(){
            	var _options = $(this).combobox('options');
                var _data = $(this).combobox('getData');/* 下拉框所有选项 */
                var _value = $(this).combobox('getText');/* 用户输入的值 */
                var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
                for (var i = 0; i < _data.length; i++) {
                    if (_data[i][_options.textField] == _value) {
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

/**
 * 初始化本单位对应许可主体，填报，不可搜索，单选
 */
function initSubjectInput(value){
	//所属主体
    var json = tools.requestJsonRs("/permissionItemCtrl/getSubjectListByOneself.action");
    if(json.rtState) {
    	$('#subjectId').combobox({
            data: json.rtData,
            valueField: 'id',
            textField: 'subName',
            panelHeight: 'auto',
            editable: false,
            prompt : '请选择',
            onLoadSuccess:function(data){
                if(value != null && value != "" && value != '0'){
                    $(this).combobox('setValue',value);
                }else{
                	$(this).combobox('setValue',data[0].id);
                }
            },
            onHidePanel:function(){
            	var _options = $(this).combobox('options');
                var _data = $(this).combobox('getData');/* 下拉框所有选项 */
                var _value = $(this).combobox('getText');/* 用户输入的值 */
                var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
                for (var i = 0; i < _data.length; i++) {
                    if (_data[i][_options.textField] == _value) {
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

/**
 * 上传完毕后加载已经上传的文件
 * @param obj 文件对象
 * @param elemId 展示元素ID
 * @param priv 权限
 * @returns
 */
function addAttachmentProp(obj, elemId, punishFile, priv) {
    var mainObj = $('#' + elemId);
    var page = "";
    page = page + '<div style="float: left; margin-right:20px" id="flowsheet' + obj.sid + '">';
    page = page + '<input type="hidden" name="'+elemId+'Path" id="'+elemId+'Path" value="' + obj.sid + '" />';
    page = page + '<input type="hidden" name="'+elemId+'Name" id="'+elemId+'Name" value="' + obj.fileName + '" />';
    page = page + '<a href="javascript:void(0);" sid="FILE' + obj.sid + '"></a>';
    page = page + '</div>';
    mainObj.append(page);
    
    obj.priv = 2+4; // 阅读、下载、删除
    var attach = tools.getAttachElement(obj, {deleteEvent:function(attachModel){
        $("#flowsheet" + attachModel.sid).remove();
        for(var index in punishFile) {
            if(punishFile[index] == attachModel.sid) {
                punishFile.splice(index, 1);
                break;
            }
        }
        
    }});
    $("[sid='FILE" + obj.sid + "']").append(attach);
    return punishFile;
}

/**
 * 修改初始化附件列表
 * @param model
 * @param modelId
 * @param elemId
 * @returns
 */
function initAttachmentInfo(model, sid, elemId) {
    var json = tools.requestJsonRs("/attachmentController/getAttachmentModelsByIds.action?attachIds="+sid);
    if(json.rtData != null && json.rtData.length > 0) {
        var punishFile = [];
        var attachList = json.rtData;
        $.each(attachList, function(i, o){
            // 添加附件属性
            punishFile.push(o.sid);//存放上传文件ID
            punishFile = addAttachmentProp(o, elemId, punishFile);
        });
    }
    return punishFile;
}