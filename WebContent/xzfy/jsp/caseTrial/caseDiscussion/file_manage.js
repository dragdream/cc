var material_listdata = {
    list: [{
        type: '申请人材料',
        content: [{
            title: '申请书',
            file: [
                { fileinfo: 'xzfy.doc,sdsdas' },
                { fileinfo: 'xy.doc,sdsdas' }
            ]
        }, {
            title: '登记材料',
            file: null
        }]
    }, {
        type: '第三材料',
        content: [{
            title: '补充材料',
            file: [
                { fileinfo: 'xzfy.doc,sdsdas' },
                { fileinfo: 'xy.doc,sdsdas' }
            ]
        }]
    }]
}


var material_tpl = [
    '<table class="material">',
    '<tr>',
    '<td class="material-title regTb"><span class="">行政复议登记表</span></td>',
    '<td class="material-content">',
    '<ul class="material-ul">',
    '</ul>',
    '</td>',
    '<td class="material-func">',
    '<button onclick="deleteMtr(this);">删除</button>',
    '<button onclick="uplodeMtr(this);">上传</button>',
    '</td>',
    '</tr>',
    '</table>',
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
    '<button onclick="deleteMtr(this);">删除</button>',
    '<button onclick="uplodeMtr(this);">上传</button>',
    '</td>',
    '</tr>',
    '{@/each}',
    '</table>',
    '{@/each}'
].join('\n');
$("#d1").append(juicer(material_tpl, material_listdata));

//删除二级
function deleteMtr(that) {
    if ($(that).parent().parent().siblings().length == 1) {
        $(that).parent().parent().parent().remove();
    }
    $(that).parent().parent().remove();
}

//案件登记表增加一行
function insertMtr() {
    $("#d1").append('<table class="material">' +
        '<th class=""><input type="text" value=""/></th>' +
        '<th class=""></th>' +
        '<th class=""></th>' +
        '<tr>' +
        '<td class="material-title"><input type="text" value=""/><span class=""></span></td>' +
        '<td class="material-content">' +
        '<ul class="material-ul">' +
        '</ul>' +
        '</td>' +
        '<td class="material-func">' +
        '<button onclick="deleteMtr(this);">删除</button>' +
        '<button onclick="uplodeMtr(this);">上传</button>' +
        '</td>' +
        '</tr>' +
        '</table>');
}
//删除材料文件
function deletefile(that) {
    if ($(that).parent().parent().siblings('li').length == 0) {
        $(that).parent().parent().parent().css('color', 'red');
        $(that).parent().parent().parent().text('未上传');
    }
    $(that).parent().parent('li').remove();
}