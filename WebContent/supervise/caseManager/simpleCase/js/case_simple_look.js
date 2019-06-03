var params;

function doInit(){
    var json = tools.requestJsonRs("/caseSimpleBaseCtrl/findSimpleBaseById.action?id=" + $("#id").val());
    if(json.rtState) {
        bindJsonObj2Cntrl(json.rtData);
        /// 特殊字段处理 
        // 执法人员处理
        if(json.rtData.staffIds != null && json.rtData.staffIds != ''){
            params = {
                ids: json.rtData.staffIds
            };
        }else{
            params = {
                ids: 'empty'
            };
        }
        var partyType = json.rtData.partyType;
    	if(partyType == '02' || partyType == "03"){
        	$('#adressTr').show();
        }else{
            $('#adressTr').hide();
        }
        initIndexDatagrid(params);
        // 违法行为处理
        if (json.rtData.powerIds != null && json.rtData.powerIds.length > 0) {
            params = {
                ids: json.rtData.powerIds
            };
        }else{
            params = {
                ids: 'empty'
            };
        }
        initPowerDatagrid(params);
        // 违法依据处理
        if (json.rtData.gistIds != null && json.rtData.gistIds.length > 0) {
            params = {
                id: json.rtData.gistIds,
                gistType : '01'
            };
        }else{
            params = {
                id: 'empty',
                gistType : '01'
            };
        }
        initGistDatagrid(params);
        // 处罚依据处理
        if (json.rtData.punishIds != null && json.rtData.punishIds.length > 0) {
            params = {
                id: json.rtData.punishIds,
                gistType : '02'
            };
        }else{
            params = {
                id: 'empty',
                gistType : '02'
            };
        }
        initPunishDatagrid(params);
    }
}


/**
 * 表格加载函数
 * @returns
 */
function initIndexDatagrid(params){
    datagrid = $('#case_simple_add_1_datagrid').datagrid({
        url: contextPath + '/caseSimpleStaffCtrl/findListBypage.action',
        queryParams: params,
        pagination: false,
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar: '#toolbar', // 工具条对象
        checkbox: true,
        border: false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns: true, // 列是否进行自动宽度适应
        singleSelect: false, //为true只能选择一行
        nowrap: true,
        columns: [[
            {field:'ID',title:'序号',align:'center',width : 5,
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field: 'subjectName', title: '主体', width: 20, halign: 'center', align: 'left',
                formatter: function(value, rowData) {
                    if(value == null || value == 'null') {
                        value = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                    return lins;
                }
            },
            {
                field: 'name', title: '人员 ', width: 12, halign: 'center', align: 'center'
            },
            {
                field: 'code', title: '执法证号 ', width: 12, halign: 'center', align: 'center'
            }
        ]]
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
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar : '#toolbar', // 工具条对象
        checkbox : true,
        border : false,
        striped : true,// 隔行变色
        fitColumns : true, // 列是否进行自动宽度适应
        singleSelect : false, // 为true只能选择一行
        nowrap : true,
        columns : [ [
            {
                field:'ID',
                title:'序号',
                align:'center',
                width : 5,
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
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
                field : 'name',
                title : '违法行为',
                width : 60,
                halign : 'center',
                align : 'left',
                formatter : function(value, rowData) {
                    if (value == null || value == 'null') {
                        value = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='" + value + "'>" + value + "</lable>"
                    return lins;
                }
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
        url : contextPath + '/powerCtrl/findGistsByPowerIds.action',
        queryParams : params,
        pagination : false,
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar : '#toolbar', // 工具条对象
        checkbox : true,
        border : false,
        striped: true,//隔行变色
        fitColumns : true, // 列是否进行自动宽度适应
        singleSelect : false, // 为true只能选择一行
        nowrap : true,
        columns : [ [
            {field:'ID',title:'序号',align:'center',width : 5,
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field : 'lawName',
                title : '法律名称',
                width : 35,
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
                width : 5,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'gistFund',
                title : '款 ',
                width : 5,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'gistItem',
                title : '项 ',
                width : 5,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'gistCatalog',
                title : '目 ',
                width : 5,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'content',
                title : '内容 ',
                width : 73,
                halign : 'center',
                align : 'left',
                formatter: function(value, rowData) {
                    if(value == null || value == 'null') {
                        value = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                    return lins;
                }
            }
        ] ]
    });
}

/**
 * 处罚依据表格加载函数
 * 
 * @returns
 */
function initPunishDatagrid(params) {
    datagrid = $('#case_simple_punish_datagrid').datagrid({
        url : contextPath + '/powerCtrl/findGistsByPowerIds.action',
        queryParams : params,
        pagination : false,
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar : '#toolbar', // 工具条对象
        checkbox : true,
        border : false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns : true, // 列是否进行自动宽度适应
        singleSelect : false, // 为true只能选择一行
        nowrap : true,
        columns : [ [
            {
                field:'ID',title:'序号',align:'center',width : 5,
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field : 'lawName',
                title : '法律名称',
                width : 35,
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
                width : 5,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'gistFund',
                title : '款 ',
                width : 5,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'gistItem',
                title : '项 ',
                width : 5,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'gistCatalog',
                title : '目 ',
                width : 5,
                halign : 'center',
                align : 'center'
            },
            {
                field : 'content',
                title : '内容 ',
                width : 73,
                halign : 'center',
                align : 'left',
                formatter: function(value, rowData) {
                    if(value == null || value == 'null') {
                        value = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                    return lins;
                }
            }
        ] ]
    });
}

function back(){
	location.href = "/caseSimpleBaseCtrl/simpleCaseIndex.action";
}