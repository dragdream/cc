var warnlistdata = {};
var noticelistdata = {};
var submitlistdata = {};
var analysislistdata = {};
var menuTrees = [];//已选菜单
var isFive = false;
var staticDate = "";//统计日期
function doInit(){
    initSysMsgRemind(); // 加载工作提醒
    initSysMsgNotice(); // 加载通知公告
    initAnalysisTotal(); // 加载模块统计
    initReportTotal(); // 加载各省数据报送情况
    initReportRecord(); // 加载最新数据报送情况
    modifyMsgCss();  // 修改消息样式
    FastMenuInit(); //加载快捷入口
}

function initAnalysisTotal() {
    var result = tools.requestJsonRs(contextPath+"/jdSysMsgGetCtrl/getSysMsgAnalysisTotal.action");
    if (result.rtState) {
        analysislistdata = {
            isShowColumn: result.rtData
        };
        staticDate = result.rtMsg;
        document.getElementById('closedate').innerText = "统计日期："+staticDate+"";
    }
    $("#page2").append(juicer(analysistpl,analysislistdata));
}

function initReportRecord() {
    var result = tools.requestJsonRs(contextPath+"/jdSysMsgGetCtrl/getSysMsgDataReportRecord.action");
    if (result.rtState) {
    	if(result.rtMsg == ""){
    		submitlistdata = {
    	            isShowColumn: result.rtData
    	        }
    	}else{
            document.getElementById('table1').innerText = result.rtMsg;
    	}
    }
    $("#table1").append(juicer(submittpl,submitlistdata));
}

function initSysMsgNotice() {
     var noticeJson = tools.requestJsonRs(contextPath+"/jdSysMsgGetCtrl/getSysMsgNotice.action");
    if(noticeJson.rtState) {
    	if(noticeJson.rtMsg == ""){
    		noticelistdata = {
    	            isShowColumn: noticeJson.rtData,
    	            contextPath: contextPath
    	        }
    	}else{
            document.getElementById('noticeUl').innerText = noticeJson.rtMsg;
    	}
    }
    $(".noticeUl").append(juicer(noticetpl,noticelistdata));
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

var submittpl=[
    '{@each isShowColumn as it}',
    '<tr><td class="subDate">${it.reportDate}</td><td class="subCity">${it.place}</td><td class="subData">${it.count}</td></tr>',
    '{@/each}'
].join('\n');

var analysistpl=[
    '<ul>',
    '{@each isShowColumn as it}',
    '<li><span class="num ${it.dataState}">${it.count}${it.unit}</span><i class="${it.icon}">&nbsp;${it.title}</i></li>',
    '{@/each}',
    '</ul>'
].join('\n');

function initReportTotal() {
    //page3高度自适应
    var page3height=$('body').height()-$('#page1').outerHeight(true)-$('#page2').outerHeight(true)-$('#closedate').outerHeight(true);
    $('#page3').css('height',page3height);
    var barheight=page3height-30;
    $('#barDiv').css('height',barheight);
   
    //图表部分
    var result = tools.requestJsonRs(contextPath+"/jdSysMsgGetCtrl/getMsgDataReportTotal.action");
    var dataJsonGroup = {};
    if(result.rtState) {
        dataJsonGroup = result.rtData;
    }

    var myChart = echarts.init(document.getElementById('barDiv')); 
    var option = {
    color: ['#3398DB'],
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        },
        extraCssText:'width:100px;height:50px;'  //设置提示框的大小
    }, 
    grid: {
        left: '1%',
        right: '1%',
        bottom: '3%',
        
        width:'98%',
        height:'80%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            data : dataJsonGroup.placeGroup,
            axisTick: {
                alignWithLabel: true
            },

            axisLabel:{  
                    // interval:0 ,  
                    // formatter:function(val){  
                    // return val.split("").join("\n");  
                    rotate : 45,
                    interval : 0
            }  

        }
    ],
    yAxis : [
        {
            type : 'value',
            axisLine: {show: false}, // 控制y轴线是否显示
            splitLine: {show: true}, // 控制网格线是否显示
            axisTick: {show: false}  // 去除y轴上的刻度线
        }
    ],
    series : [
        {
            type:'bar',
            barWidth: '70%',
            data : dataJsonGroup.countGroup,
        }
    ]
};

    

    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }

    window.onresize = function(){
        myChart.resize(); 
    }
}

