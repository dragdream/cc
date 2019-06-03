//数据部分
var invest_listdata = {
    isShowColumn: [
        { name: '张春江', time: '2019', type: '问卷', name2: '张夏江', phone: '1030'},
        { name: '张春江', time: '2019', type: '问卷', name2: '张夏江', phone: '1030' }
    ]
}

//模板引擎部分
//申请人表格
var invest_tpl = [
    '<tr class="">',
    '<th class="">序号</th>',
    '<th class="">调查人姓名</th>',
    '<th class="">调查时间</th>',
    '<th class="">调查方式</th>',
    '<th class="">被调查人姓名</th>',
    '<th class="">被调查人电话</th>',
    '<th class="">操作</th>',
    '</tr>',
    '{@each isShowColumn as it, index}',
    '<tr class="">',
    '<td class="indexTd"></td>',
    '<td class="">${it.name}</td>',
    '<td class="">${it.time}</td>',
    '<td class="">${it.type}</td>',
    '<td class="">${it.name2}</td>',
    '<td class="">${it.phone}</td>',
    '<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="编辑" title="编辑" onclick="editRow(this);" /></a>',
    '<a><input id="editUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteRow(this);" /></a></td>',
    '</tr>',
    '{@/each}'

].join('\n');
$("#invest-table").append(juicer(invest_tpl, invest_listdata));

//列表添加功能
function invest_insertRow() {
    // $("#invest-panel").append('<tr class="">' +
    //     '<td class="indexTd"></td>' +
    //     '<td class=""><input type="text" value=""/></td>' +
    //     '<td class=""><select class=""><option>1</option><option>2</option></select></td>' +
    //     '<td class=""><input type="text" value=""/></td>' +
    //     '<td class=""><select class=""><option>男</option><option>女</option></select></td>' +
    //     '<td class=""><input type="text" value=""/></td>' +
    //     '<td class=""><input type="text" value=""/></td>' +
    //     '<td class=""><input type="text" value=""/></td>' +
    //     '<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteRow(this);" /></a></td>' +
    //     '</tr>');
    // updateIndex();
}

//初始化
$(document).ready(function() {
    updateIndex();
    if (invest_listdata.isShowColumn.length > 0) {
        $('.invest-agent-div').show();
    } else {
        $('.invest-agent-div').hide();
    }
});

//公共列表删除功能
function deleteRow(that) {
    $(that).parents('tr').remove();
    updateIndex();
}
//公共更新列表序号
function updateIndex() {
    $('.edit-table').each(function() {
        var index = 0;
        var that = this;
        $(that).find(".indexTd").each(function() {
            index++;
            $(this).text(index);

        });
    });
}

//调查取证上传材料
function uploadFile(that) {
    $('#proof-table tbody').append('<tr class="">' +
        '<td class="td-col4-title"</td>' +
        '<td colspan="3" class="td-col4-content">' +
        '<span>aaa.doc</span><span>上传人：</span><span>trump;2019-1-1</span><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteRow(this);" /></a>' +
        '</td>' +
        '</tr>');
}