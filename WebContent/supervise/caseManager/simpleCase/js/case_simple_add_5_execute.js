var powerIds = [];
var returnPunishids;
var returnGistids;
var modelId = $('#modelId').val();//弹框ID
var editFlag = $("#editFlag").val();
var caseId = $("#caseId").val();
/**
 * 默认加载方法
 */
function doInitExecute() {
	if (editFlag=='2'||editFlag=='3') {
        //修改和查看初始化页面数据
        doInitEditPage();
    }else{
        initPowerDatagrid({ids: 'empty'});
    }
    if (editFlag=="3") {
        $('#doSave').linkbutton('disable');
        $('#doSaveSumit').linkbutton('disable');
    }
}

function initGistAndPunish(params) {
    initGistDatagrid(params);
    initPunishDatagrid(params);
}

/**
 * 打开添加违法行为页面
 * 
 * @returns
 */
function doAddGistLawDetail() {
    var subjectId = parent.subjectId;
    if (subjectId == "") {
        alert("请选择执法主体");
        return;
    }
    var powerType = '01';
    var url=contextPath+"/caseCommonPowerCtrl/commonCaseAddPower.action?actSubject="+subjectId+"&powerType="+powerType;
    top.bsWindow(url, "添加违法行为", {
        width : "1000",
        height : "450",
        buttons : [ {
            name : "关闭",
            classStyle : "btn-alert-gray"
        }, {
            name : "保存",
            classStyle : "btn-alert-blue"
        } ],
        submit : function(v, h) {
            var cw = h[0].contentWindow;
            if (v == "保存") {
                var lawDetails = cw.getSelectedPower();
                if(lawDetails != null && lawDetails.length > 0){
                    if (powerIds == null || powerIds.length == 0) {
                        for ( var index in lawDetails) {
                            powerIds.push(lawDetails[index].id);
                        }
                    } else {
                        for ( var index in lawDetails) {
                            if (powerIds.indexOf(lawDetails[index].id) == -1) {
                                powerIds.push(lawDetails[index].id);
                            }
                        }
                    }
                }
                var params;
                if(powerIds != null && powerIds.length > 0){
                    params = {
                            ids : powerIds.join(",")
                        };
                }else {
                    params = {
                            ids : 'empty'
                        };
                }
                // alert(tools.parseJson2String(params));
                // doInitGistLawDetailGridByIds(params);
                initPowerDatagrid(params);
                return true;
            } else if (v == "关闭") {
                return true;
            }
        }
    });
}

/**
 * 违法行为表格加载函数
 * 
 * @returns
 */