function initSysMsgRemind() {
    var warnJson = tools.requestJsonRs(contextPath+"/jdSysMsgGetCtrl/getSysMsgRemind.action");
    if(warnJson.rtState) {
        warnlistdata = {
            isShowColumn: warnJson.rtData
        }
    }
    $(".warnBar").append(juicer(warntpl,warnlistdata));
}
/**
 * 20190510  yxy  修改首页快捷入口
 */
function addMenus(){
	top.bsWindow(contextPath + "/system/frame/default/mainForSimpleFast.jsp", "快捷入口选择",
			{
				width : "800",
				height : "370",
				buttons : [{
					name : "关闭",
					classStyle : "btn-alert-gray"
				},{
					name : "保存",
					classStyle : "btn-alert-blue"
				} ],
				submit : function(v, h) {
					var cw = h[0].contentWindow;
					if (v == "保存") {
						var status = cw.save();
						if (status == true) {
							window.location.reload();
							return true;
						}
					} else if (v == "关闭") {
						return true;
					}
				}
			});
}
function initTree(){
	//初始化表格
    menuDatagridInit();
    //初始化树
    tree = $('#testTree').tree({
        url : contextPath + '/jdSysMsgGetCtrl/buildUserMenuTree.action',
        method: 'post',
        animate:true,//动画
        lines:true,//书线图
        cascadeCheck:true,
        checkbox:true,
        onLoadSuccess: function (node, data){
        	var menuTreeCheckedJson = tools.requestJsonRs("/jdSysQuickMenuCtrl/listBypage.action");
        	for(var i=0;i<menuTreeCheckedJson.rows.length;i++){
        		var menuId = menuTreeCheckedJson.rows[i].sysMenuNo;
        		var parentId = menuId.substring(0,3);
//        		var checkedNode = $(this).tree('find', parentId);
//        		$(this).tree('expand', checkedNode.target);
        		var root = $(this).tree('find', menuId);
        		$(this).tree('check', root.target);
        	}
        }
    });
    
}
//始化表格
function menuDatagridInit(){
	var DataMenu = tools.requestJsonRs("/jdSysQuickMenuCtrl/listBypage.action");
	for(var j=0;j<DataMenu.rows.length;j++){
		menuTrees.push(DataMenu.rows[j]);
	}
	datagrid = $('#menuDatagrid').datagrid({
		data:menuTrees,
//		url : contextPath + '/jdSysQuickMenuCtrl/listBypage.action',
		singleSelect:false,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		checkbox:false,
		border:false,
		idField:'id',//主键列
		fitColumns:true,//列是否进行自动宽度适应  
		columns:[[
			{field:'text',title:'已选功能（最多选择五个）',width:50,align:'center' , halign: 'center'},
		]]
	});
}
//添加菜单
function selectedMenu(){
	//获取所有选择的节点
	var checkedTree = $('#testTree').tree('getChecked');
	menuTrees = [];
	for(var i=0;i<checkedTree.length;i++){
		if(checkedTree[i].id.length>3){
			if(menuTrees.length <= 5){
				menuTrees.push(checkedTree[i]);
			}
		}
		if(menuTrees.length > 5){
			isFive = true;
		}else{
			isFive = false;
		}
	}
	if(isFive){
		$.MsgBox.Alert_auto("最多选择五个");
	}else{
		$('#menuDatagrid').datagrid('loadData',menuTrees);
	}
}
//删除菜单
function removeMenu(){
	var selectdTree = $('#menuDatagrid').datagrid('getChecked');
	for (var i=0;i<selectdTree.length;i++){
		//已选菜单删除
		for(var j=0;j<menuTrees.length;j++){
			if(selectdTree[i].id == menuTrees[j].id){
				menuTrees.splice(j,1);
			}
		}
		//菜单树去除勾选
		var deleteNode = $('#testTree').tree('find', selectdTree[i].id);
		$('#testTree').tree('uncheck', deleteNode.target);
	}
	$('#menuDatagrid').datagrid('loadData',menuTrees);
}
//保存已选菜单
function save(){
	if(menuTrees.length>0){
		var param = {};
		param.menuMaps = JSON.stringify(menuTrees);
		var menuTreeJson = tools.requestJsonRs("/jdSysQuickMenuCtrl/save.action",param);
	    return menuTreeJson.rtState;
	}else{
		return true;
	}
}
function FastMenuInit(){
	var FastMenuJson = tools.requestJsonRs("/jdSysQuickMenuCtrl/listBypage.action");
	if(FastMenuJson.rows.length<=2){
		for(var i=0;i<FastMenuJson.rows.length;i++){
			var trHTML = buildFastMenuNode(FastMenuJson.rows[i]);
			var pageDoc = $("#enterTbIdTr1").append(trHTML);
			$.parser.parse(pageDoc);
		}
		var addHtml = buildAddMenuNode();
		var pageDoc1 = $("#enterTbIdTr1").append(addHtml);
		$.parser.parse(pageDoc1);
	}else if(FastMenuJson.rows.length == 3){
		for(var i=0;i<FastMenuJson.rows.length;i++){
			var trHTML = buildFastMenuNode(FastMenuJson.rows[i]);
			var pageDoc = $("#enterTbIdTr1").append(trHTML);
			$.parser.parse(pageDoc);
		}
		var addHtml = "<tr id=\"enterTbIdTr2\">"+buildAddMenuNode()+"</tr>";
		var pageDoc1 = $("#enterTbId").append(addHtml);
		$.parser.parse(pageDoc1);
	}else{
		for(var i=0;i<3;i++){
			var trHTML = buildFastMenuNode(FastMenuJson.rows[i]);
			var pageDoc0 = $("#enterTbIdTr1").append(trHTML);
			$.parser.parse(pageDoc0);
		}
		var taHTML = "<tr id=\"enterTbIdTr2\">"+buildFastMenuNode(FastMenuJson.rows[i])+"</tr>";
		var pageDoc = $("#enterTbId").append(taHTML);
		$.parser.parse(pageDoc);
		if(FastMenuJson.rows.length == 5){
			var trHTML5 = buildFastMenuNode(FastMenuJson.rows[i]);
			var pageDoc5 = $("#enterTbIdTr2").append(trHTML5);
			$.parser.parse(pageDoc5);
		}
		var addHtml = buildAddMenuNode();
		var pageDoc1 = $("#enterTbIdTr2").append(addHtml);
		$.parser.parse(pageDoc1);
	}
}

