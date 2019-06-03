var datagrid;

function doInit() {
	doSearchLawDetail();
}

function doSearchLawDetail() {
    var param = {
    	subjectId: subjectId,
    	examine: 1
    };
    doInitDatagrid(param);
}

function doInitDatagrid(param) {
    datagrid = $('#selectStaffTable').datagrid({
        url: contextPath + '/caseCommonStaffCtrl/findListBySubjectIdAndPerson.action',
        queryParams: param,
        pagination: true,
        singleSelect: false,
        pageSize : 20,
        pageList : [ 10, 20, 50, 100 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar: '#toolbar',
        // 工具条对象
        checkbox: true,
        border: false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns: true,
        // 列是否进行自动宽度适应
        nowrap: true,
        onLoadSuccess: function(data) {
        },
        columns: [[{
            field: 'id',
            checkbox: true,
            title: "ID",
            halign: 'center', align: 'center',
            width: 20
        },
        {
        	field:'ID',
        	title:'序号',
        	align:'center',
            formatter:function(value,rowData,rowIndex){
                return rowIndex+1;
            }
        },
        {
            field: 'subjectName',
            title: '主体',
            width: 100,
            halign: 'center', align: 'left',
            formatter: function(value, rowData) {
                if(value == null || value == 'null') {
                    value = "";
                }
                var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                return lins;
            }
        },
        {
            field: 'name',
            title: '姓名',
            halign: 'center', align: 'center',
            width: 100
        },
        {
            field: 'code',
            title: '执法证号',
            halign: 'center', align: 'center',
            width: 100
        }]]
    });
}

function doSearchAddStaff() {
    var name = $('#common_case_addPerson_name').val();
    var enforceCode = $('#common_case_addPerson_code').val();
    var params = {
        subjectId: subjectId,
        name: name,
        enforceCode: enforceCode,
        examine: 1
    };
    $('#selectStaffTable').datagrid('reload',params);
}

function saveLawDetails () {
    var lawDetails = $('#selectStaffTable').datagrid('getSelections');
    return lawDetails;
} 