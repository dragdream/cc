var datagrid;
var subjectId = $('#common_case_addPerson_subjectId').val();//主体ID

function doInitAddPerson() {
    var params = {
        subjectId: subjectId,
        examine: 1
    };
    doInitAddPersonDatagrid(params);
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
    $('#select_common_case_addPerson_table').datagrid('reload',params);
}

function doInitAddPersonDatagrid(params) {
    datagrid = $('#select_common_case_addPerson_table').datagrid({
        url: contextPath + '/caseCommonStaffCtrl/findListBySubjectIdAndPerson.action',
        queryParams: params,
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
            width: 20
        },
        {field:'ID',title:'序号',align:'center',
            formatter:function(value,rowData,rowIndex){
                return rowIndex+1;
            }
        },
        {
            field: 'subjectName',
            title: '所属主体',
            halign: 'center', 
            align: 'left',
            width: 100,
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
            title: '执法人员',
            halign: 'center', 
            align: 'center',
            width: 100
        },
        {
            field: 'code',
            title: '执法证号',
            halign: 'center', 
            align: 'center',
            width: 100
        }]]
    });
}

function savePersonList() {
    var personList = $('#select_common_case_addPerson_table').datagrid('getSelections');
    return personList;
}