var warnJson;
var warnlistdata = {};
var noticeJson;
var noticelistdata = {};

function doInit(){
    initSysMsgRemind(); // 加载消息提醒
    $(".warnBar").append(juicer(warntpl,warnlistdata));
    initSysMsgNotice(); // 加载通知公告
    $(".noticeUl").append(juicer(noticetpl,noticelistdata));
    modifyMsgCss();  //修改消息样式

}

function initSysMsgRemind() {
    warnJson = tools.requestJsonRs(contextPath+"/jdSysMsgGetCtrl/getSysMsgRemind.action");
    if(warnJson.rtState) {
        warnlistdata = {
            isShowColumn: warnJson.rtData
        }
    }
}

function initSysMsgNotice() {
    noticeJson = tools.requestJsonRs(contextPath+"/jdSysMsgGetCtrl/getSysMsgNotice.action");
    if(noticeJson.rtState) {
        noticelistdata = {
            isShowColumn: noticeJson.rtData,
            contextPath: contextPath
        }
    }
}

function modifyMsgCss(){
    $(".warnBar li").each(function(){
        if($(this).children("#warnType").text()=="【待办】"){
              $(this).css("color","#ff845b");
            }
    });

    $(".noticeUl li").each(function(){
        if($(this).children("a").text().indexOf("【置顶】") !== -1 ){
            $(this).children("a").css("color","red");
        }
    });
}

var warntpl=[
    '{@each isShowColumn as it}',
    '<li>',
    '<span id="warnType">【${it.title}】</span>',
    '<span id="warnContent">${it.content}</span>',
    '<span id="warnData">(${it.count}${it.unit})</span>',
    ' </li>',
    '{@/each}'
   ].join('\n');

var noticetpl=[
    '{@each isShowColumn as it,index}',
    '<li {@if it.isLink===1}onclick="window.open(\'${contextPath}${it.url}\')"{@/if}>',
    '<a>{@if it.isTop===1}【置顶】{@/if}${it.content}',
    '</a>',
    '<span class="noticeDate">${it.date}</span>',
    ' </li>',
    '{@/each}'
].join('\n');
