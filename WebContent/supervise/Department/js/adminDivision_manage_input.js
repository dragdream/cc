/**
 * 行政区划管理填报页面管理方法
 */

//登录用户自身行政区划信息
var countryCode = '';
var provincialCode = '';
var cityCode = '';
var districtCode = '';
var provincialName = '';
var cityName = '';
var districtName = '';
var baseLevelCode = '';
var baseAdminDivisionCode = '';
var isDirectCity = '';
var errorFlag = false;
//修改数据时，部分原数据内容初始化
var id = '';
var editInitProvCode = "";
var editInitCityCode = "";
var editInitDistCode = "";
var editInitLevel = "";

function doInit() {
    //数据初始化
    id = $('#id').val();
    baseLevelCode = $('#baseLevelCode').val();
    editInitLevel = $('#editInitLevel').val();
    baseAdminDivisionCode = $('#baseAdminDivisionCode').val();
    editInitProvCode = $('#editInitProvCode').val();
    editInitCityCode = $('#editInitCityCode').val();
    editInitDistCode = $('#editInitDistCode').val();
    //初始化页面元素
    initPageElement();
}

/*
 * 初始化页面元素
 */
function initPageElement() {
    // 获取操作用户本身层级信息
    var params = {
        code : baseAdminDivisionCode
    };
    var baseResult = tools.requestJsonRs(
            "/adminDivisionManageCtrl/getAdminDivisionByCode.action", params);
    var baseInfo = baseResult.rtData;
    console.log(baseInfo);
    provincialCode = baseInfo.provincialCode;
    provincialName = baseInfo.provincialName;
    cityCode = baseInfo.cityCode;
    cityName = baseInfo.cityName;
    districtCode = baseInfo.districtCode;
    districtName = baseInfo.districtName; 
    // 初始radiobutton
    initRadioButton();
    // 初始化部门层级
    initLevelCode(isDirectCity);
    if(editInitLevel != null && editInitLevel != ''){
        handleDivisionHideCtrl(editInitLevel);
    }
    // 初始化省、市、区
    // 初始化所属各级行政区划combobox
    handleInitProvincial(baseInfo); 
    handleInitCity(baseInfo);
    handleInitDistrict(baseInfo);
}

/*
 * 初始化radiobutton
 */
function initRadioButton() {
    // 初始化radiobutton
    $('#isHaveGov_yes').radiobutton({
        width : 15,
        height : 15,
        value : '1',
        label : '是',
        labelPosition : 'after',
        labelAlign : 'left',
        labelWidth : 40
    });
    $('#isHaveGov_no').radiobutton({
        width : 15,
        height : 15,
        value : '0',
        label : '否',
        labelPosition : 'after',
        labelAlign : 'left',
        labelWidth : 40
    });
}

/*
 * 初始化所属省页面元素 @param baseInfo
 */
function initLevelCode(isBaseDirectCity) {
    var params = {
        parentCodeNo : "DEPT_LEVEL",
        codeNo : "",
        panelHeight : 'auto'

    };
    var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action",
            params);
    var adminLevelGroup = result.rtData;
    for (var i = 0; i < adminLevelGroup.length; i++) {
        if (adminLevelGroup[i].codeNo == baseLevelCode) {
            if (isBaseDirectCity == '1') {
                // 只能建立本级以下的行政区划
                // 直辖市需要再向下沉一级
                adminLevelGroup.splice(0, i + 2);
            } else {
                // 只能建立本级以下的行政区划
                adminLevelGroup.splice(0, i + 1);
            }
        }
    }
    $('#levelCode').combobox({
        data : adminLevelGroup,
        valueField : 'codeNo',
        textField : 'codeName',
        panelHeight : 'auto',
        prompt : '请选择',
        editable : false,
        onLoadSuccess : function() {
            if(editInitLevel != null && editInitLevel != ''){
                $('#levelCode').combobox('setValue', editInitLevel);
            }
        },
        onChange : function() {
            var levelCode = $('#levelCode').combobox('getValue');
            handleDivisionHideCtrl(levelCode);
        }
    });
}

/*
 * 行政区划select跟随区划层级，显隐控制
 */
function handleDivisionHideCtrl(levelCode){
    if (levelCode == '0100') {
        // 国家部委级区划
        errorFlag = true;
    } else if (levelCode == '0200') {
        // 省级区划
        $('.editArea-provincial').hide();
        $('.editArea-city').hide();
        $('.editArea-district').hide();
    } else if (levelCode == '0300') {
        // 市级区划
        $('.editArea-provincial').show();
        $('.editArea-city').hide();
        $('.editArea-district').hide();
    } else if (levelCode == '0400') {
        // 区县区划
        $('.editArea-provincial').show();
        $('.editArea-city').show();
        $('.editArea-district').hide();
    } else if (levelCode == '0500') {
        // 乡镇街道区划
        $('.editArea-provincial').show();
        $('.editArea-city').show();
        $('.editArea-district').show();
    } else {
        errorFlag = true;
    }
}
/*
 * 初始化所属省页面元素 @param baseInfo
 */
