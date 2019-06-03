/*
 * 初始化数据
 * author:肖翔
 * date:2019-05-08
 */
var datagrid;

function doInit() {
    listDatagrid();
}

/*
 * 初始化以抽选人员的表格
 */
function listDatagrid() {
    datagrid = $('#choosed_people_datagrid').datagrid({
        // url : contextPath + '/casecheckPersonCtrl/listBypage.action',
        url: '../batch/json/person.json',
        pagination: true,
        singleSelect: true,
        striped: true,
        pageSize: 20,
        pageList: [10, 20, 50, 100],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        border: false,
        rownumbers: false,
        idField: 'id',// 主键列
        columns: [[
            {field: 'id', title: '', checkbox: true},
            {
                field: 'ID', title: '序号', width: '5%', align: 'center',
                formatter: function (value, rowData, rowIndex) {
                    return rowIndex + 1;
                }
            },
            {
                field: 'name', title: '姓名', width: '10%', align: 'center', halign: 'center', formatter:
                    function (value, rowData, rowIndex) {
                        if (value == null) {
                            return "";
                        } else {
                            if (rowData.isGroupLeader == 0) {
                                var optsStr = "<i class=\"fa fa-male\" aria-hidden=\"true\" style='color: red'></i>" + "&nbsp;" + "<span title=" + value + ">" + value + "</span>";
                                return optsStr;
                            }
                            var optsStr = "<span title=" + value + ">" + value + "</span>";
                            return optsStr;
                        }
                    },
            },
            {
                field: 'caseType', title: '评查案卷类型', width: '10%', align: 'center', halign: 'center', formatter:
                    function (value, rowData, rowIndex) {
                        if (value == null) {
                            return "";
                        } else {
                            switch (value) {
                                case 1:
                                    value = "行政检查";
                                    break;
                                case 2:
                                    value = "行政处罚";
                                    break;
                                case 3:
                                    value = "行政强制";
                                    break;
                                default:
                                    value = "";
                                    break;
                            }
                            var optsStr = "<span title=" + value + ">" + value + "</span>";
                            return optsStr;
                        }
                    },
            },
            {
                field: 'department', title: '所属部门', width: '18%', align: 'center', halign: 'center', formatter:
                    function (value, rowData, rowIndex) {
                        if (value == null) {
                            return "";
                        } else {
                            var optsStr = "<span title=" + value + ">" + value + "</span>";
                            return optsStr;
                        }
                    },
            },
            {
                field: 'sex', title: '性别', width: '6%', align: 'center', halign: 'center', formatter:
                    function (value, rowData, rowIndex) {
                        if (value == null) {
                            return "";
                        } else {
                            if (value == 0) {
                                value = "男";
                            } else if (value == 1) {
                                value = "女";
                            }
                            var optsStr = "<span title=" + value + ">" + value + "</span>";
                            return optsStr;
                        }
                    },
            },
            {
                field: 'IDCard', title: '身份证号', width: '18%', align: 'center', halign: 'center', formatter:
                    function (value, rowData, rowIndex) {
                        if (value == null) {
                            return "";
                        } else {
                            var optsStr = "<span title=" + value + ">" + value + "</span>";
                            return optsStr;
                        }
                    },
            },
            {
                field: 'phoneNumber', title: '移动电话', width: '14.5%', align: 'center', halign: 'center', formatter:
                    function (value, rowData, rowIndex) {
                        if (value == null) {
                            return "";
                        } else {
                            var optsStr = "<span title=" + value + ">" + value + "</span>";
                            return optsStr;
                        }
                    },
            },
            {
                field: 'checkTime', title: '参评次数', width: '8%', align: 'center', halign: 'center', formatter:
                    function (value, rowData, rowIndex) {
                        if (value == null) {
                            return "";
                        } else {
                            var optsStr = "<span title=" + value + ">" + value + "</span>";
                            return optsStr;
                        }
                    },
            },
            {
                field: 'isGroupLeader', title: '是否为组长', width: '10%', align: 'center', halign: 'center', formatter:
                    function (value, rowData, rowIndex) {
                        if (value == null) {
                            return "";
                        } else {
                            if (value == 0) {
                                value = "是";
                            } else {
                                value = "否";
                            }
                            var optsStr = "<span title=" + value + ">" + value + "</span>";
                            return optsStr;
                        }
                    },
            }
        ]],
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
    });
}

var groupsData;

function listGroup() {
    $.ajax({
        url: '../batch/json/groups.json',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            groupsData = data;
            var html = template('people-groups', groupsData);
            // console.log(html)
            $('#group-info-panel').html(html);
            $.parser.parse('#group-info-panel');
            for (var i = 1; i <= groupsData.groupsNumber; i++) {
                var groupDatagrid = '#group_datagrid' + i;
                var groupData = groupsData.groups[i - 1].members;
                var groupsNumber = groupsData.groupsNumber;
                listGroupDatagrid(groupDatagrid, groupData, groupsNumber);
                var height = (100 + (groupsData.groups[i - 1].members.length - 1) * 30) + 'px';
                var divId = '#div_group_datagrid' + i;
                setDivHeight(divId, height);
            }
        }
    });
}

/*
 *初始化分组表格
 */
