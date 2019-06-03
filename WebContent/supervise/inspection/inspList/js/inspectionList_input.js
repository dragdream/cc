/**
 * 检查单模版管理控制js
 */

var ctrlType = '';
var loginDeptId = '';
var loginSubId = '';
var listId = '';
var inspList_module = '';

function doInit() {
    ctrlType = $('#ctrlType').val();
    loginDeptId = $('#loginDeptId').val();
    loginSubId = $('#loginSubId').val();
    listId = $('#listId').val();
    if(listId == 0){
        initOrgSysComboBox();
        initApplyhierarachy();
        initListClassify();
    } else{
        var json = tools.requestJsonRs("/inspListBaseCtrl/getById.action",{id:listId});
        if(json.rtState){
            bindJsonObj2Easyui(json.rtData , "inspection_list_input_form");
            inspList_module = json.rtData.moduleIdList;
        }
        initOrgSysComboBox(json.rtData.orgSys);
        initApplyhierarachy(json.rtData.applyHierarchy);
        initListClassify(json.rtData.listClassify);
    }
}

/**
 * 初始化所属领域
 * @param orgSys
 */
function initOrgSysComboBox(orgSys) {
    $('#inspList_orgSys').combobox({
        panelHeight : 'auto',
        prompt : '请选择',
        panelHeight:'100px',
        editable : false,
        onLoadSuccess : function() {
            if(orgSys == ""||orgSys == null){
                orgSys = $('#inspList_orgSys').combobox('getValue');
            }else{
//                alert("edit");
                $(this).combobox('setValue',orgSys);
            }
            if (orgSys != "") {
                var params = {
                    orgSys : orgSys
                };
//                alert("onLoadSuccess");
                initModuleComboBox(params);
            } else {
                initModuleComboBox();
            }
        },
        onChange : function() {
            var orgSys = $('#inspList_orgSys').combobox('getValue');
            if (orgSys != "") {
                var params = {
                    orgSys : orgSys
                };
//                alert("onChange");
                initModuleComboBox(params);
            } else {
                initModuleComboBox();
            }
        }
    });
}


function initModuleComboBox(params) {
    if (params != null) {
        var result = tools.requestJsonRs("/inspectionCtrl/getInspModulesByOrgSys.action", params);
        if (result != null) {
            $('#inspList_module').combobox({
                data : result,
                valueField : 'id',
                textField : 'moduleName',
                panelHeight:'100px',
                multiple: true,
                prompt : '请选择',
                formatter: function (row) {
                    var opts = $(this).combobox('options');
                    return '<input type="checkbox" class="combobox-checkbox" style="margin-right:5px;cursor:pointer;">' + row[opts.textField];
                },
//                onLoadSuccess : function() {
//                    if(listId != 0){
//                        $(this).combobox('setValue',inspList_module);
//                    }
//                },
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
                    if(listId != null && listId != "" && listId != '0'){
                        $(this).combobox('setValues',inspList_module);
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
                },
                editable : false
            });
        }
    } else {
        $('#inspList_module').combobox({
            panelHeight : 'auto',
            prompt : '请选择',
            multiple: true,
            editable : false
        });
    }
}

function initApplyhierarachy(applyHierarchy) {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action",
            {
                codeNo : "DEPT_LEVEL"
            });
    if (json.rtState) {
        $('#inspList_applyHierarchy').combobox({
            data : json.rtData,
            valueField : 'codeNo',
            textField : 'codeName',
            panelHeight : '100px',
            prompt : '请选择',
            onLoadSuccess : function() {
                if(listId != 0){
                    $(this).combobox('setValue',applyHierarchy);
                }
            },
            editable : false
        });
    }else{
        $('#inspList_applyHierarchy').combobox({
            panelHeight : 'auto',
            prompt : '请选择',
            editable : false
        });
    }
}

function initListClassify(listClassify) {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action",
            {
                codeNo : "INSP_LIST_TYPE"
            });

    if (json.rtState) {
        $('#inspList_listClassify').combobox({
            data : json.rtData,
            valueField : 'codeNo',
            textField : 'codeName',
            panelHeight : '100px',
            prompt : '请选择',
            onLoadSuccess : function() {
                if(listId != 0){
                    $(this).combobox('setValue',listClassify);
                }
            },
            editable : false
        });
    }else{
        $('#inspList_listClassify').combobox({
            panelHeight : 'auto',
            prompt : '请选择',
            editable : false
        });
    }
}

function saveListInfo() {
    if($('#inspection_list_input_form').form('enableValidation').form('validate')){
        var param ={
                id:listId
        };
        param.listName = $('#listName').val();
        
        param.orgSys = $('#inspList_orgSys').combobox('getValue');
        
        param.applyHierarchy = $('#inspList_applyHierarchy').combobox('getValue');
        
        param.listClassify = $('#inspList_listClassify').combobox('getValue');
        
        var tempBuff = $('#inspList_module').combobox('getValues');
        if(tempBuff != null && tempBuff.length > 0){
            param.moduleIdsStr = tempBuff.join(",");
        }
        param.loginDeptId = loginDeptId;
        param.loginSubId = loginSubId;
        param.ctrlType = ctrlType;
        var json = tools.requestJsonRs("/inspListBaseCtrl/save.action", param);
        return json.rtState;
    }
}