function buildFastMenuNode(node){
    var nodeHtml = '';
    if(node != null){
        nodeHtml = nodeHtml + "<td>";
        nodeHtml = nodeHtml + "<span class='icon-frame'>";
        if(node.menuType != null){
            nodeHtml = nodeHtml + "<span class='"+node.menuType+"' onclick='newPageInfo(\""+node.text+"\",\""+node.url+"\")'>";
        }else{
            return '';
        }
        nodeHtml = nodeHtml + "<i class='icon-font fa "+node.icon+"'>";
        nodeHtml = nodeHtml + "</i>";
        nodeHtml = nodeHtml + "</span>";
        nodeHtml = nodeHtml + "<span>"+node.text+"</span>";
        nodeHtml = nodeHtml + "</span>";
        nodeHtml = nodeHtml + "</td>";
    }
    return nodeHtml;
}

function newPageInfo(name,url){
	window.parent.NewPageINfo(name,url);
}

function buildAddMenuNode(){
    var addHtml = "<td id=\"add_td\">";
    addHtml = addHtml +"<span class='icon-frame'>";
    addHtml = addHtml + "<span  class='icon-box-blank' href='javaScript:void(0);' onclick='addMenus()'>";
    addHtml = addHtml + "<i class='icon-font'></i>";
    addHtml = addHtml + "</span>";
    addHtml = addHtml + "<span>&nbsp;</span>";
    addHtml = addHtml + "</span>";
    addHtml = addHtml + "</td>";
    return addHtml;
}

function loadDetail(){
	top.bsWindow(contextPath + "/system/core/base/notify/person/index.jsp","公告查看",
			{
				width : "1000",
				height : "500",
				buttons : [ {
					name : "关闭",
					classStyle : "btn-alert-gray"
				} ],
				submit : function(v, h) {
					var cw = h[0].contentWindow;
					if (v == "保存") {
						var status = cw.save();
						if (status == true) {
							$("#datagrid").datagrid("reload");
							$('#datagrid').datagrid("clearSelections");
							return true;
						}
					} else if (v == "关闭") {
						return true;
					}
				}
			});
}