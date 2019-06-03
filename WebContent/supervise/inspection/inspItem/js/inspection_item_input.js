/**
 * 检查项控制js
 */

var ctrlType = '';
var loginDeptId = '';
var loginSubId = '';
var itemId = '';

function doInit() {
    initPersonPermision();
    
    itemId = $('#itemId').val();
    if(itemId==0){
        initOrgSysComboBoxAdd();
    } else {
        var json = tools.requestJsonRs("/inspectitemCtrl/itemById.action",{id:itemId});
        if(json.rtState){
            bindJsonObj2Easyui(json.rtData , "inspection_item_input_form");
            initOrgSysComboBoxEdit(json.rtData);
        }
    }
    
//    alert($('#orgSysList').val());
}
function initPersonPermision() {
    // 获取权限组
    var rtJson = tools.requestJsonRs("/commonCtrl/getMenuGroupNames.action", '');
    if (rtJson.rtState) {
        // 数据初始化
        var menuGroupsName = rtJson.rtData;
        var namesBuff = [];
        // 拆分成数组
        if (menuGroupsName != null) {
            namesBuff = menuGroupsName.split(',');
        }
        // 是否主管理权限判断标识
        var isMain = false;
        // 是否子管理权限判断标识
        var isSub = false;
        // 遍历数组，判断用户权限
        for (var i = 0; i < namesBuff.length; i++) {
            if (namesBuff[i] == '执法检查主管理') {
                isMain = true;
            }
            if (namesBuff[i] == '执法检查子管理') {
                isSub = true;
            }
        }
        // 权限判定完成，设置权限标识代码，作为控制参数，用于数据控制
        // 主、子权限都有时，按主管理权限判断
        if (isMain) {
            ctrlType = '10';
        } else {
            if (isSub) {
                ctrlType = '20';
            }
        }
        if (isMain || isSub) {
            // 在具备主管理权限或子管理权限时，进行参数初始化
            initBaseParams();
        }
    }
}

function initBaseParams() {
    var result = tools.requestJsonRs("/commonCtrl/getRelationAndOrgSys.action",
            params);
    if (result.rtState) {
        var relation = result.relation;
        loginDeptId = relation.businessDeptId;
        loginSubId = relation.businessSubjectId;
        orgSys = result.orgSys;
        var params = {
            loginDeptId : loginDeptId,
            loginSubId : loginSubId,
            ctrlType : ctrlType,
            orgSys : orgSys
        };
    }
}


//执法系统查询框（添加）
function initOrgSysComboBoxAdd() {
    var result = tools.requestJsonRs("/inspectionCtrl/getOrgSystemByCurrentPerson.action");
    
    if(result != null){
        $('#inspItem_orgSys').combobox({
            data : result,
            prompt : '请选择',
            valueField : 'id',
            textField : 'name',
            panelHeight : '100px',
            editable : false,
            onLoadSuccess : function() {
                initModuleComboBox(null);
            },
            onChange : function() {
                var orgSys = $('#inspItem_orgSys').combobox('getValue');
                var params = {
                        orgSys : orgSys
                    };
                initModuleComboBox(params);
            }
        });
    }else {
        $('#inspItem_orgSys').combobox({
            panelHeight : '100px',
            prompt : '请选择',
            editable : false
        });
    }
}
var count = 0;
//执法系统查询框（编辑--初始化数据）
function initOrgSysComboBoxEdit(param) {
    
    var orgSys = param.orgSys;
    var result = tools.requestJsonRs("/inspectionCtrl/getOrgSystemByCurrentPerson.action");
    if(result != null){
        $('#inspItem_orgSys').combobox({
            data : result,
            valueField : 'id',
            textField : 'name',
            panelHeight : '100px',
            editable : false,
            prompt : '请选择',
            onChange : function() {
                var orgSys = $('#inspItem_orgSys').combobox('getValue');
                var params = {
                        orgSys : orgSys
                    };
                initModuleComboBoxEdit(params);
            },
            onLoadSuccess : function() {
                if(count == 0){
                    if(orgSys != null && orgSys != "" && orgSys != '0'){
                        $(this).combobox('setValue',orgSys);
                        count = 1;
                        initModuleComboBoxEdit1(param);
                    }
                }
            }
        });
    }else {
        $('#inspItem_orgSys').combobox({
            panelHeight : '100px',
            prompt : '请选择',
            editable : false
        });
    }
}

function initModuleComboBox(params) {
    if (params != null&&params !="") {
        var result = tools.requestJsonRs("/inspectionCtrl/getInspModulesByOrgSys.action", params);
        if (result != null) {
            $('#inspItem_module').combobox({
                data : result,
                valueField : 'id',
                textField : 'moduleName',
                panelHeight : '100px',
                prompt : '请选择',
                onShowPanel : function(){
                },
                editable : false
            });
        }
    } else {
        $('#inspItem_module').combobox({
            panelHeight : '100px',
            prompt : '请选择',
            onShowPanel : function(){
                if(params==null || params ==""){
                    $.MsgBox.Alert_auto("请先选择执法系统");
                }
            },
            editable : false
        });
    }
}
var count1 = 0;
function initModuleComboBoxEdit1(param){
    
    var orgSys = param.orgSys; 
    var params = {
            orgSys : orgSys
        };
    var moduleId = param.moduleId;
    var result = tools.requestJsonRs("/inspectionCtrl/getInspModulesByOrgSys.action", params);
    if (result != null) {
        $('#inspItem_module').combobox({
            data : result,
            valueField : 'id',
            textField : 'moduleName',
            panelHeight : '100px',
            prompt : '请选择',
            onLoadSuccess : function() {
                if(count1==0){
                    count1 = 1;
                    if(moduleId != null && moduleId != "" && moduleId != '0'){
                        $('#inspItem_module').combobox('setValue',moduleId);
                    }
                }
            },
            editable : false
        });
    }
}
/**
 * 
 * @param params
 */
function initModuleComboBoxEdit(params) {
    $('#inspItem_module').combobox('setValue', '');
    if (params != null&&params !="") {
        var result = tools.requestJsonRs("/inspectionCtrl/getInspModulesByOrgSys.action", params);
        
        if (result != null) {
            $('#inspItem_module').combobox({
                data : result,
                valueField : 'id',
                textField : 'moduleName',
                prompt : '请选择',
                panelHeight : '100px',
                editable : false
            });
        }
    } else {
        $('#inspItem_module').combobox({
            panelHeight : '100px',
            prompt : '请选择',
            editable : false
        });
    }
}


function saveItemInfo() {
    if($('#inspection_item_input_form').form('enableValidation').form('validate')){
        var param = {
                id : itemId
            };
        param.orgSys = $('#inspItem_orgSys').combobox('getValue');
        param.moduleId = $('#inspItem_module').combobox('getValue');
        param.itemName = $('#itemName').val();
        param.loginDeptId = loginDeptId;
        param.loginSubId = loginSubId;
        param.ctrlType = ctrlType;
        var json = tools.requestJsonRs("/inspectitemCtrl/save.action", param);
        return json.rtState;
    }
}