function initPowerDatagrid(params) {
    datagrid = $('#case_simple_add_power_datagrid').datagrid({
        url : contextPath + '/caseSimplePowerCtrl/findListBypage.action',
        queryParams : params,
        pagination : false,
        // pageSize : 15,
        // pageList : [ 15, 20, 30, 40, 50, 60, 70, 80, 90, 100],
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar : '#toolbar', // 工具条对象
        checkbox : true,
        border : false,
        striped : true,// 隔行变色
        /* idField:'formId',//主键列 */
        fitColumns : true, // 列是否进行自动宽度适应
        singleSelect : false, // 为true只能选择一行
        nowrap : true,
        onLoadSuccess : function(data) {
            $('#case_simple_add_power_datagrid').datagrid('selectAll');
            powerIds = [];
            for (var i = 0; i < data.rows.length; i++) {
                powerIds.push(data.rows[i].id);
            }
            var params;
            if (powerIds != null && powerIds != '') {
                params = {
                	powerId : powerIds.join(",")
                };
            }else{
                params = {
                	powerId : 'empty'
                };
            }
            initGistAndPunish(params);
        },
        columns : [ [
            {
            	field:'ID',
            	title:'序号',
            	align:'center',
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field : 'name',
                title : '违法行为',
                width : 60,
                halign : 'center',
                align : 'left',
                formatter : function(value, rowData) {
                    if (value == null || value == 'null') {
                        value = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"
                    	+ value + "'>" + value + "</lable>"
                    return lins;
                }
            },
            {
                field : 'code',
                title : '职权编号 ',
                width : 12,
                halign : 'center',
                align : 'center'
            },
            {
                field : '___',
                title : '操作',
                halign : 'center',
                align : 'center',
                formatter : function(e, rowData) {
                    var optStr = "&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='deleteRow(\"" + rowData.id
                    	+ "\")' href='javascript:void(0);' ><i class='fa fa-trash'></i> </a>&nbsp;&nbsp;&nbsp;&nbsp;";
                    return optStr;
                },
                width : 10
            }
        ]]
    });
}

/**
 * 违法依据表格加载函数
 * 
 * @returns
 */
function initGistDatagrid(params) {
    datagrid = $('#case_simple_gist_datagrid').datagrid({
        url : contextPath + '/caseSimpleGistCtrl/findListBypage.action',
        queryParams : params,
        pagination : false,
        // pageSize : 15,
        // pageList : [ 15, 20, 30, 40, 50, 60, 70, 80, 90, 100],
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar : '#toolbar', // 工具条对象
        checkbox : true,
        border : false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns : true, // 列是否进行自动宽度适应
        singleSelect : false, // 为true只能选择一行
        nowrap : true,
        onLoadSuccess : function(data) {
            if (editFlag=='2'||editFlag=='3') {
                initGist(returnGistids);
            }else{
                $('#case_simple_gist_datagrid').datagrid('selectAll');
            }
        },
        columns : [ [
        	{
                field: 'id', checkbox: true, title: "ID",
                width: 20, halign: 'center', align: 'center'
            },
            {field:'ID',title:'序号',align:'center',
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field : 'lawName',
                title : '法律名称',
                width : 120,
                halign : 'center',
                align : 'left',
                formatter: function(value, rowData) {
                    if(value == null || value == 'null') {
                        value = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                    return lins;
                }
            },
            {
                field : 'gistStrip',
                title : '条 ',
                width : 15,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'gistFund',
                title : '款 ',
                width : 15,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'gistItem',
                title : '项 ',
                width : 15,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'gistCatalog',
                title : '目 ',
                width : 15,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'content',
                title : '内容 ',
                width : 200,
                halign : 'center',
                align : 'left',
                formatter: function(value, rowData) {
                    if(value == null || value == 'null') {
                        value = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                    return lins;
                }
            }]
        ]
    });
}

/**
 * 处罚依据表格加载函数
 * 
 * @returns
 */
function initPunishDatagrid(params) {
    datagrid = $('#case_simple_punish_datagrid').datagrid({
        url : contextPath + '/caseSimplePunishCtrl/findListBypage.action',
        queryParams : params,
        pagination : false,
        // pageSize : 15,
        // pageList : [ 15, 20, 30, 40, 50, 60, 70, 80, 90, 100],
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar : '#toolbar', // 工具条对象
        checkbox : true,
        border : false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns : true, // 列是否进行自动宽度适应
        singleSelect : false, // 为true只能选择一行
        nowrap : true,
        onLoadSuccess : function(data) {
            if (editFlag=='2'||editFlag=='3') {
            	initPunish(returnPunishids);
            }else{
                $('#case_simple_punish_datagrid').datagrid('selectAll');
            }
        },
        columns : [ [
        	{
                field: 'id', checkbox: true, title: "ID",
                width: 20, halign: 'center', align: 'center'
            },
            {field:'ID',title:'序号',align:'center',
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field : 'lawName',
                title : '法律名称',
                width : 120,
                halign : 'center',
                align : 'left',
                formatter: function(value, rowData) {
                    if(value == null || value == 'null') {
                        value = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                    return lins;
                }
            },
            {
                field : 'gistStrip',
                title : '条 ',
                width : 15,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'gistFund',
                title : '款 ',
                width : 15,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'gistItem',
                title : '项 ',
                width : 15,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'gistCatalog',
                title : '目 ',
                width : 15,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'content',
                title : '内容 ',
                width : 200,
                halign : 'center',
                align : 'left',
                formatter: function(value, rowData) {
                    if(value == null || value == 'null') {
                        value = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                    return lins;
                }
            }]
        ]
    });
}

function doSave(){
	if (editFlag=='2') {
        //违法行为
        var powerRows = $('#case_simple_add_power_datagrid').datagrid('getSelections');
        if(powerRows == null || powerRows.length < 1){
            $.MsgBox.Alert_auto("请添加至少1条违法行为");
            $('html, body').animate({scrollTop: $("#case_simple_add_power_datagrid_div").offset().top}, 500);
            return false;
        }
        var powIds = [];
        for (var i = 0; i < powerRows.length; i++) {
            powIds.push(powerRows[i].id);
        }
        parent.filingForm.powerIds = powIds+"";
        //违法依据
        var gistRows = $('#case_simple_gist_datagrid').datagrid('getSelections');
        if(gistRows == null || gistRows.length < 1){
            $.MsgBox.Alert_auto("请添加至少1条违法依据");
            $('html, body').animate({scrollTop: $("#case_simple_gist_datagrid_div").offset().top}, 500);
            return false;
        }
        var gistIds = [];
        for (var i = 0; i < gistRows.length; i++) {
            gistIds.push(gistRows[i].id);
        }
        parent.filingForm.gistIds = gistIds+"";
        //处罚依据
        var punishRows = $('#case_simple_punish_datagrid').datagrid('getSelections');
        if(punishRows == null || punishRows.length < 1){
            $.MsgBox.Alert_auto("请添加至少1条处罚依据");
            $('html, body').animate({scrollTop: $("#case_simple_punish_datagrid_div").offset().top}, 500);
            return false;
        }
        var punishIds = [];
        for (var i = 0; i < punishRows.length; i++) {
            punishIds.push(punishRows[i].id);
        }
        parent.filingForm.punishIds = punishIds+"";
        parent.filingForm.isSubmit = 0;
        return true;
    }else{// 新增
    	var powerRows = $('#case_simple_add_power_datagrid').datagrid('getSelections');
        if(powerRows == null || powerRows.length < 1){
            $.MsgBox.Alert_auto("请添加至少1条违法行为");
            $('html, body').animate({scrollTop: $("#case_simple_add_power_datagrid_div").offset().top}, 500);
            return false;
        }
    	//违法依据
        var gistRows = $('#case_simple_gist_datagrid').datagrid('getSelections');
        if(gistRows == null || gistRows.length < 1){
            $.MsgBox.Alert_auto("请添加至少1条违法依据");
            $('html, body').animate({scrollTop: $("#case_simple_gist_datagrid_div").offset().top}, 500);
            return false;
        }
        var gistIds = [];
        for (var i = 0; i < gistRows.length; i++) {
            gistIds.push(gistRows[i].id);
        }
        parent.filingForm.gistIds = gistIds+"";
        //处罚依据
        var punishRows = $('#case_simple_punish_datagrid').datagrid('getSelections');
        if(punishRows == null || punishRows.length < 1){
            $.MsgBox.Alert_auto("请添加至少1条处罚依据");
            $('html, body').animate({scrollTop: $("#case_simple_punish_datagrid_div").offset().top}, 500);
            return false;
        }
        var punishIds = [];
        for (var i = 0; i < punishRows.length; i++) {
            punishIds.push(punishRows[i].id);
        }
        parent.filingForm.punishIds = punishIds+"";
        parent.filingForm.powerIds = powerIds+"";
        parent.filingForm.isSubmit = 0;
        return true;
    }
}
/**
 * 修改时，加载数据
 */
function doInitEditPage(){
    var json = tools.requestJsonRs("/caseSimpleBaseCtrl/findSimpleBaseById.action?id=" + parent.filingForm.id);
    if(json.rtState) {
        returnPunishids = json.rtData.punishIds;
        returnGistids = json.rtData.gistIds;
        var params
        if (json.rtData.powerIds != null && json.rtData.powerIds != '') {
            powerIds = json.rtData.powerIds.split(",");
            params = {
                    ids : json.rtData.powerIds
            };
        }else {
            params = {
                    ids : 'empty'
            };
        }
        initPowerDatagrid(params);
    }
}

/**
 * 回写违法依据datagrid
 */
function initGist(returnGistids){
    if (editFlag=='2'||editFlag=='3') {
        var gistIds= new Array();
        if(returnGistids != null && returnGistids != ''){
            gistIds = returnGistids.split(",");
            var gistData = $('#case_simple_gist_datagrid').datagrid('getData');
            for(var i = 0 ; i< gistData.rows.length ; i++){
                for(var j = 0 ; j < gistIds.length ; j++){
                    if (gistData.rows[i].id == gistIds[j]) {
                        $('#case_simple_gist_datagrid').datagrid('selectRow',i);
                    }
                }
            }
        }
    }
}

function deleteRow(id){
    for (var i = 0 ; i< powerIds.length;i++){
        if (id == powerIds[i]) {
            powerIds.splice(i,1);
        }
    }
    var params;
    if (powerIds != null && powerIds.length > 0) {
        params = {
                ids: powerIds.join(",")
        };
    }else{
        params = {
                ids: 'empty'
        };
    }
    initPowerDatagrid(params);
}

/**
 * 回写处罚依据datagrid
 */
function initPunish(returnPunishids){
    if (editFlag=='2'||editFlag=='3') {
        var punishIds = new Array();
        if (returnPunishids != null && returnPunishids !='') {
            punishIds = returnPunishids.split(",");
            var punishData = $('#case_simple_punish_datagrid').datagrid('getData');
            for(var i = 0 ; i< punishData.rows.length ; i++){
                for(var j = 0 ; j < punishIds.length ; j++){
                    if (punishData.rows[i].id == punishIds[j]) {
                        $('#case_simple_punish_datagrid').datagrid('selectRow',i);
                    }
                }
            }
        }
    }
}