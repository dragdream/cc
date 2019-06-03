var material_listdata = {
    list: [{
        type: '申请人材料',
        content: [{
            title: '申请书',
            file: [
                { fileinfo: 'xzfy.doc,sdsdas' },
            ]
        }, {
            title: '收件登记材料',
            file: [
                { fileinfo: '1.doc,sdsdas' },
            ]
        }, {
            title: '申请人提交的其他材料',
            file: [
                { fileinfo: '1.doc,sdsdas' },
            ]
        }, {
            title: '阅卷笔录、阅卷意见及材料',
            file: [
                { fileinfo: '1.doc,sdsdas' },
            ]
        }]
    }, {
        type: '被申请人材料',
        content: [{
            title: '阅卷笔录、阅卷意见及材料',
            file: [
                { fileinfo: 'xzfy.doc,sdsdas' },
            ]
        }, {
            title: '被申请人答复书',
            file: [
                { fileinfo: '1.doc,sdsdas' },
            ]
        }, {
            title: '被申请人证据材料',
            file: [
                { fileinfo: '1.doc,sdsdas' },
            ]
        }]
    }, {
        type: '第三材料',
        content: [{
            title: '被申请人证据材料',
            file: [
                { fileinfo: 'xzfy.doc,sdsdas' },
            ]
        }]
    }, {
        type: '复议机关材料',
        content: [{
            title: '登记文书',
            file: null
        }, {
            title: '立案文书',
            file: [
                { fileinfo: '1.doc,sdsdas' },
            ]
        }, {
            title: '审理文书',
            file: [
                { fileinfo: '1.doc,sdsdas' },
            ]
        }, {
            title: '结案文书',
            file: [
                { fileinfo: '1.doc,sdsdas' },
            ]
        }, {
            title: '审批表',
            file: [
                { fileinfo: '1.doc,sdsdas' },
            ]
        }, {
            title: '其他',
            file: [
                { fileinfo: '1.doc,sdsdas' },
            ]
        }]
    }]
}

var material_tpl = [
    '{@each list as it1}',
    '<table class="material">',
    '<th class="">${it1.type}</th>',
    '<th class=""></th>',
    '<th class=""></th>',
    '{@each it1.content as it2}',
    '<tr>',
    '<td class="material-title"><span class="">${it2.title}</span></td>',
    '<td class="material-content">',
    '<ul class="material-ul">',
    '{@if it2.file === null }',
    '<li><span class="nofile">未上传</span></li>',
    '{@else}',
    '{@each it2.file as it3}',
    '<li><span>${it3.fileinfo}</span><span class="li-func"><a>预览</a><a onclick="deletefile(this);">删除</a></span></li>',
    '{@/each}',
    '{@/if}',
    '</ul>',
    '</td>',
    '<td class="material-func">',
    '<input class="fy-btn uplode-btn" type="button" value="上传" title="上传" onclick="uplodeMtr(this);" />',
    '</td>',
    '</tr>',
    '{@/each}',
    '</table>',
    '{@/each}'
].join('\n');
$(".materialDiv").append(juicer(material_tpl, material_listdata));

//删除二级
function deleteMtr(that) {
    if ($(that).parent().parent().siblings().length == 1) {
        $(that).parent().parent().parent().remove();
    }
    $(that).parent().parent().remove();
}

//删除材料文件
function deletefile(that) {
    if ($(that).parent().parent().siblings('li').length == 0) {
        $(that).parent().parent().parent().css('color', 'red');
        $(that).parent().parent().parent().text('未上传');
    }
    $(that).parent().parent('li').remove();
}