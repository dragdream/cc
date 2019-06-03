
//类型
var type = "4";

function doInit(){
	//正卷
	var html = createPositiveHtml(null);
	$("#content").html(html);
}

//案件归档
function caseArchive(caseId){
	
}

//tab页切换
function change(that,type){
	
	$(that).siblings().removeClass().addClass("case-tab");
	$(that).removeClass().addClass("actived-tables");
	if(type==1){
		var html = createPositiveHtml(null);
		$("#content").html(html);
	}
	else{
		var html = createSecondaryHtml(null);
		$("#content").html(html);
	}
	
}

//材料类型
var material_listdata = {
    list: [{
        type: '申请人材料',
        content: [{
            title: '申请书',
            file: [{ fileinfo: 'xzfy.doc,sdsdas' },]
        }, 
        {
            title: '收件登记材料',
            file: [{ fileinfo: '1.doc,sdsdas' },]
        }, 
        {
            title: '申请人提交的其他材料',
            file: [{ fileinfo: '1.doc,sdsdas' },]
        }, 
        {
            title: '申请人补正材料',
            file: [{ fileinfo: '1.doc,sdsdas' },]
        }
        ]
    }, {
        type: '被申请人材料',
        content: [{
            title: '被申请人答复书',
            file: [
                { fileinfo: 'xzfy.doc,sdsdas' },
            ]
        }, {
            title: '被申请人证据材料',
            file: [
                { fileinfo: '1.doc,sdsdas' },
            ]
        }, {
            title: '被申请人补正材料',
            file: [
                { fileinfo: '1.doc,sdsdas' },
            ]
        }]
    }, {
        type: '第三人材料',
        content: [{
            title: '第三人有关材料',
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
            title: '其他材料',
            file: [
                { fileinfo: '1.doc,sdsdas' },
            ]
        }]
    }]
}

//正卷
function createPositiveHtml(data){
	var html = "";
	var list = material_listdata.list;
	for(var i=0;i<list.length;i++){
		html = html +'<table class="material">'
	    	+'<th class="">'+list[i].type+'</th>'
	    	+'<th class=""></th>'
	    	+'<th class=""></th>';
	    var content = list[i].content;
	    for(var k= 0;k< content.length;k++){
	    	html = html + '<tr>'
	    		+'<td class="material-title"><span class="textell">'+content[k].title+'</span></td>'
	    		+'<td class="material-content">'
	    		+'<ul class="material-ul">';
	    	if(content[k].file =="" || content[k].file==null){
	    		html = html + '<li><span class="nofile">未上传</span></li>';
	    	}
	    	else{
	    		var files = content[k].file;
	    		for(var t=0;t<files.length;t++){
	    			html = html + '<li><span>'+files[t].fileinfo+'</span>'
	    				+'<span class="li-func"><a>预览</a>'
	    				+'<a onclick="deletefile(this);">删除</a></span></li>';
	    		}
	    	}
	    	html = html +'</ul>'
	    		+'</td>'
	    		+'<td class="material-func">'
	    		+'<input class="fy-btn uplode-btn" type="button" value="上传" title="上传" onclick="uplodeMtr(this);" />'
	    		+'</td>'
	    		+'</tr>';
	    }
	    html = html +'</table>';
	}
	return html;
}

//副卷归档
function createSecondaryHtml(data){
	var html = "";
	var list = material_listdata.list;
	for(var i=0;i<list.length;i++){
		html = html +'<table class="material">'
	    	+'<th class="">'+list[i].type+'</th>'
	    	+'<th class=""></th>';
	    var content = list[i].content;
	    for(var k= 0;k< content.length;k++){
	    	html = html + '<tr>'
	    		+'<td class="material-title"><span class="textell">'+content[k].title+'</span></td>'
	    		+'<td class="material-content">'
	    		+'<ul class="material-ul">';
	    	if(content[k].file =="" || content[k].file==null){
	    		html = html + '<li><span class="nofile">未上传</span></li>';
	    	}
	    	else{
	    		var files = content[k].file;
	    		for(var t=0;t<files.length;t++){
	    			html = html + '<li><span>'+files[t].fileinfo+'</span>'
	    				+'<span class="li-func"><a>预览</a>'
	    				+'</span></li>';
	    		}
	    	}
	    	html = html +'</ul>'
	    		+'</td>'
	    		+'</tr>';
	    }
	    html = html +'</table>';
	}
	return html;
}

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