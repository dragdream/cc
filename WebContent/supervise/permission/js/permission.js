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
 * 填报，从代码表获取，高度自适应，不可搜索，单选
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