function handleInitProvincial(baseInfo) {
    if (baseInfo.levelCode > '0100') {
//        $('.editArea-provincial.selfCtrl-div').html(baseInfo.provincialName);
    	$('#provincialCode').combobox('setValue', baseInfo.provincialName);
    } else {
        var baseResult = null;
        if (baseInfo.levelCode == '0100') {
            var params = {
                baseAdminDivisionCode : baseInfo.adminDivisionCode
            };
            baseResult = tools.requestJsonRs("/adminDivisionManageCtrl/getAdminDivisionListByCode.action", params);
        }
        $('#provincialCode').combobox({
            data : baseResult,
            valueField : 'code',
            textField : 'name',
            prompt : '请选择',
            editable : false,
            onLoadSuccess : function() {
                if(editInitLevel != null && editInitLevel > baseLevelCode){
                    if(editInitProvCode != null && editInitProvCode != ''){
                        $('#provincialCode').combobox('setValue', editInitProvCode);
                    }
                }
            },
            onChange : function() {
                provincialCode = $('#provincialCode').combobox('getValue');
                provincialName = $('#provincialCode').combobox('getText');
                var params = {
                        baseAdminDivisionCode : provincialCode
                    };
                $('#cityCode').combobox('clear');
                $('#cityCode').combobox('reload', 
                        contextPath + "/adminDivisionManageCtrl/getAdminDivisionListByCode.action?" + $.param(params));
                $('#districtCode').combobox('clear');
                cityCode = '';
                cityName = '';
                districtCode = '';
                districtName = '';
            }
        });
    }
}

/*
 * 初始化所属市州页面元素 @param baseInfo
 */
function handleInitCity(baseInfo) {
    if (baseInfo.levelCode > '0200') {
//        $('.editArea-city.selfCtrl-div').html(baseInfo.cityName);
    	$('#cityCode').combobox('setValue', baseInfo.cityName);
    } else {
        var baseResult = null;
        if (baseInfo.levelCode == '0200') {
            var params = {
                baseAdminDivisionCode : baseInfo.adminDivisionCode
            };
            baseResult = tools
                    .requestJsonRs(
                            "/adminDivisionManageCtrl/getAdminDivisionListByCode.action",
                            params);
        }
        $('#cityCode').combobox({
            data : baseResult,
            valueField : 'code',
            textField : 'name',
            panelHeight : '130px',
            prompt : '请选择',
            editable : false,
            onLoadSuccess : function() {
                if(editInitCityCode != null && editInitCityCode != ''){
                    $('#cityCode').combobox('setValue', editInitCityCode);
                }
            },
            onChange : function() {
                cityCode = $('#cityCode').combobox('getValue');
                cityName = $('#cityCode').combobox('getText');
                $('#districtCode').combobox('clear');
                var params = {
                    baseAdminDivisionCode : cityCode
                };
                districtCode = '';
                districtName = '';
                $('#districtCode').combobox('reload', 
                        contextPath + "/adminDivisionManageCtrl/getAdminDivisionListByCode.action?" + $.param(params));
            }
        });
    }
}
/*
 * 初始化区县页面元素 @param baseInfo
 */
function handleInitDistrict(baseInfo) {
    if (baseInfo.levelCode > '0300') {
        $('.editArea-district.selfCtrl-div').html(baseInfo.districtName);
    } else {
        var baseResult = null;
        if (baseInfo.levelCode == '0300') {
            var params = {
                baseAdminDivisionCode : baseInfo.adminDivisionCode
            };
            baseResult = tools
                    .requestJsonRs(
                            "/adminDivisionManageCtrl/getAdminDivisionListByCode.action",
                            params);
        }
        $('#districtCode').combobox({
            data : baseResult,
            valueField : 'code',
            textField : 'name',
            panelHeight : '60px',
            prompt : '请选择',
            editable : false,
            onLoadSuccess : function() {
                if(editInitDistCode != null && editInitDistCode != ''){
                    $('#districtCode').combobox('setValue', editInitDistCode);
                }
            },
            onChange : function() {
                districtCode = $('#districtCode').combobox('getValue');
                districtName = $('#districtCode').combobox('getText');
            }
        });
    }
}
/*
 * 新增
 */
function save() {
	if($('#adminDivisionEdit_form').form('enableValidation').form('validate')){	
	    var params = tools.formToJson($('#adminDivisionEdit_form'));
	    var finalParams = {
	        id : id
	    };
	    params.provincialCode = provincialCode;
	    params.provincialName = provincialName;
	    params.cityCode = cityCode;
	    params.cityName = cityName;
	    params.districtCode = districtCode;
	    params.districtName = districtName;
	
	    if (params.levelCode == '0200') {
	        params.provincialCode = params.adminDivisionCode;
	        params.provincialName = params.adminDivisionName;
	        params.cityCode = '';
	        params.districtCode = '';
	    }
	    if (params.levelCode == '0300') {
	        params.cityCode = params.adminDivisionCode;
	        params.cityName = params.adminDivisionName;
	        params.districtCode = '';
	    }
	    if (params.levelCode == '0400') {
	        params.districtCode = params.adminDivisionCode;
	        params.districtName = params.adminDivisionName;
	    }
	    if (params.levelCode == '0500') {
	        params.streetCode = params.adminDivisionCode;
	        params.streetName = params.adminDivisionName;
	    }
	    var json = tools.requestJsonRs("/adminDivisionManageCtrl/save.action",
	            params);
	    if (json.rtState) {
	        $.MsgBox.Alert_auto("保存成功");
	        return true;
	    } else {
	        $.MsgBox.Alert_auto("保存失败,请填写必填项");
	        return false;
	    }
	}
}