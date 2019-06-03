/*初始化*/
var tree;
function doInit() {
    initTree('838d49ad-3950-7bac-e050-a8c0fb140edc');
}

function initTree(params){
    tree = $('#testTree').tree({
        url : contextPath
        + '/departmentInfoController/buildDepartmentTree.action',
        method: 'post',
        cascadeCheck:true,
        checkbox:false
//        data:[{
//            id:'838d49ad-3950-7bac-e050-a8c0fb140edc',
//            text: '中央人民政府',
//            state: 'open'
//        }]
    });
}