function listGroupDatagrid(groupDatagrid, groupData, groupsNumber) {
    datagrid = $(groupDatagrid).datagrid({
        data: groupData,
        pagination: false,
        singleSelect: true,
        striped: true,
        pageSize: 20,
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        border: false,
        rownumbers: false,
        fit: true,
        idField: 'id',// 主键列
        fitColumns: true,// 列是否进行自动宽度适应
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        columns: [[
            {
                field: 'ID', title: '序号', width: '5%', align: 'center',
                formatter: function (value, rowData, rowIndex) {
                    return rowIndex + 1;
                }
            },
            {
                field: 'groupID', title: '调整分组', width: '13%', align: 'center',
                formatter: function (value, rowData, rowIndex) {
                    return addSelectBtn(value, rowData, rowIndex, groupsNumber);
                }
            },
            {
                field: 'name', title: '姓名', width: '10%', align: 'center', halign: 'center', formatter:
                    function (value, rowData, rowIndex) {
                        if (value == null) {
                            return "";
                        } else {
                            if (rowData.isGroupLeader == 0) {
                                var optsStr = "<i class=\"fa fa-male\" aria-hidden=\"true\" style='color: red'></i>" + "&nbsp;" + "<span title=" + value + ">" + value + "</span>";
                                return optsStr;
                            }
                            var optsStr = "<span title=" + value + ">" + value + "</span>";
                            return optsStr;
                        }
                    },
            },
            {
                field: 'department', title: '所属部门', width: '18%', align: 'center', halign: 'center', formatter:
                    function (value, rowData, rowIndex) {
                        if (value == null) {
                            return "";
                        } else {
                            var optsStr = "<span title=" + value + ">" + value + "</span>";
                            return optsStr;
                        }
                    },
            },
            {
                field: 'caseType', title: '评查案卷类型', width: '12%', align: 'center', halign: 'center', formatter:
                    function (value, rowData, rowIndex) {
                        if (value == null) {
                            return "";
                        } else {
                            switch (value) {
                                case 1:
                                    value = "行政检查";
                                    break;
                                case 2:
                                    value = "行政处罚";
                                    break;
                                case 3:
                                    value = "行政强制";
                                    break;
                                default:
                                    value = "";
                                    break;
                            }
                            var optsStr = "<span title=" + value + ">" + value + "</span>";
                            return optsStr;
                        }
                    },
            },
            {
                field: 'sex', title: '性别', width: '6%', align: 'center', halign: 'center', formatter:
                    function (value, rowData, rowIndex) {
                        if (value == null) {
                            return "";
                        } else {
                            if (value == 0) {
                                value = "男";
                            } else if (value == 1) {
                                value = "女";
                            }
                            var optsStr = "<span title=" + value + ">" + value + "</span>";
                            return optsStr;
                        }
                    },
            },
            {
                field: 'IDCard', title: '身份证号', width: '17%', align: 'center', halign: 'center', formatter:
                    function (value, rowData, rowIndex) {
                        if (value == null) {
                            return "";
                        } else {
                            var optsStr = "<span title=" + value + ">" + value + "</span>";
                            return optsStr;
                        }
                    },
            },
            {
                field: 'isGroupLeader', title: '成员角色', width: '10%', align: 'center', halign: 'center', formatter:
                    function (value, rowData, rowIndex) {
                        if (value == null) {
                            return "";
                        } else {
                            if (value == 0) {
                                value = "组长";
                            } else {
                                value = "组员";
                            }
                            var optsStr = "<span title=" + value + ">" + value + "</span>";
                            return optsStr;
                        }
                    },
            },
            {
                field: 'operation', title: '操作', width: '10%', align: 'center', halign: 'center', formatter:
                    function (value, rowData, rowIndex) {
                        var optsStr = addDelBtn(rowData, groupData,rowIndex);
                        return optsStr;
                    }
            }
        ]],
    })
}

/*
 * 修改表格所占div的高度
 */
function setDivHeight(divId, height) {
    $(divId).css('height', height);
}

/**
 * 分组后组内成员删除按钮
 */
function delMember() {

}

/**
 * 分组后组内成员改变组的按钮
 */
function changeGroup() {

}

/**
 * 批量删除已选人员
 */
function delPeople(){
    
}

/**
 * 判断删除按钮是否可用，并返回相应的按钮的str
 */
function addDelBtn(rowData, groupData,rowIndex) {
    if (rowData.isGroupLeader == 0 && groupData.length > 1) {
        var optsStr = "<button id=" + "group" + rowData.groupID + "_item" + (rowIndex + 1) + "_delete_btn" + " class='easyui-linkbutton btn-member-delete-disable' disabled='true'>" +
            "<i class='fa fa-trash-o'></i>" + "删除" + "</button>";
        return optsStr;
    } else {
        var optsStr = "<button id=" + "group" + rowData.groupID + "_item" + (rowIndex + 1) + "_delete_btn" + " class = 'easyui-linkbutton btn-member-delete'onclick = '' > " +
            "<i class='fa fa-trash-o'></i>" + "删除" + "</button>";
        return optsStr;
    }
}

/*
 *判断下拉框是否可用，并返回相应的按钮的str
 */
function addSelectBtn(value, rowData, rowIndex, groupsNumber) {
    var options;
    for (var i = 1; i <= groupsNumber; i++) {
        if (i == value) {
            options += "<option value=" + i + " selected='selected'" + ">" + "第" + i + "组</option>";
        } else {
            options += "<option value=" + i + ">" + "第" + i + "组</option>";
        }
    }
    if (rowData.isGroupLeader != 0) {
        var optsStr = "<select id=" + "group" + rowData.groupID + "_item" + (rowIndex + 1) + "_select_btn" + " class='easyui-combobox' style='width:80px;height: 25px;padding-left: 10px;'> " + options + "</select>";
        return optsStr;
    } else {
        var optsStr = "<select id=" + "group" + rowData.groupID + "_item" + (rowIndex + 1) + "_select_btn" + " class='easyui-combobox' style='width:80px;height: 25px;padding-left: 10px;' disabled='true'> " + options + "</select>";
        return optsStr;
    }
}