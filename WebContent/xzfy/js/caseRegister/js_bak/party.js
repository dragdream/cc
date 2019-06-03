//数据部分
var applicant_listdata = {
    isShowColumn: [
        { name: '张春江', type: '居民身份证', number: '2334234', sex: '男', address: '深圳', post: '443000', phone: '1030' },
        { name: '张春江', type: '居民身份证', number: '2334234', sex: '男', address: '深圳', post: '443000', phone: '1030' }
    ]
}

var applicant_agent_listdata = {
    isShowColumn: [
        { name: '张春江', type: '居民身份证', phone: '2334234', authorize: '', agent: '123' },
        { name: '张春江', type: '居民身份证', phone: '2334234', authorize: '', agent: '123' }
    ]
}


//模板引擎部分
//申请人表格
var applicant_tpl = [
    '<tr class="">',
    '<th class="">序号</th>',
    '<th class="">被接待人姓名</th>',
    '<th class="">证件类型</th>',
    '<th class="">证件号</th>',
    '<th class="">性别</th>',
    '<th class="">常住地址</th>',
    '<th class="">邮编</th>',
    '<th class="">联系电话</th>',
    '<th class="">&nbsp</th>',
    '</tr>',
    '{@each isShowColumn as it, index}',
    '<tr class="">',
    '<td class="indexTd"></td>',
    '<td class=""><input type="text" value="${it.name}"/></td>',
    '<td class=""><select class=""><option>1</option><option>2</option></select></td>',
    '<td class=""><input type="text" value="${it.number}"/></td>',
    '<td class=""><select class=""><option>男</option><option>女</option></select></td>',
    '<td class=""><input type="text" value="${it.address}"/></td>',
    '<td class=""><input type="text" value="${it.post}"/></td>',
    '<td class=""><input type="text" value="${it.phone}"/></td>',
    '<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteRow(this);" /></a></td>',
    '</tr>',
    '{@/each}'

].join('\n');
$("#applicant-table").append(juicer(applicant_tpl, applicant_listdata));

//申请人表格
var applicant_tpl = [
    '<tr class="">',
    '<th class="">序号</th>',
    '<th class="">被接待人姓名</th>',
    '<th class="">证件类型</th>',
    '<th class="">证件号</th>',
    '<th class="">性别</th>',
    '<th class="">常住地址</th>',
    '<th class="">邮编</th>',
    '<th class="">联系电话</th>',
    '<th class="">&nbsp</th>',
    '</tr>',
    '{@each isShowColumn as it, index}',
    '<tr class="">',
    '<td class="indexTd"></td>',
    '<td class=""><input type="text" value="${it.name}"/></td>',
    '<td class=""><select class=""><option>1</option><option>2</option></select></td>',
    '<td class=""><input type="text" value="${it.number}"/></td>',
    '<td class=""><select class=""><option>男</option><option>女</option></select></td>',
    '<td class=""><input type="text" value="${it.address}"/></td>',
    '<td class=""><input type="text" value="${it.post}"/></td>',
    '<td class=""><input type="text" value="${it.phone}"/></td>',
    '<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteRow(this);" /></a></td>',
    '</tr>',
    '{@/each}'

].join('\n');
$("#applicant-table").append(juicer(applicant_tpl, applicant_listdata));



//申请人中代理人表格
var applicant_agent_tpl = [
    '<tr class="">',
    '<th class="">序号</th>',
    '<th class="">代理人姓名</th>',
    '<th class="">代理人类型</th>',
    '<th class="">电话</th>',
    '<th class="">是否有授权书</th>',
    '<th class="">被代理人</th>',
    '<th class="">&nbsp</th>',
    '</tr>',
    '{@each isShowColumn as it, index}',
    '<tr class="">',
    '<td class="indexTd"></td>',
    '<td class=""><input type="text" value="${it.name}"/></td>',
    '<td class=""><select class=""><option>1</option><option>2</option></select></td>',
    '<td class=""><input type="text" value="${it.phone}"/></td>',
    '<td class=""><input type="text" value="${it.authorize}"/></td>',
    '<td class=""><input type="text" value="${it.agent}"/></td>',
    '<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteRow(this);" /></a></td>',
    '</tr>',
    '{@/each}'

].join('\n');
$("#applicant-agent-table").append(juicer(applicant_agent_tpl, applicant_agent_listdata));


//被申请人表格
var applicant_tpl = [
    '<tr class="">',
    '<th class="">序号</th>',
    '<th class="">被申请人种类</th>',
    '<th class="">被申请人名称</th>',
    '</tr>',
    '{@each isShowColumn as it, index}',
    '<tr class="">',
    '<td class="indexTd"></td>',
    '<td class=""><input type="text" value="${it.name}"/></td>',
    '<td class=""><input type="text" value="${it.name}"/></td>',
    '<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteRow(this);" /></a></td>',
    '</tr>',
    '{@/each}'

].join('\n');
$("#applicant-table").append(juicer(applicant_tpl, applicant_listdata));



//列表添加功能
function applicant_insertRow() {
    $("#applicant-table").append('<tr class="">' +
        '<td class="indexTd"></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><select class=""><option>1</option><option>2</option></select></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><select class=""><option>男</option><option>女</option></select></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteRow(this);" /></a></td>' +
        '</tr>');
    updateIndex();
}

function applicant_agent_insertRow() {
    $("#applicant-agent-table").append('<tr class="">' +
        '<td class="indexTd"></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><select class=""><option>1</option><option>2</option></select></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteRow(this);" /></a></td>' +
        '</tr>');
    updateIndex();
}

//初始化
$(document).ready(function() {
    updateIndex();
    $('.applicant-agent-div').hide();
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

//申请人中添加申请人代理人
function add_agent() {
    if ($('.applicant-agent-div').is(':hidden')) {
        $('.applicant-agent-div').show();
    } else { applicant_agent_insertRow(); }
}