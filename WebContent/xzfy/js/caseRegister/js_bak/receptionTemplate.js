// 数据部分
var recp_listdata;

var material_listdata = {
    isShowColumn: [
        { name: '张春江', type: '居民身份证', fnum: '2334234', znum: '1030' },
        { name: '张春江', type: '居民身份证', fnum: '2334234', znum: '1030' }
    ]
}

//模板引擎 被接待人信息
var recp_tpl = [
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
    '<td class=""><input type="text" value="${it.visitorName}"/></td>',
    '<td class=""><select class=""><option value="${it.cardType}">1</option><option value="${it.cardType}">2</option></select></td>',
    '<td class=""><input type="text" value="${it.number}"/></td>',
    '<td class=""><select class=""><option value="${it.sexCode}">男</option><option value="${it.sexCode}">女</option></select></td>',
    '<td class=""><input type="text" value="${it.postAddress}"/></td>',
    '<td class=""><input type="text" value="${it.postCode}"/></td>',
    '<td class=""><input type="text" value="${it.phoneNum}"/></td>',
    '<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteRow(this);" /></a></td>',
    '</tr>',
    '{@/each}'
].join('\n');


var material_tpl = [
    '<tr class="">',
    '<th class="">材料名称</th>',
    '<th class="">材料类型</th>',
    '<th class="">份数</th>',
    '<th class="">张数</th>',
    '<th class="">&nbsp</th>',
    '</tr>',
    '{@each isShowColumn as it, index}',
    '<tr class="">',
    '<td class=""><input type="text" value="${it.name}"/></td>',
    '<td class=""><select class=""><option>1</option><option>2</option></select></td>',
    '<td class=""><input type="text" value="${it.fnum}"/></td>',
    '<td class=""><input type="text" value="${it.znum}"/></td>',
    '<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteRow(this);" /></a></td>',
    '</tr>',
    '{@/each}'
].join('\n');
//渲染
$("#material-table").append(juicer(material_tpl, material_listdata));

//初始化
$(document).ready(function() {
    updateIndex();
	$('.material-div').hide();
});

//公共列表删除功能
function deleteRow(that) {
    $(that).parents('tr').remove();
    updateIndex();
}

//列表添加功能 /* 被接待人信息   */
function recp_insertRow() {
    $("#recp-table").append('<tr class="">' +
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

function material_insertRow() {
    $("#material-table").append('<tr class="">' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><select class=""><option>1</option><option>2</option></select></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><input type="text" value=""/></td>' +

        '<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteRow(this);" /></a></td>' +
        '</tr>');
}
//公共更新列表序号
function updateIndex() {
    var index = 0;
    $(".indexTd").each(function() {
        index++;
        $(this).text(index);
    });
}

//按钮显示隐藏表格
function showTable(){$('.material-div').show();}

//function hideTable(){$('.material-div').hide();}



// 申请方式切换
function selectPostType(){
	document.getElementById("sel")[2].selected=true;
}